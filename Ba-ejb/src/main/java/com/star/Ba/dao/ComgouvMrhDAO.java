/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.IComgouvMrhDAO;
import com.star.Ba.model.ComgouvMrh;
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
public class ComgouvMrhDAO extends AbstractDAO<ComgouvMrh> implements IComgouvMrhDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComgouvMrhDAO() {
        super(ComgouvMrh.class);
    }

    @Override
    public ComgouvMrh getTauxGouvByGouv(String gouv) {
        Query q = em.createQuery("select c from ComgouvMrh c where c.gouvernora = :gouv")
                .setParameter("gouv", gouv);

        List<ComgouvMrh> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(list.size()-1);
        }
    }
}
