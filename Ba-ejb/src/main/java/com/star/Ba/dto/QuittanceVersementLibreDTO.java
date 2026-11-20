package com.star.Ba.dto;

import java.math.BigDecimal;

public class QuittanceVersementLibreDTO {

	private String numero_quittance;
    private String code_agence;
    private String numero_contrat;
    private String date_du;
    private String date_au;
    private String date_echeance;
    private BigDecimal prime_nette;
    private BigDecimal frais;
    private BigDecimal taxes;
    private BigDecimal fga;
    private BigDecimal prime_totale;
    private String nom_assure;
    private String prenom_assure;
    private String adresse_assure;
    
    private String encaissed;
    private String imprime;
    
    
	public String getNumero_quittance() {
		return numero_quittance;
	}
	public void setNumero_quittance(String numero_quittance) {
		this.numero_quittance = numero_quittance;
	}
	public String getCode_agence() {
		return code_agence;
	}
	public void setCode_agence(String code_agence) {
		this.code_agence = code_agence;
	}
	public String getNumero_contrat() {
		return numero_contrat;
	}
	public void setNumero_contrat(String numero_contrat) {
		this.numero_contrat = numero_contrat;
	}
	public String getDate_du() {
		return date_du;
	}
	public void setDate_du(String date_du) {
		this.date_du = date_du;
	}
	public String getDate_au() {
		return date_au;
	}
	public void setDate_au(String date_au) {
		this.date_au = date_au;
	}
	public String getDate_echeance() {
		return date_echeance;
	}
	public void setDate_echeance(String date_echeance) {
		this.date_echeance = date_echeance;
	}
	public BigDecimal getPrime_nette() {
		return prime_nette;
	}
	public void setPrime_nette(BigDecimal prime_nette) {
		this.prime_nette = prime_nette;
	}
	public BigDecimal getFrais() {
		return frais;
	}
	public void setFrais(BigDecimal frais) {
		this.frais = frais;
	}
	public BigDecimal getTaxes() {
		return taxes;
	}
	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}
	public BigDecimal getFga() {
		return fga;
	}
	public void setFga(BigDecimal fga) {
		this.fga = fga;
	}
	public BigDecimal getPrime_totale() {
		return prime_totale;
	}
	public void setPrime_totale(BigDecimal prime_totale) {
		this.prime_totale = prime_totale;
	}
	public String getNom_assure() {
		return nom_assure;
	}
	public void setNom_assure(String nom_assure) {
		this.nom_assure = nom_assure;
	}
	public String getPrenom_assure() {
		return prenom_assure;
	}
	public void setPrenom_assure(String prenom_assure) {
		this.prenom_assure = prenom_assure;
	}
	public String getAdresse_assure() {
		return adresse_assure;
	}
	public void setAdresse_assure(String adresse_assure) {
		this.adresse_assure = adresse_assure;
	}
	public String getEncaissed() {
		return encaissed;
	}
	public void setEncaissed(String encaissed) {
		this.encaissed = encaissed;
	}
	public String getImprime() {
		return imprime;
	}
	public void setImprime(String imprime) {
		this.imprime = imprime;
	}		
    
    
    
    
}
