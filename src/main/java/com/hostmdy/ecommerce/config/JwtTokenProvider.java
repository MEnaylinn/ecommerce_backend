package com.hostmdy.ecommerce.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.hostmdy.ecommerce.domain.User;
import com.hostmdy.ecommerce.exception.ClaimsNullException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	
	private static final Long EXPIRATION = 3_600_000L; // 1hr
	private static final SecretKey securityKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		
		String userId = user.getId().toString();
		
		Map<String,Object> claims = new HashMap<>();
		claims.put("id",user.getId());
		claims.put("username", user.getUsername());
		
		Date now = new Date();
		Date expiredDate = new Date(now.getTime()+EXPIRATION);
		
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setExpiration(expiredDate)
				.setIssuedAt(now)
				.signWith(securityKey,SignatureAlgorithm.HS512)
				.compact();	
	 }
	
	 public boolean validateToken(String token) {
		 try {
			 Jwts.parserBuilder().setSigningKey(securityKey).build().parseClaimsJws(token);
			 return true;
		 }catch (MalformedJwtException e) {
			 e.printStackTrace();
			log.error("token is invalid");
		}catch (ExpiredJwtException e) {
			e.printStackTrace();
			log.error("token is expired");
		}catch (UnsupportedJwtException e) {
			e.printStackTrace();
			log.error(token);
		}catch (IllegalArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("token is empty");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("token validation fails");
		}
		 
		return false;
		 
	 }
	 
	 public Long getUserId(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(securityKey).build().parseClaimsJws(token).getBody();
		if(claims == null) {
			throw new ClaimsNullException("claims object is null");
		}
		
		String userId = claims.get("id").toString();
		return Long.parseLong(userId);
	 }

}
