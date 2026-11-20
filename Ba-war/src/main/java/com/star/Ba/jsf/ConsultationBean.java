package com.star.Ba.jsf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.star.Ba.dao.AvenirJeunesseDAOInter;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IAgenceDAO;
import com.star.Ba.dao.IChartDAO;
import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.IProdconvDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IProfilDAO;
import com.star.Ba.dao.IPropositionDAO;
import com.star.Ba.dao.IQuittanceDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.DetailsEvolutionGarantieAvjDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.Nombre;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agence;
import com.star.Ba.model.BenefHayyaDeces;
import com.star.Ba.model.BenefHayyaVie;
import com.star.Ba.model.BilanFinancierHayya;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieAVJ;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.GarantieAvj;
import com.star.Ba.model.GarantieMrh;
import com.star.Ba.model.GarantieVie;
import com.star.Ba.model.Police;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.TarifMrh;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.mrh.pack.OpGar;
import com.star.Ba.service.AssistantVoiyagetServiceInter;
import com.star.Ba.service.HayyaServiceInter;
import com.star.Ba.service.IParametresMrhService;

/**
 * @author 
 *
 */
@ManagedBean(name = "consultationBean")
@ViewScoped
public class ConsultationBean implements Serializable {

    final static String PDF = "";

    private static final long serialVersionUID = 1L;

    @EJB
    private IAdhesionDAO adhesionDAO;
    @EJB
    private ISocieteDAO societeDAO;
    @EJB
    private IPropositionDAO propositionDAO;
    
    @EJB
    private IConventionDAO conventionDAO;
    @EJB
    private IProdconvDAO prodconvDAO;
    @EJB
    private IUtilisateursDAO utilisateursDAO;
    @EJB
    private IAgenceDAO agenceDAO;
    
    @EJB
    private IPoliceDAO policeDAO;
    
    @EJB
    private IClientDAO clientDAO;
    
    @EJB
    private IProduitDAO produitDAO;
  
    
    @EJB
	private IParametresMrhService parametresMrhService;
    
    @EJB
   	private IProfilDAO iProfilDAO;
    
	@EJB
	private IQuittanceDAO iQuittanceDAO;
    
    @ManagedProperty(value = "#{login.utilisateur}")
    private Utilisateurs utilisateur;
    @ManagedProperty("#{reportController}")
	private ReportController reportController;
    
    @ManagedProperty("#{gestionProfil}")
	private GestionProfil gestionProfil ;
    
    
    
    @EJB
	private HayyaServiceInter hayyaServiceInter;
    
    @EJB
	private AvenirJeunesseDAOInter avenirJeunesseDAOInter;



	private List<Adhesion> adhesions = new ArrayList<Adhesion>();
    private List<Adhesion> lstadhs;
    private List<Proposition> propositions = new ArrayList<Proposition>();
    private List<Proposition> lstprops;
    private List<Client> clients = new ArrayList<Client>();
    private List<Prodconv> lstproduit;
    private boolean affiche = true;
    private Map<String, Object> listSociete=null;
	private String codeSociete;
	
	private String codeProduit;
	
	  private Date to;
	  private Date from;
	  private String dateDu,dateAu;
	  private String nadhesion,cin;
	  
	private String [] userSociete={PofileEnum.G.name(),PofileEnum.AA.name(),PofileEnum.AS.name(),PofileEnum.BNAA.name(),PofileEnum.BNA.name()};
	 
	private List<Produit> produits=new ArrayList<Produit>();
	
	@EJB
	private IChartDAO iChartDAO;
	
	
	// pour attestation d'impot
	private Adhesion selectedAdhesion;
	private boolean valider_impot = false;
	private Date dateImpot = null;
	private Date dateImpot_init = null;
	
	private Date dateImpotDebut = null;
	private Date dateImpotFin = null;
	
	
	private static final String[] MOIS = {
		    "", "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
		    "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
		};
	
    
    public Map<String, Object> getListSociete() {
		return listSociete;
	}

	public void setListSociete(Map<String, Object> listSociete) {
		this.listSociete = listSociete;
	}

	public String getCodeSociete() {
		return codeSociete;
	}

	public void setCodeSociete(String codeSociete) {
		this.codeSociete = codeSociete;
	}
	
	
	

	public String getCodeProduit() {
		return codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}
	
	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	
	
	public GestionProfil getGestionProfil() {
		return gestionProfil;
	}

	public void setGestionProfil(GestionProfil gestionProfil) {
		this.gestionProfil = gestionProfil;
	}

	@PostConstruct
    public void init() {
		
		/*** societe****/
		 listSociete = iChartDAO.recupererListeSociete();
//		 codeSociete = (String) listSociete.values().toArray()[0];
		
//		  System.out.println("le_codeSociete :"+codeSociete);
		  
		
		  Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		  Date date = new Date();
		  System.out.println(formatter.format(date));
			 dateDu = formatter.format(date);
			 dateAu = formatter.format(date);
			
			from = date;
			to = date;
		
//    	clients=clientDAO.findAll();

			if(Arrays.asList(userSociete).contains(utilisateur.getProfil().getCode())){
				Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
				lstproduit=prodconvDAO.getlstProduit_by_convention(c);
				for (Prodconv produit : lstproduit) {
					produits.add(produit.getProduit());
				}
				
			}
			else if(PofileEnum.MSTA.name().equalsIgnoreCase(utilisateur.getProfil().getCode())){
				
				produits=produitDAO.findByUser(utilisateur);
			}
			
			
			else {
				
				 produits=produitDAO.findAll();
				
				
			}
		
			
    	
        
        if(utilisateur.getProfil().getCode().equals(PofileEnum.G.name())|| utilisateur.getProfil().getCode().equals(PofileEnum.BNAA.name()))
        {
          //  System.out.println("enter adhesionDAO.findByUtilisateur");

                adhesions = adhesionDAO.findByUtilisateurAndDate(utilisateur,dateDu,dateAu, nadhesion, cin);
           //     System.out.println("");
                propositions = propositionDAO.findByUtilisateurAndDate(utilisateur,dateDu,dateAu, nadhesion, cin);
                

                
                
                		
                /****** cas des TD de STAR pour STB ***/
                
                if(gestionProfil.getAllCodeProduitAutoriseToUser().contains("601"))
                    
                {
                List<Police> listPolice =null;
                listPolice = policeDAO.getPolice_by_conv_prod(conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete()),
                		produitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode()));
                
                List<Utilisateurs> listStar = utilisateursDAO.find_utilisateurs_by_profil(iProfilDAO.find_profil_by_code(PofileEnum.TDST.name())); //TDST
                
               
                propositions.addAll(propositionDAO.findPropositionByStarForSTB(listPolice,listStar, dateDu, dateAu, nadhesion, cin))	;
                }
                
             
                
                List<Utilisateurs> listeUtilisateurs = new ArrayList<Utilisateurs>();    
                listeUtilisateurs.add(utilisateur);
                clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateur(listeUtilisateurs));
                clients.addAll(propositionDAO.findClientFromPropositionByUtilisateur(listeUtilisateurs));
        
        }
        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AA.name()))
        {
        	 List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(utilisateur.getAgence());
        //	 System.out.println("***liste utilisateurs="+listeUtilisateurs);
        	 //List<Client> listWithDuplicates = new ArrayList<>();
             for (Utilisateurs utilisateurs : listeUtilisateurs){
            	// System.out.println("***utilisateur="+utilisateurs);
            	 lstadhs=adhesionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
            	 //System.out.println("***adhesions="+adhesions);
            	 lstprops=propositionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
                 adhesions.addAll(lstadhs);
                 propositions.addAll(lstprops);
                 
                 
                 
                
                 //listWithDuplicates.addAll(adhesionDAO.findClientFromAdhesionByUtilisateur(utilisateurs));
                 
                
                 
                 
                /* list =adhesionDAO.findClientFromAdhesionByUtilisateur(utilisateurs);
                 
                 for(Client client :list){
                 	
                 	System.out.println("client "+client.getIdClient());
	
                     if (!clients.contains(client))
                    	 clients.add(client); 
                     else clients.remove(client);
                 }*/
               
             }
             
           /*  for (Proposition proposition : lstprops){
             
            	 if (adhesionDAO.propexiste(proposition).isEmpty()) {
         			affiche = true;
         			System.out.println("affiche 1 ===================="+ affiche);
         		}
         		else {
         			
         			
         			affiche = false;
         			System.out.println("affiche 2 ===================="+ affiche);
         		}
            	 
            	 
             }*/
            	 
             
             
             /****** cas des TD de STAR pour STB ***/
             if(gestionProfil.getAllCodeProduitAutoriseToUser().contains("601"))
            
             {
             List<Police> listPolice =null;
             listPolice = policeDAO.getPolice_by_conv_prod(conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete()),
             		produitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode()));
             
             List<Utilisateurs> listStar = utilisateursDAO.find_utilisateurs_by_profil(iProfilDAO.find_profil_by_code(PofileEnum.TDST.name())); //TDST
             System.out.println("listStar="+listStar);
            System.out.println("listepolice="+listPolice);
            
             propositions.addAll(propositionDAO.findPropositionByStarForSTB(listPolice,listStar, dateDu, dateAu, nadhesion, cin))	;
             
             }
             clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateur(listeUtilisateurs));
             clients.addAll(propositionDAO.findClientFromPropositionByUtilisateur(listeUtilisateurs));
            // System.out.println("size 1 "+clients.size());

        }
        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AS.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNA.name()))
        {
        	
        	
        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(utilisateur.getAgence().getSociete());
        	
        	//List<Client> listWithDuplicates =  new ArrayList<Client>();
        	 List<Utilisateurs> listeAllUsers = new ArrayList<Utilisateurs>();
        	
        	for (Agence agence : listeAgences){
           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
//            	 System.out.println("***utilisateur="+utilisateurs);
            	 lstadhs=adhesionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
            	 //System.out.println("***adhesions="+adhesions);
            	 lstprops=propositionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
                 adhesions.addAll(lstadhs);
                 propositions.addAll(lstprops);
                 
//                 for(Adhesion adhesion : adhesions){
//                  	if (!clients.contains(adhesion.getClient()))
//                  	clients.add(adhesion.getClient());	
//                  }
             }
           	 
           	listeAllUsers.addAll(listeUtilisateurs);
           	 
           	

            }
