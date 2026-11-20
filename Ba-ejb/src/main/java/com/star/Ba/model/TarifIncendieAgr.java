/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Administrateur
 */
@Entity
@Table(name = "TARIF_INCENDIE_AGR")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "TarifIncendieAgr.findAll", query = "SELECT t FROM TarifIncendieAgr t"),
		@NamedQuery(name = "TarifIncendieAgr.findByIdtarif", query = "SELECT t FROM TarifIncendieAgr t WHERE t.idtarif = :idtarif"),
		@NamedQuery(name = "TarifIncendieAgr.findByMin", query = "SELECT t FROM TarifIncendieAgr t WHERE t.min = :min"),
		@NamedQuery(name = "TarifIncendieAgr.findByMax", query = "SELECT t FROM TarifIncendieAgr t WHERE t.max = :max"),
		@NamedQuery(name = "TarifIncendieAgr.findByTaux", query = "SELECT t FROM TarifIncendieAgr t WHERE t.taux = :taux"),
		@NamedQuery(name = "TarifIncendieAgr.findByFranchise", query = "SELECT t FROM TarifIncendieAgr t WHERE t.franchise = :franchise"),
		@NamedQuery(name = "TarifIncendieAgr.findByCommision", query = "SELECT t FROM TarifIncendieAgr t WHERE t.commision = :commision"),
		@NamedQuery(name = "TarifIncendieAgr.findByPrimeMin", query = "SELECT t FROM TarifIncendieAgr t WHERE t.primeMin = :primeMin") })
public class TarifIncendieAgr implements Serializable {
	private static final long serialVersionUID = 1L;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "IDTARIF", nullable = false, precision = 38, scale = 0)
	private BigDecimal idtarif;
	@Column(name = "MIN", precision = 18, scale = 3)
	private BigDecimal min;
	@Column(name = "MAX", precision = 18, scale = 3)
	private BigDecimal max;
	@Column(name = "TAUX", precision = 18, scale = 5)
	private BigDecimal taux;
	@Column(name = "FRANCHISE", precision = 18, scale = 3)
	private BigDecimal franchise;
	@Column(name = "COMMISION", precision = 18, scale = 3)
	private BigDecimal commision;
	@Column(name = "PRIME_MIN", precision = 18, scale = 3)
	private BigDecimal primeMin;
	@JoinColumn(name = "IDPRODCONV", referencedColumnName = "ID_PRODCONV")
	@ManyToOne
	private Prodconv idprodconv;
	@Column(name = "EXTENSION_VA")
	private BigDecimal extensionVa;
	@Column(name = "EXTENSION_PP")
	private BigDecimal extensionpp;

	public BigDecimal getExtensionVa() {
		return extensionVa;
	}

	public void setExtensionVa(BigDecimal extensionVa) {
		this.extensionVa = extensionVa;
	}

	public BigDecimal getExtensionpp() {
		return extensionpp;
	}

	public void setExtensionpp(BigDecimal extensionpp) {
		this.extensionpp = extensionpp;
	}

	public TarifIncendieAgr() {
	}

	public TarifIncendieAgr(BigDecimal idtarif) {
		this.idtarif = idtarif;
	}

	public BigDecimal getIdtarif() {
		return idtarif;
	}

	public void setIdtarif(BigDecimal idtarif) {
		this.idtarif = idtarif;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getTaux() {
		return taux;
	}

	public void setTaux(BigDecimal taux) {
		this.taux = taux;
	}

	public BigDecimal getFranchise() {
		return franchise;
	}

	public void setFranchise(BigDecimal franchise) {
		this.franchise = franchise;
	}

	public BigDecimal getCommision() {
		return commision;
	}

	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}

	public BigDecimal getPrimeMin() {
		return primeMin;
	}

	public void setPrimeMin(BigDecimal primeMin) {
		this.primeMin = primeMin;
	}

	public Prodconv getIdprodconv() {
		return idprodconv;
	}

	public void setIdprodconv(Prodconv idprodconv) {
		this.idprodconv = idprodconv;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idtarif != null ? idtarif.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TarifIncendieAgr)) {
			return false;
		}
		TarifIncendieAgr other = (TarifIncendieAgr) object;
		if ((this.idtarif == null && other.idtarif != null) || (this.idtarif != null && !this.idtarif.equals(other.idtarif))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.star.Ba.model.TarifIncendieAgr[ idtarif=" + idtarif + " ]";
	}

}
