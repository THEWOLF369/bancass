package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the CONVENTION database table.
 *
 */
@Entity
@NamedQuery(name = "Convention.findAll", query = "SELECT c FROM Convention c")
public class Convention implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_CONVENTION")
    @SequenceGenerator(name = "ID_CONVENTION", sequenceName = "ID_CONVENTION", allocationSize = 1)
    @Column(name = "ID_CONVENTION")
    private long idConvention;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_DEBUT")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FIN")
    private Date dateFin;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_SIGNATURE")
    private Date dateSignature;
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_DERNIE_MISE_A_JOUR")
    private Date datedernieMiseAjour;
    //bi-directional many-to-one association to Societe
    @ManyToOne
    @JoinColumn(name = "COD_SOCIET" )
    private Societe societe;

    
    //bi-directional many-to-one association to Police
    @OneToMany(mappedBy = "convention")
    private List<Police> polices;

    //bi-directional many-to-one association to Prodconv
    @OneToMany(mappedBy = "convention", cascade = CascadeType.ALL,orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Prodconv> prodconvs=new ArrayList<Prodconv>();

//    @ManyToMany(mappedBy = "conventions")
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private List<Produit> produits;

    public Convention() {
    }

    public long getIdConvention() {
        return this.idConvention;
    }

    public void setIdConvention(long idConvention) {
        this.idConvention = idConvention;
    }

    public Date getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateSignature() {
        return this.dateSignature;
    }

    public void setDateSignature(Date dateSignature) {
        this.dateSignature = dateSignature;
    }

    public Societe getSociete() {
        return this.societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public List<Police> getPolices() {
        return this.polices;
    }

    public void setPolices(List<Police> polices) {
        this.polices = polices;
    }

//    public List<Produit> getProduits() {
//        return produits;
//    }
//
//    public void setProduits(List<Produit> produits) {
//        this.produits = produits;
//    }

    public Police addPolice(Police police) {
        getPolices().add(police);
                                police.setConvention(this);

        return police;
    }

    public Police removePolice(Police police) {
        getPolices().remove(police);
        police.setConvention(null);

        return police;
    }

    public List<Prodconv> getProdconvs() {
        return this.prodconvs;
    }

    public void setProdconvs(List<Prodconv> prodconvs) {
        this.prodconvs = prodconvs;
    }

    public Prodconv addProdconv(Prodconv prodconv) {
        getProdconvs().add(prodconv);
        prodconv.setConvention(this);
        
        return prodconv;
    }

    public Prodconv removeProdconv(Prodconv prodconv) {
        getProdconvs().remove(prodconv);
        prodconv.setConvention(null);

        return prodconv;
    }


	public Date getDatedernieMiseAjour() {
		return datedernieMiseAjour;
	}

	public void setDatedernieMiseAjour(Date datedernieMiseAjour) {
		this.datedernieMiseAjour = datedernieMiseAjour;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.idConvention ^ (this.idConvention >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Convention other = (Convention) obj;
        if (this.idConvention != other.idConvention) {
            return false;
        }
        return true;
    }
 
    
}
