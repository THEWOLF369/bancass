/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Societe;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface ISocieteDAO {
   
    void create(Societe societe);

    void edit(Societe societe);

    void remove(Societe societe);

    Societe find(Object id);

    List<Societe> findAll();

    List<Societe> findRange(int[] range);

    int count();

	List<Societe> listeSociete();
	
	
	public Societe  createNewSociete(Societe societe);
	

	List<Societe> listeSocietebna();
	

   
}
