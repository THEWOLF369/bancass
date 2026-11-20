/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.jsf;


import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.star.Ba.dao.IPasswordHistoryDAO;
import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.dto.UtilisateursConnexionDTO;
import com.star.Ba.jsf.util.CryptPassword;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.jsf.util.PasswordValidator;
import com.star.Ba.model.Menu;
import com.star.Ba.model.PasswordHistory;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.IConnexionService;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.star.Ba.commun.Constante;
/**
 *
 * @author star
 */
@ManagedBean(name = "login")
@SessionScoped
//@ViewScoped
public class Login implements Serializable {


	@EJB
    private IConnexionService iconnexionService;
	private String login, password, reNewPassword;
    private boolean loggedIn = false,desactive;
    
    private boolean loggedInV2 = true;
    
    private boolean passwordChanged = true;
    
    private String newMdp, reNewMdp;
    private boolean weekMdp = false;
    
    private boolean expiredMdp = false;
    
    private boolean button_connexion = true;
    private boolean button_valider = true;
    
    private boolean still_connexion = true;
    
    
    private static int MAX_FAILED_ATTEMPTS_ADMIN = 3; //3 pour admin
    
    private static int MAX_FAILED_ATTEMPTS_AGENT = 6; // 6 pour agent
    
    private static int VALIDITE_ADMIN = 60;
    
    private static int VALIDITE_AGENT = 90;

    @EJB
    private IUtilisateursDAO iUtilisateursDAO;
    private Utilisateurs utilisateur;
    private UtilisateursConnexionDTO utilisateursConnexionDTO;
    private List<Menu> menus;
//    private String path = Constante.PDF + utilisateur.getAgence().getSociete().getCodSociet();
    private boolean searchClient;
    
    private HtmlInputSecret passwordInput;
    
    private boolean showPassword;

    
	@EJB
	private IPasswordHistoryDAO passwordHistoryDAO;
	
	
	boolean compteDesactive = false;
	boolean compteBloque = false;
	boolean loginMdpIncorrecte = false;
	boolean firstLogin = false;
	 
  
    
    public void retourConnexion() {
    	
      weekMdp = false;
      expiredMdp = false;
      loggedIn = true;
      loggedInV2 = true;
      still_connexion = true;
    }
    
    public void reset() {
    	
    	login = null;
    	password = null;
    	
	    compteDesactive = false;
	    compteBloque = false;
	    loginMdpIncorrecte = false;
	    expiredMdp = false;
	    loggedIn=false;
	    still_connexion = true;
	    nombreTentativeDepasser = false;
	    

	    utilisateur=null;
	    utilisateursConnexionDTO=null;
	    firstLogin = false;
    }
    
      public Login() {
//        cancel();
//        searchClient = true;
    }
      
      
  	@ManagedProperty("#{reportController}")
  	private ReportController reportController;
  	
  	
      
