package com.star.Ba.jsf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.star.Ba.commun.AgeCalculator;
import com.star.Ba.dao.AssistantVoiyagetDAOInter;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dao.IPropositionDAO;
import com.star.Ba.dao.IQuittanceDAO;
import com.star.Ba.dao.ITypeAbonnementDAO;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.AssistantVoiyagetServiceInter;

/**
 * @author
 * 
 */
@ManagedBean(name = "tarifAssTlsBean")
@SessionScoped
public class TarifAssTlsBean implements Serializable {

	private double primeglobal;
	private String numquit;
	private Adhesion adhesion;
	@EJB
	private IAdhesionDAO adhesionDAO;
	@EJB
	private IClientDAO clientDAO;
	@EJB
	private IPropositionDAO propositionDAO;
	@EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;
	@EJB
	private AssistantVoiyagetDAOInter voiyagetDAOInter;
	@EJB
	private IQuittanceDAO quittanceDAO;

	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;
	@ManagedProperty("#{assistanceVoiyageController}")
	private AssistanceVoiyageController assistanceVoiyageController;

	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	
	@EJB
	private ITypeAbonnementDAO typeAbonnementDAO;

	public TarifAssTlsBean() {

	}

	
	public String getNumquit() {
		return numquit;
	}


	public void setNumquit(String numquit) {
		this.numquit = numquit;
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

	public TarifAssTlsBean(double primeglobal) {

		this.primeglobal = primeglobal;
	}

	//	public double getPrimeglobal() {
	//
	//		devisBean.setShowTd(false);
	//		return devisBean.getPrimeglobal();
	//
	//	}

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



	public Integer calculAge(Date datnaiss) {
		if (datnaiss != null) {
			if (AgeCalculator.calculateAge(datnaiss).getMonths() >= 6) {
				return AgeCalculator.calculateAge(datnaiss).getYears() + 1;
			}
			return AgeCalculator.calculateAge(datnaiss).getYears();
		}

		return -1;
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
					+  "devis_voyage.pdf" + "\"");

			OutputStream output = ec.getResponseOutputStream();
			output.write(bytes);
			fc.responseComplete();

			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		
		
		
	}



	
	
	
	

