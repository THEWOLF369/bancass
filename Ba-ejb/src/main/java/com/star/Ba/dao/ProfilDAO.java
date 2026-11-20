package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import com.star.Ba.model.Profil;
import com.star.Ba.model.Utilisateurs;



@Stateless
public class ProfilDAO extends AbstractDAO<Profil> implements IProfilDAO{
	
	
	 @PersistenceContext(unitName = "oracle")
	    private EntityManager em;

	    @Override
	    protected EntityManager getEntityManager() {
	        return em;
	    }

	    public ProfilDAO() {
	        super(Profil.class);
	    }
	    
	    @Override
	    public Profil find_profil_by_code(String code)
	    {
	    	Query q = em.createQuery("SELECT a FROM Profil a where a.code = :code");
			q.setParameter("code", code);
			return (Profil) q.getSingleResult();
	    }
	    
	    
		public  List<Profil> getprofilbna() {
			
			//Query q = em.createQuery("SELECT a FROM Profil a where a.code IN ('BNA', 'BNAA', 'DBNA','CBNA') ");
			
			Query q = em.createQuery("SELECT a FROM Profil a where a.code = 'BNAA'");
			
//			Query q = em.createQuery("SELECT a FROM Profil a where a.code in ( 'BNAA', 'BNA' ) ");

			//q.setParameter("code", code);
			return  q.getResultList(); 
		
		}
		
		
		
		public  List<Profil> getprofilbna_all() {
			
			//Query q = em.createQuery("SELECT a FROM Profil a where a.code IN ('BNA', 'BNAA', 'DBNA','CBNA') ");
			
//			Query q = em.createQuery("SELECT a FROM Profil a where a.code = 'BNAA'");
			
			Query q = em.createQuery("SELECT a FROM Profil a where a.code in ( 'BNAA', 'BNA' ) ");

			//q.setParameter("code", code);
			return  q.getResultList(); 
		
		}

}