  	String input = "{"
	  		+ "                \"CODE_AGENCE\": 826,"
	  		+ "                \"CODE_UTILISATEUR\": 625,"
	  		+ "                \"NOTE_COUVERTURE\": \"0000000000\","
	  		+ "                \"CODE_BRANCHE\": 61,"
	  		+ "                \"CODE_SOUS_BRANCHE\": \"685\","
	  		+ "                \"CODE_CLIENT\": \"639189\","
	  		+ "                \"CODE_DUREE\": \"61\","
	  		+ "                \"DATE_EFFET_POLICE\": \"06/08/2021\","
	  		+ "                \"DATE_EXPIRATION\": \"06/08/2031\","
	  		+ "                \"Caracteristiques\": ["
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"2\","
	  		+ "                                               \"VALEUR_CARACT\": \"4.000\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"112\","
	  		+ "                                               \"VALEUR_CARACT\": \"10\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"103\","
	  		+ "                                               \"VALEUR_CARACT\": \"200.000\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"11\","
	  		+ "                                               \"VALEUR_CARACT\": \"4\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"4\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"106\","
	  		+ "                                               \"VALEUR_CARACT\": \"1\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"1\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"100\","
	  		+ "                                               \"VALEUR_CARACT\": \"4\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"4\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"131\","
	  		+ "                                               \"VALEUR_CARACT\": \"0.000\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"124\","
	  		+ "                                               \"VALEUR_CARACT\": \"2\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"2\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_CARACTERISTIQUE\": \"109\","
	  		+ "                                               \"VALEUR_CARACT\": \"0\","
	  		+ "                                               \"VALEUR_DEPENDE_CARACT\": \"0\""
	  		+ "                               }"
	  		+ "                ],"
	  		+ "                \"Garanties\": ["
	  		+ "                               {"
	  		+ "                                               \"CODE_GARANTIE\": \"1\","
	  		+ "                                               \"CAPITAL_ASSUREE\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_GARANTIE\": \"2\","
	  		+ "                                               \"CAPITAL_ASSUREE\": \"0\""
	  		+ "                               },"
	  		+ "                               {"
	  		+ "                                               \"CODE_GARANTIE\": \"3\","
	  		+ "                                               \"CAPITAL_ASSUREE\": \"0\""
	  		+ "                               }"
	  		+ "                ]"
	  		+ "}"
	  		+ "";
      
      
      public String connexion_() throws Exception {
  		
    		System.out.println("Testing 2 - Send Http POST request \n");
    		
    		
    		try {
    			
    			
    			Form f = new Form();
    			f.add("DataForCalculPrime", input);
    			f.add("saveProposition", false);

    			com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
    			client.addFilter(new GZIPContentEncodingFilter(false));
    			WebResource resource = client.resource("http://172.31.44.31/PROASSUR.APIS.INTEG/api/devis/CalculerPrimeDevisVie");
//    			String response = resource.accept(MediaType.APPLICATION_XML_TYPE,
//    			        MediaType.APPLICATION_JSON_TYPE).
//    			        header("MyHeader", "Jersey Client")
//    			       .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
//    			       .post(String.class, f);
//    			
//    			System.out.print(response);
    			
    			ClientResponse response = resource.accept(MediaType.APPLICATION_XML_TYPE,
    			        MediaType.APPLICATION_JSON_TYPE).
    			        header("MyHeader", "Jersey Client")
    			       .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
    			       .post(ClientResponse.class, f);
    			
    			System.out.print(response + " " + "\n");
    			
    			 JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON( response.getEntity(String.class) ); 
       		   
        		  System.out.println(jsonOutput);
        		  
        		 System.out.println(jsonOutput.toString());
        		  
        		 System.out.println(jsonOutput.getString("message"));
    			
    			/*
    			System.out.println("respo2");
    			
    			ClientResponse response2 = resource.accept("application/json").get(ClientResponse.class);

    			System.out.println(response2.getEntityTag());
    			System.out.println(response2.getEntity(String.class));
    			System.out.println(response2.getHeaders());
    			System.out.println(response2.getClient());
    			System.out.println(response2.getClientResponseStatus());
    			System.out.println(response2.getEntityInputStream());
    			System.out.println(response2.getProperties().values());
    			System.out.println(response2.getStatus());
    			
    			
    			System.out.println("respo3");
    			
    			ClientResponse response3 = resource.accept("application/json").post(ClientResponse.class, f);
    			
    			System.out.println(response3.getEntityTag());
    			System.out.println(response3.getEntity(String.class));
    			System.out.println(response3.getHeaders());
    			System.out.println(response3.getClient());
    			System.out.println(response3.getClientResponseStatus());
    			System.out.println(response3.getEntityInputStream());
    			System.out.println(response3.getProperties().values());
    			System.out.println(response3.getStatus());
    			
    			
//    			System.out.println("respo4");
//    			
//    			resource.accept(MediaType.APPLICATION_XML_TYPE,
//    			        MediaType.APPLICATION_JSON_TYPE);
//
//    			MultivaluedMap<String, String> params = new MultivaluedMapImpl();
//    			params.add("DataForCalculPrime", input);
//    			 
//
//    			String response4 = resource.path("/").
//    			        queryParams(params).
//    			        get(String.class);
//    			
//    			System.out.println(response4);
    			
    			
    			System.out.println("respo5");
    			
    			ClientResponse response5 = resource.accept("application/json").get(ClientResponse.class);
    			
    			String output = response5.getEntity(String.class);
    			 
    		     System.out.println("Output from Server .... \n");
    		     System.out.println(output);
    		     
    		     
    		     System.out.println("respo6");
     			
     			ClientResponse response6 = resource.accept(MediaType.APPLICATION_JSON).
     					header("content-type", MediaType.APPLICATION_JSON).get(ClientResponse.class);
     			
     			String output6 = response6.getEntity(String.class);
     			 
     		     System.out.println("Output from Server .... \n");
     		     System.out.println(output6);
     		     
     		     
    		     System.out.println("respo7");
      			
     			String response7 = resource.accept(MediaType.APPLICATION_JSON).
     					header("content-type", MediaType.APPLICATION_JSON).get(String.class);
     			
     			String output7 = response7.toString();
     			 
     		     System.out.println("Output from Server .... \n");
     		     System.out.println(output7);
    			
     		     
     		    System.out.println("respo8");
     		     
     		    ClientResponse response8 = resource.type("application/json")
     	                .get(ClientResponse.class);
     		     
     		     
     		   JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON( response8.getEntity(String.class) ); 
     		   
     		  System.out.println(jsonOutput);
     		  
     		 System.out.println(jsonOutput.toString());
     		  
     		 System.out.println(jsonOutput.getString("message"));

     		*/ 
//    			com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
//
//    	        WebResource webResource = client
//    	           .resource("http://172.31.44.31/PROASSUR.APIS.INTEG/api/devis/CalculerPrimeDevisVie");
//
//    	        //String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";
//    	        
//    	        MultivaluedMap formData = new MultivaluedMapImpl();
//    	        formData.add("DataForCalculPrime", input);
//
//    	        ClientResponse response = webResource.type("application/x-www-form-urlencoded")
//    	           .post(ClientResponse.class, formData);
//
//    	        if (response.getStatus() != 200) {
//    	            throw new RuntimeException("Failed : HTTP error code : "
//    	                 + response.getStatus());
//    	        }
//
//    	        System.out.println("Output from Server .... \n");
//    	        System.out.println(response.getStatus());
//    	        System.out.println(response.getClient());
//    	        //String output = response.getEntity(String.class);
//    	        String output = response.toString();
//    	        System.out.println(output);

    	      } catch (Exception e) {

    	        e.printStackTrace();

    	      }

    	     
    		
    		
    		//sendPost();
    			return null;
    		
    		}
      
      
      private final OkHttpClient httpClient = new OkHttpClient();
      private void sendPost() throws Exception {

    	    // form parameters
    	    RequestBody formBody = new FormBody.Builder()
    	    		 .add("DataForCalculPrime",input)
//    	            .add("username", "abc")
//    	            .add("password", "123")
//    	            .add("custom", "secret")
    	            .build();

    	    Request request = new Request.Builder()
//    	            .url("https://httpbin.org/post")
    	            .url("http://172.31.44.31/PROASSUR.APIS.INTEG/api/devis/CalculerPrimeDevisVie")
    	            .addHeader("User-Agent", "OkHttp Bot")
    	            .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_TYPE.toString())
    	            //.header("Content-Type", "application/x-www-form-urlencoded")
    	            .post(formBody)
    	            .build();

