package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import com.star.Ba.model.Produit;


/**
 * The persistent class for the POLICE database table.
 * 
 */
@Entity
@NamedQuery(name="Police.findAll", query="SELECT p FROM Police p")
public class Police implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="N_POLICE")
	private String nPolice;

	private BigDecimal agelimit;

	@Column(name="APP_TX")
	private String appTx;

	@ManyToOne
	@JoinColumn(name="COD_PRODUI")
	private Produit codProdui;

	@Temporal(TemporalType.DATE)
	@Column(name="DAT_DEB")
	private Date datDeb;

	@Temporal(TemporalType.DATE)
	@Column(name="DAT_FIN")
	private Date datFin;

	@Temporal(TemporalType.DATE)
	@Column(name="DAT_MAJ")
	private Date datMaj;

	@Column(name="ECH_POLICE")
	private String echPolice;

	@Column(name="ETA_POLICE")
	private String etaPolice;

	@Column(name="MODE_PAYE")
	private String modePaye;

	@Column(name="MT_ACC")
	private BigDecimal mtAcc;
	
	private Double fga;

	@Column(name="TAUX_PRIME")
	private Double tauxPrime;

	@Column(name="TAXE_POL")
	private String taxePol;

	@Column(name="TX_COM")
	private BigDecimal txCom;

	@Column(name="TYP_AMORT")
	private String typAmort;

	@Column(name="TYP_POLICE")
	private String typPolice;

	@Column(name="TYPE_TX")
	private String typeTx;
	

	//bi-directional many-to-one association to Adhesion
	@OneToMany(mappedBy="police")
	private List<Adhesion> adhesions;

	//bi-directional many-to-one association to GarantieVie
	@OneToMany(mappedBy="police", fetch = FetchType.EAGER)
	private List<GarantieVie> garantieVies;

	//bi-directional many-to-one association to Convention
	@ManyToOne
	@JoinColumn(name="ID_CONVENTION")
	private Convention convention;

	//bi-directional many-to-one association to PoliceCredit
	@OneToMany(mappedBy="police")
	private List<PoliceCredit> policeCredits;

	//bi-directional many-to-one association to Proposition
	@OneToMany(mappedBy="police")
	private List<Proposition> propositions;

	private Double taxe;
	private String libelle;
	public Police() {
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Double getTaxe() {
		return taxe;
	}

	public void setTaxe(Double taxe) {
		this.taxe = taxe;
	}

	public String getNPolice() {
		return this.nPolice;
	}

	public void setNPolice(String nPolice) {
		this.nPolice = nPolice;
	}

	public BigDecimal getAgelimit() {
		return this.agelimit;
	}

	public void setAgelimit(BigDecimal agelimit) {
		this.agelimit = agelimit;
	}

	public String getAppTx() {
		return this.appTx;
	}

	public void setAppTx(String appTx) {
		this.appTx = appTx;
	}

	public Produit getCodProdui() {
		return this.codProdui;
	}

	public void setCodProdui(Produit codProdui) {
		this.codProdui = codProdui;
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

	public Date getDatMaj() {
		return this.datMaj;
	}

	public void setDatMaj(Date datMaj) {
		this.datMaj = datMaj;
	}

	public String getEchPolice() {
		return this.echPolice;
	}

	public void setEchPolice(String echPolice) {
		this.echPolice = echPolice;
	}

	public String getEtaPolice() {
		return this.etaPolice;
	}

	public void setEtaPolice(String etaPolice) {
		this.etaPolice = etaPolice;
	}

	public String getModePaye() {
		return this.modePaye;
	}

	public void setModePaye(String modePaye) {
		this.modePaye = modePaye;
	}

	public BigDecimal getMtAcc() {
		return this.mtAcc;
	}

	public void setMtAcc(BigDecimal mtAcc) {
		this.mtAcc = mtAcc;
	}

	public double getTauxPrime() {
		return this.tauxPrime;
	}

	public void setTauxPrime(double tauxPrime) {
		this.tauxPrime = tauxPrime;
	}

	public String getTaxePol() {
		return this.taxePol;
	}

	public void setTaxePol(String taxePol) {
		this.taxePol = taxePol;
	}

	public BigDecimal getTxCom() {
		return this.txCom;
	}

	public void setTxCom(BigDecimal txCom) {
		this.txCom = txCom;
	}

	public String getTypAmort() {
		return this.typAmort;
	}

	public void setTypAmort(String typAmort) {
		this.typAmort = typAmort;
	}

	public String getTypPolice() {
		return this.typPolice;
	}

	public void setTypPolice(String typPolice) {
		this.typPolice = typPolice;
	}

	public String getTypeTx() {
		return this.typeTx;
	}

	public void setTypeTx(String typeTx) {
		this.typeTx = typeTx;
	}

	public List<Adhesion> getAdhesions() {
		return this.adhesions;
	}

	public void setAdhesions(List<Adhesion> adhesions) {
		this.adhesions = adhesions;
	}

	public Adhesion addAdhesion(Adhesion adhesion) {
		getAdhesions().add(adhesion);
		adhesion.setPolice(this);

		return adhesion;
	}

	public Adhesion removeAdhesion(Adhesion adhesion) {
		getAdhesions().remove(adhesion);
		adhesion.setPolice(null);

		return adhesion;
	}

	public List<GarantieVie> getGarantieVies() {
		return this.garantieVies;
	}

	public void setGarantieVies(List<GarantieVie> garantieVies) {
		this.garantieVies = garantieVies;
	}

	public GarantieVie addGarantieVy(GarantieVie garantieVy) {
		getGarantieVies().add(garantieVy);
		garantieVy.setPolice(this);

		return garantieVy;
	}

	public GarantieVie removeGarantieVy(GarantieVie garantieVy) {
		getGarantieVies().remove(garantieVy);
		garantieVy.setPolice(null);

		return garantieVy;
	}

	public Convention getConvention() {
		return this.convention;
	}

	public void setConvention(Convention convention) {
		this.convention = convention;
	}

	public List<PoliceCredit> getPoliceCredits() {
		return this.policeCredits;
	}

	public void setPoliceCredits(List<PoliceCredit> policeCredits) {
		this.policeCredits = policeCredits;
	}

	public PoliceCredit addPoliceCredit(PoliceCredit policeCredit) {
		getPoliceCredits().add(policeCredit);
		policeCredit.setPolice(this);

		return policeCredit;
	}

	public PoliceCredit removePoliceCredit(PoliceCredit policeCredit) {
		getPoliceCredits().remove(policeCredit);
		policeCredit.setPolice(null);

		return policeCredit;
	}

	public List<Proposition> getPropositions() {
		return this.propositions;
	}

	public void setPropositions(List<Proposition> propositions) {
		this.propositions = propositions;
	}

	public Proposition addProposition(Proposition proposition) {
		getPropositions().add(proposition);
		proposition.setPolice(this);

		return proposition;
	}

	public Proposition removeProposition(Proposition proposition) {
		getPropositions().remove(proposition);
		proposition.setPolice(null);

		return proposition;
	}

	public Double getFga() {
		return fga;
	}

	public void setFga(Double fga) {
		this.fga = fga;
	}
	
	

}