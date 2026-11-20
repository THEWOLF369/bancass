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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.json.JSONObject;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.star.Ba.commun.AgeCalculator;
import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAdherentDAO;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dao.IOperationTransactionDAO;
import com.star.Ba.dao.IPropositionDAO;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.jsf.util.Proprietes;
import com.star.Ba.jsf.util.TokenSTB;
import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.GarantieVie;
import com.star.Ba.model.OperationTransaction;
import com.star.Ba.model.Police;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.parametres.ExampleResourceClient;
import com.star.Ba.parametres.TrustAllX509TrustManager;
import com.star.Ba.service.AssistantVoiyagetServiceInter;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


/**
 * @author
 * 
 */
@ManagedBean(name = "tariftdBean")
@SessionScoped
public class TariftdBean implements Serializable {

	private double primeglobal;
	@ManagedProperty("#{devisBean}")
	private DevisBean devisBean;
	private Adhesion adhesion;
	@EJB
	private IAdhesionDAO adhesionDAO;
	@EJB
	private IAdherentDAO adherentDAO;
	@EJB
	private IClientDAO clientDAO;
	@EJB
	private IPropositionDAO propositionDAO;
	@EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;

	@EJB
	private IOperationTransactionDAO iOperationTransactionDAO;


	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;
	@ManagedProperty("#{assistanceVoiyageController}")
	private AssistanceVoiyageController assistanceVoiyageController;

	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;
	@ManagedProperty("#{reportController}")
	private ReportController reportController;

	public TariftdBean() {

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

	public ClientBean getClientBean() {
		return clientBean;
	}

	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}

	public AssistanceVoiyageController getAssistanceVoiyageController() {
		return assistanceVoiyageController;
	}

	public void setAssistanceVoiyageController(
			AssistanceVoiyageController assistanceVoiyageController) {
		this.assistanceVoiyageController = assistanceVoiyageController;
	}

	public TariftdBean(double primeglobal) {

		this.primeglobal = primeglobal;
	}

	public double getPrimeglobal() {

		devisBean.setShowTd(false);
		return devisBean.getPrimeglobal();

	}

	public void setPrimeglobal(float primeglobal) {
		this.primeglobal = primeglobal;
	}

	Proposition tdProposition;

	public Proposition getTdProposition() {
		return tdProposition;
	}

	public void setTdProposition(Proposition tdProposition) {
		this.tdProposition = tdProposition;
	}

	public void generateDevis() {
		// msg err
		if (devisBean.getClient().getIdentifiant() == null
				|| devisBean.getClient().getIdentifiant().isEmpty()) {
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} else {
			Client client = devisBean.getClient();
			System.out.println("client ds devisbean=" + client);
			if (client.getDatCreation() == null) {
				client.setDatCreation(new Date());
			}

			if (clientDAO.findByIdentifiant(client.getIdentifiant()) != null) {
				client.setIdClient( clientDAO.findByIdentifiant(client.getIdentifiant()).getIdClient()); 
				devisBean.getAdhesion().setClient(client);

				clientDAO.edit(client);

			} else {
				clientDAO.create(client);

			}
			if(devisBean.getSelectedProposition()!= null )
			{
				//*
				tdProposition=UpdateProposition(client, devisBean.getSelectedProposition(), devisBean.getAdhesion(), utilisateur);
			}
			else{
				tdProposition = createProposition(client, devisBean.getAdhesion(),	utilisateur);
			}

//			JsfUtil.addSuccessMessage(	"Proposition enregistrée avec le numéro: " + tdProposition.getnProposition(), "!");

			// propositionDAO.create(proposition);

			devisBean
			.imprimerDevis(devisBean.getAdhesion().getPolice()
					.getNPolice(), devisBean.getClient()
					.getIdentifiant(), devisBean.getClient()
					.getDatNaiss(), devisBean.getAdhesion()
					.getDatDebut(), devisBean.getAdhesion().getDuree(),

					devisBean.getAdhesion().getFranchise(), devisBean
					.getAdhesion().getDatFin(), devisBean.getAdhesion()
					.getCapital(),

					devisBean.getPrimeglobal(), devisBean.getClient()
					.getNomClient(), devisBean.getClient()
					.getPrenomClient(), tdProposition.getnProposition());

		}
		devisBean.setSelectedProposition(null);
	}

	public void validerProposition() {

		if (devisBean.getClient().getIdentifiant() == null
				|| devisBean.getClient().getIdentifiant().isEmpty()) {
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} else if (devisBean.getNumact() != 0 && devisBean.getNotifie().equals(false)) {
			FacesMessage msg = new FacesMessage("ERREUR","Impossible de valider l'affaire!! Surprime obligatoire!! Veuillez Consulter STAR");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} 
		
		
//		else if ((assistanceVoiyageController.getNombreAssureAajouter() != 0) && (devisBean.getAdhesion().getTypeAbonnement().getValTypeAbonnement().equalsIgnoreCase("Familial") ))
//		{
//			
//			FacesMessage msg = new FacesMessage("ERREUR", "veuillez saissir tout les assuré ");
//			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
//					
//			
//		}
		
		
		else {
			Client client = devisBean.getClient();

			if (client.getDatCreation() == null) {
				client.setDatCreation(new Date());
			}
			if (clientDAO.findByIdentifiant(client.getIdentifiant()) != null) {
				System.out.println("client existant");
				client.setIdClient( clientDAO.findByIdentifiant(client.getIdentifiant()).getIdClient()); 
				devisBean.getAdhesion().setClient(client);
				clientDAO.edit(client);

			} else {
				System.out.println("client inexistant");
				clientDAO.create(client);

			}

			if (tdProposition != null ) {

				System.out.println("tdProposition != null");
				devisBean.getAdhesion().setProposition(tdProposition);
			}
			else if(devisBean.getSelectedProposition()!= null){
				System.out.println("devisBean.getSelectedProposition()!= null");
				devisBean.getAdhesion().setProposition(devisBean.getSelectedProposition());
			}
			devisBean.getAdhesion().setEtat("V");
			devisBean.setAdhesion(serviceAssistantVoiyagetService.createAdhesion(devisBean.getAdhesion()));

			JsfUtil.addSuccessMessage("Adhésion Validée avec le numéro: "+ devisBean.getAdhesion().getNadhesion(), "!");

			devisBean.setDesabledGenerateNotice(false);

			//Status = 0;
			//validate = 1;



		}
	}

