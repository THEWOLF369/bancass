/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Administrateur
 */
@Entity
@Table(name = "CONTENU")
@NamedQueries({ @NamedQuery(name = "Contenu.findAll", query = "SELECT c FROM Contenu c") })
public class Contenu implements Serializable {
	private static final long serialVersionUID = 1L;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "IDCONTENU")
	private BigDecimal idcontenu;
	@Size(max = 100)
	@Column(name = "LIBELLECONTENU")
	private String libellecontenu;
	@Size(max = 100)
	@Column(name = "URL")
	private String url;
	@OneToMany(mappedBy = "idcontenu", fetch = FetchType.LAZY)
	private List<Menucontenu> menucontenuList;
	@ManyToOne
	@JoinColumn(name = "idprodconv")
	private Prodconv idprodconv;

	public Contenu() {
	}

	public Contenu(BigDecimal idcontenu) {
		this.idcontenu = idcontenu;
	}

	public BigDecimal getIdcontenu() {
		return idcontenu;
	}

	public Prodconv getIdprodconv() {
		return idprodconv;
	}

	public void setIdprodconv(Prodconv idprodconv) {
		this.idprodconv = idprodconv;
	}

	public void setIdcontenu(BigDecimal idcontenu) {
		this.idcontenu = idcontenu;
	}

	public String getLibellecontenu() {
		return libellecontenu;
	}

	public void setLibellecontenu(String libellecontenu) {
		this.libellecontenu = libellecontenu;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Menucontenu> getMenucontenuList() {
		return menucontenuList;
	}

	public void setMenucontenuList(List<Menucontenu> menucontenuList) {
		this.menucontenuList = menucontenuList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idcontenu != null ? idcontenu.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Contenu)) {
			return false;
		}
		Contenu other = (Contenu) object;
		if ((this.idcontenu == null && other.idcontenu != null) || (this.idcontenu != null && !this.idcontenu.equals(other.idcontenu))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.star.Ba.model.Contenu[ idcontenu=" + idcontenu + " ]";
	}

}
