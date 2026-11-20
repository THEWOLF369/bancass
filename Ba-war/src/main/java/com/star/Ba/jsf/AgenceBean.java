/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.jsf;

import com.star.Ba.dao.IAgenceDAO;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.AssistantVoiyagetServiceInter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author yassine
 */
@ManagedBean(name = "agenceBean")
@SessionScoped
public class AgenceBean implements Serializable{
	 @ManagedProperty("#{login.utilisateur}")
		private Utilisateurs utilisateur;
    @EJB
    private IAgenceDAO agenceDAO;
    @EJB
	private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;
    private List<Agence> items = null;
    private Agence selected;

    public AgenceBean() {
    }

    public Agence getSelected() {
        return selected;
    }

    public void setSelected(Agence selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Agence prepareCreate() {
        selected = new Agence();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, "Agence cree avec success");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, "Agence mise ajour avec success");
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, "Agence supprimer avec success");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    @ManagedProperty("#{gestionProfil}")
   	private GestionProfil gestionProfil;
    public List<Agence> getItems() {
    	System.out.println("enter agence bean");
    	
        
        	
        	System.out.println("items is null");
        	
           
       
        System.out.println("items sise="+items.size());
        return items;
    }

    
    public GestionProfil getGestionProfil() {
		return gestionProfil;
	}

	public void setGestionProfil(GestionProfil gestionProfil) {
		this.gestionProfil = gestionProfil;
	}

	
	public void createNewAgence(){
		
		if (!agenceDAO.VerifAgenceSTB(selected.getCodeAgence()).isEmpty() )
		{
			JsfUtil.addWarningMessage("agence deja existante ");
			System.out.println("agence existe deja====="+selected.getCodeAgence());
		  }
		else {
		
			 selected.setSociete(utilisateur.getAgence().getSociete());
			  agenceDAO.createNewAgence(selected);
			  JsfUtil.addSuccessMessage("agence crée avec success");
		}
	}
	 public void updateAgence(){
		  agenceDAO.createNewAgence(selected);
	 }
	
	
	
	private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    agenceDAO.edit(selected);
                } else {
                    agenceDAO.remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, "Erreur");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "Erreur");
            }
        }
    }

    public Agence getAgence(Integer id) {
        return agenceDAO.find(id);
    }

    public List<Agence> getItemsAvailableSelectMany() {
        return agenceDAO.findAll();
    }

    public List<Agence> getItemsAvailableSelectOne() {
        return agenceDAO.findAll();
    }

    @FacesConverter(forClass = Agence.class)
    public static class AgenceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AgenceBean controller = (AgenceBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "agenceBean");
            return controller.getAgence(getKey(value));
        }

        Integer getKey(String value) {
            Integer key = 0;
            try {
                key = Integer.parseInt(value);
            } catch (Exception e) {
            }
            return key;
        }

        String getStringKey(Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Agence) {
                Agence o = (Agence) object;
                return getStringKey(o.getIdAgence());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Agence.class.getName()});
                return null;
            }
        }
    }
   
    List<Agence> allAgence=new ArrayList<Agence>();

	public List<Agence> getAllAgence() {
		
		
		allAgence=serviceAssistantVoiyagetService.getAllAgencesBySociete(utilisateur.getAgence().getSociete());
		for (Agence agence : allAgence) {
			if(agence.getCodeAgence().equalsIgnoreCase("PRC")){
			allAgence.remove(agence);
			break;
			}
			
		}
		
		
		return allAgence;
	}

	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}

	
    
    
}
