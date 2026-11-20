/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.commun.EnumCodeProduitEjb;
import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Parcelle;
import com.star.Ba.model.Police;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Utilisateurs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author star
 */
@Stateless
public class AdhesionDAO extends AbstractDAO<Adhesion> implements IAdhesionDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public AdhesionDAO() {
		super(Adhesion.class);
	}

	@Override
	public List<Adhesion> findByUtilisateur(Utilisateurs utilisateur) {
		TypedQuery<Adhesion> query = em.createNamedQuery(
				"Adhesion.findByUtilisateur", Adhesion.class);
		query.setParameter("idUtilisateurs", utilisateur.getIdUtilisateurs());
		List<Adhesion> results = query.getResultList();

		return results;
	}
	
	@Override
	public Adhesion  merge(Adhesion adhesion) {
	if(adhesion.getIdAdhesion() == 0) {		
	em.persist(adhesion);
	}else{
	em.merge(adhesion);
	}
	em.flush();
	return adhesion;
	}

	@Override
	public Connection getConnection(){
		Session session = getEntityManager().unwrap(Session.class);

		session.getTransaction().begin();
		Connection connection = session.doReturningWork(new ReturningWork<Connection>() {
			@Override
			public Connection execute(Connection conn) throws SQLException {
				return conn;
			}
		});

		return connection; 
	}

	@Override
	public List<Adhesion> FindAdhesionByPolice(Police police) {
		Query q = em.createQuery("select a from Adhesion a where a.police = :police ").setParameter("police", police);
		return q.getResultList();

	}
	
	
	
	@Override
	public List<Adhesion> propexiste(Proposition proposition) {
		Query q = em.createQuery("select a from Adhesion a where a.proposition = :proposition and a.etat = 'V' ").setParameter("proposition", proposition);
		
		return q.getResultList();
		
		

	}
	
	@Override
	public List<Adhesion> propexiste_7ayya(Proposition proposition) {
		Query q = em.createQuery("select a from Adhesion a where a.proposition = :proposition  ").setParameter("proposition", proposition);
		
		return q.getResultList();
		
		

	}

	@Override
	public Adhesion FindAdhesionByNadhesion(String nadhesion) {
		Query q = em.createQuery("select a from Adhesion a where a.nadhesion = :nadhesion ").setParameter("nadhesion", nadhesion);
		System.out.println("Adhesion**********="+(Adhesion) q.getSingleResult());
		try {
			return (Adhesion) q.getSingleResult();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}
	@Override
	public List<Client> findClientFromAdhesionByUtilisateur(List<Utilisateurs> utilisateur) {
		if(!utilisateur.isEmpty()){
			Query q = em.createQuery("select distinct a.client from Adhesion a where a.utilisateur in :inclList ");
			q.setParameter("inclList", utilisateur);
			return q.getResultList();
		}else {
			return new ArrayList<Client>();
		}


	}


	@Override
	public List<Client> findClientFromAdhesionByUtilisateurAndPolice(List<Utilisateurs> utilisateur,List<Police> listePolice) {
		if(!utilisateur.isEmpty()){
			Query q = em.createQuery("select distinct a.client from Adhesion a where a.utilisateur in :inclList and a.police in :inplcList ");
			q.setParameter("inclList", utilisateur);
			q.setParameter("inplcList", listePolice);
			return q.getResultList();
		}else {
			return new ArrayList<Client>();
		}
	}
	@Override
	public List<Adhesion> findByPolice(Utilisateurs utilisateur,Police police) {
		TypedQuery<Adhesion> query = em.createNamedQuery(
				"Adhesion.findByPolice", Adhesion.class);
		query.setParameter("idUtilisateurs", utilisateur.getIdUtilisateurs());
		query.setParameter("police", police);
		List<Adhesion> results = query.getResultList();

		return results;
	}

	/**** transfert ****/
	@Override
	public List<Adhesion> FindAdhesionByProduit(List<Police> listPolice,String annee1,String annee2) {
		Query query = em.createQuery("select a from Adhesion a where a.police in ( :listPolice ) "
				+"and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy') and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "order by a.datcreation desc") ;
		query.setParameter("listPolice", listPolice);
		query.setParameter("annee1", annee1);
		query.setParameter("annee2", annee2);

		List<Adhesion> results = query.getResultList();

		return results;

	}

	@Override
	public List<Adherent> FindAssuree(Adhesion adhesion) {
		Query query = em.createQuery("select a from Adherent a where a.adhesion = :adhesion  ") ;
		query.setParameter("adhesion", adhesion);


		List<Adherent> results = query.getResultList();

		return results;

	}
	/**** consultation ***/
	@Override
	public List<Adhesion> findByUtilisateurAndDate(Utilisateurs utilisateur,String annee1,String annee2,
			String nadhesion, String identifiant) {
//		System.out.println("dateDebut="+annee1+" dateTo="+annee2);
//		Query query = em.createQuery("select a from Adhesion a  WHERE a.idUtilisateurs = :idUtilisateurs "
//				+"and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy') and TO_DATE(:annee2, 'dd/mm/yyyy') "
//				+ "order by a.datcreation desc") ;
		
		StringBuilder jpql = new StringBuilder(
		        "select a from Adhesion a WHERE a.idUtilisateurs = :idUtilisateurs ");
		
		  if (annee1 != null && !annee1.trim().isEmpty()
				     && annee2 != null && !annee2.trim().isEmpty()) {

			  System.out.println("sql date not null");
				        jpql.append(
				            " and trunc(a.datcreation) between "
				          + "TO_DATE(:annee1, 'dd/mm/yyyy') "
				          + "and TO_DATE(:annee2, 'dd/mm/yyyy') "
				        );
				    }
		
		 if (nadhesion != null && !nadhesion.trim().isEmpty()) {
			 System.out.println("sql nadhesion not null");
		        jpql.append(" and a.nadhesion = :nadhesion ");
		    }

		    if (identifiant != null && !identifiant.trim().isEmpty()) {
		    	System.out.println("sql identifiant not null");
		        jpql.append(" and a.client.identifiant = :identifiant ");
		    }

		    jpql.append(" order by a.datcreation desc");
		    
		    Query query = em.createQuery(jpql.toString());
		    
		query.setParameter("idUtilisateurs", utilisateur.getIdUtilisateurs());

		 if (annee1 != null && !annee1.trim().isEmpty()
			     && annee2 != null && !annee2.trim().isEmpty()) {
			        query.setParameter("annee1", annee1);
			        query.setParameter("annee2", annee2);
			    }
		
		 if (nadhesion != null && !nadhesion.trim().isEmpty()) {
		        query.setParameter("nadhesion", nadhesion);
		    }

		    if (identifiant != null && !identifiant.trim().isEmpty()) {
		        query.setParameter("identifiant", identifiant);
		    }

		List<Adhesion> results = query.getResultList();


		return results;
	}

	@Override
	public List<Adhesion> findByPoliceAndDate(Utilisateurs utilisateur,Police police,String annee1,String annee2, 
			String nadhesion, String identifiant) {
//		Query query = em.createQuery("SELECT a FROM Adhesion a WHERE a.idUtilisateurs = :idUtilisateurs and a.police = :police "
//				+"and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy') and TO_DATE(:annee2, 'dd/mm/yyyy') "
//				+ "order by a.datcreation desc") ;
		
		StringBuilder jpql = new StringBuilder(
		        "SELECT a FROM Adhesion a WHERE a.idUtilisateurs = :idUtilisateurs and a.police = :police ");
		
		 if (annee1 != null && !annee1.trim().isEmpty()
			     && annee2 != null && !annee2.trim().isEmpty()) {

		  System.out.println("sql date not null");
			        jpql.append(
			            " and trunc(a.datcreation) between "
			          + "TO_DATE(:annee1, 'dd/mm/yyyy') "
			          + "and TO_DATE(:annee2, 'dd/mm/yyyy') "
			        );
			    }
	
	 if (nadhesion != null && !nadhesion.trim().isEmpty()) {
		 System.out.println("sql nadhesion not null");
	        jpql.append(" and a.nadhesion = :nadhesion ");
	    }

	    if (identifiant != null && !identifiant.trim().isEmpty()) {
	    	System.out.println("sql identifiant not null");
	        jpql.append(" and a.client.identifiant = :identifiant ");
	    }

	    jpql.append(" order by a.datcreation desc");
	    
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
		
		 if (nadhesion != null && !nadhesion.trim().isEmpty()) {
		        query.setParameter("nadhesion", nadhesion);
		    }

		    if (identifiant != null && !identifiant.trim().isEmpty()) {
		        query.setParameter("identifiant", identifiant);
		    }
		    
		List<Adhesion> results = query.getResultList();

		return results;
	}
	@Override
	public List<Adhesion> FindAdhesionForAnnulation(Utilisateurs utilisateur,Date datcreation) {
		Query query = em.createQuery("select a from Adhesion a where a.etat = 'V' and a.utilisateur = :utilisateur "
				+ "and trunc(a.datcreation) = trunc(:datcreation) "
				+ "and idAdhesion not in (select op.idAdhesion from OperationTransaction op where trunc(op.dateTrans) = trunc(:datcreation)) "
				+ "order by a.datcreation desc") ;

		query.setParameter("utilisateur", utilisateur);
		query.setParameter("datcreation", datcreation);
		List<Adhesion> results = query.getResultList();
		if(!results.isEmpty()){
			return results;
		}else{
			return null;
		}

	}

	@Override
	public int  AnnulationAdhesion(List<String> listadhesion)
	{
		String requete = "Update  Adhesion set etat='A' where nadhesion IN (:list) " ;
		int updatedEntities = em.createQuery(requete).setParameter("list", listadhesion).executeUpdate();
		return  updatedEntities ;

	}

	@Override
	public Adhesion FindMaxNadhesion(Police p) {
		Query q = em.createQuery("select a from Adhesion a  where a.nadhesion=(select max(a.nadhesion) from Adhesion a where a.police = :p)").setParameter("p", p);

		List<Adhesion> adhesion=q.getResultList();

		if(!adhesion.isEmpty()){
			return adhesion.get(0);
		}else{
			return null;
		}

	}
	
	@Override
	public String getNadhesion(String numPolice) {
	Calendar cYears = Calendar.getInstance();
	cYears.setTime(new Date());
	int year =cYears.get(Calendar.YEAR);
	Query q = em.createQuery("select max(a.nadhesion)+1 from Adhesion a where a.police.nPolice = :p and substr(a.nadhesion,1,4) = :annee").setParameter("p", numPolice).setParameter("annee", year+"");


	List<Integer> list = q.getResultList();
	        String numero="";
	        String dernierNumString="";
	        if(list!=null && !list.isEmpty()&& list.get(0)!=null){
	        System.out.println(" NUM :"+list.get(0));
	        numero= list.get(0)+"";
	        System.out.println(" NUM :"+numero);
	        }else{
	        
	        
	        System.out.println("AUCUN NUM");
	        dernierNumString="1";
	        dernierNumString=StringUtils.leftPad(dernierNumString, 6,'0');
	        numero=year+dernierNumString;
	        }
	        System.out.println("Porchain NUmero : "+numero);
	       return numero;


	}
	
	  @Override
	    public Adhesion getParbynpolice2 (String npolice) {
	        Query q = em.createQuery("SELECT a FROM Adhesion a WHERE a.nadhesion = :npolice");
	        q.setParameter("npolice", npolice);
	       return  (Adhesion) q.getSingleResult();
	     
	    }
	  
	  @Override
		public List<Adhesion> replicatenumdec(String num_decision , String ref_credit ) {
			Query q = em.createQuery("select a from Adhesion a where a.num_decision = :num_decision and a.ref_credit = :ref_credit  ");
			q.setParameter("num_decision", num_decision);
			q.setParameter("ref_credit", ref_credit);

			//System.out.println("Adhesion**********="+(Adhesion) q.getSingleResult());
			List<Adhesion> results = q.getResultList();
			if(!results.isEmpty()){
				return results;
			}else{
				return null;
			}

		}
	  
	  
/******************************* partie encaissement terme ***************************************************/	  

	@Override
	public List<Adhesion> findByUtilisateurAndDateAndPoliceAndClient(Utilisateurs utilisateur, String annee1,
			String annee2, String nadhesion, String cin, String libelleProduit) {

		   final String CODE_HAYYA = EnumCodeProduitEjb.H7AYYA.getCode();
		   final String CODE_HAYYA_QNB = EnumCodeProduitEjb.H7ayyaQNB.getCode();
		   final String CODE_AVJ = EnumCodeProduitEjb.AVENIRJENESSE.getCode();
		
		String sql = "select a from Adhesion a  WHERE a.police.codProdui.codeProduit in ('"+CODE_AVJ+"', '"+CODE_HAYYA+"', '"+CODE_HAYYA_QNB+"') and a.etat = 'V' "
				+ "and a.idUtilisateurs =  "+ utilisateur.getIdUtilisateurs()+ " ";
		
		String range = " and trunc(a.datcreation) between "
				+ "TO_DATE('"+annee1+"', 'dd/mm/yyyy') and TO_DATE('"+annee2+"', 'dd/mm/yyyy') ";
		
		System.out.println("annee1 "+annee1);
		
		if(annee1 == "" || annee1 == null ) {
			
			range = " ";
		}
		
		if(nadhesion != "") {
			
			nadhesion = " and a.nadhesion =  '" + nadhesion + "' ";
		}
		
		if(cin != "") {
			
			cin = " and a.client.identifiant =  '" + cin + "' ";
		}
		
		if(libelleProduit != "") {
			
			libelleProduit = " and a.police.codProdui.libelleProduit = '"+libelleProduit+ "' ";
		}
		
		System.out.println(sql + range + cin + nadhesion +  libelleProduit);
		Query query_ = em.createQuery("select a from Adhesion a  WHERE a.idUtilisateurs = :idUtilisateurs "
				+"and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy') and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and a.client.identifiant = :cin and a.nadhesion = :nadhesion "
				+ "and a.police.codProdui.libelleProduit = :libelleProduit") ;
		Query query = em.createQuery(sql + range + cin + nadhesion +  libelleProduit + " order by a.datcreation desc ") ;
//		query.setParameter("idUtilisateurs", utilisateur.getIdUtilisateurs());
//		query.setParameter("annee1", annee1);
//		query.setParameter("annee2", annee2);
//		query.setParameter("nadhesion", nadhesion);
//		query.setParameter("cin", cin);
//		query.setParameter("libelleProduit", libelleProduit);

		List<Adhesion> results = query.getResultList();


		return results;
	}
	
	
	/******************************* partie encaissement libre ***************************************************/	  

	@Override
	public List<Adhesion> findByUtilisateurAndDateAndPoliceAndClient_for_VL(Utilisateurs utilisateur, String annee1,
			String annee2, String nadhesion, String cin, String libelleProduit) {

		final String CODE_HAYYA = EnumCodeProduitEjb.H7AYYA.getCode();
		
		final String CODE_HAYYA_QNB = EnumCodeProduitEjb.H7ayyaQNB.getCode();
		
		final String CODE_AVJ = EnumCodeProduitEjb.AVENIRJENESSE.getCode();
		
		String sql = "select a from Adhesion a  WHERE a.police.codProdui.codeProduit in ('"+CODE_HAYYA+"', '"+CODE_HAYYA_QNB+"', '"+CODE_AVJ+"') and a.etat = 'V' "
				+ "and a.idUtilisateurs =  "+ utilisateur.getIdUtilisateurs()+ " ";
		
		String range = " and trunc(a.datcreation) between "
				+ "TO_DATE('"+annee1+"', 'dd/mm/yyyy') and TO_DATE('"+annee2+"', 'dd/mm/yyyy') ";
		
		System.out.println("annee1 "+annee1);
		
		if(annee1 == "" || annee1 == null ) {
			
			range = " ";
		}
		
		if(nadhesion != "") {
			
			nadhesion = " and a.nadhesion =  '" + nadhesion + "' ";
		}
		
		if(cin != "") {
			
			cin = " and a.client.identifiant =  '" + cin + "' ";
		}
		
		if(libelleProduit != "") {
			
			libelleProduit = " and a.police.codProdui.libelleProduit = '"+libelleProduit+ "' ";
		}
		
		System.out.println(sql + range + cin + nadhesion +  libelleProduit);
		Query query_ = em.createQuery("select a from Adhesion a  WHERE a.idUtilisateurs = :idUtilisateurs "
				+"and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy') and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and a.client.identifiant = :cin and a.nadhesion = :nadhesion "
				+ "and a.police.codProdui.libelleProduit = :libelleProduit") ;
		Query query = em.createQuery(sql + range + cin + nadhesion +  libelleProduit + " order by a.datcreation desc ") ;
//		query.setParameter("idUtilisateurs", utilisateur.getIdUtilisateurs());
//		query.setParameter("annee1", annee1);
//		query.setParameter("annee2", annee2);
//		query.setParameter("nadhesion", nadhesion);
//		query.setParameter("cin", cin);
//		query.setParameter("libelleProduit", libelleProduit);

		List<Adhesion> results = query.getResultList();


		return results;
	}	
	    

}
