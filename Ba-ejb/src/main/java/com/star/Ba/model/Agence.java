package com.star.Ba.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the AGENCE database table.
 *
 */
@Entity
@NamedQuery(name = "Agence.findAll", query = "SELECT a FROM Agence a")
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGENCESEQUENCE")
    @SequenceGenerator(name = "AGENCESEQUENCE", sequenceName = "AGENCESEQUENCE", allocationSize = 1)
    @Column(name = "ID_AGENCE")
    private Integer idAgence;
    @Size(max = 4)
    @Column(name = "CODE_AGENCE")
    private String codeAgence;
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "ADRESSE")
    private String adresse;
    @Size(max = 8)
    @Column(name = "TELEPHONE")
    private String telephone;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 8)
    @Column(name = "FAX")
    private String fax;
    @Pattern(regexp = "|[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "Verifier Votre Email")
    @Size(max = 50)
    @Column(name = "MAIL")
    private String mail;
    @JoinColumn(name = "COD_SOCIET", referencedColumnName = "COD_SOCIET")
    @ManyToOne
    private Societe societe;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "agence")
    @OneToMany(mappedBy = "agence",fetch = FetchType.LAZY)
    private List<Utilisateurs> utilisateurs;

    public Agence() {
    }

    public Agence(Integer idAgence) {
        this.idAgence = idAgence;
    }

    public Integer getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(Integer idAgence) {
        this.idAgence = idAgence;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    @XmlTransient
    public List<Utilisateurs> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateurs> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    
    

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeAgence == null) ? 0 : codeAgence.hashCode());
		result = prime * result
				+ ((idAgence == null) ? 0 : idAgence.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agence other = (Agence) obj;
		if (codeAgence == null) {
			if (other.codeAgence != null)
				return false;
		} else if (!codeAgence.equals(other.codeAgence))
			return false;
		if (idAgence == null) {
			if (other.idAgence != null)
				return false;
		} else if (!idAgence.equals(other.idAgence))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return getSociete().getRaisonSoc() + " - " + libelle;
    }

    public Utilisateurs addUtilisateur(Utilisateurs utilisateur) {
        getUtilisateurs().add(utilisateur);
        utilisateur.setAgence(this);

        return utilisateur;
    }

    public Utilisateurs removeUtilisateur(Utilisateurs utilisateur) {
        getUtilisateurs().remove(utilisateur);
        utilisateur.setAgence(null);

        return utilisateur;
    }

}
