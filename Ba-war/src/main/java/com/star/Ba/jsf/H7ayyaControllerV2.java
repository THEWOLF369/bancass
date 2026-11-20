package com.star.Ba.jsf;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.naming.InitialContext;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.sql.DataSource;

import org.primefaces.event.FlowEvent;

import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IApiResponseEntityDAO;
import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dao.IOperationTransactionDAO;
import com.star.Ba.dao.IQuittanceDAO;
import com.star.Ba.dao.IQuittanceDetailsDAO;
import com.star.Ba.dao.ITypeAbonnementDAO;
import com.star.Ba.dto.BilanFinancierHayyaDTO;
import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.DetailsGarantieDTO;
import com.star.Ba.dto.HayyaDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.Nombre;
import com.star.Ba.jsf.util.TokenSTB;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.ApiResponseEntity;
import com.star.Ba.model.BilanFinancierHayya;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.CroissHayya;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.FractHayya;
import com.star.Ba.model.ModePaiementHayya;
import com.star.Ba.model.OperationTransaction;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.QuittanceDetails;
import com.star.Ba.model.TrancheHayya;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.parametres.ExampleResourceClient;
import com.star.Ba.parametres.TrustAllX509TrustManager;
import com.star.Ba.service.AssistantVoiyagetServiceInter;
import com.star.Ba.service.HayyaServiceInter;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.star.Ba.jsf.util.Frequency;
import com.star.Ba.jsf.util.FrequencyService;

@ManagedBean
@SessionScoped
public class H7ayyaControllerV2 implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private boolean skip;
	private boolean propositionIsSaved = false;
	
	
	
	
/********  Partie  I *********************/
	
	private Proposition selectedProposition = null;
	
	private List<Police> allPoliseByCodeAndConvention = new ArrayList<Police>();
	
	
	private Adhesion newAdhesion = new Adhesion();
	
	public Proposition newProposition = new Proposition();
	
	private DetailHayya detailHayya = new DetailHayya();
	
	private FractHayya fractHayya = new FractHayya();
	private CroissHayya croissHayya = new CroissHayya(); 
	private TrancheHayya trancheHayya = new TrancheHayya();
 	
	private double nomberPersonneMax = 1;

	private double nombrePersonneMin = 1;
	
	private int ageAssure = 0;
	
	private Date aujourdhui=new Date();
	
	private Date effDate;
	private Date echDate;
	
	private List<DetailsGarantieDTO> detailsGaranties = new ArrayList<>();
	
	private List<DetailsGarantieHayya> detailsGarantieHayyas = new ArrayList<>();
	
	private List<DetailsEvolutionGarantie> detailsEvolutionGaranties = new ArrayList<>();
	
	private List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas = new ArrayList<>();
	
	
	private BilanFinancierHayya bilanFinancierHayya = new BilanFinancierHayya();
	
	private BilanFinancierHayyaDTO bilanFinancierHayyaDTO = new BilanFinancierHayyaDTO();
	
	
	
	private BigDecimal pnette= new BigDecimal(0);
	private BigDecimal accessoires= new BigDecimal(0);
	private BigDecimal taxes= new BigDecimal(0);
	private BigDecimal total = new BigDecimal(0);
	
	private BigDecimal fga= new BigDecimal(0);
	
	
	private int duree;
	
	private double contrib;
	
	private int frac;
	
	private int crois = -1;
	
	private Map<String, String> croissanceOptions = new LinkedHashMap<>();
	
	private int tranche = -1;
	
	private double dtranche;
	
	private String code_caract_dtranche;
	
	private String personnel = "2";
	
	private boolean epargne = true;
	
	private boolean assurance_deces = true;
	
	private boolean iad = true;
	
	private int nbr_periode_pour_frac;
	
	// benef cas vie et deces
	
	private String vie;
	private String deces;
	
	private boolean disabledValidationAdhesion=false;
	
	private boolean disabledall = false;
	
	private boolean disabledEncaiss= false;
	
	private boolean disabledGenerateDevis = true;
	
	boolean testImpression = false;
	
	private String fraction;
	private String modepaie;
	private String banque;
	private String agence;
	private String rib;
	
	private String payeur;
	
	private String nomPayeur;
	private String cinPayeur;
	private String adressePayeur;
	
	private String dispoSpec;
	
	private boolean disabledModepaie = false;
	
	private boolean rendredModepaie = false;  // rendred = visible
	

	// fin   
	
	@EJB
	private ITypeAbonnementDAO typeAbonnementDAO;
	
	@EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;
	
	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;// = new Utilisateurs();
	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;
	
	@ManagedProperty("#{assureBean}")
	private AssureBean assureBean;
	
	   @EJB
		private HayyaServiceInter hayyaServiceInter;
	
	@EJB
	private IAdhesionDAO adhesionDAO ;
	
	private boolean affiche = true;	
	
	
/******* fin Partie I *********************/	
	
	
	
/********  Partie  II *********************/
	


	@EJB
	private IClientDAO clientDAO;
	

	
	
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	

	
	
	private BigDecimal pnette_annuel;

	private BigDecimal total_annuel;
	

	public DetailHayya newDetailHayya;
	public List<DetailsGarantieHayya> newDetailsGarantieHayyaList;
	
	public HayyaDTO  newHayyaDTO;
	
	private String echDateString;
	
		
	
/******* fin Partie II *********************/	
	
	
	
/********  Partie  III *********************/

	@EJB
	private IQuittanceDAO quittanceDAO;
	
	@EJB
	private IQuittanceDetailsDAO quittanceDetailsDAO;
	

	
	@EJB
    private IOperationTransactionDAO iOperationTransactionDAO;
	
	

	
//	private String vie;
//	private String deces;
	
//	private String modepaie;
//	private String banque;
//	private String agence;
//	private String rib;
//	
//	private String payeur;
//	
//	private String nomPayeur;
//	private String cinPayeur;
//	private String adressePayeur;
//	
//	private String dispoSpec;
	
	//private boolean disabledValidationAdhesion=false;
	
	private String num_adhesion;
	
	private String numquit;
	
	boolean test = false;
	boolean testqnb = false;
	// boolean testImpression = false;

	
	private String mnt_total, mnt_nette, mnt_capital;
	
	private Adhesion adhesionToPrintNotise = new Adhesion();
	
	
	private boolean valider_impot = false;
	private Date dateImpot = null;
	private Date dateImpot_init = null;
	
	private Date dateImpotDebut = null;
	private Date dateImpotFin = null;
	
/******* fin Partie III *********************/	
	
	
/*************** partie choix de date de prelev **********************************/
	
	private Integer jourPrelevement, moisPrelevement;
	private List<SelectItem> joursDisponibles;
	private List<SelectItem> moisDisponibles;
	String[] mois = {
		    "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
		    "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
		};
	
	@EJB
    private IApiResponseEntityDAO iApiResponseEntityDAO;
	
	
/*************** fin partie choix de date de prelev **********************************/	
	
	
	@PostConstruct
	public void init() {
		
		System.out.println("==================== init()");
		
		aujourdhui = new Date();
		
		effDate = aujourdhui;
		
		newAdhesion.setDatDebut(aujourdhui);
		newProposition.setDatDebut(aujourdhui);
		
		if( utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000") || 
				utilisateur.getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat())) { // stb
			
			this.rendredModepaie = true;
			
		}else {// autres societe 
			this.rendredModepaie = false;
			this.modepaie = "-1";
		}
		
		Convention convention = serviceAssistantVoiyagetService
				.getConventionBySociete(utilisateur.getAgence().getSociete());
		
		String code_sous_branche = " ";
		
		if(convention != null) {
		
		if(convention.getSociete().getCodSociet().equalsIgnoreCase("022671FPM000")) // qnb
			code_sous_branche = ENUMCODEPRODUIT.H7ayyaQNB.getCode();
		else code_sous_branche = ENUMCODEPRODUIT.H7AYYA.getCode(); // stb
		
		}else {
			
			code_sous_branche = ENUMCODEPRODUIT.H7AYYA.getCode();
		}
		
		Produit produit = serviceAssistantVoiyagetService
				.getProduitByCode(code_sous_branche);
		
		setAllPoliseByCodeAndConvention(serviceAssistantVoiyagetService
				.getPolice_by_conv_prod(convention, produit));
		
		selectedProposition = null;
		
		/*** liste des croissance ***/
		croissanceOptions.put("0%", "1");
	    croissanceOptions.put("1%", "2");
	    croissanceOptions.put("2%", "3");
	    croissanceOptions.put("3%", "4");
	    croissanceOptions.put("4%", "5");
	    croissanceOptions.put("5%", "6");
	    croissanceOptions.put("6%", "7");
	    croissanceOptions.put("7%", "8");
	    croissanceOptions.put("8%", "9");
	    croissanceOptions.put("9%", "10");
	    croissanceOptions.put("10%", "11");
	    croissanceOptions.put("15%", "12");
	    croissanceOptions.put("20%", "13");
	    croissanceOptions.put("25%", "14");
	    
	    
	    joursDisponibles = new ArrayList<>();
	    for (int i = 3; i <= 28; i++) {
	        joursDisponibles.add(new SelectItem(i, String.valueOf(i)));
	    }
	    
	    moisDisponibles = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	    	moisDisponibles.add(new SelectItem(i, mois[i-1]));
	    }
	    
//	    if(duree > 0 && duree < 8) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
//                    "Info",
//                    "Si la durée est < à 8 ans, ce contrat ne donne pas droit à l’avantage fiscal.");
//            FacesContext.getCurrentInstance().addMessage("duree_info", msg);
//        }
	}
	
	
	
	public boolean  isCondition() {
		
		if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000"))// stb
			return true;
			else
				return false;
	
	}
	
	
/************************** partie I *********************************/
	
	
	
	// getter and setter
	
	public ClientBean getClientBean() {
		return clientBean;
	}
	
	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}
	
	
	
	
	public AssureBean getAssureBean() {
		return assureBean;
	}


	public void setAssureBean(AssureBean assureBean) {
		this.assureBean = assureBean;
	}




	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	
	
	
	public Proposition getSelectedProposition() {
		System.out.println("proposition selected dans get ===="+ selectedProposition.getIdProposition());	
		return selectedProposition;
		
		
	}
	
	public void setSelectedProposition(Proposition selectedProposition) {
		System.out.println("selectedProposition :"+selectedProposition.getIdProposition());
		
		
		if (adhesionDAO.propexiste_7ayya(selectedProposition).isEmpty()) {
			affiche = true;
			System.out.println("affiche 1 ===================="+ affiche);
		}
		else {
			
			
			affiche = false;
			System.out.println("affiche 2 ===================="+ affiche);
		}
		
		
		if ((selectedProposition!=null) && (affiche == true)){
			
			System.out.println("affiche 3 ===================="+ affiche);
			
			effDate = selectedProposition.getDatDebut();
			
			DetailHayya detailHayya_ = hayyaServiceInter.find_detail_by_Proposition(selectedProposition);
			
			
			newAdhesion = new Adhesion();
			
			newProposition = new Proposition();
			newProposition.setPolice(selectedProposition.getPolice());
			newProposition.setClient(selectedProposition.getClient());
			newProposition.setUtilisateur(selectedProposition.getUtilisateur());
			newProposition.setIdUtilisateurs(selectedProposition.getIdUtilisateurs());
			newProposition.setDatFin(selectedProposition.getDatFin());
			newProposition.setDuree(selectedProposition.getDuree());
			newProposition.setEtat(selectedProposition.getEtat());
			newProposition.setMtPrime(selectedProposition.getMtPrime());
			newProposition.setTaxe(selectedProposition.getTaxe());
			newProposition.setPrimetot(selectedProposition.getPrimetot());
			
			detailHayya = new DetailHayya();
			detailHayya.setFractHayya(detailHayya_.getFractHayya());
			detailHayya.setCroissHayya(detailHayya_.getCroissHayya());
			detailHayya.setTrancheHayya(detailHayya_.getTrancheHayya());
			detailHayya.setDtranche(detailHayya_.getDtranche());
			detailHayya.setAge(detailHayya_.getAge());
			detailHayya.setContribution(detailHayya_.getContribution());
			detailHayya.setPersonnelstar(detailHayya_.getPersonnelstar());
			detailHayya.setPnette(detailHayya_.getPnette());
			detailHayya.setAccessoires(detailHayya_.getAccessoires());
			detailHayya.setCommission(detailHayya_.getCommission());
			detailHayya.setFa(detailHayya_.getFa());
			
			detailHayya.setClientWs(detailHayya_.getClientWs());
			
			
			detailsGarantieHayyas = new ArrayList<>();
			
			fractHayya = new FractHayya();
			croissHayya = new CroissHayya(); 
			trancheHayya = new TrancheHayya();
			
			
			
			bilanFinancierHayya = new BilanFinancierHayya();
			
			bilanFinancierHayyaDTO = new BilanFinancierHayyaDTO();
			
			
			
			
			newAdhesion.setDatDebut(effDate);
			newProposition.setDatDebut(effDate);
			
			newAdhesion.setNombrePersonne(BigDecimal.valueOf(1));
			newAdhesion.setTypeAbonnement(typeAbonnementDAO.find((long)1));
			
			newProposition.setNombrePersonne(BigDecimal.valueOf(1));
			newProposition.setTypeAbonnement(typeAbonnementDAO.find((long)1));
			
			nomberPersonneMax = 1;
			nombrePersonneMin = 1;		
//			desabledGenerateNotice=true;
//			desabledEncaiss=true;
			//selectedProposition=null;
			clientBean.setClient(selectedProposition.getClient());
			assureBean.setClient(selectedProposition.getClient());
//			selectedDuree=dureeDAO.find((long)1);
//			System.out.println("selectedDuree="+selectedDuree.getValDuree());
			
			contrib =  detailHayya.getContribution().doubleValue();
			duree = (int)newProposition.getDuree();
			frac = (int)detailHayya.getFractHayya().getId_fract_hayya();
			crois = (int)detailHayya.getCroissHayya().getId_croiss_hayya();
			tranche = (int)detailHayya.getTrancheHayya().getId_tranche_hayya();
			dtranche = detailHayya.getDtranche().doubleValue();
			personnel = detailHayya.getPersonnelstar();
			
			clientBean.rechercher();
			clientBean.setAgeClient(detailHayya_.getAge());
			
			// cas validation proposition
			
			this.disabledValidationAdhesion = false;
			this.disabledEncaiss = false;
			this.testImpression = false;
			this.disabledGenerateDevis = true;
			this.vie = "1";
			this.deces = "1";
			
			  enfant = new Person();
			  enfants = new ArrayList<Person>();

		      autre = new Person();
			 autres = new ArrayList<Person>();

		  defaut = new Person();
			defauts = new ArrayList<Person>();


		enfantd = new Person();
		enfantds = new ArrayList<Person>();

		conjoint = new Person();
			conjoints = new ArrayList<Person>();

		 parent = new Person();
			parents = new ArrayList<Person>();


		 autred = new Person();
			autreds = new ArrayList<Person>();
			
			
			fraction = "1";
//			modepaie = "1";
			if( utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000") ||
					utilisateur.getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat())) { // stb
					
			
			 if (utilisateur.getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat()) && frac == 5) { // stb
			        this.modepaie = "1"; // mode paiement est fixe ne change pas et il est directe
			        this.disabledModepaie = true;

			 } else if(frac == 3 || frac == 4) {
				
				this.modepaie = "2"; // mode paiement est fixe ne change pas et il est prelevement bancaire
				this.disabledModepaie = true;
				
			} else {
				
				this.modepaie = "1";
				this.disabledModepaie = false;
			}
			
			}else { // autres societe 
				
				this.modepaie = "-1";
				this.disabledModepaie = true;
			}
			
			jourPrelevement = null;
			moisPrelevement = null;
			
			banque = "1";
			agence = "";
			rib = "";

			payeur = "1";

			nomPayeur = "";
			cinPayeur = "";
			adressePayeur = "";

			dispoSpec = "";
			
			
		} else if (affiche == false) {
			
			System.out.println("je suis dans affiche false ============================");
			 //newAdhesion=new Adhesion();
			 //clientBean.getClient().setIdentifiant(null);	 
			 this.selectedProposition = null;
			 
			 initialisationClientAndAdhesion();
			 
//			 FacesMessage msg = new FacesMessage("ERREUR", "contrat déjà effectué ");
//			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//			  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			 
			 FacesContext facesContext = FacesContext.getCurrentInstance();
				Flash flash = facesContext.getExternalContext().getFlash();
				flash.setKeepMessages(true);
				
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Contrat déjà effectué!",""));
			
			 
			  
		}
		
		
		this.selectedProposition = selectedProposition;
	}
	
	
	public Adhesion getNewAdhesion() {
		return newAdhesion;
	}

	public void setNewAdhesion(Adhesion newAdhesion) {
		this.newAdhesion = newAdhesion;
	}
	
	
	
	
	
	
	
	public Proposition getNewProposition() {
		return newProposition;
	}




	public void setNewProposition(Proposition newProposition) {
		this.newProposition = newProposition;
	}




	public DetailHayya getDetailHayya() {
		return detailHayya;
	}




	public void setDetailHayya(DetailHayya detailHayya) {
		this.detailHayya = detailHayya;
	}

	
	







	public List<DetailsGarantieHayya> getDetailsGarantieHayyas() {
		return detailsGarantieHayyas;
	}




	public void setDetailsGarantieHayyas(List<DetailsGarantieHayya> detailsGarantieHayyas) {
		this.detailsGarantieHayyas = detailsGarantieHayyas;
	}

	
	
	



	public BilanFinancierHayya getBilanFinancierHayya() {
		return bilanFinancierHayya;
	}




	public void setBilanFinancierHayya(BilanFinancierHayya bilanFinancierHayya) {
		this.bilanFinancierHayya = bilanFinancierHayya;
	}
	
	
	
	




	public BilanFinancierHayyaDTO getBilanFinancierHayyaDTO() {
		return bilanFinancierHayyaDTO;
	}




	public void setBilanFinancierHayyaDTO(BilanFinancierHayyaDTO bilanFinancierHayyaDTO) {
		this.bilanFinancierHayyaDTO = bilanFinancierHayyaDTO;
	}




	public List<Police> getAllPoliseByCodeAndConvention() {
		return allPoliseByCodeAndConvention;
	}

	public void setAllPoliseByCodeAndConvention(List<Police> allPoliseByCodeAndConvention) {
		this.allPoliseByCodeAndConvention = allPoliseByCodeAndConvention;
	}
	
	
	public double getNombrePersonneMin() {
		return nombrePersonneMin;
	}

	public void setNombrePersonneMin(double nombrePersonneMin) {
		this.nombrePersonneMin = nombrePersonneMin;
	}

	public double getNomberPersonneMax() {
		return nomberPersonneMax;
	}

	public void setNomberPersonneMax(double nomberPersonneMax) {
		this.nomberPersonneMax = nomberPersonneMax;
	}
	
	
	public int getAgeAssure() {
		return clientBean.getAgeClient();
	}

 
	public void setAgeAssure(int ageAssure) {
		this.ageAssure = ageAssure;
	}
	
	
	public Date getAujourdhui() {
		return aujourdhui;
	}

	public void setAujourdhui(Date aujourdhui) {
		this.aujourdhui = aujourdhui;
	}
	
	
	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}
	
	
	
	
	public Date getEchDate() {
		return echDate;
	}




	public void setEchDate(Date echDate) {
		this.echDate = echDate;
	}




	public int getDuree() {
		return duree;
	}




	public void setDuree(int duree) {
		this.duree = duree;
	}

	
	public double getContrib() {
		return contrib;
	}




	public void setContrib(double contrib) {
		this.contrib = contrib;
	}
	
	
	public int getFrac() {
		return frac;
	}




	public void setFrac(int frac) {
		this.frac = frac;
	}
	
	
	 

	public int getCrois() {
		return crois;
	}




	public void setCrois(int crois) {
		this.crois = crois;
	}




	public int getTranche() {
		return tranche;
	}




	public void setTranche(int tranche) {
		this.tranche = tranche;
	}

	
	



	public double getDtranche() {
		return dtranche;
	}



	public void setDtranche(double dtranche) {
		this.dtranche = dtranche;
	}



	public String getPersonnel() {
		return personnel;
	}




	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}




	public boolean isEpargne() {
		return epargne;
	}




	public void setEpargne(boolean epargne) {
		this.epargne = epargne;
	}





	public boolean isAssurance_deces() {
		return assurance_deces;
	}




	public void setAssurance_deces(boolean assurance_deces) {
		this.assurance_deces = assurance_deces;
	}




	public boolean isIad() {
		return iad;
	}




	public void setIad(boolean iad) {
		this.iad = iad;
	}

	
	public List<DetailsGarantieDTO> getDetailsGaranties() {
		return detailsGaranties;
	}




	public void setDetailsGaranties(List<DetailsGarantieDTO> detailsGaranties) {
		this.detailsGaranties = detailsGaranties;
	}
	
	
	
	
	
	public List<DetailsEvolutionGarantie> getDetailsEvolutionGaranties() {
		return detailsEvolutionGaranties;
	}




	public void setDetailsEvolutionGaranties(List<DetailsEvolutionGarantie> detailsEvolutionGaranties) {
		this.detailsEvolutionGaranties = detailsEvolutionGaranties;
	}
	
	
	




	public List<DetailsEvolutionGarantieHayya> getDetailsEvolutionGarantieHayyas() {
		return detailsEvolutionGarantieHayyas;
	}




	public void setDetailsEvolutionGarantieHayyas(List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas) {
		this.detailsEvolutionGarantieHayyas = detailsEvolutionGarantieHayyas;
	}




	public BigDecimal getPnette() {
		return pnette;
	}




	public void setPnette(BigDecimal pnette) {
		this.pnette = pnette;
	}




	public BigDecimal getAccessoires() {
		return accessoires;
	}




	public void setAccessoires(BigDecimal accessoires) {
		this.accessoires = accessoires;
	}




	public BigDecimal getTaxes() {
		return taxes;
	}




	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}
	
	
	
	




	public BigDecimal getFga() {
		return fga;
	}




	public void setFga(BigDecimal fga) {
		this.fga = fga;
	}




	public BigDecimal getTotal() {
		return total;
	}




	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
	
	public boolean isDisabledValidationAdhesion() {
		return disabledValidationAdhesion;
	}


	public void setDisabledValidationAdhesion(boolean disabledValidationAdhesion) {
		this.disabledValidationAdhesion = disabledValidationAdhesion;
	}
	
	

	public boolean isDisabledall() {
		return disabledall;
	}


	public void setDisabledall(boolean disabledall) {
		this.disabledall = disabledall;
	}
	
	
	public boolean isTestImpression() {
		return testImpression;
	}


	public void setTestImpression(boolean testImpression) {
		this.testImpression = testImpression;
	}
	
	

	
	public boolean isDisabledEncaiss() {
		return disabledEncaiss;
	}




	public void setDisabledEncaiss(boolean disabledEncaiss) {
		this.disabledEncaiss = disabledEncaiss;
	}
	
	
	




	public boolean isDisabledGenerateDevis() {
		return disabledGenerateDevis;
	}




	public void setDisabledGenerateDevis(boolean disabledGenerateDevis) {
		this.disabledGenerateDevis = disabledGenerateDevis;
	}




	public String getVie() {
		return vie;
	}


	public void setVie(String vie) {
		this.vie = vie;
	}


	public String getDeces() {
		return deces;
	}


	public void setDeces(String deces) {
		this.deces = deces;
	}
	
	


	public CroissHayya getCroissHayya() {
		return croissHayya;
	}




	public void setCroissHayya(CroissHayya croissHayya) {
		this.croissHayya = croissHayya;
	}




	public String getFraction() {
		return fraction;
	}




	public void setFraction(String fraction) {
		this.fraction = fraction;
	}




	public String getModepaie() {
		return modepaie;
	}




	public void setModepaie(String modepaie) {
		this.modepaie = modepaie;
	}


	
	


	public boolean isDisabledModepaie() {
		return disabledModepaie;
	}



	public void setDisabledModepaie(boolean disabledModepaie) {
		this.disabledModepaie = disabledModepaie;
	}
	
	



	public boolean isRendredModepaie() {
		return rendredModepaie;
	}



	public void setRendredModepaie(boolean rendredModepaie) {
		this.rendredModepaie = rendredModepaie;
	}



	public String getBanque() {
		return banque;
	}




	public void setBanque(String banque) {
		this.banque = banque;
	}




	public String getAgence() {
		return agence;
	}




	public void setAgence(String agence) {
		this.agence = agence;
	}




	public String getRib() {
		return rib;
	}




	public void setRib(String rib) {
		this.rib = rib;
	}




	public String getPayeur() {
		return payeur;
	}




	public void setPayeur(String payeur) {
		this.payeur = payeur;
	}




	public String getNomPayeur() {
		return nomPayeur;
	}




	public void setNomPayeur(String nomPayeur) {
		this.nomPayeur = nomPayeur;
	}




	public String getCinPayeur() {
		return cinPayeur;
	}




	public void setCinPayeur(String cinPayeur) {
		this.cinPayeur = cinPayeur;
	}




	public String getAdressePayeur() {
		return adressePayeur;
	}




	public void setAdressePayeur(String adressePayeur) {
		this.adressePayeur = adressePayeur;
	}


	
	


	public int getNbr_periode_pour_frac() {
		return nbr_periode_pour_frac;
	}




	public void setNbr_periode_pour_frac(int nbr_periode_pour_frac) {
		this.nbr_periode_pour_frac = nbr_periode_pour_frac;
	}




	public String getDispoSpec() {
		return dispoSpec;
	}




	public void setDispoSpec(String dispoSpec) {
		this.dispoSpec = dispoSpec;
	}

	
	

	
	// functions




	public String navigateTo7ayya() {
		
		if(selectedProposition == null){
		initialisationClientAndAdhesion();
		}
		return "devis_7ayya";

	}
	
	
	
	
	
	public void initialisationClientAndAdhesion(){
		newAdhesion = new Adhesion();
		newProposition = new Proposition();
		detailHayya = new DetailHayya();
		detailsGarantieHayyas = new ArrayList<>();
		
		bilanFinancierHayya = new BilanFinancierHayya();
		
		bilanFinancierHayyaDTO = new BilanFinancierHayyaDTO();
		
		fractHayya = new FractHayya();
		croissHayya = new CroissHayya(); 
		trancheHayya = new TrancheHayya();
		
		newAdhesion.setNombrePersonne(BigDecimal.valueOf(1));
		newAdhesion.setTypeAbonnement(typeAbonnementDAO.find((long)1));
		
		newProposition.setNombrePersonne(BigDecimal.valueOf(1));
		newProposition.setTypeAbonnement(typeAbonnementDAO.find((long)1));
		
		nomberPersonneMax = 1;
		nombrePersonneMin = 1;		
//		desabledGenerateNotice=true;
//		desabledEncaiss=true;
		selectedProposition=null;
		clientBean.setClient(new Client());
		assureBean.setClient(new Client());
//		selectedDuree=dureeDAO.find((long)1);
//		System.out.println("selectedDuree="+selectedDuree.getValDuree());
		
		contrib = 0;
		duree = 0;
		frac = -1;
		crois = -1;
		tranche = -1;
		dtranche = 0;
		personnel = "2";
		
		clientBean.setAgeClient(0);
		
		// cas validation proposition
		
		this.disabledValidationAdhesion = false;
		this.disabledall = false;
		this.disabledEncaiss = false;
		this.testImpression = false;
		this.disabledGenerateDevis = true;
		this.vie = "1";
		this.deces = "1";
		
		  enfant = new Person();
		  enfants = new ArrayList<Person>();

	      autre = new Person();
		 autres = new ArrayList<Person>();

	  defaut = new Person();
		defauts = new ArrayList<Person>();


	enfantd = new Person();
	enfantds = new ArrayList<Person>();

	conjoint = new Person();
		conjoints = new ArrayList<Person>();

	 parent = new Person();
		parents = new ArrayList<Person>();


	 autred = new Person();
		autreds = new ArrayList<Person>();
		
		
		fraction = "1";
//		modepaie = "1";
		if( utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000") ||
				utilisateur.getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat())) { // stb
			
			this.modepaie = "1";
			this.disabledModepaie = false;
			
		}else {// autres societe 
			
			this.modepaie = "-1";
			this.disabledModepaie = true;
		}
		
		jourPrelevement = null;
		moisPrelevement = null;
		
		banque = "1";
		agence = "";
		rib = "";

		payeur = "1";

		nomPayeur = "";
		cinPayeur = "";
		adressePayeur = "";

		dispoSpec = "";
		
		
		skip = false;
		
		propositionIsSaved = false;
		
		
		/*** ws client ***/
		clientBean.setFromWS(false);
		clientBean.setNomWS(false);
		clientBean.setPrenomWS(false);
		clientBean.setDateNaisWS(false);
		clientBean.setCinWS(false);
		clientBean.setRibWS(false);
	 
		
	}
	
	
	
	
	public Converter getPoliceConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (Police obj : getAllPoliseByCodeAndConvention()) {

					if (obj.getNPolice().equals(value)) {
						return obj;
					}
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				return value.toString();
			}
		};
	}

	
	
	public String navigateToClientPage() {
		if(clientBean.getClient().getGouver()==null){
			System.out.println("enter to clean");
			clientBean.getAllCodePostalByGouvernera().clear();
		}

		return "client7ayya?faces-redirect=true";
	}
	
	
	public String navigateToAssurePage() {
		if(assureBean.getClient().getGouver()==null){
			System.out.println("enter to clean");
			assureBean.getAllCodePostalByGouvernera().clear();
		}

		return "assure7ayya?faces-redirect=true";
	}
	
	
	//private final OkHttpClient httpClient = new OkHttpClient();
	
