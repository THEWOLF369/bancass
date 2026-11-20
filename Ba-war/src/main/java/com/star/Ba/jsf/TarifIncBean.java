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

import org.hibernate.validator.constraints.Length;
import org.json.JSONObject;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.star.Ba.commun.AgeCalculator;
import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dao.IOperationTransactionDAO;
import com.star.Ba.dao.IPropositionDAO;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.jsf.util.Proprietes;
import com.star.Ba.jsf.util.TokenSTB;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.GarantieVie;
import com.star.Ba.model.OperationTransaction;
import com.star.Ba.model.Police;
import com.star.Ba.model.ProfessionClient;
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
@ManagedBean(name = "tarifIncBean")
@SessionScoped
public class TarifIncBean implements Serializable {

    private double primeglobal;
 
    @ManagedProperty("#{devisIncendieBean}")
    private DevisIncendieBean devisIncendieBean;
    @ManagedProperty("#{reportController}")
	private ReportController reportController;
    @EJB
   	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;
    @EJB
    private IAdhesionDAO adhesionDAO;
    @EJB
    private IClientDAO clientDAO;
    @EJB
    private IPropositionDAO propositionDAO;
	@EJB
    private IOperationTransactionDAO iOperationTransactionDAO;
    private double taxe;
    private double primetot;
    private boolean valid; 
    public TarifIncBean() {

    }
    @PostConstruct
	public void init() {
    	valid=true;
    }
    
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public double getTaxe() {
		return devisIncendieBean.getTaxe();
	}

	public void setTaxe(double taxe) {
		this.taxe = taxe;
	}

	public double getPrimetot() {
		return devisIncendieBean.getPrimetot();
	}

	public void setPrimetot(double primetot) {
		this.primetot = primetot;
	}

	public TarifIncBean(double primeglobal) {

        this.primeglobal = primeglobal;
    }

    public double getPrimeglobal() {
    	return devisIncendieBean.getPrimeglobal();
    }

    public void setPrimeglobal(float primeglobal) {
        this.primeglobal = primeglobal;
    }

