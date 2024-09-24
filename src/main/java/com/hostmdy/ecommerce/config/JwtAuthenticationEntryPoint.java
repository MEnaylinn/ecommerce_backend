package com.hostmdy.ecommerce.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hostmdy.ecommerce.exception.InvalidLoginResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		InvalidLoginResponse invalidResponse = new InvalidLoginResponse();
		String jsonString = new Gson().toJson(invalidResponse);
		response.setContentType("application/json");
		response.setStatus(403);
		response.getWriter().print(jsonString);
		
	}

}
