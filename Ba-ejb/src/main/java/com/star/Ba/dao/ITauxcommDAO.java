/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Tauxcomm;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface ITauxcommDAO {

    void create(Tauxcomm tauxcomm);

    void edit(Tauxcomm tauxcomm);

    void remove(Tauxcomm tauxcomm);

    Tauxcomm find(Object id);

    List<Tauxcomm> findAll();

    List<Tauxcomm> findRange(int[] range);

    int count();


    public com.star.Ba.model.Tauxcomm getTauxCommByUsageGar(java.lang.String usage, java.lang.String typeGar, java.lang.String numGar);

    public com.star.Ba.model.Tauxcomm getTauxCom(java.lang.String codeProd);

	Tauxcomm getTauxComValide(long idProdconv);
    
}