//	private final OkHttpClient httpClient = new OkHttpClient.Builder()
//            .connectTimeout(5, TimeUnit.SECONDS)
//            .writeTimeout(5, TimeUnit.SECONDS)
//            .readTimeout(5, TimeUnit.SECONDS)
//            .build();
	

public boolean UpdateDateOfBirth() {
	
	try {
	
	SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
	
	String date_nais = sdfr.format( clientBean.getClient().getDatNaiss() );
	
	System.out.println("date_nais "+date_nais);
	
//	final String test = Constante.test;
//	System.out.println("test "+test);
	
	final String CodeClient_STB = Constante.CodeClient_STB;
	System.out.println("CodeClient_STB "+CodeClient_STB);
	final String URL_WS_UpdateDateOfBirth_STB = Constante.URL_WS_UpdateDateOfBirth_STB;
	System.out.println("URL_WS_UpdateDateOfBirth_STB "+URL_WS_UpdateDateOfBirth_STB);
	
	Form f = new Form();
	f.add("codeClient", CodeClient_STB);
	f.add("newDoB", date_nais);
	

    
    
    ClientResponse response = JerseyClient.PostReponseDuClientEDI(URL_WS_UpdateDateOfBirth_STB, f, reportController);
    		
   

    
    if (response == null) {
//      throw new RuntimeException("Failed : HTTP error code : "
//           + response.getStatus());
  	 FacesMessage msg = new FacesMessage("ERREUR", "Pas de réponse au serveur, veuillez contacter l'administrateur! ");
		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		  return false;
  }
    
    
    if (response.getStatus() != 200) {
//        throw new RuntimeException("Failed : HTTP error code : "
//             + response.getStatus());
    	FacesMessage msg = new FacesMessage("ERREUR", "Erreur serveur, veuillez contacter l'administrateur! ( code d'erreur: " + response.getStatus() + " )");
		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		  return false;
    }
    
    JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON( response.getEntity(String.class) ); 
	 
	 String code = jsonOutput.getString("code");
	 
	 System.out.println("code "+code);
	 
	 if(code.equalsIgnoreCase("200")) {
		 
		 System.out.println("true ");
		 return true;
	 }
	 else {
		 System.out.println("false ");
		 return false;
	 }
	 
	 
} catch (Exception e) {

    e.printStackTrace();
    
    
    FacesMessage msg = new FacesMessage("ERREUR", "Erreur interne, veuillez contacter l'administrateur! ");
	  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
	  return false;

  }
	
}
	
	
public boolean calculTarif() throws Exception {
	
	boolean adhesionValide= true;
	
	int code_agence = 0;
	int code_utilisateur = 0;
	int code_branche = 0;
	String code_sous_branche = " ";
	String code_client = " ";
	String code_duree = " ";
	
	/*
	if (!UpdateDateOfBirth()) {
		adhesionValide=false;
		return null;
    }
    */
	
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	String date_nais_assure = dateFormat.format(clientBean.getClient().getDatNaiss());
	
	newProposition.setPolice(newAdhesion.getPolice());
	
	newAdhesion.setClient(clientBean.getClient());
	newProposition.setClient(clientBean.getClient());
	
	detailHayya.setAge(clientBean.getAgeClient());
	
	//calcul date echeance
	Calendar c = Calendar.getInstance();
	c.setTime(effDate);
	c.add(Calendar.YEAR, +duree);
	//c.add(Calendar.MONTH, effDate.getMonth());
	//c.add(Calendar.DAY_OF_MONTH, effDate.getDay());
	echDate = c.getTime();
	
	
	newAdhesion.setDatDebut(effDate);
	newAdhesion.setDatFin(echDate);
	newAdhesion.setDatcreation(new Date());
	newAdhesion.setDuree(duree);
	 
	
	newProposition.setDatDebut(effDate);
	newProposition.setDatFin(echDate);
	newProposition.setDatcreation(new Date());
	newProposition.setDuree(duree);
	 
	
	// pour dire fractionnement
	switch (frac) {
	case 1:
	    System.out.println("Annuel");
	    newAdhesion.setFraPolice("1");
	    newProposition.setFraPolice("1");
	    nbr_periode_pour_frac = 1;
	    this.modepaie = "1";
	    this.disabledModepaie = false;
	    break;
	  case 2:
	    System.out.println("Semestriel");
	    newAdhesion.setFraPolice("2");
	    newProposition.setFraPolice("2");
	    nbr_periode_pour_frac = 2;
	    this.modepaie = "1";
	    this.disabledModepaie = false;
	    break;
	  case 3:
	    System.out.println("Trimestriel");
	    newAdhesion.setFraPolice("3");
	    newProposition.setFraPolice("3");
	    nbr_periode_pour_frac = 4;
	    this.modepaie = "2"; // mode paiement est fixe ne change pas et il est prelevement bancaire
	    this.disabledModepaie = true;
	    break;
	  case 4:
	    System.out.println("Mensuel");
	    newAdhesion.setFraPolice("4");
	    newProposition.setFraPolice("4");
	    nbr_periode_pour_frac = 12;
	    this.modepaie = "2"; // mode paiement est fixe ne change pas et il est prelevement bancaire
	    this.disabledModepaie = true;
	    break;
	  case 5:
	    System.out.println("PRIME UNIQUE");
	    newAdhesion.setFraPolice("5");
	    newProposition.setFraPolice("5");
	    nbr_periode_pour_frac = 1;
	    this.modepaie = "1";
//	    this.disabledModepaie = false;
	    if (reportController.getSTBMat().equals(utilisateur.getAgence().getSociete().getCodSociet())) { //stb
	        this.disabledModepaie = true;
	    } else {
	        this.disabledModepaie = false;
	    }
	    break;
	  default:  
		 System.out.println("default"); 
		 break;
}
	
	detailHayya.setContribution(new BigDecimal(contrib));
	fractHayya.setId_fract_hayya(frac);
	detailHayya.setFractHayya(fractHayya);
	croissHayya.setId_croiss_hayya(crois);
	croissHayya.setCroissance(getLibelleByValeur(Integer.toString(crois)));
	detailHayya.setCroissHayya(croissHayya);
	trancheHayya.setId_tranche_hayya(tranche);
	detailHayya.setTrancheHayya(trancheHayya);
	detailHayya.setDtranche(new BigDecimal(dtranche));
	detailHayya.setPersonnelstar(personnel);
	
	 if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000") ){ // stb
			/*if( clientBean.getClient().getNcompte() == null || 
					(clientBean.getClient().getNcompte()!=null && clientBean.getClient().getNcompte().equals(""))){
				newAdhesion.setEtat("V");
			}
			else {
				newAdhesion.setEtat("N");
			}*/
		 newAdhesion.setEtat("N");
			 code_agence = Integer.parseInt(Constante.CodeAgence_STB);
			 code_utilisateur = Integer.parseInt(Constante.CodeUtilisateur_STB);
			 code_branche = Integer.parseInt(Constante.CodeBranche_STB);
		     code_sous_branche = ENUMCODEPRODUIT.H7AYYA.getCode();
			 code_client = Constante.CodeClient_STB;
			 code_duree = Constante.CodeDuree_STB;
			 
			code_caract_dtranche = "148";
			
//			 this.modepaie = "-1";
//			 this.disabledModepaie = true;
			 

		}
		else if(utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000")){ // qnb
			newAdhesion.setEtat("N");
			
			code_agence = Integer.parseInt(Constante.CodeAgence_QNB);
			 code_utilisateur = Integer.parseInt(Constante.CodeUtilisateur_QNB);
			 code_branche = Integer.parseInt(Constante.CodeBranche_QNB);
			//code_sous_branche = ENUMCODEPRODUIT.H7AYYA.getCode();
			code_sous_branche = ENUMCODEPRODUIT.H7ayyaQNB.getCode();
			 code_client = Constante.CodeClient_QNB;
			 code_duree = Constante.CodeDuree_QNB;
			 
			 code_caract_dtranche = "147";
		}else {
			
//			 this.modepaie = "-1";
//			 this.disabledModepaie = true;
			
			System.out.println("pas de choix du mode de paiement");
			
		}
	 
	 
	 if( utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000") ||
				utilisateur.getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat())) { //stb
		 
		 System.out.println("il y a choix du mode de paiement");
		 
	 }else {
		 
		 this.modepaie = "-1";
		 this.disabledModepaie = true;
		 
	 }
	 
	//newAdhesion.setEtat("V");
	newAdhesion.setIdUtilisateurs(utilisateur.getIdUtilisateurs());
	
	newProposition.setEtat("V");
	newProposition.setIdUtilisateurs(utilisateur.getIdUtilisateurs());
	
	SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
	
	String date_effet = sdfr.format( effDate );
	
	String date_ech = sdfr.format( echDate );
	
	System.out.println("echDate "+echDate);
	
	System.out.println("date_effet "+date_effet);
	System.out.println("date_ech "+date_ech);
		
	System.out.println("Testing 2 - Send Http POST request v2");
	
	String input_ = "{"
			+ "                \"CODE_AGENCE\": 826,"
			+ "                \"CODE_UTILISATEUR\": 625,"
			+ "                \"NOTE_COUVERTURE\": \"0000000000\","
			+ "                \"CODE_BRANCHE\": 61,"
			+ "                \"CODE_SOUS_BRANCHE\": \"685\","
			+ "                \"CODE_CLIENT\": \"639189\","
			+ "                \"CODE_DUREE\": \"61\","
			+ "                \"DATE_EFFET_POLICE\": \"13/09/2021\","
			+ "                \"DATE_EXPIRATION\": \"13/09/2031\","
			+ "                \"Caracteristiques\": ["
			+ "                               {"
			+ "                                               \"CODE_CARACTERISTIQUE\": \"2\","
			+ "                                               \"VALEUR_CARACT\": \"4.000\","
			+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
			+ "                               },"
			+ "                               {"
			+ "                                               \"CODE_CARACTERISTIQUE\": \"112\","
			+ "                                               \"VALEUR_CARACT\": \"10\","
			+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
			+ "                               },"
			+ "                               {"
			+ "                                               \"CODE_CARACTERISTIQUE\": \"103\","
			+ "                                               \"VALEUR_CARACT\": \"200.000\","
			+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
			+ "                               },"
			+ "                               {"
			+ "                                               \"CODE_CARACTERISTIQUE\": \"11\","
			+ "                                               \"VALEUR_CARACT\": \"3\","
			+ "                                               \"VALEUR_DEPENDE_CARACT\": \"3\""
			+ "                               },"
			+ "                               {"
			+ "                                               \"CODE_CARACTERISTIQUE\": \"106\","
			+ "                                               \"VALEUR_CARACT\": \"3\","
			+ "                                               \"VALEUR_DEPENDE_CARACT\": \"3\""
			+ "                               },"
			+ "                               {"
			+ "                                               \"CODE_CARACTERISTIQUE\": \"100\","
			+ "                                               \"VALEUR_CARACT\": \"3\","
			+ "                                               \"VALEUR_DEPENDE_CARACT\": \"3\""
			+ "                               },"
			+ "                               {"
			+ "                                               \"CODE_CARACTERISTIQUE\": \"131\","
			+ "                                               \"VALEUR_CARACT\": \"0.000\","
			+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
			+ "                               },"
			+ "                               {"
			+ "                                               \"CODE_CARACTERISTIQUE\": \"124\","
			+ "                                               \"VALEUR_CARACT\": \"1\","
			+ "                                               \"VALEUR_DEPENDE_CARACT\": \"1\""
			+ "                               },"
			+ "                               {"
			+ "                                               \"CODE_CARACTERISTIQUE\": \"109\","
			+ "                                               \"VALEUR_CARACT\": \"0\","
			+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
			+ "                               }"
			+ "                ],"
			+ "                \"Garanties\": ["
			+ "                               {"
			+ "                                               \"CODE_GARANTIE\": \"1\","
			+ "                                               \"CAPITAL_ASSUREE\": \"0\""
			+ "                               },"
			+ "                               {"
			+ "                                               \"CODE_GARANTIE\": \"2\","
			+ "                                               \"CAPITAL_ASSUREE\": \"0\""
			+ "                               },"
			+ "                               {"
			+ "                                               \"CODE_GARANTIE\": \"3\","
			+ "                                               \"CAPITAL_ASSUREE\": \"0\""
			+ "                               }"
			+ "                ]"
			+ "}";

	
	String input = "{"
	  		+ "                \"CODE_AGENCE\": " + code_agence + ","
	  		+ "                \"CODE_UTILISATEUR\": " + code_utilisateur + ","
	  		+ "                \"NOTE_COUVERTURE\": \"0000000000\","
	  		+ "                \"CODE_BRANCHE\": " + code_branche + ","
	  		+ "                \"CODE_SOUS_BRANCHE\": \""+code_sous_branche+"\","
	  		+ "                \"CODE_CLIENT\": \""+code_client+"\","
	  		+ "                \"CODE_DUREE\": \""+code_duree+"\","
	  		+ "                \"DATE_EFFET_POLICE\": \""+date_effet+"\","
	  		+ "                \"DATE_EXPIRATION\": \""+date_ech+"\","
	  		+ "                \"Caracteristiques\": ["
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"2\","
	  		+ "                                               \"VALEUR_CARACT\": \"4.000\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"112\","
	  		+ "                                               \"VALEUR_CARACT\": \""+duree+"\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"103\","
	  		+ "                                               \"VALEUR_CARACT\": \""+contrib+"\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"11\","
	  		+ "                                               \"VALEUR_CARACT\": \""+frac+"\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \""+frac+"\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"106\","
	  		+ "                                               \"VALEUR_CARACT\": \""+crois+"\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \""+crois+"\""
	  		+ "                               },"
	  		+ "                               {"
