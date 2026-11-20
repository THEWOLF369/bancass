package com.star.Ba.Agricole;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.ws.handler.Handler;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ServiceException;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

//import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
//01/11/2022
import org.primefaces.util.ComponentUtils;
//import com.star.Ba.jsf.util.ComponentUtils;

import ws.client.ParseException_Exception;

import com.bna.ws.soap.saeb.agricole.services.DecisionAgr;
import com.bna.ws.soap.saeb.agricole.services.IDecisionCreditsAgr;
import com.bna.ws.soap.saeb.agricole.servicesimpl.DecisionCreditsAgrImplService;
import com.star.Ba.commun.Age;
import com.star.Ba.commun.AgeCalculator;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IDetailActDAO;
import com.star.Ba.dao.ILocgouvDAO;
import com.star.Ba.dao.IPackPoliceDAO;
import com.star.Ba.dao.IParamActDAO;
import com.star.Ba.dao.IParcelleDAO;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IProfessionClientDAO;
import com.star.Ba.dao.IQuittanceDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.ISpeculationDAO;
import com.star.Ba.dao.ITariftdDAO;
import com.star.Ba.dao.ITypeVoieDAO;
import com.star.Ba.dto.ContenuDTO;
import com.star.Ba.dto.ContratDTO;
import com.star.Ba.dto.TarifAgrDTO;
import com.star.Ba.dto.UtilisateursConnexionDTO;
import com.star.Ba.jsf.ReportController;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.AgrclasseNature;
import com.star.Ba.model.Agrnature;
import com.star.Ba.model.Client;
import com.star.Ba.model.Delegation;
import com.star.Ba.model.DetailAct;
import com.star.Ba.model.GarantieVie;
import com.star.Ba.model.Locgouv;
import com.star.Ba.model.Parametres;
import com.star.Ba.model.ParametresPack;
import com.star.Ba.model.Police;
import com.star.Ba.model.PrixHom;
import com.star.Ba.model.ProfessionClient;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.Speculation;
import com.star.Ba.model.TarifGrele;
import com.star.Ba.model.Tauxreduction;
import com.star.Ba.model.TypeVoie;
import com.star.Ba.service.IParametreAgricoleService;
import com.star.Ba.service.IParametreMenuService;
import com.star.Ba.service.IParametresTDService;

@ManagedBean(name = "packBean")
@ViewScoped
public class PackBean implements Serializable {
	@EJB
	private IParametreAgricoleService service;
	@EJB
	private IParametreMenuService serviceMenu;
	private List<Agrnature> listAgrNature;
	private String selectedNature, natureNulle = null, libelleSelectedNature,
			yearRange;
	@ManagedProperty(value = "#{login.utilisateursConnexionDTO}")
	private UtilisateursConnexionDTO utilisateursConnexionDTO;
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	private List<ContenuDTO> listContenu;
	private Map<String, ParametresPack> listParam;
	private List<ContenuDTO> onglets;
	private int codespec;
	private Boolean verifcompte = false;
	private List<AgrclasseNature> listAgrClasseNature;
	private String selectedClasse, selectedZone, selectedDelegation;
	private boolean bClasse = true, bZone = true, bCapitale = true,
			bprixHom = true, bprixHom2 = false;
	private boolean bclasse3 = false, bclasse4 = false, bclasse5 = false,
			bclasse6 = false;
	private List<TarifGrele> listTarifGrele;
	private String primeGrele = null;
	private String TypePieceWS;
	private String situation_risque;
	private TarifAgrDTO tarifIncendie, tarifGrele;
	private BigDecimal primeTotale = BigDecimal.ZERO;
	private List<Parcelle> listParcelle;
	private List<Delegation> listDelegation;
	private Agrnature nature;
	private int tabindex = 0;
	private Date dateEffet, dateEcheance, auj, datNaiss, echecdate;
	private boolean checkedGrele, checkedIncendie, checkedTD;
	private boolean checkedIncAl;
	private long idprodconvIncendie, idprodconvGrele, idprodconvTD;
	private List<String> docs;
	private List<ContratDTO> listContrat;
	private ContratDTO contratGrele, contratIncendie, contratTD,
			contratIncendieAl;
	private String capital;
	private long selectedLocgouvCode;
	private List<PrixHom> listPrixHom;
	private Date minDateNaissance;
	private double xdevis;
	private boolean bpieds = false;
	private boolean blocage = true;
	private boolean voir = true;
	private String situationRisque;
	/* DEBUT CLIENT */
	@EJB
	private ITypeVoieDAO typeVoieDAO;
	@EJB
	private ILocgouvDAO locgouvDAO;
	@EJB
	private IProfessionClientDAO professionClientDAO;

	// private List<Variete> listvarietes;
	private boolean societe = false;
	private boolean devistd = true;
	private boolean devistd2 = false;

	private Client client = new Client();
	private List<ProfessionClient> professions;
	private List<TypeVoie> typeVoies;
	private long typeVoieCode = 10, idProfession = 8;
	private List<String> allGouverneras;
	private String selectedGouverneras;
	private List<Locgouv> locgouvs;
	private Locgouv selectedLocgouv;
	private boolean naturelegume = false;
	private boolean searching = false, ongletProduit = false,
			ongletclient = false, ongletTarif = false, save = false,
			imprimer = false, packComplet = false;
	private String updateGeneral, updateClient, updatePrduit, updateGrele,
			updateTD;
	private List<com.star.Ba.model.Parcelle> listParcelleModel;

	/* FIN CLIENT */
	@EJB
	private IParamActDAO paramActDAO;

	@EJB
	private IAdhesionDAO adhesionDAO;
	@EJB
	private IProduitDAO produitDAO;

	@EJB
	private IConventionDAO conventionDAO;

	@EJB
	private ISocieteDAO societeDAO;

	@EJB
	private IParcelleDAO parcelledao;

	@EJB
	private IPackPoliceDAO iPackPoliceDAO;

	@EJB
	private IQuittanceDAO iQuittanceDAO;

	@EJB
	private IProfessionClientDAO iProfessionClientDAO;

	@EJB
	private IPoliceDAO policeDAO;
	@EJB
	private ITariftdDAO iTariftdDAO;
	@EJB
	private IParametresTDService parametresTDService;
	@EJB
	private IDetailActDAO detailActDAO;

	@EJB
	private ISpeculationDAO iSpeculationDAO;

	private Map<String, Object> listnumplc = null;
	private String numplc;
	private String cin;
	private String nom;
	private String prenom;
	private Date datenaiss, mindate, maxdate;
	private Date effDate;
	private Date datcreation;
	private double duree;
	private Age controlAge;
	private int nbrparcelle = 0;

	private Age dureeCredit;
	private int dureeA;
	private int dureeM;
	private String nadhesion;
	private String franchise;
	private String tauxgrele;
	private int type_pack = 0;

	private Date datefin;
	private double tauxsurprime;
	private double majoration;
	private double tauxdeprime = 0;
	private double primeglobal;
	private Boolean tarifier;
	private double tauxinteret;
	private Date aujourdhui;
	private Age ageCalc;
	private int age, agem, agej;
	private String propositionId;
	private List<DetailAct> lstAnalyse;
	private boolean showTd = false;
	private Police libnumplc;
	private int numact;
	private String RefCredit;
	private String etat;
	private int nbmois;
	private int nbannee, nbjours;
	private int nbannee2, nbjours2, nbmois2;
	private int minage;
	private int maxage;
	private String numprop;
	private String TypeDoc, identifiant, ncompte, nomClient, prenomClient,
			sexe, adrClient, gouver, tel, mail;
	private String cod_societe;
	private Adhesion adhesion;
	private Speculation spec;
	private Proposition proposition;
	private String propid;
	private Double capitale;

	private Proposition selectedProposition;
	private String numDecision=null;
	private String numDetailDecision;
	private Police police = new Police();
	private String prodspeculation;
	private String speculation;
	private String speculationDetail;

	private String ref_credit;
	private String decisionCredit ;
	private void initTD() {
		updatepolice();
	}

	public void rechercherDepuisWSbna() throws ParseException_Exception,
			ServiceException, RemoteException, DatatypeConfigurationException {
		searching = true;
		String identifiant = numDecision;
		decisionCredit=numDecision;
		String identifiant2 = numDetailDecision;

		System.out.println("identifiant =" + identifiant);
		System.out.println("identifiant2 " + identifiant2);

		if ((identifiant == null) || (identifiant2 == null)||(identifiant == "") || (identifiant2 == ""))

		{
			voir = false;
			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur: "
							+ "veuillez remplir le champs numero de decision",
							""));
			//01/11/2022
			RequestContext.getCurrentInstance().update("formContrat");
//			PrimeFaces.current().ajax().update("formContrat");

		}

		else if (adhesionDAO.replicatenumdec(identifiant, identifiant2) != null) {
			voir = false;
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"growl",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Erreur: "
											+ "Veillez vérifier le numéro de decision , il est déja utilisé",
									""));
			//01/11/2022
			RequestContext.getCurrentInstance().update("formContrat");
//			PrimeFaces.current().ajax().update("formContrat");

		}

		else {

			voir = true;
			
			 // DEBUT APPEL 
		     DecisionCreditsAgrImplService service1 = new
			  DecisionCreditsAgrImplService();
			  
			  System.out.println("Create Web Service..."); 
			  
			  IDecisionCreditsAgr ws =
			  service1.getDecisionCreditsAgrImplPort();
			  System.out.println("Call Web Service Operation...");
			  
			  System.out.println("identifiant " + identifiant);
			  System.out.println("identifiant2 " + identifiant2);
			  
			  
			  Map<String, Object> req_ctx =
			  ((BindingProvider)ws).getRequestContext();
			  Binding binding =			  ((BindingProvider)ws).getBinding();
			  req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
			  "http://172.19.1.105:8089/interfacageWebService_DMZ/services/WSDecisionCreditsAgr?wsdl"
			  );
			  
			  List<Handler> handlerList = binding.getHandlerChain();
			  handlerList.add(new Handler1());
			  binding.setHandlerChain(handlerList);
			  
//			  // BindingProvider prov = (BindingProvider)port2;
//			  prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
//			  "agricole");
//			  prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
//			  "BNAbna2020");
			  
			  DecisionAgr port2 = ws.getDecisionCreditAgr(identifiant,
			  identifiant2);
			 /// FIN APPEL

		//DecisionAgr port2 = service.simulationWSBNAp1();

			try {

				long a;
				long b;
				double c;

				a = (port2.getDetailsDecision().getMontant());

				b = 1000;
				c = a / b;

				capital = Double.toString(c);

				String kcompte=null;
				kcompte = (port2.getNumCompte());

				if (kcompte!=null && !kcompte.trim().equals("") ) {

					client.setNcompte(kcompte);
					verifcompte = true;
				}else{
					verifcompte = false;
				}

				client.setNcompte(port2.getNumCompte());

				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss.S");

				String strdate = null;

				/************************************************************************/

				GregorianCalendar cal = new GregorianCalendar();
				XMLGregorianCalendar dateInString = DatatypeFactory
						.newInstance().newXMLGregorianCalendar(cal);
				XMLGregorianCalendar datecontratstr = DatatypeFactory
						.newInstance().newXMLGregorianCalendar(cal);
				XMLGregorianCalendar datechstr = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(cal);

				setDateEffet(cal.getTime());

				datechstr = (port2.getDetailsDecision().getDateDerniereEch());

				setDateEcheance(datechstr.toGregorianCalendar().getTime());

				/****************** a test *********************/

				dureeCredit = AgeCalculator.calculateDifference(dateEffet,
						dateEcheance);

				nbannee = dureeCredit.getMonths() + dureeCredit.getYears() * 12;
				nbjours = dureeCredit.getDays();
				System.out.println("calcul duree=" + nbannee);
				if ((nbannee >= 12 && nbjours > 0) || (nbannee > 12)
						|| (nbannee < 0 && nbjours < 0)) {
					FacesContext
							.getCurrentInstance()
							.addMessage(
									"growl",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"Erreur: "
													+ "La durée ne doit pas être supérieur à 12 mois",
											""));
					//01/11/2022
					RequestContext.getCurrentInstance().update("formContrat");
//					PrimeFaces.current().ajax().update("formContrat");

				}

				// Date dateEcheance =
				// datechstr.toGregorianCalendar().getTime();
				// strdate = sdf.format(dateInString.getTimezone());
				// Date date = sdf.parse(strdate);

				client.setTel(port2.getAgriculteur().getNumTel());
				client.setIdentifiant(port2.getAgriculteur().getNumPiece());

				client.setTypeDoc(port2.getAgriculteur().getTypePiece());

				String Gouv;
				int g;
				g = port2.getAgriculteur().getAddresse().getGouvernerat();

				switch (g) {
				case 10:Gouv = "TUNIS";break;
				case 11:Gouv = "ZAGHOUAN";break;
				case 12:Gouv = "KASSERINE";break;
				case 20:Gouv = "ARIANA";break;
				case 21:Gouv = "GAFSA";break;
				case 22:Gouv = "TOZEUR";break;
				case 23:Gouv = "BEN AROUS";break;
				case 24:Gouv = "MANOUBA";break;
				case 30:Gouv = "SFAX";break;
				case 31:Gouv = "KAIROUAN";break;
				case 32:Gouv = "TATAOUINE";break;
				case 40:Gouv = "SOUSSE";break;
				case 41:Gouv = "MEDENINE";break;
				case 42:Gouv = "KEBILI";break;
				case 50:Gouv = "MONASTIR";break;
				case 51:Gouv = "MAHDIA";break;
				case 60:Gouv = "GABES";break;
				case 61:Gouv = "SILIANA";break;
				case 70:Gouv = "BIZERTE";break;
				case 71:Gouv = "LE KEF";break;
				case 80:Gouv = "NABEUL";break;
				case 81:Gouv = "JENDOUBA";break;
				case 90:Gouv = "BEJA";break;
				case 91:Gouv = "SIDI BOUZID";break;
				default:
					Gouv = "Invalid Gouv";

					FacesContext
					.getCurrentInstance()
					.addMessage(
							"growl",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Erreur: "
											+ "Null ou invalid Code Gouvernorat",
									""));
					//01/11/2022
			RequestContext.getCurrentInstance().update("formContrat");
//			PrimeFaces.current().ajax().update("formContrat");

					FacesContext
					.getCurrentInstance()
					.addMessage(
							"growl",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Erreur: "
											+ "Null ou invalid Code Gouvernorat",
									""));
					//1/11/2022
					RequestContext.getCurrentInstance().update("formContrat");
//					PrimeFaces.current().ajax().update("formContrat");

					break;
				}

				TypePieceWS = port2.getAgriculteur().getTypePiece();
				if (TypePieceWS.equals("2")) {
					client.setTypeDoc("CIN");
					client.setTypePers("PA");
					client.setNomClient(port2.getAgriculteur().getNom());
					client.setPrenomClient(port2.getAgriculteur().getPrenom());
					client.setSexe(port2.getAgriculteur().getSex());
					dateInString = port2.getAgriculteur().getDateNaissance();
					Date date = dateInString.toGregorianCalendar().getTime();
					// update to set
					// client.setDatNaiss(dateInString.toGregorianCalendar().getTime());
					client.setDatNaiss(date);
					client.setAdrClient(port2.getAgriculteur().getAddresse()
							.getPays()
							+ ","
							+ Gouv
							+ ","
							+ port2.getAgriculteur().getAddresse()
									.getCodePostale()
							+ ","
							+ port2.getAgriculteur().getAddresse().getRue());
				}
				// 9: MF 11 : Association 14: RC
				else if (TypePieceWS.equals("9") || TypePieceWS.equals("11")
						|| TypePieceWS.equals("14")) {

					client.setTypeDoc("MatFiscal");
					client.setNomClient(port2.getAgriculteur()
							.getRaisonsocialle());
					client.setAdrClient(port2.getAgriculteur().getAddresse()
							.getPays()
							+ ","
							+ Gouv
							+ ","
							+ port2.getAgriculteur().getAddresse()
									.getCodePostale()
							+ ","
							+ port2.getAgriculteur().getAddresse().getRue());

				} else {

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"growl",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"Erreur: "
													+ "Ce n'est pas un particuler ou une société",
											""));
					//01/11/2022
					RequestContext.getCurrentInstance().update("formContrat");
