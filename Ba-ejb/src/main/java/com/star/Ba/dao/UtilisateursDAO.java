/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dto.UtilisateursConnexionDTO;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Menu;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Utilisateurs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author star
 */
@Stateless
public class UtilisateursDAO extends AbstractDAO<Utilisateurs> implements IUtilisateursDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateursDAO() {
        super(Utilisateurs.class);
    }

    @Override
    public UtilisateursConnexionDTO connexion(String login, String password) {
        System.out.println("Login=xxxxxxxxxxxxxxxxxxxxxxx " + login + "   password=  " + password);
        Query q = em.createQuery("SELECT a FROM Utilisateurs a WHERE a.login = :login AND a.password = :password").setParameter("login", login).setParameter("password", password);
        List<Utilisateurs> list = q.getResultList();
        if (list == null || list.isEmpty()) {
        	
        	 System.out.println("cet utilisateur n'existe pas ");

            return null;
        } else {
        	
        	
        	 System.out.println("utilisateur existe iciiiiiiiiiii");
        	
        	Utilisateurs user =list.get(0);
        	
        	List<Convention> conventions= user.getAgence().getSociete().getConventions();
        	
        	List<Menu> menus= user.getProfil().getMenuList();
        	
        	List<String> liens = new ArrayList<String>();
        	if(menus!=null){
        		for (Menu menu : menus) {
					liens.add(menu.getUrl());
				}
        	}
        	
        	UtilisateursConnexionDTO utilisateursConnexionDTO= new UtilisateursConnexionDTO();
        	utilisateursConnexionDTO.setUser(user);
        	utilisateursConnexionDTO.setConventions(conventions);
        	utilisateursConnexionDTO.setMenu(menus);
        	utilisateursConnexionDTO.setLiensAutorises(liens);
            return utilisateursConnexionDTO;
        }
    }
  
    @Override
    public Utilisateurs searchLogin(String login) {
        Query q = em.createQuery("SELECT a FROM Utilisateurs a WHERE a.login = :login").setParameter("login", login);
        List<Utilisateurs> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list.get(0);
        }
    }
    
    
    
    @Override
    public Utilisateurs searchLoginMdp(String login, String password) {
        Query q = em.createQuery("SELECT a FROM Utilisateurs a WHERE a.login = :login AND a.password = :password")
        		.setParameter("login", login)
        		.setParameter("password", password);
        List<Utilisateurs> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list.get(0);
        }
    }
    
    
    @Override
    public Utilisateurs searchmail(String mail) {
        Query q = em.createQuery("SELECT a FROM Utilisateurs a WHERE LOWER(a.mail) = LOWER(:mail)").setParameter("mail", mail.toLowerCase());
        List<Utilisateurs> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
       //     System.out.println("not empty list for searchLogin");
            return list.get(0);
        }
    }

    @Override
    public void remove(Utilisateurs utilisateur) {
        Query query = em.createNamedQuery("Utilisateurs.delete");
        query.setParameter("idUtilisateurs", utilisateur.getIdUtilisateurs());

        query.executeUpdate();
    }
    @Override
    public  List<Utilisateurs> listeUtilisateur(Agence agence) {
		Query q = em.createQuery("SELECT a FROM " + Utilisateurs.class.getName() + " a where a.agence = :agence");
		q.setParameter("agence", agence);
		return q.getResultList();
}

    @Override
    public  List<Utilisateurs> find_utilisateurs_by_profil(Profil profil)
    {
    	Query q = em.createQuery("SELECT a FROM " + Utilisateurs.class.getName() + " a where a.profil = :profil");
		q.setParameter("profil", profil);
		return q.getResultList();
    }

    
    
    @Override
    public  List<Utilisateurs> find_utilisateurs_bna()
    {
    	Query q = em.createQuery("SELECT a FROM " + Utilisateurs.class.getName() + " a where a.profil = 11");
		//q.setParameter("profil", profil);
		return q.getResultList();
    }
    
    
    @Override
    public  List<Utilisateurs> find_utilisateurs_all_bna()
    {
    	Query q = em.createQuery("SELECT a FROM " + Utilisateurs.class.getName() + " a where a.profil in(11, 12,13,14)");
		//q.setParameter("profil", profil);
		return q.getResultList();
    }
    
    @Override
    public  List<Utilisateurs> find_admin_bna()
    {
    	Query q = em.createQuery("SELECT a FROM " + Utilisateurs.class.getName() + " a where a.profil = 12 and a.login = adminbna");
		//q.setParameter("profil", profil);
		return q.getResultList();
    }
    
    
    @Override
    public  Utilisateurs createUser(Utilisateurs utilisateurs) {
		
		return  em.merge(utilisateurs);


    }
    

    
 



}
