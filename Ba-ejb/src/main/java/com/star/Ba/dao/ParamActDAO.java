/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

///
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
import com.star.Ba.model.ParamAct;
import javax.persistence.TypedQuery;



/**
 *
 * @author star
 */
@Stateless
public class ParamActDAO extends AbstractDAO<ParamAct> implements IParamActDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParamActDAO() {
        super(ParamAct.class);
    }

	@Override
    public int getParamAct_by_numact (int age ,double  capital) {
		
    	Query q =em.createQuery("select a from ParamAct a where a.ageMin<= :age And a.ageMax >=:age and a.capitalMin<= :capital and a.capitalMax >= :capital")
        		.setParameter("age",age).setParameter("capital", capital);
        
        List<ParamAct>list= q.getResultList();
        if (list != null && !list.isEmpty()) {
        	System.out.println("numact="+list.get(0).getNumAct());
        	return list.get(0).getNumAct() ;
        	}
        return 0;
    }

	
     
 
        
       
    }

	

