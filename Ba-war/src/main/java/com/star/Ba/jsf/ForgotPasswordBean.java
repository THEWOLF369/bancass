package com.star.Ba.jsf;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.star.Ba.commun.OtpType;
import com.star.Ba.dao.IPasswordHistoryDAO;
import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.jsf.util.CryptPassword;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.jsf.util.PasswordValidator;
import com.star.Ba.model.PasswordHistory;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.OtpService;


@ManagedBean
@SessionScoped
public class ForgotPasswordBean implements Serializable {

    private int step = 1;

    private String login;

    private String otp;

    private String newPassword;

    private String confirmPassword;

    private Long userId;

    @EJB
    private IUtilisateursDAO userDao;

    @EJB
    private OtpService otpService;

 
    private static int MAX_FAILED_ATTEMPTS_ADMIN = 3; //3 pour admin
    
    private static int MAX_FAILED_ATTEMPTS_AGENT = 6; // 6 pour agent
    
    
	@EJB
	private IPasswordHistoryDAO passwordHistoryDAO;
	
	
 
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
    
    
    public void sendOtp() {

    	Utilisateurs user = userDao.searchLogin(login);
    	
        if (user == null) {

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Erreur",
                                    "Utilisateur introuvable"
                            )
                    );

            return;
        }

        /*
         * Sécurité :
         * ne jamais révéler si le compte existe
         */

        if (user != null) {

            userId = Long.valueOf(user.getIdUtilisateurs());

            otpService.generateForgotPasswordOtp(login, 
            		Integer.valueOf(reportController.OTP_EXPIRATION_MINUTES()), 
            		Integer.valueOf(reportController.OTP_MAX_ATTEMPTS()));
        }

        FacesMessage msg =
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "OTP envoyé",
                        "Vérifiez votre boite email"
                );

        FacesContext.getCurrentInstance()
                .addMessage(null, msg);

        step = 2;
    }
    
    
    
    
    public void validateOtp() {

        boolean valid =
                otpService.validateForgotPasswordOtp(
                		login,
                        otp
                );

        if (!valid) {

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Erreur",
                                    "Code OTP invalide ou expiré"
                            )
                    );

            return;
        }
        
 

        step = 3;
    }
    
    
    
    public void changePassword() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {

    	try {
    		
    		
            // 1. Vérification champs password
            if (newPassword == null || confirmPassword == null
                    || newPassword.isEmpty() || confirmPassword.isEmpty()) {

                FacesContext.getCurrentInstance()
                        .addMessage(null,
                                new FacesMessage(
                                        FacesMessage.SEVERITY_ERROR,
                                        "Erreur",
                                        "Les mots de passe sont obligatoires"
                                )
                        );
                return;
            }
            
            
            
    		// 2. Vérification confirmation
            if (!newPassword.equals(confirmPassword)) {

                FacesContext.getCurrentInstance()
                        .addMessage(
                                null,
                                new FacesMessage(
                                        FacesMessage.SEVERITY_ERROR,
                                        "Erreur",
                                        "Les mots de passe ne correspondent pas"
                                )
                        );

                return;
            }
    		
    		
         // 3. Sécurité OTP (VALIDATED + fenêtre de 5 min)
    		if (!otpService.canResetPassword(
    				userId, Integer.valueOf(reportController.OTP_VALIDATED_EXPIRATION_MINUTES())
    				)) {

    		    FacesContext.getCurrentInstance()
    		            .addMessage(
    		                    null,
    		                    new FacesMessage(
    		                            FacesMessage.SEVERITY_ERROR,
    		                            "Session expirée",
    		                            "Veuillez recommencer la procédure OTP"
    		                    )
    		            );

    		    resetAll();

    		    return;
    		} 		
    		
    		
    		



     // 4. Changement mot de passe (SERVICE METIER)
        boolean success = NewPasswordForConnexion();
        
        // 🔥 SI ERREUR → STOP ICI
        if (!success) {
            return;
        }
        
        
        // 5. Marquer OTP comme USED
        otpService.markOtpAsUsed(userId, OtpType.FORGOT_PASSWORD);

        resetAll();

        FacesContext.getCurrentInstance()
                .addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Succès",
                                "Mot de passe modifié avec succès"
                        )
                );
        
    	} catch (Exception e) {

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Erreur",
                                    e.getMessage()
                            )
                    );
        }
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
    
    
    
    
    public boolean  NewPasswordForConnexion() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
  	  
  	  System.out.println("NewPasswordForConnexion begin");
  	  
  	  
