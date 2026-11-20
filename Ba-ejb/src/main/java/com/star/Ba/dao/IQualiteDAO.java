/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Qualite;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface IQualiteDAO {
   
    void create(Qualite qualite);

    void edit(Qualite qualite);

    void remove(Qualite qualite);

    Qualite find(Object id);

    List<Qualite> findAll();

    List<Qualite> findRange(int[] range);

    int count();

	List<Qualite> listeQualite();
	public Qualite  createNewQualite(Qualite qualite);
	

   
}
