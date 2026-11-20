package com.star.Ba.Agricole;

import java.math.BigDecimal;

public class Parcelle {
	private int index;
	private String variete, numTitreFoncier, situation, localite;
	private BigDecimal iddelegation, superficie, prixUnitaire;
	private Integer nbrPied;
	private boolean extensionGrele, majorationVoisinageIncendie, extensionIncendie;

	public Parcelle() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getLocalite() {
		return localite;
	}
	
	public void setLocalite(String localite) {
		this.localite = localite;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getVariete() {
		return variete;
	}

	public void setVariete(String variete) {
		this.variete = variete;
	}

	public String getNumTitreFoncier() {
		return numTitreFoncier;
	}

	public void setNumTitreFoncier(String numTitreFoncier) {
		this.numTitreFoncier = numTitreFoncier;
	}

	public BigDecimal getIddelegation() {
		return iddelegation;
	}

	public void setIddelegation(BigDecimal iddelegation) {
		this.iddelegation = iddelegation;
	}

	public BigDecimal getSuperficie() {
		return superficie;
	}

	public void setSuperficie(BigDecimal superficie) {
		this.superficie = superficie;
	}

	public BigDecimal getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(BigDecimal prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public Integer getNbrPied() {
		return nbrPied;
	}

	public void setNbrPied(Integer nbrPied) {
		this.nbrPied = nbrPied;
	}

	public boolean isExtensionGrele() {
		return extensionGrele;
	}

	public void setExtensionGrele(boolean extensionGrele) {
		this.extensionGrele = extensionGrele;
	}

	public boolean isMajorationVoisinageIncendie() {
		return majorationVoisinageIncendie;
	}

	public void setMajorationVoisinageIncendie(boolean majorationVoisinageIncendie) {
		this.majorationVoisinageIncendie = majorationVoisinageIncendie;
	}

	public boolean isExtensionIncendie() {
		return extensionIncendie;
	}

	public void setExtensionIncendie(boolean extensionIncendie) {
		this.extensionIncendie = extensionIncendie;
	}

}
