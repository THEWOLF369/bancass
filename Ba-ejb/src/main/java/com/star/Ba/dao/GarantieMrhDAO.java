/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.IGarantieMrhDAO;
import com.star.Ba.model.GarantieMrh;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author star
 */
@Stateless
public class GarantieMrhDAO extends AbstractDAO<GarantieMrh> implements IGarantieMrhDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GarantieMrhDAO() {
        super(GarantieMrh.class);
    }
    
}
