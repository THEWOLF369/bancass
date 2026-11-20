package com.star.Ba.jsf;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.star.Ba.dao.IAppConfigDAO;
import com.star.Ba.model.AppConfig;
import com.star.Ba.parametres.AppConfigService;

import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "appConfigController")
@ViewScoped
public class AppConfigController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
    private IAppConfigDAO appConfigDAO;
	
    @EJB
    private AppConfigService configService;

    private List<AppConfig> configs;
    private AppConfig selectedConfig;

    @PostConstruct
    public void init() {
        configs = appConfigDAO.findAll(); // méthode de AbstractDAO
    }

    public void onEdit(AppConfig config) {
    	try {
        appConfigDAO.edit(config);
     // 🔥 IMPORTANT : refresh cache global
        configService.refresh();
        
     // ✅ Message succès
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Succès",
                "Configuration mise à jour"));
    	 } catch (Exception e) {

    	        // ❌ Message erreur
    	        FacesContext.getCurrentInstance().addMessage(null,
    	            new FacesMessage(FacesMessage.SEVERITY_ERROR,
    	                "Erreur",
    	                "Échec de la mise à jour"));

    	        e.printStackTrace();
    	    }
    }

    public void save() {
    	 try {
        for (AppConfig c : configs) {
            appConfigDAO.edit(c);
        }
     // 🔥 refresh après save
        configService.refresh();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Succès",
                    "Toutes les configurations ont été sauvegardées"));

        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erreur",
                    "Erreur lors de la sauvegarde"));

            e.printStackTrace();
        }
    }
    
    
    public void reload() {
        configs = appConfigDAO.findAll();   // recharge DB
        configService.refresh();            // recharge cache

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage("Succès", "Données rechargées"));
    }
    
    public void forceRefreshCache() {
        configService.refreshWithLog();

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Succès",
                "Cache global rechargé"));
    }

    // getters & setters

    public List<AppConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<AppConfig> configs) {
        this.configs = configs;
    }

    public AppConfig getSelectedConfig() {
        return selectedConfig;
    }

    public void setSelectedConfig(AppConfig selectedConfig) {
        this.selectedConfig = selectedConfig;
    }
}
