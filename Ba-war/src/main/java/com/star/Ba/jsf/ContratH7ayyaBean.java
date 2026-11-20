package com.star.Ba.jsf;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.naming.InitialContext;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IOperationTransactionDAO;
import com.star.Ba.dao.IQuittanceDAO;
import com.star.Ba.dao.IQuittanceDetailsDAO;
import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.DetailsGarantieDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.Nombre;
import com.star.Ba.jsf.util.ProxyConnectionFactory;
import com.star.Ba.jsf.util.TokenSTB;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.BilanFinancierHayya;
import com.star.Ba.model.Client;
import com.star.Ba.model.Locgouv;
import com.star.Ba.model.ModePaiementHayya;
import com.star.Ba.model.OperationTransaction;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.QuittanceDetails;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.parametres.ExampleResourceClient;
import com.star.Ba.parametres.TrustAllX509TrustManager;
import com.star.Ba.service.AssistantVoiyagetServiceInter;
import com.star.Ba.service.HayyaServiceInter;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONSerializer;

@ManagedBean(name = "contrat7ayyaBean")
@SessionScoped
public class ContratH7ayyaBean implements Serializable {
	
	
	
	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;
	
	@ManagedProperty("#{h7ayyaController}")
	private H7ayyaController h7ayyaController;
	
	@ManagedProperty("#{tarif7ayyaBean}")
	private TarifH7ayyaBean tarif7ayyaBean;

	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;
	
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	
	
	
	@EJB
	private HayyaServiceInter hayyaServiceInter;
	
	
	@EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;
	@EJB
	private IQuittanceDAO quittanceDAO;
	
	@EJB
	private IQuittanceDetailsDAO quittanceDetailsDAO;
	
	@EJB
	private IAdhesionDAO adhesionDAO;
	
	@EJB
    private IOperationTransactionDAO iOperationTransactionDAO;
	
	
	private String echDate;
	
	private List<DetailsGarantieDTO> detailsGaranties;
	
	private List<DetailsEvolutionGarantie> detailsEvolutionGaranties;
	
	
	private BigDecimal pnette;
	private BigDecimal accessoires;
	private BigDecimal taxes;
	private BigDecimal total;
	
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
	
	
	
	
	@PostConstruct
	public void init() {
		
		System.out.println("date echeance init "+h7ayyaController.getEchDate());

	}
	
	
	//getter and setter
	
	
	
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
	
	
	
	
	
//	public boolean isDisabledValidationAdhesion() {
//		return disabledValidationAdhesion;
//	}
//
//
//	public void setDisabledValidationAdhesion(boolean disabledValidationAdhesion) {
//		this.disabledValidationAdhesion = disabledValidationAdhesion;
//	}


