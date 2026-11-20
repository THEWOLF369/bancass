/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;




import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.star.Ba.dao.INatureRisqDAO;
import com.star.Ba.model.NatureRisq;


/**
 *
 * @author star
 */
@Stateless
public class NatureRisqDAO extends AbstractDAO<NatureRisq> implements INatureRisqDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    
    


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NatureRisqDAO() {
        super(NatureRisq.class);
    }
    
    @Override
    public List<NatureRisq> listeNaturRisq() {
   		Query q = em.createQuery("SELECT a FROM  NatureRisq a");
   		
   		return q.getResultList();
   	}



	@Override
	public NatureRisq createNewNatureRisq(NatureRisq natureRisq) {
		// TODO Auto-generated method stub
		return em.merge(natureRisq);
		
	}
    
}
