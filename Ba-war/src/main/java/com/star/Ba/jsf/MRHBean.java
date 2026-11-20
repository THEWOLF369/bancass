package com.star.Ba.jsf;

//import com.star.simulation.dao.IGarantieSimulationMrh;
//import com.star.simulation.dao.impl.GarantieSimulationMrh;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

import com.star.Ba.dao.ILocgouvDAO;
import com.star.Ba.dao.INatureRisqDAO;
import com.star.Ba.dao.INiveauScolaireDAO;
import com.star.Ba.dao.IPackDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IQualiteDAO;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Beneficiaire;
import com.star.Ba.model.Client;
import com.star.Ba.model.ComgouvMrh;
import com.star.Ba.model.Convention;
import com.star.Ba.model.DetailMrh;
import com.star.Ba.model.GarantieMrh;
//import com.star.Ba.model.GarantieSimulationAuto;
import com.star.Ba.model.Locgouv;
import com.star.Ba.model.NatureRisq;
import com.star.Ba.model.NiveauScolaire;
import com.star.Ba.model.Pack;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.ProfessionClient;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Qualite;
import com.star.Ba.model.TarifMrh;
import com.star.Ba.model.Tauxcomm;
import com.star.Ba.model.TauxgarMrh;
import com.star.Ba.model.TypeVoie;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.mrh.contrat.MRHClosed;
import com.star.Ba.mrh.contrat.MRHContract;
import com.star.Ba.mrh.contrat.MRHRenewable;
import com.star.Ba.mrh.pack.MRHEtudiant;
import com.star.Ba.mrh.pack.MRHPNO;
import com.star.Ba.mrh.pack.MRHPack;
import com.star.Ba.mrh.pack.MRHSecurite;
import com.star.Ba.mrh.pack.MRHSecuritePlus;
import com.star.Ba.mrh.pack.MRHSerenite;
import com.star.Ba.service.AssistantVoiyagetServiceInter;
import com.star.Ba.service.IParametresMrhService;


@ManagedBean(name = "mrhBean")
@SessionScoped
public class MRHBean implements Serializable {




	@ManagedProperty("#{login.utilisateur.agence}")
	private Agence agenceConnecte;
	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;

	
	
	public ClientBean getClientBean() {
		return clientBean;
	}

	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}
	
	public Client getClient() {
		return clientBean.getClient();
	}

	public void setClient(Client client) {
		this.clientBean.setClient(client);
	}

	private int codePack;


	public int getCodePack() {
		return codePack;
	}

	public void setCodePack(int codePack) {
		this.codePack = codePack;
	}

	//Client information
	private String clientCIN;
	private String clientName;
	private String clientType="PA";

	/*private boolean step1 = false, step2 = false, step3 = false, step4 = false;
	 */
	@EJB
	private IQualiteDAO iQualiteDAO;
	@EJB
	private INatureRisqDAO iNatureRisqDAO;

	@EJB
	private IPackDAO ipackdao;

	@EJB
	private INiveauScolaireDAO iNiveauScolaireDAO;

	@EJB
	private ILocgouvDAO locgouvDAO;
	@EJB
	private IProduitDAO produitDAO;
	private boolean skip;
	
	@EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;

	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;



	private String cod_societe;
	
	private long idns;

	//private Adhesion adhesion=new Adhesion();
	private Proposition proposition;
	private String propid;

	private DetailMrh detailMrh = new DetailMrh();
	List<Locgouv> allCodePostalByGouvernera =new ArrayList<Locgouv>();
	Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat = new HashMap<String, List<Locgouv>>();
	List<String> allGouverneras=new ArrayList<String>();
	private Locgouv selectedLocgouv = new Locgouv();
	private Map<String,String> codePostal;
	/*private boolean searching = false;*/


	private Beneficiaire newBeneficiaire = new Beneficiaire();
	private Adhesion newAdhesion = new Adhesion();
	
	
	 
	public Adhesion getNewAdhesion() {
		return newAdhesion;
	}

	public void setNewAdhesion(Adhesion newAdhesion) {
		this.newAdhesion = newAdhesion;
	}

	public Beneficiaire getNewBeneficiaire() {
		return newBeneficiaire;
	}

	public void setNewBeneficiaire(Beneficiaire newBeneficiaire) {
		this.newBeneficiaire = newBeneficiaire;
	}

	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}




//	public Adhesion getAdhesion() {
//		return adhesion;
//	}
//
//	public void setAdhesion(Adhesion adhesion) {
//		this.adhesion = adhesion;
//	}

	public Proposition getProposition() {

		return proposition;
	}

	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}

	public String getPropid() {
		return propid;
	}

	public void setPropid(String propid) {
		this.propid = propid;
	}
	
	@ManagedProperty(value = "#{agenceLogin.codeAgence}")
	private String codeAgence;
	@EJB
	private IParametresMrhService parametresMrhService;





	private boolean detailsPack1 = false, detailsPack2 = false, detailsPack3 = false;

	/* private ArrayList<Boolean> steps;
    private ArrayList<String> style;*/
	private int formule;
	private String test;
	private int MoreminValue;
	private int LessminValue;
	private Date aujourdhui;



	private List<Qualite> allQualite;
	private List<NatureRisq> allnatrisk ;
	
	private long idNatureRisque,idQualite;


	// Map <String,Object>	allpack = null;

	//public Map<String, Object> getAllpack() {
	//return allpack;
	//}

	//public void setAllpack(Map<String, Object> allpack) {
	//this.allpack = allpack;
	//}


