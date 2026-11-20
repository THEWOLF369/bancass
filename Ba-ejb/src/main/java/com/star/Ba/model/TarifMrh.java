package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the TARIF_MRH database table.
 * 
 */
@Entity
@Table(name="TARIF_MRH")
@NamedQuery(name="TarifMrh.findAll", query="SELECT t FROM TarifMrh t")
public class TarifMrh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TARIF_MRH_IDTARIF_MRH_GENERATOR", sequenceName="TARIF_MRH")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TARIF_MRH_IDTARIF_MRH_GENERATOR")
	@Column(name="ID_TARIF_MRH")
	private long idTarifMrh;
	
	@Column(name="MAJOR_INHAB")
	private BigDecimal majorInhab;

	@Column(name="PRIX_PIECE")
	private BigDecimal prixPiece;

	@Column(name="PRM_ASSH")
	private BigDecimal prmAssh;

	@Column(name="PRM_SCOLAIRE")
	private BigDecimal prmScolaire;

	@Column(name="RED_APPARTEMENT")
	private BigDecimal redAppartement;

	public TarifMrh() {
	}

	public BigDecimal getMajorInhab() {
		return this.majorInhab;
	}

	public void setMajorInhab(BigDecimal majorInhab) {
		this.majorInhab = majorInhab;
	}

	public BigDecimal getPrixPiece() {
		return this.prixPiece;
	}

	public void setPrixPiece(BigDecimal prixPiece) {
		this.prixPiece = prixPiece;
	}

	public BigDecimal getPrmAssh() {
		return this.prmAssh;
	}

	public void setPrmAssh(BigDecimal prmAssh) {
		this.prmAssh = prmAssh;
	}

	public BigDecimal getPrmScolaire() {
		return this.prmScolaire;
	}

	public void setPrmScolaire(BigDecimal prmScolaire) {
		this.prmScolaire = prmScolaire;
	}

	public BigDecimal getRedAppartement() {
		return this.redAppartement;
	}

	public void setRedAppartement(BigDecimal redAppartement) {
		this.redAppartement = redAppartement;
	}
	
	

	public long getIdTarifMrh() {
		return idTarifMrh;
	}

	public void setIdTarifMrh(long idTarifMrh) {
		this.idTarifMrh = idTarifMrh;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((majorInhab == null) ? 0 : majorInhab.hashCode());
		result = prime * result
				+ ((prixPiece == null) ? 0 : prixPiece.hashCode());
		result = prime * result + ((prmAssh == null) ? 0 : prmAssh.hashCode());
		result = prime * result
				+ ((prmScolaire == null) ? 0 : prmScolaire.hashCode());
		result = prime * result
				+ ((redAppartement == null) ? 0 : redAppartement.hashCode());
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
		TarifMrh other = (TarifMrh) obj;
		if (majorInhab == null) {
			if (other.majorInhab != null)
				return false;
		} else if (!majorInhab.equals(other.majorInhab))
			return false;
		if (prixPiece == null) {
			if (other.prixPiece != null)
				return false;
		} else if (!prixPiece.equals(other.prixPiece))
			return false;
		if (prmAssh == null) {
			if (other.prmAssh != null)
				return false;
		} else if (!prmAssh.equals(other.prmAssh))
			return false;
		if (prmScolaire == null) {
			if (other.prmScolaire != null)
				return false;
		} else if (!prmScolaire.equals(other.prmScolaire))
			return false;
		if (redAppartement == null) {
			if (other.redAppartement != null)
				return false;
		} else if (!redAppartement.equals(other.redAppartement))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TarifMrh [majorInhab=" + majorInhab + ", prixPiece="
				+ prixPiece + ", prmAssh=" + prmAssh + ", prmScolaire="
				+ prmScolaire + ", redAppartement=" + redAppartement + "]";
	}

}