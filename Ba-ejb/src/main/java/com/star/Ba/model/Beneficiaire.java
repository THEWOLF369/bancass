package com.star.Ba.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the BENEFICIAIRE database table.
 * 
 */
@Entity
@NamedQuery(name="Beneficiaire.findAll", query="SELECT b FROM Beneficiaire b")
public class Beneficiaire implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_BENEFICIAIRE")
	@SequenceGenerator(name = "ID_BENEFICIAIRE", sequenceName = "ID_BENEFICIAIRE", allocationSize = 1)
	@Column(name="ID_BENEFICIAIRE")
	private long idBeneficiaire;
	
	
	private String adresse;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_NAISS")
	private Date dateNaiss;

	private String nom;

	private String prenom;

	private String sexe;
	
	
	@ManyToOne
	@JoinColumn(name = "ID_NIVEAU_SCOLAIRE")
	private NiveauScolaire niveauScolaire;

	//bi-directional many-to-one association to Adhesion
	@ManyToOne
	@JoinColumn(name="ID_ADHESION")
	private Adhesion adhesion;

	//bi-directional many-to-one association to Adhesion
		@ManyToOne
		@JoinColumn(name="ID_PROPOSITION")
		private Proposition propositions;
		
	public Beneficiaire() {
	}

	public long getIdBeneficiaire() {
		return this.idBeneficiaire;
	}

	public void setIdBeneficiaire(long idBeneficiaire) {
		this.idBeneficiaire = idBeneficiaire;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Date getDateNaiss() {
		return this.dateNaiss;
	}

	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getSexe() {
		return this.sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public Adhesion getAdhesion() {
		return this.adhesion;
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

	public NiveauScolaire getNiveauScolaire() {
		return niveauScolaire;
	}

	public void setNiveauScolaire(NiveauScolaire niveauScolaire) {
		this.niveauScolaire = niveauScolaire;
	}
	
	
	

}