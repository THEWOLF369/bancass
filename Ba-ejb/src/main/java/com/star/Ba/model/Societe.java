package com.star.Ba.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author star
 */
@Entity
@Table(name = "SOCIETE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Societe.findAll", query = "SELECT s FROM Societe s"),
    @NamedQuery(name = "Societe.findByCodSociet", query = "SELECT s FROM Societe s WHERE s.codSociet = :codSociet"),
    @NamedQuery(name = "Societe.findByRaisonSoc", query = "SELECT s FROM Societe s WHERE s.raisonSoc = :raisonSoc"),
    @NamedQuery(name = "Societe.findByAdresse", query = "SELECT s FROM Societe s WHERE s.adresse = :adresse"),
    @NamedQuery(name = "Societe.findByCodPost", query = "SELECT s FROM Societe s WHERE s.codPost = :codPost"),
    @NamedQuery(name = "Societe.findByTelphone", query = "SELECT s FROM Societe s WHERE s.telphone = :telphone"),
    @NamedQuery(name = "Societe.findByFax", query = "SELECT s FROM Societe s WHERE s.fax = :fax"),
    @NamedQuery(name = "Societe.findByTelex", query = "SELECT s FROM Societe s WHERE s.telex = :telex"),
    @NamedQuery(name = "Societe.findByNomResp", query = "SELECT s FROM Societe s WHERE s.nomResp = :nomResp"),
    @NamedQuery(name = "Societe.findByTypClient", query = "SELECT s FROM Societe s WHERE s.typClient = :typClient"),
    @NamedQuery(name = "Societe.findByMatfisc", query = "SELECT s FROM Societe s WHERE s.matfisc = :matfisc")})
public class Societe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "COD_SOCIET")
    private String codSociet;
    @Size(max = 60)
    @Column(name = "RAISON_SOC")
    private String raisonSoc;
    @Size(max = 100)
    @Column(name = "ADRESSE")
    private String adresse;
    @Size(max = 4)
    @Column(name = "COD_POST")
    private String codPost;
    @Size(max = 8)
    @Column(name = "TELPHONE")
    private String telphone;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 8)
    @Column(name = "FAX")
    private String fax;
    @Size(max = 8)
    @Column(name = "TELEX")
    private String telex;
    @Size(max = 60)
    @Column(name = "NOM_RESP")
    private String nomResp;
    @Size(max = 1)
    @Column(name = "TYP_CLIENT")
    private String typClient;
    @Size(max = 50)
    @Column(name = "MATFISC")
    private String matfisc;
    @Column(name = "NOM_COMPLET")
    private String nomComplet;
    //bi-directional many-to-one association to Convention
    @OneToMany(mappedBy = "societe",fetch=FetchType.EAGER)
    private List<Convention> conventions;

    public Societe() {
    }

    public Societe(String codSociet) {
        this.codSociet = codSociet;
    }

    public String getCodSociet() {
        return codSociet;
    }

    public void setCodSociet(String codSociet) {
        this.codSociet = codSociet;
    }

    public String getRaisonSoc() {
        return raisonSoc;
    }

    public void setRaisonSoc(String raisonSoc) {
        this.raisonSoc = raisonSoc;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodPost() {
        return codPost;
    }

    public void setCodPost(String codPost) {
        this.codPost = codPost;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelex() {
        return telex;
    }

    public void setTelex(String telex) {
        this.telex = telex;
    }

    public String getNomResp() {
        return nomResp;
    }

    public void setNomResp(String nomResp) {
        this.nomResp = nomResp;
    }

    public String getTypClient() {
        return typClient;
    }

    public void setTypClient(String typClient) {
        this.typClient = typClient;
    }

    public String getMatfisc() {
        return matfisc;
    }

    public void setMatfisc(String matfisc) {
        this.matfisc = matfisc;
    }
    
    

    public String getNomComplet() {
		return nomComplet;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	@XmlTransient
    public List<Convention> getConventions() {
        return conventions;
    }

    public void setConventions(List<Convention> conventions) {
        this.conventions = conventions;
    }

   
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codSociet == null) ? 0 : codSociet.hashCode());
		result = prime * result
				+ ((raisonSoc == null) ? 0 : raisonSoc.hashCode());
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
		Societe other = (Societe) obj;
		if (codSociet == null) {
			if (other.codSociet != null)
				return false;
		} else if (!codSociet.equals(other.codSociet))
			return false;
		if (raisonSoc == null) {
			if (other.raisonSoc != null)
				return false;
		} else if (!raisonSoc.equals(other.raisonSoc))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return raisonSoc;
    }

}
