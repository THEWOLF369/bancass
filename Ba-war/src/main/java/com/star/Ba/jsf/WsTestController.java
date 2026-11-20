package com.star.Ba.jsf;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IApiResponseEntityDAO;
import com.star.Ba.jsf.util.Frequency;
import com.star.Ba.jsf.util.FrequencyService;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.ApiResponseEntity;
import com.star.Ba.model.Utilisateurs;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONObject;

@ManagedBean
@SessionScoped
public class WsTestController implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;// = new Utilisateurs();

	@ManagedProperty("#{reportController}")
	private ReportController reportController;


	@EJB
	private IApiResponseEntityDAO iApiResponseEntityDAO;


	private String resultat;

	@PostConstruct
	public void init() {

		System.out.println("==================== init()  WsTestController");


	}


	// 🔹 Bouton JSF
	public void Appel_api_bassem() {
		System.out.println("==================== Appel_api_bassem()");

		 if(utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name())) {
		try {
			
			resultat = "en cours......";
			// Appel de la méthode WS
			String response = API_Bassem(null,
					null,
					null,
					0,
					"Elyssa assurance",
					null,
					"test",
					"PRELEVEMENT_BANCAIRE",
					21,
					null,
					null,
					"TND",
					null,
					Constante.RIB_STAR_PRELEV,
					null,
					null,
					""
					);

			// Stocker la réponse pour affichage
			resultat = UUID.randomUUID().toString().substring(0, 5) + " " +response;

		} catch (Exception e) {
			resultat = "❌ Erreur : " + e.getMessage();
			e.printStackTrace();
		}
		
		 }else {
			 
			 resultat = "connecter en tant que admin";
		 }
	}



	// 🔹 Méthode WS modifiée pour retourner String
	public String API_Bassem(Adhesion ad, String transactionId,
			String issuerRib,
			double amount,
			String beneficiaryName,
			String description,
			String frequency,
			String object,
			int maxRetries,
			String startDate,
			String endDate,
			String currencyCode,
			String orderReference,
			String beneficiaryRib,
			String fromMonth,
			String fromDayOfMonth,
			String fromDayOfWeek) {

		if(!reportController.activer_api_Bassem()) {
			System.out.println("API_Bassem est désactivé");
			return "API_Bassem désactivé";
		}

		System.out.println("API_Bassem est activé");
		final String BASE_URI = Constante.URL_API_PELEV_STB;

		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("IdTransaction", transactionId);
		jsonMap.put("issuerRib", issuerRib);
		jsonMap.put("amount", amount);
		jsonMap.put("beneficiaryName", beneficiaryName);
		jsonMap.put("description", description);
		jsonMap.put("frequency", frequency);
		jsonMap.put("object", object);
		jsonMap.put("maxRetries", maxRetries);
		jsonMap.put("startDate", startDate);
		jsonMap.put("endDate", endDate);
		jsonMap.put("currencyCode", currencyCode);
		jsonMap.put("orderReference", orderReference);
		jsonMap.put("beneficiaryRib", beneficiaryRib);
		jsonMap.put("fromMonth", fromMonth == null ? "" : fromMonth);
		jsonMap.put("fromDayOfMonth", fromDayOfMonth == null ? "" : fromDayOfMonth);
		jsonMap.put("fromDayOfWeek", fromDayOfWeek == null ? "" : fromDayOfWeek);

		JSONObject inputJson = new JSONObject(jsonMap);
		System.out.println("JSON à envoyer :\n" + inputJson.toString(4));

		Integer status = null;
		String responseBody = null;
		String errorMessage = null;

		try {
			com.sun.jersey.api.client.Client client = JerseyClient.LocalApiClient();
			ClientResponse response = JerseyClient.PostReponseDuClientSTBstandingOrder(BASE_URI, inputJson.toString(), client, reportController);

			if(response == null) {
				System.out.println("❌ Response = null");
				return "Response = null";
			}

			status = response.getStatus();
			responseBody = response.getEntity(String.class);

			System.out.println("HTTP Status : " + status);
			System.out.println("Response Body :\n" + responseBody);

			if(status == 200 || status == 201) {
				if(responseBody != null && responseBody.trim().startsWith("{")) {
					JSONObject jsonOutput = new JSONObject(responseBody);
					return jsonOutput.toString(4); // JSON formaté pour affichage
				} else {
					return responseBody != null ? responseBody : "Réponse 200 mais vide";
				}
			} else {
				if(responseBody != null && responseBody.trim().startsWith("{")) {
					JSONObject jsonError = new JSONObject(responseBody);
					return "Erreur HTTP " + status + " :\n" + jsonError.toString(4);
				} else {
					return "Erreur HTTP " + status + " : " + responseBody;
				}
			}

		} catch (ClientHandlerException e) {
			e.printStackTrace();
			return "ClientHandlerException : " + e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception : " + e.getMessage();
		} finally {
			System.out.println("Enregistrement du log API terminé");
		}
	}






	public String getResultat() {
		return resultat;
	}


	public void setResultat(String resultat) {
		this.resultat = resultat;
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







}
