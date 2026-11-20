package com.star.Ba.jsf;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dto.BilanFinancierHayyaDTO;
import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.DetailsGarantieDTO;
import com.star.Ba.dto.HayyaDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.BilanFinancierHayya;
import com.star.Ba.model.Client;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.HayyaServiceInter;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "tarif7ayyaBean")
@SessionScoped
public class TarifH7ayyaBean implements Serializable {
	
	
	
	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;
	
	@ManagedProperty("#{h7ayyaController}")
	private H7ayyaController h7ayyaController;

	@ManagedProperty("#{clientBean}")
	private ClientBean clientBean;
	
	@EJB
	private IClientDAO clientDAO;
	
	
	@EJB
	private HayyaServiceInter hayyaServiceInter;
	
	
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	
	
	private String echDate;
	
	private List<DetailsGarantieDTO> detailsGaranties;
	
	private List<DetailsEvolutionGarantie> detailsEvolutionGaranties;
	
	
	private BilanFinancierHayyaDTO bilanFinancierHayyaDTO;
	
	
	private BigDecimal pnette_annuel;
	private BigDecimal accessoires;
	private BigDecimal taxes;
	private BigDecimal total_annuel;
	
	private BigDecimal fga;
	
	private String vie;
	private String deces;
	
	public Proposition newProposition;
	public DetailHayya newDetailHayya;
	public List<DetailsGarantieHayya> newDetailsGarantieHayyaList;
	
	public HayyaDTO  newHayyaDTO;
	
	
	