    	    try (Response response = httpClient.newCall(request).execute()) {

    	        //if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

    	        // Get response body
    	        System.out.println(response.body().string());
    	    }  

    	}
      
      
      public void ChangingPassword() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
    	  
    	  still_connexion = true;
    	  
    	  if(expiredMdp) {
    		  
    		  resetExpiredPasswordForConnexion();
    		  
    	  }
    	  
    	  if(weekMdp) {
    		  
    		  newPasswordForConnexion();
    	  }
      }
      
      
      public String resetExpiredPasswordForConnexion() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
    	  
    	  System.out.println("resetExpiredPasswordForConnexion begin");
    	  
    	  
    	  nombreTentativeDepasser=false;
          desactive=false;
          loggedIn=true;
          
    	  utilisateur = iUtilisateursDAO.searchLogin(login);
    	  
 /******* cas utilisateur introuvable ************************/
    	  
    	  if(utilisateur==null) {
    		  
    		  loggedIn = false;

    		  
    		  System.out.println(" expired profil introuvable");
    		  
    		  return null;
    	  } 
    	  
    	  /******* cas utilisateur existant ************************/    	  
    	  else {
    		  
    		  
    		  System.out.println(" expired profil trouvable");
    		  
    		  loggedIn = true;
    		  
    		  String profil = utilisateur.getProfil().getCode();
    		  
//     		 int limit_before = 99;
     		 int MAX_FAILED_ATTEMPTS = 100;    		 
     		int nbr_tent_actuel = utilisateur.getNbrTentative();
     		
      		 if(     // admins star
          			  profil.equalsIgnoreCase("ASI") ||
//          			  profil.equalsIgnoreCase("VST") ||
//          			  profil.equalsIgnoreCase("TDST") ||
//          			  profil.equalsIgnoreCase("AST") ||
//          			  profil.equalsIgnoreCase("IST") ||
//          			  profil.equalsIgnoreCase("MHST") ||
//          			  profil.equalsIgnoreCase("VTST") ||
//          			  profil.equalsIgnoreCase("HST") ||
//          			  profil.equalsIgnoreCase("HQST") ||
          			  
          			  // admin bna
          			  profil.equalsIgnoreCase("BNA") ||
          			  
          			  // admin stb
          			  profil.equalsIgnoreCase("AS") ||
          			  profil.equalsIgnoreCase("AA")
          			  ) {
       			 
//       			 limit_before = 2; //2
       			MAX_FAILED_ATTEMPTS = MAX_FAILED_ATTEMPTS_ADMIN;  //3
       			 
       		 }else {
       			 
//       			 limit_before = 5; //5
       			MAX_FAILED_ATTEMPTS = MAX_FAILED_ATTEMPTS_AGENT;  //6
       		 }

     		
      		 // cas profil bloqué suite tentative
   		  if(nbr_tent_actuel >= MAX_FAILED_ATTEMPTS){
			  desactive=false; 
			  nombreTentativeDepasser=true;
			  System.out.println("expired utilisateur depassed");
			  
			  return null;
		  }
    		  
     		
     		// cas profil desactivé
    		  if(new Integer(utilisateur.getSituation()).equals(2)){
    			  desactive=true ; 
    			  nombreTentativeDepasser=false;
    			  System.out.println("expired utilisateur desactived");
    			  
    			  return null;
    		  }
    		  
    		  
    		// tentative de connexion
    		  
    		  utilisateursConnexionDTO = iconnexionService.connexion(login, CryptPassword.encrypt(getPassword()));
    		  
    		  
    	        if(utilisateursConnexionDTO!=null)
    	        {
    	    /*****succes de tentative ****/
    	        	utilisateur=utilisateursConnexionDTO.getUser();
    	        	menus=utilisateursConnexionDTO.getMenu();
    	        	
    	        	System.out.println("expired succes de tentative");
    	        	
    	         	  /// vérifier mot de passe court ou non
    	        	  
    	        	  

    	        	  
    	        	  if(     // admins star
    	        			  profil.equalsIgnoreCase("ASI") ||
//    	        			  profil.equalsIgnoreCase("VST") ||
//    	        			  profil.equalsIgnoreCase("TDST") ||
//    	        			  profil.equalsIgnoreCase("AST") ||
//    	        			  profil.equalsIgnoreCase("IST") ||
//    	        			  profil.equalsIgnoreCase("MHST") ||
//    	        			  profil.equalsIgnoreCase("VTST") ||
//    	        			  profil.equalsIgnoreCase("HST") ||
//    	        			  profil.equalsIgnoreCase("HQST") ||
    	        			  
    	        			  // admin bna
    	        			  profil.equalsIgnoreCase("BNA") ||
    	        			  
    	        			  // admin stb
    	        			  profil.equalsIgnoreCase("AS") ||
    	        			  profil.equalsIgnoreCase("AA")
    	        			  ) {
    	        		  
    	        		  
    	        		  System.out.println("profil admin.");
    	        		  
    	        		  if (!PasswordValidator.isStrongPasswordForAdmin(newMdp)) {
    	          		    System.out.println("Password admin is not strong.");
//    	          		     weekMdp = true;
    	          		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé, veuillez le changer");
    	          		   JsfUtil.showAlertMessageForPasswordAdmin();
    	          		    return null;
    	          		}
    	        		  
    	          		 List<PasswordHistory> historiesTop3 = passwordHistoryDAO.findTop3ByUserOrderByChangedAtDesc(utilisateur);
//    	         		 boolean existeHistoriesTop = false;
    	         		  
    	         		  if(!historiesTop3.isEmpty()) {
    	         			 System.out.println("PasswordHistory admin existed. (modification)");
    	         			 
    	         	        for (PasswordHistory ph : historiesTop3) {
    	         	            if (newMdp.equalsIgnoreCase(CryptPassword.decrypt(ph.getPassword())) ) {
    	         	     			JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des " + historiesTop3.size() + " derniers mots de passe.");
    	         	     			 return null;
    	         	            }
    	         	        }	 

    	         		  }
    	        		  
    	        		  
    	        	  } else {  // non admin
    	        		  
    	        		  System.out.println("profil non admin.");
    	        		  
    	        		  if (!PasswordValidator.isStrongPassword(newMdp)) {
    	            		    System.out.println("Password is not strong.");
//    	            		     weekMdp = true;
    	            		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé, veuillez le changer");
    	            		   JsfUtil.showAlertMessageForPassword();
    	            		    return null;
    	            		}
    	        		  
    	        		  
    	           		 List<PasswordHistory> historiesTop10 = passwordHistoryDAO.findTop10ByUserOrderByChangedAtDesc(utilisateur);
//    	         		 boolean existeHistoriesTop = false;
    	         		  
    	         		  if(!historiesTop10.isEmpty()) {
    	         			 System.out.println("PasswordHistory  existed. (modification)");
    	         			 
    	         	        for (PasswordHistory ph : historiesTop10) {
    	         	            if (newMdp.equalsIgnoreCase(CryptPassword.decrypt(ph.getPassword())) ) {
    	         	     			JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des " + historiesTop10.size() + " derniers mots de passe.");
    	         	     			 return null;
    	         	            }
    	         	        }	 

    	         		  }
    	        		  
    	        		  
    	        	  }
    	        	  
    	        	  
    	        	  
    	          	 PasswordHistory passwordHistory = new PasswordHistory();
    	          	passwordHistory.setUtilisateurs(utilisateur);
    	          	passwordHistory.setPassword(CryptPassword.encrypt(newMdp));
    	          	passwordHistory.setChangedAt(new Date());
    	          	
    	          	List<PasswordHistory> histories = passwordHistoryDAO.findUserOrderByChangedAtDesc(utilisateur);
    	          	if(!histories.isEmpty()) {
    	          		
    	          		histories.add(passwordHistory);
    	          	} else {
    	          		
    	          		histories = new ArrayList<>();
    	          		histories.add(passwordHistory);
    	          	}
    	          	
    	          	utilisateur.setPasswordHistories(histories);
    	        	  
    	        	    
//    		  			weekMdp = false;
    	        	  expiredMdp = false;
    		  			utilisateur.setSituation(1);
    	                utilisateur.setNbrTentative(0);
//    	              utilisateur.setDateLastConnection(new Date());
    	              utilisateur.setPassword(CryptPassword.encrypt(newMdp));
  	    			utilisateur.setDateLastChangedPwd(new Date());
    	                iUtilisateursDAO.edit(utilisateur);
    	                loggedIn = true;
    	                loggedInV2 = true;
    	                  desactive=false;
    	                  nombreTentativeDepasser=false;
    	                  
//    	                  passwordChanged = true;
    	                  
    	    			
//    	    			iUtilisateursDAO.createUser(utilisateur);
    	  			JsfUtil.addSuccessMessage("Mot de passe modifié avec succes");
    	                  
    	                  
    	                  return null;
    	        	
    	        	
    	        	
    	        	
    	        }else{
    	        	
    	 /******echec de tentative suite mdp erroné ****/
//    	        	utilisateur=null;
    	        	
    	        	System.out.println("expired echec de tentative suite mdp erroné");
    	  /*      	
    	        	utilisateur.setNbrTentative(nbr_tent_actuel+1);
    	        	
    	    	    if (utilisateur.getNbrTentative() >= MAX_FAILED_ATTEMPTS) {
    	    	    	utilisateur.setSituation(2);
    	    	    	 utilisateur.setDateDesactivation(new Date());
    	    	    	 nombreTentativeDepasser=true ; 
    	    	    	 
    	    	    	 
    	    	    	 System.out.println("expired profil devenu bloqué");
    	    	    }
    	    	    
    	    	    iUtilisateursDAO.edit(utilisateur);
    	  */  	    
//  	            	loggedInV2 = false;
  	            	JsfUtil.addErrorMessage("Veuillez saisir l'ancien mot de passe pour le changer");
    	    	    
    	    	    return null;
    	        	
    	        	
    	        }
    		  
    		  
    		  
    	  }
    	  
      }
      
      
      
      
      
      
      
      
      public String newPasswordForConnexion() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
    	  
    	  System.out.println("newPasswordForConnexion begin");
    	  
    	  weekMdp = true;
    	  
    	  
          nombreTentativeDepasser=false;
          desactive=false;
          loggedIn=true;
          loggedInV2=true;
          
    	  
    	  
    	  
    	  utilisateur = iUtilisateursDAO.searchLogin(login);
    	  
 /******* cas utilisateur introuvable ************************/
    	  
    	  if(utilisateur==null) {
    		  
    		  loggedIn = false;
    		  loggedInV2 = false;
    		  
    		  System.out.println(" newcnx profil introuvable");
    		  
    		  return null;
    	  } 
    	  
