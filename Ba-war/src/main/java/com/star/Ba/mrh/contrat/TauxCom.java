/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.mrh.contrat;

/**
 *
 * @author evilkid
 */
public class TauxCom {

    private String codeResv;
    private double tauApp;
    private double tauGest;

    public TauxCom(String codeResv, double tauApp, double tauGest) {
        this.codeResv = codeResv;
        this.tauApp = tauApp;
        this.tauGest = tauGest;
    }

    public String getCodeResv() {
        return codeResv;
    }

    public double getTauApp() {
        return tauApp;
    }

    public double getTauGest() {
        return tauGest;
    }
}
