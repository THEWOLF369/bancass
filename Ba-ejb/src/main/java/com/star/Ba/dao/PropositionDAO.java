/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;



import java.util.ArrayList;
import java.util.List;





import com.star.Ba.model.Client;
import com.star.Ba.model.Police;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.model.Adhesion;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author star
 */
@Stateless
public class PropositionDAO extends AbstractDAO<Proposition> implements IPropositionDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PropositionDAO() {
        super(Proposition.class);
    }

    @Override
    public Proposition saveOrUpdate(Proposition proposotion) {
    	return  getEntityManager().merge(proposotion);
    }
    
	@Override
	public List<Proposition> findByUtilisateur(Utilisateurs utilisateur) {

		TypedQuery<Proposition> query = em.createNamedQuery("Proposition.findByUtilisateur", Proposition.class);
		query.setParameter("idUtilisateurs", utilisateur.getIdUtilisateurs());
		List<Proposition> results = query.getResultList();
		//Query q = em.createQuery("SELECT a FROM Proposition a WHERE a.idUtilisateurs = :idUtilisateurs").setParameter("idUtilisateurs", idUtilisateurs);
        //List<Proposition> results = q.getResultList();
		return results;
	
	}
	@Override
	public List<Client> findClientFromPropositionByUtilisateurAndPolice(List<Utilisateurs> utilisateur, List<Police> listePolice) {
		if(!utilisateur.isEmpty()){
		 Query q = em.createQuery("select distinct a.client from Proposition a where a.utilisateur in (:inclList) and a.police in (:inplcList) "
		 		+ " and a.client not in (select distinct a.client from Adhesion a where a.utilisateur in (:inclList) and a.police in (:inplcList))" );
	        q.setParameter("inclList", utilisateur);
	        q.setParameter("inplcList", listePolice);
	        return q.getResultList();
		}
		else {
			 return new ArrayList<Client>();	
			}
	}
	
	
	@Override
	public List<Client> findClientFromPropositionByUtilisateur(	List<Utilisateurs> utilisateur) {
	 
		 if(!utilisateur.isEmpty()){
		 Query q = em.createQuery("select distinct a.client from Proposition a where a.utilisateur in :inclList   "
			 		+ " and a.client not in (select distinct a.client from Adhesion a where a.utilisateur in ( :inclList  ) )");
         
	        q.setParameter("inclList", utilisateur);
	        return q.getResultList();}
		 else {
			 return new ArrayList<Client>();	
			}
	}
	@Override
	public List<Proposition> findByPolice(Utilisateurs utilisateur,Police police) {

		TypedQuery<Proposition> query = em.createNamedQuery("Proposition.findByPolice", Proposition.class);
		query.setParameter("idUtilisateurs", utilisateur.getIdUtilisateurs());
		query.setParameter("police", police);
		List<Proposition> results = query.getResultList();
		 
		return results;
	
	}
