/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "AGRCLASSE_NATURE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgrclasseNature.findAll", query = "SELECT a FROM AgrclasseNature a"),
    @NamedQuery(name = "AgrclasseNature.findByIdclassenature", query = "SELECT a FROM AgrclasseNature a WHERE a.idclassenature = :idclassenature")})
public class AgrclasseNature implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCLASSENATURE", nullable = false, precision = 38, scale = 0)
    private BigDecimal idclassenature;
    @JoinColumn(name = "IDNATURE", referencedColumnName = "IDNATURE")
    @ManyToOne(fetch = FetchType.EAGER)
    private Agrnature idnature;
    @JoinColumn(name = "IDCLASSE", referencedColumnName = "IDCLASSE")
    @ManyToOne(fetch = FetchType.EAGER)
    private Agrclasse idclasse;

    public AgrclasseNature() {
    }

    public AgrclasseNature(BigDecimal idclassenature) {
        this.idclassenature = idclassenature;
    }

    public BigDecimal getIdclassenature() {
        return idclassenature;
    }

    public void setIdclassenature(BigDecimal idclassenature) {
        this.idclassenature = idclassenature;
    }

    public Agrnature getIdnature() {
        return idnature;
    }

    public void setIdnature(Agrnature idnature) {
        this.idnature = idnature;
    }

    public Agrclasse getIdclasse() {
        return idclasse;
    }

    public void setIdclasse(Agrclasse idclasse) {
        this.idclasse = idclasse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idclassenature != null ? idclassenature.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgrclasseNature)) {
            return false;
        }
        AgrclasseNature other = (AgrclasseNature) object;
        if ((this.idclassenature == null && other.idclassenature != null) || (this.idclassenature != null && !this.idclassenature.equals(other.idclassenature))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.Ba.model.AgrclasseNature[ idclassenature=" + idclassenature + " ]";
    }
    
}
