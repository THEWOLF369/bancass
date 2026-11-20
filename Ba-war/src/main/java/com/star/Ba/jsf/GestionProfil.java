package com.star.Ba.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Societe;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.AssistantVoiyagetServiceInter;




@ManagedBean
@SessionScoped
public class GestionProfil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


@EJB
private IProduitDAO produitDAO;
	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;
	
	
    private boolean modeSTB;
    private boolean modeAdminSociete;
   
	
	
	List<Profil>allChildrensProfiles;
	
	List<Profil>allChildrensProfilesSocietes;
	
	private String [] userSociete={PofileEnum.G.name(),PofileEnum.AA.name(),PofileEnum.AS.name(),PofileEnum.BNAA.name(),PofileEnum.BNA.name()};
	
	@PostConstruct
	void Init(){
		
		if(Arrays.asList(userSociete).contains(utilisateur.getProfil().getCode())){
			getAllProduitsDisponible();
		}else {
			List<Produit> produits=produitDAO.findAll();
			for (Produit produit : produits) {
				allProduitsDisponible.add(produit.getCodeProduit());
			}
			
		}
		
		getAllChildrensProfiles();
		
		
//		modeSTB = utilisateur.getAgence()
//                .getSociete()
//                .getCodSociet()
//                .equals(reportController.getSTBMat());
		
		modeSTB = false;
		
		modeAdminSociete = utilisateur.getProfil()
				.getCode()
				.equalsIgnoreCase(PofileEnum.AS.name());
	}
	


	
	@EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;
	

	
	public void startAutorisationProduit(){
		getAllProduitsDisponible();
	}
	
	private String souscriptionTDS="none";
	private String souscriptionAssistanceS="none";
	private String souscriptionIncendieS="none";
	private String souscriptionAvenirJeunesseS="none";
	private String souscriptionAssistanceTlsS="none";
	private String souscription7ayyaS="none";
	
	private String encaissementTermeS="none";
	
	private String bordereauS="none";
	private String interfaceAdministrateurS="none";
	
	
	private String uploadFtpPrelevementS="none";
	
	
	//attributs pour CSS
	private String ajoutAgenceS="none";
	private String ajoutSocieteS="none";
	private String ajoutConventionS="none";
	private String ajoutContratS="none";
	private String ajoutUserS="none";
	private String ajoutTransferS="none";
	private String souscriptionMrhS="none";
	
	//attributs pour rendered
	private boolean souscriptionTD;
	private boolean souscriptionAssistance;
	private boolean souscriptionIncendie;
	private boolean souscriptionAvenirJeunesse;
	private boolean souscriptionMrh;
	private boolean souscriptionAssistanceTls;
	private boolean souscription7ayya;
	
	private boolean encaissementTerme;
	
	private boolean bordereau;
	private boolean interfaceAdministrateur;
	private boolean annulationContrat;
	private String annulationContratS;

	private boolean ajoutAgence;
	private boolean ajoutSociete;
	private boolean ajoutConvention;
	private boolean ajoutContrat;
	private boolean ajoutUser;
	private boolean ajoutTransfer;
	
	
	private boolean uploadFtpPrelevement;
	
	
	
	//liste des profils
	private String codeSimpleUser=PofileEnum.G.name();
	private String codeSimpleUserBNA=PofileEnum.BNAA.name();
	private String codeSimpleUserBNAA=PofileEnum.BNA.name();
	
	private String codeSimpleUserDBNA=PofileEnum.DBNA.name();
	private String codeSimpleUserCBNA=PofileEnum.CBNA.name();

	private String codeAgentSociete=PofileEnum.AA.name();
	private String codeAdministrateurSociete=PofileEnum.AS.name();
	private String codeAdministrateurStar=PofileEnum.AST.name();//avenir jeunesse
	private String codeAdministrateurStarInformatique=PofileEnum.ASI.name();
	private String codeAdministrateurIncendie=PofileEnum.IST.name();
	private String codeAdministrateurAssistanceVoyage=PofileEnum.VST.name();
	private String codeAdministrateurTd=PofileEnum.TDST.name();
	private String codeAdministrateurMRH=PofileEnum.MHST.name();
	private String codeAdministrateurTLS=PofileEnum.VTST.name();
	private String codeAdministrateurHayya=PofileEnum.HST.name();
	
	private String codeAdministrateurHayyaQnb=PofileEnum.HQST.name();
	
	private String codeAdministrateurAvj=PofileEnum.AST.name();
	
	private String codeMetierSTAR=PofileEnum.MSTA.name();
	
	
	private String alyssaAccess="none";
	private String starAccess="none";
	private String starAlyssaAccess="none";
	
