package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;



/**
 * The persistent class for the TARIFTD database table.
 * 
 */
@Entity
@NamedQuery(name="Tariftd.findAll", query="SELECT t FROM Tariftd t")
public class Tariftd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TARIFTD")
	private long idTariftd;

	private int agemax;

	private int agemin;

	private String code;

	private int durmax;

	private int durmin;
	
	@Column(nullable = true)
	private double capmin;

	@Column(nullable = true)
	private double capmax;

	@Column(name="N_POLICE")
	private String nPolice;

	private double taux;

	public Tariftd() {
	}

	public long getIdTariftd() {
		return this.idTariftd;
	}

	public void setIdTariftd(long idTariftd) {
		this.idTariftd = idTariftd;
	}

	public int getAgemax() {
		return this.agemax;
	}

	public void setAgemax(int agemax) {
		this.agemax = agemax;
	}

	public int getAgemin() {
		return this.agemin;
	}

	public void setAgemin(int agemin) {
		this.agemin = agemin;
	}
	
	public double getCapmin() {
		return capmin;
	}

	public void setCapmin(double capmin) {
		this.capmin = capmin;
	}

	public double getCapmax() {
		return capmax;
	}

	public void setCapmax(double capmax) {
		this.capmax = capmax;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getDurmax() {
		return this.durmax;
	}

	public void setDurmax(int durmax) {
		this.durmax = durmax;
	}

	public int getDurmin() {
		return this.durmin;
	}

	public void setDurmin(int durmin) {
		this.durmin = durmin;
	}

	public String getNPolice() {
		return this.nPolice;
	}

	public void setNPolice(String nPolice) {
		this.nPolice = nPolice;
	}

	public double getTaux() {
		return this.taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}

}