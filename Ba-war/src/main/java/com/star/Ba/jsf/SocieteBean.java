package com.star.Ba.jsf;

import com.star.Ba.dao.IAgenceDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.jsf.util.JsfUtil.PersistAction;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Societe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

/**
 * @author yassine
 *
 */
@ManagedBean(name = "societeBean")
@ViewScoped
public class SocieteBean implements Serializable {

    @EJB
    private ISocieteDAO societeDAO;
    @EJB
    private IAgenceDAO agenceDAO;
    private List<Societe> items = null;
    private Societe selected;

    public SocieteBean() {
    }

    public Societe getSelected() {
        return selected;
    }

    public void setSelected(Societe selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Societe prepareCreate() {
        selected = new Societe();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Societe cree avec success");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Societe mise ajour avec success");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Societe supprimer avec success");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Societe> getItems() {
       
            items = societeDAO.findAll();
            for (Societe societe : items) {
            	
            	if(societe.getCodSociet().equalsIgnoreCase("star")){
            	items.remove(societe);
            		break;
            	}
				
			}
            
       

        return items;
    }

    
  public void  createNewSociete(){
	  Agence  agence=new Agence();
	  agence.setCodeAgence("PRC");
	  agence.setSociete(societeDAO.createNewSociete(selected));
	  agenceDAO.createNewAgence(agence);
	  JsfUtil.addSuccessMessage("Societe cree avec success");
	  
	  
  }
  public void  updateSociete(){
	 societeDAO.createNewSociete(selected);
	  
	  
  }  
    
    private void persist(PersistAction persistAction, String successMessage) {
    	
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    societeDAO.edit(selected);
                } else {
                    societeDAO.remove(selected);
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
        }}
        
        public Converter getSocieteConverter() {
        	return new Converter() {

        		@Override
        		public Object getAsObject(FacesContext context,
        				UIComponent component, String value) {
        			for (Societe obj :  getItemsAvailableSelectOne()) {
        				if (obj.getCodSociet().equals(value)) {
        					return obj;
        				}
        			}
        			return null;
        		}

        		@Override
        		public String getAsString(FacesContext context,
        				UIComponent component, Object value) {
        			return value.toString();
        		}
        	};
    }

    public Societe getSociete(java.lang.String id) {
        return societeDAO.find(id);
    }

    public List<Societe> getItemsAvailableSelectMany() {
        return societeDAO.findAll();
    }

    public List<SelectItem> getItemsAvailableSelectItem() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        
        if (getItemsAvailableSelectMany() != null) {
            for (Societe societe : getItemsAvailableSelectMany()) {
                list.add(new SelectItem(societe, societe.getRaisonSoc()));
            }
        }

        return list;
    }

    public List<Societe> getItemsAvailableSelectOne() {
        return societeDAO.findAll();
    }

    @FacesConverter(forClass = Societe.class)
    public static class SocieteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SocieteBean controller = (SocieteBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "societeBean");
            return controller.getSociete(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Societe) {
                Societe o = (Societe) object;
                return getStringKey(o.getCodSociet());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Societe.class.getName()});
                return null;
            }
        }
    }
}
