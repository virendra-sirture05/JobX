package com.project.referral.service;


import com.project.referral.entity.OtpData;

public interface CacheService {

    void saveOtp(String email, OtpData otpData);

    OtpData getOtp(String email);

    void deleteOtp(String email);

}