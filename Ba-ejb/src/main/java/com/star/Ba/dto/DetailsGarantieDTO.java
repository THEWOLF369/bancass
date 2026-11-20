package com.star.Ba.dto;

import java.math.BigDecimal;

public class DetailsGarantieDTO {
	
	private String code;
	private BigDecimal capital;
	private String libelle;
	private BigDecimal prime;
	private BigDecimal taux;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getCapital() {
		return capital;
	}
	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public BigDecimal getPrime() {
		return prime;
	}
	public void setPrime(BigDecimal prime) {
		this.prime = prime;
	}
	public BigDecimal getTaux() {
		return taux;
	}
	public void setTaux(BigDecimal taux) {
		this.taux = taux;
	}
	 
	
	
	
	
	

}
