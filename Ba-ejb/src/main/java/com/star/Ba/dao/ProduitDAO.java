/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.util.List;
import java.util.ArrayList;

import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Utilisateurs;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author star
 */
@Stateful
public class ProduitDAO extends AbstractDAO<Produit> implements IProduitDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProduitDAO() {
        super(Produit.class);
    }
    @Override
    public Produit getProduit_by_code( String codeProduit) {
        Query q = em.createQuery("select a from Produit a where  a.codeProduit = :codeProduit").setParameter("codeProduit", codeProduit);
        List<Produit> list = q.getResultList();
//        return list.get(0);
        return list.isEmpty() ? null : list.get(0);
    }
    
    
    @Override
    public List<Produit> findAllById(List<Long> ids) {

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return em.createQuery(
                "SELECT p FROM Produit p WHERE p.idProduit IN :ids", Produit.class)
                .setParameter("ids", ids)
                .getResultList();
    }
    
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Produit> findByUser(Utilisateurs user) {

        return em.createNativeQuery(
            "SELECT p.* FROM PRODUIT p " +
            "JOIN USER_PRODUIT up ON p.ID_PRODUIT = up.ID_PRODUIT " +
            "WHERE up.ID_UTILISATEURS = ?",
            Produit.class)
            .setParameter(1, user.getIdUtilisateurs())
            .getResultList();
    }
    
  
    
    
  
}
