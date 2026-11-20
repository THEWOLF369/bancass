/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.service;

import java.util.Date;
import java.util.Map;

import com.star.Ba.model.Police;
import com.star.Ba.model.Profil;



/**
 *
 * @author star
 */
public interface IParametresTDService {

    
    public double Tarifier(double capital,double tauxdeprime,int dureeA,int dureeM,double tauxsurprime,double majoration);
    //public java.util.Map<java.lang.String, java.lang.Object> getlistnumplc(String cod_societ);
	void imprimerDevis();
	void imprimerNotice();
	void imprimerAnalyse();
//	Map<String, Object> getlistnumplc(String cod_societ, Profil profil);
	Police getnumplcbylibelle(String cod_societ, String numplc);
	Police getnumplcbylib(String numplc);
	
    

    
}
