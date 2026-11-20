/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Pack;
import com.star.Ba.model.Produit;

import java.util.List;



/**
 *
 * @author star
 */

public interface IPackDAO {

    void create(Pack pack);

    void edit(Pack pack);

    void remove(Pack pack);

    Pack find(Object id);

    List<Pack> findAll();

    List<Pack> findRange(int[] range);
    
    int count();

	List<Pack> getPack_by_codprod(Produit codprod);
    
}
