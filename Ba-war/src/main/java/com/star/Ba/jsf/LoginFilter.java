package com.star.Ba.jsf;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {
/*
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {
			// Get the loginBean from session attribute
			Login loginBean = (Login) ((HttpServletRequest) request)
					.getSession().getAttribute("login");

			// For the first application request there is no loginBean in
			// the
			// session so user needs to log in
			// For other requests loginBean is present but we need to check
			// if user
			// has logged in successfully
			if (loginBean == null || !loginBean.isLoggedIn()) {
				String contextPath = ((HttpServletRequest) request)
						.getContextPath();
				((HttpServletResponse) response).sendRedirect(contextPath
						+ "/index.html");
				return;
			}
		}
		chain.doFilter(request, response);

	}
*/
	
	


				public void doFilter(ServletRequest request, ServletResponse response,
			            FilterChain chain) throws IOException, ServletException {
					
					System.out.println("on accede à doFilter");
			
					HttpServletRequest req = (HttpServletRequest) request;
					HttpServletResponse res = (HttpServletResponse) response;
					
					String contextPath = req.getContextPath();
					String uri = req.getRequestURI();
					
//					System.out.println("RequestURI   = " + req.getRequestURI());
//					System.out.println("ServletPath  = " + req.getServletPath());
//					System.out.println("ContextPath  = " + req.getContextPath());
//					System.out.println("RequestURL   = " + req.getRequestURL());
					
					Login loginBean = (Login) req.getSession().getAttribute("login");
					
					boolean isLoginPage = uri.contains("/index.star");
					boolean isChangePwdPage = uri.contains("/sec/updatepassword.star");
					boolean isResource = uri.contains("/javax.faces.resource/");
					
					// =========================
					// 0. JSF RESOURCES
					// =========================
					if (isResource) {
					chain.doFilter(request, response);
					return;
					}
					
					boolean isAuthenticated = loginBean != null && loginBean.isLoggedIn();
					
					System.out.println("isAuthenticated " +isAuthenticated);
//					System.out.println("loginBean " +loginBean);
//					System.out.println("loginBean.isLoggedIn() " +loginBean.isLoggedIn());
					System.out.println("isLoginPage " +isLoginPage);
//					System.out.println("loginBean.isFirstLogin() " +loginBean.isFirstLogin());
					
					// =========================
					// 1. CONNECTÉ + TENTE ACCÈS LOGIN ❌
					// =========================
					if (isAuthenticated && isLoginPage) {
					res.sendRedirect(contextPath + "/sec/accueil.star");
					return;
					}
					
					// =========================
					// 2. NON CONNECTÉ
					// =========================
					if (!isAuthenticated) {
					
					if (!isLoginPage) {
					   res.sendRedirect(contextPath + "/index.star");
					   return;
					}
					
					chain.doFilter(request, response);
					return;
					}
					
					// =========================
					// 3. COMPTE BLOQUÉ / DÉSACTIVÉ / EXPIRÉ
					// =========================
					if (loginBean.isCompteBloque()
					   || loginBean.isCompteDesactive()
					   || loginBean.isExpiredMdp()) {
					
					req.getSession().invalidate();
					res.sendRedirect(contextPath + "/index.star");
					return;
					}
					
					// =========================
					// 4. FIRST LOGIN (FORCÉ CHANGE PASSWORD)
					// =========================
					if (loginBean.isFirstLogin()) {
					
					if (!isChangePwdPage) {
					   res.sendRedirect(contextPath + "/sec/updatepassword.star");
					   return;
					}
					
					chain.doFilter(request, response);
					return;
					}
					
					// =========================
					// 5. CONNECTÉ NORMAL
					// =========================
					chain.doFilter(request, response);
			}
				
	
	
    public void doFilter__(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String requestURI = req.getRequestURI();
        
//        System.out.println("contextPath " +contextPath);
//        System.out.println("requestURI " +requestURI);

        Login loginBean = (Login) req.getSession().getAttribute("login");

        boolean isLoginPage = requestURI.contains("index") 
                              || requestURI.contains("index");

        boolean isChangePwdPage = requestURI.contains("index");
        
        System.out.println("on accede à doFilter");

        // 🔴 pas connecté
//        if (loginBean == null || loginBean.getUtilisateur() == null) {
//            if (!isLoginPage) {
//                res.sendRedirect(contextPath + "/index.html");
//                return;
//            }
//        }
		if (loginBean == null || /*!loginBean.isLoggedIn()   ||*/ loginBean.isStill_connexion()) {
			String contextPath_ = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath_
					+ "/index.html");
			return;
		}
        
        else if(loginBean != null) {
        	
        	System.out.println("loginBean != null ");
        	
            // 🔴 mot de passe expiré
             if (loginBean.isExpiredMdp()) {
            	
            	
            	System.out.println("loginBean.isExpiredMdp() " + loginBean.isExpiredMdp());
            	res.sendRedirect(contextPath + "/index.html");
            	return;

                // autoriser uniquement la page de changement
//                if (!isChangePwdPage) {
//                    res.sendRedirect(contextPath + "/index.html");
//                    return;
//                }
            }
             
             else if(loginBean.isNombreTentativeDepasser()) {
            	 System.out.println("loginBean.isNombreTentativeDepasser() " + loginBean.isNombreTentativeDepasser());
            	 res.sendRedirect(contextPath + "/index.html");
             	return;
             }
             
             else if(loginBean.isWeekMdp()) {
            	 System.out.println("loginBean.isWeekMdp() " + loginBean.isWeekMdp());
            	 res.sendRedirect(contextPath + "/index.html");
             	return;
             }
             
             else if(loginBean.isStill_connexion()) {
            	 System.out.println("loginBean.isStill_connexion() " + loginBean.isStill_connexion());
            	 res.sendRedirect(contextPath + "/index.html");
             	return;
             }

            // 🔴 connecté mais essaie d'accéder login
            else if (loginBean.isLoggedIn() && isLoginPage) {
            	
            	System.out.println("loginBean.isLoggedIn() && isLoginPage " );
            	
            	if(loginBean.isFirstLogin()) {
            		res.sendRedirect(contextPath + "/updatepassword.html");
            	}else {
            		res.sendRedirect(contextPath + "/accueil.html");
            	}
                
                return;
            }
        	
        }



        chain.doFilter(request, response);
    }

	public void init(FilterConfig config) throws ServletException {
		// Nothing to do here!
	}

	public void destroy() {
		// Nothing to do here!
	}

}
