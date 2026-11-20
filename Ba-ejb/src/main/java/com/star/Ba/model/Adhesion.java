package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.star.Ba.model.TypeAbonnement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the ADHESION database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Adhesion.findAll", query = "SELECT a FROM Adhesion a"),
		@NamedQuery(name = "Adhesion.findByUtilisateur", query = "SELECT a FROM Adhesion a WHERE a.idUtilisateurs = :idUtilisateurs"),
		@NamedQuery(name = "Adhesion.findByPolice", query = "SELECT a FROM Adhesion a WHERE a.idUtilisateurs = :idUtilisateurs and a.police = :police") })
public class Adhesion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ADHESION")
	@SequenceGenerator(name = "ID_ADHESION", sequenceName = "ID_ADHESION", allocationSize = 1)
	@Column(name = "ID_ADHESION")
	private int idAdhesion;

	// bi-directional many-to-one association to quittance

	@OneToMany(mappedBy = "idAdhesion")
	// @Fetch(value = FetchMode.SUBSELECT)
	private List<Quittance> quittance = new ArrayList<Quittance>();
	
	@OneToMany(mappedBy = "adhesion", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ModePaiementHayya> modePaiementHayya = new ArrayList<ModePaiementHayya>();

	@Column(name = "CAPITAL")
	private Double capital;
	
	@Column(name = "COUT")
	private BigDecimal cout;

	@Temporal(TemporalType.DATE)
	@Column(name = "DAT_DEBUT")
	private Date datDebut;

	@Temporal(TemporalType.DATE)
	@Column(name = "DAT_FIN")
	private Date datFin;

	@Column(name = "DAT_MAJ")
	private BigDecimal datMaj;

	@Column(name = "DUR_PAIEMT")
	private BigDecimal durPaiemt;

	@Column(name = "DUREE")
	private double duree;

	@Column(name = "ECH_ANNUEL")
	private String echAnnuel;

	@Temporal(TemporalType.DATE)
	@Column(name = "EFF_CAP")
	private Date effCap;

	@Column(name = "ETAT")
	private String etat;

	@Column(name = "FRA_POLICE")
	private String fraPolice;

	private String franchise;

	@Column(name = "ID_UTILISATEURS")
	private int idUtilisateurs;

	@ManyToOne
	@JoinColumn(name = "ID_UTILISATEURS", updatable = false, insertable = false)
	private Utilisateurs utilisateur;

	@Column(name = "MAJORATION")
	private Double majoration;

	@Column(name = "MT_PRIME")
	private Double mtPrime;

	@Column(name = "DATE_CREATION")
	private Date datcreation;

	@Column(name = "POIDS")
	private BigDecimal poids;

	@Column(name = "TAILLE")
	private BigDecimal taille;

	@Column(name = "TAUX_INTER")
	private Double tauxInter;

	@Column(name = "TAUX_PRIME")
	private Double tauxPrime;

	@Column(name = "TAUX_REMIS")
	private Double tauxRemis;

	@Column(name = "TAUX_SPRIM")
	private Double tauxSprim;

	@Column(name = "DESTINATION")
	private String destination;
	@Column(name = "TEL")
	private String telphone;

	@Column(name = "PERSONNE_PRV")
	private String personnePrv;

	@Column(name = "NOMBRE_PERSONNE")
	private BigDecimal nombrePersonne;

	private boolean impr;
	@Column(name = "LOCALITE")
	private String localite;
	@Column(name = "GOUVER")
	private String gouver;
	@Column(name = "ADRESSE")
	private String adresse;
	@Column(name = "NADHESION")
	private String nadhesion;

	@Column(name = "CODE_P")
	private String codeP;
	@Column(name = "REF_CREDIT")
	private String ref_credit;
	@Column(name = "NUM_DECISION")
	private String num_decision;
	@Column(name = "FGA")
	private Double fga;
	
	@Column(name = "COUVERTURE")
	private Double couverture;

	@Column(name = "SITUATION_RISQUE")
	private String situation_risque;

	public BigDecimal getCout() {
		return cout;
	}

	public void setCout(BigDecimal cout) {
		this.cout = cout;
	}

	// bi-directional many-to-one association to Adherent
	@OneToMany(mappedBy = "adhesion", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Adherent> adherents = new ArrayList<Adherent>();

	// bi-directional many-to-one association to Beneficiaire
	@OneToMany(mappedBy = "adhesion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Beneficiaire> beneficiaires = new ArrayList<Beneficiaire>();

	// bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name = "ID_CLIENT")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "TYPE_ABONNEMENT")
	private TypeAbonnement typeAbonnement;

	// bi-directional many-to-one association to Police
	@ManyToOne
	@JoinColumn(name = "N_POLICE")
	private Police police;

	// bi-directional many-to-one association to Beneficiaire
	@OneToMany(mappedBy = "adhesion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<GarantieAvj> garantieAvjs = new ArrayList<GarantieAvj>();

	// bi-directional many-to-one association to Beneficiaire
	@OneToMany(mappedBy = "proposition", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<GarantieMrh> garantieMrh = new ArrayList<GarantieMrh>();

	// bi-directional many-to-one association to PoliceCredit
	@ManyToOne
	@JoinColumn(name = "ID_POLCREDIT")
	private PoliceCredit policeCredit;

	// bi-directional many-to-one association to Amortissement
	@OneToMany(mappedBy = "adhesion", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Amortissement> amortissements = new ArrayList<Amortissement>();

	@Column(name = "TAXE")
	private Double taxe;
	
	@Column(name = "PRIMETOT")
	private Double primetot;

	// bi-directional many-to-one association to Pack
	@ManyToOne
	@JoinColumn(name = "ID_PACK")
	private Pack pack;

	// bi-directional many-to-one association to DetailMrh
	@ManyToOne
	@JoinColumn(name = "ID_DETAIL_MRH")
	private DetailMrh detailMrh;

	// bi-directional many-to-one association to DetailMrh
	@ManyToOne
	@JoinColumn(name = "ID_DETAIL_AVJ")
	private DetailAvj detailAvj;

	// bi-directional many-to-one association to Proposition
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_PROPOSITION")
	private Proposition proposition;

	public Adhesion() {
	}

	public String getRef_credit() {
		return ref_credit;
	}

	public void setRef_credit(String ref_credit) {
		this.ref_credit = ref_credit;
	}

	public String getNum_decision() {
		return num_decision;
	}

	public void setNum_decision(String num_decision) {
		this.num_decision = num_decision;
	}

	public int getIdAdhesion() {
		return this.idAdhesion;
	}

	public void setIdAdhesion(int idAdhesion) {
		this.idAdhesion = idAdhesion;
	}

	public Double getCapital() {
		return this.capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public Date getDatDebut() {
		return this.datDebut;
	}

	public void setDatDebut(Date datDebut) {
		this.datDebut = datDebut;
	}

	public Date getDatFin() {
		return this.datFin;
	}

	public void setDatFin(Date datFin) {
		this.datFin = datFin;
	}

	public BigDecimal getDatMaj() {
		return this.datMaj;
	}

	public void setDatMaj(BigDecimal datMaj) {
		this.datMaj = datMaj;
	}

	public BigDecimal getDurPaiemt() {
		return this.durPaiemt;
	}

	public void setDurPaiemt(BigDecimal durPaiemt) {
		this.durPaiemt = durPaiemt;
	}

	public double getDuree() {
		return this.duree;
	}

	public void setDuree(double duree) {
		this.duree = duree;
	}

	public String getEchAnnuel() {
		return this.echAnnuel;
	}

	public void setEchAnnuel(String echAnnuel) {
		this.echAnnuel = echAnnuel;
	}

	public Date getEffCap() {
		return this.effCap;
	}

	public void setEffCap(Date effCap) {
		this.effCap = effCap;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getFraPolice() {
		return this.fraPolice;
	}

	public void setFraPolice(String fraPolice) {
		this.fraPolice = fraPolice;
	}

	public String getFranchise() {
		return this.franchise;
	}

	public void setFranchise(String franchise) {
		this.franchise = franchise;
	}

	public int getIdUtilisateurs() {
		return this.idUtilisateurs;
	}

	public void setIdUtilisateurs(int idUtilisateurs) {
		this.idUtilisateurs = idUtilisateurs;
	}

	public Double getMajoration() {
		return this.majoration;
	}

	public void setMajoration(Double majoration) {
		this.majoration = majoration;
	}

	public Double getMtPrime() {
		return this.mtPrime;
	}

	public void setMtPrime(Double mtPrime) {
		this.mtPrime = mtPrime;
	}

	public BigDecimal getPoids() {
		return this.poids;
	}

	public void setPoids(BigDecimal poids) {
		this.poids = poids;
	}

	public BigDecimal getTaille() {
		return this.taille;
	}

	public void setTaille(BigDecimal taille) {
		this.taille = taille;
	}

	public Double getTauxInter() {
		return this.tauxInter;
	}

	public void setTauxInter(Double tauxInter) {
		this.tauxInter = tauxInter;
	}

	public Double getTauxPrime() {
		return this.tauxPrime;
	}

	public void setTauxPrime(Double tauxPrime) {
		this.tauxPrime = tauxPrime;
	}

	public Double getTauxRemis() {
		return this.tauxRemis;
	}

	public void setTauxRemis(Double tauxRemis) {
		this.tauxRemis = tauxRemis;
	}

	public Double getTauxSprim() {
		return this.tauxSprim;
	}

	public void setTauxSprim(Double tauxSprim) {
		this.tauxSprim = tauxSprim;
	}

	public List<Adherent> getAdherents() {
		return this.adherents;
	}

	public void setAdherents(List<Adherent> adherents) {
		this.adherents = adherents;
	}

	public Adherent addAdherent(Adherent adherent) {
		getAdherents().add(adherent);
		adherent.setAdhesion(this);

		return adherent;
	}

	public Adherent removeAdherent(Adherent adherent) {
		getAdherents().remove(adherent);
		adherent.setAdhesion(null);

		return adherent;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Pack getPack() {
		return this.pack;
	}

	public void setPack(Pack pack) {
		this.pack = pack;
	}

	public Police getPolice() {
		return this.police;
	}

	public void setPolice(Police police) {
		this.police = police;
	}

	public PoliceCredit getPoliceCredit() {
		return this.policeCredit;
	}

	public void setPoliceCredit(PoliceCredit policeCredit) {
		this.policeCredit = policeCredit;
	}

	public List<Amortissement> getAmortissements() {
		return this.amortissements;
	}

	public void setAmortissements(List<Amortissement> amortissements) {
		this.amortissements = amortissements;
	}

	public Amortissement addAmortissement(Amortissement amortissement) {
		getAmortissements().add(amortissement);
		amortissement.setAdhesion(this);

		return amortissement;
	}

	public Amortissement removeAmortissement(Amortissement amortissement) {
		getAmortissements().remove(amortissement);
		amortissement.setAdhesion(null);

		return amortissement;
	}

	public List<Beneficiaire> getBeneficiaires() {
		return this.beneficiaires;
	}

	public void setBeneficiaires(List<Beneficiaire> beneficiaires) {
		this.beneficiaires = beneficiaires;
	}

	public Beneficiaire addBeneficiaire(Beneficiaire beneficiaire) {
		getBeneficiaires().add(beneficiaire);
		beneficiaire.setAdhesion(this);

		return beneficiaire;
	}

	public Beneficiaire removeBeneficiaire(Beneficiaire beneficiaire) {
		getBeneficiaires().remove(beneficiaire);
		beneficiaire.setAdhesion(null);

		return beneficiaire;
	}

	public GarantieAvj addGarantieAvj(GarantieAvj garantieAvj) {
		getGarantieAvjs().add(garantieAvj);
		garantieAvj.setAdhesion(this);

		return garantieAvj;
	}

	public GarantieAvj removeGarantieAvj(GarantieAvj garantieAvj) {
		getGarantieAvjs().remove(garantieAvj);
		garantieAvj.setAdhesion(null);

		return garantieAvj;
	}

	public boolean isImpr() {
		return impr;
	}

	public void setImpr(boolean impr) {
		this.impr = impr;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getPersonnePrv() {
		return personnePrv;
	}

	public void setPersonnePrv(String personnePrv) {
		this.personnePrv = personnePrv;
	}

	public BigDecimal getNombrePersonne() {
		return nombrePersonne;
	}

	public void setNombrePersonne(BigDecimal nombrePersonne) {
		this.nombrePersonne = nombrePersonne;
	}

	public TypeAbonnement getTypeAbonnement() {
		return typeAbonnement;
	}

	public void setTypeAbonnement(TypeAbonnement typeAbonnement) {
		this.typeAbonnement = typeAbonnement;
	}

	public String getNadhesion() {
		return nadhesion;
	}

	public void setNadhesion(String nadhesion) {
		this.nadhesion = nadhesion;
	}

	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Adhesion(int idAdhesion, double capital, Date datDebut, Date datFin,
			double duree, String etat, String franchise, int idUtilisateurs,
			Double majoration, Double mtPrime, Double tauxInter,
			Double tauxPrime, Double tauxSprim, Client client, Police police,
			String nadhesion, Date datcreation) {
		super();
		this.idAdhesion = idAdhesion;
		this.capital = capital;
		this.datDebut = datDebut;
		this.datFin = datFin;
		this.duree = duree;
		this.etat = etat;
		this.franchise = franchise;
		this.idUtilisateurs = idUtilisateurs;
		this.majoration = majoration;
		this.mtPrime = mtPrime;
		this.tauxInter = tauxInter;
		this.tauxPrime = tauxPrime;
		this.tauxSprim = tauxSprim;
		this.client = client;
		this.police = police;
		this.nadhesion = nadhesion;
		this.datcreation = datcreation;

	}

	public Adhesion(int idAdhesion, Double capital, Date datDebut, Date datFin,
			double duree, String etat, int idUtilisateurs, Double mtPrime,
			String localite, String gouver, String adresse, String codeP,
			Client client, Police police, String nadhesion, Double taxe,
			Double primetot, Date datcreation) {
		super();
		this.idAdhesion = idAdhesion;
		this.capital = capital;
		this.datDebut = datDebut;
		this.datFin = datFin;
		this.duree = duree;
		this.etat = etat;
		this.idUtilisateurs = idUtilisateurs;
		this.mtPrime = mtPrime;
		this.localite = localite;
		this.gouver = gouver;
		this.adresse = adresse;
		this.codeP = codeP;
		this.client = client;
		this.police = police;
		this.nadhesion = nadhesion;
		this.taxe = taxe;
		this.primetot = primetot;
		this.datcreation = datcreation;
	}

	public String getCodeP() {
		return this.codeP;
	}

	public void setCodeP(String codeP) {
		this.codeP = codeP;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getLocalite() {
		return this.localite;
	}

	public void setLocalite(String localite) {
		this.localite = localite;
	}

	public String getGouver() {
		return this.gouver;
	}

	public void setGouver(String gouver) {
		this.gouver = gouver;
	}

	// @Override
	// public String toString() {
	// return "Adhesion [idAdhesion=" + idAdhesion + ", capital=" + capital
	// + ", datDebut=" + datDebut + ", datFin=" + datFin + ", datMaj="
	// + datMaj + ", durPaiemt=" + durPaiemt + ", duree=" + duree
	// + ", echAnnuel=" + echAnnuel + ", effCap=" + effCap + ", etat="
	// + etat + ", fraPolice=" + fraPolice + ", franchise="
	// + franchise + ", idUtilisateurs=" + idUtilisateurs
	// + ", majoration=" + majoration + ", mtPrime=" + mtPrime
	// + ", poids=" + poids + ", taille=" + taille + ", tauxInter="
	// + tauxInter + ", tauxPrime=" + tauxPrime + ", tauxRemis="
	// + tauxRemis + ", tauxSprim=" + tauxSprim + ", impr=" + impr
	// + ", localite=" + localite + ", gouver=" + gouver
	// + ", adresse=" + adresse + ", nadhesion=" + nadhesion
	// + ", codeP=" + codeP + ", adherents=" + adherents + ", client="
	// + client + ", police=" + police + ", policeCredit="
	// + policeCredit + ", amortissements=" + amortissements
	// + ", beneficiaires=" + beneficiaires + ", taxe=" + taxe
	// + ", primetot=" + primetot + "]";
	// }

	public Date getDatcreation() {
		return datcreation;
	}

	public void setDatcreation(Date datcreation) {
		this.datcreation = datcreation;
	}

	public Double getTaxe() {
		return taxe;
	}

	public void setTaxe(Double taxe) {
		this.taxe = taxe;
	}

	public Double getPrimetot() {
		return primetot;
	}

	public void setPrimetot(Double primetot) {
		this.primetot = primetot;
	}

	public DetailMrh getDetailMrh() {
		return this.detailMrh;
	}

	public void setDetailMrh(DetailMrh detailMrh) {
		this.detailMrh = detailMrh;
	}

	public DetailAvj getDetailAvj() {
		return detailAvj;
	}

	public void setDetailAvj(DetailAvj detailAvj) {
		this.detailAvj = detailAvj;
	}

	public Proposition getProposition() {
		return this.proposition;
	}

	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}

	public String getNumeroAdhesion() {

		// String idProduit=new
		// Long(getPolice().getCodProdui().getIdProduit()).toString();
		// String newNomber = null;

		// System.out.println("idProduit "+idProduit);

		// if(idProduit.equalsIgnoreCase("1")){

		Integer currentNomber;
		String codeProduit = new Long(getPolice().getCodProdui().getIdProduit())
				.toString();
		// System.out.println("codeProduit1 "+codeProduit);

		String structure = "00000000";
		currentNomber = getIdAdhesion();

		String number = currentNomber.toString();

		String newNomber = codeProduit
				+ structure.substring(0, structure.length() - number.length())
				+ number;
		/*
		 * }else{
		 * 
		 * Integer currentNomber ; String codeProduit=new
		 * Long(getPolice().getCodProdui().getCodeProduit()).toString();
		 * System.out.println("codeProduit2 "+codeProduit);
		 * 
		 * String structure="000000"; currentNomber=getIdAdhesion();
		 * 
		 * 
		 * String number = currentNomber.toString();
		 * 
		 * newNomber =codeProduit+ structure.substring(0, structure.length() -
		 * number.length()) + number;
		 * 
		 * }
		 */

		return newNomber;
	}

	public List<GarantieAvj> getGarantieAvjs() {
		return garantieAvjs;
	}

	public void setGarantieAvjs(List<GarantieAvj> garantieAvjs) {
		this.garantieAvjs = garantieAvjs;
	}

	public List<GarantieMrh> getGarantieMrh() {
		return garantieMrh;
	}

	public void setGarantieMrh(List<GarantieMrh> garantieMrh) {
		this.garantieMrh = garantieMrh;
	}

	public List<Quittance> getQuittance() {
		return quittance;
	}

	public void setQuittance(List<Quittance> quittance) {
		this.quittance = quittance;
	}

	public Double getFga() {
		return fga;
	}

	public void setFga(Double fga) {
		this.fga = fga;
	}

	public Double getCouverture() {
		return couverture;
	}

	public void setCouverture(Double couverture) {
		this.couverture = couverture;
	}

	public String getSituation_risque() {
		return situation_risque;
	}

	public void setSituation_risque(String situation_risque) {
		this.situation_risque = situation_risque;
	}

	public List<ModePaiementHayya> getModePaiementHayya() {
		return modePaiementHayya;
	}

	public void setModePaiementHayya(List<ModePaiementHayya> modePaiementHayya) {
		this.modePaiementHayya = modePaiementHayya;
	}

	
	
}