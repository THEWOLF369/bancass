/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.TarifIncendie;
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
public class TarifIncendieDAO extends AbstractDAO<TarifIncendie> implements ITarifIncendieDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarifIncendieDAO() {
        super(TarifIncendie.class);
    }

    @Override
    public TarifIncendie tarif_by_police(String police) {
        Query q = em.createQuery("SELECT a FROM TarifIncendie a WHERE a.police = :police").setParameter("police", police);
        List<TarifIncendie> list = q.getResultList();
        return list.get(0);
    }

  

}
