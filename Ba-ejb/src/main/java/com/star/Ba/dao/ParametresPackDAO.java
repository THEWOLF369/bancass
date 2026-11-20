/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.star.Ba.model.ParametresPack;

/**
 *
 * @author star
 */
@Stateless
public class ParametresPackDAO extends AbstractDAO<ParametresPack> implements IParametresPackDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametresPackDAO() {
        super(ParametresPack.class);
    }
    
    @Override
    public Map<String, ParametresPack> findParametresByPack(Integer pack) {
        Query q = em.createQuery("SELECT p FROM ParametresPack p WHERE p.pack = :pack and p.idParam.valBool=1").setParameter("pack", pack);
        List<ParametresPack> list=  q.getResultList();
        
        Map<String, ParametresPack> resultsMap = new HashMap<String, ParametresPack>();
        for (ParametresPack parametresPack : list) {
        	resultsMap.put(parametresPack.getIdParam().getCodeParam(), parametresPack);
		}
        return resultsMap;
    }

    
}
