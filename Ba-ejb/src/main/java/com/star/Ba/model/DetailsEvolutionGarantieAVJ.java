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
@Table(name="DETAILS_EV_GARANTIE_AVJ")
@NamedQueries({
	@NamedQuery(name="DetailsEvolutionGarantieAVJ.findAll", query="SELECT l FROM DetailsEvolutionGarantieAVJ l order by l.idDetailsEvolutionGarantieAVJ asc")
})
public class DetailsEvolutionGarantieAVJ implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_DETAILS_EV_GARANTIE_AVJ")
	@SequenceGenerator(name = "ID_DETAILS_EV_GARANTIE_AVJ", sequenceName = "ID_DETAILS_EV_GARANTIE_AVJ", allocationSize = 1)
	@Column(name="ID_DETAILS_EV_GARANTIE_AVJ")
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idDetailsEvolutionGarantieAVJ;
	
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
	
	
	@Column(name="CUMUL_VERSEMENTS")
	private BigDecimal cumul_vers;
	
	@Column(name="PRIME_EPARGNE_BOURSE")
	private BigDecimal prime_epar_bourse;
	
	@Column(name="PRIME_EPARGNE_FRAIS")
	private BigDecimal prime_epar_frais;
	
	@Column(name="EPARGNE_CONS_BOURSE_4")
	private BigDecimal epar_cons_bourse;
	
	@Column(name="PARTICIP_BENEF_BOURSE")
	private BigDecimal particip_benef_bourse;
	
	@Column(name="EPARGNE_CONS_FANNEE_BOURSE")
	private BigDecimal epar_cons_fannee_bourse;
	
	@Column(name="EPARGNE_CONS_FRAIS_4")
	private BigDecimal epar_cons_frais;
	
	@Column(name="PARTICIP_BENEF_FRAIS")
	private BigDecimal particip_benef_frais;
	
	@Column(name="EPARGNE_CONS_FANNEE_FRAIS")
	private BigDecimal epar_cons_fannee_frais;
	
	@Column(name="AVANTAGE_FINANCIER")
	private BigDecimal avantage_financier;
	
	@Column(name="AVANTAGE_FISCAL_ANNUEL")
	private BigDecimal avantage_fisc_annuel;
	


	

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

	public long getIdDetailsEvolutionGarantieAVJ() {
		return idDetailsEvolutionGarantieAVJ;
	}

	public void setIdDetailsEvolutionGarantieAVJ(long idDetailsEvolutionGarantieAVJ) {
		this.idDetailsEvolutionGarantieAVJ = idDetailsEvolutionGarantieAVJ;
	}

	public BigDecimal getCumul_vers() {
		return cumul_vers;
	}

	public void setCumul_vers(BigDecimal cumul_vers) {
		this.cumul_vers = cumul_vers;
	}

	

	public BigDecimal getPrime_epar_bourse() {
		return prime_epar_bourse;
	}

	public void setPrime_epar_bourse(BigDecimal prime_epar_bourse) {
		this.prime_epar_bourse = prime_epar_bourse;
	}

	public BigDecimal getPrime_epar_frais() {
		return prime_epar_frais;
	}

	public void setPrime_epar_frais(BigDecimal prime_epar_frais) {
		this.prime_epar_frais = prime_epar_frais;
	}

	public BigDecimal getEpar_cons_bourse() {
		return epar_cons_bourse;
	}

	public void setEpar_cons_bourse(BigDecimal epar_cons_bourse) {
		this.epar_cons_bourse = epar_cons_bourse;
	}

	public BigDecimal getParticip_benef_bourse() {
		return particip_benef_bourse;
	}

	public void setParticip_benef_bourse(BigDecimal particip_benef_bourse) {
		this.particip_benef_bourse = particip_benef_bourse;
	}

	public BigDecimal getEpar_cons_fannee_bourse() {
		return epar_cons_fannee_bourse;
	}

	public void setEpar_cons_fannee_bourse(BigDecimal epar_cons_fannee_bourse) {
		this.epar_cons_fannee_bourse = epar_cons_fannee_bourse;
	}

	public BigDecimal getEpar_cons_frais() {
		return epar_cons_frais;
	}

	public void setEpar_cons_frais(BigDecimal epar_cons_frais) {
		this.epar_cons_frais = epar_cons_frais;
	}

	public BigDecimal getParticip_benef_frais() {
		return particip_benef_frais;
	}

	public void setParticip_benef_frais(BigDecimal particip_benef_frais) {
		this.particip_benef_frais = particip_benef_frais;
	}

	public BigDecimal getEpar_cons_fannee_frais() {
		return epar_cons_fannee_frais;
	}

	public void setEpar_cons_fannee_frais(BigDecimal epar_cons_fannee_frais) {
		this.epar_cons_fannee_frais = epar_cons_fannee_frais;
	}

	public BigDecimal getAvantage_financier() {
		return avantage_financier;
	}

	public void setAvantage_financier(BigDecimal avantage_financier) {
		this.avantage_financier = avantage_financier;
	}

	public BigDecimal getAvantage_fisc_annuel() {
		return avantage_fisc_annuel;
	}

	public void setAvantage_fisc_annuel(BigDecimal avantage_fisc_annuel) {
		this.avantage_fisc_annuel = avantage_fisc_annuel;
	}

	
	
	
}
