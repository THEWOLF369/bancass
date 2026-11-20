package com.star.Ba.jsf;

import static io.restassured.path.xml.XmlPath.with;
import io.restassured.path.xml.element.Node;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.ws.rs.core.MediaType;

import org.apache.poi.util.SystemOutLogger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
//01/11/2022
import org.primefaces.util.ComponentUtils;
//import com.star.Ba.jsf.util.ComponentUtils;
//import org.springframework.web.client.RestTemplate;








import com.star.Ba.commun.Age;
import com.star.Ba.commun.AgeCalculator;
import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAdherentDAO;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IDureeDAO;
import com.star.Ba.dao.ILocgouvDAO;
import com.star.Ba.dao.IPropositionDAO;
import com.star.Ba.dao.ITypeAbonnementDAO;
import com.star.Ba.dao.ITypeVoieDAO;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.Proprietes;
import com.star.Ba.jsf.util.ProxyConnectionFactory;
import com.star.Ba.jsf.util.TokenSTB;
import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Duree;
import com.star.Ba.model.Locgouv;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.ProfessionClient;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.TarifAssist;
import com.star.Ba.model.TypeAbonnement;
import com.star.Ba.model.TypeVoie;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.parametres.ExampleResourceClient;
import com.star.Ba.parametres.TrustAllX509TrustManager;
//import com.star.Ba.parametres.Test.ExampleResourceClient;
//import com.star.Ba.parametres.Test.TrustAllX509TrustManager;
import com.star.Ba.service.AssistantVoiyagetServiceInter;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

//import static io.restassured.path.xml.XmlPath.with;
//import io.restassured.path.xml.element.Node;
import org.joda.time.DateTime;

import ws.client.ParseException_Exception;
import ws.client.WsRecupClientService;
import ws.client.WsRecupererClient;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.client.WebTarget;

