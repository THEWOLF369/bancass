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

import com.star.Ba.model.Qualite;



/**
 *
 * @author star
 */
@Stateless
public class QualiteDAO extends AbstractDAO<Qualite> implements IQualiteDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QualiteDAO() {
        super(Qualite.class);
    }
    
    @Override
    public List<Qualite> listeQualite() {
   		Query q = em.createQuery("SELECT a FROM  Qualite a");
   		
   		return q.getResultList();
   	}

	@Override
	public Qualite createNewQualite(Qualite qualite) {
		return em.merge(qualite);
	}
    
}
