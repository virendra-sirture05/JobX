package com.jobx.server.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobx.server.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	Optional<Boolean> existsByEmail(String email);
}
