package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the DURE_SERVICE_BOURSE database table.
 * 
 */
@Entity
@Table(name="DURE_SERVICE_BOURSE")
@NamedQuery(name="DureServiceBourse.findAll", query="SELECT d FROM DureServiceBourse d")
public class DureServiceBourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DURE_SERVICE_BOURSE_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DURE_SERVICE_BOURSE_ID_GENERATOR")
	private long id;

	private BigDecimal dure;

	public DureServiceBourse() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getDure() {
		return this.dure;
	}

	public void setDure(BigDecimal dure) {
		this.dure = dure;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dure == null) ? 0 : dure.hashCode());
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
		DureServiceBourse other = (DureServiceBourse) obj;
		if (dure == null) {
			if (other.dure != null)
				return false;
		} else if (!dure.equals(other.dure))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DureServiceBourse [id=" + id + ", dure=" + dure + "]";
	}

}