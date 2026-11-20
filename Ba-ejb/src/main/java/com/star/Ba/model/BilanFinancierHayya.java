package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;


/**
 * The persistent class for the LOCGOUV database table.
 * 
 */
@Entity
@Table(name="BILAN_FINANCIER_HAYYA")
@NamedQueries({
	@NamedQuery(name="BilanFinancierHayya.findAll", query="SELECT l FROM BilanFinancierHayya l order by l.idBilanFinancierHayya asc")
})
public class BilanFinancierHayya implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_BILAN_FINANCIER_HAYYA")
	@SequenceGenerator(name = "ID_BILAN_FINANCIER_HAYYA", sequenceName = "ID_BILAN_FINANCIER_HAYYA", allocationSize = 1)
	@Column(name="ID_BILAN_FINANCIER_HAYYA")
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idBilanFinancierHayya;
	
	//bi-directional many-to-one association to Proposition_test
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_PROPOSITION")
	private Proposition proposition;	
	
	//bi-directional many-to-one association to Adhesion_test
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ADHESION")
	private Adhesion adhesion;


	
	
	
	@Column(name="PRIME_VERSEE")
	private BigDecimal primeversee;
	
 
	
	@Column(name="EPARGNE_PREVISIONNELLE")
	private BigDecimal epargneprev;
	
	@Column(name="GAIN_FINANCIER")
	private BigDecimal gainfinancier;
			
	
	@Column(name="ECONOMIE_DIMPOT")
	private BigDecimal econimpot;
	
	@Column(name="GAIN_TOTAL")
	private BigDecimal gaintotal;
 

	public BilanFinancierHayya() {
	}



	


	public Proposition getProposition() {
		return proposition;
	}



	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}



	public Adhesion getAdhesion() {
		return adhesion;
	}



	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}






	public long getIdBilanFinancierHayya() {
		return idBilanFinancierHayya;
	}






	public void setIdBilanFinancierHayya(long idBilanFinancierHayya) {
		this.idBilanFinancierHayya = idBilanFinancierHayya;
	}






	public BigDecimal getPrimeversee() {
		return primeversee;
	}






	public void setPrimeversee(BigDecimal primeversee) {
		this.primeversee = primeversee;
	}






	public BigDecimal getEpargneprev() {
		return epargneprev;
	}






	public void setEpargneprev(BigDecimal epargneprev) {
		this.epargneprev = epargneprev;
	}






	public BigDecimal getGainfinancier() {
		return gainfinancier;
	}






	public void setGainfinancier(BigDecimal gainfinancier) {
		this.gainfinancier = gainfinancier;
	}






	public BigDecimal getEconimpot() {
		return econimpot;
	}






	public void setEconimpot(BigDecimal econimpot) {
		this.econimpot = econimpot;
	}






	public BigDecimal getGaintotal() {
		return gaintotal;
	}






	public void setGaintotal(BigDecimal gaintotal) {
		this.gaintotal = gaintotal;
	}



	




	
	


}