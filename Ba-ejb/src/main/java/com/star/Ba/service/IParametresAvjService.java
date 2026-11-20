package com.star.Ba.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.AgeServiceBourse;
import com.star.Ba.model.Beneficiaire;
import com.star.Ba.model.Client;
import com.star.Ba.model.DetailAvj;
import com.star.Ba.model.DetailsEvolutionGarantieAVJ;
import com.star.Ba.model.DetailsGarantieAVJ;
import com.star.Ba.model.DureServiceBourse;
import com.star.Ba.model.Fractionnement;
import com.star.Ba.model.GarantieAvj;
import com.star.Ba.model.ModePaiementHayya;
import com.star.Ba.model.Police;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.TauxCroissancePrime;
import com.star.Ba.model.TauxInteret;
import com.star.Ba.model.Utilisateurs;

public interface IParametresAvjService {

//	Proposition saveProposition(Client client, Adhesion adhesion,Adherent adherent,
//			GarantieAvj garantieAvj1, GarantieAvj garantieAvj2, GarantieAvj garantieAvj3, GarantieAvj garantieAvj4, GarantieAvj garantieAvj5, 
//			Double fraisInscriptionAnnuel, Double renteMonsuelleEnCasDeces, Utilisateurs utilisateurs);



	DetailAvj create_detail_avj(BigDecimal MtBourse,
			BigDecimal AgeServiceBourse, BigDecimal DureeBourse,
			Date DatePremierBourse, BigDecimal IdTauxCroissancePrime,
			BigDecimal tauxCroissanceBourse, BigDecimal surprime, Boolean fromWs,
			BigDecimal dtranche);
	
	public  List<Fractionnement> allFractionnements();
	public List<DureServiceBourse> allDureServiceBourses();
	public List<TauxCroissancePrime> allTauxCroissancePrimes();
	public List<AgeServiceBourse> allAgeServiceBourses();
	public List<TauxInteret> allTauxInteret();
	public TauxInteret getTauxInteretByAnnee(long annee);

	
	Police getnumplcbylibelle(String cod_societ, String numplc);


	Adhesion createAdhesion(Beneficiaire beneficiaire, Adherent adherent,
			Adhesion adhesion, double fraisInscri, double rente,
			GarantieAvj garantieAvj1, GarantieAvj garantieAvj2,
			GarantieAvj garantieAvj3, GarantieAvj garantieAvj4,
			GarantieAvj garantieAvj5, Utilisateurs utilisateurs,
			List<DetailsEvolutionGarantieAVJ> detailsEvolutionGarantiesAVJ,
			List<DetailsGarantieAVJ> detailsGarantieAVJs,
			ModePaiementHayya paiementHayya);

	Proposition saveProposition(Beneficiaire beneficiaire, Adherent adherent,
			Client client, Adhesion adhesion, GarantieAvj garantieAvj1,
			GarantieAvj garantieAvj2, GarantieAvj garantieAvj3,
			GarantieAvj garantieAvj4, GarantieAvj garantieAvj5,
			Double fraisInscriptionAnnuel, Double renteMonsuelleEnCasDeces,
			Utilisateurs utilisateurs,
			List<DetailsEvolutionGarantieAVJ> detailsEvolutionGarantiesAVJ,
			List<DetailsGarantieAVJ> detailsGarantieAVJs);




}
