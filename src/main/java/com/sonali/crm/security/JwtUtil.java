package com.sonali.crm.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final String SECRET = "mysecretkeymysecretkeymysecretkey12345";
	
	private SecretKey getSignKey() {
		 return Keys.hmacShaKeyFor(SECRET.getBytes());
	}
	
	//Generate Token...
	
	public String generateToken(String email) {
		return Jwts.builder()
				.subject(email)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000*60*60))
				.signWith(getSignKey())
				.compact();
	}
	
	//Extract email
	
	public String extractEmail(String token) {
	    try {
	        return Jwts.parser()
	                .verifyWith(getSignKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload()
	                .getSubject();
	    } catch (Exception  e) {
	        return null;  
	    }
	}
	// validate token
	
	public boolean validateToken(String token, UserDetails userDetails) {
		final String email = extractEmail(token);
		return (email != null && email.equals(userDetails.getUsername()));
	}

	
}
