package com.star.Ba.dto;

import java.math.BigDecimal;

public class DetailsEvolutionGarantie {
	
	private BigDecimal annee; //Année
	private BigDecimal prime_comm;//Prime commerciale
	private BigDecimal prime_capit;//Prime à capitaliser
	private BigDecimal capital_tmg;//Capital constitué TMG
	private BigDecimal capital_taux_reel;//Capital constitué au Taux Réel
	private BigDecimal benefices_realises;//Bénéfices Réalisés
	private BigDecimal participation_benef;//Participation aux Bénéfices
	private BigDecimal epargne_fin_annee;//Epargne Constituée en fin d'année
	private BigDecimal gain_financier;//Gain Financier
	private BigDecimal gain_fisc_annuel;//Gain fiscal Annuel
	private BigDecimal valeur_rachat;//Valeur rachat
	
	private BigDecimal cumul_vers;//Cumul Versements
	
	
	public BigDecimal getAnnee() {
		return annee;
	}
	public void setAnnee(BigDecimal annee) {
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
