package com.star.Ba.dto;

import java.math.BigDecimal;

public class TarifAgrDTO {
	private String prime, franchise , taux;
	private BigDecimal extension1, extension2;

	public TarifAgrDTO() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getExtension1() {
		return extension1;
	}

	public void setExtension1(BigDecimal extension1) {
		this.extension1 = extension1;
	}

	public BigDecimal getExtension2() {
		return extension2;
	}

	public void setExtension2(BigDecimal extension2) {
		this.extension2 = extension2;
	}

	public String getPrime() {
		return prime;
	}

	public void setPrime(String prime) {
		this.prime = prime;
	}

	public String getFranchise() {
		return franchise;
	}

	public void setFranchise(String franchise) {
		this.franchise = franchise;
	}

	public double getPrimeDouble() {
		if (prime != null) {
			return Double.parseDouble(prime);
		}
		return 0;

	}

	public String getTaux() {
		return taux;
	}

	public void setTaux(String taux) {
		this.taux = taux;
	}
	
	
}
