package com.star.Ba.jsf.util;

public enum ENUMCODEPRODUIT {
	
	ASSISTANCEVOIYAGE("305"),AVENIRJENESSE("692"),TD("601"),INCENDIE("110"),MRH("805"),TLSAssistV("302")
	,Grele("411"),IncendieAgricole("182"),IncendieAlim("184"),H7AYYA("685"), H7ayyaQNB("686");
	
	
	
	String code;
	ENUMCODEPRODUIT(String code){
		this.code=code;
		
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
