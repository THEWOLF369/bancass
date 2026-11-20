package com.star.Ba.dto;

import java.util.List;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Parcelle;
import com.star.Ba.model.Police;
import com.star.Ba.model.Quittance;

public class ContratDTO {
	private Adhesion adhesion;
	private Client client;
	private Quittance quittance;
	private Police police;
	private List<Parcelle> parcelles;
	public ContratDTO() {
	}
	
	public Adhesion getAdhesion() {
		return adhesion;
	}
	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Quittance getQuittance() {
		return quittance;
	}
	public void setQuittance(Quittance quittance) {
		this.quittance = quittance;
	}
	public Police getPolice() {
		return police;
	}
	public void setPolice(Police police) {
		this.police = police;
	}
	public List<Parcelle> getParcelles() {
		return parcelles;
	}
	public void setParcelles(List<Parcelle> parcelles) {
		this.parcelles = parcelles;
	}
	
	
	
}
