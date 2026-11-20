package com.star.Ba.parametres;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class ExampleResourceClient {
	
	
	 private WebResource webResource;
     private com.sun.jersey.api.client.Client client;
     //private static final String BASE_URI = "https://ecom.stb.com.tn:9090/BanqueAssurance/api/DNC/";

     public ExampleResourceClient(String path,String BASE_URI) {
         com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
         client = com.sun.jersey.api.client.Client.create(config);
//         client.setReadTimeout(900000000);
//         client.setConnectTimeout(900000000);
         webResource = client.resource(BASE_URI).path(path);
     }
     
     
     public int HttpReponse(){
    	 
         try {
         ClientResponse response = webResource.get(ClientResponse.class);
    	
         return response.getStatus();
         
     } catch(ClientHandlerException ex) {
 	    //handleClientHandlerException(ex);
 	    System.out.println("The ClientHandlerException !! ");
 	    ex.printStackTrace();
 	    return 0;
 	    
 	} catch (Exception e) {
		 
    	 
			System.out.println(" Exception depuis HttpReponse ");
			e.printStackTrace();
			 return 0;
	         
	    }
    }
     
     
     public ClientResponse HttpReponsePost(String input){
    	 
    	 try {
    		 
    		 ClientResponse response = webResource.type("application/json")
    				   .post(ClientResponse.class, input);
    		 return response;
    		 
    	 }catch(ClientHandlerException ex) {
    	 	    //handleClientHandlerException(ex);
    	 	    System.out.println("The ClientHandlerException !! ");
    	 	    ex.printStackTrace();
    	 	    return null;
    	 	    
    	 	} catch (Exception e) {
    			 
    	    	 
    				System.out.println(" Exception depuis HttpReponse ");
    				e.printStackTrace();
    				 return null;
    		         
    		    }
    	 
     }
     
     
     public String getJson() throws UniformInterfaceException {
         WebResource resource = webResource;
         return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
     }
     

     public String getXml() throws UniformInterfaceException {
         WebResource resource = webResource;
         return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(String.class);
     }

     public String putXml(Object requestEntity) throws UniformInterfaceException {
         return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).put(String.class, requestEntity);
     }

     public void close() {
         client.destroy();
     }

}
