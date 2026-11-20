package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


/**
 * The persistent class for the DETAIL_ACT database table.
 * 
 */
@Entity
@Table(name="DETAIL_ACT")
@NamedQuery(name="DetailAct.findAll", query="SELECT d FROM DetailAct d")
public class DetailAct implements Serializable {
	private static final long serialVersionUID = 1L;

	
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_DETACT")
	 @SequenceGenerator(name = "ID_DETACT", sequenceName = "ID_DETACT", allocationSize = 1)
	 
	 @Column(name = "ID_DETACT")
	 private int idact;

	@Column(name="LIBELLE_ACT")
	private String libelleAct;

	@Column(name="NUM_ACT")
	private int numAct;

	public DetailAct() {
	}

	public int getIdact() {
		return this.idact;
	}

	public void setIdact(int idact) {
		this.idact = idact;
	}

	public String getLibelleAct() {
		return this.libelleAct;
	}

	public void setLibelleAct(String libelleAct) {
		this.libelleAct = libelleAct;
	}

	public int getNumAct() {
		return this.numAct;
	}

	public void setNumAct(int numAct) {
		this.numAct = numAct;
	}

}