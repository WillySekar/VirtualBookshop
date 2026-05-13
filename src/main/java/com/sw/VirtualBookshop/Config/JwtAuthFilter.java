package com.sw.VirtualBookshop.Config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		   
		String authHeader=request.getHeader("Authorization");
		String token=null;
		String email = null;
		
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			token =authHeader.substring(7);
			email=jwtUtil.extractEmail(token);
		}
		
		if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			if(jwtUtil.isTokenValid(token)) {
				UsernamePasswordAuthenticationToken auth =new UsernamePasswordAuthenticationToken(email,null,new ArrayList<>());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		System.out.println("CORS request from: " + request.getHeader("Origin"));
		filterChain.doFilter(request, response);
	}
}