	public void generateNotice() {
		if (devisBean.getAdhesion() == null
				|| devisBean.getAdhesion().getIdAdhesion() == 0) {
			JsfUtil.addErrorMessage("L'adhesion doit etre validée!");
			return;
		}
		print(devisBean.getAdhesion());
	}

	public void generateAnalyse() {
		try {

			String report = reportController.getRealPath()
					+ "analyse_medicaux.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			parameters
			.put("nom_adherent", devisBean.getClient().getNomClient());
			parameters.put("prenom_adherent", devisBean.getClient()
					.getPrenomClient());
			parameters.put("age",
					calculAge(devisBean.getClient().getDatNaiss()));
			parameters.put("capital", devisBean.getAdhesion().getCapital());
			parameters.put("cin", devisBean.getClient().getIdentifiant());
			parameters.put("date_naiss",
					sdf.format(devisBean.getClient().getDatNaiss()));
			parameters.put("imagesociete",
					 devisBean.getCod_societe() + ".png");
			parameters.put("star",  "star.png");
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext
					.lookup("java:jboss/datasources/bancass_star");
			Connection connection = dataSource.getConnection();

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, connection);
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition",
					"inline; filename=\"rapport.pdf" + "\"");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DevisBean getdevisBean() {
		return devisBean;
	}

	public void setdevisBean(DevisBean devisBean) {
		this.devisBean = devisBean;
	}

	public Integer calculAge(Date datnaiss) {
		if (datnaiss != null) {
			if (AgeCalculator.calculateAge(datnaiss).getMonths() >= 6) {
				return AgeCalculator.calculateAge(datnaiss).getYears() + 1;
			}
			return AgeCalculator.calculateAge(datnaiss).getYears();
		}

		return -1;
	}

