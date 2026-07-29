package com.project.referral.cache;


import com.project.referral.otp.model.OtpData;

public interface CacheService {

    void saveOtp(String email, OtpData otpData);

    OtpData getOtp(String email);

    void deleteOtp(String email);

}