/******* cas utilisateur existant ************************/    	  
    	  else {
    		  
    		  
    		  System.out.println(" newcnx profil trouvable");
    		  
    		  loggedIn = true;
    		  
    		  String profil = utilisateur.getProfil().getCode();
    		  
//     		 int limit_before = 99;
     		 int MAX_FAILED_ATTEMPTS = 100;    		 
     		int nbr_tent_actuel = utilisateur.getNbrTentative();
     		
      		 if(     // admins star
          			  profil.equalsIgnoreCase("ASI") ||
//          			  profil.equalsIgnoreCase("VST") ||
//          			  profil.equalsIgnoreCase("TDST") ||
//          			  profil.equalsIgnoreCase("AST") ||
//          			  profil.equalsIgnoreCase("IST") ||
//          			  profil.equalsIgnoreCase("MHST") ||
//          			  profil.equalsIgnoreCase("VTST") ||
//          			  profil.equalsIgnoreCase("HST") ||
//          			  profil.equalsIgnoreCase("HQST") ||
          			  
          			  // admin bna
          			  profil.equalsIgnoreCase("BNA") ||
          			  
          			  // admin stb
          			  profil.equalsIgnoreCase("AS") ||
          			  profil.equalsIgnoreCase("AA")
          			  ) {
       			 
//       			 limit_before = 2; //2
       			MAX_FAILED_ATTEMPTS = MAX_FAILED_ATTEMPTS_ADMIN;  //3
       			 
       		 }else {
       			 
//       			 limit_before = 5; //5
       			MAX_FAILED_ATTEMPTS = MAX_FAILED_ATTEMPTS_AGENT;  //6
       		 }

     		
      		 // cas profil bloqué suite tentative
   		  if(nbr_tent_actuel >= MAX_FAILED_ATTEMPTS){
			  desactive=false; 
			  nombreTentativeDepasser=true;
			  System.out.println("newcnx utilisateur depassed");
			  
			  return null;
		  }
    		  
     		
     		// cas profil desactivé
    		  if(new Integer(utilisateur.getSituation()).equals(2)){
    			  desactive=true ; 
    			  nombreTentativeDepasser=false;
    			  System.out.println("newcnx utilisateur desactived");
    			  
    			  return null;
    		  }
    		  
    		  
    		// tentative de connexion
    		  
    		  utilisateursConnexionDTO = iconnexionService.connexion(login, CryptPassword.encrypt(getPassword()));
    		  
    		  
    	        if(utilisateursConnexionDTO!=null)
    	        {
    	    /*****succes de tentative ****/
    	        	utilisateur=utilisateursConnexionDTO.getUser();
    	        	menus=utilisateursConnexionDTO.getMenu();
    	        	
    	        	System.out.println("newcnx succes de tentative");
    	        	
    	         	  /// vérifier mot de passe court ou non
    	        	  
    	        	  

    	        	  
    	        	  if(     // admins star
    	        			  profil.equalsIgnoreCase("ASI") ||
//    	        			  profil.equalsIgnoreCase("VST") ||
//    	        			  profil.equalsIgnoreCase("TDST") ||
//    	        			  profil.equalsIgnoreCase("AST") ||
//    	        			  profil.equalsIgnoreCase("IST") ||
//    	        			  profil.equalsIgnoreCase("MHST") ||
//    	        			  profil.equalsIgnoreCase("VTST") ||
//    	        			  profil.equalsIgnoreCase("HST") ||
//    	        			  profil.equalsIgnoreCase("HQST") ||
    	        			  
    	        			  // admin bna
    	        			  profil.equalsIgnoreCase("BNA") ||
    	        			  
    	        			  // admin stb
    	        			  profil.equalsIgnoreCase("AS") ||
    	        			  profil.equalsIgnoreCase("AA")
    	        			  ) {
    	        		  
    	        		  
    	        		  System.out.println("profil admin.");
    	        		  
    	        		  if (!PasswordValidator.isStrongPasswordForAdmin(newMdp)) {
    	          		    System.out.println("Password admin is not strong.");
    	          		     weekMdp = true;
    	          		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé, veuillez le changer");
    	          		   JsfUtil.showAlertMessageForPasswordAdmin();
    	          		    return null;
    	          		}
    	        		  
    	        		  

    	        		  
    	        		  
    	        	  } else {  // non admin
    	        		  
    	        		  System.out.println("profil non admin.");
    	        		  
    	        		  if (!PasswordValidator.isStrongPassword(newMdp)) {
    	            		    System.out.println("Password is not strong.");
    	            		     weekMdp = true;
    	            		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé, veuillez le changer");
    	            		   JsfUtil.showAlertMessageForPassword();
    	            		    return null;
    	            		}
    	        		  
    	        		  

    	        		  
    	        		  
    	        	  }
    	        	  
    	        	  
    	        	  
    	          	 PasswordHistory passwordHistory = new PasswordHistory();
    	          	passwordHistory.setUtilisateurs(utilisateur);
    	          	passwordHistory.setPassword(CryptPassword.encrypt(newMdp));
    	          	passwordHistory.setChangedAt(new Date());
    	          	
    	          	List<PasswordHistory> histories = passwordHistoryDAO.findUserOrderByChangedAtDesc(utilisateur);
    	          	if(!histories.isEmpty()) {
    	          		
    	          		histories.add(passwordHistory);
    	          	} else {
    	          		
    	          		histories = new ArrayList<>();
    	          		histories.add(passwordHistory);
    	          	}
    	          	
    	          	utilisateur.setPasswordHistories(histories);
    	        	  
    	        	    
    		  			weekMdp = false;
    		  			utilisateur.setSituation(1);
    	                utilisateur.setNbrTentative(0);
    	    			utilisateur.setPassword(CryptPassword.encrypt(newMdp));
    	    			utilisateur.setDateLastChangedPwd(new Date());
    	                iUtilisateursDAO.edit(utilisateur);
    	                loggedIn = true;
    	                loggedInV2 = true;
    	                  desactive=false;
    	                  nombreTentativeDepasser=false;
    	                  
    	                  passwordChanged = true;
    	                  

//    	    			iUtilisateursDAO.createUser(utilisateur);
    	  			JsfUtil.addSuccessMessage("Mot de passe modifié avec succes");
    	                  
    	                  
    	                  return null;
    	        	
    	        	
    	        	
    	        	
    	        }else{
    	        	
    	 /******echec de tentative suite mdp erroné ****/
//    	        	utilisateur=null;
    	        	
    	        	System.out.println("newcnx echec de tentative suite mdp erroné");
    	    /*    	
    	        	utilisateur.setNbrTentative(nbr_tent_actuel+1);
    	        	
    	    	    if (utilisateur.getNbrTentative() >= MAX_FAILED_ATTEMPTS) {
    	    	    	utilisateur.setSituation(2);
    	    	    	 utilisateur.setDateDesactivation(new Date());
    	    	    	 nombreTentativeDepasser=true ; 
    	    	    	 
    	    	    	 
    	    	    	 System.out.println("newcnx profil devenu bloqué");
    	    	    }
    	    	    
    	    	    iUtilisateursDAO.edit(utilisateur);
    	    */
  	            	loggedInV2 = false;
  	            	JsfUtil.addErrorMessage("Veuillez saisir l'ancien mot de passe pour le changer");
    	    	    
    	    	    return null;
    	        	
    	        	
    	        }
    		  
    		  
    		  
    	  }
          
          
          ///////////////////////////////
    	  
 
    	  
      }
      
      
      public String Authentification() {
    	  
    	  System.out.println("DEBUT Authentification()");
    	  
    	  
    	    compteDesactive = false;
    	    compteBloque = false;
    	    loginMdpIncorrecte = false;
    	    boolean isPasswordExpired = false;
    	    expiredMdp = false;
    	    loggedIn=false;
    	    still_connexion = true;
    	    nombreTentativeDepasser = false;
    	    firstLogin = false;
    	    
    	    
    	    
    	    if (login == null || getPassword() == null
                    || login.trim().isEmpty()
                    || getPassword().trim().isEmpty()) {

                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Erreur", "Tous les champs sont obligatoires"));
                return null;
            }
    	    
    	  
    	  utilisateur = iUtilisateursDAO.searchLogin(login);
    	  
 /******* cas utilisateur introuvable ************************/
    	  
    	  if(utilisateur==null) {
    		  
//    		  loggedIn = false;
//    		  
//        	  still_connexion = false;
    		  
    		  loginMdpIncorrecte = true;
    		  
    		  System.out.println(" cnx profil introuvable");
    		  
    		  return null;
    	  }
    	  
    	  
/******* cas utilisateur existant ************************/      	  
    	  
    	  System.out.println(" cnx profil trouvable");
		  
		  String profil = utilisateur.getProfil().getCode();
		  
		  

		  if (utilisateur.getSituation() == Constante.SITUATION_COMPTE_DESACTIVE) {
			  System.out.println("cnx utilisateur desactived");
		      compteDesactive = true;
		      return null;
		  } 
		  
				  
		  if (utilisateur.getSituationBlocage() == Constante.SITUATION_COMPTE_BLOQUE) {
			  System.out.println("cnx utilisateur bloqued");
			  compteBloque = true;
			  return null;
		  } 
		  
		  
		  
	        Date today = new Date();
	        Date givenDate = utilisateur.getDateLastChangedPwd();
		  
		  
  		 int MAX_FAILED_ATTEMPTS = 100;    		 
  		int nbr_tent_actuel = utilisateur.getNbrTentative();
		  
   		 if(     // admins star
     			  profil.equalsIgnoreCase("ASI") ||
     			  
     			  // admin bna
     			  profil.equalsIgnoreCase("BNA") ||
     			  
     			  // admin stb
     			  profil.equalsIgnoreCase("AS") ||
     			  profil.equalsIgnoreCase("AA")
     			  ) {
  			 
//  			 limit_before = 2; //2
  			MAX_FAILED_ATTEMPTS = MAX_FAILED_ATTEMPTS_ADMIN;  //3
  			
  	        
  	  // Utiliser Calendar pour calculer la date 60 jours après la date donnée
  			Calendar calendar = Calendar.getInstance();
  			calendar.setTime(givenDate);
  	        calendar.add(Calendar.DAY_OF_YEAR, VALIDITE_ADMIN);
  	        Date datePlus60Days = calendar.getTime();
  	      isPasswordExpired = today.after(datePlus60Days);
  			
  			 
  		 }else {
  			 
//  			 limit_before = 5; //5
  			MAX_FAILED_ATTEMPTS = MAX_FAILED_ATTEMPTS_AGENT;  //6
  			
        	  // Utiliser Calendar pour calculer la date 90 jours après la date donnée
        			Calendar calendar = Calendar.getInstance();
        			calendar.setTime(givenDate);
        	        calendar.add(Calendar.DAY_OF_YEAR, VALIDITE_AGENT);
        	        Date datePlus90Days = calendar.getTime();
        	        isPasswordExpired = today.after(datePlus90Days);       			
  			
  		 }
   		 
   		 
   		if(isPasswordExpired){
   			expiredMdp = true;
			  System.out.println("cnx mdp expired");		  
			  return null;
		  }
   		
   		
		   	 utilisateursConnexionDTO = iconnexionService.connexion(login, CryptPassword.encrypt(getPassword()));
		   	 
		   	 
		   	 /******echec de tentative suite mdp erroné ****/ 
		   	 
		   	if(utilisateursConnexionDTO==null) {
		   		
		   	
		  	
		    	System.out.println("cnx echec de tentative suite mdp erroné");
		    	
		    	utilisateur.setNbrTentative(nbr_tent_actuel+1);
		    	
		    	loginMdpIncorrecte = true;

			    if (utilisateur.getNbrTentative() >= MAX_FAILED_ATTEMPTS) {
			    	utilisateur.setSituationBlocage(Constante.SITUATION_COMPTE_BLOQUE);
			    	 utilisateur.setDateBlocage(new Date());
			    	 nombreTentativeDepasser=true ; 
			    	 loginMdpIncorrecte = false;

			    	 compteBloque = true;

			    	 
			    	 System.out.println("cnx profil devenu bloqué");
			    }
			    
			    iUtilisateursDAO.edit(utilisateur);
			    
			    return null;
		   		
		   	}
		   	
		   	
		   	/*****succes de tentative ****/
		   	System.out.println("cnx succes de tentative");
		   	
		   	utilisateur=utilisateursConnexionDTO.getUser();
        	menus=utilisateursConnexionDTO.getMenu();
        	
        	 
//        	utilisateur.setSituation(1);
            utilisateur.setNbrTentative(0);
            utilisateur.setDateLastConnection(new Date());
            iUtilisateursDAO.edit(utilisateur);
            loggedIn = true;

        	still_connexion = false;
        	
        	
        	if(utilisateur.getFirstLogin()==1) {
        		firstLogin = true;
        		return "FirstLogin";
        	}
        	  
        	  
              return "success";
    	  
  
    	  
      }
      

      public String connexion() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
 
    	  System.out.println("connexion()");
    	  
    	  button_connexion = true;
    	  
    	  weekMdp = false;
    	  
    	  expiredMdp = false;
    	  

    	  
    	  
          nombreTentativeDepasser=false;
          desactive=false;
          loggedIn=true;
    	  
    	  
    	  
    	  
    	  utilisateur = iUtilisateursDAO.searchLogin(login);
    	  
 /******* cas utilisateur introuvable ************************/
    	  
    	  if(utilisateur==null) {
    		  
    		  loggedIn = false;
    		  
        	  still_connexion = false;
    		  
    		  System.out.println(" cnx profil introuvable");
    		  
    		  return null;
    	  } 
    	  
