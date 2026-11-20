/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.context.RequestContext;
//import org.primefaces.context.PrimeRequestContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.star.Ba.commun.BusinessException;
import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAgenceDAO;
import com.star.Ba.dao.IPasswordHistoryDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IProfilDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.jsf.stb.StbCollaborateurService;
import com.star.Ba.jsf.stb.StbLookupResult;
import com.star.Ba.jsf.util.CryptPassword;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.jsf.util.JsfUtil.PersistAction;
import com.star.Ba.jsf.util.PasswordValidator;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.jsf.util.TokenSTB;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Client;
import com.star.Ba.model.PasswordHistory;
import com.star.Ba.model.Profil;
import com.star.Ba.model.SituationBlocageUser;
import com.star.Ba.model.SituationUser;
import com.star.Ba.model.Societe;
import com.star.Ba.model.Utilisateurs;
import com.sun.jersey.api.client.ClientResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.star.Ba.model.Produit;

/**
 * 
 * @author
 */
@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private IUtilisateursDAO utilisateursDAO;
	@EJB
	private IProfilDAO profilDAO;
	@EJB
	private IAgenceDAO agenceDAO;

	@ManagedProperty("#{gestionProfil}")
	private GestionProfil gestionProfil;
	
	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;
	
	
	@ManagedProperty("#{reportController}")
	private ReportController reportController;
	
	
	Login login = (Login) FacesContext.getCurrentInstance()
	        .getExternalContext()
	        .getSessionMap()
	        .get("login");

	private List<Utilisateurs> items = null;
	private Utilisateurs selected;
	private SituationUser selectedSituation;
	
	private String rePassword, rePasswordModif;
	
	private String currentPassword;

	private String msg = "";
	private Integer selectedAgence;
	private List<Profil> xbna = new ArrayList<Profil>();
	private List<Agence> allAgence;
	private BigDecimal selectedIdProfil;
	private Integer selectedIdAgence;
	private String selectedCodSociet;
	
	
	
	List<Produit> listProduits = new ArrayList<Produit>();
	private List<String> selectedProduits = new ArrayList<>();
	  @EJB
	  private IProduitDAO iProduitDAO;

	
	@EJB
	private IPasswordHistoryDAO passwordHistoryDAO;
	
	public SituationUser getSelectedSituation() {
		return selectedSituation;
	}

	public void setSelectedSituation(SituationUser selectedSituation) {
		this.selectedSituation = selectedSituation;
	}
	
	
	private boolean userTrouve;
	
	private String utlrLoginSTB;
	
    @EJB
    private StbCollaborateurService stbService;

	

	public UserBean() {
	}

	@PostConstruct
	private void initialiser() {
		this.initialiserAgences();
		initialiserListProfile();
		initialiserListProfileSociete();
		prepareCreate();
		
		listProduits = iProduitDAO.findAll();
		
		utilisateur.getProfil()
		.getCode()
		.equalsIgnoreCase(PofileEnum.AS.name());
		
		allUsers = gestionProfil.getAllUser();
		allUsersSocietes = gestionProfil.getAllUserSocietes();
		abna = agenceDAO.getagencebna();
		
		System.out.println("utilisateur.getProfil().getCode() " +utilisateur.getProfil().getCode());
		
 
		 afficherProduits = "ASI".equalsIgnoreCase(utilisateur.getProfil().getCode());

		 
		 userTrouve = false;
	}
	
	
	

    public void rechercherUtilisateurSTB(String action) {
    	
    	
        // Toujours repartir d'un état non validé
        userTrouve = false;
        utlrLoginSTB = null;
 
        if (selected.getLogin() == null || selected.getLogin().trim().isEmpty()) {
            addMessage(FacesMessage.SEVERITY_WARN, "Matricule obligatoire", "Veuillez saisir un matricule.");
            return;
        }
        
        // Conserver le matricule et vider le reste
        if("CREATION".equals(action)) {
        	System.out.println("resetApresRecherche");
        	resetApresRecherche();
        }
        
        else {
        	System.out.println("resetApresRecherchePourModif");
        	resetApresRecherchePourModif();
        }
        
 
        try {
            StbLookupResult result = stbService.rechercherUtilisateur(selected.getLogin(), reportController);
 
            switch (result.getStatus()) {
 
                case TOKEN_UNAVAILABLE:
                    addMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                            "Le service d'authentification STB ne répond pas actuellement. Veuillez réessayer dans quelques instants.");
                    return;
 
                case TOKEN_AUTH_ERROR:
                    addMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                            "Impossible de se connecter au service d'authentification STB. Veuillez réessayer ou contacter le support technique.");
                    return;
 
                case TOKEN_ACCESS_DENIED:
                    addMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                            "Accès refusé au service d'authentification STB (problème de droits d'accès). Veuillez contacter votre administrateur.");
                    return;
 
                case SERVICE_UNAVAILABLE:
                    addMessage(FacesMessage.SEVERITY_FATAL, "Erreur",
                            "Le service STB des collaborateurs ne répond pas actuellement. Veuillez réessayer dans quelques instants.");
                    return;
 
                case SERVICE_ERROR: {
                    String msg = "Le service STB des collaborateurs a retourné une erreur (code " + result.getCode()
                            + "). Veuillez réessayer ou contacter le support technique.";
                    if (result.getDetail() != null) {
                        msg += " Détail : " + result.getDetail();
                    }
                    addMessage(FacesMessage.SEVERITY_FATAL, "Erreur", msg);
                    return;
                }
 
                case USER_NOT_FOUND: {
                    // Cas STB "Rejected" / returncode 409 : matricule inexistant ou autre motif de rejet.
                    String msg = "Aucun utilisateur STB trouvé pour ce login (matricule inexistant ou rejeté par le service STB";
//                    if (result.getCode() != null) {
//                        msg += ", code " + result.getCode();
//                    }
                    msg += "). Veuillez vérifier le login saisi.";
                    addMessage(FacesMessage.SEVERITY_ERROR, "Erreur", msg);
                    return;
                }
 
                case INVALID_RESPONSE:
                    // Toute combinaison status/returncode qui n'est ni SUCCESS/000 ni Rejected/409.
                    addMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
                            "Le service STB des collaborateurs a renvoyé une réponse inattendue (code retour : " + result.getCode()
                                    + "). Veuillez contacter le support technique.");
                    return;
 
                case SUCCESS:
                	
                    /*
                     * On conserve séparément le matricule
                     * réellement retourné par STB.
                     */
                    utlrLoginSTB = result.getUtlrLogin();
                	
                    selected.setNom(result.getNom());
                    selected.setPrenom(result.getPrenom());
                    selected.setMail(result.getMail());
                    /*
                    Agence agence = affecterAgenceDepuisCodeSTB(
                            result.getCodeDepartement());

                    selected.setAgence(agence);
                    
                    if (agence != null) {
                        selectedIdAgence = agence.getIdAgence();
                    } else {
                        selectedIdAgence = null;
                    }
                    
                    System.out.println("Agence STB = "
                            + (agence != null ? agence.getCodeAgence() : null));

                    System.out.println("ID agence = "
                            + (agence != null ? agence.getIdAgence() : null));

                    System.out.println("selectedIdAgence = "
                            + selectedIdAgence);
                    */
                    userTrouve = true;
                    addMessage(FacesMessage.SEVERITY_INFO, "Succès",
                            "Utilisateur trouvé et informations récupérées depuis STB.");
                    return;
            }
 
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException be = BusinessException.findBusinessException(e);
            String msg = (be != null) ? be.getMessage()
                    : "Une erreur est survenue lors de la connexion au service STB. Veuillez réessayer plus tard ou contacter le support technique.";
            addMessage(FacesMessage.SEVERITY_ERROR, "Erreur: ", msg);
        }
    }

	
	
	
