package com.star.Ba.service;

 

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.joda.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.star.Ba.commun.BusinessException;
import com.star.Ba.commun.Constante;
import com.star.Ba.commun.CryptPassword;
import com.star.Ba.commun.OtpStatus;
import com.star.Ba.commun.OtpType;
import com.star.Ba.commun.PasswordValidator;
import com.star.Ba.dao.IPasswordHistoryDAO;
import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.dao.UserOtpDao;
import com.star.Ba.model.PasswordHistory;
import com.star.Ba.model.UserOtp;
import com.star.Ba.model.Utilisateurs;

@Stateless
public class OtpServiceImpl implements OtpService {

    @EJB
    private UserOtpDao userOtpDao;
    
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    
    @EJB
    private MailService mailService;
    
    
    @EJB
    private IUtilisateursDAO userDao;
    
    
	@EJB
	private IPasswordHistoryDAO passwordHistoryDAO;
    
    
    
    private static int MAX_FAILED_ATTEMPTS_ADMIN = 3; //3 pour admin
    
    private static int MAX_FAILED_ATTEMPTS_AGENT = 6; // 6 pour agent
    
    
    private final int SITUATION_DESACTIVE = Constante.SITUATION_COMPTE_DESACTIVE;//2
    private final int SITUATION_ACTIVE    = Constante.SITUATION_COMPTE_ACTIVE;//1
    private final int SITUATION_BLOCAGE    = Constante.SITUATION_COMPTE_DEBLOQUE; //0
    
    

    @Override
    public void generateForgotPasswordOtp_withUserId( Long userId ) {

        invalidateOldOtp(userId, OtpType.FORGOT_PASSWORD );

        String otpCode = generateOtpCode();

        UserOtp otp = new UserOtp();

        otp.setUserId(userId);

//        otp.setOtpCode(otpCode);
        otp.setOtpCode(
                passwordEncoder.encode(otpCode)
        );

        otp.setOtpType( OtpType.FORGOT_PASSWORD );

        otp.setStatus( OtpStatus.PENDING );

        otp.setCreatedAt( new Date() );

        Calendar calendar = Calendar.getInstance();

        calendar.add( Calendar.MINUTE, 5 );

        otp.setExpiresAt( calendar.getTime() );

        otp.setAttempts(0);

        otp.setMaxAttempts(3);

//        otp.setIsUsed(0);
        otp.setUpdatedAt(new Date());

        userOtpDao.save(otp);

        System.out.println( "OTP GENERATED : " + otpCode );
    }
    
    

	@Override
	public void generateForgotPasswordOtp(String login, int expiration, int attemps) {

	    // 1. FIND USER
	    Utilisateurs user = userDao.searchLogin(login);

	    if (user == null) {
	        throw new RuntimeException("Utilisateur introuvable");
	    }

	    // 2. invalidate old OTP
	    invalidateOldOtp(Long.valueOf(user.getIdUtilisateurs()), OtpType.FORGOT_PASSWORD );

	    // 3. generate OTP plain
	    String plainOtp = generateOtpCode();

	    // 4. save HASHED OTP
	    UserOtp otp = new UserOtp();

	    otp.setUserId(Long.valueOf(user.getIdUtilisateurs()));

	    otp.setOtpCode( passwordEncoder.encode(plainOtp) );

	    otp.setOtpType(OtpType.FORGOT_PASSWORD);

	    otp.setStatus(OtpStatus.PENDING);

	    otp.setCreatedAt(new Date());

	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.MINUTE, expiration);

	    otp.setExpiresAt(cal.getTime());

	    otp.setAttempts(0);
	    otp.setMaxAttempts(attemps);
//	    otp.setIsUsed(0);
	    otp.setUpdatedAt(new Date());

	    userOtpDao.save(otp);

	    // 5. SEND EMAIL (PLAINT TEXT OTP)
//	    mailService.sendOtpEmail(
//	            user.getMail(),
//	            plainOtp
//	    );