/******* cas utilisateur existant ************************/    	  
    	  else {
    		  
    		  
    		  System.out.println(" cnx profil trouvable");
    		  
    		  String profil = utilisateur.getProfil().getCode();
    		  
    		  boolean isPasswordExpired = false;
     	        Date today = new Date();
     	        Date givenDate = utilisateur.getDateLastChangedPwd();
    		  
//     		 int limit_before = 99;
     		 int MAX_FAILED_ATTEMPTS = 100;    		 
     		int nbr_tent_actuel = utilisateur.getNbrTentative();
     		
      		 if(     // admins star
          			  profil.equalsIgnoreCase("ASI") ||
//          			  profil.equalsIgnoreCase("VST") ||
//          			  profil.equalsIgnoreCase("TDST") ||
//          			  profil.equalsIgnoreCase("AST") ||
//          			  profil.equalsIgnoreCase("IST") ||
//          			  profil.equalsIgnoreCase("MHST") ||
//          			  profil.equalsIgnoreCase("VTST") ||
//          			  profil.equalsIgnoreCase("HST") ||
//          			  profil.equalsIgnoreCase("HQST") ||
          			  
          			  // admin bna
          			  profil.equalsIgnoreCase("BNA") ||
          			  
          			  // admin stb
          			  profil.equalsIgnoreCase("AS") ||
          			  profil.equalsIgnoreCase("AA")
          			  ) {
       			 
//       			 limit_before = 2; //2
       			MAX_FAILED_ATTEMPTS = MAX_FAILED_ATTEMPTS_ADMIN;  //3
       			
       	        
       	  // Utiliser Calendar pour calculer la date 60 jours après la date donnée
       			Calendar calendar = Calendar.getInstance();
       			calendar.setTime(givenDate);
       	        calendar.add(Calendar.DAY_OF_YEAR, VALIDITE_ADMIN);
       	        Date datePlus60Days = calendar.getTime();
       	        isPasswordExpired = today.after(datePlus60Days);
       			
       			 
       		 }else {
       			 
//       			 limit_before = 5; //5
       			MAX_FAILED_ATTEMPTS = MAX_FAILED_ATTEMPTS_AGENT;  //6
       			
             	  // Utiliser Calendar pour calculer la date 90 jours après la date donnée
             			Calendar calendar = Calendar.getInstance();
             			calendar.setTime(givenDate);
             	        calendar.add(Calendar.DAY_OF_YEAR, VALIDITE_AGENT);
             	        Date datePlus90Days = calendar.getTime();
             	        isPasswordExpired = today.after(datePlus90Days);       			
       			
       		 }

     		
      		 // cas profil bloqué suite tentative
   		  if(nbr_tent_actuel >= MAX_FAILED_ATTEMPTS){
			  desactive=false; 
			  nombreTentativeDepasser=true;
			  System.out.println("cnx utilisateur depassed");
			  
			  return null;
		  }
    		  
     		
     		// cas profil desactivé
    		  if(new Integer(utilisateur.getSituation()).equals(2)){
    			  desactive=true ; 
    			  nombreTentativeDepasser=false;
    			  System.out.println("cnx utilisateur desactived");
    			  
    			  return null;
    		  }
    		  
    		 
//    		  isPasswordExpired = false;
       		// cas date mdp expiré
    		  if(isPasswordExpired){
    			  desactive=false ; 
    			  nombreTentativeDepasser=false;
    			  expiredMdp = true;
    			  System.out.println("cnx mdp expired");
    			  
    			  return "echec";
    		  }
    		  
    		  
    		// tentative de connexion
    		  
    		  utilisateursConnexionDTO = iconnexionService.connexion(login, CryptPassword.encrypt(getPassword()));
    		  
    		  
    	        if(utilisateursConnexionDTO!=null)
    	        {
    	    /*****succes de tentative ****/
    	        	utilisateur=utilisateursConnexionDTO.getUser();
    	        	menus=utilisateursConnexionDTO.getMenu();
    	        	
    	        	System.out.println("cnx succes de tentative");
    	        	
    	         	  /// vérifier mot de passe court ou non
    	        	  
    	        	  loggedIn = true;

    	        	  
    	        	  if(     // admins star
    	        			  profil.equalsIgnoreCase("ASI") ||
//    	        			  profil.equalsIgnoreCase("VST") ||
//    	        			  profil.equalsIgnoreCase("TDST") ||
//    	        			  profil.equalsIgnoreCase("AST") ||
//    	        			  profil.equalsIgnoreCase("IST") ||
//    	        			  profil.equalsIgnoreCase("MHST") ||
//    	        			  profil.equalsIgnoreCase("VTST") ||
//    	        			  profil.equalsIgnoreCase("HST") ||
//    	        			  profil.equalsIgnoreCase("HQST") ||
    	        			  
    	        			  // admin bna
    	        			  profil.equalsIgnoreCase("BNA") ||
    	        			  
    	        			  // admin stb
    	        			  profil.equalsIgnoreCase("AS") ||
    	        			  profil.equalsIgnoreCase("AA")
    	        			  ) {
    	        		  
    	        		  
    	        		  System.out.println("profil admin.");
    	        		  
    	        		  if (!PasswordValidator.isStrongPasswordForAdmin(getPassword())) {
    	          		    System.out.println("Password admin is not strong.");
    	          		     weekMdp = true;
    	          		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé, veuillez le changer");
    	          		   JsfUtil.showAlertMessageForPasswordAdmin();
    	          		    return null;
    	          		}
    	        		  
    	        		  
    	        	  } else {  // non admin
    	        		  
    	        		  System.out.println("profil non admin.");
    	        		  
    	        		  if (!PasswordValidator.isStrongPassword(getPassword())) {
    	            		    System.out.println("Password is not strong.");
    	            		     weekMdp = true;
    	            		    //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé, veuillez le changer");
    	            		   JsfUtil.showAlertMessageForPassword();
    	            		    return null;
    	            		}
    	        	  }
    	        	  
    	        	    
    	        		  			weekMdp = false;
    	        		  			utilisateur.setSituation(1);
    	        	                utilisateur.setNbrTentative(0);
    	        	                utilisateur.setDateLastConnection(new Date());
    	        	                iUtilisateursDAO.edit(utilisateur);
    	        	                loggedIn = true;
    	        	                  desactive=false;
    	        	                  nombreTentativeDepasser=false;
    	        	            	  still_connexion = false;
    	        	                  return "success";
    	        	
    	        	
    	        	
    	        	
    	        }else{
    	        	
    	 /******echec de tentative suite mdp erroné ****/
//    	        	utilisateur=null;
    	        	
    	        	System.out.println("cnx echec de tentative suite mdp erroné");
    	        	
    	        	utilisateur.setNbrTentative(nbr_tent_actuel+1);
    	        	loggedIn = false;
    	    	    if (utilisateur.getNbrTentative() >= MAX_FAILED_ATTEMPTS) {
    	    	    	utilisateur.setSituation(2);
    	    	    	 utilisateur.setDateDesactivation(new Date());
    	    	    	 nombreTentativeDepasser=true ; 
    	    	    	 loggedIn = true;
    	    	    	 
    	    	    	 System.out.println("cnx profil devenu bloqué");
    	    	    }
    	    	    
    	    	    iUtilisateursDAO.edit(utilisateur);
    	    	    
    	    	    return null;
    	        	
    	        	
    	        }
    		  
    		  
    		  
    	  }
    	  
    
        	                  
        	                  
        	                  
        	                  
        	            }
         

        		  
      

    public String deconnexion() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "success";
    }
    
    
    
    public String goForgotPassword() {
        return "/otp-reset.xhtml?faces-redirect=true&mode=forgot";
    }

    public String goExpiredPassword() {
//        return "/otp-reset.xhtml?faces-redirect=true&mode=expired";
    	return "/changePasswordExpired.xhtml?faces-redirect=true&mode=expired";
    }
    
    public String goBloquedAccount() {
        return "/otp-reset.xhtml?faces-redirect=true&mode=locked";
    }

    //Client
    public void recherche() {
//        System.out.println("client mf" + client.getMatcin());
//        System.out.println("connexion");
//        rechercheClient = iClientService.getClientByMatCin(client.getMatcin());
//        if (rechercheClient != null) {
//            client = rechercheClient;
//            System.out.println("client=" + rechercheClient + " type:" + rechercheClient.getCodtypclt());
//            searchClient = true;
//        } else {
//            FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur: Client introuvable", "");
//            FacesContext.getCurrentInstance().addMessage(null, msg1);
//            searchClient = false;
//            cancel();
//        }
    }

    public void changerTypeCLient(String type) {
//        client.setCodtypclt(type);
    }

    public void cancel() {
//        client = new ClientSimulation();
//        client.setCodtypclt(ParDefaut.TYPE_CLIENT);
//        client.setIdAgence(agenceConnecte);

    }

    public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    

    public String getReNewPassword() {
		return reNewPassword;
	}


	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}


	public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    
    
    
    public boolean isExpiredMdp() {
		return expiredMdp;
	}

	public void setExpiredMdp(boolean expiredMdp) {
		this.expiredMdp = expiredMdp;
	}
	
	
	

	public boolean isStill_connexion() {
		return still_connexion;
	}

	public void setStill_connexion(boolean still_connexion) {
		this.still_connexion = still_connexion;
	}


	String imageSociete;
    
    
    /*public void getImageSociete() {
    	//String codeSociete=utilisateur.getAgence().getSociete().getMatfisc();
    	String codeSociete=utilisateur.getAgence().getSociete().getCodSociet();
    	
	}*/
    
    
    public String getImageSociete() {
    	//String codeSociete=utilisateur.getAgence().getSociete().getMatfisc();
    	String codeSociete=utilisateur.getAgence().getSociete().getCodSociet();
//		return  codeSociete.equalsIgnoreCase("star")?"star":codeSociete;
//    	String logo = reportController.getLogoJasper();
//    	String nom = (logo != null && logo.contains("."))
//    	        ? logo.substring(0, logo.lastIndexOf('.'))
//    	        : logo;
    	
    	if(reportController.Activer_logo_alyssa_dans_menus()) {
    		return  codeSociete.equalsIgnoreCase("star")?"logoStarAlyssa":codeSociete;
    	}

    	return  codeSociete.equalsIgnoreCase("star")?"star":codeSociete;
	}

	public void setImageSociete(String imageSociete) {
		this.imageSociete = imageSociete;
	}

