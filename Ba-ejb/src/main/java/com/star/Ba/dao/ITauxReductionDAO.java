/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Tauxreduction;
import com.star.Ba.model.Tauxreduction;

import java.util.List;

import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface ITauxReductionDAO {

    void create(Tauxreduction tauxcomm);

    void edit(Tauxreduction tauxcomm);

    void remove(Tauxreduction tauxcomm);

    Tauxreduction find(Object id);

    List<Tauxreduction> findAll();

    List<Tauxreduction> findRange(int[] range);

    int count();



	Tauxreduction getTauxComValide(long idProdconv, Integer pack);
    
}