//	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"100\","
//	  		+ "                                               \"VALEUR_CARACT\": \""+tranche+"\","
//	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \""+tranche+"\""
			+ "                                               \"CODE_CARACTERISTIQUE\": \""+code_caract_dtranche+"\","
			+ "                                               \"VALEUR_CARACT\": \""+dtranche+"\","
			+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"131\","
	  		+ "                                               \"VALEUR_CARACT\": \"0.000\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"124\","
	  		+ "                                               \"VALEUR_CARACT\": \""+personnel+"\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \""+personnel+"\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"109\","
	  		+ "                                               \"VALEUR_CARACT\": \"0\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"141\","
			+ "			                                      \"VALEUR_CARACT\": \""+date_nais_assure+"\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               }"
	  		+ "                ],"
	  		+ "                \"Garanties\": ["
	  		+ "                               {"
	  		+ "                                               \"CODE_GARANTIE\": \"1\","
	  		+ "                                               \"CAPITAL_ASSUREE\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_GARANTIE\": \"2\","
	  		+ "                                               \"CAPITAL_ASSUREE\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_GARANTIE\": \"3\","
	  		+ "                                               \"CAPITAL_ASSUREE\": \"0\""
	  		+ "                               }"
	  		+ "                ]"
	  		+ "}"
	  		+ "";
	
	
	
	
	try {

		System.out.println("valu "+input);
		
		Form f = new Form();
		f.add("DataForCalculPrime", input);
		f.add("saveProposition", false);
		

        System.out.println("url_ws " + Constante.URL_WS_CalculerPrimeDevisVie_STB);
        
        ClientResponse response =  JerseyClient.PostReponseDuClientEDI(Constante.URL_WS_CalculerPrimeDevisVie_STB, f, reportController);
        		
       

        if (response == null) {
//          throw new RuntimeException("Failed : HTTP error code : "
//               + response.getStatus());
        	adhesionValide=false;
      	 FacesMessage msg = new FacesMessage("ERREUR", "Auccune réponse au WS de Tarification!");
    		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
    		  return false;
      }
        
        
        
        if (response.getStatus() != 200) {
        	adhesionValide=false;
//            throw new RuntimeException("Failed : HTTP error code : "
//                 + response.getStatus());
        	FacesMessage msg = new FacesMessage("ERREUR", "Erreur connexion au WS de Tarification, code réponse: " + response.getStatus());
  		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
  		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
  		return false;
  		  
        }

        System.out.println("Output from Server .... \n");
        //String output = response.getEntity(String.class);
        String output = response.toString();
        System.out.println(output);
        
        System.out.print(response + " " + "\n");
		
		 JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON( response.getEntity(String.class) ); 
		 
		 pnette = new BigDecimal(jsonOutput.getString("PRIME_HORS_TAXE"));
		 accessoires = new BigDecimal(jsonOutput.getString("COUT_DE_POLICE"));
		 //taxes = new BigDecimal(jsonOutput.getString("TAXE"));
		 taxes = new BigDecimal(0);
		 total = new BigDecimal(jsonOutput.getString("PRIME")); // c total pour un mois/ une trim/ un simestre/une année  = pnette + fga
		 
		 fga = new BigDecimal(jsonOutput.getString("TAXE"));
		 
		 
		 
		 if(pnette.compareTo(BigDecimal.ZERO) == 0 || total.compareTo(BigDecimal.ZERO) == 0) {
			 
			 adhesionValide=false; 
			 FacesMessage msg = new FacesMessage("Erreur WS Tarification!", "La prime est nulle, veuillez réessayer! ");
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			 return false;
		 }
		 
		 
		 detailHayya.setPnette(pnette);
		 detailHayya.setAccessoires(accessoires);
		 detailHayya.setTotal(total);
		 
		 detailHayya.setPnetteannuelle(new BigDecimal(pnette.doubleValue() * nbr_periode_pour_frac));
		 detailHayya.setTotalannuel(new BigDecimal(pnette.doubleValue() * nbr_periode_pour_frac + fga.doubleValue()));
		 
		 newAdhesion.setTaxe(taxes.doubleValue());
//		 newAdhesion.setPrimetot(total.doubleValue());
//		 newAdhesion.setPrimetot(pnette.doubleValue() * nbr_periode_pour_frac + fga.doubleValue()); // prime total pour toute une année
		 newAdhesion.setPrimetot(pnette.doubleValue()  + fga.doubleValue());
		 newAdhesion.setMtPrime(pnette.doubleValue()); // prime nette pour un mois / une trim/ un simestre/une année 
		 newAdhesion.setFga(fga.doubleValue());
		 
		 newProposition.setTaxe(taxes.doubleValue());
//		 newProposition.setPrimetot(total.doubleValue());
//		 newProposition.setPrimetot(pnette.doubleValue() * nbr_periode_pour_frac + fga.doubleValue());
		 newProposition.setPrimetot(pnette.doubleValue()  + fga.doubleValue());
		 newProposition.setMtPrime(pnette.doubleValue());
		 		 
		 detailHayya.setCommission(accessoires);
		 detailHayya.setFa(fga);
		 
		 detailHayya.setClientWs(clientBean.isFromWS());
		 
		 // 1. details garanties
		 
		 JSONArray detailsGarantieListJSON = jsonOutput.getJSONArray("DetailsGarantie");
		  detailsGaranties = new ArrayList<>();
		  detailsGarantieHayyas = new  ArrayList<>();
			
			
		 
		 List<JSONObject> detailsGarantiesJ = new ArrayList<>();
		 		 		 
		  for (int i = 0; i < detailsGarantieListJSON.size(); i++) {
			  detailsGarantiesJ.add(detailsGarantieListJSON.getJSONObject(i));
			  
			  DetailsGarantieDTO detailsGarantie = new DetailsGarantieDTO();
			  
			  detailsGarantie.setCode(detailsGarantieListJSON.getJSONObject(i).getString("CODE_GARANTIE"));
			  detailsGarantie.setCapital(new BigDecimal(detailsGarantieListJSON.getJSONObject(i).getString("CAPITAL_ASSUREE")));
			  detailsGarantie.setPrime(new BigDecimal(detailsGarantieListJSON.getJSONObject(i).getString("PRIME_COMPTANT")));
			  detailsGarantie.setLibelle(detailsGarantieListJSON.getJSONObject(i).getString("LIBELLE_GARANTIE"));
			  detailsGarantie.setTaux(new BigDecimal(detailsGarantieListJSON.getJSONObject(i).getString("TAUX_REDUCTION")));
			  
			  
			  detailsGaranties.add(detailsGarantie);
			  
			  //cas insertion dans BD
			  DetailsGarantieHayya detailsGarantieHayya = new DetailsGarantieHayya();
			  
			  detailsGarantieHayya.setCode(Integer.parseInt(detailsGarantieListJSON.getJSONObject(i).getString("CODE_GARANTIE")));
			  detailsGarantieHayya.setCapital(new BigDecimal(detailsGarantieListJSON.getJSONObject(i).getString("CAPITAL_ASSUREE")));
			  detailsGarantieHayya.setPrime(new BigDecimal(detailsGarantieListJSON.getJSONObject(i).getString("PRIME_COMPTANT")));
			  detailsGarantieHayya.setLibelle(detailsGarantieListJSON.getJSONObject(i).getString("LIBELLE_GARANTIE"));
			  detailsGarantieHayya.setTaux(new BigDecimal(detailsGarantieListJSON.getJSONObject(i).getString("TAUX_REDUCTION")));
			  
			  
			  detailsGarantieHayyas.add(detailsGarantieHayya);
			  
		    System.out.println("LIBELLE_GARANTIE "+detailsGarantieListJSON.getJSONObject(i).getString("LIBELLE_GARANTIE"));
		    System.out.println("CAPITAL_ASSUREE "+detailsGarantieListJSON.getJSONObject(i).getString("CAPITAL_ASSUREE"));
		    System.out.println("PRIME_COMPTANT "+detailsGarantieListJSON.getJSONObject(i).getString("PRIME_COMPTANT"));
		  }
		  
		  
		  // 2.Details Evolution Garantie   et   bilan financier
		  
		  JSONArray DetailsEvolutionGarantieListJSON = jsonOutput.getJSONArray("DetailsEvolutionGarantie");
		  detailsEvolutionGaranties = new ArrayList<>();
		  detailsEvolutionGarantieHayyas = new ArrayList<>();
		  
		  
		 /// pour bilan financier 
		  BigDecimal primeversee_ = new BigDecimal(0);
		  BigDecimal epargneprev_ = new BigDecimal(0);
		  BigDecimal gainfinancier_ = new BigDecimal(0);
		  BigDecimal econimpot_ = new BigDecimal(0);
		  BigDecimal gaintotal_ = new BigDecimal(0);
		/// fin pour bilan financier 
			 
			 List<JSONObject> DetailsEvolutionGarantieJ = new ArrayList<>();
			 
			  for (int i = 0; i < DetailsEvolutionGarantieListJSON.size(); i++) {
				  DetailsEvolutionGarantieJ.add(DetailsEvolutionGarantieListJSON.getJSONObject(i));
				  
				  DetailsEvolutionGarantie detailsEvolutionGarantie = new DetailsEvolutionGarantie();
				  
				  detailsEvolutionGarantie.setAnnee(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Année")));
				  detailsEvolutionGarantie.setPrime_comm(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Prime commerciale")));
				  detailsEvolutionGarantie.setPrime_capit(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Prime à capitaliser")));
				  detailsEvolutionGarantie.setCapital_tmg(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Capital constitué TMG")));
				  detailsEvolutionGarantie.setCapital_taux_reel(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Capital constitué au Taux Réel")));
				  detailsEvolutionGarantie.setBenefices_realises(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Bénéfices Réalisés")));
				  detailsEvolutionGarantie.setParticipation_benef(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Participation aux Bénéfices")));
				  detailsEvolutionGarantie.setEpargne_fin_annee(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne prévisionnelle avec PB")));
				  detailsEvolutionGarantie.setGain_financier(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Gain Financier")));
				  detailsEvolutionGarantie.setGain_fisc_annuel(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Gain fiscal Annuel")));
				  detailsEvolutionGarantie.setValeur_rachat(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Valeur rachat")));
				  detailsEvolutionGarantie.setCumul_vers(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Cumul Versements")));
				 
				  detailsEvolutionGaranties.add(detailsEvolutionGarantie);
				  
				  
				  //cas insertion bd
				  
				  
				  
				  DetailsEvolutionGarantieHayya detailsEvolutionGarantieHayya = new DetailsEvolutionGarantieHayya();
				  
				  detailsEvolutionGarantieHayya.setAnnee(Integer.parseInt(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Année")));
				  detailsEvolutionGarantieHayya.setPrime_comm(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Prime commerciale")));
				  detailsEvolutionGarantieHayya.setPrime_capit(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Prime à capitaliser")));
				  detailsEvolutionGarantieHayya.setCapital_tmg(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Capital constitué TMG")));
				  detailsEvolutionGarantieHayya.setCapital_taux_reel(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Capital constitué au Taux Réel")));
				  detailsEvolutionGarantieHayya.setBenefices_realises(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Bénéfices Réalisés")));
				  detailsEvolutionGarantieHayya.setParticipation_benef(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Participation aux Bénéfices")));
				  detailsEvolutionGarantieHayya.setEpargne_fin_annee(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne prévisionnelle avec PB")));
				  detailsEvolutionGarantieHayya.setGain_financier(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Gain Financier")));
				  detailsEvolutionGarantieHayya.setGain_fisc_annuel(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Gain fiscal Annuel")));
				  detailsEvolutionGarantieHayya.setValeur_rachat(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Valeur rachat")));
				  detailsEvolutionGarantieHayya.setCumul_vers(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Cumul Versements")));
					
				  detailsEvolutionGarantieHayyas.add(detailsEvolutionGarantieHayya);
				  
				  
				  
				/// pour bilan financier
				  primeversee_ = primeversee_.add(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Prime commerciale")));
				  
				  if(i == DetailsEvolutionGarantieListJSON.size()-1) {
					  
					  epargneprev_ = new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne prévisionnelle avec PB"));
				       
//					  gainfinancier_ = new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Gain Financier"));
				  }
				  
				  econimpot_ = econimpot_.add(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Gain fiscal Annuel")));
				///fin  pour bilan financier
			  }
			  
			  
			/// pour bilan financier + add BD
			  
			
			  gainfinancier_ = epargneprev_.subtract(primeversee_);
			  gaintotal_ = gainfinancier_.add(econimpot_);
			  
//			   gaintotal_ = ((gaintotal_.add(epargneprev_)).subtract(primeversee_)).add(econimpot_);
			   bilanFinancierHayya.setPrimeversee(primeversee_);
			   bilanFinancierHayya.setEpargneprev(epargneprev_);
			   bilanFinancierHayya.setGainfinancier(gainfinancier_);
			   bilanFinancierHayya.setEconimpot(econimpot_);			  			 
			   bilanFinancierHayya.setGaintotal(gaintotal_);
			   
			   bilanFinancierHayyaDTO.setPrimeversee(primeversee_);
			   bilanFinancierHayyaDTO.setEpargneprev(epargneprev_);
			   bilanFinancierHayyaDTO.setGainfinancier(gainfinancier_);
			   bilanFinancierHayyaDTO.setEconimpot(econimpot_);			  			 
			   bilanFinancierHayyaDTO.setGaintotal(gaintotal_);
			   
				  System.out.println("primeversee_"+primeversee_);
				  System.out.println("epargneprev_"+epargneprev_);
				  System.out.println("gainfinancier_"+gainfinancier_);
				  System.out.println("econimpot_"+econimpot_);
				  System.out.println("gaintotal_"+gaintotal_);
			   
			   
			 /// fin pour bilan financier + add BD
		   
		  System.out.println(jsonOutput);
		  
		 System.out.println(jsonOutput.toString());
		  
		 System.out.println(jsonOutput.getString("message"));

      } catch (Exception e) {

        e.printStackTrace();
        adhesionValide=false;
        
        FacesMessage msg = new FacesMessage("ERREUR", "Erreur interne liée au WS de Tarification! ");
		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		  return false;

      }

     
	this.selectedProposition = null;
	
	//sendPost();
	//return "tarif7ayya?faces-redirect=true";
	if(adhesionValide){
//		return "tarif7ayya?faces-redirect=true";
		return true;
	}
	else {
//		return "tarif7ayya?faces-redirect=false";
		return false;
	}
	//return null;
	
	}

/*
private void sendPost() throws Exception {

    // form parameters
    RequestBody formBody = new FormBody.Builder()
    		 .add("DataForCalculPrime",input)
//            .add("username", "abc")
//            .add("password", "123")
//            .add("custom", "secret")
            .build();

    Request request = new Request.Builder()
//            .url("https://httpbin.org/post")
            .url("http://172.31.44.31/PROASSUR.APIS.INTEG/api/devis/CalculerPrimeDevisVie")
            //.addHeader("User-Agent", "OkHttp Bot")
            //.header("Content-Type", "application/x-www-form-urlencoded")
            .post(formBody)
            .build();

    try (Response response = httpClient.newCall(request).execute()) {

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        // Get response body
        System.out.println(response.body().string());
    } catch (Exception e) {
        e.printStackTrace();
    }

}
*/


/////// cas beneficiaire vie et deces



/*****cas vie ****/

   private Person enfant = new Person();
   List<Person> enfants = new ArrayList<Person>();

    public List<Person> getEnfantList() {

    	
//    	 Person person1 = new Person(1, "Mert", "Caliskan", "emailaddress@gmail.com");
//    	 lsit.add(person1);
//         Person person2 = new Person(2, "Steve", "Millidge", "emailaddress1@c2b2.co.uk");
//    	 lsit.add(person2);
//         Person person3 = new Person(3, "Andrew", "Pielage", "emailaddress2@c2b2.co.uk");
//    	 lsit.add(person3);
    	 
    	 return enfants;
    }

    public void saveEnfant() {
    	
    	enfants.remove(enfant);
    	
    	enfants.add(enfant);
    	enfant = new Person();
    }

    public void deleteEnfant(Person person) {
    	enfants.remove(person);
    }

    public void setEnfant(Person person) {
        this.enfant = person;
    }

    public Person getEnfant() {
        return enfant;
    }
    
    
    
    
    
	   private Person autre = new Person();
	   List<Person> autres = new ArrayList<Person>();

	    public List<Person> getAutreList() {

	    	

	    	 
	    	 return autres;
	    }

	    public void saveAutre() {
	    	
	    	autres.remove(autre);
	    	
	    	autres.add(autre);
	    	autre = new Person();
	    }

	    public void deleteAutre(Person person) {
	    	autres.remove(person);
	    }

	    public void setAutre(Person person) {
	        this.autre = person;
	    }

	    public Person getAutre() {
	        return autre;
	    }
	    
	    
	    
	    
	   /**** cas deces *********/ 

	    //defaut
	    private Person defaut = new Person();
	    List<Person> defauts = new ArrayList<Person>();
	    
	    
	    public List<Person> getDefautList() {
	        return defauts;
	    }

	    public void saveDefaut() {
	    	defauts.remove(defaut);
	    	
	    	defauts.add(defaut);
	    	defaut = new Person();
	    }

	    public void deleteDefaut(Person person) {
	    	defauts.remove(person);
	    }

	    public void setDefaut(Person person) {
	        this.defaut = person;
	    }

	    public Person getDefaut() {
	        return defaut;
	    }

	    
	    // enfant
	    private Person enfantd = new Person();
	    List<Person> enfantds = new ArrayList<Person>();
	    
	    public List<Person> getEnfantdList() {
	        return enfantds;
	    }

	    public void saveEnfantd() {
	    	enfantds.remove(enfantd);
	    	
	    	enfantds.add(enfantd);
	    	enfantd = new Person();
	    }

	    public void deleteEnfantd(Person person) {
	    	enfantds.remove(person);
	    }

	    public void setEnfantd(Person person) {
	        this.enfantd = person;
	    }

	    public Person getEnfantd() {
	        return enfantd;
	    }
	    
	    
	    // conjoint
	    private Person conjoint = new Person();
	    List<Person> conjoints = new ArrayList<Person>();
	    
	    public List<Person> getConjList() {
	        return conjoints;
	    }

	    public void saveConj() {
	    	conjoints.remove(conjoint);
	    	
	    	conjoints.add(conjoint);
	    	conjoint = new Person();
	    }

	    public void deleteConj(Person person) {
	    	conjoints.remove(person);
	    }

	    public void setConjoint(Person person) {
	        this.conjoint = person;
	    }

	    public Person getConjoint() {
	        return conjoint;
	    }
	    
	    
	    // parents
	    private Person parent = new Person();
	    List<Person> parents = new ArrayList<Person>();
	    
	    public List<Person> getParentList() {
	        return parents;
	    }

	    public void saveParent() {
	    	parents.remove(parent);
	    	
	    	parents.add(parent);
	    	parent = new Person();
	    }

	    public void deleteParent(Person person) {
	    	parents.remove(person);
	    }

	    public void setParent(Person person) {
	        this.parent = person;
	    }

	    public Person getParent() {
	        return parent;
	    }

	    
	    //autres
	    private Person autred = new Person();
		   List<Person> autreds = new ArrayList<Person>();

		    public List<Person> getAutredList() {

		    	

		    	 
		    	 return autreds;
		    }

		    public void saveAutred() {
		    	
		    	autreds.remove(autred);
		    	
		    	autreds.add(autred);
		    	autred = new Person();
		    }

		    public void deleteAutred(Person person) {
		    	autreds.remove(person);
		    }

		    public void setAutred(Person person) {
		        this.autred = person;
		    }

		    public Person getAutred() {
		        return autred;
		    }	
	
	
/************************** fin partie I *****************************/	
	
		    
		    
/************************** partie II *****************************/
			//getter and setter
		    
			public void setEchDateString(String echDateString) {
				this.echDateString = echDateString;
			}
			
			
			public String getEchDateString() {
				
				Format formatter = new SimpleDateFormat("dd/MM/yyyy");
				return  formatter.format(getEchDate());
				
			}
			
		
			public Double getPrimeComptant() {
				
				Double mntt = getTotal().doubleValue();
//				DetailHayya newDetailHayya = getDetailHayya();
//				switch(getFrac()){
//				   
//			       case 1: 
//			    	   mntt =  getTotal().doubleValue();
//					   
//			           break;
//			   
//			       case 2:
//			    	   mntt = newDetailHayya.getPnette().doubleValue()*2 + newDetailHayya.getFa().doubleValue();
//						
//			           break;
//			   
//			       case 3:
//			    	   mntt = newDetailHayya.getPnette().doubleValue()*4 + newDetailHayya.getFa().doubleValue();
//						
//			           break;
//			           
//			       case 4:
//			    	   mntt = newDetailHayya.getPnette().doubleValue()*12 + newDetailHayya.getFa().doubleValue();
//						
//			           break;
		//	
//			       default:
//			    	   mntt = getTotal().doubleValue();
//						
//			           break;
//			   }
				
				
				return  mntt;
				
			}
			
			
		public int getNombrePeriodePourFract() {
				
				int mntt = getNbr_periode_pour_frac();
				
				
				return  mntt;
				
			}





			public BigDecimal getPnette_annuel() {
				return new BigDecimal(
						getPnette().doubleValue() * getNbr_periode_pour_frac()
						);
			}


			public void setPnette_annuel(BigDecimal pnette_annuel) {
				this.pnette_annuel = pnette_annuel;
			}


	


			


			public BigDecimal getTotal_annuel() {
				
				return new BigDecimal(
						getPnette().doubleValue() * getNbr_periode_pour_frac()
						+ getFga().doubleValue()
						);
			}


			public void setTotal_annuel(BigDecimal total_annuel) {
				this.total_annuel = total_annuel;
			}

			




			
		 

			public DetailHayya getNewDetailHayya() {
				return newDetailHayya;
			}


			public void setNewDetailHayya(DetailHayya newDetailHayya) {
				this.newDetailHayya = newDetailHayya;
			}
			
			
			
			
			public List<DetailsGarantieHayya> getNewDetailsGarantieHayyaList() {
				return newDetailsGarantieHayyaList;
			}


			public void setNewDetailsGarantieHayyaList(List<DetailsGarantieHayya> newDetailsGarantieHayyaList) {
				this.newDetailsGarantieHayyaList = newDetailsGarantieHayyaList;
			}


			public HayyaDTO getNewHayyaDTO() {
				return newHayyaDTO;
			}


			public void setNewHayyaDTO(HayyaDTO newHayyaDTO) {
				this.newHayyaDTO = newHayyaDTO;
			}
			
			
			public ReportController getReportController() {
				return reportController;
			}

			public void setReportController(ReportController reportController) {
				this.reportController = reportController;
			}
			
			
			
			// fonctions
			








			public String returnToDevis7ayya(){
				
				return "devis_7ayya";
			}
			
			
			public String validerProposition() {
				
				return "contrat7ayya?faces-redirect=true";
			}
			
			public void saveProposition() {
				
				Adhesion adhesion = getNewAdhesion();
				Proposition proposition = getNewProposition();
				DetailHayya detailHayya = getDetailHayya();
				List<DetailsGarantieHayya> detailsGarantieHayyas  = getDetailsGarantieHayyas();
				List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas = getDetailsEvolutionGarantieHayyas();
				
				BilanFinancierHayya bilanFinancierHayya = getBilanFinancierHayya();
				
				Client client = proposition.getClient();
				
				if(clientDAO.findByIdentifiant(client.getIdentifiant())!=null)
				{
					System.out.println("client existant");
					client.setIdClient( clientDAO.findByIdentifiant(client.getIdentifiant()).getIdClient()); 
					proposition.setClient(client);

				}
				
				newHayyaDTO = hayyaServiceInter.createProposition(proposition, detailHayya, 
						detailsGarantieHayyas, detailsEvolutionGarantieHayyas, bilanFinancierHayya);
				
				newProposition = newHayyaDTO.getProposition();
				
				newDetailHayya = getDetailHayya();
				
				newDetailsGarantieHayyaList = getDetailsGarantieHayyas();
				//newDetailHayya = hayyaServiceInter.createDetailHayya(detailHayya, newProposition);
				
//				JsfUtil.addSuccessMessage(
//						"Proposition enregistrée avec le numéro: "
//								+ newProposition.getnProposition(), "!");
				
				
				

				
				System.out.println("proposition sous "+proposition.getPolice().getNPolice());
				
				setDisabledGenerateDevis(false);
				
				propositionIsSaved = true;
				
				//return "contrat7ayya?faces-redirect=true";
			}			
			
			
			public void generateDevis7ayya() {
				
				Adhesion adhesion = getNewAdhesion();
				Proposition proposition = getNewProposition();
				DetailHayya detailHayya = getDetailHayya();
				List<DetailsGarantieHayya> detailsGarantieHayyas  = getDetailsGarantieHayyas();
				List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas = getDetailsEvolutionGarantieHayyas();
				
				BilanFinancierHayya bilanFinancierHayya = getBilanFinancierHayya();
				
				Client client = proposition.getClient();
				
				if(clientDAO.findByIdentifiant(client.getIdentifiant())!=null)
				{
					System.out.println("client existant");
					client.setIdClient( clientDAO.findByIdentifiant(client.getIdentifiant()).getIdClient()); 
					proposition.setClient(client);

				}
				
				newHayyaDTO = hayyaServiceInter.createProposition(proposition, detailHayya, 
						detailsGarantieHayyas, detailsEvolutionGarantieHayyas, bilanFinancierHayya);
				
				newProposition = newHayyaDTO.getProposition();
				
				newDetailHayya = getDetailHayya();
				
				newDetailsGarantieHayyaList = getDetailsGarantieHayyas();
				//newDetailHayya = hayyaServiceInter.createDetailHayya(detailHayya, newProposition);
				
//				JsfUtil.addSuccessMessage(
//						"Proposition enregistrée avec le numéro: "
//								+ newProposition.getnProposition(), "!");
				
				
				FacesContext facesContext = FacesContext.getCurrentInstance();
				Flash flash = facesContext.getExternalContext().getFlash();
				flash.setKeepMessages(true);
				
				
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Proposition enregistrée avec le numéro:", 
						newProposition.getnProposition()+"!"));

				
				System.out.println("proposition "+proposition.getPolice().getNPolice());
				
				setDisabledGenerateDevis(false);
				
				propositionIsSaved = true;
				
				//return "contrat7ayya?faces-redirect=true";
			}
			
			
			public String suiteContrat7ayya() {
				
				return "contrat7ayya?faces-redirect=true";
				
			}		    
		    
			
			public void generateDevis() {
				
				
				String path = reportController.getRealPath()
						+ "Proposition_hayaa_v9.jasper";

				try {

					// fonction d'impression du devis
					Map<String, Object> parameters = new HashMap();
					
					
					List<DetailsGarantieHayya> detailsGarantieHayyas  = getDetailsGarantieHayyas();
					
					BilanFinancierHayya bilanFinancierHayya = getBilanFinancierHayya();
					
				
						  BigDecimal epargne = detailsGarantieHayyas.get(0).getCapital();
						  BigDecimal deces = detailsGarantieHayyas.get(1).getCapital();
						  BigDecimal iad = detailsGarantieHayyas.get(2).getCapital();
						
					

					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					
					
					if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000")) {
						
						parameters.put("produit_fr","INVESTI");
						parameters.put("produit_ar","ادخـــــــــار 'أنفست'");
						
					}
						 
					else { 
						
						parameters.put("produit_fr","FUTURE DREAMS");
						parameters.put("produit_ar","ادخـــــــــار 'فيوتر دريمز'");
						
					}

					parameters.put("agence", utilisateur.getAgence().getCodeAgence());
					parameters.put("contrat",newProposition.getnProposition());
					parameters.put("effet", df.format(newProposition.getDatDebut()));
					parameters.put("duree", Integer.toString((int)newProposition.getDuree())+" ans");
					parameters.put("echeance", df.format(newProposition.getDatFin()));
					parameters.put("capital", newDetailsGarantieHayyaList.get(0).getCapital());
					parameters.put("souscr", clientBean.getClient().getNomClient()+" "+clientBean.getClient().getPrenomClient());
					parameters.put("adr_souscr", clientBean.getClient().getAdrClient());
					parameters.put("id_souscr", clientBean.getClient().getIdentifiant());
					parameters.put("assure", clientBean.getClient().getNomClient()+" "+clientBean.getClient().getPrenomClient());
					parameters.put("adr_assure", clientBean.getClient().getAdrClient());
					parameters.put("id_assure", clientBean.getClient().getIdentifiant());
					parameters.put("naiss_assure", df.format(clientBean.getClient().getDatNaiss()));
					
					if(clientBean.getClient().getSexe().equalsIgnoreCase("M"))
						parameters.put("sexe_assure", "Homme");
					else
						parameters.put("sexe_assure", "Femme");
					
					String LibelleProfession = " ";
		 	   		if (clientBean.getClient().getProfession() != null ) {
		 	   			  // doSomething
		 	   		LibelleProfession = clientBean.getClient().getProfession().getLibelleProfession();
		 	   			}
					
					parameters.put("prf_assure", LibelleProfession);
					parameters.put("tel_assure", clientBean.getClient().getTel());
					parameters.put("nom_benef", clientBean.getClient().getNomClient());
					parameters.put("cin_benef", clientBean.getClient().getIdentifiant());
					
					parameters.put("epargne", epargne);
					parameters.put("deces", deces);
					parameters.put("iad", iad);
					
					parameters.put("nom_benef_d", null);
					parameters.put("cin_benef_d", null);
					parameters.put("parente_d", null);
					parameters.put("poucent_cap_d", null);
					
					//parameters.put("prime_tot", newProposition.getPrimetot());
					parameters.put("frais", new BigDecimal(0.000));
					parameters.put("fga", newDetailHayya.getFa());
					//parameters.put("taxe", Double.parseDouble("0.000"));
					parameters.put("taxe", newProposition.getTaxe());
					//parameters.put("prime_nette", newDetailHayya.getPnette());
					
				
					
					parameters.put("primeversee", bilanFinancierHayya.getPrimeversee());
					parameters.put("epargneprev", bilanFinancierHayya.getEpargneprev());
					parameters.put("gainfinancier", bilanFinancierHayya.getGainfinancier());
					parameters.put("econimpot", bilanFinancierHayya.getEconimpot());
					parameters.put("gaintotal", bilanFinancierHayya.getGaintotal());
					
					
					switch(getFrac()){
					   
				       case 1: 
				    	   parameters.put("prime_tot", newProposition.getPrimetot());
						   parameters.put("prime_nette", newDetailHayya.getPnette());
					
				    	   parameters.put("annuel", "X");
				           break;
				   
				       case 2:
				    	   parameters.put("prime_tot", newDetailHayya.getPnette().doubleValue()*2 + newDetailHayya.getFa().doubleValue());
							parameters.put("prime_nette", new BigDecimal( newDetailHayya.getPnette().doubleValue()*2));
						   
				    	   parameters.put("semestriel", "X");
				           break;
				   
				       case 3:
				    	   parameters.put("prime_tot", newDetailHayya.getPnette().doubleValue()*4 + newDetailHayya.getFa().doubleValue());
							parameters.put("prime_nette", new BigDecimal( newDetailHayya.getPnette().doubleValue()*4));
						   
				    	   parameters.put("trimestriel", "X");
				           break;
				           
				       case 4:
				    	   parameters.put("prime_tot", newDetailHayya.getPnette().doubleValue()*12 + newDetailHayya.getFa().doubleValue());
							parameters.put("prime_nette", new BigDecimal( newDetailHayya.getPnette().doubleValue()*12));

				    	   parameters.put("mensuel", "X");
				           break;
				
				       default:
				    	   parameters.put("prime_tot", newProposition.getPrimetot());
							parameters.put("prime_nette", newDetailHayya.getPnette());
							
				    	   parameters.put("unique", "X");
				           break;
				   }
					
					parameters.put("evol_p", newDetailHayya.getCroissHayya().getCroissance());
					
					if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000")) {
						
						parameters.put("tx_benef", "80%");
					
					}else {
						
						parameters.put("tx_benef", "90%");	
						
					}
					

					parameters.put("tx_interef_gar", "4%");
					
					parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
					
					parameters
					.put("star",  reportController.getLogoJasper());
					
					parameters.put("imagesociete", utilisateur.getAgence().getSociete()
							.getCodSociet() + ".png");
					
					/*
					 ArrayList<Order> orders = new ArrayList<>();
				        
				        orders.add(new Order("Item 1", new BigDecimal("5.4"), 4));
				        orders.add(new Order("Item 2", new BigDecimal("2.3"), 3));
				        orders.add(new Order("Item 3", new BigDecimal("1.5"), 8));
				        orders.add(new Order("Item 4", new BigDecimal("4.0"), 2));
				        */
				        
				        List<DetailsEvolutionGarantie> orders = getDetailsEvolutionGaranties();

				        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(orders);

				        parameters.put("datasource", ds);
				        
				        parameters.put("report", reportController.getRealPath());
				        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH); 
				         
				        

					JasperPrint print = JasperFillManager.fillReport(path,
							parameters, new JREmptyDataSource());
					byte[] bytes = JasperExportManager.exportReportToPdf(print);

					FacesContext fc = FacesContext.getCurrentInstance();
					ExternalContext ec = fc.getExternalContext();
					ec.responseReset();
					ec.setResponseContentType("application/pdf");
					ec.setResponseContentLength(bytes.length);
					ec.setResponseHeader("Content-Disposition", "inline; filename=\""
							+  "rapport.pdf" + "\"");

					OutputStream output = ec.getResponseOutputStream();
					output.write(bytes);
					fc.responseComplete();


				} catch (Exception e) {
					e.printStackTrace();
				}


				
				
			}		    
		    
		    
/************************** fin partie II *****************************/	
			
			
/************************** partie III ***********************************/

			//getter and setter
			

			



			



			

			public boolean isValider_impot() {
				return valider_impot;
			}
			public void setValider_impot(boolean valider_impot) {
				this.valider_impot = valider_impot;
			}
			
			
			
			
			
			
			public Date getDateImpot_init() {
				return dateImpot_init;
			}
			public void setDateImpot_init(Date dateImpot_init) {
				this.dateImpot_init = dateImpot_init;
			}
			public Date getDateImpot() {
				return dateImpot;
			}
			public void setDateImpot(Date dateImpot) {
				this.dateImpot = dateImpot;
			}
			
			
			public Date getDateImpotDebut() {
				return dateImpotDebut;
			}



			public void setDateImpotDebut(Date dateImpotDebut) {
				this.dateImpotDebut = dateImpotDebut;
			}



			public Date getDateImpotFin() {
				return dateImpotFin;
			}



			public void setDateImpotFin(Date dateImpotFin) {
				this.dateImpotFin = dateImpotFin;
			}
			
			
//			public String getVie() {
//				return vie;
//			}
		//
		//
//			public void setVie(String vie) {
//				this.vie = vie;
//			}
		//
		//
//			public String getDeces() {
//				return deces;
//			}
		//
		//
//			public void setDeces(String deces) {
//				this.deces = deces;
//			}
			
			
			
		//	
//			public String getModepaie() {
//				return modepaie;
//			}
		//
		//
//			public void setModepaie(String modepaie) {
//				this.modepaie = modepaie;
//			}
		//
		//	
		//	
		//
		//
		//
//			public String getBanque() {
//				return banque;
//			}
		//
		//
//			public void setBanque(String banque) {
//				this.banque = banque;
//			}
		//
		//
		//
		//
		//
		//
//			public String getAgence() {
//				return agence;
//			}
		//
		//
//			public void setAgence(String agence) {
//				this.agence = agence;
//			}
		//
		//
//			public String getRib() {
//				return rib;
//			}
		//
		//
//			public void setRib(String rib) {
//				this.rib = rib;
//			}
		//
		//
//			public String getPayeur() {
//				return payeur;
//			}
		//
		//
//			public void setPayeur(String payeur) {
//				this.payeur = payeur;
//			}
		//
		//
//			public String getNomPayeur() {
//				return nomPayeur;
//			}
		//
		//
//			public void setNomPayeur(String nomPayeur) {
//				this.nomPayeur = nomPayeur;
//			}
		//
		//
//			public String getCinPayeur() {
//				return cinPayeur;
//			}
		//
		//
//			public void setCinPayeur(String cinPayeur) {
//				this.cinPayeur = cinPayeur;
//			}
		//
		//
//			public String getAdressePayeur() {
//				return adressePayeur;
//			}
		//
		//
//			public void setAdressePayeur(String adressePayeur) {
//				this.adressePayeur = adressePayeur;
//			}
		//
		//
//			public String getDispoSpec() {
//				return dispoSpec;
//			}
		//
		//
//			public void setDispoSpec(String dispoSpec) {
//				this.dispoSpec = dispoSpec;
//			}
		//

			
			




			public String getNumquit() {
				return numquit;
			}





			public boolean isTestqnb() {
				return testqnb;
			}


			public void setTestqnb(boolean testqnb) {
				this.testqnb = testqnb;
			}


			public boolean isTest() {
				return test;
			}


			public void setTest(boolean test) {
				this.test = test;
			}
			
			
			


//			public boolean isTestImpression() {
//				return testImpression;
//			}
		//
		//
//			public void setTestImpression(boolean testImpression) {
//				this.testImpression = testImpression;
//			}


			public void setNumquit(String numquit) {
				this.numquit = numquit;
			}




			public String getMnt_total() {
				return mnt_total;
			}


			public void setMnt_total(String mnt_total) {
				this.mnt_total = mnt_total;
			}


			public String getMnt_nette() {
				return mnt_nette;
			}


			public void setMnt_nette(String mnt_nette) {
				this.mnt_nette = mnt_nette;
			}


			public String getMnt_capital() {
				return mnt_capital;
			}


			public void setMnt_capital(String mnt_capital) {
				this.mnt_capital = mnt_capital;
			}


			public Integer getJourPrelevement() {
			    return jourPrelevement;
			}

			public void setJourPrelevement(Integer jourPrelevement) {
			    this.jourPrelevement = jourPrelevement;
			}

			public List<SelectItem> getJoursDisponibles() {
			    return joursDisponibles;
			}
				
	 

			public List<SelectItem> getMoisDisponibles() {
			    return moisDisponibles;
			}
			

			public Integer getMoisPrelevement() {
				return moisPrelevement;
			}



			public void setMoisPrelevement(Integer moisPrelevement) {
				this.moisPrelevement = moisPrelevement;
			}

			
			// fonctions
			




			
			
			





			public void ValiderProposition() {
				
				
				if(!propositionIsSaved ) {
					saveProposition();
		        }
				
				
				
				FacesContext facesContext = FacesContext.getCurrentInstance();
				Flash flash = facesContext.getExternalContext().getFlash();
				flash.setKeepMessages(true);
				
				int tot_quot_deces = 0;
				
				int tot_quot_vie = 0;
				
				
				try {
				
				if(!isDisabledValidationAdhesion()) {
					
					
					// beneficiaire cas vie
					
			        List<Person> personnes = new ArrayList<Person>();
			        
			        System.out.println("vie "+getVie());
			        
			        if(getVie().equals("1")) {//assure
			        	Person assure = new Person();
			        	assure.setNom(getClientBean().getClient().getNomClient());
			        	assure.setPrenom(getClientBean().getClient().getPrenomClient());
			        	assure.setCin(getClientBean().getClient().getIdentifiant());
			        	assure.setLien("assuré");
			        	assure.setQuote(100);
			        	personnes = new ArrayList<Person>();
			        	personnes.add(assure);
			        	System.out.println("personnes "+getClientBean().getClient().getNomClient());
			        	System.out.println("personnes "+personnes.size());
			        	
			        	tot_quot_vie = 100;
			        }
			        
			        if(getVie().equals("2")) {// enfants
			        	personnes = new ArrayList<Person>();
			        	personnes = enfants;
			        	System.out.println("personnes "+personnes.size());
			        	
			        	for(Person pers_vie :personnes) {
			        		
			        		tot_quot_vie = pers_vie.getQuote() + tot_quot_vie;
			        	}
			        }
			        
			        if(getVie().equals("3")) {// autres
			        	personnes = new ArrayList<Person>();
			        	personnes = autres;
			        	System.out.println("personnes "+personnes.size());
			        	
			        	for(Person pers_vie :personnes) {
			        		
			        		tot_quot_vie = pers_vie.getQuote() + tot_quot_vie;
			        	}
			        }
			        
			        // beneficiaire cas deces 
			        
			        List<Person> personnes_deces = new ArrayList<Person>();
			        
			        System.out.println("deces "+getDeces());
			        
			        if(getDeces().equals("1")) {//defaut
			        	personnes_deces = new ArrayList<Person>();
			        	personnes_deces = defauts;
			        	
			        	for(Person pers_dece :personnes_deces) {
			        		
			        		tot_quot_deces = pers_dece.getQuote() + tot_quot_deces;
			        	}
			        }
			        
			        if(getDeces().equals("2")) {// enfants
			        	personnes_deces = new ArrayList<Person>();
			        	personnes_deces = enfantds;
			        	System.out.println("personnes "+personnes_deces.size());
			        	
			        	
			        	for(Person pers_dece :personnes_deces) {
			        		
			        		tot_quot_deces = pers_dece.getQuote() + tot_quot_deces;
			        	}
			        		        	
			        }
			        
			        if(getDeces().equals("3")) {// conjoint
			        	personnes_deces = new ArrayList<Person>();
			        	personnes_deces = conjoints;
			        	System.out.println("personnes "+personnes_deces.size());
			        	
			        	for(Person pers_dece :personnes_deces) {
			        		
			        		tot_quot_deces = pers_dece.getQuote() + tot_quot_deces;
			        	}
			        }
			        
			        if(getDeces().equals("4")) {// parents
			        	personnes_deces = new ArrayList<Person>();
			        	personnes_deces = parents;
			        	System.out.println("personnes "+personnes_deces.size());
			        	
			        	for(Person pers_dece :personnes_deces) {
			        		
			        		tot_quot_deces = pers_dece.getQuote() + tot_quot_deces;
			        	}
			        }
			        
			        if(getDeces().equals("5")) {// autres
			        	personnes_deces = new ArrayList<Person>();
			        	personnes_deces = autreds;
			        	System.out.println("personnes "+personnes_deces.size());
			        	
			        	for(Person pers_dece :personnes_deces) {
			        		
			        		tot_quot_deces = pers_dece.getQuote() + tot_quot_deces;
			        	}
			        }
					
			        if(personnes.size()==0 || personnes_deces.size() ==0) {
			        	
			        	
						
						
						facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"La liste des bénéficiaires en cas de vie/décès est vide!",""));
						
						return ;
			        	
			        }
			        
			        if(tot_quot_deces > 100) {
		        		
		        		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"La quote-part en cas de décès a dépassé 100%!",""));
		        		return ;
		        	}
		        	
		        	if(tot_quot_deces < 100) {
		        		
		        		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"La quote-part en cas de décès est inférieure à 100%!",""));
		        		return ;
		        	}
		        	
			        if(tot_quot_vie > 100) {
		        		
		        		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"La quote-part en cas de vie a dépassé 100%!",""));
		        		return ;
		        	}
		        	
		        	if(tot_quot_vie < 100) {
		        		
		        		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"La quote-part en cas de vie est inférieure à 100%!",""));
		        		return ;
		        	}
			        
			        if(Integer.parseInt(getModepaie())==22) {
			        	
			      	   BigDecimal  b1 = new BigDecimal(getRib());
			     	   BigDecimal  b2 = new BigDecimal("97");
			     	   System.out.println(b1.remainder(b2)) ;
			     	   
			     	  if(b1.remainder(b2).intValue() != 0){
			    		   
			     		 
							
							
							facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									"RIB erroné!",""));
							
							return;
			     		   
			     	   }
			        }
			        
			        
			        if(isAfficherJourBlock()) {
			        	
			        	if(jourPrelevement == null) {
			        		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									"Veuillez choisir le jour de prélèvement!",""));
			        		return;  
			        	}
			        	
			        }
			        
//			        if(isAfficherAnnee()) {
//			        	
//			        	if(anneePrelevement  == null) {
//			        		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
//									"Veuillez choisir l'année de prélèvement!",""));
//			        		return;  
//			        	}
//			        	
//			        }
			        
			        if(isAfficherMois()) {
			        	
			        	if(moisPrelevement  == null) {
			        		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
									"Veuillez choisir le mois de prélèvement!",""));
			        		return;  
			        	}
			        	
			        }
			        
			        ModePaiementHayya modePaiementHayya = new ModePaiementHayya();
			       // modePaiementHayya.setFrac(Integer.parseInt(getFraction()));
//			        System.out.println("getModepaie() " +getModepaie());
			        modePaiementHayya.setFrac(getFrac());
			        modePaiementHayya.setModep(Integer.parseInt(getModepaie()));
			        modePaiementHayya.setBanque(getBanque());
			        modePaiementHayya.setAgence(getAgence());
			        modePaiementHayya.setRib(getRib());
			        modePaiementHayya.setPayeur( getPayeur() == null || getPayeur().trim().isEmpty()
			                ? null
			                        : Integer.parseInt(getPayeur().trim()));
			        modePaiementHayya.setNom(getNomPayeur());
			        modePaiementHayya.setCin(getCinPayeur());
			        modePaiementHayya.setAdresse(getAdressePayeur());
			        modePaiementHayya.setDisposition(getDispoSpec());
			        modePaiementHayya.setDatecreation(new Date());
			        modePaiementHayya.setJourPrelevement(jourPrelevement);
			        modePaiementHayya.setMoisPrelevement(moisPrelevement);
//			        modePaiementHayya.setAnneePrelevement(anneePrelevement);
					
					Adhesion adhesion = hayyaServiceInter.createAdhesion(getNewHayyaDTO().getProposition(),
							getNewAdhesion(), getNewHayyaDTO().getDetailHayya(),
					getNewHayyaDTO().getDetailsGarantieHayyas(), 
					getNewHayyaDTO().getDetailsEvolutionGarantieHayyas(),
					personnes, Integer.parseInt(getVie()),
					personnes_deces, Integer.parseInt(getDeces()),
					modePaiementHayya, getNewHayyaDTO().getBilanFinancierHayya());
					
					num_adhesion = adhesion.getNumeroAdhesion();
					

 			
					Double primetot; 
					Double primenette;
					
					Calendar cal = Calendar.getInstance();
					
					Date dateBefore01Days;
					
					switch(getFrac()){
					   
				       case 1: 
							cal.setTime(adhesion.getDatDebut());
							cal.add(Calendar.MONTH, 12);
							cal.add(Calendar.DATE, -1);
							 dateBefore01Days = cal.getTime();
							
				    	   primetot = getNewProposition().getPrimetot();
				    	   primenette = getNewDetailHayya().getPnette().doubleValue();
							    	 
				           break;
				   
				       case 2:
				    	   cal.setTime(adhesion.getDatDebut());
						    cal.add(Calendar.MONTH, 6);
						    cal.add(Calendar.DATE, -1);
						     dateBefore01Days = cal.getTime();
						    
				    	   primetot =  getNewDetailHayya().getTotal().doubleValue();
				    	   primenette =  getNewDetailHayya().getPnette().doubleValue();
//				    	   primetot = getNewDetailHayya().getPnette().doubleValue()*2 + getNewDetailHayya().getFa().doubleValue();
//				    	   primenette= getNewDetailHayya().getPnette().doubleValue()*2;

				           break;
				   
				       case 3:
				    	   cal.setTime(adhesion.getDatDebut());
						    cal.add(Calendar.MONTH, 3);
						    cal.add(Calendar.DATE, -1);
						     dateBefore01Days = cal.getTime();
						    
				    	   primetot =  getNewDetailHayya().getTotal().doubleValue();
				    	   primenette =  getNewDetailHayya().getPnette().doubleValue();
//				    	   primetot=getNewDetailHayya().getPnette().doubleValue()*4 + getNewDetailHayya().getFa().doubleValue();
//				    	   primenette= getNewDetailHayya().getPnette().doubleValue()*4;
						   
				           break;
				           
				       case 4:
				    	   cal.setTime(adhesion.getDatDebut());
						    cal.add(Calendar.MONTH, 1);
						    cal.add(Calendar.DATE, -1);
						     dateBefore01Days = cal.getTime();
						    
				    	   primetot =  getNewDetailHayya().getTotal().doubleValue();
				    	   primenette =  getNewDetailHayya().getPnette().doubleValue();
//				    	   primetot= getNewDetailHayya().getPnette().doubleValue()*12 + getNewDetailHayya().getFa().doubleValue();
//				    	   primenette= getNewDetailHayya().getPnette().doubleValue()*12;

				           break;
				
				       default:
//				    	   cal.setTime(adhesion.getDatDebut());
//							cal.add(Calendar.DATE, -1);
//							 dateBefore01Days = cal.getTime();
				    	   dateBefore01Days = adhesion.getDatDebut();
							
				    	   primetot =  getNewDetailHayya().getTotal().doubleValue();
				    	   primenette =  getNewDetailHayya().getPnette().doubleValue();
//				    	   primetot= getNewProposition().getPrimetot();
//				    	   primenette= getNewDetailHayya().getPnette().doubleValue();
						   

				           break;
				   }
					
					//insert quittance
					 numquit=serviceAssistantVoiyagetService.getNumeroQuit();
					System.out.println("numquit "+numquit);
					
					// on cherche date fin pour quittance			
					Quittance quittance=new Quittance(dateBefore01Days, adhesion.getDatDebut(), adhesion, 
							adhesion.getMtPrime(), 
							(double)0, adhesion.getTaxe(), 
							Integer.parseInt(numquit), new Date(), adhesion.getFga());
					quittanceDAO.create(quittance);
					
//					QuittanceDetails qt = new QuittanceDetails(
//							utilisateur.getAgence(), "C", 
//							null, getFrac(), 
//							primetot, primenette);
//					
//					quittanceDetailsDAO.createQuittanceDetails(qt, quittance);
					
					
					QuittanceDetails qt = new QuittanceDetails(
							quittanceDAO.searchquit(Integer.parseInt(numquit)).getIdQuittance(),
							adhesion,
					utilisateur.getAgence(), "C", 
					null, getFrac(), 
					primetot, primenette);
					
					quittanceDetailsDAO.create(qt);

					adhesionToPrintNotise = adhesion;
					
					mnt_capital = String.valueOf(getNewDetailsGarantieHayyaList().get(0).getCapital().doubleValue() );
					
					mnt_total = String.valueOf(primetot );
					
					mnt_nette = String.valueOf(primenette );
					
					
					// ajouter 14/02/2025
					if(adhesion.getNumeroAdhesion() ==null || adhesion.getNumeroAdhesion().isEmpty() || adhesion.getNumeroAdhesion().equalsIgnoreCase("")) {
						
						
			     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec de validation du contrat , cause: erreur lors d'enregistrement du n° contrat!, veuiller contacter l'administrateur.", ""));
			     		
			     		//setDisabledValidationAdhesion(false);
			     		setDisabledall(true);
			     		return;
			     		
						
					} else {
						
						if( utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000")) { // qnb
							
							adhesionToPrintNotise.setEtat("V");
							adhesionDAO.edit(adhesionToPrintNotise);
						}
						
						
						
					} /// fin ajouter 14/02/2025
					
					
					
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Adhésion enregistrée avec le numéro:", 
							adhesion.getNumeroAdhesion()+"!"));
					
					
					setDisabledValidationAdhesion(true);
					
					if (!utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000")){
						
						setTestImpression(true);
						
						
					}
					else {
						setTestImpression(false);
					}
					
					System.out.println("DisabledValidationAdhesion ==== " + isDisabledValidationAdhesion());
					
					//System.out.println("testqnb ==== " + testImpression);
				
				
				}
				
				skip = true;
				
				
				}catch (Exception e) {

					
		     		
		     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec de validation du contrat , cause: erreur lors d'enregistrement du contrat!, veuiller contacter l'administrateur.", ""));
		     		
		     		//setDisabledValidationAdhesion(false);
		     		setDisabledall(true);
		     		
		     		System.out.println("probleme de Exception  ");
		     		
					e.printStackTrace();

				  }
				
				
			}
			
			
			
			public void Encaissement_ancienne() throws NoSuchAlgorithmException, KeyManagementException{
				
				

				
				ValiderProposition();
				if(  adhesionToPrintNotise == null || adhesionToPrintNotise.getIdAdhesion() == 0) return;
				adhesionToPrintNotise.setEtat("N");

				
				adhesionToPrintNotise = adhesionDAO.merge(adhesionToPrintNotise);


				
				


				if(clientBean.getClient().getNcompte() == null || clientBean.getClient().getNcompte().equalsIgnoreCase("")){
		           
					FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

				}
					
				
			 
				
				 else {

		            

					SSLContext sc = SSLContext.getInstance("TLS");
					sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() }, new java.security.SecureRandom());
					HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
					HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){

						@Override
						public boolean verify(String arg0, SSLSession arg1) {
							// TODO Auto-generated method stub
							return false;
						}
					}); 



					//final String BASE_URI = Proprietes.URL_ENCAISS_STB;
					final String BASE_URI = Constante.URL_ENCAISS_STB;
					String input = "{\n" +
							"\n" +
							"    \"ribPayeur\":\""+clientBean.getClient().getNcompte()+"\",\n" +
							"\n" +
							"    \"ribBeneficiaire\":\"\",\n" +
							"\n" +
							"    \"montant\":\""+adhesionToPrintNotise.getMtPrime()+"\",\n" +
							"\n" +
							"    \"description\": \"Epargne Retraite '7ayya' : "+adhesionToPrintNotise.getNadhesion()+"\",\n" +
							"\n" +
							"    \"typeOpeCode\":\"OCCA20004\"\n" +
							"\n" +
							"}";

					System.out.println(input);
					
					System.out.println("erreur icciiii");

					ExampleResourceClient erc = new ExampleResourceClient("",BASE_URI);
					//System.out.println(erc.getXml());

					System.out.println("erreur icciiii2222222222222");   
					
					ClientResponse response = erc.HttpReponsePost(input);
					
					System.out.println("erreur icciiii333333333333333");
					
					if ( response == null) {
						System.out.println("probleme de connexion");
						FacesMessage msg = new FacesMessage("Echec d'encaissement , veuiller contacter l'administrateur.", "ERREUR");
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);

						setDisabledValidationAdhesion(false);
						return;
					}
					

					if ( response.getStatus() != 200) {
						System.out.println("probleme de connexion");
						FacesMessage msg = new FacesMessage("Erreur serveur (code ="+response.getStatus()+"), veuiller contacter l'administrateur.", "ERREUR");
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);

						setDisabledValidationAdhesion(false);
					}

					else{

						System.out.println("Output from Server .... \n" +response.getStatus());
						String output = response.getEntity(String.class);
						System.out.println(output);

						org.json.JSONObject obj = new org.json.JSONObject(output);

						if(obj.getString("Status").equalsIgnoreCase("Rejected")){
		                    System.out.println("Echec d'encaissement");
							FacesMessage msg = new FacesMessage("Echec d'encaissement", "ERREUR");
							msg.setSeverity(FacesMessage.SEVERITY_FATAL);
							FacesContext.getCurrentInstance().addMessage("Erreur", msg);

							setDisabledValidationAdhesion(false);


						}else{

							FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
							msg.setSeverity(FacesMessage.SEVERITY_INFO);
							FacesContext.getCurrentInstance().addMessage("Erreur", msg);
							System.out.println("Encaissement effectuée avec succès.");
							setDisabledValidationAdhesion(true);
							setTest(true);
							/**** enregistremnt ds notre !BD ****/

							adhesionToPrintNotise.setEtat("V");

							adhesionDAO.edit(adhesionToPrintNotise);
							setDisabledValidationAdhesion(true);
							OperationTransaction operationTransaction = new OperationTransaction();

							operationTransaction.setDateTrans(new Date());
							operationTransaction.setIdAdhesion(adhesionToPrintNotise); // à discuter
							operationTransaction.setRefOperation(obj.getString("Reference"));
							operationTransaction.setTypTrans("Encaissement");

							iOperationTransactionDAO.create(operationTransaction);

							//Status = 1;
							
							
						}




					}

				}
			}
			
			
			
		public void Encaissement() throws NoSuchAlgorithmException, KeyManagementException{
				
				//boolean test = true;

				
//				ValiderProposition();
				if(  adhesionToPrintNotise == null || adhesionToPrintNotise.getIdAdhesion() == 0) return;
				adhesionToPrintNotise.setEtat("N");

				
				adhesionToPrintNotise = adhesionDAO.merge(adhesionToPrintNotise);


				
				


				if(clientBean.getClient().getNcompte() == null || clientBean.getClient().getNcompte().equalsIgnoreCase("")){
		           
					FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

				}
					
				
			 
				
				 else {

		            

						String token = TokenSTB.tokenStb(reportController);
						
						if(token == null) {
							
							FacesMessage msg = new FacesMessage("ERREUR", "Echec d'encaissement: pas de réponse au WS Token!");
							  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
							  
							  //setDisabledValidationAdhesion(false);
							  setDisabledall(true);
							  return;
							
						}else {
							
							if(token.equalsIgnoreCase("exception")) {
								
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , Erreur de connexion au WS Token!", ""));
					     		
					     		//setDisabledValidationAdhesion(false);
								setDisabledall(true);
					     		
					     		System.out.println("probleme de Exception  ");
					     		
					     		return;
							}
							
							if(token.equalsIgnoreCase("not200")) {
								
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , problème de droit d'accès au WS Token!", ""));
					     		
					     		//setDisabledValidationAdhesion(false);
								setDisabledall(true);
					     		
					     		System.out.println("probleme de Exception  ");
					     		
					     		return;
							}
						}



					
					 final String BASE_URI = Constante.URL_ENCAISS_STB;
					
					 //final String BASE_URI = "https://openbank.stb.com.tn:9443/v0/payment";
					
					System.out.println("BASE_URI "+Constante.URL_ENCAISS_STB);
					
					String IdDemande = numquit;
					
					String PrimeAssurance = mnt_total;
					String AccountNumber = clientBean.getClient().getNcompte();
					String Police = adhesionToPrintNotise.getPolice().getNPolice()+ adhesionToPrintNotise.getNumeroAdhesion();
					String Cin = clientBean.getClient().getIdentifiant();
					String Nom = clientBean.getClient().getNomClient(); 
					String Prenom = clientBean.getClient().getPrenomClient();
					
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					
					String DateNaissance = df.format(clientBean.getClient().getDatNaiss());
					
					String CapitalAssure = mnt_capital;
					
					String DateEffet = df.format(adhesionToPrintNotise.getDatDebut());
					String Datefin = df.format(adhesionToPrintNotise.getDatFin());
					
					String Mobile = clientBean.getClient().getTel();
					String Mail = clientBean.getClient().getMail();
					
					String Produit = Constante.PRODUIT_HAYYA;
					
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
						
						

					    
					    com.sun.jersey.api.client.Client client = JerseyClient.ApiClient(reportController);
					    ClientResponse response = JerseyClient.PostReponseDuClientSTBEncaissement(BASE_URI, token, input, client, reportController);
					  
					    

						if ( response == null) {
							System.out.println("probleme de connexion vide");
							FacesMessage msg = new FacesMessage("Echec d'encaissement!", "Pas de réponse au WS d'encaissement!");
							msg.setSeverity(FacesMessage.SEVERITY_FATAL);
							FacesContext.getCurrentInstance().addMessage("Erreur", msg);

							//setDisabledValidationAdhesion(false);
							setDisabledall(true);
							return;
						}
					    
					    
					    if (response.getStatus() != 200) {
//							throw new RuntimeException("Failed : HTTP error code : "
//							     + response.getStatus());
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

							//setDisabledValidationAdhesion(false);
							setDisabledall(true);
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
							
							setDisabledall(true);
		                   
							FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
							msg.setSeverity(FacesMessage.SEVERITY_INFO);
							FacesContext.getCurrentInstance().addMessage("Erreur", msg);
							System.out.println("Encaissement effectuée avec succès.");
							//setDisabledValidationAdhesion(true);
							/**** enregistremnt ds notre !BD ****/

							adhesionToPrintNotise.setEtat("V");

							adhesionDAO.edit(adhesionToPrintNotise);
							//setDisabledValidationAdhesion(true);
							OperationTransaction operationTransaction = new OperationTransaction();

							operationTransaction.setDateTrans(new Date());
							operationTransaction.setIdAdhesion(adhesionToPrintNotise); // à discuter
							operationTransaction.setRefOperation(Reference);
							operationTransaction.setTypTrans("Encaissement");

							iOperationTransactionDAO.create(operationTransaction);
							
							setTestImpression(true);
							
							
							if(("2").equals(modepaie)) {
							
							API_Bassem(adhesionToPrintNotise,
									adhesionToPrintNotise.getNumeroAdhesion()+ "-" + UUID.randomUUID().toString().substring(0, 5),
								    clientBean.getClient().getNcompte(),
								    contrib,
								    "Elyssa assurance",
								    clientBean.getClient().getIdentifiant(),
								    Frequency.getLabelByCode(frac),
								    "PRELEVEMENT_BANCAIRE",
								    21,
								    FrequencyService.getNextDateAsString(effDate,frac),
								    FrequencyService.getPreviousDateWith28(getEchDate(),frac),
								    "TND",
								    adhesionToPrintNotise.getPolice().getNPolice()+ adhesionToPrintNotise.getNumeroAdhesion(),
								    "10404100143213078807", //Constante.RIB_STAR_PRELEV,
								    (moisPrelevement == null)
							        ? ""
							        : String.format("%02d", moisPrelevement),
							        (jourPrelevement == null)
							        ? ""
							        : String.format("%02d", jourPrelevement),
								    ""
								);
							
							}


						}else {
							
							 System.out.println("Echec d'encaissement");
								FacesMessage msg = new FacesMessage("Echec d'encaissement : " + Status + " " + RejectMessage, "Erreur WS d'encaissement!");
								msg.setSeverity(FacesMessage.SEVERITY_FATAL);
								FacesContext.getCurrentInstance().addMessage("Erreur", msg);

								//setDisabledValidationAdhesion(false);
								setDisabledall(true);
								return;
							
						}
					       
					        
					    	  
						
						} 
					catch (ClientHandlerException e) {

						
			     		
			     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement: problème d'appel au WS d'encaissement!", ""));
			     		
			     		//setDisabledValidationAdhesion(false);
			     		setDisabledall(true);
			     		
			     		System.out.println("probleme de ClientHandlerException  ");
			     		
						e.printStackTrace();

					  }
					
			catch (org.json.JSONException e) {

						
			     		
			     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , Erreur au niveau du retour du WS d'encaissement!", ""));
			     		
			     		//setDisabledValidationAdhesion(false);
			     		//setDesabledEncaiss(true); 
			     		setDisabledall(true);
			     		
			     		System.out.println("probleme de JSONException  ");
			     		
						e.printStackTrace();

					  }
					catch (Exception e) {

							
				     		
				     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , Erreur interne liée au niveau du WS d'encaissement!", ""));
				     		
				     		//setDisabledValidationAdhesion(false);
				     		setDisabledall(true);
				     		
				     		System.out.println("probleme de Exception  ");
				     		
							e.printStackTrace();

						  }
					

				

				}
			}
			
	
		/******************************* enciassement sans ws pour test 2025*************************************/
		
		public void Encaissement_2025() throws NoSuchAlgorithmException, KeyManagementException{
			
			//boolean test = true;

			
//			ValiderProposition();
			if(  adhesionToPrintNotise == null || adhesionToPrintNotise.getIdAdhesion() == 0) return;
			adhesionToPrintNotise.setEtat("N");

			
			adhesionToPrintNotise = adhesionDAO.merge(adhesionToPrintNotise);


			
			


			if(clientBean.getClient().getNcompte() == null || clientBean.getClient().getNcompte().equalsIgnoreCase("")){
	           
				FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);

			}
				
			
		 
			
			 else {

	            
					setDisabledall(true);
	                   
					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					System.out.println("Encaissement effectuée avec succès.");
					//setDisabledValidationAdhesion(true);
					/**** enregistremnt ds notre !BD ****/

					adhesionToPrintNotise.setEtat("V");

					adhesionDAO.edit(adhesionToPrintNotise);
					//setDisabledValidationAdhesion(true);
					OperationTransaction operationTransaction = new OperationTransaction();

					operationTransaction.setDateTrans(new Date());
					operationTransaction.setIdAdhesion(adhesionToPrintNotise); // à discuter
					operationTransaction.setRefOperation("Reference");
					operationTransaction.setTypTrans("Encaissement");

					iOperationTransactionDAO.create(operationTransaction);
					
					setTestImpression(true); 
					
					
					if( ("2").equals(modepaie) ) {
					
					API_Bassem(adhesionToPrintNotise,
							adhesionToPrintNotise.getNumeroAdhesion()+ "-" + UUID.randomUUID().toString().substring(0, 5),
						    clientBean.getClient().getNcompte(),
						    contrib,
						    "Elyssa assurance",
						    clientBean.getClient().getIdentifiant(),
						    Frequency.getLabelByCode(frac),
						    "PRELEVEMENT_BANCAIRE",
						    21,
						    FrequencyService.getNextDateAsString(effDate,frac),
						    FrequencyService.getPreviousDateWith28(getEchDate(),frac),
						    "TND",
						    adhesionToPrintNotise.getPolice().getNPolice()+ adhesionToPrintNotise.getNumeroAdhesion(),
						    "10404100143213078807", //Constante.RIB_STAR_PRELEV,
						    (moisPrelevement == null)
					        ? null
					        : String.format("%02d", moisPrelevement),
					        (jourPrelevement == null)
					        ? null
					        : String.format("%02d", jourPrelevement),
						    ""
						);
					
					}
			 }
		}
		
			
			
			
		public void Encaissement_sans_enc() throws NoSuchAlgorithmException, KeyManagementException{
				
				boolean test = true;

				
				ValiderProposition();
				if(  adhesionToPrintNotise == null || adhesionToPrintNotise.getIdAdhesion() == 0) return;
				adhesionToPrintNotise.setEtat("N");

				
				adhesionToPrintNotise = adhesionDAO.merge(adhesionToPrintNotise);


				
				


				if(clientBean.getClient().getNcompte() == null || clientBean.getClient().getNcompte().equalsIgnoreCase("")){
		           
					FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

				}
					
				
			 
				
				 else {

		            


							FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
							msg.setSeverity(FacesMessage.SEVERITY_INFO);
							FacesContext.getCurrentInstance().addMessage("Erreur", msg);
							System.out.println("Encaissement effectuée avec succès.");
							setDisabledValidationAdhesion(true);
							/**** enregistremnt ds notre !BD ****/

							adhesionToPrintNotise.setEtat("V");

							adhesionDAO.edit(adhesionToPrintNotise);
							setDisabledValidationAdhesion(true);
							OperationTransaction operationTransaction = new OperationTransaction();

							operationTransaction.setDateTrans(new Date());
							operationTransaction.setIdAdhesion(adhesionToPrintNotise); // à discuter
							operationTransaction.setRefOperation("Reference");
							operationTransaction.setTypTrans("Encaissement");

							iOperationTransactionDAO.create(operationTransaction);

							//Status = 1;
							
							
						




					

				}
			}
			
			
			
			public void imprimer() {

				String path = "choisir_societe";
				
				path = reportController.getRealPath()
						+ "Contrat_hayaa_v5.jasper";
				
				
				if( utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000") ) { //QNB
					
					path = reportController.getRealPath()
							+ "Contrat_hayaa_v6.jasper";
 
					
				}
				
				
				if(utilisateur.getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat())) {//stb
					
					
					if(this.modepaie.equals("2")) {
						
						path = reportController.getRealPath()
								+ "Contrat_hayaa_v6.jasper";
					} 
	 	
				} 
				

				try {

					// fonction d'impression du devis
					Map<String, Object> parameters = new HashMap();

					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					
					if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000")) {//stb
						
						parameters.put("produit_fr","INVESTI");
						parameters.put("produit_ar","ادخـــــــــار 'أنفست'");
						parameters.put("condition_fr","'INVESTI'");
						parameters.put("condition_ar","\" ادخـــــــــار أنفست\"");
					}
						 
					else { 
						
						parameters.put("produit_fr","FUTURE DREAMS");
						parameters.put("produit_ar","ادخـــــــــار 'فيوتر دريمز'");
						parameters.put("condition_fr","'FUTURE DREAMS'");
						parameters.put("condition_ar","\" ادخـــــــــار فيوتر دريمز\"");
					}
					


					parameters.put("agence", utilisateur.getAgence().getCodeAgence());
					parameters.put("contrat",num_adhesion);
					parameters.put("effet", df.format(getNewProposition().getDatDebut()));
					parameters.put("duree", Integer.toString((int)getNewProposition().getDuree())+" ans");
					parameters.put("echeance", df.format(getNewProposition().getDatFin()));
					parameters.put("capital", getNewDetailsGarantieHayyaList().get(0).getCapital());
					parameters.put("souscr", getClientBean().getClient().getNomClient()+" "+getClientBean().getClient().getPrenomClient());
					parameters.put("adr_souscr", getClientBean().getClient().getAdrClient());
					parameters.put("id_souscr", getClientBean().getClient().getIdentifiant());
					parameters.put("assure", getClientBean().getClient().getNomClient()+" "+getClientBean().getClient().getPrenomClient());
					parameters.put("adr_assure", getClientBean().getClient().getAdrClient());
					parameters.put("id_assure", getClientBean().getClient().getIdentifiant());
					parameters.put("naiss_assure", df.format(getClientBean().getClient().getDatNaiss()));
					
					parameters.put("rib", getClientBean().getClient().getNcompte());
					
					parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
					
					if(getClientBean().getClient().getSexe().equalsIgnoreCase("M"))
						parameters.put("sexe_assure", "Homme");
					else
						parameters.put("sexe_assure", "Femme");
					
					String LibelleProfession = " ";
		 	   		if (getClientBean().getClient().getProfession() != null ) {
		 	   			  // doSomething
		 	   		LibelleProfession = getClientBean().getClient().getProfession().getLibelleProfession();
		 	   			}
					
					parameters.put("prf_assure", LibelleProfession);
					parameters.put("tel_assure", getClientBean().getClient().getTel());
					parameters.put("nom_benef", getClientBean().getClient().getNomClient());
					parameters.put("cin_benef", getClientBean().getClient().getIdentifiant());
					parameters.put("parente", null);
					parameters.put("poucent_cap", null);
					
					parameters.put("nom_benef_d", null);
					parameters.put("cin_benef_d", null);
					parameters.put("parente_d", null);
					parameters.put("poucent_cap_d", null);
					
					//parameters.put("prime_tot", getNewProposition().getPrimetot());
					parameters.put("frais", new BigDecimal(0.000));
					parameters.put("fga", getNewDetailHayya().getFa());
					//parameters.put("prime_nette", getNewDetailHayya().getPnette());
					
					
					parameters.put("primeversee", getBilanFinancierHayyaDTO().getPrimeversee());
					parameters.put("epargneprev", getBilanFinancierHayyaDTO().getEpargneprev());
					parameters.put("gainfinancier", getBilanFinancierHayyaDTO().getGainfinancier());
					parameters.put("econimpot", getBilanFinancierHayyaDTO().getEconimpot());
					parameters.put("gaintotal", getBilanFinancierHayyaDTO().getGaintotal());
					
					switch(getFrac()){
					   
				       case 1: 
				    	   parameters.put("prime_tot", getNewProposition().getPrimetot());
						   parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
					
				    	   parameters.put("annuel", "X");
				           break;
				   
				       case 2:
				    	   parameters.put("prime_tot", getNewDetailHayya().getTotal().doubleValue());
						   parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
//				    	   parameters.put("prime_tot", getNewDetailHayya().getPnette().doubleValue()*2 + getNewDetailHayya().getFa().doubleValue());
//							parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()*2));
						   
				    	   parameters.put("semestriel", "X");
				           break;
				   
				       case 3:
				    	   parameters.put("prime_tot", getNewDetailHayya().getTotal().doubleValue());
						   parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
//				    	   parameters.put("prime_tot", getNewDetailHayya().getPnette().doubleValue()*4 + getNewDetailHayya().getFa().doubleValue());
//							parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()*4));
						   
				    	   parameters.put("trimestriel", "X");
				           break;
				           
				       case 4:
				    	   parameters.put("prime_tot", getNewDetailHayya().getTotal().doubleValue());
						   parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
//				    	   parameters.put("prime_tot", getNewDetailHayya().getPnette().doubleValue()*12 + getNewDetailHayya().getFa().doubleValue());
//							parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()*12));

				    	   parameters.put("mensuel", "X");
				           break;
				
				       default:
				    	   parameters.put("prime_tot", getNewDetailHayya().getTotal().doubleValue());
						   parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
//				    	   parameters.put("prime_tot", getNewProposition().getPrimetot());
//							parameters.put("prime_nette", getNewDetailHayya().getPnette());
						   
				    	   parameters.put("unique", "X");
				           break;
				   }
					
					parameters.put("evol_p", getNewDetailHayya().getCroissHayya().getCroissance());
					
					if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000")) {//stb
						
						parameters.put("tx_benef", "80%");
					
					}else {
						
						parameters.put("tx_benef", "90%");	
						
					}
					
					parameters.put("tx_interef_gar", "4%");
					
					parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
					
					parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
					
					parameters
					.put("star",  reportController.getLogoJasper());
					System.out.println("utilisateur.getAgence().getSociete().getCodSociet()***************"+
							utilisateur.getAgence().getSociete().getCodSociet());
					parameters.put("imagesociete", utilisateur.getAgence().getSociete().getCodSociet() + ".png");
							
					/*
					 ArrayList<Order> orders = new ArrayList<>();
				        
				        orders.add(new Order("Item 1", new BigDecimal("5.4"), 4));
				        orders.add(new Order("Item 2", new BigDecimal("2.3"), 3));
				        orders.add(new Order("Item 3", new BigDecimal("1.5"), 8));
				        orders.add(new Order("Item 4", new BigDecimal("4.0"), 2));
				        */
				        
				        List<DetailsEvolutionGarantie> orders = getDetailsEvolutionGaranties();

				        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(orders);

				        parameters.put("datasource", ds);
				        
				        
				        List<Person> personnes = new ArrayList<Person>();
				        
				        System.out.println("vie "+getVie());
				        
				        if(getVie().equals("1")) {//assure
				        	Person assure = new Person();
				        	assure.setNom(getClientBean().getClient().getNomClient());
				        	assure.setPrenom(getClientBean().getClient().getPrenomClient());
				        	assure.setCin(getClientBean().getClient().getIdentifiant());
				        	assure.setLien("assuré");
				        	assure.setQuote(100);
				        	personnes = new ArrayList<Person>();
				        	personnes.add(assure);
				        	System.out.println("personnes "+getClientBean().getClient().getNomClient());
				        	System.out.println("personnes "+personnes.size());
				        }
				        
				        if(getVie().equals("2")) {// enfants
				        	personnes = new ArrayList<Person>();
				        	personnes = enfants;
				        	System.out.println("personnes "+personnes.size());
				        }
				        
				        if(getVie().equals("3")) {// autres
				        	personnes = new ArrayList<Person>();
				        	personnes = autres;
				        	System.out.println("personnes "+personnes.size());
				        }
				        
				        /*
				        Person enfant = new Person();
				        enfant.setNom("abdi");
				        enfant.setPrenom("lina");
				        enfant.setCin("12345678");
				        enfant.setLien("enfant1");
				        enfant.setQuote(50);
				        
				        Person enfant2 = new Person();
				        enfant2.setNom("abdi");
				        enfant2.setPrenom("sarra");
				        enfant2.setCin("87654321");
				        enfant2.setLien("enfant2");
				        enfant2.setQuote(50);
				        
				        enfants.add(enfant);
				        enfants.add(enfant2);
				        */
				        
				        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(personnes);
				        
				        parameters.put("datasvie", beanColDataSource);
				        
				        
				        
				        List<Person> personnes_deces = new ArrayList<Person>();
				        
				        System.out.println("deces "+getDeces());
				        
				        if(getDeces().equals("1")) {//defaut
				        	personnes_deces = new ArrayList<Person>();
				        	personnes_deces = defauts;
				        }
				        
				        if(getDeces().equals("2")) {// enfants
				        	personnes_deces = new ArrayList<Person>();
				        	personnes_deces = enfantds;
				        	System.out.println("personnes "+personnes.size());
				        }
				        
				        if(getDeces().equals("3")) {// conjoint
				        	personnes_deces = new ArrayList<Person>();
				        	personnes_deces = conjoints;
				        	System.out.println("personnes "+personnes.size());
				        }
				        
				        if(getDeces().equals("4")) {// parents
				        	personnes_deces = new ArrayList<Person>();
				        	personnes_deces = parents;
				        	System.out.println("personnes "+personnes.size());
				        }
				        
				        if(getDeces().equals("5")) {// autres
				        	personnes_deces = new ArrayList<Person>();
				        	personnes_deces = autreds;
				        	System.out.println("personnes "+personnes.size());
				        }
					
				        
				        JRBeanCollectionDataSource beanColDataSourceDeces = new JRBeanCollectionDataSource(personnes_deces);
				        
				        parameters.put("datasdeces", beanColDataSourceDeces);
				        
				        
				        parameters.put("report", reportController.getRealPath());
				        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH); 
				        
				        
		/********** pour prelv****************/
			
			           

			            
			            //parameters.put("agence", utilisateur.getAgence().getCodeAgence());
			            
						 
						//parameters.put("contrat",adhesion.getPolice().getNPolice());


						parameters.put("nom_assure", adhesionToPrintNotise.getClient().getNomClient()+" "+adhesionToPrintNotise.getClient().getPrenomClient());
						parameters.put("adresse", adhesionToPrintNotise.getClient().getAdrClient());
						parameters.put("tel", adhesionToPrintNotise.getClient().getTel());
						 
						
						parameters.put("email", adhesionToPrintNotise.getClient().getMail() != null?adhesionToPrintNotise.getClient().getMail():" ");
						
						parameters.put("num_contrat",adhesionToPrintNotise.getNadhesion());
						
						parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
						
						String nom_complet = utilisateur.getAgence().getSociete().getNomComplet();
						parameters.put("nom_complet", nom_complet.replace("<b>", "").replace("</b>", "") );
						
						parameters.put("agence", utilisateur.getAgence().getLibelle());
						
						parameters.put("adresse_agence", utilisateur.getAgence().getAdresse());
						
						 
						String rib = adhesionToPrintNotise.getClient().getNcompte();
						
						parameters.put("rib", adhesionToPrintNotise.getClient().getNcompte());
						int x1 = Character.getNumericValue(rib.charAt(0)); // 1
						int x2 = Character.getNumericValue(rib.charAt(1)); // 2
						int x3 = Character.getNumericValue(rib.charAt(2)); // 3
						int x4 = Character.getNumericValue(rib.charAt(3)); // 4
						int x5 = Character.getNumericValue(rib.charAt(4)); // 5
						int x6 = Character.getNumericValue(rib.charAt(5)); // 6
						int x7 = Character.getNumericValue(rib.charAt(6)); // 7
						int x8 = Character.getNumericValue(rib.charAt(7)); // 8
						int x9 = Character.getNumericValue(rib.charAt(8)); // 9
						int x10 = Character.getNumericValue(rib.charAt(9)); // 0
						int x11 = Character.getNumericValue(rib.charAt(10)); // 1
						int x12 = Character.getNumericValue(rib.charAt(11)); // 2
						int x13 = Character.getNumericValue(rib.charAt(12)); // 3
						int x14 = Character.getNumericValue(rib.charAt(13)); // 4
						int x15 = Character.getNumericValue(rib.charAt(14)); // 5
						int x16 = Character.getNumericValue(rib.charAt(15)); // 6
						int x17 = Character.getNumericValue(rib.charAt(16)); // 7
						int x18 = Character.getNumericValue(rib.charAt(17)); // 8
						int x19 = Character.getNumericValue(rib.charAt(18)); // 9
						int x20 = Character.getNumericValue(rib.charAt(19)); // 0
						parameters.put("x1", x1);
						parameters.put("x2", x2);
						parameters.put("x3", x3);
						parameters.put("x4", x4);
						parameters.put("x5", x5);
						parameters.put("x6", x6);
						parameters.put("x7", x7);
						parameters.put("x8", x8);
						parameters.put("x9", x9);
						parameters.put("x10", x10);
						parameters.put("x11", x11);
						parameters.put("x12", x12);
						parameters.put("x13", x13);
						parameters.put("x14", x14);
						parameters.put("x15", x15);
						parameters.put("x16", x16);
						parameters.put("x17", x17);
						parameters.put("x18", x18);
						parameters.put("x19", x19);
						parameters.put("x20", x20);
				        
				        
		/************************************/		        
				        

					JasperPrint print = JasperFillManager.fillReport(path,
							parameters, new JREmptyDataSource());
					byte[] bytes = JasperExportManager.exportReportToPdf(print);

					FacesContext fc = FacesContext.getCurrentInstance();
					ExternalContext ec = fc.getExternalContext();
					ec.responseReset();
					ec.setResponseContentType("application/pdf");
					ec.setResponseContentLength(bytes.length);
					ec.setResponseHeader("Content-Disposition", "inline; filename=\""
							+  "rapport.pdf" + "\"");

					OutputStream output = ec.getResponseOutputStream();
					output.write(bytes);
					fc.responseComplete();


				} catch (Exception e) {
					e.printStackTrace();
				}



				
				

			}
			
			
			public void imprimer_notice() {

				String path = reportController.getRealPath()
						+ "notice_7ayya.jasper";

				try {

					// fonction d'impression du devis
					Map<String, Object> parameters = new HashMap();

					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

					parameters.put("agence", utilisateur.getAgence().getCodeAgence());
					parameters.put("contrat",num_adhesion);
					parameters.put("effet", df.format(getNewProposition().getDatDebut()));
					parameters.put("duree", Integer.toString((int)getNewProposition().getDuree())+" ans");
					parameters.put("echeance", df.format(getNewProposition().getDatFin()));
					parameters.put("capital", getNewDetailsGarantieHayyaList().get(0).getCapital().doubleValue() );
					parameters.put("souscr", getClientBean().getClient().getNomClient()+" "+getClientBean().getClient().getPrenomClient());
					parameters.put("adr_souscr", getClientBean().getClient().getAdrClient());
					parameters.put("id_souscr", getClientBean().getClient().getIdentifiant());
					parameters.put("assure", getClientBean().getClient().getNomClient()+" "+getClientBean().getClient().getPrenomClient());
					parameters.put("adr_assure", getClientBean().getClient().getAdrClient());
					parameters.put("id_assure", getClientBean().getClient().getIdentifiant());
					parameters.put("naiss_assure", df.format(getClientBean().getClient().getDatNaiss()));
					
					
					String LibelleProfession = " ";
		 	   		if (getClientBean().getClient().getProfession() != null ) {
		 	   			  // doSomething
		 	   		LibelleProfession = getClientBean().getClient().getProfession().getLibelleProfession();
		 	   			}
					
					parameters.put("prf_assure", LibelleProfession);
					parameters.put("tel_assure", getClientBean().getClient().getTel());
					parameters.put("nom_benef", getClientBean().getClient().getNomClient());
					parameters.put("cin_benef", getClientBean().getClient().getIdentifiant());
			
					parameters.put("prenom_benef", getClientBean().getClient().getPrenomClient());
					parameters.put("compte", getClientBean().getClient().getNcompte());
					parameters.put("email", getClientBean().getClient().getMail());
					parameters.put("cpt", getClientBean().getClient().getCodeP());
					
					parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
					
					
					String nom_complet = utilisateur.getAgence().getSociete().getNomComplet();
					parameters.put("nom_complet", nom_complet.replace("<b>", "").replace("</b>", "") );
					
					
					
					
					Double primetot;
					Double primetot_pour_lettre;
					String primetot_lettre;
					
					
					switch(getFrac()){
					   
				       case 1: 
				    	   primetot = getNewProposition().getPrimetot() ;
				    	   primetot_pour_lettre = getNewProposition().getPrimetot() * 1000;
				    	   primetot_lettre = Nombre.montantTND(primetot);
				    	   
				    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot));
				    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
						   parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
						   parameters.put("mnt_lettre", primetot_lettre);
						   
				    	   parameters.put("annuel", "X");
				           break;
				   
				       case 2:
				    	   primetot = getNewDetailHayya().getPnette().doubleValue() + getNewDetailHayya().getFa().doubleValue();
				    	   primetot_pour_lettre = (getNewDetailHayya().getPnette().doubleValue() + getNewDetailHayya().getFa().doubleValue()) * 1000;
				    	   primetot_lettre = Nombre.montantTND(primetot);
				    	   
				    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot));
				    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
							parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
							parameters.put("mnt_lettre", primetot_lettre);
							
				    	   parameters.put("semestriel", "X");
				           break;
				   
				       case 3:
				    	   primetot = getNewDetailHayya().getPnette().doubleValue() + getNewDetailHayya().getFa().doubleValue();
				    	   primetot_pour_lettre = (getNewDetailHayya().getPnette().doubleValue() + getNewDetailHayya().getFa().doubleValue()) * 1000;
				    	   primetot_lettre = Nombre.montantTND(primetot);
				    	   
				    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot));
				    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
							parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
							parameters.put("mnt_lettre", primetot_lettre);
							
				    	   parameters.put("trimestriel", "X");
				           break;
				           
				       case 4:
				    	   primetot = getNewDetailHayya().getPnette().doubleValue() + getNewDetailHayya().getFa().doubleValue();
				    	   primetot_pour_lettre = (getNewDetailHayya().getPnette().doubleValue() + getNewDetailHayya().getFa().doubleValue()) * 1000;
				    	   primetot_lettre = Nombre.montantTND(primetot);
				    	   
				    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot));
				    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
							parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
							parameters.put("mnt_lettre", primetot_lettre);
							
				    	   parameters.put("mensuel", "X");
				           break;
				
				       default:
				    	   primetot = getNewProposition().getPrimetot();
				    	   primetot_pour_lettre = (getNewProposition().getPrimetot()) * 1000;
				    	   primetot_lettre = Nombre.montantTND(primetot);
				    	   
				    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot));
				    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
							parameters.put("prime_nette", getNewDetailHayya().getPnette());
							parameters.put("mnt_lettre", primetot_lettre);
						   
				    	   parameters.put("unique", "X");
				           break;
				   }
					
					parameters
					.put("star",  reportController.getLogoJasper());
					
					parameters.put("imagesociete", utilisateur.getAgence().getSociete()
							.getCodSociet() + ".png");
					
				
			        //parameters.put("report", reportController.getRealPath());
			        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);   
				        

					JasperPrint print = JasperFillManager.fillReport(path,
							parameters, new JREmptyDataSource());
					byte[] bytes = JasperExportManager.exportReportToPdf(print);

					FacesContext fc = FacesContext.getCurrentInstance();
					ExternalContext ec = fc.getExternalContext();
					ec.responseReset();
					ec.setResponseContentType("application/pdf");
					ec.setResponseContentLength(bytes.length);
					ec.setResponseHeader("Content-Disposition", "inline; filename=\""
							+  "rapport.pdf" + "\"");

					OutputStream output = ec.getResponseOutputStream();
					output.write(bytes);
					fc.responseComplete();


				} catch (Exception e) {
					e.printStackTrace();
				}



				
				

			}
			
			
			
			

			public void imprimer_quit() {

				String path = reportController.getRealPath()
						+ "Quit_hayaa.jasper";

				try {

					// fonction d'impression du devis
					Map<String, Object> parameters = new HashMap();

					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					
					parameters.put("numquit", numquit);

					parameters.put("agence", utilisateur.getAgence().getCodeAgence());
					parameters.put("contrat",num_adhesion);
					parameters.put("effet", df.format(getNewProposition().getDatDebut()));
					
					Calendar cal = Calendar.getInstance();
//					cal.setTime(getNewProposition().getDatFin());
//					cal.add(Calendar.DATE, -1);
//					Date dateBefore01Days = cal.getTime();
//					parameters.put("au", df.format(dateBefore01Days));
					
					
					
					
					switch (getFrac()) {
						case 1:
						    System.out.println("Annuel");
							cal.setTime(getNewProposition().getDatDebut());
							cal.add(Calendar.MONTH, 12);
							cal.add(Calendar.DATE, -1);
							Date dateBefore01Days = cal.getTime();
							parameters.put("au", df.format(dateBefore01Days));
							
							parameters.put("prime_tot", getNewProposition().getPrimetot());
							parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
						    break;
						  case 2:
						    System.out.println("Semestriel");
						    cal.setTime(getNewProposition().getDatDebut());
						    cal.add(Calendar.MONTH, 6);
						    cal.add(Calendar.DATE, -1);
						    Date dateBefore01Days2 = cal.getTime();
							parameters.put("au", df.format(dateBefore01Days2));
							
							parameters.put("prime_tot", getNewDetailHayya().getPnette().doubleValue() + getNewDetailHayya().getFa().doubleValue());
							parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
						    break;
						  case 3:
						    System.out.println("Trimestriel");
						    cal.setTime(getNewProposition().getDatDebut());
						    cal.add(Calendar.MONTH, 3);
						    cal.add(Calendar.DATE, -1);
						    Date dateBefore01Days3 = cal.getTime();
							parameters.put("au", df.format(dateBefore01Days3));
							
							parameters.put("prime_tot", getNewDetailHayya().getPnette().doubleValue() + getNewDetailHayya().getFa().doubleValue());
							parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));
						    break;
						  case 4:
						    System.out.println("Mensuel");
						    System.out.println("Trimestriel");
						    cal.setTime(getNewProposition().getDatDebut());
						    cal.add(Calendar.MONTH, 1);
						    cal.add(Calendar.DATE, -1);
						    Date dateBefore01Days4 = cal.getTime();
							parameters.put("au", df.format(dateBefore01Days4));
							
							parameters.put("prime_tot",  getNewDetailHayya().getPnette().doubleValue() + getNewDetailHayya().getFa().doubleValue());
							parameters.put("prime_nette", new BigDecimal( getNewDetailHayya().getPnette().doubleValue()));

						    break;
						  case 5:
						    System.out.println("PRIME UNIQUE");
							cal.setTime(getNewProposition().getDatFin());
							cal.add(Calendar.DATE, -1);
							Date dateBefore01Days5 = cal.getTime();
							parameters.put("au", df.format(dateBefore01Days5));
							
							parameters.put("prime_tot", getNewProposition().getPrimetot());
							parameters.put("prime_nette", getNewDetailHayya().getPnette());
						    break;
						  default:  
							 System.out.println("default"); 
							 break;
					}
					
					
					parameters.put("echeance", df.format(getNewProposition().getDatDebut()));
					
					parameters.put("souscr", getClientBean().getClient().getNomClient()+" "+getClientBean().getClient().getPrenomClient());
					parameters.put("adr_souscr", getClientBean().getClient().getAdrClient());
					 
					parameters.put("assure", getClientBean().getClient().getNomClient()+" "+getClientBean().getClient().getPrenomClient());
					parameters.put("adr_assure", getClientBean().getClient().getAdrClient());
					 
					 
					
					//parameters.put("prime_tot", getNewProposition().getPrimetot());
					parameters.put("frais", new BigDecimal(0.000));
					parameters.put("fga", getNewDetailHayya().getFa());
					//parameters.put("taxe", Double.parseDouble("0.000"));
					parameters.put("taxe", getNewProposition().getTaxe());
					//parameters.put("prime_nette", getNewDetailHayya().getPnette());
					
					
					 
					
					parameters
					.put("star",  reportController.getLogoJasper());
					
					parameters.put("imagesociete", utilisateur.getAgence().getSociete()
							.getCodSociet() + ".png");
					
					 
			        //parameters.put("report", reportController.getRealPath());
			        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);   

					JasperPrint print = JasperFillManager.fillReport(path,
							parameters, new JREmptyDataSource());
					byte[] bytes = JasperExportManager.exportReportToPdf(print);

					FacesContext fc = FacesContext.getCurrentInstance();
					ExternalContext ec = fc.getExternalContext();
					ec.responseReset();
					ec.setResponseContentType("application/pdf");
					ec.setResponseContentLength(bytes.length);
					ec.setResponseHeader("Content-Disposition", "inline; filename=\""
							+  "rapport.pdf" + "\"");

					OutputStream output = ec.getResponseOutputStream();
					output.write(bytes);
					fc.responseComplete();


				} catch (Exception e) {
					e.printStackTrace();
				}




			}
			
			
			
			
			
		public void updateValiderImpot(){
				
				System.out.println("dateImpot "+dateImpot);
				System.out.println("dateImpot_init "+dateImpot_init);
				System.out.println("valider_impot "+valider_impot);
				
				if(dateImpot != null) {
					
					valider_impot = true;
				}
				
				else {
					valider_impot = false;
				}
				
			}
		

		public void updateValiderImpot_V2(){
			
			this.valider_impot = (dateImpotDebut != null && dateImpotFin != null && !dateImpotFin.before(dateImpotDebut));
		
		}
		

		
		public void imprimer_impot_V2(){
			
			try {
	            String report = "";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            
	            switch(getFrac()){
	            
	            case 5:
	            	report = reportController.getRealPath() + "attestation_impot_7ayya_prime_unique_contrat.jasper";   
	            	break;
	            
	            default:
	            	report = reportController.getRealPath() + "attestation_impot_7ayya_contrat.jasper";   
	            	break;
	            
	            
	            }

				parameters.put("souscripteur", adhesionToPrintNotise.getClient().getNomClient() + " " + adhesionToPrintNotise.getClient().getPrenomClient());
				parameters.put("numplc", adhesionToPrintNotise.getPolice().getNPolice());
				parameters.put("cin_sousc", adhesionToPrintNotise.getClient().getIdentifiant());
				
				DateFormat df = new SimpleDateFormat("yyyy");
				
				DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");

				Date today = new Date();
				dateImpotDebut = today;
				dateImpotFin = today;

				parameters.put("du", df2.format(dateImpotDebut));
				
				parameters.put("au", df2.format(dateImpotFin));
				
				parameters.put("id_adhesion", adhesionToPrintNotise.getIdAdhesion());
				
				parameters.put("duree", (int)adhesionToPrintNotise.getDuree());
				
				parameters.put("date_contrat", df2.format(adhesionToPrintNotise.getDatDebut()));
				
				parameters.put("num_adhesion", adhesionToPrintNotise.getNadhesion());
			
				parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
				
				parameters.put("nom_complet", utilisateur.getAgence().getSociete().getNomComplet());
					
					parameters
					.put("star",  reportController.getLogoJasper());
					
					parameters.put("imagesociete", utilisateur.getAgence().getSociete()
							.getCodSociet() + ".png");
					
					
			       
			        
			        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);
			        
			        
			        InitialContext initialContext = new InitialContext();
			        DataSource dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/bancass_star");
			        Connection connection = dataSource.getConnection();
			        
			    

			        JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
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
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			
		}
		
		
			
			public void imprimer_impot(){
				
				try {
		            String report = "";         
		            Map<String, Object> parameters = new HashMap<String, Object>();
		            
		            switch(getFrac()){
		            
		            case 5:
		            	report = reportController.getRealPath() + "attestation_impot_7ayya_prime_unique.jasper";   
		            	break;
		            
		            default:
		            	report = reportController.getRealPath() + "attestation_impot_7ayya.jasper";   
		            	break;
		            
		            
		            }

					parameters.put("souscripteur", adhesionToPrintNotise.getClient().getNomClient() + " " + adhesionToPrintNotise.getClient().getPrenomClient());
					parameters.put("numplc", adhesionToPrintNotise.getPolice().getNPolice());
					parameters.put("cin_sousc", adhesionToPrintNotise.getClient().getIdentifiant());
					
					DateFormat df = new SimpleDateFormat("yyyy");
					
					DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");

					parameters.put("annee", df.format(dateImpot));
					
					parameters.put("id_adhesion", adhesionToPrintNotise.getIdAdhesion());
					
					parameters.put("duree", (int)adhesionToPrintNotise.getDuree());
					
					parameters.put("date_contrat", df2.format(adhesionToPrintNotise.getDatDebut()));
					
					parameters.put("num_adhesion", adhesionToPrintNotise.getNadhesion());
				
					parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
					
					parameters.put("nom_complet", utilisateur.getAgence().getSociete().getNomComplet());
						
						parameters
						.put("star",  reportController.getLogoJasper());
						
						parameters.put("imagesociete", utilisateur.getAgence().getSociete()
								.getCodSociet() + ".png");
						
						
				       
				        
				        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);
				        
				        
				        InitialContext initialContext = new InitialContext();
				        DataSource dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/bancass_star");
				        Connection connection = dataSource.getConnection();
				        
				    

				        JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
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
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				
				
			}
			
			
	public void printAutoPrel(){
		
		try {
            String report = reportController.getRealPath() + "prelevement.jasper";         
            Map<String, Object> parameters = new HashMap<String, Object>();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            
            //parameters.put("agence", utilisateur.getAgence().getCodeAgence());
            
			 
			//parameters.put("contrat",adhesion.getPolice().getNPolice());


			parameters.put("nom_assure", adhesionToPrintNotise.getClient().getNomClient()+" "+adhesionToPrintNotise.getClient().getPrenomClient());
			parameters.put("adresse", adhesionToPrintNotise.getClient().getAdrClient());
			parameters.put("tel", adhesionToPrintNotise.getClient().getTel());
			 
			
			parameters.put("email", adhesionToPrintNotise.getClient().getMail() != null?adhesionToPrintNotise.getClient().getMail():" ");
			
			parameters.put("num_contrat",adhesionToPrintNotise.getNadhesion());
			
			parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
			
			String nom_complet = utilisateur.getAgence().getSociete().getNomComplet();
			parameters.put("nom_complet", nom_complet.replace("<b>", "").replace("</b>", "") );
			
			parameters.put("agence", utilisateur.getAgence().getLibelle());
			
			parameters.put("adresse_agence", utilisateur.getAgence().getAdresse());
			
			 
			String rib = adhesionToPrintNotise.getClient().getNcompte();
			
			parameters.put("rib", adhesionToPrintNotise.getClient().getNcompte());
			int x1 = Character.getNumericValue(rib.charAt(0)); // 1
			int x2 = Character.getNumericValue(rib.charAt(1)); // 2
			int x3 = Character.getNumericValue(rib.charAt(2)); // 3
			int x4 = Character.getNumericValue(rib.charAt(3)); // 4
			int x5 = Character.getNumericValue(rib.charAt(4)); // 5
			int x6 = Character.getNumericValue(rib.charAt(5)); // 6
			int x7 = Character.getNumericValue(rib.charAt(6)); // 7
			int x8 = Character.getNumericValue(rib.charAt(7)); // 8
			int x9 = Character.getNumericValue(rib.charAt(8)); // 9
			int x10 = Character.getNumericValue(rib.charAt(9)); // 0
			int x11 = Character.getNumericValue(rib.charAt(10)); // 1
			int x12 = Character.getNumericValue(rib.charAt(11)); // 2
			int x13 = Character.getNumericValue(rib.charAt(12)); // 3
			int x14 = Character.getNumericValue(rib.charAt(13)); // 4
			int x15 = Character.getNumericValue(rib.charAt(14)); // 5
			int x16 = Character.getNumericValue(rib.charAt(15)); // 6
			int x17 = Character.getNumericValue(rib.charAt(16)); // 7
			int x18 = Character.getNumericValue(rib.charAt(17)); // 8
			int x19 = Character.getNumericValue(rib.charAt(18)); // 9
			int x20 = Character.getNumericValue(rib.charAt(19)); // 0
			parameters.put("x1", x1);
			parameters.put("x2", x2);
			parameters.put("x3", x3);
			parameters.put("x4", x4);
			parameters.put("x5", x5);
			parameters.put("x6", x6);
			parameters.put("x7", x7);
			parameters.put("x8", x8);
			parameters.put("x9", x9);
			parameters.put("x10", x10);
			parameters.put("x11", x11);
			parameters.put("x12", x12);
			parameters.put("x13", x13);
			parameters.put("x14", x14);
			parameters.put("x15", x15);
			parameters.put("x16", x16);
			parameters.put("x17", x17);
			parameters.put("x18", x18);
			parameters.put("x19", x19);
			parameters.put("x20", x20);
			
		 	
			parameters
			.put("star",  reportController.getLogoJasper());
			
			parameters.put("imagesociete", utilisateur.getAgence().getSociete()
					.getCodSociet() + ".png");
			
		
            
	        parameters.put("report", reportController.getRealPath());
	        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);
             
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

//            adhesion.setImpr(true);
//            adhesionDAO.edit(adhesion);
        } catch (Exception e) {
            e.printStackTrace();
        }
				
			}
			
			
