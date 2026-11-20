package com.star.Ba.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.convert.Converter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.ITypeAbonnementDAO;
import com.star.Ba.dto.BilanFinancierHayyaDTO;
import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.DetailsGarantieDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.BilanFinancierHayya;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.CroissHayya;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.FractHayya;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.TrancheHayya;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.AssistantVoiyagetServiceInter;
import com.star.Ba.service.HayyaServiceInter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@ManagedBean
@SessionScoped 
public class H7ayyaController implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
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
	
	
	
	private BigDecimal pnette;
	private BigDecimal accessoires;
	private BigDecimal taxes;
	private BigDecimal total;
	
	private BigDecimal fga;
	
	
	private int duree;
	
	private double contrib;
	
	private int frac;
	
	private int crois = -1;
	
	private int tranche;
	
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
	
	
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	
	   @EJB
		private HayyaServiceInter hayyaServiceInter;
	
	@EJB
	private IAdhesionDAO adhesionDAO ;
	
	private boolean affiche = true;
	
	
	@PostConstruct
	public void init() {
		
		System.out.println("==================== init()");
		
		aujourdhui = new Date();
		
		effDate = aujourdhui;
		
		newAdhesion.setDatDebut(aujourdhui);
		newProposition.setDatDebut(aujourdhui);
		
		Convention convention = serviceAssistantVoiyagetService
				.getConventionBySociete(utilisateur.getAgence().getSociete());
		
		Produit produit = serviceAssistantVoiyagetService
				.getProduitByCode(ENUMCODEPRODUIT.H7AYYA.getCode());
		
		setAllPoliseByCodeAndConvention(serviceAssistantVoiyagetService
				.getPolice_by_conv_prod(convention, produit));
		
		selectedProposition = null;
		
		
	}
	
	
	
	
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
	
	
	
	
	
	public ReportController getReportController() {
		return reportController;
	}




	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
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
			detailHayya.setAge(detailHayya_.getAge());
			detailHayya.setContribution(detailHayya_.getContribution());
			detailHayya.setPersonnelstar(detailHayya_.getPersonnelstar());
			detailHayya.setPnette(detailHayya_.getPnette());
			detailHayya.setAccessoires(detailHayya_.getAccessoires());
			detailHayya.setCommission(detailHayya_.getCommission());
			detailHayya.setFa(detailHayya_.getFa());
			
			
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
			modepaie = "1";
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
		modepaie = "1";
		banque = "1";
		agence = "";
		rib = "";

		payeur = "1";

		nomPayeur = "";
		cinPayeur = "";
		adressePayeur = "";

		dispoSpec = "";
	 
		
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
	
	
public String calculTarif() throws Exception {
	
	boolean adhesionValide= true;
	
	if (!UpdateDateOfBirth()) {
		adhesionValide=false;
		return null;
    }
	
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
	    break;
	  case 2:
	    System.out.println("Semestriel");
	    newAdhesion.setFraPolice("2");
	    newProposition.setFraPolice("2");
	    nbr_periode_pour_frac = 2;
	    break;
	  case 3:
	    System.out.println("Trimestriel");
	    newAdhesion.setFraPolice("3");
	    newProposition.setFraPolice("3");
	    nbr_periode_pour_frac = 4;
	    break;
	  case 4:
	    System.out.println("Mensuel");
	    newAdhesion.setFraPolice("4");
	    newProposition.setFraPolice("4");
	    nbr_periode_pour_frac = 12;
	    break;
	  case 5:
	    System.out.println("PRIME UNIQUE");
	    newAdhesion.setFraPolice("5");
	    newProposition.setFraPolice("5");
	    nbr_periode_pour_frac = 1;
	    break;
	  default:  
		 System.out.println("default"); 
		 break;
}
	
	detailHayya.setContribution(new BigDecimal(contrib));
	fractHayya.setId_fract_hayya(frac);
	detailHayya.setFractHayya(fractHayya);
	croissHayya.setId_croiss_hayya(crois);
	detailHayya.setCroissHayya(croissHayya);
	trancheHayya.setId_tranche_hayya(tranche);
	detailHayya.setTrancheHayya(trancheHayya);
	detailHayya.setPersonnelstar(personnel);
	
	 if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000") ){
			/*if( clientBean.getClient().getNcompte() == null || 
					(clientBean.getClient().getNcompte()!=null && clientBean.getClient().getNcompte().equals(""))){
				newAdhesion.setEtat("V");
			}
			else {
				newAdhesion.setEtat("N");
			}*/
		 newAdhesion.setEtat("N");
		}
		else{
			newAdhesion.setEtat("V");
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
	  		+ "                \"CODE_AGENCE\": 825,"
	  		+ "                \"CODE_UTILISATEUR\": 634,"
	  		+ "                \"NOTE_COUVERTURE\": \"0000000000\","
	  		+ "                \"CODE_BRANCHE\": 61,"
	  		+ "                \"CODE_SOUS_BRANCHE\": \""+ENUMCODEPRODUIT.H7AYYA.getCode()+"\","
	  		+ "                \"CODE_CLIENT\": \"639189\","
	  		+ "                \"CODE_DUREE\": \"61\","
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
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"100\","
	  		+ "                                               \"VALEUR_CARACT\": \""+tranche+"\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \""+tranche+"\""
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
		

        
        
        ClientResponse response =  JerseyClient.PostReponseDuClientEDI(Constante.URL_WS_CalculerPrimeDevisVie_STB, f, reportController);
        		
       

        if (response == null) {
//          throw new RuntimeException("Failed : HTTP error code : "
//               + response.getStatus());
        	adhesionValide=false;
      	 FacesMessage msg = new FacesMessage("ERREUR", "Erreur interne, veuillez contacter l'administrateur! ");
    		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
    		  
      }
        
        
        
        if (response.getStatus() != 200) {
        	adhesionValide=false;
//            throw new RuntimeException("Failed : HTTP error code : "
//                 + response.getStatus());
        	FacesMessage msg = new FacesMessage("ERREUR", "Erreur interne, veuillez contacter l'administrateur! ");
  		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
  		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
  		  
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
		 
		 // 1. details garanties
		 
		 JSONArray detailsGarantieListJSON = jsonOutput.getJSONArray("DetailsGarantie");
		  detailsGaranties = new ArrayList<>();
			
			
		 
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
				  detailsEvolutionGarantie.setEpargne_fin_annee(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne Constituée en fin d'année")));
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
				  detailsEvolutionGarantieHayya.setEpargne_fin_annee(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne Constituée en fin d'année")));
				  detailsEvolutionGarantieHayya.setGain_financier(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Gain Financier")));
				  detailsEvolutionGarantieHayya.setGain_fisc_annuel(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Gain fiscal Annuel")));
				  detailsEvolutionGarantieHayya.setValeur_rachat(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Valeur rachat")));
				  detailsEvolutionGarantieHayya.setCumul_vers(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Cumul Versements")));
					
				  detailsEvolutionGarantieHayyas.add(detailsEvolutionGarantieHayya);
				  
				  
				  
				/// pour bilan financier
				  primeversee_ = primeversee_.add(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Prime commerciale")));
				  
				  if(i == DetailsEvolutionGarantieListJSON.size()-1) {
					  
					  epargneprev_ = new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne Constituée en fin d'année"));
				       
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
        
        FacesMessage msg = new FacesMessage("ERREUR", "Erreur interne, veuillez contacter l'administrateur! ");
		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);

      }

     
	this.selectedProposition = null;
	
	//sendPost();
	//return "tarif7ayya?faces-redirect=true";
	if(adhesionValide){
		return "tarif7ayya?faces-redirect=true";
	}
	else {
		return "tarif7ayya?faces-redirect=false";
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
	
	
}
