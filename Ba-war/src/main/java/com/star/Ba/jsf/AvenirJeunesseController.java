package com.star.Ba.jsf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

import com.module.txt.avenirjeunesse.Module1;
import com.star.Ba.commun.Constante;
import com.star.Ba.dao.AvenirJeunesseDAOInter;
import com.star.Ba.dao.IAdherentDAO;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IAgeServiceBourseDAO;
import com.star.Ba.dao.IApiResponseEntityDAO;
import com.star.Ba.dao.IBeneficiaireDAO;
import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IDureeServiceBourseDAO;
import com.star.Ba.dao.IFractionnementDAO;
import com.star.Ba.dao.IGarantieAvjDAO;
import com.star.Ba.dao.ILocgouvDAO;
import com.star.Ba.dao.IOperationTransactionDAO;
import com.star.Ba.dao.IParamGarantieAvjDAO;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IQuittanceDAO;
import com.star.Ba.dao.IQuittanceDetailsDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.ITauxCroissancePrimeDAO;
import com.star.Ba.dao.ITypeVoieDAO;
import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.DetailsEvolutionGarantieAvjDTO;
import com.star.Ba.dto.DetailsGarantieDTO;
import com.star.Ba.dto.Medical;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.Frequency;
import com.star.Ba.jsf.util.FrequencyService;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.jsf.util.Nombre;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.jsf.util.ProxyConnectionFactory;
import com.star.Ba.jsf.util.RapportMedical;
import com.star.Ba.jsf.util.TokenSTB;
import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.AgeServiceBourse;
import com.star.Ba.model.ApiResponseEntity;
import com.star.Ba.model.Beneficiaire;
import com.star.Ba.model.Client;
import com.star.Ba.model.DetailAvj;
import com.star.Ba.model.DetailsEvolutionGarantieAVJ;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieAVJ;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.DureServiceBourse;
import com.star.Ba.model.Fractionnement;
import com.star.Ba.model.GarantieAvj;
import com.star.Ba.model.Locgouv;
import com.star.Ba.model.ModePaiementHayya;
import com.star.Ba.model.OperationTransaction;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.ProfessionClient;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.QuittanceDetails;
import com.star.Ba.model.TauxCroissancePrime;
import com.star.Ba.model.TauxInteret;
import com.star.Ba.model.TypeVoie;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.AssistantVoiyagetServiceInter;
import com.star.Ba.service.IParametresAvjService;
import com.star.Ba.service.Module1Impl;
import com.star.Ba.service.ParametresAvjService;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;