/************************** fin partie III *********************************/			
	
	
	
	
	/*********** partie generale 
	 * @throws Exception ************************************************/
	
	public String onFlowProcess(FlowEvent event) throws Exception {
		
		System.out.println("****************skip = "+skip);
		

		
		if(skip) {

			System.out.println("****************adhesion ou encaissement deja fait********************");
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Flash flash = facesContext.getExternalContext().getFlash();
			flash.setKeepMessages(true);


				facesContext.addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Adhésion déjà enregistrée avec le numéro:", 
								adhesionToPrintNotise.getNumeroAdhesion()+"!"));

			return "contrat";
		}else {
			
			if(event.getNewStep().equals("tarif") && event.getOldStep().equals("devis"))
			{
				System.out.println("calcul tarif");
				newProposition = new Proposition();
				setDisabledGenerateDevis(true);
				propositionIsSaved = false;
				if(!calculTarif()) return "devis";
			}
			
			return event.getNewStep();
			
		}
		
		

		
//		if(skip) {
//			skip = false;   //reset in case user goes back
//			System.out.println("****************test msg rente 2222********************");
//			return "confirm";
//		}
//		else {
//			System.out.println("****************test msg rente 3333********************");
//			return event.getNewStep();
//		}
	}
	
	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	
	
	public String getLibelleByValeur(String valeur) {
	    Iterator it = croissanceOptions.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry entry = (Map.Entry) it.next();
	        if (entry.getValue().equals(valeur)) {
	            return (String) entry.getKey(); // libellé (ex: "2%")
	        }
	    }
	    return null; // si non trouvé
	}
	
	
	public boolean isAfficherJourBlock() {

	    // Société doit être "stb"
	    if (!reportController.getSTBMat().equals(utilisateur.getAgence().getSociete().getCodSociet())) {
	        return false;
	    }

	    // Mode prélèvement bancaire
	    return "2".equals(modepaie);
	}
	
	
	public boolean isAfficherMois() {
		
		   // Société doit être "stb"
	    if (!reportController.getSTBMat().equals(utilisateur.getAgence().getSociete().getCodSociet())) {
	        return false;
	    }
	    
//	    return "2".equals(modepaie) && 1==frac;
	    return "2".equals(modepaie) && (frac == 1 || frac == 2);
	}
	
	
 
	
	
	public void onModeChange() {
	    if (!isAfficherJourBlock()) {
	        jourPrelevement = null; // on efface la valeur si Direct
	    }
	    
	    if (!isAfficherMois()) {
	    	moisPrelevement  = null; // on efface la valeur si Direct
	    }
	    
	    if (isAfficherMois()) {
	    	
	    	// si prelev et mois oblig alors c'est fixe = mois effet contrat
	    	if (newAdhesion.getDatDebut() != null) {
	    	    Calendar cal = Calendar.getInstance();
	    	    cal.setTime(effDate);
	    	    moisPrelevement = cal.get(Calendar.MONTH) + 1;
	    	}
	    	
	    	 
	    }
	}
	
	
	public void onJourChange() {
		
		 if (jourPrelevement != null && jourPrelevement == 0) {
			 jourPrelevement = null; // reset manuel
		    }
		
		System.out.println("jourPrelevement est " +jourPrelevement);
	}
	
	public void onMoisChange() {
		
		 if (moisPrelevement != null && moisPrelevement == 0) {
			 moisPrelevement = null; // reset manuel
		    }
		
		System.out.println("moisPrelevement est " +moisPrelevement);
	}
	
	
 
	
	
