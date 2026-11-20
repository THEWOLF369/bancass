package com.star.Ba.jsf;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAppConfigDAO;
import com.star.Ba.parametres.AppConfigService;



@ManagedBean
@SessionScoped
public class ReportController implements Serializable {
	
	
//	@EJB
//	private IAppConfigDAO iAppConfigDAO;
	
    @EJB
    private AppConfigService configService; 

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getRealPath(){
		ServletContext cxt=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath= cxt.getRealPath("/");
//		return realPath+"report/";
//		return Constante.PDF;// realPath+"report/"  pour la prod et ma machine;
		
		
//		 String mode = iAppConfigDAO.getConfigValue("MODE_REPORT_PATH");
		String mode = configService.get("MODE_REPORT_PATH");

		    if("LOCAL".equalsIgnoreCase(mode)) {
       
		        return realPath + "report/";  //LOCAL

		    } else {

		        return Constante.PDF;  //SERVER

		    }
		
	}
	public String getRealPathImage(){
		ServletContext cxt=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath= cxt.getRealPath("/");
		return realPath+"images/";
	}
	
	public String getLogoJasper(){

//		String star= "star.png"; // ancienne
//		String alyssa= "logoAlyssaJASPER.png"; // nouvelle
//		return star;  // ici changer
		return configService.get("LOGO_JASPER");
//		return iAppConfigDAO.getConfigValue("LOGO_JASPER");
	}
	
	
	public boolean Activer_logo_alyssa_dans_menus(){  //concerne logi alyssa dans les menu apres connexion

//		 String value = iAppConfigDAO.getConfigValue("ACTIVER_LOGO_ALYSSA");
		String value = configService.get("ACTIVER_LOGO_ALYSSA");

		    if(value == null)
		        return false;

		    return "TRUE".equalsIgnoreCase(value);
	}
	
	
	
	public String getSTBMat(){ // ici pour activer mode de paiement prelebement bancaire
//		return "002707PPM000";
		return configService.get("STB_MAT_PELEV");		
//		return iAppConfigDAO.getConfigValue("STB_MAT_PELEV");

	}
	
	public String getQNBMat(){
		return "022671FPM000";
	}
	
	
	public String getSTBMat_Pour_Encaissement_Versement_Libre_et_Terme(){
//		return "ici_pour_preprod_seulement";
//		return "002707PPM000";
		return configService.get("STB_MAT_POUR_ENC");		
//		return iAppConfigDAO.getConfigValue("STB_MAT_POUR_ENC");
	}
	
	
	public boolean est_A_Encaisser(){  //concerne ws encaiseemnt STB
//		return true;  // pour la prod
//		return false; // pour test seulement
		 String value = configService.get("ACTIVER_WS_STB_ENC");
//		 String value = iAppConfigDAO.getConfigValue("ACTIVER_WS_STB_ENC");

		    if(value == null)
		        return false;

		    return "TRUE".equalsIgnoreCase(value);
	}
	
	
	public boolean activer_api_Bassem(){
//		return false;  
//		return true;  
		 String value = configService.get("ACTIVER_API_BASSEM");
//		 String value = iAppConfigDAO.getConfigValue("ACTIVER_API_BASSEM");

		    if(value == null)
		        return false;

		    return "TRUE".equalsIgnoreCase(value);
	}
	
	
	public boolean WITH_CERTIF(){
		
		String value = configService.get("CERTIF");
//		String value = iAppConfigDAO.getConfigValue("CERTIF");

	    if(value == null)
	        return false;

	    return "TRUE".equalsIgnoreCase(value);
		
	}
	
	
	public boolean WITH_STB_CERTIF(){
		
		String value = configService.get("STB_CERTIF");
//		String value = iAppConfigDAO.getConfigValue("CERTIF");

	    if(value == null)
	        return false;

	    return "TRUE".equalsIgnoreCase(value);
		
	}
	
	
	public boolean WITH_BASSEM_CERTIF(){
		
		String value = configService.get("BASSEM_CERTIF");
//		String value = iAppConfigDAO.getConfigValue("BASSEM_CERTIF");

	    if(value == null)
	        return false;

	    return "TRUE".equalsIgnoreCase(value);
		
	}
	
	
	
	public boolean WITH_STB_PROXY(){
		
		String value = configService.get("STB_PROXY");
//		String value = iAppConfigDAO.getConfigValue("CERTIF");

	    if(value == null)
	        return false;

	    return "TRUE".equalsIgnoreCase(value);
//	    return true;///pour test 12/08/2026 puis supprimer
		
	}
	
	
	public boolean WITH_EDI_PROXY(){
		
		String value = configService.get("EDI_PROXY");
//		String value = iAppConfigDAO.getConfigValue("CERTIF");

	    if(value == null)
	        return false;

	    return "TRUE".equalsIgnoreCase(value);
		
	}
	
	
	/***** partie OTP   ****************************/
	public String OTP_EXPIRATION_MINUTES(){ // ici pour duree expiration de l'otp créé 1 ere foix

		return configService.get("OTP_EXPIRATION_MINUTES");		


	}
	
	public String OTP_VALIDATED_EXPIRATION_MINUTES(){ // ici pour duree expiration de l'otp validé

		return configService.get("OTP_VALIDATED_EXPIRATION_MINUTES");		


	}
	
	public String OTP_MAX_ATTEMPTS(){ // ici pour max attempt de l'otp 

		return configService.get("OTP_MAX_ATTEMPTS");		


	}
	
	
	/***** partie duréé contrat 7ayya + avj   ****************************/
	public Integer MAX_DUREE_CONTRAT(){ // ici artie duréé contrat 7ayya + avj qui est actuellement 40 annnée 

		return Integer.valueOf(configService.get("MAX_DUREE_CONTRAT"));		


	}
}
