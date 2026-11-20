


package com.star.Ba.jsf;


import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;






@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private String username = "";
	private String password = "";
	private boolean rememberMe = false;
	private boolean loggedIn = false;

	public String doLogin() throws IOException, ServletException {
		System.out.println("Enter*********************");
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_security_check");
          
		dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
		ServletResponse response = (ServletResponse) context.getResponse()  ;
		
		//	((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL("http://localhost:8088/Ba/sec/accueil.xhtml"));
		
		return "succes";
	}
	public String logout() 
	   { 
	       FacesContext context = FacesContext.getCurrentInstance(); 
	      
	       // clear Session for this user 
	       context.getExternalContext().getSessionMap().clear(); 
	
	       // invalidate User ticket 
	 
	
	       return "logout"; 
	    } 
	
	@PostConstruct
	@SuppressWarnings("unused")
	private void handleErrorMessage() {
//		Exception e = (Exception) FacesContext
//				.getCurrentInstance()
//				.getExternalContext()
//				.getSessionMap()
//				.get(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
//
//		if (e instanceof BadCredentialsException) {
//			FacesContext
//					.getCurrentInstance()
//					.getExternalContext()
//					.getSessionMap()
//					.put(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY,
//							null);
//			FacesContext.getCurrentInstance().addMessage(
//					null,
//					new FacesMessage(FacesMessage.SEVERITY_ERROR,
//							getLocale(), null));
//		
//		}
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return this.rememberMe;
	}

	public void setRememberMe(final boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public boolean isLoggedIn() {
		return this.loggedIn;
	}

	public void setLoggedIn(final boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
//	public boolean isAuthenticated(){
//		
//		return !"roleAnonymous".equals(SecurityContextHolder.getContext().getAuthentication().getName());
//	}
	public String getLocale() {
		
			
		Locale locale;
		try {
			locale = FacesContext.getCurrentInstance().getViewRoot()
					.getLocale();
		} catch (Exception e) {
			locale = new Locale("fr");
		}

		ResourceBundle tbundle = ResourceBundle.getBundle("locale", locale,
				getClass().getClassLoader());
		
		return tbundle.getString("errorLogin");
	}
}