/*** consultation ****/
	
	@Override
	public List<Proposition> findByUtilisateurAndDate(Utilisateurs utilisateur,String annee1,String annee2,
			String nProposition, String identifiant) {
		 
		StringBuilder jpql = new StringBuilder(
			        "SELECT p FROM Proposition p WHERE p.idUtilisateurs = :idUtilisateurs "
			    );
		
		  // 🔹 Date BETWEEN (seulement si les 2 dates sont saisies)
	    if (annee1 != null && !annee1.trim().isEmpty()
	     && annee2 != null && !annee2.trim().isEmpty()) {

	        jpql.append(
	            " and trunc(p.datcreation) between "
	          + "TO_DATE(:annee1, 'dd/mm/yyyy') "
	          + "and TO_DATE(:annee2, 'dd/mm/yyyy') "
	        );
	    }

	    // 🔹 N° adhésion
	    if (nProposition != null && !nProposition.trim().isEmpty()) {
	        jpql.append(" and p.nProposition = :nProposition ");
	    }

	    // 🔹 Identifiant client
	    if (identifiant != null && !identifiant.trim().isEmpty()) {
	        jpql.append(" and p.client.identifiant = :identifiant ");
	    }

	    jpql.append(" order by p.datcreation desc");

	    Query query = em.createQuery(jpql.toString());
		
//		  Query query = em.createQuery("SELECT p FROM Proposition p  WHERE  p.idUtilisateurs = :idUtilisateurs "
//					+"and trunc(p.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy') and TO_DATE(:annee2, 'dd/mm/yyyy') "
//					+ "order by p.datcreation desc") ;
		   query.setParameter("idUtilisateurs", utilisateur.getIdUtilisateurs());
//			query.setParameter("annee1", annee1);
//			query.setParameter("annee2", annee2);
		   if (annee1 != null && !annee1.trim().isEmpty()
				     && annee2 != null && !annee2.trim().isEmpty()) {
				        query.setParameter("annee1", annee1);
				        query.setParameter("annee2", annee2);
				    }

				    if (nProposition != null && !nProposition.trim().isEmpty()) {
				        query.setParameter("nProposition", nProposition);
				    }

				    if (identifiant != null && !identifiant.trim().isEmpty()) {
				        query.setParameter("identifiant", identifiant);
				    }
				    
		List<Proposition> results = query.getResultList();
		 
		return results;
		
		
	
	}
	
	@Override
	public List<Proposition> findByPoliceAndDate(Utilisateurs utilisateur,Police police,String annee1,String annee2,
			String nProposition, String identifiant) {

//		Query query = em.createQuery("SELECT p FROM Proposition p WHERE p.idUtilisateurs = :idUtilisateurs and p.police = :police "
//				+"and trunc(p.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy') and TO_DATE(:annee2, 'dd/mm/yyyy') "
//				+ "order by p.datcreation desc") ;
		
		StringBuilder jpql = new StringBuilder(
		        "SELECT p FROM Proposition p WHERE p.idUtilisateurs = :idUtilisateurs and p.police = :police "
		    );
	
	  // 🔹 Date BETWEEN (seulement si les 2 dates sont saisies)
    if (annee1 != null && !annee1.trim().isEmpty()
     && annee2 != null && !annee2.trim().isEmpty()) {

        jpql.append(
            " and trunc(p.datcreation) between "
          + "TO_DATE(:annee1, 'dd/mm/yyyy') "
          + "and TO_DATE(:annee2, 'dd/mm/yyyy') "
        );
    }

    // 🔹 N° adhésion
    if (nProposition != null && !nProposition.trim().isEmpty()) {
        jpql.append(" and p.nProposition = :nProposition ");
    }

    // 🔹 Identifiant client
    if (identifiant != null && !identifiant.trim().isEmpty()) {
        jpql.append(" and p.client.identifiant = :identifiant ");
    }

    jpql.append(" order by p.datcreation desc");

    Query query = em.createQuery(jpql.toString());
		
		query.setParameter("idUtilisateurs", utilisateur.getIdUtilisateurs());
		query.setParameter("police", police);
//		query.setParameter("annee1", annee1);
//		query.setParameter("annee2", annee2);
		   if (annee1 != null && !annee1.trim().isEmpty()
				     && annee2 != null && !annee2.trim().isEmpty()) {
				        query.setParameter("annee1", annee1);
				        query.setParameter("annee2", annee2);
				    }

				    if (nProposition != null && !nProposition.trim().isEmpty()) {
				        query.setParameter("nProposition", nProposition);
				    }

				    if (identifiant != null && !identifiant.trim().isEmpty()) {
				        query.setParameter("identifiant", identifiant);
				    }
		List<Proposition> results = query.getResultList();
		 
		return results;
		
	}
	
	
	 /***** recherche cas TD fait par STAR pour STB ********/
	
	 @Override
	    public List<Proposition> findPropositionByStarForSTB(List<Police> listPolice,List<Utilisateurs> listStar,
	    		String annee1,String annee2, String nProposition, 
                String identifiant) {
//		   System.out.println("dateDebut="+annee1+" dateTo="+annee2);
		 
		 StringBuilder jpql = new StringBuilder(
			        "select a from Proposition a "
			      + "WHERE a.police in (:listPolice) "
			      + "and a.utilisateur in (:listStar) "
			    );

			    // 🔹 Date BETWEEN (seulement si les 2 dates sont saisies)
			    if (annee1 != null && !annee1.trim().isEmpty()
			     && annee2 != null && !annee2.trim().isEmpty()) {

			        jpql.append(
			            " and trunc(a.datcreation) between "
			          + "TO_DATE(:annee1, 'dd/mm/yyyy') "
			          + "and TO_DATE(:annee2, 'dd/mm/yyyy') "
			        );
			    }

			    // 🔹 N° adhésion
			    if (nProposition != null && !nProposition.trim().isEmpty()) {
			        jpql.append(" and a.nProposition = :nProposition ");
			    }

			    // 🔹 Identifiant client
			    if (identifiant != null && !identifiant.trim().isEmpty()) {
			        jpql.append(" and a.client.identifiant = :identifiant ");
			    }

			    jpql.append(" order by a.datcreation desc");

			    Query query = em.createQuery(jpql.toString());
		 
//		   Query query = em.createQuery("select a from Proposition a  WHERE a.police in ( :listPolice ) and  a.utilisateur in  ( :listStar ) "
//		   		+ "and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy') and TO_DATE(:annee2, 'dd/mm/yyyy') "
//		   		+ "order by p.datcreation desc") ;
		   query.setParameter("listPolice", listPolice);
		   query.setParameter("listStar", listStar);
//			query.setParameter("annee1", annee1);
//			query.setParameter("annee2", annee2);
		   if (annee1 != null && !annee1.trim().isEmpty()
				     && annee2 != null && !annee2.trim().isEmpty()) {
				        query.setParameter("annee1", annee1);
				        query.setParameter("annee2", annee2);
				    }

				    if (nProposition != null && !nProposition.trim().isEmpty()) {
				        query.setParameter("nProposition", nProposition);
				    }

				    if (identifiant != null && !identifiant.trim().isEmpty()) {
				        query.setParameter("identifiant", identifiant);
				    }
			
			List<Proposition> results = query.getResultList();


	        return results;
	    }
	 
	 
	 @Override
	    public int  traitement_prop(String nProposition)
	    {
	    	String requete = "Update  Proposition set etat='E' where nProposition=:nProposition) " ;
	          int updatedEntities = em.createQuery(requete).setParameter("nProposition", nProposition).executeUpdate();
	          return  updatedEntities ;
	                    
	    }
	 
	 @Override
	    public boolean  nproposition_unique(String nProposition)
	    {
		  Query query = em.createQuery("select a from Proposition a  WHERE a.nproposition = :nProposition ") ;
			   query.setParameter("nProposition", nProposition);
			   boolean result ;
			   if (query.getResultList() != null)
			   {
				   result = false;
			   }
			   else {
				   result = true;
			   }
			   
			   
	          return  result ;
	                    
	    }
	 
	 
	 
	 
	
}
