package com.jobx.server.cache;


import com.jobx.server.otp.model.OtpData;

public interface CacheService {

    void saveOtp(String email, OtpData otpData);

    OtpData getOtp(String email);

    void deleteOtp(String email);

}