//        	adhesions = adhesionDAO.findAll();
//        	propositions = propositionDAO.findAll();
        	
        	
        	clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateur(listeAllUsers));
        	clients.addAll(propositionDAO.findClientFromPropositionByUtilisateur(listeAllUsers));
        	/*List<Client> listWithoutDuplicates=  new ArrayList<>(new HashSet<>(listWithDuplicates));
        	 System.out.println("size 0 "+listWithoutDuplicates.size());
        	clients = listWithoutDuplicates;*/
            // System.out.println("size 1 "+clients.size());
        	
        }
        else if(/*utilisateur.getProfil().getCode().equals("AST")||*/utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name()) /*|| PofileEnum.ASI.getChildrensASI().contains(utilisateur.getProfil().getCode())*/)
        {
        	
        	System.out.println("cas star admin et il faut faire recherche par clic bouton seulement");  
 /*       	
        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(societeDAO.find(codeSociete));
        	
        	List<Utilisateurs> listeAllUsers = new ArrayList<Utilisateurs>();
        	
        	for (Agence agence : listeAgences){
           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
//            	 System.out.println("***utilisateur="+utilisateurs);
            	 lstadhs=adhesionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
            	 //System.out.println("***adhesions="+adhesions);
            	 lstprops=propositionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
                 adhesions.addAll(lstadhs);
                 propositions.addAll(lstprops);
                 
//                 for(Adhesion adhesion : adhesions){
//                  	if (!clients.contains(adhesion.getClient()))
//                  	clients.add(adhesion.getClient());	
//                  }


             }
         	listeAllUsers.addAll(listeUtilisateurs);
            }
        	clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateur(listeAllUsers));
        	clients.addAll(propositionDAO.findClientFromPropositionByUtilisateur(listeAllUsers));
        	
        	*/
        }
        else if(utilisateur.getProfil().getCode().equals(PofileEnum.MSTA.name()) )
        {
        	
        	System.out.println("cas metier STAR  et il faut faire recherche par clic bouton seulement");        	 
         
        }
        else {
        	
        	System.out.println("cas metier TD et il faut faire recherche par clic bouton seulement");  
        	/*
        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(societeDAO.find(codeSociete));
        	System.out.println("listeAgences="+listeAgences);
        	List<Utilisateurs> listeAllUsers = new ArrayList<Utilisateurs>();
//        	System.out.println("listeAllUsers="+listeAllUsers);
        	Produit produitTD= produitDAO.find(Long.valueOf(PofileEnum.ASI.getProduit(utilisateur.getProfil().getCode())));
        	System.out.println("produitTD="+produitTD);
             List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
             System.out.println("listePoliceTD="+listePoliceTD);
        	for (Agence agence : listeAgences){
           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
//           	    System.out.println("listeUtilisateurs="+listeUtilisateurs);
           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
            	 for (Police policeTD : listePoliceTD) {
            		 lstadhs=adhesionDAO.findByPoliceAndDate(utilisateurs,policeTD,dateDu,dateAu, nadhesion, cin);
            		 lstprops=propositionDAO.findByPoliceAndDate(utilisateurs,policeTD,dateDu,dateAu, nadhesion, cin);
            		 adhesions.addAll(lstadhs);
                     propositions.addAll(lstprops);
//                     System.out.println("lstprops="+lstprops);
//                     System.out.println("lstprops="+lstprops );
        	        }
             }
           	 
         	listeAllUsers.addAll(listeUtilisateurs);

            }
//        	System.out.println("listeAllUsers="+listeAllUsers);
//        	System.out.println("listePoliceTD="+listePoliceTD);
        	clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateurAndPolice(listeAllUsers,listePoliceTD));
        	clients.addAll(propositionDAO.findClientFromPropositionByUtilisateurAndPolice(listeAllUsers,listePoliceTD));
       	
       	*/
        }
      
    }
	
	  public void Chercher(){
		  
		  System.out.println("debut recherche");
		  
		  dateDu = "";
			 dateAu = "";
		  
		  
			 if(utilisateur.getProfil().getCode().equals(PofileEnum.BNAA.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNA.name())) {
				 nadhesion = "houssemxxx";
				 cin = "houssemxxx";
				 nadhesion = "houssemxxx";
			 }
			 
			  if(from == null && to == null  && cin.toString().isEmpty() && nadhesion.toString().isEmpty()) {
				  
					 
				  
				  FacesMessage msg = new FacesMessage("Veuillez saisir une date et/ou n° contrat et/ou un identifiant", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					return;
			  }
			  
			  
				 if(utilisateur.getProfil().getCode().equals(PofileEnum.BNAA.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNA.name())) {
					 nadhesion = null;
					 cin = null;
					 nadhesion = null;
				 }
					  
		  
		  clients = new ArrayList<Client>();
		  
		  if(from != null && to != null) {
			  
		  Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			 dateDu = formatter.format(from);
			 dateAu = formatter.format(to);
			 
		  }
			 
			 adhesions = new ArrayList<Adhesion>(); 
			 propositions = new ArrayList<Proposition>();
			 
			
		
		  
		  if(utilisateur.getProfil().getCode().equals(PofileEnum.G.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNAA.name()) )
	        {
	            System.out.println("consultationBean fnction rechercher pour gestionnaire");
	            
	            System.out.println("consultationBean 1");

	                adhesions = adhesionDAO.findByUtilisateurAndDate(utilisateur,dateDu,dateAu, nadhesion, cin);
	          //      System.out.println("adhesions "+adhesions.size());
	                propositions = propositionDAO.findByUtilisateurAndDate(utilisateur,dateDu,dateAu, nadhesion, cin);
	                
	                
	                /****** cas des TD de STAR pour STB ***/
	                if(gestionProfil.getAllCodeProduitAutoriseToUser().contains("601"))
	                    
	                {
	                List<Police> listPolice =null;
	                listPolice = policeDAO.getPolice_by_conv_prod(conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete()),
	                		produitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode()));
	                
	                List<Utilisateurs> listStar = utilisateursDAO.find_utilisateurs_by_profil(iProfilDAO.find_profil_by_code(PofileEnum.TDST.name())); //TDST
	                
	               
	                propositions.addAll(propositionDAO.findPropositionByStarForSTB(listPolice,listStar, dateDu, dateAu, nadhesion, cin));
	                }
	                
	                List<Utilisateurs> listeUtilisateurs = new ArrayList<Utilisateurs>();    
	                listeUtilisateurs.add(utilisateur);
	                clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateur(listeUtilisateurs));
	                clients.addAll(propositionDAO.findClientFromPropositionByUtilisateur(listeUtilisateurs));
	            //    System.out.println("size 1 "+clients.size());
	        
	        }
	        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AA.name()))
	        {
	        	 
	        	System.out.println("consultationBean 2");
	        	
	        	List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(utilisateur.getAgence());
	        	// System.out.println("***liste utilisateurs="+listeUtilisateurs);
	        	 //List<Client> listWithDuplicates = new ArrayList<>();
	             for (Utilisateurs utilisateurs : listeUtilisateurs){
	            //	 System.out.println("***utilisateur="+utilisateurs);
	            	 lstadhs=adhesionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
	            	 //System.out.println("***adhesions="+adhesions);
	            	 lstprops=propositionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
	                 adhesions.addAll(lstadhs);
	                 propositions.addAll(lstprops);
	                 
	                
	               
	               
	             }
	             
	             /****** cas des TD de STAR pour STB ***/
	             if(gestionProfil.getAllCodeProduitAutoriseToUser().contains("601"))
	                 
	             {
	                List<Police> listPolice =null;
	                listPolice = policeDAO.getPolice_by_conv_prod(conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete()),
	                		produitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode()));
	                
	                List<Utilisateurs> listStar = utilisateursDAO.find_utilisateurs_by_profil(iProfilDAO.find_profil_by_code(PofileEnum.TDST.name())); //TDST
	                
	               
	                propositions.addAll(propositionDAO.findPropositionByStarForSTB(listPolice,listStar, dateDu, dateAu, nadhesion, cin))	;
	             } 
	                
	             clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateur(listeUtilisateurs));
	             clients.addAll(propositionDAO.findClientFromPropositionByUtilisateur(listeUtilisateurs));
	          //   System.out.println("size 1 "+clients.size());
 
	        }
	        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AS.name()) || utilisateur.getProfil().getCode().equals(PofileEnum.BNA.name()))
	        {
	        	System.out.println("consultationBean 3");
	        	
	        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(utilisateur.getAgence().getSociete());
	        	
	        	//List<Client> listWithDuplicates =  new ArrayList<Client>();
	        	 List<Utilisateurs> listeAllUsers = new ArrayList<Utilisateurs>();
	        	
	        	for (Agence agence : listeAgences){
	           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
	           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
	            //	 System.out.println("***utilisateur="+utilisateurs);
	            	 lstadhs=adhesionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
	            	 //System.out.println("***adhesions="+adhesions);
	            	 lstprops=propositionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
	                 adhesions.addAll(lstadhs);
	                 propositions.addAll(lstprops);
	              //   System.out.println("***lstprops="+lstprops.size());
	             //    System.out.println("***adhesions="+adhesions.size());
//	                 for(Adhesion adhesion : adhesions){
//	                  	if (!clients.contains(adhesion.getClient()))
//	                  	clients.add(adhesion.getClient());	
//	                  }
	             }
	           	 
	           	listeAllUsers.addAll(listeUtilisateurs);
	           	 
	           	

	            }
