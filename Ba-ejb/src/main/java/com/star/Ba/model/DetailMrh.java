package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the DETAIL_MRH database table.
 * 
 */
@Entity
@Table(name="DETAIL_MRH")
@NamedQuery(name="DetailMrh.findAll", query="SELECT d FROM DetailMrh d")
public class DetailMrh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DETAIL_MRH_IDDETAILMRH_GENERATOR", sequenceName="DETAILMRH")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DETAIL_MRH_IDDETAILMRH_GENERATOR")
	@Column(name="ID_DETAIL_MRH")
	private long idDetailMrh;

	//@Column(name="ADR_RISQUE")
	//private String adrRisque;

	private BigDecimal area;

	
	private BigDecimal furnitureprice;

	

	private BigDecimal nbrpieceless;

	private BigDecimal nbrpiecemore;
	
	@Column(name="TYPE_CONTRAT")
	private String typecontrat;
	
	
	private String duree;
	
	@Column(name="VALEUR_SOLAIRE")
	private BigDecimal valsolaire;
	
	@Column(name="VALEUR_MULTIMEDIA")
	private BigDecimal valmultimedia;
	

	@Column(name="RACH_IN")
	private String rachIn;
	
	@Column(name="RESIDENCE")
	private String residence;
	
	//bi-directional many-to-one association to Adhesion_test
	@OneToMany(mappedBy="detailMrh")
	private List<Adhesion> adhesions;
	
	@OneToMany(mappedBy="detailMrh")
	private List<Proposition> propositions;

	//bi-directional many-to-one association to NatureRisq
	@ManyToOne
	@JoinColumn(name="ID_RISK_MRH")
	private NatureRisq natureRisq;

	

	//bi-directional many-to-one association to Qualite
	@ManyToOne
	@JoinColumn(name="ID_QUALITE")
	private Qualite qualite;

	public DetailMrh() {
	}

	public long getIdDetailMrh() {
		return this.idDetailMrh;
	}

	public void setIdDetailMrh(long idDetailMrh) {
		this.idDetailMrh = idDetailMrh;
	}

	

	public BigDecimal getArea() {
		return this.area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	
	public BigDecimal getFurnitureprice() {
		return this.furnitureprice;
	}

	public void setFurnitureprice(BigDecimal furnitureprice) {
		this.furnitureprice = furnitureprice;
	}

	
	public BigDecimal getNbrpieceless() {
		return this.nbrpieceless;
	}

	public void setNbrpieceless(BigDecimal nbrpieceless) {
		this.nbrpieceless = nbrpieceless;
	}

	public BigDecimal getNbrpiecemore() {
		return this.nbrpiecemore;
	}

	public void setNbrpiecemore(BigDecimal nbrpiecemore) {
		this.nbrpiecemore = nbrpiecemore;
	}

	public String getRachIn() {
		return this.rachIn;
	}

	public void setRachIn(String rachIn) {
		this.rachIn = rachIn;
	}

	public List<Adhesion> getAdhesions() {
		return this.adhesions;
	}

	public void setAdhesions(List<Adhesion> adhesions) {
		this.adhesions = adhesions;
	}

	public Adhesion addAdhesion(Adhesion adhesion) {
		getAdhesions().add(adhesion);
		adhesion.setDetailMrh(this);

		return adhesion;
	}

	public Adhesion removeAdhesion(Adhesion adhesion) {
		getAdhesions().remove(adhesion);
		adhesion.setDetailMrh(null);

		return adhesion;
	}

	public NatureRisq getNatureRisq() {
		return this.natureRisq;
	}

	public void setNatureRisq(NatureRisq natureRisq) {
		this.natureRisq = natureRisq;
	}

	

	public Qualite getQualite() {
		return this.qualite;
	}

	public void setQualite(Qualite qualite) {
		this.qualite = qualite;
	}
	
	
	

	public String getTypecontrat() {
		return typecontrat;
	}

	public void setTypecontrat(String typecontrat) {
		this.typecontrat = typecontrat;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}
    
	
	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public BigDecimal getValsolaire() {
		return valsolaire;
	}

	public void setValsolaire(BigDecimal valsolaire) {
		this.valsolaire = valsolaire;
	}

	public BigDecimal getValmultimedia() {
		return valmultimedia;
	}

	public void setValmultimedia(BigDecimal valmultimedia) {
		this.valmultimedia = valmultimedia;
	}

	 
	
	

}