package com.star.Ba.dto;

import java.util.Date;



public class ProduitStat {
	
	
	private int idagence;
	private String codeagence;
	private String libelle;
	private String produit;
	private String contrat;
	private String ncompte;
	private String numquit;

    private String dateop;
	private double mtprime;
	private double commission;
	private double rtsource;
	private int id_pack;
	private String id_bordereau;


	
	
	public ProduitStat() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getIdagence() {
		return idagence;
	}


	public void setIdagence(int idagence) {
		this.idagence = idagence;
	}


	public String getCodeagence() {
		return codeagence;
	}


	public void setCodeagence(String codeagence) {
		this.codeagence = codeagence;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public String getProduit() {
		return produit;
	}


	public void setProduit(String produit) {
		this.produit = produit;
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


	public double getCommission() {
		return commission;
	}


	public void setCommission(double commission) {
		this.commission = commission;
	}


	public String getNcompte() {
		return ncompte;
	}


	public void setNcompte(String ncompte) {
		this.ncompte = ncompte;
	}


	public String getDateop() {
		return dateop;
	}


	public void setDateop(String dateop) {
		this.dateop = dateop;
	}


	public String getNumquit() {
		return numquit;
	}


	public void setNumquit(String numquit) {
		this.numquit = numquit;
	}


	public double getRtsource() {
		return rtsource;
	}


	public void setRtsource(double rtsource) {
		this.rtsource = rtsource;
	}


	public int getId_pack() {
		return id_pack;
	}


	public void setId_pack(int id_pack) {
		this.id_pack = id_pack;
	}


	public String getId_bordereau() {
		return id_bordereau;
	}


	public void setId_bordereau(String id_bordereau) {
		this.id_bordereau = id_bordereau;
	}












	


}
