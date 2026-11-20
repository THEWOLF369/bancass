package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the QUITTANCE database table.
 * 
 */
@Entity
@Table(name = "QUITTANCE_VL")
@NamedQuery(name="QuittanceVersementLibre.findAll", query="SELECT q FROM QuittanceVersementLibre q")
public class QuittanceVersementLibre implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_QUITTANCEVL")
	@SequenceGenerator(name = "ID_QUITTANCEVL", sequenceName = "ID_QUITTANCEVL", allocationSize = 1)
	@Column(name="ID_QUITTANCEVL")
	private long idQuittance;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_AU")
	private Date dateAu;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_DU")
	private Date dateDu;

	// bi-directional many-to-one association to adhesion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ADHESION")
	private Adhesion idAdhesion;


	private Double montant;
	private Double frais;
	private Double taxe;
	private BigInteger numquit;
	private Date DATE_CREATION;
	private Double fga;

	public QuittanceVersementLibre() {
	}


	public long getIdQuittance() {
		return idQuittance;
	}


	public void setIdQuittance(long idQuittance) {
		this.idQuittance = idQuittance;
	}


	public Date getDateAu() {
		return dateAu;
	}


	public void setDateAu(Date dateAu) {
		this.dateAu = dateAu;
	}


	public Date getDateDu() {
		return dateDu;
	}


	public void setDateDu(Date dateDu) {
		this.dateDu = dateDu;
	}


	public Adhesion getIdAdhesion() {
		return idAdhesion;
	}


	public void setIdAdhesion(Adhesion idAdhesion) {
		this.idAdhesion = idAdhesion;
	}


	public Double getMontant() {
		return montant;
	}


	public void setMontant(Double montant) {
		this.montant = montant;
	}


	public Double getFrais() {
		return frais;
	}


	public void setFrais(Double frais) {
		this.frais = frais;
	}


	public Double getTaxe() {
		return taxe;
	}


	public void setTaxe(Double taxe) {
		this.taxe = taxe;
	}


	public BigInteger getNumquit() {
		return numquit;
	}


	public void setNumquit(BigInteger numquit) {
		this.numquit = numquit;
	}


	public Date getDATE_CREATION() {
		return DATE_CREATION;
	}


	public void setDATE_CREATION(Date dATE_CREATION) {
		DATE_CREATION = dATE_CREATION;
	}

	public Double getFga() {
		return fga;
	}


	public void setFga(Double fga) {
		this.fga = fga;
	}


	public QuittanceVersementLibre(Date dateAu, Date dateDu, Adhesion idAdhesion,
			Double montant, Double frais, Double taxe, BigInteger numquit,
			Date dATE_CREATION,Double fga) {
		super();
		this.dateAu = dateAu;
		this.dateDu = dateDu;
		this.idAdhesion = idAdhesion;
		this.montant = montant;
		this.frais = frais;
		this.taxe = taxe;
		this.numquit = numquit;
		DATE_CREATION = dATE_CREATION;
		this.fga=fga;
	}
	
	


}