package com.star.Ba.dto;

import java.util.Date;

public class ImportData {

	// private int idagence;
	private String codeagence;
	private String ncompte;
	// private String libelle;
	// private String produit;
	private String contrat;
	private String nom_client;
	private String prenom_client;
	private String adhesion;
	private String debut;
	private String fin;
	private double duree;
	private String etat;
	private String annulation;
	private double mtprime;
	private double commission;
	private String date_create;
	private String type_assist;
	
	private double ppnette;
	private String fract;
	private double surprime;
	private double ppnette_avec_surprime;



	public ImportData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCodeagence() {
		return codeagence;
	}

	public void setCodeagence(String codeagence) {
		this.codeagence = codeagence;
	}

	public String getContrat() {
		return contrat;
	}

	public void setContrat(String contrat) {
		this.contrat = contrat;
	}

	public double getMtprime() {
		return mtprime;
	}

	public void setMtprime(double mtprime) {
		this.mtprime = mtprime;
	}

	public String getNcompte() {
		return ncompte;
	}

	public void setNcompte(String ncompte) {
		this.ncompte = ncompte;
	}

	public String getNom_client() {
		return nom_client;
	}

	public void setNom_client(String nom_client) {
		this.nom_client = nom_client;
	}

	public String getPrenom_client() {
		return prenom_client;
	}

	public void setPrenom_client(String prenom_client) {
		this.prenom_client = prenom_client;
	}

	public String getAdhesion() {
		return adhesion;
	}

	public void setAdhesion(String adhesion) {
		this.adhesion = adhesion;
	}

	public String getDebut() {
		return debut;
	}

	public void setDebut(String debut) {
		this.debut = debut;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public double getDuree() {
		return duree;
	}

	public void setDuree(double duree) {
		this.duree = duree;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getAnnulation() {
		return annulation;
	}

	public void setAnnulation(String annulation) {
		this.annulation = annulation;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getDate_create() {
		return date_create;
	}

	public void setDate_create(String date_create) {
		this.date_create = date_create;
	}

	public String getType_assist() {
		return type_assist;
	}

	public void setType_assist(String type_assist) {
		this.type_assist = type_assist;
	}

	public double getPpnette() {
		return ppnette;
	}

	public void setPpnette(double ppnette) {
		this.ppnette = ppnette;
	}

	public String getFract() {
		return fract;
	}

	public void setFract(String fract) {
		this.fract = fract;
	}

	public double getSurprime() {
		return surprime;
	}

	public void setSurprime(double surprime) {
		this.surprime = surprime;
	}

	public double getPpnette_avec_surprime() {
		return ppnette_avec_surprime;
	}

	public void setPpnette_avec_surprime(double ppnette_avec_surprime) {
		this.ppnette_avec_surprime = ppnette_avec_surprime;
	}
	
	
	

}