	    System.out.println("OTP SENT TO EMAIL " + plainOtp);
	}
	
	
	
	@Override
	public void generateOtp(String login, int expiration, int attemps, OtpType otpType) {

	    // 1. FIND USER
	    Utilisateurs user = userDao.searchLogin(login);

	    if (user == null) {
	        throw new RuntimeException("Utilisateur introuvable");
	    }

	    // 2. invalidate old OTP
	    invalidateOldOtp(
	    		Long.valueOf(user.getIdUtilisateurs()), 
	    		otpType
	    		);

	    // 3. generate OTP plain
	    String plainOtp = generateOtpCode();

	    // 4. save HASHED OTP
	    UserOtp otp = new UserOtp();

	    otp.setUserId(Long.valueOf(user.getIdUtilisateurs()));

	    otp.setOtpCode( passwordEncoder.encode(plainOtp) );

	    otp.setOtpType(otpType);

	    otp.setStatus(OtpStatus.PENDING);

	    otp.setCreatedAt(new Date());

	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.MINUTE, expiration);

	    otp.setExpiresAt(cal.getTime());

	    otp.setAttempts(0);
	    otp.setMaxAttempts(attemps);
//	    otp.setIsUsed(0);
	    otp.setUpdatedAt(new Date());

	    userOtpDao.save(otp);

	    // 5. SEND EMAIL (PLAINT TEXT OTP)
	    mailService.sendOtpEmail(
	            user.getMail(),
	            plainOtp
	    );

	    System.out.println("OTP SENT TO EMAIL " + plainOtp);
	}
    
    
    @Override
    public boolean validateForgotPasswordOtp_withUserId( Long userId, String otpCode ) {

        UserOtp otp = userOtpDao.findLastPendingOtp(
                        userId,
                        OtpType.FORGOT_PASSWORD
                );

        if (otp == null) {

            System.out.println( "NO ACTIVE OTP" );

            return false;
        }

        // OTP expiré

        if (otp.getExpiresAt().before(new Date())) {

            otp.setStatus( OtpStatus.EXPIRED );

//            otp.setIsUsed(1);
            otp.setUpdatedAt(new Date());

            userOtpDao.update(otp);

            System.out.println( "OTP EXPIRED" );

            return false;
        }

        // Max attempts

        if (otp.getAttempts() >= otp.getMaxAttempts()) {

            otp.setStatus( OtpStatus.FAILED );

//            otp.setIsUsed(1);
            otp.setUpdatedAt(new Date());

            userOtpDao.update(otp);

            System.out.println( "MAX ATTEMPTS" );

            return false;
        }

        // Mauvais OTP
        
        
        boolean valid =
                passwordEncoder.matches(
                        otpCode,
                        otp.getOtpCode()
                );

//        if (!otp.getOtpCode().equals(otpCode)) {
        if (!valid) {

            otp.setAttempts( otp.getAttempts() == null ? 1 : otp.getAttempts() + 1 );
            
            otp.setUpdatedAt(new Date());

            userOtpDao.update(otp);

            System.out.println( "INVALID OTP" );

            return false;
        }

        // SUCCESS

        otp.setStatus( OtpStatus.VALIDATED );

        otp.setValidatedAt( new Date() );

//        otp.setIsUsed(1);
        otp.setUpdatedAt(new Date());

        userOtpDao.update(otp);

        System.out.println( "OTP VALIDATED" );

        return true;
    }
    
    
    
    
    @Override
    public boolean validateForgotPasswordOtp(String loginOrEmail, String otpCode) {

        // 1. FIND USER
        Utilisateurs user = userDao.searchLogin(loginOrEmail);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        // 2. GET LAST ACTIVE OTP
        UserOtp otp = userOtpDao.findLastPendingOtp(
                Long.valueOf(user.getIdUtilisateurs()),
                OtpType.FORGOT_PASSWORD
        );

        if (otp == null) {
            System.out.println("Aucun OTP actif");
            return false;
        }

        // 3. CHECK EXPIRATION
        if (otp.getExpiresAt().before(new Date())) {

            otp.setStatus(OtpStatus.EXPIRED);
//            otp.setIsUsed(1);
            otp.setUpdatedAt(new Date());

            userOtpDao.update(otp);

            System.out.println("OTP expiré");
            return false;
        }

        // 4. CHECK MAX ATTEMPTS
        if (otp.getAttempts() != null
                && otp.getAttempts() >= otp.getMaxAttempts()) {

            otp.setStatus(OtpStatus.FAILED);
//            otp.setIsUsed(1);
            otp.setUpdatedAt(new Date());

            userOtpDao.update(otp);

            System.out.println("Max attempts atteints");
            return false;
        }

        // 5. CHECK OTP (BCrypt)
        boolean valid = passwordEncoder.matches(
                otpCode,
                otp.getOtpCode()
        );

        if (!valid) {

            otp.setAttempts(
                    otp.getAttempts() == null ? 1 : otp.getAttempts() + 1
            );

            otp.setUpdatedAt(new Date());
            
            userOtpDao.update(otp);

            System.out.println("OTP invalide");
            return false;
        }

        // 6. SUCCESS
        otp.setStatus(OtpStatus.VALIDATED);
//        otp.setIsUsed(1);
        otp.setUpdatedAt(new Date());
        otp.setValidatedAt(new Date());

        userOtpDao.update(otp);

        System.out.println("OTP validé avec succès");

        return true;
    }
    
    
    @Override
    public boolean validateOtp(String loginOrEmail, String otpCode, OtpType otpType) {

        // 1. FIND USER
        Utilisateurs user = userDao.searchLogin(loginOrEmail);

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        // 2. GET LAST ACTIVE OTP
        UserOtp otp = userOtpDao.findLastPendingOtp(
                Long.valueOf(user.getIdUtilisateurs()),
                otpType
        );

        if (otp == null) {
            System.out.println("Aucun OTP actif");
            return false;
        }

        // 3. CHECK EXPIRATION
        if (otp.getExpiresAt().before(new Date())) {

            otp.setStatus(OtpStatus.EXPIRED);
//            otp.setIsUsed(1);
            otp.setUpdatedAt(new Date());

            userOtpDao.update(otp);

            System.out.println("OTP expiré");
            return false;
        }

        // 4. CHECK MAX ATTEMPTS
        if (otp.getAttempts() != null
                && otp.getAttempts() >= otp.getMaxAttempts()) {

            otp.setStatus(OtpStatus.FAILED);
//            otp.setIsUsed(1);
            otp.setUpdatedAt(new Date());

            userOtpDao.update(otp);

            System.out.println("Max attempts atteints");
            return false;
        }

        // 5. CHECK OTP (BCrypt)
        boolean valid = passwordEncoder.matches(
                otpCode,
                otp.getOtpCode()
        );

        if (!valid) {

            otp.setAttempts(
                    otp.getAttempts() == null ? 1 : otp.getAttempts() + 1
            );

            otp.setUpdatedAt(new Date());
            
            userOtpDao.update(otp);

            System.out.println("OTP invalide");
            return false;
        }

        // 6. SUCCESS
        otp.setStatus(OtpStatus.VALIDATED);
//        otp.setIsUsed(1);
        otp.setUpdatedAt(new Date());
        otp.setValidatedAt(new Date());

        userOtpDao.update(otp);

        System.out.println("OTP validé avec succès");

        return true;
    }
    
    
    @Override
    public boolean canResetPassword(Long userId, int expiration) {

        UserOtp otp =
            userOtpDao.findLastValidatedOtp(
                    userId,
                    OtpType.FORGOT_PASSWORD
            );

        if (otp == null) {
            return false;
        }
        
        // sécurité : doit être VALIDATED
        if (otp.getStatus() != OtpStatus.VALIDATED) {
            return false;
        }
        
        
        // CHECK EXPIRATION
        Calendar cal = Calendar.getInstance();

        cal.setTime(otp.getValidatedAt());

        cal.add(Calendar.MINUTE, expiration);
      
        boolean valid = new Date().before(cal.getTime());
        
        if (!valid) {

            otp.setStatus( OtpStatus.EXPIRED );

//            otp.setIsUsed(1);
            otp.setUpdatedAt(new Date());

            userOtpDao.update(otp);

            System.out.println( "OTP EXPIRED" );

            return false;
        }
        
        return valid;
    }
    
    
    
    @Override
    public boolean canResetPassword(Long userId, int expiration, OtpType otpType) {

        UserOtp otp =
            userOtpDao.findLastValidatedOtp(
                    userId,
                    otpType
            );

        if (otp == null) {
            return false;
        }
        
        // sécurité : doit être VALIDATED
        if (otp.getStatus() != OtpStatus.VALIDATED) {
            return false;
        }
        
        
        // CHECK EXPIRATION
        Calendar cal = Calendar.getInstance();

        cal.setTime(otp.getValidatedAt());

        cal.add(Calendar.MINUTE, expiration);
      
        boolean valid = new Date().before(cal.getTime());
        
        if (!valid) {

            otp.setStatus( OtpStatus.EXPIRED );

//            otp.setIsUsed(1);
            otp.setUpdatedAt(new Date());

            userOtpDao.update(otp);

            System.out.println( "OTP EXPIRED" );

            return false;
        }
        
        return valid;
    }
    
 
    @Override
    public void markOtpAsUsed(Long userId, OtpType otpType) {

        UserOtp otp = userOtpDao.findLastValidatedOtp(
                userId,
                otpType
        );

        if (otp != null) {

            otp.setStatus(OtpStatus.USED);
            
            otp.setUpdatedAt(new Date());

            otp.setUsedAt(new Date());

            userOtpDao.update(otp);
        }
    }
    
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void unlockAccount(
            Long userId,
            String login,
            OtpType flowType,
            Integer expirationMinutes) {

        // 1. OTP VALIDATED
        UserOtp otp = userOtpDao.findLastValidatedOtp(
                userId,
                flowType);

        if (otp == null) {
            throw new BusinessException(
                    "Aucun OTP validé trouvé");
        }
        
        // sécurité : doit être VALIDATED
        if (otp.getStatus() != OtpStatus.VALIDATED) {
        	throw new BusinessException(
                    "Aucun OTP validé trouvé");
        }

        // 2. Expiration  
        boolean valid = isOtpStillValid(otp, expirationMinutes);

        if (!valid) {
            throw new BusinessException(
                    "Session OTP expirée");
        }

        // 3. Utilisateur
        Utilisateurs user =
                userDao.searchLogin(login);

        if (user == null) {
            throw new BusinessException(
                    "Utilisateur introuvable");
        }

        // 4. Déblocage
        Date now = new Date();
        
        user.setNbrTentative(0);

        user.setSituationBlocage(
                Constante.SITUATION_COMPTE_DEBLOQUE);

        user.setDateDeblocage(now);

        userDao.edit(user);

        // 5. OTP => USED
        otp.setStatus(OtpStatus.USED);
        otp.setUpdatedAt(now);
        otp.setUsedAt(now);

        userOtpDao.update(otp);
    }
    
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void changePassword(
            Long userId,
            String login,
            String newPassword,
            String confirmPassword,
            OtpType flowType,
            Integer expirationMinutes)
            throws Exception {

        // 1. Champs obligatoires
        if (newPassword == null
                || confirmPassword == null
                || newPassword.trim().isEmpty()
                || confirmPassword.trim().isEmpty()) {

            throw new BusinessException(
                    "Tous les champs sont obligatoires");
        }

        // 2. Confirmation
        if (!newPassword.equals(confirmPassword)) {

            throw new BusinessException(
                    "Les mots de passe ne correspondent pas");
        }

        // 3. OTP VALIDATED
        UserOtp otp =
                userOtpDao.findLastValidatedOtp(
                        userId,
                        flowType);

        if (otp == null) {
            throw new BusinessException(
                    "Aucun OTP validé trouvé");
        }

        if (otp.getStatus() != OtpStatus.VALIDATED) {
            throw new BusinessException(
                    "Aucun OTP validé trouvé");
        }

        // 4. Expiration OTP
        if (!isOtpStillValid(otp, expirationMinutes)) {
            throw new BusinessException(
                    "Session OTP expirée");
        }

        // 5. Utilisateur
        Utilisateurs utilisateur =
                userDao.searchLogin(login);

        if (utilisateur == null) {
            throw new BusinessException(
                    "Utilisateur introuvable");
        }

        String profil = utilisateur.getProfil().getCode();

        boolean isAdmin =
                isAdminProfil(profil);

        int maxAttempts =
                isAdmin
                        ? MAX_FAILED_ATTEMPTS_ADMIN
                        : MAX_FAILED_ATTEMPTS_AGENT;
        
        // 6. Compte désactivé
        if (utilisateur.getSituation()
                == SITUATION_DESACTIVE) {

            throw new BusinessException(
                    "Compte désactivé");
        }

        // 7. Compte bloqué
        if (utilisateur.getNbrTentative()
                >= maxAttempts) {

            throw new BusinessException(
                    "Compte bloqué suite aux tentatives");
        }



        // 8. Force mot de passe
        if (isAdmin) {

            if (!PasswordValidator
                    .isStrongPasswordForAdmin(
                            newPassword)) {

                throw new BusinessException(
                		Constante.messageText_admin);
            }

        } else {

            if (!PasswordValidator
                    .isStrongPassword(
                            newPassword)) {

                throw new BusinessException(
                		Constante.messageText_user);
            }
        }
        
        // 9. Vérification mot de passe actuel
        String currentPassword =
                CryptPassword.decrypt(
                        utilisateur.getPassword());

        if (newPassword.equals(currentPassword)) {

            throw new BusinessException(
                    "Le nouveau mot de passe doit être différent du mot de passe actuel");
        }

 
        // 10. Vérification historique
        int historyLimit =
                isAdmin ? 3 : 10;

        List<PasswordHistory> histories =
                isAdmin
                        ? passwordHistoryDAO
                        .findTop3ByUserOrderByChangedAtDesc(
                                utilisateur)
                        : passwordHistoryDAO
                        .findTop10ByUserOrderByChangedAtDesc(
                                utilisateur);

        for (PasswordHistory ph : histories) {

            String oldPassword =
                    CryptPassword.decrypt(
                            ph.getPassword());

            if (newPassword.equals(oldPassword)) {

                throw new BusinessException(
                        "Le nouveau mot de passe ne doit pas être l'un des "
                                + historyLimit
                                + " derniers mots de passe.");
            }
        }

        // 11. Sauvegarde mot de passe
        saveNewPassword(
                utilisateur,
                newPassword);

        // 12. OTP consommé
        Date now = new Date();
        otp.setStatus(OtpStatus.USED);
        otp.setUsedAt(now);
        otp.setUpdatedAt(now);

        userOtpDao.update(otp);
    }
    
    
    
    
    /// a supprimer aprrs, seulement pour ancien projet 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void changeExpiredPassword(
            String login,
            String oldPassword,
            String newPassword,
            String confirmPassword)
            throws Exception {

        // =========================================================
        // 1. CHAMPS OBLIGATOIRES
        // =========================================================

        if (login == null
                || login.trim().isEmpty()
                || oldPassword == null
                || oldPassword.trim().isEmpty()
                || newPassword == null
                || newPassword.trim().isEmpty()
                || confirmPassword == null
                || confirmPassword.trim().isEmpty()) {

            throw new BusinessException(
                    "Tous les champs sont obligatoires");
        }


        // =========================================================
        // 2. RECHERCHE UTILISATEUR
        // =========================================================

        Utilisateurs utilisateur =
                userDao.searchLogin(login.trim());

        if (utilisateur == null) {

            throw new BusinessException(
                    "Identifiant ou mot de passe incorrect");
        }


        // =========================================================
        // 3. PROFIL
        // =========================================================

        String profil =
                utilisateur.getProfil().getCode();

        boolean isAdmin =
                isAdminProfil(profil);


        int maxAttempts =
                isAdmin
                        ? MAX_FAILED_ATTEMPTS_ADMIN
                        : MAX_FAILED_ATTEMPTS_AGENT;


        // =========================================================
        // 4. COMPTE DESACTIVE
        // =========================================================

        if (utilisateur.getSituation()
                == SITUATION_DESACTIVE) {

            throw new BusinessException(
                    "Compte désactivé");
        }


        // =========================================================
        // 5. COMPTE BLOQUE
        // =========================================================

        if (utilisateur.getNbrTentative()
                >= maxAttempts) {

            throw new BusinessException(
                    "Compte bloqué suite aux tentatives");
        }


        // =========================================================
        // 6. VERIFICATION ANCIEN MOT DE PASSE
        // =========================================================

        String currentPassword =
                CryptPassword.decrypt(
                        utilisateur.getPassword());

        if (!oldPassword.equals(currentPassword)) {

            throw new BusinessException(
                    "Ancien mot de passe incorrect");
        }


        // =========================================================
        // 7. CONFIRMATION NOUVEAU MOT DE PASSE
        // =========================================================

        if (!newPassword.equals(confirmPassword)) {

            throw new BusinessException(
                    "Les mots de passe ne correspondent pas");
        }


        // =========================================================
        // 8. NOUVEAU MOT DE PASSE DIFFERENT DE L'ANCIEN
        // =========================================================

        if (newPassword.equals(currentPassword)) {

            throw new BusinessException(
                    "Le nouveau mot de passe doit être différent du mot de passe actuel");
        }


        // =========================================================
        // 9. FORCE MOT DE PASSE
        // =========================================================

        if (isAdmin) {

            if (!PasswordValidator
                    .isStrongPasswordForAdmin(
                            newPassword)) {

                throw new BusinessException(
                        Constante.messageText_admin);
            }

        } else {

            if (!PasswordValidator
                    .isStrongPassword(
                            newPassword)) {

                throw new BusinessException(
                        Constante.messageText_user);
            }
        }


        // =========================================================
        // 10. VERIFICATION HISTORIQUE
        // =========================================================

        int historyLimit =
                isAdmin ? 3 : 10;


        List<PasswordHistory> histories =
                isAdmin
                        ? passwordHistoryDAO
                        .findTop3ByUserOrderByChangedAtDesc(
                                utilisateur)
                        : passwordHistoryDAO
                        .findTop10ByUserOrderByChangedAtDesc(
                                utilisateur);


        for (PasswordHistory ph : histories) {

            String historyPassword =
                    CryptPassword.decrypt(
                            ph.getPassword());

            if (newPassword.equals(historyPassword)) {

                throw new BusinessException(
                        "Le nouveau mot de passe ne doit pas être l'un des "
                                + historyLimit
                                + " derniers mots de passe.");
            }
        }


        // =========================================================
        // 11. SAUVEGARDE NOUVEAU MOT DE PASSE
        // =========================================================

        saveNewPassword(
                utilisateur,
                newPassword);
    }
    
    /**********************************************************************************************************************/

    private String generateOtpCode() {

        SecureRandom random = new SecureRandom();

        int otp = 100000 + random.nextInt(900000);

        return String.valueOf(otp);
    }

    private void invalidateOldOtp( Long userId, OtpType otpType ) {

        UserOtp oldOtp = userOtpDao.findLastPendingOrValidatedOtp(
                        userId,
                        otpType
                );

        if (oldOtp != null) {

            oldOtp.setStatus( OtpStatus.EXPIRED );

//            oldOtp.setIsUsed(1);
            oldOtp.setUpdatedAt(new Date());

            userOtpDao.update(oldOtp);
        }
    }

    
    public boolean isOtpStillValid(UserOtp otp, int expirationMinutes) {

        LocalDateTime now = LocalDateTime.now();

        // Cas 1 : pas encore validé
        if (otp.getValidatedAt() == null) {
            return now.isBefore(new LocalDateTime(otp.getExpiresAt()));
        }

        // Cas 2 : validé → grace period uniquement
        LocalDateTime validatedAt =
                new LocalDateTime(otp.getValidatedAt());

        LocalDateTime limit =
                validatedAt.plusMinutes(expirationMinutes);

        return now.isBefore(limit);
    }

    
    
	private boolean isAdminProfil(String profil) {
		return profil.equalsIgnoreCase("ASI") ||
		       profil.equalsIgnoreCase("BNA") ||
		       profil.equalsIgnoreCase("AS")  ||
		       profil.equalsIgnoreCase("AA");
		}
		
	 
	private void saveNewPassword(Utilisateurs utilisateur, String newPassword) 
		    throws InvalidKeyException, NoSuchPaddingException, 
		           NoSuchAlgorithmException, UnsupportedEncodingException, 
		           BadPaddingException, IllegalBlockSizeException {
		
		Date now = new Date();
		
		// Ajout dans l'historique
		PasswordHistory passwordHistory = new PasswordHistory();
		passwordHistory.setUtilisateurs(utilisateur);
		passwordHistory.setPassword(CryptPassword.encrypt(newPassword));
		passwordHistory.setChangedAt(now);
		
		List<PasswordHistory> allHistories = 
		    passwordHistoryDAO.findUserOrderByChangedAtDesc(utilisateur);
		
		if (allHistories.isEmpty()) {
		    allHistories = new ArrayList<>();
		}
		allHistories.add(passwordHistory);
		utilisateur.setPasswordHistories(allHistories);
		
		// Mise à jour utilisateur
//		utilisateur.setSituation(SITUATION_ACTIVE);
		utilisateur.setNbrTentative(0);
		utilisateur.setSituationBlocage(SITUATION_BLOCAGE);
		utilisateur.setPassword(CryptPassword.encrypt(newPassword));
		utilisateur.setDateLastChangedPwd(now);
		userDao.edit(utilisateur);
		}
		 



}