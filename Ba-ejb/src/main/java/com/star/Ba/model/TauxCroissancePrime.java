package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the TAUX_CROISSANCE_PRIME database table.
 * 
 */
@Entity
@Table(name="TAUX_CROISSANCE_PRIME")
@NamedQuery(name="TauxCroissancePrime.findAll", query="SELECT t FROM TauxCroissancePrime t")
public class TauxCroissancePrime implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAUX_CROISSANCE_PRIME_IDTAUX_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAUX_CROISSANCE_PRIME_IDTAUX_GENERATOR")
	@Column(name="ID_TAUX")
	private long idTaux;

	@Column(name="TAUX_PRIME")
	private BigDecimal tauxPrime;

	public TauxCroissancePrime() {
	}

	public long getIdTaux() {
		return this.idTaux;
	}

	public void setIdTaux(long idTaux) {
		this.idTaux = idTaux;
	}

	public BigDecimal getTauxPrime() {
		return this.tauxPrime;
	}

	public void setTauxPrime(BigDecimal tauxPrime) {
		this.tauxPrime = tauxPrime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idTaux ^ (idTaux >>> 32));
		result = prime * result
				+ ((tauxPrime == null) ? 0 : tauxPrime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TauxCroissancePrime other = (TauxCroissancePrime) obj;
		if (idTaux != other.idTaux)
			return false;
		if (tauxPrime == null) {
			if (other.tauxPrime != null)
				return false;
		} else if (!tauxPrime.equals(other.tauxPrime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TauxCroissancePrime [idTaux=" + idTaux + ", tauxPrime="
				+ tauxPrime + "]";
	}

}