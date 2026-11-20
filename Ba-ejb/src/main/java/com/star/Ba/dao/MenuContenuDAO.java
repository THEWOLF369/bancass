/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;


import java.math.BigDecimal;
import java.util.List;

import com.star.Ba.model.Duree;
import com.star.Ba.model.Menucontenu;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author star
 */
@Stateful
public class MenuContenuDAO extends AbstractDAO<Menucontenu> implements IMenucontenuDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuContenuDAO() {
        super(Menucontenu.class);
    }
    @Override
    public List<Menucontenu> getMenuContenu(String page, BigDecimal idProfil){
    	Query q = em.createQuery("SELECT a FROM Menucontenu a where a.idmenu.idprofil.idProfil = :idProfil and  a.idmenu.url = :page");
		q.setParameter("idProfil", idProfil);
		q.setParameter("page", page);
		return q.getResultList();
    }
    
    
    @Override
    public List<Menucontenu> getMenuContenuByPack(String page, BigDecimal idProfil, Integer pack){
    	Query q = em.createQuery("SELECT a FROM Menucontenu a where a.idmenu.idprofil.idProfil = :idProfil and  a.idmenu.url = :page and a.pack=:pack");
		q.setParameter("idProfil", idProfil);
		q.setParameter("page", page);
		q.setParameter("pack", pack);
		return q.getResultList();
    }
    
    
  
}
