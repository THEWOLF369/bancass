package com.star.Ba.jsf.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.faces.bean.ManagedProperty;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;

import com.star.Ba.commun.Constante;
import com.star.Ba.jsf.ReportController;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.representation.Form;

public class JerseyClient {
	


	
	
	/******************** pour EDI ********************************************/
	
	public static  ClientResponse PostReponseDuClientEDI_2020(String url, Form f){
		
		ClientResponse response = null;
		
		try {
		com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();

		client.addFilter(new GZIPContentEncodingFilter(false));
		
        WebResource resource = client
           .resource(url);

        //String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";
        
//        MultivaluedMap formData = new MultivaluedMapImpl();
//        formData.add("DataForCalculPrime", input);
//
//        ClientResponse response = webResource.type("application/x-www-form-urlencoded")
//           .post(ClientResponse.class, formData);
        
        
         response = resource.accept(MediaType.APPLICATION_XML_TYPE,
		        MediaType.APPLICATION_JSON_TYPE).
		        header("MyHeader", "Jersey Client")
		       .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		       .post(ClientResponse.class, f);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("reponse edi null");
			 e.printStackTrace();
			 return null;
		}
        
        return response;
		
	}
	
	public static ClientResponse PostReponseDuClientEDI_2026(String url, Form f) {

	    ClientResponse response = null;

	    try {
	        /* ============================
	         * 1. Désactiver SSL (Java 6)
	         * ============================ */
	        TrustManager[] trustAllCerts = new TrustManager[] {
	            new X509TrustManager() {
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };

	        SSLContext sc = SSLContext.getInstance("TLSv1.2");
	        sc.init(null, trustAllCerts, new SecureRandom());

	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        HttpsURLConnection.setDefaultHostnameVerifier(
	            new HostnameVerifier() {
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            }
	        );

	        /* ============================
	         * 2. Client Jersey
	         * ============================ */
	        com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
	        client.addFilter(new GZIPContentEncodingFilter(false));

	        WebResource resource = client.resource(url);

	        /* ============================
	         * 3. POST FORM
	         * ============================ */
	        response = resource
	                .accept(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_FORM_URLENCODED)
	                .post(ClientResponse.class, f);

	    } catch (Exception e) {
	        System.out.println("reponse edi null");
	        e.printStackTrace();
	        return null;
	    }

	    return response;
	}

	
	
	public static  ClientResponse PostReponseDuClientEDI(String url, Form f, ReportController reportController){
		
		if(!reportController.WITH_CERTIF()) {
			
			System.out.println("post pas de certif");
			
			return PostReponseDuClientEDI_2020( url,  f);
			
		}else {
			
			System.out.println("post avec certif");
			
			return PostReponseDuClientEDI_2026( url,  f);
			
		}
	}
	 
	
	
	public static  ClientResponse GetReponseDuClientEDI_2020(String url){
		
		ClientResponse response = null;
		
		try {
			
		 
		com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();

		client.addFilter(new GZIPContentEncodingFilter(false));
		
        WebResource resource = client
           .resource(url);

        //String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";
        
//        MultivaluedMap formData = new MultivaluedMapImpl();
//        formData.add("DataForCalculPrime", input);
//
//        ClientResponse response = webResource.type("application/x-www-form-urlencoded")
//           .post(ClientResponse.class, formData);
        
        
         response = resource.accept(MediaType.APPLICATION_XML_TYPE,
		        MediaType.APPLICATION_JSON_TYPE).
		        header("MyHeader", "Jersey Client")
		       .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		       .get(ClientResponse.class);
        
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("reponse edi null");
			 e.printStackTrace();
			 return null;
		}
        
        return response;
	}
	
	
	public static ClientResponse GetReponseDuClientEDI_2026(String url) {

	    ClientResponse response = null;

	    try {
	        /* ============================
	         * 1. Désactiver SSL (INT / DEV)
	         * ============================ */
	        TrustManager[] trustAllCerts = new TrustManager[] {
	            new X509TrustManager() {
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };

	        SSLContext sc = SSLContext.getInstance("TLSv1.2");
	        sc.init(null, trustAllCerts, new SecureRandom());

	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        HttpsURLConnection.setDefaultHostnameVerifier(
	            new HostnameVerifier() {
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            }
	        );

	        /* ============================
	         * 2. Client Jersey
	         * ============================ */
	        com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
	        client.addFilter(new GZIPContentEncodingFilter(false));

	        WebResource resource = client.resource(url);

	        /* ============================
	         * 3. GET
	         * ============================ */
	        response = resource
	                .accept(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON)
	                .header("MyHeader", "Jersey Client")
	                .get(ClientResponse.class);

	    } catch (Exception e) {
	        System.out.println("reponse edi null");
	        e.printStackTrace();
	        return null;
	    }

	    return response;
	}

	
	public static ClientResponse GetReponseDuClientEDI(String url, ReportController reportController){
		
		if(!reportController.WITH_CERTIF()) {
			
			System.out.println("get pas de certif");
			
			return GetReponseDuClientEDI_2020( url);
			
		}else {
			
			System.out.println("get avec certif");
			
			return GetReponseDuClientEDI_2026( url);
			
		}
	}
	
	
	/******************** pour STB ********************************************/
	public static com.sun.jersey.api.client.Client ApiClient(ReportController reportController) throws NoSuchAlgorithmException, KeyManagementException{
		
		SSLContext ctx = null;
		 ctx = SSLContext.getInstance("TLSv1.2");
        ctx.init(null, null, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        
        
        com.sun.jersey.api.client.Client client = null;
        
        
        if(!reportController.WITH_STB_PROXY()) {
			
			System.out.println("post pas de proxy");
			
			//sinon pas proxy
	         client= com.sun.jersey.api.client.Client.create(new com.sun.jersey.api.client.config.DefaultClientConfig());

			
		}else {
			
			System.out.println("post avec proxy");
			
			//si proxy 
	        com.sun.jersey.client.urlconnection.URLConnectionClientHandler ch  = new com.sun.jersey.client.urlconnection.URLConnectionClientHandler(new ProxyConnectionFactory());
	         client = new com.sun.jersey.api.client.Client(ch);

			
		}
        
                
        
        return client;
		
	}
	
	//****************************** partie token ***************************************/
	public static ClientResponse PostReponseDuClientSTBToken_SANS_CERTIF(String url, Form f, com.sun.jersey.api.client.Client client)  {
		
		
		
		
		
	    WebResource resource = client
	       .resource(url);
	    
	    ClientResponse response = resource.accept(MediaType.APPLICATION_XML_TYPE,
		        MediaType.APPLICATION_JSON_TYPE).
		        header("MyHeader", "Jersey Client")
		       .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		       .post(ClientResponse.class, f);
	    
	    return response;
		
		
	}
	
	public static ClientResponse PostReponseDuClientSTBToken_CERTIF(String url, Form f, com.sun.jersey.api.client.Client client) {

	    ClientResponse response = null;

	    try {
	        /* ============================
	         * 1. Désactiver SSL (Java 6)
	         * ============================ */
	        TrustManager[] trustAllCerts = new TrustManager[] {
	            new X509TrustManager() {
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };

	        SSLContext sc = SSLContext.getInstance("TLSv1.2");
	        sc.init(null, trustAllCerts, new SecureRandom());

	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        HttpsURLConnection.setDefaultHostnameVerifier(
	            new HostnameVerifier() {
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            }
	        );

	        /* ============================
	         * 2. Client Jersey
	         * ============================ */
//	        client = com.sun.jersey.api.client.Client.create();
	        client.addFilter(new GZIPContentEncodingFilter(false));

	        WebResource resource = client.resource(url);

	        /* ============================
	         * 3. POST FORM
	         * ============================ */
	          response = resource.accept(MediaType.APPLICATION_XML_TYPE,
			        MediaType.APPLICATION_JSON_TYPE).
			        header("MyHeader", "Jersey Client")
			       .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			       .post(ClientResponse.class, f);

 

	    } catch (Exception e) {
	        System.out.println("reponse STB null");
	        e.printStackTrace();
	        return null;
	    }

	    return response;
	}
	
	
	public static ClientResponse PostReponseDuClientSTBToken(String url, Form f, com.sun.jersey.api.client.Client client,
		ReportController reportController)  {
		
		if(!reportController.WITH_STB_CERTIF()) {
			
			System.out.println("post pas de certif");
			
			return PostReponseDuClientSTBToken_SANS_CERTIF( url,  f,  client);
			
		}else {
			
			System.out.println("post avec certif");
			
			return PostReponseDuClientSTBToken_CERTIF( url,  f,  client);
			
		}
	}
	
	//*********************************** partie encaissement *****************************************/
	public static ClientResponse PostReponseDuClientSTBEncaissement_SANS_CERTIF(String url, String token, String input, com.sun.jersey.api.client.Client client)  {
		

		
	    WebResource resource = client
	       .resource(url);
	    
	    ClientResponse response = resource.type("application/json")
	    		.header("Ocp-Apim-Subscription-Key", Constante.HEADER_ENCAISS)
	    		.header("Authorization", "Bearer " + token)
	 		   .post(ClientResponse.class, input);
	    
	    return response;
	}
	
	public static ClientResponse PostReponseDuClientSTBEncaissement_CERTIF(String url, String token, String input, com.sun.jersey.api.client.Client client) {

	    ClientResponse response = null;

	    try {
	        /* ============================
	         * 1. Désactiver SSL (Java 6)
	         * ============================ */
	        TrustManager[] trustAllCerts = new TrustManager[] {
	            new X509TrustManager() {
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };

	        SSLContext sc = SSLContext.getInstance("TLSv1.2");
	        sc.init(null, trustAllCerts, new SecureRandom());

	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        HttpsURLConnection.setDefaultHostnameVerifier(
	            new HostnameVerifier() {
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            }
	        );

	        /* ============================
	         * 2. Client Jersey
	         * ============================ */
//	        client = com.sun.jersey.api.client.Client.create();
	        client.addFilter(new GZIPContentEncodingFilter(false));

	        WebResource resource = client.resource(url);

	        /* ============================
	         * 3. POST FORM
	         * ============================ */
	        response = resource.type("application/json")
		    		.header("Ocp-Apim-Subscription-Key", Constante.HEADER_ENCAISS)
		    		.header("Authorization", "Bearer " + token)
		 		   .post(ClientResponse.class, input);

 

	    } catch (Exception e) {
	        System.out.println("reponse STB null");
	        e.printStackTrace();
	        return null;
	    }

	    return response;
	}
	
	
	public static ClientResponse PostReponseDuClientSTBEncaissement(String url, String token, String input, com.sun.jersey.api.client.Client client,
			ReportController reportController)  {
			
			if(!reportController.WITH_STB_CERTIF()) {
				
				System.out.println("post pas de certif");
				
				return PostReponseDuClientSTBEncaissement_SANS_CERTIF(  url,  token,  input, client);
				
			}else {
				
				System.out.println("post avec certif");
				
				return PostReponseDuClientSTBEncaissement_CERTIF( url,  token,  input, client);
				
			}
		}
	
	
	//************************************* partie donnée client cin, rib ***************************************/
	public static ClientResponse GetReponseDuClientSTB_SANS_CERTIF(String url, String token,  com.sun.jersey.api.client.Client client)  {
		

		
	    WebResource resource = client
	       .resource(url);
	    
	    ClientResponse response = resource.type("application/json")
	    		.header("Ocp-Apim-Subscription-Key", Constante.HEADER_ENCAISS)
	    		.header("Authorization", "Bearer " + token)
	    		   .get(ClientResponse.class);
	    
	    return response;
	}
	
	public static ClientResponse GetReponseDuClientSTB_CERTIF(String url, String token,  com.sun.jersey.api.client.Client client) {

	    ClientResponse response = null;

	    try {
	        /* ============================
	         * 1. Désactiver SSL (Java 6)
	         * ============================ */
	        TrustManager[] trustAllCerts = new TrustManager[] {
	            new X509TrustManager() {
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };

	        SSLContext sc = SSLContext.getInstance("TLSv1.2");
	        sc.init(null, trustAllCerts, new SecureRandom());

	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        HttpsURLConnection.setDefaultHostnameVerifier(
	            new HostnameVerifier() {
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            }
	        );

	        /* ============================
	         * 2. Client Jersey
	         * ============================ */
//	        client = com.sun.jersey.api.client.Client.create();
	        client.addFilter(new GZIPContentEncodingFilter(false));

	        WebResource resource = client.resource(url);

	        /* ============================
	         * 3. POST FORM
	         * ============================ */
	        response = resource.type("application/json")
		    		.header("Ocp-Apim-Subscription-Key", Constante.HEADER_ENCAISS)
		    		.header("Authorization", "Bearer " + token)
		    		   .get(ClientResponse.class);

 

	    } catch (Exception e) {
	        System.out.println("reponse STB null");
	        e.printStackTrace();
	        return null;
	    }

	    return response;
	}
	
	
	public static ClientResponse GetReponseDuClientSTB(String url, String token,  com.sun.jersey.api.client.Client client,
			ReportController reportController)  {
			
			if(!reportController.WITH_STB_CERTIF()) {
				
				System.out.println("post pas de certif");
				
				return GetReponseDuClientSTB_SANS_CERTIF(   url,  token,   client);
				
			}else {
				
				System.out.println("post avec certif");
				
				return GetReponseDuClientSTB_CERTIF(  url,  token,   client);
				
			}
		}
	
	//************************************* partie donnée collaborateurs info ***************************************/
	public static ClientResponse PostReponseDuClientSTBCollaborateurs_SANS_CERTIF(String url, String token, String input, com.sun.jersey.api.client.Client client)  {
		

		
	    WebResource resource = client
	       .resource(url);
	    
	    ClientResponse response = resource.type("application/json")
	    		.header("Ocp-Apim-Subscription-Key", Constante.HEADER_COLLAB)
	    		.header("Authorization", "Bearer " + token)
	 		   .post(ClientResponse.class, input);
	    
	    return response;
	}
	
	public static ClientResponse PostReponseDuClientSTBCollaborateurs_CERTIF(String url, String token, String input, com.sun.jersey.api.client.Client client) {

	    ClientResponse response = null;

	    try {
	        /* ============================
	         * 1. Désactiver SSL (Java 6)
	         * ============================ */
	        TrustManager[] trustAllCerts = new TrustManager[] {
	            new X509TrustManager() {
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };

	        SSLContext sc = SSLContext.getInstance("TLSv1.2");
	        sc.init(null, trustAllCerts, new SecureRandom());

	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        HttpsURLConnection.setDefaultHostnameVerifier(
	            new HostnameVerifier() {
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            }
	        );

	        /* ============================
	         * 2. Client Jersey
	         * ============================ */
//	        client = com.sun.jersey.api.client.Client.create();
	        client.addFilter(new GZIPContentEncodingFilter(false));

	        WebResource resource = client.resource(url);

	        /* ============================
	         * 3. POST FORM
	         * ============================ */
	        response = resource.type("application/json")
		    		.header("Ocp-Apim-Subscription-Key", Constante.HEADER_COLLAB)
		    		.header("Authorization", "Bearer " + token)
		 		   .post(ClientResponse.class, input);

 

	    } catch (Exception e) {
	        System.out.println("reponse STB null");
	        e.printStackTrace();
	        return null;
	    }

	    return response;
	}
	
	
	public static ClientResponse PostReponseDuClientSTBCollaborateurs(String url, String token, String input, com.sun.jersey.api.client.Client client,
			ReportController reportController)  {
			
			if(!reportController.WITH_STB_CERTIF()) {
				
				System.out.println("post pas de certif");
				
				return PostReponseDuClientSTBCollaborateurs_SANS_CERTIF(  url,  token,  input, client);
				
			}else {
				
				System.out.println("post avec certif");
				
				return PostReponseDuClientSTBCollaborateurs_CERTIF( url,  token,  input, client);
				
			}
		}	
	
	/******************** pour Bassem API prelevement STB ********************************************/
	public static com.sun.jersey.api.client.Client LocalApiClient() throws NoSuchAlgorithmException, KeyManagementException{
		
		SSLContext ctx = null;
		 ctx = SSLContext.getInstance("TLSv1.2");
        ctx.init(null, null, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
        
        //si proxy 
//        com.sun.jersey.client.urlconnection.URLConnectionClientHandler ch  = new com.sun.jersey.client.urlconnection.URLConnectionClientHandler(new ProxyConnectionFactory());
//        com.sun.jersey.api.client.Client client = new com.sun.jersey.api.client.Client(ch);
        
        //sinon pas proxy
        com.sun.jersey.api.client.Client client= com.sun.jersey.api.client.Client.create(new com.sun.jersey.api.client.config.DefaultClientConfig());

        return client;
		
	}
	
	public static ClientResponse PostReponseDuClientSTBstandingOrder_2025(
	        String url,
	        String input,
	        com.sun.jersey.api.client.Client client) {

	    WebResource resource = client.resource(url);

	    ClientResponse response = resource
	            .type("application/json")
	            .accept("application/json")
	            .header("x-api-key", Constante.Bassem_Api_Key)
	            .post(ClientResponse.class, input);

	    return response;
	}
	
	
	public static ClientResponse PostReponseDuClientSTBstandingOrder_2026(String url,
	        String input,
	        com.sun.jersey.api.client.Client client) {

	    ClientResponse response = null;

	    try {
	        /* ============================
	         * 1. Désactiver SSL (Java 6)
	         * ============================ */
	        TrustManager[] trustAllCerts = new TrustManager[] {
	            new X509TrustManager() {
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };

	        SSLContext sc = SSLContext.getInstance("TLSv1.2");
	        sc.init(null, trustAllCerts, new SecureRandom());

	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        HttpsURLConnection.setDefaultHostnameVerifier(
	            new HostnameVerifier() {
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            }
	        );

	        /* ============================
	         * 2. Client Jersey
	         * ============================ */
//	        client = com.sun.jersey.api.client.Client.create();
	        client.addFilter(new GZIPContentEncodingFilter(false));

	        WebResource resource = client.resource(url);

	        /* ============================
	         * 3. POST FORM
	         * ============================ */
	        response = resource
		            .type("application/json")
		            .accept("application/json")
		            .header("x-api-key", Constante.Bassem_Api_Key)
		            .post(ClientResponse.class, input);

 

	    } catch (Exception e) {
	        System.out.println("reponse edi null");
	        e.printStackTrace();
	        return null;
	    }

	    return response;
	}
	
	
	public static ClientResponse PostReponseDuClientSTBstandingOrder(String url,
	        String input,
	        com.sun.jersey.api.client.Client client, ReportController reportController){
		
		if(!reportController.WITH_BASSEM_CERTIF()) {
			
			System.out.println("post pas de certif");
			
			return PostReponseDuClientSTBstandingOrder_2025( url, input, client);
			
		}else {
			
			System.out.println("post avec certif");
			
			return PostReponseDuClientSTBstandingOrder_2026( url, input, client);
			
		}
	}


}
