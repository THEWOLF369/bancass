package com.star.Ba.jsf.stb;

 

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.Ba.commun.Constante;
import com.star.Ba.jsf.ReportController;
import com.star.Ba.jsf.util.JerseyClient;
import com.star.Ba.jsf.util.TokenSTB;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import javax.ejb.Stateless;

/**
 * Contient toute la logique d'appel à l'API STB (récupération du token,
 * appel HTTP, parsing de la réponse) pour rechercher un collaborateur.
 * Ne connaît rien de JSF : elle renvoie un {@link StbLookupResult} ou lève
 * une {@link BusinessException} en cas d'erreur technique imprévue.
 *
 * Adapte les imports (TokenSTB, JerseyClient, Constante, ReportController)
 * au package réel de ton projet.
 *
 * @Stateless : pas d'état conservé entre deux appels -> l'EJB le plus simple
 * et le moins coûteux pour le conteneur (pool d'instances réutilisables).
 */
@Stateless
public class StbCollaborateurService {

    public StbLookupResult rechercherUtilisateur(String login, ReportController reportController) throws Exception {

        String token = TokenSTB.tokenStb(reportController);

        if (token == null) {
            return StbLookupResult.tokenUnavailable();
        }
        if (token.equalsIgnoreCase("exception")) {
            return StbLookupResult.tokenAuthError();
        }
        if (token.equalsIgnoreCase("not200")) {
            return StbLookupResult.tokenAccessDenied();
        }

        String input = "{\"Matricule\":\"" + login.trim() + "\"}";

        Client client = JerseyClient.ApiClient(reportController);
        ClientResponse response = JerseyClient.PostReponseDuClientSTBCollaborateurs(
                Constante.URL_USER_STB, token, input, client, reportController);

        if (response == null) {
            return StbLookupResult.serviceUnavailable();
        }

        if (response.getStatus() != 200) {
            return StbLookupResult.serviceError(String.valueOf(response.getStatus()), extraireDetailErreur(response));
        }

        String output = response.getEntity(String.class);
        JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);

        String status = jsonOutput.optString("status", "");
        String returncode = jsonOutput.optString("returncode", "");

        // Contrat métier STB connu :
        //  - status = "SUCCESS",  returncode = "000" -> opération réalisée avec succès
        //  - status = "Rejected", returncode = "409" -> matricule inexistant ou autre motif de rejet
        if ("Rejected".equalsIgnoreCase(status) && "409".equals(returncode)) {
            return StbLookupResult.userNotFound(returncode);
        }

        if (!("SUCCESS".equalsIgnoreCase(status) && "000".equals(returncode))) {
            // Combinaison status/returncode non prévue par le contrat : on ne l'interprète pas.
            return StbLookupResult.invalidResponse(returncode);
        }

        JSONArray details = jsonOutput.optJSONArray("details");
        if (details == null || details.isEmpty()) {
            // Filet de sécurité : SUCCESS/000 déclaré mais aucune donnée exploitable.
            return StbLookupResult.userNotFound(returncode);
        }

        JSONObject userJson = details.getJSONObject(0);
//        return StbLookupResult.success(
//                userJson.optString("nom", ""),
//                userJson.optString("prenom", ""),
//                userJson.optString("Mail", "")
//        );
       
        // ============================================================
        // INFORMATIONS UTILISATEUR
        // ============================================================

        String nom =
                userJson.optString("nom", "");

        String prenom =
                userJson.optString("prenom", "");

        String mail =
                userJson.optString("Mail", "");

        String libelleImmeuble =
                userJson.optString("libelleimmeuble", "");

        String nomComplet =
                userJson.optString("nomcomplet", "");

        Integer codeFonction = null;

        if (userJson.containsKey("codefonction") 
                && userJson.get("codefonction") != null) {
            codeFonction = userJson.getInt("codefonction");
        }

        String mobilePhone =
                userJson.optString("MobilePhone", "");

        // ============================================================
        // BUSINESS PHONES
        // ============================================================

        String businessPhone = "";

        JSONArray businessPhones =
                userJson.optJSONArray("BusinessPhones");

        if (businessPhones != null
                && !businessPhones.isEmpty()) {

            businessPhone =
                    businessPhones.optString(0, "");
        }

        // ============================================================
        // INFORMATIONS PROFESSIONNELLES
        // ============================================================

        String libelleDepartement =
                userJson.optString("libelledepartement", "");

        String libellePoste =
                userJson.optString("libelleposte", "");

        String codeDepartement =
                userJson.optString("codedepartement", "");

        String utlrLogin =
                userJson.optString("utlr_login", "");

        String codePoste =
                userJson.optString("codeposte", "");

        String codeImmeuble =
                userJson.optString("codeimmeuble", "");

        String codeFonctionDes =
                userJson.optString("codefonctiondes", "");

        String libelleFonction =
                userJson.optString("libellefonction", "");


        // ============================================================
        // RESULTAT
        // ============================================================

        return StbLookupResult.success(
                nom,
                prenom,
                mail,
                libelleImmeuble,
                nomComplet,
                codeFonction,
                mobilePhone,
                businessPhone,
                libelleDepartement,
                libellePoste,
                codeDepartement,
                utlrLogin,
                codePoste,
                codeImmeuble,
                codeFonctionDes,
                libelleFonction
        );
 

    }

    private String extraireDetailErreur(ClientResponse response) {
        try {
            String responseBody = response.getEntity(String.class);
            if (responseBody != null && !responseBody.trim().isEmpty()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                JsonNode messageNode = jsonNode.get("message");
                if (messageNode != null && !messageNode.isNull() && !messageNode.asText().trim().isEmpty()) {
                    return messageNode.asText();
                }
            }
        } catch (Exception e) {
            // Pas de détail exploitable dans le corps de la réponse STB : on ignore.
        }
        return null;
    }
}