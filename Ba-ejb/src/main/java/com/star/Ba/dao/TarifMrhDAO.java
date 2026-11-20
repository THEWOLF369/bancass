/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.ITarifMrhDAO;
import com.star.Ba.model.TarifMrh;
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
public class TarifMrhDAO extends AbstractDAO<TarifMrh> implements ITarifMrhDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarifMrhDAO() {
        super(TarifMrh.class);
    }
    
    @Override
    public TarifMrh getTarifMrh() {
        Query q = em.createQuery("select p from TarifMrh p ") ;

        List<TarifMrh> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
