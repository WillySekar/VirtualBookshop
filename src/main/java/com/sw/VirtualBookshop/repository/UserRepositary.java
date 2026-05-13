package com.sw.VirtualBookshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sw.VirtualBookshop.model.Users;

public interface UserRepositary extends JpaRepository<Users, Integer> {
              
	Optional<Users> findByEmail(String email);
}
