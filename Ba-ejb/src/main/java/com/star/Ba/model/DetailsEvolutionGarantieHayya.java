package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="DETAILS_EV_GARANTIE_HAYYA")
@NamedQueries({
	@NamedQuery(name="DetailsEvolutionGarantieHayya.findAll", query="SELECT l FROM DetailsEvolutionGarantieHayya l order by l.idDetailsEvolutionGarantieHayya asc")
})
public class DetailsEvolutionGarantieHayya implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_DETAILS_EV_GARANTIE_HAYYA")
	@SequenceGenerator(name = "ID_DETAILS_EV_GARANTIE_HAYYA", sequenceName = "ID_DETAILS_EV_GARANTIE_HAYYA", allocationSize = 1)
	@Column(name="ID_DETAILS_EV_GARANTIE_HAYYA")
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idDetailsEvolutionGarantieHayya;
	
	//bi-directional many-to-one association to Proposition_test
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_PROPOSITION")
	private Proposition proposition;	
	
	//bi-directional many-to-one association to Adhesion_test
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ADHESION")
	private Adhesion adhesion;
	
	
	@Column(name="ANNEE")
	private int annee; 
	
	
	@Column(name="PRIME_COMMERCIALE")
	private BigDecimal prime_comm;
	
	@Column(name="PRIME_A_CAPITALISER")
	private BigDecimal prime_capit;
	
	@Column(name="CAPITAL_CONSTITUE_TMG")
	private BigDecimal capital_tmg;
	
	@Column(name="CAPITAL_CONSTITUE_AU_TAUX_REEL")
	private BigDecimal capital_taux_reel;
	
	@Column(name="BENEFICES_REALISES")
	private BigDecimal benefices_realises;
	
	@Column(name="PARTICIPATION_AUX_BENEFICES")
	private BigDecimal participation_benef;
	
	@Column(name="EPARGNE_CONSTITUEE")
	private BigDecimal epargne_fin_annee;
	
	@Column(name="GAIN_FINANCIER")
	private BigDecimal gain_financier;
	
	@Column(name="GAIN_FISCAL")
	private BigDecimal gain_fisc_annuel;
	
	@Column(name="VALEUR_RACHAT")
	private BigDecimal valeur_rachat;
	
	@Column(name="CUMUL_VERSEMENTS")
	private BigDecimal cumul_vers;

	public long getIdDetailsEvolutionGarantieHayya() {
		return idDetailsEvolutionGarantieHayya;
	}

	public void setIdDetailsEvolutionGarantieHayya(long idDetailsEvolutionGarantieHayya) {
		this.idDetailsEvolutionGarantieHayya = idDetailsEvolutionGarantieHayya;
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

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public BigDecimal getPrime_comm() {
		return prime_comm;
	}

	public void setPrime_comm(BigDecimal prime_comm) {
		this.prime_comm = prime_comm;
	}

	public BigDecimal getPrime_capit() {
		return prime_capit;
	}

	public void setPrime_capit(BigDecimal prime_capit) {
		this.prime_capit = prime_capit;
	}

	public BigDecimal getCapital_tmg() {
		return capital_tmg;
	}

	public void setCapital_tmg(BigDecimal capital_tmg) {
		this.capital_tmg = capital_tmg;
	}

	public BigDecimal getCapital_taux_reel() {
		return capital_taux_reel;
	}

	public void setCapital_taux_reel(BigDecimal capital_taux_reel) {
		this.capital_taux_reel = capital_taux_reel;
	}

	public BigDecimal getBenefices_realises() {
		return benefices_realises;
	}

	public void setBenefices_realises(BigDecimal benefices_realises) {
		this.benefices_realises = benefices_realises;
	}

	public BigDecimal getParticipation_benef() {
		return participation_benef;
	}

	public void setParticipation_benef(BigDecimal participation_benef) {
		this.participation_benef = participation_benef;
	}

	public BigDecimal getEpargne_fin_annee() {
		return epargne_fin_annee;
	}

	public void setEpargne_fin_annee(BigDecimal epargne_fin_annee) {
		this.epargne_fin_annee = epargne_fin_annee;
	}

	public BigDecimal getGain_financier() {
		return gain_financier;
	}

	public void setGain_financier(BigDecimal gain_financier) {
		this.gain_financier = gain_financier;
	}

	public BigDecimal getGain_fisc_annuel() {
		return gain_fisc_annuel;
	}

	public void setGain_fisc_annuel(BigDecimal gain_fisc_annuel) {
		this.gain_fisc_annuel = gain_fisc_annuel;
	}

	public BigDecimal getValeur_rachat() {
		return valeur_rachat;
	}

	public void setValeur_rachat(BigDecimal valeur_rachat) {
		this.valeur_rachat = valeur_rachat;
	}

	public BigDecimal getCumul_vers() {
		return cumul_vers;
	}

	public void setCumul_vers(BigDecimal cumul_vers) {
		this.cumul_vers = cumul_vers;
	}


	
	
	
	
}
