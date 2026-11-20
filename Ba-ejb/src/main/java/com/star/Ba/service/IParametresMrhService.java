/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.service;

import java.math.BigDecimal;
import java.util.Map;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Beneficiaire;
import com.star.Ba.model.Client;
import com.star.Ba.model.DetailMrh;
import com.star.Ba.model.GarantieMrh;
import com.star.Ba.model.NatureRisq;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Qualite;
import com.star.Ba.model.TarifMrh;
import com.star.Ba.model.Utilisateurs;

/**
 *
 * @author star
 */
public interface IParametresMrhService {
    
    public Map<String, Object> getCities();

  

    public java.util.List<com.star.Ba.model.TauxgarMrh> getTauxGarMrhByPack(java.lang.String packName);

    public com.star.Ba.model.Tauxcomm getTauxCom(java.lang.String codeProd);

    public com.star.Ba.model.ComgouvMrh getTauxGouvByGouv(java.lang.String gouv);

    public com.star.Ba.model.TarifMrh getTarifMrh();

    public com.star.Ba.model.Produit getProduitByCode(java.lang.String code);

	DetailMrh create_detail_mrh(Qualite qualite, NatureRisq natureRisq,
			BigDecimal nbrpieceless, BigDecimal nbrpiecemore,
			BigDecimal furnitureprice, BigDecimal area, String rachIn,
			String contractType, String duree,String residence,BigDecimal valsol,BigDecimal valmultim);


	Adhesion saveAdhesion(Client client, Adhesion adhesion,
			Proposition proposition, GarantieMrh garantieMrh1,
			GarantieMrh garantieMrh2, GarantieMrh garantieMrh3,
			GarantieMrh garantieMrh10, GarantieMrh garantieMrh11,
			GarantieMrh garantieMrh4, GarantieMrh garantieMrh5,
			GarantieMrh garantieMrh6, GarantieMrh garantieMrh7,
			GarantieMrh garantieMrh8, GarantieMrh garantieMrh9,
			GarantieMrh garantieMrh12, GarantieMrh garantieMrh13,
			GarantieMrh garantieMrh14, GarantieMrh garantieMrh15,
			GarantieMrh garantieMrh16,GarantieMrh garantieMrh17,
			Utilisateurs utilisateurs, long idpack);



	Proposition saveProposition(Client client, Adhesion adhesion,
			GarantieMrh garantieMrh1, GarantieMrh garantieMrh2,
			GarantieMrh garantieMrh3, GarantieMrh garantieMrh10,
			GarantieMrh garantieMrh11, GarantieMrh garantieMrh4,
			GarantieMrh garantieMrh5, GarantieMrh garantieMrh6,
			GarantieMrh garantieMrh7, GarantieMrh garantieMrh8,
			GarantieMrh garantieMrh9, GarantieMrh garantieMrh12,
			GarantieMrh garantieMrh13, GarantieMrh garantieMrh14,
			GarantieMrh garantieMrh15,GarantieMrh garantieMrh16,GarantieMrh garantieMrh17,Utilisateurs utilisateurs, long idpack);

    
//    public java.lang.String getTypeClientByCode(java.lang.String code);
    
    //
   

	//public Map<String, Object> recupererAllNaturisq();


	//public Map<String, Object> recupererAllQualite();





}
