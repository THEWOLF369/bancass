package com.star.Ba.jsf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.AjaxBehaviorEvent;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IAgenceDAO;
import com.star.Ba.dao.IChartDAO;
import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IOperationTransactionDAO;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.IProdconvDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IProfilDAO;
import com.star.Ba.dao.IPropositionDAO;
import com.star.Ba.dao.IQuittanceDAO;
import com.star.Ba.dao.IQuittanceDetailsDAO;
import com.star.Ba.dao.IQuittanceTermeDAO;
import com.star.Ba.dao.IQuittanceVersementLibreDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.DetailsGarantieDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.dto.QuittanceImapyeeDTO;
import com.star.Ba.dto.QuittanceVLDTO;
import com.star.Ba.dto.QuittancesClient;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.jsf.util.TokenSTB;
import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agence;
import com.star.Ba.model.BenefHayyaDeces;
import com.star.Ba.model.BenefHayyaVie;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.GarantieAvj;
import com.star.Ba.model.GarantieMrh;
import com.star.Ba.model.GarantieVie;
import com.star.Ba.model.OperationTransaction;
import com.star.Ba.model.Police;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.QuittanceDetails;
import com.star.Ba.model.QuittanceTerme;
import com.star.Ba.model.QuittanceVersementLibre;
import com.star.Ba.model.TarifMrh;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.mrh.pack.OpGar;
import com.star.Ba.service.AssistantVoiyagetServiceInter;
import com.star.Ba.service.HayyaServiceInter;
import com.star.Ba.service.IParametresMrhService;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.representation.Form;

/**
 * @author 
 *
 */
@ManagedBean(name = "versementLibreBean")
@ViewScoped
public class VersementLibreBean implements Serializable {

    final static String PDF = "";

    private static final long serialVersionUID = 1L;

    @EJB
    private IAdhesionDAO adhesionDAO;
    @EJB
    private ISocieteDAO societeDAO;
    @EJB
    private IPropositionDAO propositionDAO;
    
    @EJB
    private IConventionDAO conventionDAO;
    @EJB
    private IProdconvDAO prodconvDAO;
    @EJB
    private IUtilisateursDAO utilisateursDAO;
    @EJB
    private IAgenceDAO agenceDAO;
    
    @EJB
    private IPoliceDAO policeDAO;
    
    @EJB
    private IClientDAO clientDAO;
    
    @EJB
    private IProduitDAO produitDAO;
  
    
    @EJB
	private IParametresMrhService parametresMrhService;
    
    @EJB
   	private IProfilDAO iProfilDAO;
    
	@EJB
	private IQuittanceDAO iQuittanceDAO;
	
	@EJB
	private IQuittanceTermeDAO  iQuittanceTermeDAO;
	
	
	@EJB
    private IOperationTransactionDAO iOperationTransactionDAO;
	
	
	@EJB
	private IQuittanceVersementLibreDAO  iQuittanceVLDAO;
    
    @ManagedProperty(value = "#{login.utilisateur}")
    private Utilisateurs utilisateur;
    @ManagedProperty("#{reportController}")
	private ReportController reportController;
    
    @ManagedProperty("#{gestionProfil}")
	private GestionProfil gestionProfil ;
    
    
    
    @EJB
	private HayyaServiceInter hayyaServiceInter;
    
	@EJB
	private IQuittanceDetailsDAO quittanceDetailsDAO;
	
	
	private Visibility evenement;
    
    
    private Adhesion selectedAdhesion;
    private Double contrib;
    private boolean disabledValidationAdhesion=false;
    private boolean disabledValidationEncaissement = true;
    private boolean disabledImprimQuittance = true;


	private List<Adhesion> adhesions = new ArrayList<Adhesion>();
    private List<Adhesion> lstadhs;
    private List<Proposition> propositions = new ArrayList<Proposition>();
    private List<Proposition> lstprops;
    private List<Client> clients = new ArrayList<Client>();
    private List<Prodconv> lstproduit;
    private boolean affiche = true;
    private Map<String, Object> listSociete=null;
	private String codeSociete;
	
	  private Date to;
	  private Date from;
	  private String dateDu,dateAu;
	  private String nadhesion,cin,libelleProduit;
	  
	  int rowIndex;
	  
	  private List<QuittanceImapyeeDTO> quittancesImapyeesList = new ArrayList<>();
	  
	  private List<QuittanceVersementLibre> quittanceVLList = new ArrayList<>();
	  
	  private List<Quittance> quittancesList = new ArrayList<>();
	  
	  private List<QuittanceDetails> qtcDetailsList = new ArrayList<>();
	  
	  private List<QuittanceVLDTO> qtcVlList = new ArrayList<>();
	  
	private String [] userSociete={PofileEnum.G.name(),PofileEnum.AA.name(),PofileEnum.AS.name(),PofileEnum.BNAA.name(),PofileEnum.BNA.name()};
	  private List<Produit> produits=new ArrayList<Produit>();
	
	@EJB
	private IChartDAO iChartDAO;
	
    
    public Map<String, Object> getListSociete() {
		return listSociete;
	}

	public void setListSociete(Map<String, Object> listSociete) {
		this.listSociete = listSociete;
	}

	public String getCodeSociete() {
		return codeSociete;
	}

	public void setCodeSociete(String codeSociete) {
		this.codeSociete = codeSociete;
	}

