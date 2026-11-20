package com.star.Ba.Agricole;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

///import org.primefaces.context.RequestContext;

import com.star.Ba.dto.PackPoliceDTO;
import com.star.Ba.service.IParametreAgricoleService;

@ManagedBean(name = "packAnnulationBean")
@ViewScoped
public class AnnulationPack implements Serializable{

	@EJB
	private IParametreAgricoleService service;
	private String numPolice;
	private PackPoliceDTO packPoliceDTO;
	private boolean confirmation = false;

	public AnnulationPack() {
	}

	public void annuler() {
		service.annulerPackPolice(packPoliceDTO);

		if (confirmation = true) {
			
			FacesContext.getCurrentInstance().addMessage(
					"growl",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: "
							+ "Annulation effectuée ", ""));
			
			
		}
	}

	public void chercher() {
		packPoliceDTO = service.chercherPackPolice(numPolice);
		System.out.println("chercher msg: "+packPoliceDTO .getMsg());
		if (packPoliceDTO.getEtat() == 2) {
			FacesContext.getCurrentInstance().addMessage(
					"msgs",
					new FacesMessage(FacesMessage.SEVERITY_WARN, packPoliceDTO
							.getMsg(), ""));
			confirmation = false;
			
		} else {
			FacesContext.getCurrentInstance().addMessage(
					"msgs",
					new FacesMessage(FacesMessage.SEVERITY_INFO, packPoliceDTO
							.getMsg(), ""));
			confirmation = true;		
			
		}
		
		
		
	}
	
	

	public String getNumPolice() {
		return numPolice;
	}

	public void setNumPolice(String numPolice) {
		this.numPolice = numPolice;
	}

	public boolean isConfirmation() {
		return confirmation;
	}
	
	public PackPoliceDTO getPackPoliceDTO() {
		return packPoliceDTO;
	}

}
