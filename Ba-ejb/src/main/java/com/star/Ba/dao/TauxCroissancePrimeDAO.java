/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.ITauxcommDAO;
import com.star.Ba.model.TauxCroissancePrime;
import com.star.Ba.model.Tauxcomm;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author star
 */
@Stateless
public class TauxCroissancePrimeDAO extends AbstractDAO<TauxCroissancePrime> implements ITauxCroissancePrimeDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxCroissancePrimeDAO() {
        super(TauxCroissancePrime.class);
    }

   
}
