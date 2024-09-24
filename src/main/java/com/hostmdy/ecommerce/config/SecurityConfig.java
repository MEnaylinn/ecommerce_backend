package com.hostmdy.ecommerce.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import com.hostmdy.ecommerce.service.impl.UserSecurityService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final UserSecurityService userSecurityService;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	private final JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector inspector){
		return new MvcRequestMatcher.Builder(inspector);
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http,MvcRequestMatcher.Builder mvc) throws Exception {
		http.cors((cors) -> cors.configurationSource(corsConfigurationSource()));
		http.csrf((csrf) -> csrf.disable())
			.exceptionHandling((exceptionHandler) -> exceptionHandler.authenticationEntryPoint(authenticationEntryPoint))
			.sessionManagement((sessionManagement) -> 
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.headers((header) -> header.frameOptions((frameOption) -> frameOption.sameOrigin()))
			.securityMatcher("/api/**")
			.authorizeHttpRequests((auth) -> 
				auth
				.requestMatchers(mvc.pattern("/api/product/all")).permitAll()
				.requestMatchers(mvc.pattern("/api/product/category")).permitAll()
				.requestMatchers(mvc.pattern("/api/product/{productId:[0-9]+}")).permitAll()
				.requestMatchers(mvc.pattern("/api/image/{imageName}")).permitAll()
				.requestMatchers(mvc.pattern("/api/user/**")).permitAll()
				.requestMatchers(mvc.pattern("/api/email/contact")).permitAll()
				.requestMatchers(mvc.pattern("/api/cart-item/**")).permitAll()
				.requestMatchers(mvc.pattern("/api/shipping/**")).permitAll()
				.requestMatchers(mvc.pattern("/api/shipping/all")).permitAll()
				.requestMatchers(mvc.pattern("/api/payment/**")).permitAll()
				.requestMatchers(mvc.pattern("/api/order/**")).permitAll()
				.requestMatchers(mvc.pattern("/api/cart-item/add/**")).permitAll()
				.requestMatchers(mvc.pattern("/api/cart-item/add/{productId:[0-9]+}")).permitAll()
				.requestMatchers(mvc.pattern("/api/product/create")).hasRole("ADMIN")
				.requestMatchers(mvc.pattern("/api/product/update")).hasRole("ADMIN")
				.requestMatchers(mvc.pattern("/api/product/{productId:[0-9]+}/delete")).hasRole("ADMIN")
				.requestMatchers(mvc.pattern("/api/account/**")).hasRole("ADMIN")
				.requestMatchers(mvc.pattern("/api/image/upload/{productId:[0-9]+}")).hasRole("ADMIN")
				.anyRequest().authenticated()	
			);
			http.authenticationProvider(authProvider());
			http.addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class);
			return http.build();	
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userSecurityService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("*"));
	    configuration.setAllowedMethods(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	
	

}
