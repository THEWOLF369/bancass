/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Administrateur
 */
@Entity
// @Table(name = "PARCELLE", catalog = "", schema = "BANCASS")
@NamedQueries({ @NamedQuery(name = "Parcelle.findAll", query = "SELECT p FROM Parcelle p") })
public class Parcelle implements Serializable {
	private static final long serialVersionUID = 1L;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PARCELLE")
	@SequenceGenerator(name = "ID_PARCELLE", sequenceName = "ID_PARCELLE", allocationSize = 1)
	@NotNull
	@Column(name = "ID_PARCELLE", nullable = false, precision = 38, scale = 0)
	private BigDecimal idParcelle;
	@Size(max = 150)
	@Column(name = "VARIETE", length = 150)
	private String variete;
	@Size(max = 200)
	@Column(name = "SITUATION", length = 200)
	private String situation;
	
	@Column(name = "LOCALITE", length = 255)
	private String localite;
	@Size(max = 150)
	@Column(name = "NUMTITREFONCIER", length = 150)
	private String numtitrefoncier;
	@Column(name = "IDDELEGATION")
	private BigDecimal iddelegation;
	@Column(name = "SUPERFICIE", precision = 18, scale = 3)
	private BigDecimal superficie;
	@Column(name = "PRIXUNITAIRE", precision = 18, scale = 3)
	private BigDecimal prixunitaire;
	@Column(name = "NBRPIED")
	private Integer nbrpied;
	@Column(name = "EXTENSIONGRELE")
	private int extensiongrele;
	@Column(name = "MAJORATIONVOISINAGEINCENDIE")
	private int majorationvoisinageincendie;
	@Column(name = "EXTENSIONINCENDIE")
	private int extensionincendie;
	@Column(name = "N_POLICE")
	private String nPolice;
	@Column(name = "RENDEMENT")
	private String rendement;
	@Column(name = "UNITE_RENDEMENT")
	private String uniteRendement;
	@Column(name = "UNITE_PRIX")
	private String unitePRix;

	public Parcelle() {
	}

	public Parcelle(BigDecimal idParcelle) {
		this.idParcelle = idParcelle;
	}

	public BigDecimal getIdParcelle() {
		return idParcelle;
	}

	public void setIdParcelle(BigDecimal idParcelle) {
		this.idParcelle = idParcelle;
	}

	public String getVariete() {
		return variete;
	}

	public void setVariete(String variete) {
		this.variete = variete;
	}

	public String getNumtitrefoncier() {
		return numtitrefoncier;
	}

	public void setNumtitrefoncier(String numtitrefoncier) {
		this.numtitrefoncier = numtitrefoncier;
	}

	public BigDecimal getIddelegation() {
		return iddelegation;
	}

	public void setIddelegation(BigDecimal iddelegation) {
		this.iddelegation = iddelegation;
	}

	public BigDecimal getSuperficie() {
		return superficie;
	}

	public void setSuperficie(BigDecimal superficie) {
		this.superficie = superficie;
	}

	public BigDecimal getPrixunitaire() {
		return prixunitaire;
	}

	public void setPrixunitaire(BigDecimal prixunitaire) {
		this.prixunitaire = prixunitaire;
	}

	public Integer getNbrpied() {
		return nbrpied;
	}

	public void setNbrpied(Integer nbrpied) {
		this.nbrpied = nbrpied;
	}

	public int getExtensiongrele() {
		return extensiongrele;
	}

	public void setExtensiongrele(int extensiongrele) {
		this.extensiongrele = extensiongrele;
	}

	public int getMajorationvoisinageincendie() {
		return majorationvoisinageincendie;
	}

	public void setMajorationvoisinageincendie(int majorationvoisinageincendie) {
		this.majorationvoisinageincendie = majorationvoisinageincendie;
	}

	public int getExtensionincendie() {
		return extensionincendie;
	}

	public void setExtensionincendie(int extensionincendie) {
		this.extensionincendie = extensionincendie;
	}

	public String getnPolice() {
		return nPolice;
	}

	public void setnPolice(String nPolice) {
		this.nPolice = nPolice;
	}

	public String getRendement() {
		return rendement;
	}

	public void setRendement(String rendement) {
		this.rendement = rendement;
	}

	public String getUniteRendement() {
		return uniteRendement;
	}

	public void setUniteRendement(String uniteRendement) {
		this.uniteRendement = uniteRendement;
	}

	public String getUnitePRix() {
		return unitePRix;
	}

	public void setUnitePRix(String unitePRix) {
		this.unitePRix = unitePRix;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idParcelle != null ? idParcelle.hashCode() : 0);
		return hash;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public String getLocalite() {
		return localite;
	}
	public void setLocalite(String localite) {
		this.localite = localite;
	}
	

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Parcelle)) {
			return false;
		}
		Parcelle other = (Parcelle) object;
		if ((this.idParcelle == null && other.idParcelle != null) || (this.idParcelle != null && !this.idParcelle.equals(other.idParcelle))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Parcelle [idParcelle=" + idParcelle + ", variete=" + variete + ", numtitrefoncier=" + numtitrefoncier + ", iddelegation="
				+ iddelegation + ", superficie=" + superficie + ", prixunitaire=" + prixunitaire + ", nbrpied=" + nbrpied + ", extensiongrele="
				+ extensiongrele + ", majorationvoisinageincendie=" + majorationvoisinageincendie + ", extensionincendie=" + extensionincendie
				+ ", nPolice=" + nPolice + ", localite=" + localite + "]";
	}

}
