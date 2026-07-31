package com.project.referral.repository;

import com.project.referral.common.domain.UserRole;
import com.project.referral.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findByRole(UserRole role);

    boolean existsByEmail(String email);
}
