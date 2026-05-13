package com.sw.VirtualBookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sw.VirtualBookshop.model.Users;
import com.sw.VirtualBookshop.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	 @Autowired
	  private UserService service;
	 
	 @PostMapping("/signup")
	 public ResponseEntity<String> signup(@RequestBody Users user) {
	     try {
	         boolean created = service.Signup(user);
	         if (created) {
	             return ResponseEntity.ok("User registered successfully!");
	             
	         } else {
	             return ResponseEntity.badRequest().body("Email already exists!");
	         }
	     } catch (Exception e) {
			 return ResponseEntity.internalServerError().body("An error occurred while registering.");
	     }
	 }
	 
	 @PostMapping("/login")
	 public ResponseEntity<String> login(@RequestBody Users user){
		       return service.login(user);
		
	 }

}
