/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mounamou
 */
@Entity
@Table(name = "TARIF_GRELE_DELEGATION")
@NamedQueries({
    @NamedQuery(name = "TarifGreleDelegation.findAll", query = "SELECT t FROM TarifGreleDelegation t")})
public class TarifGreleDelegation implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTARIFDELEGATION", nullable = false, precision = 38, scale = 0)
    private BigDecimal idtarifdelegation;
    @JoinColumn(name = "IDTARIF", referencedColumnName = "IDTARIF")
    @ManyToOne(fetch = FetchType.LAZY)
    private TarifGrele idtarif;
    @JoinColumn(name = "IDDELEGATION", referencedColumnName = "IDDELEGATION")
    @ManyToOne(fetch = FetchType.LAZY)
    private Delegation iddelegation;

    public TarifGreleDelegation() {
    }

    public TarifGreleDelegation(BigDecimal idtarifdelegation) {
        this.idtarifdelegation = idtarifdelegation;
    }

    public BigDecimal getIdtarifdelegation() {
        return idtarifdelegation;
    }

    public void setIdtarifdelegation(BigDecimal idtarifdelegation) {
        this.idtarifdelegation = idtarifdelegation;
    }

    public TarifGrele getIdtarif() {
        return idtarif;
    }

    public void setIdtarif(TarifGrele idtarif) {
        this.idtarif = idtarif;
    }

    public Delegation getIddelegation() {
        return iddelegation;
    }

    public void setIddelegation(Delegation iddelegation) {
        this.iddelegation = iddelegation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtarifdelegation != null ? idtarifdelegation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarifGreleDelegation)) {
            return false;
        }
        TarifGreleDelegation other = (TarifGreleDelegation) object;
        if ((this.idtarifdelegation == null && other.idtarifdelegation != null) || (this.idtarifdelegation != null && !this.idtarifdelegation.equals(other.idtarifdelegation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.Ba.model.TarifGreleDelegation[ idtarifdelegation=" + idtarifdelegation + " ]";
    }
    
}
