/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author star
 */
@Entity
@Table(name = "PARAMETRES")
public class Parametres implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID_PARAM", nullable = false)
	private Integer				idParam;
	@Size(max = 10)
	@Column(name = "CODE_PARAM", length = 10)
	private String				codeParam;
	@Size(max = 250)
	@Column(name = "LIBELLE_PARAM", length = 250)
	private String				libelleParam;
	@Column(name = "valBool")
	private Integer valBool;
	@Column(name = "valInt", precision = 18, scale = 3)
    private BigDecimal valInt;
	@Size(max = 100)
	@Column(name = "valString", length = 100)
	private String				valString;
	@OneToMany(mappedBy="idParam")
	private List<ParametresPack> parametresPacks;
	public Parametres() {
	}

	public Parametres(Integer idParam) {
		super();
		this.idParam = idParam;
	}

	public Integer getIdParam() {
		return idParam;
	}

	public void setIdParam(Integer idParam) {
		this.idParam = idParam;
	}

	public String getCodeParam() {
		return codeParam;
	}

	public void setCodeParam(String codeParam) {
		this.codeParam = codeParam;
	}

	public String getLibelleParam() {
		return libelleParam;
	}

	public void setLibelleParam(String libelleParam) {
		this.libelleParam = libelleParam;
	}

	public Integer getValBool() {
		return valBool;
	}

	public void setValBool(Integer valBool) {
		this.valBool = valBool;
	}

	public BigDecimal getValInt() {
		return valInt;
	}

	public void setValInt(BigDecimal valInt) {
		this.valInt = valInt;
	}

	public String getValString() {
		return valString;
	}

	public void setValString(String valString) {
		this.valString = valString;
	}

	
	public List<ParametresPack> getParametresPacks() {
		return parametresPacks;
	}

	public void setParametresPacks(List<ParametresPack> parametresPacks) {
		this.parametresPacks = parametresPacks;
	}

	@Override
	public String toString() {
		return "Parametres [idParam=" + idParam + ", codeParam=" + codeParam
				+ ", libelleParam=" + libelleParam + ", valBool=" + valBool
				+ ", valInt=" + valInt + ", valString=" + valString + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeParam == null) ? 0 : codeParam.hashCode());
		result = prime * result + ((idParam == null) ? 0 : idParam.hashCode());
		result = prime * result
				+ ((libelleParam == null) ? 0 : libelleParam.hashCode());
		result = prime * result + ((valBool == null) ? 0 : valBool.hashCode());
		result = prime * result + ((valInt == null) ? 0 : valInt.hashCode());
		result = prime * result
				+ ((valString == null) ? 0 : valString.hashCode());
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
		Parametres other = (Parametres) obj;
		if (codeParam == null) {
			if (other.codeParam != null)
				return false;
		} else if (!codeParam.equals(other.codeParam))
			return false;
		if (idParam == null) {
			if (other.idParam != null)
				return false;
		} else if (!idParam.equals(other.idParam))
			return false;
		if (libelleParam == null) {
			if (other.libelleParam != null)
				return false;
		} else if (!libelleParam.equals(other.libelleParam))
			return false;
		if (valBool == null) {
			if (other.valBool != null)
				return false;
		} else if (!valBool.equals(other.valBool))
			return false;
		if (valInt == null) {
			if (other.valInt != null)
				return false;
		} else if (!valInt.equals(other.valInt))
			return false;
		if (valString == null) {
			if (other.valString != null)
				return false;
		} else if (!valString.equals(other.valString))
			return false;
		return true;
	}

	
	

}
