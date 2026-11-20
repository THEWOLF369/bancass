/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.ITauxcommDAO;
import com.star.Ba.model.Tauxcomm;

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
public class TauxcommDAO extends AbstractDAO<Tauxcomm> implements ITauxcommDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxcommDAO() {
        super(Tauxcomm.class);
    }

    @Override
    public Tauxcomm getTauxCommByUsageGar(String usage, String typeGar, String numGar) {
        Query q = em.createQuery("select t from Tauxcomm t where t.codusg = :usage AND t.typegar = :type AND t.numgar = :num")
                .setParameter("usage", usage).setParameter("type", typeGar).setParameter("num", numGar);
        List<Tauxcomm> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public Tauxcomm getTauxCom(String codeProd) {
        Query q = em.createQuery("select t from Tauxcomm t where t.codprod = :codeProd")
                .setParameter("codeProd", codeProd);

        List<Tauxcomm> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    
    @Override
    public Tauxcomm getTauxComValide(long idProdconv) {
        Query q = em.createQuery("select t from Tauxcomm t where t.idProdconv.idProdconv = :idProdconv and :date>=t.datappli")
                .setParameter("idProdconv", idProdconv).setParameter("date", new Date());

        List<Tauxcomm> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
