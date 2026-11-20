package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;


/**
 * The persistent class for the AMORTISSEMENT database table.
 * 
 */
@Entity
@NamedQuery(name="Amortissement.findAll", query="SELECT a FROM Amortissement a")
public class Amortissement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_AMORTISSEMENT")
	private long idAmortissement;

	private BigDecimal capital;

	@Temporal(TemporalType.DATE)
	@Column(name="DAT_DEB")
	private Date datDeb;

	@Temporal(TemporalType.DATE)
	@Column(name="DAT_FIN")
	private Date datFin;

	private BigDecimal prime;

	//bi-directional many-to-one association to Adhesion
	@ManyToOne
	@JoinColumn(name="ID_ADHESION")
	private Adhesion adhesion;

	public Amortissement() {
	}

	public long getIdAmortissement() {
		return this.idAmortissement;
	}

	public void setIdAmortissement(long idAmortissement) {
		this.idAmortissement = idAmortissement;
	}

	public BigDecimal getCapital() {
		return this.capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public Date getDatDeb() {
		return this.datDeb;
	}

	public void setDatDeb(Date datDeb) {
		this.datDeb = datDeb;
	}

	public Date getDatFin() {
		return this.datFin;
	}

	public void setDatFin(Date datFin) {
		this.datFin = datFin;
	}

	public BigDecimal getPrime() {
		return this.prime;
	}

	public void setPrime(BigDecimal prime) {
		this.prime = prime;
	}

	public Adhesion getAdhesion() {
		return this.adhesion;
	}

	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}

}