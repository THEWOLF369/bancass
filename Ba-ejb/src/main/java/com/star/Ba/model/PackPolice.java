/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

/**
 * 
 * @author Administrateur
 */
@Entity
@Table(name = "PACK_POLICE")
@NamedQueries({ @NamedQuery(name = "PackPolice.findAll", query = "SELECT p FROM PackPolice p") })
public class PackPolice implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected PackPolicePK packPolicePK;
	@Column(name = "DATECREATION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datecreation;
	@Column(name = "ID_CLIENT")
	private long idClient;
	@Column(name = "ETA_POLICE")
	private String etaPolice;

	public PackPolice() {
	}

	public PackPolice(PackPolicePK packPolicePK) {
		this.packPolicePK = packPolicePK;
	}

	public PackPolice(BigInteger idPackPolice, String nPolice) {
		this.packPolicePK = new PackPolicePK(idPackPolice, nPolice);
	}

	public String getEtaPolice() {
		return etaPolice;
	}

	public void setEtaPolice(String etaPolice) {
		this.etaPolice = etaPolice;
	}

	public long getIdClient() {
		return idClient;
	}

	public void setIdClient(long idClient) {
		this.idClient = idClient;
	}

	public PackPolicePK getPackPolicePK() {
		return packPolicePK;
	}

	public void setPackPolicePK(PackPolicePK packPolicePK) {
		this.packPolicePK = packPolicePK;
	}

	public Date getDatecreation() {
		return datecreation;
	}

	public void setDatecreation(Date datecreation) {
		this.datecreation = datecreation;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (packPolicePK != null ? packPolicePK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof PackPolice)) {
			return false;
		}
		PackPolice other = (PackPolice) object;
		if ((this.packPolicePK == null && other.packPolicePK != null)
				|| (this.packPolicePK != null && !this.packPolicePK
						.equals(other.packPolicePK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.star.Ba.model.PackPolice[ packPolicePK=" + packPolicePK
				+ " ]";
	}

}
