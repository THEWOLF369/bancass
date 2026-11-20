package com.star.Ba.jsf;

import static io.restassured.path.xml.XmlPath.with;
import io.restassured.path.xml.element.Node;

import java.io.IOException;
import java.io.Serializable;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.star.Ba.commun.Age;
import com.star.Ba.commun.AgeCalculator;
import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IDetailActDAO;
import com.star.Ba.dao.IParamActDAO;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IPropositionDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.ITariftdDAO;
 
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.jsf.util.Proprietes;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.DetailAct;
import com.star.Ba.model.Locgouv;
import com.star.Ba.model.Police;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.parametres.ExampleResourceClient;
import com.star.Ba.parametres.TrustAllX509TrustManager;
import com.star.Ba.service.IParametreAgricoleService;
import com.star.Ba.service.IParametresTDService;


/**
 * @author
 * 
 */
@ManagedBean(name = "devisBean")
@SessionScoped
public class DevisBean implements Serializable {
	// devis information
	
	private boolean disabledall = false;

	private Map<String, Object> listnumplc=null;
	private String numplc;
	private String cin;
	private String nom;
	private String prenom;
	private Date datenaiss, mindate,maxdate;
	private Date effDate;
	private Date datcreation;
	private double duree;
	private int dureeA;
	private int dureeM;
    private String nadhesion;
	private String franchise;
	private Date datefin;
	private double capital;
	private double tauxsurprime;
	private double majoration;
	private double tauxdeprime;
	private double primeglobal;
	private Boolean tarifier;
	private double tauxinteret;
	private Date aujourdhui;
	private Age ageCalc;
	private int age;
	private String propositionId;
	private List<DetailAct> lstAnalyse;
	private boolean showTd=false;
	private Police libnumplc;
	private int numact;
	private String RefCredit;
	private String NumDecision;
    private String etat;
	private int nbmois;
	private int nbannee;
	private int minage;
	private int maxage;
private String numprop;
	
	
	
	public String getNumprop() {
		return numprop;
	}

	public void setNumprop(String numprop) {
		this.numprop = numprop;
	}
	
	
	public int getMinage() {
		return minage;
	}

	public void setMinage(int minage) {
		this.minage = minage;
	}

	public int getMaxage() {
		return maxage;
	}

	public void setMaxage(int maxage) {
		this.maxage = maxage;
	}

	public Date getMaxdate() {
		return maxdate;
	}

	public void setMaxdate(Date maxdate) {
		this.maxdate = maxdate;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

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
	private IParametresTDService parametresTDService;

	@EJB
	private IPoliceDAO iPoliceDAO;

	@EJB
	private ITariftdDAO iTariftdDAO;

	@EJB
	private IPropositionDAO propositionDAO;
	
	@EJB
    private IPoliceDAO policeDAO;
	
	@EJB
    private IDetailActDAO detailActDAO;

    @EJB
    private IParamActDAO paramActDAO;
    
    @EJB
    private IParametreAgricoleService iParametreAgricoleService;
    
    @EJB
    private IAdhesionDAO adhesionDAO;
    @EJB
    private IProduitDAO produitDAO;
    
    @EJB
    private IConventionDAO conventionDAO;
    
    @EJB
    private ISocieteDAO societeDAO;
    
	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;

	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	
	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;
	
	@ManagedProperty("#{login.utilisateur.profil.code}")
	private String profil;

	private String cod_societe;

	private Adhesion adhesion;
	private Proposition proposition;
	private String propid;

	 private Proposition selectedProposition;



	public Proposition getSelectedProposition() {
		return selectedProposition;
	}

	public void setSelectedProposition(Proposition selectedProposition) {
		this.selectedProposition = selectedProposition;
	}

	public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}

	public int getNumact() {
		return numact;
	}

	public void setNumact(int numact) {
		this.numact = numact;
	}

	public void setListnumplc(Map<String, Object> listnumplc) {
		this.listnumplc = listnumplc;
	}

	public String getProfil() {
		return profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}

	public String getNadhesion() {
		return nadhesion;
	}

