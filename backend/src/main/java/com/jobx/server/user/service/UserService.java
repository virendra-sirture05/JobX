package com.jobx.server.user.service;

import com.jobx.server.user.entity.User;

public interface UserService {

    User findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);

}