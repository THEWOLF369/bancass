/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;
import java.math.BigInteger;
import java.util.List;

import com.star.Ba.model.PackPolice;
import com.star.Ba.model.PackPolicePK;



/**
 *
 * @author star
 */

public interface IPackPolicePKDAO {

    void create(PackPolicePK packPolicePK);

    void edit(PackPolicePK packPolicePK);

    void remove(PackPolicePK packPolicePK);

    PackPolicePK find(Object id);

    List<PackPolicePK> findAll();

    List<PackPolicePK> findRange(int[] range);
    
    int count();

	PackPolicePK insertPackPolicePK(BigInteger idPackPolice, String nPolice);

	BigInteger getMaxPackPolicePK();

    
}
