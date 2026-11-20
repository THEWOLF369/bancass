package com.star.Ba.commun;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constante {
	
	public static final String PDF=getPropriety("pdf.path");
	
	public static final String URL_ENCAISS_STB=getPropriety("ENCAISS_STB");
	public static final String URL_CLIENT_STB=getPropriety("CLIENT_STB");
	public static final String URL_REF_STB=getPropriety("REF_STB");
	public static final String URL_WS_BNA=getPropriety("URL_WS_BNA");
	public static final String URL_WS_UpdateDateOfBirth_STB=getPropriety("UPATE_DATE_OF_BIRTH_STB");
	public static final String URL_WS_CalculerPrimeDevisVie_STB=getPropriety("CALCULER_PRIME_DEVIS_VIE_STB");
	public static final String URL_WS_GetListeQuittancesImpayeesAdv_STB=getPropriety("Quittances_Impayees_STB");
	
	public static final String URL_USER_STB=getPropriety("URL_USER_STB");
	
	public static final String URL_TOKEN_STB=getPropriety("URL_TOKEN_STB");
	public static final String test=getPropriety("test");
	public static final String LOCAL="en-US";
	public static final String SERVEUR ="192.168.210.13";
	
	public static final String PRODUIT_HAYYA=getPropriety("PRODUIT_HAYYA");
	public static final String PRODUIT_AVJ=getPropriety("PRODUIT_AVJ");
	public static final String PRODUIT_TD=getPropriety("PRODUIT_TD");
	public static final String PRODUIT_ASSV=getPropriety("PRODUIT_ASSV");
	public static final String PRODUIT_INC=getPropriety("PRODUIT_INC");
	
	public static final String PRODUIT_HAYYA_VL=getPropriety("PRODUIT_HAYYA_VL");
	public static final String PRODUIT_AVJ_VL=getPropriety("PRODUIT_AVJ_VL");
	public static final String PRODUIT_HAYYA_ET=getPropriety("PRODUIT_HAYYA_ET");
	public static final String PRODUIT_AVJ_ET=getPropriety("PRODUIT_AVJ_ET");
	
	public static final String username=getPropriety("username");
	public static final String password=getPropriety("password");
	public static final String grant_type=getPropriety("grant_type");
	public static final String scope=getPropriety("scope");
	public static final String client_id=getPropriety("client_id");
	public static final String response_type=getPropriety("response_type");
	
	public static final String HEADER_ENCAISS=getPropriety("HEADER_ENCAISS");
	
	public static final String HEADER_COLLAB=getPropriety("HEADER_COLLAB");
	
	
	// input tarif 7ayya
	//STB
	public static final String CodeAgence_STB=getPropriety("CODE_AGENCE_STB");
	public static final String CodeUtilisateur_STB=getPropriety("CODE_UTILISATEUR_STB");
	public static final String CodeBranche_STB=getPropriety("CODE_BRANCHE_STB");
	public static final String CodeClient_STB=getPropriety("CODE_CLIENT_STB");
	public static final String CodeDuree_STB=getPropriety("CODE_DUREE_STB");
	//QNB
	public static final String CodeAgence_QNB=getPropriety("CODE_AGENCE_QNB");
	public static final String CodeUtilisateur_QNB=getPropriety("CODE_UTILISATEUR_QNB");
	public static final String CodeBranche_QNB=getPropriety("CODE_BRANCHE_QNB");
	public static final String CodeClient_QNB=getPropriety("CODE_CLIENT_QNB");
	public static final String CodeDuree_QNB=getPropriety("CODE_DUREE_QNB");
	
	
	//// FTP
	//STB
	public static final String ftpServer_stb=getPropriety("ftpServer_stb");
	public static final String ftpPort_stb=getPropriety("ftpPort_stb");
	public static final String ftpUser_stb=getPropriety("ftpUser_stb");
	public static final String ftpPass_stb=getPropriety("ftpPass_stb");
	public static final String ftpFolder_stb=getPropriety("ftpFolder_stb");
    // QNB
	public static final String ftpServer_qnb=getPropriety("ftpServer_qnb");
	public static final String ftpPort_qnb=getPropriety("ftpPort_qnb");
	public static final String ftpUser_qnb=getPropriety("ftpUser_qnb");
	public static final String ftpPass_qnb=getPropriety("ftpPass_qnb");
	public static final String ftpFolder_qnb=getPropriety("ftpFolder_qnb");
	
	
	/// API bassem pour prelev STB
	public static final String URL_API_PELEV_STB=getPropriety("API_PRELEVEMENT_STB");
	
	
	public static final String RIB_STAR_PRELEV=getPropriety("RIB_STAR_PRELEV");
	
	public static final String Bassem_Api_Key=getPropriety("Bassem_Api_Key");
	
	
	// service mailg
	public static final String mail_smtp_host=getPropriety("mail_smtp_host");
	public static final String mail_smtp_port=getPropriety("mail_smtp_port");
	public static final String mail_smtp_auth=getPropriety("mail_smtp_auth");
	public static final String mail_smtp_starttls_enable=getPropriety("mail_smtp_starttls_enable");
	public static final String mail_smtp_starttls_required=getPropriety("mail_smtp_starttls_required");
	public static final String mail_smtp_ssl_protocols=getPropriety("mail_smtp_ssl_protocols");
	public static final String mail_mime_charset=getPropriety("mail_mime_charset");
	public static final String mail_username=getPropriety("mail_username");
	public static final String mail_password=getPropriety("mail_password");
	
	
	
	
	/******* des constante mais dont la valeur sont fixe ici ***************************/
	public static final int SITUATION_COMPTE_DESACTIVE = 2;   // compte desactivé
	public static final int SITUATION_COMPTE_ACTIVE    = 1;   // compte activé
	public static final int SITUATION_COMPTE_BLOQUE    = 1;    // compte bloqué
	public static final int SITUATION_COMPTE_DEBLOQUE  = 0;   // compte debloqué
	
    public static String messageText_user = "Mot de passe non suffisamment sécurisé, veuillez le changer."
    		+ "<br/>Le mot de passe doit contenir: "
    		+ "<br/>1-au moins une lettre minuscule "
    		+ "<br/>2-au moins une lettre majuscule "
    		+ "<br/>3-au moins un chiffre "
    		+ "<br/>4-au moins un caractère spécial "
    		+ "<br/>La langueur du mot de passe doit être au minimum 12";
    
    public static String messageText_admin = "Mot de passe non suffisamment sécurisé, veuillez le changer."
    		+ "<br/>Le mot de passe doit contenir: "
    		+ "<br/>1-au moins 5 lettres minuscules "
    		+ "<br/>2-au moins 5 lettres majuscules "
    		+ "<br/>3-au moins 5 chiffres "
    		+ "<br/>4-au moins 5 caractères spéciaux "
    		+ "<br/>La langueur du mot de passe doit être au minimum 20";
	
	
	
	
public static String getPropriety(String key){
		
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 

			input = Constante.class.getClassLoader().getResourceAsStream("application.properties");
	 
			// load a properties file
			prop.load(input);
	 
			return prop.getProperty(key);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
		return null;
	}
}

