package com.star.Ba.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

public class BilanFinancierHayyaDTO {
	
	
   private BigDecimal primeversee;
	
 

	private BigDecimal epargneprev;
	

	private BigDecimal gainfinancier;
			
	

	private BigDecimal econimpot;
	

	private BigDecimal gaintotal;


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
