package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the TAUXCOMM database table.
 * 
 */
@Entity
@NamedQuery(name = "Tauxcomm.findAll", query = "SELECT t FROM Tauxcomm t")
public class Tauxcomm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TAUXCOMM_IDTAUXCOMM_GENERATOR", sequenceName = "TAUXCOMM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAUXCOMM_IDTAUXCOMM_GENERATOR")
	@Column(name = "ID_TAUXCOMM")
	private long idTauxcomm;

	// bi-directional many-to-one association to Produit_test
	@ManyToOne
	@JoinColumn(name = "CODPROD")
	private Produit produit;

	@JoinColumn(name = "ID_PRODCONV", referencedColumnName = "ID_PRODCONV")
    @ManyToOne
    private Prodconv idProdconv;

	@Temporal(TemporalType.DATE)
	private Date datappli;

	private BigDecimal taux;

	public Tauxcomm() {
	}

	public long getIdTauxcomm() {
		return this.idTauxcomm;
	}

	public void setIdTauxcomm(long idTauxcomm) {
		this.idTauxcomm = idTauxcomm;
	}

	public Produit getProduit() {
		return this.produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Date getDatappli() {
		return this.datappli;
	}

	public void setDatappli(Date datappli) {
		this.datappli = datappli;
	}

	public BigDecimal getTaux() {
		return this.taux;
	}

	public void setTaux(BigDecimal taux) {
		this.taux = taux;
	}

	public Prodconv getIdProdconv() {
		return idProdconv;
	}
	public void setIdProdconv(Prodconv idProdconv) {
		this.idProdconv = idProdconv;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datappli == null) ? 0 : datappli.hashCode());
		result = prime * result + (int) (idTauxcomm ^ (idTauxcomm >>> 32));
		result = prime * result + ((produit == null) ? 0 : produit.hashCode());
		result = prime * result + ((taux == null) ? 0 : taux.hashCode());
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
		Tauxcomm other = (Tauxcomm) obj;
		if (datappli == null) {
			if (other.datappli != null)
				return false;
		} else if (!datappli.equals(other.datappli))
			return false;
		if (idTauxcomm != other.idTauxcomm)
			return false;
		if (produit == null) {
			if (other.produit != null)
				return false;
		} else if (!produit.equals(other.produit))
			return false;
		if (taux == null) {
			if (other.taux != null)
				return false;
		} else if (!taux.equals(other.taux))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tauxcomm [idTauxcomm=" + idTauxcomm + ", produit=" + produit + ", datappli=" + datappli + ", taux=" + taux + "]";
	}

}