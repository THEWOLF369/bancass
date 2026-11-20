package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the TARIF_ASSIST database table.
 * 
 */
@Entity
@Table(name="TARIF_ASSIST")
@NamedQuery(name="TarifAssist.findAll", query="SELECT t FROM TarifAssist t")
public class TarifAssist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ASSIST")
	private long idAssist;

	private BigDecimal commission;

	@Column(name="ID_DUREE")
	private BigDecimal idDuree;

	@Column(name="ID_TYPE_ABONNEMENT")
	private BigDecimal idTypeAbonnement;

	private String police;

	private BigDecimal prime;
	private Double agemax;

	private Double agemin;
	private Double majoration;
	//bi-directional many-to-one association to Pack
		@ManyToOne
		@JoinColumn(name="PACK")
		private Pack pack;

	public TarifAssist() {
	}

	public long getIdAssist() {
		return this.idAssist;
	}

	public void setIdAssist(long idAssist) {
		this.idAssist = idAssist;
	}

	public BigDecimal getCommission() {
		return this.commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getIdDuree() {
		return this.idDuree;
	}

	public void setIdDuree(BigDecimal idDuree) {
		this.idDuree = idDuree;
	}

	public BigDecimal getIdTypeAbonnement() {
		return this.idTypeAbonnement;
	}

	public void setIdTypeAbonnement(BigDecimal idTypeAbonnement) {
		this.idTypeAbonnement = idTypeAbonnement;
	}

	public String getPolice() {
		return this.police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public BigDecimal getPrime() {
		return this.prime;
	}

	public void setPrime(BigDecimal prime) {
		this.prime = prime;
	}

	public Double getAgemax() {
		return agemax;
	}

	public void setAgemax(Double agemax) {
		this.agemax = agemax;
	}

	public Double getAgemin() {
		return agemin;
	}

	public void setAgemin(Double agemin) {
		this.agemin = agemin;
	}

	public Double getMajoration() {
		return majoration;
	}

	public void setMajoration(Double majoration) {
		this.majoration = majoration;
	}

	public Pack getPack() {
		return pack;
	}

	public void setPack(Pack pack) {
		this.pack = pack;
	}

	

	
	
	

}