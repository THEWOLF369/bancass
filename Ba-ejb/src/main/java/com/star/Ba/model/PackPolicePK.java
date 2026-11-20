/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrateur
 */
@Embeddable
public class PackPolicePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PACK_POLICE", nullable = false)
    private BigInteger idPackPolice;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "N_POLICE", nullable = false, length = 15)
    private String nPolice;

    public PackPolicePK() {
    }

    public PackPolicePK(BigInteger idPackPolice, String nPolice) {
        this.idPackPolice = idPackPolice;
        this.nPolice = nPolice;
    }

    public BigInteger getIdPackPolice() {
        return idPackPolice;
    }

    public void setIdPackPolice(BigInteger idPackPolice) {
        this.idPackPolice = idPackPolice;
    }

    public String getNPolice() {
        return nPolice;
    }

    public void setNPolice(String nPolice) {
        this.nPolice = nPolice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPackPolice != null ? idPackPolice.hashCode() : 0);
        hash += (nPolice != null ? nPolice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PackPolicePK)) {
            return false;
        }
        PackPolicePK other = (PackPolicePK) object;
        if ((this.idPackPolice == null && other.idPackPolice != null) || (this.idPackPolice != null && !this.idPackPolice.equals(other.idPackPolice))) {
            return false;
        }
        if ((this.nPolice == null && other.nPolice != null) || (this.nPolice != null && !this.nPolice.equals(other.nPolice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nPolice;
    }
    
}
