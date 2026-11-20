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
@Table(name = "AGRCLASSE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agrclasse.findAll", query = "SELECT a FROM Agrclasse a"),
    @NamedQuery(name = "Agrclasse.findByIdclasse", query = "SELECT a FROM Agrclasse a WHERE a.idclasse = :idclasse"),
    @NamedQuery(name = "Agrclasse.findByNomclasse", query = "SELECT a FROM Agrclasse a WHERE a.nomclasse = :nomclasse"),
    @NamedQuery(name = "Agrclasse.findByDescriptionclasse", query = "SELECT a FROM Agrclasse a WHERE a.descriptionclasse = :descriptionclasse"),
    @NamedQuery(name = "Agrclasse.findByMinnbrpied", query = "SELECT a FROM Agrclasse a WHERE a.minnbrpied = :minnbrpied"),
    @NamedQuery(name = "Agrclasse.findByMinsuperficie", query = "SELECT a FROM Agrclasse a WHERE a.minsuperficie = :minsuperficie"),
    @NamedQuery(name = "Agrclasse.findByUnitesup", query = "SELECT a FROM Agrclasse a WHERE a.unitesup = :unitesup"),
    @NamedQuery(name = "Agrclasse.findByPrixunitaire", query = "SELECT a FROM Agrclasse a WHERE a.prixunitaire = :prixunitaire"),
    @NamedQuery(name = "Agrclasse.findByUniteprix", query = "SELECT a FROM Agrclasse a WHERE a.uniteprix = :uniteprix"),
    @NamedQuery(name = "Agrclasse.findByMinrendement", query = "SELECT a FROM Agrclasse a WHERE a.minrendement = :minrendement"),
    @NamedQuery(name = "Agrclasse.findByUniterendement", query = "SELECT a FROM Agrclasse a WHERE a.uniterendement = :uniterendement")})
public class Agrclasse implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCLASSE", nullable = false, precision = 38, scale = 0)
    private BigDecimal idclasse;
    @Size(max = 20)
    @Column(name = "NOMCLASSE", length = 20)
    private String nomclasse;
    @Size(max = 150)
    @Column(name = "DESCRIPTIONCLASSE", length = 150)
    private String descriptionclasse;
    @Column(name = "MINNBRPIED")
    private BigInteger minnbrpied;
    @Column(name = "MINSUPERFICIE")
    private BigInteger minsuperficie;
    @Size(max = 10)
    @Column(name = "UNITESUP", length = 10)
    private String unitesup;
    @Size(max = 15)
    @Column(name = "PRIXUNITAIRE", length = 15)
    private String prixunitaire;
    @Size(max = 10)
    @Column(name = "UNITEPRIX", length = 10)
    private String uniteprix;
    @Column(name = "MINRENDEMENT")
    private BigInteger minrendement;
    @Size(max = 10)
    @Column(name = "UNITERENDEMENT", length = 10)
    private String uniterendement;
    @OneToMany(mappedBy = "idclasse", fetch = FetchType.EAGER)
    private List<AgrclasseNature> agrclasseNatureList;

    public Agrclasse() {
    }

    public Agrclasse(BigDecimal idclasse) {
        this.idclasse = idclasse;
    }

    public BigDecimal getIdclasse() {
        return idclasse;
    }

    public void setIdclasse(BigDecimal idclasse) {
        this.idclasse = idclasse;
    }

    public String getNomclasse() {
        return nomclasse;
    }

    public void setNomclasse(String nomclasse) {
        this.nomclasse = nomclasse;
    }

    public String getDescriptionclasse() {
        return descriptionclasse;
    }

    public void setDescriptionclasse(String descriptionclasse) {
        this.descriptionclasse = descriptionclasse;
    }

    public BigInteger getMinnbrpied() {
        return minnbrpied;
    }

    public void setMinnbrpied(BigInteger minnbrpied) {
        this.minnbrpied = minnbrpied;
    }

    public BigInteger getMinsuperficie() {
        return minsuperficie;
    }

    public void setMinsuperficie(BigInteger minsuperficie) {
        this.minsuperficie = minsuperficie;
    }

    public String getUnitesup() {
        return unitesup;
    }

    public void setUnitesup(String unitesup) {
        this.unitesup = unitesup;
    }

    public String getPrixunitaire() {
        return prixunitaire;
    }

    public void setPrixunitaire(String prixunitaire) {
        this.prixunitaire = prixunitaire;
    }

    public String getUniteprix() {
        return uniteprix;
    }

    public void setUniteprix(String uniteprix) {
        this.uniteprix = uniteprix;
    }

    public BigInteger getMinrendement() {
        return minrendement;
    }

    public void setMinrendement(BigInteger minrendement) {
        this.minrendement = minrendement;
    }

    public String getUniterendement() {
        return uniterendement;
    }

    public void setUniterendement(String uniterendement) {
        this.uniterendement = uniterendement;
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
        hash += (idclasse != null ? idclasse.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agrclasse)) {
            return false;
        }
        Agrclasse other = (Agrclasse) object;
        if ((this.idclasse == null && other.idclasse != null) || (this.idclasse != null && !this.idclasse.equals(other.idclasse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.Ba.model.Agrclasse[ idclasse=" + idclasse + " ]";
    }
    
}
