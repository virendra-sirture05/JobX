package com.project.referral.user.service;

import com.project.referral.user.entity.User;

public interface UserService {

    User findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);

}