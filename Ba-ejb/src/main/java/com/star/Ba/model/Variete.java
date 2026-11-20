/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "VARIETE", catalog = "", schema = "BANCASSPROD")
public class Variete implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VARIETE", nullable = false, precision = 38, scale = 0)
    private BigDecimal id_variete;
    @Size(max = 100)
    @Column(name = "NOM_VARIETE", length = 100)
    private String nomVariete;
    @Column(name = "CLASSE", length = 100)
    private String classe;

    
    public Variete() {
    }

	public Variete(BigDecimal id_variete) {
		super();
		this.id_variete = id_variete;
	}

	

	public BigDecimal getId_variete() {
		return id_variete;
	}

	public void setId_variete(BigDecimal id_variete) {
		this.id_variete = id_variete;
	}

	public String getNomVariete() {
		return nomVariete;
	}

	public void setNomVariete(String nomVariete) {
		this.nomVariete = nomVariete;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classe == null) ? 0 : classe.hashCode());
		result = prime * result
				+ ((id_variete == null) ? 0 : id_variete.hashCode());
		result = prime * result
				+ ((nomVariete == null) ? 0 : nomVariete.hashCode());
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
		Variete other = (Variete) obj;
		if (classe == null) {
			if (other.classe != null)
				return false;
		} else if (!classe.equals(other.classe))
			return false;
		if (id_variete == null) {
			if (other.id_variete != null)
				return false;
		} else if (!id_variete.equals(other.id_variete))
			return false;
		if (nomVariete == null) {
			if (other.nomVariete != null)
				return false;
		} else if (!nomVariete.equals(other.nomVariete))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Variete [id_variete=" + id_variete + ", nomVariete="
				+ nomVariete + ", classe=" + classe + "]";
	}



	

	
	
    
 
}
