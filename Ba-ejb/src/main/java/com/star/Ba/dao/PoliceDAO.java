/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;



import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.ParseConversionEvent;

import org.apache.commons.lang3.StringUtils;

import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;


/**
 *
 * @author star
 */
@Stateless
public class PoliceDAO extends AbstractDAO<Police> implements IPoliceDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PoliceDAO() {
        super(Police.class);
    }


	@Override
    public List<Police> getPolice_by_conv(Convention convention) {
        Query q = em.createQuery("select a from Police a where a.convention = :convention").setParameter("convention", convention);
        return q.getResultList();
       
    }
	
	@Override
    public List<Police> getPolice_by_conv_prod(Convention convention, Produit codProdui) {
        Query q = em.createQuery("select a from Police a where a.convention = :convention and a.codProdui = :codProdui").setParameter("convention", convention).setParameter("codProdui", codProdui);
        return q.getResultList();
       
    }
	
	@Override
    public List<Police> getPolice_by_prod( Produit codProdui) {
        Query q = em.createQuery("select a from Police a where  a.codProdui = :codProdui").setParameter("codProdui", codProdui);
        return q.getResultList();
       
    }

	@Override
    public List<Police> getPolice_by_conv_libelle(Convention convention, String libelle) {
		System.out.println("libelle="+libelle);
        Query q = em.createQuery("select a from Police a where a.convention = :convention and a.libelle = :libelle").setParameter("convention", convention).setParameter("libelle", libelle);
        return q.getResultList();
       
    }
	
	@Override
    public List<Police> getPolice_by_lib(String libelle) {
        Query q = em.createQuery("select a from Police a where  a.libelle = :libelle").setParameter("libelle", libelle);
        return q.getResultList();
       
    }
	
	
	
	@Override
    public String getNumeroContrat( String codeProduit) {
        Query q = em.createQuery("select MAX(a.nPolice)+1 from Police a where  a.nPolice like :codeProduit ");
        q .setParameter("codeProduit",  codeProduit + "%");
       
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
        	numero=codeProduit+dernierNumString;
        }
        System.out.println("Porchain NUmero : "+numero);
       return numero;
    }
	
	@Override
	 public Police enregistrer(Police p){
		 em.merge(p);
		 em.flush();
		 return p;
	 }
}
