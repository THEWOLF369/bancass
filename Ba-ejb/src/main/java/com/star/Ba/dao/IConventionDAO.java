/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Convention;
import com.star.Ba.model.Societe;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface IConventionDAO {

    void create(Convention convention);

    void edit(Convention convention);

    void remove(Convention convention);

    Convention find(Object id);

    List<Convention> findAll();

    List<Convention> findRange(int[] range);

    int count();
    
    Convention lazyLoad(Convention convention);

    Convention find_conv_by_Societ(Societe societe);
    Convention createOrUpdate(Convention convention);

	Convention find_conv_by_Societbna();
    
}