	public void setNadhesion(String nadhesion) {
		this.nadhesion = nadhesion;
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

	public double getTauxinteret() {
		return tauxinteret;
	}

	public void setTauxinteret(double tauxinteret) {
		this.tauxinteret = tauxinteret;
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDatenaiss() {
		return datenaiss;
	}

	
	private String dateFormat;
	private Boolean notifie;
	
	
	
	
	
	public Boolean getNotifie() {
		return notifie;
	}

	public void setNotifie(Boolean notifie) {
		this.notifie = notifie;
	}

	public /*String*/ Date getDateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		
		
		return /*sdf.format(effDate)*/ effDate;
	}

	public void setDateFormat(/*String*/ Date dateFormat) {
		this.effDate /*dateFormat*/ = dateFormat;
	}

	public void setDatenaiss(Date datenaiss) {
		this.datenaiss = datenaiss;
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

	public String getFranchise() {
		return franchise;
	}

	public void setFranchise(String franchise) {
		this.franchise = franchise;
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

	public double getTauxsurprime() {
		return tauxsurprime;
	}

	public void setTauxsurprime(double tauxsurprime) {
		this.tauxsurprime = tauxsurprime;
	}

	public double getMajoration() {
		return majoration;
	}

	public void setMajoration(double majoration) {
		this.majoration = majoration;
	}

	public double getTauxdeprime() {
		return tauxdeprime;
	}

	public void setTauxdeprime(double tauxdeprime) {
		this.tauxdeprime = tauxdeprime;
	}

	public double getPrimeglobal() {
		return primeglobal;
	}

	public void setPrimeglobal(double primeglobal) {
		this.primeglobal = primeglobal;
	}

	public Boolean getTarifier() {
		return tarifier;
	}

	public void setTarifier(Boolean tarifier) {
		this.tarifier = tarifier;
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

	
	
	public List<DetailAct> getLstAnalyse() {
		return lstAnalyse;
	}

	public void setLstAnalyse(List<DetailAct> lstAnalyse) {
		this.lstAnalyse = lstAnalyse;
	}
	
	

	public boolean isShowTd() {
		return showTd;
	}

	public void setShowTd(boolean showTd) {
		this.showTd = showTd;
	}
	
	public Date getDatcreation() {
		return datcreation;
	}

	public void setDatcreation(Date datcreation) {
		this.datcreation = datcreation;
	}
	
	
	
	public String getRefCredit() {
		return RefCredit;
	}

	public void setRefCredit(String refCredit) {
		RefCredit = refCredit;
	}
	
	public String getNumDecision() {
		return NumDecision;
	}
	
	public void setNumDecision(String numDecision) {
		NumDecision = numDecision;
	}
	
	
	public boolean isDisabledall() {
		return disabledall;
	}


	public void setDisabledall(boolean disabledall) {
		this.disabledall = disabledall;
	}
	

	@PostConstruct
	public void init() {

		aujourdhui = new Date();
		effDate = aujourdhui;
		updatepolice();
		calc_date();
		setShowTd(false);
		
//		Status = 0;
//		validate = 0;
		
	}
	public void calc_date()
	{
		mindate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(mindate);
		minage=iTariftdDAO.find_min_age((policeDAO.getPolice_by_lib(numplc).get(0)).getNPolice())+1;
		System.out.println("minage="+minage);
		cal.add(Calendar.YEAR, -minage);
		mindate = cal.getTime();
		
		maxdate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(maxdate);
		maxage=iTariftdDAO.find_max_age((policeDAO.getPolice_by_lib(numplc).get(0)).getNPolice());
		System.out.println("maxage="+maxage);
		c.add(Calendar.YEAR, -maxage);
		maxdate = c.getTime();
	}

	public void newDevis() {
		init();
		adhesion=new Adhesion();
		cin = "";
		nom = "";
		prenom = "";
		datenaiss = null;
		duree=0;
	    nbannee=0;
	    nbmois=0;

		franchise = "0";

		capital = 0.0;
		tauxsurprime = 0.0;
		majoration = 0.0;
		tauxdeprime = 0;
		primeglobal = 0;
		tauxinteret = 0;
		datefin=null;
		setClient(new Client());
		
		
		

	}

	public void updateDate() {

		Calendar c = Calendar.getInstance();
		c.setTime(effDate);
		c.add(Calendar.YEAR, dureeA);
		c.add(Calendar.MONTH, dureeM);
		c.add(Calendar.DAY_OF_MONTH, -1);
		datefin = c.getTime();
	}

	public String calculTarif() {
		// calcul du tarif
		System.out.println("age="+age);
		System.out.println("minage="+minage);
		System.out.println("maxage="+maxage);
		if(age<minage || age>maxage){
			System.out.println("test1");
			FacesMessage msg = new FacesMessage("Date de naissance erronée!!","ERROR");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("ERROR", msg);
			return null;
		}
		
		
		
		Calendar c = Calendar.getInstance();
		c.setTime(effDate);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(aujourdhui);
		
		if(c.get(Calendar.DAY_OF_YEAR) < c1.get(Calendar.DAY_OF_YEAR) && (c.get(Calendar.YEAR)==c1.get(Calendar.YEAR)) && ! utilisateur.getProfil().getCode().equals(PofileEnum.TDST.name())){
//		if (c.compareTo(c1) < 0 && ! utilisateur.getProfil().getCode().equals(PofileEnum.TDST.name())){
			FacesMessage msg = new FacesMessage("La date d'effet doit être égale à la date du jour!", "La date d'effet doit être égale à la date du jour!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("La date d'effet doit être égale à la date du jour!", msg);
            return null;
		}
		
		
		
		System.out.println("test2");
		tauxdeprime();
		
		primeglobal = parametresTDService.Tarifier(capital, tauxdeprime,
				dureeA, dureeM, tauxsurprime, majoration);
		adhesion = new Adhesion(0, capital, effDate, datefin, duree, "V",
				franchise, utilisateur.getIdUtilisateurs(), majoration,
				primeglobal, tauxinteret, tauxdeprime, tauxsurprime,
				getClient(), iPoliceDAO.find(libnumplc.getNPolice()),nadhesion,new Date());
		
		
		if (getSelectedProposition() != null )
		{
			
			if(utilisateur.getProfil().getCode().equals(PofileEnum.TDST.name()))
			{
				getSelectedProposition().setTauxSprim(tauxsurprime);
				getSelectedProposition().setMajoration(majoration);
				getSelectedProposition().setEtat("E");
				adhesion.setEtat("E");
			}
			proposition = getSelectedProposition();
			
		}
		else{
		
		if(utilisateur.getProfil().getCode().equals(PofileEnum.TDST.name()))
		{
			adhesion.setEtat("E");
		}
		proposition = new Proposition(getAdhesion());
		
		}
		adhesion.setRef_credit(RefCredit);
		numact=paramActDAO.getParamAct_by_numact(age, capital);
		lstAnalyse=detailActDAO.getDetailAct_by_numact(numact);
		return "tarifTD?faces-redirect=true";
		
	}

	public void updatepolice() {
		if(utilisateur.getProfil().getCode().equals(PofileEnum.TDST.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name()))
        {
			listnumplc = getlistnumplc(cod_societe,	utilisateur.getProfil());
        }
		else
		{
			cod_societe = utilisateur.getAgence().getSociete().getCodSociet();
		listnumplc = getlistnumplc(cod_societe,	utilisateur.getProfil());
		}
		System.out.println("cod_societe="+cod_societe);
		System.out.println("utilisateur.getProfil()="+utilisateur.getProfil());
		System.out.println("listnumplc"+listnumplc);
		numplc = (String) listnumplc.values().toArray()[0];
		System.out.println("mumplc dans update police="+numplc);

	}

	

	public void tauxdeprime() {
		System.out.println("******numplc="+numplc);
		System.out.println("******cod_societe="+cod_societe);
		if(utilisateur.getProfil().getCode().equals(PofileEnum.TDST.name()))
		{
			libnumplc=parametresTDService.getnumplcbylib(numplc);
		}
		else{
		libnumplc=parametresTDService.getnumplcbylibelle(cod_societe,numplc);
		}
			duree = nbannee * 12 + nbmois;
		
		if (duree != 0) {
			dureeA = nbannee;//(int) duree;
			dureeM = nbmois;//(int) ((duree - (int) duree) * 100);
			updateDate();
		}
       System.out.println("age"+age);
		if (capital != 0 && duree != 0 && age != 0) {
			//Police police = iPoliceDAO.find(libnumplc.getNPolice());
			
			String code = iTariftdDAO.getcodetarif_by_police(libnumplc.getNPolice());
			if (libnumplc.getTauxPrime() == 0) {

				tauxdeprime = iTariftdDAO.getTaux_prime(dureeA * 12 + dureeM, age, code, capital,libnumplc.getNPolice());
			} else {

				tauxdeprime = libnumplc.getTauxPrime();
			}
		}
	}

	
	public void loadProposition() {
		notifie=false;
		System.out.println("notifie="+notifie);
		
//		propositionId = FacesContext.getCurrentInstance()
//				.getExternalContext().getRequestParameterMap()
//				.get("proposition");
//		propid=propositionId;

		String newDevis = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("newDevis");

		if (getSelectedProposition() != null ) {
			
			try {
				

				// client = proposition.getClient();
				clientBean.setClient(getSelectedProposition().getClient());
				numprop=getSelectedProposition().getnProposition();
				notifie=getSelectedProposition().getNotifie();
				franchise = String.valueOf(getSelectedProposition().getFranchise());
				effDate = getSelectedProposition().getDatDebut();
				datefin = getSelectedProposition().getDatFin();
				capital = getSelectedProposition().getCapital();
				tauxdeprime = getSelectedProposition().getTauxPrime();
				tauxinteret = getSelectedProposition().getTauxInter();
				tauxsurprime = getSelectedProposition().getTauxSprim();
				majoration = getSelectedProposition().getMajoration();
				numplc=getSelectedProposition().getPolice().getLibelle();
				etat=getSelectedProposition().getEtat();
//				int dur = (int) getSelectedProposition().getDuree();
//				double durm=getSelectedProposition().getDuree()- dur; 
				int dur = (int) getSelectedProposition().getDuree();
				nbannee=(int)(dur/12);
				nbmois=dur-(nbannee*12);
//				duree = durm + dur;
			//	selectedProposition=null;
				} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (newDevis != null && !newDevis.isEmpty()&& Boolean.parseBoolean(newDevis)) {
			
			newDevis();
			clientBean.cancel();
			adhesion=new Adhesion();
			selectedProposition=null;
			etat="V";
			
		}
		calculAge();
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
	
	public void imprimerDevis(String numplc, String identifiant, Date datNais,
            Date effDate, double duree, String franchise,
            Date datefin, double capital, double primeglobal,
            String nom, String prenom,String numprop) {

        // fonction d'impression du devis 
        Map<String, Object> parameters = new HashMap();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        parameters.put("star","star.png");
        parameters.put("numplc", numplc);
        parameters.put("nomclt", nom);
        parameters.put("prenomclt", prenom);
        parameters.put("numprop", numprop);
        parameters.put("identifiant", identifiant);
        parameters.put("datNais", df.format(datNais));
        if(utilisateur.getProfil().getCode().equals(PofileEnum.TDST.name()))
        {
        	cod_societe=libnumplc.getConvention().getSociete().getCodSociet();
        }
        parameters.put("imagesociete",cod_societe+".png");
        parameters.put("effDate", df.format(effDate));
        parameters.put("datefin", df.format(datefin));
        parameters.put("duree", String.valueOf(duree));

//        parameters.put("tauxdeprime", String.valueOf(tauxdeprime));
        parameters.put("franchise", franchise);
        parameters.put("capital", String.valueOf(capital));

//        parameters.put("tauxinteret", String.valueOf(tauxinteret));
//        System.out.println("maj: " + majoration);
//        parameters.put("tauxsurprime", String.valueOf(tauxsurprime));
//        parameters.put("majoration", String.valueOf(majoration));
        parameters.put("primeglobal",
                (new DecimalFormat("#0.000")).format(primeglobal) + " TND");
        parameters.put("villeagence", utilisateur.getAgence().getLibelle());

        try {
            String path = reportController.getRealPath() + "devis_ba.jasper";
            // this.getClass().getResource("closed.jasper").getPath() 
            JasperPrint jp = JasperFillManager.fillReport(path, parameters,
                    new JREmptyDataSource());

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getResponse();

            httpServletResponse.addHeader("Content-disposition",
                    "inline; filename=devis_ba.pdf");
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

	public String navigateToClientPage() {
		//clientBean.rechercher();
		if(clientBean.getClient().getGouver()==null){
			clientBean.getAllCodePostalByGouvernera().clear();
		}
		return "ClientTd?faces-redirect=true&keep=true";
	}
	
	public void rechercheAdhesionByNadhesion(FacesContext context, UIComponent comp,Object value)
	{
		String nadhesion = (String) value;
		System.out.println("nadhesion"+nadhesion);
		if(adhesionDAO.FindAdhesionByNadhesion(nadhesion)!=null)
        {
			
        	String msg = "Ce numéro d'adhesion existe déja!!";
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
	}
	
    public Map<String, Object> getlistnumplc(String cod_societ,Profil profil) {
        Map<String, Object> listnumplc = new LinkedHashMap<String, Object>();
        List<Police> list =null;
        
        if(profil.getCode().equals(PofileEnum.TDST.name()) || profil.getCode().equals(PofileEnum.ASI.name()))
        {
        	list= policeDAO.getPolice_by_prod(produitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode()));
        }
        else{
            list = policeDAO.getPolice_by_conv_prod(conventionDAO.find_conv_by_Societ(societeDAO.find(cod_societ)),produitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode()));
            System.out.println("cod_societ="+cod_societ);
            System.out.println("conventionDAO.find_conv_by_Societ(societeDAO.find(cod_societ)="+conventionDAO.find_conv_by_Societ(societeDAO.find(cod_societ)).getIdConvention());
            System.out.println("produitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode())="+produitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode()).getCodeProduit());
            System.out.println("list="+list);
        }
       
        for (Police police : list) {
        	listnumplc.put(police.getLibelle().trim(), police.getLibelle());
        }

        return listnumplc;
    }
    
    public void findAge() {
		if (getClient() != null && getClient().getDatNaiss() != null) {
			ageCalc = AgeCalculator.calculateAge(getClient().getDatNaiss());
			age = ageCalc.getYears();
			if (ageCalc.getMonths() >= 6) {
				age = ageCalc.getYears() + 1;
			}
		}
	}
    
    
    public void calculAge() {
		if (getClient() != null && getClient().getDatNaiss() != null) {
			ageCalc = AgeCalculator.calculateAge(getClient().getDatNaiss());
			age = ageCalc.getYears();
			if (ageCalc.getMonths() >= 6) {
				age = ageCalc.getYears() + 1;
			}
			
			tauxdeprime();
		}
	}
    
    
    public String navigateToDevisTd(){
		newDevis();
		clientBean.cancel();
		adhesion=new Adhesion();
		selectedProposition=null;
		
		desabledGenerateNotice=true;
		
		disabledall = false;
		
		return "true";
	}
    
    
    
    /****** pour STB ws *******/
    
	public boolean isDesabledGenerateNotice() {
		return desabledGenerateNotice;
	}

	public void setDesabledGenerateNotice(boolean desabledGenerateNotice) {
		this.desabledGenerateNotice = desabledGenerateNotice;
	}
	private boolean desabledGenerateNotice=true;
	
	/****************************************************/
	
	
    
  /******************************************************/  
	
    public void rechercherRefCreditDepuisWS() throws NoSuchAlgorithmException, KeyManagementException{
		
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

		 //final String BASE_URI = Proprietes.URL_REF_STB;
		 final String BASE_URI = Constante.URL_REF_STB;
		 
		 String numCompteClient = "0";
		 
      ExampleResourceClient erc = new ExampleResourceClient(RefCredit,BASE_URI);
      //System.out.println(erc.getXml());
      
      
      if (erc.HttpReponse() != 200) {
     	 System.out.println("probleme de donnees");
     	FacesMessage msg = new FacesMessage("Erreur serveur (code ="+erc.HttpReponse()+"), veuiller contacter l'administrateur.", "ERREUR");
		msg.setSeverity(FacesMessage.SEVERITY_FATAL);
		FacesContext.getCurrentInstance().addMessage("Erreur", msg);
     }
     
    else{
    	
    	
    	try{
  
    	
      Node dcNode = with(erc.getXml()).get("ArrayOfDC.DC[0]");
      
      NumberFormat nf = NumberFormat.getInstance();
      
      
      if(!dcNode.getNode("compteclient").toString().equalsIgnoreCase("null")){
		   
		   String left = dcNode.getNode("compteclient").toString().substring(5);
		   System.out.println("left "+left);
		   String right  = left.substring(0, 13);
		   System.out.println("right "+right);
		   numCompteClient = right;
	  }
      
      
       try {
		capital= !dcNode.getNode("capital").toString().equalsIgnoreCase("null") ? nf.parse(dcNode.getNode("capital").toString()).doubleValue() : 0 ;
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       franchise= !dcNode.getNode("dureefranchise").toString().equalsIgnoreCase("null") ? dcNode.getNode("dureefranchise").toString() : "" ;
       
       if(!dcNode.getNode("duration").toString().equalsIgnoreCase("null")){
    	   
    	   Period period = Period.months( Integer.parseInt(dcNode.getNode("duration").toString()) ).normalizedStandard();
           nbannee = period.getYears();
           nbmois =  period.getMonths();
    	   
       }
       
       try {
		tauxinteret = !dcNode.getNode("interetrate").toString().equalsIgnoreCase("null") ? nf.parse(dcNode.getNode("interetrate").toString()).doubleValue() : 0 ;
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       
      /* if(!dcNode.getNode("startdate").toString().equalsIgnoreCase("null")){
    	   
    	   DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    	   DateTime dt = formatter.parseDateTime(dcNode.getNode("startdate").toString());
    	   effDate = dt.toDate();
    	   
       }
       
       if(!dcNode.getNode("enddate").toString().equalsIgnoreCase("null")){
    	   
    	   DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    	   DateTime dt = formatter.parseDateTime(dcNode.getNode("enddate").toString());
    	   datefin =dt.toDate();
    	   
       }*/
       
       
       //tauxdeprime();
       
       
    	 }catch (ClassCastException e){
     		
  		   capital = 0; nbannee=0; nbmois = 0; effDate = new Date(); datefin = null; franchise = ""; tauxinteret= 0; tauxdeprime = 0; duree = 0 ; age = 0;
  		   
  		   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Données introuvables!", ""));
  		      
      		System.out.println("probleme de cast  ");
      		e.printStackTrace();
      		
      	}catch (Exception e) {
      		
      		capital = 0; nbannee=0; nbmois = 0; effDate = new Date(); datefin = null; franchise = ""; tauxinteret= 0; tauxdeprime = 0; duree = 0 ; age = 0;
      		
      		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Données introuvables!", ""));
      		
      		System.out.println("probleme de Exception  ");
      		e.printStackTrace();
			}
       
       
    }
      
      
      erc.close();
      
      clientBean.getClient().setNcompte(numCompteClient);
      tauxdeprime();
		
	}
    
    
    
    
//    public int Status = 0;
//	public int validate = 0;	
//		
//		public int getStatus() {
//			
//			System.out.println("Status= "+Status);
//			return Status;
//		}
//
//		public void setStatus(int status) {
//			Status = status;
//		}
//
//		public int getValidate() {
//			return validate;
//		}
//
//		public void setValidate(int validate) {
//			this.validate = validate;
//		}
    
    
    
    
}



