package com.star.Ba.model;

import java.io.Serializable;

public class SituationBlocageUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SituationBlocageUser(int situationNomber, String situationName) {
		super();
		this.situationNomber = situationNomber;
		this.situationName = situationName;
	}
	public SituationBlocageUser(){
		
	};
	private int situationNomber;
	private  String situationName;
	public int getSituationNomber() {
		return situationNomber;
	}
	public void setSituationNomber(int situationNomber) {
		this.situationNomber = situationNomber;
	}
	public String getSituationName() {
		return situationName;
	}
	public void setSituationName(String situationName) {
		this.situationName = situationName;
	}
}
