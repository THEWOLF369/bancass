/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "AGRNATURE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agrnature.findAll", query = "SELECT a FROM Agrnature a"),
    @NamedQuery(name = "Agrnature.findByIdnature", query = "SELECT a FROM Agrnature a WHERE a.idnature = :idnature"),
    @NamedQuery(name = "Agrnature.findByLibellenature", query = "SELECT a FROM Agrnature a WHERE a.libellenature = :libellenature"),
    @NamedQuery(name = "Agrnature.findByMinnbrpied", query = "SELECT a FROM Agrnature a WHERE a.minnbrpied = :minnbrpied"),
    @NamedQuery(name = "Agrnature.findByUniterendement", query = "SELECT a FROM Agrnature a WHERE a.uniterendement = :uniterendement"),
    @NamedQuery(name = "Agrnature.findByUniteprix", query = "SELECT a FROM Agrnature a WHERE a.uniteprix = :uniteprix"),
    @NamedQuery(name = "Agrnature.findByMinsupha", query = "SELECT a FROM Agrnature a WHERE a.minsupha = :minsupha"),
    @NamedQuery(name = "Agrnature.findByCalculrendement", query = "SELECT a FROM Agrnature a WHERE a.calculrendement = :calculrendement")})
public class Agrnature implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDNATURE", nullable = false, precision = 38, scale = 0)
    private BigDecimal idnature;
    @Size(max = 100)
    @Column(name = "LIBELLENATURE", length = 100)
    private String libellenature;
    @Column(name = "MINNBRPIED")
    private BigInteger minnbrpied;
    @Size(max = 10)
    @Column(name = "UNITERENDEMENT", length = 10)
    private String uniterendement;
    @Size(max = 10)
    @Column(name = "UNITEPRIX", length = 10)
    private String uniteprix;
    @Column(name = "MINSUPHA")
    private BigInteger minsupha;
    @Size(max = 100)
    @Column(name = "CALCULRENDEMENT", length = 100)
    private String calculrendement;
    @OneToMany(mappedBy = "idnature", fetch = FetchType.EAGER)
    private List<AgrclasseNature> agrclasseNatureList;

    public Agrnature() {
    }

    public Agrnature(BigDecimal idnature) {
        this.idnature = idnature;
    }

    public BigDecimal getIdnature() {
        return idnature;
    }

    public void setIdnature(BigDecimal idnature) {
        this.idnature = idnature;
    }

    public String getLibellenature() {
        return libellenature;
    }

    public void setLibellenature(String libellenature) {
        this.libellenature = libellenature;
    }

    public BigInteger getMinnbrpied() {
        return minnbrpied;
    }

    public void setMinnbrpied(BigInteger minnbrpied) {
        this.minnbrpied = minnbrpied;
    }

    public String getUniterendement() {
        return uniterendement;
    }

    public void setUniterendement(String uniterendement) {
        this.uniterendement = uniterendement;
    }

    public String getUniteprix() {
        return uniteprix;
    }

    public void setUniteprix(String uniteprix) {
        this.uniteprix = uniteprix;
    }

    public BigInteger getMinsupha() {
        return minsupha;
    }

    public void setMinsupha(BigInteger minsupha) {
        this.minsupha = minsupha;
    }

    public String getCalculrendement() {
        return calculrendement;
    }

    public void setCalculrendement(String calculrendement) {
        this.calculrendement = calculrendement;
    }

    @XmlTransient
    public List<AgrclasseNature> getAgrclasseNatureList() {
        return agrclasseNatureList;
    }

    public void setAgrclasseNatureList(List<AgrclasseNature> agrclasseNatureList) {
        this.agrclasseNatureList = agrclasseNatureList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnature != null ? idnature.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agrnature)) {
            return false;
        }
        Agrnature other = (Agrnature) object;
        if ((this.idnature == null && other.idnature != null) || (this.idnature != null && !this.idnature.equals(other.idnature))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.Ba.model.Agrnature[ idnature=" + idnature + " ]";
    }
    
}
