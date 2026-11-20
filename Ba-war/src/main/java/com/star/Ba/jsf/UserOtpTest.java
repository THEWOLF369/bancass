package com.star.Ba.jsf;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.star.Ba.commun.OtpStatus;
import com.star.Ba.commun.OtpType;
import com.star.Ba.dao.UserOtpDao;
import com.star.Ba.model.UserOtp;
import com.star.Ba.service.OtpService;


@ManagedBean
@ViewScoped
public class UserOtpTest implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private UserOtpDao userOtpDao;
	
    @EJB
    private OtpService otpService;
    
    private String otpCode;
	
	 @PostConstruct
	    public void init() {

//	        try {
//
//	            UserOtp otp = new UserOtp();
//
//	            otp.setUserId(1L);
//
//	            otp.setOtpCode("123456");
//
//	            otp.setOtpType(
//	                    OtpType.FORGOT_PASSWORD
//	            );
//
//	            otp.setStatus(
//	                    OtpStatus.PENDING
//	            );
//
//	            otp.setCreatedAt(new Date());
//
//	            otp.setExpiresAt(new Date());
//
//	            otp.setAttempts(0);
//
//	            otp.setMaxAttempts(3);
//
//	            otp.setIsUsed(0);
//
//	            userOtpDao.save(otp);
//
//	            System.out.println(
//	                    "OTP saved successfully"
//	            );
//
//	        } catch (Exception e) {
//
//	            e.printStackTrace();
//	        }
	    }
	 
	 
	 public void testOtp_0() {

		    UserOtp otp = new UserOtp();

		    otp.setUserId(1L);

		    otp.setOtpCode("123456");

		    otp.setOtpType(OtpType.FORGOT_PASSWORD);

		    otp.setStatus(OtpStatus.PENDING);

		    otp.setCreatedAt(new Date());

		    otp.setExpiresAt(new Date());

		    otp.setAttempts(0);

		    otp.setMaxAttempts(3);

//		    otp.setIsUsed(0);

		    userOtpDao.save(otp);

		    System.out.println("OTP SAVED");
		}
	 
	 
	  public void testOtp() {

	        otpService.generateForgotPasswordOtp_withUserId(1L);

	        System.out.println( "OTP GENERATED" );
	    }
	  
	  
	  public void validateOtp() {

		    boolean valid = otpService.validateForgotPasswordOtp_withUserId(
		                            1L,
		                            otpCode
		                    );

		    System.out.println( "VALID : " + valid );
		}


	public String getOtpCode() {
		return otpCode;
	}


	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}
	  
	  
	  

}