//	private boolean condition = true;
	
	
	@ManagedProperty("#{reportController}")
	private ReportController reportController;

	
	
	public Convention getConventionBySociete(){
		
		return serviceAssistantVoiyagetService.getConventionBySociete(utilisateur.getAgence().getSociete());
		
	}
	
	
	public boolean  isCondition() {
		
		if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000"))
			return true;
			else
				return false;
	
	}
	
	
   List<Prodconv> getAllProduitAutoriseToUser(){
	   Convention convention=getConventionBySociete();
	   return convention.getProdconvs();
   }
   
   List<String> allProduitsDisponible=new ArrayList<String>();
   
   
   
   
   
   
  public List<String> getAllProduitsDisponible() {
	      allProduitsDisponible=getAllCodeProduitAutoriseToUser();
	  
	return allProduitsDisponible;
}


List<String> getAllCodeProduitAutoriseToUser(){
	  
	  List<String> allCode=new ArrayList<String>();
	  
	  List<Prodconv> proList=getAllProduitAutoriseToUser();
	  System.out.println("agence conncte="+utilisateur.getAgence().getLibelle());
	  for (Prodconv prodconv : proList) {
		  System.out.println(" produit authirise="+prodconv.getProduit().getCodeProduit());
		  allCode.add(prodconv.getProduit().getCodeProduit());
	}
	  
	   return allCode;
   }
	
	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	
	
	
	
	
	public String getSouscriptionMrhS() {
		souscriptionMrhS=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.MRH.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.MRH.getCode()))?"block":"none");
		return souscriptionMrhS;
	}


	public boolean isSouscriptionMrh() {
		souscriptionMrh=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.MRH.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.MRH.getCode()))?true:false);
		return souscriptionMrh;
	}


	public boolean getAjoutTransfer() {
		ajoutTransfer=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurTd)
				||utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurAssistanceVoyage)
				||utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurTLS)?true:false);
		return ajoutTransfer;
	}


	


	public boolean getSouscriptionTD() {
		souscriptionTD=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.TD.getCode()))  ||
				
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurTd) ||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.TD.getCode()))?true:false);
		return souscriptionTD;
	}
	
	public boolean getSouscriptionAssistance() {
		souscriptionAssistance=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.ASSISTANCEVOIYAGE.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.ASSISTANCEVOIYAGE.getCode()))?true:false);

		return souscriptionAssistance;
	}
	
	
	
	public boolean getSouscriptionAssistanceTls() {
		souscriptionAssistanceTls=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.TLSAssistV.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.TLSAssistV.getCode()))?true:false);

		return souscriptionAssistanceTls;
	}


	public boolean getSouscriptionIncendie() {
		souscriptionIncendie=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.INCENDIE.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.INCENDIE.getCode()))?true:false);
		return souscriptionIncendie;
	}
	
	public boolean getSouscription7ayya() {
		souscription7ayya=(
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.H7AYYA.getCode()))||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.H7ayyaQNB.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.H7AYYA.getCode()))||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.H7ayyaQNB.getCode()))
				?true:false
						);
		return souscription7ayya;
	}
	
	
	public boolean getEncaissementTerme() {
		encaissementTerme=(
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&& (
						allProduitsDisponible.contains(ENUMCODEPRODUIT.H7AYYA.getCode())
						||
						allProduitsDisponible.contains(ENUMCODEPRODUIT.H7ayyaQNB.getCode())
						||
						allProduitsDisponible.contains(ENUMCODEPRODUIT.AVENIRJENESSE.getCode())
						)
				)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&(
						allProduitsDisponible.contains(ENUMCODEPRODUIT.H7AYYA.getCode())
						||
						allProduitsDisponible.contains(ENUMCODEPRODUIT.H7ayyaQNB.getCode())
						||
						allProduitsDisponible.contains(ENUMCODEPRODUIT.AVENIRJENESSE.getCode())
						)
				)?true:false);
		return encaissementTerme;
	}
	
	
	public boolean getSouscriptionAvenirJeunesse() {
		souscriptionAvenirJeunesse=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.AVENIRJENESSE.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.AVENIRJENESSE.getCode()))?true:false);

		return souscriptionAvenirJeunesse;
	}
	public boolean getBordereau() {
		bordereau=(
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurSociete)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurAssistanceVoyage)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurIncendie) ||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurHayya) ||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurHayyaQnb)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurAvj)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeMetierSTAR)
				?true:false
						);
		return bordereau;
	}
	public boolean getInterfaceAdministrateur() {
		interfaceAdministrateur=(!utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)
				
				?true:false);
		return interfaceAdministrateur;
	}
	
	
	
	public boolean getUploadFtpPrelevement() {
		uploadFtpPrelevement=(
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurSociete)&&
						 (  /*utilisateur.getAgence().getSociete().getCodSociet().equalsIgnoreCase("002707PPM000") ||*/
								 utilisateur.getAgence().getSociete().getCodSociet().equalsIgnoreCase("022671FPM000")	 )
						)||
				
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)
				?true:false
						);
		return uploadFtpPrelevement;
	}
	
	
	public boolean getAjoutAgence() {
		ajoutAgence=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurSociete)?true:false);
		return ajoutAgence;
	}
	
	public boolean getAjoutSociete() {
		ajoutSociete=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)?true:false);
		return ajoutSociete;
	}
	
	public boolean getAjoutConvention() {
		
		 ajoutConvention=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)?true:false);
		return ajoutConvention;
	}
	public boolean getAjoutContrat() {
		ajoutContrat=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)?true:false);
		return ajoutContrat;
	}
	public boolean getAjoutUser() {
		
		 ajoutUser=(!utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)?true:false);
		return ajoutUser;
	}
	
	public void accesUserDetils(){
		System.out.println("les autorité pour le user name="+utilisateur.getNom());
		System.out.println("souscriptionTD="+souscriptionTD);
		System.out.println("souscriptionAssistance="+souscriptionAssistance);
		System.out.println("souscriptionAvenirJeunesse="+souscriptionAvenirJeunesse);
		System.out.println("souscriptionIncendie="+souscriptionIncendie);
		
		System.out.println("souscription7ayya="+souscription7ayya);
		
		
		System.out.println("interfaceAdministrateur="+interfaceAdministrateur);
		System.out.println("bordereau="+bordereau);
		
		System.out.println("ajoutAgence="+ajoutAgence);
		System.out.println("ajoutContrat="+ajoutContrat);
		System.out.println("ajoutConvention="+ajoutConvention);
		
		System.out.println("ajoutUser="+ajoutUser);

		System.out.println("ajoutSociete="+ajoutSociete);

		
		
		
		
	}
	public List<Profil> getAllChildrensProfiles() {
		if(allChildrensProfiles==null){
			if(utilisateur.getProfil().getCode().equalsIgnoreCase(PofileEnum.AA.name())){
				allChildrensProfiles=serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.AA.getChildrensAA());
			}else if(utilisateur.getProfil().getCode().equalsIgnoreCase(PofileEnum.AS.name())){
				
				
//				allChildrensProfiles=serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.AS.getChildrensAS());
				
				allChildrensProfiles=serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.AS.getChildrensAS_V2());
				
				}
			
			      else if(utilisateur.getProfil().getCode().equalsIgnoreCase(PofileEnum.BNA.name())){
				allChildrensProfiles=serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.BNA.getChildrensBNAA());
			
			      }
			            else if(utilisateur.getProfil().getCode().equalsIgnoreCase(PofileEnum.ASI.name())){
					allChildrensProfiles=	serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.ASI.getChildrensASI());
					
			          	}else   {
			          		allChildrensProfiles=	serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.AST.getChildrenMetie());
					
				}
		}
			
		return allChildrensProfiles;
	}
	public void setAllChildrensProfiles(List<Profil> allChildrensProfiles) {
		this.allChildrensProfiles = allChildrensProfiles;
	}
	
	
	public List<Profil> getAllChildrensProfilesSocietes() {
		if(allChildrensProfilesSocietes==null){
 
			allChildrensProfilesSocietes=	serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.AST.getChildrenMetie());
 
		}
			
		return allChildrensProfilesSocietes;
		
	}
	
	public void setAllChildrensProfilesSocietes(List<Profil> allChildrensProfilesSocietes) {
		this.allChildrensProfilesSocietes = allChildrensProfilesSocietes;
	}
	
	
	public  Profil  getProfileSociete() {
		
		return serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.AST.getChildrenMetie()).get(0);
		
	}
	
	//-------------------------------------Gestion User----------------------------------------------------------------------
	List<Utilisateurs> allUser=new ArrayList<Utilisateurs>();
	
	
	
	public List<Utilisateurs> getAllUser() {
		
			allUser=getAllUsersChildren();	
		return allUser;
	}


	public List<Utilisateurs> getAllUserSocietes() {
		
		allUser=serviceAssistantVoiyagetService.getAllUsersChildren(serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.ASI.getChildrenMetie()),null,null);	
	return allUser;
}	




	List<Utilisateurs> getAllUsersChildren(){
		if(utilisateur.getProfil().getCode().equalsIgnoreCase(PofileEnum.AA.name())){
			return serviceAssistantVoiyagetService.getAllUsersChildren(serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.AA.getChildrensAA()), utilisateur.getAgence(),null);
		}else if (utilisateur.getProfil().getCode().equalsIgnoreCase(PofileEnum.AS.name())) {
			
//			return	serviceAssistantVoiyagetService.getAllUsersChildren(serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.AS.getChildrensAS()),null, utilisateur.getAgence().getSociete());
			return	serviceAssistantVoiyagetService.getAllUsersChildren(serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.AS.getChildrensAS_V2()),null, utilisateur.getAgence().getSociete());
			} else if (utilisateur.getProfil().getCode().equalsIgnoreCase(PofileEnum.ASI.name())) {
				return	serviceAssistantVoiyagetService.getAllUsersChildren(serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.ASI.getChildrensASI()),null,null);
		         	} else   {
					return	serviceAssistantVoiyagetService.getAllUsersChildren(serviceAssistantVoiyagetService.getAllChildrensProfiles(PofileEnum.ASI.getChildrenMetie()),null,null);
				}

			}
    private Utilisateurs selected;


	public Utilisateurs getSelected() {
		return selected;
	}




	public void setSelectedUser(Utilisateurs selected) {
		this.selected = selected;
	}

		
		
	
	
	List<Agence> allAgence;


	public List<Agence> getAllAgence() {
		
			allAgence=serviceAssistantVoiyagetService.getAllAgencesBySociete(utilisateur.getAgence().getSociete());
		return allAgence;
	}


	
	 private Agence principaleAgence;


	public Agence getPrincipaleAgence(Societe societe) {
		return serviceAssistantVoiyagetService.getPrincipaleAgence(societe);
	}


	public String getSouscriptionTDS() {
               souscriptionTDS=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.TD.getCode()))  ||
				
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
			//	utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStar) ||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.TD.getCode()))?"block":"none");
		       
		return souscriptionTDS;
	}


	

	public String getSouscriptionAssistanceS() {
		souscriptionAssistanceS=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.ASSISTANCEVOIYAGE.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.ASSISTANCEVOIYAGE.getCode()))?"block":"none");

		return souscriptionAssistanceS;
	}


	


	public String getSouscriptionAssistanceTlsS() {
		souscriptionAssistanceTlsS=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.TLSAssistV.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.TLSAssistV.getCode()))?"block":"none");

		return souscriptionAssistanceTlsS;
	}


	public String getSouscriptionIncendieS() {
		souscriptionIncendieS=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.INCENDIE.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.INCENDIE.getCode()))?"block":"none");
	
		return souscriptionIncendieS;
	}
	
	
	
	public String getSouscription7ayyaS() {
		souscription7ayyaS=(
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.H7AYYA.getCode()))||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.H7ayyaQNB.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.H7AYYA.getCode()))||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.H7ayyaQNB.getCode()))
				?"block":"none"
					);
	
		return souscription7ayyaS;
	}
	
	
	public String getEncaissementTermeS() {
		encaissementTermeS=(
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)
						&&(
								allProduitsDisponible.contains(ENUMCODEPRODUIT.H7AYYA.getCode())
								|| 
								allProduitsDisponible.contains(ENUMCODEPRODUIT.H7ayyaQNB.getCode())
								||
								allProduitsDisponible.contains(ENUMCODEPRODUIT.AVENIRJENESSE.getCode())
						  )
				)||
				
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(
						utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)
						&&(
								allProduitsDisponible.contains(ENUMCODEPRODUIT.H7AYYA.getCode())
								||
								allProduitsDisponible.contains(ENUMCODEPRODUIT.H7ayyaQNB.getCode())
								||
								allProduitsDisponible.contains(ENUMCODEPRODUIT.AVENIRJENESSE.getCode())
						  )
				)?"block":"none");
	
		return encaissementTermeS;
	}





	public String getBordereauS() {
		bordereauS=(
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurSociete)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurAssistanceVoyage)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurIncendie) ||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurHayya)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurHayyaQnb)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurAvj)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeMetierSTAR)
				?"block":"none"
					);
		return bordereauS;
	}




	public String getInterfaceAdministrateurS() {
		System.out.println("utilisateur.getProfil().getCode(): "+utilisateur.getProfil().getCode());
		System.out.println("codeSimpleUser: "+codeSimpleUser);
		
		if(!utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser) && 
				
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStar) && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurIncendie) && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurAssistanceVoyage) && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurTd) && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurMRH) && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurTLS) && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurHayya) && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurHayyaQnb) && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurAvj) && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeMetierSTAR) && 
				
				
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUserBNA)  && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUserBNAA) && 
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUserDBNA) &&
				!utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUserCBNA)
				){
			interfaceAdministrateurS="block";
		}else{
			interfaceAdministrateurS="none";
		}