@ManagedBean
@SessionScoped
public class AvenirJeunesseController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;// = new Utilisateurs();
	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	
	
    @EJB
	private AvenirJeunesseDAOInter avenirJeunesseDAOInter;
	
	
	private boolean affiche = true;
	
	@EJB
	private IAdhesionDAO adhesionDAO;
	
	@EJB
    private IOperationTransactionDAO iOperationTransactionDAO;
	
	private String mnt_total, mnt_nette, mnt_capita, mnt_total_avec_surprime, mnt_nette_avec_surprime;
	
	
	private List<DetailsEvolutionGarantieAvjDTO> detailsEvolutionGarantiesDTO = new ArrayList<>();
	
	private List<DetailsEvolutionGarantieAVJ> detailsEvolutionGarantiesAVJ = new ArrayList<>();
	
	@EJB
	private IParametresAvjService iParametresAvjService;
	@EJB
	private IProduitDAO iProduitDAO;
	@EJB
	private ITauxCroissancePrimeDAO iTauxCroissancePrimeDAO;
	@EJB
	private IPoliceDAO policeDAO;
	@EJB
	private IProduitDAO produitDAO;
	@EJB
	private IConventionDAO conventionDAO;
	@EJB
	private ISocieteDAO societeDAO;
	@EJB
	private IFractionnementDAO fractionnementDAO;
	@EJB
	private ITypeVoieDAO typeVoieDAO;
	@EJB
	private ILocgouvDAO locgouvDAO;
	@EJB
	private IAdherentDAO adherentDAO ;
	@EJB
	private IBeneficiaireDAO beneficiaireDAO ;
	@EJB
	private IPoliceDAO iPoliceDAO;
	@EJB
	private IClientDAO clientDAO;
	@EJB
	private IAgeServiceBourseDAO  ageServiceBourseDAO;
	@EJB
	private IDureeServiceBourseDAO dureeServiceBourseDAO;
	@EJB
	private IParamGarantieAvjDAO paramGarantieAvjDAO;
	@EJB
	private IGarantieAvjDAO garantieAvjDAO;
	@EJB
	private Module1Impl module1Impl;
	@EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;
	@EJB
	private IQuittanceDAO quittanceDAO;
	@EJB
	private IQuittanceDetailsDAO quittanceDetailsDAO;
	private Module1 module1;
	private boolean AS = false;
	private boolean desabledGenerateNotice=true;
	private double fraisContrat;
	private int controlemaxduree;
	private Date minageAssure;
	private Date maxage;
	private Date maxdate;
	private Date aujourdhui;
	private Date datePremiereBourse;
	private Client client;
	private Police libnumplc;
	private Adhesion newAdhesion=new Adhesion();
	private Adherent newAdherent=new Adherent();
	private Beneficiaire newBeneficiaire=new Beneficiaire();
	private String cod_societe;
	private List<Fractionnement> allFractionnements;
	private List<DureServiceBourse> allDureServiceBourses;
	private List<TauxCroissancePrime> allTauxCroissancePrimes;
	private List<AgeServiceBourse> allAgeServiceBourses;
	private List<ProfessionClient> allProfessions;
	private List<TauxInteret> allTauxInteres;
	private TauxInteret currentTauxInteret;
	private double selectedTauxInteret;
	private double selectedTauxCroissancePrime;
	private double  tauxTechnique;
	private Double fraisInscriptionAnnuel;
	private Double renteMonsuelleEnCasDeces;
	private Double bourseMensuelle;
	private AgeServiceBourse selectedAgeServiceBourse;
	private DureServiceBourse  selectedDureServiceBourse;
	private Produit produit;
	private double tauxCroissanceBource=0.0;
	private boolean skip;
	private int fractionnement;
	private boolean existFraisInscriptionAnnuel=true;
	private boolean existrenteMonsuelleEnCasDeces=true;
	private double Prime_Nette_Deces_base;
	
	private double PAP_BaseEpargne;
	private double PAP_InscriptionEpargne;
	private double tauxCroissBourse = 0.0;
	private double capitalConstitutifGarentieBase;
	private double garentieBasePrimeEpargnePAP;
	private double garentieBasePrimeEpargnePPP;
	private double garentieBasePrimeEpargnePPCOM;
	private double garentieBasePrimedecesPAP;
	private double garentieBasePrimedecesPPP;
	private double garentieBasePrimedecesPUP;
	private double garentieBasePrimedecesPPCOM;
	private double garentieBaseToalPAP;
	private double garentieBaseToalPPP;
	private double garentieBaseToalPPCOM;
	private double capitalConstitutifInscription;
	private double garentieInscriptionPrimeEpargnePAP;
	private double garentieInscriptionPrimeEpargnePPP;
	private double garentieInscriptionPrimeEpargnePPCOM;
	private double garentieInscriptionPrimedecesPAP;
	private double garentieInscriptionPrimedecesPPP;
	private double garentieInscriptionPrimedecesPUP;
	private double garentieInscriptionPrimedecesPPCOM;
	private double garentieInscriptionToalPAP;
	private double garentieInscriptionToalPPP;
	private double garentieInscriptionToalPPCOM;
	private double capitalConstitutifRenteEnCasDece;
	private double garentieRenteEnCasDecePrimedecesPAP;
	private double garentieRenteEnCasDecePrimedecesPPP;
	private double garentieRenteEnCasDecePrimedecesPUP;
	private double garentieRenteEnCasDecePrimedecesPPCOM;
	private double garentieRenteEnCasDeceToalPAP;
	private double garentieRenteEnCasDeceToalPPP;
	private double garentieRenteEnCasDeceToalPPCOM;
	private List<TypeVoie> typeVoies;
	private List<Locgouv> locgouvs;
	private Locgouv selectedLocgouv;
	Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat = new HashMap<String, List<Locgouv>>();
	List<String> allGouverneras = new ArrayList<String>();
	Map<String, Object> listCPT = new LinkedHashMap<String, Object>();
	List<Locgouv> allCodePostalByGouvernera = new ArrayList<Locgouv>();
	private double basePrimeNetteEpargne;
	private double prime_Nette_Epargne_Inscription;
	private double prime_Nette_Deces_Inscription;
	private double prime_Nette_Deces_Rente;
	private double total_base;
	private double total_inscription;
	private double total_rente;
	private double total_nette;
	private double fraisGestion;
	private double fraisGest;
	private double prime_totale;
	private double total_nette_annuelle;
	private Adhesion newAdhesionToPrint;
	private  static final double FRAISACQUISITION =  0.18 ;//table produit where code produ = 691

	private  static final double FRAISCOMMISSION = 0.02;//	table TAUXCOMM	
	private String numquit;
	//--------------------------------------------------------


	private List<Medical> medicals = new ArrayList<>();
	
	private RapportMedical rapportMedical = new RapportMedical();
	
	private List<DetailsGarantieDTO> detailsGaranties = new ArrayList<>();
	
	private List<DetailsGarantieAVJ> detailsGarantieAVJs = new ArrayList<>();
	
	private Double surprime = 0.0;
	private double prime_totale_with_surprime_for_affichage = 0;
	
	private double prime_totale_avec_surprime;
	
	private double prime_nette_avec_surprime;
	
	
	private boolean valider_impot = false;
	private Date dateImpot = null;
	private Date dateImpot_init = null;
	
	private Date dateImpotDebut = null;
	private Date dateImpotFin = null;
	
	
	private double dtranche;
	private String modepaie;
	private boolean disabledModepaie = false;
	private boolean rendredModepaie = false;  // rendred = visible
	
	
	
	
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
	
	
	


	public Adhesion getNewAdhesionToPrint() {
		return newAdhesionToPrint;
	}
	public void setNewAdhesionToPrint(Adhesion newAdhesionToPrint) {
		this.newAdhesionToPrint = newAdhesionToPrint;
	}
	public ReportController getReportController() {
		return reportController;
	}

	public double getFraisContrat() {
		return fraisContrat;
	}

	public void setFraisContrat(double fraisContrat) {
		this.fraisContrat = fraisContrat;
	}

	public int getControlemaxduree() {
		return controlemaxduree;
	}

	public void setControlemaxduree(int controlemaxduree) {
		this.controlemaxduree = controlemaxduree;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}

	public double getPAP_InscriptionEpargne() {
		return PAP_InscriptionEpargne;
	}
	public void setPAP_InscriptionEpargne(double pAP_InscriptionEpargne) {
		PAP_InscriptionEpargne = pAP_InscriptionEpargne;
	}

	public double getTauxCroissBourse() {
		return tauxCroissBourse;
	}

	public void setTauxCroissBourse(double tauxCroissBourse) {
		this.tauxCroissBourse = tauxCroissBourse;
	}

	public double getPAP_BaseEpargne() {
		return PAP_BaseEpargne;
	}

	public void setPAP_BaseEpargne(double pAP_BaseEpargne) {
		PAP_BaseEpargne = pAP_BaseEpargne;
	}

	public int getFractionnement() {
		return fractionnement;
	}

	public void setFractionnement(int fractionnement) {
		this.fractionnement = fractionnement;
	}

	private double tauxCroissancePrimes;

	public double getTauxCroissancePrimes() {
		return tauxCroissancePrimes;
	}

	public void setTauxCroissancePrimes(double tauxCroissancePrimes) {
		this.tauxCroissancePrimes = tauxCroissancePrimes;
	}

	public double getPrime_Nette_Deces_base() {
		return Prime_Nette_Deces_base;
	}

	public void setPrime_Nette_Deces_base(double prime_Nette_Deces_base) {
		Prime_Nette_Deces_base = prime_Nette_Deces_base;
	}
	public String getCod_societe() {
		return cod_societe;
	}

	public void setCod_societe(String cod_societe) {
		this.cod_societe = cod_societe;
	}

	public Map<String, List<Locgouv>> getMapAllCodePostalByGouvernerat() {
		return mapAllCodePostalByGouvernerat;
	}

	public void setMapAllCodePostalByGouvernerat(Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat) {
		this.mapAllCodePostalByGouvernerat = mapAllCodePostalByGouvernerat;
	}

	public List<String> getAllGouverneras() {
		return allGouverneras;
	}

	public void setAllGouverneras(List<String> allGouverneras) {
		this.allGouverneras = allGouverneras;
	}

	public List<TypeVoie> getTypeVoies() {
		return typeVoies;
	}

	public void setTypeVoies(List<TypeVoie> typeVoies) {
		this.typeVoies = typeVoies;
	}

	public List<Locgouv> getLocgouvs() {
		return locgouvs;
	}

	public void setLocgouvs(List<Locgouv> locgouvs) {
		this.locgouvs = locgouvs;
		for (Locgouv locgouv : this.locgouvs) {

			listCPT.put(locgouv.getCodePosta() + " " + locgouv.getLocalite(),
					locgouv);
		}
	}

	public Map<String, Object> getListCPT() {
		return listCPT;
	}

	public void setListCPT(Map<String, Object> listCPT) {
		this.listCPT = listCPT;
	}


	public List<Locgouv> getAllCodePostalByGouvernera() {
		return allCodePostalByGouvernera;
	}

	public void setAllCodePostalByGouvernera(
			List<Locgouv> allCodePostalByGouvernera) {
		this.allCodePostalByGouvernera = allCodePostalByGouvernera;
	}
	public Locgouv getSelectedLocgouv() {
		return selectedLocgouv;
	}

	public void setSelectedLocgouv(Locgouv selectedLocgouv) {
		this.selectedLocgouv = selectedLocgouv;
	}
	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	public double getPrime_totale() {
		return prime_totale;
	}

	public void setPrime_totale(double prime_totale) {
		this.prime_totale = prime_totale;
	}
	/***** valeur calculer ******/

	public double getBasePrimeNetteEpargne() {
		return basePrimeNetteEpargne;
	}

	public void setBasePrimeNetteEpargne(double basePrimeNetteEpargne) {
		this.basePrimeNetteEpargne = basePrimeNetteEpargne;
	}

	public double getPrime_Nette_Epargne_Inscription() {
		return prime_Nette_Epargne_Inscription;
	}

	public void setPrime_Nette_Epargne_Inscription(
			double prime_Nette_Epargne_Inscription) {
		this.prime_Nette_Epargne_Inscription = prime_Nette_Epargne_Inscription;
	}

	public double getPrime_Nette_Deces_Inscription() {
		return prime_Nette_Deces_Inscription;
	}

	public void setPrime_Nette_Deces_Inscription(double prime_Nette_Deces_Inscription) {
		this.prime_Nette_Deces_Inscription = prime_Nette_Deces_Inscription;
	}

	public double getPrime_Nette_Deces_Rente() {
		return prime_Nette_Deces_Rente;
	}

	public void setPrime_Nette_Deces_Rente(double prime_Nette_Deces_Rente) {
		this.prime_Nette_Deces_Rente = prime_Nette_Deces_Rente;
	}

	public double getTotal_base() {
		return total_base;
	}

	public void setTotal_base(double total_base) {
		this.total_base = total_base;
	}

	public double getTotal_inscription() {
		return total_inscription;
	}

	public void setTotal_inscription(double total_inscription) {
		this.total_inscription = total_inscription;
	}

	public double getTotal_rente() {
		return total_rente;
	}

	public void setTotal_rente(double total_rente) {
		this.total_rente = total_rente;
	}

	public double getTotal_nette() {
		return total_nette;
	}

	public void setTotal_nette(double total_nette) {
		this.total_nette = total_nette;
	}
	
	
	

	public double getTotal_nette_annuelle() {
		return total_nette_annuelle;
	}
	public void setTotal_nette_annuelle(double total_nette_annuelle) {
		this.total_nette_annuelle = total_nette_annuelle;
	}
	public double getFraisGestion() {
		return fraisGestion;
	}

	public void setFraisGestion(double fraisGestion) {
		this.fraisGestion = fraisGestion;
	}
	
	


	public Double getSurprime() {
		return surprime;
	}
	public void setSurprime(Double surprime) {
		this.surprime = surprime;
	}
	
	
	public double getPrime_totale_with_surprime_for_affichage() {
		return prime_totale_with_surprime_for_affichage;
	}
	public void setPrime_totale_with_surprime_for_affichage(double prime_totale_with_surprime_for_affichage) {
		this.prime_totale_with_surprime_for_affichage = prime_totale_with_surprime_for_affichage;
	}
	public Date getAujourdhui() {
		return aujourdhui;
	}

	public void setAujourdhui(Date aujourdhui) {
		this.aujourdhui = aujourdhui;
	}

	public Date getMaxdate() {
		return maxdate;
	}

	public void setMaxdate(Date maxdate) {
		this.maxdate = maxdate;
	}



	public Date getMaxage() {
		return maxage;
	}

	public void setMaxage(Date maxage) {
		this.maxage = maxage;
	}


	public Date getMinageAssure() {
		return minageAssure;
	}

	public void setMinageAssure(Date minageAssure) {
		this.minageAssure = minageAssure;
	}
	public String getNumquit() {
		return numquit;
	}


	public void setNumquit(String numquit) {
		this.numquit = numquit;
	}
	
	
	private boolean desabledEncaiss = false;

	public boolean isDesabledEncaiss() {
		return desabledEncaiss;
	}

	public void setDesabledEncaiss(boolean desabledEncaiss) {
		this.desabledEncaiss = desabledEncaiss;
	}
	
	

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
	@PostConstruct
	public void init() {
		allDureServiceBourses=iParametresAvjService.allDureServiceBourses();
		allFractionnements=iParametresAvjService.allFractionnements();
		allTauxCroissancePrimes=iParametresAvjService.allTauxCroissancePrimes();
		allAgeServiceBourses=iParametresAvjService.allAgeServiceBourses();
		selectedAgeServiceBourse=allAgeServiceBourses.get(0);
		allProfessions=clientBean.getProfessions();
//		currentTauxInteret=iParametresAvjService.getTauxInteretByAnnee(Calendar.getInstance().get(Calendar.YEAR)); //15/05/2025
		//allTauxInteres=iParametresAvjService.allTauxInteret();
		produit = iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.AVENIRJENESSE.getCode());
		typeVoies = typeVoieDAO.findAll();
		List<Locgouv> list = locgouvDAO.findAll();

		for (Locgouv locgouv : list) {
			if (mapAllCodePostalByGouvernerat.containsKey(locgouv
					.getGouvernora())) {
				mapAllCodePostalByGouvernerat.get(locgouv.getGouvernora()).add(
						locgouv);
			} else {
				allGouverneras.add(locgouv.getGouvernora());
				mapAllCodePostalByGouvernerat.put(locgouv.getGouvernora(),
						new ArrayList<Locgouv>());
			}

		}

		updatepolice();
		tauxCroissBourse = 0.0;
		if(cod_societe!=null) {
		libnumplc=iParametresAvjService.getnumplcbylibelle(cod_societe,numplc);
		}
		newAdhesion.setDatDebut(aujourdhui);
		controleMaxDateDebut();
		controleminageAssure();
		fraisContrat=3.000;//il etait 5 dt avant test moa le 15/04/2022 il a été supprimé et remplacé par fga
		setDesabledGenerateNotice(true);
		desabledEncaiss = false;
		selectedTauxInteret=0.0;
		dureeContract=0;
		clientBean.setAS(false);
		bourseMensuelle=0.0;
		fraisInscriptionAnnuel=0.0;
		renteMonsuelleEnCasDeces=0.0;
		
		dtranche=0.0;
		
		if( utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000") || 
				utilisateur.getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat())) { // stb
			
			this.rendredModepaie = true;
			
		}else {// autres societe 
			this.rendredModepaie = false;
			this.modepaie = "-1";
		}
		
		
		
	    joursDisponibles = new ArrayList<>();
	    for (int i = 3; i <= 28; i++) {
	        joursDisponibles.add(new SelectItem(i, String.valueOf(i)));
	    }
	    
	    moisDisponibles = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	    	moisDisponibles.add(new SelectItem(i, mois[i-1]));
	    }
	    
	    surprime = 0.0;

	}

	public void controleMaxDateDebut()
	{
		Calendar cal = Calendar.getInstance();
		aujourdhui = new Date();
		cal.setTime(aujourdhui);
		cal.add(Calendar.MONTH,	1);
		maxdate=cal.getTime();

	}

	public void controleminageAssure(){
		minageAssure = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(minageAssure);
		cal.add(Calendar.YEAR, -18);
		minageAssure = cal.getTime();

	}

	public void controlemaxageAssure(){
		System.out.println("*****************************************controlemaxageAssure");
		if( newAdherent.getDatNaiss()!=null)
		{
			//			controlemaxduree=75-ageAssure();
			//			System.out.println("controlemaxduree="+controlemaxduree);
			//			System.out.println("dureeContract="+dureeContract);
			//			if(dureeContract>controlemaxduree)
			//			{
			//				System.out.println("enter controle ");
			//				FacesMessage msg = new FacesMessage("ERREUR", "la somme durée du contrat et age de l'assuré doit être inférieur ou égal à 75 ");
			//				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			//				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			//	            FacesContext.getCurrentInstance().validationFailed();
			//			}

			//controlemaxduree=75-(int)dureeContract;
		}

	}

	public void updateListCodePostalActionListener() {
		allCodePostalByGouvernera.clear();
		allCodePostalByGouvernera.addAll(mapAllCodePostalByGouvernerat
				.get(newAdherent.getGouver()));

	}

	/*** hoss ***/
	public void locgouvSelectListened(SelectEvent event) {

		System.out.println("sys1 bis");

		Locgouv locgouv = (Locgouv) event.getObject();
		//Locgouv locgouv = selectedLocgouv;
		if (locgouv != null) {
			newAdherent.setCodePostal(locgouv.getCodePosta());
			newAdherent.setLocalite(locgouv.getLocalite());
			newAdherent.setGouver(locgouv.getGouvernora());
			System.out.println("code postal"+newAdherent.getCodePostal());
			System.out.println("sys2 bis "+locgouv);
		} else {
			newAdherent.setCodePostal("");
			newAdherent.setLocalite("");
			newAdherent.setGouver("");
		}
	}


	public String onFlowProcess(FlowEvent event) {
		
		System.out.println("****************skip = "+skip);
		
		if(skip) {

			System.out.println("****************adhesion ou encaissement deja fait********************");
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Flash flash = facesContext.getExternalContext().getFlash();
			flash.setKeepMessages(true);


				facesContext.addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Adhésion déjà enregistrée avec le numéro:", 
								newAdhesionToPrint.getNumeroAdhesion()+"!"));

			return "tarif";
		}else {
			
			System.out.println("************** date fin onFlowProcess=="  +newAdhesion.getDatFin());
			
			if(event.getNewStep().equals("address")) {
				if(dureeContract<0)
				{
					System.out.println("****************dureeContract<0********************");
					FacesMessage msg = new FacesMessage("Erreur: ", "La durée du contrat doit être supérieure à 0 ");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					FacesContext.getCurrentInstance().validationFailed();
					return "personal";
					
				}
			}
			
			if(event.getNewStep().equals("address") && newAdhesion.getDatFin()==null)
			{
				updateTauxInteretActionListener();
				System.out.println("**************update date fin onFlowProcess****************");
			}
			if(event.getNewStep().equals("tarif") && event.getOldStep().equals("beneficiaire"))
			{
				System.out.println("calcul tarif");
				calculTarif_avec_WS();
			}
			if(event.getOldStep().equals("evol_gar"))
			{
				System.out.println("Tableau d'évolution des garanties déjà vu");
			}

			if(event.getNewStep().equals("contact"))
			{
				if(renteMonsuelleEnCasDeces>0 && renteMonsuelleEnCasDeces<200)
				{
					System.out.println("****************test msg rente********************");
					FacesMessage msg = new FacesMessage("Erreur: Le montant de la rente ne peut pas être inférieur à 200 DT ", "Le montant de la rente ne peut pas être inférieur à 200 DT ");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					FacesContext.getCurrentInstance().validationFailed();
					return "address";
					
				}
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

	
	public void handleKeyEvent_total_with_suprime(){
		
		try {
			
			prime_totale_with_surprime_for_affichage = surprime + prime_totale;
			
		}catch (Exception e) {

			prime_totale_with_surprime_for_affichage = 0;
			
	        e.printStackTrace();
			
		}
		
	}

	//	public void calcul(){
	//    	
	//    	 
	//    
	//    	double bourseMensuelle = this.bourseMensuelle;
	//    	System.out.println("bourseMensuelle "+bourseMensuelle);
	//    	
	//    	System.out.println("fraisInscriptionAnnuel "+fraisInscriptionAnnuel);
	//    	double inscriptionAnnuelle = fraisInscriptionAnnuel;
	//    	System.out.println("inscriptionAnnuelle "+inscriptionAnnuelle);
	//    	int fractionnPrime = fractionnement;//Integer.valueOf(getFractionnementConverter().toString()); // =2  Fractionnement : 
	//    	System.out.println("fractionnPrime "+fractionnPrime);
	//    	double CroissPrime = tauxCroissancePrimes;
	//    	System.out.println("CroissPrime "+(double )CroissPrime / 100);
	//    
	//    }

	public long anneNaissance(){
		return getAnnee((newBeneficiaire.getDateNaiss()));
	}
	public long anneDebutContrat(){
		return getAnnee((newAdhesion.getDatDebut()));
	}
	public long age(){
		return anneDebutContrat()-anneNaissance();
	}
	public int ageEnfant(){
		return (int) age();
	}
	public long anneNaissanceAss(){
		return getAnnee((newAdherent.getDatNaiss()));
	}
	public int ageAssure(){
		return (int) (anneDebutContrat() - anneNaissanceAss());
	}
	public int fractionnPrime()
	{
		return fractionnementDAO.find(fractionnement).getNombre_Fraction();
	}
	public double CroissPrime()
	{
		return (double) tauxCroissancePrimes / 100;
	}
	public int ageServiceBourse()
	{
		return selectedAgeServiceBourse.getAgeServiceBourse().intValue();
	}

	public void calculTarif() {

		boolean error = false;

		controlemaxduree=75-ageAssure();
		System.out.println("controlemaxduree="+controlemaxduree);
		System.out.println("dureeContract="+dureeContract);
		if(dureeContract>controlemaxduree)
		{
			System.out.println("enter controle ");
			FacesMessage msg = new FacesMessage("ERREUR", "la somme durée du contrat et age de l'assuré doit être inférieur ou égal à 75 ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			FacesContext.getCurrentInstance().validationFailed();
			error = true;
		}

		if(!error){
			getDatePremiereBourse();
			//module1=new Module1(ageEnfant());

			double bourseMensuelle = this.bourseMensuelle;
			System.out.println("bourseMensuelle "+bourseMensuelle);

			double inscriptionAnnuelle = fraisInscriptionAnnuel;
			System.out.println("fraisInscriptionAnnuel "+fraisInscriptionAnnuel);

			//    	int ageServiceBourse = selectedAgeServiceBourse.getAgeServiceBourse().intValue();
			int ageFinServiceBourse = ageServiceBourse() + selectedDureServiceBourse.getDure().intValue();
			double tauxCroissanceBourse = 0.0;  // a voir
			
			fraisGest=(double) produit.getFraisGestion().doubleValue() / 100 ;

			/**** a été remplacé par :  *****/
			//capitalConstitutifGarentieBase = module1Impl.cb(bourseMensuelle, inscriptionAnnuelle, ageServiceBourse(), ageFinServiceBourse, tauxCroissanceBourse,ageEnfant()); // = 3415.561
			
			tauxTechnique = module1Impl.tmg(ageServiceBourse(),ageEnfant());//(double) selectedTauxInteret / 100;//module1.tmg(ageServiceBourse); // =0.035

			capitalConstitutifGarentieBase = module1Impl.CapitalCb_Unit01(ageServiceBourse(), selectedDureServiceBourse.getDure().intValue(), bourseMensuelle, tauxTechnique, tauxCroissanceBourse);
			System.out.println("capitalConstitutifGarentieBase ici "+capitalConstitutifGarentieBase);
			/***************************************/
			
			
			//    	int fractionnPrime =fractionnementDAO.find(fractionnement).getNombre_Fraction();//Integer.valueOf(getFractionnementConverter().toString()); // =2  Fractionnement : 
			System.out.println("fractionnPrime "+fractionnPrime());



			//    	double CroissPrime = (double) tauxCroissancePrimes / 100;// Integer.valueOf(getFractionnementConverter().toString());  //Taux evolution Prime

			///02/09/2022 tauxCroissBourse = 0.0;

			//    	int ageAssure = (int) (anneDebutContrat() - anneNaissanceAss());

			double fraisAcqui =  FRAISACQUISITION ;//table produit where code produ = 691

			double fraisCommi = FRAISCOMMISSION;//	table TAUXCOMM	


			/******** a ete remplace **************/
//			basePrimeNetteEpargne = BasePrimeNetteEpargne( capitalConstitutifGarentieBase ,tauxTechnique , fractionnPrime() , ageServiceBourse() , ageEnfant() , CroissPrime() , tauxCroissBourse ,0 , ageAssure() 
//					,fraisAcqui, fraisCommi);
			basePrimeNetteEpargne =module1Impl.PAC_Unit01(capitalConstitutifGarentieBase, CroissPrime(), ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, fractionnPrime(), 
					fraisGest, FRAISACQUISITION, FRAISCOMMISSION)/fractionnPrime();
			/********************************************/

			System.out.println("champs "+capitalConstitutifGarentieBase+" "+ CroissPrime()+" "+ ageAssure()+" "+ ageEnfant()+" "+ ageServiceBourse()+" "+ tauxTechnique+" "+ fractionnPrime()+" "+ 
					fraisGest+" "+FRAISACQUISITION+" "+ FRAISCOMMISSION);
			System.out.println("basePrimeNetteEpargne "+basePrimeNetteEpargne);

			PAP_BaseEpargne = module1Impl.pe0(capitalConstitutifGarentieBase, tauxTechnique, fractionnPrime(), ageServiceBourse(), ageEnfant(), CroissPrime(), tauxCroissBourse);


            /******** a ete remplace **************/
//			Prime_Nette_Deces_base=module1Impl.pppAcomm(ageAssure(), (int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_BaseEpargne, 0, fraisAcqui, fraisCommi);
			Prime_Nette_Deces_base=module1Impl.PAC_Unit02(capitalConstitutifGarentieBase, CroissPrime(), ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, fractionnPrime(), 
					fraisGest, FRAISACQUISITION, FRAISCOMMISSION, PartiEntier(dureeContract))/fractionnPrime();
			/****************************/
			
			
			/*** calcul prime épargne pour Garantie Frais de rentrée universaitaire Annuelle *****/
			
			/****** a été remplacé ici par *****************/
			//capitalConstitutifInscription = module1Impl.ci(bourseMensuelle, inscriptionAnnuelle, ageServiceBourse(), ageFinServiceBourse, tauxCroissBourse,ageEnfant());
			capitalConstitutifInscription = module1Impl.CapitalCb_Unit03(fraisInscriptionAnnuel, selectedDureServiceBourse.getDure().intValue(), tauxTechnique);
			System.out.println("capitalConsti_GarantieA ci "+capitalConstitutifInscription);
			/*****************************************************/

//			prime_Nette_Epargne_Inscription = BasePrimeNetteEpargne( capitalConstitutifInscription ,tauxTechnique , fractionnPrime() , ageServiceBourse() , ageEnfant() , CroissPrime() , tauxCroissBourse ,0 , ageAssure() 
//					,fraisAcqui, fraisCommi);
			prime_Nette_Epargne_Inscription = module1Impl.PAC_Unit03(capitalConstitutifInscription, CroissPrime(), ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, fractionnPrime(), 
					fraisGest, FRAISACQUISITION, FRAISCOMMISSION)/fractionnPrime();
			System.out.println("prime_Nette_Epargne_Inscription "+prime_Nette_Epargne_Inscription);


			/*** calcul prime décès pour Garantie Frais de rentrée universaitaire Annuelle *****/
			PAP_InscriptionEpargne = module1Impl.pe0(capitalConstitutifInscription, tauxTechnique, fractionnPrime(), ageServiceBourse(), ageEnfant(), CroissPrime(), tauxCroissBourse);

			/***** a ete remplace ***********/
//			prime_Nette_Deces_Inscription = module1Impl.pppAcomm(ageAssure(), (int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_InscriptionEpargne, 0, fraisAcqui, fraisCommi);

			prime_Nette_Deces_Inscription = module1Impl.PAC_Unit04(capitalConstitutifInscription, CroissPrime(), ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, fractionnPrime(), 
					fraisGest, FRAISACQUISITION, FRAISCOMMISSION, PartiEntier(dureeContract))/fractionnPrime();
			/***********************************/
			
			
			
			
			/*** calcul prime décès pour Garantie Rente *****/
			/****** a ete remplace **************/
//			prime_Nette_Deces_Rente = module1Impl.pppBcomme(ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, CroissPrime(), renteMonsuelleEnCasDeces, fractionnPrime(), 0, fraisAcqui, fraisCommi);

			prime_Nette_Deces_Rente = module1Impl.PAC_Unit05(renteMonsuelleEnCasDeces, CroissPrime(), ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, fractionnPrime(), 
					fraisGest, FRAISACQUISITION, FRAISCOMMISSION, PartiEntier(dureeContract))/fractionnPrime();
            /**************************************/
			
			
			total_base = Prime_Nette_Deces_base + basePrimeNetteEpargne;

			total_inscription = prime_Nette_Deces_Inscription + prime_Nette_Epargne_Inscription;

			total_rente = prime_Nette_Deces_Rente;

			total_nette = total_base + total_inscription + total_rente;

			fraisGestion= produit.getFraisGestion().doubleValue() ;

			 //fraisGest=(double) produit.getFraisGestion().doubleValue() / 100 ;

			prime_totale= total_nette + fraisContrat;
			System.out.println("total_nette="+total_nette);
			System.out.println("fraisContrat="+fraisContrat);
			System.out.println("prime_totale="+prime_totale);

		}
	}
	
	
	public void calculTarif_avec_WS() {
		
		/*
		if (!UpdateDateOfBirth()) {
			setDesabledEncaiss(true); 
			return;
	    }
	    */
		
		System.out.println("fin calcul age et debut ws tarif");
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		
		String date_effet = dateFormat.format(newAdhesion.getDatDebut());
		String date_ech = dateFormat.format(newAdhesion.getDatFin());
		String date_nais_benef = dateFormat.format(newBeneficiaire.getDateNaiss());
		
		String date_nais_assure = dateFormat.format(clientBean.getClient().getDatNaiss());
		
		Double Bourse_mensuelle = bourseMensuelle;
		
		long Age_de_service_bourse = selectedAgeServiceBourse.getId();
		
		int Duree_de_service_bourse = selectedDureServiceBourse.getDure().intValue();
		
		int fractionnement_ = 1;
		
		switch(fractionnement) {
		
		case 1 : fractionnement_ = 4;
		break;
		
		case 2 : fractionnement_ = 3;
		break;
		
		case 3 : fractionnement_ = 2;
		break;
		
		case 4 : fractionnement_ = 1;
		break;
		
		default : fractionnement_ = 1;
		break;
		
		}
		
		if( utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000") ||
				utilisateur.getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat())) { // stb
				
		
		if(fractionnement_ == 3 || fractionnement_ == 4) {
			
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
		
		int Taux_evolution_Prime = (int) tauxCroissancePrimes;
		
		double Taux_Croiss_Bourse = tauxCroissBourse;
		
		// double Taux_d_interet = selectedTauxInteret;
		double Taux_d_interet = 4.0;
		
		double Frais_d_inscription_annuel = fraisInscriptionAnnuel.doubleValue();
		
		double Rente_Mensuelle_en_cas_deces = renteMonsuelleEnCasDeces.doubleValue();
		
		
		String Garantie_Rente = " ";
		if(Rente_Mensuelle_en_cas_deces != 0) {
			
			Garantie_Rente = "		,{"
					+ "			\"CODE_GARANTIE\": \"5\","
					+ "			\"LIBELLE_GARANTIE\": \"Garantie Rente Mensuelle en Cas de Décès de l'Assuré\","
					+ "			\"CAPITAL_ASSUREE\": \"0\""
					+ "		}";
			
			prime_Nette_Deces_Rente = 0;
		}
		
		String input = "{"
				+ "	\"CODE_AGENCE\": 625,"
				+ "	\"CODE_UTILISATEUR\": 820,"
				+ "	\"NOTE_COUVERTURE\": \"0000000000\","
				+ "	\"CODE_BRANCHE\": 61,"
		  		+ " \"CODE_SOUS_BRANCHE\": \""+ENUMCODEPRODUIT.AVENIRJENESSE.getCode()+"\","
				+ "	\"CODE_CLIENT\": \"664143\","
				+ "	\"CODE_DUREE\": \"61\","
				+ "	\"DATE_EFFET_POLICE\": \""+date_effet+"\","
				+ "	\"DATE_EXPIRATION\": \""+date_ech+"\","
				+ "	\"Caracteristiques\": ["
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"76\","
				+ "			\"VALEUR_CARACT\": \""+date_nais_benef+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"85\","
				+ "			\"VALEUR_CARACT\": \""+Bourse_mensuelle+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"77\","
				+ "			\"VALEUR_CARACT\": \""+Age_de_service_bourse+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"78\","
				+ "			\"VALEUR_CARACT\": \""+Duree_de_service_bourse+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"11\","
				+ "			\"VALEUR_CARACT\": \""+fractionnement_+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"119\","
				+ "			\"VALEUR_CARACT\": \""+Taux_evolution_Prime+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"2\","
				+ "			\"VALEUR_CARACT\": \""+Taux_d_interet+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},		"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"144\","
				+ "			\"VALEUR_CARACT\": \""+Frais_d_inscription_annuel+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"145\","
				+ "			\"VALEUR_CARACT\": \""+Rente_Mensuelle_en_cas_deces+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"141\","
				+ "			\"VALEUR_CARACT\": \"1\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},		"
				+ "		{"
//				+ "			\"CODE_CARACTERISTIQUE\": \"100\","
//				+ "			\"VALEUR_CARACT\": \"4\","
//				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "         \"CODE_CARACTERISTIQUE\": \"185\","
				+ "         \"VALEUR_CARACT\": \""+dtranche+"\","
				+ "         \"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"120\","
				+ "			\"VALEUR_CARACT\": \""+Taux_Croiss_Bourse+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"180\","
				+ "			\"VALEUR_CARACT\": \""+date_nais_assure+"\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		}"
				+ "	],"
				+ "	\"Garanties\": ["
				+ "		{"
				+ "			\"CODE_GARANTIE\": \"1\","
				+ "			\"LIBELLE_GARANTIE\": \"Epargne bourse Mensuelle\","
				+ "			\"CAPITAL_ASSUREE\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_GARANTIE\": \"2\","
				+ "			\"LIBELLE_GARANTIE\": \"Prise en charge prime Bourse Mensuelle\","
				+ "			\"CAPITAL_ASSUREE\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_GARANTIE\": \"3\","
				+ "			\"LIBELLE_GARANTIE\": \"frais Universitaires\","
				+ "			\"CAPITAL_ASSUREE\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_GARANTIE\": \"4\","
				+ "			\"LIBELLE_GARANTIE\": \"Prise en charge prime Frais Universitaires\","
				+ "			\"CAPITAL_ASSUREE\": \"0\""
				+ "		}"	
				+ Garantie_Rente			
				+ "	]"
				+ "}";
		
		
		String input_ =  "{"
				+ "	\"CODE_AGENCE\": 825,"
				+ "	\"CODE_UTILISATEUR\": 634,"
				+ "	\"NOTE_COUVERTURE\": \"0000000000\","
				+ "	\"CODE_BRANCHE\": 61,"
				+ "	\"CODE_SOUS_BRANCHE\": \"692\","
				+ "	\"CODE_CLIENT\": \"639189\","
				+ "	\"CODE_DUREE\": \"61\","
				+ "	\"DATE_EFFET_POLICE\": \"11/08/2022\","
				+ "	\"DATE_EXPIRATION\": \"11/08/2023\","
				+ "	\"Caracteristiques\": ["
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"76\","
				+ "			\"VALEUR_CARACT\": \"01/08/2018\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"85\","
				+ "			\"VALEUR_CARACT\": \"100.000\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"77\","
				+ "			\"VALEUR_CARACT\": \"1\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"78\","
				+ "			\"VALEUR_CARACT\": \"3\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"11\","
				+ "			\"VALEUR_CARACT\": \"4\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"119\","
				+ "			\"VALEUR_CARACT\": \"0\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"2\","
				+ "			\"VALEUR_CARACT\": \"4.000\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},		"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"144\","
				+ "			\"VALEUR_CARACT\": \"100.000\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"145\","
				+ "			\"VALEUR_CARACT\": \"200.000\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"141\","
				+ "			\"VALEUR_CARACT\": \"1\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},		"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"100\","
				+ "			\"VALEUR_CARACT\": \"4\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_CARACTERISTIQUE\": \"120\","
				+ "			\"VALEUR_CARACT\": \"0\","
				+ "			\"VALEUR_DEPENDE_CARACT\": \"0\""
				+ "		}"
				+ "	],"
				+ "	\"Garanties\": ["
				+ "		{"
				+ "			\"CODE_GARANTIE\": \"1\","
				+ "			\"LIBELLE_GARANTIE\": \"Epargne bourse Mensuelle\","
				+ "			\"CAPITAL_ASSUREE\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_GARANTIE\": \"2\","
				+ "			\"LIBELLE_GARANTIE\": \"Prise en charge prime Bourse Mensuelle\","
				+ "			\"CAPITAL_ASSUREE\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_GARANTIE\": \"3\","
				+ "			\"LIBELLE_GARANTIE\": \"frais Universitaires\","
				+ "			\"CAPITAL_ASSUREE\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_GARANTIE\": \"4\","
				+ "			\"LIBELLE_GARANTIE\": \"Prise en charge prime Frais Universitaires\","
				+ "			\"CAPITAL_ASSUREE\": \"0\""
				+ "		},"
				+ "		{"
				+ "			\"CODE_GARANTIE\": \"5\","
				+ "			\"LIBELLE_GARANTIE\": \"Garantie Rente Mensuelle en Cas de Décès de l'Assuré\","
				+ "			\"CAPITAL_ASSUREE\": \"0\""
				+ "		}		"
				+ "	]"
				+ "}";
		
		
		try {

			System.out.println("valu "+input);
			
			Form f = new Form();
			f.add("DataForCalculPrime", input);
			f.add("saveProposition", false);
			
			

			System.out.println("url_ws " + Constante.URL_WS_CalculerPrimeDevisVie_STB);
	        
	        ClientResponse response = JerseyClient.PostReponseDuClientEDI(Constante.URL_WS_CalculerPrimeDevisVie_STB, f, reportController);


	        if (response == null) {
//	          throw new RuntimeException("Failed : HTTP error code : "
//	               + response.getStatus());
//	        	adhesionValide=false;
	        	setDesabledEncaiss(true); 
	      	 FacesMessage msg = new FacesMessage("ERREUR", "Auccune réponse au WS de Tarification!");
	    		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
	    		  
	    		  return;
	      }
	        
	        
	        
	        if (response.getStatus() != 200) {
//	        	adhesionValide=false;
//	            throw new RuntimeException("Failed : HTTP error code : "
//	                 + response.getStatus());
	        	setDesabledEncaiss(true); 
	        	FacesMessage msg = new FacesMessage("ERREUR", "Erreur connexion au WS de Tarification " + "(erreur: "+response.getStatus() + " )");
	  		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	  		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
	  		  
	  		return;
	  		  
	        }

	        System.out.println("Output from Server .... \n");
	        //String output = response.getEntity(String.class);
	        String output = response.toString();
	        System.out.println(output);
	        
	        System.out.print(response + " " + "\n");
			
	        net.sf.json.JSONObject jsonOutput = (net.sf.json.JSONObject) JSONSerializer.toJSON( response.getEntity(String.class) ); 
			 
	        int code = jsonOutput.getInt("code");
	        String message = " "+jsonOutput.getString("message");
	        
	        System.out.println("code "+code);
	        
	        if(code != 200) {
	        	
	        	setDesabledEncaiss(true); 
	        	FacesMessage msg = new FacesMessage("Echec de calcul du tarif (problème WS Tarification) ", message);
		  		  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		  		  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		  		  
	        	return;
	        }
	        
	        setDesabledEncaiss(false); 
	        
	        double PRIME = new BigDecimal(jsonOutput.getString("PRIME")).doubleValue();
	        
	        double PRIME_HORS_TAXE = new BigDecimal(jsonOutput.getString("PRIME_HORS_TAXE")).doubleValue();
	        
	        fraisContrat = new BigDecimal(jsonOutput.getString("TAXE")).doubleValue();
			

			 
			 // 1. details garanties
			 
			 JSONArray detailsGarantieListJSON = jsonOutput.getJSONArray("DetailsGarantie");
			  detailsGaranties = new ArrayList<>();
			  
			  detailsGarantieAVJs = new ArrayList<>();
				
				
			 
			 List<net.sf.json.JSONObject> detailsGarantiesJ = new ArrayList<>();
			 		 		 
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
				  DetailsGarantieAVJ detailsGarantieAVJ = new DetailsGarantieAVJ();
				  
				  detailsGarantieAVJ.setCode(Integer.parseInt(detailsGarantieListJSON.getJSONObject(i).getString("CODE_GARANTIE")));
				  detailsGarantieAVJ.setCapital(new BigDecimal(detailsGarantieListJSON.getJSONObject(i).getString("CAPITAL_ASSUREE")));
				  detailsGarantieAVJ.setPrime(new BigDecimal(detailsGarantieListJSON.getJSONObject(i).getString("PRIME_COMPTANT")));
				  detailsGarantieAVJ.setLibelle(detailsGarantieListJSON.getJSONObject(i).getString("LIBELLE_GARANTIE"));
				  detailsGarantieAVJ.setTaux(new BigDecimal(detailsGarantieListJSON.getJSONObject(i).getString("TAUX_REDUCTION")));
				 
				  detailsGarantieAVJs.add(detailsGarantieAVJ);
				  
				  switch(detailsGarantie.getCode()) {
				  
				  // garatie de base
				  case "1": basePrimeNetteEpargne = detailsGarantie.getPrime().doubleValue();
					  break;
					  
				  case "2": Prime_Nette_Deces_base = detailsGarantie.getPrime().doubleValue();
				  break;
				  
				  // frais d'inscription
				  case "3": prime_Nette_Epargne_Inscription = detailsGarantie.getPrime().doubleValue();
				  break;
				  
				  case "4": prime_Nette_Deces_Inscription = detailsGarantie.getPrime().doubleValue();
				  break;
				  
				  // rente décès
				  case "5": prime_Nette_Deces_Rente = detailsGarantie.getPrime().doubleValue();
				  break;
				  
				  }
				  
				  // total garatie de base
				  total_base = Prime_Nette_Deces_base + basePrimeNetteEpargne;
				  
				  // total frais d'inscription
				  total_inscription = prime_Nette_Deces_Inscription + prime_Nette_Epargne_Inscription;
				  
				  // total rente décès
				  total_rente = prime_Nette_Deces_Rente;
				  
				  // prime total
				  total_nette = PRIME_HORS_TAXE;
				  //total_nette = total_base + total_inscription + total_rente;
				  prime_totale = PRIME;
				  prime_totale_with_surprime_for_affichage = prime_totale;
				  //prime_totale= total_nette + fraisContrat;
			  }
			  
			  ////Prime annulelle
			  
			  /*
				switch(fractionnement) {
				
				case 1 : total_nette_annuelle = total_nette * 12;
				break;
				
				case 2 : total_nette_annuelle = total_nette * 4;
				break;
				
				case 3 : total_nette_annuelle = total_nette * 2;
				break;
				
				case 4 : total_nette_annuelle = total_nette;
				break;
				
				default : total_nette_annuelle = total_nette;
				break;
				
				}
				
				*/
			  
			  

			 if(prime_totale == 0.0) {
				 
				 setDesabledEncaiss(true); 
				 FacesMessage msg = new FacesMessage("Erreur WS Tarification!", "La prime est nulle, veuillez réessayer! ");
				  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				 return;
			 }
			 
			 // pour 3.medicaux
			 double prime_medical = 0;
			 
			 
			 // 2.Details Evolution Garantie   
			  
			  JSONArray DetailsEvolutionGarantieListJSON = jsonOutput.getJSONArray("DetailsEvolutionGarantie");
			  detailsEvolutionGarantiesDTO = new ArrayList<>();
			  detailsEvolutionGarantiesAVJ = new ArrayList<>();
			  
			  List<net.sf.json.JSONObject> DetailsEvolutionGarantieJ = new ArrayList<>();
			  
			  for (int i = 0; i < DetailsEvolutionGarantieListJSON.size(); i++) {
				  DetailsEvolutionGarantieJ.add(DetailsEvolutionGarantieListJSON.getJSONObject(i));
				  
				  DetailsEvolutionGarantieAvjDTO detailsEvolutionGarantie = new DetailsEvolutionGarantieAvjDTO();
				  
				  detailsEvolutionGarantie.setAnnee(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Année")));
				  detailsEvolutionGarantie.setCumul_vers(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Cumul Versements")));
				  detailsEvolutionGarantie.setPrime_epar_bourse(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Prime épargnée Bourse")));
				  detailsEvolutionGarantie.setPrime_epar_frais(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Prime épargné frais")));
				  detailsEvolutionGarantie.setEpar_cons_bourse(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne constituée Bourse (4%)")));
				  detailsEvolutionGarantie.setParticip_benef_bourse(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Participation aux Bénéfices  Bourse")));
				  detailsEvolutionGarantie.setEpar_cons_fannee_bourse(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne prévisionnelle Bourse avec PB")));
				  detailsEvolutionGarantie.setEpar_cons_frais(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne constituée Frais (4%)")));
				  detailsEvolutionGarantie.setParticip_benef_frais(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Participation aux Bénéfices Frais")));
				  detailsEvolutionGarantie.setEpar_cons_fannee_frais(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne prévisionnelle Frais avec PB")));
				  detailsEvolutionGarantie.setAvantage_financier(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Avantage financier")));
				  detailsEvolutionGarantie.setAvantage_fisc_annuel(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Avantage fiscal Annuel")));
				  
					
				 
				  detailsEvolutionGarantiesDTO.add(detailsEvolutionGarantie);
				  
				  
				////Prime annulelle  depuis Details Evolution Garantie
				  if(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Année")).intValue() == 1) {
					  
					  total_nette_annuelle = new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Cumul Versements")).doubleValue();
				  }
				  
				// pour 3.medicaux
				  if(i ==DetailsEvolutionGarantieListJSON.size()-1) {
					  
					  prime_medical = new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne constituée Bourse (4%)")).doubleValue();
				  }
				  
				  //cas insertion bd
				  
				  
				  
				  DetailsEvolutionGarantieAVJ detailsEvolutionGarantieAVJ = new DetailsEvolutionGarantieAVJ();
				  
				  detailsEvolutionGarantieAVJ.setAnnee(Integer.parseInt(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Année")));
				  detailsEvolutionGarantieAVJ.setCumul_vers(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Cumul Versements")));
				  detailsEvolutionGarantieAVJ.setPrime_epar_bourse(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Prime épargnée Bourse")));
				  detailsEvolutionGarantieAVJ.setPrime_epar_frais(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Prime épargné frais")));
				  detailsEvolutionGarantieAVJ.setEpar_cons_bourse(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne constituée Bourse (4%)")));
				  detailsEvolutionGarantieAVJ.setParticip_benef_bourse(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Participation aux Bénéfices  Bourse")));
				  detailsEvolutionGarantieAVJ.setEpar_cons_fannee_bourse(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne prévisionnelle Bourse avec PB")));
				  detailsEvolutionGarantieAVJ.setEpar_cons_frais(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne constituée Frais (4%)")));
				  detailsEvolutionGarantieAVJ.setParticip_benef_frais(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Participation aux Bénéfices Frais")));
				  detailsEvolutionGarantieAVJ.setEpar_cons_fannee_frais(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Epargne prévisionnelle Frais avec PB")));
				  detailsEvolutionGarantieAVJ.setAvantage_financier(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Avantage financier")));
				  detailsEvolutionGarantieAVJ.setAvantage_fisc_annuel(new BigDecimal(DetailsEvolutionGarantieListJSON.getJSONObject(i).getString("Avantage fiscal Annuel")));
				  
				  detailsEvolutionGarantiesAVJ.add(detailsEvolutionGarantieAVJ);
				  
			  }
			  
			  
			  //3. medicaux
			  medicals = new ArrayList<>();
			  
			  medicals = rapportMedical.getMedicalList( prime_medical,  ageAssure());
			  

			  
			   
			  System.out.println(jsonOutput);
			  
			 System.out.println(jsonOutput.toString());
			  
			 System.out.println(jsonOutput.getString("message"));

	      } catch (Exception e) {

	        e.printStackTrace();
//	        adhesionValide=false;
	        setDesabledEncaiss(true); 
	        
	        FacesMessage msg = new FacesMessage("ERREUR", "Erreur interne liée au WS de Tarification! ");
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage("Erreur", msg);

	      }
		
		
		///// autres calcul


			getDatePremiereBourse();
			//module1=new Module1(ageEnfant());

			double bourseMensuelle = this.bourseMensuelle;
			System.out.println("bourseMensuelle "+bourseMensuelle);

			double inscriptionAnnuelle = fraisInscriptionAnnuel;
			System.out.println("fraisInscriptionAnnuel "+fraisInscriptionAnnuel);

			//    	int ageServiceBourse = selectedAgeServiceBourse.getAgeServiceBourse().intValue();
			int ageFinServiceBourse = ageServiceBourse() + selectedDureServiceBourse.getDure().intValue();
			double tauxCroissanceBourse = 0.0;  // a voir
			
			fraisGest=(double) produit.getFraisGestion().doubleValue() / 100 ;

			/**** a été remplacé par :  *****/
			//capitalConstitutifGarentieBase = module1Impl.cb(bourseMensuelle, inscriptionAnnuelle, ageServiceBourse(), ageFinServiceBourse, tauxCroissanceBourse,ageEnfant()); // = 3415.561
			
			tauxTechnique = module1Impl.tmg(ageServiceBourse(),ageEnfant());//(double) selectedTauxInteret / 100;//module1.tmg(ageServiceBourse); // =0.035

			capitalConstitutifGarentieBase = module1Impl.CapitalCb_Unit01(ageServiceBourse(), selectedDureServiceBourse.getDure().intValue(), bourseMensuelle, tauxTechnique, tauxCroissanceBourse);
			System.out.println("capitalConstitutifGarentieBase ici "+capitalConstitutifGarentieBase);
			/***************************************/
			
			
			//    	int fractionnPrime =fractionnementDAO.find(fractionnement).getNombre_Fraction();//Integer.valueOf(getFractionnementConverter().toString()); // =2  Fractionnement : 
			System.out.println("fractionnPrime "+fractionnPrime());



			//    	double CroissPrime = (double) tauxCroissancePrimes / 100;// Integer.valueOf(getFractionnementConverter().toString());  //Taux evolution Prime

			//02/09/2022 tauxCroissBourse = 0.0;

			//    	int ageAssure = (int) (anneDebutContrat() - anneNaissanceAss());

			double fraisAcqui =  FRAISACQUISITION ;//table produit where code produ = 691

			double fraisCommi = FRAISCOMMISSION;//	table TAUXCOMM	


			/******** a ete remplace **************/
//			basePrimeNetteEpargne = BasePrimeNetteEpargne( capitalConstitutifGarentieBase ,tauxTechnique , fractionnPrime() , ageServiceBourse() , ageEnfant() , CroissPrime() , tauxCroissBourse ,0 , ageAssure() 
//					,fraisAcqui, fraisCommi);
//			basePrimeNetteEpargne =module1Impl.PAC_Unit01(capitalConstitutifGarentieBase, CroissPrime(), ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, fractionnPrime(), 
//					fraisGest, FRAISACQUISITION, FRAISCOMMISSION)/fractionnPrime();
			/********************************************/

			System.out.println("champs "+capitalConstitutifGarentieBase+" "+ CroissPrime()+" "+ ageAssure()+" "+ ageEnfant()+" "+ ageServiceBourse()+" "+ tauxTechnique+" "+ fractionnPrime()+" "+ 
					fraisGest+" "+FRAISACQUISITION+" "+ FRAISCOMMISSION);
			System.out.println("basePrimeNetteEpargne "+basePrimeNetteEpargne);

			PAP_BaseEpargne = module1Impl.pe0(capitalConstitutifGarentieBase, tauxTechnique, fractionnPrime(), ageServiceBourse(), ageEnfant(), CroissPrime(), tauxCroissBourse);


            /******** a ete remplace **************/
//			Prime_Nette_Deces_base=module1Impl.pppAcomm(ageAssure(), (int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_BaseEpargne, 0, fraisAcqui, fraisCommi);
//			Prime_Nette_Deces_base=module1Impl.PAC_Unit02(capitalConstitutifGarentieBase, CroissPrime(), ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, fractionnPrime(), 
//					fraisGest, FRAISACQUISITION, FRAISCOMMISSION, PartiEntier(dureeContract))/fractionnPrime();
			/****************************/
			
			
			/*** calcul prime épargne pour Garantie Frais de rentrée universaitaire Annuelle *****/
			
			/****** a été remplacé ici par *****************/
			//capitalConstitutifInscription = module1Impl.ci(bourseMensuelle, inscriptionAnnuelle, ageServiceBourse(), ageFinServiceBourse, tauxCroissBourse,ageEnfant());
			capitalConstitutifInscription = module1Impl.CapitalCb_Unit03(fraisInscriptionAnnuel, selectedDureServiceBourse.getDure().intValue(), tauxTechnique);
			System.out.println("capitalConsti_GarantieA ci "+capitalConstitutifInscription);
			/*****************************************************/

//			prime_Nette_Epargne_Inscription = BasePrimeNetteEpargne( capitalConstitutifInscription ,tauxTechnique , fractionnPrime() , ageServiceBourse() , ageEnfant() , CroissPrime() , tauxCroissBourse ,0 , ageAssure() 
//					,fraisAcqui, fraisCommi);
//			prime_Nette_Epargne_Inscription = module1Impl.PAC_Unit03(capitalConstitutifInscription, CroissPrime(), ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, fractionnPrime(), 
//					fraisGest, FRAISACQUISITION, FRAISCOMMISSION)/fractionnPrime();
			System.out.println("prime_Nette_Epargne_Inscription "+prime_Nette_Epargne_Inscription);


			/*** calcul prime décès pour Garantie Frais de rentrée universaitaire Annuelle *****/
			PAP_InscriptionEpargne = module1Impl.pe0(capitalConstitutifInscription, tauxTechnique, fractionnPrime(), ageServiceBourse(), ageEnfant(), CroissPrime(), tauxCroissBourse);

			/***** a ete remplace ***********/
//			prime_Nette_Deces_Inscription = module1Impl.pppAcomm(ageAssure(), (int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_InscriptionEpargne, 0, fraisAcqui, fraisCommi);

//			prime_Nette_Deces_Inscription = module1Impl.PAC_Unit04(capitalConstitutifInscription, CroissPrime(), ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, fractionnPrime(), 
//					fraisGest, FRAISACQUISITION, FRAISCOMMISSION, PartiEntier(dureeContract))/fractionnPrime();
			/***********************************/
			
			
			
			
			/*** calcul prime décès pour Garantie Rente *****/
			/****** a ete remplace **************/
//			prime_Nette_Deces_Rente = module1Impl.pppBcomme(ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, CroissPrime(), renteMonsuelleEnCasDeces, fractionnPrime(), 0, fraisAcqui, fraisCommi);

//			prime_Nette_Deces_Rente = module1Impl.PAC_Unit05(renteMonsuelleEnCasDeces, CroissPrime(), ageAssure(), ageEnfant(), ageServiceBourse(), tauxTechnique, fractionnPrime(), 
//					fraisGest, FRAISACQUISITION, FRAISCOMMISSION, PartiEntier(dureeContract))/fractionnPrime();
            /**************************************/
			
			
//			total_base = Prime_Nette_Deces_base + basePrimeNetteEpargne;
//
//			total_inscription = prime_Nette_Deces_Inscription + prime_Nette_Epargne_Inscription;
//
//			total_rente = prime_Nette_Deces_Rente;
//
//			total_nette = total_base + total_inscription + total_rente;

			fraisGestion= produit.getFraisGestion().doubleValue() ;

			 //fraisGest=(double) produit.getFraisGestion().doubleValue() / 100 ;

//			prime_totale= total_nette + fraisContrat;
			System.out.println("total_nette="+total_nette);
			System.out.println("fraisContrat="+fraisContrat);
			System.out.println("prime_totale="+prime_totale);

		
		
	}


	/****** calcul *********************/



	public double BasePrimeNetteEpargne(double capitalConsti,double tauxTechnique,int fractionnPrime,int ageServiceBourse,
			int ageEnfant, double CroissPrime,double tauxCroissBourse,int zero,int ageAssure,double fraisAcqui, double fraisCommi ){

		double primenette = module1Impl.pekcomme( capitalConsti, tauxTechnique, fractionnPrime, ageServiceBourse,
				ageEnfant,  CroissPrime, tauxCroissBourse, zero, ageAssure, fraisAcqui,  fraisCommi);

		System.out.println("primenette "+primenette);

		return primenette;

	}







	public int ageFinServiceBourse(Double ageServiceBourse,Double dureBourse){

		return ageServiceBourse.intValue()+dureBourse.intValue();
	}


	public double getCapitalConstitutifGarentieBase() {
		return capitalConstitutifGarentieBase;
	}



	public void setCapitalConstitutifGarentieBase(
			double capitalConstitutifGarentieBase) {
		this.capitalConstitutifGarentieBase = capitalConstitutifGarentieBase;
	}



	public double getCapitalConstitutifInscription() {
		return capitalConstitutifInscription;
	}



	public void setCapitalConstitutifInscription(
			double capitalConstitutifInscription) {
		this.capitalConstitutifInscription = capitalConstitutifInscription;
	}



	public double getCapitalConstitutifRenteEnCasDece() {
		return capitalConstitutifRenteEnCasDece;
	}



	public void setCapitalConstitutifRenteEnCasDece(
			double capitalConstitutifRenteEnCasDece) {
		this.capitalConstitutifRenteEnCasDece = capitalConstitutifRenteEnCasDece;
	}


	public boolean isExistFraisInscriptionAnnuel() {
		return existFraisInscriptionAnnuel;
	}



	public void setExistFraisInscriptionAnnuel(boolean existFraisInscriptionAnnuel) {
		this.existFraisInscriptionAnnuel = existFraisInscriptionAnnuel;
	}



	public boolean isExistrenteMonsuelleEnCasDeces() {
		return existrenteMonsuelleEnCasDeces;
	}
	public void updateChekedMonsuelleEnCasDeces(){
		existrenteMonsuelleEnCasDeces=!existrenteMonsuelleEnCasDeces;
		System.out.println("existrenteMonsuelleEnCasDeces="+existrenteMonsuelleEnCasDeces);


	}
	public void updateChekedFraisInscriptionAnnuel(){

		existFraisInscriptionAnnuel=!existFraisInscriptionAnnuel;
		System.out.println("existFraisInscriptionAnnuel="+existFraisInscriptionAnnuel);
	}

	public void setExistrenteMonsuelleEnCasDeces(
			boolean existrenteMonsuelleEnCasDeces) {
		this.existrenteMonsuelleEnCasDeces = existrenteMonsuelleEnCasDeces;
	}



	public AgeServiceBourse getSelectedAgeServiceBourse() {

		return selectedAgeServiceBourse;
	}



	public void setSelectedAgeServiceBourse(
			AgeServiceBourse selectedAgeServiceBourse) {
		this.selectedAgeServiceBourse = selectedAgeServiceBourse;
	}



	public DureServiceBourse getSelectedDureServiceBourse() {
		return selectedDureServiceBourse;
	}



	public void setSelectedDureServiceBourse(
			DureServiceBourse selectedDureServiceBourse) {
		this.selectedDureServiceBourse = selectedDureServiceBourse;
	}



	public double getTauxCroissanceBource() {
		return tauxCroissanceBource;
	}



	public void setTauxCroissanceBource(double tauxCroissanceBource) {
		this.tauxCroissanceBource = tauxCroissanceBource;
	}



	public long getDureeContract() {
		return dureeContract;
	}



	public void setDureeContract(long dureeContract) {
		this.dureeContract = dureeContract;
	}



	public double getTauxTechnique() {
		return tauxTechnique;
	}
	public void setTauxTechnique(double tauxTechnique) {
		this.tauxTechnique = tauxTechnique;
	}
	public double getSelectedTauxCroissancePrime() {
		return selectedTauxCroissancePrime;
	}
	public void setSelectedTauxCroissancePrime(double selectedTauxCroissancePrime) {
		this.selectedTauxCroissancePrime = selectedTauxCroissancePrime;
	}
	public double getSelectedTauxInteret() {
		return selectedTauxInteret;
	}
	public void setSelectedTauxInteret(double selectedTauxInteret) {
		this.selectedTauxInteret = selectedTauxInteret;
	}
	public TauxInteret getCurrentTauxInteret() {
		return currentTauxInteret;
	}
	public void setCurrentTauxInteret(TauxInteret currentTauxInteret) {
		this.currentTauxInteret = currentTauxInteret;
	}
	public Double getFraisInscriptionAnnuel() {
		return fraisInscriptionAnnuel;
	}
	public void setFraisInscriptionAnnuel(Double fraisInscriptionAnnuel) {
		this.fraisInscriptionAnnuel = fraisInscriptionAnnuel;
	}
	public Double getRenteMonsuelleEnCasDeces() {
		return renteMonsuelleEnCasDeces;
	}
	public void setRenteMonsuelleEnCasDeces(Double renteMonsuelleEnCasDeces) {
		this.renteMonsuelleEnCasDeces = renteMonsuelleEnCasDeces;
	}
	public Double getBourseMensuelle() {
		return bourseMensuelle;
	}
	public void setBourseMensuelle(Double bourseMensuelle) {
		this.bourseMensuelle = bourseMensuelle;
	}
	public List<ProfessionClient> getAllProfessions() {
		return allProfessions;
	}

	public void setAllProfessions(List<ProfessionClient> allProfessions) {
		this.allProfessions = allProfessions;
	}

	public Adherent getNewAdherent() {
		return newAdherent;
	}

	public void setNewAdherent(Adherent newAdherent) {
		this.newAdherent = newAdherent;
	}

	public Beneficiaire getNewBeneficiaire() {
		return newBeneficiaire;
	}

	public void setNewBeneficiaire(Beneficiaire newBeneficiaire) {
		this.newBeneficiaire = newBeneficiaire;
	}

	public List<AgeServiceBourse> getAllAgeServiceBourses() {
		return allAgeServiceBourses;
	}

	public void setAllAgeServiceBourses(List<AgeServiceBourse> allAgeServiceBourses) {
		this.allAgeServiceBourses = allAgeServiceBourses;
	}

	public List<Fractionnement> getAllFractionnements() {
		return allFractionnements;
	}
	public void setAllFractionnements(List<Fractionnement> allFractionnements) {
		this.allFractionnements = allFractionnements;
	}
	public List<DureServiceBourse> getAllDureServiceBourses() {
		return allDureServiceBourses;
	}
	public void setAllDureServiceBourses(
			List<DureServiceBourse> allDureServiceBourses) {
		this.allDureServiceBourses = allDureServiceBourses;
	}
	public List<TauxCroissancePrime> getAllTauxCroissancePrimes() {
		return allTauxCroissancePrimes;
	}
	public void setAllTauxCroissancePrimes(
			List<TauxCroissancePrime> allTauxCroissancePrimes) {
		this.allTauxCroissancePrimes = allTauxCroissancePrimes;
	}
	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}
	public ClientBean getClientBean() {
		return clientBean;
	}
	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}
	public Adhesion getNewAdhesion() {
		return newAdhesion;
	}
	public void setNewAdhesion(Adhesion newAdhesion) {
		this.newAdhesion = newAdhesion;
	}
	public boolean isDesabledGenerateNotice() {
		return desabledGenerateNotice;
	}
	public void setDesabledGenerateNotice(boolean desabledGenerateNotice) {
		this.desabledGenerateNotice = desabledGenerateNotice;
	}
	public void initialisationClientAndAdhesion(){

		newAdhesion = new Adhesion();
		newAdherent=new Adherent();
		newBeneficiaire=new Beneficiaire();
		newAdhesionToPrint=new Adhesion();
		clientBean.setClient(new Client());
		newProposition = null;
		desabledGenerateNotice=true;
		desabledEncaiss = false;
		
		detailsGarantieAVJs = new ArrayList<>();
		
		skip = false;
		
		
		/*** ws client ***/
		clientBean.setFromWS(false);
		clientBean.setNomWS(false);
		clientBean.setPrenomWS(false);
		clientBean.setDateNaisWS(false);
		clientBean.setCinWS(false);
		clientBean.setRibWS(false);
		
		/*** mode pai***/
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
	}

	private String[] selectedConsoles;
	private String numplc;

	private Map<String, Object> listnumplc=null;

	public String navigateToGarantiePage() {

		return "garantieAvenirJeunesse?faces-redirect=true";
	}
	public String[] getSelectedConsoles() {
		return selectedConsoles;
	}

	public void setSelectedConsoles(String[] selectedConsoles) {
		this.selectedConsoles = selectedConsoles;
	}
	public String getNumplc() {
		return numplc;
	}

	public void setNumplc(String numplc) {
		this.numplc = numplc;
	}

	public String navigateToClientPage() {
		if(clientBean.getClient().getGouver()==null){
			clientBean.getAllCodePostalByGouvernera().clear();
		}

		return "clientAvenirJeunesse?faces-redirect=true";
	}


	public String navigateToAvenirJeunesse(){
		initialisationClientAndAdhesion();
		init();
		return "devisAvenirJeunesse";
	}


	public long getAnnee(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}




	private long dureeContract;



	public void updateDateFin(){

		Calendar cal = Calendar.getInstance();
		cal.setTime(newAdhesion.getDatDebut());
		cal.add(Calendar.YEAR,	(int) dureeContract);
		cal.add(Calendar.DATE, -1);
		newAdhesion.setDatFin(cal.getTime());
	}

	public void updateTauxInteretActionListener(){

		System.out.println("debut updateTauxInteretActionListener");


		if(newBeneficiaire.getDateNaiss()!=null && selectedAgeServiceBourse!=null && newAdhesion.getDatDebut()!=null){
//			long age=anneDebutContrat()-anneNaissance();
			
			System.out.println("Date effet : "+newAdhesion.getDatDebut());
			
			System.out.println("Date naissance bénéficiaire : "+newBeneficiaire.getDateNaiss());
			
			int days360 = calculateDays360(newBeneficiaire.getDateNaiss(), newAdhesion.getDatDebut());
			int age = Math.round((float) days360 / 360);
			System.out.println("age : "+age);
//			System.out.println("anneDebutContrat : "+anneDebutContrat());
//			System.out.println("anneNaissance : "+anneNaissance());
			dureeContract=selectedAgeServiceBourse.getAgeServiceBourse().longValue()-age;
			//05/09/2022 selectedTauxInteret= (dureeContract<=10)?currentTauxInteret.getDureeInf10ans().doubleValue():currentTauxInteret.getDureeSup10ans().doubleValue();
			selectedTauxInteret = 4.0;
			updateDateFin();
			controlemaxageAssure();
		}

	}
	
	
	


	public double getDtranche() {
		return dtranche;
	}
	public void setDtranche(double dtranche) {
		this.dtranche = dtranche;
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
	public double getGarentieBasePrimeEpargnePAP() {
		return garentieBasePrimeEpargnePAP;
	}



	public void setGarentieBasePrimeEpargnePAP(double garentieBasePrimeEpargnePAP) {
		this.garentieBasePrimeEpargnePAP = garentieBasePrimeEpargnePAP;
	}



	public double getGarentieBasePrimeEpargnePPP() {
		return garentieBasePrimeEpargnePPP;
	}



	public void setGarentieBasePrimeEpargnePPP(double garentieBasePrimeEpargnePPP) {
		this.garentieBasePrimeEpargnePPP = garentieBasePrimeEpargnePPP;
	}



	public double getGarentieBasePrimeEpargnePPCOM() {
		return garentieBasePrimeEpargnePPCOM;
	}



	public void setGarentieBasePrimeEpargnePPCOM(
			double garentieBasePrimeEpargnePPCOM) {
		this.garentieBasePrimeEpargnePPCOM = garentieBasePrimeEpargnePPCOM;
	}



	public double getGarentieBasePrimedecesPAP() {
		return garentieBasePrimedecesPAP;
	}



	public void setGarentieBasePrimedecesPAP(double garentieBasePrimedecesPAP) {
		this.garentieBasePrimedecesPAP = garentieBasePrimedecesPAP;
	}



	public double getGarentieBasePrimedecesPPP() {
		return garentieBasePrimedecesPPP;
	}



	public void setGarentieBasePrimedecesPPP(double garentieBasePrimedecesPPP) {
		this.garentieBasePrimedecesPPP = garentieBasePrimedecesPPP;
	}



	public double getGarentieBasePrimedecesPUP() {
		return garentieBasePrimedecesPUP;
	}



	public void setGarentieBasePrimedecesPUP(double garentieBasePrimedecesPUP) {
		this.garentieBasePrimedecesPUP = garentieBasePrimedecesPUP;
	}



	public double getGarentieBasePrimedecesPPCOM() {
		return garentieBasePrimedecesPPCOM;
	}



	public void setGarentieBasePrimedecesPPCOM(double garentieBasePrimedecesPPCOM) {
		this.garentieBasePrimedecesPPCOM = garentieBasePrimedecesPPCOM;
	}



	public double getGarentieBaseToalPAP() {
		return garentieBaseToalPAP;
	}



	public void setGarentieBaseToalPAP(double garentieBaseToalPAP) {
		this.garentieBaseToalPAP = garentieBaseToalPAP;
	}



	public double getGarentieBaseToalPPP() {
		return garentieBaseToalPPP;
	}



	public void setGarentieBaseToalPPP(double garentieBaseToalPPP) {
		this.garentieBaseToalPPP = garentieBaseToalPPP;
	}



	public double getGarentieBaseToalPPCOM() {
		return garentieBaseToalPPCOM;
	}



	public void setGarentieBaseToalPPCOM(double garentieBaseToalPPCOM) {
		this.garentieBaseToalPPCOM = garentieBaseToalPPCOM;
	}



	public double getGarentieInscriptionPrimeEpargnePAP() {
		return garentieInscriptionPrimeEpargnePAP;
	}



	public void setGarentieInscriptionPrimeEpargnePAP(
			double garentieInscriptionPrimeEpargnePAP) {
		this.garentieInscriptionPrimeEpargnePAP = garentieInscriptionPrimeEpargnePAP;
	}



	public double getGarentieInscriptionPrimeEpargnePPP() {
		return garentieInscriptionPrimeEpargnePPP;
	}



	public void setGarentieInscriptionPrimeEpargnePPP(
			double garentieInscriptionPrimeEpargnePPP) {
		this.garentieInscriptionPrimeEpargnePPP = garentieInscriptionPrimeEpargnePPP;
	}



	public double getGarentieInscriptionPrimeEpargnePPCOM() {
		return garentieInscriptionPrimeEpargnePPCOM;
	}



	public void setGarentieInscriptionPrimeEpargnePPCOM(
			double garentieInscriptionPrimeEpargnePPCOM) {
		this.garentieInscriptionPrimeEpargnePPCOM = garentieInscriptionPrimeEpargnePPCOM;
	}



	public double getGarentieInscriptionPrimedecesPAP() {
		return garentieInscriptionPrimedecesPAP;
	}



	public void setGarentieInscriptionPrimedecesPAP(
			double garentieInscriptionPrimedecesPAP) {
		this.garentieInscriptionPrimedecesPAP = garentieInscriptionPrimedecesPAP;
	}



	public double getGarentieInscriptionPrimedecesPPP() {
		return garentieInscriptionPrimedecesPPP;
	}



	public void setGarentieInscriptionPrimedecesPPP(
			double garentieInscriptionPrimedecesPPP) {
		this.garentieInscriptionPrimedecesPPP = garentieInscriptionPrimedecesPPP;
	}



	public double getGarentieInscriptionPrimedecesPUP() {
		return garentieInscriptionPrimedecesPUP;
	}



	public void setGarentieInscriptionPrimedecesPUP(
			double garentieInscriptionPrimedecesPUP) {
		this.garentieInscriptionPrimedecesPUP = garentieInscriptionPrimedecesPUP;
	}



	public double getGarentieInscriptionPrimedecesPPCOM() {
		return garentieInscriptionPrimedecesPPCOM;
	}



	public void setGarentieInscriptionPrimedecesPPCOM(
			double garentieInscriptionPrimedecesPPCOM) {
		this.garentieInscriptionPrimedecesPPCOM = garentieInscriptionPrimedecesPPCOM;
	}



	public double getGarentieInscriptionToalPAP() {
		return garentieInscriptionToalPAP;
	}



	public void setGarentieInscriptionToalPAP(double garentieInscriptionToalPAP) {
		this.garentieInscriptionToalPAP = garentieInscriptionToalPAP;
	}



	public double getGarentieInscriptionToalPPP() {
		return garentieInscriptionToalPPP;
	}



	public void setGarentieInscriptionToalPPP(double garentieInscriptionToalPPP) {
		this.garentieInscriptionToalPPP = garentieInscriptionToalPPP;
	}



	public double getGarentieInscriptionToalPPCOM() {
		return garentieInscriptionToalPPCOM;
	}



	public void setGarentieInscriptionToalPPCOM(double garentieInscriptionToalPPCOM) {
		this.garentieInscriptionToalPPCOM = garentieInscriptionToalPPCOM;
	}



	public double getGarentieRenteEnCasDecePrimedecesPAP() {
		return garentieRenteEnCasDecePrimedecesPAP;
	}



	public void setGarentieRenteEnCasDecePrimedecesPAP(
			double garentieRenteEnCasDecePrimedecesPAP) {
		this.garentieRenteEnCasDecePrimedecesPAP = garentieRenteEnCasDecePrimedecesPAP;
	}



	public double getGarentieRenteEnCasDecePrimedecesPPP() {
		return garentieRenteEnCasDecePrimedecesPPP;
	}



	public void setGarentieRenteEnCasDecePrimedecesPPP(
			double garentieRenteEnCasDecePrimedecesPPP) {
		this.garentieRenteEnCasDecePrimedecesPPP = garentieRenteEnCasDecePrimedecesPPP;
	}



	public double getGarentieRenteEnCasDecePrimedecesPUP() {
		return garentieRenteEnCasDecePrimedecesPUP;
	}



	public void setGarentieRenteEnCasDecePrimedecesPUP(
			double garentieRenteEnCasDecePrimedecesPUP) {
		this.garentieRenteEnCasDecePrimedecesPUP = garentieRenteEnCasDecePrimedecesPUP;
	}



	public double getGarentieRenteEnCasDecePrimedecesPPCOM() {
		return garentieRenteEnCasDecePrimedecesPPCOM;
	}



	public void setGarentieRenteEnCasDecePrimedecesPPCOM(
			double garentieRenteEnCasDecePrimedecesPPCOM) {
		this.garentieRenteEnCasDecePrimedecesPPCOM = garentieRenteEnCasDecePrimedecesPPCOM;
	}



	public double getGarentieRenteEnCasDeceToalPAP() {
		return garentieRenteEnCasDeceToalPAP;
	}



	public void setGarentieRenteEnCasDeceToalPAP(
			double garentieRenteEnCasDeceToalPAP) {
		this.garentieRenteEnCasDeceToalPAP = garentieRenteEnCasDeceToalPAP;
	}



	public double getGarentieRenteEnCasDeceToalPPP() {
		return garentieRenteEnCasDeceToalPPP;
	}



	public void setGarentieRenteEnCasDeceToalPPP(
			double garentieRenteEnCasDeceToalPPP) {
		this.garentieRenteEnCasDeceToalPPP = garentieRenteEnCasDeceToalPPP;
	}



	public double getGarentieRenteEnCasDeceToalPPCOM() {
		return garentieRenteEnCasDeceToalPPCOM;
	}



	public void setGarentieRenteEnCasDeceToalPPCOM(
			double garentieRenteEnCasDeceToalPPCOM) {
		this.garentieRenteEnCasDeceToalPPCOM = garentieRenteEnCasDeceToalPPCOM;
	}

	
	

	public List<DetailsEvolutionGarantieAvjDTO> getDetailsEvolutionGarantiesDTO() {
		return detailsEvolutionGarantiesDTO;
	}
	public void setDetailsEvolutionGarantiesDTO(List<DetailsEvolutionGarantieAvjDTO> detailsEvolutionGarantiesDTO) {
		this.detailsEvolutionGarantiesDTO = detailsEvolutionGarantiesDTO;
	}
	
	
	public Map<String, Object> getListnumplc() {

		return listnumplc;
	}
	
	public boolean isAffiche() {
		return affiche;
	}

	public void setAffiche(boolean affiche) {
		this.affiche = affiche;
	}
	
	
	 
	public List<Medical> getMedicals() {
		return medicals;
	}
	public void setMedicals(List<Medical> medicals) {
		this.medicals = medicals;
	}
	
	
 
	
	public Integer getJourPrelevement() {
		return jourPrelevement;
	}
	public void setJourPrelevement(Integer jourPrelevement) {
		this.jourPrelevement = jourPrelevement;
	}
	public Integer getMoisPrelevement() {
		return moisPrelevement;
	}
	public void setMoisPrelevement(Integer moisPrelevement) {
		this.moisPrelevement = moisPrelevement;
	}
	
	public List<SelectItem> getJoursDisponibles() {
	    return joursDisponibles;
	}
	
	
	public List<SelectItem> getMoisDisponibles() {
	    return moisDisponibles;
	}
	
	
	/*** fonction date premiere bourse*********************/
	public Date getDatePremiereBourse(){
		Calendar cal = Calendar.getInstance();
//		cal.setTime(newBeneficiaire.getDateNaiss());
//		cal.add(Calendar.YEAR, (int) dureeContract);
		cal.setTime(newAdhesion.getDatFin());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		datePremiereBourse = cal.getTime();	

		return datePremiereBourse;
	}
	public void setDatePremiereBourse(Date datePremiereBourse) {
		this.datePremiereBourse = datePremiereBourse;
	}

	public Map<String, Object> getlistnumplc(String cod_societ,Profil profil) {
		Map<String, Object> listnumplc = new LinkedHashMap<String, Object>();
		List<Police> list =null;
		if(profil.getCode().equals(PofileEnum.AST.name()) || profil.getCode().equals(PofileEnum.ASI.name()))
		{
			list= policeDAO.getPolice_by_prod(produitDAO.getProduit_by_code(ENUMCODEPRODUIT.AVENIRJENESSE.getCode()));
		}
		else{
			list = policeDAO.getPolice_by_conv_prod(conventionDAO.find_conv_by_Societ(societeDAO.find(cod_societ)),produitDAO.getProduit_by_code(ENUMCODEPRODUIT.AVENIRJENESSE.getCode()));
		}
		for (Police police : list) {
			listnumplc.put(police.getLibelle().trim(), police.getLibelle());
		}

		return listnumplc;
	}

	public void updatepolice() {
		if(utilisateur.getProfil().getCode().equals(PofileEnum.IST.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name()))
		{
			listnumplc = getlistnumplc(cod_societe,utilisateur.getProfil());
		}
		else
		{
			cod_societe = utilisateur.getAgence().getSociete().getCodSociet();
			listnumplc = getlistnumplc(cod_societe,utilisateur.getProfil());
		}
		numplc = (String) listnumplc.values().toArray()[0];

	}



	public Converter getFractionnementConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (Fractionnement obj : getAllFractionnements()) {
					if (obj.toString().equals(value)) {
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
		};}

	public Converter getDureServiceBourseConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (DureServiceBourse obj : getAllDureServiceBourses()) {
					if (obj.toString().equals(value)) {
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
	public Converter getTauxCroissancePrimesConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (TauxCroissancePrime obj : getAllTauxCroissancePrimes()) {
					if (obj.toString().equals(value)) {
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
	public Converter getAgeServiceBoursesConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (AgeServiceBourse obj : getAllAgeServiceBourses()) {
					if (obj.toString().equals(value)) {
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

	public Client getClient() {
		return clientBean.getClient();
	}

	public void setClient(Client client) {
		this.clientBean.setClient(client);
	}

	private boolean noneException = true;

	public Proposition newProposition;

	public Proposition getNewProposition() {
		return newProposition;
	}

	public void setNewProposition(Proposition newProposition) {
		this.newProposition = newProposition;
	}




	/************* bouton imprimer devis *************************/
	public void generateDevisAvenirJeunesse() {

		
			
		
		
		boolean error = false;

		controlemaxduree=75-ageAssure();
		System.out.println("controlemaxduree="+controlemaxduree);
		System.out.println("dureeContract="+dureeContract);
		if(dureeContract>controlemaxduree)
		{
			System.out.println("enter controle ");
			FacesMessage msg = new FacesMessage("ERREUR", "la somme durée du contrat et age de l'assuré doit être inférieur ou égal à 75 ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			FacesContext.getCurrentInstance().validationFailed();
			error = true;
		}

		if(!error){
		client = getClient();
		if (client.getIdentifiant() == null	|| client.getIdentifiant().isEmpty()) {
			noneException = false;
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} else {
			newAdhesion.setDuree(dureeContract);
			newAdhesion.setEtat("V");
			newAdhesion.setTauxInter(selectedTauxInteret);
			newAdhesion.setMtPrime(total_nette);
			newAdhesion.setFga((fraisContrat));
			newAdhesion.setPrimetot(prime_totale);
			newAdhesion.setFraPolice(fractionnementDAO.find(fractionnement).getLibelle().substring(0, 1) );
			newAdhesion.setPolice(libnumplc);

			GarantieAvj garantieAvjbaseEpargne= new GarantieAvj( new BigDecimal(capitalConstitutifGarentieBase),  new BigDecimal(bourseMensuelle), 
					new BigDecimal(basePrimeNetteEpargne), new BigDecimal(0), new BigDecimal(PAP_BaseEpargne), new BigDecimal(ppp_baseEpargne()), new BigDecimal(basePrimeNetteEpargne));
			GarantieAvj garantieAvjbaseDeces = new GarantieAvj(new BigDecimal(capitalConstitutifGarentieBase),  
					new BigDecimal(bourseMensuelle), new BigDecimal(Prime_Nette_Deces_base), new BigDecimal(pup_baseDeces()), new BigDecimal(pap_baseDeces()), new BigDecimal(ppp_basedeces()), new BigDecimal(Prime_Nette_Deces_base));
			GarantieAvj garantieAvjEpargneInscription = null;
			GarantieAvj garantieAvjDecesInscription = null;
			GarantieAvj garantieAvjDecesRente = null;

			if(fraisInscriptionAnnuel !=0){

				garantieAvjEpargneInscription = new GarantieAvj( new BigDecimal(capitalConstitutifInscription),  new BigDecimal(fraisInscriptionAnnuel),  new BigDecimal(prime_Nette_Epargne_Inscription), 
						new BigDecimal(0), new BigDecimal(PAP_InscriptionEpargne), new BigDecimal(ppp_InscriptionEpargne()), new BigDecimal(prime_Nette_Epargne_Inscription));
				System.out.println("capitalConstitutifInscription="+capitalConstitutifInscription);

				garantieAvjDecesInscription = new GarantieAvj(  new BigDecimal(capitalConstitutifInscription),   new BigDecimal(fraisInscriptionAnnuel),  new BigDecimal(prime_Nette_Deces_Inscription), 
						new BigDecimal(pup_DecesInscription()), new BigDecimal(pap_DecesInscription()), new BigDecimal(ppp_Inscriptiondeces()), new BigDecimal(prime_Nette_Deces_Inscription));
			}

			if(renteMonsuelleEnCasDeces !=0){

				garantieAvjDecesRente = new GarantieAvj( new BigDecimal(capitalConstitutifRenteEnCasDece),new BigDecimal(renteMonsuelleEnCasDeces), 
						new BigDecimal(prime_Nette_Deces_Rente), new BigDecimal(pup_rente()), new BigDecimal(pap_rente()), new BigDecimal(ppp_rente()), new BigDecimal(prime_Nette_Deces_Rente));
				System.out.println("capitalConstitutifRenteEnCasDece="+capitalConstitutifRenteEnCasDece);	
			}

			DetailAvj detailAvj = iParametresAvjService.create_detail_avj(BigDecimal.valueOf(bourseMensuelle), selectedAgeServiceBourse.getAgeServiceBourse(), 
					selectedDureServiceBourse.getDure(), datePremiereBourse,  BigDecimal.valueOf(tauxCroissancePrimes) ,
					BigDecimal.valueOf(tauxCroissBourse), 
					BigDecimal.valueOf(surprime),
					clientBean.isFromWS(),
					BigDecimal.valueOf(dtranche));
			newAdhesion.setDetailAvj(detailAvj);
			
			prime_totale_avec_surprime = surprime + prime_totale;
			prime_nette_avec_surprime = surprime + total_nette;

						if (newProposition == null) {

			newProposition = iParametresAvjService.saveProposition(newBeneficiaire,newAdherent,client, newAdhesion,garantieAvjbaseEpargne,garantieAvjbaseDeces,garantieAvjEpargneInscription,garantieAvjDecesInscription,garantieAvjDecesRente,
					fraisInscriptionAnnuel,	renteMonsuelleEnCasDeces, utilisateur, detailsEvolutionGarantiesAVJ,
					detailsGarantieAVJs);
			
			
			// on recupere la liste des DETAILS_GARANTIE_AVJ  avec id proposition	
			detailsGarantieAVJs = avenirJeunesseDAOInter.find_details_gar_by_Proposition(newProposition);
						
			// on recupere la liste des DetailsEvolutionGarantie  avec id proposition	
			detailsEvolutionGarantiesAVJ = avenirJeunesseDAOInter.find_details_ev_gar_by_Proposition(newProposition);
			/*
			List<DetailsEvolutionGarantieAVJ> detailsEG_AVJ = new ArrayList<>();
			 for(DetailsEvolutionGarantieAVJ detail : detailsEvolutionGarantiesAVJ) {
				 detail.setProposition(newProposition);
				 detailsEG_AVJ.add(detail);
								 
			 }	
			 
			 detailsEvolutionGarantiesAVJ = detailsEG_AVJ;
			 */
						
						}

			JsfUtil.addSuccessMessage(	"Proposition enregistrée avec le numéro: "	+ newProposition.getnProposition(), "!");
			imprimerDevis();
			noneException = true;
			FacesMessage msg = new FacesMessage("Succes"," Succes Insert In proposition and generate devis");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
		}
		}
		
		
	}

	public void validerPrposition_sans_encaissement()
	{
		boolean error = false;

		controlemaxduree=75-ageAssure();
		System.out.println("controlemaxduree="+controlemaxduree);
		System.out.println("dureeContract="+dureeContract);
		if(dureeContract>controlemaxduree)
		{
			System.out.println("enter controle ");
			FacesMessage msg = new FacesMessage("ERREUR", "la somme durée du contrat et age de l'assuré doit être inférieur ou égal à 75 ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			FacesContext.getCurrentInstance().validationFailed();
			error = true;
		}

		if(!error){
		client = getClient();

		if (client.getIdentifiant() == null || client.getIdentifiant().isEmpty()) {
			noneException=false;
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} else if(newAdherent==null || newAdherent.getIdentifiant().isEmpty()	|| newBeneficiaire==null || newBeneficiaire.getNom().isEmpty() || newBeneficiaire.getPrenom().isEmpty() ||newBeneficiaire.getDateNaiss()==null)
		{
			FacesMessage msg = new FacesMessage("ERREUR", "Veuillez saisir l'assuré et le beneficiaire");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		}
		else
		{
			if (client.getDatCreation() == null) {
				client.setDatCreation(new Date());
			}
			if(clientDAO.findByIdentifiant(client.getIdentifiant())!=null)
			{
				System.out.println("client existant");
				clientDAO.edit(client);
			}
			else
			{
				System.out.println("client inexistant");
				clientDAO.create(client);
			}
			if(newProposition!=null){
				newAdhesion.setProposition(newProposition); 
			}
			newAdhesion.setClient(client);
			newAdhesion.setDuree(dureeContract);
			newAdhesion.setEtat("V");
			newAdhesion.setTauxInter(selectedTauxInteret);
			newAdhesion.setMtPrime(total_nette);
			newAdhesion.setFga(fraisContrat);
			newAdhesion.setPrimetot(prime_totale);
			newAdhesion.setFraPolice(fractionnementDAO.find(fractionnement).getLibelle().substring(0, 1) );
			newAdhesion.setPolice(libnumplc);

			/************insertion de garanties et des details**********************/
			GarantieAvj garantieAvjbaseEpargne= new GarantieAvj( new BigDecimal(capitalConstitutifGarentieBase),  new BigDecimal(bourseMensuelle), 
					new BigDecimal(basePrimeNetteEpargne), new BigDecimal(0), new BigDecimal(PAP_BaseEpargne), new BigDecimal(ppp_baseEpargne()), new BigDecimal(basePrimeNetteEpargne));
			System.out.println("capitalConstitutifGarentieBase="+capitalConstitutifGarentieBase);

			GarantieAvj garantieAvjbaseDeces = new GarantieAvj(new BigDecimal(capitalConstitutifGarentieBase),  
					new BigDecimal(bourseMensuelle), new BigDecimal(Prime_Nette_Deces_base), new BigDecimal(pup_baseDeces()), new BigDecimal(pap_baseDeces()), new BigDecimal(ppp_basedeces()), new BigDecimal(Prime_Nette_Deces_base));
			GarantieAvj garantieAvjEpargneInscription = null;
			GarantieAvj garantieAvjDecesInscription = null;
			GarantieAvj garantieAvjDecesRente = null;

			if(fraisInscriptionAnnuel !=0){

				garantieAvjEpargneInscription = new GarantieAvj( new BigDecimal(capitalConstitutifInscription),  new BigDecimal(fraisInscriptionAnnuel),  new BigDecimal(prime_Nette_Epargne_Inscription), 
						new BigDecimal(0), new BigDecimal(PAP_InscriptionEpargne), new BigDecimal(ppp_InscriptionEpargne()), new BigDecimal(prime_Nette_Epargne_Inscription));
				System.out.println("capitalConstitutifInscription="+capitalConstitutifInscription);

				garantieAvjDecesInscription = new GarantieAvj(  new BigDecimal(capitalConstitutifInscription),   new BigDecimal(fraisInscriptionAnnuel),  new BigDecimal(prime_Nette_Deces_Inscription), 
						new BigDecimal(pup_DecesInscription()), new BigDecimal(pap_DecesInscription()), new BigDecimal(ppp_Inscriptiondeces()), new BigDecimal(prime_Nette_Deces_Inscription));
			}

			if(renteMonsuelleEnCasDeces !=0){

				garantieAvjDecesRente = new GarantieAvj( new BigDecimal(capitalConstitutifRenteEnCasDece),new BigDecimal(renteMonsuelleEnCasDeces), 
						new BigDecimal(prime_Nette_Deces_Rente), new BigDecimal(pup_rente()), new BigDecimal(pap_rente()), new BigDecimal(ppp_rente()), new BigDecimal(prime_Nette_Deces_Rente));
				System.out.println("capitalConstitutifRenteEnCasDece="+capitalConstitutifRenteEnCasDece);	
			}
			DetailAvj detailAvj = iParametresAvjService.create_detail_avj(BigDecimal.valueOf(bourseMensuelle), selectedAgeServiceBourse.getAgeServiceBourse(), 
					selectedDureServiceBourse.getDure(), datePremiereBourse,  BigDecimal.valueOf(tauxCroissancePrimes),
					BigDecimal.valueOf(tauxCroissBourse),
					BigDecimal.valueOf(surprime),
					clientBean.isFromWS(),
					BigDecimal.valueOf(dtranche));

			newAdhesion.setDetailAvj(detailAvj);
			
			
	        ModePaiementHayya modePaiementHayya = new ModePaiementHayya();
	       // modePaiementHayya.setFrac(Integer.parseInt(getFraction()));
//	        System.out.println("getModepaie() " +getModepaie());
	        modePaiementHayya.setFrac(fractionnement);
	        modePaiementHayya.setModep(Integer.parseInt(getModepaie()));
	        modePaiementHayya.setBanque("0");
	        modePaiementHayya.setAgence("0");
	        modePaiementHayya.setRib("0");
	        modePaiementHayya.setPayeur(0);
	        modePaiementHayya.setNom("0");
	        modePaiementHayya.setCin("0");
	        modePaiementHayya.setAdresse("0");
	        modePaiementHayya.setDisposition("0");
	        modePaiementHayya.setDatecreation(new Date());
	        modePaiementHayya.setJourPrelevement(jourPrelevement);
	        modePaiementHayya.setMoisPrelevement(moisPrelevement);
			

			newAdhesionToPrint = iParametresAvjService.createAdhesion(newBeneficiaire,newAdherent, newAdhesion,
					fraisInscriptionAnnuel,	renteMonsuelleEnCasDeces,garantieAvjbaseEpargne,
					garantieAvjbaseDeces,garantieAvjEpargneInscription,garantieAvjDecesInscription,
					garantieAvjDecesRente, utilisateur,
					detailsEvolutionGarantiesAVJ, detailsGarantieAVJs, 
					modePaiementHayya);
			
			//insert quittance
			 numquit=serviceAssistantVoiyagetService.getNumeroQuit();
			System.out.println("numquit "+numquit);
			
			// date fin dans quitance par fractionnmement
			Calendar cal = Calendar.getInstance();
			Date dateBefore01Days;
			
			
			switch(newAdhesionToPrint.getFraPolice()) {
			

			
		       case "A": 
					cal.setTime(newAdhesionToPrint.getDatDebut());
					cal.add(Calendar.MONTH, 12);
					cal.add(Calendar.DATE, -1);
					 dateBefore01Days = cal.getTime();
					    	 
		           break;
		   
		       case "S":
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
				    cal.add(Calendar.MONTH, 6);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();


		           break;
		   
		       case "T":
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
				    cal.add(Calendar.MONTH, 3);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();
				    
				   
		           break;
		           
		       case "M":
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
				    cal.add(Calendar.MONTH, 1);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();
				    

		           break;
		
		       default:
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
					cal.add(Calendar.DATE, -1);
					 dateBefore01Days = cal.getTime();
					
		           break;
			
			}
			
			Quittance quittance=new Quittance(dateBefore01Days, newAdhesionToPrint.getDatDebut(), newAdhesionToPrint, 
					newAdhesionToPrint.getMtPrime(), 
					(double)0, newAdhesionToPrint.getTaxe(), 
					Integer.parseInt(numquit), new Date(), newAdhesionToPrint.getFga().doubleValue());
			
			
			quittanceDAO.create(quittance);

			noneException=true;
			setDesabledGenerateNotice(false);
			System.out.println("newAdhesionToPrint="+newAdhesionToPrint);

			JsfUtil.addSuccessMessage("Adhésion Validée avec le numéro: "+newAdhesionToPrint.getNadhesion(),"!");

		}
		}
	}
	
	
	
	public void validerPrposition()
	{
		

		boolean error = false;
		
		
	      if(isAfficherJourBlock()) {
	    	  
	    	  System.out.println("isAfficherJourBlock() " +isAfficherJourBlock());
	        	
	        	if(jourPrelevement == null || jourPrelevement ==0) {

	        		System.out.println("jourPrelevement " +jourPrelevement);
	        		
	        		FacesMessage msg = new FacesMessage("ERREUR", "Veuillez choisir le jour de prélèvement!");
	    			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
	    			FacesContext.getCurrentInstance().validationFailed();
	    			error = true;
	    			setDesabledEncaiss(false); 
	        		return;  
	        	}
	        	
	        }
	        
	        if(isAfficherMois()) {
	        	
	        	System.out.println("isAfficherMois() " +isAfficherMois());
	        	
	        	if(moisPrelevement  == null || moisPrelevement ==0) {
	 
	        		System.out.println("moisPrelevement " +moisPrelevement);
	          		
	        		FacesMessage msg = new FacesMessage("ERREUR", "Veuillez choisir le mois de prélèvement!");
	    			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
	    			FacesContext.getCurrentInstance().validationFailed();
	    			error = true;
	    			setDesabledEncaiss(false); 
	        		return;  
	        	}
	        	
	        }	
		

		controlemaxduree=75-ageAssure();
		System.out.println("controlemaxduree="+controlemaxduree);
		System.out.println("dureeContract="+dureeContract);
		if(dureeContract>controlemaxduree)
		{
			System.out.println("enter controle ");
			FacesMessage msg = new FacesMessage("ERREUR", "la somme durée du contrat et age de l'assuré doit être inférieur ou égal à 75 ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			FacesContext.getCurrentInstance().validationFailed();
			error = true;
		}

		if(!error){
		client = getClient();

		if (client.getIdentifiant() == null || client.getIdentifiant().isEmpty()) {
			noneException=false;
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			return;  
		} else if(newAdherent==null || newAdherent.getIdentifiant().isEmpty()	|| newBeneficiaire==null || newBeneficiaire.getNom().isEmpty() || newBeneficiaire.getPrenom().isEmpty() ||newBeneficiaire.getDateNaiss()==null)
		{
			FacesMessage msg = new FacesMessage("ERREUR", "Veuillez saisir l'assuré et le beneficiaire");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			return;  
		}
		else
		{
			if (client.getDatCreation() == null) {
				client.setDatCreation(new Date());
			}
			if(clientDAO.findByIdentifiant(client.getIdentifiant())!=null)
			{
				System.out.println("client existant");
				clientDAO.edit(client);
			}
			else
			{
				System.out.println("client inexistant");
				clientDAO.create(client);
			}
			if(newProposition!=null){
				newAdhesion.setProposition(newProposition); 
			}
			newAdhesion.setClient(client);
			newAdhesion.setDuree(dureeContract);
			newAdhesion.setEtat("V");
			newAdhesion.setTauxInter(selectedTauxInteret);
			newAdhesion.setMtPrime(total_nette);
			newAdhesion.setFga(fraisContrat);
			newAdhesion.setPrimetot(prime_totale);
			newAdhesion.setFraPolice(fractionnementDAO.find(fractionnement).getLibelle().substring(0, 1) );
			newAdhesion.setPolice(libnumplc);

			/************insertion de garanties et des details**********************/
			GarantieAvj garantieAvjbaseEpargne= new GarantieAvj( new BigDecimal(capitalConstitutifGarentieBase),  new BigDecimal(bourseMensuelle), 
					new BigDecimal(basePrimeNetteEpargne), new BigDecimal(0), new BigDecimal(PAP_BaseEpargne), new BigDecimal(ppp_baseEpargne()), new BigDecimal(basePrimeNetteEpargne));
			System.out.println("capitalConstitutifGarentieBase="+capitalConstitutifGarentieBase);

			GarantieAvj garantieAvjbaseDeces = new GarantieAvj(new BigDecimal(capitalConstitutifGarentieBase),  
					new BigDecimal(bourseMensuelle), new BigDecimal(Prime_Nette_Deces_base), new BigDecimal(pup_baseDeces()), new BigDecimal(pap_baseDeces()), new BigDecimal(ppp_basedeces()), new BigDecimal(Prime_Nette_Deces_base));
			GarantieAvj garantieAvjEpargneInscription = null;
			GarantieAvj garantieAvjDecesInscription = null;
			GarantieAvj garantieAvjDecesRente = null;

			if(fraisInscriptionAnnuel !=0){

				garantieAvjEpargneInscription = new GarantieAvj( new BigDecimal(capitalConstitutifInscription),  new BigDecimal(fraisInscriptionAnnuel),  new BigDecimal(prime_Nette_Epargne_Inscription), 
						new BigDecimal(0), new BigDecimal(PAP_InscriptionEpargne), new BigDecimal(ppp_InscriptionEpargne()), new BigDecimal(prime_Nette_Epargne_Inscription));
				System.out.println("capitalConstitutifInscription="+capitalConstitutifInscription);

				garantieAvjDecesInscription = new GarantieAvj(  new BigDecimal(capitalConstitutifInscription),   new BigDecimal(fraisInscriptionAnnuel),  new BigDecimal(prime_Nette_Deces_Inscription), 
						new BigDecimal(pup_DecesInscription()), new BigDecimal(pap_DecesInscription()), new BigDecimal(ppp_Inscriptiondeces()), new BigDecimal(prime_Nette_Deces_Inscription));
			}

			if(renteMonsuelleEnCasDeces !=0){

				garantieAvjDecesRente = new GarantieAvj( new BigDecimal(capitalConstitutifRenteEnCasDece),new BigDecimal(renteMonsuelleEnCasDeces), 
						new BigDecimal(prime_Nette_Deces_Rente), new BigDecimal(pup_rente()), new BigDecimal(pap_rente()), new BigDecimal(ppp_rente()), new BigDecimal(prime_Nette_Deces_Rente));
				System.out.println("capitalConstitutifRenteEnCasDece="+capitalConstitutifRenteEnCasDece);	
			}
			DetailAvj detailAvj = iParametresAvjService.create_detail_avj(BigDecimal.valueOf(bourseMensuelle), selectedAgeServiceBourse.getAgeServiceBourse(), 
					selectedDureServiceBourse.getDure(), datePremiereBourse,  BigDecimal.valueOf(tauxCroissancePrimes),
					BigDecimal.valueOf(tauxCroissBourse),
					BigDecimal.valueOf(surprime),
					clientBean.isFromWS(),
					BigDecimal.valueOf(dtranche));

			newAdhesion.setDetailAvj(detailAvj);
			
			prime_totale_avec_surprime = surprime + prime_totale;
			prime_nette_avec_surprime = surprime + total_nette;
			
			
	        ModePaiementHayya modePaiementHayya = new ModePaiementHayya();
	       // modePaiementHayya.setFrac(Integer.parseInt(getFraction()));
//	        System.out.println("getModepaie() " +getModepaie());
	        modePaiementHayya.setFrac(fractionnement);
	        modePaiementHayya.setModep(Integer.parseInt(getModepaie()));
	        modePaiementHayya.setBanque("0");
	        modePaiementHayya.setAgence("0");
	        modePaiementHayya.setRib("0");
	        modePaiementHayya.setPayeur(0);
	        modePaiementHayya.setNom("0");
	        modePaiementHayya.setCin("0");
	        modePaiementHayya.setAdresse("0");
	        modePaiementHayya.setDisposition("0");
	        modePaiementHayya.setDatecreation(new Date());
	        modePaiementHayya.setJourPrelevement(jourPrelevement);
	        modePaiementHayya.setMoisPrelevement(moisPrelevement);
			

			newAdhesionToPrint = iParametresAvjService.createAdhesion(newBeneficiaire,newAdherent, newAdhesion,
					fraisInscriptionAnnuel,	renteMonsuelleEnCasDeces,garantieAvjbaseEpargne,
					garantieAvjbaseDeces,garantieAvjEpargneInscription,garantieAvjDecesInscription,
					garantieAvjDecesRente, utilisateur,
					detailsEvolutionGarantiesAVJ, detailsGarantieAVJs, 
					modePaiementHayya);
			
			//insert quittance
			 numquit=serviceAssistantVoiyagetService.getNumeroQuit();
			System.out.println("numquit "+numquit);
			
			// date fin dans quitance par fractionnmement
			Calendar cal = Calendar.getInstance();
			Date dateBefore01Days;
			
			
			switch(newAdhesionToPrint.getFraPolice()) {
			

			
		       case "A": 
					cal.setTime(newAdhesionToPrint.getDatDebut());
					cal.add(Calendar.MONTH, 12);
					cal.add(Calendar.DATE, -1);
					 dateBefore01Days = cal.getTime();
					    	 
		           break;
		   
		       case "S":
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
				    cal.add(Calendar.MONTH, 6);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();


		           break;
		   
		       case "T":
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
				    cal.add(Calendar.MONTH, 3);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();
				    
				   
		           break;
		           
		       case "M":
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
				    cal.add(Calendar.MONTH, 1);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();
				    

		           break;
		
		       default:
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
					cal.add(Calendar.DATE, -1);
					 dateBefore01Days = cal.getTime();
					
		           break;
			
			}
			
			Quittance quittance=new Quittance(dateBefore01Days, newAdhesionToPrint.getDatDebut(), newAdhesionToPrint, 
					newAdhesionToPrint.getMtPrime(), 
					(double)0, newAdhesionToPrint.getTaxe(), 
					Integer.parseInt(numquit), new Date(), newAdhesionToPrint.getFga().doubleValue());
			quittanceDAO.create(quittance);
			
			Double primetot = newAdhesionToPrint.getPrimetot(); 
			Double primenette = newAdhesionToPrint.getMtPrime();
			
			Double primetot_avec_surprime = newAdhesionToPrint.getPrimetot() + newAdhesionToPrint.getDetailAvj().getSurprime().doubleValue(); 
			Double primenette_avec_surprime = newAdhesionToPrint.getMtPrime() + newAdhesionToPrint.getDetailAvj().getSurprime().doubleValue(); 
			
			QuittanceDetails qt = new QuittanceDetails(
					quittanceDAO.searchquit(Integer.parseInt(numquit)).getIdQuittance(),
					newAdhesionToPrint,
			utilisateur.getAgence(), "C", 
			null, 0, 
			primetot, primenette, primetot_avec_surprime, primenette_avec_surprime);
			
			quittanceDetailsDAO.create(qt);
			
			
			//mnt_capital = String.valueOf(newAdhesionToPrint.getCapital().doubleValue() );
			
			mnt_total = String.valueOf(primetot );
			
			mnt_total_avec_surprime = String.valueOf(primetot_avec_surprime);
			
			mnt_nette = String.valueOf(primenette );
			
			mnt_nette_avec_surprime =  String.valueOf(primenette_avec_surprime);

			// noneException=true;
			// setDesabledGenerateNotice(false);
			System.out.println("newAdhesionToPrint="+newAdhesionToPrint);
			
			skip = true;

			JsfUtil.addSuccessMessage("Adhésion Validée avec le numéro: "+newAdhesionToPrint.getNadhesion(),"!");

		}
		} else {
			
			System.out.println("error " +error);
			return;  
		}
	}
	

	public void Encaissement_sans_ws() {
		
		setDesabledEncaiss(true); 
		validerPrposition();
		
		setDesabledGenerateNotice(false);
        
		FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		System.out.println("Encaissement effectuée avec succès.");
		
		/**** enregistremnt ds notre !BD ****/

		newAdhesionToPrint.setEtat("V");

		adhesionDAO.edit(newAdhesionToPrint);
	}
	
	
public void Encaissement() throws NoSuchAlgorithmException, KeyManagementException{
		
	setDesabledEncaiss(true); 
	
		boolean test = true;

		
		validerPrposition();
		if(  newAdhesionToPrint == null || newAdhesionToPrint.getIdAdhesion() == 0) return;
		newAdhesionToPrint.setEtat("N");

		
		newAdhesionToPrint = adhesionDAO.merge(newAdhesionToPrint);


		
		


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
					  
					  //h7ayyaController.setDisabledValidationAdhesion(false);
						//setDesabledEncaiss(true); 
						
					  return;
					
				}else {
					
					if(token.equalsIgnoreCase("exception")) {
						
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , Erreur de connexion au WS Token!", ""));
			     		
			     		//h7ayyaController.setDisabledValidationAdhesion(false);
						//setDesabledEncaiss(true); 
			     		
			     		System.out.println("probleme de Exception  ");
			     		
			     		return;
					}
					
					if(token.equalsIgnoreCase("not200")) {
						
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , problème de droit d'accès au WS Token!", ""));
			     		
			     		//h7ayyaController.setDisabledValidationAdhesion(false);
						//setDesabledEncaiss(true); 
			     		
			     		System.out.println("probleme de Exception  ");
			     		
			     		return;
					}
				}



			
			 final String BASE_URI = Constante.URL_ENCAISS_STB;
			
			 //final String BASE_URI = "https://openbank.stb.com.tn:9443/v0/payment";
			 
			//final String BASE_URI = "https://openbank.stb.com.tn/v0/payment";
			
			System.out.println("BASE_URI "+Constante.URL_ENCAISS_STB);
			
			String IdDemande = numquit;
			
//			String PrimeAssurance = mnt_total;
			String PrimeAssurance = mnt_total_avec_surprime;
			String AccountNumber = clientBean.getClient().getNcompte();
			String Police = newAdhesionToPrint.getPolice().getNPolice()+ newAdhesionToPrint.getNumeroAdhesion();
			String Cin = clientBean.getClient().getIdentifiant();
			String Nom = clientBean.getClient().getNomClient(); 
			String Prenom = clientBean.getClient().getPrenomClient();
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			String DateNaissance = df.format(clientBean.getClient().getDatNaiss());
			
			String CapitalAssure = "0";
			
			String DateEffet = df.format(newAdhesionToPrint.getDatDebut());
			String Datefin = df.format(newAdhesionToPrint.getDatFin());
			
			String Mobile = clientBean.getClient().getTel();
			String Mail = clientBean.getClient().getMail();
			
			// String Produit = "01";
			String Produit = Constante.PRODUIT_AVJ;
			
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

					//h7ayyaController.setDisabledValidationAdhesion(false);
					//setDesabledEncaiss(true); 
					return;
				}
			    
			    
			    if (response.getStatus() != 200) {
//					throw new RuntimeException("Failed : HTTP error code : "
//					     + response.getStatus());
			    	String output_0 = response.getEntity(String.class);
			    	JSONObject jsonOutput_0 = new JSONObject(output_0);
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

					//h7ayyaController.setDisabledValidationAdhesion(false);
					//setDesabledEncaiss(true); 
					return;
				}

				System.out.println("Output from Server .... \n");
				String output = response.getEntity(String.class);
				System.out.println(output);
				
				// JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
				
				JSONObject jsonOutput = new JSONObject(output);

			
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
					
					
					setDesabledGenerateNotice(false);
                   
					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					System.out.println("Encaissement effectuée avec succès.");
					
					/**** enregistremnt ds notre !BD ****/

					newAdhesionToPrint.setEtat("V");

					adhesionDAO.edit(newAdhesionToPrint);
					
					OperationTransaction operationTransaction = new OperationTransaction();

					operationTransaction.setDateTrans(new Date());
					operationTransaction.setIdAdhesion(newAdhesionToPrint); // à discuter
					operationTransaction.setRefOperation(Reference);
					operationTransaction.setTypTrans("Encaissement");

					iOperationTransactionDAO.create(operationTransaction);
					
					
					if(("2").equals(modepaie)) {
					
					API_Bassem(newAdhesionToPrint,
							newAdhesionToPrint.getNumeroAdhesion()+ "-" + UUID.randomUUID().toString().substring(0, 5),
						    clientBean.getClient().getNcompte(),
//						    bourseMensuelle,
						    total_nette + surprime ,
						    "Elyssa assurance",
						    clientBean.getClient().getIdentifiant(),
						    Frequency.getLabelByCode(convertFractionnement(fractionnement)),
						    "PRELEVEMENT_BANCAIRE",
						    21,
						    FrequencyService.getNextDateAsString(newAdhesion.getDatDebut(),convertFractionnement(fractionnement)),
						    FrequencyService.getPreviousDateWith28(newAdhesion.getDatFin(),convertFractionnement(fractionnement)),
						    "TND",
						    newAdhesionToPrint.getPolice().getNPolice()+ newAdhesionToPrint.getNumeroAdhesion(),
						    "10404100143212778883", //Constante.RIB_STAR_PRELEV,
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

						//h7ayyaController.setDisabledValidationAdhesion(false);
						//setDesabledEncaiss(true); 
						return;
					
				}
			       
			        
			    	  
				
				} 
			catch (ClientHandlerException e) {

				
	     		
	     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement: problème d'appel au WS d'encaissement!", ""));
	     		
	     		//h7ayyaController.setDisabledValidationAdhesion(false);
	     		//setDesabledEncaiss(true); 
	     		
	     		System.out.println("probleme de ClientHandlerException  ");
	     		
				e.printStackTrace();

			  }
			catch (org.json.JSONException e) {

				
	     		
	     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , Erreur au niveau du retour du WS d'encaissement!", ""));
	     		
	     		//h7ayyaController.setDisabledValidationAdhesion(false);
	     		//setDesabledEncaiss(true); 
	     		
	     		System.out.println("probleme de JSONException  ");
	     		
				e.printStackTrace();

			  }
			catch (Exception e) {

					
		     		
		     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , Erreur interne liée au niveau du WS d'encaissement!", ""));
		     		
		     		//h7ayyaController.setDisabledValidationAdhesion(false);
		     		//setDesabledEncaiss(true); 
		     		
		     		System.out.println("probleme de Exception  ");
		     		
					e.printStackTrace();

				  }
			

		

		}
	}
	


/************** encaissement sans ws pour test 2025*************************/

public void Encaissement_2025() throws NoSuchAlgorithmException, KeyManagementException{
	
	setDesabledEncaiss(true); 
	
		boolean test = true;

		
		validerPrposition();
		if(  newAdhesionToPrint == null || newAdhesionToPrint.getIdAdhesion() == 0) return;
		newAdhesionToPrint.setEtat("N");

		
		newAdhesionToPrint = adhesionDAO.merge(newAdhesionToPrint);


		
		


		if(clientBean.getClient().getNcompte() == null || clientBean.getClient().getNcompte().equalsIgnoreCase("")){
           
			FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

		}
			
		
	 
		
		 else {

            
			 setDesabledGenerateNotice(false);
             
				FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				System.out.println("Encaissement effectuée avec succès.");
				
				/**** enregistremnt ds notre !BD ****/

				newAdhesionToPrint.setEtat("V");

				adhesionDAO.edit(newAdhesionToPrint);
				
				OperationTransaction operationTransaction = new OperationTransaction();

				operationTransaction.setDateTrans(new Date());
				operationTransaction.setIdAdhesion(newAdhesionToPrint); // à discuter
				operationTransaction.setRefOperation("Reference");
				operationTransaction.setTypTrans("Encaissement");

				iOperationTransactionDAO.create(operationTransaction);
			
				if(("2").equals(modepaie)) {
				
				API_Bassem(newAdhesionToPrint,
						newAdhesionToPrint.getNumeroAdhesion()+ "-" + UUID.randomUUID().toString().substring(0, 5),
					    clientBean.getClient().getNcompte(),
//					    bourseMensuelle,
					    total_nette + surprime,
					    "Elyssa assurance",
					    clientBean.getClient().getIdentifiant(),
					    Frequency.getLabelByCode(convertFractionnement(fractionnement)),
					    "PRELEVEMENT_BANCAIRE",
					    21,
					    FrequencyService.getNextDateAsString(newAdhesion.getDatDebut(),convertFractionnement(fractionnement)),
					    FrequencyService.getPreviousDateWith28(newAdhesion.getDatFin(),convertFractionnement(fractionnement)),
					    "TND",
					    newAdhesionToPrint.getPolice().getNPolice()+ newAdhesionToPrint.getNumeroAdhesion(),
					    "10404100143212778883", //Constante.RIB_STAR_PRELEV,
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
	
	
	
	
	
	
	/**************************************Calcul à afficher dans la table garantiesavj**********************************/
	public double pup_baseDeces(){
		System.out.println("pup_baseDeces"+ module1Impl.pupA(ageAssure(), fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_BaseEpargne));

		return module1Impl.pupA(ageAssure(), fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_BaseEpargne);
	}
	public double pup_DecesInscription(){
		System.out.println("pup_DecesInscription"+module1Impl.pupA(ageAssure(), fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(),PAP_InscriptionEpargne));

		return module1Impl.pupA(ageAssure(), fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(),PAP_InscriptionEpargne);

	}
	public double pup_rente(){
		System.out.println("pup_rente"+module1Impl.pupB(ageAssure(),ageEnfant(),ageServiceBourse(),tauxTechnique,CroissPrime(),renteMonsuelleEnCasDeces));

		return module1Impl.pupB(ageAssure(),ageEnfant(),ageServiceBourse(),tauxTechnique,CroissPrime(),renteMonsuelleEnCasDeces);

	}
	public double pap_baseDeces(){
		System.out.println("pap_baseDeces"+module1Impl.papA(ageAssure(),(int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_BaseEpargne));

		return module1Impl.papA(ageAssure(),(int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_BaseEpargne);

	}
	public double pap_DecesInscription(){
		System.out.println("pap_DecesInscription"+module1Impl.papA(ageAssure(),(int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(),PAP_InscriptionEpargne));

		return module1Impl.papA(ageAssure(),(int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(),PAP_InscriptionEpargne);

	}
	public double pap_rente(){
		System.out.println("pap_rente"+module1Impl.papB(ageAssure(),ageEnfant(),ageServiceBourse(),tauxTechnique,CroissPrime(),renteMonsuelleEnCasDeces,fractionnPrime()));

		return module1Impl.papB(ageAssure(),ageEnfant(),ageServiceBourse(),tauxTechnique,CroissPrime(),renteMonsuelleEnCasDeces,fractionnPrime());

	}
	public double ppp_baseEpargne(){
		
		/****** a ete remplace par ******/
//		System.out.println("ppp_baseEpargne"+module1Impl.pek(capitalConstitutifGarentieBase, tauxTechnique, fractionnPrime(), ageServiceBourse(), ageEnfant(), CroissPrime(), tauxCroissBourse, 0));
//
//		return module1Impl.pek(capitalConstitutifGarentieBase, tauxTechnique, fractionnPrime(), ageServiceBourse(), ageEnfant(), CroissPrime(), tauxCroissBourse, 0);

		return basePrimeNetteEpargne*fractionnPrime();
		 
	}
	public double ppp_basedeces(){
		
		/****** a ete remplace par ******/
//		System.out.println("ppp_basedeces"+module1Impl.pppA(ageAssure(), (int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_BaseEpargne, 0));
//
//		return module1Impl.pppA(ageAssure(), (int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_BaseEpargne, 0);

		return Prime_Nette_Deces_base*fractionnPrime();
		 
	}
	public double ppp_InscriptionEpargne(){
		
		/****** a ete remplace par ******/
//		System.out.println("ppp_InscriptionEpargne"+module1Impl.pek(capitalConstitutifInscription, tauxTechnique, fractionnPrime(), ageServiceBourse(), ageEnfant(), CroissPrime(), tauxCroissBourse, 0));
//
//		return module1Impl.pek(capitalConstitutifInscription, tauxTechnique, fractionnPrime(), ageServiceBourse(), ageEnfant(), CroissPrime(), tauxCroissBourse, 0);
//		
		return prime_Nette_Epargne_Inscription*fractionnPrime();
		 
	}
	public double ppp_Inscriptiondeces(){
		
		/****** a ete remplace par ******/
//		System.out.println("ppp_Inscriptiondeces"+ module1Impl.pppA(ageAssure(), (int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_InscriptionEpargne, 0));
//
//		return module1Impl.pppA(ageAssure(), (int)dureeContract, fractionnPrime(), tauxTechnique, CroissPrime(), ageEnfant(), ageServiceBourse(), PAP_InscriptionEpargne, 0);

		return prime_Nette_Deces_Inscription*fractionnPrime();
	 
	}
	public double ppp_rente(){
//		System.out.println("ppp_rente"+module1Impl.pppB(ageAssure(),ageEnfant(),ageServiceBourse(),tauxTechnique,CroissPrime(),renteMonsuelleEnCasDeces,fractionnPrime(),0));
//
//		return module1Impl.pppB(ageAssure(),ageEnfant(),ageServiceBourse(),tauxTechnique,CroissPrime(),renteMonsuelleEnCasDeces,fractionnPrime(),0);

		return prime_Nette_Deces_Rente*fractionnPrime();
	}
	
	
	public int PartiEntier(long dureeContrat ){
		
		return (int) ((int)2*dureeContrat/3); 
	}
	/**************************************************************/
	public void imprimerDevis(){
		
		
		try {
		
		Map<String, Object> parameters = new HashMap();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		parameters.put("star",reportController.getLogoJasper());
		parameters.put("numplc", libnumplc.getNPolice());
		parameters.put("nomclt", client.getNomClient());
		parameters.put("prenomclt", client.getPrenomClient());
		parameters.put("numprop", newProposition.getNumeroProposition());
		parameters.put("identifiant", client.getIdentifiant());
		parameters.put("imagesociete",cod_societe+".png");
		parameters.put("effDate", df.format(newAdhesion.getDatDebut()));
		parameters.put("datefin", df.format(newAdhesion.getDatFin()));
		parameters.put("duree", String.valueOf((int)dureeContract) + " ans");
		parameters.put("Mntbourse", bourseMensuelle);
		parameters.put("Mntbourse", (new DecimalFormat("#0.000")).format(bourseMensuelle) + " TND");
		parameters.put("Agesevice", selectedAgeServiceBourse.getAgeServiceBourse().toString());
		parameters.put("Durservice", selectedDureServiceBourse.getDure().toString() + " ans");
		parameters.put("Dateservice", df.format(datePremiereBourse));
		parameters.put("PrmEpargneb", (new DecimalFormat("#0.000")).format(basePrimeNetteEpargne) + " TND");
		parameters.put("PrmDecb", (new DecimalFormat("#0.000")).format(Prime_Nette_Deces_base) + " TND");
		parameters.put("FraisinscON", "NON");
		if(fraisInscriptionAnnuel !=0){
			parameters.put("FraisinscON", "OUI");
		}
		parameters.put("Mtfrais", (new DecimalFormat("#0.000")).format(fraisInscriptionAnnuel) + " TND");
		parameters.put("Mtpepargnfrais", (new DecimalFormat("#0.000")).format(prime_Nette_Epargne_Inscription) + " TND");
		parameters.put("Mtpdecfrais", (new DecimalFormat("#0.000")).format(prime_Nette_Deces_Inscription) + " TND");
		parameters.put("RenteON", "NON");
		if(renteMonsuelleEnCasDeces !=0){
			parameters.put("RenteON", "OUI");
		}
		parameters.put("Mtrente", renteMonsuelleEnCasDeces);
		parameters.put("Mtrente", (new DecimalFormat("#0.000")).format(renteMonsuelleEnCasDeces) + " TND");
		parameters.put("Mtrentedeces", (new DecimalFormat("#0.000")).format(prime_Nette_Deces_Rente) + " TND");
		parameters.put("Prmnetepargne", (new DecimalFormat("#0.000")).format(basePrimeNetteEpargne+prime_Nette_Epargne_Inscription) + " TND");
		parameters.put("Prmnetdeces", (new DecimalFormat("#0.000")).format(Prime_Nette_Deces_base+prime_Nette_Deces_Inscription+prime_Nette_Deces_Rente) + " TND");
//		parameters.put("Totprmnet", (new DecimalFormat("#0.000")).format(total_nette) + " TND");
		parameters.put("Totprmnet", (new DecimalFormat("#0.000")).format(prime_nette_avec_surprime) + " TND");
		parameters.put("frais", (new DecimalFormat("#0.000")).format(fraisContrat)+ " TND");
		//	        parameters.put("Prmtot", prime_totale);
//		parameters.put("Prmtot", (new DecimalFormat("#0.000")).format(prime_totale) + " TND");
		parameters.put("Prmtot", (new DecimalFormat("#0.000")).format(prime_totale_avec_surprime) + " TND");
		parameters.put("ville_agence", utilisateur.getAgence().getAdresse());
		
		
		List<DetailsEvolutionGarantieAvjDTO> orders = detailsEvolutionGarantiesDTO;
		System.out.println("size "+detailsEvolutionGarantiesDTO.size());

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(orders);

        parameters.put("datasource", ds);
        
        parameters.put("report", reportController.getRealPath());
        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH); 
        
        
        List<Medical> medicals_ = medicals;
		System.out.println("size "+medicals_.size());

        JRBeanCollectionDataSource ds2 = new JRBeanCollectionDataSource(medicals_);

        parameters.put("datasource2", ds2);

        String path = reportController.getRealPath()
				+ "devisAVJ_v4.jasper";

//        System.out.println("path "+path);
		

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
        
        /*
		try {
			String path = reportController.getRealPath() + "devisAVJ.jasper";
			JasperPrint jp = JasperFillManager.fillReport(path, parameters,  new JREmptyDataSource());
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "inline; filename=devis_ba.pdf");
			ServletOutputStream servletOutputStream;
			try {
				servletOutputStream = httpServletResponse.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jp, servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();

			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		} catch (JRException e) {
			System.out.println(e.getMessage());
		}
		*/
	}

	/**************************************************************************************************


		 /**************************************************************/
	public void imprimerContratAvj(){
		
		try {
			
		Map<String, Object> parameters = new HashMap();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
		parameters.put("star",reportController.getLogoJasper());
		 parameters.put("contrat", newAdhesionToPrint.getNadhesion());
		//parameters.put("numplc", newAdhesionToPrint.getPolice().getNPolice());
		parameters.put("Souscripteur", client.getNomClient()+" "+client.getPrenomClient());
		
		String typeVoie_libelle_sous = " ";
		if (client.getTypeVoie() != null ) {
			  // doSomething
			typeVoie_libelle_sous = client.getTypeVoie().getLibelle();
			}
		
		parameters.put("Adrsouscripteur", client.getNumAdr()+" "+typeVoie_libelle_sous+" "+client.getAdrClient()+" "+client.getLocalite()+" "+client.getGouver()+" "+client.getCodeP());
		parameters.put("Cinsouscripteur", client.getIdentifiant());
		/**************/
		
		String typeVoie_libelle = " ";
		if (newAdherent.getTypeVoie() != null ) {
			  // doSomething
			typeVoie_libelle = newAdherent.getTypeVoie().getLibelle();
			}
		parameters.put("Assure", newAdherent.getNomAdherent()+" "+newAdherent.getPrenomAdherent());
		parameters.put("Adrassure", newAdherent.getNumAdr()+" "+typeVoie_libelle+" "+newAdherent.getAdresse()+" "+newAdherent.getLocalite()+" "+newAdherent.getGouver()+" "+newAdherent.getCodePostal());
		parameters.put("Cinassure", newAdherent.getIdentifiant());
		parameters.put("Datnaiassure", df.format(newAdherent.getDatNaiss()));
		//parameters.put("Prfassure", newAdherent.getProfession().getLibelleProfession());
		parameters.put("Prfassure", newAdherent.getProfession() != null?newAdherent.getProfession().getLibelleProfession():" ");
		parameters.put("Sexassure", newAdherent.getSexe());
		parameters.put("Telassure", newAdherent.getTel());

		parameters.put("Beneficiaire", newBeneficiaire.getNom()+" "+newBeneficiaire.getPrenom());
		parameters.put("Adrbenef", newBeneficiaire.getAdresse());
		parameters.put("Datnaisbenef", df.format(newBeneficiaire.getDateNaiss()));
		parameters.put("Sexbenef", newBeneficiaire.getSexe());
		/***********/
		parameters.put("imagesociete",cod_societe+".png");
		parameters.put("Datedeff", df.format(newAdhesion.getDatDebut()));
		parameters.put("Datech", df.format(newAdhesion.getDatFin()));
		parameters.put("Duree", String.valueOf((int)dureeContract) + " ans");
		parameters.put("Mntbourse", bourseMensuelle);
		parameters.put("Mntbourse", (new DecimalFormat("#0.000")).format(bourseMensuelle) + " TND");
		parameters.put("Agesevice", selectedAgeServiceBourse.getAgeServiceBourse().toString());
		parameters.put("Durservice", selectedDureServiceBourse.getDure().toString() + " ans");
		parameters.put("Dateservice", df.format(datePremiereBourse));
		parameters.put("PrmEpargneb", (new DecimalFormat("#0.000")).format(basePrimeNetteEpargne) + " TND");
		parameters.put("PrmDecb", (new DecimalFormat("#0.000")).format(Prime_Nette_Deces_base) + " TND");
		parameters.put("FraisinscON", "NON");
		if(fraisInscriptionAnnuel !=0){
			parameters.put("FraisinscON", "OUI");
		}
		parameters.put("Mtfrais", (new DecimalFormat("#0.000")).format(fraisInscriptionAnnuel) + " TND");
		parameters.put("Mtpepargnfrais", (new DecimalFormat("#0.000")).format(prime_Nette_Epargne_Inscription) + " TND");
		parameters.put("Mtpdecfrais", (new DecimalFormat("#0.000")).format(prime_Nette_Deces_Inscription) + " TND");
		parameters.put("RenteON", "NON");
		if(renteMonsuelleEnCasDeces !=0){
			parameters.put("RenteON", "OUI");
		}
		parameters.put("Mtrente", renteMonsuelleEnCasDeces);
		parameters.put("Mtrente", (new DecimalFormat("#0.000")).format(renteMonsuelleEnCasDeces) + " TND");
		parameters.put("Mtrentedeces", (new DecimalFormat("#0.000")).format(prime_Nette_Deces_Rente) + " TND");
		parameters.put("Prmnetepargne", (new DecimalFormat("#0.000")).format(basePrimeNetteEpargne+prime_Nette_Epargne_Inscription) + " TND");
		parameters.put("Prmnetdeces", (new DecimalFormat("#0.000")).format(Prime_Nette_Deces_base+prime_Nette_Deces_Inscription+prime_Nette_Deces_Rente) + " TND");
//		parameters.put("Totprmnet", (new DecimalFormat("#0.000")).format(total_nette) + " TND");
		parameters.put("Totprmnet", (new DecimalFormat("#0.000")).format(prime_nette_avec_surprime) + " TND");
		parameters.put("frais", (new DecimalFormat("#0.000")).format(fraisContrat)+ " TND");
//		parameters.put("Prmtot", (new DecimalFormat("#0.000")).format(prime_totale) + " TND");
		parameters.put("Prmtot", (new DecimalFormat("#0.000")).format(prime_totale_avec_surprime) + " TND");

		parameters.put("Fractionnement", fractionnementDAO.find(fractionnement).getLibelle());
		parameters.put("tauxevol", String.valueOf(tauxCroissancePrimes));
		parameters.put("DerEch", df.format(newAdhesion.getDatFin()));
		parameters.put("taux", String.valueOf(selectedTauxInteret));
		parameters.put("ville_agence", utilisateur.getAgence().getAdresse());
		
		
		parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
		
		
		Calendar cal = Calendar.getInstance();
		Date dateBefore01Days;
		
		
		switch(newAdhesion.getFraPolice()) {
		

		
	       case "A": 
				cal.setTime(newAdhesion.getDatDebut());
				cal.add(Calendar.MONTH, 12);
				cal.add(Calendar.DATE, -1);
				 dateBefore01Days = cal.getTime();
				    	 
	           break;
	   
	       case "S":
	    	   cal.setTime(newAdhesion.getDatDebut());
			    cal.add(Calendar.MONTH, 6);
			    cal.add(Calendar.DATE, -1);
			     dateBefore01Days = cal.getTime();


	           break;
	   
	       case "T":
	    	   cal.setTime(newAdhesion.getDatDebut());
			    cal.add(Calendar.MONTH, 3);
			    cal.add(Calendar.DATE, -1);
			     dateBefore01Days = cal.getTime();
			    
			   
	           break;
	           
	       case "M":
	    	   cal.setTime(newAdhesion.getDatDebut());
			    cal.add(Calendar.MONTH, 1);
			    cal.add(Calendar.DATE, -1);
			     dateBefore01Days = cal.getTime();
			    

	           break;
	
	       default:
	    	   cal.setTime(newAdhesion.getDatDebut());
				cal.add(Calendar.DATE, -1);
				 dateBefore01Days = cal.getTime();
				
	           break;
		
		}
		
		parameters.put("Date_ech_prime", df.format(dateBefore01Days));

		
		List<DetailsEvolutionGarantieAvjDTO> orders = detailsEvolutionGarantiesDTO;
		System.out.println("size "+detailsEvolutionGarantiesDTO.size());

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(orders);

        parameters.put("datasource", ds);
        
        parameters.put("report", reportController.getRealPath());
        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH); 

        String path =  "choisir_societe";
        
        path = reportController.getRealPath()
 				+ "avenir_jeunesse_v5.jasper";
        
		if( utilisateur.getAgence().getSociete().getCodSociet().equals("022671FPM000") ) { //QNB
			
			path = reportController.getRealPath()
					+ "avenir_jeunesse_v6.jasper";

			
		}
		
		
		if(utilisateur.getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat())) {//stb
			
			
			if(this.modepaie.equals("2")) {
				
				path = reportController.getRealPath()
						+ "avenir_jeunesse_v6.jasper";
			} 
	
		} 
        
        
 
        
        
        
/********** pour prelv****************/

           

            
            //parameters.put("agence", utilisateur.getAgence().getCodeAgence());
            
			 
			//parameters.put("contrat",adhesion.getPolice().getNPolice());


			parameters.put("nom_assure", newAdhesionToPrint.getClient().getNomClient()+" "+newAdhesionToPrint.getClient().getPrenomClient());
			parameters.put("adresse", newAdhesionToPrint.getClient().getAdrClient());
			parameters.put("tel", newAdhesionToPrint.getClient().getTel());
			 
			
			parameters.put("email", newAdhesionToPrint.getClient().getMail() != null?newAdhesionToPrint.getClient().getMail():" ");
			
			parameters.put("num_contrat",newAdhesionToPrint.getNadhesion());
			
			parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
			
			String nom_complet = utilisateur.getAgence().getSociete().getNomComplet();
			parameters.put("nom_complet", nom_complet.replace("<b>", "").replace("</b>", "") );
			
			parameters.put("agence", utilisateur.getAgence().getLibelle());
			
			parameters.put("adresse_agence", utilisateur.getAgence().getAdresse());
			
			 
			String rib = newAdhesionToPrint.getClient().getNcompte();
			
			parameters.put("rib", newAdhesionToPrint.getClient().getNcompte());
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
		/*
		try {
			String path = reportController.getRealPath() + "avenir_jeunesse.jasper";
			JasperPrint jp = JasperFillManager.fillReport(path, parameters,  new JREmptyDataSource());
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "inline; filename=Notice_avj.pdf");
			ServletOutputStream servletOutputStream;
			try {
				servletOutputStream = httpServletResponse.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jp, servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();

			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		} catch (JRException e) {
			System.out.println(e.getMessage());
		}
		*/
	}


	public String remplirAssure() {


		//		if(clientBean.isAS())
		//		{
		//			System.out.println("coché");

		
			if (clientBean.getClient().getIdentifiant() != null ) {
				System.out.println("enter remplirAssure");
				newAdherent=new Adherent();
				newAdherent.setTypeDoc(clientBean.getClient().getTypeDoc());
				newAdherent.setIdentifiant(clientBean.getClient().getIdentifiant());
				newAdherent.setNomAdherent(clientBean.getClient().getNomClient());
				newAdherent.setPrenomAdherent(clientBean.getClient().getPrenomClient());
				newAdherent.setDatNaiss(clientBean.getClient().getDatNaiss());
				newAdherent.setNumAdr(clientBean.getClient().getNumAdr());
				newAdherent.setTypeVoie(clientBean.getClient().getTypeVoie());
				newAdherent.setAdresse(clientBean.getClient().getAdrClient());
				newAdherent.setSexe(clientBean.getClient().getSexe().equals("F")?"Feminin":"Masculin");
				if(clientBean.getClient().getLocalite().isEmpty()){

				}
				else{
				newAdherent.setGouver(clientBean.getClient().getGouver());
				selectedLocgouv = locgouvDAO.findByPostaCode(clientBean.getClient().getCodeP(),	clientBean.getClient().getLocalite());
				allCodePostalByGouvernera.clear();
				allCodePostalByGouvernera.addAll(mapAllCodePostalByGouvernerat.get(selectedLocgouv.getGouvernora()));
				newAdherent.setCodePostal(clientBean.getClient().getCodeP());
				newAdherent.setLocalite(clientBean.getClient().getLocalite());
				}
				newAdherent.setTel(clientBean.getClient().getTel());
				newAdherent.setProfession(clientBean.getClient().getProfession());
				newAdherent.setClient(clientBean.getClient());

				ncompte = clientBean.getClient().getNcompte();

				//} 
			}else {
				System.out.println("enter ViderAssure");
				newAdherent=new Adherent(); 
			}
		
		return "devisAvenirJeunesse?faces-redirect=true";
	}
	
	
	public void remplirAssure_pour_ancien_proposition() {


		//		if(clientBean.isAS())
		//		{
		//			System.out.println("coché");

		
			if (clientBean.getClient().getIdentifiant() != null ) {
				System.out.println("enter remplirAssure");
				newAdherent=new Adherent();
				newAdherent.setTypeDoc(clientBean.getClient().getTypeDoc());
				newAdherent.setIdentifiant(clientBean.getClient().getIdentifiant());
				newAdherent.setNomAdherent(clientBean.getClient().getNomClient());
				newAdherent.setPrenomAdherent(clientBean.getClient().getPrenomClient());
				newAdherent.setDatNaiss(clientBean.getClient().getDatNaiss());
				newAdherent.setNumAdr(clientBean.getClient().getNumAdr());
				newAdherent.setTypeVoie(clientBean.getClient().getTypeVoie());
				newAdherent.setAdresse(clientBean.getClient().getAdrClient());
				newAdherent.setSexe(clientBean.getClient().getSexe().equals("F")?"Feminin":"Masculin");
				if(clientBean.getClient().getLocalite().isEmpty()){

				}
				else{
				newAdherent.setGouver(clientBean.getClient().getGouver());
				selectedLocgouv = locgouvDAO.findByPostaCode(clientBean.getClient().getCodeP(),	clientBean.getClient().getLocalite());
				allCodePostalByGouvernera.clear();
				allCodePostalByGouvernera.addAll(mapAllCodePostalByGouvernerat.get(selectedLocgouv.getGouvernora()));
				newAdherent.setCodePostal(clientBean.getClient().getCodeP());
				newAdherent.setLocalite(clientBean.getClient().getLocalite());
				}
				newAdherent.setTel(clientBean.getClient().getTel());
				newAdherent.setProfession(clientBean.getClient().getProfession());
				newAdherent.setClient(clientBean.getClient());


				//} 
			}else {
				System.out.println("enter ViderAssure");
				newAdherent=new Adherent(); 
			}
		
		
	}
	
	

	public void reinitialiserAdherent()
	{
		newAdherent=new Adherent();
		allCodePostalByGouvernera.clear();
	}

	private Proposition selectedProposition;

	public Proposition getSelectedProposition() {
		return selectedProposition;
	}

	public void setSelectedProposition(Proposition selectedProposition) {
		this.selectedProposition = selectedProposition;
	}
	public void loadProposition() {

		String newDevis = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("newDevis");

		
		if (adhesionDAO.propexiste_7ayya(selectedProposition).isEmpty()) {
			affiche = true;
			System.out.println("affiche 1 ===================="+ affiche);
		}
		else {
			
			
			affiche = false;
			System.out.println("affiche 2 ===================="+ affiche);
		}
		
		System.out.println("jourPrelevement ===================="+ jourPrelevement);
		System.out.println("moisPrelevement ===================="+ moisPrelevement);
		
				if (getSelectedProposition()!=null && (affiche == true)) {
					
					try {
						
						desabledEncaiss = false;
						
						setDesabledGenerateNotice(true);
						
						newProposition = getSelectedProposition();
		
						clientBean.setClient(getSelectedProposition().getClient());
						
						
						detailsGarantieAVJs = new ArrayList<>();
						
						
						remplirAssure_pour_ancien_proposition();
						
						newAdhesion.setDatDebut(getSelectedProposition().getDatDebut());
						setBourseMensuelle(getSelectedProposition().getDetailAvj().getMtBourse().doubleValue());
						setSelectedAgeServiceBourse(ageServiceBourseDAO.getAgeServiceBourse_by_Age(getSelectedProposition().getDetailAvj().getAgeServiceBourse()));
						setSelectedDureServiceBourse(dureeServiceBourseDAO.getDureeServiceBourse_by_Duree(getSelectedProposition().getDetailAvj().getDureeBourse()));
						newAdhesion.setDatFin(getSelectedProposition().getDatFin());
						setFractionnement(fractionnementDAO.getFractionnement_by_Libelle(getSelectedProposition().getFraPolice()).getIdFrc());
						setTauxCroissancePrimes(getSelectedProposition().getDetailAvj().getIdTauxCroissancePrime().doubleValue());
						setTauxCroissBourse(getSelectedProposition().getDetailAvj().getTauxCroissanceBourse().doubleValue());
						setSelectedTauxInteret(getSelectedProposition().getTauxInter());
						setDureeContract((long)getSelectedProposition().getDuree());
						
						setDtranche(getSelectedProposition().getDetailAvj().getDtranche().doubleValue());
						
						newAdherent.setDatNaiss(getSelectedProposition().getClient().getDatNaiss());
						//setNewAdherent(getSelectedProposition().getAdherents().get(0));
						setNewBeneficiaire(getSelectedProposition().getBeneficiaires().get(0));
						System.out.println("benefic="+getSelectedProposition().getBeneficiaires().get(0));
						for (GarantieAvj gar : getSelectedProposition().getGarantieAvjs())
						{
							System.out.println("in garantie");
							System.out.println("gar name="+gar.getGarantie().getGarantie());
							if(gar.getGarantie().getIdGarantie()==3)
							{
								System.out.println("in 3");
								setFraisInscriptionAnnuel(gar.getMontant().doubleValue());
							}
							if(gar.getGarantie().getIdGarantie()==5)
							{
								System.out.println("in 5");
								setRenteMonsuelleEnCasDeces(gar.getMontant().doubleValue());
							}
						}
						
//						jourPrelevement = null;
//						anneePrelevement = null;
						
//						if(getSelectedProposition().getGarantieAvjs().contains(paramGarantieAvjDAO.find(3)))
//						{
//							setFraisInscriptionAnnuel(garantieAvjDAO.getGarantieAvj_by_Garantie(paramGarantieAvjDAO.find(3)).getMontant().doubleValue());
							
//						}
//						if(getSelectedProposition().getGarantieAvjs().contains(paramGarantieAvjDAO.find(5)))
//						{
//							setRenteMonsuelleEnCasDeces(garantieAvjDAO.getGarantieAvj_by_Garantie(paramGarantieAvjDAO.find(5)).getMontant().doubleValue());
							
//						}
		
						selectedProposition=null;
						} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (affiche == false) {
					
					initialisationClientAndAdhesion();
					init();
					clientBean.cancel();
					selectedProposition=null;
					
					FacesMessage msg = new FacesMessage("ATTENTION", "contrat déjà effectué!");
					  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				}
				/*
				else  if (newDevis != null && !newDevis.isEmpty()&& Boolean.parseBoolean(newDevis)) {
					initialisationClientAndAdhesion();
					init();
					clientBean.cancel();
					selectedProposition=null;
				}
				*/
	}
	
	
	public void imprimer_quit() {

		String path = reportController.getRealPath()+ "Quit_avj.jasper";

		try {

			// fonction d'impression du devis
			Map<String, Object> parameters = new HashMap();

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
			parameters.put("numquit", numquit);

			parameters.put("agence", utilisateur.getAgence().getCodeAgence());
			parameters.put("contrat",newAdhesionToPrint.getNumeroAdhesion());
			parameters.put("effet", df.format(newAdhesionToPrint.getDatDebut()));
			
			Calendar cal = Calendar.getInstance();
			Date dateBefore01Days;
			
			switch(newAdhesionToPrint.getFraPolice()) {
			

			
		       case "A": 
					cal.setTime(newAdhesionToPrint.getDatDebut());
					cal.add(Calendar.MONTH, 12);
					cal.add(Calendar.DATE, -1);
					 dateBefore01Days = cal.getTime();
					    	 
		           break;
		   
		       case "S":
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
				    cal.add(Calendar.MONTH, 6);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();


		           break;
		   
		       case "T":
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
				    cal.add(Calendar.MONTH, 3);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();
				    
				   
		           break;
		           
		       case "M":
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
				    cal.add(Calendar.MONTH, 1);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();
				    

		           break;
		
		       default:
		    	   cal.setTime(newAdhesionToPrint.getDatDebut());
					cal.add(Calendar.DATE, -1);
					 dateBefore01Days = cal.getTime();
					
		           break;
			
			}
			
			
			parameters.put("au", df.format(dateBefore01Days));
			
//			parameters.put("prime_tot", newAdhesionToPrint.getPrimetot());
			parameters.put("prime_tot", newAdhesionToPrint.getPrimetot() + newAdhesionToPrint.getDetailAvj().getSurprime().doubleValue());
//			parameters.put("prime_nette", new BigDecimal( newAdhesionToPrint.getMtPrime().doubleValue()));
			parameters.put("prime_nette", newAdhesionToPrint.getMtPrime() + newAdhesionToPrint.getDetailAvj().getSurprime().doubleValue());
            parameters.put("echeance", df.format(newAdhesionToPrint.getDatDebut()));
			
			parameters.put("souscr", newAdhesionToPrint.getClient().getNomClient()+" "+newAdhesionToPrint.getClient().getPrenomClient());
			parameters.put("adr_souscr", newAdhesionToPrint.getClient().getAdrClient());
			 
			parameters.put("assure", newAdhesionToPrint.getClient().getNomClient()+" "+newAdhesionToPrint.getClient().getPrenomClient());
			parameters.put("adr_assure", newAdhesionToPrint.getClient().getAdrClient());
			
			parameters.put("frais", new BigDecimal(0.000));
			parameters.put("fga", newAdhesionToPrint.getFga());
			
			parameters.put("taxe", Double.parseDouble("0.000"));
			
			
			
			parameters.put("star",  reportController.getLogoJasper());
			
			parameters.put("imagesociete", utilisateur.getAgence().getSociete()
					.getCodSociet() + ".png");
			
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
	
	
	
	public void imprimerNoticeAvj(){
		
		/// notice
		try {
            String report = reportController.getRealPath() + "notice_avj.jasper";         
            Map<String, Object> parameters = new HashMap<String, Object>();
            
            

			parameters.put("nom_benef", newAdhesionToPrint.getClient().getNomClient());
			parameters.put("prenom_benef", newAdhesionToPrint.getClient().getPrenomClient());
			parameters.put("cin_benef", newAdhesionToPrint.getClient().getIdentifiant());
			parameters.put("compte", newAdhesionToPrint.getClient().getNcompte());
			parameters.put("adr_assure", newAdhesionToPrint.getClient().getAdrClient());
			parameters.put("cpt", newAdhesionToPrint.getClient().getCodeP());
			parameters.put("tel_assure", newAdhesionToPrint.getClient().getTel());
			parameters.put("email", newAdhesionToPrint.getClient().getMail());				
			parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
			
			Double primetot_avec_surprime = newAdhesionToPrint.getPrimetot() + newAdhesionToPrint.getDetailAvj().getSurprime().doubleValue(); 
			Double primetot_avec_surprime_pour_lettre = primetot_avec_surprime * 1000;
			String primetot_avec_surprime_lettre = Nombre.montantTND(primetot_avec_surprime);
	    	   
	    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot_avec_surprime));
	    	   parameters.put("primetot_pour_lettre", primetot_avec_surprime_pour_lettre.intValue());
				parameters.put("mnt_lettre", primetot_avec_surprime_lettre);
				
				
				String nom_complet = utilisateur.getAgence().getSociete().getNomComplet();
				parameters.put("nom_complet", nom_complet.replace("<b>", "").replace("</b>", "") );
				
				
				parameters
				.put("star",  reportController.getLogoJasper());
				
				parameters.put("imagesociete", utilisateur.getAgence().getSociete()
						.getCodSociet() + ".png");
				
				
		       
		        
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
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	  
	}
	

	
/****************** calcul age assure********************************************/
	public boolean UpdateDateOfBirth() {
		
		try {
		
		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
		
		String date_nais = sdfr.format( clientBean.getClient().getDatNaiss() );
		
		System.out.println("date_nais "+date_nais);
		
//		final String test = Constante.test;
//		System.out.println("test "+test);
		
		final String CodeClient_STB = Constante.CodeClient_STB;
		System.out.println("CodeClient_STB "+CodeClient_STB);
		final String URL_WS_UpdateDateOfBirth_STB = Constante.URL_WS_UpdateDateOfBirth_STB;
		System.out.println("URL_WS_UpdateDateOfBirth_STB "+URL_WS_UpdateDateOfBirth_STB);
		
		Form f = new Form();
		f.add("codeClient", CodeClient_STB);
		f.add("newDoB", date_nais);
		
		com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();

		client.addFilter(new GZIPContentEncodingFilter(false));
		
	    WebResource resource = client
	       .resource(URL_WS_UpdateDateOfBirth_STB);
	    
	    
	    ClientResponse response = resource.accept(MediaType.APPLICATION_XML_TYPE,
		        MediaType.APPLICATION_JSON_TYPE).
		        header("MyHeader", "Jersey Client")
		       .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		       .post(ClientResponse.class, f);

	    
	    if (response == null) {
//	      throw new RuntimeException("Failed : HTTP error code : "
//	           + response.getStatus());
	  	 FacesMessage msg = new FacesMessage("ERREUR", "Pas de réponse au serveur, veuillez contacter l'administrateur! ");
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			  return false;
	  }
	    
	    
	    if (response.getStatus() != 200) {
//	        throw new RuntimeException("Failed : HTTP error code : "
//	             + response.getStatus());
	    	 FacesMessage msg = new FacesMessage("ERREUR", "Erreur serveur, veuillez contacter l'administrateur! ( code d'erreur: " + response.getStatus() + " )");
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			  return false;
	    }
	    
	    net.sf.json.JSONObject jsonOutput = (net.sf.json.JSONObject) JSONSerializer.toJSON( response.getEntity(String.class) ); 
		 
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
	
	
/********* convertor **************/
	public Converter getTypeVoieConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (TypeVoie professionClient : getTypeVoies()) {

					if (String.valueOf(professionClient.getCode())
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

	
/********** recherche assure **********/
	
	private String ncompte;
	
	public String getNcompte() {
		return ncompte;
	}

	public void setNcompte(String ncompte) {
		this.ncompte = ncompte;
	}
	
	public void assure_introuvable_par_ws() {
		
		client_assure = new Client();
		newAdherent=new Adherent();
		ncompte="";
		selectedLocgouv = null;
		allCodePostalByGouvernera.clear();
	}
	
	private Client client_assure = new Client();
	
	public void rechercherDepuisWS() {
		
		
		String numCompte = ncompte;
		String numCIN = newAdherent.getIdentifiant();
		
		String token = TokenSTB.tokenStb(reportController);
		
		if(token == null) {
			
			System.out.println("token null  ");
			
			assure_introuvable_par_ws();
			
			FacesMessage msg = new FacesMessage("ERREUR", "Erreur: Pas de réponse au serveur!");
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			  return;
			
		}else {
			
			if(token.equalsIgnoreCase("exception")) {
				
				assure_introuvable_par_ws();
				
	     		System.out.println("probleme de Exception  token");
	     		FacesMessage msg = new FacesMessage("ERREUR", "Erreur: veuiller contacter l'administrateur!");
				  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				  return;
			}
			
			if(token.equalsIgnoreCase("not200")) {
				
				assure_introuvable_par_ws();
				
	     		System.out.println("not200 token  ");
	     		FacesMessage msg = new FacesMessage("ERREUR", "Erreur: problème de droit d'accès!");
				  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				  return;
			}
		}
		
		final String URL_WS_URL_CLIENT_STB = Constante.URL_CLIENT_STB;
		
		//final String URL_WS_URL_CLIENT_STB = "https://openbank.stb.com.tn:9443/v0/CustomerInformations";
		
		//final String URL_WS_URL_CLIENT_STB = "https://openbank.stb.com.tn/v0/CustomerInformations";
		
		
		String numTypeDoc = "01";
		
		 switch (newAdherent.getTypeDoc() ){
 	   	
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
		
		
		
		SSLContext ctx = null;
		 ctx = SSLContext.getInstance("TLSv1.2");
         ctx.init(null, null, new SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
         
       //si proxy 
//         URLConnectionClientHandler ch  = new URLConnectionClientHandler(new ProxyConnectionFactory());
//         com.sun.jersey.api.client.Client client = new com.sun.jersey.api.client.Client(ch);
         
       //sinon pas proxy
         com.sun.jersey.api.client.Client client= com.sun.jersey.api.client.Client.create(new com.sun.jersey.api.client.config.DefaultClientConfig());

		
	    WebResource resource = client
	       .resource(URL_WS_URL_CLIENT_STB);
	    
	    ClientResponse response = resource.type("application/json")
	    		.header("Ocp-Apim-Subscription-Key", Constante.HEADER_ENCAISS)
	    		.header("Authorization", "Bearer " + token)
	 		   .post(ClientResponse.class, input);
	    
	    
	    if ( response == null) {
			System.out.println("probleme de connexion vide");
			
			assure_introuvable_par_ws();
			
			FacesMessage msg = new FacesMessage("Erreur: pas de réponse au serveur!", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

			return;
		}

	    
	    
	    if (response.getStatus() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//			     + response.getStatus());
	    	
	    	assure_introuvable_par_ws();
     		
     		FacesContext.getCurrentInstance().addMessage(null, 
     				new FacesMessage(FacesMessage.SEVERITY_FATAL, 
     						"Erreur serveur (code ="+response.getStatus()+") ", ""));
     		return;
		}

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
		
		net.sf.json.JSONObject jsonOutput = (net.sf.json.JSONObject) JSONSerializer.toJSON(output);
		
		 
		if(!jsonOutput.getString("ResponseCode").equalsIgnoreCase("null")){
			
			
			
			if(jsonOutput.getString("ResponseCode").equalsIgnoreCase("01")) {
				
				assure_introuvable_par_ws();
				
				FacesMessage msg = new FacesMessage("Assuré inexistant!", "Assuré inexistant");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				return;
				
			} else if(jsonOutput.getString("ResponseCode").equalsIgnoreCase("00")) {
				

				client_assure = new Client();
				newAdherent=new Adherent();
				
				newAdherent.setIdentifiant(numCIN);
				
				newAdherent.setNomAdherent(jsonOutput.getString("LastName"));
				newAdherent.setPrenomAdherent(jsonOutput.getString("FirstName"));
	        
	    	   DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
	    	   DateTime dt = formatter.parseDateTime(jsonOutput.getString("BirthDate"));
	    	   
	    	   newAdherent.setDatNaiss(dt.toDate());
	    	   
	    	   newAdherent.setSexe(jsonOutput.getString("Gender").substring(0,1).toUpperCase().equals("F")?"Feminin":"Masculin");
	    	   newAdherent.setTypeVoie(clientBean.getTypeVoies().get(5));
	        
	    	   newAdherent.setAdresse(jsonOutput.getString("Adress")); 
	        
	        if(!jsonOutput.getString("ZipCode").equalsIgnoreCase("null")){
	      	   
	      	 //System.out.println("gouver="+locgouvDAO.findByPostaCode(dcNode.getNode("codepostal").toString()).getGouvernora());  
	      	  Locgouv gouver =locgouvDAO.findByPostaCode(jsonOutput.getString("ZipCode"));
	      	 if(gouver!= null){
	      		newAdherent.setGouver( gouver.getGouvernora() ); 
	      	 }
	      	 else{
	      		newAdherent.setGouver("");
	      	 	}
	      	 
	      
	      	 
	      	 
	      	 if(!jsonOutput.getString("City").equalsIgnoreCase("null")){
	      		 
	      		 System.out.println("condition_if");
	      		 
	      		 Locgouv localite = locgouvDAO.findByPostaCode(jsonOutput.getString("ZipCode"), jsonOutput.getString("City"));
	      		 
	      		 if(localite !=null){
	      			 
	      			 System.out.println("condition_if2");
	      			 
	      			 //clientBean.setSelectedLocgouv(localite);
	      			selectedLocgouv = localite;
	      			newAdherent.setLocalite(jsonOutput.getString("City"));
	      			newAdherent.setCodePostal(jsonOutput.getString("ZipCode"));
	      		 }else{
	      			 
	      			 localite = new Locgouv();
	      			 //clientBean.setSelectedLocgouv(localite);
	      			selectedLocgouv = localite;
	      			newAdherent.setLocalite("");
	      			newAdherent.setCodePostal("");
	      			 
	      		 }
	      		 
	      		 
	      	 }
	      	 
	      	 
	      	 	allCodePostalByGouvernera.clear();
	  			allCodePostalByGouvernera.addAll(mapAllCodePostalByGouvernerat.get(selectedLocgouv.getGouvernora()));
	  			


	         }
	         
	        newAdherent.setTel(jsonOutput.getString("PhoneNumber"));
	         //clientBean.getClient().setMail(jsonOutput.getString("Email"));
	        newAdherent.setClient(clientBean.getClient());
	         
	         
			}
			
			else  {
				
				if(!jsonOutput.getString("RejectMessage").equalsIgnoreCase("null")) {
					
					assure_introuvable_par_ws();
					
					FacesMessage msg = new FacesMessage(jsonOutput.getString("RejectMessage"), jsonOutput.getString("RejectMessage"));
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					return;
					
				}else {
					
					assure_introuvable_par_ws();
					
					FacesMessage msg = new FacesMessage("Assuré inexistant!", "Assuré inexistant");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					return;
					
				}
				
			}
			
		}
		
		
		
		
		} 
		
		catch (ClientHandlerException e) {
			
			assure_introuvable_par_ws();
			
			
				System.out.println("probleme de connexion vide");
				FacesMessage msg = new FacesMessage("Erreur: problème d'accès au serveur!", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);

				System.out.println("probleme de ClientHandlerException  ");
				
			}
		
		catch (Exception e) {

			assure_introuvable_par_ws();
     		
     		FacesContext.getCurrentInstance().addMessage(null, 
     				new FacesMessage(
     						FacesMessage.SEVERITY_FATAL, "Erreur: veuiller contacter l'administrateur!", ""));
     		
     		System.out.println("probleme de Exception  ");
     		
			e.printStackTrace();

		  }
		
	}
	
	
	public void rechercher() {
		//searching = true;
		if ( newAdherent.getIdentifiant() != null) {
			client_assure = clientDAO.findByIdentifiant(newAdherent.getIdentifiant());
			if (client_assure == null) {
				FacesMessage msg = new FacesMessage("Assuré inexistant","Assuré inexistant ");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				FacesContext.getCurrentInstance().validationFailed();
				//cancel();
			} else {
				
				
				newAdherent=new Adherent();
				newAdherent.setTypeDoc(client_assure.getTypeDoc());
				newAdherent.setIdentifiant(client_assure.getIdentifiant());
				newAdherent.setNomAdherent(client_assure.getNomClient());
				newAdherent.setPrenomAdherent(client_assure.getPrenomClient());
				newAdherent.setDatNaiss(client_assure.getDatNaiss());
				newAdherent.setNumAdr(client_assure.getNumAdr());
				newAdherent.setTypeVoie(client_assure.getTypeVoie());
				newAdherent.setAdresse(client_assure.getAdrClient());
				newAdherent.setSexe(client_assure.getSexe().equals("F")?"Feminin":"Masculin");
				if(client_assure.getLocalite().isEmpty()){

				}
				else{
				newAdherent.setGouver(client_assure.getGouver());
				selectedLocgouv = locgouvDAO.findByPostaCode(client_assure.getCodeP(),	client_assure.getLocalite());
				allCodePostalByGouvernera.clear();
				allCodePostalByGouvernera.addAll(mapAllCodePostalByGouvernerat.get(selectedLocgouv.getGouvernora()));
				newAdherent.setCodePostal(client_assure.getCodeP());
				newAdherent.setLocalite(client_assure.getLocalite());
				}
				newAdherent.setTel(client_assure.getTel());
				newAdherent.setProfession(client_assure.getProfession());
				newAdherent.setClient(client_assure);

			
				
			
				//find = true;

			}
		} else {
			FacesMessage msg = new FacesMessage("Veuillez saisir un identifiant", "Assuré inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
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
            
            if(newAdhesionToPrint.getFraPolice().equalsIgnoreCase("P")) {
            	
            	report = reportController.getRealPath() + "attestation_impot_avj_prime_unique_contrat.jasper";   
            	
            }else {
            	
            	report = reportController.getRealPath() + "attestation_impot_avj_contrat.jasper";   
            	
            }
            
            Map<String, Object> parameters = new HashMap<String, Object>();
            
            

			parameters.put("souscripteur", newAdhesionToPrint.getClient().getNomClient() + " " + newAdhesionToPrint.getClient().getPrenomClient());
			parameters.put("numplc", newAdhesionToPrint.getPolice().getNPolice());
			parameters.put("cin_sousc", newAdhesionToPrint.getClient().getIdentifiant());
			
			DateFormat df = new SimpleDateFormat("yyyy");
			
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			
			Date today = new Date();
			dateImpotDebut = today;
			dateImpotFin = today;

			parameters.put("du", df2.format(dateImpotDebut));
			
			parameters.put("au", df2.format(dateImpotFin));
			
			parameters.put("id_adhesion", newAdhesionToPrint.getIdAdhesion());
			
			parameters.put("duree", (int)newAdhesionToPrint.getDuree());
			
			parameters.put("date_contrat", df2.format(newAdhesionToPrint.getDatDebut()));
			
			parameters.put("num_adhesion", newAdhesionToPrint.getNadhesion());
		
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
            
            if(newAdhesionToPrint.getFraPolice().equalsIgnoreCase("P")) {
            	
            	report = reportController.getRealPath() + "attestation_impot_avj_prime_unique.jasper";   
            	
            }else {
            	
            	report = reportController.getRealPath() + "attestation_impot_avj.jasper";   
            	
            }
            
            Map<String, Object> parameters = new HashMap<String, Object>();
            
            

			parameters.put("souscripteur", newAdhesionToPrint.getClient().getNomClient() + " " + newAdhesionToPrint.getClient().getPrenomClient());
			parameters.put("numplc", newAdhesionToPrint.getPolice().getNPolice());
			parameters.put("cin_sousc", newAdhesionToPrint.getClient().getIdentifiant());
			
			DateFormat df = new SimpleDateFormat("yyyy");
			
			DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");

			parameters.put("annee", df.format(dateImpot));
			
			parameters.put("id_adhesion", newAdhesionToPrint.getIdAdhesion());
			
			parameters.put("duree", (int)newAdhesionToPrint.getDuree());
			
			parameters.put("date_contrat", df2.format(newAdhesionToPrint.getDatDebut()));
			
			parameters.put("num_adhesion", newAdhesionToPrint.getNadhesion());
		
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
	
	
	
    public static int calculateDays360(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int startDay = startCal.get(Calendar.DAY_OF_MONTH);
        int startMonth = startCal.get(Calendar.MONTH) + 1; // Les mois commencent à 0
        int startYear = startCal.get(Calendar.YEAR);

        int endDay = endCal.get(Calendar.DAY_OF_MONTH);
        int endMonth = endCal.get(Calendar.MONTH) + 1; // Les mois commencent à 0
        int endYear = endCal.get(Calendar.YEAR);

        // Ajustements selon la méthode 30/360
        if (startDay == 31 || (startMonth == 2 && startDay == startCal.getActualMaximum(Calendar.DAY_OF_MONTH))) {
            startDay = 30;
        }
        if (endDay == 31 && startDay == 30) {
            endDay = 30;
        }

        return (endYear - startYear) * 360 + (endMonth - startMonth) * 30 + (endDay - startDay);
    }
    
    
    
    /********** partie prelev *****************************************/
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
	    
//	    return "2".equals(modepaie) && 1==convertFractionnement(fractionnement);
	    return "2".equals(modepaie) 
	    	       && (1 == convertFractionnement(fractionnement) 
	    	           || 2 == convertFractionnement(fractionnement));
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
	    	    cal.setTime(newAdhesion.getDatDebut());
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
//	        cal.setTime(newAdhesion.getDatDebut());
//	        int startYear = cal.get(Calendar.YEAR); // année minimale
//	        int endYear = startYear + 5; // par ex., 5 ans max
//	        for (int y = startYear; y <= endYear; y++) {
//	            anneesDisponibles.add(new SelectItem(y, String.valueOf(y)));
//	        }
////	    }
//	    return anneesDisponibles;
//	}
//	
	
	
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
//      jsonMap.put("fromMonth", fromMonth);
      jsonMap.put("fromMonth", fromMonth == null ? "" : fromMonth);
//      jsonMap.put("fromDayOfMonth", fromDayOfMonth);
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
	
	
	
	public int convertFractionnement(int fractionnement) {

	    switch (fractionnement) {

	        case 1:
	            return 4;

	        case 2:
	            return 3;

	        case 3:
	            return 2;

	        case 4:
	            return 1;

	        default:
	            return 1;
	    }
	}
	
	
	public String getEchDateString() {
		
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		return  formatter.format(newAdhesion.getDatFin());
		
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

	    // Créer une date "aujourd'hui" sans l'heure
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

	    Calendar calMax = Calendar.getInstance();
	    calMax.setTime(maxdate);
	    calMax.set(Calendar.HOUR_OF_DAY, 0);
	    calMax.set(Calendar.MINUTE, 0);
	    calMax.set(Calendar.SECOND, 0);
	    calMax.set(Calendar.MILLISECOND, 0);

	    // Vérification date >= aujourd'hui
	    if (calSaisie.before(calAuj)) {
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Date invalide",
	                "La date d'effet doit être ≥ aujourd'hui");
	        throw new ValidatorException(msg);
	    }

	    // Vérification date <= maxdate
	    if (calSaisie.after(calMax)) {
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Date invalide",
	                "La date doit être ≤ " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(maxdate));
	        throw new ValidatorException(msg);
	    }
	}

	
	/***** demande de slimen si durée < 8/
	 * 
	 */
	public boolean isAfficherDureeInfo() {
	    return dureeContract > 0 && dureeContract < 8;
	}
	
}
