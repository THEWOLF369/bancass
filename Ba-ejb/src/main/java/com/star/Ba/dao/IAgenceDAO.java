/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Agence;
import com.star.Ba.model.Societe;

import java.util.List;

import javax.ejb.Local;

/**
 *
 * @author 
 */
@Local
public interface IAgenceDAO {
    void create(Agence agence);

    void edit(Agence agence);

    void remove(Agence agence);

    Agence find(Object id);
    List<Agence> getagencebna();
    List<Agence> getagencestb();


    List<Agence> findAll();
    
    int count();

	List<Agence> listeAgenceParSociete(Societe societe);
	 public Agence createNewAgence(Agence agence);
	 List<Agence> VerifAgenceSTB(String codeAgence);
	 
	 
	 public Agence FindAgencePrincipaleByCodeSociete(String code_societe);
	 
	 // pour 7ayya
	 public Agence FindAgenceBycode(String code);

}
