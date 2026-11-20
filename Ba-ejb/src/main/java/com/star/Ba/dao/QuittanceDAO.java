/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.star.Ba.commun.EnumCodeProduitEjb;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.Utilisateurs;

/**
 *
 * @author star
 */
@Stateless
public class QuittanceDAO extends AbstractDAO<Quittance> implements IQuittanceDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuittanceDAO() {
        super(Quittance.class);
    }

    
    
    @Override
    public Quittance searchquit (int numquit) {
        Query q = em.createQuery("SELECT a FROM Quittance a WHERE a.numquit = :numquit").setParameter("numquit", numquit);
        List<Quittance> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list.get(0);
        }
    }
    
    @Override
    public Quittance searchquitByNAdhesion (String nAdhesion) {
        Query q = em.createQuery("SELECT a FROM Quittance a WHERE a.idAdhesion.nadhesion = :nAdhesion").setParameter("nAdhesion", nAdhesion);
        List<Quittance> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list.get(0);
        }
    }
    
    
    @Override
    public Quittance searchQuitContratByNAdhesion (String nAdhesion, int numquit) {
    	  Query q_ = em.createQuery("SELECT a FROM Quittance a WHERE a.idAdhesion.nadhesion = :nAdhesion").setParameter("nAdhesion", nAdhesion);
         
    	  Query q___ = em.createQuery("SELECT a FROM Quittance a WHERE a.idAdhesion.nadhesion = :nAdhesion "
    	  		+ "and a.montant = 1000").setParameter("nAdhesion", nAdhesion);
    	  
    	  Query q_u = em.createQuery("SELECT a FROM Quittance a WHERE a.idAdhesion.nadhesion = :nAdhesion "
          		+ "and a.numquit = ( "
          		+ "select min(a.numquit) from Quittance a where a.idAdhesion.nadhesion = :nAdhesion "
          		+ ") ").setParameter("nAdhesion", nAdhesion);
          
        Query q__ = em.createQuery("SELECT a FROM Quittance a WHERE a.idAdhesion.nadhesion = :nAdhesion "
        		+ "and a.DATE_CREATION = ( "
        		+ "select min(a.DATE_CREATION) from Quittance a where a.idAdhesion.nadhesion = :nAdhesion "
        		+ ") ")
        		.setParameter("nAdhesion", nAdhesion);
        
        
        Query q = em.createQuery("SELECT a FROM Quittance a WHERE a.idAdhesion.nadhesion = :nAdhesion "
          		+ "and a.numquit = :numquit " )
        		.setParameter("nAdhesion", nAdhesion)
        		.setParameter("numquit", numquit);
          
        
        List<Quittance> list = q.getResultList();
        if (list == null || list.isEmpty()) {
        	 System.out.println("is empty list for searchLogin "+nAdhesion);
            return null;
        } else {
            System.out.println("not empty list for searchLogin "+nAdhesion);
            return list.get(0);
        }
    }
    
    
    @Override
    public List<Quittance> searchQuitByManyIdQuittance (List<Long> idQuittance) {
        Query q = em.createQuery("SELECT a FROM Quittance a WHERE a.idQuittance in ("+join (",", idQuittance)+")");
        System.out.println("sql "+"SELECT a FROM Quittance a WHERE a.idQuittance in ("+join (",", idQuittance)+")");		
        List<Quittance> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            System.out.println("not empty list for Quittance");
            return list;
        }
    }
    
    
    
    @Override
    public List<Object[]> searchVLByAdhesions(List<Integer> idsUtilisateurs,
            String annee1,
            String annee2,
            String nadhesion,
            String cin,
            String libelleProduit,
            String dateEncDebut,
            String dateEncFin,
            String numQuit) {
    	
    	
        // 🔴 PROTECTION IMPORTANTE
        if (idsUtilisateurs == null || idsUtilisateurs.isEmpty()) {
            return new ArrayList<Object[]>();
        }

    	// Codes produits spécifiques
    	 final String CODE_HAYYA = EnumCodeProduitEjb.H7AYYA.getCode();
    	    final String CODE_HAYYA_QNB = EnumCodeProduitEjb.H7ayyaQNB.getCode();
    	    final String CODE_AVJ = EnumCodeProduitEjb.AVENIRJENESSE.getCode();

    	    // Requête JPQL directe
    	    String jpql = "SELECT a, d, qt " +
    	              "FROM Adhesion a, Quittance qt, QuittanceDetails d " +
    	              "WHERE qt.idAdhesion = a " +
    	              "AND d.idQuittance = qt.idQuittance " +
    	              "AND a.police.codProdui.codeProduit IN (:code1, :code2, :code3) " +
    	              "AND a.etat = 'V' " +
    	              "AND a.idUtilisateurs IN :idsUsers " +
    	              "AND d.typequit = :type";

    	    // Filtre dynamique
    	    if (annee1 != null && !annee1.equals("") && annee2 != null && !annee2.equals("")) {
    	        jpql += " AND trunc(a.datcreation) BETWEEN TO_DATE(:annee1, 'dd/mm/yyyy') AND TO_DATE(:annee2, 'dd/mm/yyyy')";
    	    }
    	    if (nadhesion != null && !nadhesion.equals("")) {
    	        jpql += " AND a.nadhesion = :nadhesion";
    	    }
    	    if (cin != null && !cin.equals("")) {
    	        jpql += " AND a.client.identifiant = :cin";
    	    }
    	    if (libelleProduit != null && !libelleProduit.equals("")) {
    	        jpql += " AND a.police.codProdui.libelleProduit = :libelleProduit";
    	    }
    	    if (dateEncDebut != null && !dateEncDebut.equals("") && dateEncFin != null && !dateEncFin.equals("")) {
    	        jpql += " AND trunc(d.dateEnc) BETWEEN TO_DATE(:dateDebut, 'dd/mm/yyyy') AND TO_DATE(:dateFin, 'dd/mm/yyyy') ";
    	    }
    	    
    	    Integer numQuitInt = null;
    	    
    	    if (numQuit != null && !numQuit.trim().equals("")) {
    	        try {
    	            numQuitInt = Integer.valueOf(numQuit.trim());
    	            jpql += " AND qt.numquit = :numQuit";
    	        } catch (NumberFormatException e) {
    	            // Si ce n'est pas un nombre → on ignore le filtre
    	        }
    	    }
    	    
    	    
    	    System.out.println("jpql " +jpql);

    	    Query q = em.createQuery(jpql);

    	    // Paramètres fixes
    	    q.setParameter("code1", CODE_HAYYA);
    	    q.setParameter("code2", CODE_HAYYA_QNB);
    	    q.setParameter("code3", CODE_AVJ);
    	    q.setParameter("idsUsers", idsUtilisateurs);
    	    q.setParameter("type", "VL");

    	    // Paramètres dynamiques
    	    if (annee1 != null && !annee1.equals("") && annee2 != null && !annee2.equals("")) {
    	        q.setParameter("annee1", annee1);
    	        q.setParameter("annee2", annee2);
    	    }
    	    if (nadhesion != null && !nadhesion.equals("")) q.setParameter("nadhesion", nadhesion);
    	    if (cin != null && !cin.equals("")) q.setParameter("cin", cin);
    	    if (libelleProduit != null && !libelleProduit.equals("")) q.setParameter("libelleProduit", libelleProduit);
    	    if (dateEncDebut != null && !dateEncDebut.equals("") && dateEncFin != null && !dateEncFin.equals("")) {
    	        q.setParameter("dateDebut", dateEncDebut);
    	        q.setParameter("dateFin", dateEncFin);
    	    }
    	 // ⚡ IMPORTANT : on ne set le paramètre que si conversion réussie
    	    if (numQuitInt != null) {
    	        q.setParameter("numQuit", numQuitInt);
    	    }

    	    // Exécution et construction des DTO
    	    List<Object[]> results = q.getResultList();

      

        return results;
    }
    
    
    
	public static String join(String separator, List<Long> values) {
	    StringBuilder sb = new StringBuilder(128);
	    int end = 0;
	    for (Long s : values) {
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
    public String FindMinNumquit(String nAdhesion) {
        Query q = em.createQuery("select min(a.numquit) from Quittance a WHERE "
        		+ "a.idAdhesion.nadhesion = :nAdhesion")
        		.setParameter("nAdhesion", nAdhesion);
		
		String numquit=String.valueOf( q.getSingleResult());
		if(!numquit.isEmpty()){
			return numquit;
		}else{
			return "0";
		}
    }
	    @Override
	    public String FindMaxNumquit() {
	        Query q = em.createQuery("select max(a.numquit) from Quittance a ");
			
			String numquit=String.valueOf( q.getSingleResult());
			if(!numquit.isEmpty()){
				return numquit;
			}else{
				return "0";
			}
	       
	    }
}
