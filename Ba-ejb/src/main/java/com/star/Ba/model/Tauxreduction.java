/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "TAUXREDUCTION")
@NamedQueries({
    @NamedQuery(name = "Tauxreduction.findAll", query = "SELECT t FROM Tauxreduction t")})
public class Tauxreduction implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TAUXRED", nullable = false, precision = 38, scale = 0)
    private BigDecimal idTauxred;
    @Column(name = "CODPROD")
    private BigInteger codprod;
    @Column(name = "TAUX")
    private BigDecimal taux;
    @Column(name = "DATAPPLI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datappli;
    @JoinColumn(name = "ID_PRODCONV", referencedColumnName = "ID_PRODCONV")
    @ManyToOne(fetch = FetchType.EAGER)
    private Prodconv idProdconv;
    @Column(name = "PACK")
	private Integer pack;

    public Tauxreduction() {
    }

    public Tauxreduction(BigDecimal idTauxred) {
        this.idTauxred = idTauxred;
    }

    public BigDecimal getIdTauxred() {
        return idTauxred;
    }

    public void setIdTauxred(BigDecimal idTauxred) {
        this.idTauxred = idTauxred;
    }

    public BigInteger getCodprod() {
        return codprod;
    }

    public void setCodprod(BigInteger codprod) {
        this.codprod = codprod;
    }

    public BigDecimal getTaux() {
        return taux;
    }

    public void setTaux(BigDecimal taux) {
        this.taux = taux;
    }

    public Date getDatappli() {
        return datappli;
    }

    public void setDatappli(Date datappli) {
        this.datappli = datappli;
    }

    public Prodconv getIdProdconv() {
        return idProdconv;
    }

    public void setIdProdconv(Prodconv idProdconv) {
        this.idProdconv = idProdconv;
    }
    
    public Integer getPack() {
		return pack;
	}
    public void setPack(Integer pack) {
		this.pack = pack;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTauxred != null ? idTauxred.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tauxreduction)) {
            return false;
        }
        Tauxreduction other = (Tauxreduction) object;
        if ((this.idTauxred == null && other.idTauxred != null) || (this.idTauxred != null && !this.idTauxred.equals(other.idTauxred))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.Ba.model.Tauxreduction[ idTauxred=" + idTauxred + " ]";
    }
    
}