//	        	adhesions = adhesionDAO.findAll();
//	        	propositions = propositionDAO.findAll();
	        	
	        	
	        	clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateur(listeAllUsers));
	        	clients.addAll(propositionDAO.findClientFromPropositionByUtilisateur(listeAllUsers));
	        	/*List<Client> listWithoutDuplicates=  new ArrayList<>(new HashSet<>(listWithDuplicates));
	        	 System.out.println("size 0 "+listWithoutDuplicates.size());
	        	clients = listWithoutDuplicates;*/
	          //   System.out.println("size 1 "+clients.size());
	        	
	        }
	        else if(/*utilisateur.getProfil().getCode().equals(PofileEnum.AST.name())||*/utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name())/*|| PofileEnum.ASI.getChildrensASI().contains(utilisateur.getProfil().getCode())*/)
	        {
	        	System.out.println("consultationBean 4");
	        	
	        	System.out.println("cas star admin recherche");
	        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(societeDAO.find(codeSociete));
	        	List<Utilisateurs> listeAllUsers = new ArrayList<Utilisateurs>();
	        	
	        	for (Agence agence : listeAgences){
	           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
	           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
	            	 lstadhs=adhesionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
	            	 lstprops=propositionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
	                 adhesions.addAll(lstadhs);
	                 propositions.addAll(lstprops);
	             }
	           	 
	         	listeAllUsers.addAll(listeUtilisateurs);

	            }
	        	
	        	clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateur(listeAllUsers));
	        	clients.addAll(propositionDAO.findClientFromPropositionByUtilisateur(listeAllUsers));
	        }
		  
	        else if(utilisateur.getProfil().getCode().equals(PofileEnum.MSTA.name()) )
	        {
	        	
	        	System.out.println("cas metier STAR");   

	        	
	        	if(codeProduit != null && !codeProduit.isEmpty()) {
	        		
	        		System.out.println("cas metier STAR par produit ");  
		        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(societeDAO.find(codeSociete));
		        	System.out.println("listeAgences="+listeAgences);
		        	List<Utilisateurs> listeAllUsers = new ArrayList<Utilisateurs>();
//		        	System.out.println("listeAllUsers="+listeAllUsers);
		        	Produit produitTD= produitDAO.find(Long.valueOf(codeProduit));
		        	System.out.println("produitTD="+produitTD);
		             List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
		             System.out.println("listePoliceTD="+listePoliceTD);
		        	for (Agence agence : listeAgences){
		           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
//		           	    System.out.println("listeUtilisateurs="+listeUtilisateurs);
		           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
		            	 for (Police policeTD : listePoliceTD) {
		            		 lstadhs=adhesionDAO.findByPoliceAndDate(utilisateurs,policeTD,dateDu,dateAu, nadhesion, cin);
		            		 lstprops=propositionDAO.findByPoliceAndDate(utilisateurs,policeTD,dateDu,dateAu, nadhesion, cin);
		            		 adhesions.addAll(lstadhs);
		                     propositions.addAll(lstprops);
//		                     System.out.println("lstprops="+lstprops);
//		                     System.out.println("lstprops="+lstprops );
		        	        }
		             }
		           	 
		         	listeAllUsers.addAll(listeUtilisateurs);

		            }
//		        	System.out.println("listeAllUsers="+listeAllUsers);
//		        	System.out.println("listePoliceTD="+listePoliceTD);
		        	clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateurAndPolice(listeAllUsers,listePoliceTD));
		        	clients.addAll(propositionDAO.findClientFromPropositionByUtilisateurAndPolice(listeAllUsers,listePoliceTD));

		        	
	        		
	        	}else {
	        		
	        		System.out.println("cas metier STAR tous ");  
		        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(societeDAO.find(codeSociete));
		        	List<Utilisateurs> listeAllUsers = new ArrayList<Utilisateurs>();
		        	
		        	for (Agence agence : listeAgences){
		           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
		           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
		            	 lstadhs=adhesionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
		            	 lstprops=propositionDAO.findByUtilisateurAndDate(utilisateurs,dateDu,dateAu, nadhesion, cin);
		                 adhesions.addAll(lstadhs);
		                 propositions.addAll(lstprops);
		             }
		           	 
		         	listeAllUsers.addAll(listeUtilisateurs);

		            }
		        	
		        	clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateur(listeAllUsers));
		        	clients.addAll(propositionDAO.findClientFromPropositionByUtilisateur(listeAllUsers));
	        	}


	        }
		  
		  
	        else {
	        	System.out.println("consultationBean 5");

	        	System.out.println("cas metier TD recherche"); 
	        	List<Agence> listeAgences= agenceDAO.listeAgenceParSociete(societeDAO.find(codeSociete));
//	        	System.out.println("listeAgences="+listeAgences);
	        	List<Utilisateurs> listeAllUsers = new ArrayList<Utilisateurs>();
//	        	System.out.println("listeAllUsers="+listeAllUsers);
//	        	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
	        	 Produit produitTD= produitDAO.find(Long.valueOf(PofileEnum.ASI.getProduit(utilisateur.getProfil().getCode()))); 
	        	 System.out.println("produitTD="+produitTD);
	             List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
//	             System.out.println("listePoliceTD="+listePoliceTD);
	        	for (Agence agence : listeAgences){
	           	    List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(agence);
//	           	 System.out.println("listeUtilisateurs="+listeUtilisateurs);
	           	 for (Utilisateurs utilisateurs : listeUtilisateurs){
	            	 for (Police policeTD : listePoliceTD) {
	     			    
	            		 lstadhs=adhesionDAO.findByPoliceAndDate(utilisateurs,policeTD,dateDu,dateAu, nadhesion, cin);
	            		 lstprops=propositionDAO.findByPoliceAndDate(utilisateurs,policeTD,dateDu,dateAu, nadhesion, cin);
	            		 
	            		 adhesions.addAll(lstadhs);
	                     propositions.addAll(lstprops);
//	                     System.out.println("lstprops="+lstprops);
//	                     System.out.println("lstprops="+lstprops );
	        	        }
	             }
	         	listeAllUsers.addAll(listeUtilisateurs);

	            }
	        	clients.addAll(adhesionDAO.findClientFromAdhesionByUtilisateurAndPolice(listeAllUsers,listePoliceTD));
	        	clients.addAll(propositionDAO.findClientFromPropositionByUtilisateurAndPolice(listeAllUsers,listePoliceTD));
	        }
      	
      }
      

    public List<Prodconv> getLstproduit() {
		return lstproduit;
	}

	public void setLstproduit(List<Prodconv> lstproduit) {
		this.lstproduit = lstproduit;
	}

	public List<Adhesion> getAdhesions() {
        return adhesions;
    }

    public List<Proposition> getPropositions() {
        return propositions;
    }

    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
	
	
	@EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;
	private String numquit;
	
	public void printFDR(Adhesion adhesion) {
		
		
		if (adhesion.getPolice().getCodProdui().getLibelleProduit().equals("TLSAssistV"))
		{
			
			
			numquit=serviceAssistantVoiyagetService.getNumeroQuit();
		
			
			//Adhesion adhesion =adhesionToPrintNotise;
			String report;
			try {
				
					 report = reportController.getRealPath()	+ "qtcTLS.jasper";
				
				Map<String, Object> parameters = new HashMap<String, Object>();
				DecimalFormat formatter = new DecimalFormat("#0.000");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
				parameters.put("imagesociete",  utilisateur.getAgence().getSociete().getCodSociet()+ ".png");
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
				parameters.put("frais", formatter.format(adhesion.getCout()));
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
		else if (adhesion.getPolice().getCodProdui().getLibelleProduit().contains("INVESTI") || adhesion.getPolice().getCodProdui().getLibelleProduit().contains("FUTURE DREAMS"))
		{
			
			
			//numquit=serviceAssistantVoiyagetService.getNumeroQuit();
		
			
			//Adhesion adhesion =adhesionToPrintNotise;
			String report;
			try {
				
					 report = reportController.getRealPath()	+ "Quit_hayaa.jasper";
					 
					 String numquit = iQuittanceDAO.FindMinNumquit(adhesion.getNadhesion());
					 Quittance quittance = iQuittanceDAO.searchQuitContratByNAdhesion(
							 adhesion.getNadhesion(), Integer.parseInt(numquit));
					 
					 DetailHayya detailHayya = hayyaServiceInter.find_detail_by_Adhesion(adhesion);
				
				Map<String, Object> parameters = new HashMap<String, Object>();
			
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				parameters.put("numquit", String.valueOf( quittance.getNumquit() ) );

				//parameters.put("agence", utilisateur.getAgence().getCodeAgence());
				parameters.put("agence", adhesion.getUtilisateur().getAgence().getCodeAgence());
				parameters.put("contrat",adhesion.getNumeroAdhesion());
				parameters.put("effet", df.format(quittance.getDateDu()));
				
//				Calendar cal = Calendar.getInstance();

				
				parameters.put("echeance", df.format(quittance.getDateDu()));
				
				parameters.put("souscr", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_souscr", adhesion.getClient().getAdrClient());
				 
				parameters.put("assure", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_assure", adhesion.getClient().getAdrClient());
				 
				 
				
				//parameters.put("prime_tot", adhesion.getPrimetot());
				parameters.put("frais", new BigDecimal(0.000));
				parameters.put("fga", detailHayya.getFa());
				//parameters.put("taxe", Double.parseDouble("0.000"));
				parameters.put("taxe", adhesion.getTaxe());
				//parameters.put("prime_nette", detailHayya.getPnette());
				
				
				switch ((int) detailHayya.getFractHayya().getId_fract_hayya()) {
				case 1:
				    System.out.println("Annuel");
//					cal.setTime(adhesion.getDatDebut());
//					cal.add(Calendar.MONTH, 12);
//					cal.add(Calendar.DATE, -1);
//					Date dateBefore01Days = cal.getTime();
					parameters.put("au", df.format(quittance.getDateAu()));
					
					parameters.put("prime_tot", adhesion.getPrimetot());
					parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
				    break;
				  case 2:
				    System.out.println("Semestriel");
//				    cal.setTime(adhesion.getDatDebut());
//				    cal.add(Calendar.MONTH, 6);
//				    cal.add(Calendar.DATE, -1);
//				    Date dateBefore01Days2 = cal.getTime();
					parameters.put("au", df.format(quittance.getDateAu()));
					
					parameters.put("prime_tot", detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
				    break;
				  case 3:
				    System.out.println("Trimestriel");
//				    cal.setTime(adhesion.getDatDebut());
//				    cal.add(Calendar.MONTH, 3);
//				    cal.add(Calendar.DATE, -1);
//				    Date dateBefore01Days3 = cal.getTime();
					parameters.put("au", df.format(quittance.getDateAu()));
					
					parameters.put("prime_tot", detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
				    break;
				  case 4:
				    System.out.println("Mensuel");				   
//				    cal.setTime(adhesion.getDatDebut());
//				    cal.add(Calendar.MONTH, 1);
//				    cal.add(Calendar.DATE, -1);
//				    Date dateBefore01Days4 = cal.getTime();
					parameters.put("au", df.format(quittance.getDateAu()));
					
					parameters.put("prime_tot",  detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue());
					parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));

				    break;
				  case 5:
				    System.out.println("PRIME UNIQUE");
//					cal.setTime(adhesion.getDatFin());
//					cal.add(Calendar.DATE, -1);
//					Date dateBefore01Days5 = cal.getTime();
					parameters.put("au", df.format(quittance.getDateAu()));
					
					parameters.put("prime_tot", adhesion.getPrimetot());
					parameters.put("prime_nette", detailHayya.getPnette());
				    break;
				  default:  
					 System.out.println("default"); 
					 break;
			}
			
				
				 
				
				parameters
				.put("star",  reportController.getLogoJasper());
				
				parameters.put("imagesociete", utilisateur.getAgence().getSociete()
						.getCodSociet() + ".png");
				
				parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);
				


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
		else if (adhesion.getPolice().getCodProdui().getLibelleProduit().equals("UNIVERSITY"))
		{
			
			
			//numquit=serviceAssistantVoiyagetService.getNumeroQuit();
		
			
			//Adhesion adhesion =adhesionToPrintNotise;
			String report;
			try {
				
					 report = reportController.getRealPath()	+ "Quit_avj.jasper";
					 
					 String numquit = iQuittanceDAO.FindMinNumquit(adhesion.getNadhesion());
					 Quittance quittance = iQuittanceDAO.searchQuitContratByNAdhesion(
							 adhesion.getNadhesion(), Integer.parseInt(numquit));
					 
					
				
				Map<String, Object> parameters = new HashMap<String, Object>();
			
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				parameters.put("numquit", String.valueOf( quittance.getNumquit() ) );

				//parameters.put("agence", utilisateur.getAgence().getCodeAgence());
				parameters.put("agence", adhesion.getUtilisateur().getAgence().getCodeAgence());
				parameters.put("contrat",adhesion.getNumeroAdhesion());
				parameters.put("effet", df.format(quittance.getDateDu()));
				
				Calendar cal = Calendar.getInstance();
//				cal.setTime(adhesion.getDatFin());
//				cal.add(Calendar.DATE, -1);
//				Date dateBefore01Days = cal.getTime();
//				parameters.put("au", df.format(dateBefore01Days));
				
				parameters.put("au", df.format(quittance.getDateAu()));
				
				parameters.put("echeance", df.format(quittance.getDateDu()));
				
				parameters.put("souscr", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_souscr", adhesion.getClient().getAdrClient());
				 
				parameters.put("assure", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_assure", adhesion.getClient().getAdrClient());
				 
				 
				
				//parameters.put("prime_tot", adhesion.getPrimetot());
				parameters.put("frais", new BigDecimal(0.000));
				parameters.put("fga", adhesion.getFga());
				parameters.put("taxe", Double.parseDouble("0.000"));
				//parameters.put("taxe", adhesion.getTaxe());
				//parameters.put("prime_nette", detailHayya.getPnette());
				
//				parameters.put("prime_tot", adhesion.getPrimetot());
				parameters.put("prime_tot", adhesion.getPrimetot() + adhesion.getDetailAvj().getSurprime().doubleValue());
//				parameters.put("prime_nette", new BigDecimal( adhesion.getMtPrime().doubleValue()));
				parameters.put("prime_nette", adhesion.getMtPrime() + adhesion.getDetailAvj().getSurprime().doubleValue());
				
				
				
				 
				
				parameters
				.put("star",  reportController.getLogoJasper());
				
				parameters.put("imagesociete", utilisateur.getAgence().getSociete()
						.getCodSociet() + ".png");
				
				
				parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);


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
		else {
			
			
		
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
        parameters.put("imagesociete",adhesion.getUtilisateur().getAgence().getSociete().getCodSociet()+".png");
        parameters.put("star","star.png");
        parameters.put("capital", String.valueOf(adhesion.getCapital()));
        parameters.put("ville_agence", adhesion.getUtilisateur().getAgence().getLibelle());
        
        parameters.put("codagc", adhesion.getUtilisateur().getAgence().getCodeAgence());

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
		
	}
	public void print(Adhesion adhesion) {
		System.out.println("**********************************print");
		if (adhesion.getPolice().getCodProdui().getLibelleProduit().equals("TD"))
		{
        try {
           
            String report = reportController.getRealPath() + "adhesion.jasper";
            Map<String, Object> parameters = new HashMap<String, Object>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            parameters.put("star","star.png");
            parameters.put("imagesociete",adhesion.getUtilisateur().getAgence().getSociete().getCodSociet()+".png");
            parameters.put("numeroPolice", adhesion.getPolice().getNPolice());
            parameters.put("contractant", adhesion.getPolice().getConvention()
                    .getSociete().getRaisonSoc());
            parameters.put("adresse", adhesion.getPolice().getConvention()
                    .getSociete().getAdresse());
            parameters.put("clientName", adhesion.getClient().getNomClient()
                    + " " + adhesion.getClient().getPrenomClient());
            parameters.put("clientBirthday",
                    sdf.format(adhesion.getClient().getDatNaiss()));
            parameters.put("clientCIN",
                    String.valueOf(adhesion.getClient().getIdentifiant()));
            parameters.put("dateEffet", sdf.format(adhesion.getDatDebut()));
            parameters.put("dateFin", sdf.format(adhesion.getDatFin()));
            String duree="";
            String[]tab=String.valueOf(adhesion.getDuree()).split("\\.");
   		
            if(!tab[1].equals("0")){
            	 duree=tab[0]+"(a)"+","+ tab[1]+ "(m)";
            } else{
            	 duree=tab[0]+"(année)";
            }
           	
           parameters.put("duree", duree);
            parameters.put("ageLimit", adhesion.getPolice().getAgelimit()
                    .toString());

            String garBase = "";
            String garExclusion = "";
            String garOpt = "";
            String optExclusion = "";

            Police police = adhesion.getPolice();
            List<GarantieVie> list = police.getGarantieVies();

            for (GarantieVie garantieVie : list) {

                if (garantieVie.getGarBase() != null
                        && !garantieVie.getGarBase().isEmpty()) {
                    garBase +=  garantieVie.getGarBase() + "\n";
                }
                if (garantieVie.getGarExclusion() != null
                        && !garantieVie.getGarExclusion().isEmpty()) {
                    garExclusion += garantieVie.getGarExclusion() + "\n";
                }
                if (garantieVie.getGarOpt() != null
                        && !garantieVie.getGarOpt().isEmpty()) {
                    garOpt +=  garantieVie.getGarOpt() + "\n";
                }
                if (garantieVie.getOptExclusion() != null
                        && !garantieVie.getOptExclusion().isEmpty()) {
                    optExclusion +=  garantieVie.getOptExclusion() + "\n";
                }
            }

            parameters.put("garBase", garBase.trim());
            parameters.put("garExclusion", garExclusion.trim());
            parameters.put("garOpt", garOpt.trim());
            parameters.put("optExclusion", optExclusion.trim());

            parameters.put("capital", String.valueOf(adhesion.getCapital()));
            parameters.put("mtPrime", String.valueOf(adhesion.getMtPrime()));

            JasperPrint print = JasperFillManager.fillReport(report,
                    parameters, new JREmptyDataSource());
            byte[] bytes = JasperExportManager.exportReportToPdf(print);

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseContentLength(bytes.length);
            ec.setResponseHeader("Content-Disposition",
                    "inline; filename=\""
                    + adhesion.getPolice().getNPolice() + "rapport.pdf"
                    + "\"");

            OutputStream output = ec.getResponseOutputStream();
            output.write(bytes);
            fc.responseComplete();

//            adhesion.setImpr(true);
//            adhesionDAO.edit(adhesion);
        } catch (Exception e) {
            e.printStackTrace();
        }
		}
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("Incendie"))
		{
			try {
                
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
	            parameters.put("nadhesion", adhesion.getNadhesion());
	            parameters.put("imagesociete",adhesion.getUtilisateur().getAgence().getSociete().getCodSociet()+".png");
	            parameters.put("star","star.png");
	            parameters.put("capital", String.valueOf(adhesion.getCapital()));
	            parameters.put("mtPrime", String.valueOf(adhesion.getMtPrime()));
	            parameters.put("taxe", String.valueOf(adhesion.getTaxe()));
	            parameters.put("primtot", String.valueOf(adhesion.getPrimetot()));
	            parameters.put("villeagence", adhesion.getUtilisateur().getAgence().getAdresse());
	            parameters.put("codagc", adhesion.getUtilisateur().getAgence().getCodeAgence());
	            String duree="";
	            String[]tab=String.valueOf(adhesion.getDuree()).split("\\.");
	   		
	            if(!tab[1].equals("0")){
	            	 duree=tab[0]+"(a)"+","+ tab[1]+ "(m)";
	            } else{
	            	 duree=tab[0]+"(année)";
	            }
	           	
	            
	            
	            parameters.put("duree", duree);
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

//	            adhesion.setImpr(true);
//	            adhesionDAO.edit(adhesion);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("Assistance voyage"))
		{
			try {
	            String report = reportController.getRealPath() + "notice_voyage.jasper";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            parameters.put("numplc", adhesion.getPolice().getNPolice());
	            parameters.put("Souscripteur", adhesion.getClient().getNomClient().toUpperCase()+" "+adhesion.getClient().getPrenomClient().toUpperCase());
	            parameters.put("adrsouscripteur", adhesion.getClient().getAdrClient());
	            parameters.put("nomclt", adhesion.getClient().getNomClient().toUpperCase() );
	            parameters.put("prenomclt", adhesion.getClient().getPrenomClient().toUpperCase());
	            parameters.put("cinclt",adhesion.getClient().getIdentifiant());
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
	            parameters.put("datefin",  sdf.format(adhesion.getDatFin()));
	            parameters.put("mtPrime", String.valueOf(adhesion.getMtPrime()));
	            parameters.put("surprime", "0");
	            parameters.put("primetot", String.valueOf(adhesion.getMtPrime()));
	            parameters.put("imagesociete",utilisateur.getAgence().getSociete().getCodSociet()+".png");
	            parameters.put("villeagence", utilisateur.getAgence().getLibelle());
	            parameters.put("personprv", adhesion.getPersonnePrv());
	            parameters.put("telpersonprv", adhesion.getTelphone());
	            parameters.put("destination", adhesion.getDestination());
	            parameters.put("star","star.png");
	            parameters.put("typedoc", adhesion.getClient().getTypeDoc().toUpperCase());
	            parameters.put("ncompte", adhesion.getClient().getNcompte());
	            if(adhesion.getCouverture()!=null)
	            {
	            	parameters.put("couverture", adhesion.getCouverture().intValue()+"");
	            }
	            String nom_assure="";
	            String prenom_assure="";
	            String datnais_assure="";
	            for (Adherent  adherent : adhesion.getAdherents()) {
	            	nom_assure+=adherent.getNomAdherent()+ "\n";
	            	prenom_assure+=adherent.getPrenomAdherent()+ "\n";
	            	datnais_assure+= sdf.format(adherent.getDatNaiss())+ "\n";
	          }
	            
	            parameters.put("nom_assure", nom_assure.trim());
	            parameters.put("prenom_assure", prenom_assure.trim());
	            parameters.put("datnais_assure", datnais_assure.trim());
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

//	            adhesion.setImpr(true);
//	            adhesionDAO.edit(adhesion);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		System.out.println("adhesion.getPolice().getCodProdui().getLibelleProduit()"+adhesion.getPolice().getCodProdui().getLibelleProduit());
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("UNIVERSITY"))
		{
			try {
				System.out.println("test impression");
	            // String report = reportController.getRealPath() + "avenir_jeunesse_v2.jasper";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    		//parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
	    		parameters.put("codagc", adhesion.getUtilisateur().getAgence().getCodeAgence());
	    		parameters.put("star",reportController.getLogoJasper());
	    		parameters.put("contrat", adhesion.getNadhesion());
	    		//parameters.put("numplc",adhesion.getPolice().getNPolice());
 	    		parameters.put("Souscripteur", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
 	    		
 	    		String typeVoie_libelle_sous = " ";
 	   		if (adhesion.getClient().getTypeVoie() != null ) {
 	   			  // doSomething
 	   			typeVoie_libelle_sous = adhesion.getClient().getTypeVoie().getLibelle();
 	   			}
 	   		
 	    		parameters.put("Adrsouscripteur", adhesion.getClient().getNumAdr()+" "+typeVoie_libelle_sous+" "+adhesion.getClient().getAdrClient()+" "+adhesion.getClient().getLocalite()+" "+adhesion.getClient().getGouver()+" "+adhesion.getClient().getCodeP());
 	    		parameters.put("Cinsouscripteur", adhesion.getClient().getIdentifiant());
	    		/**************/
 	    		parameters.put("Assure", adhesion.getAdherents().get(0).getNomAdherent()+" "+adhesion.getAdherents().get(0).getPrenomAdherent());
 	    		
 	    		String typeVoie_libelle = " ";
 	   		if (adhesion.getAdherents().get(0).getTypeVoie() != null ) {
 	   			  // doSomething
 	   			typeVoie_libelle = adhesion.getAdherents().get(0).getTypeVoie().getLibelle();
 	   			}
 	   		
 	    		parameters.put("Adrassure", adhesion.getAdherents().get(0).getNumAdr()+" "+typeVoie_libelle+" "+adhesion.getAdherents().get(0).getAdresse()+" "+adhesion.getAdherents().get(0).getLocalite()+" "+adhesion.getAdherents().get(0).getGouver()+" "+adhesion.getAdherents().get(0).getCodePostal());
 	    		parameters.put("Cinassure", adhesion.getAdherents().get(0).getIdentifiant());
 	    		parameters.put("Datnaiassure", df.format(adhesion.getAdherents().get(0).getDatNaiss()));
 	    		// parameters.put("Prfassure", adhesion.getAdherents().get(0).getProfession().getLibelleProfession());
 	    		parameters.put("Prfassure", adhesion.getAdherents().get(0).getProfession() != null?adhesion.getAdherents().get(0).getProfession().getLibelleProfession():" ");
 	    		parameters.put("Sexassure", adhesion.getAdherents().get(0).getSexe());
 	    		parameters.put("Telassure", adhesion.getAdherents().get(0).getTel());

 	    		parameters.put("Beneficiaire", adhesion.getBeneficiaires().get(0).getNom()+" "+adhesion.getBeneficiaires().get(0).getPrenom());
 	    		parameters.put("Adrbenef", adhesion.getBeneficiaires().get(0).getAdresse());
 	    		parameters.put("Datnaisbenef", df.format(adhesion.getBeneficiaires().get(0).getDateNaiss()));
 	    		parameters.put("Sexbenef", adhesion.getBeneficiaires().get(0).getSexe());
	    		/***********/
 	    		parameters.put("imagesociete",utilisateur.getAgence().getSociete().getCodSociet()+".png");
 	    		parameters.put("Datedeff", df.format(adhesion.getDatDebut()));
 	    		parameters.put("Datech", df.format(adhesion.getDatFin()));
 	    		parameters.put("Duree", String.valueOf((int)adhesion.getDuree())+ " ans");
 	    		parameters.put("Mntbourse", (new DecimalFormat("#0.000")).format(adhesion.getDetailAvj().getMtBourse()) + " TND");
 	    		parameters.put("Agesevice", adhesion.getDetailAvj().getAgeServiceBourse().toString());
 	    		parameters.put("Durservice", adhesion.getDetailAvj().getDureeBourse().toString()+ " ans");
 	    		parameters.put("Dateservice", df.format(adhesion.getDetailAvj().getDatePremierBourse()));
 	    		parameters.put("FraisinscON", "NON");
 	    		parameters.put("RenteON", "NON");
 	    		double totEpargne=0;
 	    		double totDeces=0;
 	    		for (GarantieAvj garantieAvj : adhesion.getGarantieAvjs())
 	    		{
 	    			if(garantieAvj.getGarantie().getIdGarantie()==1)
 	    			{
 	    	    		parameters.put("PrmEpargneb", (new DecimalFormat("#0.000")).format(garantieAvj.getMtPrime()) + " TND");
 	    	    		totEpargne+=garantieAvj.getMtPrime().doubleValue();
 	    			}
 	    			if(garantieAvj.getGarantie().getIdGarantie()==2)
 	    			{
 	    	    		parameters.put("PrmDecb", (new DecimalFormat("#0.000")).format(garantieAvj.getMtPrime()) + " TND");
 	    	    		totDeces+=garantieAvj.getMtPrime().doubleValue();
 	    			}
 	    			if(garantieAvj.getGarantie().getIdGarantie()==3)
 	    			{
 		    			parameters.put("FraisinscON", "OUI");
 			    		parameters.put("Mtfrais", (new DecimalFormat("#0.000")).format(garantieAvj.getMontant()) + " TND");
 			    		parameters.put("Mtpepargnfrais", (new DecimalFormat("#0.000")).format(garantieAvj.getMtPrime()) + " TND");
 	    	    		totEpargne+=garantieAvj.getMtPrime().doubleValue();
 
 	    			}
 	    			if(garantieAvj.getGarantie().getIdGarantie()==4)
 	    			{
 	    	    		parameters.put("Mtpdecfrais", (new DecimalFormat("#0.000")).format(garantieAvj.getMtPrime()) + " TND");
 	    	    		totDeces+=garantieAvj.getMtPrime().doubleValue();
 
 	    			}
 	    			if(garantieAvj.getGarantie().getIdGarantie()==5)
 	    			{
 		    			parameters.put("RenteON", "OUI");
 		    			parameters.put("Mtrente", (new DecimalFormat("#0.000")).format(garantieAvj.getMontant()) + " TND");
 			    		parameters.put("Mtrentedeces", (new DecimalFormat("#0.000")).format(garantieAvj.getMtPrime()) + " TND");
 	    	    		totDeces+=garantieAvj.getMtPrime().doubleValue();
 
 	    			}
 	    		}
 	    		
 	    		
 	    		
 	    		
 	    		parameters.put("Prmnetepargne", (new DecimalFormat("#0.000")).format(totEpargne) + " TND");
 	    		parameters.put("Prmnetdeces", (new DecimalFormat("#0.000")).format(totDeces) + " TND");
// 	    		parameters.put("Totprmnet", (new DecimalFormat("#0.000")).format(adhesion.getMtPrime()) + " TND");
 	    		parameters.put("Totprmnet", (new DecimalFormat("#0.000")).format(adhesion.getMtPrime() + adhesion.getDetailAvj().getSurprime().doubleValue()) + " TND");
 	    		//parameters.put("frais", (new DecimalFormat("#0.000")).format(adhesion.getCout())+ " TND");
 	    		parameters.put("frais", (new DecimalFormat("#0.000")).format(adhesion.getFga())+ " TND");
// 	    		parameters.put("Prmtot", (new DecimalFormat("#0.000")).format(adhesion.getPrimetot()) + " TND");
 	    		parameters.put("Prmtot", (new DecimalFormat("#0.000")).format(adhesion.getPrimetot() + adhesion.getDetailAvj().getSurprime().doubleValue()) + " TND");
 	    		
 	    		parameters.put("Fractionnement", adhesion.getFraPolice().equals("M")?"Mensuel":adhesion.getFraPolice().equals("T")?"Trimestriel":adhesion.getFraPolice().equals("S")?"Semestriel":"Annuel");
 	    		parameters.put("tauxevol", String.valueOf(adhesion.getDetailAvj().getIdTauxCroissancePrime()));
 	    		parameters.put("DerEch", df.format(adhesion.getDatFin()));
 	    		parameters.put("taux", String.valueOf(adhesion.getTauxInter()));
 	    		//parameters.put("ville_agence", utilisateur.getAgence().getAdresse());
 	    		parameters.put("ville_agence", adhesion.getUtilisateur().getAgence().getAdresse());
 	    		
 	    		parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
 	    		
 	    		
 	   		Calendar cal = Calendar.getInstance();
 			Date dateBefore01Days;
 			
 			
 			switch(adhesion.getFraPolice()) {
 			

 			
 		       case "A": 
 					cal.setTime(adhesion.getDatDebut());
 					cal.add(Calendar.MONTH, 12);
 					cal.add(Calendar.DATE, -1);
 					 dateBefore01Days = cal.getTime();
 					    	 
 		           break;
 		   
 		       case "S":
 		    	   cal.setTime(adhesion.getDatDebut());
 				    cal.add(Calendar.MONTH, 6);
 				    cal.add(Calendar.DATE, -1);
 				     dateBefore01Days = cal.getTime();


 		           break;
 		   
 		       case "T":
 		    	   cal.setTime(adhesion.getDatDebut());
 				    cal.add(Calendar.MONTH, 3);
 				    cal.add(Calendar.DATE, -1);
 				     dateBefore01Days = cal.getTime();
 				    
 				   
 		           break;
 		           
 		       case "M":
 		    	   cal.setTime(adhesion.getDatDebut());
 				    cal.add(Calendar.MONTH, 1);
 				    cal.add(Calendar.DATE, -1);
 				     dateBefore01Days = cal.getTime();
 				    

 		           break;
 		
 		       default:
 		    	   cal.setTime(adhesion.getDatDebut());
 					cal.add(Calendar.DATE, -1);
 					 dateBefore01Days = cal.getTime();
 					
 		           break;
 			
 			}
 			
 			parameters.put("Date_ech_prime", df.format(dateBefore01Days));
 	    		
 	    		
 	    		/// partie tableau ev des gar
 	    		
		        List<DetailsEvolutionGarantieAVJ> evs = avenirJeunesseDAOInter.find_details_ev_gar_by_Adhesion(adhesion);
		        List<DetailsEvolutionGarantieAvjDTO> orders = new ArrayList<DetailsEvolutionGarantieAvjDTO>();
		        
		        for(DetailsEvolutionGarantieAVJ ev : evs) {
		        	
		        	DetailsEvolutionGarantieAvjDTO eg = new DetailsEvolutionGarantieAvjDTO();
		        	eg.setAnnee(new BigDecimal(ev.getAnnee()));
		        	eg.setCumul_vers(ev.getCumul_vers());
		        	eg.setPrime_epar_bourse(ev.getPrime_epar_bourse());
		        	eg.setPrime_epar_frais(ev.getPrime_epar_frais());
		        	eg.setEpar_cons_bourse(ev.getEpar_cons_bourse());
		        	eg.setParticip_benef_bourse(ev.getParticip_benef_bourse());
		        	eg.setEpar_cons_fannee_bourse(ev.getEpar_cons_fannee_bourse());
		        	eg.setEpar_cons_frais(ev.getEpar_cons_frais());
		        	eg.setParticip_benef_frais(ev.getParticip_benef_frais());
		        	eg.setEpar_cons_fannee_frais(ev.getEpar_cons_fannee_frais());
		        	eg.setAvantage_financier(ev.getAvantage_financier());
		        	eg.setAvantage_fisc_annuel(ev.getAvantage_fisc_annuel());
					  
		        	
		        	orders.add(eg);
		        	
		        }

		        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(orders);

		        parameters.put("datasource", ds);
 	    		
		        
		        parameters.put("report", reportController.getRealPath());
		        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH); 
 	    		
 	    		 String path =  "choisir_societe"; 
 	    		path = reportController.getRealPath()
						+ "avenir_jeunesse_v5.jasper";
				
				
				if( adhesion.getUtilisateur().getAgence().getSociete().getCodSociet().equals("022671FPM000") ) { //QNB
					
					path = reportController.getRealPath()
							+ "avenir_jeunesse_v6.jasper";

					
				}
				
				
				if(adhesion.getUtilisateur().getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat())) {//stb
					
					
					if(adhesion.getModePaiementHayya().get(0).getModep()==2) {
						
						path = reportController.getRealPath()
								+ "avenir_jeunesse_v6.jasper";
					} 
			
				} 
 	    		 
 	             		 

 	    		 
 	            parameters.put("report", reportController.getRealPath());
 	           parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);
 	           

 	          /******************* pour prelevement ******************************/
		        

				parameters.put("nom_assure", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adresse", adhesion.getClient().getAdrClient());
				parameters.put("tel", adhesion.getClient().getTel());
				 
				
				parameters.put("email", adhesion.getClient().getMail() != null?adhesion.getClient().getMail():" ");
				
				parameters.put("num_contrat",adhesion.getNadhesion());
				
				parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
				
				String nom_complet = utilisateur.getAgence().getSociete().getNomComplet();
				parameters.put("nom_complet", nom_complet.replace("<b>", "").replace("</b>", "") );
				
				parameters.put("agence", adhesion.getUtilisateur().getAgence().getLibelle());
				
				parameters.put("adresse_agence", adhesion.getUtilisateur().getAgence().getAdresse());
				
				 
				String rib = adhesion.getClient().getNcompte();
				
				parameters.put("rib", adhesion.getClient().getNcompte());
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
				
		        
		        /***************************************/	           
 	    		

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
	            JasperPrint print = JasperFillManager.fillReport(report,  parameters,new JREmptyDataSource());
	            byte[] bytes = JasperExportManager.exportReportToPdf(print);

	            FacesContext fc = FacesContext.getCurrentInstance();
	            ExternalContext ec = fc.getExternalContext();
	            ec.responseReset();
	            ec.setResponseContentType("application/pdf");
	            ec.setResponseContentLength(bytes.length);
	            ec.setResponseHeader("Content-Disposition","inline; filename=Notice_avj.pdf");
	            OutputStream output = ec.getResponseOutputStream();
	            output.write(bytes);
	            fc.responseComplete();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        */
		}
		
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("MultiRisque Habitation"))
		{
			try {
	            String report = reportController.getRealPath() + "noticemrhp1.jasper";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	  
	            
	            
	            
	            
	            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				parameters.put("nadhesion",  adhesion.getNadhesion());
				parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
				//parameters.put("contractType", contractType);
				//System.out.println("contractType" + contractType );
				 if (adhesion.getDatDebut() != null) {
			            parameters.put("effDate", df.format(adhesion.getDatDebut()));
			        } else {
			            parameters.put("effDate", " ");
			        }

			       // if (lastEcheanceDate != null) {
			         //   parameters.put("echeanceDate", df.format(lastEcheanceDate));
			        //} else {
			        //    parameters.put("echeanceDate", "N/A");
			        //}
				 
				  Date echeanceDate;
				  Date extinction;
				 if (adhesion.getDetailMrh().getTypecontrat().equals("Ferme")) { // Mouna: Contrat ferme
						Calendar c = Calendar.getInstance();
						c.setTime(adhesion.getDatDebut());
						c.add(Calendar.YEAR, 1);
						c.add(Calendar.DAY_OF_MONTH, -1);
						extinction = c.getTime();
						echeanceDate = null;
						System.out.println("date extinction= "+extinction);
					} else {  // Mouna: Contrat renouvelable
						Calendar c = Calendar.getInstance();
						c.setTime(adhesion.getDatDebut());
						c.add(Calendar.YEAR, 1);
						echeanceDate = c.getTime();
						extinction = null;
						//duration ="Annuelle";
						System.out.println("date echeanceDate= "+echeanceDate);
					}
				 

			        if ((extinction != null) &&  adhesion.getDetailMrh().getTypecontrat().equals("Ferme") ){
			            parameters.put("expDate", df.format(extinction));
			        } else {
			            parameters.put("expDate", " ");
			        }
			        
			        if ((echeanceDate != null) && adhesion.getDetailMrh().getTypecontrat().equals("Renouvelable")){
			            parameters.put("echeanceDate", df.format(echeanceDate));
			            System.out.println("echeanceDate" + echeanceDate );
			        } else {
			            parameters.put("echeanceDate", " ");
			        }
			        
			        parameters.put("clientName", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
			        parameters.put("adrclt", adhesion.getClient().getAdrClient());
			        parameters.put("cpclt", adhesion.getClient().getCodeP());
			        parameters.put("vilclt", adhesion.getClient().getLocalite());
			        parameters.put("gouvclt",adhesion.getClient().getGouver());
			        //paramaters.put("faxclt",client.getfaxclt);
			        parameters.put("mailclt",adhesion.getClient().getMail());
			        parameters.put("telclt",adhesion.getClient().getTel());
			        
			        parameters.put("quality", adhesion.getDetailMrh().getQualite().getQualite() );
			        parameters.put("sitrisq", adhesion.getAdresse());
			        parameters.put("cprisq", adhesion.getCodeP());
			        parameters.put("vilrisq", adhesion.getLocalite());
			        parameters.put("city", adhesion.getGouver());///city
			        parameters.put("riskNature", adhesion.getDetailMrh().getNatureRisq().getLibelleNature()  );
			        parameters.put("nbrPieceLess", String.valueOf(adhesion.getDetailMrh().getNbrpieceless()  ));
			        parameters.put("nbrPieceMore", String.valueOf(adhesion.getDetailMrh().getNbrpiecemore() ));
			        parameters.put("area", String.valueOf(adhesion.getDetailMrh().getArea()));
			        parameters.put("furniturePrice", (new DecimalFormat("#0.000")).format(adhesion.getDetailMrh().getFurnitureprice() ) + " TND");
			        
			        parameters.put("duration",adhesion.getDetailMrh().getDuree() );
			     
			        
			        
			       // parameters.put("primeNette", (new DecimalFormat("#0.000")).format(primeNette) + " TND");
			       // parameters.put("tax", (new DecimalFormat("#0.000")).format(tax) + " TND");
			       // parameters.put("cost", (new DecimalFormat("#0.000")).format(cost) + " TND");
			       // parameters.put("primeTotal", (new DecimalFormat("#0.000")).format(primeTotal) + " TND");

			        parameters.put("baseGar", "pack.getBaseGar()");
			        System.out.println("baseGar" + "pack.getBaseGar()");

			        parameters.put("opGar", "pack.getOpGarString()");
			        System.out.println("optGar" + "pack.getOpGarString()");
			        
//			        TarifMrh tarif805 =  getParamPrices();
//			         ArrayList<String> baseGar;
//			         ArrayList<OpGar> opGar;
//			        for(GarantieMrh garnti : adhesion.getGarantieMrh()){
//			        	
//			        	garnti.get
//			        	
//			        }
			        
			        
			        
			        //*******************************page 2*******************
			        parameters.put("primeNette", (new DecimalFormat("#0.000")).format(adhesion.getMtPrime() ) + " TND");
			        parameters.put("cost", (new DecimalFormat("#0.000")).format(adhesion.getCout() ) + " TND");
			        parameters.put("tax", (new DecimalFormat("#0.000")).format(adhesion.getTaxe() ) + " TND");
			        parameters.put("primeTotal", (new DecimalFormat("#0.000")).format(adhesion.getPrimetot() ) + " TND");
			        if(adhesion.getDetailMrh().getRachIn().equals("O")) parameters.put("rach", "1");  
			        
			        else
			        parameters.put("rach", "0");
			        
			        if (adhesion.getDetailMrh().getTypecontrat().equals("Renouvelable")) parameters.put("renouv", "1");
			        else
			        parameters.put("renouv", "0");
			        
			        if (((adhesion.getDetailMrh().getTypecontrat().equals("Renouvelable"))&& adhesion.getDetailMrh().getDuree() == "Annuelle" ) ||   ((adhesion.getDetailMrh().getTypecontrat().equals("Ferme"))&& adhesion.getDetailMrh().getDuree() == "Prorata" )) 
			        parameters.put("reval", "1");
			        else
			        parameters.put("reval", "0");
			        
			        
			        boolean AS = false;
			        for(GarantieMrh garnti : adhesion.getGarantieMrh()){
			        	
			        	if (garnti.getCodGar().equals("15")){
			        		AS = true;
			        		break;
			        	}
			        	
			        }

			        if (AS)   parameters.put("as", "1");
			        else
			        parameters.put("as", "0");	

	            
	            
	            
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

//	            adhesion.setImpr(true);
//	            adhesionDAO.edit(adhesion);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("TLSAssistV"))
		{
			
			
			//Adhesion adhesion=adhesionToPrintNotise;
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
				parameters.put("star",  "star.png");
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
				parameters.put("frais", formatter.format(adhesion.getCout()));
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
		
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().contains("INVESTI") || adhesion.getPolice().getCodProdui().getLibelleProduit().contains("FUTURE DREAMS"))
		{
			
			/// adhesion
			
			try {
				
				String report = "choisir_societe";
				report = reportController.getRealPath()
						+ "Contrat_hayaa_v5.jasper";
				
				
				if( adhesion.getUtilisateur().getAgence().getSociete().getCodSociet().equals("022671FPM000") ) { //QNB
					
					report = reportController.getRealPath()
							+ "Contrat_hayaa_v6.jasper";

					
				}
				
				
				if(adhesion.getUtilisateur().getAgence().getSociete().getCodSociet().equals(reportController.getSTBMat())) {//stb
					
					
					if(adhesion.getModePaiementHayya().get(0).getModep()==2) {
						
						report = reportController.getRealPath()
								+ "Contrat_hayaa_v6.jasper";
					} 
			
				} 
		        
	 
				
				
     
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	            
	            
	            DetailHayya detailHayya = hayyaServiceInter.find_detail_by_Adhesion(adhesion);
	            
	            BilanFinancierHayya bilanFinancierHayya =  hayyaServiceInter.find_bilan_financier_7ayya_by_Adhesion(adhesion);
	            
	            List<DetailsGarantieHayya> detailsGarantieHayyaList =  hayyaServiceInter.find_detail_garatie_by_Adhesion(adhesion);
	            
	            
	            if( adhesion.getUtilisateur().getAgence().getSociete().getCodSociet().equals("002707PPM000")) {
					
					parameters.put("produit_fr","INVESTI");
					parameters.put("produit_ar","ادخـــــــــار 'أنفست'");
					parameters.put("condition_fr","'INVESTI'");
					parameters.put("condition_ar","\" ادخـــــــــار أنفست\"");
				}
					 
				else {
					
					parameters.put("produit_fr","FUTURE DREAMS");
					parameters.put("produit_ar","ادخـــــــــار 'فيوتر دريمز'");
					parameters.put("condition_fr","'FUTURE DREAMS'");
					parameters.put("condition_ar","\" ادخـــــــــار فيوتر دريمز\"");
				}
	            
	            
	            //parameters.put("agence", utilisateur.getAgence().getCodeAgence());
	            parameters.put("agence", adhesion.getUtilisateur().getAgence().getCodeAgence());
				 parameters.put("contrat",adhesion.getNadhesion());
				//parameters.put("contrat",adhesion.getPolice().getNPolice());
				parameters.put("effet", df.format(adhesion.getDatDebut()));
				parameters.put("duree", Integer.toString((int)adhesion.getDuree())+" ans");
				parameters.put("echeance", df.format(adhesion.getDatFin()));
				parameters.put("capital", detailsGarantieHayyaList.get(0).getCapital());
				parameters.put("souscr", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_souscr", adhesion.getClient().getAdrClient());
				parameters.put("id_souscr", adhesion.getClient().getIdentifiant());
				parameters.put("assure", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_assure", adhesion.getClient().getAdrClient());
				parameters.put("id_assure", adhesion.getClient().getIdentifiant());
				parameters.put("naiss_assure", df.format(adhesion.getClient().getDatNaiss()));
				
				parameters.put("rib", adhesion.getClient().getNcompte());
				
				
				if(adhesion.getClient().getSexe().equalsIgnoreCase("M"))
					parameters.put("sexe_assure", "Homme");
				else
					parameters.put("sexe_assure", "Femme");
				
				parameters.put("prf_assure", adhesion.getClient().getProfession() != null?adhesion.getClient().getProfession().getLibelleProfession():" ");
				//parameters.put("prf_assure", adhesion.getClient().getProfession().getLibelleProfession());
				parameters.put("tel_assure", adhesion.getClient().getTel());
				parameters.put("nom_benef", adhesion.getClient().getNomClient());
				parameters.put("cin_benef", adhesion.getClient().getIdentifiant());
				parameters.put("parente", null);
				parameters.put("poucent_cap", null);
				
				parameters.put("nom_benef_d", null);
				parameters.put("cin_benef_d", null);
				parameters.put("parente_d", null);
				parameters.put("poucent_cap_d", null);
				
				//parameters.put("prime_tot", adhesion.getPrimetot());
				parameters.put("frais", new BigDecimal(0.000));
				parameters.put("fga", detailHayya.getFa());
				//parameters.put("prime_nette", detailHayya.getPnette());
				
				
				parameters.put("primeversee", bilanFinancierHayya.getPrimeversee());
				parameters.put("epargneprev", bilanFinancierHayya.getEpargneprev());
				parameters.put("gainfinancier", bilanFinancierHayya.getGainfinancier());
				parameters.put("econimpot", bilanFinancierHayya.getEconimpot());
				parameters.put("gaintotal", bilanFinancierHayya.getGaintotal());
				
				
				
				switch((int)detailHayya.getFractHayya().getId_fract_hayya()){
				   
			       case 1: 
			    	   parameters.put("prime_tot", adhesion.getPrimetot());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
					   
			    	   parameters.put("annuel", "X");
			           break;
			   
			       case 2:

						parameters.put("prime_tot", detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
					   
			    	   parameters.put("semestriel", "X");
			           break;
			   
			       case 3:
			    	   parameters.put("prime_tot", detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
					    
			    	   parameters.put("trimestriel", "X");
			           break;
			           
			       case 4:
			    	   parameters.put("prime_tot",  detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));

			    	   parameters.put("mensuel", "X");
			           break;
			
			       default:
			    	   parameters.put("prime_tot", adhesion.getPrimetot());
						parameters.put("prime_nette", detailHayya.getPnette());
						
			    	   parameters.put("unique", "X");
			           break;
			   }
				

				
				parameters.put("evol_p", detailHayya.getCroissHayya().getCroissance());

				
				if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000")) {
					
					parameters.put("tx_benef", "80%");
				
				}else {
					
					parameters.put("tx_benef", "90%");	
					
				}
				
				parameters.put("tx_interef_gar", "4%");
				
				
				parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
				
				parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
				
				parameters
				.put("star",  reportController.getLogoJasper());
				
				parameters.put("imagesociete", utilisateur.getAgence().getSociete()
						.getCodSociet() + ".png");
				
				
		        List<DetailsEvolutionGarantieHayya> evs = hayyaServiceInter.find_details_ev_gar_by_Adhesion(adhesion);
		        List<DetailsEvolutionGarantie> orders = new ArrayList<DetailsEvolutionGarantie>();
		        
		        for(DetailsEvolutionGarantieHayya ev : evs) {
		        	
		        	DetailsEvolutionGarantie eg = new DetailsEvolutionGarantie();
		        	eg.setAnnee(new BigDecimal(ev.getAnnee()));
		        	eg.setPrime_comm(ev.getPrime_comm());
		        	eg.setPrime_capit(ev.getPrime_capit());
		        	eg.setCapital_tmg(ev.getCapital_tmg());
		        	eg.setCapital_taux_reel(ev.getCapital_taux_reel());
		        	eg.setBenefices_realises(ev.getBenefices_realises());
		        	eg.setParticipation_benef(ev.getParticipation_benef());
		        	eg.setEpargne_fin_annee(ev.getEpargne_fin_annee());
		        	eg.setGain_financier(ev.getGain_financier());
		        	eg.setGain_fisc_annuel(ev.getGain_fisc_annuel());
		        	eg.setValeur_rachat(ev.getValeur_rachat());
		        	eg.setCumul_vers(ev.getCumul_vers());
		        	
		        	orders.add(eg);
		        	
		        }

		        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(orders);

		        parameters.put("datasource", ds);
		        
		        
		        List<Person> personnes = new ArrayList<Person>();
		        
		        System.out.println("vie ");
		        
		        for(BenefHayyaVie benefHayyaVie: hayyaServiceInter.find_benef_vie_by_Adhesion(adhesion)) {
		        	Person assure = new Person();
		        	assure.setNom(benefHayyaVie.getNom());
		        	assure.setPrenom(benefHayyaVie.getPrenom());
		        	assure.setCin(benefHayyaVie.getCin());
		        	assure.setLien(benefHayyaVie.getLien());
		        	assure.setQuote(benefHayyaVie.getQuote());
		        	
		        	personnes.add(assure);
		        }
		        
		         
		      
		        
		        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(personnes);
		        
		        parameters.put("datasvie", beanColDataSource);
		        
		        
		        
		        List<Person> personnes_deces = new ArrayList<Person>();
		        
		        System.out.println("deces " );
		        
		        
		        for(BenefHayyaDeces benefHayyaDeces: hayyaServiceInter.find_benef_deces_by_Adhesion(adhesion)) {
		        	Person assure = new Person();
		        	assure.setNom(benefHayyaDeces.getNom());
		        	assure.setPrenom(benefHayyaDeces.getPrenom());
		        	assure.setCin(benefHayyaDeces.getCin());
		        	assure.setLien(benefHayyaDeces.getLien());
		        	assure.setQuote(benefHayyaDeces.getQuote());
		        	
		        	personnes_deces.add(assure);
		        }
		        
		         
			
		        
		        JRBeanCollectionDataSource beanColDataSourceDeces = new JRBeanCollectionDataSource(personnes_deces);
		        
		        parameters.put("datasdeces", beanColDataSourceDeces);
	            
		        parameters.put("report", reportController.getRealPath());
		        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);
		        
/******************* pour prelevement ******************************/
		        

				parameters.put("nom_assure", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adresse", adhesion.getClient().getAdrClient());
				parameters.put("tel", adhesion.getClient().getTel());
				 
				
				parameters.put("email", adhesion.getClient().getMail() != null?adhesion.getClient().getMail():" ");
				
				parameters.put("num_contrat",adhesion.getNadhesion());
				
				parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
				
				String nom_complet = utilisateur.getAgence().getSociete().getNomComplet();
				parameters.put("nom_complet", nom_complet.replace("<b>", "").replace("</b>", "") );
				
				parameters.put("agence", adhesion.getUtilisateur().getAgence().getLibelle());
				
				parameters.put("adresse_agence", adhesion.getUtilisateur().getAgence().getAdresse());
				
				 
				String rib = adhesion.getClient().getNcompte();
				
				parameters.put("rib", adhesion.getClient().getNcompte());
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
				
		        
		        /***************************************/
	             
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

//	            adhesion.setImpr(true);
//	            adhesionDAO.edit(adhesion);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			

		}
		
    }
	
	public void print_notice(Adhesion adhesion) {
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().contains("INVESTI") || adhesion.getPolice().getCodProdui().getLibelleProduit().contains("FUTURE DREAMS"))
		{
			
			System.out.println("notice INVESTI " +adhesion.getPolice().getCodProdui().getLibelleProduit());
			
			/// notice
			try {
	            String report = reportController.getRealPath() + "notice_7ayya.jasper";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	            
	            
	            DetailHayya detailHayya = hayyaServiceInter.find_detail_by_Adhesion(adhesion);
	            
	            List<DetailsGarantieHayya> detailsGarantieHayyaList =  hayyaServiceInter.find_detail_garatie_by_Adhesion(adhesion);
	            
	            //parameters.put("agence", utilisateur.getAgence().getCodeAgence());
	            parameters.put("agence", adhesion.getUtilisateur().getAgence().getCodeAgence());
				parameters.put("contrat",adhesion.getNadhesion());
				parameters.put("effet", df.format(adhesion.getDatDebut()));
				parameters.put("duree", Integer.toString((int)adhesion.getDuree())+" ans");
				parameters.put("echeance", df.format(adhesion.getDatFin()));
				parameters.put("capital", detailsGarantieHayyaList.get(0).getCapital().doubleValue() );
				parameters.put("souscr", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_souscr", adhesion.getClient().getAdrClient());
				parameters.put("id_souscr", adhesion.getClient().getIdentifiant());
				parameters.put("assure", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_assure", adhesion.getClient().getAdrClient());
				parameters.put("id_assure", adhesion.getClient().getIdentifiant());
				parameters.put("naiss_assure", df.format(adhesion.getClient().getDatNaiss()));
				
				
				
				parameters.put("prf_assure", adhesion.getClient().getProfession() != null?adhesion.getClient().getProfession().getLibelleProfession():" ");
				//parameters.put("prf_assure", adhesion.getClient().getProfession().getLibelleProfession());
				parameters.put("tel_assure", adhesion.getClient().getTel());
				parameters.put("nom_benef", adhesion.getClient().getNomClient());
				parameters.put("cin_benef", adhesion.getClient().getIdentifiant());

				parameters.put("prenom_benef", adhesion.getClient().getPrenomClient());
				parameters.put("compte", adhesion.getClient().getNcompte());
				parameters.put("email", adhesion.getClient().getMail());
				parameters.put("cpt", adhesion.getClient().getCodeP());
				
				parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
				
				
				String nom_complet = utilisateur.getAgence().getSociete().getNomComplet();
				parameters.put("nom_complet", nom_complet.replace("<b>", "").replace("</b>", "") );
				
				
				
				
				Double primetot;
				Double primetot_pour_lettre;
				String primetot_lettre;
				
				switch((int)detailHayya.getFractHayya().getId_fract_hayya()){
				   
			       case 1: 
			    	   primetot = adhesion.getPrimetot();
			    	   primetot_pour_lettre = adhesion.getPrimetot() * 1000;
			    	   primetot_lettre = Nombre.montantTND(primetot);
			    	   
			    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot));
			    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
						parameters.put("mnt_lettre", primetot_lettre);
						
			    	   parameters.put("annuel", "X");
			           break;
			   
			       case 2:
			    	   primetot = detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue();
			    	   primetot_pour_lettre = (detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue()) * 1000;
			    	   primetot_lettre = Nombre.montantTND(primetot);
			    	   
			    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot));
			    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
						parameters.put("mnt_lettre", primetot_lettre);
					   
			    	   parameters.put("semestriel", "X");
			           break;
			   
			       case 3:
			    	   primetot = detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue();
			    	   primetot_pour_lettre = (detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue()) * 1000;
			    	   primetot_lettre = Nombre.montantTND(primetot);
			    	   
			    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot));
			    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
						parameters.put("mnt_lettre", primetot_lettre);
					    
			    	   parameters.put("trimestriel", "X");
			           break;
			           
			       case 4:
			    	   primetot = detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue();
			    	   primetot_pour_lettre = (detailHayya.getPnette().doubleValue() + detailHayya.getFa().doubleValue()) * 1000;
			    	   primetot_lettre = Nombre.montantTND(primetot);
			    	   
			    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot));
			    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
						parameters.put("prime_nette", new BigDecimal( detailHayya.getPnette().doubleValue()));
						parameters.put("mnt_lettre", primetot_lettre);

			    	   parameters.put("mensuel", "X");
			           break;
			
			       default:
			    	   primetot = adhesion.getPrimetot();
			    	   primetot_pour_lettre = (adhesion.getPrimetot()) * 1000;
			    	   primetot_lettre = Nombre.montantTND(primetot);
			    	   
			    	   parameters.put("prime_tot", new java.text.DecimalFormat("#,##0.000").format(primetot));
			    	   parameters.put("primetot_pour_lettre", primetot_pour_lettre.intValue());
						parameters.put("prime_nette", detailHayya.getPnette());
						parameters.put("mnt_lettre", primetot_lettre);
						
			    	   parameters.put("unique", "X");
			           break;
			           
			           
			   }
				
				System.out.println("prime_tot= "+primetot);
		           System.out.println("primetot_pour_lettre= "+primetot_pour_lettre);
		           System.out.println("mnt_lettre= "+primetot_lettre);
				
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

//	            adhesion.setImpr(true);
//	            adhesionDAO.edit(adhesion);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			
		}
		
		
		if(adhesion.getPolice().getCodProdui().getLibelleProduit().equals("UNIVERSITY"))
		{
			
			System.out.println("notice UNIVERSITY " +adhesion.getPolice().getCodProdui().getLibelleProduit());
			
			/// notice
			try {
	            String report = reportController.getRealPath() + "notice_avj.jasper";         
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	            
	
				parameters.put("nom_benef", adhesion.getClient().getNomClient());
				parameters.put("prenom_benef", adhesion.getClient().getPrenomClient());
				parameters.put("cin_benef", adhesion.getClient().getIdentifiant());
				parameters.put("compte", adhesion.getClient().getNcompte());
				parameters.put("adr_assure", adhesion.getClient().getAdrClient());
				parameters.put("cpt", adhesion.getClient().getCodeP());
				parameters.put("tel_assure", adhesion.getClient().getTel());
				parameters.put("email", adhesion.getClient().getMail());				
				parameters.put("adresseagence", utilisateur.getAgence().getAdresse());
				
				Double primetot_avec_surprime = adhesion.getPrimetot() + adhesion.getDetailAvj().getSurprime().doubleValue(); 
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
	}
    
	
	
	 public Date getTo() {
	        return to;
	    }

	    public void setTo(Date to) {
	        this.to = to;
	    }

	    public Date getFrom() {
	        return from;
	    }

	    public void setFrom(Date from) {
	        this.from = from;
	    }
    
	    
	    public TarifMrh getParamPrices() {
			TarifMrh tarif805 = parametresMrhService.getTarifMrh();
			return tarif805;
		}

		public boolean isAffiche() {
			return affiche;
		}

		public void setAffiche(boolean affiche) {
			this.affiche = affiche;
		}
		
		
		public Adhesion getSelectedAdhesion() {
			return selectedAdhesion;
		}

		public void setSelectedAdhesion(Adhesion selectedAdhesion) {
			this.selectedAdhesion = selectedAdhesion;
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

/********************* impression impot *******************************************/
		
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
			System.out.println("dateImpotDebut "+dateImpotDebut);
			System.out.println("dateImpotFin "+dateImpotFin);
			
			String report = "";
			try {
				
				if(selectedAdhesion.getPolice().getCodProdui().getLibelleProduit().equals("UNIVERSITY")) {
					
					
					if(selectedAdhesion.getFraPolice().equalsIgnoreCase("P")) {
		            	
		            	report = reportController.getRealPath() + "attestation_impot_avj_prime_unique_v2.jasper";   
		            	
		            }else {
		            	
		            	report = reportController.getRealPath() + "attestation_impot_avj_v2.jasper";   
		            	
		            }
					
					
					
				}else {
					
					
					DetailHayya detailHayya = hayyaServiceInter.find_detail_by_Adhesion(selectedAdhesion);
					
					switch ((int) detailHayya.getFractHayya().getId_fract_hayya()) {

					 case 5:
			            	report = reportController.getRealPath() + "attestation_impot_7ayya_prime_unique_v2.jasper";   
			            	break;
			            
			            default:
			            	report = reportController.getRealPath() + "attestation_impot_7ayya_v2.jasper";   
			            	break;
			            	
					}
					
					
				}
   
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            
	            

				parameters.put("souscripteur", selectedAdhesion.getClient().getNomClient() + " " + selectedAdhesion.getClient().getPrenomClient());
				parameters.put("numplc", selectedAdhesion.getPolice().getNPolice());
				parameters.put("cin_sousc", selectedAdhesion.getClient().getIdentifiant());
				
				DateFormat df = new SimpleDateFormat("yyyy");
				
				DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");

				parameters.put("du", df2.format(dateImpotDebut));
				
				parameters.put("au", df2.format(dateImpotFin));
				
				parameters.put("id_adhesion", selectedAdhesion.getIdAdhesion());
				
				parameters.put("duree", (int)selectedAdhesion.getDuree());
				
				parameters.put("date_contrat", df2.format(selectedAdhesion.getDatDebut()));
			
				
				parameters.put("num_adhesion", selectedAdhesion.getNadhesion());
				
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
			
			String report = "";
			try {
				
				if(selectedAdhesion.getPolice().getCodProdui().getLibelleProduit().equals("UNIVERSITY")) {
					
					
					if(selectedAdhesion.getFraPolice().equalsIgnoreCase("P")) {
		            	
		            	report = reportController.getRealPath() + "attestation_impot_avj_prime_unique.jasper";   
		            	
		            }else {
		            	
		            	report = reportController.getRealPath() + "attestation_impot_avj.jasper";   
		            	
		            }
					
					
					
				}else {
					
					
					DetailHayya detailHayya = hayyaServiceInter.find_detail_by_Adhesion(selectedAdhesion);
					
					switch ((int) detailHayya.getFractHayya().getId_fract_hayya()) {

					 case 5:
			            	report = reportController.getRealPath() + "attestation_impot_7ayya_prime_unique.jasper";   
			            	break;
			            
			            default:
			            	report = reportController.getRealPath() + "attestation_impot_7ayya.jasper";   
			            	break;
			            	
					}
					
					
				}
   
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            
	            

				parameters.put("souscripteur", selectedAdhesion.getClient().getNomClient() + " " + selectedAdhesion.getClient().getPrenomClient());
				parameters.put("numplc", selectedAdhesion.getPolice().getNPolice());
				parameters.put("cin_sousc", selectedAdhesion.getClient().getIdentifiant());
				
				DateFormat df = new SimpleDateFormat("yyyy");
				
				DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");

				parameters.put("annee", df.format(dateImpot));
				
				parameters.put("id_adhesion", selectedAdhesion.getIdAdhesion());
				
				parameters.put("duree", (int)selectedAdhesion.getDuree());
				
				parameters.put("date_contrat", df2.format(selectedAdhesion.getDatDebut()));
			
				
				parameters.put("num_adhesion", selectedAdhesion.getNadhesion());
				
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
		
 /********************************************* ipmression autorisation de prelevement bancaire ************************/
		
		public void printAutoPrel(Adhesion adhesion) {
			
			
			if(adhesion.getPolice().getCodProdui().getLibelleProduit().contains("FUTURE DREAMS") ||
					adhesion.getPolice().getCodProdui().getLibelleProduit().contains("INVESTI") ||
					adhesion.getPolice().getCodProdui().getLibelleProduit().contains("UNIVERSITY"))
			{
				
				/// adhesion
				
				try {
		            String report = reportController.getRealPath() + "prelevement.jasper";         
		            Map<String, Object> parameters = new HashMap<String, Object>();
		            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		            
		            //parameters.put("agence", utilisateur.getAgence().getCodeAgence());
		            
					 
					//parameters.put("contrat",adhesion.getPolice().getNPolice());


					parameters.put("nom_assure", adhesion.getClient().getNomClient()+" "+adhesion.getClient().getPrenomClient());
					parameters.put("adresse", adhesion.getClient().getAdrClient());
					parameters.put("tel", adhesion.getClient().getTel());
					 
					
					parameters.put("email", adhesion.getClient().getMail() != null?adhesion.getClient().getMail():" ");
					
					parameters.put("num_contrat",adhesion.getNadhesion());
					
					parameters.put("raison_soc", utilisateur.getAgence().getSociete().getRaisonSoc());
					
					String nom_complet = utilisateur.getAgence().getSociete().getNomComplet();
					parameters.put("nom_complet", nom_complet.replace("<b>", "").replace("</b>", "") );
					
					parameters.put("agence", adhesion.getUtilisateur().getAgence().getLibelle());
					
					parameters.put("adresse_agence", adhesion.getUtilisateur().getAgence().getAdresse());
					
					 
					String rib = adhesion.getClient().getNcompte();
					
					parameters.put("rib", adhesion.getClient().getNcompte());
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
					
				 	
					parameters
					.put("star",  reportController.getLogoJasper());
					
					parameters.put("imagesociete", utilisateur.getAgence().getSociete()
							.getCodSociet() + ".png");
					
				
		            
			        parameters.put("report", reportController.getRealPath());
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

//		            adhesion.setImpr(true);
//		            adhesionDAO.edit(adhesion);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				

			}
			
		}


    
    /*
	 * private static void fillReport(Map parameters, JRBeanArrayDataSource
	 * arrayDataSource) { try { final JasperReport report =
	 * loadReport("/tn/tunisiamall/utils/facture.jasper");
	 * 
	 * JasperPrint jasperPrint = JasperFillManager.fillReport(report,
	 * parameters, arrayDataSource);
	 * 
	 * JRPrintPreview printPreview = new JRPrintPreview(jasperPrint);
	 * 
	 * printPreview.show(); } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * private static JasperReport loadReport(final String fileName) throws
	 * IOException, JRException { try (InputStream in =
	 * loadReportAsStream(fileName)) { return (JasperReport)
	 * JRLoader.loadObject(in); } }
	 * 
	 * private static InputStream loadReportAsStream(final String fileName) {
	 * final InputStream report =
	 * CartHistoryViewController.class.getResourceAsStream(fileName); if (report
	 * == null) { throw new RuntimeException("Report not found: " + fileName); }
	 * return report; }
     */
		
		
		public String getNadhesion() {
			return nadhesion;
		}

		public void setNadhesion(String nadhesion) {
			this.nadhesion = nadhesion;
		}

		public String getCin() {
			return cin;
		}

		public void setCin(String cin) {
			this.cin = cin;
		}

	 
		
		public String nomMois(Integer mois) {
			if (mois == null || mois < 1 || mois > 12) {
		        return "";
		    }
		    return MOIS[mois];
		}
		
		
}
