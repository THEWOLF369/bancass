/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Agence;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Utilisateurs;

import java.util.List;

import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface IProfilDAO {

    

    Profil find(Object id);

	Profil find_profil_by_code(String code);
	List<Profil> getprofilbna();
	
	List<Profil> getprofilbna_all();

    

   
}
