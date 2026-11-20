/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author star
 */
@Entity
@Table(name = "PARAMETRES_PACK")
public class ParametresPack implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID_PP", nullable = false)
	private Integer idPp;
	@Size(max = 100)
	@Column(name = "LIBELLE_PP", length = 100)
	private String libellePp;
	@Column(name = "PACK")
	private Integer pack;
	@JoinColumn(name = "ID_PARAM", referencedColumnName = "ID_PARAM")
	@ManyToOne
	private Parametres idParam;

	public ParametresPack() {
	}

	public ParametresPack(Integer idPp) {
		super();
		this.idPp = idPp;
	}

	public Integer getIdPp() {
		return idPp;
	}

	public void setIdPp(Integer idPp) {
		this.idPp = idPp;
	}

	public String getLibellePp() {
		return libellePp;
	}

	public void setLibellePp(String libellePp) {
		this.libellePp = libellePp;
	}

	public Integer getPack() {
		return pack;
	}

	public void setPack(Integer pack) {
		this.pack = pack;
	}

	public Parametres getIdParam() {
		return idParam;
	}

	public void setIdParam(Parametres idParam) {
		this.idParam = idParam;
	}
	

	@Override
	public String toString() {
		return "ParametresPack [idPp=" + idPp + ", libellePp=" + libellePp
				+ ", pack=" + pack + ", idParam=" + idParam + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idParam == null) ? 0 : idParam.hashCode());
		result = prime * result + ((idPp == null) ? 0 : idPp.hashCode());
		result = prime * result
				+ ((libellePp == null) ? 0 : libellePp.hashCode());
		result = prime * result + ((pack == null) ? 0 : pack.hashCode());
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
		ParametresPack other = (ParametresPack) obj;
		if (idParam == null) {
			if (other.idParam != null)
				return false;
		} else if (!idParam.equals(other.idParam))
			return false;
		if (idPp == null) {
			if (other.idPp != null)
				return false;
		} else if (!idPp.equals(other.idPp))
			return false;
		if (libellePp == null) {
			if (other.libellePp != null)
				return false;
		} else if (!libellePp.equals(other.libellePp))
			return false;
		if (pack == null) {
			if (other.pack != null)
				return false;
		} else if (!pack.equals(other.pack))
			return false;
		return true;
	}

}
