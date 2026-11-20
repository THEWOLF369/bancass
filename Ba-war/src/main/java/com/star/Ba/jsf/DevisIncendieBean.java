package com.star.Ba.jsf;

import static io.restassured.path.xml.XmlPath.with;
import io.restassured.path.xml.element.Node;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.annotation.PostConstruct;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.primefaces.event.SelectEvent;

import com.star.Ba.commun.Age;
import com.star.Ba.commun.AgeCalculator;
import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IDetailActDAO;
import com.star.Ba.dao.ILocgouvDAO;
import com.star.Ba.dao.IParamActDAO;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IPropositionDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.ITarifIncendieDAO;
import com.star.Ba.dao.ITariftdDAO;
import com.star.Ba.dao.ITaxeDAO;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.jsf.util.Proprietes;
import com.star.Ba.jsf.util.TokenSTB;
import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.DetailAct;
import com.star.Ba.model.Locgouv;
import com.star.Ba.model.Police;
import com.star.Ba.model.ProfessionClient;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.TarifIncendie;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.parametres.ExampleResourceClient;
import com.star.Ba.parametres.TrustAllX509TrustManager;
import com.star.Ba.service.IParametresIncendieService;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author
 * 
 */
@ManagedBean(name = "devisIncendieBean")
@SessionScoped
public class DevisIncendieBean implements Serializable {
	// devis information

	private Map<String, Object> listnumplc = null;
	private String numplc;
	private String cin;
	private Date mindate;
	private Date effDate;
	private Date datcreation;
	private double duree;
	private int dureeA;
	private int dureeM;
	private Date datefin;
	private double capital;
	private double periode;
	private double primeglobal;
	private String nadhesion;
	private Date aujourdhui;
	private Age ageCalc;
	private int age;
	private String propositionId;
	private Locgouv selectedLocgouv;
	private List<Locgouv> locgouvs;
	private boolean showInc = false;
	private double taxe;
	private boolean affiche = false;
	private double primetot;
	private Police libnumplc;
	private String RefCredit;
	/** hoss ***/
	private int nbmois;
	private int nbannee;

	public int getNbmois() {
		return nbmois;
	}

	public void setNbmois(int nbmois) {
		this.nbmois = nbmois;
	}

	public int getNbannee() {
		return nbannee;
	}

	public void setNbannee(int nbannee) {
		this.nbannee = nbannee;
	}

	@EJB
	private IParametresIncendieService parametresIncendieService;

	@EJB
	private IPoliceDAO iPoliceDAO;

	@EJB
	private IPropositionDAO propositionDAO;

	@EJB
	private IPoliceDAO policeDAO;
	@EJB
	private ITarifIncendieDAO tarifIncendieDAO;

	@EJB
	private IConventionDAO conventionDAO;

	@EJB
	private ISocieteDAO societeDAO;

	@EJB
	private ILocgouvDAO locgouvDAO;

	@EJB
	private IProduitDAO produitDAO;

	@EJB
	private ITaxeDAO taxeDAO;
	
	@EJB
	private IAdhesionDAO adhesionDAO;

	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;

	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;

	@ManagedProperty("#{AssistanceVoiyageController}")
	private AssistanceVoiyageController assistanceVoiyageController;

	public AssistanceVoiyageController getAssistanceVoiyageController() {
		return assistanceVoiyageController;
	}

	public void setAssistanceVoiyageController(
			AssistanceVoiyageController assistanceVoiyageController) {
		this.assistanceVoiyageController = assistanceVoiyageController;
	}

	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	private String cod_societe;

	private Adhesion adhesion = new Adhesion();
	private Proposition proposition;
	private String propid;

	public Date getDatcreation() {
		return datcreation;
	}

	public void setDatcreation(Date datcreation) {
		this.datcreation = datcreation;
	}

	public Police getLibnumplc() {
		return libnumplc;
	}

	public void setLibnumplc(Police libnumplc) {
		this.libnumplc = libnumplc;
	}

	public Locgouv getLocgouv(long code) {
		return locgouvDAO.find(code);
	}

	public String getNadhesion() {
		return nadhesion;
	}

	public void setNadhesion(String nadhesion) {
		this.nadhesion = nadhesion;
	}

	public double getPeriode() {
		return periode;
	}

	public void setPeriode(double periode) {
		this.periode = periode;
	}

	public List<Locgouv> getLocgouvs() {
		return locgouvs;
	}

