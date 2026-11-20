package com.star.Ba.service;

import com.star.Ba.commun.OtpType;

public interface OtpService {

    void generateForgotPasswordOtp_withUserId( Long userId );
    
    public void generateForgotPasswordOtp(String login, int expiration, int attemps);
    
    
    public void generateOtp(String login, int expiration, int attemps, OtpType otpType);
    
    boolean validateForgotPasswordOtp_withUserId(
            Long userId,
            String otpCode
    );
    
    
    boolean validateForgotPasswordOtp(
    		String loginOrEmail,
            String otpCode
    );
    
    
    public boolean validateOtp(String loginOrEmail, String otpCode, OtpType otpType);
    
    
    boolean canResetPassword(Long userId,int attemps);
    
    public boolean canResetPassword(Long userId, int expiration, OtpType otpType);
    
    void markOtpAsUsed(Long userId, OtpType otpType);
    
    
    public void unlockAccount(
            Long userId,
            String login,
            OtpType flowType,
            Integer expirationMinutes);
    
    
    public void changePassword(
            Long userId,
            String login,
            String newPassword,
            String confirmPassword,
            OtpType flowType,
            Integer expirationMinutes) throws Exception;
    
    
  /// a supprimer aprrs, seulement pour ancien projet 
    public void changeExpiredPassword( String login,
    		String oldPassword,
    		String newPassword,
    		String confirmPassword)throws Exception ;
}