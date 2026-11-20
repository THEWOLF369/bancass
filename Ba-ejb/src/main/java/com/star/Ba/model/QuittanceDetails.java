package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the QUITTANCE database table.
 * 
 */
@Entity
@Table(name="QUITTANCE_DETAILS")
@NamedQuery(name="QuittanceDetails.findAll", query="SELECT q FROM QuittanceDetails q")
public class QuittanceDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="ID_QUITTANCE")
	private long idQuittance;
	
	
	 @ManyToOne
	 @JoinColumn(name = "ID_AGENCE")
	 private Agence agence;


	 @Column(name="TYPE_QUIT")
	 private String typequit;
	 
	 private String etat;
	 
	 private int frac;
	 
	 



	 @Column(name="PRIME_TOT")
	private Double primetot;
	 
	 @Column(name="PRIME_NETTE")
	private Double primenette;
	
	 
	 @Column(name="PRIME_TOT_WITH_SURPRIME")
		private Double primetot_with_surprime;
	 
	 
	 @Column(name="PRIME_NETTE_WITH_SURPRIME")
		private Double primenette_with_surprime;
	 
	 @Column(name="DATE_ENC")
	 private Date dateEnc;
	 
	 @Column(name="REF_OPERATION")
		private String refOperation;
	 
	 
	 @ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="ID_ADHESION")
		private Adhesion idAdhesion;
	 

	public QuittanceDetails() {
	}


	public long getIdQuittance() {
		return idQuittance;
	}


	public void setIdQuittance(long idQuittance) {
		this.idQuittance = idQuittance;
	}


	public Agence getAgence() {
		return agence;
	}


	public void setAgence(Agence agence) {
		this.agence = agence;
	}


	public String getTypequit() {
		return typequit;
	}


	public void setTypequit(String typequit) {
		this.typequit = typequit;
	}


	public String getEtat() {
		return etat;
	}


	public void setEtat(String etat) {
		this.etat = etat;
	}


	public int getFrac() {
		return frac;
	}


	public void setFrac(int frac) {
		this.frac = frac;
	}


	public Double getPrimetot() {
		return primetot;
	}


	public void setPrimetot(Double primetot) {
		this.primetot = primetot;
	}


	public Double getPrimenette() {
		return primenette;
	}


	public void setPrimenette(Double primenette) {
		this.primenette = primenette;
	}
	
	
	
	
	


	public Double getPrimetot_with_surprime() {
		return primetot_with_surprime;
	}


	public void setPrimetot_with_surprime(Double primetot_with_surprime) {
		this.primetot_with_surprime = primetot_with_surprime;
	}
	
	
	


	public Double getPrimenette_with_surprime() {
		return primenette_with_surprime;
	}


	public void setPrimenette_with_surprime(Double primenette_with_surprime) {
		this.primenette_with_surprime = primenette_with_surprime;
	}
	
	
	
	


	public Date getDateEnc() {
		return dateEnc;
	}


	public void setDateEnc(Date dateEnc) {
		this.dateEnc = dateEnc;
	}


	public String getRefOperation() {
		return refOperation;
	}


	public void setRefOperation(String refOperation) {
		this.refOperation = refOperation;
	}


	public Adhesion getIdAdhesion() {
		return idAdhesion;
	}


	public void setIdAdhesion(Adhesion idAdhesion) {
		this.idAdhesion = idAdhesion;
	}


	public QuittanceDetails(long idQuittance, Adhesion idAdhesion, Agence agence, String typequit, String etat, int frac, Double primetot,
			Double primenette) {
		super();
		this.idQuittance = idQuittance;
		this.idAdhesion = idAdhesion;
		this.agence = agence;
		this.typequit = typequit;
		this.etat = etat;
		this.frac = frac;
		this.primetot = primetot;
		this.primenette = primenette;
	}


	public QuittanceDetails(Agence agence, Adhesion idAdhesion, String typequit, String etat, int frac, Double primetot, Double primenette) {
		super();
		this.agence = agence;
		this.idAdhesion = idAdhesion;
		this.typequit = typequit;
		this.etat = etat;
		this.frac = frac;
		this.primetot = primetot;
		this.primenette = primenette;
	}
	
	
	
	public QuittanceDetails(long idQuittance, Adhesion idAdhesion, Agence agence, String typequit, String etat, int frac, Double primetot,
			Double primenette, Double primetot_with_surprime, Double primenette_with_surprime) {
		super();
		this.idQuittance = idQuittance;
		this.idAdhesion = idAdhesion;
		this.agence = agence;
		this.typequit = typequit;
		this.etat = etat;
		this.frac = frac;
		this.primetot = primetot;
		this.primenette = primenette;
		this.primetot_with_surprime = primetot_with_surprime;
		this.primenette_with_surprime = primenette_with_surprime;
	}
	
	


	
	


}