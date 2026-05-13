package com.sw.VirtualBookshop.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
           
	@Autowired
    private JwtAuthFilter jwtAuthFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception  {
		
		httpSecurity
		.csrf(csrf-> csrf.disable())
		.authorizeHttpRequests(auth ->auth 
				.requestMatchers(
						 "/",  
						 "/index.html",
						 "/login.html",
		                    "/signup.html",
		                    "/book.html",
		                    "/cart.html",
		                    "/script.js",
		                    "/style.css",
	                    "/users/login", "/images/**",
	               
	                    "/users/signup", 
	                    "/books/**","/orders.html"
	                ).permitAll()
				 .anyRequest().authenticated()
				) 
		 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
		.formLogin(form->form.disable())
		.httpBasic(basic -> basic.disable());
		
		return httpSecurity.build();
		
	}
	

}
