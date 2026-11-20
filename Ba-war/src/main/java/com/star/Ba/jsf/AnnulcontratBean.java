package com.star.Ba.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.DualListModel;

import javax.faces.bean.ViewScoped;

import com.star.Ba.dao.AdhesionDAO;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Utilisateurs;

import javax.ejb.EJB;
/**
 *
 * @author yassine
 */
@ManagedBean(name = "annulcontratBean")
@ViewScoped
public class AnnulcontratBean implements Serializable {

private DualListModel<String> adhesions;
	
	@EJB
	private IAdhesionDAO iAdhesionDAO;
	
	@ManagedProperty(value = "#{login.utilisateur}")
    private Utilisateurs utilisateur;
	List<String> adhesionsSource = new ArrayList<String>();
    List<String> adhesionsTarget = new ArrayList<String>();
    
    public void initialiseList(){
    	
    	
    	
    	
    	adhesionsSource.clear();
    	 List<Adhesion> list= iAdhesionDAO.FindAdhesionForAnnulation(utilisateur, new Date());
    	 if(list!=null){
    	 System.out.println("list="+list);
         for(Adhesion adhesion : list){
      	   adhesionsSource.add(adhesion.getNadhesion());   
         }
    	 }
    	 else
    	 {
    		 adhesionsSource = new ArrayList<String>();
    	 }
         adhesions = new DualListModel<String>(adhesionsSource, adhesionsTarget);
    }
    @PostConstruct
   public void init() {
    	initialiseList();
       
      
            
   }
    
    
    public void Annuler(){
    	if(!adhesions.getTarget().isEmpty()){
    	int result = iAdhesionDAO.AnnulationAdhesion(adhesions.getTarget());
    	adhesionsTarget = new ArrayList<String>();
    	//adhesionsSource.clear();
    	initialiseList();
    	if(result>=1){
    		JsfUtil.addSuccessMessage("Adhésion(s) Annulée(s) avec succès ","!");
    	}
    	}
    }
   
   
    public DualListModel<String> getadhesions() {
       return adhesions;
   }

   public void setadhesions(DualListModel<String> adhesions) {
       this.adhesions = adhesions;
   }
   
    

   public Utilisateurs getUtilisateur() {
       return utilisateur;
   }

   public void setUtilisateur(Utilisateurs utilisateur) {
       this.utilisateur = utilisateur;
   }


public List<String> getAdhesionsTarget() {
	return adhesionsTarget;
}


public void setAdhesionsTarget(List<String> adhesionsTarget) {
	this.adhesionsTarget = adhesionsTarget;
}

   
    }

   

//}
