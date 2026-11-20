package com.star.Ba.jsf.util;

import java.util.ArrayList;
import java.util.List;

import com.star.Ba.dto.Medical;

public class RapportMedical {
	
	
	public List<Medical> getMedicalList(double prime_totale, int ageAssure){
		
		
		List<Medical> medicals = new ArrayList<>();
		  
		  if(ageAssure <= 39) { //Jusqu'à 39 ans
			  
			  if(prime_totale <= 150000.000) {
				  
				  medicals.add(new Medical("FDR"));
			  }
			  
			  if(prime_totale >= 150000.001 && prime_totale <= 300000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));
			  }
			  
			  if(prime_totale >= 300000.001 && prime_totale <= 500000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));
				  medicals.add(new Medical("ECG"));
			  }
			  
			  if(prime_totale >= 500000.001 && prime_totale <= 800000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));
				  medicals.add(new Medical("Radiographie Pulmonaire"));
				  medicals.add(new Medical("ECG d'effort"));
				  medicals.add(new Medical("Questionnaire Financier"));
			  }
			  
			  if(prime_totale > 800000.000) {
				  
				  medicals.add(new Medical("Consulter le réassureur pour obtenir la liste détaillée des documents à fournir."));
				  
			  }
			  
			  
		  }else if(ageAssure >= 40 && ageAssure <= 59) { //De 40 à 59 ans
			  

			  
			  if(prime_totale <= 75000.000) {
				  
				  medicals.add(new Medical("FDR"));
			  }
			  
			  if(prime_totale >= 75000.001 && prime_totale <= 150000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));
			  }
			  
			  if(prime_totale >= 150000.001 && prime_totale <= 300000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));
				  medicals.add(new Medical("ECG"));
			  }
			  
			  if(prime_totale >= 300000.001 && prime_totale <= 500000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));					 
				  medicals.add(new Medical("ECG"));
				  medicals.add(new Medical("Radiographie Pulmonaire"));
			  }
			  
			  if(prime_totale >= 500000.001 && prime_totale <= 800000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));
				  medicals.add(new Medical("Radiographie Pulmonaire"));
				  medicals.add(new Medical("ECG d'effort"));
				  medicals.add(new Medical("Questionnaire Financier"));
			  }
			  
			  if(prime_totale > 800000.000) {
				  
				  medicals.add(new Medical("Consulter le réassureur pour obtenir la liste détaillée des documents à fournir."));
				  
			  }
			  
			  
			  
		  }else { //De 60 à 74 ans
			 
			  if(prime_totale <= 30000.000) {
				  
				  medicals.add(new Medical("FDR"));
			  }
			  
			  if(prime_totale >= 30000.001 && prime_totale <= 75000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
			  }
			  
			  if(prime_totale >= 75000.001 && prime_totale <= 150000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));
				  medicals.add(new Medical("ECG"));
			  }
			  
			  if(prime_totale >= 150000.001 && prime_totale <= 300000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));
				  medicals.add(new Medical("ECG"));
				  medicals.add(new Medical("Radiographie Pulmonaire"));
			  }
			  
			  if(prime_totale >= 300000.001 && prime_totale <= 500000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));					 
				  medicals.add(new Medical("Radiographie Pulmonaire"));
				  medicals.add(new Medical("ECG d'effort + PSA"));
			  }
			  
			  if(prime_totale >= 500000.001 && prime_totale <= 800000.000) {
				  
				  medicals.add(new Medical("FDR"));
				  medicals.add(new Medical("Rapport Médical"));
				  medicals.add(new Medical("Profil Sanguin"));
				  medicals.add(new Medical("Radiographie Pulmonaire"));
				  medicals.add(new Medical("ECG d'effort + PSA"));
				  medicals.add(new Medical("Questionnaire Financier"));
			  }
			  
			  if(prime_totale > 800000.000) {
				  
				  medicals.add(new Medical("Consulter le réassureur pour obtenir la liste détaillée des documents à fournir."));
				  
			  }				  
		
	}
		  
		  return medicals;
		  
	}

}
