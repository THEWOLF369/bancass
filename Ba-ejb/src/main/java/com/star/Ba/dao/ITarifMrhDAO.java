/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.TarifMrh;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface ITarifMrhDAO {

    void create(TarifMrh tarifMrh);

    void edit(TarifMrh tarifMrh);

    void remove(TarifMrh tarifMrh);

    TarifMrh find(Object id);

    List<TarifMrh> findAll();

    List<TarifMrh> findRange(int[] range);

    int count();

    public com.star.Ba.model.TarifMrh getTarifMrh();
    
}
