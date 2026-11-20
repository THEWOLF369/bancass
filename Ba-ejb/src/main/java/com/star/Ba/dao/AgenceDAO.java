/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Police;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Societe;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author star
 */
@Stateless
public class AgenceDAO extends AbstractDAO<Agence> implements IAgenceDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    public AgenceDAO() {
        super(Agence.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<Agence> listeAgenceParSociete(Societe societe) {
		Query q = em.createQuery("SELECT a FROM Agence a where a.societe = :societe and a.codeAgence not in ('PRC','AGST') order by a.idAgence");
		q.setParameter("societe", societe);
		return q.getResultList();
	}

	@Override
	public Agence createNewAgence(Agence agence) {
		
		return em.merge(agence);
	}
	
	/*public Agence getagencebna() {  
		
		Query q = em.createQuery("SELECT a FROM Agence a where a.societe = 'B142431996'");
		//q.setParameter("code", code);
		return (Agence) q.getSingleResult();
	
	}*/
	
	
	
public  List<Agence> getagencebna() {  
		
		Query q = em.createQuery("SELECT a FROM Agence a where a.societe = 'B142431996'");
		//q.setParameter("code", code);
		return q.getResultList();
	
	}
	

public  List<Agence> getagencestb() {  
	
	Query q = em.createQuery("SELECT a FROM Agence a where a.societe = '002707PPM000'");
	//q.setParameter("code", code);
	return q.getResultList();

}

public  List<Agence> VerifAgenceSTB(String codeAgence) {  
	
	Query q = em.createQuery("SELECT a FROM Agence a where a.societe = '002707PPM000' and codeAgence = :codeAgence");
	q.setParameter("codeAgence", codeAgence);
	return q.getResultList();

}

	
 /* public List<Agence> getagencebna() {  
		
		Query q = em.createQuery("SELECT a FROM Agence a where a.idAgence BETWEEN 5769 AND 5949");
		//q.setParameter("code", code);
		return q.getResultList();
	
	}*/


@Override
public Agence FindAgencePrincipaleByCodeSociete(String code_societe) {
	Query q = em.createQuery("select a from Agence a where a.societe.codSociet = :code  and a.codeAgence= 'PRC'").setParameter("code", code_societe);

	List<Agence> agc=q.getResultList();

	if(!agc.isEmpty()){
		return agc.get(0);
	}else{
		return null;
	}

}



/// pour 7ayya 

@Override
public Agence FindAgenceBycode(String code) {
	Query q = em.createQuery("select a from Agence a  where a.codeAgence= :code").setParameter("code", code);

	List<Agence> agc=q.getResultList();

	if(!agc.isEmpty()){
		return agc.get(0);
	}else{
		return null;
	}

}

}
