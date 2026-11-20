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
@Table(name = "PROFIL")
@NamedQueries({
    @NamedQuery(name = "Profil.findAll", query = "SELECT p FROM Profil p")})
public class Profil implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROFIL")
    private BigDecimal idProfil;
    @Size(max = 50)
    @Column(name = "LIBELLE")
    private String libelle;
    @Size(max = 4)
    @Column(name = "CODE")
    private String code;
    @OneToMany(mappedBy = "profil", fetch = FetchType.LAZY)
    private List<Utilisateurs> utilisateursList;
    @OneToMany(mappedBy = "idprofil", fetch = FetchType.LAZY)
    private List<Menu> menuList;

    public Profil() {
    }

    public Profil(BigDecimal idProfil) {
        this.idProfil = idProfil;
    }

    public BigDecimal getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(BigDecimal idProfil) {
        this.idProfil = idProfil;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Utilisateurs> getUtilisateursList() {
        return utilisateursList;
    }

    public void setUtilisateursList(List<Utilisateurs> utilisateursList) {
        this.utilisateursList = utilisateursList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProfil != null ? idProfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profil)) {
            return false;
        }
        Profil other = (Profil) object;
        if ((this.idProfil == null && other.idProfil != null) || (this.idProfil != null && !this.idProfil.equals(other.idProfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.star.Ba.model.Profil[ idProfil=" + idProfil + " ]";
    }
    
}
