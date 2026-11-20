package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TARIF_INCENDIE database table.
 * 
 */
@Entity
@Table(name="TARIF_INCENDIE")
@NamedQuery(name="TarifIncendie.findAll", query="SELECT t FROM TarifIncendie t")
public class TarifIncendie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TARIF_INCENDIE_IDINCENDIE_GENERATOR", sequenceName="ID_INCENDIE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TARIF_INCENDIE_IDINCENDIE_GENERATOR")
	@Column(name="ID_INCENDIE")
	private int idIncendie;

	private double commission;

	private String police;

	@Column(name="PRIME_MIN")
	private double primeMin;

	private double taux;

	public TarifIncendie() {
	}

	public int getIdIncendie() {
		return this.idIncendie;
	}

	public void setIdIncendie(int idIncendie) {
		this.idIncendie = idIncendie;
	}

	public double getCommission() {
		return this.commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getPolice() {
		return this.police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public double getPrimeMin() {
		return this.primeMin;
	}

	public void setPrimeMin(double primeMin) {
		this.primeMin = primeMin;
	}

	public double getTaux() {
		return this.taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}

}