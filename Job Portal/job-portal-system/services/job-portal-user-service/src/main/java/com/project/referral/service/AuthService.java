package com.project.referral.service;

import com.project.referral.common.dto.response.AuthResponse;
import com.project.referral.common.exception.UserException;
import com.project.referral.dto.request.SignupRequest;
import com.project.referral.common.dto.request.LoginRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest req) throws UserException;

    AuthResponse login(LoginRequest req) throws UserException;
}
