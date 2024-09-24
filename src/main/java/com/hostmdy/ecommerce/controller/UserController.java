package com.hostmdy.ecommerce.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hostmdy.ecommerce.config.JwtTokenProvider;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.domain.security.Role;
import com.hostmdy.ecommerce.domain.security.UserRole;
import com.hostmdy.ecommerce.exception.RoleNotFoundException;
import com.hostmdy.ecommerce.payload.JwtSuccessResponse;
import com.hostmdy.ecommerce.payload.LoginRequest;
import com.hostmdy.ecommerce.payload.UpdatePasswordRequest;
import com.hostmdy.ecommerce.service.MapValidationErrorService;
import com.hostmdy.ecommerce.service.RoleService;
import com.hostmdy.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private static final String TOKEN_PREFIX = "Bearer ";

	private final UserService userService;
	private final RoleService roleService;
	private final AuthenticationManager authManager;
	private final JwtTokenProvider tokenProvider;
	private final MapValidationErrorService mapValidationErrorService;
	private final BCryptPasswordEncoder passwordEncoder;


	
	private JwtSuccessResponse authenticate(User user, String password) {

		try {
			Authentication authentication = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), password));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwtToken = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

			List<String> roles = user.getUserRoles().stream().map((ur) -> ur.getRole().getName()).toList();

			return new JwtSuccessResponse(true, jwtToken, user, roles);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new JwtSuccessResponse();
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {
		Optional<User> userOpt = userService.getUserByUsername(loginRequest.getUsername());

		if (userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+loginRequest.getUsername()+" is not found");
		}
		System.out.println("OldPassword is "+userOpt.get().getPassword());
		System.out.println("LoingPassword is "+passwordEncoder.encode(loginRequest.getPassword()));

		return ResponseEntity.ok(authenticate(userOpt.get(), loginRequest.getPassword()));

	}


	@PostMapping("/signup")
	@Transactional
	public ResponseEntity<?> signup(@RequestBody User user) {
		Optional<Role> roleOpt = roleService.getRoleByName("ROLE_USER");

		if (roleOpt.isEmpty()) {
			log.error("ROLE_USER is not found");
			throw new RoleNotFoundException("role with name = ROLE_USER is not found");
		}

		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, roleOpt.get()));
		String rawPassword = user.getPassword();
		User createdUser = userService.createUser(user, userRoles);
		
		return ResponseEntity.ok(authenticate(createdUser, rawPassword));

	}
	
	@PutMapping("/updateInfo")
	public ResponseEntity<?> setUsername(@RequestBody User user, BindingResult result){
		ResponseEntity<Map<String, String>> errorResponse = mapValidationErrorService.validate(result);
				if(errorResponse != null) {
					return(errorResponse);
				}
			Optional<User> userOpt = userService.getUserById(user.getId());
			
			System.out.println("update user is ");
			System.out.println(user);
			
			User currentUser = userOpt.get();
			currentUser.setFirstName(user.getFirstName());
			currentUser.setLastName(user.getLastName());
			currentUser.setFullname(user.getFullname());
			currentUser.setUsername(user.getUsername());
				
			User updateUser = userService.save(currentUser);
			System.out.println(updateUser);
			return new ResponseEntity<User>(updateUser,HttpStatus.OK);
	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<?> setPassword(@RequestBody UpdatePasswordRequest updatePasswordRequest, Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		System.out.println(userOpt.get());
		System.out.println(updatePasswordRequest.getOldPassword());
		System.out.println(updatePasswordRequest.getNewPassword());
		
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		User updatedUser = userService.updateUserPassword(userOpt.get(), updatePasswordRequest.getOldPassword(), updatePasswordRequest.getNewPassword());
		System.out.println("updated password is "+updatedUser);
		
		return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/info")
	public ResponseEntity<?> getUser(@PathVariable Long userId){
		Optional<User> userOpt = userService.getUserById(userId);
		if(userOpt.isEmpty()) {
			return new ResponseEntity<String>("User with this id is not present",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User> (userOpt.get(),HttpStatus.OK);
		
	}
	

	

}