	public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}

	public H7ayyaController getH7ayyaController() {
		return h7ayyaController;
	}

	public void setH7ayyaController(H7ayyaController h7ayyaController) {
		this.h7ayyaController = h7ayyaController;
	}
	
	
	
	
	
	public TarifH7ayyaBean getTarif7ayyaBean() {
		return tarif7ayyaBean;
	}


	public void setTarif7ayyaBean(TarifH7ayyaBean tarif7ayyaBean) {
		this.tarif7ayyaBean = tarif7ayyaBean;
	}


	public String getEchDate() {
		
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		return  formatter.format(h7ayyaController.getEchDate());
		
	}


	public void setEchDate(String echDate) {
		this.echDate = echDate;
	}
	


	public List<DetailsGarantieDTO> getDetailsGaranties() {
		return h7ayyaController.getDetailsGaranties();
	}


	public void setDetailsGaranties(List<DetailsGarantieDTO> detailsGaranties) {
		this.detailsGaranties = detailsGaranties;
	}
	
	
	
	


	public List<DetailsEvolutionGarantie> getDetailsEvolutionGaranties() {
		return h7ayyaController.getDetailsEvolutionGaranties();
	}


	public void setDetailsEvolutionGaranties(List<DetailsEvolutionGarantie> detailsEvolutionGaranties) {
		this.detailsEvolutionGaranties = detailsEvolutionGaranties;
	}


	public BigDecimal getPnette() {
		return h7ayyaController.getPnette();
	}


	public void setPnette(BigDecimal pnette) {
		this.pnette = pnette;
	}


	public BigDecimal getAccessoires() {
		return h7ayyaController.getAccessoires();
	}


	public void setAccessoires(BigDecimal accessoires) {
		this.accessoires = accessoires;
	}


	public BigDecimal getTaxes() {
		return h7ayyaController.getTaxes();
	}


	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}


	public BigDecimal getTotal() {
		return h7ayyaController.getTotal();
	}


	public void setTotal(BigDecimal total) {
		this.total = total;
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
	
	
//	public String getVie() {
//		return vie;
//	}
//
//
//	public void setVie(String vie) {
//		this.vie = vie;
//	}
//
//
//	public String getDeces() {
//		return deces;
//	}
//
//
//	public void setDeces(String deces) {
//		this.deces = deces;
//	}
	
	
	
//	
//	public String getModepaie() {
//		return modepaie;
//	}
//
//
//	public void setModepaie(String modepaie) {
//		this.modepaie = modepaie;
//	}
//
//	
//	
//
//
//
//	public String getBanque() {
//		return banque;
//	}
//
//
//	public void setBanque(String banque) {
//		this.banque = banque;
//	}
//
//
//
//
//
//
//	public String getAgence() {
//		return agence;
//	}
//
//
//	public void setAgence(String agence) {
//		this.agence = agence;
//	}
//
//
//	public String getRib() {
//		return rib;
//	}
//
//
//	public void setRib(String rib) {
//		this.rib = rib;
//	}
//
//
//	public String getPayeur() {
//		return payeur;
//	}
//
//
//	public void setPayeur(String payeur) {
//		this.payeur = payeur;
//	}
//
//
//	public String getNomPayeur() {
//		return nomPayeur;
//	}
//
//
//	public void setNomPayeur(String nomPayeur) {
//		this.nomPayeur = nomPayeur;
//	}
//
//
//	public String getCinPayeur() {
//		return cinPayeur;
//	}
//
//
//	public void setCinPayeur(String cinPayeur) {
//		this.cinPayeur = cinPayeur;
//	}
//
//
//	public String getAdressePayeur() {
//		return adressePayeur;
//	}
//
//
//	public void setAdressePayeur(String adressePayeur) {
//		this.adressePayeur = adressePayeur;
//	}
//
//
//	public String getDispoSpec() {
//		return dispoSpec;
//	}
//
//
//	public void setDispoSpec(String dispoSpec) {
//		this.dispoSpec = dispoSpec;
//	}
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
	
	
	


//	public boolean isTestImpression() {
//		return testImpression;
//	}
//
//
//	public void setTestImpression(boolean testImpression) {
//		this.testImpression = testImpression;
//	}


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


			    
	
	// fonctions
	



	public String returnToDevis7ayya(){
		
		return "devis_7ayya";
	}
	
	
	public String validerProposition() {
		
		return "contrat7ayya?faces-redirect=true";
	}
	
	
	public void generateDevis7ayya() {
		
		//System.out.println("date echeance "+h7ayyaController.getEchDate());
	}
	
	
	
	public void ValiderProposition() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		
		int tot_quot_deces = 0;
		
		int tot_quot_vie = 0;
		
		
		try {
		
		if(!h7ayyaController.isDisabledValidationAdhesion()) {
			
			
			// beneficiaire cas vie
			
	        List<Person> personnes = new ArrayList<Person>();
	        
	        System.out.println("vie "+h7ayyaController.getVie());
	        
	        if(h7ayyaController.getVie().equals("1")) {//assure
	        	Person assure = new Person();
	        	assure.setNom(tarif7ayyaBean.getClientBean().getClient().getNomClient());
	        	assure.setPrenom(tarif7ayyaBean.getClientBean().getClient().getPrenomClient());
	        	assure.setCin(tarif7ayyaBean.getClientBean().getClient().getIdentifiant());
	        	assure.setLien("assuré");
	        	assure.setQuote(100);
	        	personnes = new ArrayList<Person>();
	        	personnes.add(assure);
	        	System.out.println("personnes "+tarif7ayyaBean.getClientBean().getClient().getNomClient());
	        	System.out.println("personnes "+personnes.size());
	        	
	        	tot_quot_vie = 100;
	        }
	        
	        if(h7ayyaController.getVie().equals("2")) {// enfants
	        	personnes = new ArrayList<Person>();
	        	personnes = h7ayyaController.enfants;
	        	System.out.println("personnes "+personnes.size());
	        	
	        	for(Person pers_vie :personnes) {
	        		
	        		tot_quot_vie = pers_vie.getQuote() + tot_quot_vie;
	        	}
	        }
	        
	        if(h7ayyaController.getVie().equals("3")) {// autres
	        	personnes = new ArrayList<Person>();
	        	personnes = h7ayyaController.autres;
	        	System.out.println("personnes "+personnes.size());
	        	
	        	for(Person pers_vie :personnes) {
	        		
	        		tot_quot_vie = pers_vie.getQuote() + tot_quot_vie;
	        	}
	        }
	        
	        // beneficiaire cas deces 
	        
	        List<Person> personnes_deces = new ArrayList<Person>();
	        
	        System.out.println("deces "+h7ayyaController.getDeces());
	        
	        if(h7ayyaController.getDeces().equals("1")) {//defaut
	        	personnes_deces = new ArrayList<Person>();
	        	personnes_deces = h7ayyaController.defauts;
	        	
	        	for(Person pers_dece :personnes_deces) {
	        		
	        		tot_quot_deces = pers_dece.getQuote() + tot_quot_deces;
	        	}
	        }
	        
	        if(h7ayyaController.getDeces().equals("2")) {// enfants
	        	personnes_deces = new ArrayList<Person>();
	        	personnes_deces = h7ayyaController.enfantds;
	        	System.out.println("personnes "+personnes_deces.size());
	        	
	        	
	        	for(Person pers_dece :personnes_deces) {
	        		
	        		tot_quot_deces = pers_dece.getQuote() + tot_quot_deces;
	        	}
	        		        	
	        }
	        
	        if(h7ayyaController.getDeces().equals("3")) {// conjoint
	        	personnes_deces = new ArrayList<Person>();
	        	personnes_deces = h7ayyaController.conjoints;
	        	System.out.println("personnes "+personnes_deces.size());
	        	
	        	for(Person pers_dece :personnes_deces) {
	        		
	        		tot_quot_deces = pers_dece.getQuote() + tot_quot_deces;
	        	}
	        }
	        
	        if(h7ayyaController.getDeces().equals("4")) {// parents
	        	personnes_deces = new ArrayList<Person>();
	        	personnes_deces = h7ayyaController.parents;
	        	System.out.println("personnes "+personnes_deces.size());
	        	
	        	for(Person pers_dece :personnes_deces) {
	        		
	        		tot_quot_deces = pers_dece.getQuote() + tot_quot_deces;
	        	}
	        }
	        
	        if(h7ayyaController.getDeces().equals("5")) {// autres
	        	personnes_deces = new ArrayList<Person>();
	        	personnes_deces = h7ayyaController.autreds;
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
	        
	        if(Integer.parseInt(h7ayyaController.getModepaie())==2) {
	        	
	      	   BigDecimal  b1 = new BigDecimal(h7ayyaController.getRib());
	     	   BigDecimal  b2 = new BigDecimal("97");
	     	   System.out.println(b1.remainder(b2)) ;
	     	   
	     	  if(b1.remainder(b2).intValue() != 0){
	    		   
	     		 
					
					
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"RIB erroné!",""));
					
					return;
	     		   
	     	   }
	        }
	        
	        ModePaiementHayya modePaiementHayya = new ModePaiementHayya();
	       // modePaiementHayya.setFrac(Integer.parseInt(h7ayyaController.getFraction()));
	        modePaiementHayya.setFrac(h7ayyaController.getFrac());
	        modePaiementHayya.setModep(Integer.parseInt(h7ayyaController.getModepaie()));
	        modePaiementHayya.setBanque(h7ayyaController.getBanque());
	        modePaiementHayya.setAgence(h7ayyaController.getAgence());
	        modePaiementHayya.setRib(h7ayyaController.getRib());
	        modePaiementHayya.setPayeur(Integer.parseInt(h7ayyaController.getPayeur()));
	        modePaiementHayya.setNom(h7ayyaController.getNomPayeur());
	        modePaiementHayya.setCin(h7ayyaController.getCinPayeur());
	        modePaiementHayya.setAdresse(h7ayyaController.getAdressePayeur());
	        modePaiementHayya.setDisposition(h7ayyaController.getDispoSpec());
			
			Adhesion adhesion = hayyaServiceInter.createAdhesion(tarif7ayyaBean.getNewHayyaDTO().getProposition(),
					h7ayyaController.getNewAdhesion(), tarif7ayyaBean.getNewHayyaDTO().getDetailHayya(),
			tarif7ayyaBean.getNewHayyaDTO().getDetailsGarantieHayyas(), 
			tarif7ayyaBean.getNewHayyaDTO().getDetailsEvolutionGarantieHayyas(),
			personnes, Integer.parseInt(h7ayyaController.getVie()),
			personnes_deces, Integer.parseInt(h7ayyaController.getDeces()),
			modePaiementHayya, tarif7ayyaBean.getNewHayyaDTO().getBilanFinancierHayya());
			
			num_adhesion = adhesion.getNumeroAdhesion();
			
			

			
			Double primetot; 
			Double primenette;
			
			Calendar cal = Calendar.getInstance();
			
			Date dateBefore01Days;
			
			switch(h7ayyaController.getFrac()){
			   
		       case 1: 
					cal.setTime(adhesion.getDatDebut());
					cal.add(Calendar.MONTH, 12);
					cal.add(Calendar.DATE, -1);
					 dateBefore01Days = cal.getTime();
					
		    	   primetot = tarif7ayyaBean.getNewProposition().getPrimetot();
		    	   primenette = tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue();
					    	 
		           break;
		   
		       case 2:
		    	   cal.setTime(adhesion.getDatDebut());
				    cal.add(Calendar.MONTH, 6);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();
				    
		    	   primetot =  tarif7ayyaBean.getNewDetailHayya().getTotal().doubleValue();
		    	   primenette =  tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue();
//		    	   primetot = tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*2 + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue();
//		    	   primenette= tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*2;

		           break;
		   
		       case 3:
		    	   cal.setTime(adhesion.getDatDebut());
				    cal.add(Calendar.MONTH, 3);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();
				    
		    	   primetot =  tarif7ayyaBean.getNewDetailHayya().getTotal().doubleValue();
		    	   primenette =  tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue();
//		    	   primetot=tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*4 + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue();
//		    	   primenette= tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*4;
				   
		           break;
		           
		       case 4:
		    	   cal.setTime(adhesion.getDatDebut());
				    cal.add(Calendar.MONTH, 1);
				    cal.add(Calendar.DATE, -1);
				     dateBefore01Days = cal.getTime();
				    
		    	   primetot =  tarif7ayyaBean.getNewDetailHayya().getTotal().doubleValue();
		    	   primenette =  tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue();
//		    	   primetot= tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*12 + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue();
//		    	   primenette= tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*12;

		           break;
		
		       default:
		    	   cal.setTime(adhesion.getDatDebut());
					cal.add(Calendar.DATE, -1);
					 dateBefore01Days = cal.getTime();
					
		    	   primetot =  tarif7ayyaBean.getNewDetailHayya().getTotal().doubleValue();
		    	   primenette =  tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue();
//		    	   primetot= tarif7ayyaBean.getNewProposition().getPrimetot();
//		    	   primenette= tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue();
				   

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
			
//			QuittanceDetails qt = new QuittanceDetails(
//					utilisateur.getAgence(), "C", 
//					null, h7ayyaController.getFrac(), 
//					primetot, primenette);
//			
//			quittanceDetailsDAO.createQuittanceDetails(qt, quittance);
			
			
			QuittanceDetails qt = new QuittanceDetails(
					quittanceDAO.searchquit(Integer.parseInt(numquit)).getIdQuittance(),
					adhesion,
			utilisateur.getAgence(), "C", 
			null, h7ayyaController.getFrac(), 
			primetot, primenette);
			
			quittanceDetailsDAO.create(qt);

			adhesionToPrintNotise = adhesion;
			
			mnt_capital = String.valueOf(tarif7ayyaBean.getNewDetailsGarantieHayyaList().get(0).getCapital().doubleValue() );
			
			mnt_total = String.valueOf(primetot );
			
			mnt_nette = String.valueOf(primenette );
			
			
			
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Adhésion enregistrée avec le numéro:", 
					adhesion.getNumeroAdhesion()+"!"));
			
			
			h7ayyaController.setDisabledValidationAdhesion(true);
			
			if (!utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000")){
				
				h7ayyaController.setTestImpression(true);
				
				
			}
			else {
				h7ayyaController.setTestImpression(false);
			}
			
			System.out.println("DisabledValidationAdhesion ==== " + h7ayyaController.isDisabledValidationAdhesion());
			
			//System.out.println("testqnb ==== " + testImpression);
		
		
		}
		
		
		}catch (Exception e) {

			
     		
     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec de validation du contrat , veuiller contacter l'administrateur!", ""));
     		
     		//h7ayyaController.setDisabledValidationAdhesion(false);
     		h7ayyaController.setDisabledall(true);
     		
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

				h7ayyaController.setDisabledValidationAdhesion(false);
				return;
			}
			

			if ( response.getStatus() != 200) {
				System.out.println("probleme de connexion");
				FacesMessage msg = new FacesMessage("Erreur serveur (code ="+response.getStatus()+"), veuiller contacter l'administrateur.", "ERREUR");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);

				h7ayyaController.setDisabledValidationAdhesion(false);
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

					h7ayyaController.setDisabledValidationAdhesion(false);


				}else{

					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					System.out.println("Encaissement effectuée avec succès.");
					h7ayyaController.setDisabledValidationAdhesion(true);
					setTest(true);
					/**** enregistremnt ds notre !BD ****/

					adhesionToPrintNotise.setEtat("V");

					adhesionDAO.edit(adhesionToPrintNotise);
					h7ayyaController.setDisabledValidationAdhesion(true);
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

		
//		ValiderProposition();
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
					
					FacesMessage msg = new FacesMessage("ERREUR", "Echec d'encaissement: pas de réponse au serveur!");
					  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					  
					  //h7ayyaController.setDisabledValidationAdhesion(false);
					  h7ayyaController.setDisabledall(true);
					  return;
					
				}else {
					
					if(token.equalsIgnoreCase("exception")) {
						
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , veuiller contacter l'administrateur!", ""));
			     		
			     		//h7ayyaController.setDisabledValidationAdhesion(false);
						h7ayyaController.setDisabledall(true);
			     		
			     		System.out.println("probleme de Exception  ");
			     		
			     		return;
					}
					
					if(token.equalsIgnoreCase("not200")) {
						
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , problème de droit d'accès!", ""));
			     		
			     		//h7ayyaController.setDisabledValidationAdhesion(false);
						h7ayyaController.setDisabledall(true);
			     		
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
			String Police = adhesionToPrintNotise.getPolice().getNPolice();//adhesionToPrintNotise.getNadhesion();
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
					FacesMessage msg = new FacesMessage("Echec d'encaissement: pas de réponse au serveur!", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

					//h7ayyaController.setDisabledValidationAdhesion(false);
					h7ayyaController.setDisabledall(true);
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

					//h7ayyaController.setDisabledValidationAdhesion(false);
					h7ayyaController.setDisabledall(true);
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
					
					h7ayyaController.setDisabledall(true);
                   
					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					System.out.println("Encaissement effectuée avec succès.");
					//h7ayyaController.setDisabledValidationAdhesion(true);
					/**** enregistremnt ds notre !BD ****/

					adhesionToPrintNotise.setEtat("V");

					adhesionDAO.edit(adhesionToPrintNotise);
					//h7ayyaController.setDisabledValidationAdhesion(true);
					OperationTransaction operationTransaction = new OperationTransaction();

					operationTransaction.setDateTrans(new Date());
					operationTransaction.setIdAdhesion(adhesionToPrintNotise); // à discuter
					operationTransaction.setRefOperation(Reference);
					operationTransaction.setTypTrans("Encaissement");

					iOperationTransactionDAO.create(operationTransaction);
					
					h7ayyaController.setTestImpression(true);


				}else {
					
					 System.out.println("Echec d'encaissement");
						FacesMessage msg = new FacesMessage("Echec d'encaissement : " + Status + " " + RejectMessage, "ERREUR");
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);

						//h7ayyaController.setDisabledValidationAdhesion(false);
						h7ayyaController.setDisabledall(true);
						return;
					
				}
			       
			        
			    	  
				
				} 
			catch (ClientHandlerException e) {

				
	     		
	     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement: problème d'accès au serveur!", ""));
	     		
	     		//h7ayyaController.setDisabledValidationAdhesion(false);
	     		h7ayyaController.setDisabledall(true);
	     		
	     		System.out.println("probleme de ClientHandlerException  ");
	     		
				e.printStackTrace();

			  }
			
	catch (org.json.JSONException e) {

				
	     		
	     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , erreur au niveau du retour du WS d'encaissement!", ""));
	     		
	     		//h7ayyaController.setDisabledValidationAdhesion(false);
	     		//setDesabledEncaiss(true); 
	     		h7ayyaController.setDisabledall(true);
	     		
	     		System.out.println("probleme de JSONException  ");
	     		
				e.printStackTrace();

			  }
			catch (Exception e) {

					
		     		
		     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , veuiller contacter l'administrateur!", ""));
		     		
		     		//h7ayyaController.setDisabledValidationAdhesion(false);
		     		h7ayyaController.setDisabledall(true);
		     		
		     		System.out.println("probleme de Exception  ");
		     		
					e.printStackTrace();

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
					h7ayyaController.setDisabledValidationAdhesion(true);
					/**** enregistremnt ds notre !BD ****/

					adhesionToPrintNotise.setEtat("V");

					adhesionDAO.edit(adhesionToPrintNotise);
					h7ayyaController.setDisabledValidationAdhesion(true);
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

		String path = reportController.getRealPath()
				+ "Contrat_hayaa_v4.jasper";

		try {

			// fonction d'impression du devis
			Map<String, Object> parameters = new HashMap();

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			parameters.put("agence", utilisateur.getAgence().getCodeAgence());
			parameters.put("contrat",num_adhesion);
			parameters.put("effet", df.format(tarif7ayyaBean.getNewProposition().getDatDebut()));
			parameters.put("duree", Integer.toString((int)tarif7ayyaBean.getNewProposition().getDuree())+" ans");
			parameters.put("echeance", df.format(tarif7ayyaBean.getNewProposition().getDatFin()));
			parameters.put("capital", tarif7ayyaBean.getNewDetailsGarantieHayyaList().get(0).getCapital());
			parameters.put("souscr", tarif7ayyaBean.getClientBean().getClient().getNomClient()+" "+tarif7ayyaBean.getClientBean().getClient().getPrenomClient());
			parameters.put("adr_souscr", tarif7ayyaBean.getClientBean().getClient().getAdrClient());
			parameters.put("id_souscr", tarif7ayyaBean.getClientBean().getClient().getIdentifiant());
			parameters.put("assure", tarif7ayyaBean.getClientBean().getClient().getNomClient()+" "+tarif7ayyaBean.getClientBean().getClient().getPrenomClient());
			parameters.put("adr_assure", tarif7ayyaBean.getClientBean().getClient().getAdrClient());
			parameters.put("id_assure", tarif7ayyaBean.getClientBean().getClient().getIdentifiant());
			parameters.put("naiss_assure", df.format(tarif7ayyaBean.getClientBean().getClient().getDatNaiss()));
			
			parameters.put("rib", tarif7ayyaBean.getClientBean().getClient().getNcompte());
			
			parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
			
			if(tarif7ayyaBean.getClientBean().getClient().getSexe().equalsIgnoreCase("M"))
				parameters.put("sexe_assure", "Homme");
			else
				parameters.put("sexe_assure", "Femme");
			
			String LibelleProfession = " ";
 	   		if (tarif7ayyaBean.getClientBean().getClient().getProfession() != null ) {
 	   			  // doSomething
 	   		LibelleProfession = tarif7ayyaBean.getClientBean().getClient().getProfession().getLibelleProfession();
 	   			}
			
			parameters.put("prf_assure", LibelleProfession);
			parameters.put("tel_assure", tarif7ayyaBean.getClientBean().getClient().getTel());
			parameters.put("nom_benef", tarif7ayyaBean.getClientBean().getClient().getNomClient());
			parameters.put("cin_benef", tarif7ayyaBean.getClientBean().getClient().getIdentifiant());
			parameters.put("parente", null);
			parameters.put("poucent_cap", null);
			
			parameters.put("nom_benef_d", null);
			parameters.put("cin_benef_d", null);
			parameters.put("parente_d", null);
			parameters.put("poucent_cap_d", null);
			
			//parameters.put("prime_tot", tarif7ayyaBean.getNewProposition().getPrimetot());
			parameters.put("frais", new BigDecimal(0.000));
			parameters.put("fga", tarif7ayyaBean.getNewDetailHayya().getFa());
			//parameters.put("prime_nette", tarif7ayyaBean.getNewDetailHayya().getPnette());
			
			
			parameters.put("primeversee", tarif7ayyaBean.getBilanFinancierHayyaDTO().getPrimeversee());
			parameters.put("epargneprev", tarif7ayyaBean.getBilanFinancierHayyaDTO().getEpargneprev());
			parameters.put("gainfinancier", tarif7ayyaBean.getBilanFinancierHayyaDTO().getGainfinancier());
			parameters.put("econimpot", tarif7ayyaBean.getBilanFinancierHayyaDTO().getEconimpot());
			parameters.put("gaintotal", tarif7ayyaBean.getBilanFinancierHayyaDTO().getGaintotal());
			
			switch(h7ayyaController.getFrac()){
			   
		       case 1: 
		    	   parameters.put("prime_tot", tarif7ayyaBean.getNewProposition().getPrimetot());
				   parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
			
		    	   parameters.put("annuel", "X");
		           break;
		   
		       case 2:
		    	   parameters.put("prime_tot", tarif7ayyaBean.getNewDetailHayya().getTotal().doubleValue());
				   parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
//		    	   parameters.put("prime_tot", tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*2 + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue());
//					parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*2));
				   
		    	   parameters.put("semestriel", "X");
		           break;
		   
		       case 3:
		    	   parameters.put("prime_tot", tarif7ayyaBean.getNewDetailHayya().getTotal().doubleValue());
				   parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
//		    	   parameters.put("prime_tot", tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*4 + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue());
//					parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*4));
				   
		    	   parameters.put("trimestriel", "X");
		           break;
		           
		       case 4:
		    	   parameters.put("prime_tot", tarif7ayyaBean.getNewDetailHayya().getTotal().doubleValue());
				   parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
//		    	   parameters.put("prime_tot", tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*12 + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue());
//					parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()*12));

		    	   parameters.put("mensuel", "X");
		           break;
		
		       default:
		    	   parameters.put("prime_tot", tarif7ayyaBean.getNewDetailHayya().getTotal().doubleValue());
				   parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
//		    	   parameters.put("prime_tot", tarif7ayyaBean.getNewProposition().getPrimetot());
//					parameters.put("prime_nette", tarif7ayyaBean.getNewDetailHayya().getPnette());
				   
		    	   parameters.put("unique", "X");
		           break;
		   }
			
			parameters.put("evol_p", "0%");
			parameters.put("tx_benef", "80%");
			parameters.put("tx_interef_gar", "4%");
			
			parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
			
			parameters
			.put("star",  "star.png");
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
		        
		        List<DetailsEvolutionGarantie> orders = h7ayyaController.getDetailsEvolutionGaranties();

		        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(orders);

		        parameters.put("datasource", ds);
		        
		        
		        List<Person> personnes = new ArrayList<Person>();
		        
		        System.out.println("vie "+h7ayyaController.getVie());
		        
		        if(h7ayyaController.getVie().equals("1")) {//assure
		        	Person assure = new Person();
		        	assure.setNom(tarif7ayyaBean.getClientBean().getClient().getNomClient());
		        	assure.setPrenom(tarif7ayyaBean.getClientBean().getClient().getPrenomClient());
		        	assure.setCin(tarif7ayyaBean.getClientBean().getClient().getIdentifiant());
		        	assure.setLien("assuré");
		        	assure.setQuote(100);
		        	personnes = new ArrayList<Person>();
		        	personnes.add(assure);
		        	System.out.println("personnes "+tarif7ayyaBean.getClientBean().getClient().getNomClient());
		        	System.out.println("personnes "+personnes.size());
		        }
		        
		        if(h7ayyaController.getVie().equals("2")) {// enfants
		        	personnes = new ArrayList<Person>();
		        	personnes = h7ayyaController.enfants;
		        	System.out.println("personnes "+personnes.size());
		        }
		        
		        if(h7ayyaController.getVie().equals("3")) {// autres
		        	personnes = new ArrayList<Person>();
		        	personnes = h7ayyaController.autres;
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
		        
		        System.out.println("deces "+h7ayyaController.getDeces());
		        
		        if(h7ayyaController.getDeces().equals("1")) {//defaut
		        	personnes_deces = new ArrayList<Person>();
		        	personnes_deces = h7ayyaController.defauts;
		        }
		        
		        if(h7ayyaController.getDeces().equals("2")) {// enfants
		        	personnes_deces = new ArrayList<Person>();
		        	personnes_deces = h7ayyaController.enfantds;
		        	System.out.println("personnes "+personnes.size());
		        }
		        
		        if(h7ayyaController.getDeces().equals("3")) {// conjoint
		        	personnes_deces = new ArrayList<Person>();
		        	personnes_deces = h7ayyaController.conjoints;
		        	System.out.println("personnes "+personnes.size());
		        }
		        
		        if(h7ayyaController.getDeces().equals("4")) {// parents
		        	personnes_deces = new ArrayList<Person>();
		        	personnes_deces = h7ayyaController.parents;
		        	System.out.println("personnes "+personnes.size());
		        }
		        
		        if(h7ayyaController.getDeces().equals("5")) {// autres
		        	personnes_deces = new ArrayList<Person>();
		        	personnes_deces = h7ayyaController.autreds;
		        	System.out.println("personnes "+personnes.size());
		        }
			
		        
		        JRBeanCollectionDataSource beanColDataSourceDeces = new JRBeanCollectionDataSource(personnes_deces);
		        
		        parameters.put("datasdeces", beanColDataSourceDeces);
		        
		        
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
	
	
	public void imprimer_notice() {

		String path = reportController.getRealPath()
				+ "notice_7ayya.jasper";

		try {

			// fonction d'impression du devis
			Map<String, Object> parameters = new HashMap();

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			parameters.put("agence", utilisateur.getAgence().getCodeAgence());
			parameters.put("contrat",num_adhesion);
			parameters.put("effet", df.format(tarif7ayyaBean.getNewProposition().getDatDebut()));
			parameters.put("duree", Integer.toString((int)tarif7ayyaBean.getNewProposition().getDuree())+" ans");
			parameters.put("echeance", df.format(tarif7ayyaBean.getNewProposition().getDatFin()));
			parameters.put("capital", tarif7ayyaBean.getNewDetailsGarantieHayyaList().get(0).getCapital().doubleValue() );
			parameters.put("souscr", tarif7ayyaBean.getClientBean().getClient().getNomClient()+" "+tarif7ayyaBean.getClientBean().getClient().getPrenomClient());
			parameters.put("adr_souscr", tarif7ayyaBean.getClientBean().getClient().getAdrClient());
			parameters.put("id_souscr", tarif7ayyaBean.getClientBean().getClient().getIdentifiant());
			parameters.put("assure", tarif7ayyaBean.getClientBean().getClient().getNomClient()+" "+tarif7ayyaBean.getClientBean().getClient().getPrenomClient());
			parameters.put("adr_assure", tarif7ayyaBean.getClientBean().getClient().getAdrClient());
			parameters.put("id_assure", tarif7ayyaBean.getClientBean().getClient().getIdentifiant());
			parameters.put("naiss_assure", df.format(tarif7ayyaBean.getClientBean().getClient().getDatNaiss()));
			
			
			String LibelleProfession = " ";
 	   		if (tarif7ayyaBean.getClientBean().getClient().getProfession() != null ) {
 	   			  // doSomething
 	   		LibelleProfession = tarif7ayyaBean.getClientBean().getClient().getProfession().getLibelleProfession();
 	   			}
			
			parameters.put("prf_assure", LibelleProfession);
			parameters.put("tel_assure", tarif7ayyaBean.getClientBean().getClient().getTel());
			parameters.put("nom_benef", tarif7ayyaBean.getClientBean().getClient().getNomClient());
			parameters.put("cin_benef", tarif7ayyaBean.getClientBean().getClient().getIdentifiant());
	
			parameters.put("prenom_benef", tarif7ayyaBean.getClientBean().getClient().getPrenomClient());
			parameters.put("compte", tarif7ayyaBean.getClientBean().getClient().getNcompte());
			parameters.put("email", tarif7ayyaBean.getClientBean().getClient().getMail());
			parameters.put("cpt", tarif7ayyaBean.getClientBean().getClient().getCodeP());
			
			parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
			
			
			String nom_complet = utilisateur.getAgence().getSociete().getNomComplet();
			parameters.put("nom_complet", nom_complet.replace("<b>", "").replace("</b>", "") );
			
			
			
			
			Double primetot;
			Double primetot_pour_lettre;
			String primetot_lettre;
			
			
			switch(h7ayyaController.getFrac()){
			   
		       case 1: 
		    	   primetot = tarif7ayyaBean.getNewProposition().getPrimetot() ;
		    	   primetot_pour_lettre = tarif7ayyaBean.getNewProposition().getPrimetot() * 1000;
		    	   primetot_lettre = Nombre.getLettre(primetot_pour_lettre.longValue());
		    	   
		    	   parameters.put("prime_tot", primetot);
		    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
				   parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
				   parameters.put("mnt_lettre", primetot_lettre);
				   
		    	   parameters.put("annuel", "X");
		           break;
		   
		       case 2:
		    	   primetot = tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue() + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue();
		    	   primetot_pour_lettre = (tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue() + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue()) * 1000;
		    	   primetot_lettre = Nombre.getLettre(primetot_pour_lettre.longValue());
		    	   
		    	   parameters.put("prime_tot", primetot);
		    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
					parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
					parameters.put("mnt_lettre", primetot_lettre);
					
		    	   parameters.put("semestriel", "X");
		           break;
		   
		       case 3:
		    	   primetot = tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue() + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue();
		    	   primetot_pour_lettre = (tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue() + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue()) * 1000;
		    	   primetot_lettre = Nombre.getLettre(primetot_pour_lettre.longValue());
		    	   
		    	   parameters.put("prime_tot", primetot);
		    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
					parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
					parameters.put("mnt_lettre", primetot_lettre);
					
		    	   parameters.put("trimestriel", "X");
		           break;
		           
		       case 4:
		    	   primetot = tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue() + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue();
		    	   primetot_pour_lettre = (tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue() + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue()) * 1000;
		    	   primetot_lettre = Nombre.getLettre(primetot.longValue());
		    	   
		    	   parameters.put("prime_tot", primetot);
		    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
					parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
					parameters.put("mnt_lettre", primetot_lettre);
					
		    	   parameters.put("mensuel", "X");
		           break;
		
		       default:
		    	   primetot = tarif7ayyaBean.getNewProposition().getPrimetot();
		    	   primetot_pour_lettre = (tarif7ayyaBean.getNewProposition().getPrimetot()) * 1000;
		    	   primetot_lettre = Nombre.getLettre(primetot_pour_lettre.longValue());
		    	   
		    	   parameters.put("prime_tot", primetot);
		    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
					parameters.put("prime_nette", tarif7ayyaBean.getNewDetailHayya().getPnette());
					parameters.put("mnt_lettre", primetot_lettre);
				   
		    	   parameters.put("unique", "X");
		           break;
		   }
			
			parameters
			.put("star",  "star.png");
			
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
			parameters.put("effet", df.format(tarif7ayyaBean.getNewProposition().getDatDebut()));
			
			Calendar cal = Calendar.getInstance();
//			cal.setTime(tarif7ayyaBean.getNewProposition().getDatFin());
//			cal.add(Calendar.DATE, -1);
//			Date dateBefore01Days = cal.getTime();
//			parameters.put("au", df.format(dateBefore01Days));
			
			
			
			
			switch (h7ayyaController.getFrac()) {
				case 1:
				    System.out.println("Annuel");
					cal.setTime(tarif7ayyaBean.getNewProposition().getDatDebut());
					cal.add(Calendar.MONTH, 12);
					cal.add(Calendar.DATE, -1);
					Date dateBefore01Days = cal.getTime();
					parameters.put("au", df.format(dateBefore01Days));
					
					parameters.put("prime_tot", tarif7ayyaBean.getNewProposition().getPrimetot());
					parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
				    break;
				  case 2:
				    System.out.println("Semestriel");
				    cal.setTime(tarif7ayyaBean.getNewProposition().getDatDebut());
				    cal.add(Calendar.MONTH, 6);
				    cal.add(Calendar.DATE, -1);
				    Date dateBefore01Days2 = cal.getTime();
					parameters.put("au", df.format(dateBefore01Days2));
					
					parameters.put("prime_tot", tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue() + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
				    break;
				  case 3:
				    System.out.println("Trimestriel");
				    cal.setTime(tarif7ayyaBean.getNewProposition().getDatDebut());
				    cal.add(Calendar.MONTH, 3);
				    cal.add(Calendar.DATE, -1);
				    Date dateBefore01Days3 = cal.getTime();
					parameters.put("au", df.format(dateBefore01Days3));
					
					parameters.put("prime_tot", tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue() + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));
				    break;
				  case 4:
				    System.out.println("Mensuel");
				    System.out.println("Trimestriel");
				    cal.setTime(tarif7ayyaBean.getNewProposition().getDatDebut());
				    cal.add(Calendar.MONTH, 1);
				    cal.add(Calendar.DATE, -1);
				    Date dateBefore01Days4 = cal.getTime();
					parameters.put("au", df.format(dateBefore01Days4));
					
					parameters.put("prime_tot",  tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue() + tarif7ayyaBean.getNewDetailHayya().getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( tarif7ayyaBean.getNewDetailHayya().getPnette().doubleValue()));

				    break;
				  case 5:
				    System.out.println("PRIME UNIQUE");
					cal.setTime(tarif7ayyaBean.getNewProposition().getDatFin());
					cal.add(Calendar.DATE, -1);
					Date dateBefore01Days5 = cal.getTime();
					parameters.put("au", df.format(dateBefore01Days5));
					
					parameters.put("prime_tot", tarif7ayyaBean.getNewProposition().getPrimetot());
					parameters.put("prime_nette", tarif7ayyaBean.getNewDetailHayya().getPnette());
				    break;
				  default:  
					 System.out.println("default"); 
					 break;
			}
			
			
			parameters.put("echeance", df.format(tarif7ayyaBean.getNewProposition().getDatDebut()));
			
			parameters.put("souscr", tarif7ayyaBean.getClientBean().getClient().getNomClient()+" "+tarif7ayyaBean.getClientBean().getClient().getPrenomClient());
			parameters.put("adr_souscr", tarif7ayyaBean.getClientBean().getClient().getAdrClient());
			 
			parameters.put("assure", tarif7ayyaBean.getClientBean().getClient().getNomClient()+" "+tarif7ayyaBean.getClientBean().getClient().getPrenomClient());
			parameters.put("adr_assure", tarif7ayyaBean.getClientBean().getClient().getAdrClient());
			 
			 
			
			//parameters.put("prime_tot", tarif7ayyaBean.getNewProposition().getPrimetot());
			parameters.put("frais", new BigDecimal(0.000));
			parameters.put("fga", tarif7ayyaBean.getNewDetailHayya().getFa());
			//parameters.put("taxe", Double.parseDouble("0.000"));
			parameters.put("taxe", tarif7ayyaBean.getNewProposition().getTaxe());
			//parameters.put("prime_nette", tarif7ayyaBean.getNewDetailHayya().getPnette());
			
			
			 
			
			parameters
			.put("star",  "star.png");
			
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
	
	
	public void imprimer_impot(){
		
		try {
            String report = "";         
            Map<String, Object> parameters = new HashMap<String, Object>();
            
            switch(h7ayyaController.getFrac()){
            
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
				.put("star",  "star.png");
				
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
	
	
	

}
