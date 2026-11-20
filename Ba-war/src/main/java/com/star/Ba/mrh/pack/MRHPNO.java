/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.mrh.pack;

import java.sql.SQLException;
import java.util.List;

import com.star.Ba.mrh.pack.OpGar;
import com.star.Ba.model.TarifMrh;
import com.star.Ba.model.TauxgarMrh;


/**
 *
 * @author evilkid
 */
public class MRHPNO extends MRHPack {

    public static String name = "PNO";
    
    private boolean JP;
    private boolean Pansol;
    private double valsol;
    
    public MRHPNO(boolean JP, boolean Pansol,double valsol, boolean redemption, int nbrPiece, double furniturePrice, TarifMrh tarif805) {
        super(redemption, nbrPiece, furniturePrice,tarif805);
        this.JP = JP;
        this.Pansol = Pansol; 
        this.valsol = valsol;
      
    }

    @Override
    public double getPrimeNette(List<TauxgarMrh> tauxList) throws SQLException {
        double primeNette = 0.0;

       
        baseGar.clear();

        for (TauxgarMrh tauxgar805 : tauxList) {
            String codeGar = tauxgar805.getCodeGar();
            double tauxGarPack = tauxgar805.getTauxgarpac5().doubleValue();

            if (tauxgar805.getTypeGar().equals("B")) {
                baseGar.add(tauxgar805.getLibGar());
            }

//            switch (codeGar) {
//                case "001":
//                case "002":
//                case "006":
//                case "007":
//                case "008":
//                case "009":
//                case "010":
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
//                case "004":
//                case "005":
//                    primeNette = primeNette + ((prixPiece * nbrPiece) * tauxGarPack);
//                    break;
//                case "012":
//                    if (RCG) {
//                        primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
//                        opGar.add(new OpGar(codeGar, tauxgar805.getLibGar()));
//                    }
//                    break;
//
//                case "013":
//                    if (JP) {
//                        primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
//                        opGar.add(new OpGar(codeGar, tauxgar805.getLibGar()));
//                    }
//                    break;
//
//                case "014":
//                    if (SV) {
//                        primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
//                        opGar.add(new OpGar(codeGar, tauxgar805.getLibGar()));
//                    }
//                    break;
//
//                case "015":
//                    if (AS) {
//                        primeNette = primeNette + (nbrChild * prmScolaire);
//                        opGar.add(new OpGar(codeGar, tauxgar805.getLibGar(), nbrChild));
//                    }
//                    break;
//            }

           // if (codeGar.equals("1") || codeGar.equals("2") || codeGar.equals("6")
                 //   || codeGar.equals("7") || codeGar.equals("8")
                  //  || codeGar.equals("9") || codeGar.equals("10") || codeGar.equals("11")) {
                //primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
            if(codeGar.equals("1")) {
              	 primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
              	 primeNetteRC = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
              	 
              }
              
              if(codeGar.equals("2")) {
              	 primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
              	 primeNetteInc = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
              }
              
              if(codeGar.equals("6")) {
                	 primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
                	 primeNetteDomElec = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
                }
              if(codeGar.equals("7")) {
              	 primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
              	 primeNetteEvenClim = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
              }

             
              if(codeGar.equals("10")) {
            	  primeNette = primeNette + prmAssh;
                  primeNetteAssh = prmAssh;
                  
               }
              if(codeGar.equals("11")) {
              	 primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
              	 primeNetteGemp = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
              }
              
             else if (codeGar.equals("3")) {
                if (redemption) {
                    primeNette = primeNette + ((((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack) * ((100 + majorInhab) / 100));
                    primeNetteVol = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack) * ((100 + majorInhab) / 100);
                } else {
                    primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
                    primeNetteVol = (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
                }
                }
                
           // } else if (codeGar.equals("4") || codeGar.equals("5")) {
             //   primeNette = primeNette + ((prixPiece * nbrPiece) * tauxGarPack);
                else if (codeGar.equals("4"))  {
                    primeNette = primeNette + ((prixPiece * nbrPiece) * tauxGarPack);
                    primeNetteDeg = (prixPiece * nbrPiece) * tauxGarPack;
            } 
                else if (codeGar.equals("5"))  {
                    primeNette = primeNette + ((prixPiece * nbrPiece) * tauxGarPack);
                    primeNetteBris = (prixPiece * nbrPiece) * tauxGarPack;
                
            }
             else if (codeGar.equals("13")) {
                if (JP) {
                    primeNette = primeNette + (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
                    primeNetteJP =  (((prixPiece * nbrPiece) + furniturePrice) * tauxGarPack);
                    opGar.add(new OpGar(codeGar, tauxgar805.getLibGar()));
                }
            } 
            else if(codeGar.equals("16")){
                if (Pansol) {
                        primeNette = primeNette +( valsol * tauxGarPack );
                        primeNettePansol = valsol * tauxGarPack ;
                        opGar.add(new OpGar(codeGar, tauxgar805.getLibGar()));
                        System.out.println("primeNettePansol "+primeNettePansol);
                        System.out.println("valsol "+valsol);
                        System.out.println("tauxGarPack "+tauxGarPack);
                    }
            }  
            
        }

        return primeNette;
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
    public double getPrimeNetteDomElec() {
		return primeNetteDomElec;
	}

    @Override 
	public double getPrimeNetteEvenClim() {
		return primeNetteEvenClim;
	}

    
    @Override
	public double getPrimeNetteDeg() {
		return primeNetteDeg;
	}

	 
    @Override
	public double getPrimeNetteBris() {
		return primeNetteBris;
	}
    
    

    @Override
    public String getName() {
        return name;
    }
	 
    @Override
	public double getPrimeNetteJP() {
		return primeNetteJP;
	}

    @Override
    public double getPrimeNettePansol() {
		return primeNettePansol;
	} 
    
   }
