/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.ITauxgarMrhDAO;
import com.star.Ba.model.TauxgarMrh;

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
public class TauxgarMrhDAO extends AbstractDAO<TauxgarMrh> implements ITauxgarMrhDAO {

   

	@PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxgarMrhDAO() {
        super(TauxgarMrh.class);
    }

    @Override
    public List<TauxgarMrh> getTauxGarMrhByPack(String packName) {
        Query q = em.createQuery("select t from TauxgarMrh t where t.pack1 = 1");

        if (packName.equals("pack2")) {
            q = em.createQuery("select t from TauxgarMrh t where t.pack2 = 1");
        } else if (packName.equals("pack3")) {
            q = em.createQuery("select t from TauxgarMrh t where t.pack3 = 1");
        } else if (packName.equals("pack4")) {
            q = em.createQuery("select t from TauxgarMrh t where t.pack4 = 1");
        }else if (packName.equals("pack5")) {
            q = em.createQuery("select t from TauxgarMrh t where t.pack5 = 1");
        }
       
        List<TauxgarMrh> list = q.getResultList();
        return list;
    }
}
