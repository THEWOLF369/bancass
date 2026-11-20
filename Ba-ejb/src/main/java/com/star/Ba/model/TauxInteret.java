package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the TAUX_INTERET database table.
 * 
 */
@Entity
@Table(name="TAUX_INTERET")
@NamedQuery(name="TauxInteret.findAll", query="SELECT t FROM TauxInteret t")
public class TauxInteret implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long annee;

	@Column(name="DUREE_INF_10ANS")
	private BigDecimal dureeInf10ans;

	@Column(name="DUREE_SUP_10ANS")
	private BigDecimal dureeSup10ans;

	public TauxInteret() {
	}

	public long getAnnee() {
		return this.annee;
	}

	public void setAnnee(long annee) {
		this.annee = annee;
	}

	public BigDecimal getDureeInf10ans() {
		return this.dureeInf10ans;
	}

	public void setDureeInf10ans(BigDecimal dureeInf10ans) {
		this.dureeInf10ans = dureeInf10ans;
	}

	public BigDecimal getDureeSup10ans() {
		return this.dureeSup10ans;
	}

	public void setDureeSup10ans(BigDecimal dureeSup10ans) {
		this.dureeSup10ans = dureeSup10ans;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (annee ^ (annee >>> 32));
		result = prime * result
				+ ((dureeInf10ans == null) ? 0 : dureeInf10ans.hashCode());
		result = prime * result
				+ ((dureeSup10ans == null) ? 0 : dureeSup10ans.hashCode());
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
		TauxInteret other = (TauxInteret) obj;
		if (annee != other.annee)
			return false;
		if (dureeInf10ans == null) {
			if (other.dureeInf10ans != null)
				return false;
		} else if (!dureeInf10ans.equals(other.dureeInf10ans))
			return false;
		if (dureeSup10ans == null) {
			if (other.dureeSup10ans != null)
				return false;
		} else if (!dureeSup10ans.equals(other.dureeSup10ans))
			return false;
		return true;
	}

}