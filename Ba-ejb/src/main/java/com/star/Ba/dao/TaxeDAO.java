
package com.star.Ba.dao;

import java.util.Date;
import java.util.List;

import com.star.Ba.model.Produit;
import com.star.Ba.model.Taxe;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author star
 */
@Stateful
public class TaxeDAO extends AbstractDAO<Taxe> implements ITaxeDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxeDAO() {
        super(Taxe.class);
    }
    
    
    @Override
    public double gettaxe_by_produit( Produit produit,Date date_creation) {
        Query q = em.createQuery("select a from Taxe a where  a.produit = :produit and :date_creation >= date_effet ");
        q.setParameter("produit", produit);
        q.setParameter("date_creation", date_creation);
        
        List<Taxe> list = q.getResultList();
        if(list.size()==1){
        	return list.get(0).getTaxe();
        }
        else{
        	  q = em.createQuery("select a from Taxe a where  a.produit = :produit and date_effet=(select max(a.date_effet) from Taxe a where a.produit = :produit) ").setParameter("produit", produit);
        	 list = q.getResultList();
        	 if(!list.isEmpty()){
     			return list.get(0).getTaxe();
     		}else{
     			return 0;
     		}
        }
        	
       
    }
}