	public void setLocgouvs(List<Locgouv> locgouvs) {
		this.locgouvs = locgouvs;
		/** hoss **/
		for (Locgouv locgouv : this.locgouvs) {

			listCPT.put(locgouv.getCodePosta() + " " + locgouv.getLocalite(),
					locgouv);
		}
	}

	public Locgouv getSelectedLocgouv() {
		return selectedLocgouv;
	}

	public void setSelectedLocgouv(Locgouv selectedLocgouv) {
		this.selectedLocgouv = selectedLocgouv;
	}

	public Proposition getProposition() {
		return proposition;
	}

	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}

	public String getCod_societe() {
		return cod_societe;
	}

	public void setCod_societe(String cod_societe) {
		this.cod_societe = cod_societe;
	}

	public String getNumplc() {
		return numplc;
	}

	public void setNumplc(String numplc) {
		this.numplc = numplc;
	}

	public Map<String, Object> getListnumplc() {

		return listnumplc;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
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

	public double getDuree() {
		return duree;
	}

	public void setDuree(double duree) {
		this.duree = duree;
	}

	public Date getDatefin() {
		return datefin;
	}

	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public double getPrimeglobal() {
		return primeglobal;
	}

	public void setPrimeglobal(double primeglobal) {
		this.primeglobal = primeglobal;
	}

	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Date getAujourdhui() {
		return aujourdhui;
	}

	public Age getAgeCalc() {
		return ageCalc;
	}

	public void setAgeCalc(Age ageCalc) {
		this.ageCalc = ageCalc;
	}

	public Adhesion getAdhesion() {
		return adhesion;
	}

	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}

	public void setAujourdhui(Date aujourdhui) {
		this.aujourdhui = aujourdhui;
	}

	public Date getMindate() {
		return mindate;
	}

	public void setMindate(Date mindate) {
		this.mindate = mindate;
	}

	public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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

	public boolean isShowInc() {
		return showInc;
	}

	public void setShowInc(boolean showInc) {
		this.showInc = showInc;
	}

	public double getTaxe() {
		return taxe;
	}

	public void setTaxe(double taxe) {
		this.taxe = taxe;
	}

	public double getPrimetot() {
		return primetot;
	}

	public void setPrimetot(double primetot) {
		this.primetot = primetot;
	}

	public String getRefCredit() {
		return RefCredit;
	}

	public void setRefCredit(String refCredit) {
		RefCredit = refCredit;
	}

	/*** hoss ***/
	Map<String, Object> listCPT = new LinkedHashMap<String, Object>();

	public Map<String, Object> getListCPT() {
		return listCPT;
	}

	public void updateListCodePostalActionListener() {

		// allCodePostalByGouvernera.clear();
		// allCodePostalByGouvernera=
		// locgouvDAO.findByGouv(adhesion.getGouver());
		// System.out.println("Size="+allCodePostalByGouvernera.size());

		if (adhesion.getGouver() != null && (!adhesion.getGouver().equals(""))) {
			// allCodePostalByGouvernera.clear();
			allCodePostalByGouvernera = new ArrayList<Locgouv>();
			adhesion.setCodeP(null);
			adhesion.setLocalite(null);
			selectedLocgouv = new Locgouv();
			allCodePostalByGouvernera = locgouvDAO.findByGouv(adhesion
					.getGouver());// (mapAllCodePostalByGouvernerat.get(newAdhesion.getGouver()));
			System.out.println("gov1");

		} else {
			// allCodePostalByGouvernera.clear();
			allCodePostalByGouvernera = new ArrayList<Locgouv>();
			adhesion.setCodeP(null);
			adhesion.setLocalite(null);
			adhesion.setGouver(null);
			// selectedLocgouv = new Locgouv();
			System.out.println("gov2");

		}

	}

	public void setListCPT(Map<String, Object> listCPT) {
		this.listCPT = listCPT;
	}

	@PostConstruct
	public void init() {
		allCodePostalByGouvernera.clear();
		aujourdhui = new Date();
		effDate = aujourdhui;
		datcreation = effDate;
		mindate = new Date();
		updatepolice();
		// if(locgouvs==null){
		// setLocgouvs(locgouvDAO.findAll());
		// }
		// for (Locgouv locgouv : locgouvs) {
		// if(mapAllCodePostalByGouvernerat.containsKey(locgouv.getGouvernora())){
		// mapAllCodePostalByGouvernerat.get(locgouv.getGouvernora()).add(locgouv);
		// }else {
		// allGouverneras.add(locgouv.getGouvernora());
		// mapAllCodePostalByGouvernerat.put(locgouv.getGouvernora(), new
		// ArrayList<Locgouv>());
		// }
		//
		//
		// }

		allGouverneras = locgouvDAO.findGouv();

		desabledGenerateNoticeIncendie = true;
		desabledEncaiss = true;
		setShowInc(false);
		duree = 00.00;

		// Status = 0;
		// validate = 0;

	}

	List<String> allGouverneras = new ArrayList<String>();
	Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat = new HashMap<String, List<Locgouv>>();

	public List<String> getAllGouverneras() {
		return allGouverneras;
	}

	public void setAllGouverneras(List<String> allGouverneras) {
		this.allGouverneras = allGouverneras;
	}

	public Map<String, List<Locgouv>> getMapAllCodePostalByGouvernerat() {
		return mapAllCodePostalByGouvernerat;
	}

	public void setMapAllCodePostalByGouvernerat(
			Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat) {
		this.mapAllCodePostalByGouvernerat = mapAllCodePostalByGouvernerat;
	}

	List<Locgouv> allCodePostalByGouvernera = new ArrayList<Locgouv>();

	public List<Locgouv> getAllCodePostalByGouvernera() {
		return allCodePostalByGouvernera;
	}

	public void setAllCodePostalByGouvernera(
			List<Locgouv> allCodePostalByGouvernera) {
		this.allCodePostalByGouvernera = allCodePostalByGouvernera;
	}

	public void newDevis() {
		init();

		adhesion = new Adhesion();
		duree = 0;
		nbannee = 0;
		nbmois = 0;
		cin = "";
		capital = 0.0;
		primeglobal = 0;
		datefin = null;
		selectedLocgouv = new Locgouv();

	}

	public void updateDate() {
		dureeA = nbannee;
		dureeM = nbmois;
		Calendar c = Calendar.getInstance();
		c.setTime(effDate);
		c.add(Calendar.YEAR, dureeA);
		c.add(Calendar.MONTH, dureeM);
		c.add(Calendar.DAY_OF_MONTH, -1);
		datefin = c.getTime();

	}

	/** hoss ***/
	public void updateDuree() {
		duree = nbannee + (((double) nbmois) / 100);
		updateDate();

	}

	public String calculTarif() {

		/*** hoss ***/

		/*
		 * if (getClient().getIdentifiant() == null ||
		 * getClient().getIdentifiant().isEmpty()) {
		 * 
		 * 
		 * //noneException=false; System.out.println("Client inexistant ");
		 * FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
		 * msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		 * FacesContext.getCurrentInstance().addMessage("Erreur", msg); return
		 * null; } if(getAdhesion().getAdresse()==null ||
		 * getAdhesion().getAdresse().isEmpty() ||
		 * getAdhesion().getLocalite()==null ||
		 * getAdhesion().getLocalite().isEmpty() ||
		 * getAdhesion().getGouver()==null ||
		 * getAdhesion().getGouver().isEmpty() || getSelectedLocgouv()==null ) {
		 * 
		 * 
		 * FacesMessage msg = new FacesMessage("ERREUR",
		 * "Veuillez saisir l'adresse, gouvernorat et code postale du risque.");
		 * msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		 * FacesContext.getCurrentInstance().addMessage("Erreur", msg); return
		 * null;
		 * 
		 * }
		 */

		if (nbannee == 0 && nbmois == 0) {
			FacesMessage msg = new FacesMessage(
					"La durée ne doit pas être nulle.",
					"La durée ne doit pas être nulle.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(
					"La durée ne doit pas être nulle.", msg);
			return null;
		}
		System.out.println("effDate" + effDate);
		System.out.println("aujourdhui" + aujourdhui);
		Calendar c = Calendar.getInstance();
		c.setTime(effDate);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(aujourdhui);

		if ((c.get(Calendar.DAY_OF_YEAR) < c1.get(Calendar.DAY_OF_YEAR))
				&& (c.get(Calendar.YEAR) == c1.get(Calendar.YEAR))) {
			// if( c.compareTo(c1) <= 0 ) {
			FacesMessage msg = new FacesMessage(
					"La date d'effet doit être supérieur à la date du jour!",
					"La date d'effet doit être supérieur à la date du jour!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"La date d'effet doit être supérieur à la date du jour!",
							msg);
			return null;
		}

		if (getSelectedLocgouv() != null
				&& (getAdhesion().getLocalite() == null || getAdhesion()
						.getLocalite().isEmpty())) {

			FacesMessage msg = new FacesMessage("ERREUR",
					"Veuillez saisir la ville du risque.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		}

		libnumplc = parametresIncendieService.getnumplcbylibelle(cod_societe,
				numplc);
		// calcul du tarif
		BigDecimal mois = new BigDecimal(dureeM);
		periode = dureeA + (mois.doubleValue() * 30) / 365;
		primeglobal = parametresIncendieService.calculPrime(capital,
				libnumplc.getNPolice(), periode);
		taxe = taxeDAO.gettaxe_by_produit(produitDAO
				.getProduit_by_code(ENUMCODEPRODUIT.INCENDIE.getCode()),
				aujourdhui);
		System.out.println("*************taxe=" + taxe);
		taxe = (taxe / 100) * primeglobal;
		System.out.println("*************taxe=" + taxe);
		primetot = primeglobal + taxe;
		adhesion = new Adhesion(0, capital, effDate, datefin, duree, "V",
				utilisateur.getIdUtilisateurs(), primeglobal,
				adhesion.getLocalite(), adhesion.getGouver(),
				adhesion.getAdresse(), adhesion.getCodeP(), getClient(),
				policeDAO.find(libnumplc.getNPolice()), nadhesion, taxe,
				primetot, datcreation);
		proposition = new Proposition(getAdhesion(), "V");
		return "success";

	}

	public void updatepolice() {
		if (utilisateur.getProfil().getCode().equals(PofileEnum.IST.name())
				|| utilisateur.getProfil().getCode()
						.equals(PofileEnum.ASI.name())) {
			listnumplc = getlistnumplc(cod_societe, utilisateur.getProfil());
		} else {
			cod_societe = utilisateur.getAgence().getSociete().getCodSociet();
			listnumplc = getlistnumplc(cod_societe, utilisateur.getProfil());
		}
		numplc = (String) listnumplc.values().toArray()[0];

	}

	public void calculAge() {
		if (getClient() != null && getClient().getDatNaiss() != null) {
			ageCalc = AgeCalculator.calculateAge(getClient().getDatNaiss());
			age = ageCalc.getYears();
			if (ageCalc.getMonths() >= 6) {
				age = ageCalc.getYears() + 1;
			}
		}
	}

	public void loadProposition() {

		String newDevis = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("newDevis");
		
		if (adhesionDAO.propexiste(selectedProposition).isEmpty()) {
			affiche = true;
			System.out.println("affiche 1 ===================="+ affiche);
		}
		else {
			
			
			affiche = false;
			System.out.println("affiche 2 ===================="+ affiche);
		}

		if ((getSelectedProposition() != null) && (affiche == true)) {

			try {

				// client = proposition.getClient();
				clientBean.setClient(getSelectedProposition().getClient());

				effDate = getSelectedProposition().getDatDebut();
				datefin = getSelectedProposition().getDatFin();
				capital = getSelectedProposition().getCapital();
				adhesion.setAdresse(getSelectedProposition().getAdresse());
				adhesion.setGouver(getSelectedProposition().getGouver());
				updateListCodePostalActionListener();
				selectedLocgouv = locgouvDAO.findByPostaCode(
						getSelectedProposition().getCodeP(),
						getSelectedProposition().getLocalite());
				adhesion.setLocalite(getSelectedProposition().getLocalite());
				adhesion.setGouver(getSelectedProposition().getGouver());
				if (adhesion.getGouver() == null) {
					allCodePostalByGouvernera.clear();
				}
               
				
				nbannee = (int) getSelectedProposition().getDuree();
				double mois = (getSelectedProposition().getDuree() - nbannee);
				nbmois = (int) (100 * mois);
				selectedProposition = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (newDevis != null && !newDevis.isEmpty()
				&& Boolean.parseBoolean(newDevis)) {
			newDevis();
			clientBean.cancel();
			RefCredit = "";
			adhesion = new Adhesion();
			selectedProposition = null;
		}
	else if (affiche == false) {
			
			System.out.println("je suis dans affiche false ============================");
			 adhesion =new Adhesion();
			 clientBean.getClient().setIdentifiant(null);
			 nbannee = 0;
			 nbmois = 0;
			 capital = 0.0 ;
			 //selectedLocgouv.setCode(0);
			selectedLocgouv = new Locgouv();
			RefCredit = "";
			 datefin = null ;
			 selectedProposition = null;
			 FacesMessage msg = new FacesMessage("ATTENTION", "contrat déjà effectué!");
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			 
			  
		}
		
		
		calculAge();
	}

	public String navigateToDevisIncendie() {
		newDevis();
		clientBean.cancel();
		RefCredit = "";
		adhesion = new Adhesion();
		allCodePostalByGouvernera.clear();
		selectedProposition = null;

		// a voir lors du test
		// adhesion.setRef_credit("");

		return "devisIncendieTD";
	}

	public Client getClient() {
		return clientBean.getClient();
	}

	public void setClient(Client client) {
		this.clientBean.setClient(client);
	}

	public ClientBean getClientBean() {
		return clientBean;
	}

	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}

	public List<Locgouv> completeLocgouv(String query) {
		ArrayList<Locgouv> complete = new ArrayList<Locgouv>();
		for (Locgouv locgouv : locgouvs) {
			if (locgouv.getCodePosta().toLowerCase()
					.startsWith(query.toLowerCase())) {
				complete.add(locgouv);
			}
		}

		return complete;
	}

	/*** hoss ***/
	public void locgouvSelectListened() {
		// System.out.println("selected code  localite="+selectedLocgouv.getCodePosta()+" "+
		// selectedLocgouv.getLocalite());
		selectedLocgouv = locgouvDAO
				.findByCode((getSelectedLocgouv().getCode()));
		Locgouv locgouv = selectedLocgouv;
		if (locgouv != null) {
			adhesion.setCodeP(locgouv.getCodePosta());
			adhesion.setLocalite(locgouv.getLocalite());
			adhesion.setGouver(locgouv.getGouvernora());
		} else {
			adhesion.setCodeP("");
			adhesion.setLocalite("");
			adhesion.setGouver("");
		}
	}

	public void locgouvSelectListener(SelectEvent event) {

		Locgouv locgouv = (Locgouv) event.getObject();
		if (locgouv != null) {
			adhesion.setCodeP(locgouv.getCodePosta());
			adhesion.setLocalite(locgouv.getLocalite());
			adhesion.setGouver(locgouv.getGouvernora());
		} else {
			adhesion.setCodeP("");
			adhesion.setLocalite("");
			adhesion.setGouver("");
		}
	}

	@FacesConverter(forClass = Locgouv.class)
	public static class LocgouvConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
			if (value != null && !value.isEmpty()) {
				try {
					long code = Long.parseLong(value);
					DevisIncendieBean devisIncendieBean = (DevisIncendieBean) context
							.getApplication()
							.getELResolver()
							.getValue(context.getELContext(), null,
									"devisIncendieBean");
					return devisIncendieBean.getLocgouv(code);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			return null;
		}

		@Override
		public String getAsString(FacesContext context, UIComponent component,
				Object value) {

			try {
				Locgouv locgouv = (Locgouv) value;
				if (locgouv != null) {
					return String.valueOf(locgouv.getCode());
				}

			} catch (Exception e) {
			}

			return null;
		}

	}

	public void loadEcranInc() {
		setShowInc(true);
	}

	public void imprimerDevisInc(String numplc, String identifiant,
			Date effDate, double duree, Date datefin, double capital,
			double primeglobal, String nom, String prenom, double taxe,
			double primetot, String ville_agc, String numprop) {

		String path = reportController.getRealPath() + "devis_inc.jasper";

		try {

			Map<String, Object> parameters = new HashMap();

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			parameters.put("numplc", numplc);
			parameters.put("nomclt", nom);
			parameters.put("numprop", numprop);
			parameters.put("prenomclt", prenom);
			parameters.put("identifiant", identifiant);
			parameters.put("imagesociete", cod_societe + ".png");
			parameters.put("star", "star.png");
			parameters.put("effDate", df.format(effDate));
			parameters.put("datefin", df.format(datefin));
			parameters.put("duree", String.valueOf(duree));
			parameters.put("capital", String.valueOf(capital));
			parameters.put("primeglobal",
					(new DecimalFormat("#0.000")).format(primeglobal) + " TND");
			parameters.put("taxe", (new DecimalFormat("#0.000")).format(taxe)
					+ " TND");
			parameters.put("primtot",
					(new DecimalFormat("#0.000")).format(primetot) + " TND");
			parameters.put("villeagence", ville_agc);

			JasperPrint print = JasperFillManager.fillReport(path, parameters,
					new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition", "inline; filename=\""
					+ "devis_inc.pdf" + "\"");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void imprimerDevisInc__(String numplc, String identifiant,
			Date effDate, double duree, Date datefin, double capital,
			double primeglobal, String nom, String prenom, double taxe,
			double primetot, String ville_agc, String numprop) {

		// fonction d'impression du devis
		Map<String, Object> parameters = new HashMap();

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		parameters.put("numplc", numplc);
		parameters.put("nomclt", nom);
		parameters.put("numprop", numprop);
		parameters.put("prenomclt", prenom);
		parameters.put("identifiant", identifiant);
		parameters.put("imagesociete", cod_societe + ".png");
		parameters.put("star", "star.png");
		parameters.put("effDate", df.format(effDate));
		parameters.put("datefin", df.format(datefin));
		parameters.put("duree", String.valueOf(duree));
		parameters.put("capital", String.valueOf(capital));
		parameters.put("primeglobal",
				(new DecimalFormat("#0.000")).format(primeglobal) + " TND");
		parameters.put("taxe", (new DecimalFormat("#0.000")).format(taxe)
				+ " TND");
		parameters.put("primtot",
				(new DecimalFormat("#0.000")).format(primetot) + " TND");
		parameters.put("villeagence", ville_agc);

		try {
			String path = reportController.getRealPath() + "devis_inc.jasper";
			// this.getClass().getResource("closed.jasper").getPath()
			JasperPrint jp = JasperFillManager.fillReport(path, parameters,
					new JREmptyDataSource());

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();

			httpServletResponse.addHeader("Content-disposition",
					"inline; filename=devis_inc.pdf");
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

	public String getRealPath() {
		ServletContext cxt = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();
		return cxt.getContextPath() + "/report/";
	}

	public String navigateToClientPage() {
		// clientBean.rechercher();
		if (clientBean.getClient().getGouver() == null) {
			clientBean.getAllCodePostalByGouvernera().clear();
		}

		return "clientInc?faces-redirect=true&keep=true";
	}

	/*** hoss ***/

	public void EqualsValidator(FacesContext context, UIComponent component,
			Object input) throws ValidatorException {

		updateDuree();
		if (duree == 0) {
			throw new ValidatorException(new FacesMessage("message", null));
		} else
			updateDuree();

		// String var = (String)input;
		//
		// if(var.length() &lt; 3){
		// throw new ValidatorException(
		// new FacesMessage(message, null));
		// }
	}

	public void initialisationClientAndAdhesion() {
		adhesion = new Adhesion();
		newDevis();

		clientBean.setClient(new Client());
		
		/*** ws client ***/
		clientBean.setFromWS(false);
		clientBean.setNomWS(false);
		clientBean.setPrenomWS(false);
		clientBean.setDateNaisWS(false);
		clientBean.setCinWS(false);
		clientBean.setRibWS(false);
	}

	private boolean desabledGenerateNoticeIncendie = true;
	private boolean desabledEncaiss = true;

	public boolean isDesabledEncaiss() {
		return desabledEncaiss;
	}

	public void setDesabledEncaiss(boolean desabledEncaiss) {
		this.desabledEncaiss = desabledEncaiss;
	}

	public boolean isDesabledGenerateNoticeIncendie() {
		return desabledGenerateNoticeIncendie;
	}

	public void setDesabledGenerateNoticeIncendie(
			boolean desabledGenerateNoticeIncendie) {
		this.desabledGenerateNoticeIncendie = desabledGenerateNoticeIncendie;
	}

	private Proposition selectedProposition;

	public Proposition getSelectedProposition() {
		return selectedProposition;
	}

	public void setSelectedProposition(Proposition selectedProposition) {
		this.selectedProposition = selectedProposition;
	}

	public Map<String, Object> getlistnumplc(String cod_societ, Profil profil) {
		Map<String, Object> listnumplc = new LinkedHashMap<String, Object>();
		List<Police> list = null;
		if (profil.getCode().equals(PofileEnum.IST.name())
				|| profil.getCode().equals(PofileEnum.ASI.name())) {
			list = policeDAO.getPolice_by_prod(produitDAO
					.getProduit_by_code(ENUMCODEPRODUIT.INCENDIE.getCode()));
		} else {
			list = policeDAO.getPolice_by_conv_prod(conventionDAO
					.find_conv_by_Societ(societeDAO.find(cod_societ)),
					produitDAO.getProduit_by_code(ENUMCODEPRODUIT.INCENDIE
							.getCode()));
		}
		for (Police police : list) {
			listnumplc.put(police.getLibelle().trim(), police.getLibelle());
		}

		return listnumplc;
	}

	public void rechercherRefCreditDepuisWS_ancien() throws NoSuchAlgorithmException,
			KeyManagementException {

		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() },
				new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		// final String BASE_URI = Proprietes.URL_REF_STB;
		final String BASE_URI = Constante.URL_REF_STB;

		String numCompteClient = "0";

		ExampleResourceClient erc = new ExampleResourceClient(RefCredit,
				BASE_URI);
		// System.out.println(erc.getXml());

		if (erc.HttpReponse() != 200) {
			System.out.println("probleme de donnees");

			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_FATAL,
									"Erreur serveur, veuiller contacter l'administrateur!",
									""));
		}

		else {

			try {
				System.out.println("reference credit=" + RefCredit);

				Node dcNode = with(erc.getXml()).get("ArrayOfDC.DC[0]");

				NumberFormat nf = NumberFormat.getInstance();

				if (!dcNode.getNode("compteclient").toString()
						.equalsIgnoreCase("null")) {

					String left = dcNode.getNode("compteclient").toString()
							.substring(5);
					System.out.println("left " + left);
					String right = left.substring(0, 13);
					System.out.println("right " + right);
					numCompteClient = right;
				}

				try {
					capital = !dcNode.getNode("capital").toString()
							.equalsIgnoreCase("null") ? nf.parse(
							dcNode.getNode("capital").toString()).doubleValue()
							: 0;
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// franchise=
				// !dcNode.getNode("dureefranchise").toString().equalsIgnoreCase("null")
				// ? dcNode.getNode("dureefranchise").toString() : "" ;

				if (!dcNode.getNode("duration").toString()
						.equalsIgnoreCase("null")) {

					Period period = Period.months(
							Integer.parseInt(dcNode.getNode("duration")
									.toString())).normalizedStandard();
					nbannee = period.getYears();
					nbmois = period.getMonths();
					updateDuree();
				}

				// try {
				// tauxinteret =
				// !dcNode.getNode("interetrate").toString().equalsIgnoreCase("null")
				// ?
				// nf.parse(dcNode.getNode("interetrate").toString()).doubleValue()
				// : 0 ;
				// } catch (ParseException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				/*
				 * if(!dcNode.getNode("startdate").toString().equalsIgnoreCase(
				 * "null")){
				 * 
				 * DateTimeFormatter formatter =
				 * DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"); DateTime dt
				 * =
				 * formatter.parseDateTime(dcNode.getNode("startdate").toString
				 * ()); effDate = dt.toDate();
				 * 
				 * }
				 * 
				 * if(!dcNode.getNode("enddate").toString().equalsIgnoreCase("null"
				 * )){
				 * 
				 * DateTimeFormatter formatter =
				 * DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"); DateTime dt
				 * =
				 * formatter.parseDateTime(dcNode.getNode("enddate").toString()
				 * ); datefin =dt.toDate();
				 * 
				 * }
				 */

			} catch (ClassCastException e) {

				capital = 0;
				nbannee = 0;
				nbmois = 0;
				effDate = aujourdhui;
				datefin = null;

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Données introuvables!", ""));

				System.out.println("probleme de cast  ");
				e.printStackTrace();

			} catch (Exception e) {

				capital = 0;
				nbannee = 0;
				nbmois = 0;
				effDate = aujourdhui;
				datefin = null;

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL,
								"Données introuvables!", ""));

				System.out.println("probleme de Exception  ");
				e.printStackTrace();
			}

		}

		erc.close();

		clientBean.getClient().setNcompte(numCompteClient);
		// rechercherClientDepuisWS(numCompteClient);
		// assistanceVoiyageController.rechercherDepuisWS();

	}
	
	
	
	public void rechercherRefCreditDepuisWS() throws NoSuchAlgorithmException,
	KeyManagementException {


		String token = TokenSTB.tokenStb(reportController);
		
		if(token == null) {
			
			FacesMessage msg = new FacesMessage("ERREUR", "Pas de réponse au WS Token!");
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			  
			  //h7ayyaController.setDisabledValidationAdhesion(false);
				//setDesabledEncaiss(true); 
				
			  return;
			
		}else {
			
			if(token.equalsIgnoreCase("exception")) {
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur de connexion au WS Token!", ""));
	     		
	     		//h7ayyaController.setDisabledValidationAdhesion(false);
				//setDesabledEncaiss(true); 
	     		
	     		System.out.println("probleme de Exception  ");
	     		
	     		return;
			}
			
			if(token.equalsIgnoreCase("not200")) {
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Problème de droit d'accès au WS Token!", ""));
	     		
	     		//h7ayyaController.setDisabledValidationAdhesion(false);
				//setDesabledEncaiss(true); 
	     		
	     		System.out.println("probleme de Exception  ");
	     		
	     		return;
			}
		}		

// final String BASE_URI = "https://openbank.stb.com.tn/v0/GetCreditInfobyContractCode/";
final String BASE_URI = Constante.URL_REF_STB;


try {
	
	



com.sun.jersey.api.client.Client client = JerseyClient.ApiClient(reportController);
ClientResponse response = JerseyClient.GetReponseDuClientSTB(BASE_URI + RefCredit, token, client, reportController);



if ( response == null) {
	System.out.println("probleme de connexion vide");
	
	capital = 0;
	nbannee = 0;
	nbmois = 0;
	effDate = aujourdhui;
	datefin = null;
	
	FacesMessage msg = new FacesMessage("Pas de réponse au WS Référence Crédit!", "ERREUR");
	msg.setSeverity(FacesMessage.SEVERITY_FATAL);
	FacesContext.getCurrentInstance().addMessage("Erreur", msg);

	return;
}



if (response.getStatus() != 200) {
//	throw new RuntimeException("Failed : HTTP error code : "
//	     + response.getStatus());
	
	capital = 0;
	nbannee = 0;
	nbmois = 0;
	effDate = aujourdhui;
	datefin = null;
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_FATAL, 
						"Erreur serveur (code ="+response.getStatus()+") ", ""));
		return;
}

System.out.println("Output from Server .... \n");
String output = response.getEntity(String.class);
System.out.println(output);

JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);

 
if(jsonOutput.has("ResponseCode")){
	
	
	
	if(jsonOutput.getString("ResponseCode").equalsIgnoreCase("00")) {
		
		NumberFormat nf = NumberFormat.getInstance();
		
		System.out.println("avant_repl "+jsonOutput.getString("CapitalNominal"));
		
		System.out.println("apres_repl "+jsonOutput.getString("CapitalNominal").replace(",", "."));
		
		/*
		capital = nf.parse(
				jsonOutput.getString("CapitalNominal").replace(",", ".")
				).doubleValue();
				*/
		
		capital =  Double.parseDouble(jsonOutput.getString("CapitalNominal").replace(",", "."));
		
		System.out.println("capital "+capital);
		
		Period period = Period.months(
				Integer.parseInt(
						jsonOutput.getString("Duration")
						)).normalizedStandard();
		nbannee = period.getYears();
		nbmois = period.getMonths();
		updateDuree();
				
		
	} 
	
	else  {
		
		if(jsonOutput.has("RejectMessage")) {
			
			capital = 0;
			nbannee = 0;
			nbmois = 0;
			effDate = aujourdhui;
			datefin = null;
			
			FacesMessage msg = new FacesMessage(jsonOutput.getString("RejectMessage"), jsonOutput.getString("RejectMessage"));
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			return;
			
		}else {
			
			capital = 0;
			nbannee = 0;
			nbmois = 0;
			effDate = aujourdhui;
			datefin = null;
			
			FacesMessage msg = new FacesMessage("Données introuvables!", "Données introuvables!");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			return;
			
		}
		
	}
	
}




} 

catch (ClientHandlerException e) {
	
	capital = 0;
	nbannee = 0;
	nbmois = 0;
	effDate = aujourdhui;
	datefin = null;
	
		System.out.println("probleme de connexion vide");
		FacesMessage msg = new FacesMessage("Problème d'appel au WS Référence Crédit!", "ERREUR");
		msg.setSeverity(FacesMessage.SEVERITY_FATAL);
		FacesContext.getCurrentInstance().addMessage("Erreur", msg);

		System.out.println("probleme de ClientHandlerException  ");
		
		e.printStackTrace();
		
	}
catch (JSONException e) {

	capital = 0;
	nbannee = 0;
	nbmois = 0;
	effDate = aujourdhui;
	datefin = null;
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur au niveau du retour du WS Référence Crédit!", ""));
		
		//h7ayyaController.setDisabledValidationAdhesion(false);
		//setDesabledEncaiss(true); 
		
		System.out.println("probleme de JSONException  ");
		
	e.printStackTrace();

  }

catch (Exception e) {

	capital = 0;
	nbannee = 0;
	nbmois = 0;
	effDate = aujourdhui;
	datefin = null;
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(
						FacesMessage.SEVERITY_FATAL, "Erreur interne liée au niveau du WS Référence Crédit!", ""));
		
		System.out.println("probleme de Exception  ");
		
	e.printStackTrace();

  }




}	
	
	
	

	public boolean isAffiche() {
		return affiche;
	}

	public void setAffiche(boolean affiche) {
		this.affiche = affiche;
	}
	
	

	// public int Status = 0;
	// public int validate = 0;
	//
	// public int getStatus() {
	// return Status;
	// }
	//
	// public void setStatus(int status) {
	// Status = status;
	// }
	//
	// public int getValidate() {
	// return validate;
	// }
	//
	// public void setValidate(int validate) {
	// this.validate = validate;
	// }

}
