/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "comgouv_mrh")
@NamedQueries({
    @NamedQuery(name = "Comgouv1.findAll", query = "SELECT c FROM Comgouv1 c")})
public class Comgouv1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "lib_zone", nullable = false, length = 5)
    private String libZone;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "GOUVERNORA", nullable = false, length = 30)
    private String gouvernora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "taux", nullable = false)
    private int taux;

    public Comgouv1() {
    }

    public Comgouv1(String gouvernora) {
        this.gouvernora = gouvernora;
    }

    public Comgouv1(String gouvernora, String libZone, int taux) {
        this.gouvernora = gouvernora;
        this.libZone = libZone;
        this.taux = taux;
    }

    public String getLibZone() {
        return libZone;
    }

    public void setLibZone(String libZone) {
        this.libZone = libZone;
    }

    public String getGouvernora() {
        return gouvernora;
    }

    public void setGouvernora(String gouvernora) {
        this.gouvernora = gouvernora;
    }

    public int getTaux() {
        return taux;
    }

    public void setTaux(int taux) {
        this.taux = taux;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gouvernora != null ? gouvernora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comgouv1)) {
            return false;
        }
        Comgouv1 other = (Comgouv1) object;
        if ((this.gouvernora == null && other.gouvernora != null) || (this.gouvernora != null && !this.gouvernora.equals(other.gouvernora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.simulation.model.Comgouv1[ gouvernora=" + gouvernora + " ]";
    }
    
}
