package com.star.Ba.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Duree;
import com.star.Ba.model.ParamGarantieAvj;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.Societe;
import com.star.Ba.model.TarifAssist;
import com.star.Ba.model.TypeAbonnement;
import com.star.Ba.model.Utilisateurs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class AssistantVoiyagetDAOImpl extends AbstractDAO<Client> implements AssistantVoiyagetDAOInter {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;


	public AssistantVoiyagetDAOImpl() {
		super(Client.class);
	}

	public AssistantVoiyagetDAOImpl(Class<Client> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
    public Client saveOrUpdate(Client client) {
    	
    	return  getEntityManager().merge(client);
    }
    public Adhesion createAdhesion(Adhesion adhesion) {
    	Client client=em.merge(adhesion.getClient());
    	if(adhesion.getAdherents()!=null && ! adhesion.getAdherents().isEmpty()){
    		for (int i = 0; i <  adhesion.getAdherents().size(); i++) {
    			adhesion.getAdherents().get(i).setClient(client);
    			adhesion.getAdherents().get(i).setProfession(null);
    			adhesion.getAdherents().get(i).setTypeVoie(null);
    			if(adhesion.getAdherents().get(i).getDatNaiss()==null){
    				adhesion.getAdherents().get(i).setDatNaiss(client.getDatNaiss());
    			}
			}
    		 		
    	}
    	
    	
    	adhesion.setClient(client);
    	if(adhesion.getProposition()!=null){
    		adhesion.getProposition().setEtat("V");
    		adhesion.getProposition().setIdUtilisateurs(adhesion.getIdUtilisateurs());
    		Proposition proposition=em.merge(adhesion.getProposition());
    		adhesion.setProposition(proposition);
    	}
    	Adhesion newAdhesion=em.merge(adhesion);
    	newAdhesion.setNadhesion(newAdhesion.getNumeroAdhesion());
    	newAdhesion.setDatFin(adhesion.getDatFin());	
 	    return  em.merge(newAdhesion);
    }
    
    public Adhesion createAdhesionTLS(Adhesion adhesion,String numpol) {
    	Client client=em.merge(adhesion.getClient());
    for (int i = 0; i <  adhesion.getAdherents().size(); i++) {
		adhesion.getAdherents().get(i).setClient(client);
	}
    	adhesion.setClient(client);
    	if(adhesion.getProposition()!=null){
    		Proposition proposition=em.merge(adhesion.getProposition());
    		adhesion.setProposition(proposition);
    	}
    	adhesion.setNadhesion(numpol);
    	Adhesion newAdhesion=em.merge(adhesion);
	
 	    return  newAdhesion;
    }
    
    
 public Proposition createProposition(Proposition proposition) {
    	
     Client client	=	em.merge(proposition.getClient());
        
    	
      proposition.setClient(client);
    	Proposition newProposition= em.merge(proposition);
    	newProposition.setnProposition(newProposition.getNumeroProposition());
    	newProposition.setDatFin(proposition.getDatFin());

    	 em.merge(newProposition);
    	em.flush();
   return newProposition;

    }
	@Override
	public Client findByIdentifiant(String identifiant) {
		TypedQuery<Client> query = em.createNamedQuery(
				"Client.findByIdentifiant", Client.class);
		query.setParameter("identifiant", identifiant);

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			System.out.println("not found");
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<TypeAbonnement> getAllTypeAbonnement() {
		// TODO Auto-generated method stub
		return em.createQuery( "SELECT p FROM TypeAbonnement p").getResultList();
	}

	@Override
	public List<Duree> getAllDurees() {
		// TODO Auto-generated method stub
		return em.createQuery( "SELECT p FROM Duree p").getResultList();
	}

	@Override
	public Double calculePrimeAssistantVoiyage(Long duree, double typeAbonnement) {
		
		BigDecimal dure = new BigDecimal(duree);
		
		BigDecimal type=new BigDecimal(typeAbonnement);
		
		
		Query query = em.createQuery("SELECT a FROM TarifAssist a WHERE a.idDuree = :dure  and a.idTypeAbonnement = :type").setParameter("dure", dure).setParameter("type", type);
		
		 
		 List<TarifAssist> list = query.getResultList();
	        if (list == null || list.isEmpty()) {

	            return null;
	        } else {

	            return list.get(0).getPrime().doubleValue();
	        }
		
	   
	}
	
	
	@Override
    public List<Police> getPolice_by_conv(Convention convention) {
        Query q = em.createQuery("select a from Police a where a.convention = :convention").setParameter("convention", convention);
        return q.getResultList();
       
    }
		
	
	@Override
    public List<Police> getPolice_by_prod( String codProdui) {
        Query q = em.createQuery("select a from Police a where  a.codProdui = :codProdui").setParameter("codProdui", codProdui);
        return q.getResultList();
       
    }

	@Override
	public Convention getConventionBySociete(Societe societe) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT a FROM Convention a WHERE a.societe = :societe").setParameter("societe", societe);
		
		List<Convention> conventions=query.getResultList();
		if(!conventions.isEmpty()){
			return conventions.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<Police> getPolice_by_conv_prod(Convention convention,Produit codProdui) {
		 Query q;
		if(convention!=null){
			   q = em.createQuery("select a from Police a where a.convention = :convention and a.codProdui = :codProdui").setParameter("convention", convention).setParameter("codProdui", codProdui);

		}else{
			   q = em.createQuery("select a from Police a where  a.codProdui = :codProdui").setParameter("codProdui", codProdui);

		}
	        return q.getResultList();
	}

	@Override
	public List<Produit> getAllProduits() {
		// TODO Auto-generated method stub
		return em.createQuery( "SELECT p FROM Produit p").getResultList();
	}

	@Override
	public Produit getProduitByCode(String code) {
Query query = em.createQuery("SELECT a FROM Produit a WHERE a.codeProduit = :code").setParameter("code", code);
		
		List<Produit> conventions=query.getResultList();
		if(!conventions.isEmpty()){
			return conventions.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<ParamGarantieAvj> getAllGarantieAvenirJeunesses() {
		// TODO Auto-generated method stub
		return em.createQuery( "SELECT p FROM ParamGarantieAvj p").getResultList();
	}

	@Override
	public List<Profil> getAllProfiles() {
		// TODO Auto-generated method stub
		return em.createQuery( "SELECT p FROM Profil p").getResultList();
	}

	@Override
	public List<Profil> getAllChildrensProfiles(List<String> childrenCodes) {
		
	
		// TODO Auto-generated method stub
		Query    query = em.createQuery("SELECT p FROM Profil p where p.code in :inclList").setParameter("inclList", childrenCodes);
	      
	        return query.getResultList();
		
	}
	
	@Override
	public List<Profil> getAllChildrensProfilesbna() {
		
	
		// TODO Auto-generated method stub
		Query    query = em.createQuery("SELECT p FROM Profil p where p.idProfil = '11'");
	      
	        return query.getResultList();
		
	}

	@Override
	public List<Utilisateurs> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Agence> getAllAgencesBySociete(Societe societe) {
		Query query = em.createQuery("SELECT a FROM Agence a WHERE a.societe = :societe ").setParameter("societe", societe);
		return query.getResultList();
	}

	@Override
	public Agence getPrincipaleAgence(Societe societe) {
Query query = em.createQuery("SELECT a FROM Agence a WHERE a.societe = :societe and a.codeAgence = :codePrincipale").setParameter("societe", societe).setParameter("codePrincipale", "PRC");
		
		List<Agence> agences=query.getResultList();
		if(!agences.isEmpty()){
			return agences.get(0);
			
			
		}else{
			return null;
		}
	}



	@Override
	public List<Utilisateurs> getAllUsersChildren(List<Profil> codeProfile,	Agence agence, Societe societe) {
		if(agence==null&&societe==null){
			Query    query = em.createQuery("SELECT p FROM Utilisateurs p where p.profil in :inclList").setParameter("inclList", codeProfile);

			return  query.getResultList();
			}else if(agence!=null){
				Query    query = em.createQuery("SELECT p FROM Utilisateurs p where p.profil in :inclList and p.agence= :agence").setParameter("inclList", codeProfile).setParameter("agence", agence);
				return query.getResultList();
			}else {
				Query    query = em.createQuery("SELECT p FROM Utilisateurs p where p.profil in :inclList and p.agence.societe= :societe").setParameter("inclList", codeProfile).setParameter("societe", societe);
				return query.getResultList();
			}
	}

	@Override
	public void createQuittance(Quittance quittance)
	{
		em.merge(quittance);
	}
	
	 public Proposition UpdateProposition(Proposition proposition) {
		 	//*
		     Client client	=	em.merge(proposition.getClient());
		     proposition.setClient(client);
		     return em.merge(proposition);
		 }
	
}