//					PrimeFaces.current().ajax().update("formContrat");

				}

			} catch (Exception e) {
				e.printStackTrace();
				client = null;
				System.out.println("Exception ");

			}

			if (client == null) {
				FacesMessage msg = new FacesMessage("ERREUR",
						"Client inexistant ");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				FacesContext.getCurrentInstance().validationFailed();
				cancel();
			}

			String x;
			int y;
			int y1, y2;

			x = Long.toString(port2.getDetailsDecision()
					.getCodeProduitSpeculation());
			y1 = Integer.parseInt(x);
			y = Integer.parseInt(port2.getDetailsDecision().getSpeculation());
			y2 = Integer.parseInt(port2.getDetailsDecision().getSpeculation());
			System.out
					.println("code spec ========================================"
							+ y);
			codespec = y;

			spec = iSpeculationDAO.getspeculation(y);
			speculationDetail = spec.getLibelle_prod_spec();
			if(spec == null) {
				
				FacesMessage msg = new FacesMessage("ERREUR",
						"code speculation hors convention");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				
			}
			if(spec!=null)
			{
				speculationDetail = spec.getLibelle_prod_spec();
			}

			selectedClasse = spec.getClasse();
			selectedNature = spec.getNature();
			type_pack = spec.getType_pack();
			speculation = spec.getLibelle_spec();
			prodspeculation = spec.getLibelle_prod_spec();
			initialiserListContenu();

			if (spec.getPrix() != null && spec.getPrix() == 1) {
				bprixHom = true;
			}
			calculAge();
		}
		

	}

	public void cancel() {

		System.out.println("cancel ");
		client = new Client();
		selectedLocgouv = null;
		allCodePostalByGouvernera.clear();
	}

	public Map<String, Object> getlistnumplc(String cod_societ, Profil profil) {
		Map<String, Object> listnumplc = new LinkedHashMap<String, Object>();
		List<Police> list = null;

		if (profil.getCode().equals(PofileEnum.TDST.name())
				|| profil.getCode().equals(PofileEnum.ASI.name())) {
			list = policeDAO.getPolice_by_prod(produitDAO
					.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode()));
		} else {
			list = policeDAO
					.getPolice_by_conv_prod(conventionDAO
							.find_conv_by_Societ(societeDAO.find(cod_societ)),
							produitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD
									.getCode()));
			System.out.println("cod_societ=" + cod_societ);
			System.out
					.println("conventionDAO.find_conv_by_Societ(societeDAO.find(cod_societ)="
							+ conventionDAO.find_conv_by_Societ(
									societeDAO.find(cod_societ))
									.getIdConvention());
			System.out
					.println("produitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode())="
							+ produitDAO.getProduit_by_code(
									ENUMCODEPRODUIT.TD.getCode())
									.getCodeProduit());
			System.out.println("list=" + list);
		}

		for (Police police : list) {
			listnumplc.put(police.getLibelle().trim(), police.getNPolice());
		}

		return listnumplc;
	}

	public void updateDate() {

		Calendar c = Calendar.getInstance();
		c.setTime(dateEffet);
		c.add(Calendar.YEAR, dureeA);
		c.add(Calendar.MONTH, dureeM);
		c.add(Calendar.DAY_OF_MONTH, -1);
		dateEcheance = c.getTime();

	}

	public void updatepolice() {
		if (utilisateursConnexionDTO.getUser().getProfil().getCode()
				.equals(PofileEnum.TDST.name())
				|| utilisateursConnexionDTO.getUser().getProfil().getCode()
						.equals(PofileEnum.ASI.name())) {
			listnumplc = getlistnumplc(cod_societe, utilisateursConnexionDTO
					.getUser().getProfil());
		} else {
			cod_societe = utilisateursConnexionDTO.getUser().getAgence()
					.getSociete().getCodSociet();
			listnumplc = getlistnumplc(cod_societe, utilisateursConnexionDTO
					.getUser().getProfil());
		}
		System.out.println("cod_societe=" + cod_societe);
		System.out.println("utilisateur.getProfil()="
				+ utilisateursConnexionDTO.getUser().getProfil());
		System.out.println("listnumplc" + listnumplc);
		numplc = (String) listnumplc.values().toArray()[0];
		System.out.println("mumplc dans update police=" + numplc);
		calc_date();
	}

	public void calculAge() {

		int x = 0;
		if (getClient() != null && getClient().getDatNaiss() != null) {

			ageCalc = (AgeCalculator.calculateAge(getClient().getDatNaiss()));
			agem = ageCalc.getMonths();
			agej = ageCalc.getDays();

			controlAge = (AgeCalculator.calculateDifference(
					(getClient().getDatNaiss()), dateEcheance));

			System.out.println("client get date de naissance =============="
					+ getClient().getDatNaiss());
			System.out.println("controlAge kkkkkkkkkkkkkkkkkkkk : "
					+ controlAge);

			nbannee2 = controlAge.getYears();
			System.out.println("Nombre année kkkkkkkkkkkkkkkkkkkk : "
					+ nbannee2);
			nbmois2 = controlAge.getMonths();
			System.out.println("Nombre mois kkkkkkkkkkkkkkkkkkkk : " + nbmois2);

			nbjours2 = controlAge.getDays();
			System.out
					.println("Nombre days kkkkkkkkkkkkkkkkkkkk : " + nbjours2);

			if (nbannee2 < 75 || nbannee2 > 75) {

				age = nbannee2;
			} else if (nbannee2 == 75 && nbmois2 == 0 && nbjours2 == 0) {
				age = (nbannee2 - 1);
			} else {
				age = (nbannee2 + 1);
			}

			/******************** Control age 75 ans *************/

			if ((age < 75 && client.getTypePers().equalsIgnoreCase("PA"))) {
				checkedTD = true;
				System.out.println("Age < 75");
				// reinitialiser menuContenu
				initialiserListContenu();

			}

			else {
				// && ageCalc.getYears() >= 75 && age >= 75
				System.out.println("Age > 75");
				checkedTD = false;
				for (ContenuDTO c : listContenu) {
					System.out.println(c.getC().getLibellecontenu() + "     "
							+ c.isChecked() + "     "
							+ c.getC().getIdprodconv());
					if (c.getC().getIdprodconv() != null
							&& c.getC().getIdprodconv().getTarifGreleList() == null
							&& c.getC().getIdprodconv()
									.getTarifIncendieAgrList() == null) {
						// TD
						c.setChecked(false);
						c.setDisabled(true);
						idprodconvTD = 0;

						FacesContext
								.getCurrentInstance()
								.addMessage(
										"growl",
										new FacesMessage(
												FacesMessage.SEVERITY_ERROR,
												"Erreur: "
														+ "Souscription TD interdite pour age supérieur à 75 ans ou une personne morale",
												""));

					}
				}
			}

			// test pack 2

			x = gettype_pack();
			System.out
					.println("pack 2 valeur =============*********************=========================="
							+ x);

			if (x == 1) {

				checkedIncendie = true;
				// reinitialiser menuContenu
				initialiserListContenu();

			}

			else if (x == 2) {
				checkedIncendie = false;
				for (ContenuDTO c : listContenu) {
					if (c.getObligatoire() == 5) {
						c.setChecked(false);
						c.setDisabledCheckBox(true);
					}
				}
			}

			majContenu();
			tauxdeprime();
		}

		if (getClient() != null && getClient().getTypeDoc().equals("MatFiscal")) {

			x = gettype_pack();
			System.out
					.println("pack valeur =============*********************=========================="
							+ x);

			if (x == 1) {

				System.out.println("pack 2 true");
				checkedIncendie = true;
				System.out.println("pack 2 truee ========");
				// reinitialiser menuContenu
				initialiserListContenu();

			} else if (x == 2) {

				checkedIncendie = false;
				for (ContenuDTO c : listContenu) {
					if (c.getObligatoire() == 5) {
						c.setChecked(false);
						c.setDisabledCheckBox(true);
					}
				}
				// && ageCalc.getYears() >= 75 && age >= 75
				System.out.println("pack 2 false");
			}
			majContenu();
			// tauxdeprime();
		}
	}

	public void enregistrerClient() {

		setTabindex(2);
		client.setTypeVoie(typeVoieDAO.find(typeVoieCode));
		System.out.println("Profession:" + client.getProfession());
		System.out.println("type voie:" + client.getTypeVoie());
		// client.setSexe(sexe);
		ongletProduit = true;
		enregistrerProduit();
	}

	public void enregistrerGreleIncendie() {
		if (listParam.containsKey("PARCELLE") && listParcelle.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur: "
							+ "Veuillez ajouter au moins une parcelle", ""));
		} else {
			ongletTarif = true;
			setTabindex(tabindex + 1);
		}

	}

	public void enregistrerIncendieAL() {
		if (listParam.containsKey("TINCAGAL")&& situation_risque.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur: "
							+ "Veuillez ajouter la situation du risque", ""));
		}
		else {

			ongletTarif = true;
			setTabindex(tabindex + 1);
		}

	}

	public void enregistrerProduit() {
		
		for (ContenuDTO c : onglets) {
			if (c.isChecked()) {
				c.setDisabled(false);
			}
		}
		if (listContenu != null) {
			System.out.println("hhh"
					+ listContenu.get(listContenu.size() - 1).getNextTab());
			setTabindex(listContenu.get(listContenu.size() - 1).getNextTab());
		} else {
			setTabindex(3);
		}

		System.out.println("Profession:" + client.getProfession());
		System.out.println("type voie:" + client.getTypeVoie());
	}

	public void enregistrerGeneral() throws RemoteException, ParseException_Exception, ServiceException, DatatypeConfigurationException {
		System.out.println("****************enregistrerCapital");
		if(!decisionCredit.equals(numDecision)){
			rechercherDepuisWSbna();
		}else{
		calculDuree();

		if ((nbannee >= 12 && nbjours > 0) || (nbannee > 12)) {
			// updateGeneral = "panelGeneral";

			FacesContext
					.getCurrentInstance()
					.addMessage(
							"growl",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Erreur: "
											+ "Veuillez vérifier la date elle doit pas dépasser 12 mois",
									""));
			ongletclient = false;
			System.out.println("nbAnne>12");
		} else {
			// updateGeneral = "formContrat";
			setTabindex(1);
			ongletclient = true;
			System.out.println("nbAnne<12");
			initialiserListContenu();
		}

		if (capital == null || (capital != null && capital.equals(""))) {

			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur: "
							+ "Veuillez saisir le capital", ""));
		}
		}
	}

	public void tauxdeprime() {
		System.out.println("******capital=" + capital);
		System.out.println("******nbannee=" + nbannee);
		double valueCapital = 0;
		if (capital != null && !capital.equals("")) {
			valueCapital = Double.parseDouble(capital);
			libnumplc = policeDAO.find(numplc);
			duree = nbannee;

			if (duree != 0) {
				dureeA = 0;// (int) duree;
				dureeM = nbannee;// (int) ((duree - (int) duree) * 100);
				// updateDate();
			}

			System.out.println("age" + age);

			if (valueCapital != 0 && duree != 0 && age != 0) {
				// Police police = iPoliceDAO.find(libnumplc.getNPolice());

				String code = iTariftdDAO.getcodetarif_by_police(libnumplc
						.getNPolice());
				if (libnumplc.getTauxPrime() == 0) {
					
				System.out.println("tauxdeprime = iTariftdDAO.getTaux_prime(nbannee, age, code");
					tauxdeprime = iTariftdDAO.getTaux_prime(nbannee, age, code,
							valueCapital, libnumplc.getNPolice());
				} else {

					tauxdeprime = libnumplc.getTauxPrime();
				}
			}
		}
		// modif 07/01/2019
		if ((tauxdeprime != 0) && (client.getTypePers().equals("PA"))) {

			checkedTD = true;
		} else {
			checkedTD = false;
		}

		// fin modif 07/01/2019
		System.out.println("taux de prime:" + tauxdeprime);

	}

	public void calc_date() {
		System.out.println("calcdate numplc:" + numplc);
		mindate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(mindate);

		minage = iTariftdDAO.find_min_age(numplc) + 1;
		System.out.println("minage=" + minage);
		cal.add(Calendar.YEAR, -minage);
		mindate = cal.getTime();

		maxdate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(maxdate);
		maxage = iTariftdDAO.find_max_age(numplc);
		System.out.println("maxage=" + maxage);
		c.add(Calendar.YEAR, -maxage);
		maxdate = c.getTime();
	}

	public void calculDuree() {

		dureeCredit = AgeCalculator
				.calculateDifference(dateEffet, dateEcheance);
		nbannee = dureeCredit.getMonths() + dureeCredit.getYears() * 12;
		nbjours = dureeCredit.getDays();
		System.out.println("calcul duree=" + nbannee);
		if ((nbannee >= 12 && nbjours > 0) || (nbannee > 12)) {
			FacesContext.getCurrentInstance().addMessage(
					"growl",
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur: "
			+ "La durée ne doit pas être supérieur à 12 mois",""));
			
			// prime faces 6  01/11/2022
			RequestContext.getCurrentInstance().update("formContrat");
//			PrimeFaces.current().ajax().update("formContrat");

		} else if (nbannee <= 0) {
			Calendar cYears = Calendar.getInstance();
			cYears.setTime(dateEffet);
			cYears.add(Calendar.MONTH, 1);
			dateEcheance = cYears.getTime();
			System.out.println("calcul duree=" + dateEcheance);

			nbannee = 1;
		} else {
			tauxdeprime();
		}

	}

	public void calculTarifTD() {

		double valueCapital = Double.parseDouble(capital);
		// calcul du tarif
		System.out.println("age=" + age);

		System.out.println("minage=" + minage);
		System.out.println("maxage=" + maxage);

		if ((age <= minage) || (age >= maxage) || (age == 75)) {

			System.out.println("test1");
			FacesMessage msg = new FacesMessage("Date de naissance erronée!!",
					"ERROR");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("ERROR", msg);

			// checkedTD = false;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(dateEffet);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(auj);

		if (c.get(Calendar.DAY_OF_YEAR) < c1.get(Calendar.DAY_OF_YEAR)
				&& (c.get(Calendar.YEAR) == c1.get(Calendar.YEAR))
				&& !utilisateursConnexionDTO.getUser().getProfil().getCode()
						.equals(PofileEnum.TDST.name())) {
			// if (c.compareTo(c1) < 0 && !
			// utilisateur.getProfil().getCode().equals(PofileEnum.TDST.name())){
			FacesMessage msg = new FacesMessage(
					"La date d'effet doit être égale à la date du jour!",
					"La date d'effet doit être égale à la date du jour!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(
					"La date d'effet doit être égale à la date du jour!", msg);
		}

		System.out.println("test2");
		tauxdeprime();

		primeglobal = parametresTDService.Tarifier(valueCapital, tauxdeprime,
				dureeA, dureeM, tauxsurprime, majoration);
		adhesion = new Adhesion(0, valueCapital, effDate, datefin, duree, "V",
				franchise, utilisateursConnexionDTO.getUser()
						.getIdUtilisateurs(), majoration, primeglobal,
				tauxinteret, tauxdeprime, tauxsurprime, getClient(),
				policeDAO.find(libnumplc.getNPolice()), nadhesion, new Date());

		System.out.println("test3");

		if (getSelectedProposition() != null) {

			if (utilisateursConnexionDTO.getUser().getProfil().getCode()
					.equals(PofileEnum.TDST.name())) {
				getSelectedProposition().setTauxSprim(tauxsurprime);
				getSelectedProposition().setMajoration(majoration);
				getSelectedProposition().setEtat("E");
				adhesion.setEtat("E");
			}
			proposition = getSelectedProposition();

		} else {

			if (utilisateursConnexionDTO.getUser().getProfil().getCode()
					.equals(PofileEnum.TDST.name())) {
				adhesion.setEtat("E");
			}
			proposition = new Proposition(getAdhesion());

		}
		adhesion.setRef_credit(RefCredit);
		numact = paramActDAO.getParamAct_by_numact(age, valueCapital);
		lstAnalyse = detailActDAO.getDetailAct_by_numact(numact);

	}

	public Map<String, Object> getListnumplc() {
		return listnumplc;
	}

	public Date getMindate() {
		return mindate;
	}

	public void setMindate(Date mindate) {
		this.mindate = mindate;
	}

	public Date getMaxdate() {
		return maxdate;
	}

	public void setMaxdate(Date maxdate) {
		this.maxdate = maxdate;
	}

	public String getNumplc() {
		return numplc;
	}

	public void setNumplc(String numplc) {
		this.numplc = numplc;
	}

	public double getPrimeglobal() {
		return primeglobal;
	}

	public void setPrimeglobal(double primeglobal) {
		this.primeglobal = primeglobal;
	}

	public Adhesion getAdhesion() {
		return adhesion;
	}

	public double getTauxdeprime() {
		return tauxdeprime;
	}

	public void setTauxdeprime(double tauxdeprime) {
		this.tauxdeprime = tauxdeprime;
	}

	public int getNbannee() {
		return nbannee;
	}

	public void setNbannee(int nbannee) {
		this.nbannee = nbannee;
	}

	public Police getLibnumplc() {
		return libnumplc;
	}

	public void setLibnumplc(Police libnumplc) {
		this.libnumplc = libnumplc;
	}

	public Proposition getSelectedProposition() {
		return selectedProposition;
	}

	public void setSelectedProposition(Proposition selectedProposition) {
		this.selectedProposition = selectedProposition;
	}

	/* FIN TD */
	public PackBean() {
		// TODO Auto-generated constructor stub

	}

	@PostConstruct
	private void init() {
		listPrixHom = service.findPrixHom();

		listContrat = new ArrayList<ContratDTO>();
		docs = new ArrayList<String>();
		auj = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(auj);

		Calendar cYears = Calendar.getInstance();
		cYears.setTime(new Date());
		cYears.add(Calendar.YEAR, -18);

		int year = cYears.get(Calendar.YEAR);
		minDateNaissance = cYears.getTime();
		System.out.println("min date naissance :" + minDateNaissance);
		yearRange = "1900:" + year + "";
		dateEffet = new Date();
		dateEcheance = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dateEcheance);
		c.add(Calendar.YEAR, 1);
		dateEcheance = c.getTime();
		calculDuree();
		listAgrNature = service.getAgrNature();
		initialiserListContenu();
		listTarifGrele = service.getTarifGrele();
		listParcelle = new ArrayList<Parcelle>();
		initClient();
		//majContenu();
		tarifIncendie = new TarifAgrDTO();
		tarifGrele = new TarifAgrDTO();
		initTD();
	}

	private void initialiserListContenu() {
		// listContenu = serviceMenu.getMenuContenu("pack",
		// utilisateursConnexionDTO.getUser().getProfil().getIdProfil());
		listContenu = serviceMenu.getMenuContenuByPack("pack",
				utilisateursConnexionDTO.getUser().getProfil().getIdProfil(),
				type_pack);
		if(numDecision!=null && (listContenu ==null ||listContenu.isEmpty())){
			FacesMessage msg = new FacesMessage("ERREUR Pack innexistant",
					"Pack innexistant");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		}else{
			listParam = serviceMenu.findParametresByPack(type_pack);
			 for (Map.Entry<String, ParametresPack> entry : listParam.entrySet())  
		            System.out.println("Key = " + entry.getKey() + 
		                             ", Value = " + entry.getValue()); 
			System.out.println("-------------");
			for (ContenuDTO c : listContenu) {
				System.out.println(c);
			}
			 majContenu();
		}
		
	}

	public void tarifier() {
		/*
		 * primeTotale = new BigDecimal(0); System.out.println("TYPE VOIE :" +
		 * typeVoieCode + "Profession:" + client.getProfession()); if
		 * (listParam.containsKey("ZONE")&& selectedZone == null) { FacesContext
		 * .getCurrentInstance() .addMessage( "growl", new FacesMessage(
		 * FacesMessage.SEVERITY_ERROR, "Erreur: " +
		 * "Veuillez bien remplir le formulaire Grele/Incendie", "")); } else {
		 * if (listParam.containsKey("PARCELLE")&& listParcelle.isEmpty()) {
		 * FacesContext .getCurrentInstance() .addMessage( "growl", new
		 * FacesMessage( FacesMessage.SEVERITY_ERROR, "Erreur: " +
		 * "Veuillez ajouter au moins une parcelle", "")); } else {
		 * 
		 * tarifGrele = service.getTarifByClasseZone(selectedClasse,
		 * selectedZone, new BigDecimal(capital), 0);
		 * 
		 * primeGrele = tarifGrele.getPrime(); tauxgrele = tarifGrele.getTaux();
		 * if (listParam.containsKey("RED1") && checkedGrele && checkedIncendie
		 * && checkedTD)// reduction { Tauxreduction tauxReduction = service
		 * .getTauxreductionValide(idprodconvGrele,type_pack); BigDecimal p =
		 * new BigDecimal(primeGrele); BigDecimal p2 = new
		 * BigDecimal(primeGrele);
		 * 
		 * double d = p.doubleValue(); if (tauxReduction != null) {
		 * System.out.println("taux Reduction:" + tauxReduction.getTaux()); p =
		 * p.subtract(p.multiply(tauxReduction.getTaux() .divide(new
		 * BigDecimal(100)))); System.out.println("Prime:" + primeGrele +
		 * " apre red:" + p); } if (d > 15) { primeGrele = p.toString();
		 * tarifGrele.setPrime(primeGrele); packComplet = true; }
		 * 
		 * else { primeGrele = p2.toString(); tarifGrele.setPrime(primeGrele);
		 * packComplet = true; }
		 * 
		 * } // grele et td else if (listParam.containsKey("RED2")&&checkedGrele
		 * && checkedTD) {
		 * 
		 * BigDecimal p2 = new BigDecimal(primeGrele); Tauxreduction
		 * tauxReduction2 = service
		 * .getTauxreductionValide(idprodconvGrele,type_pack);
		 * 
		 * 
		 * BigDecimal p = new BigDecimal(primeGrele); double d =
		 * p.doubleValue(); p = p.subtract(p.multiply(tauxReduction2.getTaux()
		 * .divide(new BigDecimal(100))));
		 * 
		 * if (d > 15) { primeGrele = p.toString();
		 * tarifGrele.setPrime(primeGrele); packComplet = true; }
		 * 
		 * else { primeGrele = p2.toString(); tarifGrele.setPrime(primeGrele);
		 * packComplet = true; }
		 * 
		 * }
		 * 
		 * else { packComplet = false; } if (checkedGrele) {
		 * 
		 * primeTotale = primeTotale.add(new BigDecimal(tarifGrele
		 * .getPrime())); } System.out.println("PRIME= " + primeGrele);
		 * System.out.println("Bean:" + this);
		 * 
		 * if (checkedIncendie) { tarifIncendie = service.getTarifByIdprodConv(
		 * idprodconvIncendie, new BigDecimal(capital),
		 * trouveCheckedExtension(1), trouveCheckedExtension(2));
		 * 
		 * primeTotale = primeTotale.add(new BigDecimal(tarifIncendie
		 * .getPrime())); } if (checkedTD && client.getTypePers().equals("PA"))
		 * { calculTarifTD(); primeTotale = primeTotale.add(new
		 * BigDecimal(primeglobal)); } save = true; } }
		 */
		
	   //BigDecimal tarif661 = new BigDecimal(0);
	   BigDecimal taux661 = new BigDecimal(0.002);
		primeTotale = new BigDecimal(0);
		System.out.println("TYPE VOIE :" + typeVoieCode + "Profession:"
				+ client.getProfession());
		if (listParam.containsKey("ZONE") && selectedZone == null) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"growl",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Erreur: "
											+ "Veuillez bien remplir le formulaire Grele/Incendie",
									""));
		}
		if (listParam.containsKey("PARCELLE") && listParcelle.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur: "
							+ "Veuillez ajouter au moins une parcelle", ""));
		}
		if (listParam.containsKey("TGRELE")) {
			//packComplet = false;
			tarifGrele = service.getTarifByClasseZone(selectedClasse,
					selectedZone, new BigDecimal(capital), 0);

			primeGrele = tarifGrele.getPrime();
			tauxgrele = tarifGrele.getTaux();
			if (checkedTD && listParam.containsKey("RED1")
					&& listParam.containsKey("TINCAG")
					&& listParam.containsKey("TTD"))// reduction
			{
				//packComplet = true;
				Tauxreduction tauxReduction = service.getTauxreductionValide(
						idprodconvGrele, type_pack);
				BigDecimal p = new BigDecimal(primeGrele);
				BigDecimal p2 = new BigDecimal(primeGrele);

				double d = 0.0;
				if (tauxReduction != null) {
					System.out.println("taux Reduction:"
							+ tauxReduction.getTaux());
					p = p.subtract(p.multiply(tauxReduction.getTaux().divide(
							new BigDecimal(100))));
					 d = p.doubleValue();
					System.out
							.println("Prime:" + primeGrele + " apre red:" + p);
					System.out.println("d========== "+d);
				}
				if (d > 15) {
					primeGrele = p.toString();
					tarifGrele.setPrime(primeGrele);
				
				}

				else {
					//primeGrele = p2.toString();
					primeGrele = "15";

					tarifGrele.setPrime(primeGrele);
				
				}

			}
			// grele et td
			
			
			else if (checkedTD && listParam.containsKey("RED2")
					&& listParam.containsKey("TTD")) {
				//packComplet = true;

				BigDecimal p2 = new BigDecimal(primeGrele);
				Tauxreduction tauxReduction2 = service.getTauxreductionValide(
						idprodconvGrele, type_pack);

				BigDecimal p = new BigDecimal(primeGrele);
				double d = p.doubleValue();
				p = p.subtract(p.multiply(tauxReduction2.getTaux().divide(
						new BigDecimal(100))));

				if (d > 15) {
					primeGrele = p.toString();
					tarifGrele.setPrime(primeGrele);
			
				}

				else {
					//primeGrele = p2.toString();
					
					primeGrele = "15";

					tarifGrele.setPrime(primeGrele);
					
				}

			}
			primeTotale = primeTotale
					.add(new BigDecimal(tarifGrele.getPrime()));
			
		}

		/*else {
			packComplet = false;
		}*/
		/////// voir a ce niveau

		System.out.println("PRIME grele= " + primeGrele);
		System.out.println("Bean:" + this);

		if (listParam.containsKey("TINCAG")) {
			tarifIncendie = service.getTarifByIdprodConv(idprodconvIncendie,
					new BigDecimal(capital), trouveCheckedExtension(1),
					trouveCheckedExtension(2));

			primeTotale = primeTotale.add(new BigDecimal(tarifIncendie
					.getPrime()));
		}
		if (listParam.containsKey("TINCAGAL")) {
			System.out.println("calcul tarif incendie aliment");
			checkedIncAl = true;
			if (codespec == 661) {				
				//tarif661 = (new BigDecimal(capital).multiply(taux661));
				tarifIncendie = service.gettarif661(taux661, new BigDecimal(capital));
				primeTotale = primeTotale.add(new BigDecimal(tarifIncendie.getPrime()));				
			}
			
			else {
				
			System.out.println("incendiealiment***" + checkedIncAl);
			tarifIncendie = service.getTarifByIdprodConv(idprodconvIncendie,
					new BigDecimal(capital), false, false);
			System.out.println("tarif****incendie" + tarifIncendie.getPrime());

			primeTotale = primeTotale.add(new BigDecimal(tarifIncendie
					.getPrime()));
			}
		}
		
		/*if (listParam.containsKey("TINCAGAL")&& listParam.containsKey("TTD")&& client.getTypePers().equals("PA")&& checkedTD) {
			packComplet = true;
			
		}*/
		if (listParam.containsKey("TTD")&& client.getTypePers().equals("PA")&& checkedTD) {
			packComplet = true;
			
		}
		else {
			packComplet = false;
		}
		
		/*if (!listParam.containsKey("TTD")&& !client.getTypePers().equals("PA")&& checkedTD) {
			packComplet = false;
			
		}*/
		
		if (listParam.containsKey("TTD") && client.getTypePers().equals("PA")&& checkedTD) {
			calculTarifTD();
			primeTotale = primeTotale.add(new BigDecimal(primeglobal));
		}
		save = true;

	}

	public void tarifierdevis() {
		primeTotale = new BigDecimal(0);
		System.out.println("TYPE VOIE :" + typeVoieCode + "Profession:"
				+ client.getProfession());
		if (selectedZone == null) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"growl",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Erreur: "
											+ "Veuillez bien remplir le formulaire Grele/Incendie",
									""));
		} else {
			if (listParcelle.isEmpty()) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"growl",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Erreur: "
												+ "Veuillez ajouter au moins une parcelle",
										""));
			} else {
				System.out.println("Enregistrer NATURE " + selectedNature);
				System.out.println("Enregistrer CLASSE " + selectedClasse);
				System.out.println("Enregistrer ZONE "
						+ new BigDecimal(selectedZone));

				System.out.println("Enregistrer GRELe " + checkedGrele);
				System.out.println("Enregistrer INCENDIE " + checkedIncendie);
				System.out.println("Enregistrer TD " + checkedTD);

				System.out.println("Enregistrer capital " + capital);

				tarifGrele = service.getTarifByClasseZone(selectedClasse,
						selectedZone, new BigDecimal(capital), 0);

				primeGrele = tarifGrele.getPrime();
				tauxgrele = tarifGrele.getTaux();
				if (checkedGrele && checkedIncendie && checkedTD)// reduction
				{
					Tauxreduction tauxReduction = service
							.getTauxreductionValide(idprodconvGrele, type_pack);
					BigDecimal p = new BigDecimal(primeGrele);
					if (tauxReduction != null) {
						System.out.println("taux Reduction:"
								+ tauxReduction.getTaux());
						p = p.subtract(p.multiply(tauxReduction.getTaux()
								.divide(new BigDecimal(100))));
						System.out.println("Prime:" + primeGrele + " apre red:"
								+ p);
					}
					primeGrele = p.toString();
					tarifGrele.setPrime(primeGrele);
					//packComplet = true;
				} else {
					//packComplet = false;
				}
				if (checkedGrele) {

					primeTotale = primeTotale.add(new BigDecimal(tarifGrele
							.getPrime()));
					System.out
							.println("je suis ici dans le tarifier devis grele=======");
				}
				System.out.println("PRIME Grele= " + primeGrele);
				System.out.println("Bean:" + this);
				if (checkedIncendie) {
					tarifIncendie = service.getTarifByIdprodConv(
							idprodconvIncendie, new BigDecimal(capital),
							trouveCheckedExtension(1),
							trouveCheckedExtension(2));
					primeTotale = primeTotale.add(new BigDecimal(tarifIncendie
							.getPrime()));

					System.out
							.println("je suis ici dans le tarifier devis incendie =======");
				}
				if (devistd == true) {
					System.out
							.println("je suis ici dans le tarifier devis td =======");

					xdevis = (Double.parseDouble(capital) * 0.01);

					if (xdevis < 15) {
						xdevis = 15;
					}

					System.out.println("xxxxxxxxxxxxxxxxxxx" + xdevis);

					// calculTarifTD();
					primeTotale = primeTotale.add(new BigDecimal(xdevis));

					System.out.println("prime totalllllllll" + primeTotale);
				}
				save = true;
			}
		}

	}

	private boolean trouveCheckedExtension(int typeEx) {
		boolean trouve = false;
		int i = 0;
		do {
			if ((typeEx == 1 && listParcelle.get(i).isExtensionIncendie())
					|| (typeEx == 2 && listParcelle.get(i)
							.isMajorationVoisinageIncendie())) {
				trouve = true;
			}
			i++;
		} while (!trouve && i < listParcelle.size());
		return trouve;
	}

	public void enregistrerPolice() {
		tarifier();
		System.out.println("enregistrerPolice********** ");
		BigInteger idPackPolice = service.getMaxPackPolicePK();
		if (listParam.containsKey("TINCAGAL")) {

			TypeVoie v = new TypeVoie();

			if (typeVoieCode != 0) {
				v.setCode(typeVoieCode);
				client.setTypeVoie(v);
			}

			client = service.enregistrerClient(client);
			contratIncendieAl = service.enregistrerAgricoleIncendieAliment(
					idprodconvIncendie, dateEffet, dateEcheance, tarifIncendie,
					client, utilisateursConnexionDTO.getUser(), new BigDecimal(
							capital), idPackPolice, packComplet, numDecision,
					numDetailDecision, situation_risque);
			listContrat.add(contratIncendieAl);

			

			save = false;
			imprimer = true;

		}
		
		if (listParam.containsKey("TINCAGAL")&& listParam.containsKey("TTD") && client.getTypePers().equals("PA")) {
			System.out.println("Enregistrement TD");
			contratTD = service.enregistrerAgricoleTD(idprodconvTD,
					dateEffet, dateEcheance, primeglobal, client,
					utilisateursConnexionDTO.getUser(), numplc,
					idPackPolice, numDecision, numDetailDecision);
			listContrat.add(contratTD);
		}
		

		if (listParcelle.isEmpty()&& (listParam.containsKey("TINCAG")||listParam.containsKey("TGRELE"))) {

			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur: "
							+ "Veuillez ajouter au moins une parcelle", ""));
			save = true;
			imprimer = false;
		} else {
			listParcelleModel = new ArrayList<com.star.Ba.model.Parcelle>();
			for (Parcelle p : listParcelle) {
				com.star.Ba.model.Parcelle pa = new com.star.Ba.model.Parcelle();
				pa.setIddelegation(p.getIddelegation());
				pa.setNbrpied(p.getNbrPied());
				pa.setSuperficie(p.getSuperficie());
				pa.setVariete(p.getVariete());
				// pa.setVariete(prodspeculation);
				// pa.setVariete("kacem2");
				pa.setPrixunitaire(p.getPrixUnitaire());
				pa.setNumtitrefoncier(p.getNumTitreFoncier());
				pa.setSituation("");
				pa.setLocalite(p.getLocalite());
				if (p.isExtensionGrele()) {
					pa.setExtensiongrele(1);
				} else {
					pa.setExtensiongrele(0);
				}

				if (p.isExtensionIncendie()) {
					pa.setExtensionincendie(1);
				} else {
					pa.setExtensionincendie(0);
				}

				if (p.isMajorationVoisinageIncendie()) {
					pa.setMajorationvoisinageincendie(1);
				} else {
					pa.setMajorationvoisinageincendie(0);
				}
				listParcelleModel.add(pa);
			}

			System.out.println("TYPE VOIE :" + typeVoieCode + "Profession:"
					+ client.getProfession());
			// ProfessionClient p = new ProfessionClient();
			// if(idProfession!=0){ p.setIdProfession(idProfession);
			// client.setProfession(p);}
			// adhesion.setNum_decision(numDecision);

			TypeVoie v = new TypeVoie();

			if (typeVoieCode != 0) {
				v.setCode(typeVoieCode);
				client.setTypeVoie(v);
			}

			client = service.enregistrerClient(client);


			if (checkedIncendie) {
				System.out.println("Enregistrement Incendie");
				contratIncendie = service.enregistrerAgricoleIncendieGrele(
						idprodconvIncendie, dateEffet, dateEcheance,
						tarifIncendie, client,
						utilisateursConnexionDTO.getUser(), listParcelleModel,
						selectedClasse, new BigDecimal(capital), idPackPolice,
						packComplet, numDecision, numDetailDecision , nbrparcelle);
				listContrat.add(contratIncendie);
			}

			if (checkedGrele) {
				System.out.println("Enregistrement Grele");
				contratGrele = service.enregistrerAgricoleIncendieGrele(
						idprodconvGrele, dateEffet, dateEcheance, tarifGrele,
						client, utilisateursConnexionDTO.getUser(),
						listParcelleModel, selectedClasse, new BigDecimal(
								capital), idPackPolice, packComplet,
						numDecision, numDetailDecision, nbrparcelle);
				listContrat.add(contratGrele);
			}
			if (checkedTD && !listParam.containsKey("TINCAGAL")) {
			System.out.println("Enregistrement TD");
			contratTD = service.enregistrerAgricoleTD(idprodconvTD,
					dateEffet, dateEcheance, primeglobal, client,
					utilisateursConnexionDTO.getUser(), numplc,
					idPackPolice, numDecision, numDetailDecision);
			listContrat.add(contratTD);
			}
			

			save = false;
			imprimer = true;
		}

	}

	public void ajouterParcelle() {

		if (selectedNature.equals("1") && selectedClasse.equals("classe1")) {
			// cereale
			bprixHom = true;
			}

		else {
			bprixHom = false;
		}

		if (selectedNature.equals("2")) {
			bpieds = true;
		}

		else {
			bpieds = false;

		}

		Parcelle p = new Parcelle();
		p.setIndex(listParcelle.size());
		p.setVariete(speculationDetail);
		if (speculationDetail == "FOURRAGE D ETE"
				|| speculationDetail == "FOURRAGE HIVER"
				|| speculationDetail == "FOURRAGE") {
			p.setPrixUnitaire(new BigDecimal(5));
		}
		listParcelle.add(p);
		nbrparcelle = nbrparcelle + 1;
		
		
	
	}

	public void onChangeNature() {
		System.out.println("onChangeNature " + selectedNature);

		if (selectedNature != null) {
			bClasse = false;
			chercherclasse();

			/*
			 * if (selectedNature.equals("1")) {// cereale bprixHom = true;}
			 * else {bprixHom = false; }
			 */
		} else {
			bClasse = true;
		}
		chercherclasse();
	}

	public void onChangeClasse() {
		System.out.println("onChangeClasse " + selectedClasse);

		if (selectedClasse != null) {
			bZone = false;
			if (selectedNature.equals("1") && selectedClasse.equals("classe1")) {

				// cereale

				bprixHom = true;
			}

			else {
				bprixHom = false;

			}

		} else {
			bZone = true;
		}
	}

	public void onChangeZone() {
		System.out.println("onChangeZone " + selectedZone);
		if (selectedZone != null) {
			bCapitale = false;
			chargerDelegation();
		} else {
			bCapitale = true;
		}
	}

	public void onChangeVariete(Parcelle p) {

		System.out.println("onChangeVariete :" + p.getVariete());

		for (PrixHom pr : listPrixHom) {

			if ((pr.getLibelle().equals(p.getVariete()))
					&& (pr.getIdprix().toString() != "5")) {

				if (pr.getValeur() != null) {
					p.setPrixUnitaire(pr.getValeur());
					System.out.println("Prix unitaire =" + p.getPrixUnitaire());
					bprixHom2 = true;
					System.out.println("bprixhom2 ============" + bprixHom2);

				}

				else {
					p.setPrixUnitaire(pr.getValeur());
					System.out.println("Prix unitaire =" + p.getPrixUnitaire());
					bprixHom2 = false;
					System.out.println("bprixhom2 ============" + bprixHom2);

				}

			}

		}
		// listvarietes = iVarieteDAO.findvariete(selectedClasse);

	}

	private void chargerDelegation() {
		listDelegation = service.getDelegationByZone(new BigDecimal(
				selectedZone));
	}

	private void chercherclasse() {
		boolean trouve = false;
		int i = 0;
		do {
			if (listAgrNature.get(i).getIdnature().toString()
					.equals(selectedNature)) {
				trouve = true;
				nature = listAgrNature.get(i);
				listAgrClasseNature = listAgrNature.get(i)
						.getAgrclasseNatureList();
				libelleSelectedNature = listAgrNature.get(i).getLibellenature();
			}
			i++;
		} while (!trouve && i < listAgrNature.size());
	}

	public void majContenu() {
		System.out.println("MAJ CONTENU");
		onglets = new ArrayList<ContenuDTO>();
		docs = new ArrayList<String>();
		for (ContenuDTO c : listContenu) {
			// test pack 1 ou pack 2

			System.out.println(c.getC().getLibellecontenu() + "     "
					+ c.isChecked() + "     " + c.getC().getIdprodconv());

			if (c.getC().getIdprodconv() != null
					&& c.isChecked()
					&& (onglets.isEmpty() || !chercherOnglets(c.getC().getUrl()))) {
				onglets.add(c);
			}
			if (c.getC().getIdprodconv() != null
					&& c.isChecked()
					&& c.getC().getIdprodconv().getTarifIncendieAgrList() != null
					&& !c.getC().getIdprodconv().getTarifIncendieAgrList()
							.isEmpty()) {
				// Incendie
				idprodconvIncendie = c.getC().getIdprodconv().getIdProdconv();
			}
			if (c.getC().getIdprodconv() != null && c.isChecked()
					&& c.getC().getIdprodconv().getTarifGreleList() != null) {
				// grele
				idprodconvGrele = c.getC().getIdprodconv().getIdProdconv();
			}
			if (c.getC().getIdprodconv() != null
					&& c.isChecked()
					&& c.getC().getIdprodconv().getTarifGreleList() == null
					&& c.getC().getIdprodconv().getTarifIncendieAgrList() == null) {
				// TD
				idprodconvTD = c.getC().getIdprodconv().getIdProdconv();
			}

		}
		checkedGrele = listParam.containsKey("TGRELE");
		if (checkedGrele) {
			docs.add("CP Grele");
		}

		checkedIncendie = listParam.containsKey("TINCAG");
		if (checkedIncendie) {
			docs.add("CP Incendie");
		}
		if (client != null && client.getTypePers() != null) {
			System.out.println("client.getTypePers().equals PA:"
					+ client.getTypePers().equals("PA"));
		}

		System.out.println("checkedTD:" + checkedTD);
		if (chercherCheckedTD() && checkedTD
				&& client.getTypePers().equals("PA")) {
			checkedTD = true;
			docs.add("NOTICE TD");
			System.out.println("checked TD");
		} else {
			checkedTD = false;
			System.out.println("NOT checked TD");
		}

		if (listParam.containsKey("TINCAGAL")) {
			checkedIncAl = true;
			System.out.println("checked inc al 2 ****"+checkedIncAl);
			docs.add("CP Incendie Al");
			//docs.add("FDR INC AL");
			System.out.println("checked Incendie AL");
		} else {
			checkedIncAl = false;
			System.out.println("NOT checked Incendie Al");
		}
		if (!listParam.containsKey("TINCAGAL")){
		docs.add("FDR");
		}
		docs.add("QUITTANCE");
	}

	private boolean chercherCheckedTD() {
		boolean trouve = false;
		int i = 0;
		do {
			if (listContenu.get(i).isChecked()
					&& listContenu.get(i).getC().getIdprodconv() != null
					&& listContenu.get(i).getC().getIdprodconv()
							.getTarifGreleList() == null
					&& listContenu.get(i).getC().getIdprodconv()
							.getTarifIncendieAgrList() == null) {
				trouve = true;
			}
			i++;
		} while (!trouve && i < listContenu.size());
		return trouve;
	}

	private boolean chercherCheckedGrele() {
		boolean trouve = false;
		int i = 0;
		do {
			if (listContenu.get(i).isChecked()
					&& listContenu.get(i).getC().getIdprodconv() != null
					&& listContenu.get(i).getC().getIdprodconv()
							.getTarifGreleList() != null) {
				trouve = true;
			}
			i++;
		} while (!trouve && i < listContenu.size());
		return trouve;
	}

	private boolean chercherCheckedIncendie() {
		boolean trouve = false;
		int i = 0;
		do {
			if (listContenu.get(i).isChecked()
					&& listContenu.get(i).getC().getIdprodconv() != null
					&& listContenu.get(i).getC().getIdprodconv()
							.getTarifIncendieAgrList() != null

					&& listContenu.get(i).getC().getIdprodconv()
							.getTarifIncendieAgrList().size() > 0) {
				System.out.println("idprodconv**************"
						+ listContenu.get(i).getC().getIdprodconv());
				trouve = true;
			}
			i++;
		} while (!trouve && i < listContenu.size());
		return trouve;
	}

	private boolean chercherOnglets(String url) {
		boolean trouve = false;
		int i = 0;
		do {
			if (onglets.get(i).getC().getUrl().equals(url)) {
				trouve = true;
			}
			i++;
		} while (!trouve && i < onglets.size());
		return trouve;
	}

	/************************** DEBUT CLient */
	public void initClient() {
		System.out.println("init client bean");
		allGouverneras = new ArrayList<String>();
		client = new Client();
		client.setTypePers("PA");
		typeVoies = typeVoieDAO.findAll();
		allGouverneras = locgouvDAO.findGouv();
		professions = professionClientDAO.findAll();
		selectedLocgouv = new Locgouv();
	}

	public void onChangeTYpeIdentifiant() {

		if (client != null && client.getTypeDoc().equals("MatFiscal")) {
			societe = true;
			client.setTypePers("SO");
			System.out.println("typepersonne =====" + client.getTypePers());
			client.setProfession(null);
			// client.setProfession(iProfessionClientDAO.find(10));
			checkedTD = false;
			System.out.println("======== voir TD ==== " + checkedTD);
			for (ContenuDTO c : listContenu) {
				if (c.getObligatoire() == 2) {
					c.setChecked(false);
				} else if (c.getObligatoire() == 3) {
					c.setDisabledCheckBox(true);
					c.setChecked(false);
				}
			}

		} else {
			societe = false;
			checkedTD = true;
			client.setTypePers("PA");
			for (ContenuDTO c : listContenu) {
				if (c.getObligatoire() == 2) {
					c.setChecked(false);

				} else if (c.getObligatoire() == 3) {
					c.setDisabledCheckBox(false);
				}
				/* else if (c.getObligatoire() == 0) {c.setChecked(true);} */
			}
		}

		/*
		 * if (client != null && client.getTypeDoc().equals("MatFiscal") &&
		 * type_pack == 1) { checkedIncendie = false; for (ContenuDTO c :
		 * listContenu) {if (c.getObligatoire() == 5) { c.setChecked(false);
		 * c.setDisabledCheckBox(true); } } }
		 */
	}

	public void identificationValidator(FacesContext context, UIComponent comp,
			Object value) {

		String docTypeId = ComponentUtils.findComponentClientId("docType");
		if (docTypeId != null) {
			UIInput docTypeInput = (UIInput) context.getViewRoot()
					.findComponent(docTypeId);
			if (docTypeInput != null) {
				String docType = (String) docTypeInput.getValue();

				if (docType != null && docType.equals("CIN")
						&& client.getTypePers().equals("PA")) {
					System.out.println("type ====== " + client.getTypePers());

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

				else if (docType != null && docType.equals("MatFiscal")
						&& client.getTypePers().equals("SO")) {
					String matf = (String) value;

					// cin should start with either 0 or 1, length = 8
					Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
					Matcher matcher = pattern.matcher(matf);
					if (!matcher.find()) {
						String msg = "matricule fiscal erreur";
						throw new ValidatorException(new FacesMessage(
								FacesMessage.SEVERITY_ERROR, msg, msg));
					}

				}
			}

		}

	}

	public Date getMaxDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -18);
		return calendar.getTime();
	}

	public Converter getProfessionConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (ProfessionClient professionClient : getProfessions()) {

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

	public List<ProfessionClient> getProfessions() {
		return professions;
	}

	public void setProfessions(List<ProfessionClient> professions) {
		this.professions = professions;
	}

	public List<TypeVoie> getTypeVoies() {
		return typeVoies;
	}

	public void setTypeVoies(List<TypeVoie> typeVoies) {
		this.typeVoies = typeVoies;
	}

	Map<String, Object> listCPT = new LinkedHashMap<String, Object>();
	List<Locgouv> allCodePostalByGouvernera = new ArrayList<Locgouv>();
	Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat = new HashMap<String, List<Locgouv>>();

	public List<Locgouv> getAllCodePostalByGouvernera() {
		return allCodePostalByGouvernera;
	}

	public void setAllCodePostalByGouvernera(
			List<Locgouv> allCodePostalByGouvernera) {
		this.allCodePostalByGouvernera = allCodePostalByGouvernera;
	}

	public Map<String, Object> getListCPT() {
		return listCPT;
	}

	public void setListCPT(Map<String, Object> listCPT) {
		this.listCPT = listCPT;
	}

	public Map<String, List<Locgouv>> getMapAllCodePostalByGouvernerat() {
		return mapAllCodePostalByGouvernerat;
	}

	public void setMapAllCodePostalByGouvernerat(
			Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat) {
		this.mapAllCodePostalByGouvernerat = mapAllCodePostalByGouvernerat;
	}

	public void onSelectTypeVoie() {
		System.out.println("onSelectTypeVoie :" + typeVoieCode);
	}

	public void updateListCodePostalActionListener() {

		System.out.println("updateListCodePostalActionListener : "
				+ client.getGouver());

		if (client.getGouver() != null && (!client.getGouver().equals(""))) {
			// allCodePostalByGouvernera.clear();
			allCodePostalByGouvernera = new ArrayList<Locgouv>();
			client.setCodeP(null);
			client.setLocalite(null);
			selectedLocgouv = new Locgouv();
			allCodePostalByGouvernera = locgouvDAO.findByGouv(client
					.getGouver());// (mapAllCodePostalByGouvernerat.get(newAdhesion.getGouver()));
			System.out.println("gov1 ");

		} else {
			allCodePostalByGouvernera = new ArrayList<Locgouv>();
			client.setCodeP(null);
			client.setLocalite(null);
			client.setGouver(null);
			System.out.println("gov2");

		}

	}

	public void locgouvSelectListened() {

		System.out.println("sys1 bis: " + selectedLocgouvCode);

		searching = true;
		selectedLocgouv = locgouvDAO.findByCode(selectedLocgouvCode);
		Locgouv locgouv = selectedLocgouv;
		if (locgouv != null) {
			client.setCodeP(locgouv.getCodePosta());
			client.setLocalite(locgouv.getLocalite());
			client.setGouver(locgouv.getGouvernora());
		} else {
			client.setCodeP("");
			client.setLocalite("");
			client.setGouver("");
		}
	}

	public String getSelectedGouverneras() {
		return selectedGouverneras;
	}

	public void setSelectedGouverneras(String selectedGouverneras) {
		this.selectedGouverneras = selectedGouverneras;
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

	/************************** FIN CLient */
	/************************* Impression *****/
	public void imprimer(String d) {
		System.out.println("Imprimer :" + d);
		if (d.equals("FDR")) {
			imprimerFDR();
		} 
		
		
		/*else if (d.equals("FDR INC AL")) {
			// imprimer CP Grele
			imprimerFDRIncAl();
		}
		*/else if (d.equals("CP Grele")) {
			// imprimer CP Grele
			imprimerCPGrele();
		} else if (d.equals("CP Incendie")) {
			// imprimer CP Inc
			imprimerCPIncendie();
		} else if (d.equals("CP Incendie Al")) {
			// imprimer CP Inc
			imprimerCPIncendieAl();
		} else if (d.equals("QUITTANCE")) {
			// imprimer quittance
			imprimerQuittance();
		} else if (d.equals("NOTICE TD")) {
			// imprimer NOTICE
			imprimerTD();
		}
	}

	public void imprimerTD() {
		System.out.println("NOTICE TD");
		Adhesion adhesion = contratTD.getAdhesion();
		try {

			String report = com.star.Ba.commun.Constante.PDF
					+ "adhesion.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			parameters.put("numeroPolice", adhesion.getPolice().getNPolice());
			parameters.put("numAdhesion", adhesion.getNadhesion());
			parameters.put("numdecision", adhesion.getNum_decision());

			parameters.put("contractant", adhesion.getPolice().getConvention()
					.getSociete().getRaisonSoc());
			parameters.put("adresse", adhesion.getPolice().getConvention()
					.getSociete().getAdresse());
			parameters.put("clientName", adhesion.getClient().getNomClient()
					.toUpperCase()
					+ " "
					+ adhesion.getClient().getPrenomClient().toUpperCase());
			parameters.put("clientBirthday",
					sdf.format(adhesion.getClient().getDatNaiss()));
			parameters.put("clientCIN",
					String.valueOf(adhesion.getClient().getIdentifiant()));
			parameters.put("dateEffet", sdf.format(adhesion.getDatDebut()));
			parameters.put("dateFin", sdf.format(adhesion.getDatFin()));
			parameters.put("duree", nbannee + "");
			parameters.put("ageLimit", adhesion.getPolice().getAgelimit()
					.toString());
			parameters.put("imagesociete", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");
			String garBase = "";
			String garExclusion = "";
			String garOpt = "";
			String optExclusion = "";

			Police police = adhesion.getPolice();
			List<GarantieVie> list = police.getGarantieVies();
			for (GarantieVie garantieVie : list) {

				if (garantieVie.getGarBase() != null
						&& !garantieVie.getGarBase().isEmpty()) {
					garBase += garantieVie.getGarBase() + "\n";
				}
				if (garantieVie.getGarExclusion() != null
						&& !garantieVie.getGarExclusion().isEmpty()) {
					garExclusion += garantieVie.getGarExclusion() + "\n";
				}
				if (garantieVie.getGarOpt() != null
						&& !garantieVie.getGarOpt().isEmpty()) {
					garOpt += garantieVie.getGarOpt() + "\n";
				}
				if (garantieVie.getOptExclusion() != null
						&& !garantieVie.getOptExclusion().isEmpty()) {
					optExclusion += garantieVie.getOptExclusion() + "\n";
				}
			}

			parameters.put("garBase", garBase.trim());
			parameters.put("garExclusion", garExclusion.trim());
			parameters.put("garOpt", garOpt.trim());
			parameters.put("optExclusion", optExclusion.trim());

			parameters.put("capital", capital);
			parameters.put("adresseagence", adhesion.getUtilisateur().getAgence().getLibelle());
			
			double primeFga = adhesion.getMtPrime()+adhesion.getFga();

			/*parameters.put("mtPrime",
					(new DecimalFormat("#0")).format(adhesion.getMtPrime()));*/
			
			parameters.put("mtPrime",
					(new DecimalFormat("#0")).format(primeFga));

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\""
							+ adhesion.getPolice().getNPolice() + "Notice.pdf"
							+ "\"");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

			adhesion.setImpr(true);
			adhesionDAO.edit(adhesion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void imprimerTDconsul(Adhesion adhesion) {
		System.out.println("NOTICE TD");
		double x = (adhesion.getMtPrime() * 100);
		String cap = Double.toString(x);

		try {

			String report = com.star.Ba.commun.Constante.PDF
					+ "adhesion.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			parameters.put("numeroPolice", adhesion.getPolice().getNPolice());
			parameters.put("numdecision", adhesion.getNum_decision());
			parameters.put("numAdhesion", adhesion.getNadhesion());
			parameters.put("contractant", adhesion.getPolice().getConvention()
					.getSociete().getRaisonSoc());
			parameters.put("adresse", adhesion.getPolice().getConvention()
					.getSociete().getAdresse());
			parameters.put("clientName", adhesion.getClient().getNomClient()
					.toUpperCase()
					+ " "
					+ adhesion.getClient().getPrenomClient().toUpperCase());
			parameters.put("clientBirthday",
					sdf.format(adhesion.getClient().getDatNaiss()));
			parameters.put("clientCIN",
					String.valueOf(adhesion.getClient().getIdentifiant()));
			parameters.put("dateEffet", sdf.format(adhesion.getDatDebut()));
			parameters.put("dateFin", sdf.format(adhesion.getDatFin()));
			parameters.put("duree", nbannee + "");
			parameters.put("ageLimit", adhesion.getPolice().getAgelimit()
					.toString());
			parameters.put("imagesociete", adhesion.getUtilisateur()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");
			String garBase = "";
			String garExclusion = "";
			String garOpt = "";
			String optExclusion = "";

			Police police = adhesion.getPolice();
			List<GarantieVie> list = police.getGarantieVies();
			for (GarantieVie garantieVie : list) {

				if (garantieVie.getGarBase() != null
						&& !garantieVie.getGarBase().isEmpty()) {
					garBase += garantieVie.getGarBase() + "\n";
				}
				if (garantieVie.getGarExclusion() != null
						&& !garantieVie.getGarExclusion().isEmpty()) {
					garExclusion += garantieVie.getGarExclusion() + "\n";
				}
				if (garantieVie.getGarOpt() != null
						&& !garantieVie.getGarOpt().isEmpty()) {
					garOpt += garantieVie.getGarOpt() + "\n";
				}
				if (garantieVie.getOptExclusion() != null
						&& !garantieVie.getOptExclusion().isEmpty()) {
					optExclusion += garantieVie.getOptExclusion() + "\n";
				}
			}

			parameters.put("garBase", garBase.trim());
			parameters.put("garExclusion", garExclusion.trim());
			parameters.put("garOpt", garOpt.trim());
			parameters.put("optExclusion", optExclusion.trim());

			parameters.put("capital", cap);
			parameters.put("adresseagence", adhesion.getUtilisateur().getAgence().getLibelle());

			// parameters.put("mtPrime",(new
			// DecimalFormat("#0.000")).format(adhesion.getMtPrime()));
			
			
			double primeFga = adhesion.getMtPrime()+adhesion.getFga();

			
			
			parameters.put("mtPrime",
					(new DecimalFormat("#0")).format(primeFga));

			/*parameters.put("mtPrime",
					(new DecimalFormat("#0")).format(adhesion.getMtPrime()));*/

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\""
							+ adhesion.getPolice().getNPolice() + "Notice.pdf"
							+ "\"");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

			adhesion.setImpr(true);
			adhesionDAO.edit(adhesion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void supprimerParcelle(Parcelle p) {
		System.out.println("avant supprimerParcelle list size: "
				+ listParcelle.size());
		listParcelle.remove(p);
		System.out.println("apres  supprimerParcelle list size: "
				+ listParcelle.size());
	}

	public void imprimerCPGrele() {
		String report;
		try {

			report = com.star.Ba.commun.Constante.PDF + "CPGRELE.jasper";

			System.out.println("CP GRELE:" + com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nom = client.getNomClient();
			if (client.getPrenomClient() != null
					&& !client.getPrenomClient().equals(null)) {
				nom += "  " + client.getPrenomClient();
			}

			double cout = 0;
			cout = contratGrele.getAdhesion().getFga();
			double cout1 = 0;
			cout1 = (contratGrele.getAdhesion().getCout()).doubleValue();

			double coutf = 0;
			coutf = cout + cout1;

			String adrk = client.getCodeP() + "," + client.getGouver() + ","
					+ client.getLocalite() + "," + client.getAdrClient();
			double frais2;

			if (checkedTD == true) {

				frais2 = 0.000;
			} else {
				frais2 = 15.000;
			}

			parameters.put("imagesociete", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");
			parameters.put("Contrat", contratGrele.getPolice().getNPolice());
			parameters.put("DateEffet",
					sdf.format(contratGrele.getAdhesion().getDatDebut()));
			parameters.put("Expiration",
					sdf.format(contratGrele.getAdhesion().getDatFin()));
			parameters.put("Echeance",
					sdf.format(contratGrele.getAdhesion().getDatFin()));
			parameters.put("Avenant", "");
			parameters.put("adresseagence", contratGrele.getAdhesion().getUtilisateur().getAgence().getLibelle());

			parameters.put("Fractionnement", "");
			parameters.put("capital", capital);
			parameters.put("taux", tauxgrele);
			parameters.put("frais", formatter.format(frais2));
			parameters.put("PtVente", utilisateursConnexionDTO.getUser()
					.getAgence().getCodeAgence());
			parameters.put("DateDeb",
					sdf.format(contratGrele.getAdhesion().getDatDebut()));
			parameters.put("DateFin",
					sdf.format(contratGrele.getAdhesion().getDatFin()));
			parameters.put("Assure", nom);
			parameters.put("Adresse", adrk);
			parameters.put("franchise", contratGrele.getAdhesion()
					.getFranchise());
			parameters.put("Cin", contratGrele.getClient().getIdentifiant());
			parameters.put("mfS2", client.getTypeDoc());
			parameters.put(
					"PrimeNette",
					(new DecimalFormat("#0.000")).format(contratGrele
							.getAdhesion().getMtPrime()) + "");
			parameters.put(
					"Cout",
					(new DecimalFormat("#0.000")).format(contratGrele
							.getAdhesion().getCout()) + "");
			parameters.put("Cout", coutf + "");

			parameters.put("Taxes", contratGrele.getAdhesion().getTaxe());
			parameters.put(
					"PrimeTotale",
					(new DecimalFormat("#0.000")).format(contratGrele
							.getAdhesion().getPrimetot() + 3) + "");
			parameters.put("QSouscripteur", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getRaisonSoc());
			// parameters.put("contratautre", contratIncendie.getPolice()
			// .getNPolice());
			List<ParcellePrint> listParcellePrint = new ArrayList<ParcellePrint>();
			for (com.star.Ba.model.Parcelle pa : contratGrele.getParcelles()) {
				ParcellePrint p = new ParcellePrint();
				if (pa.getIddelegation() != null) {
					p.setLocalisation(pa.getSituation()
							+ " "
							+ service.getDelegationBYID(pa.getIddelegation())
									.getLibelledelegation());
				}
				p.setNatureRecoltes(pa.getVariete());
				p.setPrixUnitaire((new DecimalFormat("#0.000")).format(
						pa.getPrixunitaire()).toString()
						+ " " + pa.getUnitePRix());
				p.setRendement(pa.getRendement() + " " + pa.getUniteRendement());
				p.setSuperficie(pa.getSuperficie() + "");
				p.setNomParcelle(pa.getNumtitrefoncier());
				if (pa.getNbrpied() == null) {
					p.setNbrPieds("Néant");
				} else {
					p.setNbrPieds(pa.getNbrpied() + "");
				}
				listParcellePrint.add(p);
			}

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
					listParcellePrint);
			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, beanCollectionDataSource);
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=CP_GRELE.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);

			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void imprimerCPGreleconsul(Adhesion adhesion) {
		String report;
		List<com.star.Ba.model.Parcelle> listparce = new ArrayList<>();
		listparce = parcelledao.getParbynpolice(adhesion.getNadhesion());
		try {

			report = com.star.Ba.commun.Constante.PDF + "CPGRELE.jasper";

			System.out.println("CP GRELE:" + com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nom = adhesion.getClient().getNomClient() ;
			if( adhesion.getClient().getPrenomClient()!=null){
				nom+= " "
						+ adhesion.getClient().getPrenomClient();
			}
			double cout = 0;
			cout = adhesion.getFga();
			double cout1 = 0;
			cout1 = (adhesion.getCout()).doubleValue();

			double coutf = 0;
			coutf = cout + cout1;

			String adrk = adhesion.getClient().getCodeP() + ","
					+ adhesion.getClient().getGouver() + ","
					+ adhesion.getClient().getLocalite() + ","
					+ adhesion.getClient().getAdrClient();
			double frais2;

			frais2 = adhesion.getCout().doubleValue();

			parameters.put("imagesociete", adhesion.getUtilisateur()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");
			parameters.put("Contrat", adhesion.getPolice().getNPolice());
			parameters.put("DateEffet", sdf.format(adhesion.getDatDebut()));
			parameters.put("Expiration", sdf.format(adhesion.getDatFin()));
			parameters.put("Echeance", sdf.format(adhesion.getDatFin()));
			parameters.put("Avenant", "");
			parameters.put("adresseagence", adhesion.getUtilisateur().getAgence().getLibelle());

			parameters.put("Fractionnement", "");
			parameters.put("capital", adhesion.getCapital());
			parameters.put("taux", tauxgrele);
			parameters.put("frais", formatter.format(frais2));
			parameters.put("PtVente", adhesion.getUtilisateur().getAgence()
					.getCodeAgence());
			parameters.put("DateDeb", sdf.format(adhesion.getDatDebut()));
			parameters.put("DateFin", sdf.format(adhesion.getDatFin()));
			parameters.put("Assure", nom);
			parameters.put("Adresse", adrk);
			parameters.put("franchise", adhesion.getFranchise());
			parameters.put("Cin", adhesion.getClient().getIdentifiant());
			parameters.put("mfS2", adhesion.getClient().getTypeDoc());
			parameters.put("PrimeNette",
					(new DecimalFormat("#0.000")).format(adhesion.getMtPrime())
							+ "");
			parameters.put("Cout",
					(new DecimalFormat("#0.000")).format(adhesion.getCout())
							+ "");
			parameters.put("Cout", coutf + "");

			parameters.put("Taxes", adhesion.getTaxe());
			parameters
					.put("PrimeTotale",
							(new DecimalFormat("#0.000")).format(adhesion
									.getPrimetot() + 3) + "");
			parameters.put("QSouscripteur", adhesion.getUtilisateur()
					.getAgence().getSociete().getRaisonSoc());
			// parameters.put("contratautre", contratIncendie.getPolice()
			// .getNPolice());
			List<ParcellePrint> listParcellePrint = new ArrayList<ParcellePrint>();
			for (com.star.Ba.model.Parcelle pa : listparce) {
				ParcellePrint p = new ParcellePrint();
				if (pa.getIddelegation() != null) {
					p.setLocalisation(service.getDelegationBYID(
							pa.getIddelegation()).getLibelledelegation());
				}
				p.setNatureRecoltes(pa.getVariete());
				p.setPrixUnitaire((new DecimalFormat("#0.000")).format(
						pa.getPrixunitaire()).toString()
						+ " " + pa.getUnitePRix());
				p.setRendement(pa.getRendement() + " " + pa.getUniteRendement());
				p.setSuperficie(pa.getSuperficie() + "");
				p.setNomParcelle(pa.getNumtitrefoncier());
				if (pa.getNbrpied() == null) {
					p.setNbrPieds("Néant");
				} else {
					p.setNbrPieds(pa.getNbrpied() + "");
				}
				listParcellePrint.add(p);
			}

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
					listParcellePrint);
			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, beanCollectionDataSource);

			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=CP_GRELE.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);

			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void imprimerCPIncendie() {
		String report;
		try {

			report = com.star.Ba.commun.Constante.PDF + "CPINCENDIE.jasper";
			System.out.println("CP INCENDIE:"
					+ com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nom = client.getNomClient();
			if (client.getPrenomClient() != null
					&& !client.getPrenomClient().equals(null)) {
				nom += "  " + client.getPrenomClient();
			}

			double cout = 0;
			cout = contratIncendie.getAdhesion().getFga();
			double cout1 = 0;
			cout1 = (contratIncendie.getAdhesion().getCout()).doubleValue();

			double coutf = 0;
			coutf = cout + cout1;

			String adrk = client.getCodeP() + "," + client.getGouver() + ","
					+ client.getLocalite() + "," + client.getAdrClient();

			double frais;

			if (checkedTD == true) {

				frais = 0.000;
			} else {
				frais = 15.000;
			}

			parameters.put("imagesociete", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");
			parameters.put("Contrat", contratIncendie.getPolice().getNPolice());
			parameters.put("DateEffet",
					sdf.format(contratIncendie.getAdhesion().getDatDebut()));
			parameters.put("Expiration",
					sdf.format(contratIncendie.getAdhesion().getDatFin()));
			parameters.put("Echeance",
					sdf.format(contratIncendie.getAdhesion().getDatFin()));
			parameters.put("Avenant", "");
			parameters.put("adresseagence", contratIncendie.getAdhesion().getUtilisateur().getAgence().getLibelle());

			parameters.put("Fractionnement", "");
			parameters.put("PtVente", utilisateursConnexionDTO.getUser()
					.getAgence().getCodeAgence());
			parameters.put("DateDeb",
					sdf.format(contratIncendie.getAdhesion().getDatDebut()));
			parameters.put("DateFin",
					sdf.format(contratIncendie.getAdhesion().getDatFin()));
			parameters.put("Assure", nom);
			parameters.put("Adresse", adrk);
			parameters.put("capital", capital);

			parameters.put("frais", formatter.format(frais));
			parameters.put("franchise", contratIncendie.getAdhesion()
					.getFranchise());
			parameters.put("Cin", contratIncendie.getClient().getIdentifiant());
			parameters.put("mfS2", client.getTypeDoc());
			parameters.put(
					"PrimeNette",
					formatter
							.format(contratIncendie.getAdhesion().getMtPrime())
							+ "");
			/*
			 * parameters.put("Cout",
			 * formatter.format(contratIncendie.getAdhesion().getCout()) + "");
			 */

			parameters.put("Cout", coutf + "");
			if (contratIncendie.getAdhesion().getTaxe() != null) {
				parameters.put("Taxes", contratIncendie.getAdhesion().getTaxe()
						+ "");
			}
			parameters.put(

					"PrimeTotale",
					(new DecimalFormat("#0.000")).format(contratIncendie
							.getAdhesion().getPrimetot() + 3) + "");

			parameters.put("QSouscripteur", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getRaisonSoc());
			parameters.put("contratautre", contratGrele.getPolice()
					.getNPolice());

			List<ParcellePrint> listParcellePrint = new ArrayList<ParcellePrint>();
			for (com.star.Ba.model.Parcelle pa : contratIncendie.getParcelles()) {
				ParcellePrint p = new ParcellePrint();
				if (pa.getIddelegation() != null) {
					p.setLocalisation(pa.getSituation()
							+ " "
							+ service.getDelegationBYID(pa.getIddelegation())
									.getLibelledelegation());
				}
				p.setNatureRecoltes(pa.getVariete());
				p.setPrixUnitaire(formatter.format(pa.getPrixunitaire())
						.toString() + " " + pa.getUnitePRix());
				p.setRendement(pa.getRendement() + " " + pa.getUniteRendement());
				p.setSuperficie(pa.getSuperficie() + "");
				p.setNomParcelle(pa.getNumtitrefoncier());
				if (pa.getNbrpied() == null) {
					p.setNbrPieds("Néant");
				} else {
					p.setNbrPieds(pa.getNbrpied() + "");
				}
				listParcellePrint.add(p);
			}

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
					listParcellePrint);
			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, beanCollectionDataSource);
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=CP_INCENDIE.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// imprimer incendie aliementation

	public void imprimerCPIncendieAl() {
		String report;
		try {

			report = com.star.Ba.commun.Constante.PDF + "CPINCENDIEP3.jasper";
			System.out.println("CP INCENDIE:"
					+ com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nom = client.getNomClient();
			if (client.getPrenomClient() != null
					&& !client.getPrenomClient().equals(null)) {
				nom += "  " + client.getPrenomClient();
			}

			double cout = 0;
			cout = contratIncendieAl.getAdhesion().getFga();
			double cout1 = 0;
			cout1 = (contratIncendieAl.getAdhesion().getCout()).doubleValue();

			double coutf = 0;
			coutf = cout + cout1;

			String adrk = client.getCodeP() + "," + client.getGouver() + ","
					+ client.getLocalite() + "," + client.getAdrClient();

			double frais;

			if (checkedTD == true) {

				frais = 0.000;
			} else {
				frais = 15.000;
			}

			parameters.put("imagesociete", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");
			parameters.put("Contrat", contratIncendieAl.getPolice()
					.getNPolice());
			parameters.put("DateEffet",
					sdf.format(contratIncendieAl.getAdhesion().getDatDebut()));
			parameters.put("Expiration",
					sdf.format(contratIncendieAl.getAdhesion().getDatFin()));
			parameters.put("Echeance",
					sdf.format(contratIncendieAl.getAdhesion().getDatFin()));
			parameters.put("Avenant", "");
			parameters.put("adresseagence", contratIncendieAl.getAdhesion().getUtilisateur().getAgence().getLibelle());

			parameters.put("Fractionnement", "");
			parameters.put("PtVente", utilisateursConnexionDTO.getUser()
					.getAgence().getCodeAgence());
			parameters.put("DateDeb",
					sdf.format(contratIncendieAl.getAdhesion().getDatDebut()));
			parameters.put("DateFin",
					sdf.format(contratIncendieAl.getAdhesion().getDatFin()));
			parameters.put("Assure", nom);
			parameters.put("Adresse", adrk);
			parameters.put("capital", capital);

			parameters.put("frais", formatter.format(frais));
			parameters.put("franchise", contratIncendieAl.getAdhesion()
					.getFranchise());
			parameters.put("Cin", contratIncendieAl.getClient()
					.getIdentifiant());
			parameters.put("mfS2", client.getTypeDoc());
			parameters.put(
					"PrimeNette",
					formatter.format(contratIncendieAl.getAdhesion()
							.getMtPrime()) + "");
			/*
			 * parameters.put("Cout",
			 * formatter.format(contratIncendie.getAdhesion().getCout()) + "");
			 */

			parameters.put("Cout", coutf + "");
			if (contratIncendieAl.getAdhesion().getTaxe() != null) {
				parameters.put("Taxes", contratIncendieAl.getAdhesion()
						.getTaxe() + "");
			}
			parameters.put(

					"PrimeTotale",
					(new DecimalFormat("#0.000")).format(contratIncendieAl
							.getAdhesion().getPrimetot() + 3) + "");

			parameters.put("QSouscripteur", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getRaisonSoc());

			parameters.put("situation", contratIncendieAl.getAdhesion()
					.getSituation_risque());

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=CPINCENDIEP3.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void imprimerCPIncendieconsul(Adhesion adhesion) {
		String report;
		List<com.star.Ba.model.Parcelle> listparce = new ArrayList<>();
		listparce = parcelledao.getParbynpolice(adhesion.getNadhesion());
		try {

			report = com.star.Ba.commun.Constante.PDF + "CPINCENDIE.jasper";
			System.out.println("CP INCENDIE:"
					+ com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nom = adhesion.getClient().getNomClient() ;
			if( adhesion.getClient().getPrenomClient()!=null){
				nom+= " "
						+ adhesion.getClient().getPrenomClient();
			}
			double cout = 0;
			cout = adhesion.getFga();
			double cout1 = 0;
			cout1 = (adhesion.getCout()).doubleValue();

			double coutf = 0;
			coutf = cout + cout1;

			String adrk = adhesion.getClient().getCodeP() + ","
					+ adhesion.getClient().getGouver() + ","
					+ adhesion.getClient().getLocalite() + ","
					+ adhesion.getClient().getAdrClient();

			double frais;
			frais = adhesion.getCout().doubleValue();
			parameters.put("imagesociete", adhesion.getUtilisateur()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");
			parameters.put("Contrat", adhesion.getPolice().getNPolice());
			parameters.put("DateEffet", sdf.format(adhesion.getDatDebut()));
			parameters.put("Expiration", sdf.format(adhesion.getDatFin()));
			parameters.put("Echeance", sdf.format(adhesion.getDatFin()));
			parameters.put("Avenant", "");
			parameters.put("adresseagence", adhesion.getUtilisateur().getAgence().getLibelle());

			parameters.put("Fractionnement", "");
			parameters.put("PtVente", adhesion.getUtilisateur().getAgence()
					.getCodeAgence());
			parameters.put("DateDeb", sdf.format(adhesion.getDatDebut()));
			parameters.put("DateFin", sdf.format(adhesion.getDatFin()));
			parameters.put("Assure", nom);
			parameters.put("Adresse", adrk);
			parameters.put("capital", adhesion.getCapital());

			parameters.put("frais", formatter.format(frais));
			parameters.put("franchise", adhesion.getFranchise());
			parameters.put("Cin", adhesion.getClient().getIdentifiant());
			parameters.put("mfS2", adhesion.getClient().getTypeDoc());
			parameters.put("PrimeNette",
					formatter.format(adhesion.getMtPrime()) + "");
			/*
			 * parameters.put("Cout",
			 * formatter.format(contratIncendie.getAdhesion().getCout()) + "");
			 */

			parameters.put("Cout", coutf + "");
			if (adhesion.getTaxe() != null) {
				parameters.put("Taxes", adhesion.getTaxe() + "");
			}
			parameters
					.put(

					"PrimeTotale",
							(new DecimalFormat("#0.000")).format(adhesion
									.getPrimetot() + 3) + "");
			parameters.put("QSouscripteur", adhesion.getUtilisateur()
					.getAgence().getSociete().getRaisonSoc());
			parameters.put("contratautre", adhesion.getPolice().getNPolice());

			List<ParcellePrint> listParcellePrint = new ArrayList<ParcellePrint>();
			for (com.star.Ba.model.Parcelle pa : listparce) {
				ParcellePrint p = new ParcellePrint();
				// if (pa.getIddelegation() != null) {
				p.setLocalisation(service.getDelegationBYID(
						pa.getIddelegation()).getLibelledelegation());
				// }
				p.setNatureRecoltes(pa.getVariete());
				p.setPrixUnitaire(formatter.format(pa.getPrixunitaire())
						.toString() + " " + pa.getUnitePRix());
				p.setRendement(pa.getRendement() + " " + pa.getUniteRendement());
				p.setSuperficie(pa.getSuperficie() + "");
				p.setNomParcelle(pa.getNumtitrefoncier());
				if (pa.getNbrpied() == null) {
					p.setNbrPieds("Néant");
				} else {
					p.setNbrPieds(pa.getNbrpied() + "");
				}
				listParcellePrint.add(p);
			}

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
					listParcellePrint);
			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, beanCollectionDataSource);
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=CP_INCENDIE.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	public void imprimerCPIncendieAlconsul(Adhesion adhesion) {
		String report;
		List<com.star.Ba.model.Parcelle> listparce = new ArrayList<>();
		listparce = parcelledao.getParbynpolice(adhesion.getNadhesion());
		try {

			report = com.star.Ba.commun.Constante.PDF + "CPINCENDIEP3.jasper";
			System.out.println("CP INCENDIEAl:"
					+ com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nom = adhesion.getClient().getNomClient() ;
			if( adhesion.getClient().getPrenomClient()!=null){
				nom+= " "
						+ adhesion.getClient().getPrenomClient();
			}
			double cout = 0;
			cout = adhesion.getFga();
			double cout1 = 0;
			cout1 = (adhesion.getCout()).doubleValue();

			double coutf = 0;
			coutf = cout + cout1;

			String adrk = adhesion.getClient().getCodeP() + ","
					+ adhesion.getClient().getGouver() + ","
					+ adhesion.getClient().getLocalite() + ","
					+ adhesion.getClient().getAdrClient();

			double frais;
			frais = adhesion.getCout().doubleValue();
			parameters.put("imagesociete", adhesion.getUtilisateur()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");
			parameters.put("Contrat", adhesion.getPolice().getNPolice());
			parameters.put("DateEffet", sdf.format(adhesion.getDatDebut()));
			parameters.put("Expiration", sdf.format(adhesion.getDatFin()));
			parameters.put("Echeance", sdf.format(adhesion.getDatFin()));
			parameters.put("Avenant", "");
			parameters.put("adresseagence", adhesion.getUtilisateur().getAgence().getLibelle());

			parameters.put("Fractionnement", "");
			parameters.put("PtVente", adhesion.getUtilisateur().getAgence()
					.getCodeAgence());
			parameters.put("DateDeb", sdf.format(adhesion.getDatDebut()));
			parameters.put("DateFin", sdf.format(adhesion.getDatFin()));
			parameters.put("Assure", nom);
			parameters.put("Adresse", adrk);
			parameters.put("capital", adhesion.getCapital());

			parameters.put("frais", formatter.format(frais));
			parameters.put("franchise", adhesion.getFranchise());
			parameters.put("Cin", adhesion.getClient().getIdentifiant());
			parameters.put("mfS2", adhesion.getClient().getTypeDoc());
			parameters.put("PrimeNette",
					formatter.format(adhesion.getMtPrime()) + "");
			/*
			 * parameters.put("Cout",
			 * formatter.format(contratIncendie.getAdhesion().getCout()) + "");
			 */

			parameters.put("Cout", coutf + "");
			if (adhesion.getTaxe() != null) {
				parameters.put("Taxes", adhesion.getTaxe() + "");
			}
			parameters
					.put(

					"PrimeTotale",
							(new DecimalFormat("#0.000")).format(adhesion
									.getPrimetot() + 3) + "");
			parameters.put("QSouscripteur", adhesion.getUtilisateur()
					.getAgence().getSociete().getRaisonSoc());
			parameters.put("contratautre", adhesion.getPolice().getNPolice());
			parameters.put("situation", adhesion.getSituation_risque());
					

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=CPINCENDIEP3.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	

	public void imprimerFDR() {
		String report;

		try {

			report = com.star.Ba.commun.Constante.PDF + "FDR.jasper";
			System.out.println("FDR:" + com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nom = client.getNomClient();
			if (client.getPrenomClient() != null
					&& !client.getPrenomClient().equals(null)) {
				nom += "  " + client.getPrenomClient();
			}

			parameters.put("imagesociete", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");

			parameters.put("star2", "ouufff.png");
			parameters.put("Contrat", contratGrele.getPolice().getNPolice());
			parameters.put("DateEffet",
					sdf.format(contratGrele.getAdhesion().getDatDebut()));
			parameters.put("Expiration",
					sdf.format(contratGrele.getAdhesion().getDatFin()));
			parameters.put("Echeance",
					sdf.format(contratGrele.getAdhesion().getDatFin()));
			parameters.put("Avenant", "");
			parameters.put("adresseagence", contratGrele.getAdhesion().getUtilisateur().getAgence().getLibelle());

			parameters.put("Fractionnement", "");
			parameters.put("PtVente", utilisateursConnexionDTO.getUser()
					.getAgence().getCodeAgence());
			parameters.put("DateDeb",
					sdf.format(contratGrele.getAdhesion().getDatDebut()));
			parameters.put("DateFin",
					sdf.format(contratGrele.getAdhesion().getDatFin()));
			parameters.put("Assure", nom);
			parameters.put("AdresseA", getAdresseClient());
			parameters.put("TelA", client.getTel());
			parameters.put("FaxA", "");
			if (societe) {
				parameters.put("bsociete", "X");
			} else {
				parameters.put("bparticulier", "X");
			}
			if (client.getProfession() != null) {
				parameters.put("ProfessionA", client.getProfession()
						.getLibelleProfession());
			}

			parameters.put("Cin", contratGrele.getClient().getIdentifiant());
			parameters.put("PrimeNette", contratGrele.getAdhesion()
					.getMtPrime() + "");
			parameters.put("Cout", contratGrele.getAdhesion().getCout() + "");
			parameters.put("Taxes", contratGrele.getAdhesion().getTaxe() + "");
			parameters.put("PrimeTotale", contratGrele.getAdhesion()
					.getPrimetot() + "");
			parameters.put("QSouscripteur", "");
			parameters.put("NomS", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getRaisonSoc());
			parameters.put("AdresseS", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getAdresse());
			parameters.put("TelS", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getTelphone());
			parameters.put("FaxS", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getFax());
			parameters.put("mfS", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getMatfisc());
			parameters.put("mfS2", client.getTypeDoc());

			List<ParcellePrint> listParcellePrint = new ArrayList<ParcellePrint>();
			for (com.star.Ba.model.Parcelle pa : contratGrele.getParcelles()) {
				ParcellePrint p = new ParcellePrint();
				if (pa.getIddelegation() != null) {
					p.setLocalisation(pa.getSituation()
							+ " "
							+ service.getDelegationBYID(pa.getIddelegation())
									.getLibelledelegation());
				}
				p.setNatureRecoltes(pa.getVariete());
				p.setPrixUnitaire(formatter.format(pa.getPrixunitaire())
						.toString() + " " + pa.getUnitePRix());
				p.setRendement(pa.getRendement() + " " + pa.getUniteRendement());
				p.setSuperficie(pa.getSuperficie() + "");
				p.setNomParcelle(pa.getNumtitrefoncier());
				p.setVariete(pa.getVariete());
				if (pa.getNbrpied() == null) {
					p.setNbrPieds("Néant");
				} else {
					p.setNbrPieds(pa.getNbrpied() + "");
				}

				listParcellePrint.add(p);
			}

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
					listParcellePrint);
			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, beanCollectionDataSource);
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=FDR.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	//fdr incendie
	
	public void imprimerFDRIncAl() {
		String report;

		try {

			report = com.star.Ba.commun.Constante.PDF + "FDRINCAL.jasper";
			System.out.println("FDR:" + com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nom = client.getNomClient();
			if (client.getPrenomClient() != null
					&& !client.getPrenomClient().equals(null)) {
				nom += "  " + client.getPrenomClient();
			}

			parameters.put("imagesociete", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");

			parameters.put("star2", "ouufff.png");
			parameters.put("adresseagence", contratIncendieAl.getAdhesion().getUtilisateur().getAgence().getLibelle());

			parameters.put("Contrat", contratIncendieAl.getPolice().getNPolice());
			parameters.put("DateEffet",
					sdf.format(contratIncendieAl.getAdhesion().getDatDebut()));
			parameters.put("Expiration",
					sdf.format(contratIncendieAl.getAdhesion().getDatFin()));
			parameters.put("Echeance",
					sdf.format(contratIncendieAl.getAdhesion().getDatFin()));
			parameters.put("Avenant", "");
			parameters.put("Fractionnement", "");
			parameters.put("PtVente", utilisateursConnexionDTO.getUser()
					.getAgence().getCodeAgence());
			parameters.put("DateDeb",
					sdf.format(contratIncendieAl.getAdhesion().getDatDebut()));
			parameters.put("DateFin",
					sdf.format(contratIncendieAl.getAdhesion().getDatFin()));
			parameters.put("Assure", nom);
			parameters.put("AdresseA", getAdresseClient());
			parameters.put("TelA", client.getTel());
			parameters.put("FaxA", "");
			if (societe) {
				parameters.put("bsociete", "X");
			} else {
				parameters.put("bparticulier", "X");
			}
			if (client.getProfession() != null) {
				parameters.put("ProfessionA", client.getProfession()
						.getLibelleProfession());
			}

			parameters.put("Cin", contratIncendieAl.getClient().getIdentifiant());
			parameters.put("PrimeNette", contratIncendieAl.getAdhesion()
					.getMtPrime() + "");
			parameters.put("Cout", contratIncendieAl.getAdhesion().getCout() + "");
			parameters.put("Taxes", contratIncendieAl.getAdhesion().getTaxe() + "");
			parameters.put("PrimeTotale", contratIncendieAl.getAdhesion()
					.getPrimetot() + "");
			parameters.put("QSouscripteur", "");
			parameters.put("NomS", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getRaisonSoc());
			parameters.put("AdresseS", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getAdresse());
			parameters.put("TelS", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getTelphone());
			parameters.put("FaxS", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getFax());
			parameters.put("mfS", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getMatfisc());
			parameters.put("mfS2", client.getTypeDoc());


			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=FDRINCAL.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
	
	
	public void imprimerFDRconsult(Adhesion adhesion) {
		String report;
		List<com.star.Ba.model.Parcelle> listparce = new ArrayList<>();
		listparce = parcelledao.getParbynpolice(adhesion.getNadhesion());

		try {

			report = com.star.Ba.commun.Constante.PDF + "FDR.jasper";
			System.out.println("FDR:" + com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			String nom = adhesion.getClient().getNomClient() ;
			if( adhesion.getClient().getPrenomClient()!=null){
				nom+= " "
						+ adhesion.getClient().getPrenomClient();
			}

			parameters.put("imagesociete", adhesion.getUtilisateur()
					.getAgence().getSociete().getCodSociet()
					+ ".png");

			parameters.put("star", "star2018.png");

			parameters.put("star2", "ouufff.png");
			parameters.put("Contrat", adhesion.getNadhesion());
			parameters.put("DateEffet", sdf.format(adhesion.getDatDebut()));
			parameters.put("Expiration", sdf.format(adhesion.getDatFin()));
			parameters.put("Echeance", sdf.format(adhesion.getDatFin()));
			parameters.put("Avenant", "");
			parameters.put("adresseagence", adhesion.getUtilisateur().getAgence().getLibelle());

			parameters.put("Fractionnement", "");
			parameters.put("PtVente", adhesion.getUtilisateur().getAgence()
					.getCodeAgence());

			parameters.put("DateDeb", sdf.format(adhesion.getDatDebut()));
			parameters.put("DateFin", sdf.format(adhesion.getDatFin()));
			parameters.put("Assure", nom);
			parameters.put("AdresseA", adhesion.getClient().getNumAdr() + " "
					+ adhesion.getClient().getTypeVoie().getLibelle() + " "
					+ adhesion.getClient().getAdrClient() + " "
					+ adhesion.getClient().getCodeP() + " "
					+ adhesion.getClient().getLocalite() + " "
					+ adhesion.getClient().getGouver());
			parameters.put("TelA", adhesion.getClient().getTel());
			parameters.put("FaxA", "");
			if (societe) {
				parameters.put("bsociete", "X");
			} else {
				parameters.put("bparticulier", "X");
			}
			if (adhesion.getClient()!=null && adhesion.getClient().getProfession() != null) {
				parameters.put("ProfessionA", adhesion.getClient()
						.getProfession().getLibelleProfession());
			}
			else {
				parameters.put("ProfessionA", "");

			}

			parameters.put("Cin", adhesion.getClient().getIdentifiant());
			parameters.put("PrimeNette", adhesion.getMtPrime() + "");
			parameters.put("Cout", adhesion.getCout() + "");
			parameters.put("Taxes", adhesion.getTaxe() + "");
			parameters.put("PrimeTotale", adhesion.getPrimetot() + "");
			parameters.put("QSouscripteur", "");
			parameters.put("NomS", adhesion.getUtilisateur().getAgence()
					.getSociete().getRaisonSoc());
			parameters.put("AdresseS", adhesion.getUtilisateur().getAgence()
					.getSociete().getAdresse());
			parameters.put("TelS", adhesion.getUtilisateur().getAgence()
					.getSociete().getTelphone());
			parameters.put("FaxS", adhesion.getUtilisateur().getAgence()
					.getSociete().getFax());
			parameters.put("mfS", adhesion.getUtilisateur().getAgence()
					.getSociete().getMatfisc());

			parameters.put("mfS2", adhesion.getClient().getTypeDoc());

			Collection<ParcellePrint> listParcellePrint = new ArrayList<ParcellePrint>();
			for (com.star.Ba.model.Parcelle pa : listparce) {

				ParcellePrint p = new ParcellePrint();
				if (pa.getIddelegation() != null) {
					p.setLocalisation(service.getDelegationBYID(
							pa.getIddelegation()).getLibelledelegation());
				} else {
					p.setLocalisation("");
				}
				p.setNatureRecoltes(pa.getVariete());
				p.setPrixUnitaire(formatter.format(pa.getPrixunitaire())
						.toString() + " " + pa.getUnitePRix());
				p.setRendement(pa.getRendement() + " " + pa.getUniteRendement());
				p.setSuperficie(pa.getSuperficie() + "");
				p.setNomParcelle(pa.getNumtitrefoncier());
				p.setVariete(pa.getVariete());
				if (pa.getNbrpied() == null) {
					p.setNbrPieds("Néant");
				} else {
					p.setNbrPieds(pa.getNbrpied() + "");
				}

				listParcellePrint.add(p);
			}
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
					listParcellePrint);
			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, beanCollectionDataSource);

			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=FDR.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void imprimerFDRAlconsult(Adhesion adhesion) {
		String report;
		List<com.star.Ba.model.Parcelle> listparce = new ArrayList<>();
		listparce = parcelledao.getParbynpolice(adhesion.getNadhesion());

		try {

			report = com.star.Ba.commun.Constante.PDF + "FDRINCAL.jasper";
			System.out.println("FDR:" + com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String nom = adhesion.getClient().getNomClient() ;
			if( adhesion.getClient().getPrenomClient()!=null){
				nom+= " "
						+ adhesion.getClient().getPrenomClient();
			}
			parameters.put("imagesociete", adhesion.getUtilisateur()
					.getAgence().getSociete().getCodSociet()
					+ ".png");

			parameters.put("star", "star2018.png");

			parameters.put("star2", "ouufff.png");
			parameters.put("adresseagence", adhesion.getUtilisateur().getAgence().getLibelle());

			parameters.put("Contrat", adhesion.getNadhesion());
			parameters.put("DateEffet", sdf.format(adhesion.getDatDebut()));
			parameters.put("Expiration", sdf.format(adhesion.getDatFin()));
			parameters.put("Echeance", sdf.format(adhesion.getDatFin()));
			parameters.put("Avenant", "");
			parameters.put("Fractionnement", "");
			parameters.put("PtVente", adhesion.getUtilisateur().getAgence()
					.getCodeAgence());

			parameters.put("DateDeb", sdf.format(adhesion.getDatDebut()));
			parameters.put("DateFin", sdf.format(adhesion.getDatFin()));
			parameters.put("Assure", nom);
			parameters.put("AdresseA", adhesion.getClient().getNumAdr() + " "
					+ adhesion.getClient().getTypeVoie().getLibelle() + " "
					+ adhesion.getClient().getAdrClient() + " "
					+ adhesion.getClient().getCodeP() + " "
					+ adhesion.getClient().getLocalite() + " "
					+ adhesion.getClient().getGouver());
			parameters.put("TelA", adhesion.getClient().getTel());
			parameters.put("FaxA", "");
			if (societe) {
				parameters.put("bsociete", "X");
			} else {
				parameters.put("bparticulier", "X");
			}
			if (client.getProfession() != null) {
				parameters.put("ProfessionA", adhesion.getClient()
						.getProfession().getLibelleProfession());
			}

			else {
				parameters.put("ProfessionA", "");

			}

			parameters.put("Cin", adhesion.getClient().getIdentifiant());
			parameters.put("PrimeNette", adhesion.getMtPrime() + "");
			parameters.put("Cout", adhesion.getCout() + "");
			parameters.put("Taxes", adhesion.getTaxe() + "");
			parameters.put("PrimeTotale", adhesion.getPrimetot() + "");
			parameters.put("QSouscripteur", "");
			parameters.put("NomS", adhesion.getUtilisateur().getAgence()
					.getSociete().getRaisonSoc());
			parameters.put("AdresseS", adhesion.getUtilisateur().getAgence()
					.getSociete().getAdresse());
			parameters.put("TelS", adhesion.getUtilisateur().getAgence()
					.getSociete().getTelphone());
			parameters.put("FaxS", adhesion.getUtilisateur().getAgence()
					.getSociete().getFax());
			parameters.put("mfS", adhesion.getUtilisateur().getAgence()
					.getSociete().getMatfisc());

			parameters.put("mfS2", adhesion.getClient().getTypeDoc());

			/*Collection<ParcellePrint> listParcellePrint = new ArrayList<ParcellePrint>();
			for (com.star.Ba.model.Parcelle pa : listparce) {

				ParcellePrint p = new ParcellePrint();
				if (pa.getIddelegation() != null) {
					p.setLocalisation(service.getDelegationBYID(
							pa.getIddelegation()).getLibelledelegation());
				} else {
					p.setLocalisation("");
				}
				p.setNatureRecoltes(pa.getVariete());
				p.setPrixUnitaire(formatter.format(pa.getPrixunitaire())
						.toString() + " " + pa.getUnitePRix());
				p.setRendement(pa.getRendement() + " " + pa.getUniteRendement());
				p.setSuperficie(pa.getSuperficie() + "");
				p.setNomParcelle(pa.getNumtitrefoncier());
				p.setVariete(pa.getVariete());
				if (pa.getNbrpied() == null) {
					p.setNbrPieds("Néant");
				} else {
					p.setNbrPieds(pa.getNbrpied() + "");
				}

				listParcellePrint.add(p);
			}*/
			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=FDRINCAL.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getAdresseClient() {
		if (client.getTypeVoie() != null) {
			return client.getNumAdr() + " " + client.getTypeVoie().getLibelle()
					+ " " + client.getAdrClient() + " " + client.getCodeP()
					+ " " + client.getLocalite() + " " + client.getGouver();
		} else {
			return client.getNumAdr() + " " + client.getAdrClient() + " "
					+ client.getCodeP() + " " + client.getLocalite() + " "
					+ client.getGouver();
		}
	}

	public void imprimerQuittance() {
		String report;
		try {

			report = com.star.Ba.commun.Constante.PDF + "quittance.jasper";
			System.out.println("com.star.Ba.commun.Constante.PDF:"
					+ com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			parameters.put("imagesociete", utilisateursConnexionDTO.getUser()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");

			double primtot = 0, primeNette = 0;
			BigDecimal cout2 = new BigDecimal(0);
			double cout1 = 0;
			double cout = 0;
			String numContrat = "", numQuittance = "";
			for (ContratDTO c : listContrat) {
				primtot += c.getAdhesion().getPrimetot();
				primeNette += c.getAdhesion().getMtPrime();
				cout2 = cout2.add(c.getAdhesion().getCout());
				if (c.getAdhesion().getFga() != null) {
					cout1 = cout1 + (c.getAdhesion().getFga());
				}

				System.out.println("==================primtot=========="
						+ primtot);

				System.out.println("==================fga==========" + cout1);

				if (numContrat.equals("")) {
					numContrat += c.getAdhesion().getNadhesion();
					numQuittance += c.getQuittance().getNumquit();
				} else {
					numContrat += "*" + c.getAdhesion().getNadhesion();
					numQuittance += "*" + c.getQuittance().getNumquit();
				}
				System.out.println("==================fga string=========="
						+ cout);

			}
			primtot = (primtot + cout1);
			cout = cout1 + (cout2.doubleValue());

			String nom = client.getNomClient();
			if (client.getPrenomClient() != null
					&& !client.getPrenomClient().equals(null)) {
				nom += "  " + client.getPrenomClient();
			}
			parameters.put("nomclt", nom);
			parameters.put("adrclt", client.getAdrClient());
			parameters.put("vilclt", client.getLocalite());
			parameters.put("gouvclt", client.getGouver());
			parameters.put("codpclt", client.getCodeP());
			parameters.put("Dateff", sdf.format(dateEffet));
			parameters.put("Datech", sdf.format(dateEcheance));
			parameters.put("numquit", numQuittance);
			parameters.put("nomass", nom);
			parameters.put("typeclient", client.getTypePers());
			parameters.put("identifiant", client.getIdentifiant());
			parameters.put("mfS2", client.getTypeDoc());
			parameters.put("codagc", utilisateursConnexionDTO.getUser()
					.getAgence().getCodeAgence()
					+ "");
			parameters.put("nadhesion", numContrat);
			parameters.put("prmnet", formatter.format(primeNette) + "");
			parameters.put("frais", cout + "");
			parameters.put("prmtot", formatter.format(primtot) + "");
			parameters.put("datjour", sdf.format(new Date()));
			parameters.put("vilagc", utilisateursConnexionDTO.getUser()
					.getAgence().getLibelle());
			parameters.put("libelle_pack", "Pack BNA");
			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=QUITTANCE.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void imprimerQuittanceconsul(Adhesion adhesion) {
		String report;
		List<com.star.Ba.model.PackPolice> listpack = new ArrayList<>();
		listpack = iPackPoliceDAO.findPackbypolice(adhesion.getNadhesion());

		try {

			report = com.star.Ba.commun.Constante.PDF + "quittance.jasper";
			System.out.println("com.star.Ba.commun.Constante.PDF:"
					+ com.star.Ba.commun.Constante.PDF);
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			parameters.put("imagesociete", adhesion.getUtilisateur()
					.getAgence().getSociete().getCodSociet()
					+ ".png");
			parameters.put("star", "star2018.png");

			double primtot = 0, primeNette = 0;
			BigDecimal cout2 = new BigDecimal(0);
			double cout1 = 0;
			double cout = 0;
			String numContrat = "", numQuittance = "";
			for (com.star.Ba.model.PackPolice c : listpack) {
				Adhesion k = adhesionDAO.getParbynpolice2(c.getPackPolicePK()
						.getNPolice());
				Quittance kk = iQuittanceDAO.searchquitByNAdhesion(c
						.getPackPolicePK().getNPolice());
				primtot += k.getPrimetot();
				primeNette += k.getMtPrime();
				cout2 = cout2.add(k.getCout());
				if (k.getFga() != null) {
					cout1 = cout1 + (k.getFga());
				}

				System.out.println("==================primtot=========="
						+ primtot);

				System.out.println("==================fga==========" + cout1);

				if (numContrat.equals("")) {
					numContrat += k.getNadhesion();
					numQuittance += kk.getNumquit();
				} else {
					numContrat += "*" + k.getNadhesion();
					numQuittance += "*" + kk.getNumquit();
				}
				System.out.println("==================fga string=========="
						+ cout);

			}
			primtot = (primtot + cout1);
			cout = cout1 + (cout2.doubleValue());

			String nom = adhesion.getClient().getNomClient();
			if (adhesion.getClient().getPrenomClient() != null
					&& !adhesion.getClient().getPrenomClient().equals(null)) {
				nom += "  " + adhesion.getClient().getPrenomClient();
			}
			parameters.put("nomclt", nom);
			parameters.put("adrclt", adhesion.getClient().getAdrClient());
			parameters.put("vilclt", adhesion.getClient().getLocalite());
			parameters.put("gouvclt", adhesion.getClient().getGouver());
			parameters.put("codpclt", adhesion.getClient().getCodeP());
			parameters.put("Dateff", sdf.format(adhesion.getDatDebut()));
			parameters.put("Datech", sdf.format(adhesion.getDatFin()));
			parameters.put("numquit", numQuittance);
			parameters.put("nomass", nom);
			parameters.put("typeclient", adhesion.getClient().getTypePers());
			parameters.put("mfS2", adhesion.getClient().getTypeDoc());
			parameters
					.put("identifiant", adhesion.getClient().getIdentifiant());
			parameters.put("codagc", adhesion.getUtilisateur().getAgence()
					.getCodeAgence()
					+ "");
			parameters.put("nadhesion", numContrat);
			parameters.put("prmnet", formatter.format(primeNette) + "");
			parameters.put("frais", cout + "");
			parameters.put("prmtot", formatter.format(primtot) + "");
			parameters.put("datjour", sdf.format(new Date()));
			parameters.put("vilagc", adhesion.getUtilisateur().getAgence()
					.getLibelle());
			parameters.put("libelle_pack", "Pack BNA");
			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=QUITTANCE.pdf");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isbClasse() {
		return bClasse;
	}

	public List<AgrclasseNature> getListAgrClasse() {
		return listAgrClasseNature;
	}

	public List<Agrnature> getListAgrNature() {
		return listAgrNature;
	}

	public String getSelectedNature() {
		return selectedNature;
	}

	public void setSelectedNature(String selectedNature) {
		this.selectedNature = selectedNature;
	}

	public void setUtilisateursConnexionDTO(
			UtilisateursConnexionDTO utilisateursConnexionDTO) {
		this.utilisateursConnexionDTO = utilisateursConnexionDTO;
	}

	public List<ContenuDTO> getListContenu() {
		return listContenu;
	}

	public String getSelectedClasse() {
		return selectedClasse;
	}

	public void setSelectedClasse(String selectedClasse) {
		this.selectedClasse = selectedClasse;
	}

	public String getNatureNulle() {
		return natureNulle;
	}

	public List<TarifGrele> getListTarifGrele() {
		return listTarifGrele;
	}

	public boolean isbCapitale() {
		return bCapitale;
	}

	public boolean isbZone() {
		return bZone;
	}

	public String getSelectedZone() {
		return selectedZone;
	}

	public void setSelectedZone(String selectedZone) {
		this.selectedZone = selectedZone;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getPrimeGrele() {
		return primeGrele;
	}

	public List<Parcelle> getListParcelle() {
		return listParcelle;
	}

	public List<Delegation> getListDelegation() {
		return listDelegation;
	}

	public String getSelectedDelegation() {
		return selectedDelegation;
	}

	public void setSelectedDelegation(String selectedDelegation) {
		this.selectedDelegation = selectedDelegation;
	}

	public Agrnature getNature() {
		return nature;
	}

	public int getTabindex() {
		return tabindex;
	}

	public void setTabindex(int tabindex) {
		this.tabindex = tabindex;
		System.out.println("TYPE VOIE :" + typeVoieCode + "Profession:"
				+ client.getProfession());
	}

	public void suivant() {
		if (onglets != null && onglets.size() > tabindex) {
			this.tabindex++;
		}
	}

	public boolean isOngletProduit() {
		return ongletProduit;
	}

	public boolean isSociete() {
		return societe;
	}

	public void setSociete(boolean societe) {
		this.societe = societe;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getDateEffet() {
		return dateEffet;
	}

	public void setDateEffet(Date dateEffet) {
		this.dateEffet = dateEffet;
	}

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public Date getAuj() {
		return auj;
	}

	public void setAuj(Date auj) {
		this.auj = auj;
	}

	public List<ContenuDTO> getOnglets() {
		return onglets;
	}

	public void setOnglets(List<ContenuDTO> onglets) {
		this.onglets = onglets;
	}

	public boolean isCheckedTD() {
		return checkedTD;
	}

	public boolean isCheckedGrele() {
		return checkedGrele;
	}

	public boolean isCheckedIncendie() {
		return checkedIncendie;
	}

	public TarifAgrDTO getTarifIncendie() {
		return tarifIncendie;
	}

	public boolean isSave() {
		return save;
	}

	public boolean isImprimer() {
		return imprimer;
	}

	public List<String> getDocs() {
		return docs;
	}

	public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}

	public Double getPrimeTotale() {
		return primeTotale.doubleValue();
	}

	public String getYearRange() {
		return yearRange;
	}

	public TarifAgrDTO getTarifGrele() {
		return tarifGrele;
	}

	public long getSelectedLocgouvCode() {
		return selectedLocgouvCode;
	}

	public void setSelectedLocgouvCode(long selectedLocgouvCode) {
		this.selectedLocgouvCode = selectedLocgouvCode;
	}

	public long getTypeVoieCode() {
		return typeVoieCode;
	}

	public void setTypeVoieCode(long typeVoieCode) {
		this.typeVoieCode = typeVoieCode;
	}

	public void setIdProfession(long idProfession) {
		this.idProfession = idProfession;
	}

	public long getIdProfession() {
		return idProfession;
	}

	public List<PrixHom> getListPrixHom() {
		return listPrixHom;
	}

	public boolean isBprixHom() {
		return bprixHom;
	}

	public boolean isOngletclient() {
		return ongletclient;
	}

	public void setOngletclient(boolean ongletclient) {
		this.ongletclient = ongletclient;
	}

	public boolean isOngletTarif() {
		return ongletTarif;
	}

	public void setOngletTarif(boolean ongletTarif) {
		this.ongletTarif = ongletTarif;
	}

	public String getUpdateGeneral() {
		return updateGeneral;
	}

	public void setUpdateGeneral(String updateGeneral) {
		this.updateGeneral = updateGeneral;
	}

	public String getUpdateClient() {
		return updateClient;
	}

	public void setUpdateClient(String updateClient) {
		this.updateClient = updateClient;
	}

	public String getUpdatePrduit() {
		return updatePrduit;
	}

	public void setUpdatePrduit(String updatePrduit) {
		this.updatePrduit = updatePrduit;
	}

	public String getUpdateGrele() {
		return updateGrele;
	}

	public void setUpdateGrele(String updateGrele) {
		this.updateGrele = updateGrele;
	}

	public String getUpdateTD() {
		return updateTD;
	}

	public void setUpdateTD(String updateTD) {
		this.updateTD = updateTD;
	}

	public Date getMinDateNaissance() {
		return minDateNaissance;
	}

	public int getNbjours() {
		return nbjours;
	}

	public void setCheckedTD(boolean checkedTD) {
		this.checkedTD = checkedTD;
	}

	public String getNumDecision() {
		return numDecision;
	}

	public void setNumDecision(String numDecision) {
		this.numDecision = numDecision;
	}

	public Date getDatNaiss() {
		return datNaiss;
	}

	public void setDatNaiss(Date datNaiss) {
		this.datNaiss = datNaiss;
	}

	public String getTypeDoc() {
		return TypeDoc;
	}

	public void setTypeDoc(String typeDoc) {
		TypeDoc = typeDoc;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public String getPrenomClient() {
		return prenomClient;
	}

	public void setPrenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getNcompte() {
		return ncompte;
	}

	public void setNcompte(String ncompte) {
		this.ncompte = ncompte;
	}

	public String getAdrClient() {
		return adrClient;
	}

	public void setAdrClient(String adrClient) {
		this.adrClient = adrClient;
	}

	public Double getCapitale() {
		return capitale;
	}

	public void setCapitale(Double capitale) {
		this.capitale = capitale;
	}

	public String getRef_credit() {
		return ref_credit;
	}

	public void setRef_credit(String ref_credit) {
		this.ref_credit = ref_credit;
	}

	public String getNumDetailDecision() {
		return numDetailDecision;
	}

	public void setNumDetailDecision(String numDetailDecision) {
		this.numDetailDecision = numDetailDecision;
	}

	public String getProdspeculation() {
		return prodspeculation;
	}

	public void setProdspeculation(String prodspeculation) {
		this.prodspeculation = prodspeculation;
	}

	public String getSpeculation() {
		return speculation;
	}

	public void setSpeculation(String speculation) {
		this.speculation = speculation;
	}

	public Date getEchecdate() {
		return echecdate;
	}

	public void setEchecdate(Date echecdate) {
		this.echecdate = echecdate;
	}

	public int getNbannee2() {
		return nbannee2;
	}

	public void setNbannee2(int nbannee2) {
		this.nbannee2 = nbannee2;
	}

	public int getNbjours2() {
		return nbjours2;
	}

	public void setNbjours2(int nbjours2) {
		this.nbjours2 = nbjours2;
	}

	public int getNbmois2() {
		return nbmois2;
	}

	public void setNbmois2(int nbmois2) {
		this.nbmois2 = nbmois2;
	}

	public String getTauxgrele() {
		return tauxgrele;
	}

	public void setTauxgrele(String tauxgrele) {
		this.tauxgrele = tauxgrele;
	}

	public boolean isBclasse3() {
		return bclasse3;
	}

	public void setBclasse3(boolean bclasse3) {
		this.bclasse3 = bclasse3;
	}

	public boolean isBclasse4() {
		return bclasse4;
	}

	public void setBclasse4(boolean bclasse4) {
		this.bclasse4 = bclasse4;
	}

	public boolean isBclasse5() {
		return bclasse5;
	}

	public void setBclasse5(boolean bclasse5) {
		this.bclasse5 = bclasse5;
	}

	public boolean isBclasse6() {
		return bclasse6;
	}

	public void setBclasse6(boolean bclasse6) {
		this.bclasse6 = bclasse6;
	}

	public double getXdevis() {
		return xdevis;
	}

	public void setXdevis(double xdevis) {
		this.xdevis = xdevis;
	}

	public int getAgem() {
		return agem;
	}

	public void setAgem(int agem) {
		this.agem = agem;
	}

	public int getAgej() {
		return agej;
	}

	public void setAgej(int agej) {
		this.agej = agej;
	}

	public boolean isDevistd() {
		return devistd;
	}

	public void setDevistd(boolean devistd) {
		this.devistd = devistd;
	}

	public boolean isDevistd2() {
		return devistd2;
	}

	public void setDevistd2(boolean devistd2) {
		this.devistd2 = devistd2;
	}

	public boolean isBlocage() {
		return blocage;
	}

	public void setBlocage(boolean blocage) {
		this.blocage = blocage;
	}

	public boolean isNaturelegume() {
		return naturelegume;
	}

	public void setNaturelegume(boolean naturelegume) {
		this.naturelegume = naturelegume;
	}

	public int gettype_pack() {
		return type_pack;
	}

	public void settype_pack(int type_pack) {
		this.type_pack = type_pack;
	}

	public boolean isBprixHom2() {
		return bprixHom2;
	}

	public void setBprixHom2(boolean bprixHom2) {
		this.bprixHom2 = bprixHom2;
	}

	public boolean getBpieds() {
		return bpieds;
	}

	public void setBpieds(boolean bpieds) {
		this.bpieds = bpieds;
	}

	public String getSpeculationDetail() {
		return speculationDetail;
	}

	public void setSpeculationDetail(String speculationDetail) {
		this.speculationDetail = speculationDetail;
	}

	public String getTypePieceWS() {
		return TypePieceWS;
	}

	public void setTypePieceWS(String typePieceWS) {
		TypePieceWS = typePieceWS;
	}

	public boolean isVoir() {
		return voir;
	}

	public void setVoir(boolean voir) {
		this.voir = voir;
	}

	public String getSituationRisque() {
		return situationRisque;
	}

	public void setSituationRisque(String situationRisque) {
		this.situationRisque = situationRisque;
	}

	
	public boolean isCheckedIncAl() {
		return checkedIncAl;
	}

	public void setCheckedIncAl(boolean checkedIncAl) {
		this.checkedIncAl = checkedIncAl;
	}

	public String getSituation_risque() {
		return situation_risque;
	}

	public void setSituation_risque(String situation_risque) {
		this.situation_risque = situation_risque;
	}

	public int getCodespec() {
		return codespec;
	}

	public void setCodespec(int codespec) {
		this.codespec = codespec;
	}

	public Boolean getVerifcompte() {
		return verifcompte;
	}

	public void setVerifcompte(Boolean verifcompte) {
		this.verifcompte = verifcompte;
	}

	public int getNbrparcelle() {
		return nbrparcelle;
	}

	public void setNbrparcelle(int nbrparcelle) {
		this.nbrparcelle = nbrparcelle;
	}

	
	
	

}
