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
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.DetailsGarantieDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.dto.QuittanceImapyeeDTO;
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
@ManagedBean(name = "encaissementTermeBean")
@ViewScoped
public class EncaissementTermeBean implements Serializable {

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
	private IQuittanceDetailsDAO  iQuittanceDetailsDAO;
    
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
	
	@EJB
    private IOperationTransactionDAO iOperationTransactionDAO;



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
	  
	  Integer codeEDI = 0;
	  
	  private List<QuittanceImapyeeDTO> quittancesImapyeesList = new ArrayList<>();
	  
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
				lstproduit=prodconvDAO.getlstProduit_by_convention_for_qtc(c);
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
//				 adhesions = new ArrayList<Adhesion>(); 
//				 propositions = new ArrayList<Proposition>();
//			  
//			  if(utilisateur.getProfil().getCode().equals(PofileEnum.G.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNAA.name()) )
//		        {
//		            System.out.println("consultationBean fnction rechercher pour gestionnaire");
//
//		                adhesions = adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
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
//		            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
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
//		            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
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
//		            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
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

	                adhesions = adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
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
	            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
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
	            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
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
	            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient(
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
	
	public void printFDR(Adhesion adhesion) {
		
		
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
				parameters.put("star",  reportController.getLogoJasper());
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
		else if (adhesion.getPolice().getCodProdui().getLibelleProduit().equals("7ayya"))
		{
			
			
			//numquit=serviceAssistantVoiyagetService.getNumeroQuit();
		
			
			//Adhesion adhesion =adhesionToPrintNotise;
			String report;
			try {
				
					 report = reportController.getRealPath()	+ "Quit_hayaa.jasper";
					 
					 
					 Quittance quittance = iQuittanceDAO.searchquitByNAdhesion(adhesion.getNadhesion());
					 
					 DetailHayya detailHayya = hayyaServiceInter.find_detail_by_Adhesion(adhesion);
				
				Map<String, Object> parameters = new HashMap<String, Object>();
			
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				parameters.put("numquit", String.valueOf( quittance.getNumquit() ) );

				parameters.put("agence", utilisateur.getAgence().getCodeAgence());
				parameters.put("contrat",adhesion.getNumeroAdhesion());
				parameters.put("effet", df.format(adhesion.getDatDebut()));
				
				Calendar cal = Calendar.getInstance();
//				cal.setTime(adhesion.getDatFin());
//				cal.add(Calendar.DATE, -1);
//				Date dateBefore01Days = cal.getTime();
//				parameters.put("au", df.format(dateBefore01Days));
				
				parameters.put("echeance", df.format(adhesion.getDatDebut()));
				
				parameters.put("souscr", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_souscr", adhesion.getClient().getAdrClient());
				 
				parameters.put("assure", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_assure", adhesion.getClient().getAdrClient());
				 
				 
				
				//parameters.put("prime_tot", adhesion.getPrimetot());
				parameters.put("frais", new BigDecimal(0.000));
				parameters.put("fga", detailHayya.getFa());
				parameters.put("taxe", Double.parseDouble("0.000"));
				//parameters.put("prime_nette", detailHayya.getPnette());
				
				
				switch ((int) detailHayya.getFractHayya().getId_fract_hayya()) {
				case 1:
				    System.out.println("Annuel");
					cal.setTime(adhesion.getDatDebut());
					cal.add(Calendar.MONTH, 12);
					cal.add(Calendar.DATE, -1);
					Date dateBefore01Days = cal.getTime();
					parameters.put("au", df.format(dateBefore01Days));
					
					parameters.put("prime_tot", adhesion.getPrimetot());
					parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
				    break;
				  case 2:
				    System.out.println("Semestriel");
				    cal.setTime(adhesion.getDatDebut());
				    cal.add(Calendar.MONTH, 6);
				    cal.add(Calendar.DATE, -1);
				    Date dateBefore01Days2 = cal.getTime();
					parameters.put("au", df.format(dateBefore01Days2));
					
					parameters.put("prime_tot", detailHayya.getPnette().doubleValue()/2 + detailHayya.getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()/2));
				    break;
				  case 3:
				    System.out.println("Trimestriel");
				    cal.setTime(adhesion.getDatDebut());
				    cal.add(Calendar.MONTH, 3);
				    cal.add(Calendar.DATE, -1);
				    Date dateBefore01Days3 = cal.getTime();
					parameters.put("au", df.format(dateBefore01Days3));
					
					parameters.put("prime_tot", detailHayya.getPnette().doubleValue()/4 + detailHayya.getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()/4));
				    break;
				  case 4:
				    System.out.println("Mensuel");				   
				    cal.setTime(adhesion.getDatDebut());
				    cal.add(Calendar.MONTH, 1);
				    cal.add(Calendar.DATE, -1);
				    Date dateBefore01Days4 = cal.getTime();
					parameters.put("au", df.format(dateBefore01Days4));
					
					parameters.put("prime_tot",  detailHayya.getPnette().doubleValue()/12 + detailHayya.getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()/12));

				    break;
				  case 5:
				    System.out.println("PRIME UNIQUE");
					cal.setTime(adhesion.getDatFin());
					cal.add(Calendar.DATE, -1);
					Date dateBefore01Days5 = cal.getTime();
					parameters.put("au", df.format(dateBefore01Days5));
					
					parameters.put("prime_tot", adhesion.getPrimetot());
					parameters.put("prime_nette", detailHayya.getPnette());
				    break;
				  default:  
					 System.out.println("default"); 
					 break;
			}
			
				
				 
				
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

				adhesion.setImpr(true);
				adhesionDAO.edit(adhesion);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
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
        parameters.put("star",reportController.getLogoJasper());
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
		
	}
	
	
	
public void printQTC(Adhesion adhesion, QuittanceImapyeeDTO qtc) {
		
	
	
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
				parameters.put("star",  reportController.getLogoJasper());
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
				
					 report = reportController.getRealPath()	+ "Quit_hayaa.jasper";
					 
					 
					 
				
				Map<String, Object> parameters = new HashMap<String, Object>();
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
				
				parameters.put("numquit", String.valueOf( qtc.getNumero_quittance() ) );

				parameters.put("agence", qtc.getCode_agence());
				parameters.put("contrat",qtc.getNumero_contrat());
				
				
				if(qtc.getDate_du().contains("/"))/*(codeEDI != 200)*/ {
					
					parameters.put("effet", qtc.getDate_du());				
					parameters.put("echeance", qtc.getDate_echeance());
					parameters.put("au", qtc.getDate_au());
					
				}else {
					
					parameters.put("effet", dateFormat.format(sdf.parse(qtc.getDate_du())));				
					parameters.put("echeance", dateFormat.format(sdf.parse(qtc.getDate_echeance())));
					parameters.put("au", dateFormat.format(sdf.parse(qtc.getDate_au())));
				}
				
				
				
				parameters.put("souscr", qtc.getNom_assure()+" "+qtc.getPrenom_assure());
				parameters.put("adr_souscr", qtc.getAdresse_assure());
				 
				parameters.put("assure", qtc.getNom_assure()+" "+qtc.getPrenom_assure());
				parameters.put("adr_assure", qtc.getAdresse_assure());
				 
				 
				
				//parameters.put("prime_tot", adhesion.getPrimetot());
				parameters.put("frais", qtc.getFrais());
				parameters.put("fga", qtc.getFga());
				parameters.put("taxe", qtc.getTaxes().doubleValue());
				//parameters.put("prime_nette", detailHayya.getPnette());
				
				
			
					
					
					parameters.put("prime_tot", qtc.getPrime_totale().doubleValue());
					parameters.put("prime_nette", qtc.getPrime_nette());
				  
			 
			
				
				 
				
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
		 
		 if (adhesion.getPolice().getCodProdui().getCodeProduit().equalsIgnoreCase(ENUMCODEPRODUIT.AVENIRJENESSE.getCode()))
		{
			
			
			//numquit=serviceAssistantVoiyagetService.getNumeroQuit();
		
			
			//Adhesion adhesion =adhesionToPrintNotise;
			String report;
			try {
				
					 report = reportController.getRealPath()	+ "Quit_avj.jasper";
					 
					 
					 
				
				Map<String, Object> parameters = new HashMap<String, Object>();
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
				
				parameters.put("numquit", String.valueOf( qtc.getNumero_quittance() ) );

				parameters.put("agence", qtc.getCode_agence());
				parameters.put("contrat",qtc.getNumero_contrat());
				
				
				if(qtc.getDate_du().contains("/") /*codeEDI != 200*/) {
					
					parameters.put("effet", qtc.getDate_du());				
					parameters.put("echeance", qtc.getDate_echeance());
					parameters.put("au", qtc.getDate_au());
					
				}else {
					
					parameters.put("effet", dateFormat.format(sdf.parse(qtc.getDate_du())));				
					parameters.put("echeance", dateFormat.format(sdf.parse(qtc.getDate_echeance())));
					parameters.put("au", dateFormat.format(sdf.parse(qtc.getDate_au())));
				}

				
				
				
				parameters.put("souscr", qtc.getNom_assure()+" "+qtc.getPrenom_assure());
				parameters.put("adr_souscr", qtc.getAdresse_assure());
				 
				parameters.put("assure", qtc.getNom_assure()+" "+qtc.getPrenom_assure());
				parameters.put("adr_assure", qtc.getAdresse_assure());
				 
				 
				
				//parameters.put("prime_tot", adhesion.getPrimetot());
				parameters.put("frais", qtc.getFrais());
				parameters.put("fga", qtc.getFga());
				parameters.put("taxe", qtc.getTaxes().doubleValue());
				//parameters.put("prime_nette", detailHayya.getPnette());
				
				
			
					
					
					parameters.put("prime_tot", qtc.getPrime_totale().doubleValue());
					parameters.put("prime_nette", qtc.getPrime_nette());
				  
			 
			
				
				 
				
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
        parameters.put("star",reportController.getLogoJasper());
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

	public void print(Adhesion adhesion) {
		System.out.println("**********************************print");
		if (adhesion.getPolice().getCodProdui().getLibelleProduit().equals("TD"))
		{
        try {
           
            String report = reportController.getRealPath() + "adhesion.jasper";
            Map<String, Object> parameters = new HashMap<String, Object>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            parameters.put("star",reportController.getLogoJasper());
            parameters.put("imagesociete",adhesion.getUtilisateur().getAgence().getSociete().getCodSociet()+".png");
            parameters.put("numeroPolice", adhesion.getPolice().getNPolice());
            parameters.put("contractant", adhesion.getPolice().getConvention()
                    .getSociete().getRaisonSoc());
            parameters.put("adresse", adhesion.getPolice().getConvention()
                    .getSociete().getAdresse());
            parameters.put("clientName", adhesion.getClient().getNomClient()
                    + " " + adhesion.getClient().getPrenomClient());
            parameters.put("clientBirthday",
                    sdf.format(adhesion.getClient().getDatNaiss()));
            parameters.put("clientCIN",
                    String.valueOf(adhesion.getClient().getIdentifiant()));
            parameters.put("dateEffet", sdf.format(adhesion.getDatDebut()));
            parameters.put("dateFin", sdf.format(adhesion.getDatFin()));
            String duree="";
            String[]tab=String.valueOf(adhesion.getDuree()).split("\\.");
   		
            if(!tab[1].equals("0")){
            	 duree=tab[0]+"(a)"+","+ tab[1]+ "(m)";
            } else{
            	 duree=tab[0]+"(année)";
            }
           	
           parameters.put("duree", duree);
            parameters.put("ageLimit", adhesion.getPolice().getAgelimit()
                    .toString());

            String garBase = "";
            String garExclusion = "";
            String garOpt = "";
            String optExclusion = "";

            Police police = adhesion.getPolice();
            List<GarantieVie> list = police.getGarantieVies();

            for (GarantieVie garantieVie : list) {

                if (garantieVie.getGarBase() != null
                        && !garantieVie.getGarBase().isEmpty()) {
                    garBase +=  garantieVie.getGarBase() + "\n";
                }
                if (garantieVie.getGarExclusion() != null
                        && !garantieVie.getGarExclusion().isEmpty()) {
                    garExclusion += garantieVie.getGarExclusion() + "\n";
                }
                if (garantieVie.getGarOpt() != null
                        && !garantieVie.getGarOpt().isEmpty()) {
                    garOpt +=  garantieVie.getGarOpt() + "\n";
                }
                if (garantieVie.getOptExclusion() != null
                        && !garantieVie.getOptExclusion().isEmpty()) {
                    optExclusion +=  garantieVie.getOptExclusion() + "\n";
                }
            }

            parameters.put("garBase", garBase.trim());
            parameters.put("garExclusion", garExclusion.trim());
            parameters.put("garOpt", garOpt.trim());
            parameters.put("optExclusion", optExclusion.trim());

            parameters.put("capital", String.valueOf(adhesion.getCapital()));
            parameters.put("mtPrime", String.valueOf(adhesion.getMtPrime()));

            JasperPrint print = JasperFillManager.fillReport(report,
                    parameters, new JREmptyDataSource());
            byte[] bytes = JasperExportManager.exportReportToPdf(print);

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseContentLength(bytes.length);
            ec.setResponseHeader("Content-Disposition",
                    "inline; filename=\""
                    + adhesion.getPolice().getNPolice() + "rapport.pdf"
                    + "\"");

            OutputStream output = ec.getResponseOutputStream();
            output.write(bytes);
            fc.responseComplete();

//            adhesion.setImpr(true);
//            adhesionDAO.edit(adhesion);
        } catch (Exception e) {
            e.printStackTrace();
        }
		}
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("Incendie"))
		{
			try {
                
	            String report = reportController.getRealPath() + "notice _inc.jasper";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            parameters.put("numeroPolice", adhesion.getPolice().getNPolice());
	            parameters.put("contractant", adhesion.getPolice().getConvention()
	                    .getSociete().getRaisonSoc());
	            parameters.put("adresse", adhesion.getPolice().getConvention()
	                    .getSociete().getAdresse());
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
	            parameters.put("dateEffet", sdf.format(adhesion.getDatDebut()));
	            parameters.put("dateFin", sdf.format(adhesion.getDatFin()));
	            parameters.put("nadhesion", adhesion.getNadhesion());
	            parameters.put("imagesociete",adhesion.getUtilisateur().getAgence().getSociete().getCodSociet()+".png");
	            parameters.put("star",reportController.getLogoJasper());
	            parameters.put("capital", String.valueOf(adhesion.getCapital()));
	            parameters.put("mtPrime", String.valueOf(adhesion.getMtPrime()));
	            parameters.put("taxe", String.valueOf(adhesion.getTaxe()));
	            parameters.put("primtot", String.valueOf(adhesion.getPrimetot()));
	            parameters.put("villeagence", adhesion.getUtilisateur().getAgence().getAdresse());
	            parameters.put("codagc", adhesion.getUtilisateur().getAgence().getCodeAgence());
	            String duree="";
	            String[]tab=String.valueOf(adhesion.getDuree()).split("\\.");
	   		
	            if(!tab[1].equals("0")){
	            	 duree=tab[0]+"(a)"+","+ tab[1]+ "(m)";
	            } else{
	            	 duree=tab[0]+"(année)";
	            }
	           	
	            
	            
	            parameters.put("duree", duree);
	            JasperPrint print = JasperFillManager.fillReport(report,
	                    parameters, new JREmptyDataSource());
	            byte[] bytes = JasperExportManager.exportReportToPdf(print);

	            FacesContext fc = FacesContext.getCurrentInstance();
	            ExternalContext ec = fc.getExternalContext();
	            ec.responseReset();
	            ec.setResponseContentType("application/pdf");
	            ec.setResponseContentLength(bytes.length);
	            ec.setResponseHeader("Content-Disposition",
	                    "inline; filename=notice _inc.pdf");

	            OutputStream output = ec.getResponseOutputStream();
	            output.write(bytes);
	            fc.responseComplete();

//	            adhesion.setImpr(true);
//	            adhesionDAO.edit(adhesion);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("Assistance voyage"))
		{
			try {
	            String report = reportController.getRealPath() + "notice_voyage.jasper";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            parameters.put("numplc", adhesion.getPolice().getNPolice());
	            parameters.put("Souscripteur", adhesion.getClient().getNomClient().toUpperCase()+" "+adhesion.getClient().getPrenomClient().toUpperCase());
	            parameters.put("adrsouscripteur", adhesion.getClient().getAdrClient());
	            parameters.put("nomclt", adhesion.getClient().getNomClient().toUpperCase() );
	            parameters.put("prenomclt", adhesion.getClient().getPrenomClient().toUpperCase());
	            parameters.put("cinclt",adhesion.getClient().getIdentifiant());
	            parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
	            parameters.put("nadhesion", adhesion.getNadhesion());
	            parameters.put("adrclt", adhesion.getClient().getAdrClient());
	            parameters.put("vilclt", adhesion.getClient().getLocalite());
	            parameters.put("cpclt", adhesion.getClient().getCodeP());
	            parameters.put("gouvclt", adhesion.getClient().getGouver());
	            parameters.put("telclt", adhesion.getClient().getTel());
	            parameters.put("duree", String.valueOf(adhesion.getDuree()));
	            parameters.put("mailclt", adhesion.getClient().getMail());
	            parameters.put("nature_abonnement", adhesion.getTypeAbonnement().getValTypeAbonnement());
	            parameters.put("dateEffet", sdf.format(adhesion.getDatDebut()));
	            parameters.put("datefin",  sdf.format(adhesion.getDatFin()));
	            parameters.put("mtPrime", String.valueOf(adhesion.getMtPrime()));
	            parameters.put("surprime", "0");
	            parameters.put("primetot", String.valueOf(adhesion.getMtPrime()));
	            parameters.put("imagesociete",utilisateur.getAgence().getSociete().getCodSociet()+".png");
	            parameters.put("villeagence", utilisateur.getAgence().getLibelle());
	            parameters.put("personprv", adhesion.getPersonnePrv());
	            parameters.put("telpersonprv", adhesion.getTelphone());
	            parameters.put("destination", adhesion.getDestination());
	            parameters.put("star",reportController.getLogoJasper());
	            parameters.put("typedoc", adhesion.getClient().getTypeDoc().toUpperCase());
	            parameters.put("ncompte", adhesion.getClient().getNcompte());
	            if(adhesion.getCouverture()!=null)
	            {
	            	parameters.put("couverture", adhesion.getCouverture().intValue()+"");
	            }
	            String nom_assure="";
	            String prenom_assure="";
	            String datnais_assure="";
	            for (Adherent  adherent : adhesion.getAdherents()) {
	            	nom_assure+=adherent.getNomAdherent()+ "\n";
	            	prenom_assure+=adherent.getPrenomAdherent()+ "\n";
	            	datnais_assure+= sdf.format(adherent.getDatNaiss())+ "\n";
	          }
	            
	            parameters.put("nom_assure", nom_assure.trim());
	            parameters.put("prenom_assure", prenom_assure.trim());
	            parameters.put("datnais_assure", datnais_assure.trim());
	            JasperPrint print = JasperFillManager.fillReport(report,
	                    parameters,new JREmptyDataSource());
	            byte[] bytes = JasperExportManager.exportReportToPdf(print);

	            FacesContext fc = FacesContext.getCurrentInstance();
	            ExternalContext ec = fc.getExternalContext();
	            ec.responseReset();
	            ec.setResponseContentType("application/pdf");
	            ec.setResponseContentLength(bytes.length);
	            ec.setResponseHeader("Content-Disposition", "inline; filename=rapport.pdf");

	            OutputStream output = ec.getResponseOutputStream();
	            output.write(bytes);
	            fc.responseComplete();

//	            adhesion.setImpr(true);
//	            adhesionDAO.edit(adhesion);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		System.out.println("adhesion.getPolice().getCodProdui().getLibelleProduit()"+adhesion.getPolice().getCodProdui().getLibelleProduit());
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("Avenirjeunesse"))
		{
			try {
				System.out.println("test impression");
	            String report = reportController.getRealPath() + "avenir_jeunesse.jasper";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    		parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
	    		parameters.put("star",reportController.getLogoJasper());
	    		parameters.put("numplc", adhesion.getNadhesion());
 	    		parameters.put("Souscripteur", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
 	    		parameters.put("Adrsouscripteur", adhesion.getClient().getNumAdr()+" "+adhesion.getClient().getTypeVoie().getLibelle()+" "+adhesion.getClient().getAdrClient()+" "+adhesion.getClient().getLocalite()+" "+adhesion.getClient().getGouver()+" "+adhesion.getClient().getCodeP());
 	    		parameters.put("Cinsouscripteur", adhesion.getClient().getIdentifiant());
	    		/**************/
 	    		parameters.put("Assure", adhesion.getAdherents().get(0).getNomAdherent()+" "+adhesion.getAdherents().get(0).getPrenomAdherent());
 	    		parameters.put("Adrassure", adhesion.getAdherents().get(0).getNumAdr()+" "+adhesion.getAdherents().get(0).getTypeVoie().getLibelle()+" "+adhesion.getAdherents().get(0).getAdresse()+" "+adhesion.getAdherents().get(0).getLocalite()+" "+adhesion.getAdherents().get(0).getGouver()+" "+adhesion.getAdherents().get(0).getCodePostal());
 	    		parameters.put("Cinassure", adhesion.getAdherents().get(0).getIdentifiant());
 	    		parameters.put("Datnaiassure", df.format(adhesion.getAdherents().get(0).getDatNaiss()));
 	    		parameters.put("Prfassure", adhesion.getAdherents().get(0).getProfession().getLibelleProfession());
 	    		parameters.put("Sexassure", adhesion.getAdherents().get(0).getSexe());
 	    		parameters.put("Telassure", adhesion.getAdherents().get(0).getTel());

 	    		parameters.put("Beneficiaire", adhesion.getBeneficiaires().get(0).getNom()+" "+adhesion.getBeneficiaires().get(0).getPrenom());
 	    		parameters.put("Adrbenef", adhesion.getBeneficiaires().get(0).getAdresse());
 	    		parameters.put("Datnaisbenef", df.format(adhesion.getBeneficiaires().get(0).getDateNaiss()));
 	    		parameters.put("Sexbenef", adhesion.getBeneficiaires().get(0).getSexe());
	    		/***********/
 	    		parameters.put("imagesociete",utilisateur.getAgence().getSociete().getCodSociet()+".png");
 	    		parameters.put("Datedeff", df.format(adhesion.getDatDebut()));
 	    		parameters.put("Datech", df.format(adhesion.getDatFin()));
 	    		parameters.put("Duree", String.valueOf(adhesion.getDuree()));
 	    		parameters.put("Mntbourse", (new DecimalFormat("#0.000")).format(adhesion.getDetailAvj().getMtBourse()) + " TND");
 	    		parameters.put("Agesevice", adhesion.getDetailAvj().getAgeServiceBourse().toString());
 	    		parameters.put("Durservice", adhesion.getDetailAvj().getDureeBourse().toString());
 	    		parameters.put("Dateservice", df.format(adhesion.getDetailAvj().getDatePremierBourse()));
 	    		parameters.put("FraisinscON", "NON");
 	    		parameters.put("RenteON", "NON");
 	    		double totEpargne=0;
 	    		double totDeces=0;
 	    		for (GarantieAvj garantieAvj : adhesion.getGarantieAvjs())
 	    		{
 	    			if(garantieAvj.getGarantie().getIdGarantie()==1)
 	    			{
 	    	    		parameters.put("PrmEpargneb", (new DecimalFormat("#0.000")).format(garantieAvj.getMtPrime()) + " TND");
 	    	    		totEpargne+=garantieAvj.getMtPrime().doubleValue();
 	    			}
 	    			if(garantieAvj.getGarantie().getIdGarantie()==2)
 	    			{
 	    	    		parameters.put("PrmDecb", (new DecimalFormat("#0.000")).format(garantieAvj.getMtPrime()) + " TND");
 	    	    		totDeces+=garantieAvj.getMtPrime().doubleValue();
 	    			}
 	    			if(garantieAvj.getGarantie().getIdGarantie()==3)
 	    			{
 		    			parameters.put("FraisinscON", "OUI");
 			    		parameters.put("Mtfrais", (new DecimalFormat("#0.000")).format(garantieAvj.getMontant()) + " TND");
 			    		parameters.put("Mtpepargnfrais", (new DecimalFormat("#0.000")).format(garantieAvj.getMtPrime()) + " TND");
 	    	    		totEpargne+=garantieAvj.getMtPrime().doubleValue();
 
 	    			}
 	    			if(garantieAvj.getGarantie().getIdGarantie()==4)
 	    			{
 	    	    		parameters.put("Mtpdecfrais", (new DecimalFormat("#0.000")).format(garantieAvj.getMtPrime()) + " TND");
 	    	    		totDeces+=garantieAvj.getMtPrime().doubleValue();
 
 	    			}
 	    			if(garantieAvj.getGarantie().getIdGarantie()==5)
 	    			{
 		    			parameters.put("RenteON", "OUI");
 		    			parameters.put("Mtrente", (new DecimalFormat("#0.000")).format(garantieAvj.getMontant()) + " TND");
 			    		parameters.put("Mtrentedeces", (new DecimalFormat("#0.000")).format(garantieAvj.getMtPrime()) + " TND");
 	    	    		totDeces+=garantieAvj.getMtPrime().doubleValue();
 
 	    			}
 	    		}
 	    		
 	    		
 	    		
 	    		
 	    		parameters.put("Prmnetepargne", (new DecimalFormat("#0.000")).format(totEpargne) + " TND");
 	    		parameters.put("Prmnetdeces", (new DecimalFormat("#0.000")).format(totDeces) + " TND");
 	    		parameters.put("Totprmnet", (new DecimalFormat("#0.000")).format(adhesion.getMtPrime()) + " TND");
 	    		parameters.put("frais", (new DecimalFormat("#0.000")).format(adhesion.getCout())+ " TND");
 	    		parameters.put("Prmtot", (new DecimalFormat("#0.000")).format(adhesion.getPrimetot()) + " TND");
 
 	    		parameters.put("Fractionnement", adhesion.getFraPolice().equals("M")?"Mensuel":adhesion.getFraPolice().equals("T")?"Trimestriel":adhesion.getFraPolice().equals("S")?"Semestriel":"Annuel");
 	    		parameters.put("tauxevol", String.valueOf(adhesion.getDetailAvj().getIdTauxCroissancePrime()));
 	    		parameters.put("DerEch", df.format(adhesion.getDatFin()));
 	    		parameters.put("taux", String.valueOf(adhesion.getTauxInter()));
 	    		parameters.put("ville_agence", utilisateur.getAgence().getAdresse());
 	    			            
	            JasperPrint print = JasperFillManager.fillReport(report,  parameters,new JREmptyDataSource());
	            byte[] bytes = JasperExportManager.exportReportToPdf(print);

	            FacesContext fc = FacesContext.getCurrentInstance();
	            ExternalContext ec = fc.getExternalContext();
	            ec.responseReset();
	            ec.setResponseContentType("application/pdf");
	            ec.setResponseContentLength(bytes.length);
	            ec.setResponseHeader("Content-Disposition","inline; filename=Notice_avj.pdf");
	            OutputStream output = ec.getResponseOutputStream();
	            output.write(bytes);
	            fc.responseComplete();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("MultiRisque Habitation"))
		{
			try {
	            String report = reportController.getRealPath() + "noticemrhp1.jasper";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	  
	            
	            
	            
	            
	            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				parameters.put("nadhesion",  adhesion.getNadhesion());
				parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
				//parameters.put("contractType", contractType);
				//System.out.println("contractType" + contractType );
				 if (adhesion.getDatDebut() != null) {
			            parameters.put("effDate", df.format(adhesion.getDatDebut()));
			        } else {
			            parameters.put("effDate", " ");
			        }

			       // if (lastEcheanceDate != null) {
			         //   parameters.put("echeanceDate", df.format(lastEcheanceDate));
			        //} else {
			        //    parameters.put("echeanceDate", "N/A");
			        //}
				 
				  Date echeanceDate;
				  Date extinction;
				 if (adhesion.getDetailMrh().getTypecontrat().equals("Ferme")) { // Mouna: Contrat ferme
						Calendar c = Calendar.getInstance();
						c.setTime(adhesion.getDatDebut());
						c.add(Calendar.YEAR, 1);
						c.add(Calendar.DAY_OF_MONTH, -1);
						extinction = c.getTime();
						echeanceDate = null;
						System.out.println("date extinction= "+extinction);
					} else {  // Mouna: Contrat renouvelable
						Calendar c = Calendar.getInstance();
						c.setTime(adhesion.getDatDebut());
						c.add(Calendar.YEAR, 1);
						echeanceDate = c.getTime();
						extinction = null;
						//duration ="Annuelle";
						System.out.println("date echeanceDate= "+echeanceDate);
					}
				 

			        if ((extinction != null) &&  adhesion.getDetailMrh().getTypecontrat().equals("Ferme") ){
			            parameters.put("expDate", df.format(extinction));
			        } else {
			            parameters.put("expDate", " ");
			        }
			        
			        if ((echeanceDate != null) && adhesion.getDetailMrh().getTypecontrat().equals("Renouvelable")){
			            parameters.put("echeanceDate", df.format(echeanceDate));
			            System.out.println("echeanceDate" + echeanceDate );
			        } else {
			            parameters.put("echeanceDate", " ");
			        }
			        
			        parameters.put("clientName", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
			        parameters.put("adrclt", adhesion.getClient().getAdrClient());
			        parameters.put("cpclt", adhesion.getClient().getCodeP());
			        parameters.put("vilclt", adhesion.getClient().getLocalite());
			        parameters.put("gouvclt",adhesion.getClient().getGouver());
			        //paramaters.put("faxclt",client.getfaxclt);
			        parameters.put("mailclt",adhesion.getClient().getMail());
			        parameters.put("telclt",adhesion.getClient().getTel());
			        
			        parameters.put("quality", adhesion.getDetailMrh().getQualite().getQualite() );
			        parameters.put("sitrisq", adhesion.getAdresse());
			        parameters.put("cprisq", adhesion.getCodeP());
			        parameters.put("vilrisq", adhesion.getLocalite());
			        parameters.put("city", adhesion.getGouver());///city
			        parameters.put("riskNature", adhesion.getDetailMrh().getNatureRisq().getLibelleNature()  );
			        parameters.put("nbrPieceLess", String.valueOf(adhesion.getDetailMrh().getNbrpieceless()  ));
			        parameters.put("nbrPieceMore", String.valueOf(adhesion.getDetailMrh().getNbrpiecemore() ));
			        parameters.put("area", String.valueOf(adhesion.getDetailMrh().getArea()));
			        parameters.put("furniturePrice", (new DecimalFormat("#0.000")).format(adhesion.getDetailMrh().getFurnitureprice() ) + " TND");
			        
			        parameters.put("duration",adhesion.getDetailMrh().getDuree() );
			     
			        
			        
			       // parameters.put("primeNette", (new DecimalFormat("#0.000")).format(primeNette) + " TND");
			       // parameters.put("tax", (new DecimalFormat("#0.000")).format(tax) + " TND");
			       // parameters.put("cost", (new DecimalFormat("#0.000")).format(cost) + " TND");
			       // parameters.put("primeTotal", (new DecimalFormat("#0.000")).format(primeTotal) + " TND");

			        parameters.put("baseGar", "pack.getBaseGar()");
			        System.out.println("baseGar" + "pack.getBaseGar()");

			        parameters.put("opGar", "pack.getOpGarString()");
			        System.out.println("optGar" + "pack.getOpGarString()");
			        
//			        TarifMrh tarif805 =  getParamPrices();
//			         ArrayList<String> baseGar;
//			         ArrayList<OpGar> opGar;
//			        for(GarantieMrh garnti : adhesion.getGarantieMrh()){
//			        	
//			        	garnti.get
//			        	
//			        }
			        
			        
			        
			        //*******************************page 2*******************
			        parameters.put("primeNette", (new DecimalFormat("#0.000")).format(adhesion.getMtPrime() ) + " TND");
			        parameters.put("cost", (new DecimalFormat("#0.000")).format(adhesion.getCout() ) + " TND");
			        parameters.put("tax", (new DecimalFormat("#0.000")).format(adhesion.getTaxe() ) + " TND");
			        parameters.put("primeTotal", (new DecimalFormat("#0.000")).format(adhesion.getPrimetot() ) + " TND");
			        if(adhesion.getDetailMrh().getRachIn().equals("O")) parameters.put("rach", "1");  
			        
			        else
			        parameters.put("rach", "0");
			        
			        if (adhesion.getDetailMrh().getTypecontrat().equals("Renouvelable")) parameters.put("renouv", "1");
			        else
			        parameters.put("renouv", "0");
			        
			        if (((adhesion.getDetailMrh().getTypecontrat().equals("Renouvelable"))&& adhesion.getDetailMrh().getDuree() == "Annuelle" ) ||   ((adhesion.getDetailMrh().getTypecontrat().equals("Ferme"))&& adhesion.getDetailMrh().getDuree() == "Prorata" )) 
			        parameters.put("reval", "1");
			        else
			        parameters.put("reval", "0");
			        
			        
			        boolean AS = false;
			        for(GarantieMrh garnti : adhesion.getGarantieMrh()){
			        	
			        	if (garnti.getCodGar().equals("15")){
			        		AS = true;
			        		break;
			        	}
			        	
			        }

			        if (AS)   parameters.put("as", "1");
			        else
			        parameters.put("as", "0");	

	            
	            
	            
	            JasperPrint print = JasperFillManager.fillReport(report,
	                    parameters,new JREmptyDataSource());
	            byte[] bytes = JasperExportManager.exportReportToPdf(print);

	            FacesContext fc = FacesContext.getCurrentInstance();
	            ExternalContext ec = fc.getExternalContext();
	            ec.responseReset();
	            ec.setResponseContentType("application/pdf");
	            ec.setResponseContentLength(bytes.length);
	            ec.setResponseHeader("Content-Disposition", "inline; filename=rapport.pdf");

	            OutputStream output = ec.getResponseOutputStream();
	            output.write(bytes);
	            fc.responseComplete();

//	            adhesion.setImpr(true);
//	            adhesionDAO.edit(adhesion);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("TLSAssistV"))
		{
			
			
			//Adhesion adhesion=adhesionToPrintNotise;
			String report;
			try {
				if(adhesion.getPack().getLibelle().equals("Business"))
				{
					 report = reportController.getRealPath()	+ "TLSbisness.jasper";
				}
				else{
					 report = reportController.getRealPath()	+ "TLSvisa.jasper";
				}
				Map<String, Object> parameters = new HashMap<String, Object>();
				DecimalFormat formatter = new DecimalFormat("#0.000");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
				parameters.put("nadhesion", adhesion.getNadhesion());
				parameters.put("libelle_pack", adhesion.getPack().getLibelle());
				parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
				parameters.put("libelle_societe", utilisateur.getAgence().getSociete().getRaisonSoc());
				parameters.put("imagesociete", utilisateur.getAgence().getSociete().getCodSociet()+ ".png");
				parameters.put("star",  reportController.getLogoJasper());
				parameters.put("vilagc", utilisateur.getAgence().getLibelle());
				parameters.put("Datdeff", sdf.format(adhesion.getDatDebut()));
				parameters.put("Datexp", sdf.format(adhesion.getDatFin()));
				parameters.put("duree", String.valueOf((int)adhesion.getDuree()));
				parameters.put("nomclt", adhesion.getClient().getNomClient() + " " + adhesion.getClient().getPrenomClient());
				parameters.put("adrlt", adhesion.getClient().getNumAdr()+" "+adhesion.getClient().getTypeVoie().getLibelle()+" "+adhesion.getClient().getAdrClient());
				parameters.put("codpclt", adhesion.getClient().getCodeP());
				parameters.put("vilclt", adhesion.getClient().getLocalite());
				parameters.put("gouvclt", adhesion.getClient().getGouver());
				parameters.put("identifiant", adhesion.getClient().getIdentifiant());
				parameters.put("prmnet", formatter.format(adhesion.getMtPrime()));
				parameters.put("frais", formatter.format(adhesion.getCout()));
				parameters.put("prmtot", formatter.format(adhesion.getPrimetot()));
				parameters.put("SUBREPORT_DIR", reportController.getRealPath());
				
				for (Adherent adherent : adhesion.getAdherents()) {

					parameters.put("nomass", adherent.getNomAdherent()+" "+adherent.getPrenomAdherent());
					parameters.put("datnaisass", sdf.format(adherent.getDatNaiss()));
					if(adherent.getNumAdr()==null) adherent.setNumAdr(new BigDecimal(0));
					if(adherent.getTypeVoie().getLibelle()==null) adherent.getTypeVoie().setLibelle(" ");
					if(adherent.getAdresse()==null) adherent.setAdresse(" ");
					parameters.put("adrass", adherent.getNumAdr()+" "+adherent.getTypeVoie().getLibelle()+" "+adherent.getAdresse());
					if(adherent.getCodePostal()!=null)
					parameters.put("codpass", adherent.getCodePostal());
					else parameters.put("codpass", " ");
					if(adherent.getLocalite()!=null)
					parameters.put("vilass", adherent.getLocalite());
					else parameters.put("vilass", " ");
					if(adherent.getIdentifiant()!=null)
					parameters.put("identifiantass", adherent.getIdentifiant());
					else parameters.put("identifiantass", " ");
					if(adherent.getProfession()==null)
					
						parameters.put("prfass", " ");
					
					else
					parameters.put("prfass", adherent.getProfession().getLibelleProfession());
					parameters.put("sexass", adherent.getSexe());
				}


				JasperPrint print = JasperFillManager.fillReport(report,parameters, new JREmptyDataSource());
				byte[] bytes = JasperExportManager.exportReportToPdf(print);

				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				ec.responseReset();
				ec.setResponseContentType("application/pdf");
				ec.setResponseContentLength(bytes.length);
				ec.setResponseHeader("Content-Disposition", "inline; filename=contrat.pdf");

				OutputStream output = ec.getResponseOutputStream();
				output.write(bytes);
				fc.responseComplete();

				adhesion.setImpr(true);
				adhesionDAO.edit(adhesion);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("7ayya"))
		{
			try {
	            String report = reportController.getRealPath() + "Contrat_hayaa_v2.jasper";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	            
	            
	            DetailHayya detailHayya = hayyaServiceInter.find_detail_by_Adhesion(adhesion);
	            
	            List<DetailsGarantieHayya> detailsGarantieHayyaList =  hayyaServiceInter.find_detail_garatie_by_Adhesion(adhesion);
	            
	            parameters.put("agence", utilisateur.getAgence().getCodeAgence());
				parameters.put("contrat",adhesion.getNadhesion());
				parameters.put("effet", df.format(adhesion.getDatDebut()));
				parameters.put("duree", Integer.toString((int)adhesion.getDuree())+" ans");
				parameters.put("echeance", df.format(adhesion.getDatFin()));
				parameters.put("capital", detailsGarantieHayyaList.get(0).getCapital());
				parameters.put("souscr", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_souscr", adhesion.getClient().getAdrClient());
				parameters.put("id_souscr", adhesion.getClient().getIdClient());
				parameters.put("assure", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_assure", adhesion.getClient().getAdrClient());
				parameters.put("id_assure", adhesion.getClient().getIdClient());
				parameters.put("naiss_assure", df.format(adhesion.getClient().getDatNaiss()));
				
				
				
				if(adhesion.getClient().getSexe().equalsIgnoreCase("M"))
					parameters.put("sexe_assure", "Homme");
				else
					parameters.put("sexe_assure", "Femme");
				
				parameters.put("prf_assure", adhesion.getClient().getProfession().getLibelleProfession());
				parameters.put("tel_assure", adhesion.getClient().getTel());
				parameters.put("nom_benef", adhesion.getClient().getNomClient());
				parameters.put("cin_benef", adhesion.getClient().getIdentifiant());
				parameters.put("parente", null);
				parameters.put("poucent_cap", null);
				
				parameters.put("nom_benef_d", null);
				parameters.put("cin_benef_d", null);
				parameters.put("parente_d", null);
				parameters.put("poucent_cap_d", null);
				
				//parameters.put("prime_tot", adhesion.getPrimetot());
				parameters.put("frais", new BigDecimal(0.000));
				parameters.put("fga", detailHayya.getFa());
				//parameters.put("prime_nette", detailHayya.getPnette());
				
				
				
				switch((int)detailHayya.getFractHayya().getId_fract_hayya()){
				   
			       case 1: 
			    	   parameters.put("prime_tot", adhesion.getPrimetot());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
					   
			    	   parameters.put("annuel", "X");
			           break;
			   
			       case 2:

						parameters.put("prime_tot", detailHayya.getPnette().doubleValue()/2 + detailHayya.getFa().doubleValue());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()/2));
					   
			    	   parameters.put("semestriel", "X");
			           break;
			   
			       case 3:
			    	   parameters.put("prime_tot", detailHayya.getPnette().doubleValue()/4 + detailHayya.getFa().doubleValue());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()/4));
					    
			    	   parameters.put("trimestriel", "X");
			           break;
			           
			       case 4:
			    	   parameters.put("prime_tot",  detailHayya.getPnette().doubleValue()/12 + detailHayya.getFa().doubleValue());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()/12));

			    	   parameters.put("mensuel", "X");
			           break;
			
			       default:
			    	   parameters.put("prime_tot", adhesion.getPrimetot());
						parameters.put("prime_nette", detailHayya.getPnette());
						
			    	   parameters.put("unique", "X");
			           break;
			   }
				
				parameters.put("evol_p", "0%");
				parameters.put("tx_benef", "80%");
				parameters.put("tx_interef_gar", "4%");
				
				parameters
				.put("star",  reportController.getLogoJasper());
				
				parameters.put("imagesociete", utilisateur.getAgence().getSociete()
						.getCodSociet() + ".png");
				
				
		        List<DetailsEvolutionGarantieHayya> evs = hayyaServiceInter.find_details_ev_gar_by_Adhesion(adhesion);
		        List<DetailsEvolutionGarantie> orders = new ArrayList<DetailsEvolutionGarantie>();
		        
		        for(DetailsEvolutionGarantieHayya ev : evs) {
		        	
		        	DetailsEvolutionGarantie eg = new DetailsEvolutionGarantie();
		        	eg.setAnnee(new BigDecimal(ev.getAnnee()));
		        	eg.setPrime_comm(ev.getPrime_comm());
		        	eg.setPrime_capit(ev.getPrime_capit());
		        	eg.setCapital_tmg(ev.getCapital_tmg());
		        	eg.setCapital_taux_reel(ev.getCapital_taux_reel());
		        	eg.setBenefices_realises(ev.getBenefices_realises());
		        	eg.setParticipation_benef(ev.getParticipation_benef());
		        	eg.setEpargne_fin_annee(ev.getEpargne_fin_annee());
		        	eg.setGain_financier(ev.getGain_financier());
		        	eg.setGain_fisc_annuel(ev.getGain_fisc_annuel());
		        	eg.setValeur_rachat(ev.getValeur_rachat());
		        	eg.setCumul_vers(ev.getCumul_vers());
		        	
		        	orders.add(eg);
		        	
		        }

		        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(orders);

		        parameters.put("datasource", ds);
		        
		        
		        List<Person> personnes = new ArrayList<Person>();
		        
		        System.out.println("vie ");
		        
		        for(BenefHayyaVie benefHayyaVie: hayyaServiceInter.find_benef_vie_by_Adhesion(adhesion)) {
		        	Person assure = new Person();
		        	assure.setNom(benefHayyaVie.getNom());
		        	assure.setPrenom(benefHayyaVie.getPrenom());
		        	assure.setCin(benefHayyaVie.getCin());
		        	assure.setLien(benefHayyaVie.getLien());
		        	assure.setQuote(benefHayyaVie.getQuote());
		        	
		        	personnes.add(assure);
		        }
		        
		         
		      
		        
		        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(personnes);
		        
		        parameters.put("datasvie", beanColDataSource);
		        
		        
		        
		        List<Person> personnes_deces = new ArrayList<Person>();
		        
		        System.out.println("deces " );
		        
		        
		        for(BenefHayyaDeces benefHayyaDeces: hayyaServiceInter.find_benef_deces_by_Adhesion(adhesion)) {
		        	Person assure = new Person();
		        	assure.setNom(benefHayyaDeces.getNom());
		        	assure.setPrenom(benefHayyaDeces.getPrenom());
		        	assure.setCin(benefHayyaDeces.getCin());
		        	assure.setLien(benefHayyaDeces.getLien());
		        	assure.setQuote(benefHayyaDeces.getQuote());
		        	
		        	personnes_deces.add(assure);
		        }
		        
		         
			
		        
		        JRBeanCollectionDataSource beanColDataSourceDeces = new JRBeanCollectionDataSource(personnes_deces);
		        
		        parameters.put("datasdeces", beanColDataSourceDeces);
	            
	            
	             
	            JasperPrint print = JasperFillManager.fillReport(report,
	                    parameters,new JREmptyDataSource());
	            byte[] bytes = JasperExportManager.exportReportToPdf(print);

	            FacesContext fc = FacesContext.getCurrentInstance();
	            ExternalContext ec = fc.getExternalContext();
	            ec.responseReset();
	            ec.setResponseContentType("application/pdf");
	            ec.setResponseContentLength(bytes.length);
	            ec.setResponseHeader("Content-Disposition", "inline; filename=rapport.pdf");

	            OutputStream output = ec.getResponseOutputStream();
	            output.write(bytes);
	            fc.responseComplete();

//	            adhesion.setImpr(true);
//	            adhesionDAO.edit(adhesion);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
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
		   
		    if (toggleEvent.getVisibility() == Visibility.VISIBLE) {
		        System.out.println("visible "+ ((Adhesion)toggleEvent.getData()).getNadhesion());
		        String num_ad = ((Adhesion)toggleEvent.getData()).getNadhesion();
		        String num_police = ((Adhesion)toggleEvent.getData()).getPolice().getNPolice();
		        System.out.println("num_police "+num_police);
		        //String num_ad ="68521000003";
		        
		        try {
		        	
		        	System.out.println("ImpayeesAdv_STB " + Constante.URL_WS_GetListeQuittancesImpayeesAdv_STB + num_police + num_ad);

		            
		            
		            ClientResponse response = JerseyClient.GetReponseDuClientEDI(Constante.URL_WS_GetListeQuittancesImpayeesAdv_STB + num_police + num_ad, reportController);
		            		
		            
		            if (response == null) {
//		              throw new RuntimeException("Failed : HTTP error code : "
//		                   + response.getStatus());
		            	 
		          	 FacesMessage msg = new FacesMessage("ERREUR", "Auccune réponse au WS de récupération des quittances termes!");
		        		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		        		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		        		  return;
		          }
		        	
		            if (response.getStatus() != 200) {
		            	
//		                throw new RuntimeException("Failed : HTTP error code : "
//		                     + response.getStatus());
		            	FacesMessage msg = new FacesMessage("ERREUR", "Erreur connexion au WS de récupération des quittances termes! " + "(erreur: "+response.getStatus() + " )");
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);
						return;
		            }
		            
		            System.out.println("Output from Server .... \n");
		            //String output = response.getEntity(String.class);
		            String output = response.toString();
		            System.out.println(output);
		            
		            System.out.print(response + " " + "\n");
		    		
		            
		             quittancesImapyeesList = new ArrayList<>(); // la sous liste des quittances dans html
		            
		    		 JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON( response.getEntity(String.class) ); 
		        			    		
		    		 codeEDI = jsonOutput.getInt("code");
		    		 
			          if(codeEDI != 200) {
			        	  
			        	  System.out.println("message WS EDI "+jsonOutput.getString("message")  );
			        	  
			          List<QuittanceTerme> liste_quit_dans_bd = iQuittanceTermeDAO.searchquitByNAdhesionNonEncaissed(num_ad);
			          
			          DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			          
			          System.out.println("liste_quit_dans_bd "+liste_quit_dans_bd);
			          
			        	
			          if(liste_quit_dans_bd != null && liste_quit_dans_bd.size() != 0) {
			          
			          for(QuittanceTerme qtc : liste_quit_dans_bd) {
//			   				System.out.println("qtcDTO "+qtcDTO.getNumero_quittance().trim());
//			   				System.out.println("qtc "+qtc.getNumquit().toString().trim());
			        	  
			        	  QuittanceImapyeeDTO qtDTO = new QuittanceImapyeeDTO();
			        	  qtDTO.setNumero_quittance(qtc.getNumquit().toString().trim());
			        	  qtDTO.setCode_agence(((Adhesion)toggleEvent.getData()).getUtilisateur().getAgence().getCodeAgence());
			        	  qtDTO.setNumero_contrat(num_police+num_ad);
			        	  qtDTO.setDate_du(df.format(qtc.getDateDu()));
			        	  qtDTO.setDate_au(df.format(qtc.getDateAu()));
			        	  qtDTO.setDate_echeance(df.format(qtc.getDateAu()));
			        	  qtDTO.setPrime_nette(new BigDecimal(qtc.getMontant()));
			        	  qtDTO.setFrais(new BigDecimal(qtc.getFrais()));
			        	  qtDTO.setTaxes(new BigDecimal(qtc.getTaxe()));
			        	  qtDTO.setFga(new BigDecimal(qtc.getFga()));
			        	  qtDTO.setPrime_totale(new BigDecimal(qtc.getMontant() + qtc.getFrais() + qtc.getTaxe()+qtc.getFga()));
			        	  qtDTO.setNom_assure(((Adhesion)toggleEvent.getData()).getClient().getNomClient());
			        	  qtDTO.setPrenom_assure(((Adhesion)toggleEvent.getData()).getClient().getPrenomClient());
			        	  qtDTO.setAdresse_assure(((Adhesion)toggleEvent.getData()).getClient().getAdrClient());
//			   					System.out.println("etat "+qtc.getEtat());
			        	  qtDTO.setEncaissed(qtc.getEtat());
			   					
			        	  quittancesImapyeesList.add(qtDTO);
			   				
			   			}
			          
			          }
			          
			        	  
			          } else {
			        	  
			          
		    		 
			        	  System.out.println("message WS EDI "+jsonOutput.getString("message")  );
		    		 
		    		 JSONArray quittancesImapyeesJSON = jsonOutput.getJSONArray("quittancesImapyees");
		    		 
		    		 //List<JSONObject> quittancesImapyeesJ = new ArrayList<>();
		    		 
		    		 
		    		 
		    		 List<QuittanceImapyeeDTO> allQuittancesImapyeesList = new ArrayList<>();
		    		 
		    		 List<BigInteger> numquits = new ArrayList<>();
		    		 
		   		  for (int i = 0; i < quittancesImapyeesJSON.size(); i++) {
		   			//quittancesImapyeesJ.add(quittancesImapyeesJSON.getJSONObject(i));
					  
					  QuittanceImapyeeDTO quittanceImapyee = new QuittanceImapyeeDTO();
					  
					  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
					  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//					  System.out.println("contrat est "+quittancesImapyeesJSON.getJSONObject(i).getString("numero_contrat").substring(9));
//					  System.out.println("code_agence "+((Adhesion)toggleEvent.getData()).getUtilisateur().getAgence().getCodeAgence());
					  
					  quittanceImapyee.setNumero_quittance(new BigDecimal(quittancesImapyeesJSON.getJSONObject(i).getString("numero_quittance")).toString());
//					  quittanceImapyee.setCode_agence(quittancesImapyeesJSON.getJSONObject(i).getString("code_agence"));
					  quittanceImapyee.setCode_agence(((Adhesion)toggleEvent.getData()).getUtilisateur().getAgence().getCodeAgence());
					  quittanceImapyee.setNumero_contrat(quittancesImapyeesJSON.getJSONObject(i).getString("numero_contrat").substring(9));
					  quittanceImapyee.setDate_du(dateFormat.format(sdf.parse(quittancesImapyeesJSON.getJSONObject(i).getString("date_du"))));
					  quittanceImapyee.setDate_au(dateFormat.format(sdf.parse(quittancesImapyeesJSON.getJSONObject(i).getString("date_au"))));
					  
					  quittanceImapyee.setDate_echeance(dateFormat.format(sdf.parse(quittancesImapyeesJSON.getJSONObject(i).getString("date_echeance"))));
					  quittanceImapyee.setPrime_nette(new BigDecimal(quittancesImapyeesJSON.getJSONObject(i).getString("prime_nette")));
					  quittanceImapyee.setFrais(new BigDecimal(quittancesImapyeesJSON.getJSONObject(i).getString("frais")));
					  
					  quittanceImapyee.setTaxes(new BigDecimal(quittancesImapyeesJSON.getJSONObject(i).getString("taxes")));
					  quittanceImapyee.setFga(new BigDecimal(quittancesImapyeesJSON.getJSONObject(i).getString("FGA")));
					  quittanceImapyee.setPrime_totale(new BigDecimal(quittancesImapyeesJSON.getJSONObject(i).getString("prime_totale")));
					  quittanceImapyee.setNom_assure(quittancesImapyeesJSON.getJSONObject(i).getString("nom_assure"));
					  
					  quittanceImapyee.setPrenom_assure(quittancesImapyeesJSON.getJSONObject(i).getString("prenom_assure"));
					  quittanceImapyee.setAdresse_assure(quittancesImapyeesJSON.getJSONObject(i).getString("adresse_assure"));
					  
					  quittanceImapyee.setEncaissed("N");
					  
					  allQuittancesImapyeesList.add(quittanceImapyee);
					  
					  numquits.add(new BigDecimal(quittancesImapyeesJSON.getJSONObject(i).getString("numero_quittance")).toBigInteger());
					  
					  
				  }
		   		  
		   		  

		   		List<QuittanceTerme> liste_quit_dans_bd = iQuittanceTermeDAO.searchquitByManyNumquitWithNonEncaissed(numquits, num_ad);
		   		
		   		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		   		
		   		if(liste_quit_dans_bd ==null) {
		   			System.out.println("liste_quit_dans_bd ==null");
		   			quittancesImapyeesList = allQuittancesImapyeesList;
		   			
		   		}else {
		   			System.out.println("liste_quit_dans_bd ==not null");
		   		for(QuittanceImapyeeDTO qtcDTO : allQuittancesImapyeesList) {
		   			
//		   			for(QuittanceTerme qtc : liste_quit_dans_bd) {
		   			
		   			System.out.println("qtc int "+new BigInteger(qtcDTO.getNumero_quittance().trim()));
		   			
		   			QuittanceTerme qtc = iQuittanceTermeDAO.searchquit(new BigInteger(qtcDTO.getNumero_quittance().trim()));

		   			if(qtc != null) {
		   				System.out.println("qtc "+qtc.getNumquit().toString().trim());
		   				
		   				if(qtcDTO.getNumero_quittance().trim().equalsIgnoreCase(qtc.getNumquit().toString().trim()) ) {
		   					System.out.println("if1 ");

		   					qtcDTO.setEncaissed(qtc.getEtat());
		   				}else {
		   					System.out.println("if2 ");
		   					// ajout quittances dans bd qui ont etat encaissemet = N
		   					
		   					if(qtc.getEtat().equalsIgnoreCase("N")) {
		   						
		   						System.out.println("if22 ");
		   					
				        	  QuittanceImapyeeDTO qtDTO = new QuittanceImapyeeDTO();
				        	  qtDTO.setNumero_quittance(qtc.getNumquit().toString().trim());
				        	  qtDTO.setCode_agence(((Adhesion)toggleEvent.getData()).getUtilisateur().getAgence().getCodeAgence());
				        	  qtDTO.setNumero_contrat(num_police+num_ad);
				        	  qtDTO.setDate_du(df.format(qtc.getDateDu()));
				        	  qtDTO.setDate_au(df.format(qtc.getDateAu()));
				        	  qtDTO.setDate_echeance(df.format(qtc.getDateAu()));
				        	  qtDTO.setPrime_nette(new BigDecimal(qtc.getMontant()));
				        	  qtDTO.setFrais(new BigDecimal(qtc.getFrais()));
				        	  qtDTO.setTaxes(new BigDecimal(qtc.getTaxe()));
				        	  qtDTO.setFga(new BigDecimal(qtc.getFga()));
				        	  qtDTO.setPrime_totale(new BigDecimal(qtc.getMontant() + qtc.getFrais() + qtc.getTaxe()+qtc.getFga()));
				        	  qtDTO.setNom_assure(((Adhesion)toggleEvent.getData()).getClient().getNomClient());
				        	  qtDTO.setPrenom_assure(((Adhesion)toggleEvent.getData()).getClient().getPrenomClient());
				        	  qtDTO.setAdresse_assure(((Adhesion)toggleEvent.getData()).getClient().getAdrClient());

				        	  qtDTO.setEncaissed(qtc.getEtat());
				   					
				        	  quittancesImapyeesList.add(qtDTO);
				        	  
		   					}
		   					
		   					
		   				}
		   				
		   			}
		   				
//		   			}
		   			
		   			quittancesImapyeesList.add(qtcDTO);
		   			
		   		  }
		   		}
		   		
		   		
		        }
		   		
		        	
		        } catch (Exception e) {

		            e.printStackTrace();
		            
		            FacesMessage msg = new FacesMessage("ERREUR", "Erreur interne liée au WS de récupération des quittances termes!");
		  		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		  		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		  		  return;
		            

		          }
		        
		        
		    } else {
		    	 System.out.println("non visible");
		    }
		   
		}
		
		
		
		public void Encaissement_sans_ws(Adhesion adhesion, QuittanceImapyeeDTO qtc) {
			
			System.out.println("Encaissement");
			QuittanceTerme qTerme = iQuittanceTermeDAO.searchquitByNumquit(
					new BigInteger(qtc.getNumero_quittance()));
			
			if(qTerme != null) {
				
				/*** cas echec encaissement ****/
				
				qTerme.setEtat("O");
				iQuittanceTermeDAO.Merge(qTerme);
				qtc.setEncaissed("O");
				
			}else {
				
			

				/*** cas nouveau encaissement ******/
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date du = new Date();
			Date au = new Date();
			
			try {
				 du = sdf.parse(qtc.getDate_du());
				 au = sdf.parse(qtc.getDate_au());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			//QuittanceTerme quittancet = iQuittanceTermeDAO.searchquitByNumquit(Integer.parseInt(qtc.getNumero_quittance()));
				System.out.println("12021100053 ");
				System.out.println("12021100053_ "+new BigInteger("12021100053"));
				System.out.println("12021100053 "+new BigInteger(qtc.getNumero_quittance()));
				QuittanceTerme quittance=new QuittanceTerme(
						au, du, adhesion, 
						adhesion.getMtPrime(), 
						(double)0, qtc.getTaxes().doubleValue(), 
						new BigInteger(qtc.getNumero_quittance()), new Date(), qtc.getFga().doubleValue(),
						"N");
				
				iQuittanceTermeDAO.create(quittance);
				// qtc.setEncaissed("O");
				
				
				DetailHayya detailHayya = hayyaServiceInter.find_detail_by_Adhesion(adhesion);
				
				Agence agc_prim = agenceDAO.FindAgenceBycode(qtc.getCode_agence());
				
				Agence agc_bis = utilisateur.getAgence();
				
				Agence agc;
				
				if(agc_prim == null) {
					agc = agc_bis;
				}else {
					agc = agc_prim;
				}
				
				
				QuittanceTerme qTermeSaved = iQuittanceTermeDAO.searchquitByNumquit(
						new BigInteger(qtc.getNumero_quittance()));
				
				QuittanceDetails qt = new QuittanceDetails(
						qTermeSaved.getIdQuittance(),
						adhesion,
						agc, "ET", 
						"O", (int) detailHayya.getFractHayya().getId_fract_hayya(), 
						qtc.getPrime_totale().doubleValue(), qtc.getPrime_nette().doubleValue());
						
						quittanceDetailsDAO.create(qt);
						
						
						qTermeSaved.setEtat("O");
						iQuittanceTermeDAO.Merge(qTermeSaved);
						qtc.setEncaissed("O");
			
			}
				
				
				addMessage("Succès", "encaissement effectué avec succès");
			
		}
		
		
		
		
public void Encaissement(Adhesion adhesion, QuittanceImapyeeDTO qtc) throws KeyManagementException, NoSuchAlgorithmException {
			
			System.out.println("Encaissement");
			QuittanceTerme qTerme = iQuittanceTermeDAO.searchquitByNumquit(
					new BigInteger(qtc.getNumero_quittance()));
			
			if(qTerme != null) {
				
				/*** cas echec encaissement ****/
				
				QuittanceDetails qt_detail_saved = 	quittanceDetailsDAO.searchDetailQuit(
						qTerme.getIdQuittance());	
				/*
				qTerme.setEtat("O");
				iQuittanceTermeDAO.Merge(qTerme);
				qtc.setEncaissed("O");
				*/
				
				if(utilisateur.getAgence().getSociete()
						.getCodSociet().equalsIgnoreCase(
								reportController.getSTBMat_Pour_Encaissement_Versement_Libre_et_Terme())) {  //STB
					
					
					WS_Encaissement(adhesion, qtc, qTerme, qt_detail_saved);
					
				}else{  //QNB
					
					
					
					
					/**** enregistremnt ds notre !BD ****/

					qTerme.setEtat("O");
					iQuittanceTermeDAO.Merge(qTerme);
					qt_detail_saved.setEtat("O");
					qt_detail_saved.setDateEnc(new Date());
					//qt_detail_saved.setRefOperation(Reference);
					iQuittanceDetailsDAO.Merge(qt_detail_saved);
					qtc.setEncaissed("O");
					
					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					System.out.println("Encaissement effectuée avec succès.");
					
					
				}
				
				
				
				
			}else {
				
			

				/*** cas nouveau encaissement ******/
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date du = new Date();
			Date au = new Date();
			
			try {
				 du = sdf.parse(qtc.getDate_du());
				 au = sdf.parse(qtc.getDate_au());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			//QuittanceTerme quittancet = iQuittanceTermeDAO.searchquitByNumquit(Integer.parseInt(qtc.getNumero_quittance()));
				System.out.println("12021100053 ");
				System.out.println("12021100053_ "+new BigInteger("12021100053"));
				System.out.println("12021100053 "+new BigInteger(qtc.getNumero_quittance()));
				QuittanceTerme quittance=new QuittanceTerme(
						au, du, adhesion, 
						/*adhesion.getMtPrime()*/qtc.getPrime_nette().doubleValue(), 
						(double)0, qtc.getTaxes().doubleValue(), 
						new BigInteger(qtc.getNumero_quittance()), new Date(), qtc.getFga().doubleValue(),
						"N");
				
				iQuittanceTermeDAO.create(quittance);
				// qtc.setEncaissed("O");
				
				int fract_id = 0;
				if(adhesion.getPolice().getCodProdui().getCodeProduit().
						equalsIgnoreCase(ENUMCODEPRODUIT.H7AYYA.getCode()) ||
						adhesion.getPolice().getCodProdui().getCodeProduit().
						equalsIgnoreCase(ENUMCODEPRODUIT.H7ayyaQNB.getCode())
						) {
					
					DetailHayya detailHayya = hayyaServiceInter.find_detail_by_Adhesion(adhesion);
					fract_id = (int) detailHayya.getFractHayya().getId_fract_hayya();
				}
				
				
				Agence agc_prim = agenceDAO.FindAgenceBycode(qtc.getCode_agence());
				
				Agence agc_bis = utilisateur.getAgence();
				
				Agence agc;
				
				if(agc_prim == null) {
					agc = agc_bis;
				}else {
					agc = agc_prim;
				}
				
				
				QuittanceTerme qTermeSaved = iQuittanceTermeDAO.searchquitByNumquit(
						new BigInteger(qtc.getNumero_quittance()));
				
				QuittanceDetails qt = new QuittanceDetails(
						qTermeSaved.getIdQuittance(),
						adhesion,
						/*agc*/agc_bis, "ET", 
						"N", fract_id, 
						qtc.getPrime_totale().doubleValue(), qtc.getPrime_nette().doubleValue(),
						qtc.getPrime_totale().doubleValue(), qtc.getPrime_nette().doubleValue());
						
						quittanceDetailsDAO.create(qt);
						
			    QuittanceDetails qt_detail_saved = 	quittanceDetailsDAO.searchDetailQuit(
			    		qTermeSaved.getIdQuittance());	
						
						/*
						qTermeSaved.setEtat("O");
						iQuittanceTermeDAO.Merge(qTermeSaved);
						qtc.setEncaissed("O");
						*/
			    
			    
			    if(utilisateur.getAgence().getSociete()
						.getCodSociet().equalsIgnoreCase(
								reportController.getSTBMat_Pour_Encaissement_Versement_Libre_et_Terme())) {  //STB
					
					
			    	WS_Encaissement(adhesion, qtc, qTermeSaved, qt_detail_saved);
					
				}else{  //QNB
					
					
					/**** enregistremnt ds notre !BD ****/

					qTermeSaved.setEtat("O");
					iQuittanceTermeDAO.Merge(qTermeSaved);
					qt_detail_saved.setEtat("O");
					qt_detail_saved.setDateEnc(new Date());
					//qt_detail_saved.setRefOperation(Reference);
					iQuittanceDetailsDAO.Merge(qt_detail_saved);
					qtc.setEncaissed("O");
					
					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					System.out.println("Encaissement effectuée avec succès.");
					
					
				}
			    
						
						
			
			}
				
				
				//addMessage("Succès", "encaissement effectué avec succès");
			
		}



public void WS_Encaissement(Adhesion adhesion, QuittanceImapyeeDTO qtc, 
		QuittanceTerme qTerme, QuittanceDetails qt_detail_saved) 
		throws NoSuchAlgorithmException, KeyManagementException{
	
	boolean test = true;


	// System.out.println("IdDemande est "+String.valueOf(qTerme.getIdQuittance()));

        

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
		
		
		
		
		String IdDemande = qtc.getNumero_quittance();
		// String IdDemande = String.valueOf(qTerme.getIdQuittance());
		// System.out.println("IdDemande "+IdDemande);
		
		String PrimeAssurance = qtc.getPrime_totale().toString();
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date du = new Date();
		Date au = new Date();
		
		try {
			 du = sdf.parse(qtc.getDate_du());
			 au = sdf.parse(qtc.getDate_au());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String DateEffet = df.format(du);
		String Datefin = df.format(au);
		
		String Mobile = adhesion.getClient().getTel();
		String Mail = adhesion.getClient().getMail();
		
		String Produit = "00";
		if(adhesion.getPolice().getCodProdui().getCodeProduit().equalsIgnoreCase( ENUMCODEPRODUIT.H7AYYA.getCode())) {
			Produit = Constante.PRODUIT_HAYYA_ET;
		}else {
			Produit = Constante.PRODUIT_AVJ_ET;
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
//				throw new RuntimeException("Failed : HTTP error code : "
//				     + response.getStatus());
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

				qTerme.setEtat("O");
				iQuittanceTermeDAO.Merge(qTerme);
				qt_detail_saved.setEtat("O");
				qt_detail_saved.setDateEnc(new Date());
				qt_detail_saved.setRefOperation(Reference);
				iQuittanceDetailsDAO.Merge(qt_detail_saved);
				qtc.setEncaissed("O");
				
				OperationTransaction operationTransaction = new OperationTransaction();

				operationTransaction.setDateTrans(new Date());
				operationTransaction.setIdAdhesion(adhesion); // à discuter
				operationTransaction.setRefOperation(Reference);
				operationTransaction.setTypTrans("EncaissTerme");

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

			
     		
     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement: problème d'appel au WS d'encaissement!", ""));
     		
     		
     		
     		System.out.println("probleme de ClientHandlerException  ");
     		
			e.printStackTrace();

		  }
		catch (Exception e) {

				
	     		
	     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , Erreur interne liée au niveau du WS d'encaissement!", ""));
	     		
	     		
	     		
	     		System.out.println("probleme de Exception  ");
	     		
				e.printStackTrace();

			  }
		

	

}


		
		
		

		public void confirm() {
		    addMessage("Confirmed", "You have accepted");
		}
		
		public void addMessage(String summary, String detail) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
	        FacesContext.getCurrentInstance().addMessage(null, message);
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