//  	  nombreTentativeDepasser=false;
//        desactive=false;
//        loggedIn=true;
        
  	  Utilisateurs utilisateur = userDao.searchLogin(login);
  	  
/******* cas utilisateur introuvable ************************/
  	  
  	  if(utilisateur==null) {
  		  
//  		  loggedIn = false;

  		JsfUtil.addErrorMessage("Utilisateur introuvable");
  		  
  		  System.out.println("  profil introuvable");
  		  
  		  return false;
  	  } 
  	  
  	  /******* cas utilisateur existant ************************/    	  
  	  else {
  		  
  		  
  		  System.out.println("  profil trouvable");
  		  
//  		  loggedIn = true;
  		  
  		  String profil = utilisateur.getProfil().getCode();
  		  
 
   		 int MAX_FAILED_ATTEMPTS = 100;    		 
   		int nbr_tent_actuel = utilisateur.getNbrTentative();
   		
    		 if(     // admins star
        			  profil.equalsIgnoreCase("ASI") ||
 
        			  
        			  // admin bna
        			  profil.equalsIgnoreCase("BNA") ||
        			  
        			  // admin stb
        			  profil.equalsIgnoreCase("AS") ||
        			  profil.equalsIgnoreCase("AA")
        			  ) {
     			 
//     			 limit_before = 2; //2
     			MAX_FAILED_ATTEMPTS = MAX_FAILED_ATTEMPTS_ADMIN;  //3
     			 
     		 }else {
     			 
//     			 limit_before = 5; //5
     			MAX_FAILED_ATTEMPTS = MAX_FAILED_ATTEMPTS_AGENT;  //6
     		 }

   		
    		 // cas profil bloqué suite tentative
 		  if(nbr_tent_actuel >= MAX_FAILED_ATTEMPTS){
//			  desactive=false; 
//			  nombreTentativeDepasser=true;
 			 JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR,"Compte bloqué suite aux tentatives", "");
			  System.out.println(" utilisateur depassed");
			  
			  return false;
		  }
  		  
   		
   		// cas profil desactivé
  		  if(new Integer(utilisateur.getSituation()).equals(2)){
//  			  desactive=true ; 
//  			  nombreTentativeDepasser=false;
  			JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR,"Compte désactivé", "");
  			  System.out.println(" utilisateur desactived");
  			  
  			  return false;
  		  }
  		  
  		  
  		 
  		  
  	      
  	        	  
  	        	  if(     // admins star
  	        			  profil.equalsIgnoreCase("ASI") ||
 
  	        			  
  	        			  // admin bna
  	        			  profil.equalsIgnoreCase("BNA") ||
  	        			  
  	        			  // admin stb
  	        			  profil.equalsIgnoreCase("AS") ||
  	        			  profil.equalsIgnoreCase("AA")
  	        			  ) {
  	        		  
  	        		  
  	        		  System.out.println("profil admin.");
  	        		  
  	        		  if (!PasswordValidator.isStrongPasswordForAdmin(newPassword)) {
  	          		    System.out.println("Password admin is not strong.");
 
//  	          		   JsfUtil.showAlertMessageForPasswordAdmin();
  	          		JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR,  JsfUtil.messageText_admin, "");

  	          		    return false;
  	          		}
  	        		  
  	          		 List<PasswordHistory> historiesTop3 = passwordHistoryDAO.findTop3ByUserOrderByChangedAtDesc(utilisateur);
 
  	         		  
  	         		  if(!historiesTop3.isEmpty()) {
  	         			 System.out.println("PasswordHistory admin existed. (modification)");
  	         			 
  	         	        for (PasswordHistory ph : historiesTop3) {
  	         	            if (newPassword.equalsIgnoreCase(CryptPassword.decrypt(ph.getPassword())) ) {
  	         	     			JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Le nouveau mot de passe ne doit pas être l'un des " + historiesTop3.size() + " derniers mots de passe.", "");
  	         	     			 return false;
  	         	            }
  	         	        }	 

  	         		  }
  	        		  
  	        		  
  	        	  } else {  // non admin
  	        		  
  	        		  System.out.println("profil non admin.");
  	        		  
  	        		  if (!PasswordValidator.isStrongPassword(newPassword)) {
  	            		    System.out.println("Password is not strong.");
 
//  	            		   JsfUtil.showAlertMessageForPassword();
  	            		  JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erreur", JsfUtil.messageText_user);
  	            		    return false;
  	            		}
  	        		  
  	        		  
  	           		 List<PasswordHistory> historiesTop10 = passwordHistoryDAO.findTop10ByUserOrderByChangedAtDesc(utilisateur);
 
  	         		  
  	         		  if(!historiesTop10.isEmpty()) {
  	         			 System.out.println("PasswordHistory  existed. (modification)");
  	         			 
  	         	        for (PasswordHistory ph : historiesTop10) {
  	         	            if (newPassword.equalsIgnoreCase(CryptPassword.decrypt(ph.getPassword())) ) {
  	         	     			JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Le nouveau mot de passe ne doit pas être l'un des " + historiesTop10.size() + " derniers mots de passe.", "");
  	         	     			 return false;
  	         	            }
  	         	        }	 

  	         		  }
  	        		  
  	        		  
  	        	  }
  	        	  
  	        	  
  	        	  
  	          	 PasswordHistory passwordHistory = new PasswordHistory();
  	          	passwordHistory.setUtilisateurs(utilisateur);
  	          	passwordHistory.setPassword(CryptPassword.encrypt(newPassword));
  	          	passwordHistory.setChangedAt(new Date());
  	          	
  	          	List<PasswordHistory> histories = passwordHistoryDAO.findUserOrderByChangedAtDesc(utilisateur);
  	          	if(!histories.isEmpty()) {
  	          		
  	          		histories.add(passwordHistory);
  	          	} else {
  	          		
  	          		histories = new ArrayList<>();
  	          		histories.add(passwordHistory);
  	          	}
  	          	
  	          	utilisateur.setPasswordHistories(histories);
  	        	  
  	        	    
 
