package com.star.Ba.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
@NamedQuery(name = "Taxe.findAll", query = "SELECT t FROM Taxe t")
public class Taxe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_TAXE")
    @SequenceGenerator(name = "ID_TAXE", sequenceName = "ID_TAXE", allocationSize = 1)
    @Column(name = "ID_TAXE")
    private long IDTAXE;

    
    //bi-directional many-to-one association to Produit
    
    @ManyToOne
    @JoinColumn(name = "PRODUIT")
    private Produit produit;

    private double taxe;
    
    private Date date_effet;
    
    public Taxe(){
    }
    

    public Taxe(long iDTAXE, Produit produit, double taxe, Date date_effet) {
		super();
		IDTAXE = iDTAXE;
		this.produit = produit;
		this.taxe = taxe;
		this.date_effet = date_effet;
	}

	public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }


	public long getIDTAXE() {
		return IDTAXE;
	}


	public void setIDTAXE(long iDTAXE) {
		IDTAXE = iDTAXE;
	}


	


	public double getTaxe() {
		return taxe;
	}


	public void setTaxe(double taxe) {
		this.taxe = taxe;
	}


	public Date getDate_effet() {
		return date_effet;
	}


	public void setDate_effet(Date date_effet) {
		this.date_effet = date_effet;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (IDTAXE ^ (IDTAXE >>> 32));
		result = prime * result
				+ ((date_effet == null) ? 0 : date_effet.hashCode());
		result = prime * result + ((produit == null) ? 0 : produit.hashCode());
		long temp;
		temp = Double.doubleToLongBits(taxe);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Taxe other = (Taxe) obj;
		if (IDTAXE != other.IDTAXE)
			return false;
		if (date_effet == null) {
			if (other.date_effet != null)
				return false;
		} else if (!date_effet.equals(other.date_effet))
			return false;
		if (produit == null) {
			if (other.produit != null)
				return false;
		} else if (!produit.equals(other.produit))
			return false;
		if (Double.doubleToLongBits(taxe) != Double
				.doubleToLongBits(other.taxe))
			return false;
		return true;
	}







    

	

}
