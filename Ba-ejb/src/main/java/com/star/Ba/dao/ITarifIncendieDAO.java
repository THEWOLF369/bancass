/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.TarifIncendie;

import java.util.List;

import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface ITarifIncendieDAO {

    void create(TarifIncendie tarifIncendie);

    void edit(TarifIncendie tarifIncendie);

    void remove(TarifIncendie tarifIncendie);

    TarifIncendie find(Object id);

    List<TarifIncendie> findAll();

    List<TarifIncendie> findRange(int[] range);

    int count();
    
	TarifIncendie tarif_by_police(String numpol);


}