    public ReportController getReportController() {
		return reportController;
	}
	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}
	Proposition ncdProposition;
	
	
	
	public Proposition getNcdProposition() {
		return ncdProposition;
	}
	public void setNcdProposition(Proposition ncdProposition) {
		this.ncdProposition = ncdProposition;
	}
	
	 Adhesion newAdhesionToPrint;
	    
	    
	   
	public Adhesion getNewAdhesionToPrint() {
		return newAdhesionToPrint;
	}
	public void setNewAdhesionToPrint(Adhesion newAdhesionToPrint) {
		this.newAdhesionToPrint = newAdhesionToPrint;
	}
	public void generateDevis() {
        // msg err
        if (devisIncendieBean.getClient().getIdentifiant() == null
                || devisIncendieBean.getClient().getIdentifiant().isEmpty()) {
            FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
        } else if(devisIncendieBean.getAdhesion().getAdresse()==null || devisIncendieBean.getAdhesion().getAdresse().isEmpty()|| devisIncendieBean.getAdhesion().getLocalite()==null ||
        		devisIncendieBean.getAdhesion().getLocalite().isEmpty()||
        		devisIncendieBean.getAdhesion().getGouver()==null || 
        		devisIncendieBean.getAdhesion().getGouver().isEmpty()
        		|| devisIncendieBean.getSelectedLocgouv()==null ){
        	
        	FacesMessage msg = new FacesMessage("ERREUR", "Veuillez saisir l'adresse, gouvernorat et code postale du risque.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
            
        }
        else{
            Client client = devisIncendieBean.getClient();
            if (client.getDatCreation() == null) {
                client.setDatCreation(new Date());
            }
            
            if(clientDAO.findByIdentifiant(client.getIdentifiant())!=null)
            {
            	System.out.println("client existant");
            	client.setIdClient( clientDAO.findByIdentifiant(client.getIdentifiant()).getIdClient()); 
            	devisIncendieBean.getAdhesion().setClient(client);
            	clientDAO.edit(client);
                
            }
            else
            {
            	System.out.println("client inexistant");
                   clientDAO.create(client);
                        
            }
            /*if(ncdProposition==null){
            	System.out.println("enter to insert ncdProposition");
            	 ncdProposition =  serviceAssistantVoiyagetService.createProposition(new Proposition(devisIncendieBean.getAdhesion(),"a"));
            }*/
           
            System.out.println("enter to insert ncdProposition");
       	 ncdProposition =  serviceAssistantVoiyagetService.createProposition(new Proposition(devisIncendieBean.getAdhesion(),"a"));
                
                
                	devisIncendieBean.imprimerDevisInc(devisIncendieBean.getAdhesion().getPolice().getNPolice(),
                	devisIncendieBean.getClient().getIdentifiant(),
                	devisIncendieBean.getAdhesion().getDatDebut(),
                	devisIncendieBean.getAdhesion().getDuree(),
                	devisIncendieBean.getAdhesion().getDatFin(),
                	devisIncendieBean.getAdhesion().getCapital(),
                	devisIncendieBean.getAdhesion().getMtPrime(),
                	devisIncendieBean.getClient().getNomClient(),
                	devisIncendieBean.getClient().getPrenomClient(),
                	devisIncendieBean.getAdhesion().getTaxe(),
                	devisIncendieBean.getAdhesion().getPrimetot(),"",
                	ncdProposition.getNumeroProposition());
                JsfUtil.addSuccessMessage(" La Proposition est enregistrée");
    }
    }

    public void validerProposition() {
    	
    	
    	
    	System.out.println("tessst ayaaa client avant if dans validation="+devisIncendieBean.getClient());
    	
        if (devisIncendieBean.getClient().getIdentifiant() == null
                || devisIncendieBean.getClient().getIdentifiant().isEmpty()) {
        	
        	
        	
        	System.out.println("Client inexistant ");
            FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
            noneException=false;
        }
        else if(devisIncendieBean.getAdhesion().getAdresse()==null || devisIncendieBean.getAdhesion().getAdresse().isEmpty()
        		|| devisIncendieBean.getAdhesion().getLocalite()==null || devisIncendieBean.getAdhesion().getLocalite().isEmpty()
        		|| devisIncendieBean.getAdhesion().getGouver()==null || devisIncendieBean.getAdhesion().getGouver().isEmpty()
        		|| devisIncendieBean.getSelectedLocgouv()==null )
        {
        	
        	
        	FacesMessage msg = new FacesMessage("ERREUR", "Veuillez saisir l'adresse, gouvernorat et code postale du risque.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
            noneException=false;
        }
        
//        else if(devisIncendieBean.getNadhesion().isEmpty())
//        {
//        	noneException=false;
//        	FacesMessage msg = new FacesMessage("ERREUR", "Le numéro d'adhesion est obligatoire pour valider la proposition");
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
//        }
        else
        {
        	System.out.println("Client existant ");
        	
        	

            Client client = devisIncendieBean.getClient();
            
            System.out.println("12222222222222222 == "+client);


            if (client.getDatCreation() == null) {
                client.setDatCreation(new Date());
            }
if(clientDAO.findByIdentifiant(client.getIdentifiant())!=null)
{
	
	client.setIdClient( clientDAO.findByIdentifiant(client.getIdentifiant()).getIdClient()); 
	devisIncendieBean.getAdhesion().setClient(client);
	clientDAO.edit(client);
    
}
else
{
	System.out.println("client inexistant");
            clientDAO.create(client);
            //devisBean.getAdhesion().setClient(client);
            
}

   if(ncdProposition!=null){
	   devisIncendieBean.getAdhesion().setProposition(ncdProposition); 
   }

   
   newAdhesionToPrint=serviceAssistantVoiyagetService.createAdhesion(devisIncendieBean.getAdhesion());
   
   System.out.println("kacemmmm========"+newAdhesionToPrint);
   noneException=true;
   if(!devisIncendieBean.getCod_societe().equals("002707PPM000")){
   devisIncendieBean.setDesabledGenerateNoticeIncendie(false);
   }
       //     adhesionDAO.create(devisIncendieBean.getAdhesion());
   JsfUtil.addSuccessMessage("Adhésion Validée avec le numéro: "+newAdhesionToPrint.getNadhesion(),"!");
   
//   devisIncendieBean.Status =0;
//   devisIncendieBean.validate = 1;

            //Proposition proposition = new Proposition(devisBean.getAdhesion());
            //propositionDAO.create(proposition);
        }
    }
    boolean noneException;
    public String returnToDevisInced() {
		
		if(noneException&&newAdhesionToPrint!=null){
			newAdhesionToPrint=null;
			ncdProposition=null;
			
			devisIncendieBean.newDevis();
			devisIncendieBean.setClient(new Client());
			devisIncendieBean.setAdhesion(new Adhesion());
			devisIncendieBean.getAllCodePostalByGouvernera().clear();
			devisIncendieBean.setDesabledGenerateNoticeIncendie(true);
			devisIncendieBean.setDesabledEncaiss(true);
		}
		return "devisIncendie";
	}
    public void generateNotice() {
        if (newAdhesionToPrint == null || newAdhesionToPrint.getIdAdhesion() == 0) {
        	noneException=false;
            JsfUtil.addErrorMessage("La proposition doit etre validée!");
            return;
        }
        print(newAdhesionToPrint,devisIncendieBean.getCod_societe()+".png",devisIncendieBean.getUtilisateur().getAgence().getCodeAgence(),devisIncendieBean.getUtilisateur().getAgence().getLibelle());
    }

    public void generateFDR() {
        imprimerFdrInc(devisIncendieBean.getAdhesion(),devisIncendieBean.getCod_societe()+".png","");
        setValid(false);
    }    

    public DevisIncendieBean getDevisIncendieBean() {
		return devisIncendieBean;
	}

	public void setDevisIncendieBean(DevisIncendieBean devisIncendieBean) {
		this.devisIncendieBean = devisIncendieBean;
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
    
    
    public void print(Adhesion adhesion, String Cod_societe,String codagc,String ville_agc) {
        try {
        	 DecimalFormat formatter = new DecimalFormat("#0.000");
            String report = reportController.getRealPath() + "notice _inc.jasper";         
            Map<String, Object> parameters = new HashMap<String, Object>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            parameters.put("numeroPolice", adhesion.getPolice().getNPolice());
            parameters.put("contractant", adhesion.getPolice().getConvention()
                    .getSociete().getRaisonSoc());
            parameters.put("adresse", adhesion.getPolice().getConvention()
                    .getSociete().getAdresse());
            parameters.put("nomclt", adhesion.getClient().getNomClient());
            parameters.put("prenomclt",adhesion.getClient().getPrenomClient());
            parameters.put("identifiant",String.valueOf(adhesion.getClient().getIdentifiant()));
            parameters.put("telclt",adhesion.getClient().getTel());
            parameters.put("mailclt",adhesion.getClient().getMail());
            parameters.put("adrclt", adhesion.getClient().getNumAdr()+" "+adhesion.getClient().getTypeVoie()+" "+adhesion.getClient().getAdrClient());
            parameters.put("vilclt", adhesion.getClient().getLocalite());
            parameters.put("cpclt", adhesion.getClient().getCodeP());
            parameters.put("gouvclt", adhesion.getClient().getGouver());
            parameters.put("adrrisque", adhesion.getAdresse());
            parameters.put("vilrisque", adhesion.getLocalite());
            parameters.put("gouvrisque", adhesion.getGouver());
            parameters.put("cprisque", adhesion.getCodeP());
            parameters.put("dateEffet", sdf.format(adhesion.getDatDebut()));
            parameters.put("dateFin", sdf.format(adhesion.getDatFin()));
            
            parameters.put("codagc", codagc);
            parameters.put("nadhesion", adhesion.getNadhesion());
            parameters.put("imagesociete",Cod_societe);
            parameters.put("star","star.png");
            parameters.put("capital", String.valueOf(adhesion.getCapital()));
            parameters.put("mtPrime", formatter.format(adhesion.getMtPrime()));
            parameters.put("taxe", formatter.format(adhesion.getTaxe()));
            parameters.put("primtot", formatter.format(adhesion.getPrimetot()));
           // parameters.put("villeagence", ville_agc);
            parameters.put("villeagence", devisIncendieBean.getUtilisateur().getAgence().getLibelle());

            
            String duree="";
            String[]tab=String.valueOf(adhesion.getDuree()).split("\\.");
   		
            if(!tab[1].equals("0")){
            	 duree=tab[0]+"(a)"+","+ tab[1]+ "(m)";
            } else{
            	 duree=tab[0]+"(année)";
            }
           	
           
           
            parameters.put("duree", duree);
            
            
//            int nbmois;
//        	int nbannee;
//        	System.out.println("durée="+adhesion.getDuree());
//            nbannee = (int) adhesion.getDuree();
//            System.out.println("nbannee="+nbannee);
//			//double mois= (adhesion.getDuree()- nbannee);
//            BigDecimal mois1=new BigDecimal(adhesion.getDuree()- nbannee).multiply(new BigDecimal(100));
//            System.out.println("mois1="+mois1);
//            double mois=(adhesion.getDuree()- nbannee)*100;
//			System.out.println("mois="+mois);
//            nbmois=(int)mois+(nbannee*12);
//            System.out.println("nbmois="+nbmois);
            
            
            
            JasperPrint print = JasperFillManager.fillReport(report,
                    parameters, new JREmptyDataSource());
            byte[] bytes = JasperExportManager.exportReportToPdf(print);

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseContentLength(bytes.length);
            ec.setResponseHeader("Content-Disposition",
                    "inline; filename=notice _inc.pdf");

            OutputStream output = ec.getResponseOutputStream();
            output.write(bytes);
            fc.responseComplete();

            adhesion.setImpr(true);
            adhesionDAO.edit(adhesion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    public void imprimerFdrInc(Adhesion adhesion, String Cod_societe, String ville_agc) {
    	
    	String path = reportController.getRealPath() + "fdr_inc.jasper";
    	
    	try {

    		// fonction d'impression du devis 
            Map<String, Object> parameters = new HashMap();

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            parameters.put("societe", adhesion.getPolice().getConvention()
                    .getSociete().getRaisonSoc());
            parameters.put("nomclt", adhesion.getClient().getNomClient());
            parameters.put("prenomclt",adhesion.getClient().getPrenomClient());
            parameters.put("identifiant",String.valueOf(adhesion.getClient().getIdentifiant()));
            parameters.put("telclt",adhesion.getClient().getTel());
            parameters.put("mailclt",adhesion.getClient().getMail());
            parameters.put("adrclt", adhesion.getClient().getNumAdr()+" "+adhesion.getClient().getTypeVoie()+" "+adhesion.getClient().getAdrClient());
            parameters.put("vilclt", adhesion.getClient().getLocalite());
            parameters.put("cpclt", adhesion.getClient().getCodeP());
            parameters.put("gouvclt", adhesion.getClient().getGouver());
            parameters.put("adrrisque", adhesion.getAdresse());
            parameters.put("vilrisque", adhesion.getLocalite());
            parameters.put("gouvrisque", adhesion.getGouver());
            parameters.put("cprisque", adhesion.getCodeP());
            parameters.put("dateEffet", df.format(adhesion.getDatDebut()));
            parameters.put("datefin", df.format(adhesion.getDatFin()));
            parameters.put("duree", String.valueOf(adhesion.getDuree()));
            parameters.put("codagc", devisIncendieBean.getUtilisateur().getAgence().getCodeAgence());
            parameters.put("imagesociete",Cod_societe);
            parameters.put("star","star.png");
            parameters.put("capital", String.valueOf(adhesion.getCapital()));
           // parameters.put("villeagence", ville_agc);
            // a tester
                
            parameters.put("villeagence", devisIncendieBean.getUtilisateur().getAgence().getLibelle());

    		JasperPrint print = JasperFillManager.fillReport(path,
    				parameters, new JREmptyDataSource());
    		byte[] bytes = JasperExportManager.exportReportToPdf(print);

    		FacesContext fc = FacesContext.getCurrentInstance();
    		ExternalContext ec = fc.getExternalContext();
    		ec.responseReset();
    		ec.setResponseContentType("application/pdf");
    		ec.setResponseContentLength(bytes.length);
    		ec.setResponseHeader("Content-Disposition", "inline; filename=\""
    				+  "FDR_inc.pdf" + "\"");

    		OutputStream output = ec.getResponseOutputStream();
    		output.write(bytes);
    		fc.responseComplete();

    		 
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	 
    	
    	
    	
    }



    
    
    public void imprimerFdrInc___(Adhesion adhesion, String Cod_societe, String ville_agc) {

        // fonction d'impression du devis 
        Map<String, Object> parameters = new HashMap();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        parameters.put("societe", adhesion.getPolice().getConvention()
                .getSociete().getRaisonSoc());
        parameters.put("nomclt", adhesion.getClient().getNomClient());
        parameters.put("prenomclt",adhesion.getClient().getPrenomClient());
        parameters.put("identifiant",String.valueOf(adhesion.getClient().getIdentifiant()));
        parameters.put("telclt",adhesion.getClient().getTel());
        parameters.put("mailclt",adhesion.getClient().getMail());
        parameters.put("adrclt", adhesion.getClient().getNumAdr()+" "+adhesion.getClient().getTypeVoie()+" "+adhesion.getClient().getAdrClient());
        parameters.put("vilclt", adhesion.getClient().getLocalite());
        parameters.put("cpclt", adhesion.getClient().getCodeP());
        parameters.put("gouvclt", adhesion.getClient().getGouver());
        parameters.put("adrrisque", adhesion.getAdresse());
        parameters.put("vilrisque", adhesion.getLocalite());
        parameters.put("gouvrisque", adhesion.getGouver());
        parameters.put("cprisque", adhesion.getCodeP());
        parameters.put("dateEffet", df.format(adhesion.getDatDebut()));
        parameters.put("datefin", df.format(adhesion.getDatFin()));
        parameters.put("duree", String.valueOf(adhesion.getDuree()));
        parameters.put("codagc", devisIncendieBean.getUtilisateur().getAgence().getCodeAgence());
        parameters.put("imagesociete",Cod_societe);
        parameters.put("star","star.png");
        parameters.put("capital", String.valueOf(adhesion.getCapital()));
        //parameters.put("villeagence", ville_agc);
        parameters.put("villeagence", devisIncendieBean.getUtilisateur().getAgence().getLibelle());


        try {
            String path = reportController.getRealPath() + "fdr_inc.jasper";
            // this.getClass().getResource("closed.jasper").getPath() 
            JasperPrint jp = JasperFillManager.fillReport(path, parameters,
                    new JREmptyDataSource());

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
                    .getCurrentInstance().getExternalContext()
                    .getResponse();

            httpServletResponse.addHeader("Content-disposition",
                    "inline; filename=FDR_inc.pdf");
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
    
    
    
    
    

	
	public void encaissement_ancien() throws NoSuchAlgorithmException, KeyManagementException{
		
		devisIncendieBean.setDesabledEncaiss(false); 
		validerProposition();
		
		if(/*devisIncendieBean.validate ==0*/ newAdhesionToPrint == null || newAdhesionToPrint.getIdAdhesion() == 0 )return;

		System.out.println("ayyyyyyyyyyyyyyyyyyyyyyyyyaaaaaaaaaa"+newAdhesionToPrint);
		System.out.println("ayyyyyyyyyyyyyyyyyyyyyyyyyaaaaaaaaaa"+newAdhesionToPrint.getEtat());

		
		newAdhesionToPrint.setEtat("N");
		adhesionDAO.edit(newAdhesionToPrint); 
		
		
			 
	
		if(devisIncendieBean.getClient().getNcompte() == null || devisIncendieBean.getClient().getNcompte().equalsIgnoreCase("")){
			
			FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			
		}
		
		else if (devisIncendieBean.getClient().getIdentifiant() == null
	                || devisIncendieBean.getClient().getIdentifiant().isEmpty()) {
	            FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
	            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
	        }
		
		else if( devisIncendieBean.getAdhesion().getLocalite()==null ||
	        		devisIncendieBean.getAdhesion().getLocalite().isEmpty()||
	        		devisIncendieBean.getAdhesion().getGouver()==null || 
	        		devisIncendieBean.getAdhesion().getGouver().isEmpty()
	        		|| devisIncendieBean.getSelectedLocgouv()==null ){
	        	
	        	FacesMessage msg = new FacesMessage("ERREUR", "Veuillez saisir l'adresse, gouvernorat et code postale du risque.");
	            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
	        }
        
				
		else{
			
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
                 "    \"ribPayeur\":\""+devisIncendieBean.getClient().getNcompte()+"\",\n" +
                 "\n" +
                 "    \"ribBeneficiaire\":\"\",\n" +
                 "\n" +
                 "    \"montant\":\""+getPrimetot()+"\",\n" +
                 "\n" +
                 "    \"description\": \"Incendie : "+newAdhesionToPrint.getNadhesion()+"\",\n" +
                 "\n" +
                 "    \"typeOpeCode\":\"OCCA20002\"\n" +
                 "\n" +
                 "}";
		 
		 System.out.println("first input \n" +input);
		 
	      ExampleResourceClient erc = new ExampleResourceClient("",BASE_URI);
	      //System.out.println(erc.getXml());
	      
	      
	      ClientResponse response = erc.HttpReponsePost(input);
	      
	      if (response.getStatus() != 200) {
	     	 System.out.println("probleme de connexion");
	     	System.out.println("problem with input \n" +input);
	     	FacesMessage msg = new FacesMessage("Erreur serveur (code ="+response.getStatus()+"), veuiller contacter l'administrateur.", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			devisIncendieBean.setDesabledGenerateNoticeIncendie(true);
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
				
				devisIncendieBean.setDesabledGenerateNoticeIncendie(true);
				
			}else{
				
				FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				System.out.println("Encaissement effectuée avec succès.");
				
                 /**** enregistremnt ds notre !BD ****/
				
                newAdhesionToPrint.setEtat("V");
				newAdhesionToPrint.setRef_credit(devisIncendieBean.getRefCredit());
				adhesionDAO.edit(newAdhesionToPrint);
				devisIncendieBean.setDesabledGenerateNoticeIncendie(false);
				
				OperationTransaction operationTransaction = new OperationTransaction();
				
				operationTransaction.setDateTrans(new Date());
				operationTransaction.setIdAdhesion(newAdhesionToPrint); // à discuter
				operationTransaction.setRefOperation(obj.getString("Reference"));
				operationTransaction.setTypTrans("Encaissement");
				
				iOperationTransactionDAO.create(operationTransaction);
				//devisIncendieBean.Status = 1;
				
				
				
			}
			
			 
			 
	    	
	    }
	      
	      
	}
	
	}
	
	
	
public void encaissement() throws NoSuchAlgorithmException, KeyManagementException{
		
		devisIncendieBean.setDesabledEncaiss(false); 
		validerProposition();
		
		if(/*devisIncendieBean.validate ==0*/ newAdhesionToPrint == null || newAdhesionToPrint.getIdAdhesion() == 0 )return;

		System.out.println("ayyyyyyyyyyyyyyyyyyyyyyyyyaaaaaaaaaa"+newAdhesionToPrint);
		System.out.println("ayyyyyyyyyyyyyyyyyyyyyyyyyaaaaaaaaaa"+newAdhesionToPrint.getEtat());

		
		newAdhesionToPrint.setEtat("N");
		adhesionDAO.edit(newAdhesionToPrint); 
		
		
			 
	
		if(devisIncendieBean.getClient().getNcompte() == null || devisIncendieBean.getClient().getNcompte().equalsIgnoreCase("")){
			
			FacesMessage msg = new FacesMessage("Veuillez saisir le n° de compte.", "ERREUR");
			msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			
		}
		
		else if (devisIncendieBean.getClient().getIdentifiant() == null
	                || devisIncendieBean.getClient().getIdentifiant().isEmpty()) {
	            FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
	            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
	        }
		
		else if( devisIncendieBean.getAdhesion().getLocalite()==null ||
	        		devisIncendieBean.getAdhesion().getLocalite().isEmpty()||
	        		devisIncendieBean.getAdhesion().getGouver()==null || 
	        		devisIncendieBean.getAdhesion().getGouver().isEmpty()
	        		|| devisIncendieBean.getSelectedLocgouv()==null ){
	        	
	        	FacesMessage msg = new FacesMessage("ERREUR", "Veuillez saisir l'adresse, gouvernorat et code postale du risque.");
	            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
	        }
        
				
		else{
			
		 
			
			
			
			String token = TokenSTB.tokenStb(reportController);
			
			if(token == null) {
				
				FacesMessage msg = new FacesMessage("ERREUR", "Echec d'encaissement: pas de réponse au WS Token!");
				  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				  FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				  
				  return;
				
			}else {
				
				if(token.equalsIgnoreCase("exception")) {
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , Erreur de connexion au WS Token!", ""));
		     		
		     		
		     		System.out.println("probleme de Exception  ");
		     		
		     		return;
				}
				
				if(token.equalsIgnoreCase("not200")) {
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement , problème de droit d'accès au WS Token!", ""));
		     		
		     		
		     		System.out.println("probleme de Exception  ");
		     		
		     		return;
				}
			}
			
			
		 
		 
		 
		 //final String BASE_URI = Proprietes.URL_ENCAISS_STB;
		 final String BASE_URI = Constante.URL_ENCAISS_STB;
		 
		 
		    String IdDemande = String.valueOf( newAdhesionToPrint.getIdAdhesion() );
			
			String PrimeAssurance = String.valueOf(getPrimetot());
			String AccountNumber = devisIncendieBean.getClient().getNcompte();
			String Police = newAdhesionToPrint.getPolice().getNPolice();//newAdhesionToPrint.getNadhesion();
			String Cin = devisIncendieBean.getClient().getIdentifiant();
			String Nom = devisIncendieBean.getClient().getNomClient(); 
			String Prenom = devisIncendieBean.getClient().getPrenomClient();
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			String DateNaissance = df.format(devisIncendieBean.getClient().getDatNaiss());
			
			String CapitalAssure = "0";
			
			String DateEffet = df.format(newAdhesionToPrint.getDatDebut());
			String Datefin = df.format(newAdhesionToPrint.getDatFin());
			
			String Mobile = devisIncendieBean.getClient().getTel();
			String Mail = devisIncendieBean.getClient().getMail();
			
			String Produit = Constante.PRODUIT_INC;
			
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
		 

		 
		 System.out.println("first input \n" +input);
		 
			try {
				
	    
			    com.sun.jersey.api.client.Client client = JerseyClient.ApiClient(reportController);
			    ClientResponse response = JerseyClient.PostReponseDuClientSTBEncaissement(BASE_URI, token, input, client, reportController);
			   
			    

				if ( response == null) {
					System.out.println("probleme de connexion vide");
					FacesMessage msg = new FacesMessage("Echec d'encaissement!", "Pas de réponse au WS d'encaissement!");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);

					devisIncendieBean.setDesabledGenerateNoticeIncendie(true);
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

					devisIncendieBean.setDesabledGenerateNoticeIncendie(true);
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
                   
					FacesMessage msg = new FacesMessage("Encaissement effectuée avec succès.", "");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					System.out.println("Encaissement effectuée avec succès.");
					
					/**** enregistremnt ds notre !BD ****/

					newAdhesionToPrint.setEtat("V");
					newAdhesionToPrint.setRef_credit(devisIncendieBean.getRefCredit());
					adhesionDAO.edit(newAdhesionToPrint);
					devisIncendieBean.setDesabledGenerateNoticeIncendie(false);
					
					OperationTransaction operationTransaction = new OperationTransaction();
					
					operationTransaction.setDateTrans(new Date());
					operationTransaction.setIdAdhesion(newAdhesionToPrint); // à discuter
					operationTransaction.setRefOperation(Reference);
					operationTransaction.setTypTrans("Encaissement");

					iOperationTransactionDAO.create(operationTransaction);


				}else {
					
					 System.out.println("Echec d'encaissement");
						FacesMessage msg = new FacesMessage("Echec d'encaissement : " + Status + " " + RejectMessage, "Erreur WS d'encaissement!");
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage("Erreur", msg);

						devisIncendieBean.setDesabledGenerateNoticeIncendie(true);
						return;
					
				}
			       
			        
			    	  
				
				} 
			catch (ClientHandlerException e) {

				
	     		
	     		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Echec d'encaissement: problème d'appel au WS d'encaissement!", ""));
	     		
	     		devisIncendieBean.setDesabledGenerateNoticeIncendie(true);
	     		
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
		     		
		     		devisIncendieBean.setDesabledGenerateNoticeIncendie(true);
		     		
		     		System.out.println("probleme de Exception  ");
		     		
					e.printStackTrace();

				  }
		 

	      
	      
	}
	
	}
    
}
