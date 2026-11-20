package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the NIVEAU_SCOLAIRE database table.
 * 
 */
@Entity
@Table(name="NIVEAU_SCOLAIRE")
@NamedQuery(name="NiveauScolaire.findAll", query="SELECT n FROM NiveauScolaire n")
public class NiveauScolaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="NIVEAU_SCOLAIRE_IDNIVEAUSCOLAIRE_GENERATOR", sequenceName="NIVEAUSCOLAIRE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NIVEAU_SCOLAIRE_IDNIVEAUSCOLAIRE_GENERATOR")
	@Column(name="ID_NIVEAU_SCOLAIRE")
	private long idNiveauScolaire;

	@Column(name="NIVEAU_SCOL")
	private String niveauScol;

	public NiveauScolaire() {
	}

	public long getIdNiveauScolaire() {
		return this.idNiveauScolaire;
	}

	public void setIdNiveauScolaire(long idNiveauScolaire) {
		this.idNiveauScolaire = idNiveauScolaire;
	}

	public String getNiveauScol() {
		return this.niveauScol;
	}

	public void setNiveauScol(String niveauScol) {
		this.niveauScol = niveauScol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (idNiveauScolaire ^ (idNiveauScolaire >>> 32));
		result = prime * result
				+ ((niveauScol == null) ? 0 : niveauScol.hashCode());
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
		NiveauScolaire other = (NiveauScolaire) obj;
		if (idNiveauScolaire != other.idNiveauScolaire)
			return false;
		if (niveauScol == null) {
			if (other.niveauScol != null)
				return false;
		} else if (!niveauScol.equals(other.niveauScol))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NiveauScolaire [idNiveauScolaire=" + idNiveauScolaire
				+ ", niveauScol=" + niveauScol + "]";
	}

}