	public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}
	
	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	
	
	public GestionProfil getGestionProfil() {
		return gestionProfil;
	}

	public void setGestionProfil(GestionProfil gestionProfil) {
		this.gestionProfil = gestionProfil;
	}
	
	
	

	public Adhesion getSelectedAdhesion() {
		disabledValidationAdhesion = false;
		disabledValidationEncaissement = true;
		disabledImprimQuittance = true;
		return selectedAdhesion;
	}

	public void setSelectedAdhesion(Adhesion selectedAdhesion) {
		this.selectedAdhesion = selectedAdhesion;
	}
	
	

	public Double getContrib() {
		return contrib;
	}

	public void setContrib(Double contrib) {
		this.contrib = contrib;
	}
	
	
	

	public boolean isDisabledValidationAdhesion() {
		return disabledValidationAdhesion;
	}

	public void setDisabledValidationAdhesion(boolean disabledValidationAdhesion) {
		this.disabledValidationAdhesion = disabledValidationAdhesion;
	}
	
	

	public boolean isDisabledValidationEncaissement() {
		return disabledValidationEncaissement;
	}

	public void setDisabledValidationEncaissement(boolean disabledValidationEncaissement) {
		this.disabledValidationEncaissement = disabledValidationEncaissement;
	}
	
	
	

	public boolean isDisabledImprimQuittance() {
		return disabledImprimQuittance;
	}

	public void setDisabledImprimQuittance(boolean disabledImprimQuittance) {
		this.disabledImprimQuittance = disabledImprimQuittance;
	}

	@PostConstruct
    public void init() {
		
		/*** societe****/
		 listSociete = iChartDAO.recupererListeSociete();
		 codeSociete = (String) listSociete.values().toArray()[0];
		
		  System.out.println("le_codeSociete :"+codeSociete);
		  
		/*
		  Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		  Date date = new Date();
		  System.out.println(formatter.format(date));
			 dateDu = formatter.format(date);
			 dateAu = formatter.format(date);
			
			from = date;
			to = date;
			
			 String cin_ = cin;
			 String nadhesion_ = nadhesion;
			 String libelleProduit_ = libelleProduit;
		*/


			if(Arrays.asList(userSociete).contains(utilisateur.getProfil().getCode())){
				Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
				// lstproduit=prodconvDAO.getlstProduit_by_convention_for_qtc(c);
				lstproduit=prodconvDAO.getlstProduit_by_convention_for_qtc_vlibre(c);
				for (Prodconv produit : lstproduit) {
					produits.add(produit.getProduit());
				}
			}else {
				
				 produits=produitDAO.findAll();
				
				
			}
			
//			
//			 Format formatter = new SimpleDateFormat("dd/MM/yyyy");
//			  Date date = new Date();
//			  System.out.println(formatter.format(date));
//				 dateDu = formatter.format(date);
//				 dateAu = formatter.format(date);
//				
//				from = date;
//				to = date;
//				
//				
//				 String cin_ = "";
//				 String nadhesion_ = "";
//				 String libelleProduit_ = "";
//				 
//				 
//				 adhesions = new ArrayList<Adhesion>(); 
//				 propositions = new ArrayList<Proposition>();
//			  
//			  if(utilisateur.getProfil().getCode().equals(PofileEnum.G.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNAA.name()) )
//		        {
//		            System.out.println("consultationBean fnction rechercher pour gestionnaire");
//
//		                adhesions = adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient_for_VL(
//		                		utilisateur,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
//		          //      System.out.println("adhesions "+adhesions.size());
//		               
//		        
//		        }
//		        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AA.name()))
//		        {
//		        	 System.out.println("consultationBean fnction rechercher pour AA"); 
//		        	
//		        	List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(utilisateur.getAgence());
//		        	// System.out.println("***liste utilisateurs="+listeUtilisateurs);
//		        	 //List<Client> listWithDuplicates = new ArrayList<>();
//		             for (Utilisateurs utilisateurs : listeUtilisateurs){
//		            //	 System.out.println("***utilisateur="+utilisateurs);
//		            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient_for_VL(
//			                		utilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
//		            	 //System.out.println("***adhesions="+adhesions);
//		            	 
//		                 adhesions.addAll(lstadhs);
//		                 
//		                
//		               
//		               
//		             }
//		             
//		            
//	 
//		        }
//		        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AS.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNA.name()))
//		        {
//		        	System.out.println("consultationBean fnction rechercher pour AS"); 
//		        	
//		        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(utilisateur.getAgence().getSociete());
//		        	
//		        	//List<Client> listWithDuplicates =  new ArrayList<Client>();
//
//		        	
//		        	for (Agence agence : listeAgences){
//		           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
//		           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
//		            //	 System.out.println("***utilisateur="+utilisateurs);
//		            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient_for_VL(
//			                		utilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
//		            	 //System.out.println("***adhesions="+adhesions);
//		            	 
//		                 adhesions.addAll(lstadhs);
//		                 
//		              
//		             }
//		           	 
//		           	
//		           	 
//		           	
//
//		            }
//
//		        	
//		        }
//		        else if(/*utilisateur.getProfil().getCode().equals(PofileEnum.AST.name())||*/utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name())/*|| PofileEnum.ASI.getChildrensASI().contains(utilisateur.getProfil().getCode())*/)
//		        {
//		        	System.out.println("consultationBean fnction rechercher pour ASI"); 
//		        	
//		        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(societeDAO.find(codeSociete));
//		        	
//		        	
//		        	for (Agence agence : listeAgences){
//		           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
//		           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
//		            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient_for_VL(
//			                		utilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
//		            	 
//		                 adhesions.addAll(lstadhs);
//		                 
//		             }
//		           	 
//		         	
//
//		            }
//		        	
//		        	
//		        }
//		        else {
//		        	
//		        	System.out.println("cas metier TD"); 
//		        	
//		        	  }
//		
//			
    	/*
        
        if(utilisateur.getProfil().getCode().equals(PofileEnum.G.name())|| utilisateur.getProfil().getCode().equals(PofileEnum.BNAA.name()))
        {
          

                adhesions = adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
                		utilisateur,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
           
                
		
            
        
        }
        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AA.name()))
        {
        	 List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(utilisateur.getAgence());
        
             for (Utilisateurs utilisateurs : listeUtilisateurs){
            	
            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
	                		utilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
            	 
            	 
                 adhesions.addAll(lstadhs);
                
                
                 
              
               
             }
             
           
             System.out.println("***PofileEnum.AA.name()="+PofileEnum.AA.name()); 
         

        }
        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AS.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNA.name()))
        {
        	
        	
        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(utilisateur.getAgence().getSociete());
        	
        	
        	
        	for (Agence agence : listeAgences){
           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
           	 for (Utilisateurs utilisateurs : listeUtilisateurs){

            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
	                		utilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
            	 
                 adhesions.addAll(lstadhs);
                
             }
           	 
          
           	

            }

        	
        }
        else if(utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name()) )
        {
        	
        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(societeDAO.find(codeSociete));
        	
        	
        	
        	for (Agence agence : listeAgences){
           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
           	 for (Utilisateurs utilisateurs : listeUtilisateurs){

            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
	                		utilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
            	 
            	
                 adhesions.addAll(lstadhs);
                


             }
         	
            }
        	
        }
        else {
        	
        	System.out.println("cas metier TD");        	 

        }
      
      */
    }
	
	  public void Chercher(){
		  
		//  System.out.println("debut recherche");
		
		  dateDu = "";
			 dateAu = "";
		  
		 
		  if(from == null && to == null  && cin.toString().isEmpty() && nadhesion.toString().isEmpty()) {
			  
			 
			  
			  FacesMessage msg = new FacesMessage("Veuillez saisir une date et/ou n° contrat et/ou un identifiant", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				return;
		  }
		  
		  
		  if(from != null && to == null) {
			  
			 
			  
			  FacesMessage msg = new FacesMessage("Veuillez vérifier la date de création", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				return;
		  }
		  
		  if(from == null && to != null) {
			  
			 
			  
			  FacesMessage msg = new FacesMessage("Veuillez vérifier la date de création", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				return;
		  }
		  
		  if(from != null && to != null) {
			  
			  Format formatter = new SimpleDateFormat("dd/MM/yyyy");
				 dateDu = formatter.format(from);
				 dateAu = formatter.format(to);
		  }
		  
		  
			 
			 String cin_ = cin;
			 String nadhesion_ = nadhesion;
			 String libelleProduit_ = libelleProduit;
			 
			 adhesions = new ArrayList<Adhesion>(); 
			 propositions = new ArrayList<Proposition>();
		  
		  if(utilisateur.getProfil().getCode().equals(PofileEnum.G.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNAA.name()) )
	        {
	            System.out.println("consultationBean fnction rechercher pour gestionnaire");

	                adhesions = adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient_for_VL(
	                		utilisateur,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
	          //      System.out.println("adhesions "+adhesions.size());
	               
	        
	        }
	        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AA.name()))
	        {
	        	 System.out.println("consultationBean fnction rechercher pour AA"); 
	        	
	        	List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(utilisateur.getAgence());
	        	// System.out.println("***liste utilisateurs="+listeUtilisateurs);
	        	 //List<Client> listWithDuplicates = new ArrayList<>();
	             for (Utilisateurs utilisateurs : listeUtilisateurs){
	            //	 System.out.println("***utilisateur="+utilisateurs);
	            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient_for_VL(
		                		utilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
	            	 //System.out.println("***adhesions="+adhesions);
	            	 
	                 adhesions.addAll(lstadhs);
	                 
	                
	               
	               
	             }
	             
	            
 
	        }
	        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AS.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNA.name()))
	        {
	        	System.out.println("consultationBean fnction rechercher pour AS"); 
	        	
	        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(utilisateur.getAgence().getSociete());
	        	
	        	//List<Client> listWithDuplicates =  new ArrayList<Client>();

	        	
	        	for (Agence agence : listeAgences){
	           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
	           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
	            //	 System.out.println("***utilisateur="+utilisateurs);
	            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient_for_VL(
		                		utilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
	            	 //System.out.println("***adhesions="+adhesions);
	            	 
	                 adhesions.addAll(lstadhs);
	                 
	              
	             }
	           	 
	           	
	           	 
	           	

	            }

	        	
	        }
	        else if(/*utilisateur.getProfil().getCode().equals(PofileEnum.AST.name())||*/utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name())/*|| PofileEnum.ASI.getChildrensASI().contains(utilisateur.getProfil().getCode())*/)
	        {
	        	System.out.println("consultationBean fnction rechercher pour ASI"); 
	        	
	        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(societeDAO.find(codeSociete));
	        	
	        	
	        	for (Agence agence : listeAgences){
	           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
	           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
	            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient_for_VL(
		                		utilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
	            	 
	                 adhesions.addAll(lstadhs);
	                 
	             }
	           	 
	         	

	            }
	        	
	        	
	        }
	        else {
	        	
	        	System.out.println("cas metier TD"); 
	        	
	        	  }
      	
      }
      

    public List<Prodconv> getLstproduit() {
		return lstproduit;
	}

	public void setLstproduit(List<Prodconv> lstproduit) {
		this.lstproduit = lstproduit;
	}

	public List<Adhesion> getAdhesions() {
        return adhesions;
    }

    public List<Proposition> getPropositions() {
        return propositions;
    }

    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
	
	
	@EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;
	private String numquit;
	

	
	
	