//  	        	  expiredMdp = false;
  		  			utilisateur.setSituation(1);
  	                utilisateur.setNbrTentative(0);
 
  	              utilisateur.setPassword(CryptPassword.encrypt(newPassword));
	    			utilisateur.setDateLastChangedPwd(new Date());
  	                userDao.edit(utilisateur);
//  	                loggedIn = true;
//  	                loggedInV2 = true;
//  	                  desactive=false;
//  	                  nombreTentativeDepasser=false;
  	                  
 
  	                  
  	    			
 
//  	  			JsfUtil.addSuccessMessage("Mot de passe modifié avec succes");
  	                  
  	                  
  	                  return true;
  	        	
 
  		  
  		  
  		  
  	  }
  	  
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




	public ReportController getReportController() {
		return reportController;
	}




	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}


    
    
    
}







/*************** ancienne version *********************************************/
/*
@ManagedBean
@ViewScoped
public class ForgotPasswordBean implements Serializable {


	private static final long serialVersionUID = 1L;

	private boolean otpSent = false;
	
    private String login;
    private String otp;
    private String newPassword;
    private String confirmPassword;
    
    private boolean otpValidated = false;

    // injection service (EJB/Spring)

    @EJB
    private OtpService otpService;

    public void sendOtp() {

        try {
            otpService.generateForgotPasswordOtp(login);

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                "OTP envoyé",
                "Vérifiez votre email/SMS"));
            
            otpSent = true;

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Erreur",
                e.getMessage()));
        }
    }

    public void resetPassword() {

        try {

            if (!otpValidated) {

                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Erreur",
                                "Veuillez d'abord valider l'OTP"
                        )
                );

                return;
            }

            if (!newPassword.equals(confirmPassword)) {

                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Erreur",
                                "Les mots de passe ne correspondent pas"
                        )
                );

                return;
            }

//            userService.resetPasswordWithOtp(
//                    login,
//                    newPassword
//            );

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Succès",
                            "Mot de passe modifié avec succès"
                    )
            );

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Erreur",
                            e.getMessage()
                    )
            );
        }
    }
    
    
    
    public void validateOtp() {

        try {

            boolean valid =
                    otpService.validateForgotPasswordOtp(
                            login,
                            otp
                    );

            if (!valid) {

                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                "Erreur",
                                "Code OTP invalide ou expiré"
                        )
                );

                return;
            }

            otpValidated = true;

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Succès",
                            "OTP validé"
                    )
            );

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Erreur",
                            e.getMessage()
                    )
            );
        }
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

	public boolean isOtpSent() {
		return otpSent;
	}

	public void setOtpSent(boolean otpSent) {
		this.otpSent = otpSent;
	}


	public boolean isOtpValidated() {
	    return otpValidated;
	}

	public void setOtpValidated(boolean otpValidated) {
	    this.otpValidated = otpValidated;
	}
    
    
  
    
}

*/  