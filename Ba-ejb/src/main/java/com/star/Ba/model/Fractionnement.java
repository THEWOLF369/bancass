package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the FRACTIONNEMENT database table.
 * 
 */
@Entity
@NamedQuery(name="Fractionnement.findAll", query="SELECT f FROM Fractionnement f")
public class Fractionnement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FRACTIONNEMENT_IDFRC_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FRACTIONNEMENT_IDFRC_GENERATOR")
	@Column(name="ID_FRC")
	private int idFrc;

	@Column(name="NOMBRE_FRACTION")
	private int nombre_Fraction;
	
	private String libelle;

	public Fractionnement() {
	}

	public int getIdFrc() {
		return this.idFrc;
	}

	public void setIdFrc(int idFrc) {
		this.idFrc = idFrc;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	
	public int getNombre_Fraction() {
		return nombre_Fraction;
	}

	public void setNombre_Fraction(int nombre_Fraction) {
		this.nombre_Fraction = nombre_Fraction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idFrc ^ (idFrc >>> 32));
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Fractionnement [idFrc=" + idFrc + ", libelle=" + libelle + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fractionnement other = (Fractionnement) obj;
		if (idFrc != other.idFrc)
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		return true;
	}

}