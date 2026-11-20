//package com.star.Ba.jsf.util;
//
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//
//import org.primefaces.util.ComponentTraversalUtils;
//
//public class ComponentUtils {
//	
//	
//	public static String findComponentClientId(String id) {
//		  FacesContext facesContext = FacesContext.getCurrentInstance();
//		  UIComponent component = ComponentTraversalUtils.firstWithId(id, facesContext.getViewRoot());
//		  return component.getClientId(facesContext);
//		}
//
//}
