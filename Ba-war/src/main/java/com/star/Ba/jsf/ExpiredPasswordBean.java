package com.star.Ba.jsf;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.service.OtpService;

@ManagedBean
@SessionScoped
public class ExpiredPasswordBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private int step = 1;

    private String login;

    private String otp;

    private String newPassword;

    private String confirmPassword;

    private Long userId;

    @EJB
    private OtpService otpService;

    @EJB
    private IUtilisateursDAO userDao;
    
    
    
    public void sendOtp() {
    	
    }
    
    
    
    public void validateOtp() {
    	
    }
    
    
    
    public void changePassword() {
    	
    }


    
    
    public void stepBack() {
        step = 1;
        otp = null;
    }
    
    
    public void resetAll() {

        step = 1;

        login = null;

        otp = null;

        newPassword = null;

        confirmPassword = null;

        userId = null;
    }
    
    
    public String goToLogin() {

        // reset propre de la session OTP
        resetAll();

        return "/index.xhtml?faces-redirect=true";
    }
    
    
    // getters setters 

	public int getStep() {
		return step;
	}



	public void setStep(int step) {
		this.step = step;
	}



	public String getLogin() {
		return login;
	}



	public void setLogin(String login) {
		this.login = login;
	}



	public String getOtp() {
		return otp;
	}



	public void setOtp(String otp) {
		this.otp = otp;
	}



	public String getNewPassword() {
		return newPassword;
	}



	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}



	public String getConfirmPassword() {
		return confirmPassword;
	}



	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    
    
    
    
    
    

}
