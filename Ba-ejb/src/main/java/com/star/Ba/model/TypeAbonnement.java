package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the TYPE_ABONNEMENT database table.
 * 
 */
@Entity
@Table(name="TYPE_ABONNEMENT")
@NamedQuery(name="TypeAbonnement.findAll", query="SELECT t FROM TypeAbonnement t")
public class TypeAbonnement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TYPE_ABONNEMENT")
	private long idTypeAbonnement;

	@Column(name="VAL_TYPE_ABONNEMENT")
	private String valTypeAbonnement;

	@Column(name="MAX_NUMBER")
	private long maxNumber;
	
	
	public long getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(long maxNumber) {
		this.maxNumber = maxNumber;
	}

	public TypeAbonnement() {
	}

	public long getIdTypeAbonnement() {
		return this.idTypeAbonnement;
	}

	public void setIdTypeAbonnement(long idTypeAbonnement) {
		this.idTypeAbonnement = idTypeAbonnement;
	}

	public String getValTypeAbonnement() {
		return this.valTypeAbonnement;
	}

	public void setValTypeAbonnement(String valTypeAbonnement) {
		this.valTypeAbonnement = valTypeAbonnement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idTypeAbonnement ^ (idTypeAbonnement >>> 32));
		result = prime * result + (int) (maxNumber ^ (maxNumber >>> 32));
		result = prime
				* result
				+ ((valTypeAbonnement == null) ? 0 : valTypeAbonnement
						.hashCode());
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
		TypeAbonnement other = (TypeAbonnement) obj;
		if (idTypeAbonnement != other.idTypeAbonnement)
			return false;
		if (maxNumber != other.maxNumber)
			return false;
		if (valTypeAbonnement == null) {
			if (other.valTypeAbonnement != null)
				return false;
		} else if (!valTypeAbonnement.equals(other.valTypeAbonnement))
			return false;
		return true;
	}
	

}