package com.star.Ba.jsf.util;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class JsfUtil {

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }
    
    public static String messageText_user = "Mot de passe non suffisamment sécurisé, veuillez le changer."
    		+ "<br/>Le mot de passe doit contenir: "
    		+ "<br/>1-au moins une lettre minuscule "
    		+ "<br/>2-au moins une lettre majuscule "
    		+ "<br/>3-au moins un chiffre "
    		+ "<br/>4-au moins un caractère spécial "
    		+ "<br/>La langueur du mot de passe doit être au minimum 12";
    
    public static String messageText_admin = "Mot de passe non suffisamment sécurisé, veuillez le changer."
    		+ "<br/>Le mot de passe doit contenir: "
    		+ "<br/>1-au moins 5 lettres minuscules "
    		+ "<br/>2-au moins 5 lettres majuscules "
    		+ "<br/>3-au moins 5 chiffres "
    		+ "<br/>4-au moins 5 caractères spéciaux "
    		+ "<br/>La langueur du mot de passe doit être au minimum 20";
    
    
    public static void showAlertMessageForPassword() {
        String messageText = messageText_user;
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static void showAlertMessageForPasswordAdmin() {
        String messageText = messageText_admin;
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    
    public static void addMessage(Severity x, String summary, String details) {
        FacesMessage facesMsg = new FacesMessage(x, summary, details);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    public static void addWarningMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
        FacesContext.getCurrentInstance().addMessage("Warning Info", facesMsg);
    }
    public static void addSuccessMessage(String summary, String details) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, details);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    
    
    
    public static void addFatalMessage(String summary, String details) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, details);
        FacesContext.getCurrentInstance().addMessage("fatalMessage", facesMsg);
    }
    
    

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }
}