//		interfaceAdministrateurS=(!utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)?"block":"none");
		
		return interfaceAdministrateurS;
	}


	
	public String getUploadFtpPrelevementS() {
		uploadFtpPrelevementS=(
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurSociete)&&
						 (  /*utilisateur.getAgence().getSociete().getCodSociet().equalsIgnoreCase("002707PPM000") ||*/
								 utilisateur.getAgence().getSociete().getCodSociet().equalsIgnoreCase("022671FPM000")	 )
						)||
				
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)
				
				?"block":"none"
					);
	
		return uploadFtpPrelevementS;
	}


	public String getAjoutAgenceS() {
		ajoutAgenceS=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurSociete)?"block":"none");
		return ajoutAgenceS;
	}



	public String getAjoutSocieteS() {
		ajoutSocieteS=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)?"block":"none");
		return ajoutSocieteS;
	}




	public String getAjoutConventionS() {
		ajoutConventionS=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)?"block":"none");
		return ajoutConventionS;
	}




	public String getAjoutContratS() {
		ajoutContratS=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)?"block":"none");
		return ajoutContratS;
	}





	public String getAjoutUserS() {
		ajoutUserS=(!utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)?"block":"none");
		return ajoutUserS;
	}

   


	public String getAnnulationContratS() {
		
		 annulationContratS=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)||utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete))?"block":"none";
	return annulationContratS;
	}


	

	public boolean getAnnulationContrat() {
		 annulationContrat=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)||utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete))?true:false;
		 return annulationContrat;
	}


	


	public String getAjoutTransferS() {
		ajoutTransferS=(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurTd)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurAssistanceVoyage)||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurTLS)?"block":"none");
		return ajoutTransferS;
	}


	


	public String getSouscriptionAvenirJeunesseS() {
		souscriptionAvenirJeunesseS=((utilisateur.getProfil().getCode().equalsIgnoreCase(codeSimpleUser)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.AVENIRJENESSE.getCode()))||
				utilisateur.getProfil().getCode().equalsIgnoreCase(codeAdministrateurStarInformatique)||
				(utilisateur.getProfil().getCode().equalsIgnoreCase(codeAgentSociete)&&allProduitsDisponible.contains(ENUMCODEPRODUIT.AVENIRJENESSE.getCode()))?"block":"none");
		return souscriptionAvenirJeunesseS;
	}


	/*** partie alyssa  *************/
	
	public String getAlyssaAccess_0() {  ///vie seulement
		
	 	 alyssaAccess=(
	 			utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000") 
			)?"block":"none";
 
	
	return alyssaAccess;
	}
	
	
	public String getStarAccess_0() {  // pas de vie
		
		starAccess=(
			utilisateur.getAgence().getSociete().getCodSociet().equals("B142431996")
			)?"block":"none";

	
	return starAccess;
	}
	
	
	public String getStarAlyssaAccess_0() {  // vie + non vie
		
		starAlyssaAccess=(
			!utilisateur.getAgence().getSociete().getCodSociet().equals("B142431996")
			&&!utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000")
			)?"block":"none";

	
	return starAlyssaAccess;
	}
	/*********************************/
	
	
	/*** partie ancienne *************/
	
	public String getAlyssaAccess() {  ///vie seulement
		
		
		if(nouvelleGestionActive()) {
	        return getAlyssaAccess_0();
	    }
		
	 	 alyssaAccess="none";

	
	return alyssaAccess;
	}
	
	
	public String getStarAccess() {  // pour tous
		
		 if(nouvelleGestionActive()) {
		        return getStarAccess_0();
		    }
		
		starAccess="block";

	
	return starAccess;
	}
	
	public String getStarAlyssaAccess() {  // vie + non vie
		
		 if(nouvelleGestionActive()) {
		        return getStarAlyssaAccess_0();
		    }
		
		starAlyssaAccess="none";

	
	return starAlyssaAccess;
	}



	
	
	/*********************************/
	
	
	
	
	
	public ReportController getReportController() {
		return reportController;
	}


	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}
	
	
	private boolean nouvelleGestionActive() {
	    return reportController.Activer_logo_alyssa_dans_menus();
	}


	public boolean isModeSTB() {
		return modeSTB;
	}


	public void setModeSTB(boolean modeSTB) {
		this.modeSTB = modeSTB;
	}


	public boolean isModeAdminSociete() {
		return modeAdminSociete;
	}


	public void setModeAdminSociete(boolean modeAdminSociete) {
		this.modeAdminSociete = modeAdminSociete;
	}
	
	
	
	
	
	
	
}
	
	
	
	
	