//	public List<Pack> getLstpack() {
//		return lstpack;
//	}
//
//	public void setLstpack(List<Pack> lstpack) {
//		this.lstpack = lstpack;
//	}

	 

	private List<Pack> lstpack;

	List <NiveauScolaire>niveauscolaire;


	public List<NiveauScolaire> getNiveauscolaire() {
		return niveauscolaire;
	}

	public void setNiveauscolaire(List<NiveauScolaire> niveauscolaire) {
		this.niveauscolaire = niveauscolaire;
	}


	public List<NatureRisq> getAllnatrisk() {
		return allnatrisk;
	}

	public void setAllnatrisk(List<NatureRisq> allnatrisk) {
		this.allnatrisk = allnatrisk;
	}

	public List<Qualite> getAllQualite() {
		return allQualite;
	}

	public void setAllQualite(List<Qualite> allQualite) {
		this.allQualite = allQualite;
	}


	private Qualite selctedQualite = new Qualite();
	private NatureRisq selctedNaturisk = new NatureRisq();
	private Pack selctedPack = new Pack ();
	private NiveauScolaire selctedniveau = new NiveauScolaire();


	public NiveauScolaire getSelctedniveau() {
		return selctedniveau;
	}

	public void setSelctedniveau(NiveauScolaire selctedniveau) {
		this.selctedniveau = selctedniveau;
	}

	public Pack getSelctedPack() {
		return selctedPack;
	}

	public void setSelctedPack(Pack selctedPack) {
		this.selctedPack = selctedPack;
	}

	public NatureRisq getSelctedNaturisk() {
		//return iNatureRisqDAO.find(riskNature);
		return selctedNaturisk;
	}
	
	
	

	public long getIdNatureRisque() {
		return idNatureRisque;
	}

	public void setIdNatureRisque(long idNatureRisque) {
		this.idNatureRisque = idNatureRisque;
	}

	public long getIdQualite() {
		return idQualite;
	}

	public void setIdQualite(long idQualite) {
		this.idQualite = idQualite;
	}

	public void setSelctedNaturisk(NatureRisq selctedNaturisk) {
		this.selctedNaturisk = selctedNaturisk;
	}
	private boolean desabledGenerateNoticeMRH= true;
	private boolean desabledrachatMRH= false;
	private boolean desabledobjMultimediaMRH = false;


	public boolean isDesabledGenerateNoticeMRH() {
		return desabledGenerateNoticeMRH;
	}

	public void setDesabledGenerateNoticeMRH(boolean desabledGenerateNoticeMRH) {
		this.desabledGenerateNoticeMRH = desabledGenerateNoticeMRH;
	}
    
	
	public boolean isDesabledrachatMRH() {
		return desabledrachatMRH;
	}

	public void setDesabledrachatMRH(boolean desabledrachatMRH) {
		this.desabledrachatMRH = desabledrachatMRH;
	}
	
	
	

	public boolean isDesabledobjMultimediaMRH() {
		return desabledobjMultimediaMRH;
	}

	public void setDesabledobjMultimediaMRH(boolean desabledobjMultimediaMRH) {
		this.desabledobjMultimediaMRH = desabledobjMultimediaMRH;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getAdrisq() {
		return adrisq;
	}

	public void setAdrisq(String adrisq) {
		this.adrisq = adrisq;
	}

	public String getCodpost() {
		return codpost;
	}

	public void setCodpost(String codpost) {
		this.codpost = codpost;
	}

	public String getCodeAgence() {
		return codeAgence;
	}

	public void setCodeAgence(String codeAgence) {
		this.codeAgence = codeAgence;
	}

	public String getClientCIN() {
		return clientCIN;
	}

	public void setClientCIN(String clientCIN) {
		this.clientCIN = clientCIN;
	}

	public List<Pack> getPack() {
		return lstpack;
	}

	public void setPack(List<Pack> lstpack) {
		this.lstpack = lstpack;
	}
	
	
	
	
	public long getIdns() {
		return idns;
	}

	public void setIdns(long idns) {
		this.idns = idns;
	}

	private String contractType;//="renewable";
	private Date effDate;
	private Date echeanceDate;
	private Date lastEcheanceDate;
	private Date extinction;
	private int fractionnement=1;
	private String duration;
	//private String quality;
	private int nbrPieceLess;
	private int nbrPieceMore;
	private String city;
	private String riskNature;
	private double furniturePrice;
	private double area;
	private String adrisq;
	private String codpost;
	private int formulaCode;
	private boolean redemption ;
	private boolean Assh ;
	private boolean SV  ;
	private boolean RCG  ;
	private boolean JP  ;
	private boolean AS  ;
	private boolean Pansol;
	private boolean ObjM;
	private int nbrChild;
	private double valsol;
	private double valmultim;
	private double primeNette;
	private double cost;
	private double tax;
	private double primeTotal;
	private double monCom;
	private String libPack;
    private String residence;
	//report
	private MRHContract contrat;
	private MRHPack pack = null;

	private boolean searching = false;


	//report



	public String newDevis(){
		System.out.println("newDevis");
		clearFields();
		return "success";

	}

	@PostConstruct
	public void init() {
		
		System.out.println("init");
		//detailsPack1 = detailsPack2 = detailsPack3 = false;
		/* style = new ArrayList<String>();
        style.add("");
        style.add("active in");
        style.add("not-active");
        style.add("not-active");
        style.add("not-active");
        style.add("not-active");



        steps = new ArrayList<Boolean>();
        steps.add(null);
        steps.add(false);
        steps.add(false);
        steps.add(false);
        steps.add(false);
        steps.add(false);

		 */
		aujourdhui = new Date();
		effDate = aujourdhui;
		Calendar c = Calendar.getInstance();
		c.setTime(effDate);
		c.add(Calendar.YEAR, 1);
		echeanceDate = c.getTime();
		nbrPieceLess=0;
		nbrPieceMore=0;


		formulaCode = 1;


		//allQualite = iParametresMrhService.recupererAllQualite();
		//allnatrisk = iParametresMrhService.recupererAllNaturisq();
		/*List<Locgouv>  list=locgouvDAO.findAll();
		for (Locgouv locgouv : list) {
			if(mapAllCodePostalByGouvernerat.containsKey(locgouv.getGouvernora())){
				mapAllCodePostalByGouvernerat.get(locgouv.getGouvernora()).add(locgouv);
			}else {
				allGouverneras.add(locgouv.getGouvernora());
				mapAllCodePostalByGouvernerat.put(locgouv.getGouvernora(), new ArrayList<Locgouv>());
			}


		}*/

		allGouverneras = locgouvDAO.findGouv();



		lstpack=ipackdao.getPack_by_codprod(produitDAO.getProduit_by_code(ENUMCODEPRODUIT.MRH.getCode()));
		//formule =

		niveauscolaire =  iNiveauScolaireDAO.findAll();
		allQualite = iQualiteDAO.findAll();
		allnatrisk = iNatureRisqDAO.findAll();
		
		
		
		/***** initialisation client et adherent *****/
		newAdhesion = new Adhesion();
		newAdhesion.setNombrePersonne(BigDecimal.valueOf(0));
		 
		clientBean.setClient(new Client());
		newProposition = null;
		
		
		Convention convention = serviceAssistantVoiyagetService
				.getConventionBySociete(utilisateur.getAgence().getSociete());

		Produit produit = serviceAssistantVoiyagetService
				.getProduitByCode(ENUMCODEPRODUIT.MRH.getCode());
		
		setAllPoliseByCodeAndConvention(serviceAssistantVoiyagetService
				.getPolice_by_conv_prod(convention, produit));
		
		
		desabledGenerateNoticeMRH = true;
		
		
		contractType = "Renouvelable";
		
		
	}
	//    public int getMoreminValue() {
	//        if(nbrPieceLess>0) { 
	//            MoreminValue=0;
	//        }
	//        else {
	//            MoreminValue=1;
	//        } 
	//        System.out.println("***************nombre de piece less="+nbrPieceLess+"******************MoreminValue="+MoreminValue);
	//        return MoreminValue;
	//    }
	//
	//    public void setMoreminValue(int MoreminValue) {
	//        this.MoreminValue = MoreminValue;
	//    }
	//
	//    public int getLessminValue() {
	//        if(nbrPieceMore>0) { 
	//            LessminValue=0;
	//        }
	//        else {
	//            LessminValue=1;
	//        } 
	//        System.out.println("***************nombre de piece more="+nbrPieceMore+"******************LessminValue="+LessminValue);
	//        return LessminValue;
	//    }

	//    public void setLessminValue(int LessminValue) {
	//        this.LessminValue = LessminValue;
	//    }



   
	public double getPrimeNette() {
		return primeNette;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public void setPrimeNette(double primeNette) {
		this.primeNette = primeNette;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getPrimeTotal() {
		return primeTotal;
	}

	public void setPrimeTotal(double primeTotal) {
		this.primeTotal = primeTotal;
	}

	public double getMonCom() {
		return monCom;
	}

	public void setMonCom(double monCom) {
		this.monCom = monCom;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public Date getEcheanceDate() {
		return echeanceDate;
	}

	public void updateEcheanceDate() {
		this.echeanceDate = this.effDate;
	}



	public void handleDateSelect(SelectEvent event) {
		if (contractType.equals("Ferme")) {
			Date date = (Date) event.getObject();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.YEAR, 1);
		}
	}

	//  public void setEcheanceDate(Date echeanceDate){ //throws ParseException {
	//if (echeanceDate != null && echeanceDate.length() == 10) {
	// SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
	// this.echeanceDate = sf.parse(echeanceDate);
	// this.echeanceDate = echeanceDate;
	//}
	// }

	public Date getLastEcheanceDate() {
		return lastEcheanceDate;
	}

	public void setLastEcheanceDate(Date lastEcheanceDate) /*throws ParseException*/ {
		//        if (lastEcheanceDate != null && lastEcheanceDate.length() == 10) {
		//            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		this.lastEcheanceDate = lastEcheanceDate ;//sf.parse(lastEcheanceDate);
		//        }
	}

	public Date getExtinction() {
		return extinction;
	}

	//    public void setExtinction(String extinction) throws ParseException {
	//        if (extinction != null && extinction.length() == 10) {
	//            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
	//            this.extinction = sf.parse(extinction);
	//        }
	//    }



	public int getFractionnement() {
		return fractionnement;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}






	public Qualite getSelctedQualite() {
		return selctedQualite;
	}

	public void setSelctedQualite(Qualite selctedQualite) {
		this.selctedQualite = selctedQualite;
	}

	public int getNbrPieceLess() {
		return nbrPieceLess;
	}

	public void setNbrPieceLess(int nbrPieceLess) {
		this.nbrPieceLess = nbrPieceLess;
	}

	public int getNbrPieceMore() {
		return nbrPieceMore;
	}

	public void setNbrPieceMore(int nbrPieceMore) {
		this.nbrPieceMore = nbrPieceMore;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRiskNature() {
		return riskNature;
	}

	public void setRiskNature(String riskNature) {
		this.riskNature = riskNature;
	}

	public double getFurniturePrice() {
		return furniturePrice;
	}

	public void setFurniturePrice(double furniturePrice) {
		this.furniturePrice = furniturePrice;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getFormulaCode() {
		return formulaCode;
	}

	public void setFormulaCode(int formulaCode) {
		this.formulaCode = formulaCode;
	}

	public boolean isRedemption() {
		return redemption;
	}

	public void setRedemption(boolean redemption) {
		this.redemption = redemption;
	}

	public boolean isAssh() {
		return Assh;
	}

	public void setAssh(boolean Assh) {
		this.Assh = Assh;
	}

	public boolean isSV() {
		return SV;
	}

	public void setSV(boolean SV) {
		this.SV = SV;
	}

	public boolean isRCG() {
		return RCG;
	}

	public void setRCG(boolean RCG) {
		this.RCG = RCG;
	}

	public boolean isJP() {
		return JP;
	}

	public void setJP(boolean JP) {
		this.JP = JP;
	}

	public boolean isAS() {
		return AS;
	}

	public void setAS(boolean AS) {
		this.AS = AS;
	}

	
	public boolean isPansol() {
		return Pansol;
	}

	public void setPansol(boolean pansol) {
		Pansol = pansol;
	}

	public boolean isObjM() {
		return ObjM;
	}

	public void setObjM(boolean objM) {
		ObjM = objM;
	}
    
	
	public double getValsol() {
		return valsol;
	}

	public void setValsol(double valsol) {
		this.valsol = valsol;
	}
	
	

	public double getValmultim() {
		return valmultim;
	}

	public void setValmultim(double valmultim) {
		this.valmultim = valmultim;
	}

	public int getNbrChild() {
		return nbrChild;
	}

	public void setNbrChild(int nbrChild) {
		this.nbrChild = nbrChild;
	}

	public void setEcheanceDate(Date echeanceDate) {
		this.echeanceDate = echeanceDate;
	}

	public void setExtinction(Date extinction) {
		this.extinction = extinction;
	}

	public void setFractionnement(int fractionnement) {
		this.fractionnement = fractionnement;
	}




	public void checkForm() {

		boolean error = false;
		// System.out.println( "effdate" + effDate);
		// System.out.println("nbr peieces inf:" +this.nbrPieceLess);
		//System.out.println("nbr pieces sup " + getNbrPieceMore());
		// System.out.println("valeur mobilier" + getFurniturePrice());
		/* if (effDate == null) {
            FacesMessage msg = new FacesMessage("Date Effet", "Date effet est obligatoire.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
            FacesContext.getCurrentInstance().validationFailed();
            error = true;

        }

        if (nbrPieceLess + nbrPieceMore == 0) {
            FacesMessage msg = new FacesMessage("Nombres de piÃ©ces", "Nombre de piÃ©ces Obligatoire, le total ne doit pas etre zero.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
            FacesContext.getCurrentInstance().validationFailed();
            error = true;
        }

        if (furniturePrice == 0) {
            FacesMessage msg = new FacesMessage("Valeur Mobilier", "Valeur Mobilier Obligatoire, le valuer ne doit pas etre zero.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
            FacesContext.getCurrentInstance().validationFailed();
            error = true;
        }

        if (AS && nbrChild == 0) {
            FacesMessage msg = new FacesMessage("Assurance Scolaire", "Nombre d'enfants Obligatoire, le valuer ne doit pas etre zero.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
            FacesContext.getCurrentInstance().validationFailed();
            error = true;
        }*/

		//city= detailMrh.getGouvernora();


		if(error){
             
			//getSelctedNaturisk();

			System.out.println("contractType:" + contractType);
			System.out.println("duration:" + duration);
			System.out.println("selctedPack "+selctedPack.getIdPack());
			System.out.println("selctedPack label "+selctedPack.getLibelle());
			System.out.println("qualite "+selctedQualite.getQualite());
			System.out.println("libelleNature "+selctedNaturisk.getLibelleNature());
			System.out.println("idNature "+selctedNaturisk.getIdRiskMrh());


			System.out.println("redemption "+redemption);
			System.out.println("assh "+Assh);
			
			System.out.println("Sejours et Voyages "+SV);
			System.out.println("R.C.Gens de Maison "+RCG);
			System.out.println("Jardin et Piscine "+JP);
			System.out.println("Assurance Scolaire "+AS);
			
//			System.out.println("getNiveauScolaire "+ newBeneficiaire.getNiveauScolaire().getIdNiveauScolaire());
//			System.out.println("getNiveauScolaire "+ newBeneficiaire.getNiveauScolaire().getNiveauScol());

		}


		if (!error) {
			int nbrPieceTotal = nbrPieceLess + (2 * nbrPieceMore);
			if (area > 25) {
				nbrPieceTotal += (area / 25);
			}

			
			this.contrat = null;

			int code_pack =  (int) newAdhesion.getPack().getIdPack();//selctedPack.getIdPack();
			TarifMrh tarif805 =  getParamPrices();
			System.out.println("code pack rym:" + code_pack);

			switch (code_pack) {
			case 1:
				pack = new MRHSecurite(Assh, Pansol,valsol,redemption, nbrPieceTotal, furniturePrice, tarif805);
				//code_pack = "01";
				libPack = "pack1";
				break;
			case 2:
				pack = new MRHSecuritePlus(Assh,Pansol,valsol,redemption, nbrPieceTotal, furniturePrice, tarif805);
				//code_pack = "02";
				libPack = "pack2";
				break;
			case 3:
				pack = new MRHSerenite(SV, RCG, JP, AS, Pansol,ObjM ,valsol,nbrChild, redemption, nbrPieceTotal, furniturePrice, tarif805);
				// code_pack = "03";
				libPack = "pack3";
				break;
			case 4:
				pack = new MRHEtudiant(redemption,nbrPieceTotal,furniturePrice,tarif805);
			
				// code_pack = "4";
				libPack = "pack4";
				break;
			case 5:
				pack = new MRHPNO(JP,Pansol,valsol,redemption,nbrPieceTotal,furniturePrice,tarif805);

				// code_pack = "5";
				libPack = "pack5";
				break;
			}
			System.out.println("libelle pack:" + libPack);

			if (contractType.equals("Renouvelable")) {
				contrat = new MRHRenewable(echeanceDate, fractionnement, contractType,
						effDate, clientType, selctedQualite.getQualite(), nbrPieceLess, nbrPieceMore,
						newAdhesion.getGouver(), selctedNaturisk.getLibelleNature(), area, pack);
				
				System.out.println("echeanceDate "+echeanceDate);
				System.out.println("fractionnement "+fractionnement);
				System.out.println("contractType "+contractType);
				System.out.println("effDate "+effDate);
				System.out.println("clientType "+clientType);
				System.out.println("selctedQualite.getQualite() "+selctedQualite.getQualite());
				System.out.println("nbrPieceLess "+nbrPieceLess);
				System.out.println("nbrPieceMore "+nbrPieceMore);
				System.out.println("newAdhesion.getGouver() "+newAdhesion.getGouver());
				System.out.println("selctedNaturisk.getLibelleNature() "+selctedNaturisk.getLibelleNature());
				System.out.println("area "+area);
				System.out.println("pack "+pack);
				
				
			} else if (contractType.equals("Ferme")) {
				contrat = new MRHClosed(lastEcheanceDate, extinction, duration,
						contractType, effDate, clientType, selctedQualite.getQualite(), nbrPieceLess,
						nbrPieceMore, newAdhesion.getGouver(), selctedNaturisk.getLibelleNature(), area, pack);

			}

			try {
				List<TauxgarMrh> listTaux = getTauxGarantie(libPack);
				System.out.println("listTaux =" + listTaux.size());

				for(TauxgarMrh xx : listTaux){
					System.out.println("affihage tableau "+xx.getCodeGar());

				}

				int taux = getTauxGouv(newAdhesion.getGouver());
				System.out.println("taux =" + taux);
				if (newAdhesion.getPack().getIdPack() != 4 ){
				primeNette = contrat.getPrimeNette(listTaux, taux);
				}
				else
				primeNette = contrat.getPrimeNette(listTaux, 100);
			
				System.out.println("primeNette =" + primeNette);


				//SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
				//if(effDate.before( sf.parse("01/01/2016")) )
				// {
				// primeNette=primeNette*0.9166666666666667;
				//}
				Produit produit = getProduit();
				cost = contrat.getCost(produit);
				tax = contrat.getTax(primeNette, cost, produit);
				primeTotal = contrat.getPrimeTotal(primeNette, cost, tax);
				System.out.println("primeTotal =" + primeTotal);
				//Tauxcomm tauxcomm = getTauxCom();
				//monCom = contrat.getCommision(primeNette, cost, tauxcomm);

				//contrat.insertDatabase(clientCIN, codeAgence, primeNette, cost, tax,
				// primeTotal, monCom, code_pack);
				
				//System.out.println("primeNetteRC "+pack.getPrimeNetteRC());
				
				
			} catch (Exception ex) {
				Logger.getLogger(MRHBean.class.getName()).log(Level.SEVERE, null, ex);

			}
		}
	}


	 public void generateReport() {
	        if (this.contrat != null) {
	            System.out.println("*******************************************generateReport");
	            
	            clientName = getClient().getNomClient()+" "+getClient().getPrenomClient();
	            contrat.generateReport(primeNette, tax, cost, primeTotal, clientName, "PA", null);
	            
	        }
	    }
	 
	 
	 
	 
	 
	 
	 

	public void clearFields() {
		
		System.out.println("clearFields");
		
		init();



		// contractType="renewable";


		fractionnement = 1;
		contractType = "Renouvelable";
		duration = "Annuelle";
		lastEcheanceDate = null;
		//quality = "";
		nbrPieceLess = 0;
		nbrPieceMore = 0;
		city = "";
		newAdhesion.setGouver(null) ;
		//riskNature = "";
		furniturePrice = 0;
		area = 0;
		formulaCode = 1;
		//redemption = false;
		//Assh = false;
		SV = false;
		RCG = false;
		JP = false;
		AS = false;
		nbrChild = 0;
		primeNette = 0;
		cost = 0;
		tax = 0;
		primeTotal = 0;
		monCom = 0;
        residence = "Principale";
        idQualite = 1;
        idNatureRisque =1;
        selctedPack.setIdPack(1);
		//changeDetailsPack1();// pack1 par defaut
		
		adrisq = null;
		selectedLocgouv = new Locgouv();
		residence = "Principale";
		desabledrachatMRH = false;
		desabledobjMultimediaMRH = false;
		redemption = false;
		Assh = false;
		ObjM = false;
		Pansol = false;
		valsol = 0;
		valmultim = 0;
		desabledGenerateNoticeMRH= true;
        noneException = false;
		
		 newProposition = null;
		 

	}

	public boolean imprimer() {
		if (primeNette != 0.0) {
			return false;
		} else {
			return true;
		}
	}
	//Class interface









	//Appels base
	public TarifMrh getParamPrices() {
		TarifMrh tarif805 = parametresMrhService.getTarifMrh();
		return tarif805;
	}

	public List<TauxgarMrh> getTauxGarantie(String packName) {
		return parametresMrhService.getTauxGarMrhByPack(packName);
	}

	public Tauxcomm getTauxCom() {
		return parametresMrhService.getTauxCom(ENUMCODEPRODUIT.MRH.getCode());
	}

	public Produit getProduit() {
		return parametresMrhService.getProduitByCode(ENUMCODEPRODUIT.MRH.getCode());
	}

	public int getTauxGouv(String gouv) {
		ComgouvMrh comgouv1 = parametresMrhService.getTauxGouvByGouv(newAdhesion.getGouver());
		if (comgouv1!= null) {
			return comgouv1.getTaux();
		}

		return 0;
	}




	//    private String getTypeClientByCode(String code) {
	//        return parametresMrhService.getTypeClientByCode(code);
	//    }



//	public void setClient(Client client) {
//		//clientBean.getClient() = client;
//		clientCIN = client.getIdentifiant();
//		clientName = client.getNomClient()+" "+client.getPrenomClient(); 
//		//        clientType = client.getCodtypclt();
//		
//		
//	}






	public void setAgenceConnecte(Agence agenceConnecte) {
		this.agenceConnecte = agenceConnecte;
	}

	public void changeDetailsPack1() {
		if (detailsPack1) {
			this.detailsPack1 = false;
		} else {
			this.detailsPack1 = true;
		}

	}

	public void changeDetailsPack2() {
		if (detailsPack2) {
			this.detailsPack2 = false;
		} else {
			this.detailsPack2 = true;
		}
	}

	public void changeDetailsPack3() {
		if (detailsPack3) {
			this.detailsPack3 = false;
		} else {
			this.detailsPack3 = true;
		}
	}

	public boolean isDetailsPack1() {
		return detailsPack1;
	}

	public void setDetailsPack1(boolean detailsPack1) {
		this.detailsPack1 = detailsPack1;
	}

	public boolean isDetailsPack2() {
		return detailsPack2;
	}

	public void setDetailsPack2(boolean detailsPack2) {
		this.detailsPack2 = detailsPack2;
	}

	public boolean isDetailsPack3() {
		return detailsPack3;
	}

	public void setDetailsPack3(boolean detailsPack3) {
		this.detailsPack3 = detailsPack3;
	}

	public void updateDate() {
		if (contractType.equals("Ferme")) { // Mouna: Contrat ferme
			Calendar c = Calendar.getInstance();
			c.setTime(effDate);
			c.add(Calendar.YEAR, 1);
			c.add(Calendar.DAY_OF_MONTH, -1);
			extinction = c.getTime();
			echeanceDate = null;
			System.out.println("date extinction= "+extinction);
		} else {  // Mouna: Contrat renouvelable
			Calendar c = Calendar.getInstance();
			c.setTime(effDate);
			c.add(Calendar.YEAR, 1);
			echeanceDate = c.getTime();
			extinction = null;
			duration ="Annuelle";
			System.out.println("date echeanceDate= "+echeanceDate);
		}

	}
	public void formule(int formule) {

		formulaCode = formule;
		//redemption = false;
		//Assh = false;
		SV = false;
		RCG = false;
		JP = false;
		AS = false;
		nbrChild=0;
		ObjM = false;
		Pansol = false;
	}



	public Date getAujourdhui() {
		return aujourdhui;
	}



	public DetailMrh getDetailMrh() {
		return detailMrh;
	}

	public void setDetailMrh(DetailMrh detailMrh) {
		this.detailMrh = detailMrh;
	}


	public List<Locgouv> getAllCodePostalByGouvernera() {
		return allCodePostalByGouvernera;
	}

	public void setAllCodePostalByGouvernera(List<Locgouv> allCodePostalByGouvernera) {
		this.allCodePostalByGouvernera = allCodePostalByGouvernera;
	}

	public void updateListCodePostalActionListener(){	
		
		System.out.println("gov bis");

		if(newAdhesion.getGouver() !=null && (!newAdhesion.getGouver().equals("")))
		{
			//allCodePostalByGouvernera.clear();
			allCodePostalByGouvernera=new  ArrayList<Locgouv>();
			newAdhesion.setCodeP(null);
			newAdhesion.setLocalite(null);
			selectedLocgouv = new Locgouv();
			allCodePostalByGouvernera = locgouvDAO.findByGouv(newAdhesion.getGouver());// (mapAllCodePostalByGouvernerat.get(newAdhesion.getGouver()));
			System.out.println("gov1");

		}
		else{
			//allCodePostalByGouvernera.clear();
			allCodePostalByGouvernera=new  ArrayList<Locgouv>();
			newAdhesion.setCodeP(null);
			newAdhesion.setLocalite(null);
			newAdhesion.setGouver(null);
			//selectedLocgouv = new Locgouv();
			System.out.println("gov2");

		}
		
		System.out.println("newAdhesion.getGouver() "+newAdhesion.getGouver());
		System.out.println("newAdhesion.getCodeP() "+newAdhesion.getCodeP());
		System.out.println("newAdhesion.getLocalite() "+newAdhesion.getLocalite());
		System.out.println("selectedLocgouv "+selectedLocgouv.getCodePosta());
		System.out.println("allCodePostalByGouvernera "+allCodePostalByGouvernera.size());


	}
	
	
	/***hoss***/
	public void locgouvSelectListened( ) {

		System.out.println("sys1 bis");

		searching = true;
		selectedLocgouv = locgouvDAO.findByCode((getSelectedLocgouv().getCode()));
		Locgouv locgouv = selectedLocgouv;
		if (locgouv != null) {
			newAdhesion.setCodeP(locgouv.getCodePosta());
			newAdhesion.setLocalite(locgouv.getLocalite());
			//newAdhesion.setGouver(locgouv.getGouvernora());
			System.out.println("sys2 bis "+locgouv);
		} else {
			newAdhesion.setCodeP(null);
			newAdhesion.setLocalite(null);
			//newAdhesion.setGouver("");
				System.out.println("sys3 bis "+locgouv);
		}
		
		System.out.println("newAdhesion.getGouver() "+newAdhesion.getGouver());
		System.out.println("newAdhesion.getCodeP() "+newAdhesion.getCodeP());
		System.out.println("newAdhesion.getLocalite() "+newAdhesion.getLocalite());
		System.out.println("selectedLocgouv "+selectedLocgouv.getCodePosta());
	}



	public Map<String, List<Locgouv>> getMapAllCodePostalByGouvernerat() {
		return mapAllCodePostalByGouvernerat;
	}

	public void setMapAllCodePostalByGouvernerat(
			Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat) {
		this.mapAllCodePostalByGouvernerat = mapAllCodePostalByGouvernerat;
	}

	public List<String> getAllGouverneras() {
		return allGouverneras;
	}

	public void setAllGouverneras(List<String> allGouverneras) {
		this.allGouverneras = allGouverneras;
	}


	public Locgouv getSelectedLocgouv() {
		return selectedLocgouv;
	}

	public void setSelectedLocgouv(Locgouv selectedLocgouv) {
		this.selectedLocgouv = selectedLocgouv;
	}


	

	
	//public void loadProposition() {
		//		System.out.println("loading");
		//		
		//
		//		String newDevis = FacesContext.getCurrentInstance()
		//				.getExternalContext().getRequestParameterMap().get("newDevis");

		//		if (getSelectedProposition()!=null) {
		//			
		//			try {
		//				
		//System.out.println("enter 1");
		//				// client = proposition.getClient();
		//				clientBean.setClient(getSelectedProposition().getClient());
		//
		//				effDate = getSelectedProposition().getDatDebut();
		//				datefin = getSelectedProposition().getDatFin();
		//				capital = getSelectedProposition().getCapital();
		//				adhesion.setAdresse(getSelectedProposition().getAdresse());
		//				selectedLocgouv = locgouvDAO.findByPostaCode(getSelectedProposition().getCodeP());
		//				adhesion.setLocalite(getSelectedProposition().getLocalite());
		//				adhesion.setGouver(getSelectedProposition().getGouver());
		//
		//				nbannee = (int) getSelectedProposition().getDuree();
		//				double mois= (getSelectedProposition().getDuree()- nbannee); 
		//				nbmois=(int) (100*mois);
		//				selectedProposition=null;
		//				} catch (Exception e) {
		//				e.printStackTrace();
		//			}
		//		} else  if (newDevis != null && !newDevis.isEmpty()
		//				&& Boolean.parseBoolean(newDevis)) {
		//			System.out.println("enter 2");
		//
		//			newDevis();
		//			clientBean.cancel();
		//			adhesion=new Adhesion();
		//			selectedProposition=null;
		//		}
		//		calculAge();
	//}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {

		//    	System.out.println("Current wizard step:" + event.getOldStep());
		//    	System.out.println("Next step:" + event.getNewStep());


		if(event.getNewStep().equals("tarif")){

			checkForm();
			
		}
		
		
		if(event.getNewStep().equals("garanties")){
			
			newAdhesion.setPack(getTypePack(selctedPack.getIdPack()));
		}
		
		
		
		if(event.getNewStep().equals("risque")){
			
			System.out.println("idQualite "+idQualite);
			System.out.println("idNatureRisque "+idNatureRisque);
			System.out.println("residence "+residence);
			
			selctedQualite = iQualiteDAO.find(idQualite);
			selctedNaturisk = iNatureRisqDAO.find(idNatureRisque);
		}
		

		if(event.getOldStep().equals("tarif")){

			System.out.println(event.getOldStep().toString());
			
			newAdhesionToPrint = null;
			newProposition= null;
			
		}
		
		 


		//    	if(event.getNewStep().equals("risque")){
		//    		
		//    		  if (nbrPieceLess + nbrPieceMore == 0) {
		//    	            FacesMessage msg = new FacesMessage("Nombres de pièces", "Nombre de pièces Obligatoire, le total ne doit pas être zero.");
		//    	            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		//
		//    	            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		//    	            FacesContext.getCurrentInstance().validationFailed();
		//    	             
		//    	        }
		//    		
		//    	}



		if(skip) {
			skip = false;   //reset in case user goes back
			return "confirm";
		}
		else {
			return event.getNewStep();
		}
	}


	public void validateNumber(FacesContext context, UIComponent component, Object value) throws ValidatorException
	{
		int more=(int)value;
		System.out.println("nbrPieceMore" + more);


		UIInput bbvolInput = (UIInput) context.getViewRoot().findComponent((String) component.getAttributes().get("bbv_value"));
		// Get its value, the entered value of the first field.
		int less = (int) bbvolInput.getValue();
		System.out.println("nbrPieceLess" + less);

		FacesMessage msg = new FacesMessage("Nombre de pièces Obligatoire, le total ne doit pas être zero.");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);

		if (less + more == 0  ) throw new ValidatorException(msg);
		// some more validation code
	}

	public void validatePassword(ComponentSystemEvent event) {

		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputPassword = (UIInput) components.findComponent("txtnbrPieceLess");
		int password = (int) (uiInputPassword.getLocalValue() == null ? 0
				: uiInputPassword.getLocalValue());
		String passwordId = uiInputPassword.getClientId();

		// get confirm password
		UIInput uiInputConfirmPassword = (UIInput) components.findComponent("txtnbrPieceMore");
		int confirmPassword = (int) (uiInputConfirmPassword.getLocalValue() == null ? 0
				: uiInputConfirmPassword.getLocalValue());



		if (password + confirmPassword == 0) {

			FacesMessage msg = new FacesMessage("Password must match confirm password");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(passwordId, msg);
			fc.renderResponse();

		}

	}
	
	
	 public NiveauScolaire getBeer(Integer id) {
	        if (id == null){
	            throw new IllegalArgumentException("no id provided");
	        }
	        for (NiveauScolaire beer : niveauscolaire){
	            if (id.equals(beer.getIdNiveauScolaire())){
	                return beer;
	            }
	        }
	        return null;
	    }
	
	 
	 
	 
	 public Converter getNiveauConverter() {
			return new Converter() {

				@Override
				public Object getAsObject(FacesContext context,
						UIComponent component, String value) {
					for (NiveauScolaire obj : getNiveauscolaire()) {
						if (obj.getNiveauScol().equals(value)) {
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
	 
	 
	 public Converter getRiskConverter() {
			return new Converter() {

				@Override
				public Object getAsObject(FacesContext context,
						UIComponent component, String value) {
					for (NatureRisq obj : getAllnatrisk()) {
						if (obj.getLibelleNature().equals(value)) {
							System.out.println("return obj "+obj.getLibelleNature());
							return obj;
						}
					}
					System.out.println("return null");
					return null;
				}

				@Override
				public String getAsString(FacesContext context,
						UIComponent component, Object value) {
					System.out.println("value.toString() "+value.toString());
					return value.toString();
				}
			};
		}
	 
	 
	 
	 public Converter getQualiteConverter() {
			return new Converter() {

				@Override
				public Object getAsObject(FacesContext context,
						UIComponent component, String value) {
					for (Qualite obj : getAllQualite()) {
						if (obj.getQualite().equals(value)) {
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
	 
	 public Converter getPackConverter() {
			return new Converter() {

				@Override
				public Object getAsObject(FacesContext context,
						UIComponent component, String value) {
					System.out.println("value "+value);
					for (Pack obj : getPack()) {
						if (obj.getLibelle().equals(value)) {
							System.out.println("obj.getLibelle() "+obj.getLibelle());
							return obj;
						}
					}
					return null;
				}

				@Override
				public String getAsString(FacesContext context,
						UIComponent component, Object value) {
					System.out.println("value.toString() "+value.toString());
					return value.toString();
				}
			};
		}
	 
	 
//		public Converter getPackConverter_() {
//			return new Converter() {
//
//				@Override
//				public Object getAsObject(FacesContext context,
//						UIComponent component, String value) {
//					for (Pack professionClient : getPack()) {
//
//						if (professionClient.getIdPack().toString()
//								.equals(value)) {
//							return professionClient;
//						}
//					}
//					return null;
//				}
//
//				@Override
//				public String getAsString(FacesContext context,
//						UIComponent component, Object value) {
//					if (value != null)
//						return value.toString();
//					else
//						return null;
//
//				}
//			};
//		}
	 
	
	 
	 public void addNewBeneficiaireTolist() {
		
		 
			
			if (newAdhesion.getBeneficiaires().size() < newAdhesion.getNombrePersonne().intValue()) {
				
				
				Calendar c = Calendar.getInstance();
				c.setTime(getAujourdhui());
				Calendar c1 = Calendar.getInstance();
				c1.setTime(newBeneficiaire.getDateNaiss());
				
				if(c.get(Calendar.DAY_OF_YEAR) < c1.get(Calendar.DAY_OF_YEAR)){
					
					FacesMessage msg = new FacesMessage("Attention!","La date de naissance doit être inférieur à la date du jour!");
		            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
		            FacesContext.getCurrentInstance().addMessage(null, msg);
				}
				else{
				
				
				
				newAdhesion.setClient(clientBean.getClient());
				newBeneficiaire.setAdhesion(newAdhesion);  ///////////// à changer par beneficiare
				//newAdherent.setClient(clientBean.getClient());
				newAdhesion.getBeneficiaires().add(newBeneficiaire);
				newBeneficiaire = new Beneficiaire();
				
				}

			} else {
				FacesMessage msg = new FacesMessage("Attention!",
						"le nombre maximaum des enfants à ajouter est "
								
								+ newAdhesion.getNombrePersonne().intValue());
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		 
		
		}
	 
	 private int nombreBenificiaireAajouter=0;

		public int getNombreBenificiaireAajouter() {
			 newAdhesion.setNombrePersonne(new BigDecimal( nbrChild)); 
			nombreBenificiaireAajouter=newAdhesion.getNombrePersonne().intValue()-newAdhesion.getBeneficiaires().size();
			return nombreBenificiaireAajouter;
		}

		public void setNombreBenificiaireAajouter(int nombreBenificiaireAajouter) {
			this.nombreBenificiaireAajouter = nombreBenificiaireAajouter;
		}
		
		Beneficiaire selectedBeneficiaireToUpdate = new Beneficiaire();

		public Beneficiaire getSelectedBeneficiaireToUpdate() {
			return selectedBeneficiaireToUpdate;
		}

		public void setSelectedBeneficiaireToUpdate(Beneficiaire selectedBeneficiaireToUpdate) {
			this.selectedBeneficiaireToUpdate = selectedBeneficiaireToUpdate;
		}
	
		public void deleteBenificiaireFromList() {

			newAdhesion.getBeneficiaires().remove(selectedBeneficiaireToUpdate);
			

		}
		
		
		
		
		
		/******* insertion proposition****************/
		
		private boolean noneException = false;
		
		public Proposition newProposition;

		public Proposition getNewProposition() {
			return newProposition;
		}

		public void setNewProposition(Proposition newProposition) {
			this.newProposition = newProposition;
		}
		
		

		public void validerProposition(){
			
			/**** si adhesion unique sans proposition ***/
			//if(newProposition == null){
				
				Operation();
			
			//}
			
			
			if (client.getIdentifiant() == null
					|| client.getIdentifiant().isEmpty()) {
				noneException = false;
				FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			}else{
				
				
				newAdhesionToPrint = parametresMrhService.saveAdhesion(client, adhesion,newProposition,
						garantieMrh1,garantieMrh2,garantieMrh3,garantieMrh10,garantieMrh11,
						garantieMrh4,garantieMrh5,garantieMrh6,garantieMrh7,
						garantieMrh8,garantieMrh9,garantieMrh12,garantieMrh13,garantieMrh14,garantieMrh15,garantieMrh16,garantieMrh17,
						utilisateur, newAdhesion.getPack().getIdPack());
				
				
				if (newAdhesionToPrint != null){
					
					noneException = true;
					
					desabledGenerateNoticeMRH = false;
					System.out.println("rym arous "+newAdhesionToPrint.getNadhesion());
					JsfUtil.addSuccessMessage(
							"Adhesion enregistrée avec le numéro: "
									+ newAdhesionToPrint.getNadhesion(), "!");
					
				}

			}
			
			
			 
			
		}

		
		public void generateDevisMRH() {

			
			Operation();
			
			
			if (client.getIdentifiant() == null
					|| client.getIdentifiant().isEmpty()) {
				noneException = false;
				FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			} else {

				if (newProposition == null) {
					
					System.out.println("1 "+detailMrh.getNbrpieceless());
					System.out.println("2 "+detailMrh.getNbrpiecemore());
					System.out.println("3 "+detailMrh.getFurnitureprice());
					System.out.println("4 "+detailMrh.getArea());
					System.out.println("5 "+adhesion.getGouver());
					System.out.println("6 "+adhesion.getAdresse());
					System.out.println("7 "+adhesion.getCodeP());
					System.out.println("8 "+adhesion.getLocalite());
					System.out.println("9 "+detailMrh.getRachIn());
					System.out.println("10 "+adhesion.getNombrePersonne());
					
				 
					
					
					newProposition = parametresMrhService.saveProposition(client, adhesion,
							garantieMrh1,garantieMrh2,garantieMrh3,garantieMrh10,garantieMrh11,
							garantieMrh4,garantieMrh5,garantieMrh6,garantieMrh7,
							garantieMrh8,garantieMrh9,garantieMrh12,garantieMrh13,garantieMrh14,garantieMrh15,garantieMrh16,garantieMrh17,
							utilisateur, newAdhesion.getPack().getIdPack());
					
					
					
					if (newProposition != null){
						
						noneException = true;
						System.out.println("rym arous "+newProposition.getnProposition());
						JsfUtil.addSuccessMessage(
								"Proposition enregistrée avec le numéro: "
										+ newProposition.getnProposition(), "!");
						
					}
					
				}
				
				
//				imprimerDevisAssistanceVoiyage(adhesion.getPolice().getNPolice(),
//						client.getIdentifiant(), client.getNomClient(),
//						client.getPrenomClient(), adhesion.getDatDebut(),
//						String.valueOf(adhesion.getDuree()), adhesion.getDatFin(),
//						adhesion.getTypeAbonnement().getValTypeAbonnement(),
//
//						adhesion.getMtPrime(),
//						utilisateur.getAgence().getLibelle(), "../images/"
//								+ utilisateur.getAgence().getSociete()
//										.getCodSociet() + ".png", adhesion
//								.getNombrePersonne().toString(),
//						newProposition.getNumeroProposition());
				//noneException = true;

//				FacesMessage msg = new FacesMessage("Succes",
//						" Succes Insert In proposition and generate devis");
//				msg.setSeverity(FacesMessage.SEVERITY_INFO);

			}
			
			if (noneException == true){
				generateReport();
			}
			 
		}
		
		
		
		
		 Adhesion newAdhesionToPrint ;
		 
		 public Adhesion getNewAdhesionToPrint() {
				return newAdhesionToPrint;
			}
			public void setNewAdhesionToPrint(Adhesion newAdhesionToPrint) {
				this.newAdhesionToPrint = newAdhesionToPrint;
			}
			
			
			
			private List<Police> allPoliseByCodeAndConvention = new ArrayList<Police>();

			public List<Police> getAllPoliseByCodeAndConvention() {
				return allPoliseByCodeAndConvention;
			}

			public void setAllPoliseByCodeAndConvention(List<Police> allPoliseByCodeAndConvention) {
				this.allPoliseByCodeAndConvention = allPoliseByCodeAndConvention;
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
			
			
	
	/***** pour proposition et adhesion ***********/		
			
	private Adhesion adhesion;
	private Client client;
	private GarantieMrh garantieMrh1 = null;
	private GarantieMrh garantieMrh2 = null;
	private GarantieMrh garantieMrh3 = null;
	private GarantieMrh garantieMrh10 = null;
	private GarantieMrh garantieMrh11 = null;
	
	
	private GarantieMrh garantieMrh4 = null;
	private GarantieMrh garantieMrh5 = null;
	private GarantieMrh garantieMrh6 = null;
	private GarantieMrh garantieMrh7 = null;
	
	private GarantieMrh garantieMrh8 = null;
	private GarantieMrh garantieMrh9 = null;
	private GarantieMrh garantieMrh12 = null;
	private GarantieMrh garantieMrh13 = null;
	private GarantieMrh garantieMrh14 = null;
	private GarantieMrh garantieMrh15 = null;
	private GarantieMrh garantieMrh16 = null;
	private GarantieMrh garantieMrh17 = null;
	public void Operation(){
		
		
		 adhesion = getNewAdhesion();
		adhesion.setEtat("V");
		adhesion.setDatDebut(effDate);
		
		if(contractType.equals("Renouvelable")){
			
			Calendar c = Calendar.getInstance();
			c.setTime(echeanceDate);
			c.add(Calendar.DAY_OF_MONTH, -1);
			Date dateFin  = c.getTime();
			System.out.println("date dateFin= "+dateFin);
			adhesion.setDatFin(dateFin);
		}else{
			
			if(duration.equals("Annuelle"))
				adhesion.setDatFin(extinction);
			else {
				
				Calendar c = Calendar.getInstance();
				c.setTime(lastEcheanceDate);
				c.add(Calendar.DAY_OF_MONTH, -1);
				Date dateFin  = c.getTime();
				System.out.println("date dateFin= "+dateFin);
				adhesion.setDatFin(dateFin);
				
			}
				
				
			
		}
		
		 client = getClient();
		
		System.out.println("client "+client.getIdentifiant());
		adhesion.setClient(client);
		
		adhesion.setIdUtilisateurs(utilisateur.getIdUtilisateurs());
		
		 
		adhesion.setAdresse(adrisq);
		
		adhesion.setFraPolice("1");
		
		adhesion.setDatcreation(new Date());
		if (client.getIdClient() == 0) {
			client.setDatCreation(new Date());
		}
		
		
		adhesion.setMtPrime(primeNette);
		adhesion.setImpr(false);
		adhesion.setNadhesion(adhesion.getNumeroAdhesion());
		
		adhesion.setTaxe(tax);
		adhesion.setPrimetot(primeTotal);
		adhesion.setCout(new BigDecimal(cost));
		
		/***** a voir num_proposition ********/
		
		Beneficiaire beneficiaire = newBeneficiaire;
		
		
		 
		garantieMrh1 = new GarantieMrh();
		garantieMrh1.setCodGar("1");
		garantieMrh1.setPrmGar(new BigDecimal(pack.getPrimeNetteRC()));
		
		garantieMrh2 = new GarantieMrh();
		garantieMrh2.setCodGar("2");
		garantieMrh2.setPrmGar(new BigDecimal(pack.getPrimeNetteInc()));
		
		garantieMrh3 = new GarantieMrh();
		garantieMrh3.setCodGar("3");
		garantieMrh3.setPrmGar(new BigDecimal(pack.getPrimeNetteVol()));
		
		garantieMrh11 = new GarantieMrh();
		garantieMrh11.setCodGar("11");
		garantieMrh11.setPrmGar(new BigDecimal(pack.getPrimeNetteGemp()));
	
		
		if(newAdhesion.getPack().getIdPack()==3 ){
			
		
			garantieMrh10 = new GarantieMrh();
			garantieMrh10.setCodGar("10");
			garantieMrh10.setPrmGar(new BigDecimal(pack.getPrimeNetteAssh()));
			
			garantieMrh4 = new GarantieMrh();
			garantieMrh4.setCodGar("4");
			garantieMrh4.setPrmGar(new BigDecimal(pack.getPrimeNetteDeg()));
			
			garantieMrh5 = new GarantieMrh();
			garantieMrh5.setCodGar("5");
			garantieMrh5.setPrmGar(new BigDecimal(pack.getPrimeNetteBris()));
			
			garantieMrh6 = new GarantieMrh();
			garantieMrh6.setCodGar("6");
			garantieMrh6.setPrmGar(new BigDecimal(pack.getPrimeNetteDomElec()));
			
			garantieMrh7 = new GarantieMrh();
			garantieMrh7.setCodGar("7");
			garantieMrh7.setPrmGar(new BigDecimal(pack.getPrimeNetteEvenClim()));
			
			
			
			garantieMrh8 = new GarantieMrh();
			garantieMrh8.setCodGar("8");
			garantieMrh8.setPrmGar(new BigDecimal(pack.getPrimeNetteEvenClim()));
			
			garantieMrh9 = new GarantieMrh();
			garantieMrh9.setCodGar("9");
			garantieMrh9.setPrmGar(new BigDecimal(pack.getPrimeNetteEvenClim()));
			
			if(RCG ){
			garantieMrh12 = new GarantieMrh();
			garantieMrh12.setCodGar("12");
			garantieMrh12.setPrmGar(new BigDecimal(pack.getPrimeNetteRCG()));
			}
			
			if(JP ){
				garantieMrh13 = new GarantieMrh();
				garantieMrh13.setCodGar("13");
				garantieMrh13.setPrmGar(new BigDecimal(pack.getPrimeNetteJP()));
			}else garantieMrh13 = null;
			
			if(SV ){
			garantieMrh14 = new GarantieMrh();
			garantieMrh14.setCodGar("14");
			garantieMrh14.setPrmGar(new BigDecimal(pack.getPrimeNetteSV()));
			}else garantieMrh14 = null;
			
			if(AS ){
			garantieMrh15 = new GarantieMrh();
			garantieMrh15.setCodGar("15");
			garantieMrh15.setPrmGar(new BigDecimal(pack.getPrimeNetteAS()));
			
			adhesion.setNombrePersonne(BigDecimal.valueOf(nbrChild));
			}else garantieMrh15 = null;
			
			if (Pansol){
   				garantieMrh16 = new GarantieMrh();
   				garantieMrh16.setCodGar("16");
   				garantieMrh16.setPrmGar(new BigDecimal(pack.getPrimeNettePansol()));
   				}else garantieMrh16 = null;
			
			if (ObjM){
				garantieMrh17 = new GarantieMrh();
				garantieMrh17.setCodGar("17");
				garantieMrh17.setPrmGar(new BigDecimal(pack.getPrimeNetteObjM()));
   				}else garantieMrh17 = null;
			
			
		}else {
			
			if(newAdhesion.getPack().getIdPack()==1 ||newAdhesion.getPack().getIdPack()==2 ){
				
				if (Assh) {
					
					garantieMrh10 = new GarantieMrh();
					garantieMrh10.setCodGar("10");
					garantieMrh10.setPrmGar(new BigDecimal(pack.getPrimeNetteAssh()));
					System.out.println("test pak 1 ou 2 garanti 1 est coché "+ Assh);
				}else garantieMrh10 = null;
				
				if (Pansol){
	   				garantieMrh16 = new GarantieMrh();
	   				garantieMrh16.setCodGar("16");
	   				garantieMrh16.setPrmGar(new BigDecimal(pack.getPrimeNettePansol()));
	   				System.out.println("test pak 1 ou 2 garanti 2 est coché "+Pansol);
	   				}else garantieMrh16 = null;
				
			}
			
			
		
			adhesion.setNombrePersonne(BigDecimal.valueOf(0));
			
		
			if(newAdhesion.getPack().getIdPack()==2){
				
				garantieMrh4 = new GarantieMrh();
				garantieMrh4.setCodGar("4");
				garantieMrh4.setPrmGar(new BigDecimal(pack.getPrimeNetteDeg()));
				
				garantieMrh5 = new GarantieMrh();
				garantieMrh5.setCodGar("5");
				garantieMrh5.setPrmGar(new BigDecimal(pack.getPrimeNetteBris()));
				
				garantieMrh6 = new GarantieMrh();
				garantieMrh6.setCodGar("6");
				garantieMrh6.setPrmGar(new BigDecimal(pack.getPrimeNetteDomElec()));
				
				garantieMrh7 = new GarantieMrh();
				garantieMrh7.setCodGar("7");
				garantieMrh7.setPrmGar(new BigDecimal(pack.getPrimeNetteEvenClim()));
				
				
			}
			
               if(newAdhesion.getPack().getIdPack()==4){
				
				garantieMrh4 = new GarantieMrh();
				garantieMrh4.setCodGar("4");
				garantieMrh4.setPrmGar(new BigDecimal(pack.getPrimeNetteDeg()));
				
				garantieMrh6 = new GarantieMrh();
				garantieMrh6.setCodGar("6");
				garantieMrh6.setPrmGar(new BigDecimal(pack.getPrimeNetteDomElec()));
				
				garantieMrh10 = new GarantieMrh();
				garantieMrh10.setCodGar("10");
				garantieMrh10.setPrmGar(new BigDecimal(pack.getPrimeNetteAssh()));
				
				garantieMrh17 = new GarantieMrh();
				garantieMrh17.setCodGar("17");
				garantieMrh17.setPrmGar(new BigDecimal(pack.getPrimeNetteObjM()));
				
							
			  }
               if(newAdhesion.getPack().getIdPack()==5){
   				
   				garantieMrh4 = new GarantieMrh();
   				garantieMrh4.setCodGar("4");
   				garantieMrh4.setPrmGar(new BigDecimal(pack.getPrimeNetteDeg()));
   				
   				garantieMrh5 = new GarantieMrh();
				garantieMrh5.setCodGar("5");
				garantieMrh5.setPrmGar(new BigDecimal(pack.getPrimeNetteBris()));
   				
   				
   				garantieMrh6 = new GarantieMrh();
   				garantieMrh6.setCodGar("6");
   				garantieMrh6.setPrmGar(new BigDecimal(pack.getPrimeNetteDomElec()));
   				
   				garantieMrh7 = new GarantieMrh();
				garantieMrh7.setCodGar("7");
				garantieMrh7.setPrmGar(new BigDecimal(pack.getPrimeNetteEvenClim()));
				
   				garantieMrh10 = new GarantieMrh();
   				garantieMrh10.setCodGar("10");
   				garantieMrh10.setPrmGar(new BigDecimal(pack.getPrimeNetteAssh()));
   				
   				if(JP ){
   					garantieMrh13 = new GarantieMrh();
   					garantieMrh13.setCodGar("13");
   					garantieMrh13.setPrmGar(new BigDecimal(pack.getPrimeNetteJP()));
   				}else garantieMrh13 = null;
   				
   				if (Pansol){
   				garantieMrh16 = new GarantieMrh();
   				garantieMrh16.setCodGar("16");
   				garantieMrh16.setPrmGar(new BigDecimal(pack.getPrimeNettePansol()));
   				}else garantieMrh16 = null;
   				
   							
   			  }
   			
			
			
			
		
			
			
		} 
		
		String rachIn ;
		if(redemption) rachIn="O"; else rachIn="N";
		
		DetailMrh detailMrh = parametresMrhService.create_detail_mrh
				(selctedQualite, selctedNaturisk, BigDecimal.valueOf(nbrPieceLess), BigDecimal.valueOf(nbrPieceMore), BigDecimal.valueOf(furniturePrice), 
						BigDecimal.valueOf(area), 
						rachIn,contractType,duration,residence,BigDecimal.valueOf(valsol),BigDecimal.valueOf(valmultim)) ;
		
		adhesion.setDetailMrh(detailMrh);
		adhesion.setPack(newAdhesion.getPack());
		
		
	}
	
	Adhesion adhesionToPrintNotice;

	public Adhesion getAdhesionToPrintNotice() {
		return adhesionToPrintNotice;
	}

	public void setAdhesionToPrintNotice(Adhesion adhesionToPrintNotice) {
		this.adhesionToPrintNotice = adhesionToPrintNotice;
	}
			
	public void generateNoticeMRH() {

		printmrh(adhesionToPrintNotice);

	}		
		
	//--
	public void printmrh(Adhesion adhesion) {
		
		try {
	
		
			String path = getRealPath() + "noticemrhp1.jasper";
			
			Map parameters = new HashMap();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			parameters.put("nadhesion",  newAdhesionToPrint.getNadhesion());
			parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
			parameters.put("contractType", contractType);
			System.out.println("contractType" + contractType );
			 if (effDate != null) {
		            parameters.put("effDate", df.format(effDate));
		        } else {
		            parameters.put("effDate", " ");
		        }

		       // if (lastEcheanceDate != null) {
		         //   parameters.put("echeanceDate", df.format(lastEcheanceDate));
		        //} else {
		        //    parameters.put("echeanceDate", "N/A");
		        //}

		        if ((extinction != null) &&  contractType.equals("Ferme") ){
		            parameters.put("expDate", df.format(extinction));
		        } else {
		            parameters.put("expDate", " ");
		        }
		        
		        if ((echeanceDate != null) && contractType.equals("Renouvelable")){
		            parameters.put("echeanceDate", df.format(echeanceDate));
		            System.out.println("echeanceDate" + echeanceDate );
		        } else {
		            parameters.put("echeanceDate", " ");
		        }
		        
		        parameters.put("clientName", clientName);
		        parameters.put("adrclt", client.getAdrClient());
		        parameters.put("cpclt", client.getCodeP());
		        parameters.put("vilclt", client.getLocalite());
		        parameters.put("gouvclt",client.getGouver());
		        //paramaters.put("faxclt",client.getfaxclt);
		        parameters.put("mailclt",client.getMail());
		        parameters.put("telclt",client.getTel());
		        
		        parameters.put("quality", selctedQualite.getQualite());
		        parameters.put("sitrisq", adrisq);
		        parameters.put("cprisq", newAdhesion.getCodeP());
		        parameters.put("vilrisq", newAdhesion.getLocalite());
		        parameters.put("city", newAdhesion.getGouver());///city
		        parameters.put("riskNature", selctedNaturisk.getLibelleNature());
		        parameters.put("nbrPieceLess", String.valueOf(nbrPieceLess));
		        parameters.put("nbrPieceMore", String.valueOf(nbrPieceMore));
		        parameters.put("area", String.valueOf(area));
		        parameters.put("furniturePrice", (new DecimalFormat("#0.000")).format(furniturePrice) + " TND");
		        
		        parameters.put("duration", duration);
		     
		        
		        
		       // parameters.put("primeNette", (new DecimalFormat("#0.000")).format(primeNette) + " TND");
		       // parameters.put("tax", (new DecimalFormat("#0.000")).format(tax) + " TND");
		       // parameters.put("cost", (new DecimalFormat("#0.000")).format(cost) + " TND");
		       // parameters.put("primeTotal", (new DecimalFormat("#0.000")).format(primeTotal) + " TND");

		        parameters.put("baseGar", pack.getBaseGar());
		        System.out.println("baseGar" + pack.getBaseGar());

		        parameters.put("opGar", pack.getOpGarString());
		        System.out.println("optGar" + pack.getOpGarString());
		        //*******************************page 2*******************
		        parameters.put("primeNette", (new DecimalFormat("#0.000")).format(primeNette) + " TND");
		        parameters.put("cost", (new DecimalFormat("#0.000")).format(cost) + " TND");
		        parameters.put("tax", (new DecimalFormat("#0.000")).format(tax) + " TND");
		        parameters.put("primeTotal", (new DecimalFormat("#0.000")).format(primeTotal) + " TND");
		        if(redemption) parameters.put("rach", "1");
		        else
		        parameters.put("rach", "0");
		        
		        if (contractType.equals("Renouvelable")) parameters.put("renouv", "1");
		        else
		        parameters.put("renouv", "0");
		        
		        if (((contractType.equals("Renouvelable"))&& duration == "Annuelle" ) ||   ((contractType.equals("Ferme"))&& duration == "Prorata" )) 
		        parameters.put("reval", "1");
		        else
		        parameters.put("reval", "0");

		        if (AS)   parameters.put("as", "1");
		        else
		        parameters.put("as", "0");	

				
	
	 JasperPrint print = JasperFillManager.fillReport(path,
             parameters, new JREmptyDataSource());
     byte[] bytes = JasperExportManager.exportReportToPdf(print);

     FacesContext fc = FacesContext.getCurrentInstance();
     ExternalContext ec = fc.getExternalContext();
     ec.responseReset();
     ec.setResponseContentType("application/pdf");
     ec.setResponseContentLength(bytes.length);
     ec.setResponseHeader("Content-Disposition",
             "inline; filename=noticemrhp1.pdf");

     OutputStream output = ec.getResponseOutputStream();
     output.write(bytes);
     fc.responseComplete();

     adhesion.setImpr(true);
   
 } catch (Exception e) {
     e.printStackTrace();
 }
}
		
	
		
 
	
	 public String getRealPath(){
			ServletContext cxt=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath= cxt.getRealPath("/");
			return realPath+"report/";
			 
		}

	 public void updatecheckboxrachatActionListener(){	
			
			System.out.println("resisence" + residence);

			//if(residence !=null && (!residence.equals("")))
			
				 if (residence.equals("Secondaire")) {
			
				redemption = true;
				desabledrachatMRH =  true;
				 }else{
					 
					redemption = false;
					desabledrachatMRH = false;	
				 }

	 }	
	 
	 
	 public void updateQualiteActionListener(){
		 
		 selctedQualite = iQualiteDAO.find(idQualite);
		 System.out.println(selctedQualite.getIdQualite() +" "+selctedQualite.getQualite());
			 
	 }
	 
	 
	 public void updateNatureRisqueActionListener(){
		 
		  
		selctedNaturisk = iNatureRisqDAO.find(idNatureRisque);
		System.out.println(selctedNaturisk.getIdRiskMrh() +" "+selctedNaturisk.getLibelleNature());
	 }
	 
	 
	 public void updatecheckboxObjetMultimediaActionListener(){	
			
			//System.out.println("resisence" + residence);
		 
		 newAdhesion.setPack(getTypePack(selctedPack.getIdPack()));

			//if(residence !=null && (!residence.equals("")))
			
				 if (newAdhesion.getPack().getIdPack()==3) {
			
					 ObjM = false;
					 desabledobjMultimediaMRH =  false;
					 valmultim = 0;
				 }else{
					 
					ObjM = true;
					desabledobjMultimediaMRH = true;
					valmultim = 1500;
				 }

	 }	
	 
	 public void updateobjMultimediaActionListener(){
		 
		 if(ObjM && newAdhesion.getPack().getIdPack()==3){
			
			 valmultim = 4000;
			 System.out.println("valmultim "+valmultim);  
		 }else{
			 valmultim = 0;
			 System.out.println("valmultim "+valmultim); 
		 }
		 
	 }
	 
	 
	 public void updatePansolActionListener(){
		 
		 if(!Pansol) valsol = 0;
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
			
			System.out.println("loadProposition avant if");
			

					if (getSelectedProposition()!=null) {
						
						System.out.println("loadProposition  if");
						clearFields();
						
						try {
			
							clientBean.setClient(getSelectedProposition().getClient());
							newAdhesion.setDatDebut(getSelectedProposition().getDatDebut());
							newAdhesion.setDatFin(getSelectedProposition().getDatFin());
							newAdhesion.setPolice((getSelectedProposition().getPolice()));
							newAdhesion.setDuree(getSelectedProposition().getDuree());
							newAdhesion.setFraPolice(getSelectedProposition().getFraPolice());
							newAdhesion.setEtat(getSelectedProposition().getEtat());
							newAdhesion.setMtPrime(getSelectedProposition().getMtPrime());
							//newAdhesion.setImpr(getSelectedProposition().geti);
							newAdhesion.setAdresse(getSelectedProposition().getAdresse());
							adrisq=getSelectedProposition().getAdresse();
							newAdhesion.setCodeP(getSelectedProposition().getCodeP());
							newAdhesion.setGouver(getSelectedProposition().getGouver());
							newAdhesion.setLocalite(getSelectedProposition().getLocalite());
							allCodePostalByGouvernera = locgouvDAO.findByGouv(newAdhesion.getGouver());//(mapAllCodePostalByGouvernerat.get(getSelectedProposition().getGouver()));
							selectedLocgouv = locgouvDAO.findByPostaCode(getSelectedProposition().getCodeP(),getSelectedProposition().getLocalite());
							System.out.println("selectedLocgouv "+selectedLocgouv.getCodePosta()+" "+selectedLocgouv.getLocalite());
							newAdhesion.setDatcreation(getSelectedProposition().getDatcreation());
							//newAdhesion.setNadhesion(getSelectedProposition().getNumeroAdhesion());
							newAdhesion.setTaxe(getSelectedProposition().getTaxe());
							newAdhesion.setPrimetot(getSelectedProposition().getPrimetot());
							//newAdhesion.setProposition(getSelectedProposition().getp);
							newAdhesion.setDetailMrh(getSelectedProposition().getDetailMrh());
							selctedQualite = getSelectedProposition().getDetailMrh().getQualite();
							idQualite = selctedQualite.getIdQualite();
							System.out.println("selctedQualite est "+selctedQualite.getQualite());
							selctedNaturisk = getSelectedProposition().getDetailMrh().getNatureRisq();
							idNatureRisque = selctedNaturisk.getIdRiskMrh();
							System.out.println("selctedNaturisk est "+selctedNaturisk.getLibelleNature());
							nbrPieceLess = getSelectedProposition().getDetailMrh().getNbrpieceless().intValue();
							furniturePrice = getSelectedProposition().getDetailMrh().getFurnitureprice().doubleValue();
							nbrPieceMore = getSelectedProposition().getDetailMrh().getNbrpiecemore().intValue();
							area = getSelectedProposition().getDetailMrh().getArea().doubleValue();
							residence = getSelectedProposition().getDetailMrh().getResidence();
							if(getSelectedProposition().getDetailMrh().getRachIn().equals("O")) redemption = true; else redemption = false;
							duration = getSelectedProposition().getDetailMrh().getDuree();
							contractType = getSelectedProposition().getDetailMrh().getTypecontrat();
							
							 
							newAdhesion.setCout(getSelectedProposition().getCout());
							newAdhesion.setPack(getSelectedProposition().getPack());
							selctedPack = getSelectedProposition().getPack();
							System.out.println("selctedPack est "+selctedPack.getLibelle()+" "+selctedPack.getIdPack() );
							System.out.println("selctedPack est est "+newAdhesion.getPack().getIdPack()+" "+newAdhesion.getPack().getLibelle());
							newAdhesion.setNombrePersonne(getSelectedProposition().getNombrePersonne());
							 
							effDate = getSelectedProposition().getDatDebut();
							updateDate();  //extinction avec echeanceDate comme valeurs
							
							if(duration.equals("Prorata"))
							{
								Calendar c = Calendar.getInstance();
								c.setTime(getSelectedProposition().getDatFin());
								c.add(Calendar.DAY_OF_MONTH, +1);
								Date dateFin  = c.getTime();
								System.out.println("date dateFin= "+dateFin);
								lastEcheanceDate = dateFin;
								
							}
							
							
 				
							 
							

							for(GarantieMrh garnti : getSelectedProposition().getGarantieMrh()){
								if (garnti.getCodGar().equals("1"))
									garantieMrh1 = garnti;
							
								if (garnti.getCodGar().equals("2"))
									garantieMrh2 = garnti;
								
								if (garnti.getCodGar().equals("3"))
									garantieMrh3 = garnti;
								
								if (garnti.getCodGar().equals("11"))
									garantieMrh11 = garnti;
									
								
							}
							
							
							
							
							if(newAdhesion.getPack().getIdPack()==3 ){
								
								
								for(GarantieMrh garnti : getSelectedProposition().getGarantieMrh()){
									if (garnti.getCodGar().equals("10"))
										garantieMrh10 = garnti;
								
									if (garnti.getCodGar().equals("4"))
										garantieMrh4 = garnti;
									
									if (garnti.getCodGar().equals("5"))
										garantieMrh5 = garnti;
									
									if (garnti.getCodGar().equals("6"))
										garantieMrh6 = garnti;
									
									if (garnti.getCodGar().equals("7"))
										garantieMrh7 = garnti;
									
									if (garnti.getCodGar().equals("8"))
										garantieMrh8 = garnti;
									
									if (garnti.getCodGar().equals("9"))
										garantieMrh9 = garnti;
									
									if (garnti.getCodGar().equals("12")){
										garantieMrh12 = garnti;
										RCG=true;
									}
									if (garnti.getCodGar().equals("13")){
										garantieMrh13 = garnti;
										JP =true;
									}
										
									if (garnti.getCodGar().equals("14")){
										garantieMrh14 = garnti;
										SV =true;
									}	
									
									if (garnti.getCodGar().equals("15")){
										garantieMrh15 = garnti;
										AS =true;
										//adhesion.setNombrePersonne(BigDecimal.valueOf(nbrChild));
										nbrChild = getSelectedProposition().getNombrePersonne().intValue();
										Beneficiaire bnben = new Beneficiaire();
										for(Beneficiaire benef : getSelectedProposition().getBeneficiaires()){
											bnben.setNom(benef.getNom());
											bnben.setPrenom(benef.getPrenom());
											bnben.setDateNaiss(benef.getDateNaiss());
											bnben.setNiveauScolaire(benef.getNiveauScolaire());
											newAdhesion.getBeneficiaires().add(bnben);
											bnben = new Beneficiaire();
									    	 
							    		}
										//newAdhesion.setBeneficiaires(getSelectedProposition().getBeneficiaires());
									}	
									
									if (garnti.getCodGar().equals("16")){
										garantieMrh16 = garnti;
										Pansol =true;
										valsol = getSelectedProposition().getDetailMrh().getValsolaire().doubleValue();
									}	
									
									if (garnti.getCodGar().equals("17")){
										garantieMrh17 = garnti;
										ObjM =true;
										valmultim = getSelectedProposition().getDetailMrh().getValmultimedia().doubleValue();
									}	
									
									
									 
									
								}
								
								 
								
							}else {
								
								 
								
								if(newAdhesion.getPack().getIdPack()==1 ||newAdhesion.getPack().getIdPack()==2 ){
									
									
									for(GarantieMrh garnti : getSelectedProposition().getGarantieMrh()){
										if (garnti.getCodGar().equals("10")){
											garantieMrh10 = garnti;
											Assh = true;
										}
										if (garnti.getCodGar().equals("16")){
											garantieMrh16 = garnti;
											Pansol = true;
											valsol = getSelectedProposition().getDetailMrh().getValsolaire().doubleValue();
											//valsol=getSelectedProposition().get
										}
											
										
									}
									
									 
									
								}
								
								
							
								//adhesion.setNombrePersonne(BigDecimal.valueOf(0));
								
							
								if(newAdhesion.getPack().getIdPack()==2){
									
									for(GarantieMrh garnti : getSelectedProposition().getGarantieMrh()){
										if (garnti.getCodGar().equals("4"))
											garantieMrh4 = garnti;
									
										if (garnti.getCodGar().equals("5"))
											garantieMrh5 = garnti;
										
										if (garnti.getCodGar().equals("6"))
											garantieMrh6 = garnti;
										
										if (garnti.getCodGar().equals("7"))
											garantieMrh7 = garnti;
									 	
										
									}
								 	
									
								}
								
								
					               if(newAdhesion.getPack().getIdPack()==4){
					            	   
					            	   for(GarantieMrh garnti : getSelectedProposition().getGarantieMrh()){
											if (garnti.getCodGar().equals("4"))
												garantieMrh4 = garnti;
										 
											
											if (garnti.getCodGar().equals("6"))
												garantieMrh6 = garnti;
											
											if (garnti.getCodGar().equals("10"))
												garantieMrh10 = garnti;
											
											if (garnti.getCodGar().equals("17")){
												garantieMrh17 = garnti;
												valmultim = getSelectedProposition().getDetailMrh().getValmultimedia().doubleValue();
											}
												
											
											 
										 	
											
										}
								 
												
								  }
					               if(newAdhesion.getPack().getIdPack()==5){
					            	   
					            	   for(GarantieMrh garnti : getSelectedProposition().getGarantieMrh()){
											if (garnti.getCodGar().equals("4"))
												garantieMrh4 = garnti;
										 
											if (garnti.getCodGar().equals("5"))
												garantieMrh5 = garnti;
											
											if (garnti.getCodGar().equals("6"))
												garantieMrh6 = garnti;
											
											if (garnti.getCodGar().equals("7"))
												garantieMrh7 = garnti;
											
											if (garnti.getCodGar().equals("10"))
												garantieMrh10 = garnti;
											
											if (garnti.getCodGar().equals("13")){
												garantieMrh13 = garnti;
												JP = true;
											}
											
											if (garnti.getCodGar().equals("16")){
												garantieMrh16 = garnti;
												Pansol = true;
												valsol = getSelectedProposition().getDetailMrh().getValsolaire().doubleValue();
											}
										 	
											
										}
					   				
					   			  
								  
					   				
					   							
					   			  }
					   			
								
								
								
							
								
								
							} 
							
							
							
							
							
							
							
							
							
							
							
							
							
 					
				
							
							
			
							selectedProposition=null;
							desabledGenerateNoticeMRH= true;
							 noneException = false;
							 newProposition = null;
							} catch (Exception e) {
							e.printStackTrace();
						}
					} else  if (newDevis != null && !newDevis.isEmpty()&& Boolean.parseBoolean(newDevis)) {
						System.out.println("loadProposition else if");
						initialisationClientAndAdhesion();
						init();
						clientBean.cancel();
						selectedProposition=null;
					}
		}



	 public void initialisationClientAndAdhesion(){

			newAdhesion = new Adhesion();
			//newAdherent=new Adherent();
			newBeneficiaire=new Beneficiaire();
			newAdhesionToPrint=new Adhesion();
			clientBean.setClient(new Client());
			newProposition = null;
			//desabledGenerateNotice=true;
		}
	 
	 
	 @EJB
	 private IPackDAO packDAO;
	 
	 public Pack getTypePack(long code) {
		 System.out.println("packDAO.find(code) "+code);
			return packDAO.find(code);
		}
	 
//	 @FacesConverter(forClass = Pack.class)
//		public static class getPackMrhConverter implements Converter {
//
//			@Override
//			public Object getAsObject(FacesContext context, UIComponent component,
//					String value) {
//				if (value != null && !value.isEmpty()) {
//					try {
//						long code = Long.parseLong(value);
//						MRHBean clientBean = (MRHBean) context
//								.getApplication()
//								.getELResolver()
//								.getValue(context.getELContext(), null,
//										"mrhBean");
//						return clientBean.getTypePack(code);
//					} catch (Exception e) {
//						System.out.println(e.getMessage());
//					}
//				}
//				return null;
//			}
//
//			@Override
//			public String getAsString(FacesContext context, UIComponent component,
//					Object value) {
//
//				try {
//					Pack typeVoie = (Pack) value;
//					if (typeVoie != null) {
//						return String.valueOf(typeVoie.getIdPack());
//					}
//
//				} catch (Exception e) {
//				}
//
//				return null;
//			}
//		}

}
