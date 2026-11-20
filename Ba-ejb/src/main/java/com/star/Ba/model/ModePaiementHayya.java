package com.star.Ba.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the BENEFICIAIRE database table.
 * 
 */
@Entity
@Table(name="MODE_PAIMENT_HAYYA")
@NamedQuery(name="ModePaiementHayya.findAll", query="SELECT b FROM ModePaiementHayya b")
public class ModePaiementHayya implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_MODE_PAIMENT_HAYYA")
	@SequenceGenerator(name = "ID_MODE_PAIMENT_HAYYA", sequenceName = "ID_MODE_PAIMENT_HAYYA", allocationSize = 1)
	@Column(name="ID_MODE_PAIMENT_HAYYA")
	private long idModePaiement;
	
	
	private int frac;
	
	private int modep;
	 
	private String banque;
	
	private String agence;
	
	private String rib;
	
	private int payeur;

	private String nom;

	private String cin;
	
	private String adresse;
	
	private String disposition;
	
	@Column(name = "DATE_CREATION")
	private Date datecreation;
	
	@Column(name = "JOUR_PRELEVEMENT")
	private Integer jourPrelevement;
	
//	@Column(name = "ANNEE_PRELEVEMENT")
//	private Integer anneePrelevement;
	
	
	@Column(name = "MOIS_PRELEVEMENT")
	private Integer moisPrelevement;

	//bi-directional many-to-one association to Adhesion
	@ManyToOne
	@JoinColumn(name="ID_ADHESION")
	private Adhesion adhesion;

	//bi-directional many-to-one association to Adhesion
		@ManyToOne
		@JoinColumn(name="ID_PROPOSITION")
		private Proposition propositions;
		
	public ModePaiementHayya() {
	}

	

	
	


	public long getIdModePaiement() {
		return idModePaiement;
	}







	public void setIdModePaiement(long idModePaiement) {
		this.idModePaiement = idModePaiement;
	}











	public int getFrac() {
		return frac;
	}







	public void setFrac(int frac) {
		this.frac = frac;
	}







	public int getModep() {
		return modep;
	}







	public void setModep(int modep) {
		this.modep = modep;
	}







	public String getBanque() {
		return banque;
	}







	public void setBanque(String banque) {
		this.banque = banque;
	}







	public String getAgence() {
		return agence;
	}







	public void setAgence(String agence) {
		this.agence = agence;
	}







	public String getRib() {
		return rib;
	}







	public void setRib(String rib) {
		this.rib = rib;
	}







	public int getPayeur() {
		return payeur;
	}







	public void setPayeur(int payeur) {
		this.payeur = payeur;
	}







	public String getAdresse() {
		return adresse;
	}







	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}







	public String getDisposition() {
		return disposition;
	}







	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}







	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	 

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	 

	 

	public Adhesion getAdhesion() {
		return adhesion;
	}

	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}

	public Proposition getPropositions() {
		return propositions;
	}

	public void setPropositions(Proposition propositions) {
		this.propositions = propositions;
	}







	public Date getDatecreation() {
		return datecreation;
	}







	public void setDatecreation(Date datecreation) {
		this.datecreation = datecreation;
	}







	public Integer getJourPrelevement() {
		return jourPrelevement;
	}







	public void setJourPrelevement(Integer jourPrelevement) {
		this.jourPrelevement = jourPrelevement;
	}







	public Integer getMoisPrelevement() {
		return moisPrelevement;
	}







	public void setMoisPrelevement(Integer moisPrelevement) {
		this.moisPrelevement = moisPrelevement;
	}





	
	
	
	

}