//	public List<SelectItem> getAnneesDisponibles() {
////	    if (anneesDisponibles == null) {
//	        anneesDisponibles = new ArrayList<>();
//	        Calendar cal = Calendar.getInstance();
//	        cal.setTime(effDate);
//	        int startYear = cal.get(Calendar.YEAR); // année minimale
//	        int endYear = startYear + 5; // par ex., 5 ans max
//	        for (int y = startYear; y <= endYear; y++) {
//	            anneesDisponibles.add(new SelectItem(y, String.valueOf(y)));
//	        }
////	    }
//	    return anneesDisponibles;
//	}
	
	
	
	/***** appel API bassem *************/
	public void API_Bassem_fin( Adhesion ad, String transactionId,
	        String issuerRib,
	        double amount,
	        String beneficiaryName,
	        String description,
	        String frequency,
	        String object,
	        int maxRetries,
	        String startDate,
	        String endDate,
	        String currencyCode,
	        String orderReference,
	        String beneficiaryRib,
	        String fromMonth,
	        String fromDayOfMonth,
	        String fromDayOfWeek) {
		
		System.out.println("sysout");
		
	}
	
	public void API_Bassem( Adhesion ad, String transactionId,
	        String issuerRib,
	        double amount,
	        String beneficiaryName,
	        String description,
	        String frequency,
	        String object,
	        int maxRetries,
	        String startDate,
	        String endDate,
	        String currencyCode,
	        String orderReference,
	        String beneficiaryRib,
	        String fromMonth,
	        String fromDayOfMonth,
	        String fromDayOfWeek) {
		
		
		if(!reportController.activer_api_Bassem()) {
			System.out.println("API_Bassem est desactivé");
			return;
		}
		
		System.out.println("API_Bassem est activé");

        final String BASE_URI = Constante.URL_API_PELEV_STB;
        System.out.println("BASE_URI : " + BASE_URI);
        
        // Variables dynamiques
//        String transactionId = "000005";
//        String issuerRib = "10000000500310578826";
//        double amount = 100;
//        String beneficiaryName = "Elyssa assurance";
//        String orderReference = "999456789781015544551001";
//        String description = "test api Monthly";

        // Construire le JSON dynamiquement avec Map
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("IdTransaction", transactionId);
        jsonMap.put("issuerRib", issuerRib);
        jsonMap.put("amount", amount);
        jsonMap.put("beneficiaryName", beneficiaryName);
        jsonMap.put("description", description);
        jsonMap.put("frequency", frequency);
        jsonMap.put("object", object);
        jsonMap.put("maxRetries", maxRetries);               // nombre
        jsonMap.put("startDate", startDate);    // ISO format recommandé
        jsonMap.put("endDate", endDate);
        jsonMap.put("currencyCode", "TND");
        jsonMap.put("orderReference", orderReference);
        jsonMap.put("beneficiaryRib", beneficiaryRib);
//        jsonMap.put("fromMonth", fromMonth);
        jsonMap.put("fromMonth", fromMonth == null ? "" : fromMonth);
//        jsonMap.put("fromDayOfMonth", fromDayOfMonth);
        jsonMap.put("fromDayOfMonth", fromDayOfMonth == null ? "" : fromDayOfMonth);
        jsonMap.put("fromDayOfWeek", "");

        // Convertir la Map en JSONObject
        org.json.JSONObject inputJson = new org.json.JSONObject(jsonMap);
        
        // 🔹 Variables utilisées pour la DB
        Integer status = null;
        String responseBody = null;
        String responseType = null;
        String errorMessage = null;
 

        // Affichage JSON pour Postman
        System.out.println("JSON à envoyer / tester en Postman :\n" + inputJson.toString(4));

        try {
        	com.sun.jersey.api.client.Client client = JerseyClient.LocalApiClient();
            ClientResponse response = JerseyClient.PostReponseDuClientSTBstandingOrder(BASE_URI, inputJson.toString(), client, reportController);

            if (response == null) {
                System.out.println("❌ Problème : response = null");
                responseType = "NULL";
                errorMessage = "Response is null";
            }else {
            	
                 status = response.getStatus();
                 responseBody  = response.getEntity(String.class);

                System.out.println("=================================");
                System.out.println("HTTP Status : " + status);
                System.out.println("Response Body :\n" + responseBody );
                System.out.println("=================================");

                // ✅ CAS SUCCÈS 200 ou 201
                if (status == 200 || status == 201) {
                    if (responseBody  != null && responseBody .trim().startsWith("{")) {

                    	responseType = "JSON";
                    	
                        org.json.JSONObject jsonOutput = new org.json.JSONObject(responseBody);
                        System.out.println("Response JSON:\n" + jsonOutput.toString(4));

                        // 🔹 Lecture dynamique de tous les champs
//                        System.out.println("✅ Contenu JSON reçu :");
//                        Iterator<String> keys = jsonOutput.keys();
//                        while (keys.hasNext()) {
//                            String key = keys.next();
//                            Object value = jsonOutput.get(key);
//                            System.out.println(key + " : " + value);
//                        }


                    } else {
                        System.out.println("⚠️ Réponse 200/201 mais non JSON !");
                        responseType = "NON_JSON";
                    }

                } else {
                    // ❌ CAS ERREUR HTTP
                    System.out.println("❌ Erreur HTTP : " + status);

                    if (responseBody != null && responseBody.trim().startsWith("{")) {
                    	org.json.JSONObject jsonError = new org.json.JSONObject(responseBody);
                    	responseType = "JSON";
                        System.out.println("Erreur JSON reçue : " + jsonError.toString(4));
                    } else {
                    	responseType = "NON_JSON";
                        System.out.println("Erreur non JSON reçue : " + responseBody);
                    }
                }            	
            	
            }



        } catch (ClientHandlerException e) {
            System.out.println("❌ ClientHandlerException (SSL / réseau)");
            responseType = "EXCEPTION";
            errorMessage = "ClientHandlerException : " + e.getMessage();
            e.printStackTrace();

        } catch (org.json.JSONException e) {
            System.out.println("❌ JSONException (réponse non JSON)");
            responseType = "EXCEPTION";
            errorMessage = "JSONException : " + e.getMessage();
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("❌ Exception générale");
            responseType = "EXCEPTION";
            errorMessage = "General Exception : " + e.getMessage();
            e.printStackTrace();
        }finally {
            // 🔹 Enregistrement dans Oracle
//            saveApiResponse(transactionId, orderReference, status, dbResponse);
        	 try {
        	ApiResponseEntity entity  = new ApiResponseEntity();
        	entity.setIdAdhesion(ad);
        	entity.setTransactionId(transactionId);
        	entity.setOrderReference(orderReference);
        	entity.setHttpStatus(status);
        	entity.setInputJson(inputJson.toString());
        	entity.setResponseJson(responseBody);
        	entity.setResponseType(responseType);
            entity.setErrorMessage(errorMessage);
            entity.setCreatedAt(new Date());
        	
        	iApiResponseEntityDAO.create(entity);
        	 } catch (Exception dbException) {

        	        System.out.println("❌ Erreur lors de l'enregistrement du log API");
        	        dbException.printStackTrace();

        	        // IMPORTANT : ne pas relancer l'exception
        	        // sinon tu casses ton traitement principal
        	    }
        }
    }
	
	
	public void API_Bassem_0() throws NoSuchAlgorithmException, KeyManagementException{
		
		//boolean test = true;

   
			
			 final String BASE_URI = Constante.URL_API_PELEV_STB;
			
			 //final String BASE_URI = "https://openbank.stb.com.tn:9443/v0/payment";
			
			System.out.println("BASE_URI "+Constante.URL_API_PELEV_STB);
			
//			String IdDemande = numquit;
//			
//			String PrimeAssurance = mnt_total;
//			String AccountNumber = clientBean.getClient().getNcompte();
//			String Police = adhesionToPrintNotise.getPolice().getNPolice(); 
//			String Cin = clientBean.getClient().getIdentifiant();
//			String Nom = clientBean.getClient().getNomClient(); 
//			String Prenom = clientBean.getClient().getPrenomClient();
//			
//			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//			
//			String DateNaissance = df.format(clientBean.getClient().getDatNaiss());
//			
//			String CapitalAssure = mnt_capital;
//			
//			String DateEffet = df.format(adhesionToPrintNotise.getDatDebut());
//			String Datefin = df.format(adhesionToPrintNotise.getDatFin());
//			
//			String Mobile = clientBean.getClient().getTel();
//			String Mail = clientBean.getClient().getMail();
//			
//			String Produit = Constante.PRODUIT_HAYYA;
//			
//			String input_ = "{"
//					+ "    \"IdDemande\": \""+IdDemande+"\","
//					+ "    \"PrimeAssurance\": \""+PrimeAssurance+"\","
//					+ "    \"AccountNumber\": \""+AccountNumber+"\","
//					+ "    \"Police\": \""+Police+"\","
//					+ "    \"Cin\": \""+Cin+"\","
//					+ "    \"Nom\": \""+Nom+"\","
//					+ "    \"Prenom\": \""+Prenom+"\","
//					+ "    \"DateNaissance\": \""+DateNaissance+"\","
//					+ "    \"CapitalAssure\": \""+CapitalAssure+"\","
//					+ "    \"DateEffet\": \""+DateEffet+"\","
//					+ "    \"Datefin\": \""+Datefin+"\","
//					+ "    \"Mobile\": \""+Mobile+"\","
//					+ "    \"Mail\": \""+Mail+"\","
//					+ "    \"Produit\": \""+Produit+"\""
//					+ "}";
			
			String input = "{\r\n"
					+ "    \"IdTransaction\":\"000001\",\r\n"
					+ "    \"issuerRib\": \"10000000500310578826\",\r\n"
					+ "    \"amount\": 100,\r\n"
					+ "    \"beneficiaryName\": \"Elyssa assurance\",\r\n"
					+ "    \"description\": \"test api Monthly\",\r\n"
					+ "    \"frequency\": \"Annual\",\r\n"
					+ "    \"object\": \"STAR VIE\",\r\n"
					+ "    \"maxRetries\": \"3\",\r\n"
					+ "    \"startDate\": \"01/02/2026\",\r\n"
					+ "    \"endDate\": \"31/12/2026\",\r\n"
					+ "    \"currencyCode\": \"TND\",\r\n"
					+ "    \"orderReference\": \"993456789781015544551001\",\r\n"
					+ "    \"beneficiaryRib\": \"10000000500310578826\",\r\n"
					+ "    \"fromMonth\": \"04\",\r\n"
					+ "    \"fromDayOfMonth\": \"25\",\r\n"
					+ "    \"fromDayOfWeek\": \"\"\r\n"
					+ "} ";
			
			
			try {
				
				

			    
			    com.sun.jersey.api.client.Client client = JerseyClient.LocalApiClient();
			    ClientResponse response = JerseyClient.PostReponseDuClientSTBstandingOrder(BASE_URI, input, client, reportController);
			  
			    

				if ( response == null) {
					System.out.println("probleme de connexion , response = null");
					
				}
			    
			    
			    if (response.getStatus() != 200) {
//					throw new RuntimeException("Failed : HTTP error code : "
//					     + response.getStatus());
			    	System.out.println("probleme de connexion , response != 200");
			    	String output_0 = response.getEntity(String.class);
			    	System.out.println("Raw response:");
			    	System.out.println(output_0);
			    	org.json.JSONObject jsonOutput_0 = new org.json.JSONObject(output_0);
			    	
			    	System.out.println(jsonOutput_0);
					
				} else {

				System.out.println("Output from Server .... \n");
				String output = response.getEntity(String.class);
				System.out.println(output);
				
				// JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
				
				org.json.JSONObject jsonOutput = new org.json.JSONObject(output);

			
				//String Status =jsonOutput.getString("Status");
				
				
				String Status = " ";
				String RejectMessage = " ";
				String Reference = " ";
				
//				if (jsonOutput.has("Status")) {
//					Status =jsonOutput.getString("Status");
//					}
//				if (jsonOutput.has("RejectMessage")) {
//					RejectMessage = jsonOutput.getString("RejectMessage");
//					}
//				if (jsonOutput.has("Reference")) {
//					Reference = jsonOutput.getString("Reference");
//					}
//			
//
//				if(Status.equalsIgnoreCase("Processed")){
//					
//					setDisabledall(true);
//                   
//					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
//					msg.setSeverity(FacesMessage.SEVERITY_INFO);
//					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
//					System.out.println("Encaissement effectuée avec succès.");
//					//setDisabledValidationAdhesion(true);
//					/**** enregistremnt ds notre !BD ****/
//
//					adhesionToPrintNotise.setEtat("V");
//
//					adhesionDAO.edit(adhesionToPrintNotise);
//					//setDisabledValidationAdhesion(true);
//					OperationTransaction operationTransaction = new OperationTransaction();
//
//					operationTransaction.setDateTrans(new Date());
//					operationTransaction.setIdAdhesion(adhesionToPrintNotise); // à discuter
//					operationTransaction.setRefOperation(Reference);
//					operationTransaction.setTypTrans("Encaissement");
//
//					iOperationTransactionDAO.create(operationTransaction);
//					
//					setTestImpression(true);
//
//
//				}
			       
				}  
			    	  
				
				} 
			catch (ClientHandlerException e) {

	 		
	     		System.out.println("probleme de ClientHandlerException  ");
	     		
				e.printStackTrace();

			  }
			
	catch (org.json.JSONException e) {
		
	     		System.out.println("probleme de JSONException  ");
	     		
				e.printStackTrace();

			  }
			catch (Exception e) {

		     		
		     		System.out.println("probleme de Exception  ");
		     		
					e.printStackTrace();

				  }
	
	}
	
	
	
	public void API_Bassem_1() {

	    final String BASE_URI = Constante.URL_API_PELEV_STB;

	    System.out.println("BASE_URI : " + BASE_URI);

	    // ⚠️ JSON corrigé (maxRetries en nombre + dates ISO recommandées)
	    String jsonInputString = "{"
                + "\"IdTransaction\":\"000001\","
                + "\"issuerRib\": \"10000000500310578826\","
                + "\"amount\": 100,"
                + "\"beneficiaryName\": \"Elyssa assurance\","
                + "\"description\": \"test api Monthly\","
                + "\"frequency\": \"Annual\","
                + "\"object\": \"STAR VIE\","
                + "\"maxRetries\": \"3\","
                + "\"startDate\": \"01/02/2026\","
                + "\"endDate\": \"31/12/2026\","
                + "\"currencyCode\": \"TND\","
                + "\"orderReference\": \"993456789781015544551001\","
                + "\"beneficiaryRib\": \"10000000500310578826\","
                + "\"fromMonth\": \"04\","
                + "\"fromDayOfMonth\": \"25\","
                + "\"fromDayOfWeek\": \"\""
                + "}";

	    try {

	        com.sun.jersey.api.client.Client client = JerseyClient.LocalApiClient();

	        ClientResponse response =
	                JerseyClient.PostReponseDuClientSTBstandingOrder(BASE_URI, jsonInputString, client, reportController);

	        if (response == null) {
	            System.out.println("❌ Problème : response = null");
	            return;
	        }

	        int status = response.getStatus();
	        String output = response.getEntity(String.class);

	        System.out.println("=================================");
	        System.out.println("HTTP Status : " + status);
	        System.out.println("Response Body : ");
	        System.out.println(output);
	        System.out.println("=================================");

	        // ✅ CAS SUCCÈS
	        if (status == 200) {

	            if (output != null && output.trim().startsWith("{")) {

	                org.json.JSONObject jsonOutput = new org.json.JSONObject(output);

	                String Status = jsonOutput.optString("Status", "");
	                String RejectMessage = jsonOutput.optString("RejectMessage", "");
	                String Reference = jsonOutput.optString("Reference", "");

	                System.out.println("Status : " + Status);
	                System.out.println("RejectMessage : " + RejectMessage);
	                System.out.println("Reference : " + Reference);

	                if ("Processed".equalsIgnoreCase(Status)) {

	                    System.out.println("✅ Encaissement effectué avec succès.");

	                    // 👉 Ici ton traitement métier (DB, log, etc.)

	                } else {

	                    System.out.println("⚠️ Transaction non traitée : " + RejectMessage);
	                }

	            } else {
	                System.out.println("⚠️ Réponse 200 mais non JSON !");
	            }

	        }
	        // ❌ CAS ERREUR HTTP
	        else {

	            System.out.println("❌ Erreur HTTP : " + status);

	            if (output != null && output.trim().startsWith("{")) {

	                org.json.JSONObject jsonError = new org.json.JSONObject(output);
	                System.out.println("Erreur JSON reçue : " + jsonError);

	            } else {

	                System.out.println("Erreur non JSON reçue : ");
	                System.out.println(output);
	            }
	        }

	    } catch (ClientHandlerException e) {

	        System.out.println("❌ ClientHandlerException (problème connexion SSL / réseau)");
	        e.printStackTrace();

	    } catch (org.json.JSONException e) {

	        System.out.println("❌ JSONException (réponse non JSON)");
	        e.printStackTrace();

	    } catch (Exception e) {

	        System.out.println("❌ Exception générale");
	        e.printStackTrace();
	    }
	}

	
	
	/**** enciassement  
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException **********************/
	
	public void Encaissement_Global() throws KeyManagementException, NoSuchAlgorithmException {
		
		if(reportController.est_A_Encaisser()) {  //STB
			
			System.out.println("WS STB d'encaissement a été lancé");
			
			Encaissement();
			
		}else {
			
			System.out.println("WS STB d'encaissement est desactivé");
			
			Encaissement_2025();
			
		}
		
	}
	
	
	
	/***** validation date effet/
	 * 
	 */
	
	public void validerDateEffet(FacesContext context, UIComponent component, Object value) {
		
	    if (value == null) {
	        return; // ou gérer le null selon ton besoin
	    }

	    Date dateSaisie = (Date) value;

	    Calendar calSaisie = Calendar.getInstance();
	    calSaisie.setTime(dateSaisie);
	    calSaisie.set(Calendar.HOUR_OF_DAY, 0);
	    calSaisie.set(Calendar.MINUTE, 0);
	    calSaisie.set(Calendar.SECOND, 0);
	    calSaisie.set(Calendar.MILLISECOND, 0);

	    Calendar calAuj = Calendar.getInstance();
	    calAuj.setTime(aujourdhui);
	    calAuj.set(Calendar.HOUR_OF_DAY, 0);
	    calAuj.set(Calendar.MINUTE, 0);
	    calAuj.set(Calendar.SECOND, 0);
	    calAuj.set(Calendar.MILLISECOND, 0);

	    if (calSaisie.before(calAuj)) {
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Date invalide", "La date d'effet doit être ≥ aujourd'hui");
	        throw new ValidatorException(msg);
	    }
	}
	
	
	/***** demande de slimen si durée < 8/
	 * 
	 */
	public void validerDuree(FacesContext context, UIComponent component, Object value) {

//	    int d = (value != null) ? ((Number)value).intValue() : 0;
//
//	    // ✅ Message info si < 8
//	    if(d > 0 && d < 8) {
//	        FacesMessage info = new FacesMessage(FacesMessage.SEVERITY_INFO,
//	                "Info",
//	                "Si la durée est < à 8 ans, ce contrat ne donne pas droit à l’avantage fiscal.");
//	        context.addMessage(component.getClientId(context) + "_info", info);
//	    }

	  
	}
	
	public boolean isAfficherDureeInfo() {
	    return duree > 0 && duree < 8;
	}
	
	
	
	public Integer getMaxDureeContrat() {
	    return reportController.MAX_DUREE_CONTRAT();
	}
}
