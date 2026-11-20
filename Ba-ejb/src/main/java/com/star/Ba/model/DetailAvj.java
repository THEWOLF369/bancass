package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DETAIL_AVJ database table.
 * 
 */
@Entity
@Table(name="DETAIL_AVJ")
@NamedQuery(name="DetailAvj.findAll", query="SELECT d FROM DetailAvj d")
public class DetailAvj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DETAIL_AVJ_IDDETAILAVJ_GENERATOR", sequenceName="DETAILAVJ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DETAIL_AVJ_IDDETAILAVJ_GENERATOR")
	@Column(name="ID_DETAIL_AVJ")
	private long idDetailAvj;

	@Column(name="AGE_SERVICE_BOURSE")
	private BigDecimal ageServiceBourse;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_PREMIER_BOURSE")
	private Date datePremierBourse;

	@Column(name="DUREE_BOURSE")
	private BigDecimal dureeBourse;

 
	
	//bi-directional many-to-one association to Proposition_test
		@OneToMany(mappedBy="detailAvj")
		private List<Adhesion> adhesions;

	@Column(name="TAUX_CROISSANCE_PRIME")
	private BigDecimal idTauxCroissancePrime;

	@Column(name="MT_BOURSE")
	private BigDecimal mtBourse;

//	@Column(name="RENTE_DECES")
//	private String renteDeces;

	
	@Column(name="TAUX_CROISSANCE_BOURSE")
	private BigDecimal tauxCroissanceBourse;
	 
	
	@Column(name="SURPRIME")
	private BigDecimal surprime;
	
	
	
	@Column(name="CLIENT_WS")
	private Boolean clientWs;
	
	@Column(name="DTRANCHE")
	private BigDecimal dtranche;

	 
	//bi-directional many-to-one association to Proposition_test
	@OneToMany(mappedBy="detailAvj")
	private List<Proposition> propositions;

	public DetailAvj() {
	}

	public long getIdDetailAvj() {
		return this.idDetailAvj;
	}

	public void setIdDetailAvj(long idDetailAvj) {
		this.idDetailAvj = idDetailAvj;
	}

	public BigDecimal getAgeServiceBourse() {
		return this.ageServiceBourse;
	}

	public void setAgeServiceBourse(BigDecimal ageServiceBourse) {
		this.ageServiceBourse = ageServiceBourse;
	}

	public Date getDatePremierBourse() {
		return this.datePremierBourse;
	}

	public void setDatePremierBourse(Date datePremierBourse) {
		this.datePremierBourse = datePremierBourse;
	}

	public BigDecimal getDureeBourse() {
		return this.dureeBourse;
	}

	public void setDureeBourse(BigDecimal dureeBourse) {
		this.dureeBourse = dureeBourse;
	}

//	public String getFraisInscription() {
//		return this.fraisInscription;
//	}
//
//	public void setFraisInscription(String fraisInscription) {
//		this.fraisInscription = fraisInscription;
//	}

	 

	public BigDecimal getIdTauxCroissancePrime() {
		return this.idTauxCroissancePrime;
	}

	public void setIdTauxCroissancePrime(BigDecimal idTauxCroissancePrime) {
		this.idTauxCroissancePrime = idTauxCroissancePrime;
	}
	
	public BigDecimal getTauxCroissanceBourse() {
		return tauxCroissanceBourse;
	}

	public void setTauxCroissanceBourse(BigDecimal tauxCroissanceBourse) {
		this.tauxCroissanceBourse = tauxCroissanceBourse;
	}

	public BigDecimal getMtBourse() {
		return this.mtBourse;
	}

	public void setMtBourse(BigDecimal mtBourse) {
		this.mtBourse = mtBourse;
	}
	
	
	

//	public String getRenteDeces() {
//		return this.renteDeces;
//	}
//
//	public void setRenteDeces(String renteDeces) {
//		this.renteDeces = renteDeces;
//	}
 

	 

	public BigDecimal getSurprime() {
		return surprime;
	}

	public void setSurprime(BigDecimal surprime) {
		this.surprime = surprime;
	}
	
	
	

	public Boolean getClientWs() {
		return clientWs;
	}

	public void setClientWs(Boolean clientWs) {
		this.clientWs = clientWs;
	}
	
	

	public BigDecimal getDtranche() {
		return dtranche;
	}

	public void setDtranche(BigDecimal dtranche) {
		this.dtranche = dtranche;
	}

	public List<Proposition> getPropositions() {
		return this.propositions;
	}

	public void setPropositions(List<Proposition> propositions) {
		this.propositions = propositions;
	}
	
	

	public List<Adhesion> getAdhesions() {
		return adhesions;
	}

	public void setAdhesions(List<Adhesion> adhesions) {
		this.adhesions = adhesions;
	}

	public Proposition addProposition(Proposition proposition) {
		getPropositions().add(proposition);
		proposition.setDetailAvj(this);

		return proposition;
	}

	public Proposition removeProposition(Proposition proposition) {
		getPropositions().remove(proposition);
		proposition.setDetailAvj(null);

		return proposition;
	}

}