	@PostConstruct
	public void init() {
		
		System.out.println("date echeance "+h7ayyaController.getEchDate());
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

	public H7ayyaController getH7ayyaController() {
		return h7ayyaController;
	}

	public void setH7ayyaController(H7ayyaController h7ayyaController) {
		this.h7ayyaController = h7ayyaController;
	}
	
	
	public Double getPrimeComptant() {
		
		Double mntt = h7ayyaController.getTotal().doubleValue();
//		DetailHayya newDetailHayya = h7ayyaController.getDetailHayya();
//		switch(h7ayyaController.getFrac()){
//		   
//	       case 1: 
//	    	   mntt =  h7ayyaController.getTotal().doubleValue();
//			   
//	           break;
//	   
//	       case 2:
//	    	   mntt = newDetailHayya.getPnette().doubleValue()*2 + newDetailHayya.getFa().doubleValue();
//				
//	           break;
//	   
//	       case 3:
//	    	   mntt = newDetailHayya.getPnette().doubleValue()*4 + newDetailHayya.getFa().doubleValue();
//				
//	           break;
//	           
//	       case 4:
//	    	   mntt = newDetailHayya.getPnette().doubleValue()*12 + newDetailHayya.getFa().doubleValue();
//				
//	           break;
//	
//	       default:
//	    	   mntt = h7ayyaController.getTotal().doubleValue();
//				
//	           break;
//	   }
		
		
		return  mntt;
		
	}
	
	
public int getNombrePeriodePourFract() {
		
		int mntt = h7ayyaController.getNbr_periode_pour_frac();
		
		
		return  mntt;
		
	}


	public void setEchDate(String echDate) {
		this.echDate = echDate;
	}
	
	
	public String getEchDate() {
		
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		return  formatter.format(h7ayyaController.getEchDate());
		
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
	
	
	
	


	public BilanFinancierHayyaDTO getBilanFinancierHayyaDTO() {
		return h7ayyaController.getBilanFinancierHayyaDTO();
	}


	public void setBilanFinancierHayyaDTO(BilanFinancierHayyaDTO bilanFinancierHayyaDTO) {
		this.bilanFinancierHayyaDTO = bilanFinancierHayyaDTO;
	}


	public BigDecimal getPnette_annuel() {
		return new BigDecimal(
				h7ayyaController.getPnette().doubleValue() * h7ayyaController.getNbr_periode_pour_frac()
				);
	}


	public void setPnette_annuel(BigDecimal pnette_annuel) {
		this.pnette_annuel = pnette_annuel;
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
	
	
	public BigDecimal getFga() {
		return h7ayyaController.getFga();
	}


	public void setFga(BigDecimal fga) {
		this.fga = fga;
	}
	


	public BigDecimal getTotal_annuel() {
		
		return new BigDecimal(
				h7ayyaController.getPnette().doubleValue() * h7ayyaController.getNbr_periode_pour_frac()
				+ h7ayyaController.getFga().doubleValue()
				);
	}


	public void setTotal_annuel(BigDecimal total_annuel) {
		this.total_annuel = total_annuel;
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
	
	
	public Proposition getNewProposition() {
		return newProposition;
	}

	public void setNewProposition(Proposition newProposition) {
		this.newProposition = newProposition;
	}
	
 

	public DetailHayya getNewDetailHayya() {
		return newDetailHayya;
	}


	public void setNewDetailHayya(DetailHayya newDetailHayya) {
		this.newDetailHayya = newDetailHayya;
	}
	
	
	
	
	public List<DetailsGarantieHayya> getNewDetailsGarantieHayyaList() {
		return newDetailsGarantieHayyaList;
	}


	public void setNewDetailsGarantieHayyaList(List<DetailsGarantieHayya> newDetailsGarantieHayyaList) {
		this.newDetailsGarantieHayyaList = newDetailsGarantieHayyaList;
	}


	public HayyaDTO getNewHayyaDTO() {
		return newHayyaDTO;
	}


	public void setNewHayyaDTO(HayyaDTO newHayyaDTO) {
		this.newHayyaDTO = newHayyaDTO;
	}
	
	
	public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}
	
	
	
	// fonctions
	








	public String returnToDevis7ayya(){
		
		return "devis_7ayya";
	}
	
	
	public String validerProposition() {
		
		return "contrat7ayya?faces-redirect=true";
	}
	
	
	public void generateDevis7ayya() {
		
		Adhesion adhesion = h7ayyaController.getNewAdhesion();
		Proposition proposition = h7ayyaController.getNewProposition();
		DetailHayya detailHayya = h7ayyaController.getDetailHayya();
		List<DetailsGarantieHayya> detailsGarantieHayyas  = h7ayyaController.getDetailsGarantieHayyas();
		List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas = h7ayyaController.getDetailsEvolutionGarantieHayyas();
		
		BilanFinancierHayya bilanFinancierHayya = h7ayyaController.getBilanFinancierHayya();
		
		Client client = proposition.getClient();
		
		if(clientDAO.findByIdentifiant(client.getIdentifiant())!=null)
		{
			System.out.println("client existant");
			client.setIdClient( clientDAO.findByIdentifiant(client.getIdentifiant()).getIdClient()); 
			proposition.setClient(client);

		}
		
		newHayyaDTO = hayyaServiceInter.createProposition(proposition, detailHayya, 
				detailsGarantieHayyas, detailsEvolutionGarantieHayyas, bilanFinancierHayya);
		
		newProposition = newHayyaDTO.getProposition();
		
		newDetailHayya = h7ayyaController.getDetailHayya();
		
		newDetailsGarantieHayyaList = h7ayyaController.getDetailsGarantieHayyas();
		//newDetailHayya = hayyaServiceInter.createDetailHayya(detailHayya, newProposition);
		
//		JsfUtil.addSuccessMessage(
//				"Proposition enregistrée avec le numéro: "
//						+ newProposition.getnProposition(), "!");
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		
		
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Proposition enregistrée avec le numéro:", 
				newProposition.getnProposition()+"!"));

		
		System.out.println("proposition "+proposition.getPolice().getNPolice());
		
		h7ayyaController.setDisabledGenerateDevis(false);
		
		//return "contrat7ayya?faces-redirect=true";
	}
	
	
	public String suiteContrat7ayya() {
		
		return "contrat7ayya?faces-redirect=true";
		
	}
	
	
	public void generateDevis() {
		
		
		String path = reportController.getRealPath()
				+ "Proposition_hayaa_v8.jasper";

		try {

			// fonction d'impression du devis
			Map<String, Object> parameters = new HashMap();
			
			
			List<DetailsGarantieHayya> detailsGarantieHayyas  = h7ayyaController.getDetailsGarantieHayyas();
			
			BilanFinancierHayya bilanFinancierHayya = h7ayyaController.getBilanFinancierHayya();
			
		
				  BigDecimal epargne = detailsGarantieHayyas.get(0).getCapital();
				  BigDecimal deces = detailsGarantieHayyas.get(1).getCapital();
				  BigDecimal iad = detailsGarantieHayyas.get(2).getCapital();
				
			

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			parameters.put("agence", utilisateur.getAgence().getCodeAgence());
			parameters.put("contrat",newProposition.getnProposition());
			parameters.put("effet", df.format(newProposition.getDatDebut()));
			parameters.put("duree", Integer.toString((int)newProposition.getDuree())+" ans");
			parameters.put("echeance", df.format(newProposition.getDatFin()));
			parameters.put("capital", newDetailsGarantieHayyaList.get(0).getCapital());
			parameters.put("souscr", clientBean.getClient().getNomClient()+" "+clientBean.getClient().getPrenomClient());
			parameters.put("adr_souscr", clientBean.getClient().getAdrClient());
			parameters.put("id_souscr", clientBean.getClient().getIdentifiant());
			parameters.put("assure", clientBean.getClient().getNomClient()+" "+clientBean.getClient().getPrenomClient());
			parameters.put("adr_assure", clientBean.getClient().getAdrClient());
			parameters.put("id_assure", clientBean.getClient().getIdentifiant());
			parameters.put("naiss_assure", df.format(clientBean.getClient().getDatNaiss()));
			
			if(clientBean.getClient().getSexe().equalsIgnoreCase("M"))
				parameters.put("sexe_assure", "Homme");
			else
				parameters.put("sexe_assure", "Femme");
			
			String LibelleProfession = " ";
 	   		if (clientBean.getClient().getProfession() != null ) {
 	   			  // doSomething
 	   		LibelleProfession = clientBean.getClient().getProfession().getLibelleProfession();
 	   			}
			
			parameters.put("prf_assure", LibelleProfession);
			parameters.put("tel_assure", clientBean.getClient().getTel());
			parameters.put("nom_benef", clientBean.getClient().getNomClient());
			parameters.put("cin_benef", clientBean.getClient().getIdentifiant());
			
			parameters.put("epargne", epargne);
			parameters.put("deces", deces);
			parameters.put("iad", iad);
			
			parameters.put("nom_benef_d", null);
			parameters.put("cin_benef_d", null);
			parameters.put("parente_d", null);
			parameters.put("poucent_cap_d", null);
			
			//parameters.put("prime_tot", newProposition.getPrimetot());
			parameters.put("frais", new BigDecimal(0.000));
			parameters.put("fga", newDetailHayya.getFa());
			//parameters.put("taxe", Double.parseDouble("0.000"));
			parameters.put("taxe", newProposition.getTaxe());
			//parameters.put("prime_nette", newDetailHayya.getPnette());
			
		
			
			parameters.put("primeversee", bilanFinancierHayya.getPrimeversee());
			parameters.put("epargneprev", bilanFinancierHayya.getEpargneprev());
			parameters.put("gainfinancier", bilanFinancierHayya.getGainfinancier());
			parameters.put("econimpot", bilanFinancierHayya.getEconimpot());
			parameters.put("gaintotal", bilanFinancierHayya.getGaintotal());
			
			
			switch(h7ayyaController.getFrac()){
			   
		       case 1: 
		    	   parameters.put("prime_tot", newProposition.getPrimetot());
				   parameters.put("prime_nette", newDetailHayya.getPnette());
			
		    	   parameters.put("annuel", "X");
		           break;
		   
		       case 2:
		    	   parameters.put("prime_tot", newDetailHayya.getPnette().doubleValue()*2 + newDetailHayya.getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( newDetailHayya.getPnette().doubleValue()*2));
				   
		    	   parameters.put("semestriel", "X");
		           break;
		   
		       case 3:
		    	   parameters.put("prime_tot", newDetailHayya.getPnette().doubleValue()*4 + newDetailHayya.getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( newDetailHayya.getPnette().doubleValue()*4));
				   
		    	   parameters.put("trimestriel", "X");
		           break;
		           
		       case 4:
		    	   parameters.put("prime_tot", newDetailHayya.getPnette().doubleValue()*12 + newDetailHayya.getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( newDetailHayya.getPnette().doubleValue()*12));

		    	   parameters.put("mensuel", "X");
		           break;
		
		       default:
		    	   parameters.put("prime_tot", newProposition.getPrimetot());
					parameters.put("prime_nette", newDetailHayya.getPnette());
					
		    	   parameters.put("unique", "X");
		           break;
		   }
			
			parameters.put("evol_p", "0%");
			parameters.put("tx_benef", "80%");
			parameters.put("tx_interef_gar", "4%");
			
			parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
			
			parameters
			.put("star",  "star.png");
			
			parameters.put("imagesociete", utilisateur.getAgence().getSociete()
					.getCodSociet() + ".png");
			
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

}
