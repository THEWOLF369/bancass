package com.star.Ba.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the BENEFICIAIRE database table.
 * 
 */
@Entity
@Table(name="BENEF_HAYYA_DECES")
@NamedQuery(name="BenefHayyaDeces.findAll", query="SELECT b FROM BenefHayyaDeces b")
public class BenefHayyaDeces implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_BENEF_HAYYA_DECES")
	@SequenceGenerator(name = "ID_BENEF_HAYYA_DECES", sequenceName = "ID_BENEF_HAYYA_DECES", allocationSize = 1)
	@Column(name="ID_BENEF_HAYYA_DECES")
	private long idBenefDeces;
	
	
	 

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
		
	public BenefHayyaDeces() {
	}

	

	public long getIdBenefDeces() {
		return idBenefDeces;
	}



	public void setIdBenefDeces(long idBenefDeces) {
		this.idBenefDeces = idBenefDeces;
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