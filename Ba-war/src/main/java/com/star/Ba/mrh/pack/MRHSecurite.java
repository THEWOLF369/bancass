/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.mrh.pack;

import com.star.Ba.model.TarifMrh;
import com.star.Ba.model.TauxgarMrh;

import java.util.List;


/**
 *
 * @author evilkid
 */
public class MRHSecurite extends MRHPack {

    public static String name = "Securite";
    private boolean assh;
    private boolean Pansol;
    private double valsol;

    public MRHSecurite(boolean assh,boolean Pansol,double valsol,boolean redemption, int nbrPiece, double furniturePrice, TarifMrh tarif805) {
        super(redemption, nbrPiece, furniturePrice,tarif805);
        this.assh = assh;
        this.Pansol  = Pansol;
        this.valsol = valsol;
    }

    @Override
    public double getPrimeNette(List<TauxgarMrh> tauxList) throws Exception {
    	
    	System.out.println("debut getPrimeNette.");
        double primeNette = 0.0;
        

        baseGar.clear();

        for (TauxgarMrh tauxgar805 : tauxList) {
            String codeGar = tauxgar805.getCodeGar();

            if (tauxgar805.getTypeGar().equals("B") & !(codeGar.equals("10"))) {
                baseGar.add(tauxgar805.getLibGar());
            }

            double tauxGarPack = tauxgar805.getTauxgarpac1().doubleValue();

//            switch (codeGar) {
//                case "001":
//                case "002":
//                case "011":
//                    primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
//                    break;
//                case "003":
//                    if (redemption) {
//                        primeNette = primeNette + ((((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack) * ((100 + majorInhab) / 100));
//                    } else {
//                        primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
//                    }
//                    break;
//                case "010":
//                    if (assh) {
//                        primeNette = primeNette + prmAssh;
//
//                        opGar.add(new OpGar(codeGar, tauxgar805.getLibGar()));
//                    }
//                    break;
//            }
//            if(codeGar.equals("1") || codeGar.equals("2") || codeGar.equals("11")){
//                primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
//            }
            if(codeGar.equals("1")) {
            	 primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
            	 primeNetteRC = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
            	 
            }
            
            if(codeGar.equals("2")) {
            	 primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
            	 primeNetteInc = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
            }
            
            if(codeGar.equals("11")) {
            	 primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
            	 primeNetteGemp = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
            }
            
            else if(codeGar.equals("3")){
                if (redemption) {
                        primeNette = primeNette + ((((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack) * ((100 + majorInhab) / 100));
                        primeNetteVol = ((((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack) * ((100 + majorInhab) / 100));
                } else {
                        primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
                        primeNetteVol = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
                    }
            } else if(codeGar.equals("10")){
                if (assh) {
                        primeNette = primeNette + prmAssh;
                        primeNetteAssh = prmAssh;
                        opGar.add(new OpGar(codeGar, tauxgar805.getLibGar()));
                    }
            }
            else if(codeGar.equals("16")){
                if (Pansol) {
                        primeNette = primeNette +( valsol * tauxGarPack );
                        primeNettePansol = valsol * tauxGarPack ;
                        opGar.add(new OpGar(codeGar, tauxgar805.getLibGar()));
                    }
            }
        }
        
        System.out.println("fin primeNette."+primeNette);

        return primeNette;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isAssh() {
        return assh;
    }

	 
    @Override
    public double getPrimeNetteRC(){
		return primeNetteRC;
    	
    }
    
     
    @Override
	public double getPrimeNetteInc() {
		return primeNetteInc;
	}

	 
    @Override
	public double getPrimeNetteGemp() {
		return primeNetteGemp;
	}

 
    @Override
	public double getPrimeNetteVol() {
		return primeNetteVol;
	}

	 
    @Override
	public double getPrimeNetteAssh() {
		return primeNetteAssh;
	}
    

    @Override
    public double getPrimeNettePansol() {
		return primeNettePansol;
	}
    
    
    
}
