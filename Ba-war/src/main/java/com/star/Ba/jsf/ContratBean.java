package com.star.Ba.jsf;

import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.jsf.util.JsfUtil.PersistAction;
import com.star.Ba.model.Police;
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
@ManagedBean(name = "contratBean")
@ViewScoped
public class ContratBean implements Serializable {

    @EJB
    private IPoliceDAO policeDAO;
    private List<Police> items = null;
    private Police selected;

    public ContratBean() {
    }

    public Police getSelected() {
        return selected;
    }

    public void setSelected(Police selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Police prepareCreate() {
        selected = new Police();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Contrat cree avec success");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Contrat mise ajour avec success");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Contrat supprimer avec success");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Police> getItems() {
        if (items == null) {
            items = policeDAO.findAll();
        }

        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                	policeDAO.edit(selected);
                } else {
                	policeDAO.remove(selected);
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

    public Police getSociete(java.lang.String id) {
        return policeDAO.find(id);
    }

    public List<Police> getItemsAvailableSelectMany() {
        return policeDAO.findAll();
    }

    public List<SelectItem> getItemsAvailableSelectItem() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        
        if (getItemsAvailableSelectMany() != null) {
            for (Police police : getItemsAvailableSelectMany()) {
                list.add(new SelectItem(police, police.getNPolice()));
            }
        }

        return list;
    }

    public List<Police> getItemsAvailableSelectOne() {
        return policeDAO.findAll();
    }

    @FacesConverter(forClass = Police.class)
    public static class SocieteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SocieteBean controller = (SocieteBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contratBean");
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
            if (object instanceof Police) {
            	Police o = (Police) object;
                return getStringKey(o.getNPolice());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Police.class.getName()});
                return null;
            }
        }
    }
}
