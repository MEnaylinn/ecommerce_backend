package com.hostmdy.ecommerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostmdy.ecommerce.domain.Review;
import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.service.ReviewService;
import com.hostmdy.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
	private final ReviewService reviewService;
	private final UserService userService;
	
	@PostMapping("/create/{productId}")
	public ResponseEntity<Review> createReview(@RequestBody Review review,@PathVariable Long productId,Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(review,productId, userOpt.get()));
	}
	
	@PutMapping("/update/{productId}")
	public ResponseEntity<Review> updateReview(@RequestBody Review review,@PathVariable Long productId,Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(reviewService.createReview(review,productId, userOpt.get()));
	}
	
	@GetMapping("/all/{productId}")
	public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long productId){
		return ResponseEntity.ok(reviewService.getAllReview(productId));
	}
	
	@DeleteMapping("/{reviewId}/delete/{productId}")
	public ResponseEntity<String> deleteReviewById(@PathVariable Long reviewId,@PathVariable Long productId,Principal principal){
		String username = principal.getName();
		Optional<User> userOpt = userService.getUserByUsername(username);
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("user with username="+username+" is not found");
		}
		
		reviewService.deleteReviewById(reviewId,productId,userOpt.get());
		
		return ResponseEntity.ok(reviewId.toString());
	}
}
