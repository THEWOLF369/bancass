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
 * @author Administrateur
 */
@Entity
@Table(name = "MENU")
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")})
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMENU")
    private BigDecimal idmenu;
    @Size(max = 100)
    @Column(name = "LIBELLEMENU")
    private String libellemenu;
    @Size(max = 100)
    @Column(name = "URL")
    private String url;
    @Size(max = 20)
    @Column(name = "ICON")
    private String icon;
    @JoinColumn(name = "IDPROFIL", referencedColumnName = "ID_PROFIL")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profil idprofil;
    @OneToMany(mappedBy = "idmenu", fetch = FetchType.LAZY)
    private List<Menucontenu> menucontenuList;

    public Menu() {
    }

    public Menu(BigDecimal idmenu) {
        this.idmenu = idmenu;
    }

    public BigDecimal getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(BigDecimal idmenu) {
        this.idmenu = idmenu;
    }

    public String getLibellemenu() {
        return libellemenu;
    }

    public void setLibellemenu(String libellemenu) {
        this.libellemenu = libellemenu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Profil getIdprofil() {
        return idprofil;
    }

    public void setIdprofil(Profil idprofil) {
        this.idprofil = idprofil;
    }

    public List<Menucontenu> getMenucontenuList() {
        return menucontenuList;
    }

    public void setMenucontenuList(List<Menucontenu> menucontenuList) {
        this.menucontenuList = menucontenuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmenu != null ? idmenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.idmenu == null && other.idmenu != null) || (this.idmenu != null && !this.idmenu.equals(other.idmenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.Ba.model.Menu[ idmenu=" + idmenu + " ]";
    }
    
}
