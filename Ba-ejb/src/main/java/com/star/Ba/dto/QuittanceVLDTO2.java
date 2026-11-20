package com.star.Ba.dto;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.QuittanceDetails;

public class QuittanceVLDTO2 {
	
	private Adhesion adhesion;
	private Quittance quittance;
	private QuittanceDetails detail;
	
	
	
	
	public QuittanceVLDTO2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public QuittanceVLDTO2(Adhesion adhesion, Quittance quittance, QuittanceDetails detail) {
		super();
		this.adhesion = adhesion;
		this.quittance = quittance;
		this.detail = detail;
	}


	public Quittance getQuittance() {
		return quittance;
	}
	public void setQuittance(Quittance quittance) {
		this.quittance = quittance;
	}
	public QuittanceDetails getDetail() {
		return detail;
	}
	public void setDetail(QuittanceDetails detail) {
		this.detail = detail;
	}


	public Adhesion getAdhesion() {
		return adhesion;
	}


	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}
	
	
	
	

}
