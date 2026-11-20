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
import com.star.Ba.model.Police;
import com.star.Ba.model.Profil;
//import com.star.Ba.jsf.util.ENUMCODEPRODUIT;

/**
 *
 * @author star
 */
@Stateless
public class ParametresTDService implements IParametresTDService {

	
	@EJB
    private IPoliceDAO policeDAO;
    @EJB
    private IConventionDAO conventionDAO;
	@EJB
	private ISocieteDAO societeDAO;
	
	@EJB
	private IProduitDAO produitDAO;
	
//    @Override
//    public Map<String, Object> getlistnumplc(String cod_societ,Profil profil) {
//        Map<String, Object> listnumplc = new LinkedHashMap<String, Object>();
//        List<Police> list =null;
//        
//        if(profil.getCode().equals("TDST"))
//        {
//        	list= policeDAO.getPolice_by_prod(produitDAO.getProduit_by_code("601"));
//        }
//        else{
//            list = policeDAO.getPolice_by_conv_prod(conventionDAO.find_conv_by_Societ(societeDAO.find(cod_societ)),produitDAO.getProduit_by_code("601"));
//        }
//       
//        for (Police police : list) {
//        	listnumplc.put(police.getLibelle().trim(), police.getLibelle());
//        }
//
//        return listnumplc;
//    }
    @Override
    public double Tarifier(double capital,double tauxdeprime,int dureeA,int dureeM,double tauxsurprime,double majoration)
    {
    	
    	double primeglobal = (tauxdeprime/1000)*capital;//*(dureeA+(dureeM/100));  effacé suite recette de mariem
		primeglobal=primeglobal+(primeglobal*tauxsurprime/100)+majoration;
		if(primeglobal<15){
			primeglobal=15;
		}
    	return primeglobal;
    }
    
    @Override
    public Police getnumplcbylibelle(String cod_societ,String numplc)
	{
		List<Police> list=null;	
		
			list=policeDAO.getPolice_by_conv_libelle(conventionDAO.find_conv_by_Societ(societeDAO.find(cod_societ)), numplc);
        
		return list.get(0);
		
	}
    
    
    @Override
    public Police getnumplcbylib(String numplc)
	{
		List<Police> list=null;	
		
			list=policeDAO.getPolice_by_lib(numplc);
        
		return list.get(0);
		
	}
    
    
    @Override
    public void imprimerDevis()
    {
    	//fonction d'impression du devis
    }
    @Override
    public void imprimerNotice()
    {
    	//fonction d'impression de la notice d'assurance
    }
    @Override
    public void imprimerAnalyse()
    {
    	//fonction d'impression des analyses
    }
}
