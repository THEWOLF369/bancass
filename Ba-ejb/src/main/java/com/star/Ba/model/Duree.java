package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the DUREE database table.
 * 
 */
@Entity
@Table(name="DUREE")
@NamedQuery(name="Duree.findAll", query="SELECT d FROM Duree d")
public class Duree implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_DUREE")
	private long idDuree;

	@Column(name="VAL_DUREE")
	private String valDuree;

	public Duree() {
	}

	public long getIdDuree() {
		return this.idDuree;
	}

	public void setIdDuree(long idDuree) {
		this.idDuree = idDuree;
	}

	public String getValDuree() {
		return this.valDuree;
	}

	public void setValDuree(String valDuree) {
		this.valDuree = valDuree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idDuree ^ (idDuree >>> 32));
		result = prime * result
				+ ((valDuree == null) ? 0 : valDuree.hashCode());
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
		Duree other = (Duree) obj;
		if (idDuree != other.idDuree)
			return false;
		if (valDuree == null) {
			if (other.valDuree != null)
				return false;
		} else if (!valDuree.equals(other.valDuree))
			return false;
		return true;
	}

}