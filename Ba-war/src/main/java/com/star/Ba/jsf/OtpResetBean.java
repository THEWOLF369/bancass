package com.star.Ba.jsf;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
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

import com.star.Ba.commun.BusinessException;
import com.star.Ba.commun.Constante;
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
@ViewScoped
public class OtpResetBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int step = 1;

    private String mode; // from URL
    private OtpType flowType;

    private String login;
    private String otp;

    private Long userId;

    private String newPassword;
    private String confirmPassword;

    @EJB
    private OtpService otpService;

    @EJB
    private IUtilisateursDAO userDao;
    
    
	@EJB
	private IPasswordHistoryDAO passwordHistoryDAO;
	
	
 
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	
	
	@ManagedProperty("#{login}")
	private Login loginController;
	
	
    private static int MAX_FAILED_ATTEMPTS_ADMIN = 3; //3 pour admin
    
    private static int MAX_FAILED_ATTEMPTS_AGENT = 6; // 6 pour agent
    
    
    private boolean showNewPassword;
    private boolean showConfirmPassword;
    
    
	// ── Constantes ──────────────────────────────────────────────
    private final int SITUATION_DESACTIVE = Constante.SITUATION_COMPTE_DESACTIVE;//2
    private final int SITUATION_ACTIVE    = Constante.SITUATION_COMPTE_ACTIVE;//1
    private final int SITUATION_BLOCAGE    = Constante.SITUATION_COMPTE_DEBLOQUE; //0
	
	
    
    
    // pour ancienn logique seulement puis à supprimer
    private String oldPassword;
    private boolean showOldPassword;
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public boolean isShowOldPassword() {
        return showOldPassword;
    }

    public void setShowOldPassword(boolean showOldPassword) {
        this.showOldPassword = showOldPassword;
    }
    
    public void toggleOldPasswordVisibility() {

        showOldPassword =
                !showOldPassword;
    }
	

    // =========================
    // INIT JSF (PROPER WAY)
    // =========================
    @PostConstruct
    public void init() {
    	
    	System.out.println("*** Appel de init()*****");

        FacesContext ctx = FacesContext.getCurrentInstance();

        String m = ctx.getExternalContext()
                      .getRequestParameterMap()
                      .get("mode");
        
        System.out.println("*** mode recu : " + m + " *****");

        flowType = OtpType.fromString(m);
        mode     = flowType.getUrlParam();

        System.out.println("*** flowType : " + flowType + " *****");

        step = 1;
    }

    // =========================
    // STEP 1 - SEND OTP
    // =========================
    public void sendOtp() {
    	
    	try {
    	
    	 // 1. Vérifier login
        if (login == null || login.trim().isEmpty()) {

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erreur", "Veuillez saisir votre Identifiant"));
            return;
        }
    	
        
     // 2. Rechercher utilisateur
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

        

 
     // 3. stocker userId pour les étapes suivantes
            userId = Long.valueOf(user.getIdUtilisateurs());

         // 4. invalider ancien OTP (sécurité)
         // 5. générer OTP
            otpService.generateOtp(login, 
            		Integer.valueOf(reportController.OTP_EXPIRATION_MINUTES()), 
            		Integer.valueOf(reportController.OTP_MAX_ATTEMPTS()),
            		flowType);
   
            
            // 6. passer à l'étape OTP
            this.step = 2;

         // 7. message utilisateur
        FacesMessage msg =
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "OTP envoyé",
                        "Veuillez vérifier votre email"
                );

        FacesContext.getCurrentInstance()
                .addMessage(null, msg);
        
        


        } catch (Exception e) {

            e.printStackTrace();

            BusinessException be = BusinessException.findBusinessException(e);

            String msg = (be != null)
                    ? be.getMessage()
                    : "Une erreur est survenue. Veuillez réessayer plus tard.";

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Erreur",
                                    msg));
        }
   
    }

    // =========================
    // STEP 2 - VALIDATE OTP
    // =========================
    public void validateOtp() {
    	
    	
    	  // 1. Vérifier input
        if (otp == null || otp.trim().isEmpty()) {

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erreur", "Veuillez saisir le code OTP"));
            return;
        }
    	
    	
        // 2. Récupérer OTP actif
        boolean valid =
                otpService.validateOtp(
                		login,
                        otp,
                        flowType
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
        
 
     // 3. passer à l'étape password
        step = 3;

   
    }

    // =========================
    // STEP 3 A - CHANGE PASSWORD
    // =========================
    public void changePassword() {

        try {

            otpService.changePassword(
                    userId,
                    login,
                    newPassword,
                    confirmPassword,
                    flowType,
                    Integer.valueOf(
                            reportController
                                    .OTP_VALIDATED_EXPIRATION_MINUTES()));

            resetAll();
            
            loginController.reset();

            FacesContext fc = FacesContext.getCurrentInstance();

            fc.getExternalContext()
              .getFlash()
              .setKeepMessages(true);

            fc.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Succès",
                            "Mot de passe modifié avec succès"));

            fc.getApplication()
              .getNavigationHandler()
              .handleNavigation(
                      fc,
                      null,
                      "/index.xhtml?faces-redirect=true");

        } catch (Exception e) {

            e.printStackTrace();

            BusinessException be = BusinessException.findBusinessException(e);

            String msg = (be != null)
                    ? be.getMessage()
                    : "Une erreur est survenue. Veuillez réessayer plus tard.";

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Erreur",
                                    msg));
        }
    }
    
    public void changePassword_v0() {
    	
    	
    	try {
    		
    		
    		
    		 // 1. Vérifier champs
            if (newPassword == null || confirmPassword == null
                    || newPassword.trim().isEmpty()
                    || confirmPassword.trim().isEmpty()) {

                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Erreur", "Tous les champs sont obligatoires"));
                return;
            }
            
            
            // 2. Vérifier confirmation
            if (!newPassword.equals(confirmPassword)) {

                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Erreur", "Les mots de passe ne correspondent pas"));
                return;
            }
            
            
            // 3. Sécurité OTP (doit être VALIDATED + encore valide)
            if (!otpService.canResetPassword(
            		userId, 
            		Integer.valueOf(reportController.OTP_VALIDATED_EXPIRATION_MINUTES()),
            		flowType
            		)
            	){

                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Session expirée", "Veuillez recommencer la procédure OTP"));

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
            otpService.markOtpAsUsed(userId, flowType);

         // 6. reset flow
            resetAll();

//            FacesContext.getCurrentInstance()
//                    .addMessage(
//                            null,
//                            new FacesMessage(
//                                    FacesMessage.SEVERITY_INFO,
//                                    "Succès",
//                                    "Mot de passe modifié avec succès"
//                            )
//                    );
            
            
         // 7. Message Flash
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Succès",
                "Mot de passe modifié avec succès"
            ));

            // 8. Redirection correcte via NavigationHandler
            fc.getApplication()
              .getNavigationHandler()
              .handleNavigation(fc, null, "/index.xhtml?faces-redirect=true");
    		
    		
    		
    		
    	} catch (Exception e) {
    		
    		e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erreur", e.getMessage()));
        }

 
    }
    
  /// a supprimer aprrs, seulement pour ancien projet 
    public void changeExpiredPassword() {

        try {

            otpService.changeExpiredPassword(
                    login,
                    oldPassword,
                    newPassword,
                    confirmPassword);


            // =====================================================
            // SUCCES
            // =====================================================

            resetAll();

            loginController.reset();


            FacesContext fc =
                    FacesContext.getCurrentInstance();


            fc.getExternalContext()
              .getFlash()
              .setKeepMessages(true);


            fc.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Succès",
                            "Mot de passe modifié avec succès"));


            fc.getApplication()
              .getNavigationHandler()
              .handleNavigation(
                      fc,
                      null,
                      "/index.xhtml?faces-redirect=true");


        } catch (Exception e) {

            e.printStackTrace();


            BusinessException be =
                    BusinessException
                            .findBusinessException(e);


            String msg =
                    (be != null)
                            ? be.getMessage()
                            : "Une erreur est survenue. Veuillez réessayer plus tard.";


            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Erreur",
                                    msg));
        }
    }
    
    
    // =========================
    // STEP 3 B - DEBLOQUER COMPTE
    // =========================
    public void unlockAccount() {

        try {

            otpService.unlockAccount(
                    userId,
                    login,
                    flowType,
                    Integer.valueOf(reportController.OTP_VALIDATED_EXPIRATION_MINUTES())
            );
            
            resetAll();
            
            loginController.reset();

            FacesContext fc = FacesContext.getCurrentInstance();

            fc.getExternalContext()
              .getFlash()
              .setKeepMessages(true);

            fc.addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Succès",
                            "Compte débloqué avec succès"));

            fc.getApplication()
              .getNavigationHandler()
              .handleNavigation(
                      fc,
                      null,
                      "/index.xhtml?faces-redirect=true");

        } catch (Exception e) {

            e.printStackTrace();
            
            
            BusinessException be = BusinessException.findBusinessException(e);

            String msg = (be != null)
                    ? be.getMessage()
                    : "Une erreur est survenue. Veuillez réessayer plus tard.";

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Erreur",
                                    msg));
        }
    }
     
    

    // =========================
    // RESET
    // =========================
    public void resetAll() {
        step = 1;
        login = null;
        otp = null;
        userId = null;
        newPassword = null;
        confirmPassword = null;
        flowType = null;
    }
    
    public String goToLogin() {

        // reset propre de la session OTP
        resetAll();
        
        loginController.reset();

        return "/index.xhtml?faces-redirect=true";
    }
    
    
    public void stepBack() {
        step = 1;
        otp = null;
    }
    

    // =========================
    // Fonctions
    // =========================
    
    
	public String getStep3Label() {
	    return flowType == OtpType.LOCKED_ACCOUNT
	            ? "Déblocage"
	            : "Mot de passe";
	}
	
	public boolean isLockedAccount() {
	    return flowType == OtpType.LOCKED_ACCOUNT;
	}
 
    
    public boolean NewPasswordForConnexion() throws InvalidKeyException, 
    NoSuchPaddingException, NoSuchAlgorithmException, 
    UnsupportedEncodingException, BadPaddingException, 
    IllegalBlockSizeException {


			
			// ── Recherche utilisateur ────────────────────────────────────
			Utilisateurs utilisateur = userDao.searchLogin(login);
			
			if (utilisateur == null) {
			    JsfUtil.addErrorMessage("Utilisateur introuvable");
			    return false;
			}
			
			String profil      = utilisateur.getProfil().getCode();
			boolean isAdmin    = isAdminProfil(profil);
			int maxAttempts    = isAdmin ? MAX_FAILED_ATTEMPTS_ADMIN : MAX_FAILED_ATTEMPTS_AGENT;
			
			// ── Vérification blocage ─────────────────────────────────────
			if (utilisateur.getNbrTentative() >= maxAttempts) {
			    JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Compte bloqué suite aux tentatives", "");
			    return false;
			}
			
			// ── Vérification désactivation ───────────────────────────────
			if (utilisateur.getSituation() == SITUATION_DESACTIVE) {
			    JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Compte désactivé", "");
			    return false;
			}
			
			// ── Validation force du mot de passe ─────────────────────────
			if (isAdmin) {
			    if (!PasswordValidator.isStrongPasswordForAdmin(newPassword)) {
			        JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, JsfUtil.messageText_admin, "");
			        return false;
			    }
			} else {
			    if (!PasswordValidator.isStrongPassword(newPassword)) {
			        JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erreur", JsfUtil.messageText_user);
			        return false;
			    }
			}
			
			// ── Vérification historique ──────────────────────────────────
			int historyLimit          = isAdmin ? 3 : 10;
			List<PasswordHistory> histories = isAdmin
			    ? passwordHistoryDAO.findTop3ByUserOrderByChangedAtDesc(utilisateur)
			    : passwordHistoryDAO.findTop10ByUserOrderByChangedAtDesc(utilisateur);
			
			if (!histories.isEmpty()) {
			    for (PasswordHistory ph : histories) {
			        if (newPassword.equalsIgnoreCase(CryptPassword.decrypt(ph.getPassword()))) {
			            JsfUtil.addMessage(FacesMessage.SEVERITY_ERROR,
			                "Le nouveau mot de passe ne doit pas être l'un des " 
			                + historyLimit + " derniers mots de passe.", "");
			            return false;
			        }
			    }
			}
			
			// ── Sauvegarde ───────────────────────────────────────────────
			saveNewPassword(utilisateur);
			
			return true;
			}
			
			
			//── Méthodes extraites ───────────────────────────────────────────
			
			private boolean isAdminProfil(String profil) {
			return profil.equalsIgnoreCase("ASI") ||
			       profil.equalsIgnoreCase("BNA") ||
			       profil.equalsIgnoreCase("AS")  ||
			       profil.equalsIgnoreCase("AA");
			}
			
			private void saveNewPassword(Utilisateurs utilisateur) 
			    throws InvalidKeyException, NoSuchPaddingException, 
			           NoSuchAlgorithmException, UnsupportedEncodingException, 
			           BadPaddingException, IllegalBlockSizeException {
			
			// Ajout dans l'historique
			PasswordHistory passwordHistory = new PasswordHistory();
			passwordHistory.setUtilisateurs(utilisateur);
			passwordHistory.setPassword(CryptPassword.encrypt(newPassword));
			passwordHistory.setChangedAt(new Date());
			
			List<PasswordHistory> allHistories = 
			    passwordHistoryDAO.findUserOrderByChangedAtDesc(utilisateur);
			
			if (allHistories.isEmpty()) {
			    allHistories = new ArrayList<>();
			}
			allHistories.add(passwordHistory);
			utilisateur.setPasswordHistories(allHistories);
			
			// Mise à jour utilisateur
//			utilisateur.setSituation(SITUATION_ACTIVE);
			utilisateur.setNbrTentative(0);
			utilisateur.setSituationBlocage(SITUATION_BLOCAGE);
			utilisateur.setPassword(CryptPassword.encrypt(newPassword));
			utilisateur.setDateLastChangedPwd(new Date());
			userDao.edit(utilisateur);
			}
			 
    
    

    // =========================
    // GETTERS / SETTERS
    // =========================
    public int getStep() { return step; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

	public OtpType getFlowType() {
		return flowType;
	}

	public void setFlowType(OtpType flowType) {
		this.flowType = flowType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}

	public static int getMAX_FAILED_ATTEMPTS_ADMIN() {
		return MAX_FAILED_ATTEMPTS_ADMIN;
	}

	public static void setMAX_FAILED_ATTEMPTS_ADMIN(int mAX_FAILED_ATTEMPTS_ADMIN) {
		MAX_FAILED_ATTEMPTS_ADMIN = mAX_FAILED_ATTEMPTS_ADMIN;
	}

	public static int getMAX_FAILED_ATTEMPTS_AGENT() {
		return MAX_FAILED_ATTEMPTS_AGENT;
	}

	public static void setMAX_FAILED_ATTEMPTS_AGENT(int mAX_FAILED_ATTEMPTS_AGENT) {
		MAX_FAILED_ATTEMPTS_AGENT = mAX_FAILED_ATTEMPTS_AGENT;
	}

	public int getSITUATION_DESACTIVE() {
		return SITUATION_DESACTIVE;
	}

	public int getSITUATION_ACTIVE() {
		return SITUATION_ACTIVE;
	}

	public int getSITUATION_BLOCAGE() {
		return SITUATION_BLOCAGE;
	}

	public void setStep(int step) {
		this.step = step;
	}

	// NEW PASSWORD
	public boolean isShowNewPassword() {
	    return showNewPassword;
	}

	public void toggleNewPasswordVisibility() {
	    this.showNewPassword = !this.showNewPassword;
	}

	// CONFIRM PASSWORD
	public boolean isShowConfirmPassword() {
	    return showConfirmPassword;
	}

	public void toggleConfirmPasswordVisibility() {
	    this.showConfirmPassword = !this.showConfirmPassword;
	}

	public Login getLoginController() {
		return loginController;
	}

	public void setLoginController(Login loginController) {
		this.loginController = loginController;
	}


    
	

    
    
}
