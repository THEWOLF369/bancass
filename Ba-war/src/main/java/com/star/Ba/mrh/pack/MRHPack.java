/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.mrh.pack;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.star.Ba.model.Produit;
import com.star.Ba.model.TarifMrh;
import com.star.Ba.model.TauxgarMrh;
import com.star.Ba.mrh.MRHParamsDAO;


public abstract class MRHPack implements Serializable {

    protected boolean redemption;
    protected int nbrPiece;
    protected double furniturePrice;
    protected ArrayList<String> baseGar;
    protected ArrayList<OpGar> opGar;
    

    /**** proposition  *****/
    protected double primeNetteRC =0;
    protected double primeNetteInc=0;
    protected double  primeNetteGemp=0;
    protected double primeNetteVol =0;
    protected double primeNetteAssh=0;
    
    
    protected double primeNetteDomElec = 0;
    protected double primeNetteEvenClim = 0;
    protected double primeNetteDeg = 0;
    protected double primeNetteBris = 0;
    
    
    
    
    
    protected double primeNetteObjval =0 ;
    protected double primeNetteAide = 0 ;
    protected double primeNetteRCG = 0;
    protected double  primeNetteSV = 0; 
    protected double  primeNetteJP = 0;
    protected double  primeNetteAS = 0;
    
    protected double primeNettePansol = 0 ;
    protected double primeNetteObjM = 0 ;
    

    public void setBaseGar(ArrayList<String> baseGar) {
        this.baseGar = baseGar;
    }

    public void setOpGar(ArrayList<OpGar> opGar) {
        this.opGar = opGar;
    }

    public ArrayList<String> getOpCodeGar() {
        ArrayList<String> tmp = new ArrayList<String>();

        for (OpGar tmpOpGar : opGar) {
            tmp.add(tmpOpGar.codeGar);
        }

        return tmp;
    }

    public String getOpGarString() {
        StringBuilder sb = new StringBuilder();
        for (OpGar gar : opGar) {
            sb.append(gar.libGar + "\n");
        }

        return sb.toString();
    }

    public String getBaseGar() {
        StringBuilder sb = new StringBuilder();
        for (String string : baseGar) {
            sb.append(string + "\n");
        }

        if (redemption) {
            sb.append("\nAvec Rachat d'Inhabitation");
        }
        return sb.toString();
    }

    protected double prixPiece;
    protected double prmScolaire;
    protected double redAppart;
    protected double majorInhab;
    protected double prmAssh;
    protected double redStPac1;
    protected double redStPac2;
    protected double redStPac3;

    public boolean isRedemption() {
        return redemption;
    }

    public double getFurniturePrice() {
        return furniturePrice;
    }

    public double getRedAppart() {
        return redAppart;
    }

    public double getRedStPac1() {
        return redStPac1;
    }

    public double getRedStPac2() {
        return redStPac2;
    }

    public double getRedStPac3() {
        return redStPac3;
    }
    

    public double getPrmAssh() {
		return prmAssh;
	}

	public void setPrixPiece(double prixPiece) {
        this.prixPiece = prixPiece;
    }

    public void setPrmScolaire(double prmScolaire) {
        this.prmScolaire = prmScolaire;
    }

    public void setRedAppart(double redAppart) {
        this.redAppart = redAppart;
    }

    public void setMajorInhab(double majorInhab) {
        this.majorInhab = majorInhab;
    }

    public void setPrmAssh(double prmAssh) {
        this.prmAssh = prmAssh;
    }

    public void setRedStPac1(double redStPac1) {
        this.redStPac1 = redStPac1;
    }

    public void setRedStPac2(double redStPac2) {
        this.redStPac2 = redStPac2;
    }

    public void setRedStPac3(double redStPac3) {
        this.redStPac3 = redStPac3;
    }
 
  
    
    public MRHPack(boolean redemption, int nbrPiece, double furniturePrice, TarifMrh tarif805) {
        this.redemption = redemption;
        this.nbrPiece = nbrPiece;
        this.furniturePrice = furniturePrice;
        


        prixPiece = tarif805.getPrixPiece().doubleValue();
        prmScolaire = tarif805.getPrmScolaire().doubleValue();
        redAppart = tarif805.getRedAppartement().doubleValue();
        majorInhab = tarif805.getMajorInhab().doubleValue();
        prmAssh = tarif805.getPrmAssh().doubleValue();
        
       // = TarifMrh.getRedStPac1();
      //  redStPac2 = TarifMrh.getRedStPac2();
       // redStPac3 = TarifMrh.getRedStPac3();

        baseGar = new ArrayList<String>();
        opGar = new ArrayList<OpGar>();
    }

