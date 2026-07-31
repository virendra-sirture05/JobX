package com.project.referral.service.impl;

import com.project.referral.common.domain.UserRole;
import com.project.referral.common.domain.UserStatus;
import com.project.referral.common.dto.response.UserResponse;
import com.project.referral.common.exception.UserException;
import com.project.referral.dto.request.UpdateUserRequest;
import com.project.referral.entity.User;
import com.project.referral.mapper.UserMapper;
import com.project.referral.repository.UserRepository;
import com.project.referral.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws UserException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id));
    }

    @Override
    public List<User> getUsersByRole(UserRole role) throws UserException {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getAllUsers() throws UserException {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserResponse updateProfile(String email, UpdateUserRequest req) throws UserException {
        User user = getUserByEmail(email);
        user.setFullName(req.getFullName());
        if (req.getPhone() != null) {
            user.setPhone(req.getPhone());
        }
        if (req.getProfileImage() != null) {
            user.setProfileImage(req.getProfileImage());
        }
        return UserMapper.toDTO(userRepository.save(user));
    }

    // ── Admin actions ──────────────────────────────────────────────────────────

    @Override
    @Transactional
    public UserResponse suspendUser(Long id) throws UserException {
        User user = getUserById(id);
        user.setStatus(UserStatus.SUSPENDED);
        user.setSuspendedAt(LocalDateTime.now());
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse activateUser(Long id) throws UserException {
        User user = getUserById(id);
        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedAt(null);
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse deleteUser(Long id) throws UserException {
        User user = getUserById(id);
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse changeUserRole(Long id, UserRole role) throws UserException {
        User user = getUserById(id);
        user.setRole(role);
        return UserMapper.toDTO(userRepository.save(user));
    }
}
