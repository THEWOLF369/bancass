/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mounamou
 */
@Entity
@Table(name = "DELEGATION")
@NamedQueries({
    @NamedQuery(name = "Delegation.findAll", query = "SELECT d FROM Delegation d")})
public class Delegation implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDDELEGATION", nullable = false, precision = 38, scale = 0)
    private BigDecimal iddelegation;
    @Size(max = 50)
    @Column(name = "LIBELLEDELEGATION", length = 50)
    private String libelledelegation;
    @JoinColumn(name = "IDGOUVERNORAT", referencedColumnName = "IDGOUVERNORAT")
    @ManyToOne(fetch = FetchType.LAZY)
    private Gouvernorat idgouvernorat;
    @OneToMany(mappedBy = "iddelegation", fetch = FetchType.LAZY)
    private List<TarifGreleDelegation> tarifGreleDelegationList;

    public Delegation() {
    }

    public Delegation(BigDecimal iddelegation) {
        this.iddelegation = iddelegation;
    }

    public BigDecimal getIddelegation() {
        return iddelegation;
    }

    public void setIddelegation(BigDecimal iddelegation) {
        this.iddelegation = iddelegation;
    }

    public String getLibelledelegation() {
        return libelledelegation;
    }

    public void setLibelledelegation(String libelledelegation) {
        this.libelledelegation = libelledelegation;
    }

    public Gouvernorat getIdgouvernorat() {
        return idgouvernorat;
    }

    public void setIdgouvernorat(Gouvernorat idgouvernorat) {
        this.idgouvernorat = idgouvernorat;
    }

    public List<TarifGreleDelegation> getTarifGreleDelegationList() {
        return tarifGreleDelegationList;
    }

    public void setTarifGreleDelegationList(List<TarifGreleDelegation> tarifGreleDelegationList) {
        this.tarifGreleDelegationList = tarifGreleDelegationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddelegation != null ? iddelegation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Delegation)) {
            return false;
        }
        Delegation other = (Delegation) object;
        if ((this.iddelegation == null && other.iddelegation != null) || (this.iddelegation != null && !this.iddelegation.equals(other.iddelegation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.Ba.model.Delegation[ iddelegation=" + iddelegation + " ]";
    }
    
}
