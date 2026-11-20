package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PARAM_GARANTIE_AVJ database table.
 * 
 */
@Entity
@Table(name="PARAM_GARANTIE_AVJ")
@NamedQuery(name="ParamGarantieAvj.findAll", query="SELECT p FROM ParamGarantieAvj p")
public class ParamGarantieAvj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PARAM_GARANTIE_AVJ_IDGARANTIE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PARAM_GARANTIE_AVJ_IDGARANTIE_GENERATOR")
	@Column(name="ID_GARANTIE")
	private int idGarantie;

	private String garantie;

	
	public ParamGarantieAvj() {
	}

	public int getIdGarantie() {
		return this.idGarantie;
	}

	public void setIdGarantie(int idGarantie) {
		this.idGarantie = idGarantie;
	}

	public String getGarantie() {
		return this.garantie;
	}

	public void setGarantie(String garantie) {
		this.garantie = garantie;
	}



}