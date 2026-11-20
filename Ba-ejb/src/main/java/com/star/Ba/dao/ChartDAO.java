package com.star.Ba.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agence;
import com.star.Ba.model.AgenceProduit;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Societe;
import com.star.Ba.model.Utilisateurs;



@Stateless
public class ChartDAO extends AbstractDAO<Adhesion> implements IChartDAO {


	@PersistenceContext(unitName = "oracle")
	private EntityManager em;


	@EJB
	private IAgenceDAO iagenceDAO;

	@EJB
	private ISocieteDAO iSocieteDAO;


	public ChartDAO(){

		super(Adhesion.class);
	}

	public ChartDAO(Class<Adhesion> entityClass) {
		super(entityClass);

	}


	@Override
	protected EntityManager getEntityManager() {
		return em;

	}

	@Override
	public List<Adhesion> SimplePie(Police numpolice) {
		Query q = em.createQuery("SELECT a FROM " + Adhesion.class.getName() + " a where a.police = :numpolice ");
		q.setParameter("numpolice", numpolice);
		return q.getResultList();

	}



	@Override
	public long countAdhesion(Police numpolice) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police = :numpolice and a.etat = 'V'");
		q.setParameter("numpolice", numpolice);
		return (Long) q.getSingleResult();
	}


	@Override
	public long countAdhesionYear(Police numpolice) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police = :numpolice and" +" a.etat = 'V' and"
				+ " EXTRACT(YEAR FROM a.datcreation) = EXTRACT(YEAR FROM sysdate) ");
		q.setParameter("numpolice", numpolice);
		return (Long) q.getSingleResult();
	}


	@Override
	public long countAdhesionYeargrele() {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police like '411%' and" +" a.etat = 'V' and"
				+ " EXTRACT(YEAR FROM a.datcreation) = EXTRACT(YEAR FROM sysdate) ");

		return (Long) q.getSingleResult();
	}

	@Override
	public long countAdhesionYearincendie() {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police like '182%' and" +" a.etat = 'V' and"
				+ " EXTRACT(YEAR FROM a.datcreation) = EXTRACT(YEAR FROM sysdate) ");

		return (Long) q.getSingleResult();
	}

	@Override
	public long countAdhesionYeartdbna() {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police = '601393381' and" +" a.etat = 'V' and"
				+ " EXTRACT(YEAR FROM a.datcreation) = EXTRACT(YEAR FROM sysdate) ");

		return (Long) q.getSingleResult();
	}

	@Override
	public long countAdhesionYeartdbnaparuser(int x) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police = '601393381' and" +" a.etat = 'V' and"+" a.idUtilisateurs = :x and"
				+ " EXTRACT(YEAR FROM a.datcreation) = EXTRACT(YEAR FROM sysdate) ");

		q.setParameter("x", x);

		return (Long) q.getSingleResult();
	}

	@Override
	public long countAdhesionYearincendieuser(int x) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police like '182%' and" +" a.etat = 'V' and"+" a.idUtilisateurs = :x and"
				+ " EXTRACT(YEAR FROM a.datcreation) = EXTRACT(YEAR FROM sysdate) ");

		q.setParameter("x", x);

		return (Long) q.getSingleResult();
	}

	@Override
	public long countAdhesionYeargreleuser(int x) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police like '411%' and" +" a.etat = 'V' and"+" a.idUtilisateurs = :x and"
				+ " EXTRACT(YEAR FROM a.datcreation) = EXTRACT(YEAR FROM sysdate) ");

		q.setParameter("x", x);

		return (Long) q.getSingleResult();
	}


	@Override
	public List<Object[]> SimplePieObject() {

		/*Query query = em.createQuery("SELECT pr.libelle_produit, COUNT(adhesion) FROM adhesion a JOIN a.police p JOIN p.produit pr GROUP BY pr.libelle_produit ");

		 return query.getResultList();*/
		return null;



	}

	@Override
	public List<Police> listePolice(Produit codeproduit) {
		Query q = em.createQuery("SELECT p FROM " + Police.class.getName() + " p where p.codProdui = :codeproduit");
		q.setParameter("codeproduit", codeproduit);
		return q.getResultList();
	}

	@Override
	public List<Police> listePolicebna(Produit codeproduit) {
		Query q = em.createQuery("SELECT DISTINCT p.codProdui FROM " + Police.class.getName() + " p where p.codProdui = :codeproduit");
		q.setParameter("codeproduit", codeproduit);
		return q.getResultList();
	}
	
	@Override
	public List<Police> listePolicebna2(Produit codeproduit) {
		Query q = em.createQuery("SELECT  p FROM " + Police.class.getName() + " p where p.codProdui = :codeproduit");
		q.setParameter("codeproduit", codeproduit);
		return q.getResultList();
	}


	@Override
	public List<Police> listePolicebnaagent(Produit codeproduit) {
		Query q = em.createQuery("SELECT p FROM " + Police.class.getName() + " p where p.codProdui = :codeproduit" );

		q.setParameter("codeproduit", codeproduit);
		return q.getResultList();
	}


	@Override
	public List<Police> listePoliceParConvention(Produit codeproduit,Convention convention) {
		Query q = em.createQuery("SELECT p FROM " + Police.class.getName() + " p where p.codProdui = :codeproduit and p.convention = :convention");
		q.setParameter("codeproduit", codeproduit);
		q.setParameter("convention", convention);
		if(!q.getResultList().isEmpty()){
			return q.getResultList();
		}else{
			return new ArrayList<Police>();
		}

	}

	@Override

	public List<Police> listePoliceParConventionbna(Produit codeproduit,Convention convention) {
		Query q = em.createQuery("SELECT p FROM " + Police.class.getName() + " p where p.codProdui = :codeproduit and p.convention = :convention");
		q.setParameter("codeproduit", codeproduit);
		q.setParameter("convention", convention);

		return q.setMaxResults(1).getResultList();


	}


	@Override
	public Convention avoirConvention(Societe societe) {
		Query q = em.createQuery("SELECT p FROM " + Convention.class.getName() + " p where p.societe = :societe");
		q.setParameter("societe", societe);
		return (Convention) q.getSingleResult();
	}



	@Override
	public Produit avoirProduit(long id_produit) {
		Query q = em.createQuery("SELECT p FROM Produit p where p.idProduit = :id_produit");
		q.setParameter("id_produit", id_produit);
		List<Produit> list = q.getResultList();
		if(!list.isEmpty()){

		}
		return list.get(0);

	}

	@Override
	public long countAdhesionParDate(Police numpolice,String annee1,String annee2,int num_mois) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + 
				" a where a.police = :numpolice and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and a.etat = 'V'"+" and EXTRACT(Month FROM a.datcreation) = :num_mois ");
		q.setParameter("numpolice", numpolice);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		return (Long) q.getSingleResult();
	}

	@Override
	public Agence avoirAgence(int idAgence) {
		Query q = em.createQuery("SELECT a FROM " + Agence.class.getName() + " a where a.idAgence = :idAgence");
		q.setParameter("idAgence", idAgence);
		return (Agence) q.getSingleResult();
	}




	@Override
	public List<Agence> avoirAgencebysoci() {
		Query q = em.createQuery("SELECT a FROM " + Agence.class.getName() + " a where a.societe = 'B142431996'");

		return q.getResultList();
	}

	@Override
	public Societe avoiSociete(String codSociet) {
		Query q = em.createQuery("SELECT a FROM " + Societe.class.getName() + " a where a.codSociet = :codSociet");
		q.setParameter("codSociet", codSociet);
		return (Societe) q.getSingleResult();
	}

	@Override
	public  List<Utilisateurs> listeUtilisateur(Agence agence) {
		Query q = em.createQuery("SELECT  a FROM " + Utilisateurs.class.getName() + " a where a.agence = :agence");
		q.setParameter("agence", agence);
		return q.getResultList();
	}

	@Override
	public  List<Utilisateurs> listeUtilisateurbna(Agence agence) {
		Query q = em.createQuery("SELECT  a FROM " + Utilisateurs.class.getName() + " a where a.agence = :agence");
		q.setParameter("agence", agence);
		return q.setMaxResults(1).getResultList();
	}






	@Override
	public long countAdhesionParUtilisateur(Police numpolice,
			Utilisateurs utilisateurs) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police = :numpolice "
				+"and a.etat ='V'"	+ " and a.utilisateur =  :utilisateurs");
		q.setParameter("numpolice", numpolice);
		q.setParameter("utilisateurs", utilisateurs);
		if(!q.getResultList().isEmpty()){
			return (Long) q.getSingleResult();
		}else{
			return 0;
		}
	}


	@Override
	public long countAdhesionParUtilisateurYear(Police numpolice,
			Utilisateurs utilisateurs) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police = :numpolice " +
				" and a.etat= 'V' " + " and a.utilisateur =  :utilisateurs  and EXTRACT(YEAR FROM a.datcreation) = EXTRACT(YEAR FROM sysdate) ");
		q.setParameter("numpolice", numpolice);
		q.setParameter("utilisateurs", utilisateurs);
		if(!q.getResultList().isEmpty()){
			return (Long) q.getSingleResult();
		}else{
			return 0;
		}
	}


	@Override
	public long countAdhesionParDatePArUtilisateur(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() + " a where a.police = :numpolice "+
				"and a.etat = 'V'"
				+ " and a.utilisateur =  :utilisateurs "
				+ " and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')  and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ " and EXTRACT(Month FROM a.datcreation) = :num_mois ");
		q.setParameter("numpolice", numpolice);
		q.setParameter("utilisateurs", utilisateur);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		System.out.println("utilisateur_reqtt "+utilisateur.getIdUtilisateurs());
		System.out.println("numpolice "+numpolice.getNPolice());
		System.out.println("annee1 "+annee1);
		System.out.println("annee2 "+annee2);
		System.out.println("num_mois "+num_mois);
		if(!q.getResultList().isEmpty()){
			return (Long) q.getSingleResult();
		}else{
			return 0;
		}
	}



	@Override
	public long countAdhesionParDatePArUtilisateurbna(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() +
				" a where a.police = :numpolice and  a.etat = 'V'"
				+ " and a.utilisateur =  :utilisateurs "

			 		+ " and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')  and TO_DATE(:annee2, 'dd/mm/yyyy') "
			 		+ " and EXTRACT(Month FROM a.datcreation) = :num_mois ");

		q.setParameter("numpolice", numpolice);
		q.setParameter("utilisateurs", utilisateur);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		//System.out.println("utilisateur_reqtt "+utilisateur.getIdUtilisateurs());

		//System.out.println("annee1 "+annee1);
		//System.out.println("annee2 "+annee2);
		//System.out.println("num_mois "+num_mois);
		if(!q.getResultList().isEmpty()){
			return (Long) q.getSingleResult();
		}else{
			return 0;
		}
	}
	
	
	@Override
	public long countAdhesionParDatePAragencebna(
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() +
				" a where  a.etat = 'V'"
				+ " and a.utilisateur =  :utilisateurs "

			 		+ " and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')  and TO_DATE(:annee2, 'dd/mm/yyyy') "
			 		+ " and EXTRACT(Month FROM a.datcreation) = :num_mois ");

		
		q.setParameter("utilisateurs", utilisateur);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		//System.out.println("utilisateur_reqtt "+utilisateur.getIdUtilisateurs());

		//System.out.println("annee1 "+annee1);
		//System.out.println("annee2 "+annee2);
		//System.out.println("num_mois "+num_mois);
		if(!q.getResultList().isEmpty()){
			return (Long) q.getSingleResult();
		}else{
			return 0;
		}
	}

	
	
	@Override
	public long countAdhesionParDatePArUtilisateurbna(
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		Query q = em.createQuery("SELECT COUNT(a) FROM " + Adhesion.class.getName() +
				" a where  a.etat = 'V'"
				+ " and a.utilisateur =  :utilisateurs "

			 		+ " and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')  and TO_DATE(:annee2, 'dd/mm/yyyy') "
			 		+ " and EXTRACT(Month FROM a.datcreation) = :num_mois ");

		
		q.setParameter("utilisateurs", utilisateur);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		//System.out.println("utilisateur_reqtt "+utilisateur.getIdUtilisateurs());

		//System.out.println("annee1 "+annee1);
		//System.out.println("annee2 "+annee2);
		//System.out.println("num_mois "+num_mois);
		if(!q.getResultList().isEmpty()){
			return (Long) q.getSingleResult();
		}else{
			return 0;
		}
	}




	@Override
	public Map<String, Object> recupererListeAgence(Societe societe,
			String profil) {
		Map<String, Object> listagence = new LinkedHashMap<String, Object>();
		List<Agence> list =null;

		if(profil.equals("AST")){
			list = iagenceDAO.findAll();

		}else{

			list = iagenceDAO.listeAgenceParSociete(societe);
		}


		for(Agence agence : list){
			listagence.put(agence.getCodeAgence()+": "+ agence.getLibelle(),agence.getIdAgence().toString()) ;
		}

		return listagence;
	}




	@Override
	public Map<String, Object> recupererListeSociete() {
		Map<String, Object> listsociete = new LinkedHashMap<String, Object>();
		List<Societe> list =null;

		list =  iSocieteDAO.listeSociete();

		for(Societe societe : list){
			listsociete.put( societe.getRaisonSoc(),societe.getCodSociet()) ;
		}

		return listsociete;
	}
	@Override
	public Map<String, Object> recupererListeSocietebna() {
		Map<String, Object> listsocietebna = new LinkedHashMap<String, Object>();
		List<Societe> list =null;

		list =  iSocieteDAO.listeSocietebna();

		for(Societe societe : list){
			listsocietebna.put( societe.getRaisonSoc(),societe.getCodSociet()) ;
		}

		return listsocietebna;
	}



	@Override
	public double sommeMtPrime(Police numpolice,String annee1, String annee2,int num_mois){
		Query q = em.createQuery("SELECT coalesce(SUM(a.mtPrime),0) FROM " + Adhesion.class.getName() + " a where a.police = :numpolice "
				+"and a.etat='V'"
				+ " and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ " and EXTRACT(Month FROM a.datcreation) = :num_mois ");
		q.setParameter("numpolice", numpolice);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		if(!q.getResultList().isEmpty()){
			return (Double) q.getSingleResult();
		}else{
			return 0;
		}

	}

	@Override
	public double sommeMtPrimeTot(Police numpolice, String annee1, String annee2,int num_mois) {
		Query q = em.createQuery("SELECT coalesce(SUM(a.primetot),0) FROM " + Adhesion.class.getName() + " a where a.police = :numpolice "
				+"and a.etat='V'"
				+ " and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ " and EXTRACT(Month FROM a.datcreation) = :num_mois ");
		q.setParameter("numpolice", numpolice);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);

		if(!q.getResultList().isEmpty()){
			return (Double) q.getSingleResult();
		}else{
			return 0;
		}


	}






	@Override
	public double sommeMtPrimeParDateParUtilisateur(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		Query q = em.createQuery("SELECT coalesce(SUM(a.mtPrime),0) FROM " + Adhesion.class.getName() + " a where a.police = :numpolice "
				+" and a.etat = 'V' "
				+ " and a.utilisateur =  :utilisateurs "
				+ " and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ " and EXTRACT(Month FROM a.datcreation) = :num_mois ");
		q.setParameter("numpolice", numpolice);
		q.setParameter("utilisateurs", utilisateur);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		if(!q.getResultList().isEmpty()){
			return (Double) q.getSingleResult();
		}else{
			return 0;
		}
	}



	@Override
	public double sommeMtPrimeParDateParUtilisateurbna(
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		Query q = em.createQuery("SELECT coalesce(SUM(a.mtPrime),0) FROM " + Adhesion.class.getName() + " a where a.etat = 'V' "

			 		+ " and a.utilisateur =  :utilisateurs "


			 		+ " and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
			 		+ " and EXTRACT(Month FROM a.datcreation) = :num_mois ");
		q.setParameter("utilisateurs", utilisateur);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		if(!q.getResultList().isEmpty()){
			return (Double) q.getSingleResult();
		}else{
			return 0;
		}
	}
	
	@Override
	public double sommeMtPrimeParDateParUtilisateurbna2(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		Query q = em.createQuery("SELECT coalesce(SUM(a.mtPrime),0) FROM " + Adhesion.class.getName() + " a where a.police = :numpolice and a.etat = 'V' "

			 		+ " and a.utilisateur =  :utilisateurs "


			 		+ " and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
			 		+ " and EXTRACT(Month FROM a.datcreation) = :num_mois ");
		q.setParameter("numpolice", numpolice);

		q.setParameter("utilisateurs", utilisateur);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		if(!q.getResultList().isEmpty()){
			return (Double) q.getSingleResult();
		}else{
			return 0;
		}
	}



	@Override
	public double sommeMtPrimeTotParDateParUtilisateur(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		Query q = em.createQuery("SELECT coalesce(SUM(a.primetot),0) FROM " + Adhesion.class.getName() + " a where a.police = :numpolice "
				+" and a.etat = 'V' "
				+ " and a.utilisateur =  :utilisateurs "
				+ " and trunc(a.datcreation) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ " and EXTRACT(Month FROM a.datcreation) = :num_mois ");
		q.setParameter("numpolice", numpolice);
		q.setParameter("utilisateurs", utilisateur);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);

		if(!q.getResultList().isEmpty()){
			return (Double) q.getSingleResult();
		}else{
			return 0;
		}


	}

	@Override
	public List<Object> exportProduit(int idagence,String annee1, String annee2,List<Integer> listIdProduit,int num_mois) {
		
		System.out.println("exportProduit");
		
		String sqlString= "select  U.ID_AGENCE,e.code_agence,E.LIBELLE,pr.libelle_produit,pr.ID_PRODUIT, count(pr.libelle_produit ) as nb_prd,"
				+"coalesce(sum (mt_prime),0) mt_prime,coalesce(sum (primetot),0) primetot "
				+"from adhesion a inner join police p "
				+"on a.n_police = P.n_police join produit pr on P.COD_PRODUI = PR.ID_PRODUIT "
				+"join utilisateurs u on  a.ID_UTILISATEURS = U.ID_UTILISATEURS "
				+"join agence e on U.ID_AGENCE = e.ID_AGENCE "
				+"where a.etat='V'"
				+"and trunc(a.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+"and U.ID_AGENCE = :idagence "
				+"and PR.ID_PRODUIT in ( :listIdProduit ) "
				+"and EXTRACT(Month FROM a.DATE_CREATION) = :num_mois "
				+"group by pr.libelle_produit ,pr.ID_PRODUIT,e.LIBELLE,e.code_agence,U.ID_AGENCE";
		
		
		
		String sqlString_VL= "select  U.ID_AGENCE, e.code_agence , E.LIBELLE, "
				+"pr.libelle_produit || ' Versement Libre' , pr.ID_PRODUIT, count(pr.libelle_produit ) as nb_prd, "
				+"coalesce(sum (qd.PRIME_NETTE),0) mt_prime,coalesce(sum (qd.PRIME_TOT),0) primetot "

				
				+"from adhesion a inner join police p "	       
				+"on a.n_police = p.n_police "
				+ "inner join QUITTANCE_DETAILS qd on (a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'VL')  "
				+ "inner join QUITTANCE q on (a.id_adhesion = q.id_adhesion) " 
				+"join produit pr on P.COD_PRODUI = PR.ID_PRODUIT " 
				+"join utilisateurs u on  a.ID_UTILISATEURS = u.ID_UTILISATEURS " 
				+"join agence e on u.ID_AGENCE = e.ID_AGENCE " 
				+"where qd.etat='O' "
				+"and trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy') "
		        +"and TO_DATE(:annee2, 'dd/mm/yyyy') "
		        +"and U.ID_AGENCE = :idagence "
		        
		        +"and PR.ID_PRODUIT in ( :listIdProduit ) "
				+"and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois "
				+"group by pr.libelle_produit ,pr.ID_PRODUIT,e.LIBELLE,e.code_agence,U.ID_AGENCE";
		
		
		String sqlString_ET= "select  U.ID_AGENCE, e.code_agence , E.LIBELLE, "
				+"pr.libelle_produit || ' Versement Terme' , pr.ID_PRODUIT, count(pr.libelle_produit ) as nb_prd, "
				+"coalesce(sum (qd.PRIME_NETTE),0) mt_prime,coalesce(sum (qd.PRIME_TOT),0) primetot "

				
				+"from adhesion a inner join police p "	       
				+"on a.n_police = p.n_police "
				+ "inner join QUITTANCE_DETAILS qd on (a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'ET')  "
				+ "inner join QUITTANCE_TERME q on (a.id_adhesion = q.id_adhesion) " 
				+"join produit pr on P.COD_PRODUI = PR.ID_PRODUIT " 
				+"join utilisateurs u on  a.ID_UTILISATEURS = u.ID_UTILISATEURS " 
				+"join agence e on u.ID_AGENCE = e.ID_AGENCE " 
				+"where q.etat='O' "
				+"and trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy') "
		        +"and TO_DATE(:annee2, 'dd/mm/yyyy') "
		        +"and U.ID_AGENCE = :idagence "
		        
		        +"and PR.ID_PRODUIT in ( :listIdProduit ) "
				+"and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois "
				+"group by pr.libelle_produit ,pr.ID_PRODUIT,e.LIBELLE,e.code_agence,U.ID_AGENCE";

		System.out.println(sqlString+ " union all " + sqlString_VL + " union all " + sqlString_ET);
		Query q = em.createNativeQuery(sqlString+ " union all " + sqlString_VL + " union all " + sqlString_ET);
		q.setParameter("idagence", idagence);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("listIdProduit", listIdProduit);
		q.setParameter("num_mois", num_mois);

		//		 q.unwrap(SQLQuery.class)
		//         .addScalar("idagence", IntegerType.INSTANCE)
		//         .addScalar("codeagence", StringType.INSTANCE)
		//         .addScalar("libelle", StringType.INSTANCE)
		//         .addScalar("produit", StringType.INSTANCE)
		//         .addScalar("nombre", IntegerType.INSTANCE)
		//         .addScalar("mtprime", DoubleType.INSTANCE)
		//         .addScalar("primetot", DoubleType.INSTANCE)
		//         .setResultTransformer(Transformers.aliasToBean(AgenceProduit.class));

		return q.getResultList();
	}

	@Override
	public List<Object> exportProduit(String codsociete, String annee1,
			String annee2,List<Integer> listIdProduit,int num_mois) {
		
		System.out.println("exportProduit");
		
		String sqlString= "select  U.ID_AGENCE,e.code_agence,E.LIBELLE,pr.libelle_produit,pr.ID_PRODUIT, count(pr.libelle_produit ) as nb_prd,"
				+"coalesce(sum (mt_prime),0) mt_prime,coalesce(sum (primetot),0) primetot "
				+"from adhesion a inner join police p "
				+"on a.n_police = P.n_police join produit pr on P.COD_PRODUI = PR.ID_PRODUIT "
				+"join utilisateurs u on  a.ID_UTILISATEURS = U.ID_UTILISATEURS "
				+"join agence e on U.ID_AGENCE = e.ID_AGENCE "
				+"where a.etat='V'"
				+"and trunc(a.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+"and E.COD_SOCIET = :codsociete "
				+"and PR.ID_PRODUIT in ( :listIdProduit ) "
				+"and EXTRACT(Month FROM a.DATE_CREATION) = :num_mois "
				+"group by pr.libelle_produit ,pr.ID_PRODUIT,e.LIBELLE,e.code_agence,U.ID_AGENCE";
		
		
		String sqlString_VL= "select  U.ID_AGENCE, e.code_agence , E.LIBELLE, "
				+"pr.libelle_produit || ' Versement Libre' , pr.ID_PRODUIT, count(pr.libelle_produit ) as nb_prd, "
				+"coalesce(sum (qd.PRIME_NETTE),0) mt_prime,coalesce(sum (qd.PRIME_TOT),0) primetot "

				
				+"from adhesion a inner join police p "	       
				+"on a.n_police = p.n_police "
				+ "inner join QUITTANCE_DETAILS qd on (a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'VL')  "
				+ "inner join QUITTANCE q on (a.id_adhesion = q.id_adhesion) " 
				+"join produit pr on P.COD_PRODUI = PR.ID_PRODUIT " 
				+"join utilisateurs u on  a.ID_UTILISATEURS = u.ID_UTILISATEURS " 
				+"join agence e on u.ID_AGENCE = e.ID_AGENCE " 
				+"where qd.etat='O' "
				+"and trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy') "
		        +"and TO_DATE(:annee2, 'dd/mm/yyyy') "
		        +"and e.COD_SOCIET = :codsociete "
		        +"and PR.ID_PRODUIT in ( :listIdProduit ) "
				+"and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois "
				+"group by pr.libelle_produit ,pr.ID_PRODUIT,e.LIBELLE,e.code_agence,U.ID_AGENCE";
		
		
		String sqlString_ET= "select  U.ID_AGENCE, e.code_agence , E.LIBELLE, "
				+"pr.libelle_produit || ' Versement Terme' , pr.ID_PRODUIT, count(pr.libelle_produit ) as nb_prd, "
				+"coalesce(sum (qd.PRIME_NETTE),0) mt_prime,coalesce(sum (qd.PRIME_TOT),0) primetot "

				
				+"from adhesion a inner join police p "	       
				+"on a.n_police = p.n_police "
				+ "inner join QUITTANCE_DETAILS qd on (a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'ET')  "
				+ "inner join QUITTANCE_TERME q on (a.id_adhesion = q.id_adhesion) " 
				+"join produit pr on P.COD_PRODUI = PR.ID_PRODUIT " 
				+"join utilisateurs u on  a.ID_UTILISATEURS = u.ID_UTILISATEURS " 
				+"join agence e on u.ID_AGENCE = e.ID_AGENCE " 
				+"where q.etat='O' "
				+"and trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy') "
		        +"and TO_DATE(:annee2, 'dd/mm/yyyy') "
		        +"and e.COD_SOCIET = :codsociete "
		        +"and PR.ID_PRODUIT in ( :listIdProduit ) "
				+"and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois "
				+"group by pr.libelle_produit ,pr.ID_PRODUIT,e.LIBELLE,e.code_agence,U.ID_AGENCE";
		
		System.out.println(sqlString+ " union all " + sqlString_VL + " union all " + sqlString_ET);

		Query q = em.createNativeQuery(sqlString + " union all " + sqlString_VL + " union all " + sqlString_ET);
		q.setParameter("codsociete", codsociete);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);

		q.setParameter("listIdProduit", listIdProduit);
		q.setParameter("num_mois", num_mois);
		return q.getResultList();
	}

	//extraction par periode

	@Override
//	public List<Object> exportData(String codsociete, String annee1,String annee2, String etat) {
	public List<Object> exportDataupdate(String codsociete, String annee1,String annee2) {
		
		System.out.println("exportDataupdate");
		
		String sqlString= "select  distinct e.code_agence as code_agence, cl.NCOMPTE as numero_de_compte, "
				+"p.libelle as n_contrat, "
			//+"cl.nom_client, cl.prenom_client, a.nadhesion , a.dat_debut, a.dat_fin,a.duree , a.etat, " 
			    +"a.DATE_CREATION, "
			    
			    +"case p.n_police when '685327555' then 0 "
			    + "when '686327555' then 0 "
				+ "when '692327555' then 0 "
				+ "else  a.TYPE_ABONNEMENT end as TYPE_ABONNEMENT, "
				
			    + "a.nadhesion , a.dat_debut, a.dat_fin,a.duree , a.etat, " 
				+"a.DATE_CREATION as date_annulation, "
				+"case  when p.n_police = '110101724' then a.primetot when p.n_police = '302000000' then a.primetot "
				+ "when p.n_police = '692327555' then a.primetot + davj.surprime "
				+ "when p.n_police in ('685327555', '686327555') then  "
				+ "   case dh.id_fract_hayya when 1 then a.primetot "
				+ "        when 2  then (dh.pnette ) + dh.fa "
				+ "        when 3  then (dh.pnette ) + dh.fa "
				+ "        when 4  then (dh.pnette ) + dh.fa "
				+ "   else  a.primetot end "
				+ "else a.mt_prime  end , "
               
//				+"case when t.COMMISSION is not null then t.COMMISSION else "
//                + "(case when h.commission is not null then (a.mt_prime * (h.taux / 100)) "
//                + "else ti.COMMISSION * a.MT_PRIME end) end COMMISSION "
				

                + "case when p.n_police in ( '685327555', '686327555') then "
                
				+ "   case dh.id_fract_hayya when 1 then (a.mt_prime * (h.taux / 100)) "
				+ "        when 2  then (dh.pnette ) * (h.taux / 100) "
				+ "        when 3  then (dh.pnette ) * (h.taux / 100) "
				+ "        when 4  then (dh.pnette ) * (h.taux / 100) "
				+ "   else  (a.mt_prime * (h.taux / 100)) end "
				+ " "
				+ "                when p.n_police = '692327555' then ((a.mt_prime + davj.surprime) * (p.tx_com / 100)) "
                
                + "else "
                + "  case when t.COMMISSION is not null then t.COMMISSION "
                + "  else "
                + "   case when h.commission is not null then (a.mt_prime * (h.taux / 100)) "
                + "   else ti.COMMISSION * a.MT_PRIME end "
                + "  end "
                + "end as COMMISSION, "
                
				+"case   "
				+ "when p.n_police in ('685327555', '686327555') then  "
				+ "   case dh.id_fract_hayya when 1 then 'Annuel' "
				+ "        when 2  then 'Semestriel' "
				+ "        when 3  then 'Trimestriel' "
				+ "        when 4  then 'Mensuel' "
				+ "   else  'Prime Unique' end "
				+ "when p.n_police = '692327555' then  "
				+ "   case a.FRA_POLICE when 'A' then 'Annuel' "
				+ "        when 'S'  then 'Semestriel' "
				+ "        when 'T'  then 'Trimestriel' "
				+ "        when 'M'  then 'Mensuel' "
				+ "   else  'Prime Unique' end "				
				+ "else ' '  end as fract, "                
                
                + "a.mt_prime + nvl(davj.surprime,0) as ppnette_avec_sur, nvl(davj.surprime,0) as surprime, "
                + "a.mt_prime as ppnette "
                
                +"from adhesion a inner join police p "	       
				+"on a.n_police = p.n_police " 
				+"join produit pr on P.COD_PRODUI = PR.ID_PRODUIT " 
				+"join utilisateurs u on  a.ID_UTILISATEURS = u.ID_UTILISATEURS " 
				+"join agence e on u.ID_AGENCE = e.ID_AGENCE " 
				+"join client cl on cl.id_client = a.id_client "
				+ " left join TARIF_ASSIST t on ( p.N_POLICE=t.POLICE)"
				+ " left join TARIF_INCENDIE ti on (p.n_police= ti.police)"
				+ " left join TARIF_HAYYA h on (p.n_police= h.police) "
				+ " left join detail_hayya dh on (a.id_adhesion = dh.id_adhesion) "
				+ " left join detail_avj davj on (a.id_detail_avj = davj.id_detail_avj) "
				+ ",DUREE d,TYPE_ABONNEMENT ta "
				+"where trunc(a.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy') "
		        +"and TO_DATE(:annee2, 'dd/mm/yyyy') "
		        +"and e.COD_SOCIET = :codsociete "
		        +"AND(( t.ID_DUREE = d.ID_DUREE "
				+"AND t.ID_TYPE_ABONNEMENT  = ta.ID_TYPE_ABONNEMENT "
				+"AND a.DUREE  = d.VAL_DUREE "
				+"AND a.TYPE_ABONNEMENT = t.ID_TYPE_ABONNEMENT )or t.ID_TYPE_ABONNEMENT is null or t.POLICE is null) ";
				//+"and t.VAL_TYPE_ABONNEMENT = $P{typecontrat}
		
		
		String sqlString_VL= "select  distinct e.code_agence as code_agence, cl.NCOMPTE as numero_de_compte, "
				+"p.libelle || ' Versement Libre' as n_contrat, "
			    +"q.DATE_CREATION, 0 TYPE_ABONNEMENT, a.nadhesion , a.dat_debut, a.dat_fin,a.duree , "
			    + "case  qd.etat when 'O' then 'V' "
			    + "else qd.etat end as etat, " 
				+"q.DATE_CREATION as date_annulation, "
				+"case  when p.n_police = '110101724' then a.primetot when p.n_police ='302000000' then a.primetot "
				+ "when p.n_police in ('685327555', '686327555' ) then qd.PRIME_NETTE "
				+ "when p.n_police = '692327555' then qd.PRIME_NETTE "
				
				+ "else a.mt_prime  end , "
//                +"case when t.COMMISSION is not null then t.COMMISSION else "
//                + "(case when h.commission is not null then (qd.PRIME_NETTE * (h.taux / 100)) "
//                + "else ti.COMMISSION * a.MT_PRIME end) end COMMISSION, "

                +"case  when p.n_police in ( '685327555', '686327555' ) then (qd.PRIME_NETTE * (h.taux / 100)) "
                + "when p.n_police = '692327555' then (qd.PRIME_NETTE * (p.TX_COM/100)) "
                + "else ( "
                + "case when t.COMMISSION is not null then t.COMMISSION else "
                + "ti.COMMISSION * a.MT_PRIME end "
                + ") end COMMISSION,  "
                
			
				+ " ' ' as fract, "                  
                
				+ "qd.PRIME_NETTE as ppnette_avec_sur, 0 as surprime, "
				+ "qd.PRIME_NETTE as ppnette "

				+"from adhesion a inner join police p "	       
				+"on a.n_police = p.n_police "
				
				+ "inner join QUITTANCE q on (a.id_adhesion = q.id_adhesion) " 
				
				+ "inner join QUITTANCE_DETAILS qd on (q.ID_QUITTANCE = qd.ID_QUITTANCE and qd.TYPE_QUIT = 'VL')  "
				
				+"join produit pr on P.COD_PRODUI = PR.ID_PRODUIT " 
				+"join utilisateurs u on  a.ID_UTILISATEURS = u.ID_UTILISATEURS " 
				+"join agence e on u.ID_AGENCE = e.ID_AGENCE " 
				+"join client cl on cl.id_client = a.id_client "
				+ " left join TARIF_ASSIST t on ( p.N_POLICE=t.POLICE)"
				+ " left join TARIF_INCENDIE ti on (p.n_police= ti.police)"
				+ " left join TARIF_HAYYA h on (p.n_police= h.police) "
				+ " left join detail_hayya dh on (a.id_adhesion = dh.id_adhesion) "
				+ ",DUREE d,TYPE_ABONNEMENT ta "
				+"where trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy') "
		        +"and TO_DATE(:annee2, 'dd/mm/yyyy') "
		        +"and e.COD_SOCIET = :codsociete "
		        +"AND(( t.ID_DUREE = d.ID_DUREE "
				+"AND t.ID_TYPE_ABONNEMENT  = ta.ID_TYPE_ABONNEMENT "
				+"AND a.DUREE  = d.VAL_DUREE "
				+"AND a.TYPE_ABONNEMENT = t.ID_TYPE_ABONNEMENT )or t.ID_TYPE_ABONNEMENT is null or t.POLICE is null) ";
		
		
		String sqlString_ET= "select  distinct e.code_agence as code_agence, cl.NCOMPTE as numero_de_compte, "
				+"p.libelle || ' Encaissement Terme' as n_contrat, "
			    +"q.DATE_CREATION, 0 TYPE_ABONNEMENT, a.nadhesion , q.DATE_DU, q.DATE_AU, a.duree , "
			    + "case  q.etat when 'O' then 'V' "
			    + "else q.etat end as etat, " 
				+"q.DATE_CREATION as date_annulation, "
				+"case  when p.n_police = '110101724' then a.primetot when p.n_police ='302000000' then a.primetot "
				+ "when p.n_police in ('685327555', '686327555' )then qd.PRIME_NETTE "
				+ "when p.n_police = '692327555' then qd.PRIME_NETTE "

				+ "else a.mt_prime  end , "
				
                +"case  when p.n_police in ('685327555', '686327555' ) then (qd.PRIME_NETTE * (h.taux / 100)) "
                + "when p.n_police = '692327555' then (qd.PRIME_NETTE * (p.TX_COM/100)) "
                + "else ( "
                + "case when t.COMMISSION is not null then t.COMMISSION else "
                + "ti.COMMISSION * a.MT_PRIME end "
                + ") end COMMISSION,  "
                
				
				+ "' '  as fract, "                  
                
				+ "qd.PRIME_NETTE as ppnette_avec_sur, 0 as surprime, "
				+ "qd.PRIME_NETTE as ppnette "
                
				+"from adhesion a inner join police p "	       
				+"on a.n_police = p.n_police "
				
				+ "inner join QUITTANCE_TERME q on (a.id_adhesion = q.id_adhesion) " 
				
				+ "inner join QUITTANCE_DETAILS qd on (q.ID_QUITTANCET = qd.ID_QUITTANCE and qd.TYPE_QUIT = 'ET')  "
				
				+"join produit pr on P.COD_PRODUI = PR.ID_PRODUIT " 
				+"join utilisateurs u on  a.ID_UTILISATEURS = u.ID_UTILISATEURS " 
				+"join agence e on u.ID_AGENCE = e.ID_AGENCE " 
				+"join client cl on cl.id_client = a.id_client "
				+ " left join TARIF_ASSIST t on ( p.N_POLICE=t.POLICE)"
				+ " left join TARIF_INCENDIE ti on (p.n_police= ti.police)"
				+ " left join TARIF_HAYYA h on (p.n_police= h.police) "
				+ " left join detail_hayya dh on (a.id_adhesion = dh.id_adhesion) "
				+ ",DUREE d,TYPE_ABONNEMENT ta "
				+"where trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy') "
		        +"and TO_DATE(:annee2, 'dd/mm/yyyy') "
		        +"and e.COD_SOCIET = :codsociete "
		        +"AND(( t.ID_DUREE = d.ID_DUREE "
				+"AND t.ID_TYPE_ABONNEMENT  = ta.ID_TYPE_ABONNEMENT "
				+"AND a.DUREE  = d.VAL_DUREE "
				+"AND a.TYPE_ABONNEMENT = t.ID_TYPE_ABONNEMENT )or t.ID_TYPE_ABONNEMENT is null or t.POLICE is null) ";
		
		System.out.println(sqlString+ " union all " + sqlString_VL + " union all " + sqlString_ET);

		Query q = em.createNativeQuery(sqlString + " union all " + sqlString_VL + " union all " + sqlString_ET);
		q.setParameter("codsociete", codsociete);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		//q.setParameter("etat", etat);


		return q.getResultList();
	}


	
	@Override
	public List<Object> exportData(String codsociete, String annee1,String annee2, String etat) {
		
		System.out.println("exportData");
		
		String sqlString= "select distinct  e.code_agence as code_agence, cl.NCOMPTE as numero_de_compte, "
				+"p.libelle as n_contrat, "
			//+"cl.nom_client, cl.prenom_client, a.nadhesion , a.dat_debut, a.dat_fin,a.duree , a.etat, " 
			    +"a.DATE_CREATION, a.TYPE_ABONNEMENT, a.nadhesion , a.dat_debut, a.dat_fin,a.duree , a.etat, " 
				+"a.DATE_CREATION as date_annulation, "
				+"case p.n_police when '110101724' then a.primetot when '302000000' then a.primetot else a.mt_prime  end , "
                +"case when t.COMMISSION is not null then t.COMMISSION else ti.COMMISSION * a.MT_PRIME end COMMISSION "
               
				+"from adhesion a inner join police p "	       
				+"on a.n_police = p.n_police " 
				+"join produit pr on P.COD_PRODUI = PR.ID_PRODUIT " 
				+"join utilisateurs u on  a.ID_UTILISATEURS = u.ID_UTILISATEURS " 
				+"join agence e on u.ID_AGENCE = e.ID_AGENCE " 
				+"join client cl on cl.id_client = a.id_client "
				+ " left join TARIF_ASSIST t on (t.POLICE  = p.N_POLICE)"
				+ " left join TARIF_INCENDIE ti on (p.n_police= ti.police) "
				+ ",DUREE d,TYPE_ABONNEMENT ta "
				+"where a.ETAT = :etat "
				+"and trunc(a.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy') "
		        +"and TO_DATE(:annee2, 'dd/mm/yyyy') "
		        +"and e.COD_SOCIET = :codsociete "
		        +"AND(( t.ID_DUREE = d.ID_DUREE "
				+"AND t.ID_TYPE_ABONNEMENT  = ta.ID_TYPE_ABONNEMENT "
				+"AND a.DUREE  = d.VAL_DUREE "
				+"AND a.TYPE_ABONNEMENT = t.ID_TYPE_ABONNEMENT )or t.ID_TYPE_ABONNEMENT is null) ";
				//+"and t.VAL_TYPE_ABONNEMENT = $P{typecontrat}

		Query q = em.createNativeQuery(sqlString);
		q.setParameter("codsociete", codsociete);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("etat", etat);


		return q.getResultList();
	}

	//// modif pour bna	

	@Override
	public List<Object> aficherStat(String codsociete, String annee1,
			String annee2, List<Integer> listIdProduit) {
		String sqlString= "select  U.ID_AGENCE,e.code_agence,a.DATE_CREATION,c.ncompte,E.LIBELLE,pr.libelle_produit,q.numquit, a.nadhesion as contrat,"
				+"case when a.COUT is not null then a.mt_prime + a.fga+a.COUT else a.primetot + a.fga end primetotale, (a.mt_prime* p.TX_COM )/100 TX_COM, a.DAT_DEBUT  "
				+"from adhesion a inner join police p "		       
				+"on a.n_police = P.n_police join produit pr on P.COD_PRODUI = PR.ID_PRODUIT "
				+"join utilisateurs u on  a.ID_UTILISATEURS = U.ID_UTILISATEURS "
				+"join client c on  a.ID_client = c.ID_client "
				+"join quittance q on  a.ID_adhesion = q.ID_adhesion "

				+"join agence e on U.ID_AGENCE = e.ID_AGENCE "
				+"where a.ETAT = 'V'"
				+"and trunc(a.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+"and E.COD_SOCIET = :codsociete "
				+"and PR.ID_PRODUIT in ( :listIdProduit ) ";


		Query q = em.createNativeQuery(sqlString);
		q.setParameter("codsociete", codsociete);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);

		q.setParameter("listIdProduit", listIdProduit);

		return q.getResultList();
	}

	//  +"and a.ETAT = 'v'"
	@Override
	public List<Object> aficherStat(int idagence, String annee1,
			String annee2, List<Integer> listIdProduit) {
		String sqlString= "select  U.ID_AGENCE,e.code_agence,a.DATE_CREATION,c.ncompte,E.LIBELLE,pr.libelle_produit,q.numquit, a.nadhesion as contrat,"
				+"case when a.COUT is not null then a.mt_prime + a.fga+a.COUT else a.primetot + a.fga end primetotale, (a.mt_prime* p.TX_COM )/100 TX_COM, a.DAT_DEBUT  "
				+"from adhesion a inner join police p "        
				+"on a.n_police = P.n_police join produit pr on P.COD_PRODUI = PR.ID_PRODUIT "
				+"join utilisateurs u on  a.ID_UTILISATEURS = U.ID_UTILISATEURS "
				+"join client c on  a.ID_client = c.ID_client "
				+"join quittance q on  a.ID_adhesion = q.ID_adhesion "

				+"join agence e on U.ID_AGENCE = e.ID_AGENCE "
				+"where a.ETAT = 'V'"
				+"and trunc(a.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+"and U.ID_AGENCE = :idagence "
				+"and PR.ID_PRODUIT in ( :listIdProduit ) ";

		Query q = em.createNativeQuery(sqlString);
		q.setParameter("idagence", idagence);
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);

		q.setParameter("listIdProduit", listIdProduit);

		return q.getResultList();
	}



	//// fin modif pour bna	



	
	/////////////// pour 7ayyya
	
	@Override
	public long countAdhesionParUtilisateurYear_VL(Police numpolice,
			Utilisateurs utilisateurs) {
		
		String sqlString = "SELECT COUNT(*) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'VL' and qd.etat = 'O') "
				+ "inner join QUITTANCE q on (a.id_adhesion = q.id_adhesion) "
				+ "where a.N_POLICE = :numpolice and "
				+ "a.ID_UTILISATEURS =  :utilisateurs and "
				+ "EXTRACT(YEAR FROM q.DATE_CREATION) = EXTRACT(YEAR FROM sysdate) ";
		
		Query q = em.createNativeQuery(sqlString);
		
		
		q.setParameter("numpolice", numpolice.getNPolice());
		q.setParameter("utilisateurs", utilisateurs.getIdUtilisateurs());
		if(!q.getResultList().isEmpty()){
			return ((BigDecimal) q.getSingleResult()).longValue();
		}else{
			return 0;
		}
	}
	
	@Override
	public long countAdhesionParUtilisateurYear_ET(Police numpolice,
			Utilisateurs utilisateurs) {
		
		String sqlString = "SELECT COUNT(*) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'ET' ) "
				+ "inner join QUITTANCE_TERME q on (a.id_adhesion = q.id_adhesion and q.etat = 'O') "
				+ "where a.N_POLICE = :numpolice and "
				+ "a.ID_UTILISATEURS =  :utilisateurs and "
				+ "EXTRACT(YEAR FROM q.DATE_CREATION) = EXTRACT(YEAR FROM sysdate) ";
		
		Query q = em.createNativeQuery(sqlString);
		
		
		q.setParameter("numpolice", numpolice.getNPolice());
		q.setParameter("utilisateurs", utilisateurs.getIdUtilisateurs());
		if(!q.getResultList().isEmpty()){
			return ((BigDecimal) q.getSingleResult()).longValue();
		}else{
			return 0;
		}
	}
	
	@Override
	public long countAdhesionYear_VL(Police numpolice) {
		
		
		String sqlString = "SELECT COUNT(*) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'VL' and qd.etat = 'O') "
				+ "inner join QUITTANCE q on (a.id_adhesion = q.id_adhesion) "
				+ "where a.N_POLICE = :numpolice and "
				+ "EXTRACT(YEAR FROM q.DATE_CREATION) = EXTRACT(YEAR FROM sysdate) ";
		
		Query q = em.createNativeQuery(sqlString);
		q.setParameter("numpolice", numpolice.getNPolice());
		return ((BigDecimal) q.getSingleResult()).longValue();
	}
	
	
	@Override
	public long countAdhesionYear_ET(Police numpolice) {
		
		
		String sqlString = "SELECT COUNT(*) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'ET' ) "
				+ "inner join QUITTANCE_TERME q on (a.id_adhesion = q.id_adhesion and q.etat = 'O') "
				+ "where a.N_POLICE = :numpolice and "
				+ "EXTRACT(YEAR FROM q.DATE_CREATION) = EXTRACT(YEAR FROM sysdate) ";
		
		Query q = em.createNativeQuery(sqlString);
		q.setParameter("numpolice", numpolice.getNPolice());
		return ((BigDecimal) q.getSingleResult()).longValue();
	}
	
	
	
	@Override
	public long countAdhesionParDate_VL(Police numpolice,String annee1,String annee2,int num_mois) {
		
		
		String sqlString = "SELECT COUNT(*) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'VL' and qd.etat = 'O') "
				+ "inner join QUITTANCE q on (a.id_adhesion = q.id_adhesion) "
				+ "where a.N_POLICE = :numpolice and "
				+ "trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois";
		
		Query q = em.createNativeQuery(sqlString);
		
		q.setParameter("numpolice", numpolice.getNPolice());
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		return ((BigDecimal) q.getSingleResult()).longValue();
	}
	
	
	@Override
	public long countAdhesionParDate_ET(Police numpolice,String annee1,String annee2,int num_mois) {
		
	
		String sqlString = "SELECT COUNT(*) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'ET' ) "
				+ "inner join QUITTANCE_TERME q on (a.id_adhesion = q.id_adhesion and q.etat = 'O') "
				+ "where a.N_POLICE = :numpolice and "
				+ "trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois";
		
		Query q = em.createNativeQuery(sqlString);
		
		q.setParameter("numpolice", numpolice.getNPolice());
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		return ((BigDecimal) q.getSingleResult()).longValue();
	}
	
	
	@Override
	public long countAdhesionParDatePArUtilisateur_VL(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		
		
		String sqlString = "SELECT COUNT(*) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'VL' and qd.etat = 'O') "
				+ "inner join QUITTANCE q on (a.id_adhesion = q.id_adhesion) "
				+ "where a.N_POLICE = :numpolice and "
				+ "a.ID_UTILISATEURS =  :utilisateurs and "
				+ "trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois";
		
		Query q = em.createNativeQuery(sqlString);
		
		q.setParameter("numpolice", numpolice.getNPolice());
		q.setParameter("utilisateurs", utilisateur.getIdUtilisateurs());
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		System.out.println("utilisateur_reqtt "+utilisateur.getIdUtilisateurs());
		System.out.println("numpolice "+numpolice.getNPolice());
		System.out.println("annee1 "+annee1);
		System.out.println("annee2 "+annee2);
		System.out.println("num_mois "+num_mois);
		if(!q.getResultList().isEmpty()){
			return ((BigDecimal) q.getSingleResult()).longValue();
		}else{
			return 0;
		}
	}
	
	@Override
	public long countAdhesionParDatePArUtilisateur_ET(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		
		
		
		String sqlString = "SELECT COUNT(*) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'ET' ) "
				+ "inner join QUITTANCE_TERME q on (a.id_adhesion = q.id_adhesion and q.etat = 'O') "
				+ "where a.N_POLICE = :numpolice and "
				+ "a.ID_UTILISATEURS =  :utilisateurs and "
				+ "trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois";
		
		Query q = em.createNativeQuery(sqlString);
		
		q.setParameter("numpolice", numpolice.getNPolice());
		q.setParameter("utilisateurs", utilisateur.getIdUtilisateurs());
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);
		System.out.println("utilisateur_reqtt "+utilisateur.getIdUtilisateurs());
		System.out.println("numpolice "+numpolice.getNPolice());
		System.out.println("annee1 "+annee1);
		System.out.println("annee2 "+annee2);
		System.out.println("num_mois "+num_mois);
		if(!q.getResultList().isEmpty()){
			return ((BigDecimal) q.getSingleResult()).longValue();
		}else{
			return 0;
		}
	}
	
	
	
	
	@Override
	public double sommeMtPrimeTot_VL(Police numpolice, String annee1, String annee2,int num_mois) {
		
		
		String sqlString = "SELECT coalesce(SUM(qd.PRIME_TOT),0) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'VL' and qd.etat = 'O') "
				+ "inner join QUITTANCE q on (a.id_adhesion = q.id_adhesion) "
				+ "where a.N_POLICE = :numpolice and "
				+ "trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois";
		
		Query q = em.createNativeQuery(sqlString);
		
		q.setParameter("numpolice", numpolice.getNPolice());
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);

		if(!q.getResultList().isEmpty()){
			return ((BigDecimal) q.getSingleResult()).doubleValue();
		}else{
			return 0;
		}


	}
	
	
	
	@Override
	public double sommeMtPrimeTot_ET(Police numpolice, String annee1, String annee2,int num_mois) {
		

		
		String sqlString = "SELECT coalesce(SUM(qd.PRIME_TOT),0) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'ET' ) "
				+ "inner join QUITTANCE_TERME q on (a.id_adhesion = q.id_adhesion and q.etat = 'O') "
				+ "where a.N_POLICE = :numpolice and "
				+ "trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois";
		
		Query q = em.createNativeQuery(sqlString);
		
		q.setParameter("numpolice", numpolice.getNPolice());
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);

		if(!q.getResultList().isEmpty()){
			return ((BigDecimal) q.getSingleResult()).doubleValue();
		}else{
			return 0;
		}


	}
	
	
	@Override
	public double sommeMtPrimeTotParDateParUtilisateur_VL(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
		
		
		String sqlString = "SELECT coalesce(SUM(qd.PRIME_TOT),0) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'VL' and qd.etat = 'O') "
				+ "inner join QUITTANCE q on (a.id_adhesion = q.id_adhesion) "
				+ "where a.N_POLICE = :numpolice and "
				+ "a.ID_UTILISATEURS =  :utilisateurs and "
				+ "trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois";
		
		Query q = em.createNativeQuery(sqlString);
		
		q.setParameter("numpolice", numpolice.getNPolice());
		q.setParameter("utilisateurs", utilisateur.getIdUtilisateurs());
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);

		if(!q.getResultList().isEmpty()){
			return ((BigDecimal) q.getSingleResult()).doubleValue();
		}else{
			return 0;
		}


	}
	
	
	@Override
	public double sommeMtPrimeTotParDateParUtilisateur_ET(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois) {
	
		
		String sqlString = "SELECT coalesce(SUM(qd.PRIME_TOT),0) FROM Adhesion a inner join  QUITTANCE_DETAILS qd on ( "
				+ "a.id_adhesion = qd.id_adhesion and qd.TYPE_QUIT = 'ET' ) "
				+ "inner join QUITTANCE_TERME q on (a.id_adhesion = q.id_adhesion and q.etat = 'O') "
				+ "where a.N_POLICE = :numpolice and "
				+ "a.ID_UTILISATEURS =  :utilisateurs and "
				+ "trunc(q.DATE_CREATION) between TO_DATE(:annee1, 'dd/mm/yyyy')   and TO_DATE(:annee2, 'dd/mm/yyyy') "
				+ "and EXTRACT(Month FROM q.DATE_CREATION) = :num_mois";
		
		Query q = em.createNativeQuery(sqlString);
		
		
		q.setParameter("numpolice", numpolice.getNPolice());
		q.setParameter("utilisateurs", utilisateur.getIdUtilisateurs());
		q.setParameter("annee1", annee1);
		q.setParameter("annee2", annee2);
		q.setParameter("num_mois", num_mois);

		if(!q.getResultList().isEmpty()){
			return ((BigDecimal) q.getSingleResult()).doubleValue();
		}else{
			return 0;
		}


	}


}