public void printQTC(Adhesion adhesion, QuittanceVLDTO qtc) {
		
		/*
		if (adhesion.getPolice().getCodProdui().getLibelleProduit().equals("TLSAssistV"))
		{
			
			
			numquit=serviceAssistantVoiyagetService.getNumeroQuit();
		
			
			//Adhesion adhesion =adhesionToPrintNotise;
			String report;
			try {
				
					 report = reportController.getRealPath()	+ "qtcTLS.jasper";
				
				Map<String, Object> parameters = new HashMap<String, Object>();
				DecimalFormat formatter = new DecimalFormat("#0.000");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
				parameters.put("imagesociete",  utilisateur.getAgence().getSociete().getCodSociet()+ ".png");
				parameters.put("star",  "star.png");
				parameters.put("nomclt", adhesion.getClient().getNomClient() + " " + adhesion.getClient().getPrenomClient());
				for (Adherent adherent : adhesion.getAdherents()) {
				parameters.put("nomass", adherent.getNomAdherent()+" "+adherent.getPrenomAdherent());
				}
				parameters.put("adrclt", adhesion.getClient().getNumAdr()+" "+adhesion.getClient().getTypeVoie().getLibelle()+" "+adhesion.getClient().getAdrClient());
				parameters.put("codpclt", adhesion.getClient().getCodeP());
				parameters.put("vilclt", adhesion.getClient().getLocalite());
				parameters.put("gouvclt", adhesion.getClient().getGouver());
				parameters.put("identifiant", adhesion.getClient().getIdentifiant());
				parameters.put("libelle_pack", adhesion.getPack().getLibelle());
				parameters.put("Dateff", sdf.format(adhesion.getDatDebut()));
				parameters.put("Datech", sdf.format(adhesion.getDatFin()));
				parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
				parameters.put("nadhesion", adhesion.getNadhesion());
				parameters.put("prmnet", formatter.format(adhesion.getMtPrime()));
				parameters.put("frais", formatter.format(adhesion.getCout()));
				parameters.put("prmtot", formatter.format(adhesion.getPrimetot()));
				parameters.put("vilagc", utilisateur.getAgence().getLibelle());
				parameters.put("numquit", numquit);


				JasperPrint print = JasperFillManager.fillReport(report,parameters, new JREmptyDataSource());
				byte[] bytes = JasperExportManager.exportReportToPdf(print);

				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				ec.responseReset();
				ec.setResponseContentType("application/pdf");
				ec.setResponseContentLength(bytes.length);
				ec.setResponseHeader("Content-Disposition", "inline; filename=QUITTANCE.pdf");

				OutputStream output = ec.getResponseOutputStream();
				output.write(bytes);
				fc.responseComplete();

				adhesion.setImpr(true);
				adhesionDAO.edit(adhesion);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		*/
	
	/// 7ayya
		 if (adhesion.getPolice().getCodProdui().getCodeProduit().
				 equalsIgnoreCase( ENUMCODEPRODUIT.H7AYYA.getCode()) ||
				 adhesion.getPolice().getCodProdui().getCodeProduit().
				 equalsIgnoreCase( ENUMCODEPRODUIT.H7ayyaQNB.getCode())
				 )
		{
			
			
			//numquit=serviceAssistantVoiyagetService.getNumeroQuit();
		
			
			//Adhesion adhesion =adhesionToPrintNotise;
			String report;
			try {
				
					 report = reportController.getRealPath()	+ "Quit_VL_hayaa.jasper";
					 
					 
					 
				
				Map<String, Object> parameters = new HashMap<String, Object>();
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
				
				parameters.put("numquit", String.valueOf( qtc.getQuittance().getNumquit()) );

				parameters.put("agence", qtc.getDetail().getAgence().getCodeAgence());
				parameters.put("contrat",adhesion.getNadhesion());
				
				

				
				parameters.put("echeance", dateFormat.format(qtc.getQuittance().getDATE_CREATION()));
				
				parameters.put("souscr", adhesion.getClient().getNomClient()
						+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_souscr", adhesion.getClient().getAdrClient());
				 
				parameters.put("assure", adhesion.getClient().getNomClient()
						+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_assure", adhesion.getClient().getAdrClient());
				 
				 
				
				//parameters.put("prime_tot", adhesion.getPrimetot());
				parameters.put("frais", qtc.getQuittance().getFrais());
				parameters.put("fga", qtc.getQuittance().getFga());
				parameters.put("taxe", qtc.getQuittance().getTaxe().doubleValue());
				//parameters.put("prime_nette", detailHayya.getPnette());
				
				
			
					
					// parameters.put("prime_tot", qtc.getDetail().getPrimetot().doubleValue());
					parameters.put("prime_tot", new BigDecimal( qtc.getDetail().getPrimenette() ));
					parameters.put("prime_nette", new BigDecimal( qtc.getDetail().getPrimenette() ));
				  
			 
			
				
				 
				
				parameters
				.put("star",  reportController.getLogoJasper());
				
				parameters.put("imagesociete", utilisateur.getAgence().getSociete()
						.getCodSociet() + ".png");
				
				
				


				JasperPrint print = JasperFillManager.fillReport(report,parameters, new JREmptyDataSource());
				byte[] bytes = JasperExportManager.exportReportToPdf(print);

				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				ec.responseReset();
				ec.setResponseContentType("application/pdf");
				ec.setResponseContentLength(bytes.length);
				ec.setResponseHeader("Content-Disposition", "inline; filename=QUITTANCE.pdf");

				OutputStream output = ec.getResponseOutputStream();
				output.write(bytes);
				fc.responseComplete();

				//adhesion.setImpr(true);
				//adhesionDAO.edit(adhesion);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		 
		// avenir jeunesse
		 if (adhesion.getPolice().getCodProdui().getCodeProduit().equalsIgnoreCase( ENUMCODEPRODUIT.AVENIRJENESSE.getCode()))
		{
			 
			 String report;
				try {
					
						 report = reportController.getRealPath()	+ "Quit_VL_avj.jasper";
						 
						 
						 
					
					Map<String, Object> parameters = new HashMap<String, Object>();
				
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
					
					parameters.put("numquit", String.valueOf( qtc.getQuittance().getNumquit()) );

					parameters.put("agence", qtc.getDetail().getAgence().getCodeAgence());
					parameters.put("contrat",adhesion.getNadhesion());
					
					

					
					parameters.put("echeance", dateFormat.format(qtc.getQuittance().getDATE_CREATION()));
					
					parameters.put("souscr", adhesion.getClient().getNomClient()
							+" "+adhesion.getClient().getPrenomClient());
					parameters.put("adr_souscr", adhesion.getClient().getAdrClient());
					 
					parameters.put("assure", adhesion.getClient().getNomClient()
							+" "+adhesion.getClient().getPrenomClient());
					parameters.put("adr_assure", adhesion.getClient().getAdrClient());
					 
					 
					
					//parameters.put("prime_tot", adhesion.getPrimetot());
					parameters.put("frais", qtc.getQuittance().getFrais());
					parameters.put("fga", qtc.getQuittance().getFga());
					double taxe = qtc.getQuittance().getTaxe() != null ? qtc.getQuittance().getTaxe().doubleValue() : 0;
					parameters.put("taxe", taxe);
					//parameters.put("prime_nette", detailHayya.getPnette());
					
					
				
						
						// parameters.put("prime_tot", qtc.getDetail().getPrimetot().doubleValue());
						parameters.put("prime_tot", new BigDecimal( qtc.getDetail().getPrimenette() ));
						parameters.put("prime_nette", new BigDecimal( qtc.getDetail().getPrimenette() ));
					  
				 
				
					
					 
					
					parameters
					.put("star",  reportController.getLogoJasper());
					
					parameters.put("imagesociete", utilisateur.getAgence().getSociete()
							.getCodSociet() + ".png");
					
					
					


					JasperPrint print = JasperFillManager.fillReport(report,parameters, new JREmptyDataSource());
					byte[] bytes = JasperExportManager.exportReportToPdf(print);

					FacesContext fc = FacesContext.getCurrentInstance();
					ExternalContext ec = fc.getExternalContext();
					ec.responseReset();
					ec.setResponseContentType("application/pdf");
					ec.setResponseContentLength(bytes.length);
					ec.setResponseHeader("Content-Disposition", "inline; filename=QUITTANCE.pdf");

					OutputStream output = ec.getResponseOutputStream();
					output.write(bytes);
					fc.responseComplete();

					//adhesion.setImpr(true);
					//adhesionDAO.edit(adhesion);
				} catch (Exception e) {
					e.printStackTrace();
				}
			 
		}
		 
		 /*
		else {
			
			
		
		Map<String, Object> parameters = new HashMap();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        parameters.put("societe", adhesion.getPolice().getConvention()
                .getSociete().getRaisonSoc());
        parameters.put("nomclt", adhesion.getClient().getNomClient());
        parameters.put("prenomclt",adhesion.getClient().getPrenomClient());
        parameters.put("identifiant",String.valueOf(adhesion.getClient().getIdentifiant()));
        parameters.put("telclt",adhesion.getClient().getTel());
        parameters.put("mailclt",adhesion.getClient().getMail());
        parameters.put("adrclt", adhesion.getClient().getNumAdr()+" "+adhesion.getClient().getTypeVoie()+" "+adhesion.getClient().getAdrClient());
        parameters.put("vilclt", adhesion.getClient().getLocalite());
        parameters.put("cpclt", adhesion.getClient().getCodeP());
        parameters.put("gouvclt", adhesion.getClient().getGouver());
        parameters.put("adrrisque", adhesion.getAdresse());
        parameters.put("vilrisque", adhesion.getLocalite());
        parameters.put("gouvrisque", adhesion.getGouver());
        parameters.put("cprisque", adhesion.getCodeP());
        parameters.put("dateEffet", df.format(adhesion.getDatDebut()));
        parameters.put("datefin", df.format(adhesion.getDatFin()));
        parameters.put("duree", String.valueOf(adhesion.getDuree()));
        parameters.put("imagesociete",adhesion.getUtilisateur().getAgence().getSociete().getCodSociet()+".png");
        parameters.put("star","star.png");
        parameters.put("capital", String.valueOf(adhesion.getCapital()));
        parameters.put("ville_agence", adhesion.getUtilisateur().getAgence().getLibelle());
        
        parameters.put("codagc", adhesion.getUtilisateur().getAgence().getCodeAgence());

        try {
            String path = reportController.getRealPath() + "fdr_inc.jasper";
            // this.getClass().getResource("closed.jasper").getPath() 
            JasperPrint jp = JasperFillManager.fillReport(path, parameters,
                    new JREmptyDataSource());

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getResponse();

            httpServletResponse.addHeader("Content-disposition",
                    "inline; filename=FDR_inc.pdf");
            ServletOutputStream servletOutputStream;
            try {
                servletOutputStream = httpServletResponse.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jp,
                        servletOutputStream);
                FacesContext.getCurrentInstance().responseComplete();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
        
        
		}
		
		*/
		
	}



	
	
	
	
	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	
	 public Date getTo() {
	        return to;
	    }

	    public void setTo(Date to) {
	        this.to = to;
	    }

	    public Date getFrom() {
	        return from;
	    }

	    public void setFrom(Date from) {
	        this.from = from;
	    }
	    
	    
    
	    
	    public List<QuittanceImapyeeDTO> getQuittancesImapyeesList() {
			return quittancesImapyeesList;
		}

		public void setQuittancesImapyeesList(List<QuittanceImapyeeDTO> quittancesImapyeesList) {
			this.quittancesImapyeesList = quittancesImapyeesList;
		}
		
		
		

		public List<QuittanceVersementLibre> getQuittanceVLList() {
			return quittanceVLList;
		}

		public void setQuittanceVLList(List<QuittanceVersementLibre> quittanceVLList) {
			this.quittanceVLList = quittanceVLList;
		}
		
		

		public List<Quittance> getQuittancesList() {
			return quittancesList;
		}

		public void setQuittancesList(List<Quittance> quittancesList) {
			this.quittancesList = quittancesList;
		}
		
		
		
		

		public Visibility getEvenement() {
			return evenement;
		}

		public void setEvenement(Visibility evenement) {
			this.evenement = evenement;
		}

		public List<QuittanceVLDTO> getQtcVlList() {
			return qtcVlList;
		}

		public void setQtcVlList(List<QuittanceVLDTO> qtcVlList) {
			this.qtcVlList = qtcVlList;
		}

		public List<QuittanceDetails> getQtcDetailsList() {
			return qtcDetailsList;
		}

		public void setQtcDetailsList(List<QuittanceDetails> qtcDetailsList) {
			this.qtcDetailsList = qtcDetailsList;
		}

		public String getLibelleProduit() {
			return libelleProduit;
		}

		public void setLibelleProduit(String libelleProduit) {
			this.libelleProduit = libelleProduit;
		}

		public String getNadhesion() {
			return nadhesion;
		}

		public void setNadhesion(String nadhesion) {
			this.nadhesion = nadhesion;
		}

		public String getCin() {
			return cin;
		}

		public void setCin(String cin) {
			this.cin = cin;
		}

		public TarifMrh getParamPrices() {
			TarifMrh tarif805 = parametresMrhService.getTarifMrh();
			return tarif805;
		}

		public boolean isAffiche() {
			return affiche;
		}

		public void setAffiche(boolean affiche) {
			this.affiche = affiche;
		}
	    
		
		
		public void onRowToggle(AjaxBehaviorEvent event) {
		    ToggleEvent toggleEvent = (ToggleEvent) event;
		    
		    evenement = toggleEvent.getVisibility();
		   
		    if (toggleEvent.getVisibility() == Visibility.VISIBLE) {
		        System.out.println("visible");
		        
		        System.out.println("event.getData() "+toggleEvent.getData());
		        
		        System.out.println("event.getData() Adhesion "+ ((Adhesion)toggleEvent.getData()).getNadhesion());
		        
		        
		        String id= ((Adhesion)toggleEvent.getData()).getNadhesion();
		        // quittanceVLList = iQuittanceVLDAO.searchquitByNAdhesion(id);
		        
		        qtcVlList = new ArrayList<>();
		        
		        qtcDetailsList = quittanceDetailsDAO.searchByNAdhesion(id, "VL");
		        
		        if(qtcDetailsList != null) {
		        
		        List<Long> idQuittances = new ArrayList<Long>();
		        
		        for(QuittanceDetails det : qtcDetailsList) {
		        	
		        	idQuittances.add(det.getIdQuittance());
		        }
		        
		        quittancesList = iQuittanceDAO.searchQuitByManyIdQuittance(idQuittances);
		        
		        
		        for(QuittanceDetails det : qtcDetailsList) {
		        	
		        	for(Quittance qtc : quittancesList) {
		        		
		        		if(qtc.getIdQuittance() == det.getIdQuittance()) {
		        			QuittanceVLDTO vl = new QuittanceVLDTO(qtc,det);
			        		qtcVlList.add(vl);
		        		}
		        	}
		        	
		        	
		        }
		        
//		        for(int i = 0; i < idQuittances.size(); i++) {
// 		
//		        		QuittanceVLDTO vl = new QuittanceVLDTO(quittancesList.get(i),qtcDetailsList.get(i));
//		        		qtcVlList.add(vl);
//		        	
//		        }
		        
		    }
		        
		        
		        
		    } else {
		    	 System.out.println("non visible");
		    }
		   
		}
		
		
		
		public void EncaissementQuittance__sans_ws(Adhesion adhesion, QuittanceVLDTO qtc) {
			
			//if(evenement == Visibility.VISIBLE)
			
			System.out.println("evenement "+evenement);

			QuittanceDetails qtc_detail = qtc.getDetail();
			System.out.println("qtc_detail "+qtc_detail.getIdQuittance());
			System.out.println("qtc_detail "+qtc.getDetail().getIdQuittance());
			qtc_detail.setEtat("O");
			qtc.getDetail().setEtat("O");
			
			System.out.println("qtc_detail 0 "+qtc.getDetail().getIdQuittance());
			
			quittanceDetailsDAO.Merge(qtc_detail);

			
			
				addMessage("Succès", "encaissement effectué avec succès");
			
		}
		
		public void EncaissementQuittance(Adhesion adhesion, QuittanceVLDTO qtc) throws KeyManagementException, NoSuchAlgorithmException {
			
			//if(evenement == Visibility.VISIBLE)
			
			System.out.println("evenement "+evenement);

			QuittanceDetails qtc_detail = qtc.getDetail();
			System.out.println("qtc_detail "+qtc_detail.getIdQuittance());
			System.out.println("qtc_detail "+qtc.getDetail().getIdQuittance());
			
			/*
			qtc_detail.setEtat("O");
			qtc.getDetail().setEtat("O");
			
			System.out.println("qtc_detail 0 "+qtc.getDetail().getIdQuittance());
			
			quittanceDetailsDAO.Merge(qtc_detail);

				addMessage("Succès", "encaissement effectué avec succès");
				*/
			
			
			if(utilisateur.getAgence().getSociete()
					.getCodSociet().equalsIgnoreCase(reportController.getSTBMat_Pour_Encaissement_Versement_Libre_et_Terme())) {  //STB
				
				
				WS_Encaissement(adhesion, qtc, qtc.getQuittance(), qtc.getDetail());
				
			}else{  //QNB
				
				
				
				/**** enregistremnt ds notre !BD ****/

				qtc.getDetail().setEtat("O");
				qtc.getDetail().setDateEnc(new Date());
				//quittance_details_saved.setRefOperation(Reference);
				quittanceDetailsDAO.Merge(qtc.getDetail());
				qtc.getDetail().setEtat("O");
				
				disabledValidationEncaissement = true;
				disabledImprimQuittance = false;
				
				
				FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				System.out.println("Encaissement effectuée avec succès.");
				
				
			}
			
			
			
			
			
		}

		public void confirm() {
		    addMessage("Confirmed", "You have accepted");
		}
		
		public void addMessage(String summary, String detail) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
	        FacesContext.getCurrentInstance().addMessage(null, message);
	    }
	    
    
		public void ValiderContrat() {
			System.out.println("visible");
			
			disabledValidationAdhesion = false;
			disabledValidationEncaissement = true;
			disabledImprimQuittance = true;
			
			if(contrib <=0) {
				System.out.println("visible neg");
				FacesMessage msg = new FacesMessage("La Contribution Exceptionnelle est obligatoire et supérieure à zéro!", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				return;
			}
			
			if(contrib == null) {
				System.out.println("visible null");
				FacesMessage msg = new FacesMessage("contrib null", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				return;
			}
			
			
			/*
			QuittanceDetails qtc_det_not_encaissed =  quittanceDetailsDAO.searchDetailQuit_not_encaissed(
					selectedAdhesion.getNadhesion(), "VL", "N");
			
			if(qtc_det_not_encaissed != null) {
				System.out.println("qtc_det_not_encaissed");
				FacesMessage msg = new FacesMessage("Il y a une quittance non encaissée !", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				return;
			}
			*/
			
			System.out.println("visible222");
			
			disabledValidationAdhesion = true;
			disabledValidationEncaissement = false;
			
			
			System.out.println("selectedAdhesion "+selectedAdhesion.getNadhesion());
			
			numquit=serviceAssistantVoiyagetService.getNumeroQuit();
			
			
			
			Quittance quittance=new Quittance(new Date(), selectedAdhesion.getDatDebut(), selectedAdhesion, 
					contrib, 
					(double)0, /*selectedAdhesion.getTaxe()*/(double)0, 
					Integer.parseInt(numquit), new Date(), /*selectedAdhesion.getFga()*/(double)0);
			
			iQuittanceDAO.create(quittance);
			
			Quittance quittance_saved = iQuittanceDAO.searchquit(Integer.parseInt(numquit));
			
			int fract_id = 0;
			if(selectedAdhesion.getPolice().getCodProdui().getCodeProduit().
					equalsIgnoreCase( ENUMCODEPRODUIT.H7AYYA.getCode()) ||
					selectedAdhesion.getPolice().getCodProdui().getCodeProduit().
					equalsIgnoreCase( ENUMCODEPRODUIT.H7ayyaQNB.getCode())
					) {
				
				DetailHayya detailHayya = hayyaServiceInter.find_detail_by_Adhesion(selectedAdhesion);
				fract_id = (int) detailHayya.getFractHayya().getId_fract_hayya();
			}
			
			QuittanceDetails qt = new QuittanceDetails(
					quittance_saved.getIdQuittance(),
					selectedAdhesion,
					utilisateur.getAgence(), "VL", 
					"N", fract_id, 
					contrib /*+ selectedAdhesion.getFga()*/ , contrib,
					contrib, contrib);
					
					quittanceDetailsDAO.create(qt);
			
			
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Flash flash = facesContext.getExternalContext().getFlash();
			flash.setKeepMessages(true);
			facesContext.addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Validation effectuée avec succès.", 
					"!"));
		}
		
		
		public void Encaissement_sans_ws() {
			
			disabledValidationEncaissement = true;
			disabledImprimQuittance = false;
			
			Quittance quittance_saved = iQuittanceDAO.searchquit(Integer.parseInt(numquit));
			
			QuittanceDetails quittance_details_saved = quittanceDetailsDAO.searchDetailQuit(
					quittance_saved.getIdQuittance());
			
			quittance_details_saved.setEtat("O");
			
			quittance_details_saved.setDateEnc(new Date());
			quittance_details_saved.setRefOperation("Reference");
			
			quittanceDetailsDAO.Merge(quittance_details_saved);
			
			FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			
		}
		
		public void Encaissement() throws KeyManagementException, NoSuchAlgorithmException {
			
			/*
			disabledValidationEncaissement = true;
			disabledImprimQuittance = false;
			*/
			
			Quittance quittance_saved = iQuittanceDAO.searchquit(Integer.parseInt(numquit));
			
			QuittanceDetails quittance_details_saved = quittanceDetailsDAO.searchDetailQuit(
					quittance_saved.getIdQuittance());
			
			/*
			quittance_details_saved.setEtat("O");
			quittanceDetailsDAO.Merge(quittance_details_saved);
			
			FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			*/
			QuittanceVLDTO qtc = new QuittanceVLDTO();
			Quittance qtc_quittance = new Quittance();
			QuittanceDetails qtc_detail = new QuittanceDetails();
			qtc.setQuittance(qtc_quittance);
			qtc.setDetail(qtc_detail);
			
			if(utilisateur.getAgence().getSociete()
					.getCodSociet().equalsIgnoreCase(reportController.getSTBMat_Pour_Encaissement_Versement_Libre_et_Terme())) {  //STB
				
				WS_Encaissement(selectedAdhesion, qtc, quittance_saved, quittance_details_saved) ;
				
			}else{  //QNB
				
				
				
				/**** enregistremnt ds notre !BD ****/

				quittance_details_saved.setEtat("O");
				quittance_details_saved.setDateEnc(new Date());
				//quittance_details_saved.setRefOperation(Reference);
				quittanceDetailsDAO.Merge(quittance_details_saved);
				qtc.getDetail().setEtat("O");
				
				disabledValidationEncaissement = true;
				disabledImprimQuittance = false;
				
				
				FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				System.out.println("Encaissement effectuée avec succès.");
				
				
			}
			
			
			
			
		}
		
		
		
		public void imprimer_quit() {
	
			// 7ayya
			 if (selectedAdhesion.getPolice().getCodProdui().getCodeProduit().
					 equalsIgnoreCase( ENUMCODEPRODUIT.H7AYYA.getCode()) ||
					 selectedAdhesion.getPolice().getCodProdui().getCodeProduit().
					 equalsIgnoreCase( ENUMCODEPRODUIT.H7ayyaQNB.getCode())
					 )
			{
				
				
				//numquit=serviceAssistantVoiyagetService.getNumeroQuit();
			
				
				//Adhesion adhesion =adhesionToPrintNotise;
				String report;
				try {
					
						 report = reportController.getRealPath()	+ "Quit_VL_hayaa.jasper";
						 
						 
						 Quittance quittance_saved = iQuittanceDAO.searchquit(Integer.parseInt(numquit));
						 
						 QuittanceDetails quittance_details_saved = quittanceDetailsDAO.searchDetailQuit(
									quittance_saved.getIdQuittance());
							
							Map<String, Object> parameters = new HashMap<String, Object>();
						
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
							
							parameters.put("numquit", numquit );

							parameters.put("agence", quittance_details_saved.getAgence().getCodeAgence());
							parameters.put("contrat",selectedAdhesion.getNadhesion());
							

							
							parameters.put("echeance", dateFormat.format(quittance_saved.getDATE_CREATION()));
							
							parameters.put("souscr", selectedAdhesion.getClient().getNomClient()
									+" "+selectedAdhesion.getClient().getPrenomClient());
							parameters.put("adr_souscr", selectedAdhesion.getClient().getAdrClient());
							 
							parameters.put("assure", selectedAdhesion.getClient().getNomClient()
									+" "+selectedAdhesion.getClient().getPrenomClient());
							parameters.put("adr_assure", selectedAdhesion.getClient().getAdrClient());
							 
							 
							
							//parameters.put("prime_tot", adhesion.getPrimetot());
							parameters.put("frais", quittance_saved.getFrais());
							parameters.put("fga", quittance_saved.getFga());
							parameters.put("taxe", quittance_saved.getTaxe().doubleValue());
							//parameters.put("prime_nette", detailHayya.getPnette());
							
							
						
								
								
								parameters.put("prime_tot", quittance_details_saved.getPrimetot().doubleValue());
								parameters.put("prime_nette", new BigDecimal(quittance_details_saved.getPrimenette()));
							  
						 
						
							
							 
							
							parameters
							.put("star",  reportController.getLogoJasper());
							
							parameters.put("imagesociete", utilisateur.getAgence().getSociete()
									.getCodSociet() + ".png");
							
							
							


							JasperPrint print = JasperFillManager.fillReport(report,parameters, new JREmptyDataSource());
							byte[] bytes = JasperExportManager.exportReportToPdf(print);

							FacesContext fc = FacesContext.getCurrentInstance();
							ExternalContext ec = fc.getExternalContext();
							ec.responseReset();
							ec.setResponseContentType("application/pdf");
							ec.setResponseContentLength(bytes.length);
							ec.setResponseHeader("Content-Disposition", "inline; filename=QUITTANCE.pdf");

							OutputStream output = ec.getResponseOutputStream();
							output.write(bytes);
							fc.responseComplete();
							
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			 
		 // avenir jeunesse
			 if (selectedAdhesion.getPolice().getCodProdui().getCodeProduit().equalsIgnoreCase( ENUMCODEPRODUIT.AVENIRJENESSE.getCode()))
			{
				
				
				//numquit=serviceAssistantVoiyagetService.getNumeroQuit();
			
				
				//Adhesion adhesion =adhesionToPrintNotise;
				String report;
				try {
					
						 report = reportController.getRealPath()	+ "Quit_VL_avj.jasper";
						 
						 
						 Quittance quittance_saved = iQuittanceDAO.searchquit(Integer.parseInt(numquit));
						 
						 QuittanceDetails quittance_details_saved = quittanceDetailsDAO.searchDetailQuit(
									quittance_saved.getIdQuittance());
							
							Map<String, Object> parameters = new HashMap<String, Object>();
						
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
							
							parameters.put("numquit", numquit );

							parameters.put("agence", quittance_details_saved.getAgence().getCodeAgence());
							parameters.put("contrat",selectedAdhesion.getNadhesion());
							

							
							parameters.put("echeance", dateFormat.format(quittance_saved.getDATE_CREATION()));
							
							parameters.put("souscr", selectedAdhesion.getClient().getNomClient()
									+" "+selectedAdhesion.getClient().getPrenomClient());
							parameters.put("adr_souscr", selectedAdhesion.getClient().getAdrClient());
							 
							parameters.put("assure", selectedAdhesion.getClient().getNomClient()
									+" "+selectedAdhesion.getClient().getPrenomClient());
							parameters.put("adr_assure", selectedAdhesion.getClient().getAdrClient());
							 
							 
							
							//parameters.put("prime_tot", adhesion.getPrimetot());
							parameters.put("frais", quittance_saved.getFrais());
							parameters.put("fga", quittance_saved.getFga());
							double taxe = quittance_saved.getTaxe() != null ? quittance_saved.getTaxe().doubleValue() : 0;
							parameters.put("taxe", taxe);
							//parameters.put("prime_nette", detailHayya.getPnette());
							
							
						
								
								
								parameters.put("prime_tot", quittance_details_saved.getPrimetot().doubleValue());
								parameters.put("prime_nette", new BigDecimal(quittance_details_saved.getPrimenette()));
							  
						 
						
							
							 
							
							parameters
							.put("star",  reportController.getLogoJasper());
							
							parameters.put("imagesociete", utilisateur.getAgence().getSociete()
									.getCodSociet() + ".png");
							
							
							


							JasperPrint print = JasperFillManager.fillReport(report,parameters, new JREmptyDataSource());
							byte[] bytes = JasperExportManager.exportReportToPdf(print);

							FacesContext fc = FacesContext.getCurrentInstance();
							ExternalContext ec = fc.getExternalContext();
							ec.responseReset();
							ec.setResponseContentType("application/pdf");
							ec.setResponseContentLength(bytes.length);
							ec.setResponseHeader("Content-Disposition", "inline; filename=QUITTANCE.pdf");

							OutputStream output = ec.getResponseOutputStream();
							output.write(bytes);
							fc.responseComplete();
							
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
    
		
		
		public void WS_Encaissement(Adhesion adhesion, 
				QuittanceVLDTO qtc, Quittance quittance_saved, QuittanceDetails quittance_details_saved) 
				throws NoSuchAlgorithmException, KeyManagementException{
			
			boolean test = true;




		        

					String token = TokenSTB.tokenStb(reportController);
					
					if(token == null) {
						
						FacesMessage msg = new FacesMessage("ERREUR", "Echec d'encaissement: pas de réponse au WS Token!");
						  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
						  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
						  
						  
						  return;
						
					}else {
						
						if(token.equalsIgnoreCase("exception")) {
							
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , Erreur de connexion au WS Token!", ""));
				     		
				     		
				     		
				     		System.out.println("probleme de Exception  ");
				     		
				     		return;
						}
						
						if(token.equalsIgnoreCase("not200")) {
							
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , problème de droit d'accès au WS Token!", ""));
				     		
				     		
				     		
				     		System.out.println("probleme de Exception  ");
				     		
				     		return;
						}
					}



				
				 final String BASE_URI = Constante.URL_ENCAISS_STB;
				
				 //final String BASE_URI = "https://openbank.stb.com.tn:9443/v0/payment";
				
				System.out.println("BASE_URI "+Constante.URL_ENCAISS_STB);
				
				
				
				
				String IdDemande = String.valueOf( quittance_saved.getNumquit() );
				
				String PrimeAssurance = quittance_details_saved.getPrimetot().toString();
				String AccountNumber = adhesion.getClient().getNcompte();
				String Police = adhesion.getPolice().getNPolice() + adhesion.getNadhesion();
				String Cin = adhesion.getClient().getIdentifiant();
				String Nom = adhesion.getClient().getNomClient(); 
				String Prenom = adhesion.getClient().getPrenomClient();
				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				String DateNaissance = df.format(adhesion.getClient().getDatNaiss());
				
				
				String CapitalAssure = "0";
				if(adhesion.getPolice().getCodProdui().getCodeProduit().equalsIgnoreCase( ENUMCODEPRODUIT.H7AYYA.getCode())) {
					
					List<DetailsGarantieHayya> detailsGarantieHayyaList =  
							hayyaServiceInter.find_detail_garatie_by_Adhesion(adhesion);
					CapitalAssure = detailsGarantieHayyaList.get(0).getCapital().toString();
				}
				
				
				
		
				
				String DateEffet = df.format(adhesion.getDatDebut());
				String Datefin = df.format(adhesion.getDatFin());
				
				String Mobile = adhesion.getClient().getTel();
				String Mail = adhesion.getClient().getMail();
				
				
				
				String Produit = "00";
				if(adhesion.getPolice().getCodProdui().getCodeProduit().equalsIgnoreCase( ENUMCODEPRODUIT.H7AYYA.getCode())) {
					Produit = Constante.PRODUIT_HAYYA_VL;
				}else {
					Produit = Constante.PRODUIT_AVJ_VL;
				}
				
				String input = "{"
						+ "    \"IdDemande\": \""+IdDemande+"\","
						+ "    \"PrimeAssurance\": \""+PrimeAssurance+"\","
						+ "    \"AccountNumber\": \""+AccountNumber+"\","
						+ "    \"Police\": \""+Police+"\","
						+ "    \"Cin\": \""+Cin+"\","
						+ "    \"Nom\": \""+Nom+"\","
						+ "    \"Prenom\": \""+Prenom+"\","
						+ "    \"DateNaissance\": \""+DateNaissance+"\","
						+ "    \"CapitalAssure\": \""+CapitalAssure+"\","
						+ "    \"DateEffet\": \""+DateEffet+"\","
						+ "    \"Datefin\": \""+Datefin+"\","
						+ "    \"Mobile\": \""+Mobile+"\","
						+ "    \"Mail\": \""+Mail+"\","
						+ "    \"Produit\": \""+Produit+"\""
						+ "}";
				
				
				try {
					
					
	
				    
				    com.sun.jersey.api.client.Client client= JerseyClient.ApiClient(reportController);
				    ClientResponse response = JerseyClient.PostReponseDuClientSTBEncaissement(BASE_URI, token, input, client, reportController);

					if ( response == null) {
						System.out.println("probleme de connexion vide");
						FacesMessage msg = new FacesMessage("Echec d'encaissement!", "Pas de réponse au WS d'encaissement!");
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);

						
						return;
					}
				    
				    
				    if (response.getStatus() != 200) {
//						throw new RuntimeException("Failed : HTTP error code : "
//						     + response.getStatus());
				    	String output_0 = response.getEntity(String.class);
				    	org.json.JSONObject jsonOutput_0 = new org.json.JSONObject(output_0);
						String status = " ";
						String rejectMessage = " ";
						if (jsonOutput_0.has("Status")) {
							status = jsonOutput_0.getString("Status");
							}
						if (jsonOutput_0.has("RejectMessage")) {
							rejectMessage = jsonOutput_0.getString("RejectMessage");
							}
				    	System.out.println("probleme de connexion diff de 200");
						FacesMessage msg = new FacesMessage(status + " " + rejectMessage, "Erreur WS d'encaissement (code ="+response.getStatus()+") ");
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);

						
						return;
					}

					System.out.println("Output from Server .... \n");
					String output = response.getEntity(String.class);
					System.out.println(output);
					
					// JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
					
					org.json.JSONObject jsonOutput = new org.json.JSONObject(output);

				
					//String Status =jsonOutput.getString("Status");
					
					
					String Status = " ";
					String RejectMessage = " ";
					String Reference = " ";
					
					if (jsonOutput.has("Status")) {
						Status =jsonOutput.getString("Status");
						}
					if (jsonOutput.has("RejectMessage")) {
						RejectMessage = jsonOutput.getString("RejectMessage");
						}
					if (jsonOutput.has("Reference")) {
						Reference = jsonOutput.getString("Reference");
						}
				

					if(Status.equalsIgnoreCase("Processed")){
		               
						FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
						msg.setSeverity(FacesMessage.SEVERITY_INFO);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);
						System.out.println("Encaissement effectuée avec succès.");
						
						/**** enregistremnt ds notre !BD ****/

						quittance_details_saved.setEtat("O");
						quittance_details_saved.setDateEnc(new Date());
						quittance_details_saved.setRefOperation(Reference);
						quittanceDetailsDAO.Merge(quittance_details_saved);
						qtc.getDetail().setEtat("O");
						
						disabledValidationEncaissement = true;
						disabledImprimQuittance = false;
						
						OperationTransaction operationTransaction = new OperationTransaction();

						operationTransaction.setDateTrans(new Date());
						operationTransaction.setIdAdhesion(adhesion); // à discuter
						operationTransaction.setRefOperation(Reference);
						operationTransaction.setTypTrans("EncaissLibre");

						iOperationTransactionDAO.create(operationTransaction);


					}else {
						
						 System.out.println("Echec d'encaissement");
							FacesMessage msg = new FacesMessage("Echec d'encaissement : " + Status + " " + RejectMessage, "Erreur WS d'encaissement!");
							msg.setSeverity(FacesMessage.SEVERITY_FATAL);
							FacesContext.getCurrentInstance().addMessage("Erreur", msg);

							
							return;
						
					}
				       
				        
				    	  
					
					} 
				catch (ClientHandlerException e) {

					
		     		
		     		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement: problème d'accès au serveur!", ""));
		     		
		     		FacesMessage msg = new FacesMessage("Echec d'encaissement: problème d'appel au WS d'encaissement!", "");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		     		
		     		
		     		System.out.println("probleme de ClientHandlerException  ");
		     		
					e.printStackTrace();

				  }
				catch (Exception e) {

						
			     		
			     		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , veuiller contacter l'administrateur!", ""));
			     		
			     		FacesMessage msg = new FacesMessage("Echec d'encaissement , Erreur interne liée au niveau du WS d'encaissement!", "");
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			     		
			     		
			     		System.out.println("probleme de Exception  ");
			     		
						e.printStackTrace();

					  }
				

			

		}
    /*
	 * private static void fillReport(Map parameters, JRBeanArrayDataSource
	 * arrayDataSource) { try { final JasperReport report =
	 * loadReport("/tn/tunisiamall/utils/facture.jasper");
	 * 
	 * JasperPrint jasperPrint = JasperFillManager.fillReport(report,
	 * parameters, arrayDataSource);
	 * 
	 * JRPrintPreview printPreview = new JRPrintPreview(jasperPrint);
	 * 
	 * printPreview.show(); } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * private static JasperReport loadReport(final String fileName) throws
	 * IOException, JRException { try (InputStream in =
	 * loadReportAsStream(fileName)) { return (JasperReport)
	 * JRLoader.loadObject(in); } }
	 * 
	 * private static InputStream loadReportAsStream(final String fileName) {
	 * final InputStream report =
	 * CartHistoryViewController.class.getResourceAsStream(fileName); if (report
	 * == null) { throw new RuntimeException("Report not found: " + fileName); }
	 * return report; }
     */
}