/*	
    public void rechercherUtilisateurSTB_v0() {

        userTrouve = false;

        if (selected.getLogin() == null || selected.getLogin().trim().isEmpty()) {

        	FacesContext.getCurrentInstance().addMessage(null,
        	        new FacesMessage(FacesMessage.SEVERITY_WARN,
        	                "Attention", "Veuillez saisir un login."));
            return;
        }

        try {
        	
        	String token = TokenSTB.tokenStb(reportController);
    		
    		if(token == null) {
    			
    			System.out.println("token null  ");
    			 			
    			FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Erreur",
                                "Le service d'authentification STB ne répond pas actuellement. Veuillez réessayer dans quelques instants."));
                return;
    			
    		} 
    		
			if(token.equalsIgnoreCase("exception")) {
							
				FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                            "Erreur",
	                            "Impossible de se connecter au service d'authentification STB. Veuillez réessayer ou contacter le support technique."));
	            return;
			}
			
			if(token.equalsIgnoreCase("not200")) {
							
	     		System.out.println("not200 token  ");
	     		 FacesContext.getCurrentInstance().addMessage(null,
	                     new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                             "Erreur",
	                             "Accès refusé au service d'authentification STB (problème de droits d'accès). Veuillez contacter votre administrateur."));
	             return;
			}
			
			final String URL_USER_STB = Constante.URL_USER_STB;
			
			String input = "{"
					+ "\"Matricule\":\""+selected.getLogin().trim()+"\""
					+ "}";
			
			   com.sun.jersey.api.client.Client client = JerseyClient.ApiClient(reportController);
			   ClientResponse response =
		                JerseyClient.PostReponseDuClientSTBCollaborateurs(
		                        URL_USER_STB,
		                        token,
		                        input,
		                        client,
		                        reportController);
			   
			    if ( response == null) {
					System.out.println("probleme de connexion vide");
					
					
					FacesContext.getCurrentInstance().addMessage(null,
		                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
		                            "Erreur",
		                            "Le service STB des collaborateurs ne répond pas actuellement. Veuillez réessayer dans quelques instants."));
		            return;
				}
			    
			    
			    
			    if (response.getStatus() != 200) {

			        String messageErreur = "Le service STB des collaborateurs a retourné une erreur (code "
			                + response.getStatus()
			                + "). Veuillez réessayer ou contacter le support technique.";

			        try {
			            String responseBody = response.getEntity(String.class);

			            if (responseBody != null && !responseBody.trim().isEmpty()) {

			                ObjectMapper objectMapper = new ObjectMapper();
			                JsonNode jsonNode = objectMapper.readTree(responseBody);

			                JsonNode messageNode = jsonNode.get("message");

			                if (messageNode != null
			                        && !messageNode.isNull()
			                        && !messageNode.asText().trim().isEmpty()) {

			                    messageErreur += " Détail : " + messageNode.asText();
			                }
			            }

			        } catch (Exception e) {
			            // En cas de problème de lecture du retour STB,
			            // on conserve simplement le message générique.
			        }

			        FacesContext.getCurrentInstance().addMessage(
			                null,
			                new FacesMessage(
			                        FacesMessage.SEVERITY_FATAL,
			                        "Erreur",
			                        messageErreur
			                )
			        );

			        return;
			    }
			    
				System.out.println("Output from Server .... \n");
				String output = response.getEntity(String.class);
				System.out.println(output);
				
				JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
				
		        // Vérification du status de l'API
		        String status = jsonOutput.optString("status", "");

		        if (!"SUCCESS".equalsIgnoreCase(status)) {

		            String returncode = jsonOutput.optString("returncode", "");

		            FacesContext.getCurrentInstance().addMessage(null,
		                    new FacesMessage(
		                            FacesMessage.SEVERITY_ERROR,
		                            "Erreur",
		                            "Le service STB des collaborateurs a renvoyé une réponse invalide (code retour : " + returncode + "). Veuillez contacter le support technique."));

		            return;
		        }
				

		        // Récupération de details
		        JSONArray details = jsonOutput.optJSONArray("details");

		        if (details == null || details.isEmpty()) {

		        	FacesContext.getCurrentInstance().addMessage(null,
		                    new FacesMessage(
		                            FacesMessage.SEVERITY_ERROR,
		                            "Erreur",
		                            "Aucun utilisateur STB trouvé pour ce login. Veuillez vérifier le login saisi."));
		            return;
		        }

		        // Premier utilisateur retourné
		        JSONObject userJson = details.getJSONObject(0);

		        String nom = userJson.optString("nom", "");
		        String prenom = userJson.optString("prenom", "");
		        String mail = userJson.optString("Mail", "");

		        // Affectation à ton objet selected
		        selected.setNom(nom);
		        selected.setPrenom(prenom);
		        selected.setMail(mail);

            userTrouve = true;

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Succès",
                            "Utilisateur trouvé et informations récupérées depuis STB."));


        } catch (Exception e) {
        	
        	System.out.println("partie Exception");

//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                            e.getMessage(), null));
            
            e.printStackTrace();

            BusinessException be = BusinessException.findBusinessException(e);

            String msg = (be != null)
                    ? be.getMessage()
                    : "Une erreur est survenue lors de la connexion au service STB. Veuillez réessayer plus tard ou contacter le support technique.";


            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Erreur: ",
                                    msg));
            
        }

    }
	*/
	

	public Utilisateurs getSelected() {
		return selected;
	}

	public void setSelected(Utilisateurs selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	public void prepareCreate() {
		selected = new Utilisateurs();
		selected.setConnecte(BigDecimal.ZERO);
		selected.setNbrTentative(0);
		//selected.setAgence(new Agence());
		selectedProduits = new ArrayList<String>();
		
		selectedIdAgence = null;
		
		userTrouve = false;
		rePassword = null;
		
		utlrLoginSTB = null;
		
		selectedIdProfil = null;
		selectedProduits = null;
	}
	
	public void prepareEditSociete() {

//	    if (selected != null
//	            && selected.getAgence() != null) {
//
//	        selectedSociete = selected.getAgence().getSociete();
//
//	    } else {
//
//	        selectedSociete = null;
//	    }
		
//		selectedSociete = null;
	}
	
	public void loadProduits() {
		
		 selectedProduits = new ArrayList<String>();
		 
		 List<Produit> produits = findByUser();  

		    if (produits != null) {
		        for (Produit p : produits) {
		            selectedProduits.add(String.valueOf(p.getIdProduit()));
		        }
		    }
		    
		    /// c'est pour lorsque dialog de modif s'ouvre
		    if (gestionProfil.isModeSTB()) {
		    	userTrouve = true;   
		    	utlrLoginSTB = selected.getLogin();
	        }
		    
	}
	
 
	
 

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
			+ "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
			+ "(\\.[A-Za-z]{2,})$";

	private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	private Matcher matcher;

	public void verifmail() {

		if (exitUsermail(selected.getMail())) {
			JsfUtil.addErrorMessage("e_mail deja existant");
			System.out.println("email existantt ============");

		}

	}

	public void create() {
		
		
		String loginAUtiliser;
		
		  if (gestionProfil.isModeSTB()) {

		        /*
		         * MODE STB
		         */
			  
			  
			  if (!userTrouve || utlrLoginSTB == null
		                || selected.getLogin() == null
		                || utlrLoginSTB.trim().isEmpty()
		                || !selected.getLogin().trim().equals(utlrLoginSTB.trim())) {

		            addMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
		                    "Le matricule a été modifié après la recherche STB. "
		                  + "Veuillez relancer une recherche avant d'ajouter l'utilisateur.");

		            userTrouve = false;
		            utlrLoginSTB = null;
		            return;
		        }
			  
			  

			  
			  
 
		        /*
		         * C'est le matricule retourné par STB,
		         * donc celui qui a réellement été validé.
		         */
		        loginAUtiliser = utlrLoginSTB.trim();

		    }else {
		    	
		    	
		    	// Mode manuel : on utilise directement la saisie
		        loginAUtiliser = selected.getLogin();
		    	
		    }
		  
		  
		  selected.setLogin(loginAUtiliser);
		
	 
 
		boolean valide = false;
		
		
		  if(utilisateur.getProfil().getCode()
					.equalsIgnoreCase(PofileEnum.AS.name())) {
		  
		  if ( selectedIdAgence == null || selectedIdAgence == 0) {
			  
			  addMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
	                    "L'agence est obligatoire");
			  
			  return;

			}
		  }
		  
		

		if (selected.getNom().isEmpty() || selected.getLogin().isEmpty()
				|| selected.getPassword().isEmpty()
				|| selected.getPrenom().isEmpty()
				|| selected.getMail().isEmpty() || selectedIdProfil == null || selectedIdProfil.compareTo(BigDecimal.ZERO) == 0) {
			JsfUtil.addErrorMessage("Vérifier les champs ");

		}

		else if (!pattern.matcher(selected.getMail()).matches()
				&& !selected.getMail().isEmpty()) {

			JsfUtil.addErrorMessage("Format E-mail Invalide ");

		} /*else if (exitUsermail(selected.getMail())) {

			JsfUtil.addErrorMessage("e_mail deja existant");

		}*/ else if (exitUserLogin(selected.getLogin())) {

			JsfUtil.addErrorMessage("Utilisateur déjà existant");

		}
		
		/*else if (selected.getLogin().equals(selected.getPassword())
				|| selected.getPassword().length() < 6) {

			JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");

		}*//*else if (!PasswordValidator.isStrongPassword(selected.getPassword())) {
		    System.out.println("Password is not strong.");
		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");
		    JsfUtil.showAlertMessageForPassword();
		}*/ else {
			
			
			 String profil = utilisateur.getProfil().getCode();
		   	  
	     	  if(     // admin star crée des admin stars produits qui font la creations des admin societes
	     			  profil.equalsIgnoreCase("ASI") ||  //admin info
	     			  profil.equalsIgnoreCase("VST") ||
	     			  profil.equalsIgnoreCase("TDST") ||
	     			  profil.equalsIgnoreCase("AST") ||
	     			  profil.equalsIgnoreCase("IST") ||
	     			  profil.equalsIgnoreCase("MHST") ||
	     			  profil.equalsIgnoreCase("VTST") ||
	     			  profil.equalsIgnoreCase("HST") ||
	     			 profil.equalsIgnoreCase("HQST")||
	     			profil.equalsIgnoreCase("MSTA") 
	     			  
	     			  // admin bna
	     			  //profil.equalsIgnoreCase("BNA") || // admin crée des agents
	     			  
	     			  // admin stb
//	     			  profil.equalsIgnoreCase("AS")   // admin societe crée un admin agent
	     			  //profil.equalsIgnoreCase("AA") c'est un admin agent qui crée des agents
	     			  ) {
	     		  
	     		 System.out.println("profil admin. (modification)");
	     		  
					/*
					 * if (!PasswordValidator.isStrongPasswordForAdmin(selected.getPassword())) {
					 * System.out.println("Password admin is not strong. (modification)");
					 * JsfUtil.showAlertMessageForPasswordAdmin(); return ; }
					 */
	     		  
	     	  }
	     	  
	     		// admin stb
     		  else if (profil.equalsIgnoreCase("AS")) {
     			  
     			  System.out.println("profil creation");
//     			 System.out.println("selectedIdProfil " +selectedIdProfil);
     		
     			 if (selectedIdProfil == null || selectedIdProfil.compareTo(BigDecimal.ZERO) == 0) {
     				JsfUtil.addErrorMessage("Vérifier le profil ");
     				return ;
     			}
     			 
				/*
				 * if(selectedIdProfil.compareTo(new BigDecimal("2")) == 0) {
				 * 
				 * if (!PasswordValidator.isStrongPasswordForAdmin(selected.getPassword())) {
				 * System.out.println("Password admin is not strong. (modification)");
				 * JsfUtil.showAlertMessageForPasswordAdmin(); return ; }
				 * 
				 * }else {
				 * 
				 * if (!PasswordValidator.isStrongPassword(selected.getPassword())) {
				 * System.out.println("Password is not strong. (modification)");
				 * JsfUtil.showAlertMessageForPassword(); return; }
				 * 
				 * }
				 */
     			  
     			  
     		  }
	     	  
	     	  else {
	     		  
	     		 System.out.println("profil non admin. (modification)");
	     		  
					/*
					 * if (!PasswordValidator.isStrongPassword(selected.getPassword())) {
					 * System.out.println("Password is not strong. (modification)");
					 * JsfUtil.showAlertMessageForPassword(); return; }
					 */
	     		  
	     	  }
	     	  
			
			
			
			
			
			System.out.println("avant persisitttt");
			//this.selected.setAgence(utilisateur.getAgence());
			//this.selected.setProfil(selected.getProfil());
			
			
//			System.out.println("selectedCodSociet " + selectedCodSociet);
//			
//			System.out.println("selectedIdAgence " + selectedIdAgence);
//			
//			
//			System.out.println("listSocieteRender " + listSocieteRender);
			
			if(listSocieteRender) { /// cas choisir des admin pour une societe specifique comme STB ou QNB
				
				
				Agence agencePRC = agenceDAO.FindAgencePrincipaleByCodeSociete(selectedCodSociet);
				
				selectedIdAgence = agencePRC.getIdAgence();
				
				selected.setAgence(new Agence(selectedIdAgence));
				
				
			}else {
				
			
			
			
			if( selectedIdAgence != null) {
				
				selected.setAgence(new Agence(selectedIdAgence));
			}
			else {
				selected.setAgence(utilisateur.getAgence());
			}
			
			
			}
		
			selected.setProfil(new Profil(selectedIdProfil));
			
			selected.setDateCreation(new Date());
			
			selected.setDateLastChangedPwd(new Date());
			
			selected.setFirstLogin(1);
			
	     	PasswordHistory passwordHistory = new PasswordHistory();
	      	passwordHistory.setUtilisateurs(selected);
	      	passwordHistory.setPassword(CryptPassword.encrypt(selected
	 					.getPassword()));
	      	passwordHistory.setChangedAt(new Date());
	      	
	      	List<PasswordHistory> histories = new ArrayList<>();
	      	histories.add(passwordHistory);
	      	
	      	selected.setPasswordHistories(histories);
	      	

	      	if(afficherProduits) {
	      	
	      	List<Long> ids = new ArrayList<Long>();
	      	if (selectedProduits != null) {
	      	    for (String id : selectedProduits) {
	      	        if (id != null && !"".equals(id)) {
	      	        	System.out.println("Produit ID = " + id);
	      	            ids.add(Long.valueOf(id));
	      	        }
	      	    }
	      	}
	      	
	      	
	      	List<Produit> produits = iProduitDAO.findAllById(ids);
	      	selected.setProduits(produits);
	      	
	      	}

			persist(PersistAction.CREATE, "Utilisateur cree avec success");

			System.out.println("apres persisitttt");

			if (!JsfUtil.isValidationFailed()) {
				items = null; // Invalidate list of items to trigger re-query.

			}
			valide = true;
			//01/11/2022
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("valide", valide);			
//			PrimeRequestContext.getCurrentInstance().getCallbackParams().put("valide", valide);

		}

	}
	
	public void createSociete() {
		boolean valide = false;

		if (selected.getNom().isEmpty() || selected.getLogin().isEmpty()
				|| selected.getPassword().isEmpty()
				|| selected.getPrenom().isEmpty()
				|| selected.getMail().isEmpty() || selectedIdProfil == null || selectedIdProfil.compareTo(BigDecimal.ZERO) == 0) {
			JsfUtil.addErrorMessage("Vérifier les champs ");

		}

		else if (!pattern.matcher(selected.getMail()).matches()
				&& !selected.getMail().isEmpty()) {

			JsfUtil.addErrorMessage("Format E-mail Invalide ");

		} /*else if (exitUsermail(selected.getMail())) {

			JsfUtil.addErrorMessage("e_mail deja existant");

		} */else if (exitUserLogin(selected.getLogin())) {

			JsfUtil.addErrorMessage("Utilisateur déjà existant");

		}	
		/*else if (selected.getLogin().equals(selected.getPassword())
				|| selected.getPassword().length() < 6) {

			JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");

		}*//*else if (!PasswordValidator.isStrongPassword(selected.getPassword())) {
		    System.out.println("Password is not strong.");
		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");
		    JsfUtil.showAlertMessageForPassword();
		}*/ else {
			
			
			 String profil = utilisateur.getProfil().getCode();
		   	  
	     	  if(     // admin star crée des admin stars produits qui font la creations des admin societes
	     			  profil.equalsIgnoreCase("ASI")   //admin info
	     			  
	     			  
	     			  // admin bna
	     			  //profil.equalsIgnoreCase("BNA") || // admin crée des agents
	     			  
	     			  // admin stb
//	     			  profil.equalsIgnoreCase("AS")   // admin societe crée un admin agent
	     			  //profil.equalsIgnoreCase("AA") c'est un admin agent qui crée des agents
	     			  ) {
	     		  
	     		 System.out.println("profil admin. (modification)");
	     		  
					/*
					 * if (!PasswordValidator.isStrongPasswordForAdmin(selected.getPassword())) {
					 * System.out.println("Password admin is not strong. (modification)");
					 * JsfUtil.showAlertMessageForPasswordAdmin(); return ; }
					 */
	     		  
	     	  }
 
	     	  
			
			
			
			
			
			System.out.println("avant persisitttt");
			//this.selected.setAgence(utilisateur.getAgence());
			//this.selected.setProfil(selected.getProfil());
			
			
//			System.out.println("selectedCodSociet " + selectedCodSociet);
//			
//			System.out.println("selectedIdAgence " + selectedIdAgence);
//			
//			
//			System.out.println("listSocieteRender " + listSocieteRender);
			
			
			Agence agencePRC = agenceDAO.FindAgencePrincipaleByCodeSociete(selectedCodSociet);
			
			selectedIdAgence = agencePRC.getIdAgence();
			
			selected.setAgence(new Agence(selectedIdAgence));
			
 
		
			selected.setProfil(new Profil(selectedIdProfil));
			
			selected.setDateCreation(new Date());
			
			selected.setDateLastChangedPwd(new Date());
			
			selected.setFirstLogin(1);
			
	     	PasswordHistory passwordHistory = new PasswordHistory();
	      	passwordHistory.setUtilisateurs(selected);
	      	passwordHistory.setPassword(CryptPassword.encrypt(selected
	 					.getPassword()));
	      	passwordHistory.setChangedAt(new Date());
	      	
	      	List<PasswordHistory> histories = new ArrayList<>();
	      	histories.add(passwordHistory);
	      	
	      	selected.setPasswordHistories(histories);
			

			persist(PersistAction.CREATE, "Utilisateur cree avec success");

			System.out.println("apres persisitttt");

			if (!JsfUtil.isValidationFailed()) {
				items = null; // Invalidate list of items to trigger re-query.

			}
			valide = true;
			//01/11/2022
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("valide", valide);			
//			PrimeRequestContext.getCurrentInstance().getCallbackParams().put("valide", valide);

		}

	}

	public void destroy() {
		persist(PersistAction.DELETE, "Utilisateur supprimer avec success");
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null; // Invalidate list of items to trigger re-query.
		}
	}

	public List<Utilisateurs> getItems() {
		if (items == null) {
			items = utilisateursDAO.findAll();
		}
		return items;
	}

	public boolean exitUserLogin(String login) {
		return utilisateursDAO.searchLogin(login) == null ? false : true;

	}

	public boolean exitUsermail(String mail) {
		return utilisateursDAO.searchmail(mail) == null ? false : true;

	}

	boolean initialiseTentative;

	public boolean isInitialiseTentative() {
		return initialiseTentative;
	}

	public void setInitialiseTentative(boolean initialiseTentative) {
		this.initialiseTentative = initialiseTentative;
	}
	
	
	
	boolean premiereConnexion;



	public boolean isPremiereConnexion() {
		return premiereConnexion;
	}

	public void setPremiereConnexion(boolean premiereConnexion) {
		this.premiereConnexion = premiereConnexion;
	}

	public void updateUser() {
		
		
		String loginAUtiliser;
		
		  if (gestionProfil.isModeSTB()) {

		        /*
		         * MODE STB
		         */
			  if (!userTrouve || utlrLoginSTB == null
		                || selected.getLogin() == null
		                || utlrLoginSTB.trim().isEmpty()
		                || !selected.getLogin().trim().equals(utlrLoginSTB.trim())) {

		            addMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
		                    "Le matricule a été modifié après la recherche STB. "
		                  + "Veuillez relancer une recherche avant d'ajouter l'utilisateur.");

		            userTrouve = false;
		            utlrLoginSTB = null;
		            return;
		        }
			  
			  

			  
			  

		        /*
		         * C'est le matricule retourné par STB,
		         * donc celui qui a réellement été validé.
		         */
		        loginAUtiliser = utlrLoginSTB.trim();

		    }else {
		    	
		    	
		    	// Mode manuel : on utilise directement la saisie
		        loginAUtiliser = selected.getLogin();
		    	
		    }
		  
		  
		  selected.setLogin(loginAUtiliser);
		
		
		
		
		
		boolean valide = false;
		
		
		  if(utilisateur.getProfil().getCode()
					.equalsIgnoreCase(PofileEnum.AS.name())) {
		  if ( selected.getAgence() == null || selected.getAgence().getIdAgence() == null) {
			  
			  addMessage(FacesMessage.SEVERITY_ERROR, "Erreur",
	                    "L'agence est obligatoire");
			  
			  return;

			}
		  }
		

		if (selected.getNom().isEmpty() || selected.getLogin().isEmpty() ||

		selected.getPrenom().isEmpty() || selected.getMail().isEmpty() ) {
			JsfUtil.addWarningMessage("Vérifier  les champs ");

		} /*else if (!PasswordValidator.isStrongPassword(selected.getNewPassword())) {
		    System.out.println("Password is not strong.");
		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");
		    JsfUtil.showAlertMessageForPassword();
		    
		}*/ else {
			
			
			
			 String profil = utilisateur.getProfil().getCode();
		   	  

			
			
			
          if(listSocieteRender) { /// cas choisir des admin pour une societe specifique comme STB ou QNB
				
        	  
        	  //System.out.println("selectedSociete " + selectedSociete.getCodSociet());
				
				Agence agencePRC = agenceDAO.FindAgencePrincipaleByCodeSociete(selectedSociete.getCodSociet());
				
				selectedIdAgence = agencePRC.getIdAgence();
				
				//System.out.println("selectedIdAgence " + selectedIdAgence);
				
				selected.setAgence(new Agence(selectedIdAgence));
				
				
			}

			
			if (!selected.getNewPassword().isEmpty()) {
				
				
		     	  if(     // admin star crée des admin stars produits qui font la creations des admin societes
		     			  profil.equalsIgnoreCase("ASI") ||  //admin info
		     			  profil.equalsIgnoreCase("VST") ||
		     			  profil.equalsIgnoreCase("TDST") ||
		     			  profil.equalsIgnoreCase("AST") ||
		     			  profil.equalsIgnoreCase("IST") ||
		     			  profil.equalsIgnoreCase("MHST") ||
		     			  profil.equalsIgnoreCase("VTST") ||
		     			  profil.equalsIgnoreCase("HST") ||
		     			 profil.equalsIgnoreCase("HQST") ||
		     			profil.equalsIgnoreCase("MSTA") 
		     			  
		     			  // admin bna
		     			  //profil.equalsIgnoreCase("BNA") || // admin crée des agents
		     			  
		     			  // admin stb
//		     			  profil.equalsIgnoreCase("AS")   // admin societe crée un admin agent
		     			  //profil.equalsIgnoreCase("AA") c'est un admin agent qui crée des agents
		     			  ) {
		     		  
		     		 System.out.println("profil admin. (modification)");
						/*
						 * if (!PasswordValidator.isStrongPasswordForAdmin(selected.getNewPassword())) {
						 * System.out.println("Password admin is not strong. (modification)");
						 * JsfUtil.showAlertMessageForPasswordAdmin(); return ; }
						 */
		     		  
		     		  
		      		 List<PasswordHistory> historiesTop3 = passwordHistoryDAO.findTop3ByUserOrderByChangedAtDesc(selected);
//		     		 boolean existeHistoriesTop = false;
		     		  
		     		  if(!historiesTop3.isEmpty()) {
		     			 System.out.println("PasswordHistory admin existed. (modification)");
		     			 
							/*
							 * for (PasswordHistory ph : historiesTop3) { if
							 * (selected.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.
							 * getPassword())) ) {
							 * JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des "
							 * + historiesTop3.size() + " derniers mots de passe."); return ; } }
							 */

		     		  }
		     		  
		     		  
		     		  
		     		  
		     	  }
		     	  
		     	  
		     		// admin stb
	     		  else if (profil.equalsIgnoreCase("AS")) {
	     			  
	     			  System.out.println("profil modification");
//	     			 System.out.println("selectedIdProfil " +selectedIdProfil);
	     		
	     			
	     			 
	     			 if(selected.getProfil().getCode().equalsIgnoreCase("AA")) {
	     				 
			     		 System.out.println("profil admin. (modification)");
			     		  
							/*
							 * if (!PasswordValidator.isStrongPasswordForAdmin(selected.getNewPassword())) {
							 * System.out.println("Password admin is not strong. (modification)");
							 * JsfUtil.showAlertMessageForPasswordAdmin(); return ; }
							 */
			     		  
			     		  
			      		 List<PasswordHistory> historiesTop3 = passwordHistoryDAO.findTop3ByUserOrderByChangedAtDesc(selected);
//			     		 boolean existeHistoriesTop = false;
			     		  
			     		  if(!historiesTop3.isEmpty()) {
			     			 System.out.println("PasswordHistory admin existed. (modification)");
			     			 
								/*
								 * for (PasswordHistory ph : historiesTop3) { if
								 * (selected.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.
								 * getPassword())) ) {
								 * JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des "
								 * + historiesTop3.size() + " derniers mots de passe."); return ; } }
								 */

			     		  }
	     				 
	     			 }else {
	     				 
			     		 System.out.println("profil non admin. (modification)");
			     		  
							/*
							 * if (!PasswordValidator.isStrongPassword(selected.getNewPassword())) {
							 * System.out.println("Password is not strong. (modification)");
							 * JsfUtil.showAlertMessageForPassword(); return; }
							 */
			     		  
			     		  
			       		 List<PasswordHistory> historiesTop10 = passwordHistoryDAO.findTop10ByUserOrderByChangedAtDesc(selected);
//			     		 boolean existeHistoriesTop = false;
			     		  
			     		  if(!historiesTop10.isEmpty()) {
			     			 System.out.println("PasswordHistory  existed. (modification)");
			     			 
								/*
								 * for (PasswordHistory ph : historiesTop10) { if
								 * (selected.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.
								 * getPassword())) ) {
								 * JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des "
								 * + historiesTop10.size() + " derniers mots de passe."); return ; } }
								 */

			     		  }
	     				 
	     			 }
	     			  
	     			  
	     		  }
		     	  
		     	  
		     	  else {
		     		  
		     		 System.out.println("profil non admin. (modification)");
		     		  
						/*
						 * if (!PasswordValidator.isStrongPassword(selected.getNewPassword())) {
						 * System.out.println("Password is not strong. (modification)");
						 * JsfUtil.showAlertMessageForPassword(); return; }
						 */
		     		  
		     		  
		       		 List<PasswordHistory> historiesTop10 = passwordHistoryDAO.findTop10ByUserOrderByChangedAtDesc(selected);
//		     		 boolean existeHistoriesTop = false;
		     		  
		     		  if(!historiesTop10.isEmpty()) {
		     			 System.out.println("PasswordHistory  existed. (modification)");
		     			 
							/*
							 * for (PasswordHistory ph : historiesTop10) { if
							 * (selected.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.
							 * getPassword())) ) {
							 * JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des "
							 * + historiesTop10.size() + " derniers mots de passe."); return ; } }
							 */

		     		  }
		     		  
		     		  
		     		  
		     	  }
		     	  
		     	 PasswordHistory passwordHistory = new PasswordHistory();
		      	passwordHistory.setUtilisateurs(selected);
		      	passwordHistory.setPassword(CryptPassword.encrypt(selected
		 					.getNewPassword()));
		      	passwordHistory.setChangedAt(new Date());
		      	
		      	List<PasswordHistory> histories = passwordHistoryDAO.findUserOrderByChangedAtDesc(selected);
		     	if(!histories.isEmpty()) {
		     		
		     		histories.add(passwordHistory);
		     	} else {
		     		
		     		histories = new ArrayList<>();
		     		histories.add(passwordHistory);
		     	}
					
		     	selected.setPasswordHistories(histories);
				
				selected.setPassword(CryptPassword.encrypt(selected
						.getNewPassword()));
				
				selected.setDateLastChangedPwd(new Date());
			}
			
			
			

			if (selected.getSituationUserToSave() != null) {
				selected.setSituation(selected.getSituationUserToSave()
						.getSituationNomber());
				
				if(selected.getSituationUserToSave().getSituationNomber()==1) {
					
					System.out.println("profil activé");
					selected.setDateReactivation(new Date());
				}else {
					
					System.out.println("profil désactivé");
					selected.setDateDesactivation(new Date());
				}
				
			}
			
			
			if (selected.getSituationBlocageUserToSave() != null) {
				selected.setSituationBlocage(selected.getSituationBlocageUserToSave()
						.getSituationNomber());
				
				if(selected.getSituationBlocageUserToSave().getSituationNomber()==1) {
					
					System.out.println("profil bloqué");
					selected.setDateBlocage(new Date());
				}else {
					
					System.out.println("profil débloqué");
					selected.setDateDeblocage(new Date());
				}
				
			}			
			
			

			if (initialiseTentative) {
				selected.setNbrTentative(0);
			}
			
			if (premiereConnexion) {
				selected.setFirstLogin(1);
			}else {
				selected.setFirstLogin(0);
			}
			
			
			if(afficherProduits) {
				
		  	List<Long> ids = new ArrayList<Long>();
	      	if (selectedProduits != null) {
	      	    for (String id : selectedProduits) {
	      	        if (id != null && !"".equals(id)) {
	      	        	System.out.println("Produit ID = " + id);
	      	            ids.add(Long.valueOf(id));
	      	        }
	      	    }
	      	}
	      	     	
	      	List<Produit> produits = iProduitDAO.findAllById(ids);
	      	selected.setProduits(produits);
	      	
			}
			
			// selected.setSituation(selectedSituation.getSituationNomber());
			utilisateursDAO.createUser(selected);
//			selected = new Utilisateurs();
			JsfUtil.addSuccessMessage("Utilisateur modifié avec succèss");
			
			
			
			valide = true;
			//01/11/2022
//			RequestContext context = RequestContext.getCurrentInstance();
//			context.addCallbackParam("valide", valide);		
			
		}
		


	}
	
	
	
	
	
	public void updateUserSociete() {
		
		boolean valide = false;

		if (selected.getNom().isEmpty() || selected.getLogin().isEmpty() ||

		selected.getPrenom().isEmpty() || selected.getMail().isEmpty() ) {
			JsfUtil.addWarningMessage("Vérifier  les champs ");

		} /*else if (!PasswordValidator.isStrongPassword(selected.getNewPassword())) {
		    System.out.println("Password is not strong.");
		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");
		    JsfUtil.showAlertMessageForPassword();
		    
		}*/ else {
			
			
			
			 String profil = utilisateur.getProfil().getCode();
		   	  

			
			
			
            /// cas choisir des admin pour une societe specifique comme STB ou QNB
				
        	  
        	  //System.out.println("selectedSociete " + selectedSociete.getCodSociet());
				
//				Agence agencePRC = agenceDAO.FindAgencePrincipaleByCodeSociete(selectedSociete.getCodSociet());
			 Agence agencePRC = agenceDAO.FindAgencePrincipaleByCodeSociete(selectedCodSociet);
				
				selectedIdAgence = agencePRC.getIdAgence();
				
				//System.out.println("selectedIdAgence " + selectedIdAgence);
				
				selected.setAgence(new Agence(selectedIdAgence));
				
				
//		          System.out.println("efefe "+gestionProfil.getProfileSociete().getCode());
//		          System.out.println("efefe "+gestionProfil.getProfileSociete().getLibelle());
		          
		          selected.setProfil(  gestionProfil.getProfileSociete() );

			
			if (!selected.getNewPassword().isEmpty()) {
				
				
		     	  if(     // admin star crée des admin stars produits qui font la creations des admin societes
		     			  profil.equalsIgnoreCase("ASI") ||  //admin info
		     			  profil.equalsIgnoreCase("VST") ||
		     			  profil.equalsIgnoreCase("TDST") ||
		     			  profil.equalsIgnoreCase("AST") ||
		     			  profil.equalsIgnoreCase("IST") ||
		     			  profil.equalsIgnoreCase("MHST") ||
		     			  profil.equalsIgnoreCase("VTST") ||
		     			  profil.equalsIgnoreCase("HST") ||
		     			 profil.equalsIgnoreCase("HQST") ||
		     			profil.equalsIgnoreCase("MSTA") 
		     			  
		     			  // admin bna
		     			  //profil.equalsIgnoreCase("BNA") || // admin crée des agents
		     			  
		     			  // admin stb
//		     			  profil.equalsIgnoreCase("AS")   // admin societe crée un admin agent
		     			  //profil.equalsIgnoreCase("AA") c'est un admin agent qui crée des agents
		     			  ) {
		     		  
		     		 System.out.println("profil admin. (modification)");
		     		  
						/*
						 * if (!PasswordValidator.isStrongPasswordForAdmin(selected.getNewPassword())) {
						 * System.out.println("Password admin is not strong. (modification)");
						 * JsfUtil.showAlertMessageForPasswordAdmin(); return ; }
						 */
		     		  
		     		  
		      		 List<PasswordHistory> historiesTop3 = passwordHistoryDAO.findTop3ByUserOrderByChangedAtDesc(selected);
//		     		 boolean existeHistoriesTop = false;
		     		  
		     		  if(!historiesTop3.isEmpty()) {
		     			 System.out.println("PasswordHistory admin existed. (modification)");
		     			 
							/*
							 * for (PasswordHistory ph : historiesTop3) { if
							 * (selected.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.
							 * getPassword())) ) {
							 * JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des "
							 * + historiesTop3.size() + " derniers mots de passe."); return ; } }
							 */

		     		  }
		     		  
		     		  
		     		  
		     		  
		     	  }
		     	  
		     	  
		     		// admin stb
	     		  else if (profil.equalsIgnoreCase("AS")) {
	     			  
	     			  System.out.println("profil modification");
//	     			 System.out.println("selectedIdProfil " +selectedIdProfil);
	     		
	     			
	     			 
	     			 if(selected.getProfil().getCode().equalsIgnoreCase("AA")) {
	     				 
			     		 System.out.println("profil admin. (modification)");
			     		  
							/*
							 * if (!PasswordValidator.isStrongPasswordForAdmin(selected.getNewPassword())) {
							 * System.out.println("Password admin is not strong. (modification)");
							 * JsfUtil.showAlertMessageForPasswordAdmin(); return ; }
							 */
			     		  
			     		  
			      		 List<PasswordHistory> historiesTop3 = passwordHistoryDAO.findTop3ByUserOrderByChangedAtDesc(selected);
//			     		 boolean existeHistoriesTop = false;
			     		  
			     		  if(!historiesTop3.isEmpty()) {
			     			 System.out.println("PasswordHistory admin existed. (modification)");
			     			 
								/*
								 * for (PasswordHistory ph : historiesTop3) { if
								 * (selected.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.
								 * getPassword())) ) {
								 * JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des "
								 * + historiesTop3.size() + " derniers mots de passe."); return ; } }
								 */

			     		  }
	     				 
	     			 }else {
	     				 
			     		 System.out.println("profil non admin. (modification)");
			     		  
							/*
							 * if (!PasswordValidator.isStrongPassword(selected.getNewPassword())) {
							 * System.out.println("Password is not strong. (modification)");
							 * JsfUtil.showAlertMessageForPassword(); return; }
							 */
			     		  
			     		  
			       		 List<PasswordHistory> historiesTop10 = passwordHistoryDAO.findTop10ByUserOrderByChangedAtDesc(selected);
//			     		 boolean existeHistoriesTop = false;
			     		  
			     		  if(!historiesTop10.isEmpty()) {
			     			 System.out.println("PasswordHistory  existed. (modification)");
			     			 
								/*
								 * for (PasswordHistory ph : historiesTop10) { if
								 * (selected.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.
								 * getPassword())) ) {
								 * JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des "
								 * + historiesTop10.size() + " derniers mots de passe."); return ; } }
								 */

			     		  }
	     				 
	     			 }
	     			  
	     			  
	     		  }
		     	  
		     	  
		     	  else {
		     		  
		     		 System.out.println("profil non admin. (modification)");
		     		  
						/*
						 * if (!PasswordValidator.isStrongPassword(selected.getNewPassword())) {
						 * System.out.println("Password is not strong. (modification)");
						 * JsfUtil.showAlertMessageForPassword(); return; }
						 */
		     		  
		     		  
		       		 List<PasswordHistory> historiesTop10 = passwordHistoryDAO.findTop10ByUserOrderByChangedAtDesc(selected);
//		     		 boolean existeHistoriesTop = false;
		     		  
		     		  if(!historiesTop10.isEmpty()) {
		     			 System.out.println("PasswordHistory  existed. (modification)");
		     			 
							/*
							 * for (PasswordHistory ph : historiesTop10) { if
							 * (selected.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.
							 * getPassword())) ) {
							 * JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des "
							 * + historiesTop10.size() + " derniers mots de passe."); return ; } }
							 */

		     		  }
		     		  
		     		  
		     		  
		     	  }
		     	  
		     	 PasswordHistory passwordHistory = new PasswordHistory();
		      	passwordHistory.setUtilisateurs(selected);
		      	passwordHistory.setPassword(CryptPassword.encrypt(selected
		 					.getNewPassword()));
		      	passwordHistory.setChangedAt(new Date());
		      	
		      	List<PasswordHistory> histories = passwordHistoryDAO.findUserOrderByChangedAtDesc(selected);
		     	if(!histories.isEmpty()) {
		     		
		     		histories.add(passwordHistory);
		     	} else {
		     		
		     		histories = new ArrayList<>();
		     		histories.add(passwordHistory);
		     	}
					
		     	selected.setPasswordHistories(histories);
				
				selected.setPassword(CryptPassword.encrypt(selected
						.getNewPassword()));
				
				selected.setDateLastChangedPwd(new Date());
			}
			
			
			

			if (selected.getSituationUserToSave() != null) {
				selected.setSituation(selected.getSituationUserToSave()
						.getSituationNomber());
				
				if(selected.getSituationUserToSave().getSituationNomber()==1) {
					
					System.out.println("profil activé");
					selected.setDateReactivation(new Date());
				}else {
					
					System.out.println("profil désactivé");
					selected.setDateDesactivation(new Date());
				}
				
			}
			
			if (selected.getSituationBlocageUserToSave() != null) {
				selected.setSituationBlocage(selected.getSituationBlocageUserToSave()
						.getSituationNomber());
				
				if(selected.getSituationBlocageUserToSave().getSituationNomber()==1) {
					
					System.out.println("profil bloqué");
					selected.setDateBlocage(new Date());
				}else {
					
					System.out.println("profil débloqué");
					selected.setDateDeblocage(new Date());
				}
				
			}	
			

			if (initialiseTentative) {
				selected.setNbrTentative(0);
			}
			
			if (premiereConnexion) {
				selected.setFirstLogin(1);
			}else {
				selected.setFirstLogin(0);
			}
			
			// selected.setSituation(selectedSituation.getSituationNomber());
			utilisateursDAO.createUser(selected);
//			selected = new Utilisateurs();
			JsfUtil.addSuccessMessage("Utilisateur modifié avec succèss");
			
			
			
			valide = true;
			//01/11/2022
//			RequestContext context = RequestContext.getCurrentInstance();
//			context.addCallbackParam("valide", valide);		
			
		}
		


	}

	private void persist(PersistAction persistAction, String successMessage) {
		if (selected != null) {

			try {

				if (!exitUserLogin(selected.getLogin())) {
					/*
					 * if (utilisateur.getProfil().getCode()
					 * .equalsIgnoreCase(PofileEnum.AS.name())) {
					 * System.out.println("enter to AS");
					 * System.out.println(selectedSociete
					 * .getCodSociet()+" "+selectedSociete.getRaisonSoc());
					 * Agence
					 * agence=gestionProfil.getPrincipaleAgence(selectedSociete
					 * ); System.out.println(agence.getCodeAgence()+" "+agence.
					 * getIdAgence());
					 * 
					 * selected.setAgence(agence);
					 * 
					 * }
					 * 
					 * 
					 * 
					 * else {
					 * 
					 * if (selected.getProfil().getCode()
					 * .equalsIgnoreCase(PofileEnum.G.name())) {
					 * selected.setAgence(utilisateur.getAgence()); }
					 * 
					 * 
					 * 
					 * else if
					 * (PofileEnum.ASI.getChildrensASI().contains(selected
					 * .getProfil().getCode())){
					 * selected.setAgence(agenceDAO.find(0)); }
					 * 
					 * }
					 */
					selected.setPassword(CryptPassword.encrypt(selected
							.getPassword()));
					selected.setSituation(1);
					
					utilisateursDAO.createUser(selected);
					selected = new Utilisateurs();
					JsfUtil.addSuccessMessage(successMessage);
				} else {
					JsfUtil.addWarningMessage("login déja exist");

				}

			} catch (EJBException ex) {
				String msg = "";
				Throwable cause = ex.getCause();
				if (cause != null) {
					msg = cause.getLocalizedMessage();
				}
				if (msg.length() > 0) {
					JsfUtil.addErrorMessage(msg);
				} else {
					JsfUtil.addErrorMessage(ex, "Error");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						null, ex);
				JsfUtil.addErrorMessage(ex, "Error");
			}
		}
	}

	public Utilisateurs getUtilisateurs(Integer id) {
		return utilisateursDAO.find(id);
	}

	public List<Utilisateurs> getItemsAvailableSelectMany() {
		return utilisateursDAO.findAll();
	}

	public List<Utilisateurs> getItemsAvailableSelectOne() {
		return utilisateursDAO.findAll();
	}

	@FacesConverter(forClass = Utilisateurs.class)
	public static class UtilisateursControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext,
				UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			UserBean controller = (UserBean) facesContext.getApplication()
					.getELResolver()
					.getValue(facesContext.getELContext(), null, "userBean");
			return controller.getUtilisateurs(getKey(value));
		}

		Integer getKey(String value) {
			Integer key;
			key = new Integer(value);
			return key;
		}

		String getStringKey(Integer value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext,
				UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Utilisateurs) {
				Utilisateurs o = (Utilisateurs) object;
				return getStringKey(o.getIdUtilisateurs());
			} else {
				Logger.getLogger(this.getClass().getName()).log(
						Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(),
								Utilisateurs.class.getName() });
				return null;
			}
		}

	}

	public GestionProfil getGestionProfil() {
		return gestionProfil;
	}

	public void setGestionProfil(GestionProfil gestionProfil) {
		this.gestionProfil = gestionProfil;
	}

	List<Profil> listProfile = new ArrayList<Profil>();
	List<Profil> listProfileSocietes = new ArrayList<Profil>();

	List<Utilisateurs> allUsers = new ArrayList<Utilisateurs>();
	List<Utilisateurs> allUsersSocietes = new ArrayList<Utilisateurs>();
	List<Utilisateurs> allUsersbna = new ArrayList<Utilisateurs>();

	List<Agence> abna = new ArrayList<Agence>();

	public List<Utilisateurs> getAllUsersbna() {
		allUsersbna = utilisateursDAO.find_utilisateurs_bna();
		return allUsersbna;
	}

	public List<Utilisateurs> getAllUsers() {
//		allUsers = gestionProfil.getAllUser();
		return allUsers;
	}
	
	public List<Utilisateurs> getAllUsersSocietes() {
//		allUsers = gestionProfil.getAllUserSocietes();
		return allUsersSocietes;
	}

	public List<Agence> agencebna() {

//		abna = agenceDAO.getagencebna();
		return abna;
	}

	public void initialiserListProfile() {
		listProfile = gestionProfil.getAllChildrensProfiles();
	}
	
	public void  initialiserListProfileSociete() {
		listProfileSocietes = gestionProfil.getAllChildrensProfilesSocietes();
	}

	public List<Agence> allAgence() {

		allAgence = agenceDAO.getagencestb();
		return allAgence;
	}

	public List<Profil> getListProfile() {
		return listProfile;
	}

	public void setListProfile(List<Profil> listProfile) {
		this.listProfile = listProfile;
	}
	
	public List<Profil> getListProfileSocietes() {
		return listProfileSocietes;
	}

	public void setListProfileSocietes(List<Profil> listProfileSocietes) {
		this.listProfileSocietes = listProfileSocietes;
	}

	public Converter getProfilConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (Profil profil : getListProfile()) {
					if (profil.getCode().equals(value)) {
						return profil;
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

	public Converter getSituationConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (SituationUser situationUser : getSituationUserList()) {
					if (situationUser.getSituationName().equals(value)) {
						return situationUser;
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
	
	
	public Converter getSituationBlocageConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (SituationBlocageUser situationBlocageUser : getSituationBlocageUserList()) {
					if (situationBlocageUser.getSituationName().equals(value)) {
						return situationBlocageUser;
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
	
	

	public List<Agence> initialiserAgences() {
		allAgence = gestionProfil.getAllAgence();
		for (Agence agence : allAgence) {
			if (agence.getCodeAgence().equalsIgnoreCase("PRC")) {
				allAgence.remove(agence);
				break;
			}

		}
		return allAgence;
	}

	public List<Agence> getAllAgence() {
		return allAgence;
	}

	/*
	 * List<Agence> allAgence;
	 * 
	 * public List<Agence> getAllAgence() {
	 * 
	 * 
	 * allAgence = agenceDAO.getagencestb(); HashMap<String, String>agences= new
	 * HashMap<String, String>(); for (Agence a : allAgence) {
	 * agences.put(a.getLibelle(), a.getIdAgence()+""); } return allAgence;
	 * 
	 * }
	 * 
	 * public HashMap<String, String> getAgences(){ getAllAgence();
	 * HashMap<String, String>agences= new HashMap<String, String>(); for
	 * (Agence a : allAgence) { agences.put(a.getLibelle(), a.getIdAgence()+"");
	 * } return agences; }
	 */

	public Converter getAgenceConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				List<Agence> allAgence = agenceDAO.getagencestb();

				for (Agence agence : allAgence) {
					System.out.println("converter before if ========");
					if (agence.getLibelle().equals(value)) {

						return agence;
					}
					System.out.println("converter after if ========");
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				System.out.println("getAsString ========");
				return value.toString();

			}
		};
	}

	private boolean listAgentRender;
	private boolean listAgentRender2;
	// private boolean listProfilRender;

	

	public void updatePasswordUserConnected() {
//		if (!utilisateur.getNewPassword().isEmpty()) {
//			if (utilisateur.getLogin().equals(utilisateur.getNewPassword())
//					|| utilisateur.getNewPassword().length() < 6) {
//
//				JsfUtil.addErrorMessage("mot de passe non suffisamment sécurisé");
//			} else {
//				utilisateur.setPassword(CryptPassword.encrypt(utilisateur
//						.getNewPassword()));
//				utilisateursDAO.createUser(utilisateur);
//				JsfUtil.addSuccessMessage("Utilisateur modifié avec succes");
//			}
//		}
		
		if(!utilisateur.getDecryptedPassword().equals(currentPassword)  ) {
			JsfUtil.addErrorMessage("Votre mot de passe actuel est incorrect");
			return ;
		}
		
		 String profil = utilisateur.getProfil().getCode();
   	  
     	  if(     // admins star
     			  profil.equalsIgnoreCase("ASI") ||
//     			  profil.equalsIgnoreCase("VST") ||
//     			  profil.equalsIgnoreCase("TDST") ||
//     			  profil.equalsIgnoreCase("AST") ||
//     			  profil.equalsIgnoreCase("IST") ||
//     			  profil.equalsIgnoreCase("MHST") ||
//     			  profil.equalsIgnoreCase("VTST") ||
//     			  profil.equalsIgnoreCase("HST") ||
//     			 profil.equalsIgnoreCase("HQST") ||
     			  
     			  // admin bna
     			  profil.equalsIgnoreCase("BNA") ||
     			  
     			  // admin stb
     			  profil.equalsIgnoreCase("AS") ||
     			  profil.equalsIgnoreCase("AA")
     			  ) {
     		  
     		 System.out.println("profil admin. (modification)");
     		  
     		  if (!PasswordValidator.isStrongPasswordForAdmin(utilisateur.getNewPassword())) {
       		    System.out.println("Password admin is not strong. (modification)");
       		   JsfUtil.showAlertMessageForPasswordAdmin();
       		    return ;
       		}
     		  
     		 List<PasswordHistory> historiesTop3 = passwordHistoryDAO.findTop3ByUserOrderByChangedAtDesc(utilisateur);
//     		 boolean existeHistoriesTop = false;
     		  
     		  if(!historiesTop3.isEmpty()) {
     			 System.out.println("PasswordHistory admin existed. (modification)");
     			 
     	        for (PasswordHistory ph : historiesTop3) {
     	            if (utilisateur.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.getPassword())) ) {
     	     			JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des " + historiesTop3.size() + " derniers mots de passe.");
     	     			 return ;
     	            }
     	        }	 

     		  }
     		  
     		  
     	  }else {
     		  
     		 System.out.println("profil non admin. (modification)");
     		  
     		  if (!PasswordValidator.isStrongPassword(utilisateur.getNewPassword())) {
         		    System.out.println("Password is not strong. (modification)");
         		   JsfUtil.showAlertMessageForPassword();
         		    return;
         		}
     		  
     		  
      		 List<PasswordHistory> historiesTop10 = passwordHistoryDAO.findTop10ByUserOrderByChangedAtDesc(utilisateur);
//     		 boolean existeHistoriesTop = false;
     		  
     		  if(!historiesTop10.isEmpty()) {
     			 System.out.println("PasswordHistory  existed. (modification)");
     			 
     	        for (PasswordHistory ph : historiesTop10) {
     	            if (utilisateur.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.getPassword())) ) {
     	     			JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des " + historiesTop10.size() + " derniers mots de passe.");
     	     			 return ;
     	            }
     	        }	 

     		  }
     		  
     	  }
     	  
     	  
     	 PasswordHistory passwordHistory = new PasswordHistory();
     	passwordHistory.setUtilisateurs(utilisateur);
     	passwordHistory.setPassword(CryptPassword.encrypt(utilisateur
					.getNewPassword()));
     	passwordHistory.setChangedAt(new Date());
     	
     	List<PasswordHistory> histories = passwordHistoryDAO.findUserOrderByChangedAtDesc(utilisateur);
     	if(!histories.isEmpty()) {
     		
     		histories.add(passwordHistory);
     	} else {
     		
     		histories = new ArrayList<>();
     		histories.add(passwordHistory);
     	}
     	
     	
//     	utilisateur.getPasswordHistories().add(passwordHistory);
     	
     	utilisateur.setPasswordHistories(histories);
     	  
     	  
     	 utilisateur.setPassword(CryptPassword.encrypt(utilisateur
					.getNewPassword()));
     	utilisateur.setDateLastChangedPwd(new Date());
     	
     	utilisateur.setFirstLogin(0);
     	
     	login.setFirstLogin(false);
     	
			utilisateursDAO.createUser(utilisateur);
			JsfUtil.addSuccessMessage("Mot de passe modifié avec succès");
		
	/*	
		if (!PasswordValidator.isStrongPassword(utilisateur.getNewPassword())) {
		    System.out.println("Password is not strong.");
		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");
		    JsfUtil.showAlertMessageForPassword();
		}else {
			
			utilisateur.setPassword(CryptPassword.encrypt(utilisateur
					.getNewPassword()));
			utilisateursDAO.createUser(utilisateur);
			JsfUtil.addSuccessMessage("Utilisateur modifié avec succes");
		}
		*/

	}
	
	
	
	public void updateEmailUserConnected() {
//		if (!utilisateur.getNewPassword().isEmpty()) {
//			if (utilisateur.getLogin().equals(utilisateur.getNewPassword())
//					|| utilisateur.getNewPassword().length() < 6) {
//
//				JsfUtil.addErrorMessage("mot de passe non suffisamment sécurisé");
//			} else {
//				utilisateur.setPassword(CryptPassword.encrypt(utilisateur
//						.getNewPassword()));
//				utilisateursDAO.createUser(utilisateur);
//				JsfUtil.addSuccessMessage("Utilisateur modifié avec succes");
//			}
//		}
		
		if(!utilisateur.getDecryptedPassword().equals(currentPassword)  ) {
			JsfUtil.addErrorMessage("Mot de passe incorrect");
			return ;
		}
		
   
			utilisateursDAO.createUser(utilisateur);
			JsfUtil.addSuccessMessage("Email modifié avec succès");
		
	/*	
		if (!PasswordValidator.isStrongPassword(utilisateur.getNewPassword())) {
		    System.out.println("Password is not strong.");
		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");
		    JsfUtil.showAlertMessageForPassword();
		}else {
			
			utilisateur.setPassword(CryptPassword.encrypt(utilisateur
					.getNewPassword()));
			utilisateursDAO.createUser(utilisateur);
			JsfUtil.addSuccessMessage("Utilisateur modifié avec succes");
		}
		*/

	}
	

	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}

	public boolean isListAgentRender2() {
		listAgentRender2 = utilisateur.getProfil().getCode()
				.equalsIgnoreCase(PofileEnum.BNA.name()) ? true : false;
		return listAgentRender2;
	}

	public void setListAgentRender2(boolean listAgentRender2) {
		this.listAgentRender2 = listAgentRender2;
	}

	public boolean isListAgentRender() {
		listAgentRender = utilisateur.getProfil().getCode()
				.equalsIgnoreCase(PofileEnum.AS.name()) ? true : false;
		return listAgentRender;
//		listAgentRender =
//	            utilisateur.getProfil().getCode().equalsIgnoreCase(PofileEnum.AS.name())
//	            || utilisateur.getProfil().getCode().equalsIgnoreCase(PofileEnum.AA.name());

//	    return listAgentRender;
	}

	public void setListAgentRender(boolean listAgentRender) {
		this.listAgentRender = listAgentRender;
	}

	private boolean listSocieteRender;

	public boolean isListSocieteRender() {

		listSocieteRender = PofileEnum.ASI.getChildrensASI().contains(
				utilisateur.getProfil().getCode()) ? true : false;
		// if(PofileEnum.ASI.getChildrensASI().contains(utilisateur.getProfil().getCode())
		// || utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name()))
		// {
		// listSocieteRender=true;
		// }
		// else
		// {
		// listSocieteRender=false;
		// }
		// System.out.println("Bolean="+listSocieteRender+" code="+utilisateur.getProfil().getCode());
		// listSocieteRender = utilisateur.getProfil().getCode()
		// .equalsIgnoreCase(PofileEnum.AST.name()) ? true : false;
		return listSocieteRender;
	}
	
	
	private boolean afficherProduits;

	public boolean isAfficherProduits() {				 
		 return afficherProduits;
	}

	public void setAfficherProduits(boolean afficherProduits) {
		this.afficherProduits = afficherProduits;
	}





	@EJB
	private ISocieteDAO serviceSociete;

	List<Societe> allSocietes = new ArrayList<Societe>();

	public List<Societe> getAllSocietes() {
		allSocietes = serviceSociete.findAll();

		return allSocietes;
	}

	public void setAllSocietes(List<Societe> allSocietes) {
		this.allSocietes = allSocietes;
	}

	private Societe selectedSociete;

	public Societe getSelectedSociete() {
		return selectedSociete;
	}

	public void setSelectedSociete(Societe selectedSociete) {
		this.selectedSociete = selectedSociete;
	}

	public Converter getSocieteConverter_() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (Societe societe : getAllSocietes()) {
					if (societe.getRaisonSoc().equals(value)) {
						return societe;
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
	
	public Converter getSocieteConverter() {
	    return new Converter() {

	        @Override
	        public Object getAsObject(FacesContext context,
	                                   UIComponent component,
	                                   String value) {

	            if (value == null || value.trim().isEmpty()) {
	                return null;
	            }

	            for (Societe societe : getAllSocietes()) {

	                if (String.valueOf(societe.getCodSociet()).equals(value)) {
	                    return societe;
	                }
	            }

	            return null;
	        }

	        @Override
	        public String getAsString(FacesContext context,
	                                   UIComponent component,
	                                   Object value) {

	            if (value == null) {
	                return "";
	            }

	            Societe societe = (Societe) value;

	            return String.valueOf(societe.getCodSociet());
	        }
	    };
	}
	
	
	
	public void synchroniserSociete() {

	    selectedSociete = selected.getAgence().getSociete();
	}
	
	

	public List<SituationUser> situationUserList = new ArrayList<SituationUser>();

	public List<SituationUser> getSituationUserList() {
		if (situationUserList.isEmpty()) {
			situationUserList.add(new SituationUser(1, "Activé"));
			situationUserList.add(new SituationUser(2, "Désactivé"));
		}
		return situationUserList;
	}

	public void setSituationUserList(List<SituationUser> situationUserList) {
		this.situationUserList = situationUserList;
	}
	
	
	
	public List<SituationBlocageUser> situationBlocageUserList = new ArrayList<SituationBlocageUser>();

	public List<SituationBlocageUser> getSituationBlocageUserList() {
		if (situationBlocageUserList.isEmpty()) {
			situationBlocageUserList.add(new SituationBlocageUser(1, "Bloqué"));
			situationBlocageUserList.add(new SituationBlocageUser(0, "Débloqué"));
		}
		return situationBlocageUserList;
	}

	public void setSituationBlocageUserList(List<SituationBlocageUser> situationBlocageUserList) {
		this.situationBlocageUserList = situationBlocageUserList;
	}
	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public BigDecimal getSelectedIdProfil() {
		return selectedIdProfil;
	}

	public void setSelectedIdProfil(BigDecimal selectedIdProfil) {
		this.selectedIdProfil = selectedIdProfil;
	}

	public Integer getSelectedIdAgence() {
		return selectedIdAgence;
	}

	public void setSelectedIdAgence(Integer selectedIdAgence) {
		this.selectedIdAgence = selectedIdAgence;
	}

	public String getSelectedCodSociet() {
		return selectedCodSociet;
	}

	public void setSelectedCodSociet(String selectedCodSociet) {
		this.selectedCodSociet = selectedCodSociet;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getRePasswordModif() {
		return rePasswordModif;
	}

	public void setRePasswordModif(String rePasswordModif) {
		this.rePasswordModif = rePasswordModif;
	}

	public List<Produit> getListProduits() {
		return listProduits;
	}

	public void setListProduits(List<Produit> listProduits) {
		this.listProduits = listProduits;
	}

	public List<String> getSelectedProduits() {
		return selectedProduits;
	}

	public void setSelectedProduits(List<String> selectedProduits) {
		this.selectedProduits = selectedProduits;
	}
	
	
	public List<Produit> findByUser(){
		System.out.println("findByUser()");
		System.out.println("afficherProduits_1 " + afficherProduits);
		 
		
		  if (afficherProduits && selected != null ) {
			  System.out.println("selected not null" );
		        return iProduitDAO.findByUser(selected);
		    }

		    return new ArrayList<Produit>();
		 
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
	
	
    public boolean isUserTrouve() {
        return userTrouve;
    }

    public void setUserTrouve(boolean userTrouve) {
        this.userTrouve = userTrouve;
    }

	public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}
    
	
	
    
    
	
	public String getUtlrLoginSTB() {
	    return utlrLoginSTB;
	}

	public void setUtlrLoginSTB(String utlrLoginSTB) {
	    this.utlrLoginSTB = utlrLoginSTB;
	}
	
	

	// Petit utilitaire pour éviter de répéter FacesContext.getCurrentInstance().addMessage(...) partout.
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    
    
    private Agence affecterAgenceDepuisCodeSTB(String codeDepartement) {

        if (codeDepartement == null) {
            return null;
        }

        codeDepartement = codeDepartement.trim();

        // 1 caractère de préfixe + code agence
        if (codeDepartement.length() < 2) {
            return null;
        }

        // Exemple : C212 -> 212
        String codeAgence = codeDepartement.substring(1);
        
        System.out.println("codeDepartement " + codeDepartement);
        System.out.println("codeAgence " + codeAgence);

        if (allAgence == null || allAgence.isEmpty()) {
            return null;
        }

        for (Agence agence : allAgence) {

            if (codeAgence.equals(agence.getCodeAgence())) {
                return agence;
            }
        }

        return null;
    }

    
    public void loginModifie() {

        // En mode manuel, aucune validation STB à invalider
        if (!gestionProfil.isModeSTB()) {
            return;
        }

        // En mode STB, une modification du matricule
        // invalide la recherche précédente
        userTrouve = false;
        utlrLoginSTB = null;
    }
    
    
    
    private void resetApresRecherche() {

        // Conserver le matricule actuellement saisi
        String login = selected != null
                ? selected.getLogin()
                : null;
        
        

        // Réinitialiser l'utilisateur
        selected = new Utilisateurs();

        selected.setConnecte(BigDecimal.ZERO);
        selected.setNbrTentative(0);

        // Restaurer le matricule
        selected.setLogin(login);
        
        

        // Réinitialiser les autres données
        selectedIdAgence = null;
        selectedIdProfil = null;
        selectedProduits = new ArrayList<String>();

        rePassword = null;

        userTrouve = false;
        utlrLoginSTB = null;
    }
    
    
    private void resetApresRecherchePourModif() {
    	
    	selected.setNom(null);
    	selected.setPrenom(null);
    	selected.setMail(null);
    	
//    	selectedIdAgence = null;

 
        userTrouve = false;
        utlrLoginSTB = null;
    }
	
	
}