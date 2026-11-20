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
@Table(name = "GOUVERNORAT")
@NamedQueries({
    @NamedQuery(name = "Gouvernorat.findAll", query = "SELECT g FROM Gouvernorat g")})
public class Gouvernorat implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDGOUVERNORAT", nullable = false, precision = 38, scale = 0)
    private BigDecimal idgouvernorat;
    @Size(max = 50)
    @Column(name = "LIBELLEGOUVERNORAT", length = 50)
    private String libellegouvernorat;
    @OneToMany(mappedBy = "idgouvernorat", fetch = FetchType.LAZY)
    private List<Delegation> delegationList;

    public Gouvernorat() {
    }

    public Gouvernorat(BigDecimal idgouvernorat) {
        this.idgouvernorat = idgouvernorat;
    }

    public BigDecimal getIdgouvernorat() {
        return idgouvernorat;
    }

    public void setIdgouvernorat(BigDecimal idgouvernorat) {
        this.idgouvernorat = idgouvernorat;
    }

    public String getLibellegouvernorat() {
        return libellegouvernorat;
    }

    public void setLibellegouvernorat(String libellegouvernorat) {
        this.libellegouvernorat = libellegouvernorat;
    }

    public List<Delegation> getDelegationList() {
        return delegationList;
    }

    public void setDelegationList(List<Delegation> delegationList) {
        this.delegationList = delegationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgouvernorat != null ? idgouvernorat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gouvernorat)) {
            return false;
        }
        Gouvernorat other = (Gouvernorat) object;
        if ((this.idgouvernorat == null && other.idgouvernorat != null) || (this.idgouvernorat != null && !this.idgouvernorat.equals(other.idgouvernorat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.Ba.model.Gouvernorat[ idgouvernorat=" + idgouvernorat + " ]";
    }
    
}