    public abstract double getPrimeNette(List<TauxgarMrh> tauxList) throws Exception;

    public double getCost(String clientType, Produit produit) throws SQLException {
       // if (clientType.toUpperCase().equals("ST")) {
          //  return 0.0;
       // } else {
            return produit.getMntfrc().doubleValue();
        //}
    }

    public double getTax(double primeNette, double cost, Produit produit) throws Exception {
        double tax = produit.getTauxbas().doubleValue() / 100;

        return (primeNette + cost) * tax;
    	}

    public double getPrimeTotal(double primeNette, double cost, double tax) {
        return primeNette + cost + tax;
    }
    
    

    
	 
    
    

    public double getPrimeNetteRC() {
		return primeNetteRC;
	}

	public void setPrimeNetteRC(double primeNetteRC) {
		this.primeNetteRC = primeNetteRC;
	}

	public double getPrimeNetteInc() {
		return primeNetteInc;
	}

	public void setPrimeNetteInc(double primeNetteInc) {
		this.primeNetteInc = primeNetteInc;
	}

	public double getPrimeNetteGemp() {
		return primeNetteGemp;
	}

	public void setPrimeNetteGemp(double primeNetteGemp) {
		this.primeNetteGemp = primeNetteGemp;
	}

	public double getPrimeNetteVol() {
		return primeNetteVol;
	}

	public void setPrimeNetteVol(double primeNetteVol) {
		this.primeNetteVol = primeNetteVol;
	}

	public double getPrimeNetteAssh() {
		return primeNetteAssh;
	}

	public void setPrimeNetteAssh(double primeNetteAssh) {
		this.primeNetteAssh = primeNetteAssh;
	}

	
	
	
	
	public double getPrimeNetteDomElec() {
		return primeNetteDomElec;
	}

	public void setPrimeNetteDomElec(double primeNetteDomElec) {
		this.primeNetteDomElec = primeNetteDomElec;
	}

	public double getPrimeNetteEvenClim() {
		return primeNetteEvenClim;
	}

	public void setPrimeNetteEvenClim(double primeNetteEvenClim) {
		this.primeNetteEvenClim = primeNetteEvenClim;
	}

	public double getPrimeNetteDeg() {
		return primeNetteDeg;
	}

	public void setPrimeNetteDeg(double primeNetteDeg) {
		this.primeNetteDeg = primeNetteDeg;
	}

	public double getPrimeNetteBris() {
		return primeNetteBris;
	}

	public void setPrimeNetteBris(double primeNetteBris) {
		this.primeNetteBris = primeNetteBris;
	}
	
	
	
	
	
	

	public double getPrimeNetteObjval() {
		return primeNetteObjval;
	}

	public void setPrimeNetteObjval(double primeNetteObjval) {
		this.primeNetteObjval = primeNetteObjval;
	}

	public double getPrimeNetteAide() {
		return primeNetteAide;
	}

	public void setPrimeNetteAide(double primeNetteAide) {
		this.primeNetteAide = primeNetteAide;
	}

	public double getPrimeNetteRCG() {
		return primeNetteRCG;
	}

	public void setPrimeNetteRCG(double primeNetteRCG) {
		this.primeNetteRCG = primeNetteRCG;
	}

	public double getPrimeNetteSV() {
		return primeNetteSV;
	}

	public void setPrimeNetteSV(double primeNetteSV) {
		this.primeNetteSV = primeNetteSV;
	}

	public double getPrimeNetteJP() {
		return primeNetteJP;
	}

	public void setPrimeNetteJP(double primeNetteJP) {
		this.primeNetteJP = primeNetteJP;
	}

	public double getPrimeNetteAS() {
		return primeNetteAS;
	}

	public void setPrimeNetteAS(double primeNetteAS) {
		this.primeNetteAS = primeNetteAS;
	}
   
	
	public double getPrimeNettePansol() {
		return primeNettePansol;
	}

	public void setPrimeNettePansol(double primeNettePansol) {
		this.primeNettePansol = primeNettePansol;
	}
    
	
	public double getPrimeNetteObjM() {
		return primeNetteObjM;
	}

	public void setPrimeNetteObjM(double primeNetteObjM) {
		this.primeNetteObjM = primeNetteObjM;
	}
	
	public abstract String getName();
    
    
    
    
    
    
    
}
