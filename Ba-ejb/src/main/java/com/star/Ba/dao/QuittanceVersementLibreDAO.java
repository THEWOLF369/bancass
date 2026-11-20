/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.star.Ba.model.Quittance;
import com.star.Ba.model.QuittanceTerme;
import com.star.Ba.model.QuittanceVersementLibre;
import com.star.Ba.model.Utilisateurs;

/**
 *
 * @author star
 */
@Stateless
public class QuittanceVersementLibreDAO extends AbstractDAO<QuittanceVersementLibre> implements IQuittanceVersementLibreDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuittanceVersementLibreDAO() {
        super(QuittanceVersementLibre.class);
    }

    
    
    @Override
    public QuittanceVersementLibre searchquit (int numquit) {
        Query q = em.createQuery("SELECT a FROM QuittanceVersementLibre a WHERE a.numquit = :numquit").setParameter("numquit", numquit);
        List<QuittanceVersementLibre> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list.get(0);
        }
    }
    
    @Override
    public List<QuittanceVersementLibre> searchquitByNAdhesion (String nAdhesion) {
        Query q = em.createQuery("SELECT a FROM QuittanceVersementLibre a WHERE a.idAdhesion.nadhesion = :nAdhesion").setParameter("nAdhesion", nAdhesion);
        List<QuittanceVersementLibre> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list;
        }
    }
    
    
    @Override
    public QuittanceVersementLibre searchquitByNumquit (int numquit) {
        Query q = em.createQuery("SELECT a FROM QuittanceVersementLibre a WHERE a.numquit = :numquit").setParameter("numquit", numquit);
        List<QuittanceVersementLibre> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list.get(0);
        }
    }
    
    
    
    @Override
    public List<QuittanceVersementLibre> searchquitByManyNumquit (List<BigInteger> numquit) {
        Query q = em.createQuery("SELECT a FROM QuittanceVersementLibre a WHERE a.numquit in ("+join (",", numquit)+")");
        System.out.println("sql "+"SELECT a FROM QuittanceVersementLibre a WHERE a.numquit in ("+join (",", numquit)+")");		
        List<QuittanceVersementLibre> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            System.out.println("not empty list for QuittanceVersementLibre");
            return list;
        }
    }
    
    
    
	public static String join(String separator, List<BigInteger> values) {
	    StringBuilder sb = new StringBuilder(128);
	    int end = 0;
	    for (BigInteger s : values) {
	      if (s != null) {
	        sb.append(s);
	        end = sb.length();
	        sb.append(separator);
	      }
	    }
	    return sb.substring(0, end);
	  }
    
//	    @Override
//	    public Quittance FindMaxNumquit() {
//	        Query q = em.createQuery("select a from Quittance a  where a.numquit=(select max(a.numquit) from Quittance a )");
//			
//	        List<Quittance> quittance=q.getResultList();
//			
//			if(!quittance.isEmpty()){
//				return quittance.get(0);
//			}else{
//				return null;
//			}
//	       
//	    }
	    
	    @Override
	    public String FindMaxNumquit() {
	        Query q = em.createQuery("select max(a.numquit) from QuittanceVersementLibre a ");
			
			String numquit=String.valueOf( q.getSingleResult());
			if(!numquit.isEmpty()){
				return numquit;
			}else{
				return "0";
			}
	       
	    }
}
