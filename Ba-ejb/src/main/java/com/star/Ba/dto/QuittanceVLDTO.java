package com.star.Ba.dto;

import com.star.Ba.model.Quittance;
import com.star.Ba.model.QuittanceDetails;

public class QuittanceVLDTO {
	
	private Quittance quittance;
	private QuittanceDetails detail;
	
	
	
	
	public QuittanceVLDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public QuittanceVLDTO(Quittance quittance, QuittanceDetails detail) {
		super();
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
	
	
	
	

}
