package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the GARANTIE_MRH database table.
 * 
 */
@Entity
@Table(name="GARANTIE_MRH")
@NamedQuery(name="GarantieMrh.findAll", query="SELECT g FROM GarantieMrh g")
public class GarantieMrh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GARANTIE_MRH_IDGARMRH_GENERATOR", sequenceName="GARANTIEMRH")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GARANTIE_MRH_IDGARMRH_GENERATOR")
	@Column(name="ID_GARMRH")
	private long idGarmrh;

	@Column(name="COD_GAR")
	private String codGar;

	@Column(name="PRM_GAR")
	private BigDecimal prmGar;

	//bi-directional many-to-one association to Adhesion_test
	@ManyToOne
	@JoinColumn(name="ID_ADHESION")
	private Adhesion adhesion;

	//bi-directional many-to-one association to Proposition_test
	@ManyToOne
	@JoinColumn(name="ID_PROPOSITION")
	private Proposition proposition;

	public GarantieMrh() {
	}

	public long getIdGarmrh() {
		return this.idGarmrh;
	}

	public void setIdGarmrh(long idGarmrh) {
		this.idGarmrh = idGarmrh;
	}

	public String getCodGar() {
		return this.codGar;
	}

	public void setCodGar(String codGar) {
		this.codGar = codGar;
	}

	public BigDecimal getPrmGar() {
		return this.prmGar;
	}

	public void setPrmGar(BigDecimal prmGar) {
		this.prmGar = prmGar;
	}

	public Adhesion getAdhesion() {
		return this.adhesion;
	}

	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}

	public Proposition getProposition() {
		return this.proposition;
	}

	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}

 

}