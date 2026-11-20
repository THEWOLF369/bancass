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

import com.star.Ba.model.Quittance;
import com.star.Ba.model.QuittanceDetails;
import com.star.Ba.model.QuittanceTerme;
import com.star.Ba.model.Utilisateurs;

/**
 *
 * @author star
 */
@Stateless
public class QuittanceDetailsDAO extends AbstractDAO<QuittanceDetails> implements IQuittanceDetailsDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuittanceDetailsDAO() {
        super(QuittanceDetails.class);
    }

	@Override
	public void createQuittanceDetails(QuittanceDetails quittancedetails, Quittance quittance) {
		// TODO Auto-generated method stub
		
		Quittance qtc = em.merge(quittance);
		
		QuittanceDetails qt = new QuittanceDetails(qtc.getIdQuittance(), quittancedetails.getIdAdhesion(),
				quittancedetails.getAgence(), quittancedetails.getTypequit(), 
				quittancedetails.getEtat(), quittancedetails.getFrac(), 
				quittancedetails.getPrimetot(), quittancedetails.getPrimenette());
		
		em.merge(qt);
		
	}
	
	
	@Override
	public QuittanceDetails  Merge(QuittanceDetails qtc) {
	
	em.merge(qtc);
	
	em.flush();
	return qtc;
	}
	
	
	
    @Override
    public List<QuittanceDetails> searchByNAdhesion (String nAdhesion, String typequit) {
        Query q = em.createQuery("SELECT a FROM QuittanceDetails a WHERE "
        		+ "a.idAdhesion.nadhesion = :nAdhesion and a.typequit = :typequit")
        		.setParameter("nAdhesion", nAdhesion)
        		.setParameter("typequit", typequit);
        List<QuittanceDetails> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list;
        }
    }

    
    @Override
    public QuittanceDetails searchDetailQuit (long idQuittance) {
        Query q = em.createQuery("SELECT a FROM QuittanceDetails a WHERE a.idQuittance = :idQuittance")
        		.setParameter("idQuittance", idQuittance);
        List<QuittanceDetails> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list.get(0);
        }
    }
    
    
    @Override
    public QuittanceDetails searchDetailQuit_not_encaissed (String nAdhesion, String typequit, String etat) {
        Query q = em.createQuery("SELECT a FROM QuittanceDetails a WHERE "
        		+ "a.idAdhesion.nadhesion = :nAdhesion and a.typequit = :typequit and a.etat = :etat ")
        		.setParameter("nAdhesion", nAdhesion)
        .setParameter("typequit", typequit)
        .setParameter("etat", etat);
        List<QuittanceDetails> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list.get(0);
        }
    }
    
    
    
}
