package com.hostmdy.ecommerce.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hostmdy.ecommerce.service.impl.UserSecurityService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private static final String HEADER_STRING = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	
	private final JwtTokenProvider tokenProvider;
	private final UserSecurityService userSecurityService;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String jwtToken = getJwtTokenFromRequest(request);
			
			if(StringUtils.hasText(jwtToken) && tokenProvider.validateToken(jwtToken)) {
					UserDetails userDetails = userSecurityService.loadUserById(tokenProvider.getUserId(jwtToken));
					
					UsernamePasswordAuthenticationToken authenticationToken =
						UsernamePasswordAuthenticationToken.authenticated(userDetails,null,userDetails.getAuthorities());
					
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}	
		} catch (Exception e) {
			// TODO: handle 
			e.printStackTrace();
			log.error("token filter is failed");
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getJwtTokenFromRequest(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER_STRING);
		
		if(StringUtils.hasText(jwtToken) && jwtToken.startsWith(TOKEN_PREFIX)) {
			return jwtToken.substring(7,jwtToken.length());
		}
		return null;
	}

}
