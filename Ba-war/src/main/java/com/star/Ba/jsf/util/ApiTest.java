package com.star.Ba.jsf.util;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.star.Ba.commun.Constante;

public class ApiTest {

    public static void main(String[] args) {
        String apiUrl = Constante.URL_API_PELEV_STB; // http ou https
        String apiKey = Constante.Bassem_Api_Key;

        // Variables dynamiques
        String transactionId = "000001";
        String issuerRib = "10000000500310578826";
        double amount = 100;
        String beneficiaryName = "Elyssa assurance";
        String orderReference = "993456789781015544551001";

        // Construire le JSON dynamiquement
        JSONObject inputJson = new JSONObject();
        inputJson.put("IdTransaction", transactionId);
        inputJson.put("issuerRib", issuerRib);
        inputJson.put("amount", amount);
        inputJson.put("beneficiaryName", beneficiaryName);
        inputJson.put("description", "test api Monthly");
        inputJson.put("frequency", "Annual");
        inputJson.put("object", "STAR VIE");
        inputJson.put("maxRetries", "3");
        inputJson.put("startDate", "01/02/2026");
        inputJson.put("endDate", "31/12/2026");
        inputJson.put("currencyCode", "TND");
        inputJson.put("orderReference", orderReference);
        inputJson.put("beneficiaryRib", issuerRib);
        inputJson.put("fromMonth", "04");
        inputJson.put("fromDayOfMonth", "25");
        inputJson.put("fromDayOfWeek", "");

        // Affichage pour Postman
        System.out.println("JSON à envoyer / tester en Postman :\n" + inputJson.toString(4));

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn;

            if (apiUrl.toLowerCase().startsWith("https")) {
                conn = (HttpsURLConnection) url.openConnection();
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-api-key", apiKey);
            conn.setDoOutput(true);

            // Envoyer le JSON
            try (OutputStream os = conn.getOutputStream()) {
                os.write(inputJson.toString().getBytes("UTF-8"));
            }

            int statusCode = conn.getResponseCode();
            InputStream is = (statusCode >= 200 && statusCode < 400) 
                             ? conn.getInputStream() 
                             : conn.getErrorStream();

            // Lire la réponse
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseText.append(line);
            }

            // Parser la réponse JSON
            JSONObject responseJson = new JSONObject(responseText.toString());

            // Affichage
            System.out.println("HTTP STATUS: " + statusCode);
            System.out.println("Response JSON:\n" + responseJson.toString(4));

            // Exemple : lire les champs
            System.out.println("Success: " + responseJson.optBoolean("success"));
            System.out.println("Status: " + responseJson.optString("Status"));
            System.out.println("ResponseCode: " + responseJson.optString("ResponseCode"));
            System.out.println("Message: " + responseJson.optString("Message"));
            System.out.println("Reference: " + responseJson.optString("Reference"));
            System.out.println("ExternalReference: " + responseJson.optString("ExternalReference"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}