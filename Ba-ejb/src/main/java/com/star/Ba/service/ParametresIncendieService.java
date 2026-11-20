/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.ITarifIncendieDAO;
import com.star.Ba.model.Police;
import com.star.Ba.model.Profil;
import com.star.Ba.model.TarifIncendie;

/**
 *
 * @author star
 */
@Stateless
public class ParametresIncendieService implements IParametresIncendieService {

	
	@EJB
    private ITarifIncendieDAO tarifIncendieDAO ;
	@EJB
    private IPoliceDAO policeDAO;
    @EJB
    private IConventionDAO conventionDAO;
	@EJB
	private ISocieteDAO societeDAO;
	@EJB
	private IProduitDAO produitDAO;
	
    @Override
    public double calculPrime(double capital,String numpol,double duree) {
    	TarifIncendie tarifIncendie =tarifIncendieDAO.tarif_by_police(numpol);
    	double prime=capital*tarifIncendie.getTaux()*duree;
    	if(prime<tarifIncendie.getPrimeMin())
    	{
    		return tarifIncendie.getPrimeMin();
    	}
    	else{
    	return prime;
    	}
    }
    
//    @Override
//    public Map<String, Object> getlistnumplc(String cod_societ,Profil profil) {
//        Map<String, Object> listnumplc = new LinkedHashMap<String, Object>();
//        List<Police> list =null;
//        if(profil.getCode().equals("IST"))
//        {
//        	list= policeDAO.getPolice_by_prod(produitDAO.getProduit_by_code("110"));
//        }
//        else{
//            list = policeDAO.getPolice_by_conv_prod(conventionDAO.find_conv_by_Societ(societeDAO.find(cod_societ)),produitDAO.getProduit_by_code("110"));
//        }
//        for (Police police : list) {
//        	listnumplc.put(police.getLibelle().trim(), police.getLibelle());
//        }
//
//        return listnumplc;
//    }
    
    
    @Override
    public Police getnumplcbylibelle(String cod_societ,String numplc)
	{
		List<Police> list=null;	
		
			list=policeDAO.getPolice_by_conv_libelle(conventionDAO.find_conv_by_Societ(societeDAO.find(cod_societ)), numplc);
        
		return list.get(0);
		
	}
}
