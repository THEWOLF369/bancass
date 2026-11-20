/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.ComgouvMrh;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface IComgouvMrhDAO {

    void create(ComgouvMrh comgouv);

    void edit(ComgouvMrh comgouv);

    void remove(ComgouvMrh comgouv);

    ComgouvMrh find(Object id);

    List<ComgouvMrh> findAll();

    List<ComgouvMrh> findRange(int[] range);

    int count();

    public com.star.Ba.model.ComgouvMrh getTauxGouvByGouv(java.lang.String gouv);
   

    
}
