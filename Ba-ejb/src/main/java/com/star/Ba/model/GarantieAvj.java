package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the GARANTIE_AVJ database table.
 * 
 */
@Entity
@Table(name="GARANTIE_AVJ")
@NamedQuery(name="GarantieAvj.findAll", query="SELECT g FROM GarantieAvj g")

public class GarantieAvj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ASS_GAR")
	@SequenceGenerator(name = "ID_ASS_GAR", sequenceName = "ID_ASS_GAR", allocationSize = 1)
	@Column(name="ID_ASS_GAR")
	private long idAssGar;

	private BigDecimal capital;


	@ManyToOne
	@JoinColumn(name = "ID_ADHESION")
	private Adhesion adhesion;
	
	
	@ManyToOne
	@JoinColumn(name = "ID_PROPOSITON")
	private Proposition proposition;

	
	@ManyToOne
	@JoinColumn(name = "ID_GARANTIE")
	private ParamGarantieAvj Garantie;

	private BigDecimal montant;

	@Column(name="MT_PRIME")
	private BigDecimal mtPrime;

	@Column(name="PRM_AN_PUR")
	private BigDecimal prmAnPur;

	@Column(name="PRM_PER_PUR")
	private BigDecimal prmPerPur;

	@Column(name="PRM_PER_PUR_COMM")
	private BigDecimal prmPerPurComm;

	@Column(name="PRM_UNIQ_PUR")
	private BigDecimal prmUniqPur;

	public GarantieAvj() {
	}


	public GarantieAvj( BigDecimal capital, 
			
			BigDecimal montant, BigDecimal mtPrime, BigDecimal prmAnPur,
			BigDecimal prmPerPur, BigDecimal prmPerPurComm,
			BigDecimal prmUniqPur) {
		super();
		
		this.capital = capital;
		this.montant = montant;
		this.mtPrime = mtPrime;
		this.prmUniqPur = prmUniqPur;
		this.prmAnPur = prmAnPur;
		this.prmPerPur = prmPerPur;
		this.prmPerPurComm = prmPerPurComm;
		
	}

	public long getIdAssGar() {
		return this.idAssGar;
	}

	public void setIdAssGar(long idAssGar) {
		this.idAssGar = idAssGar;
	}

	public BigDecimal getCapital() {
		return this.capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	

	public Adhesion getAdhesion() {
		return adhesion;
	}

	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}

	public ParamGarantieAvj getGarantie() {
		return Garantie;
	}

	public void setGarantie(ParamGarantieAvj garantie) {
		Garantie = garantie;
	}

	public BigDecimal getMontant() {
		return this.montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public BigDecimal getMtPrime() {
		return this.mtPrime;
	}

	public void setMtPrime(BigDecimal mtPrime) {
		this.mtPrime = mtPrime;
	}

	public BigDecimal getPrmAnPur() {
		return this.prmAnPur;
	}

	public void setPrmAnPur(BigDecimal prmAnPur) {
		this.prmAnPur = prmAnPur;
	}

	public BigDecimal getPrmPerPur() {
		return this.prmPerPur;
	}

	public void setPrmPerPur(BigDecimal prmPerPur) {
		this.prmPerPur = prmPerPur;
	}

	public BigDecimal getPrmPerPurComm() {
		return this.prmPerPurComm;
	}

	public void setPrmPerPurComm(BigDecimal prmPerPurComm) {
		this.prmPerPurComm = prmPerPurComm;
	}

	public BigDecimal getPrmUniqPur() {
		return this.prmUniqPur;
	}

	public void setPrmUniqPur(BigDecimal prmUniqPur) {
		this.prmUniqPur = prmUniqPur;
	}

	public Proposition getProposition() {
		return proposition;
	}

	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}
	

}