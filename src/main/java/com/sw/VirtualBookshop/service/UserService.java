package com.sw.VirtualBookshop.service;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sw.VirtualBookshop.Config.JwtUtil;
import com.sw.VirtualBookshop.model.Users;
import com.sw.VirtualBookshop.repository.UserRepositary;

@Service
public class UserService {

	@Autowired
	private UserRepositary repositary;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JwtUtil jwtUtil;
	
	private static final Logger log=LoggerFactory.getLogger(UserService.class);
	 
	public Boolean Signup(Users user) {
		if(repositary.findByEmail(user.getEmail()).isPresent()) {
			return false;
		}else {
			user.setPassword(encoder.encode(user.getPassword()));
			repositary.save(user);
			return true;
		}	
	}

	public ResponseEntity<String> login(Users user) {
		Optional<Users> res= repositary.findByEmail(user.getEmail());
		  if(res.isPresent()) {
			  Users DbUsers= res.get(); 
			  if(encoder.matches(user.getPassword(), DbUsers.getPassword())) {
				 String token= jwtUtil.generateToken(user.getEmail());
				 System.out.println(token);
				  return ResponseEntity.ok(token);
			  }else {
				  return ResponseEntity.badRequest().body("Password Incorrect");
			  }
		  }else {
			  return ResponseEntity.badRequest().body("User Not Found");
		  }
	}
}
