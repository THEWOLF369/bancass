package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;


/**
 * The persistent class for the LOCGOUV database table.
 * 
 */
@Entity
@Table(name="DETAILS_GARANTIE_AVJ")
@NamedQueries({
	@NamedQuery(name="DetailsGarantieAVJ.findAll", query="SELECT l FROM DetailsGarantieAVJ l order by l.idDetailsGarantieAvj asc")
})
public class DetailsGarantieAVJ implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_DETAILS_GARANTIE_AVJ")
	@SequenceGenerator(name = "ID_DETAILS_GARANTIE_AVJ", sequenceName = "ID_DETAILS_GARANTIE_AVJ", allocationSize = 1)
	@Column(name="ID_DETAILS_GARANTIE_AVJ")
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idDetailsGarantieAvj;
	
	//bi-directional many-to-one association to Proposition_test
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_PROPOSITION")
	private Proposition proposition;	
	
	//bi-directional many-to-one association to Adhesion_test
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ADHESION")
	private Adhesion adhesion;


		
	@Column(name="CODE_GARANTIE")
	private int code; 
	
	
	@Column(name="CAPITAL_ASSUREE")
	private BigDecimal capital;
	
	@Column(name="LIBELLE_GARANTIE")
	private String libelle;
	
	@Column(name="PRIME_COMPTANT")
	private BigDecimal prime;
	
	@Column(name="TAUX_REDUCTION")
	private BigDecimal taux;
			
 

	public DetailsGarantieAVJ() {
	}







	public long getIdDetailsGarantieAvj() {
		return idDetailsGarantieAvj;
	}







	public void setIdDetailsGarantieAvj(long idDetailsGarantieAvj) {
		this.idDetailsGarantieAvj = idDetailsGarantieAvj;
	}







	public Proposition getProposition() {
		return proposition;
	}



	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}



	public Adhesion getAdhesion() {
		return adhesion;
	}



	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}



	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public BigDecimal getCapital() {
		return capital;
	}



	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}



	public String getLibelle() {
		return libelle;
	}



	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}



	public BigDecimal getPrime() {
		return prime;
	}



	public void setPrime(BigDecimal prime) {
		this.prime = prime;
	}



	public BigDecimal getTaux() {
		return taux;
	}



	public void setTaux(BigDecimal taux) {
		this.taux = taux;
	}

	
	


}