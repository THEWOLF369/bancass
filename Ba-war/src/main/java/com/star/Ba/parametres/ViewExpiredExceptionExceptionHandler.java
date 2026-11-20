/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.parametres;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.jsf.util.WriteExceptionToFile;
import com.star.Ba.model.Utilisateurs;





//@ManagedBean
//@SessionScoped
public class ViewExpiredExceptionExceptionHandler extends
ExceptionHandlerWrapper {
//	@ManagedProperty("#{login.utilisateur}")
//	private Utilisateurs utilisateur;
    private ExceptionHandler wrapped;
static String  DIRECTORY=System.getProperty("user.home");
    ViewExpiredExceptionExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context
                    = (ExceptionQueuedEventContext) event.getSource();

            // get the exception from context
            Throwable t = context.getException();

            final FacesContext fc = FacesContext.getCurrentInstance();
            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
            final NavigationHandler nav = fc.getApplication().getNavigationHandler();

            //here you do what ever you want with exception
            try {

                //log error ?
                //log.log(Level.SEVERE, "Critical Exception!", t);
                if (t instanceof ViewExpiredException) {
                	System.out.println("Exception type= ViewExpired");
                    requestMap.put("javax.servlet.error.message", "Session expired, try again!");
                    String errorPageLocation = "/sec/viewExperedErro.xhtml";
                    fc.setViewRoot(fc.getApplication().getViewHandler().createView(fc, errorPageLocation));
                    fc.getPartialViewContext().setRenderAll(true);
                    fc.renderResponse();
                } else{
                	
                    requestMap.put("ERROR", "ERROR!");
                    System.out.println("Exception type="+t.getMessage());
                    String errorPageLocation = "/sec/Erro.xhtml";
                    fc.setViewRoot(fc.getApplication().getViewHandler().createView(fc, errorPageLocation));
                    fc.getPartialViewContext().setRenderAll(true);
                    fc.renderResponse();
                    WriteExceptionToFile.createExceptionTraceToFile(DIRECTORY, "STB",t.getMessage());
                }

                fc.renderResponse();
                // remove the comment below if you want to report the error in a jsf error message
                JsfUtil.addErrorMessage(t.getMessage());
            } finally {
                //remove it from queue
                i.remove();
            }
        }
        //parent hanle
        getWrapped().handle();
    }

}
