package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the PARAM_ACT database table.
 * 
 */
@Entity
@Table(name="PARAM_ACT")
@NamedQuery(name="ParamAct.findAll", query="SELECT p FROM ParamAct p")
public class ParamAct implements Serializable {
	private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ACT")
	 @SequenceGenerator(name = "ID_ACT", sequenceName = "ID_ACT", allocationSize = 1)
	 @Column(name = "ID_ACT")
	 private int idAct;
	 
	@Column(name="AGE_MAX")
	private int ageMax;

	@Column(name="AGE_MIN")
	private int ageMin;

	@Column(name="CAPITAL_MAX")
	private double capitalMax;

	@Column(name="CAPITAL_MIN")
	private double capitalMin;

	@Column(name="NUM_ACT")
	private int numAct;

	public ParamAct() {
	}

	public int getAgeMax() {
		return this.ageMax;
	}

	public void setAgeMax(int ageMax) {
		this.ageMax = ageMax;
	}

	public int getAgeMin() {
		return this.ageMin;
	}

	public void setAgeMin(int ageMin) {
		this.ageMin = ageMin;
	}

	public double getCapitalMax() {
		return this.capitalMax;
	}

	public void setCapitalMax(double capitalMax) {
		this.capitalMax = capitalMax;
	}

	public double getCapitalMin() {
		return this.capitalMin;
	}

	public void setCapitalMin(double capitalMin) {
		this.capitalMin = capitalMin;
	}

	public int getIdAct() {
		return this.idAct;
	}

	public void setIdAct(int idAct) {
		this.idAct = idAct;
	}

	public int getNumAct() {
		return this.numAct;
	}

	public void setNumAct(int numAct) {
		this.numAct = numAct;
	}

}