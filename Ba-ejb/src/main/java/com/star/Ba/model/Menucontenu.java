/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "MENUCONTENU")
@NamedQueries({
    @NamedQuery(name = "Menucontenu.findAll", query = "SELECT m FROM Menucontenu m")})
public class Menucontenu implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMENUCONTENU")
    private BigDecimal idmenucontenu;
    /*
     * 0 : NON OBLIGATOIRE
     * 1 : OBLIGATOIRE
     * 2:  OBLIGATOIRE SI TYPE CLIENT = PERSONNE PHYSIQUE : IL NE FAUT pas l'ajouter si le type client est societe comme le cas de TD
     * 3 : NON OBLIGATOIRE SI TYPE CLIENT= PERSONNE PHYSIQUE  : IL NE FAUT pas l'ajouter si le type client est societe
     * */
    @Column(name = "OBLIGATOIRE")
    private BigInteger obligatoire;
    @Column(name = "PACK")
    private Integer pack;
    @JoinColumn(name = "IDMENU", referencedColumnName = "IDMENU")
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu idmenu;
    @JoinColumn(name = "IDCONTENU", referencedColumnName = "IDCONTENU")
    @ManyToOne(fetch = FetchType.EAGER)
    private Contenu idcontenu;
    @Column(name = "NEXTTAB")
    private Integer nextTab;
    public Menucontenu() {
    }

    public Menucontenu(BigDecimal idmenucontenu) {
        this.idmenucontenu = idmenucontenu;
    }

    public BigDecimal getIdmenucontenu() {
        return idmenucontenu;
    }

    public void setIdmenucontenu(BigDecimal idmenucontenu) {
        this.idmenucontenu = idmenucontenu;
    }

    public BigInteger getObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(BigInteger obligatoire) {
        this.obligatoire = obligatoire;
    }

    public Menu getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(Menu idmenu) {
        this.idmenu = idmenu;
    }

    public Contenu getIdcontenu() {
        return idcontenu;
    }

    public void setIdcontenu(Contenu idcontenu) {
        this.idcontenu = idcontenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmenucontenu != null ? idmenucontenu.hashCode() : 0);
        return hash;
    }
    public Integer getPack() {
		return pack;
	}
    public void setPack(Integer pack) {
		this.pack = pack;
	}
    
    public Integer getNextTab() {
		return nextTab;
	}
    public void setNextTab(Integer nextTab) {
		this.nextTab = nextTab;
	}

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menucontenu)) {
            return false;
        }
        Menucontenu other = (Menucontenu) object;
        if ((this.idmenucontenu == null && other.idmenucontenu != null) || (this.idmenucontenu != null && !this.idmenucontenu.equals(other.idmenucontenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.Ba.model.Menucontenu[ idmenucontenu=" + idmenucontenu + " ]";
    }
    
}
