/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.commun.EnumCodeProduitEjb;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author star
 */
@Stateful
public class ProdconvDAO extends AbstractDAO<Prodconv> implements IProdconvDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProdconvDAO() {
        super(Prodconv.class);
    }
    
    
    @Override
    public List<Prodconv> getlstProduit_by_convention( Convention convention) {
        Query q = em.createQuery("select a from Prodconv a where  a.convention = :convention").setParameter("convention", convention);
        List<Prodconv> list = q.getResultList();
        return list;
        
       
    }
    
    
    @Override
    public List<Prodconv> getlstProduit_by_convention_for_qtc( Convention convention) {
    	
		   final String CODE_HAYYA = EnumCodeProduitEjb.H7AYYA.getCode();
		   final String CODE_HAYYA_QNB = EnumCodeProduitEjb.H7ayyaQNB.getCode();
		   final String CODE_AVJ = EnumCodeProduitEjb.AVENIRJENESSE.getCode();
		   
        Query q = em.createQuery("select a from Prodconv a where  a.convention = :convention and "
        		+ "a.codeProduit in ('"+CODE_HAYYA+"', '"+CODE_HAYYA_QNB+"', '"+CODE_AVJ+"')").setParameter("convention", convention);
        List<Prodconv> list = q.getResultList();
        return list;
        
       
    }
    
    
    @Override
    public List<Prodconv> getlstProduit_by_convention_for_qtc_vlibre( Convention convention) {
    	
    	final String CODE_HAYYA = EnumCodeProduitEjb.H7AYYA.getCode();
    	final String CODE_HAYYA_QNB = EnumCodeProduitEjb.H7ayyaQNB.getCode();
    	final String CODE_AVJ = EnumCodeProduitEjb.AVENIRJENESSE.getCode();
    	
        Query q = em.createQuery("select a from Prodconv a where  a.convention = :convention and "
        		+ "a.codeProduit in ('"+CODE_HAYYA+"', '"+CODE_HAYYA_QNB+"', '"+CODE_AVJ+"')").setParameter("convention", convention);
        List<Prodconv> list = q.getResultList();
        return list;
        
       
    }
    
   

}