	public void imprimerDevisAssistanceVoiyage__(String numplc,
			String identifiant, String nomclt, String prenomclt, Date effDate,
			String duree, Date datefin, String type_contrat,
			double primrglobal, String ville_agence, String imagesociete,
			String nbrpersonne, String numprop) {

		// fonction d'impression du devis
		Map<String, Object> parameters = new HashMap();

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

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

	public Proposition createProposition(Client client, Adhesion adhesion,
			Utilisateurs utilisateurs) {
		Proposition proposition = new Proposition();
		proposition.setPolice(adhesion.getPolice());
		proposition.setClient(adhesion.getClient());
		proposition.setPoliceCredit(adhesion.getPoliceCredit());
		proposition.setPack(adhesion.getPack());
		proposition.setMajoration(adhesion.getMajoration());
		proposition.setPrimetot(adhesion.getPrimetot());
		proposition.setCout(adhesion.getCout());


		proposition.setDatDebut(adhesion.getDatDebut());
		proposition.setDatFin(adhesion.getDatFin());
		proposition.setDuree(adhesion.getDuree());

		proposition.setFraPolice(adhesion.getFraPolice());
		proposition.setDurPaiemt(adhesion.getDurPaiemt());

		proposition.setEtat(adhesion.getEtat());
		proposition.setDatMaj(adhesion.getDatMaj());
		proposition.setTauxPrime(adhesion.getTauxPrime());

		proposition.setMtPrime(adhesion.getMtPrime());
		proposition.setPersonnePrv(adhesion.getPersonnePrv());
		proposition.setNombrePersonne(adhesion.getNombrePersonne());
		proposition.setDestination(adhesion.getDestination());
		proposition.setTelphone(adhesion.getTelphone());
		proposition.setTypeAbonnement(adhesion.getTypeAbonnement());

		proposition.setDatcreation(new Date());
		if (client.getIdClient() == 0) {
			client.setDatCreation(new Date());
		}
		if(client.getTypePers().equals("SO")){
client.setProfession(null);}
		proposition.setClient(client);

		proposition.setIdUtilisateurs(utilisateurs.getIdUtilisateurs());

		return serviceAssistantVoiyagetService.createProposition(proposition);
	}

	public void generateDevisAssistanceVoiyageTLS() {
		Client client = assistanceVoiyageController.getClientBean().getClient();
		Adhesion adhesion = assistanceVoiyageController.getNewAdhesion();
		if (client.getIdentifiant() == null	|| client.getIdentifiant().isEmpty()) {
			noneException = false;
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} else {
			
			newProposition = assistanceVoiyageController.getNewProposition();

			if (newProposition == null) {
				newProposition = createProposition(client, adhesion,
						utilisateur);
				assistanceVoiyageController.setNewProposition(newProposition);
			}

			JsfUtil.addSuccessMessage(
					"Proposition enregistrée avec le numéro: "
							+ newProposition.getnProposition(), "!");

			imprimerDevisAssistanceVoiyage(adhesion.getPolice().getNPolice(),
					client.getIdentifiant(), client.getNomClient(),
					client.getPrenomClient(), adhesion.getDatDebut(),
					String.valueOf(adhesion.getDuree()), adhesion.getDatFin(),
					adhesion.getTypeAbonnement().getValTypeAbonnement(),

					adhesion.getPrimetot(),
					utilisateur.getAgence().getLibelle(),  utilisateur.getAgence().getSociete()
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

		Adhesion adhesion=adhesionToPrintNotise;
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
			parameters.put("star", "star.png");
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
			parameters.put("frais", formatter.format(adhesion.getCout().doubleValue()+adhesion.getFga()));
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

	Adhesion adhesionToPrintNotise;

	public Adhesion getAdhesionToPrintNotise() {
		return adhesionToPrintNotise;
	}

	public void setAdhesionToPrintNotise(Adhesion adhesionToPrintNotise) {
		this.adhesionToPrintNotise = adhesionToPrintNotise;
	}

	public void validerPropositionAssistanceTLS() {

		Client client = assistanceVoiyageController.getClientBean().getClient();
		if (client.getIdClient() == 0) {
			client.setDatCreation(new Date());
		}
		if(client.getTypePers().equals("SO")){
		client.setProfession(null);}
		Adhesion adhesion = assistanceVoiyageController.getNewAdhesion();
		if (client.getIdentifiant() == null	|| client.getIdentifiant().isEmpty()) {
			noneException = false;
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
		} else if (!adhesion.getNombrePersonne().equals(BigDecimal.valueOf(adhesion.getAdherents().size()))) {
			noneException = false;

			FacesMessage msg = new FacesMessage("ERREUR","Vérifier le nombre des assurées");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);

		} else {

			noneException = true;
			
			/*** modifié ici car le client avant s'ajoute 2 fois ds le cas propo puis adhesion****/
			if (newProposition != null) {
				adhesion.setProposition(newProposition);
				client = newProposition.getClient();
				System.out.println("id_client "+client.getIdClient());

			}

			adhesion.setClient(client);

			for (Adherent adherent : adhesion.getAdherents()) {

				adherent.setAdhesion(adhesion);

			}

			adhesion.setIdUtilisateurs(utilisateur.getIdUtilisateurs());

//			if (newProposition != null) {
//				adhesion.setProposition(newProposition);
//
//			}
			adhesion.setEtat("V");
			adhesionToPrintNotise = serviceAssistantVoiyagetService	.createAdhesionTLS(adhesion,getNumeroPolice());
			
			//insert quittance
			numquit=serviceAssistantVoiyagetService.getNumeroQuit();
			Quittance quittance=new Quittance(adhesionToPrintNotise.getDatFin(), adhesionToPrintNotise.getDatDebut(), adhesionToPrintNotise, adhesionToPrintNotise.getMtPrime(), adhesionToPrintNotise.getCout().doubleValue(), (double)0, Integer.parseInt(numquit), new Date(),adhesionToPrintNotise.getPolice().getFga());
			quittanceDAO.create(quittance);
			
			System.out.println("End insert");

			assistanceVoiyageController.setDesabledGenerateNotice(false);

			JsfUtil.addSuccessMessage("Contrat enregistré avec le numéro: "+ adhesionToPrintNotise.getNadhesion(), "!");

		}
	}



	public void initialisationClientAndAdhesion() {

		Adhesion newAdhesion = new Adhesion();

		newAdhesion.setNombrePersonne(BigDecimal.valueOf(1));
		newAdhesion.setTypeAbonnement(typeAbonnementDAO.find((long)1));

		assistanceVoiyageController.setNewAdhesion(newAdhesion);
		assistanceVoiyageController.setSelectedProposition(null);
		assistanceVoiyageController.setNomberPersonneMax(1);
		assistanceVoiyageController.setNombrePersonneMin(1);
		assistanceVoiyageController.maxnomberPersonneOne = true;
		clientBean.setClient(new Client());
		newProposition = null;
		adhesionToPrintNotise = null;
		assistanceVoiyageController.setDesabledGenerateNotice(true);

	}

	private boolean noneException = true;

	public String returnToDevisAssistanceTls() {
		if (noneException && adhesionToPrintNotise != null) {
			initialisationClientAndAdhesion();
			assistanceVoiyageController.setNewProposition(null);
			assistanceVoiyageController.setNewAdherent(new Adherent());  
		}
		return "devis_AssistvoyageTLS";
	}



	public String getNumeroPolice() {
		Adhesion a=new Adhesion();
		Integer currentNomber ;
		String codeProduit=new Long(assistanceVoiyageController.getNewAdhesion().getPolice().getCodProdui().getCodeProduit()).toString();

		String structure="000000";
		a=adhesionDAO.FindMaxNadhesion(assistanceVoiyageController.getNewAdhesion().getPolice());
		System.out.println("adhesion="+a);
		if(a == null)
		{
			currentNomber=1;
		}
		else{
			currentNomber=Integer.valueOf(a.getNadhesion().substring(3, 9))+1;
		}
		String number = currentNomber.toString();
		String newNomber =codeProduit+ structure.substring(0, structure.length() - number.length())	+ number;
		return newNomber;
	}


	public void ImprimerQuittanceTLS() {

		Adhesion adhesion =adhesionToPrintNotise;
		String report;
		try {
			
				 report = reportController.getRealPath()	+ "qtcTLS.jasper";
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			DecimalFormat formatter = new DecimalFormat("#0.000");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			parameters.put("imagesociete", utilisateur.getAgence().getSociete().getCodSociet()+ ".png");
			parameters.put("star",  "star.png");
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
			parameters.put("frais", formatter.format(adhesion.getCout().doubleValue()+adhesion.getFga()));
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
	
	
//	public void EnregistrerQuittance()
//	{
//		Adhesion adhesion=adhesionToPrintNotise;
//		String numquit=serviceAssistantVoiyagetService.getNumeroQuit();
//		Quittance quittance=new Quittance(adhesion.getDatFin(), adhesion.getDatDebut(), adhesion, adhesion.getMtPrime(), adhesion.getCout().doubleValue(), (double)0, Integer.parseInt(numquit), new Date());
//		quittanceDAO.create(quittance);
//		ImprimerQuittanceTLS(adhesion,numquit);
//	}
	
	
	

}
