/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.jsf;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import ws.adherent.ParseException_Exception;
import ws.adherent.WsRecupAdherentService;
import ws.adherent.WsRecupererAdherent;

/**
 *
 * @author 
 */
@ManagedBean(name = "WSdevis")
@ViewScoped
public class WSdevis implements Serializable{
    
	@ManagedProperty("#{devisBean}")
    private DevisBean devisBean;
    public WSdevis() {
    }

    
    
	public DevisBean getDevisBean() {
		return devisBean;
	}



	public void setDevisBean(DevisBean devisBean) {
		this.devisBean = devisBean;
	}



	public void rechercherDevisDepuisWS() {
		
		int  nadhesion = Integer.parseInt(devisBean.getNadhesion());
		System.out.println("nadhesion="+nadhesion);
		String ctrl = null;
		
		
		 System.out.println("***********************");
	        System.out.println("Create Web Service Client...");
	        WsRecupAdherentService service1 = new WsRecupAdherentService();
	        System.out.println("Create Web Service...");
	        WsRecupererAdherent port1 = service1.getWsRecupererAdherentPort();
	        System.out.println("Call Web Service Operation...");
	        try {
				 
				ctrl = port1.recupererAdherent(nadhesion).getIDENTIFIANT();
				devisBean.setDuree(port1.recupererAdherent(nadhesion).getDUREE());
				devisBean.setFranchise(port1.recupererAdherent(nadhesion).getFRANCHISE());
				devisBean.setTauxinteret(port1.recupererAdherent(nadhesion).getTAUXINTER());
				devisBean.setCapital(port1.recupererAdherent(nadhesion).getCAPITAL());
				
				
			} catch (ParseException_Exception e) {
				e.printStackTrace();
				ctrl = null;
				 System.out.println("ParseException_Exception ");
			}catch (Exception e){
				e.printStackTrace();
				ctrl = null;
				 System.out.println("Exception ");
				
			}
	        
	        
	        
	        if ( ctrl == null) {
				FacesMessage msg = new FacesMessage("ERREUR",
						"données inexistante ");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				FacesContext.getCurrentInstance().validationFailed();
				//cancel();
			}
		
		
	}
}