@ManagedBean
@SessionScoped  
public class AssistanceVoiyageController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean disabledall = false;

	private Map<String, Object> listnumplc = null;
	private String numplc;
    private boolean affiche = true;
	private Map<String, Object> listnatabon = null;
	private String natabon;

	private Map<Long, Object> listduree = null;
	private double duree1 = 10.14;
	@EJB
	private IPropositionDAO propositionDAO;
	
	@EJB
	private IAdhesionDAO adhesionDAO ;
	@EJB
	private ITypeVoieDAO typeVoieDAO;
	@EJB
	private IDureeDAO dureeDAO;
	@EJB
	private IAdherentDAO adherentDAO;
	private Date aujourdhui=new Date();
	private Date effDate;
	
	private TypeAbonnement typeAbonnement;
	//private Date datefin;*
	private Date fin;
	private Date datenaiss, mindate;
	private Age ageCalc;
	private double age;
	private String propositionId;
	public Date currentDate;
	public String format;
	private String propid;
	private int dureeA;
	private int dureeM;
	@EJB
	private ITypeAbonnementDAO typeAbonnementDAO;

	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;// = new Utilisateurs();
	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;
	
	/*@ManagedProperty("#{devisBean}")
	private DevisBean devisBean;
	

	public DevisBean getDevisBean() {
		return devisBean;
	}

	public void setDevisBean(DevisBean devisBean) {
		this.devisBean = devisBean;
	}*/
	
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	
	
	

	public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}

	public ClientBean getClientBean() {
		return clientBean;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}
	public String getPropositionId() {
		return propositionId;
	}

	public void setPropositionId(String propositionId) {
		this.propositionId = propositionId;
	}
	public String getPropid() {
		return propid;
	}

	public void setPropid(String propid) {
		this.propid = propid;
	}
	private String cod_societe;

	@EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;

	@PostConstruct
	public void init() {
		aujourdhui = new Date();
		setAllTypeAbonnement(serviceAssistantVoiyagetService
				.getAllTypeAbonnement());
		setAllDurees(serviceAssistantVoiyagetService.getAllDurees());

		Convention convention = serviceAssistantVoiyagetService
				.getConventionBySociete(utilisateur.getAgence().getSociete());

		Produit produit = serviceAssistantVoiyagetService
				.getProduitByCode(ENUMCODEPRODUIT.ASSISTANCEVOIYAGE.getCode());

		setAllPoliseByCodeAndConvention(serviceAssistantVoiyagetService
				.getPolice_by_conv_prod(convention, produit));

		Produit produitTLS = serviceAssistantVoiyagetService
				.getProduitByCode(ENUMCODEPRODUIT.TLSAssistV.getCode());

		setAllPoliseByCodeAndConventionTLS(serviceAssistantVoiyagetService
				.getPolice_by_conv_prod(convention, produitTLS));
		
		
		typeVoies = typeVoieDAO.findAll();
		 
//		List<Locgouv> list = locgouvDAO.findAll();
//
//		for (Locgouv locgouv : list) {
//			if (mapAllCodePostalByGouvernerat.containsKey(locgouv
//					.getGouvernora())) {
//				mapAllCodePostalByGouvernerat.get(locgouv.getGouvernora()).add(
//						locgouv);
//			} else {
//				allGouverneras.add(locgouv.getGouvernora());
//				mapAllCodePostalByGouvernerat.put(locgouv.getGouvernora(),
//						new ArrayList<Locgouv>());
//			}
//
//		}
		
		allGouverneras = locgouvDAO.findGouv();
		allProfessions=clientBean.getProfessions();
		selectedProposition = null;

	}

	public String calculTarif() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(newAdhesion.getDatDebut());
		cal.add(Calendar.DATE,Integer.parseInt(selectedDuree.getValDuree()));
		fin=cal.getTime();
		newAdhesion.setDatFin(fin);
		System.out.println("fin dans tarif="+fin);
			
		Calendar c = Calendar.getInstance();
		c.setTime(newAdhesion.getDatDebut());
		Calendar c1 = Calendar.getInstance();
		c1.setTime(aujourdhui);

		if((c.get(Calendar.DAY_OF_YEAR) < c1.get(Calendar.DAY_OF_YEAR)) && (c.get(Calendar.YEAR)==c1.get(Calendar.YEAR))){
			//if( c.compareTo(c1)	<= 0 ) {
			FacesMessage msg = new FacesMessage("La date d'effet doit être supérieur à la date du jour!", "La date d'effet doit être supérieur à la date du jour!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("La date d'effet doit être supérieur à la date du jour!", msg);
			return null;
		}


		Double prime = serviceAssistantVoiyagetService
				.calculePrimeAssistantVoiyage(selectedDuree.getIdDuree(),
						newAdhesion.getTypeAbonnement().getIdTypeAbonnement());

		primeGlobal = prime;
		if(newAdhesion.getAdherents()!=null && newAdhesion.getAdherents().isEmpty()&&newAdhesion.getNombrePersonne().equals(BigDecimal.valueOf(1))){
			Adherent adherent = new Adherent();

			adherent.setNomAdherent(clientBean.getClient().getNomClient());
			adherent.setPrenomAdherent(clientBean.getClient().getPrenomClient());

			adherent.setDatNaiss(clientBean.getClient().getDatNaiss());
			adherent.setClient(clientBean.getClient());

			newAdhesion.getAdherents().add(adherent);

		}
		boolean adhesionValide= true;
		if(newAdhesion.getAdherents()!=null && !newAdhesion.getAdherents().isEmpty()){
			for (Adherent adherent : newAdhesion.getAdherents()) {
				if(adherent !=null && adherent.getDatNaiss()==null){
					FacesMessage msg = new FacesMessage("La date de naissance des adérents ne doit pas  être nulle !", "Merci de renseigner la date de naissance!");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage("La date de naissance des adérents ne doit pas  être nulle !!", msg);
					adhesionValide=false;
				}
			}
		}
		
		
		newAdhesion.setMtPrime(prime);
		newAdhesion.setDuree(Double.parseDouble(selectedDuree.getValDuree()));
		newAdhesion.setDatcreation(new Date());
		newAdhesion.setDatFin(fin);
		if(adhesionValide){
			return "AssistantTarif?faces-redirect=true";
		}
		else {
			//return "AssistantTarif?faces-redirect=false";
			return null;
		}


	}
	public String calculTarifTLS() {
		
		Calendar cal = Calendar.getInstance();
		if (newAdhesion.getDatDebut() == null )
		{
			FacesMessage msg = new FacesMessage("La date debut est obligatoire!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("La date debut est obligatoire!", msg);
			System.out.println("if null =====================");
			
			return null;
		
		}
		else {
			
			cal.setTime(newAdhesion.getDatDebut());
		
		cal.add(Calendar.DATE,Integer.parseInt(selectedDuree.getValDuree()));
		fin=cal.getTime();
		newAdhesion.setDatFin(fin);
  
		Calendar c = Calendar.getInstance();
		
		
		cal.setTime(newAdhesion.getDatDebut());
		
		
		//c.setTime(newAdhesion.getDatDebut());
		//d1= c.setTime(newAdhesion.getDatDebut());
		Calendar c1 = Calendar.getInstance();
		c1.setTime(getAujourdhui());
       // SimpleDateFormat  sdf = new SimpleDateFormat("dd/mm/yyyy");
       // Date d1  =  sdf.parse("newAdhesion.getDatDebut()");
       // Date d2 = sdf.
		//if(c.get(Calendar.DAY_OF_YEAR) < c1.get(Calendar.DAY_OF_YEAR)){
		if(c.compareTo(c1)<  0) {
			System.out.println("comparaison date " + c.compareTo(c1));
			FacesMessage msg = new FacesMessage("La date d'effet doit être supérieur ou égale à la date du jour!", "La date d'effet doit être supérieur à la date du jour!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("La date d'effet doit être supérieur à la date du jour!", msg);
			return null;
		}
		
		if(newAdherent.getDatNaiss()==null){

			FacesMessage msg = new FacesMessage("La date de naissance de l'assuré est obligatoire!", "La date de naissance de l'assuré est obligatoire!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("La date de naissance de l'assuré est obligatoire!", msg);
			return null;
		}
		calculAge(newAdherent.getDatNaiss());
		TarifAssist tarifAssist = serviceAssistantVoiyagetService.calculePrimeAssistantVoiyageTLS(selectedDuree.getIdDuree(),newAdhesion.getTypeAbonnement().getIdTypeAbonnement(), newAdhesion.getPolice(), age);
		System.out.println("objet="+tarifAssist);
		primeGlobal = tarifAssist.getPrime().add(tarifAssist.getPrime().multiply(new BigDecimal(tarifAssist.getMajoration())).divide(new BigDecimal(100))).doubleValue() ;
		
		newAdhesion.setMtPrime(primeGlobal-newAdhesion.getPolice().getMtAcc().doubleValue());
		newAdhesion.setCout(newAdhesion.getPolice().getMtAcc());
		newAdhesion.setFga(newAdhesion.getPolice().getFga());
		newAdhesion.setPrimetot(primeGlobal+newAdhesion.getPolice().getFga());
		newAdhesion.setDuree(Double.parseDouble(selectedDuree.getValDuree()));
		newAdhesion.setDatcreation(new Date());
		newAdhesion.setEtat("V");
		newAdhesion.setPack(tarifAssist.getPack());
		newAdhesion.setMajoration(tarifAssist.getMajoration());
		newAdhesion.setDatFin(fin);
		return "TarifAssTls?faces-redirect=true";
		}
		
		
	
		
	}

	private List<TypeAbonnement> allTypeAbonnement = new ArrayList<TypeAbonnement>();

	private TypeAbonnement selctedTypeAbonnement = new TypeAbonnement();

	private List<Police> allPoliseByCodeAndConvention = new ArrayList<Police>();
	private List<Police> allPoliseByCodeAndConventionTLS = new ArrayList<Police>();



	public List<Police> getAllPoliseByCodeAndConventionTLS() {
		return allPoliseByCodeAndConventionTLS;
	}

	public void setAllPoliseByCodeAndConventionTLS(
			List<Police> allPoliseByCodeAndConventionTLS) {
		this.allPoliseByCodeAndConventionTLS = allPoliseByCodeAndConventionTLS;
	}

	public List<Police> getAllPoliseByCodeAndConvention() {
		return allPoliseByCodeAndConvention;
	}

	public void setAllPoliseByCodeAndConvention(List<Police> allPoliseByCodeAndConvention) {
		this.allPoliseByCodeAndConvention = allPoliseByCodeAndConvention;
	}

	double primeGlobal;

	public double getPrimeGlobal() {
		return primeGlobal;
	}

	public void setPrimeGlobal(double primeGlobal) {
		this.primeGlobal = primeGlobal;
	}

	private Adhesion newAdhesion = new Adhesion();

	private Proposition newProposition = new Proposition();

	Adherent newAdherent = new Adherent();

	private Client newClient = new Client();

	private Duree selectedDuree = new Duree();
	 

	private Date dateSystem = new Date();

	private Date dateDebut = new Date();

	//private Date dateFin = new Date();
	private Date dateCreation = new Date(new Date().getTime());

	private int nombrePersonne;

	private String destination = new String();

	private String personneAprevenir = new String();

	private String tel = new String();

	List<Adherent> adherentsList = new ArrayList<Adherent>();


	public void initialisationClientAndAdhesion(){
		newAdhesion = new Adhesion();
		newAdhesion.setNombrePersonne(BigDecimal.valueOf(1));
		newAdhesion.setTypeAbonnement(typeAbonnementDAO.find((long)1));
		nomberPersonneMax = 1;
		nombrePersonneMin = 1;		
		desabledGenerateNotice=true;
		desabledEncaiss=true;
		disabledall = false;
		selectedProposition=null;
		clientBean.setClient(new Client());
		selectedDuree=dureeDAO.find((long)1);
		System.out.println("selectedDuree="+selectedDuree.getValDuree());
		
		
		/** ws client**/
		clientBean.setFromWS(false);
		clientBean.setNomWS(false);
		clientBean.setPrenomWS(false);
		clientBean.setDateNaisWS(false);
		clientBean.setCinWS(false);
		clientBean.setRibWS(false);
	}
	
	
	
	public String navigateToDevisAssistanceVoiyage() {
		
		if(selectedProposition == null){
		initialisationClientAndAdhesion();
		}
		return "devis_Assistantvoyage";

	}
	public String navigateToDevisAssistanceVoiyageTLS() {
		if(selectedProposition == null){
			initialisationClientAndAdhesion();
			}
		newProposition = null;
		return "devis_AssistvoyageTLS";

	}


	public void addNewAdherentTolist() {
		Calendar c = Calendar.getInstance();
		c.setTime(getAujourdhui());
		Calendar c1 = Calendar.getInstance();
		c1.setTime(newAdherent.getDatNaiss());

//		if(c.get(Calendar.DAY_OF_YEAR) < c1.get(Calendar.DAY_OF_YEAR)){
//
//			FacesMessage msg = new FacesMessage("La date de naissance doit être inférieur à la date du jour!", "La date de naissance doit être inférieur à la date du jour!");
//			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//			FacesContext.getCurrentInstance().addMessage("La date de naissance doit être inférieur à la date du jour!", msg);
//		}
//		else{
			if (newAdhesion.getAdherents().size() < newAdhesion.getNombrePersonne().intValue()) {

				newAdherent.setAdhesion(newAdhesion);
				newAdherent.setClient(clientBean.getClient());
				newAdhesion.getAdherents().add(newAdherent);
				newAdherent = new Adherent();

			} else {
				FacesMessage msg = new FacesMessage("ERREUR","le nombre maximaum pour le type d'abonnement "+ newAdhesion.getTypeAbonnement().getValTypeAbonnement() + " est "	+ newAdhesion.getNombrePersonne().intValue());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);

			}
		//}

	}
	
	
	public void addNewAdherentTolist_TLS() {
		Calendar c = Calendar.getInstance();
		c.setTime(getAujourdhui());
		Calendar c1 = Calendar.getInstance();
		c1.setTime(newAdherent.getDatNaiss());

//		if(c.get(Calendar.DAY_OF_YEAR) < c1.get(Calendar.DAY_OF_YEAR)){
//
//			FacesMessage msg = new FacesMessage("La date de naissance doit être inférieur à la date du jour!", "La date de naissance doit être inférieur à la date du jour!");
//			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//			FacesContext.getCurrentInstance().addMessage("La date de naissance doit être inférieur à la date du jour!", msg);
//		}
//		else{
			if (newAdhesion.getAdherents().size() < newAdhesion.getNombrePersonne().intValue()) {

				newAdherent.setAdhesion(newAdhesion);
				newAdherent.setClient(clientBean.getClient());
				newAdhesion.getAdherents().add(newAdherent);
				//newAdherent = new Adherent();

			} else {
				FacesMessage msg = new FacesMessage("ERREUR","le nombre maximaum pour le type d'abonnement "+ newAdhesion.getTypeAbonnement().getValTypeAbonnement() + " est "	+ newAdhesion.getNombrePersonne().intValue());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);

			}
		//}

	}
	
	
	
	
	Adherent selectedAdherentToUpdate = new Adherent();

	public Adherent getSelectedAdherentToUpdate() {
		return selectedAdherentToUpdate;
	}

	public void setSelectedAdherentToUpdate(Adherent selectedAdherentToUpdate) {
		this.selectedAdherentToUpdate = selectedAdherentToUpdate;
	}

	public void deleteAderentFromList() {

		newAdhesion.getAdherents().remove(selectedAdherentToUpdate);
		// adherentsList.remove(selectedAdherentToUpdate);

	}

	public List<Adherent> getAdherentsList() {
		return adherentsList;
	}

	public void setAdherentsList(List<Adherent> adherentsList) {
		this.adherentsList = adherentsList;
	}

	public Adherent getNewAdherent() {
		return newAdherent;
	}

	public void setNewAdherent(Adherent newAdherent) {
		this.newAdherent = newAdherent;
	}

	public Converter getDureConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (Duree obj : getAllDurees()) {
					if (obj.getValDuree().equals(value)) {
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

	public Converter getNatureAbonnementConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (TypeAbonnement obj : getAllTypeAbonnement()) {

					if (obj.getValTypeAbonnement().equals(value)) {
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

	public Converter getPoliceConverterTLS() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (Police obj : getAllPoliseByCodeAndConventionTLS()) {

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

		return "clientAssistant?faces-redirect=true";
	}
	
	public String navigateToClientAssitancePage() {
		if(clientBean.getClient().getGouver()==null){
			System.out.println("enter to clean");
			clientBean.getAllCodePostalByGouvernera().clear();
		}
		
		 
		
		List<Adherent> adherents_ = new ArrayList<>();
		newAdhesion.setAdherents(adherents_);

		return "clientAssistant?faces-redirect=true";
	}
	
	 

	public String navigateToClientPageTLS() {
		if(clientBean.getClient().getGouver()==null){
			System.out.println("enter to clean");
			clientBean.getAllCodePostalByGouvernera().clear();
		}

		return "clientAssistTLS?faces-redirect=true";
	}
	
	

	public String navigateToListAssuree() {
		System.out.println("enter navigateToListAssuree");
		if (clientBean.getClient().getIdentifiant() != null ) {
			newAdherent=new Adherent(); 
			if(newAdhesion.getAdherents().isEmpty()){
				//newAdhesion.getAdherents().clear();
				Adherent adherent = new Adherent();

				adherent.setNomAdherent(clientBean.getClient().getNomClient());
				adherent.setPrenomAdherent(clientBean.getClient().getPrenomClient());

				adherent.setDatNaiss(clientBean.getClient().getDatNaiss());
				adherent.setClient(clientBean.getClient());

				newAdhesion.getAdherents().add(adherent);
			}
			// adherentsList.add(adherent);
			// if(newAdhesion.getTypeAbonnement().getMaxNumber()==1){
			// return null;
			// }else{
			return "addAssureToAssistance?faces-redirect=true&keep=true";
			// }

		} else {

			FacesMessage msg = new FacesMessage("Ajouter un client", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

			return null;
		}
	}


	public String navigateToListAssureTLS() {
		System.out.println("enter navigateToListAssuree");
		if (clientBean.getClient().getIdentifiant() != null ) {
			//			newAdherent=new Adherent(); 
			//			if(newAdhesion.getAdherents().isEmpty()){
			//			Adherent adherent = new Adherent();
			//
			//			adherent.setNomAdherent(clientBean.getClient().getNomClient());
			//			adherent.setPrenomAdherent(clientBean.getClient().getPrenomClient());
			//
			//			adherent.setDatNaiss(clientBean.getClient().getDatNaiss());
			//			adherent.setClient(clientBean.getClient());
			//
			//			newAdhesion.getAdherents().add(adherent);
			//			}

			return "addAssureTLS?faces-redirect=true&keep=true";

		} else {

			FacesMessage msg = new FacesMessage("Ajouter un client", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

			return null;
		}
	}

	public String returnToDevisAssistance() {
		initialisationClientAndAdhesion();
		return "devisAssistantvoyage";
	}

	public void identificationValidator(FacesContext context, UIComponent comp,
			Object value) {

		String docTypeId = ComponentUtils.findComponentClientId("docType");
		if (docTypeId != null) {
			UIInput docTypeInput = (UIInput) context.getViewRoot()
					.findComponent(docTypeId);
			if (docTypeInput != null) {
				String docType = (String) docTypeInput.getValue();
				if (docType != null && docType.equals("CIN")) {
					String cin = (String) value;

					// cin should start with either 0 or 1, length = 8
					Pattern pattern = Pattern.compile("^[0, 1]\\d{7}");
					Matcher matcher = pattern.matcher(cin);
					if (!matcher.find()) {
						String msg = "CIN doit commencer par 0 ou 1 sur 8 chiffre";
						throw new ValidatorException(new FacesMessage(
								FacesMessage.SEVERITY_ERROR, msg, msg));
					}
				}
			}

		}
	}

	public void onDateSelectDateFin() {

		if (selectedDuree.getValDuree() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(newAdhesion.getDatDebut());
			cal.add(Calendar.DATE,
					Integer.parseInt(selectedDuree.getValDuree()));
			newAdhesion.setDatFin(cal.getTime());
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(newAdhesion.getDatDebut());
			cal.add(Calendar.DATE, 8);
			newAdhesion.setDatFin(cal.getTime());
		}

	}

	public void onDureeSelect() {

		if (newAdhesion.getDatDebut() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(newAdhesion.getDatDebut());
			cal.add(Calendar.DATE,
					Integer.parseInt(selectedDuree.getValDuree()));
			newAdhesion.setDatFin(cal.getTime());
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE,
					Integer.parseInt(selectedDuree.getValDuree()));
			newAdhesion.setDatFin(cal.getTime());
		}
	}

	private double nomberPersonneMax = 1;

	private double nombrePersonneMin = 1;

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

	public void onNatureAbonnementChangeUpdateNomberPeronne() {
		nomberPersonneMax = (double) newAdhesion.getTypeAbonnement()
				.getMaxNumber();

		if (nomberPersonneMax > 1) {
			//		System.out.println("enter 2");

			nombrePersonneMin = 2;
			maxnomberPersonneOne = false;

		}
		if (nomberPersonneMax == 1) {
			//	System.out.println("enter 1");

			newAdhesion.setNombrePersonne(BigDecimal.valueOf(1));
			maxnomberPersonneOne = true;

		}

	}

	boolean maxnomberPersonneOne = true;

	public boolean isMaxnomberPersonneOne() {

		return maxnomberPersonneOne;
	}

	public void setMaxnomberPersonneOne(boolean maxnomberPersonneOne) {
		this.maxnomberPersonneOne = maxnomberPersonneOne;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateSystem() {
		return dateSystem;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

//	public Date getDateFin() {
//		return dateFin;
//	}
//
//	public void setDateFin(Date dateFin) {
//		this.dateFin = dateFin;
//	}

	public void setDateSystem(Date dateSystem) {
		this.dateSystem = dateSystem;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getPersonneAprevenir() {
		return personneAprevenir;
	}

	public void setPersonneAprevenir(String personneAprevenir) {
		this.personneAprevenir = personneAprevenir;
	}

	public int getNombrePersonne() {
		return nombrePersonne;
	}

	public void setNombrePersonne(int nombrePersonne) {
		this.nombrePersonne = nombrePersonne;
	}

	List<Duree> allDurees = new ArrayList<Duree>();

	public List<Duree> getAllDurees() {
		return allDurees;
	}

	public void setAllDurees(List<Duree> allDurees) {
		this.allDurees = allDurees;
	}

	public TypeAbonnement getSelctedTypeAbonnement() {
		return selctedTypeAbonnement;
	}

	public Duree getSelectedDuree() {
		return selectedDuree;
	}

	public void setSelectedDuree(Duree selectedDuree) {
		this.selectedDuree = selectedDuree;
	}

	public List<TypeAbonnement> getAllTypeAbonnement() {
		return allTypeAbonnement;
	}

	public void setAllTypeAbonnement(List<TypeAbonnement> allTypeAbonnement) {
		this.allTypeAbonnement = allTypeAbonnement;
	}

	public void setSelctedTypeAbonnement(TypeAbonnement selctedTypeAbonnement) {
		this.selctedTypeAbonnement = selctedTypeAbonnement;
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

	public Client getNewClient() {
		return newClient;
	}

	public void setNewClient(Client newClient) {
		this.newClient = newClient;
	}

	public void updatenatabon() {

		// List<String> liste = parametresAVService.getlistnatabon();
		// int index = 0;

		// listnatabon= parametresAVService.getlistnatabon();

		System.out.println("listnatabon=" + listnatabon);

		natabon = (String) listnatabon.values().toArray()[0];

		System.out.println("natabon=" + natabon);

	}

	// public void updateduree() {
	//
	// //List<String> liste = parametresAVService.getlistnatabon();
	// //int index = 0;
	//
	// // listduree= parametresAVService.getlistduree();
	//
	// System.out.println("la listduree="+listduree);
	//
	// duree = (String) listduree.values().toArray()[0];
	//
	// System.out.println("la duree="+duree);
	//
	// }
	//

	public void SopCurrentDate() {
		currentDate = new Date();
		format = "dd/MM/yyyy";
	}

//	public void updateDate() {
//
//		Calendar c = Calendar.getInstance();
//		c.setTime(effDate);
//		c.add(Calendar.YEAR, dureeA);
//		c.add(Calendar.MONTH, dureeM);
//		c.add(Calendar.DAY_OF_MONTH, -1);
//		datefin = c.getTime();
//	}

	public Map<String, Object> getListnumplc() {
		return listnumplc;
	}

	public void setListnumplc(Map<String, Object> listnumplc) {
		this.listnumplc = listnumplc;
	}

	public String getNumplc() {
		return numplc;
	}

	public void setNumplc(String numplc) {
		this.numplc = numplc;
	}

	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getCod_societe() {
		return cod_societe;
	}

	public void setCod_societe(String cod_societe) {
		this.cod_societe = cod_societe;
	}

	public Date getMindate() {
		return mindate;
	}

	public void setMindate(Date mindate) {
		this.mindate = mindate;
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

//	public Date getDatefin() {
//		return datefin;
//	}
//
//	public void setDatefin(Date datefin) {
//		this.datefin = datefin;
//	}

	public Date getDatenaiss() {
		return datenaiss;
	}

	public void setDatenaiss(Date datenaiss) {
		this.datenaiss = datenaiss;
	}

	public Age getAgeCalc() {
		return ageCalc;
	}

	public void setAgeCalc(Age ageCalc) {
		this.ageCalc = ageCalc;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	public Map<String, Object> getListnatabon() {
		return listnatabon;
	}

	public void setListnatabon(Map<String, Object> listnatabon) {
		this.listnatabon = listnatabon;
	}

	public String getNatabon() {
		return natabon;
	}

	public void setNatabon(String natabon) {
		this.natabon = natabon;
	}

	public Map<Long, Object> getListduree() {
		return listduree;
	}

	public void setListduree(Map<Long, Object> listduree) {
		this.listduree = listduree;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getDureeA() {
		return dureeA;
	}

	public void setDureeA(int dureeA) {
		this.dureeA = dureeA;
	}

	public int getDureeM() {
		return dureeM;
	}

	public void setDureeM(int dureeM) {
		this.dureeM = dureeM;
	}

	
	
	/*public class TrustAllX509TrustManager implements X509TrustManager {
	    public X509Certificate[] getAcceptedIssuers() {
	        return new X509Certificate[0];
	    }

	    public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
	            String authType) {
	    }

	    public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
	            String authType) {
	    }

	}*/
	

  /*  static class ExampleResourceClient {

        private WebResource webResource;
        private com.sun.jersey.api.client.Client client;
        //private static final String BASE_URI = "https://ecom.stb.com.tn:9090/BanqueAssurance/api/DNC/";

        public ExampleResourceClient(String path,String BASE_URI) {
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = com.sun.jersey.api.client.Client.create(config);
            webResource = client.resource(BASE_URI).path(path);
        }

        public String getXml() throws UniformInterfaceException {
            WebResource resource = webResource;
            return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(String.class);
        }

        public String putXml(Object requestEntity) throws UniformInterfaceException {
            return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(String.class, requestEntity);
        }

        public void close() {
            client.destroy();
        }
    }*/
	
/*	public String tokenStb() {
		
		final String URL_WS_URL_TOKEN_STB = Constante.URL_TOKEN_STB;
		
		//final String URL_WS_URL_TOKEN_STB = "https://openbank.stb.com.tn:9443/authentication/token";
		
		Form f = new Form();
		 f.add("username", "star_BMehdi");
		 f.add("password", "$927283tb@2022SI+");
		 f.add("grant_type", "password");
		 f.add("scope", "openid 8d083a5a-32f1-42b1-9f5f-0444da5b2889");
		 f.add("client_id", "8d083a5a-32f1-42b1-9f5f-0444da5b2889");
		 f.add("response_type", "token id_token");
		
		System.out.println("Constante.URL_TOKEN_STB "+Constante.URL_TOKEN_STB);
		
		try {
			
		
			SSLContext ctx = null;
			 ctx = SSLContext.getInstance("TLSv1.2");
	         ctx.init(null, null, new SecureRandom());
	         HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
	         com.sun.jersey.api.client.Client client= com.sun.jersey.api.client.Client.create(new com.sun.jersey.api.client.config.DefaultClientConfig());

			
		    WebResource resource = client
		       .resource(URL_WS_URL_TOKEN_STB);
		    
		    ClientResponse response = resource.accept(MediaType.APPLICATION_XML_TYPE,
			        MediaType.APPLICATION_JSON_TYPE).
			        header("MyHeader", "Jersey Client")
			       .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			       .post(ClientResponse.class, f);
		    

		    
		    
		    if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + response.getStatus());
		    	 
				  //return null;
			}

			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
			
			JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
			
			//JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON( response.getEntity(String.class) ); 
			 
			 String access_token = jsonOutput.getString("access_token");
			 
			 return access_token;
			
			} catch (Exception e) {

				e.printStackTrace();
				
				return null;

			  }
		
	}*/
	
	public void rechercherDepuisWS() {
		
		/**** demande STB pour ne pas modifier les donner depuis WS ***/
		clientBean.setFromWS(true); 
		clientBean.setNomWS(false);
		clientBean.setPrenomWS(false);
		clientBean.setDateNaisWS(false);
		clientBean.setCinWS(false);
		clientBean.setRibWS(false);
		/*********************************************************/
		
		String numCompte = clientBean.getClient().getNcompte();
		String numCIN = clientBean.getClient().getIdentifiant();
		String typeCompte = clientBean.getClient().getTypeDoc();
		
		clientBean.setClient(new Client());
		
		clientBean.getClient().setNcompte(numCompte);
		clientBean.getClient().setIdentifiant(numCIN);
		clientBean.getClient().setTypeDoc(typeCompte);
		
		
		System.out.println("numCompte "+numCompte);
		System.out.println("numCIN "+numCIN);
		System.out.println("nom "+clientBean.getClient().getTypeDoc());
		
		 if(numCompte == null || numCIN == null  || numCompte.isEmpty() || numCIN.isEmpty()) {
			  
			 
			  
			  FacesMessage msg = new FacesMessage("ERREUR", "Veuillez saisir le n° document et le n° compte!");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				return;
		  }
		
		
	
		String token = TokenSTB.tokenStb(reportController);
		
		if(token == null) {
			
			System.out.println("token null  ");
			clientBean.setClient(new Client());
			
			FacesMessage msg = new FacesMessage("ERREUR", "Pas de réponse au WS Token!");
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			  return;
			
		}else {
			
			if(token.equalsIgnoreCase("exception")) {
				
				clientBean.setClient(new Client());
				
	     		System.out.println("probleme de Exception  token");
	     		FacesMessage msg = new FacesMessage("ERREUR", "Erreur de connexion au WS Token!");
				  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				  return;
			}
			
			if(token.equalsIgnoreCase("not200")) {
				
				clientBean.setClient(new Client());
				
	     		System.out.println("not200 token  ");
	     		FacesMessage msg = new FacesMessage("ERREUR", "Erreur: problème de droit d'accès au WS Token!");
				  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				  return;
			}
		}
		
		final String URL_WS_URL_CLIENT_STB = Constante.URL_CLIENT_STB;
		
		//final String URL_WS_URL_CLIENT_STB = "https://openbank.stb.com.tn:9443/v0/CustomerInformations";
		
		//final String URL_WS_URL_CLIENT_STB = "https://openbank.stb.com.tn/v0/CustomerInformations";
		
		
		String numTypeDoc = "01";
		
		 switch (clientBean.getClient().getTypeDoc() ){
 	   	
  	   case "CIN" : numTypeDoc = "01";
  		   break;
  		   
  	   case "Passport": numTypeDoc = "03";
  		   break;
  		   
  	   case "CarteSejour": numTypeDoc = "04";
		   break;
  		     		   
  	   default : numTypeDoc = "01";
  		   break;
  	   
  	   }
		
		String input = "{"
				+ "\"NumeroCompte\":\""+numCompte+"\","
				+ "\"TypePiece\":\""+numTypeDoc+"\","
				+ "\"NumeroPiece\":\""+numCIN+"\""
				+ "}";
		
		System.out.println("URL_WS_URL_CLIENT_STB "+Constante.URL_CLIENT_STB);
		
		try {
		
		

	    
	    com.sun.jersey.api.client.Client client = JerseyClient.ApiClient(reportController);
	    ClientResponse response = JerseyClient.PostReponseDuClientSTBEncaissement(URL_WS_URL_CLIENT_STB, token, input, client, reportController);
	   
	    
	    
	    if ( response == null) {
			System.out.println("probleme de connexion vide");
			
			clientBean.setClient(new Client());
			
			FacesMessage msg = new FacesMessage("Erreur: Pas de réponse au WS client!", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

			return;
		}

	    
	    
	    if (response.getStatus() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//			     + response.getStatus());
	    	
	    	clientBean.setClient(new Client());
     		
     		FacesContext.getCurrentInstance().addMessage(null, 
     				new FacesMessage(FacesMessage.SEVERITY_FATAL, 
     						"Erreur WS client (code ="+response.getStatus()+") ", ""));
     		return;
		}

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
		
		JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
		
		 
		if(!jsonOutput.getString("ResponseCode").equalsIgnoreCase("null")){
			
			
			
			if(jsonOutput.getString("ResponseCode").equalsIgnoreCase("01")) {
				
				clientBean.setClient(new Client());
				
				FacesMessage msg = new FacesMessage("Client inexistant!", "Client inexistant");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				return;
				
			} else if(jsonOutput.getString("ResponseCode").equalsIgnoreCase("00")) {
				

		/**** demande STB pour ne pas modifier les donner depuis WS ***/
				if(jsonOutput.getString("LastName") == null || jsonOutput.getString("LastName").equals("")) {
					System.out.println("(\"LastName\") == null \n");
					clientBean.setNomWS(false);
				}else {
					System.out.println("(\"LastName\") == not null \n");
					clientBean.setNomWS(true);
					clientBean.getClient().setNomClient(jsonOutput.getString("LastName"));
				}
				
				if(jsonOutput.getString("FirstName") == null || jsonOutput.getString("FirstName").equals("")) {
					
					clientBean.setPrenomWS(false);
				}else {
					
					clientBean.setPrenomWS(true);
					clientBean.getClient().setPrenomClient(jsonOutput.getString("FirstName"));
					
				}
				
				if(jsonOutput.getString("BirthDate") == null || jsonOutput.getString("BirthDate").equals("") 
						|| jsonOutput.getString("BirthDate").equalsIgnoreCase("null") ) {
					System.out.println("(\"BirthDate\") == null \n");
					clientBean.setDateNaisWS(false);
				}else {
					System.out.println("(\"BirthDate\") == not null \n");
					clientBean.setDateNaisWS(true);
					
					
					try {
					  DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			    	   DateTime dt = formatter.parseDateTime(jsonOutput.getString("BirthDate"));
			    	   
			    	   clientBean.getClient().setDatNaiss(dt.toDate());
			    	   
			    	   // pour calcul age
			    	   clientBean.calculateAgeWithJava7();
					}catch (Exception e) {
						// TODO: handle exception
						clientBean.setDateNaisWS(false);
					}
			    	   //
					
					
				}
				
				clientBean.setCinWS(true);
				clientBean.setRibWS(true);
				
		/**** demande STB pour ne pas modifier les donner depuis WS ***/		
		 
	       
	       
	        
	    	 
	    	   
	         
			 
			 if(jsonOutput.getString("Gender") == null || jsonOutput.getString("Gender").equals("")
				 || jsonOutput.getString("Gender").equalsIgnoreCase("null")) {
				 
				 clientBean.getClient().setSexe("");
				 
			 }else{
				 clientBean.getClient().setSexe(jsonOutput.getString("Gender").substring(0,1).toUpperCase());
			 }
			 
	        clientBean.getClient().setTypeVoie(clientBean.getTypeVoies().get(5));
	        
	        clientBean.getClient().setAdrClient(jsonOutput.getString("Adress")); 
	        
	        if(!jsonOutput.getString("ZipCode").equalsIgnoreCase("null")){
	      	   
	      	 //System.out.println("gouver="+locgouvDAO.findByPostaCode(dcNode.getNode("codepostal").toString()).getGouvernora());  
	      	  Locgouv gouver =locgouvDAO.findByPostaCode(jsonOutput.getString("ZipCode"));
	      	 if(gouver!= null){
	      	 clientBean.getClient().setGouver( gouver.getGouvernora() ); 
	      	 }
	      	 else{
	  			 clientBean.getClient().setGouver("");
	      	 	}
	      	 
	      	 //clientBean.setSelectedLocgouv(new Locgouv() );
	      	 
	      	 
	      	 
	      	 /*clientBean.setSelectedLocgouv(locgouvDAO.findByPostaCode(dcNode.getNode("codepostal").toString(),dcNode.getNode("ville").toString()));
	      	 clientBean.getClient().setLocalite(dcNode.getNode("ville").toString());
	      	 clientBean.getClient().setCodeP(dcNode.getNode("codepostal").toString());*/
	      	 
	      	 
	      	 
	      	 if(!jsonOutput.getString("City").equalsIgnoreCase("null")){
	      		 
	      		 System.out.println("condition_if");
	      		 
	      		 Locgouv localite = locgouvDAO.findByPostaCode(jsonOutput.getString("ZipCode"), jsonOutput.getString("City"));
	      		 
	      		 if(localite !=null){
	      			 
	      			 System.out.println("condition_if2");
	      			 
	      			 clientBean.setSelectedLocgouv(localite);
	              	 clientBean.getClient().setLocalite(jsonOutput.getString("City"));
	              	 clientBean.getClient().setCodeP(jsonOutput.getString("ZipCode"));
	      		 }else{
	      			 
	      			 localite = new Locgouv();
	      			 clientBean.setSelectedLocgouv(localite);
	      			 clientBean.getClient().setLocalite("");
	      			 clientBean.getClient().setCodeP("");
	      			 
	      		 }
	      		 
	      		 
	      	 }
	      	 
	      	 
	  			clientBean.getAllCodePostalByGouvernera().clear();
	  			clientBean.setAllCodePostalByGouvernera(locgouvDAO.findByGouv(clientBean.getClient().getGouver()));
	  			System.out.println("getGouver "+clientBean.getClient().getGouver());
	  			System.out.println("allCodePostalByGouvernera "+clientBean.getAllCodePostalByGouvernera().size());
	      	 
//	      	 selectedLocgouv = locgouvDAO.findByPostaCode(client.getCodeP(),	client.getLocalite());
//	  			System.out.println("else "+selectedLocgouv);
//	  			if(selectedLocgouv!=null){
//	  			allCodePostalByGouvernera.clear();
//	  			allCodePostalByGouvernera = locgouvDAO.findByGouv(selectedLocgouv.getGouvernora());
//	  			}

	         }
	         
	         clientBean.getClient().setTel(jsonOutput.getString("PhoneNumber"));
	         clientBean.getClient().setMail(jsonOutput.getString("Email"));
	         
	         
//	         clientBean.getClient().setWs("O");
	         
			}
			
			else  {
				
				if(!jsonOutput.getString("RejectMessage").equalsIgnoreCase("null")) {
					
					clientBean.setClient(new Client());
					
					FacesMessage msg = new FacesMessage(jsonOutput.getString("RejectMessage"), jsonOutput.getString("RejectMessage"));
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					return;
					
				}else {
					
					clientBean.setClient(new Client());
					
					FacesMessage msg = new FacesMessage("Client inexistant!", "Client inexistant");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					return;
					
				}
				
			}
			
		}
		
		
		
		
		} 
		
		catch (ClientHandlerException e) {
			
			clientBean.setClient(new Client());
			
				System.out.println("probleme de connexion vide");
				FacesMessage msg = new FacesMessage("Erreur: problème d'appel au WS Client!", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);

				System.out.println("probleme de ClientHandlerException  ");
				
				e.printStackTrace();
				
			}
		
		catch (Exception e) {

			 clientBean.setClient(new Client());
     		
     		FacesContext.getCurrentInstance().addMessage(null, 
     				new FacesMessage(
     						FacesMessage.SEVERITY_FATAL, "Erreur interne liée au niveau du WS", ""));
     		
     		System.out.println("probleme de Exception  ");
     		
			e.printStackTrace();

		  }
	}
	
	public void rechercherDepuisWS_ancienne() throws NoSuchAlgorithmException, KeyManagementException{
		

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
		 
		 
		 
		 String numCompte = clientBean.getClient().getNcompte();
		 
		
		 //final String BASE_URI = Proprietes.URL_CLIENT_STB;
		 final String BASE_URI = Constante.URL_CLIENT_STB;
		
       ExampleResourceClient erc = new ExampleResourceClient(numCompte,BASE_URI);
       //System.out.println(erc.HttpReponse());
       
       
       if (erc.HttpReponse() != 200) {
      	 System.out.println("probleme de donnees reponse = "+erc.HttpReponse());
      	 
      	FacesMessage msg = new FacesMessage("Erreur serveur, veuiller contacter l'administrateur.", "ERREUR");
		msg.setSeverity(FacesMessage.SEVERITY_FATAL);
		FacesContext.getCurrentInstance().addMessage("Erreur", msg);
      }
      
     else{
    	 
    	 
    	 try{
       
       
       Node dcNode = with(erc.getXml()).get("ArrayOfDNC.DNC[0]");
       
       if(!dcNode.getNode("codetypedocument").toString().equalsIgnoreCase("null")){
    	   
    	   switch (dcNode.getNode("codetypedocument").toString() ){
    	   	
    	   case "01" : clientBean.getClient().setTypeDoc("CIN");
    		   break;
    		   
    	   case "03": clientBean.getClient().setTypeDoc("Passport");
    		   break;
    		   
    	   case "08": clientBean.getClient().setTypeDoc("Passport");
		   break;
    		   
    	   case "04":clientBean.getClient().setTypeDoc("CarteSejour");
    		   break;
    		   
    	   default : clientBean.getClient().setTypeDoc("CIN");
    		   break;
    	   
    	   }
    		   
    		   
    	   
       }
       
            
       //clientBean.getClient().setTypeDoc( !dcNode.getNode("cin").toString().equalsIgnoreCase("null") ? dcNode.getNode("cin").toString() : "" );
       clientBean.getClient().setIdentifiant( !dcNode.getNode("cin").toString().equalsIgnoreCase("null") ? dcNode.getNode("cin").toString() : "" );
       clientBean.getClient().setNomClient(!dcNode.getNode("nom").toString().equalsIgnoreCase("null") ? dcNode.getNode("nom").toString() : "");
       clientBean.getClient().setPrenomClient(!dcNode.getNode("prenom").toString().equalsIgnoreCase("null") ? dcNode.getNode("prenom").toString() : "");
       
       if(!dcNode.getNode("datenaissance").toString().equalsIgnoreCase("null")){
    	   
    	   DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    	   DateTime dt = formatter.parseDateTime(dcNode.getNode("datenaissance").toString());
    	   
    	   clientBean.getClient().setDatNaiss(dt.toDate());
    	   
       }
       
       //clientBean.getClient().setProfession( (dcNode.getNode("Profession").toString()!=null) ? dcNode.getNode("Profession") : "");
       clientBean.getClient().setSexe(!dcNode.getNode("sexe").toString().equalsIgnoreCase("null") ? dcNode.getNode("sexe").toString().substring(0,1).toUpperCase() : "");
       //clientBean.getClient().setNumAdr(!dcNode.getNode("adresse").toString().equalsIgnoreCase("null") ? dcNode.getNode("adresse").toString() : ""); 
       //clientBean.getClient().setTypeVoie(!dcNode.getNode("adresse").toString().equalsIgnoreCase("null") ? dcNode.getNode("adresse").toString() : ""); 
       //clientBean.getClient().setTypeVoie(typeVoieDAO.find(10));
       clientBean.getClient().setTypeVoie(clientBean.getTypeVoies().get(5));
       System.out.println("adresse="+dcNode.getNode("adresse").toString());
       clientBean.getClient().setAdrClient(!dcNode.getNode("adresse").toString().equalsIgnoreCase("null") ? dcNode.getNode("adresse").toString() : ""); 
       
      if(!dcNode.getNode("codepostal").toString().equalsIgnoreCase("null")){
    	   
    	 //System.out.println("gouver="+locgouvDAO.findByPostaCode(dcNode.getNode("codepostal").toString()).getGouvernora());  
    	  Locgouv gouver =locgouvDAO.findByPostaCode(dcNode.getNode("codepostal").toString());
    	 if(gouver!= null){
    	 clientBean.getClient().setGouver( gouver.getGouvernora() ); 
    	 }
    	 else{
			 clientBean.getClient().setGouver("");
    	 	}
    	 
    	 //clientBean.setSelectedLocgouv(new Locgouv() );
    	 
    	 
    	 
    	 /*clientBean.setSelectedLocgouv(locgouvDAO.findByPostaCode(dcNode.getNode("codepostal").toString(),dcNode.getNode("ville").toString()));
    	 clientBean.getClient().setLocalite(dcNode.getNode("ville").toString());
    	 clientBean.getClient().setCodeP(dcNode.getNode("codepostal").toString());*/
    	 
    	 
    	 
    	 if(!dcNode.getNode("ville").toString().equalsIgnoreCase("null")){
    		 
    		 System.out.println("condition_if");
    		 
    		 Locgouv localite = locgouvDAO.findByPostaCode(dcNode.getNode("codepostal").toString(),dcNode.getNode("ville").toString());
    		 
    		 if(localite !=null){
    			 
    			 System.out.println("condition_if2");
    			 
    			 clientBean.setSelectedLocgouv(localite);
            	 clientBean.getClient().setLocalite(dcNode.getNode("ville").toString());
            	 clientBean.getClient().setCodeP(dcNode.getNode("codepostal").toString());
    		 }else{
    			 
    			 localite = new Locgouv();
    			 clientBean.setSelectedLocgouv(localite);
    			 clientBean.getClient().setLocalite("");
    			 clientBean.getClient().setCodeP("");
    			 
    		 }
    		 
    		 
    	 }
    	 
    	 
			clientBean.getAllCodePostalByGouvernera().clear();
			clientBean.setAllCodePostalByGouvernera(locgouvDAO.findByGouv(clientBean.getClient().getGouver()));
			System.out.println("getGouver "+clientBean.getClient().getGouver());
			System.out.println("allCodePostalByGouvernera "+clientBean.getAllCodePostalByGouvernera().size());
    	 
//    	 selectedLocgouv = locgouvDAO.findByPostaCode(client.getCodeP(),	client.getLocalite());
//			System.out.println("else "+selectedLocgouv);
//			if(selectedLocgouv!=null){
//			allCodePostalByGouvernera.clear();
//			allCodePostalByGouvernera = locgouvDAO.findByGouv(selectedLocgouv.getGouvernora());
//			}

       }
       
       clientBean.getClient().setTel(!dcNode.getNode("tel").toString().equalsIgnoreCase("null") ? dcNode.getNode("tel").toString() : "");
       clientBean.getClient().setMail(!dcNode.getNode("email").toString().equalsIgnoreCase("null") ? dcNode.getNode("email").toString() : "");
       
//       System.out.println("adresse = "+dcNode.getNode("adresse"));
//       System.out.println("cin = "+dcNode.getNode("cin"));
       
       
    	 }catch (ClassCastException e){
      		
    		   clientBean.setClient(new Client());
    		   
    		   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Client introuvable!", ""));
    		      
        		System.out.println("probleme de cast  ");
        		e.printStackTrace();
        		
        	}catch (Exception e) {
        		
        		 clientBean.setClient(new Client());
        		
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Client introuvable!", ""));
        		
        		System.out.println("probleme de Exception  ");
        		e.printStackTrace();
  			}
    	 
    	 
       
     }
 
       erc.close();
	}
	
	
	 
	public void rechercherDepuisWS_() {
		String numCompte = clientBean.getClient().getNcompte();

		String ctrl = null;
		// client = null;
		System.out.println("identifiant =" + numCompte);

		// if (client != null && client.getIdentifiant() != null) {
		// client = clientDAO.findByIdentifiant(client.getIdentifiant());
		System.out.println("***********************");
		System.out.println("Create Web Service Client...");
		WsRecupClientService service1 = new WsRecupClientService();
		System.out.println("Create Web Service...");
		WsRecupererClient port1 = service1.getWsRecupererClientPort();
		System.out.println("Call Web Service Operation...");

		// identifiant = client.getIdentifiant();
		System.out.println("identifiant " + numCompte);
		// client = null;

		try {
			ctrl = port1.recupererClient(numCompte).getNOMCLIENT();
			System.out.println("ctrl: " + ctrl);

			clientBean.getClient().setNomClient(
					port1.recupererClient(numCompte).getNOMCLIENT());
			clientBean.getClient().setPrenomClient(
					port1.recupererClient(numCompte).getPRENOMCLIENT());

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.S");
			String dateInString = port1.recupererClient(numCompte)
					.getDATNAISS();
			System.out.println("dateInString " + dateInString);



			clientBean.getClient().setIdentifiant(
					port1.recupererClient(numCompte).getIDENTIFIANT());
			clientBean.getClient().setTypeDoc(
					port1.recupererClient(numCompte).getTYPEDOC());
			clientBean.getClient().setTel(
					port1.recupererClient(numCompte).getTEL());
		} catch (ParseException_Exception e) {

			System.out.println("ParseException_Exception ");
		} catch (Exception e) {
			e.printStackTrace();


		}


	}

	private Date toDays = new Date();
	private Date minage;
	

	public Date getMinage() {
		minage = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(minage);
		cal.add(Calendar.MONTH, -1);
		minage = cal.getTime();
		return minage;
	}

	public void setMinage(Date minage) {
		this.minage = minage;
	}

	public Date getToDays() {
		return toDays;
	}

	public void setToDays(Date toDays) {
		this.toDays = toDays;
	}

	private int nombreAssureAajouter=0;

	public int getNombreAssureAajouter() {
		nombreAssureAajouter=newAdhesion.getNombrePersonne().intValue()-newAdhesion.getAdherents().size();
		return nombreAssureAajouter;
	}

	public void setNombreAssureAajouter(int nombreAssureAajouter) {
		this.nombreAssureAajouter = nombreAssureAajouter;
	}

	public void loadProposition() {
		System.out.println("Start load  ");
		//	
		//		String newDevis = FacesContext.getCurrentInstance()
		//				.getExternalContext().getRequestParameterMap().get("newDevis");
		//		
		//		//getAllTypeAbonnement().set(0, selectedProposition.getTypeAbonnement());
		//		if (getSelectedProposition()!=null) {
		//			
		//			try {
		//				
		//				newAdhesion.setPolice(selectedProposition.getPolice());
		//				newAdhesion.setTypeAbonnement(selectedProposition.getTypeAbonnement());
		//				newAdhesion.setNombrePersonne(selectedProposition.getNombrePersonne());
		//				newAdhesion.setDestination(selectedProposition.getDestination());
		//				newAdhesion.setPersonnePrv(selectedProposition.getPersonnePrv());
		//				newAdhesion.setTelphone(selectedProposition.getTelphone());
		//				newAdhesion.setDuree(selectedProposition.getDuree());
		//				newAdhesion.setDatDebut(selectedProposition.getDatDebut());
		//				newAdhesion.setDatFin(selectedProposition.getDatFin());
		//				newAdhesion.setClient(selectedProposition.getClient());
		//				clientBean.setClient(selectedProposition.getClient());
		//				clientBean.updateListCodePostalActionListener();
		//				if(newAdhesion.getNombrePersonne().intValue()>1) {
		//					maxnomberPersonneOne=false;
		//				} else{
		//					maxnomberPersonneOne=false;
		//				}
		//			//	System.out.println("type abonement 2="+newAdhesion.getTypeAbonnement().getValTypeAbonnement());
		//
		//				} catch (Exception e) {
		//				e.printStackTrace();
		//			}
		//		} else if (newDevis != null && !newDevis.isEmpty()
		//				&& Boolean.parseBoolean(newDevis)) {
		//			
		//			
		//			navigateToDevisAssistanceVoiyage();
		//			clientBean.cancel();
		//		}
	}
	private boolean desabledEncaiss= true;



	public boolean isDesabledEncaiss() {
		return desabledEncaiss;
	}

	public void setDesabledEncaiss(boolean desabledEncaiss) {
		this.desabledEncaiss = desabledEncaiss;
	}
	
	
	public boolean isDisabledall() {
		return disabledall;
	}


	public void setDisabledall(boolean disabledall) {
		this.disabledall = disabledall;
	}
	
	
	public boolean isDesabledGenerateNotice() {
		return desabledGenerateNotice;
	}



	public void setDesabledGenerateNotice(boolean desabledGenerateNotice) {
		this.desabledGenerateNotice = desabledGenerateNotice;
	}
	private boolean desabledGenerateNotice=true;
	private Proposition selectedProposition = null;


	

	public Proposition getSelectedProposition() {
		System.out.println("proposition selected dans get ===="+ selectedProposition.getIdProposition());	
		return selectedProposition;
		
		
	}
	

	public void setSelectedProposition(Proposition selectedProposition) {
		
		if((selectedProposition!=null)) {
		System.out.println("selectedProposition :"+selectedProposition.getIdProposition());
		}
		
		if (adhesionDAO.propexiste_7ayya(selectedProposition).isEmpty()) {
			affiche = true;
			System.out.println("affiche 1 ===================="+ affiche);
		}
		else {
			
			
			affiche = false;
			System.out.println("affiche 2 ===================="+ affiche);
		}
		
		if ((selectedProposition!=null) && (affiche == true)){
			
			
			System.out.println("selectedProposition :"+selectedProposition.getIdProposition());
			List<Adherent> list=adherentDAO.findAdhByIdProposition(selectedProposition.getIdProposition()) ;
			if(list!=null && ! list.isEmpty()){
				System.out.println("list  :"+list.size());
			}
			newAdhesion.setAdherents(list);
			
			//initialisationClientAndAdhesion();
			newProposition = null;
			if(selectedProposition.getNombrePersonne()!=null){
				nombrePersonne=selectedProposition.getNombrePersonne().intValue();
			}
				
			
			//onNatureAbonnementChangeUpdateNomberPeronne();
			
			newAdhesion.setPolice(selectedProposition.getPolice());
			newAdhesion.setCouverture(selectedProposition.getCouverture());
			//newAdhesion.setTypeAbonnement(selectedProposition.getTypeAbonnement());
			newAdhesion.setNombrePersonne(selectedProposition.getNombrePersonne());
			newAdhesion.setDestination(selectedProposition.getDestination());
			newAdhesion.setPersonnePrv(selectedProposition.getPersonnePrv());
			newAdhesion.setTelphone(selectedProposition.getTelphone());
            //selectedDuree.setValDuree(String.valueOf(selectedProposition.getDuree()));
					
			
			  if (selectedProposition.getTypeAbonnement().getIdTypeAbonnement() == 1){
				  
				  newAdhesion.setTypeAbonnement(typeAbonnementDAO.find((long)1));
				  
	            }
	            else if (selectedProposition.getTypeAbonnement().getIdTypeAbonnement() == 2){
	            	newAdhesion.setTypeAbonnement(typeAbonnementDAO.find((long)2));
	            }
			
			
            if (selectedProposition.getDuree() == 8){
            	selectedDuree = dureeDAO.find((long)1);
            }
            else if (selectedProposition.getDuree() == 16)
            {
            	selectedDuree = dureeDAO.find((long)2);
            }
            else if (selectedProposition.getDuree() == 32)
            {
            	selectedDuree = dureeDAO.find((long)3);
            }
            else if (selectedProposition.getDuree() == 60)
            {
            	selectedDuree = dureeDAO.find((long)4);
            }
            else if (selectedProposition.getDuree() == 90)
            {
            	selectedDuree = dureeDAO.find((long)5);
            }
            else if (selectedProposition.getDuree() == 365)
            {
            	selectedDuree = dureeDAO.find((long)6);
            }
            
            
			newAdhesion.setDuree(selectedProposition.getDuree());
			newAdhesion.setDatDebut(selectedProposition.getDatDebut());
			newAdhesion.setDatFin(selectedProposition.getDatFin());
			newAdhesion.setClient(selectedProposition.getClient());
			clientBean.setClient(selectedProposition.getClient());
			
			
			//clientBean.updateListCodePostalActionListener();
			clientBean.rechercher();
			selectedProposition=null;
			/*if(newAdhesion.getNombrePersonne().intValue()>1) {
				maxnomberPersonneOne=false;
			} else{
				maxnomberPersonneOne=true;
			}*/
		}
		
		else if (affiche == false) {
			
			System.out.println("je suis dans affiche false ============================");
			 newAdhesion=new Adhesion();
			 clientBean.getClient().setIdentifiant(null);	 
			 selectedProposition = null;
			 
//			 FacesMessage msg = new FacesMessage("ATTENTION", "contrat déjà effectué!");
//			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//			  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
//			  
			  FacesContext facesContext = FacesContext.getCurrentInstance();
				Flash flash = facesContext.getExternalContext().getFlash();
				flash.setKeepMessages(true);
				
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Contrat déjà effectué!",""));
			
			 
			  
		}
		
	
		//	System.out.println("(newAdhesion.getNombrePersonne().intValue() ="+newAdhesion.getNombrePersonne().intValue());
		//	System.out.println("maxnomberPersonneOne desabled ="+maxnomberPersonneOne);

		this.selectedProposition = selectedProposition;
	}


	public void calculAge(Date datnais) {

		ageCalc = AgeCalculator.calculateAge(datnais);
		System.out.println("agecalc="+ageCalc);
		System.out.println("year="+ageCalc.getYears());
		System.out.println("month="+ageCalc.getMonths());
		age = ageCalc.getYears();
//		if (ageCalc.getMonths() >= 6) {
//			age = ageCalc.getYears() + 1;
//		}
		if (ageCalc.getMonths() >= 1) {
			age = (double)ageCalc.getYears() + ((double)ageCalc.getMonths()/100);
			System.out.println("age1="+age);
		}
		
	} 


	@EJB
	private ILocgouvDAO locgouvDAO;

	public String remplirAssureTLS() {


		if (clientBean.getClient().getIdentifiant() != null ) {
			System.out.println("enter remplirAssure");
			newAdherent=new Adherent();
			
			newAdherent.setIdentifiant(clientBean.getClient().getIdentifiant());
			newAdherent.setNomAdherent(clientBean.getClient().getNomClient());
			if(clientBean.getClient().getTypePers().equals("PA"))
			{
			newAdherent.setPrenomAdherent(clientBean.getClient().getPrenomClient());
			newAdherent.setDatNaiss(clientBean.getClient().getDatNaiss());
			newAdherent.setTypeDoc(clientBean.getClient().getTypeDoc());
			newAdherent.setProfession(clientBean.getClient().getProfession());
			newAdherent.setSexe(clientBean.getClient().getSexe().equals("F")?"Feminin":"Masculin");
			}
			newAdherent.setNumAdr(clientBean.getClient().getNumAdr());
			newAdherent.setTypeVoie(clientBean.getClient().getTypeVoie());
			newAdherent.setAdresse(clientBean.getClient().getAdrClient());
			
							if(clientBean.getClient().getLocalite()==null){
			
							}
							else{
							newAdherent.setGouver(clientBean.getClient().getGouver());
							selectedLocgouv = locgouvDAO.findByPostaCode(clientBean.getClient().getCodeP(),	clientBean.getClient().getLocalite());
							if(selectedLocgouv!=null){
							allCodePostalByGouvernera.clear();
							allCodePostalByGouvernera=locgouvDAO.findByGouv(selectedLocgouv.getGouvernora());//.addAll(mapAllCodePostalByGouvernerat.get(selectedLocgouv.getGouvernora()));
							}
							newAdherent.setCodePostal(clientBean.getClient().getCodeP());
							newAdherent.setLocalite(clientBean.getClient().getLocalite());
							}
			newAdherent.setTel(clientBean.getClient().getTel());
			
			newAdherent.setClient(clientBean.getClient());


			newAdhesion.getAdherents().add(newAdherent);

			//} 
		}else {
			System.out.println("enter ViderAssure");
			newAdherent=new Adherent(); 
		}

		return "devis_AssistvoyageTLS?faces-redirect=true";
	}
	
	public void updateListCodePostalActionListener() {
//		allCodePostalByGouvernera.clear();
//		allCodePostalByGouvernera.addAll(mapAllCodePostalByGouvernerat	.get(newAdherent.getGouver()));
		System.out.println("gov bis");

		if(newAdherent.getGouver() !=null && (!newAdherent.getGouver().equals("")))
		{
			//allCodePostalByGouvernera.clear();
			allCodePostalByGouvernera=new  ArrayList<Locgouv>();
			newAdherent.setCodePostal(null);
			newAdherent.setLocalite(null);
			selectedLocgouv = new Locgouv();
			allCodePostalByGouvernera = locgouvDAO.findByGouv(newAdherent.getGouver());// (mapAllCodePostalByGouvernerat.get(newAdhesion.getGouver()));
			System.out.println("gov1");

		}
		else{
			//allCodePostalByGouvernera.clear();
			allCodePostalByGouvernera=new  ArrayList<Locgouv>();
			newAdherent.setCodePostal(null);
			newAdherent.setLocalite(null);
			newAdherent.setGouver(null);
			//selectedLocgouv = new Locgouv();
			System.out.println("gov2");

		}

	}
	List<String> allGouverneras = new ArrayList<String>();
	public List<String> getAllGouverneras() {
		return allGouverneras;
	}

	public void setAllGouverneras(List<String> allGouverneras) {
		this.allGouverneras = allGouverneras;
	}
	private Locgouv selectedLocgouv;
	public Locgouv getSelectedLocgouv() {
		return selectedLocgouv;
	}

	public void setSelectedLocgouv(Locgouv selectedLocgouv) {
		this.selectedLocgouv = selectedLocgouv;
	}
	
	
	
	public void locgouvSelectListened( ) {

		System.out.println("sys1 bis");

		 
		selectedLocgouv = locgouvDAO.findByCode((getSelectedLocgouv().getCode()));
		Locgouv locgouv = selectedLocgouv;
		if (locgouv != null) {
			newAdherent.setCodePostal(locgouv.getCodePosta());
			newAdherent.setLocalite(locgouv.getLocalite());
			//newAdhesion.setGouver(locgouv.getGouvernora());
			System.out.println("sys2 bis "+locgouv);
		} else {
			newAdherent.setCodePostal(null);
			newAdherent.setLocalite(null);
			//newAdhesion.setGouver("");
				System.out.println("sys3 bis "+locgouv);
		}
		
		 
	}
	
	
	
//	public void locgouvSelectListened(SelectEvent event) {
//
//		System.out.println("sys1 bis");
//
//		Locgouv locgouv = (Locgouv) event.getObject();
//		if (locgouv != null) {
//			newAdherent.setCodePostal(locgouv.getCodePosta());
//			newAdherent.setLocalite(locgouv.getLocalite());
//			newAdherent.setGouver(locgouv.getGouvernora());
//			System.out.println("code postal"+newAdherent.getCodePostal());
//			System.out.println("sys2 bis "+locgouv);
//		} else {
//			newAdherent.setCodePostal("");
//			newAdherent.setLocalite("");
//			newAdherent.setGouver("");
//		}
//	}
	
	List<Locgouv> allCodePostalByGouvernera = new ArrayList<Locgouv>();
	public List<Locgouv> getAllCodePostalByGouvernera() {
		return allCodePostalByGouvernera;
	}

	public void setAllCodePostalByGouvernera(
			List<Locgouv> allCodePostalByGouvernera) {
		this.allCodePostalByGouvernera = allCodePostalByGouvernera;
	}
	Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat = new HashMap<String, List<Locgouv>>();
	public Map<String, List<Locgouv>> getMapAllCodePostalByGouvernerat() {
		return mapAllCodePostalByGouvernerat;
	}

	public void setMapAllCodePostalByGouvernerat(Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat) {
		this.mapAllCodePostalByGouvernerat = mapAllCodePostalByGouvernerat;
	}
	public Converter getProfessionConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (ProfessionClient professionClient : getAllProfessions()) {

					if (professionClient.getIdProfession().toString()
							.equals(value)) {
						return professionClient;
					}
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				if (value != null)
					return value.toString();
				else
					return null;

			}
		};
	}
	private List<ProfessionClient> allProfessions;
	public List<ProfessionClient> getAllProfessions() {
		return allProfessions;
	}

	public void setAllProfessions(List<ProfessionClient> allProfessions) {
		this.allProfessions = allProfessions;
	}
	private List<TypeVoie> typeVoies;
	public List<TypeVoie> getTypeVoies() {
		return typeVoies;
	}

	public void setTypeVoies(List<TypeVoie> typeVoies) {
		this.typeVoies = typeVoies;
	}
	public void reinitialiserAdherent()
	{
		newAdherent=new Adherent();
		allCodePostalByGouvernera.clear();
	}

	public boolean isAffiche() {
		return affiche;
	}

	public void setAffiche(boolean affiche) {
		this.affiche = affiche;
	}
	
	
	
}
