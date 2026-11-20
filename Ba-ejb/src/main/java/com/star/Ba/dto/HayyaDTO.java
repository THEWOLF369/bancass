package com.star.Ba.dto;

import java.util.List;

import com.star.Ba.model.BilanFinancierHayya;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.Proposition;

public class HayyaDTO {
	
	Proposition proposition;
	DetailHayya detailHayya; 
	List<DetailsGarantieHayya> detailsGarantieHayyas;
	List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas;
	
	BilanFinancierHayya bilanFinancierHayya;
	
	
	
	public Proposition getProposition() {
		return proposition;
	}
	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}
	public DetailHayya getDetailHayya() {
		return detailHayya;
	}
	public void setDetailHayya(DetailHayya detailHayya) {
		this.detailHayya = detailHayya;
	}
	public List<DetailsGarantieHayya> getDetailsGarantieHayyas() {
		return detailsGarantieHayyas;
	}
	public void setDetailsGarantieHayyas(List<DetailsGarantieHayya> detailsGarantieHayyas) {
		this.detailsGarantieHayyas = detailsGarantieHayyas;
	}
	public List<DetailsEvolutionGarantieHayya> getDetailsEvolutionGarantieHayyas() {
		return detailsEvolutionGarantieHayyas;
	}
	public void setDetailsEvolutionGarantieHayyas(List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas) {
		this.detailsEvolutionGarantieHayyas = detailsEvolutionGarantieHayyas;
	}
	public BilanFinancierHayya getBilanFinancierHayya() {
		return bilanFinancierHayya;
	}
	public void setBilanFinancierHayya(BilanFinancierHayya bilanFinancierHayya) {
		this.bilanFinancierHayya = bilanFinancierHayya;
	}
	
	
	
	
	

}
