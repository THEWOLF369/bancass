package com.star.Ba.dto;

public class QuittancesClient {
	
	private Double montant, fga;
	private String identifiant, nom,prenom;
	private int codeRetour;
	private String msgRetour;
	private Boolean packComplet;

	private String nomPack;
	private String numdecision;
	private String numdetaildecision;
	private Double commision;
	public QuittancesClient() {
		// TODO Auto-generated constructor stub
	}
	public QuittancesClient(Double montant, Double fga, String identifiant,
			String nom, String prenom, Double commision, String numdecision, String numdetaildecision) {
		super();
		this.montant = montant;
		this.fga = fga;
		this.identifiant = identifiant;
		this.nom = nom;
		this.prenom = prenom;
		this.commision = commision;
		this.numdecision = numdecision;
		this.numdetaildecision = numdetaildecision;
	}
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	public Double getFga() {
		return fga;
	}
	public void setFga(Double fga) {
		this.fga = fga;
	}
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getCodeRetour() {
		return codeRetour;
	}
	public void setCodeRetour(int codeRetour) {
		this.codeRetour = codeRetour;
	}
	public String getMsgRetour() {
		return msgRetour;
	}
	public void setMsgRetour(String msgRetour) {
		this.msgRetour = msgRetour;
	}
	public Boolean getPackComplet() {
		return packComplet;
	}
	public void setPackComplet(Boolean packComplet) {
		this.packComplet = packComplet;
	}
	public String getNomPack() {
		return nomPack;
	}
	public void setNomPack(String nomPack) {
		this.nomPack = nomPack;
	}
	public Double getCommision() {
		return commision;
	}
	public void setCommision(Double commision) {
		this.commision = commision;
	}
	public String getNumdecision() {
		return numdecision;
	}
	public void setNumdecision(String numdecision) {
		this.numdecision = numdecision;
	}
	public String getNumdetaildecision() {
		return numdetaildecision;
	}
	public void setNumdetaildecision(String numdetaildecision) {
		this.numdetaildecision = numdetaildecision;
	}


}
