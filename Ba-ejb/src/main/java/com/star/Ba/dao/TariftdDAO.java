/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;



import com.star.Ba.model.Tariftd;

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
public class TariftdDAO extends AbstractDAO<Tariftd> implements ITariftdDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TariftdDAO() {
        super(Tariftd.class);
    }

	@Override
	public double getTaux_prime(int dur, int age, String code,double capital,String nPolice)
	{
		System.out.println("duree de getTaux_prime="+dur);
		Query q = null;
		System.out.println("code="+code);
        if (code.equals("AD")) {
        	System.out.println("q1");
            q = em.createQuery("select t from Tariftd t where t.durmin <:dur AND t.durmax >= :dur AND t.agemin < :age AND t.agemax >= :age AND t.nPolice =:nPolice")
            		.setParameter("dur", dur).setParameter("age", age).setParameter("nPolice", nPolice);
        }
        if (code.equals("D")) {
            q = em.createQuery("select t from Tariftd t where t.durmin <:dur AND t.durmax >= :dur AND t.nPolice =:nPolice").setParameter("dur", dur).setParameter("nPolice", nPolice);
        }
            
          if (code.equals("C")) {
          q = em.createQuery("select t from Tariftd t where t.capmin <:capital AND t.capmax>= :capital AND t.nPolice =:nPolice").setParameter("capital", capital).setParameter("nPolice", nPolice);
      }
        System.out.println("affich list");
        List<Tariftd> list = q.getResultList();
        System.out.println("list tarifts"+list);
        if (list != null && !list.isEmpty()) {
        	return list.get(0).getTaux();
        	}
        return 0;
    	
	}
	@Override
	public String getcodetarif_by_police(String nPolice)
	{
		Query a = em.createQuery("select t from Tariftd t where t.nPolice =:nPolice ").setParameter("nPolice", nPolice);
		
		List<Tariftd> list = a.getResultList();
		System.out.println("List<Tariftd> list="+ a.getResultList());
		System.out.println("list.get(0).getCode()="+list.get(0).getCode());
	    return list.get(0).getCode();
	}
	
	@Override
	public int find_min_age(String nPolice)
	 {
		Query a = em.createQuery("select MIN(t.agemin) from Tariftd t where t.nPolice =:nPolice ").setParameter("nPolice", nPolice);
		return ((Integer) a.getResultList().get(0)).intValue();
	}
	
	@Override
	public int find_max_age(String nPolice)
	 {
		Query a = em.createQuery("select MAX(t.agemax) from Tariftd t where t.nPolice =:nPolice ").setParameter("nPolice", nPolice);
		return ((Integer) a.getResultList().get(0)).intValue();
	}
    
}