	public void print(Adhesion adhesion) {
		try {

			String report = reportController.getRealPath() + "adhesion.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			parameters.put("numeroPolice", adhesion.getPolice().getNPolice());
			parameters.put("contractant", adhesion.getPolice().getConvention()
					.getSociete().getRaisonSoc());
			parameters.put("adresse", adhesion.getPolice().getConvention()
					.getSociete().getAdresse());
			parameters.put("clientName", adhesion.getClient().getNomClient().toUpperCase()
					+ " " + adhesion.getClient().getPrenomClient().toUpperCase());
			parameters.put("clientBirthday",
					sdf.format(adhesion.getClient().getDatNaiss()));
			parameters.put("clientCIN",
					String.valueOf(adhesion.getClient().getIdentifiant()));
			parameters.put("dateEffet", sdf.format(adhesion.getDatDebut()));
			parameters.put("dateFin", sdf.format(adhesion.getDatFin()));
			parameters.put("duree", String.valueOf((int)adhesion.getDuree()));
			parameters.put("ageLimit", adhesion.getPolice().getAgelimit()
					.toString());
			parameters.put("imagesociete",
					 devisBean.getCod_societe() + ".png");
			parameters.put("star", "star.png");
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

			parameters.put("capital", String.valueOf(adhesion.getCapital()));
			parameters.put("mtPrime",     (new DecimalFormat("#0.000")).format(adhesion.getMtPrime())  );

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition", "inline; filename=\""
					+ adhesion.getPolice().getNPolice() + "rapport.pdf" + "\"");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

			adhesion.setImpr(true);
			adhesionDAO.edit(adhesion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void imprimerDevisAssistanceVoiyage(String numplc,
			String identifiant, String nomclt, String prenomclt, Date effDate,
			String duree, Date datefin, String type_contrat,
			double primrglobal, String ville_agence, String imagesociete,
			String nbrpersonne, String numprop) {

		String path = reportController.getRealPath()
				+ "devis_voyage.jasper";

		try {

			// fonction d'impression du devis
			Map<String, Object> parameters = new HashMap();

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			parameters.put("numplc", numplc);
			parameters.put("prenomclt", prenomclt);
			parameters.put("numprop", numprop);
			parameters.put("nomclt", nomclt);
			parameters.put("identifiant", identifiant);

			parameters.put("effDate", df.format(effDate));
			parameters.put("datefin", df.format(datefin));
			parameters.put("duree", duree);
			parameters.put("type_contrat", type_contrat);
			parameters.put("primeglobal", String.valueOf(primrglobal));
			parameters.put("ville_agence", ville_agence);
			parameters.put("imagesociete", imagesociete);
			parameters
			.put("star",  "star.png");
			parameters.put("nbrpersonne", nbrpersonne);

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





	public void imprimerDevisAssistanceVoiyage___(String numplc,
			String identifiant, String nomclt, String prenomclt, Date effDate,
			String duree, Date datefin, String type_contrat,
			double primrglobal, String ville_agence, String imagesociete,
			String nbrpersonne, String numprop) {

		// fonction d'impression du devis
		Map<String, Object> parameters = new HashMap();

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		parameters.put("numplc", numplc);
		parameters.put("prenomclt", prenomclt);
		parameters.put("numprop", numprop);
		parameters.put("nomclt", nomclt);
		parameters.put("identifiant", identifiant);

		parameters.put("effDate", df.format(effDate));
		parameters.put("datefin", df.format(datefin));
		parameters.put("duree", duree);
		parameters.put("type_contrat", type_contrat);
		parameters.put("primeglobal", String.valueOf(primrglobal));
		parameters.put("ville_agence", ville_agence);
		parameters.put("imagesociete", imagesociete);
		parameters
		.put("star",  "star.png");
		parameters.put("nbrpersonne", nbrpersonne);

		try {
			String path = reportController.getRealPath()
					+ "devis_voyage.jasper";
			// this.getClass().getResource("closed.jasper").getPath()
			JasperPrint jp = JasperFillManager.fillReport(path, parameters,
					new JREmptyDataSource());

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();

			httpServletResponse.addHeader("Content-disposition",
					"inline; filename=devis_voyage.pdf");
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

	public boolean propositionCreated = false;
	public Proposition newProposition;

	public Proposition getNewProposition() {
		return newProposition;
	}

	public void setNewProposition(Proposition newProposition) {
		this.newProposition = newProposition;
	}

	public Proposition createProposition(Client client, Adhesion adhesion,	Utilisateurs utilisateurs) {
		Proposition proposition = new Proposition();
		proposition.setPolice(adhesion.getPolice());
		proposition.setClient(adhesion.getClient());

		proposition.setPoliceCredit(adhesion.getPoliceCredit());

		// proposition.setCapital(adhesion.getCapital()==null?null:adhesion.getCapital())
		// ;

		proposition.setDatDebut(adhesion.getDatDebut());
		proposition.setDatFin(adhesion.getDatFin());
		proposition.setDuree(adhesion.getDuree());

		proposition.setFraPolice(adhesion.getFraPolice());
		proposition.setDurPaiemt(adhesion.getDurPaiemt());
		proposition.setCapital(adhesion.getCapital()==null?0:adhesion.getCapital()); //modifier  11/10/2017
		proposition.setMajoration(adhesion.getMajoration()==null?0:adhesion.getMajoration());//modifier 11/10/2017//
		proposition.setTauxSprim(adhesion.getTauxSprim()==null?0:adhesion.getTauxSprim());//modifier 11/10/2017
		proposition.setTauxInter(adhesion.getTauxInter()==null?0:adhesion.getTauxInter()); //modifier 11/10/2017
		proposition.setEtat(adhesion.getEtat());
		proposition.setDatMaj(adhesion.getDatMaj());
		proposition.setTauxPrime(adhesion.getTauxPrime());
		proposition.setFranchise(adhesion.getFranchise());
		proposition.setMtPrime(adhesion.getMtPrime());

		proposition.setNombrePersonne(adhesion.getNombrePersonne());
		proposition.setDestination(adhesion.getDestination());
		proposition.setPersonnePrv(adhesion.getPersonnePrv());
		proposition.setTelphone(adhesion.getTelphone());
		proposition.setTypeAbonnement(adhesion.getTypeAbonnement());
		//add couverture
		proposition.setCouverture(adhesion.getCouverture());
		proposition.setNotifie(false);
		proposition.setDatcreation(new Date());
		if (client.getIdClient() == 0) {
			client.setDatCreation(new Date());
		}

		proposition.setClient(client);

		proposition.setIdUtilisateurs(utilisateurs.getIdUtilisateurs());

		return serviceAssistantVoiyagetService.createProposition(proposition);
	}

	public void generateDevisAssistanceVoiyage() {
		Client client = devisBean.getClient();
		Adhesion adhesion = assistanceVoiyageController.getNewAdhesion();
		if (client.getIdentifiant() == null
				|| client.getIdentifiant().isEmpty()) {
			noneException = false;
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} else {

			if (client.getDatCreation() == null) {
				client.setDatCreation(new Date());
			}
			if(clientDAO.findByIdentifiant(client.getIdentifiant())!=null)
			{
				System.out.println("client existant");
				client.setIdClient( clientDAO.findByIdentifiant(client.getIdentifiant()).getIdClient()); 

			}

		/*	if (newProposition == null) {
				
				adhesion.setEtat("V");
				newProposition = createProposition(client, adhesion,utilisateur);
			}*/
			
			adhesion.setEtat("V");
			newProposition = createProposition(client, adhesion,utilisateur);
		    	if(adhesion!=null &&  adhesion.getAdherents() !=null && !adhesion.getAdherents().isEmpty()){
		    		System.out.println("enregistrement adherents"+newProposition.getIdProposition());
		    		for (Adherent adherent : adhesion.getAdherents()) {
		    			System.out.println("enregistrement adherents "+adherent.getTypeVoie());
		    			adherent.setClient(newProposition.getClient());
		    			adherent.setPropositions( newProposition.getIdProposition());
		    			adherent= adherentDAO.enregistrerAdherent(adherent);
					}
		    		//newProposition.setAdherents(adherentDAO.findAdhByIdProposition(newProposition.getIdProposition()));
		    	}
			//newProposition.setAdherents( adherentDAO.createAdherents(adhesion.getAdherents(), newProposition));
			JsfUtil.addSuccessMessage(
					"Proposition enregistrée avec le numéro: "
							+ newProposition.getnProposition(), "!");

			imprimerDevisAssistanceVoiyage(adhesion.getPolice().getNPolice(),
					client.getIdentifiant(), client.getNomClient(),
					client.getPrenomClient(), adhesion.getDatDebut(),
					String.valueOf(adhesion.getDuree()), adhesion.getDatFin(),
					adhesion.getTypeAbonnement().getValTypeAbonnement(),

					adhesion.getMtPrime(),
					utilisateur.getAgence().getLibelle(), utilisateur.getAgence().getSociete()
							.getCodSociet() + ".png", adhesion
							.getNombrePersonne().toString(),
							newProposition.getNumeroProposition());
			noneException = true;

			FacesMessage msg = new FacesMessage("Succes",
					" Succes Insert In proposition and generate devis");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);

		}
	}

	public void generateNoticeAssistanceVoiyage() {

		printAssistanceVoiyage(adhesionToPrintNotise);

	}

	public void printAssistanceVoiyage(Adhesion adhesion) {
		try {

			String report = reportController.getRealPath()
					+ "notice_voyage.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			parameters.put("numplc", adhesion.getPolice().getNPolice());
			parameters.put("Souscripteur", adhesion.getClient().getNomClient().toUpperCase() + " " + adhesion.getClient().getPrenomClient().toUpperCase());
			parameters.put("adrsouscripteur", adhesion.getClient().getAdrClient());
			parameters.put("nomclt", adhesion.getClient().getNomClient().toUpperCase());
			parameters.put("prenomclt", adhesion.getClient().getPrenomClient().toUpperCase());
			parameters.put("cinclt", adhesion.getClient().getIdentifiant());
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
			parameters.put("datefin", sdf.format(adhesion.getDatFin()));
			parameters.put("mtPrime", String.valueOf(adhesion.getMtPrime()));
			parameters.put("surprime", "0");
			parameters.put("primetot", String.valueOf(adhesion.getMtPrime()));
			parameters.put("imagesociete",  utilisateur.getAgence().getSociete().getCodSociet()+ ".png");
			parameters.put("villeagence", utilisateur.getAgence().getLibelle());
			parameters.put("personprv", adhesion.getPersonnePrv());
			parameters.put("telpersonprv", adhesion.getTelphone());
			parameters.put("destination", adhesion.getDestination());
			parameters.put("star",  "star.png");
			parameters.put("typedoc", adhesion.getClient().getTypeDoc().toUpperCase());
			if(adhesion.getClient().getNcompte()!=null && !adhesion.getClient().getNcompte().equals("")){
				parameters.put("ncompte", adhesion.getClient().getNcompte());
			}
			 if(adhesion.getCouverture()!=null)
	            {
	            	parameters.put("couverture", adhesion.getCouverture().intValue()+"");
	            }
			String nom_assure = "";
			String prenom_assure = "";
			String datnais_assure = "";

			for (Adherent adherent : adhesion.getAdherents()) {

				nom_assure += adherent.getNomAdherent() + "\n";

				prenom_assure += adherent.getPrenomAdherent() + "\n";
				datnais_assure += sdf.format(adherent.getDatNaiss()) + "\n";
			}

			parameters.put("nom_assure", nom_assure.trim());
			parameters.put("prenom_assure", prenom_assure.trim());
			parameters.put("datnais_assure", datnais_assure.trim());

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JREmptyDataSource());
			byte[] bytes = JasperExportManager.exportReportToPdf(print);

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseContentLength(bytes.length);
			ec.setResponseHeader("Content-Disposition", "inline; filename=\""
					+ adhesion.getPolice().getNPolice() + "rapport.pdf" + "\"");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

			adhesion.setImpr(true);
			adhesionDAO.edit(adhesion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// devisBean.setClient(new Client());
		// assistanceVoiyageController.setNewAdhesion(new Adhesion());
		// adhesionToPrintNotise=null;
	}

	Adhesion adhesionToPrintNotise;

	public Adhesion getAdhesionToPrintNotise() {
		return adhesionToPrintNotise;
	}

	public void setAdhesionToPrintNotise(Adhesion adhesionToPrintNotise) {
		this.adhesionToPrintNotise = adhesionToPrintNotise;
	}

	public void validerPropositionAssistance() {

		Client client = devisBean.getClient();
		Adhesion adhesion = assistanceVoiyageController.getNewAdhesion();
		if (client.getIdentifiant() == null
				|| client.getIdentifiant().isEmpty()) {
			noneException = false;
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} else if (!adhesion.getNombrePersonne().equals(BigDecimal.valueOf(adhesion.getAdherents().size()))) {
			noneException = false;

			FacesMessage msg = new FacesMessage("ERREUR",	"Vérifier le nombre des assurés");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

		} else {


			if (client.getDatCreation() == null) {
				client.setDatCreation(new Date());
			}
			if(clientDAO.findByIdentifiant(client.getIdentifiant())!=null)
			{
				System.out.println("client existant");
				client.setIdClient( clientDAO.findByIdentifiant(client.getIdentifiant()).getIdClient()); 


			}

			noneException = true;

			adhesion.setClient(client);

			for (Adherent adherent : adhesion.getAdherents()) {

				adherent.setAdhesion(adhesion);

			}

			adhesion.setIdUtilisateurs(utilisateur.getIdUtilisateurs());

			if (newProposition != null) {
				adhesion.setProposition(newProposition);

			}
			System.out.println("compte client=-"+client.getNcompte()+"-");
			if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000") ){
				if( client.getNcompte() == null || (client.getNcompte()!=null && client.getNcompte().equals(""))){
				adhesion.setEtat("V");
				}
				else {
					adhesion.setEtat("N");
				}
			}
			else{
				adhesion.setEtat("V");
			}

			adhesionToPrintNotise = serviceAssistantVoiyagetService	.createAdhesion(adhesion);
			System.out.println("End insert");
			
			if( client.getNcompte() == null || (client.getNcompte()!=null && client.getNcompte().equals(""))){
				assistanceVoiyageController.setDesabledGenerateNotice(false);
				}

			JsfUtil.addSuccessMessage("Adhésion Validée avec le numéro: "+ adhesionToPrintNotise.getNadhesion(), "!");
		
			assistanceVoiyageController.maxnomberPersonneOne = true;


			//Status = 0;
			//validate = 1;
		}
	}

	public String returnToDevisAssistance() {
		if (noneException && adhesionToPrintNotise != null) {
			initialisationClientAndAdhesion();
		}
		return "devisAssistantvoyage";
	}

	public void initialisationClientAndAdhesion() {

		Adhesion newAdhesion = new Adhesion();

		newAdhesion.setNombrePersonne(BigDecimal.valueOf(1));

		assistanceVoiyageController.setNewAdhesion(newAdhesion);
		assistanceVoiyageController.setSelectedProposition(null);;
		assistanceVoiyageController.setNomberPersonneMax(1);
		assistanceVoiyageController.setNombrePersonneMin(1);
		assistanceVoiyageController.maxnomberPersonneOne = true;
		clientBean.setClient(new Client());
		newProposition = null;
		adhesionToPrintNotise = null;
		assistanceVoiyageController.setDesabledGenerateNotice(true);
		assistanceVoiyageController.setDesabledEncaiss(true);

	}

	private boolean noneException = true;

	//	public void taritementProp()
	//	{
	//		int result = propositionDAO.traitement_prop(tdProposition.getnProposition());
	//		if(result==1){
	//			JsfUtil.addSuccessMessage("Adhésion traitée avec succès ","!");
	//		}
	//
	//		//un champ traitement dans proposition table 
	//		//update taitement =1 en appyant sur le bouttant
	//		//traitement est au depart=0
	//		//ajouter dans la fonction validerproposition le controle taritemant=1
	//	}





	public void encaissement_ancienne() throws NoSuchAlgorithmException, KeyManagementException{

		validerProposition();

		devisBean.getAdhesion().setEtat("N");

		adhesionDAO.edit(devisBean.getAdhesion());

		if(/*validate ==0*/ devisBean.getAdhesion() == null
				|| devisBean.getAdhesion().getIdAdhesion() == 0 ) return;

		if(clientBean.getClient().getNcompte() == null || clientBean.getClient().getNcompte().equalsIgnoreCase("")){

			FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

		}
		
		else if  (devisBean.getClient().getIdentifiant() == null
				|| devisBean.getClient().getIdentifiant().isEmpty()) {		
			
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					
		}
		
		else if ((assistanceVoiyageController.getNombreAssureAajouter() != 0) && (devisBean.getAdhesion().getTypeAbonnement().getValTypeAbonnement().equalsIgnoreCase("Familial") ))
		{
			
			FacesMessage msg = new FacesMessage("ERREUR", "veuillez saissir tout les assuré ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					
			
		}
		
		else{


			try {
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
					"    \"montant\":\""+getPrimeglobal()+"\",\n" +
					"\n" +
					"    \"description\": \"Tmp Deces : "+devisBean.getAdhesion().getNadhesion()+"\",\n" +
					"\n" +
					"    \"typeOpeCode\":\"OCCA20001\"\n" +
					"\n" +
					"}";

			System.out.println(input);

			ExampleResourceClient erc = new ExampleResourceClient("",BASE_URI);
			//System.out.println(erc.getXml());


			ClientResponse response = erc.HttpReponsePost(input);

			if (response.getStatus() != 200) {
				System.out.println("probleme de donnees");
				FacesMessage msg = new FacesMessage("Erreur serveur (code ="+response.getStatus()+"), veuiller contacter l'administrateur.", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);

				devisBean.setDesabledGenerateNotice(true);
			}

			else{

				System.out.println("Output from Server .... \n" +response.getStatus());
				String output = response.getEntity(String.class);
				System.out.println(output);

				JSONObject obj = new JSONObject(output);

				if(obj.getString("Status").equalsIgnoreCase("Rejected")){

					FacesMessage msg = new FacesMessage("Echec d'encaissement", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

					devisBean.setDesabledGenerateNotice(true);

				}else{

					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

					/**** enregistremnt ds notre !BD ****/

					OperationTransaction operationTransaction = new OperationTransaction();

					operationTransaction.setDateTrans(new Date());
					operationTransaction.setIdAdhesion(devisBean.getAdhesion()); // à discuter
					operationTransaction.setRefOperation(obj.getString("Reference"));
					operationTransaction.setTypTrans("Encaissement");

					iOperationTransactionDAO.create(operationTransaction);
					//Status =1;

					devisBean.getAdhesion().setEtat("V");

					adhesionDAO.edit(devisBean.getAdhesion());
				}

			}
			}catch(Exception e){
				FacesMessage msg = new FacesMessage("Encaissement erroné.", "");
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			}

		}

	}
	
	
	public void encaissement(){

		validerProposition();

		devisBean.getAdhesion().setEtat("N");

		adhesionDAO.edit(devisBean.getAdhesion());

		if(/*validate ==0*/ devisBean.getAdhesion() == null
				|| devisBean.getAdhesion().getIdAdhesion() == 0 ) return;

		if(clientBean.getClient().getNcompte() == null || clientBean.getClient().getNcompte().equalsIgnoreCase("")){

			FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

		}
		
		else if  (devisBean.getClient().getIdentifiant() == null
				|| devisBean.getClient().getIdentifiant().isEmpty()) {		
			
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					
		}
		
		else if ((assistanceVoiyageController.getNombreAssureAajouter() != 0) && (devisBean.getAdhesion().getTypeAbonnement().getValTypeAbonnement().equalsIgnoreCase("Familial") ))
		{
			
			FacesMessage msg = new FacesMessage("ERREUR", "veuillez saissir tout les assuré ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					
			
		}
		
		else{



			String token = TokenSTB.tokenStb(reportController);
			
			if(token == null) {
				
				FacesMessage msg = new FacesMessage("ERREUR", "Echec d'encaissement: pas de réponse au serveur!");
				  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				  
				  devisBean.setDesabledGenerateNotice(true);
				  
				  assistanceVoiyageController.setDisabledall(true);
				  return;
				
			}else {
				
				if(token.equalsIgnoreCase("exception")) {
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , veuiller contacter l'administrateur!", ""));
		     		
					devisBean.setDesabledGenerateNotice(true);
					
					assistanceVoiyageController.setDisabledall(true);
		     		
		     		System.out.println("probleme de Exception  ");
		     		
		     		return;
				}
				
				if(token.equalsIgnoreCase("not200")) {
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , problème de droit d'accès!", ""));
		     		
					devisBean.setDesabledGenerateNotice(true);
					
					assistanceVoiyageController.setDisabledall(true);
		     		
		     		System.out.println("probleme de Exception  ");
		     		
		     		return;
				}
			}



			//final String BASE_URI = Proprietes.URL_ENCAISS_STB;
			final String BASE_URI = Constante.URL_ENCAISS_STB;
			
			
			System.out.println("BASE_URI "+Constante.URL_ENCAISS_STB);
			
			String IdDemande = String.valueOf( devisBean.getAdhesion().getIdAdhesion() );
			
			String PrimeAssurance = String.valueOf(getPrimeglobal());
			String AccountNumber = clientBean.getClient().getNcompte();
			String Police = devisBean.getAdhesion().getPolice().getNPolice();//devisBean.getAdhesion().getNadhesion();
			String Cin = clientBean.getClient().getIdentifiant();
			String Nom = clientBean.getClient().getNomClient(); 
			String Prenom = clientBean.getClient().getPrenomClient();
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			String DateNaissance = df.format(clientBean.getClient().getDatNaiss());
			
			String CapitalAssure = "0";
			
			String DateEffet = df.format(devisBean.getAdhesion().getDatDebut());
			String Datefin = df.format(devisBean.getAdhesion().getDatFin());
			
			String Mobile = clientBean.getClient().getTel();
			String Mail = clientBean.getClient().getMail();
			
			String Produit = Constante.PRODUIT_TD;
			
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
					FacesMessage msg = new FacesMessage("Echec d'encaissement: pas de réponse au serveur!", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

					devisBean.setDesabledGenerateNotice(true);
					
					assistanceVoiyageController.setDisabledall(true);
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
					FacesMessage msg = new FacesMessage(status + " " + rejectMessage, "Erreur serveur (code ="+response.getStatus()+") ");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

					devisBean.setDesabledGenerateNotice(true);
					
					assistanceVoiyageController.setDisabledall(true);
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
					
					assistanceVoiyageController.setDisabledall(false);
                   
					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					System.out.println("Encaissement effectuée avec succès.");
					
					/**** enregistremnt ds notre !BD ****/

					
					OperationTransaction operationTransaction = new OperationTransaction();

					operationTransaction.setDateTrans(new Date());
					operationTransaction.setIdAdhesion(devisBean.getAdhesion()); // à discuter
					operationTransaction.setRefOperation(Reference);
					operationTransaction.setTypTrans("Encaissement");

					iOperationTransactionDAO.create(operationTransaction);
					//Status =1;

					devisBean.getAdhesion().setEtat("V");

					adhesionDAO.edit(devisBean.getAdhesion());


				}else {
					
					 System.out.println("Echec d'encaissement");
						FacesMessage msg = new FacesMessage("Echec d'encaissement : " + Status + " " + RejectMessage, "ERREUR");
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);

						devisBean.setDesabledGenerateNotice(true);
						
						assistanceVoiyageController.setDisabledall(true);
						return;
					
				}
			       
			        
			    	  
				
				} 
			catch (ClientHandlerException e) {

				
	     		
	     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement: problème d'accès au serveur!", ""));
	     		
	     		devisBean.setDesabledGenerateNotice(true);
	     		
	     		assistanceVoiyageController.setDisabledall(true);
	     		
	     		System.out.println("probleme de ClientHandlerException  ");
	     		
				e.printStackTrace();

			  }
	catch (org.json.JSONException e) {

				
	     		
	     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , erreur au niveau du retour du WS d'encaissement!", ""));
	     		
	     		//h7ayyaController.setDisabledValidationAdhesion(false);
	     		//setDesabledEncaiss(true); 
	     		
	     		System.out.println("probleme de JSONException  ");
	     		
				e.printStackTrace();

			  }
			catch (Exception e) {

					
		     		
		     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , veuiller contacter l'administrateur!", ""));
		     		
		     		devisBean.setDesabledGenerateNotice(true);
		     		
		     		assistanceVoiyageController.setDisabledall(true);
		     		
		     		System.out.println("probleme de Exception  ");
		     		
					e.printStackTrace();

				  }
			
			
	 

		}

	}
	
	
	
	/************************** assistance voyage ************************************************************/

	public void encaissementAssistance_ancienne() throws NoSuchAlgorithmException, KeyManagementException{
		
		boolean test = true;

		
		validerPropositionAssistance();
		if(/*validate ==0*/  adhesionToPrintNotise == null || adhesionToPrintNotise.getIdAdhesion() == 0) return;
		adhesionToPrintNotise.setEtat("N");

		//adhesionDAO.edit(adhesionToPrintNotise);
		adhesionToPrintNotise = adhesionDAO.merge(adhesionToPrintNotise);


		Client client = devisBean.getClient();
		Adhesion adhesion = assistanceVoiyageController.getNewAdhesion();
		
		//if(/*validate ==0*/  adhesionToPrintNotise == null || adhesionToPrintNotise.getIdAdhesion() == 0) return;
		


		if(clientBean.getClient().getNcompte() == null || clientBean.getClient().getNcompte().equalsIgnoreCase("")){
           
			FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

		}
			
		
	/*	else if (client.getIdentifiant() == null
				|| client.getIdentifiant().isEmpty()) {
			noneException = false;


			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} 
		 else if (!adhesion.getNombrePersonne().equals(BigDecimal.valueOf(adhesion.getAdherents().size()))) {
			noneException = false;

			FacesMessage msg = new FacesMessage("ERREUR",	"Vérifier le nombre des assurés");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

		}*/
		
		
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
					"    \"description\": \"Assistance voyage : "+adhesionToPrintNotise.getNadhesion()+"\",\n" +
					"\n" +
					"    \"typeOpeCode\":\"OCCA20003\"\n" +
					"\n" +
					"}";

			System.out.println(input);
			
			System.out.println("erreur icciiii");

			ExampleResourceClient erc = new ExampleResourceClient("",BASE_URI);
			//System.out.println(erc.getXml());

			System.out.println("erreur icciiii2222222222222");   
			
			ClientResponse response = erc.HttpReponsePost(input);
			
			System.out.println("erreur icciiii333333333333333");

			if ( response.getStatus() != 200) {
				System.out.println("probleme de connexion");
				FacesMessage msg = new FacesMessage("Erreur serveur (code ="+response.getStatus()+"), veuiller contacter l'administrateur.", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);

				assistanceVoiyageController.setDesabledGenerateNotice(true);
			}

			else{

				System.out.println("Output from Server .... \n" +response.getStatus());
				String output = response.getEntity(String.class);
				System.out.println(output);

				JSONObject obj = new JSONObject(output);

				if(obj.getString("Status").equalsIgnoreCase("Rejected")){
                    System.out.println("Echec d'encaissement");
					FacesMessage msg = new FacesMessage("Echec d'encaissement", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

					assistanceVoiyageController.setDesabledGenerateNotice(true);


				}else{

					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					System.out.println("Encaissement effectuée avec succès.");
					assistanceVoiyageController.setDesabledEncaiss(false); 
					/**** enregistremnt ds notre !BD ****/

					adhesionToPrintNotise.setEtat("V");

					adhesionDAO.edit(adhesionToPrintNotise);
					assistanceVoiyageController.setDesabledGenerateNotice(false);
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
	
	
	
public void encaissementAssistance(){
		
		boolean test = true;

		
		validerPropositionAssistance();
		if(/*validate ==0*/  adhesionToPrintNotise == null || adhesionToPrintNotise.getIdAdhesion() == 0) return;
		adhesionToPrintNotise.setEtat("N");

		//adhesionDAO.edit(adhesionToPrintNotise);
		adhesionToPrintNotise = adhesionDAO.merge(adhesionToPrintNotise);


		Client client = devisBean.getClient();
		Adhesion adhesion = assistanceVoiyageController.getNewAdhesion();
		
		//if(/*validate ==0*/  adhesionToPrintNotise == null || adhesionToPrintNotise.getIdAdhesion() == 0) return;
		


		if(clientBean.getClient().getNcompte() == null || clientBean.getClient().getNcompte().equalsIgnoreCase("")){
           
			FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

		}
			
		
	/*	else if (client.getIdentifiant() == null
				|| client.getIdentifiant().isEmpty()) {
			noneException = false;


			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} 
		 else if (!adhesion.getNombrePersonne().equals(BigDecimal.valueOf(adhesion.getAdherents().size()))) {
			noneException = false;

			FacesMessage msg = new FacesMessage("ERREUR",	"Vérifier le nombre des assurés");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

		}*/
		
		
		 else {

            

				String token = TokenSTB.tokenStb(reportController);
				
				if(token == null) {
					
					FacesMessage msg = new FacesMessage("ERREUR", "Echec d'encaissement: pas de réponse au WS Token!");
					  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					  
					  assistanceVoiyageController.setDesabledGenerateNotice(true);
					  
					  assistanceVoiyageController.setDisabledall(true);
					  return;
					
				}else {
					
					if(token.equalsIgnoreCase("exception")) {
						
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , Erreur de connexion au WS Token!", ""));
			     		
						assistanceVoiyageController.setDesabledGenerateNotice(true);
						
						assistanceVoiyageController.setDisabledall(true);
			     		
			     		System.out.println("probleme de Exception  ");
			     		
			     		return;
					}
					
					if(token.equalsIgnoreCase("not200")) {
						
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , problème de droit d'accès au WS Token!", ""));
			     		
						assistanceVoiyageController.setDesabledGenerateNotice(true);
						
						assistanceVoiyageController.setDisabledall(true);
			     		
			     		System.out.println("probleme de Exception  ");
			     		
			     		return;
					}
				}
		



			//final String BASE_URI = Proprietes.URL_ENCAISS_STB;
			final String BASE_URI = Constante.URL_ENCAISS_STB;
			
			System.out.println("BASE_URI "+Constante.URL_ENCAISS_STB);
			
			String IdDemande =  String.valueOf( adhesionToPrintNotise.getIdAdhesion() );
			
			String PrimeAssurance = String.valueOf(adhesionToPrintNotise.getMtPrime());
			String AccountNumber = clientBean.getClient().getNcompte();
			String Police = adhesionToPrintNotise.getPolice().getNPolice();//adhesionToPrintNotise.getNadhesion();
			String Cin = clientBean.getClient().getIdentifiant();
			String Nom = clientBean.getClient().getNomClient(); 
			String Prenom = clientBean.getClient().getPrenomClient();
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			String DateNaissance = df.format(clientBean.getClient().getDatNaiss());
			
			String CapitalAssure = "0";
			
			String DateEffet = df.format(adhesionToPrintNotise.getDatDebut());
			String Datefin = df.format(adhesionToPrintNotise.getDatFin());
			
			String Mobile = clientBean.getClient().getTel();
			String Mail = clientBean.getClient().getMail();
			
			String Produit = Constante.PRODUIT_ASSV;
			
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
			
			
			

			System.out.println(input);
			
			
try {
				
				

			    
			    com.sun.jersey.api.client.Client client_js = JerseyClient.ApiClient(reportController);
			    ClientResponse response = JerseyClient.PostReponseDuClientSTBEncaissement(BASE_URI, token, input, client_js, reportController);
			   
			    

				if ( response == null) {
					System.out.println("probleme de connexion vide");
					FacesMessage msg = new FacesMessage("Echec d'encaissement!", "Pas de réponse au WS d'encaissement!");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

					assistanceVoiyageController.setDesabledGenerateNotice(true);
					
					assistanceVoiyageController.setDisabledall(true);
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

					assistanceVoiyageController.setDesabledGenerateNotice(true);
					
					assistanceVoiyageController.setDisabledall(true);
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
                   
					assistanceVoiyageController.setDisabledall(false);
					
					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					System.out.println("Encaissement effectuée avec succès.");
					assistanceVoiyageController.setDesabledEncaiss(false); 
					/**** enregistremnt ds notre !BD ****/

					adhesionToPrintNotise.setEtat("V");

					adhesionDAO.edit(adhesionToPrintNotise);
					assistanceVoiyageController.setDesabledGenerateNotice(false);
					OperationTransaction operationTransaction = new OperationTransaction();

					operationTransaction.setDateTrans(new Date());
					operationTransaction.setIdAdhesion(adhesionToPrintNotise); // à discuter
					operationTransaction.setRefOperation(Reference);
					operationTransaction.setTypTrans("Encaissement");

					iOperationTransactionDAO.create(operationTransaction);


				}else {
					
					 System.out.println("Echec d'encaissement");
						FacesMessage msg = new FacesMessage("Echec d'encaissement : " + Status + " " + RejectMessage, "Erreur WS d'encaissement!");
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);

						assistanceVoiyageController.setDesabledGenerateNotice(true);
						
						assistanceVoiyageController.setDisabledall(true);
						return;
					
				}
			       
			        
			    	  
				
				} 
			catch (ClientHandlerException e) {

				
	     		
	     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement: problème d'appel au WS d'encaissement!", ""));
	     		
	     		assistanceVoiyageController.setDesabledGenerateNotice(true);
	     		
	     		assistanceVoiyageController.setDisabledall(true);
	     		
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
		     		
		     		assistanceVoiyageController.setDesabledGenerateNotice(true);
		     		
		     		assistanceVoiyageController.setDisabledall(true);
		     		
		     		System.out.println("probleme de Exception  ");
		     		
					e.printStackTrace();

				  }
			


		}
	}


	//public int Status = 0;
	//	public int validate = 0;	

	//		public int getStatus() {
	//			
	//			System.out.println("Status= "+Status);
	//			return Status;
	//		}
	//
	//		public void setStatus(int status) {
	//			Status = status;
	//		}

	//		public int getValidate() {
	//			return validate;
	//		}
	//
	//		public void setValidate(int validate) {
	//			this.validate = validate;
	//		}

	@PostConstruct
	public void init() {

		System.out.println("monCompte="+clientBean.getClient().getNcompte());

		//Status = 0;
		//validate = 0;

	}



	public Proposition UpdateProposition(Client client, Proposition proposition,Adhesion adhesion,	Utilisateurs utilisateurs) {
		//*
		proposition.setPolice(adhesion.getPolice());
		proposition.setClient(adhesion.getClient());

		proposition.setPoliceCredit(adhesion.getPoliceCredit());

		// proposition.setCapital(adhesion.getCapital()==null?null:adhesion.getCapital())
		// ;

		proposition.setDatDebut(adhesion.getDatDebut());
		proposition.setDatFin(adhesion.getDatFin());
		proposition.setDuree(adhesion.getDuree());

		proposition.setFraPolice(adhesion.getFraPolice());
		proposition.setDurPaiemt(adhesion.getDurPaiemt());
		proposition.setCapital(adhesion.getCapital());
		proposition.setMajoration(adhesion.getMajoration());
		proposition.setTauxSprim(adhesion.getTauxSprim());
		proposition.setTauxInter(adhesion.getTauxInter());
		proposition.setEtat(adhesion.getEtat());
		proposition.setDatMaj(adhesion.getDatMaj());
		proposition.setTauxPrime(adhesion.getTauxPrime());
		proposition.setFranchise(adhesion.getFranchise());
		proposition.setMtPrime(adhesion.getMtPrime());

		proposition.setNombrePersonne(adhesion.getNombrePersonne());
		proposition.setDestination(adhesion.getDestination());
		proposition.setTelphone(adhesion.getTelphone());
		proposition.setTypeAbonnement(adhesion.getTypeAbonnement());

		if(utilisateur.getProfil().getCode().equals(PofileEnum.TDST.name())){
			proposition.setNotifie(true);}

		proposition.setDatcreation(new Date());
		if (client.getIdClient() == 0) {
			client.setDatCreation(new Date());
		}

		proposition.setClient(client);

		proposition.setIdUtilisateurs(utilisateurs.getIdUtilisateurs());

		return serviceAssistantVoiyagetService.UpdateProposition(proposition);
	}

}
