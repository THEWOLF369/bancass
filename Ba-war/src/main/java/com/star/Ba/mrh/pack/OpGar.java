/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.mrh.pack;

import java.io.Serializable;

/**
 *
 * @author evilkid
 */
public class OpGar implements Serializable {

    public String codeGar;
    public String libGar;
    public int nbrChild;

    public OpGar(String codeGar, String libGar) {
        this.codeGar = codeGar;
        this.libGar = libGar;
    }

    public OpGar(String codeGar, String libGar, int nbrChild) {
        this.codeGar = codeGar;
        this.libGar = libGar;
        this.nbrChild = nbrChild;
    }
}


