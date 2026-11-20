package com.star.Ba.service;

import java.util.Map;

import com.star.Ba.model.Police;
import com.star.Ba.model.Profil;

public interface IParametresIncendieService {


//	Map<String, Object> getlistnumplc(String cod_societ, Profil profil);

	Police getnumplcbylibelle(String cod_societ, String numplc);

	double calculPrime(double capital, String numpol, double duree);

}
