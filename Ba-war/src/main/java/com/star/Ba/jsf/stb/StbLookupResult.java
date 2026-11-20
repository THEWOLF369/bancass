package com.star.Ba.jsf.stb;
 
 
/**
 * Représente le résultat d'une recherche d'utilisateur STB.
 * Volontairement sans aucune dépendance à JSF (FacesContext, FacesMessage...)
 * afin que la logique métier reste testable et réutilisable indépendamment
 * de la couche présentation.
 */
public class StbLookupResult {

    public enum Status {
        SUCCESS,
        TOKEN_UNAVAILABLE,     // token == null
        TOKEN_AUTH_ERROR,      // token == "exception"
        TOKEN_ACCESS_DENIED,   // token == "not200"
        SERVICE_UNAVAILABLE,   // response == null
        SERVICE_ERROR,         // response.getStatus() != 200
        INVALID_RESPONSE,      // status != "SUCCESS" dans le JSON
        USER_NOT_FOUND         // details vide
    }

    private final Status status;
    private final String nom;
    private final String prenom;
    private final String mail;
    
    private final String libelleImmeuble;
    private final String nomComplet;
    private final Integer codeFonction;
    private final String mobilePhone;
    private final String businessPhone;
    private final String libelleDepartement;
    private final String libellePoste;
    private final String codeDepartement;
    private final String utlrLogin;
    private final String codePoste;
    private final String codeImmeuble;
    private final String codeFonctionDes;
    private final String libelleFonction;
    
    private final String code;   // code HTTP ou returncode selon le cas
    private final String detail; // détail optionnel renvoyé par STB

    private StbLookupResult(
            Status status,
            String nom,
            String prenom,
            String mail,
            String libelleImmeuble,
            String nomComplet,
            Integer codeFonction,
            String mobilePhone,
            String businessPhone,
            String libelleDepartement,
            String libellePoste,
            String codeDepartement,
            String utlrLogin,
            String codePoste,
            String codeImmeuble,
            String codeFonctionDes,
            String libelleFonction,
            String code,
            String detail) {

        this.status = status;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.libelleImmeuble = libelleImmeuble;
        this.nomComplet = nomComplet;
        this.codeFonction = codeFonction;
        this.mobilePhone = mobilePhone;
        this.businessPhone = businessPhone;
        this.libelleDepartement = libelleDepartement;
        this.libellePoste = libellePoste;
        this.codeDepartement = codeDepartement;
        this.utlrLogin = utlrLogin;
        this.codePoste = codePoste;
        this.codeImmeuble = codeImmeuble;
        this.codeFonctionDes = codeFonctionDes;
        this.libelleFonction = libelleFonction;
        this.code = code;
        this.detail = detail;
    }


 // ============================================================
    // SUCCESS
    // ============================================================

    public static StbLookupResult success(
            String nom,
            String prenom,
            String mail,
            String libelleImmeuble,
            String nomComplet,
            Integer codeFonction,
            String mobilePhone,
            String businessPhone,
            String libelleDepartement,
            String libellePoste,
            String codeDepartement,
            String utlrLogin,
            String codePoste,
            String codeImmeuble,
            String codeFonctionDes,
            String libelleFonction) {

        return new StbLookupResult(
                Status.SUCCESS,
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
                libelleFonction,
                null,
                null
        );
    }


    // ============================================================
    // ERREURS TOKEN
    // ============================================================

    public static StbLookupResult tokenUnavailable() {
        return new StbLookupResult(
                Status.TOKEN_UNAVAILABLE,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }


    public static StbLookupResult tokenAuthError() {
        return new StbLookupResult(
                Status.TOKEN_AUTH_ERROR,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }


    public static StbLookupResult tokenAccessDenied() {
        return new StbLookupResult(
                Status.TOKEN_ACCESS_DENIED,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }


    // ============================================================
    // ERREUR SERVICE
    // ============================================================

    public static StbLookupResult serviceUnavailable() {
        return new StbLookupResult(
                Status.SERVICE_UNAVAILABLE,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }


    public static StbLookupResult serviceError(
            String httpCode,
            String detail) {

        return new StbLookupResult(
                Status.SERVICE_ERROR,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                httpCode,
                detail
        );
    }


    // ============================================================
    // REPONSE INVALIDE
    // ============================================================

    public static StbLookupResult invalidResponse(
            String returnCode) {

        return new StbLookupResult(
                Status.INVALID_RESPONSE,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                returnCode,
                null
        );
    }


    // ============================================================
    // UTILISATEUR NON TROUVE
    // ============================================================

    public static StbLookupResult userNotFound(
            String returnCode) {

        return new StbLookupResult(
                Status.USER_NOT_FOUND,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                returnCode,
                null
        );
    }


    public Status getStatus() { return status; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getMail() { return mail; }
    public String getCode() { return code; }
    public String getDetail() { return detail; }


	public String getLibelleImmeuble() {
		return libelleImmeuble;
	}


	public String getNomComplet() {
		return nomComplet;
	}


	public Integer getCodeFonction() {
		return codeFonction;
	}


	public String getMobilePhone() {
		return mobilePhone;
	}


	public String getBusinessPhone() {
		return businessPhone;
	}


	public String getLibelleDepartement() {
		return libelleDepartement;
	}


	public String getLibellePoste() {
		return libellePoste;
	}


	public String getCodeDepartement() {
		return codeDepartement;
	}


	public String getUtlrLogin() {
		return utlrLogin;
	}


	public String getCodePoste() {
		return codePoste;
	}


	public String getCodeImmeuble() {
		return codeImmeuble;
	}


	public String getCodeFonctionDes() {
		return codeFonctionDes;
	}


	public String getLibelleFonction() {
		return libelleFonction;
	}
    
    
}