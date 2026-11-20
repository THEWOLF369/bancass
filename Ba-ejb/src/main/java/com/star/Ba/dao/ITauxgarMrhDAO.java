/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.TauxgarMrh;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface ITauxgarMrhDAO {

    void create(TauxgarMrh tauxgarMrh);

    void edit(TauxgarMrh tauxgarMrh);

    void remove(TauxgarMrh tauxgarMrh);

    TauxgarMrh find(Object id);

    List<TauxgarMrh> findAll();

    List<TauxgarMrh> findRange(int[] range);

    int count();

    public List<TauxgarMrh> getTauxGarMrhByPack(String packName);
    
}
