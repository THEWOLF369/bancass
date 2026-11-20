package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the DURE_SERVICE_BOURSE database table.
 * 
 */
@Entity
@Table(name="AGE_SERVICE_BOURSE")
@NamedQuery(name="AgeServiceBourse.findAll", query="SELECT d FROM AgeServiceBourse d")
public class AgeServiceBourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DURE_SERVICE_BOURSE_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DURE_SERVICE_BOURSE_ID_GENERATOR")
	@Column(name="ID_SERVICE_BOURSE")
	private long id;

	
	@Column(name="AGE_SERVICE_BOURSE")
	private BigDecimal ageServiceBourse;

	public AgeServiceBourse() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAgeServiceBourse() {
		return ageServiceBourse;
	}

	public void setAgeServiceBourse(BigDecimal ageServiceBourse) {
		this.ageServiceBourse = ageServiceBourse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((ageServiceBourse == null) ? 0 : ageServiceBourse.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		AgeServiceBourse other = (AgeServiceBourse) obj;
		if (ageServiceBourse == null) {
			if (other.ageServiceBourse != null)
				return false;
		} else if (!ageServiceBourse.equals(other.ageServiceBourse))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AgeServiceBourse [id=" + id + ", ageServiceBourse="
				+ ageServiceBourse + "]";
	}

	

}