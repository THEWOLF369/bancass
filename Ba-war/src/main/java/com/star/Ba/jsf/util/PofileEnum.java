package com.star.Ba.jsf.util;

import java.util.ArrayList;
import java.util.List;

public enum PofileEnum {
	G(),AA(),AS(),AST(),ASI(),IST(),VST(),TDST(),MHST(),VTST(),BNAA(),BNA(), DBNA(),CBNA(), HST(), HQST(), MSTA();
	
	
	List<String> childrensAA=new ArrayList<String>();
	List<String> childrensAS=new ArrayList<String>();
	List<String> childrensAST=new ArrayList<String>();
	List<String> childrensASI=new ArrayList<String>();
	List<String> childrensASM=new ArrayList<String>();
	List<String> childrensMHST=new ArrayList<String>();
	List<String> childrensBNAA=new ArrayList<String>();
	
	List<String> childrensHST=new ArrayList<String>();
	
	List<String> childrensHQST=new ArrayList<String>();
	
	List<String> childrensMSTA=new ArrayList<String>();
	
	public List<String> getChildrensAA() {
		childrensAA.clear();
		childrensAA.add("G");
		return childrensAA;
	}
	
	public List<String> getChildrensAS() {
		childrensAS.clear();
		childrensAS.add("AA");
		return childrensAS;
	}
	
	public List<String> getChildrensAS_V2() {
		childrensAS.clear();
		childrensAS.add("AA");
		childrensAS.add("G");
		return childrensAS;
	}
	
	public List<String> getChildrensASI() {
		childrensASI.clear();
		childrensASI.add("AST");
		childrensASI.add("IST");
		childrensASI.add("VST");
		childrensASI.add("TDST");
		childrensASI.add("MHST");
		childrensASI.add("VTST");
		childrensASI.add("HST");
		childrensASI.add("HQST");
		childrensASI.add("MSTA");
		return childrensASI;
	}
	
	public List<String> getChildrenMetie(){
	childrensASM.clear();
	childrensASM.add("AS");
    return childrensASM;
}
	
	
	

		
    public List<String> getChildrensBNAA() {
    	
    	childrensBNAA.clear();
    	childrensBNAA.add("BNAA");
		return childrensBNAA;
	}



	public int getProduit(String profil) {
    	if(PofileEnum.MHST.name().equals(profil)){
	    return 5;}
    	else if(PofileEnum.AST.name().equals(profil)){
    		return 4;   	}
    	else if(PofileEnum.VST.name().equals(profil)){
    		return 3;}
    		else if(PofileEnum.IST.name().equals(profil)){
        		return 2;}
    		else if(PofileEnum.VTST.name().equals(profil)){
        		return 6;}
    		else if(PofileEnum.HST.name().equals(profil)){
        		return 10;}
    		else if(PofileEnum.HQST.name().equals(profil)){
        		return 11;}
    		else {
        		return 1;}
        		
    }	
	
    
}
