package com.star.Ba.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the BENEFICIAIRE database table.
 * 
 */
@Entity
@Table(name="BENEF_HAYYA_VIE")
@NamedQuery(name="BenefHayyaVie.findAll", query="SELECT b FROM BenefHayyaVie b")
public class BenefHayyaVie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_BENEF_HAYYA_VIE")
	@SequenceGenerator(name = "ID_BENEF_HAYYA_VIE", sequenceName = "ID_BENEF_HAYYA_VIE", allocationSize = 1)
	@Column(name="ID_BENEF_HAYYA_VIE")
	private long idBenefVie;
	
	
	 

	private String nom;

	private String prenom;
	
	private String cin;
	
	private String lien;
	
	private int quote;

  
	private int type;
	

	//bi-directional many-to-one association to Adhesion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ADHESION")
	private Adhesion adhesion;

	//bi-directional many-to-one association to Adhesion
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="ID_PROPOSITION")
		private Proposition propositions;
		
	public BenefHayyaVie() {
	}

	public long getIdBenefVie() {
		return idBenefVie;
	}

	public void setIdBenefVie(long idBenefVie) {
		this.idBenefVie = idBenefVie;
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

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getLien() {
		return lien;
	}

	public void setLien(String lien) {
		this.lien = lien;
	}

	public int getQuote() {
		return quote;
	}

	public void setQuote(int quote) {
		this.quote = quote;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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


	
	
	
	

}