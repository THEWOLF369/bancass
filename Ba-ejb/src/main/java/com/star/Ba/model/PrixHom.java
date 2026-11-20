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
@Table(name = "PRIXHOM")
public class PrixHom implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPRIX", nullable = false, precision = 38, scale = 0)
    private BigDecimal idprix;
    @Size(max = 100)
    @Column(name = "LIBELLE", length = 100)
    private String libelle;
    @Column(name = "VALEUR", nullable = false, precision = 38, scale = 0)
    private BigDecimal valeur;
    @Column(name = "DATEAPPLICATION")
    private Date dateApplication;

    public PrixHom() {
    }

	public PrixHom(BigDecimal idprix) {
		super();
		this.idprix = idprix;
	}

	public BigDecimal getIdprix() {
		return idprix;
	}

	public void setIdprix(BigDecimal idprix) {
		this.idprix = idprix;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public BigDecimal getValeur() {
		return valeur;
	}

	public void setValeur(BigDecimal valeur) {
		this.valeur = valeur;
	}

	public Date getDateApplication() {
		return dateApplication;
	}

	public void setDateApplication(Date dateApplication) {
		this.dateApplication = dateApplication;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateApplication == null) ? 0 : dateApplication.hashCode());
		result = prime * result + ((idprix == null) ? 0 : idprix.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result + ((valeur == null) ? 0 : valeur.hashCode());
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
		PrixHom other = (PrixHom) obj;
		if (dateApplication == null) {
			if (other.dateApplication != null)
				return false;
		} else if (!dateApplication.equals(other.dateApplication))
			return false;
		if (idprix == null) {
			if (other.idprix != null)
				return false;
		} else if (!idprix.equals(other.idprix))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		if (valeur == null) {
			if (other.valeur != null)
				return false;
		} else if (!valeur.equals(other.valeur))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PrixHom [idprix=" + idprix + ", libelle=" + libelle + ", valeur=" + valeur + ", dateApplication=" + dateApplication + "]";
	}
    
 
}
