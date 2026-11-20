package com.star.Ba.model;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

//@SqlResultSetMapping(
//        name = "AgenceProduitMapping",
//        entities = @EntityResult(
//                entityClass = AgenceProduit.class,
//                fields = {
//                    @FieldResult(name = "idagence", column = "ID_AGENCE"),
//                    @FieldResult(name = "codeagence", column = "code_agence"),
//                    @FieldResult(name = "libelle", column = "LIBELLE"),
//                    @FieldResult(name = "produit", column = "libelle_produit"),
//                    @FieldResult(name = "nombre", column = "nb_prd"),
//                    @FieldResult(name = "mtprime", column = "mt_prime"),
//                    @FieldResult(name = "primetot", column = "primetot")}))

public class AgenceProduit {
	
	

	
	private int idagence;
	private String codeagence;
	private String libelle;
	private String produit;
	private int idproduit;
	private int nombre;
	private double mtprime;
	private double primetot;
	private String semaine;
	
	
	
	
	
	public AgenceProduit() {
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





	public int getNombre() {
		return nombre;
	}





	public void setNombre(int nombre) {
		this.nombre = nombre;
	}





	public double getMtprime() {
		return mtprime;
	}





	public void setMtprime(double mtprime) {
		this.mtprime = mtprime;
	}





	public double getPrimetot() {
		return primetot;
	}





	public void setPrimetot(double primetot) {
		this.primetot = primetot;
	}





	public String getSemaine() {
		return semaine;
	}





	public void setSemaine(String semaine) {
		this.semaine = semaine;
	}





	public int getIdproduit() {
		return idproduit;
	}





	public void setIdproduit(int idproduit) {
		this.idproduit = idproduit;
	}
	
	
	
	 
	

}
