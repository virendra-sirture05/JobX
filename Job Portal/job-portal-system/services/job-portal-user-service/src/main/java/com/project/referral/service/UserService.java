package com.project.referral.service;

import com.project.referral.common.domain.UserRole;
import com.project.referral.common.dto.response.UserResponse;
import com.project.referral.common.exception.UserException;
import com.project.referral.entity.User;
import com.project.referral.dto.request.UpdateUserRequest;


import java.util.List;

public interface UserService {

    User getUserByEmail(String email) throws UserException;

    User getUserById(Long id) throws UserException;

    List<User> getUsersByRole(UserRole role) throws UserException;

    List<User> getAllUsers() throws UserException;

    UserResponse updateProfile(String email, UpdateUserRequest req) throws UserException;

    // ── Admin actions ──────────────────────────────────────────────────────────
    UserResponse suspendUser(Long id) throws UserException;

    UserResponse activateUser(Long id) throws UserException;

    UserResponse deleteUser(Long id) throws UserException;

    UserResponse changeUserRole(Long id, UserRole role) throws UserException;
}
