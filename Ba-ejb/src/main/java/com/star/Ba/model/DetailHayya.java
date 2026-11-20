package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the DETAIL_MRH database table.
 * 
 */
@Entity
@Table(name="DETAIL_HAYYA")
@NamedQuery(name="DetailHayya.findAll", query="SELECT d FROM DetailHayya d")
public class DetailHayya implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	@SequenceGenerator(name="DETAIL_HAYYA_IDDETAILHAYYA_GENERATOR", sequenceName="DETAILHAYYA")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DETAIL_HAYYA_IDDETAILHAYYA_GENERATOR")
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_DETAIL_HAYYA")
	@SequenceGenerator(name = "ID_DETAIL_HAYYA", sequenceName = "ID_DETAIL_HAYYA", allocationSize = 1)
	@Column(name="ID_DETAIL_HAYYA")
	private long idDetailHayya;

	private int age; 

	private BigDecimal Contribution;

	
	private BigDecimal pnette;
	

	private BigDecimal accessoires;

	private BigDecimal commission;
	
	
	private BigDecimal fa;
	
	
	@Column(name="PERSONNEL_STAR")
	private String personnelstar;
	
	
	//bi-directional many-to-one association to Adhesion_test
		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="ID_ADHESION")
		private Adhesion adhesion;

		//bi-directional many-to-one association to Proposition_test
		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="ID_PROPOSITION")
		private Proposition proposition;

	

	//bi-directional many-to-one association to Qualite
	@ManyToOne
	@JoinColumn(name="ID_FRACT_HAYYA")
	private FractHayya fractHayya;
	
	
	//bi-directional many-to-one association to Qualite
	@ManyToOne
	@JoinColumn(name="ID_CROISS_HAYYA")
	private CroissHayya croissHayya;
	
	
	//bi-directional many-to-one association to Qualite
	@ManyToOne
	@JoinColumn(name="ID_TRANCHE_HAYYA")
	private TrancheHayya trancheHayya;
	
	
	@Column(name="TOTAL")
	private BigDecimal total;
	
	@Column(name="PNETTE_ANNUELLE")
	private BigDecimal pnetteannuelle;
	
	
	@Column(name="TOTAL_ANNUEL")
	private BigDecimal totalannuel;
	
	
	@Column(name="CLIENT_WS")
	private Boolean clientWs;
	
	
	private BigDecimal Dtranche;
	

	public DetailHayya() {
	}



	public long getIdDetailHayya() {
		return idDetailHayya;
	}



	public void setIdDetailHayya(long idDetailHayya) {
		this.idDetailHayya = idDetailHayya;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public BigDecimal getContribution() {
		return Contribution;
	}



	public void setContribution(BigDecimal contribution) {
		Contribution = contribution;
	}



	public BigDecimal getPnette() {
		return pnette;
	}



	public void setPnette(BigDecimal pnette) {
		this.pnette = pnette;
	}



	public BigDecimal getAccessoires() {
		return accessoires;
	}



	public void setAccessoires(BigDecimal accessoires) {
		this.accessoires = accessoires;
	}



	public BigDecimal getCommission() {
		return commission;
	}



	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}



	public BigDecimal getFa() {
		return fa;
	}



	public void setFa(BigDecimal fa) {
		this.fa = fa;
	}



	public String getPersonnelstar() {
		return personnelstar;
	}



	public void setPersonnelstar(String personnelstar) {
		this.personnelstar = personnelstar;
	}



	public Adhesion getAdhesion() {
		return adhesion;
	}



	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}



	public Proposition getProposition() {
		return proposition;
	}



	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}



	public FractHayya getFractHayya() {
		return fractHayya;
	}



	public void setFractHayya(FractHayya fractHayya) {
		this.fractHayya = fractHayya;
	}



	public CroissHayya getCroissHayya() {
		return croissHayya;
	}



	public void setCroissHayya(CroissHayya croissHayya) {
		this.croissHayya = croissHayya;
	}



	public TrancheHayya getTrancheHayya() {
		return trancheHayya;
	}



	public void setTrancheHayya(TrancheHayya trancheHayya) {
		this.trancheHayya = trancheHayya;
	}



	public BigDecimal getTotal() {
		return total;
	}



	public void setTotal(BigDecimal total) {
		this.total = total;
	}



	public BigDecimal getPnetteannuelle() {
		return pnetteannuelle;
	}



	public void setPnetteannuelle(BigDecimal pnetteannuelle) {
		this.pnetteannuelle = pnetteannuelle;
	}



	public BigDecimal getTotalannuel() {
		return totalannuel;
	}



	public void setTotalannuel(BigDecimal totalannuel) {
		this.totalannuel = totalannuel;
	}

	
	public Boolean getClientWs() {
		return clientWs;
	}

	public void setClientWs(Boolean clientWs) {
		this.clientWs = clientWs;
	}



	public BigDecimal getDtranche() {
		return Dtranche;
	}



	public void setDtranche(BigDecimal dtranche) {
		Dtranche = dtranche;
	}
	

}