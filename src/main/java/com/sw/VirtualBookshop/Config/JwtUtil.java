package com.sw.VirtualBookshop.Config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	 
	@Value("${jwt.secret}")
	private String secret;
	
	private final long EXPIRATION_TIME=1000*60*60;
	
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateToken(String email)
    {
    	return Jwts.builder()
    			 .setSubject(email)
    	         .setIssuedAt(new Date())
    	         .setExpiration(new Date(System.currentTimeMillis() +EXPIRATION_TIME))
    	         .signWith(getSigningKey(), SignatureAlgorithm.HS256)
    	         .compact();
    }
    
    public String extractEmail(String token) {
    	return getClaims(token).getSubject();
    }
    
    public Claims getClaims(String token) {
    	 return Jwts.parserBuilder()
    			 .setSigningKey(getSigningKey())
    			 .build()
    			 .parseClaimsJws(token)
    			 .getBody();
    }
    
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
