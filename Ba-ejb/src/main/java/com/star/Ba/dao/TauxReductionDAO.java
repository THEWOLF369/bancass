/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.ITauxcommDAO;
import com.star.Ba.model.Tauxcomm;
import com.star.Ba.model.Tauxreduction;

import java.util.Date;
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
public class TauxReductionDAO extends AbstractDAO<Tauxreduction> implements ITauxReductionDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxReductionDAO() {
        super(Tauxreduction.class);
    }

    
    @Override
    public Tauxreduction  getTauxComValide(long idProdconv , Integer pack) {
        Query q = em.createQuery("select t from Tauxreduction t where t.idProdconv.idProdconv = :idProdconv and :date>=t.datappli and t.pack=:pack")
                .setParameter("idProdconv", idProdconv).setParameter("pack", pack).setParameter("date", new Date());

        List<Tauxreduction> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
