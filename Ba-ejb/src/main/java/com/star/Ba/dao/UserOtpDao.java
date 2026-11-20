package com.star.Ba.dao;

import com.star.Ba.commun.OtpType;
import com.star.Ba.model.UserOtp;

public interface UserOtpDao {

    void save(UserOtp otp);
    
    void update(UserOtp otp);

//    UserOtp findLastActiveOtp(
//            Long userId,
//            OtpType otpType
//    );
    
	UserOtp findLastPendingOrValidatedOtp(
	        Long userId,
	        OtpType otpType
	);
    
    UserOtp findLastPendingOtp(
            Long userId,
            OtpType otpType
    );
    
    
    UserOtp findLastValidatedOtp(
            Long userId,
            OtpType otpType
    );
}