//	public String imageSociete(){
//    	String codeSociete=utilisateur.getAgence().getSociete().getCodSociet();
//    	System.out.println("Code="+codeSociete);
//    	return codeSociete.equalsIgnoreCase("star")?"star":codeSociete;
//    }
/*
    public Agence getAgenceConnecte() {
        return agenceConnecte;
    }

    public ClientSimulation getClient() {
    	//System.out.println("********************** login.getclient(). matcin:"+client.getMatcin());
    	if(client.getMatcin()=="")
    	{
    		System.out.println("********************** login.getclient(). matcin:"+client.getMatcin());
            
            List<MatcinClientSimulation> MatcinClientSimulation= iMatcinClientSimulationDAO.findAll();
            System.out.println("getClient MatcinClientSimulation: "+MatcinClientSimulation.get(0).getMatcin());
            client.setMatcin(MatcinClientSimulation.get(0).getMatcin());
            
            int matcin=Integer.parseInt(MatcinClientSimulation.get(0).getMatcin())+1;
            MatcinClientSimulation.get(0).setMatcin( Integer.toString(matcin));
            iMatcinClientSimulationDAO.edit(MatcinClientSimulation.get(0));
            
    	}
        return client;
    }

    public void setClient(ClientSimulation client) {
        this.client = client;
    }
*/
    public boolean isSearchClient() {
        return searchClient;
    }

    public boolean isDesactive() {
        return desactive;
    }
    private boolean nombreTentativeDepasser;


	public boolean isNombreTentativeDepasser() {
		return nombreTentativeDepasser;
	}

	public void setNombreTentativeDepasser(boolean nombreTentativeDepasser) {
		this.nombreTentativeDepasser = nombreTentativeDepasser;
	}
	
	public UtilisateursConnexionDTO getUtilisateursConnexionDTO() {
		return utilisateursConnexionDTO;
	}


	public String getNewMdp() {
		return newMdp;
	}


	public void setNewMdp(String newMdp) {
		this.newMdp = newMdp;
	}


	public String getReNewMdp() {
		return reNewMdp;
	}


	public void setReNewMdp(String reNewMdp) {
		this.reNewMdp = reNewMdp;
	}


	public boolean isWeekMdp() {
		return weekMdp;
	}


	public void setWeekMdp(boolean weekMdp) {
		this.weekMdp = weekMdp;
	}

	public boolean isPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}
	
	   // Getter and Setter for passwordInput
    public HtmlInputSecret getPasswordInput() {
        return passwordInput;
    }

    public void setPasswordInput(HtmlInputSecret passwordInput) {
        this.passwordInput = passwordInput;
    }
    
    
    public boolean isShowPassword() {
        return showPassword;
    }

    public void toggleShowPassword() {
        this.showPassword = !this.showPassword;
    }
    
    
	public ReportController getReportController() {
		return reportController;
	}


	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}
    
    public boolean isAlyssaLogoPresent() {
    	return reportController.Activer_logo_alyssa_dans_menus();
    }
    
    
    
	

//	public String getPath() {
//		return path;
//	}
//
//	public void setPath(String path) {
//		this.path = path;
//	}
    
    
    
    

	public boolean isCompteDesactive() {
		return compteDesactive;
	}

	public boolean isLoginMdpIncorrecte() {
		return loginMdpIncorrecte;
	}

	public void setLoginMdpIncorrecte(boolean loginMdpIncorrecte) {
		this.loginMdpIncorrecte = loginMdpIncorrecte;
	}

	public void setCompteDesactive(boolean compteDesactive) {
		this.compteDesactive = compteDesactive;
	}

	public boolean isCompteBloque() {
		return compteBloque;
	}

	public void setCompteBloque(boolean compteBloque) {
		this.compteBloque = compteBloque;
	}
	
	

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public String requestUnlockOtp() {

//        otpService.generateForgotPasswordOtp(login); // ou ACCOUNT_UNLOCK

        return "/otp-unlock.xhtml?faces-redirect=true";
    }
}
