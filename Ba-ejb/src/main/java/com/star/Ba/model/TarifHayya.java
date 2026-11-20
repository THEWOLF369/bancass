package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TARIF_INCENDIE database table.
 * 
 */
@Entity
@Table(name="TARIF_HAYYA")
@NamedQuery(name="TarifHayya.findAll", query="SELECT t FROM TarifHayya t")
public class TarifHayya implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TARIF_HAYYA_IDHAYYA_GENERATOR", sequenceName="ID_HAYYA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TARIF_HAYYA_IDHAYYA_GENERATOR")
	@Column(name="ID_HAYYA")
	private int idHayya;

	private double commission;

	private String police;

	@Column(name="PRIME_MIN")
	private double primeMin;

	private double taux;

	public TarifHayya() {
	}

	

	public int getIdHayya() {
		return idHayya;
	}



	public void setIdHayya(int idHayya) {
		this.idHayya = idHayya;
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