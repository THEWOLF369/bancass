/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.mrh.contrat;

import com.star.Ba.model.Produit;
import com.star.Ba.model.Tauxcomm;
import com.star.Ba.model.TauxgarMrh;
import com.star.Ba.mrh.pack.MRHPack;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;



public abstract class MRHContract implements Serializable {

    //Contrat
    protected String contractType;
    protected Date effDate;
    protected String clientType ;//="PA";

    //Identification du risque
    protected String quality;
    protected int nbrPieceLess;
    protected int nbrPieceMore;
    protected String city;
    protected String riskNature;
    protected double area;

    //Garenties souscrites
    protected MRHPack pack;

    public MRHContract(String contractType, Date effDate, String clientType,
            String quality, int nbrPieceLess, int nbrPieceMore, String city,
            String riskNature, double area, MRHPack pack) {

        this.contractType = contractType;
        this.effDate = effDate;
        this.clientType = clientType;
        this.quality = quality;
        this.nbrPieceLess = nbrPieceLess;
        this.nbrPieceMore = nbrPieceMore;
        this.city = city;
        this.riskNature = riskNature;
        this.area = area;
        this.pack = pack;
    }

    public double getPrimeNette(List<TauxgarMrh> tauxList,int taux) throws Exception {
        double primeNette = pack.getPrimeNette(tauxList);
System.out.println("Primenette -2="+primeNette);
System.out.println("riskNature "+riskNature);
System.out.println("riskNature 2 "+riskNature.toLowerCase());
           if (riskNature.toLowerCase().equals("appartement")) {
        	
            primeNette = primeNette * (100 - pack.getRedAppart()) / 100;
            System.out.println("Primenette -1="+primeNette);
        }

        return primeNette;
    }

    public double getCost(Produit produit) throws SQLException {
        return pack.getCost(clientType,produit);
    }

    public double getTax(double primeNette, double cost,Produit produit) throws Exception {
        return pack.getTax(primeNette, cost,produit);
    }

    public double getPrimeTotal(double primeNette, double cost, double tax) {
        return pack.getPrimeTotal(primeNette, cost, tax);
    }

    public double getCommision(double primeNette, double cost,Tauxcomm tauxComm) {
        double mtCom = 0;
       /* if (tauxComm.getCodresv().toUpperCase().equals("N")) {
            double TCom = tauxComm.getTauapp().doubleValue() + tauxComm.getTaugest().doubleValue();
            mtCom = (TCom * (primeNette + cost)) / 100;
        }*/
        return mtCom;
    }

    public abstract void generateReport(double primeNette, double tax, double cost,
            double primeTotal, String clientName ,String clientType, HttpServletResponse response);

	public abstract void generateReportAdhesion(double primeNette, double tax,
			double cost, double primeTotal, String clientName,
			String clientType, HttpServletResponse response) ;

  /*  public abstract MrhLogSimulation insertDatabase(String clientCIN, String codeAgence, double primeNette,
            double cost, double tax, double primeTotal, double monCom, String code_pack) throws SQLException;
*/
	
		
	

	
}
