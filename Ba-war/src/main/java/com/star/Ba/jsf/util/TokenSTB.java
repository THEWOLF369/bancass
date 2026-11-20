package com.star.Ba.jsf.util;

import java.security.SecureRandom;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.ws.rs.core.MediaType;

import com.star.Ba.commun.Constante;
import com.star.Ba.jsf.ReportController;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class TokenSTB {

	
public static String tokenStb(ReportController reportController) {
		
		final String URL_WS_URL_TOKEN_STB = Constante.URL_TOKEN_STB;
		
		//final String URL_WS_URL_TOKEN_STB = "https://openbank.stb.com.tn:9443/authentication/token";
		
		 //final String URL_WS_URL_TOKEN_STB = "https://openbank.stb.com.tn/authentication/token";
		
		Form f = new Form();
		 f.add("username", Constante.username);
		 f.add("password", Constante.password);
		 f.add("grant_type", Constante.grant_type);
		 f.add("scope", Constante.scope);
		 f.add("client_id", Constante.client_id);
		 f.add("response_type", Constante.response_type);
		
		System.out.println("Constante.URL_TOKEN_STB "+Constante.URL_TOKEN_STB);
		
		try {
			
			com.sun.jersey.api.client.Client client = JerseyClient.ApiClient(reportController);
		    ClientResponse response = JerseyClient.PostReponseDuClientSTBToken(URL_WS_URL_TOKEN_STB, f, client, reportController);
		    

		    if ( response == null) {
				System.out.println("probleme de connexion vide token");
				
				return null;
			}
		    
		    
		    if (response.getStatus() != 200) {
		    	System.out.println("erreur token diff 200 .... \n");
//				throw new RuntimeException("Failed : HTTP error code : "
//				     + response.getStatus());
		    	 
				  return "not200";
			}

			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
			
			JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
			
			//JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON( response.getEntity(String.class) ); 
			 
			 String access_token = jsonOutput.getString("access_token");
			 
			 return access_token;
			
			} 
		
		catch (ClientHandlerException e) {

     		
     		System.out.println("probleme de ClientHandlerException token  ");
     		
			e.printStackTrace();
			
			return null;

		  }
		
		catch (Exception e) {

				System.out.println("erreur Exception token .... \n");
				e.printStackTrace();
				
				return "exception";

			  }
		
	}
	
	
}
