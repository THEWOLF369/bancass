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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Administrateur
 */
@Entity
@Table(name = "TARIF_GRELE")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "TarifGrele.findAll", query = "SELECT t FROM TarifGrele t"),
		@NamedQuery(name = "TarifGrele.findByIdtarif", query = "SELECT t FROM TarifGrele t WHERE t.idtarif = :idtarif"),
		@NamedQuery(name = "TarifGrele.findByZone", query = "SELECT t FROM TarifGrele t WHERE t.zone = :zone"),
		@NamedQuery(name = "TarifGrele.findByClasse1", query = "SELECT t FROM TarifGrele t WHERE t.classe1 = :classe1"),
		@NamedQuery(name = "TarifGrele.findByClasse2", query = "SELECT t FROM TarifGrele t WHERE t.classe2 = :classe2"),
		@NamedQuery(name = "TarifGrele.findByClasse3", query = "SELECT t FROM TarifGrele t WHERE t.classe3 = :classe3"),
		@NamedQuery(name = "TarifGrele.findByClasse4", query = "SELECT t FROM TarifGrele t WHERE t.classe4 = :classe4"),
		@NamedQuery(name = "TarifGrele.findByClasse5", query = "SELECT t FROM TarifGrele t WHERE t.classe5 = :classe5"),
		@NamedQuery(name = "TarifGrele.findByClasse6", query = "SELECT t FROM TarifGrele t WHERE t.classe6 = :classe6"),		
		@NamedQuery(name = "TarifGrele.findByClasse1_b", query = "SELECT t FROM TarifGrele t WHERE t.classe1_b = :classe1_b"),
		@NamedQuery(name = "TarifGrele.findByClasse1_p", query = "SELECT t FROM TarifGrele t WHERE t.classe1_p = :classe1_p"),
		
		@NamedQuery(name = "TarifGrele.findByClasse2_v", query = "SELECT t FROM TarifGrele t WHERE t.classe2_v = :classe2_v"),
		@NamedQuery(name = "TarifGrele.findByClasse6_1", query = "SELECT t FROM TarifGrele t WHERE t.classe6_1 = :classe6_1"),
		@NamedQuery(name = "TarifGrele.findByClasse3_1", query = "SELECT t FROM TarifGrele t WHERE t.classe3_1 = :classe3_1"),
		@NamedQuery(name = "TarifGrele.findByClasse5_1", query = "SELECT t FROM TarifGrele t WHERE t.classe5_1 = :classe5_1"),

		@NamedQuery(name = "TarifGrele.findByFranchise", query = "SELECT t FROM TarifGrele t WHERE t.franchise = :franchise") })

public class TarifGrele implements Serializable {
	
	private static final long serialVersionUID = 1L;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "IDTARIF", nullable = false, precision = 38, scale = 0)
	private BigDecimal idtarif;
	@Size(max = 150)
	@Column(name = "ZONE", length = 150)
	private String zone;
	@Size(max = 50)
	@Column(name = "CLASSE1", length = 50)
	private String classe1;
	@Size(max = 50)
	@Column(name = "CLASSE2", length = 50)
	private String classe2;
	@Size(max = 50)
	@Column(name = "CLASSE3", length = 50)
	private String classe3;
	@Size(max = 50)
	@Column(name = "CLASSE4", length = 50)
	private String classe4;
	@Size(max = 50)
	@Column(name = "CLASSE5", length = 50)
	private String classe5;
	@Size(max = 50)
	@Column(name = "CLASSE6", length = 50)
	private String classe6;
	@Size(max = 50)
	@Column(name = "CLASSE1_B", length = 50)
	private String classe1_b;
	@Size(max = 50)
	@Column(name = "CLASSE1_P", length = 50)
	private String classe1_p;
	@Size(max = 50)
	@Column(name = "CLASSE2_V", length = 50)
	private String classe2_v;
	@Size(max = 50)
	@Column(name = "CLASSE6_1", length = 50)
	private String classe6_1;
	@Size(max = 50)
	@Column(name = "CLASSE3_1", length = 50)
	private String classe3_1;
	@Size(max = 50)
	@Column(name = "CLASSE5_1", length = 50)
	private String classe5_1;

	
	
	@Column(name = "FRANCHISE")
	private BigInteger franchise;
	@JoinColumn(name = "IDPRODCONV", referencedColumnName = "ID_PRODCONV")
	@ManyToOne(fetch = FetchType.LAZY)
	private Prodconv idprodconv;
	@Column(name = "EXTENSION")
	private BigDecimal extension;

	public TarifGrele() {
	}

	public TarifGrele(BigDecimal idtarif) {
		this.idtarif = idtarif;
	}

	public BigDecimal getExtension() {
		return extension;
	}

	public void setExtension(BigDecimal extension) {
		this.extension = extension;
	}

	public BigDecimal getIdtarif() {
		return idtarif;
	}

	public void setIdtarif(BigDecimal idtarif) {
		this.idtarif = idtarif;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getClasse1() {
		return classe1;
	}

	public void setClasse1(String classe1) {
		this.classe1 = classe1;
	}

	public String getClasse2() {
		return classe2;
	}

	public void setClasse2(String classe2) {
		this.classe2 = classe2;
	}

	public String getClasse3() {
		return classe3;
	}

	public void setClasse3(String classe3) {
		this.classe3 = classe3;
	}

	public String getClasse4() {
		return classe4;
	}

	public void setClasse4(String classe4) {
		this.classe4 = classe4;
	}

	public String getClasse5() {
		return classe5;
	}

	public void setClasse5(String classe5) {
		this.classe5 = classe5;
	}

	public String getClasse6() {
		return classe6;
	}
	
	

	public String getClasse1_b() {
		return classe1_b;
	}

	public void setClasse1_b(String classe1_b) {
		this.classe1_b = classe1_b;
	}

	public String getClasse1_p() {
		return classe1_p;
	}

	public void setClasse1_p(String classe1_p) {
		this.classe1_p = classe1_p;
	}

	public void setClasse6(String classe6) {
		this.classe6 = classe6;
	}

	public BigInteger getFranchise() {
		return franchise;
	}

	public void setFranchise(BigInteger franchise) {
		this.franchise = franchise;
	}

	public Prodconv getIdprodconv() {
		return idprodconv;
	}

	public void setIdprodconv(Prodconv idprodconv) {
		this.idprodconv = idprodconv;
	}
	
	
	
	
	

	public String getClasse2_v() {
		return classe2_v;
	}

	public void setClasse2_v(String classe2_v) {
		this.classe2_v = classe2_v;
	}

	public String getClasse6_1() {
		return classe6_1;
	}

	public void setClasse6_1(String classe6_1) {
		this.classe6_1 = classe6_1;
	}

	public String getClasse3_1() {
		return classe3_1;
	}

	public void setClasse3_1(String classe3_1) {
		this.classe3_1 = classe3_1;
	}

	public String getClasse5_1() {
		return classe5_1;
	}

	public void setClasse5_1(String classe5_1) {
		this.classe5_1 = classe5_1;
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
		if (!(object instanceof TarifGrele)) {
			return false;
		}
		TarifGrele other = (TarifGrele) object;
		if ((this.idtarif == null && other.idtarif != null) || (this.idtarif != null && !this.idtarif.equals(other.idtarif))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.star.Ba.model.TarifGrele[ idtarif=" + idtarif + " ]";
	}

}
