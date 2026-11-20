package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.star.Ba.dao.PropositionDAO;





/**
 * The persistent class for the PROPOSITION database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Proposition.findAll", query = "SELECT p FROM Proposition p"),
	@NamedQuery(name = "Proposition.findByUtilisateur", query = "SELECT p FROM Proposition p WHERE p.idUtilisateurs = :idUtilisateurs"), 
	@NamedQuery(name = "Proposition.findByPolice", query = "SELECT p FROM Proposition p WHERE p.idUtilisateurs = :idUtilisateurs and p.police = :police") })
public class Proposition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PROPOSITION")
	@SequenceGenerator(name = "ID_PROPOSITION", sequenceName = "ID_PROPOSITION", allocationSize = 1)
	@Column(name = "ID_PROPOSITION")
	private int idProposition;

	//private long idProposition;
	
	

	private double capital;

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

	private double duree;
	@Column(name="DATE_CREATION")
	private Date datcreation;
	
	@Column(name = "ECH_ANNUEL")
	private String echAnnuel;

	@Column(name = "NPROPOSITION")
	private String nProposition;
	@Temporal(TemporalType.DATE)
	@Column(name = "EFF_CAP")
	private Date effCap;

	private String etat;

	@Column(name = "FRA_POLICE")
	private String fraPolice;

	private String franchise;

	@Column(name = "ID_UTILISATEURS")
	private int idUtilisateurs;

	@ManyToOne
	@JoinColumn(name="ID_UTILISATEURS", updatable=false , insertable=false )
	private Utilisateurs utilisateur;
	
	private double majoration;

	@Column(name = "MT_PRIME")
	private Double mtPrime;

	private BigDecimal poids;

	private BigDecimal taille;

	@Column(name = "TAUX_INTER")
	private Double tauxInter;

	@Column(name = "TAUX_PRIME")
	private Double tauxPrime;

	@Column(name = "TAUX_REMIS")
	private Double tauxRemis;

	@Column(name = "TAUX_SPRIM")
	private Double tauxSprim;
	@Column(name="DESTINATION")
	private String destination;
	@ManyToOne
	@JoinColumn(name="TYPE_ABONNEMENT")
	private TypeAbonnement typeAbonnement;
	@Column(name="NOMBRE_PERSONNE")
	private BigDecimal nombrePersonne;
	
	// bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name = "ID_CLIENT" )
	private Client client;

	// bi-directional many-to-one association to Beneficiaire
		@OneToMany(mappedBy = "proposition", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
		@Fetch(value = FetchMode.SUBSELECT)
		private List<GarantieAvj> garantieAvjs = new ArrayList<GarantieAvj>();
		
		
		// bi-directional many-to-one association to Beneficiaire
		@OneToMany(mappedBy = "proposition", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
		@Fetch(value = FetchMode.SUBSELECT)
		private List<GarantieMrh> garantieMrh = new ArrayList<GarantieMrh>();

	// bi-directional many-to-one association to Police
	@ManyToOne
	@JoinColumn(name = "N_POLICE")
	private Police police;

	// bi-directional many-to-one association to PoliceCredit
	@ManyToOne
	@JoinColumn(name = "ID_POLCREDIT")
	private PoliceCredit policeCredit;
	
	// bi-directional many-to-one association to Beneficiaire
	@OneToMany(mappedBy = "propositions", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Beneficiaire> beneficiaires = new ArrayList<Beneficiaire>();
		
	//bi-directional many-to-one association to Adherent
	//(mappedBy="propositions",cascade = {CascadeType.ALL} , fetch = FetchType.EAGER)
    //@Fetch(value = FetchMode.SUBSELECT)
	/*@OneToMany(mappedBy = "propositions", fetch = FetchType.LAZY)
	private List<Adherent> adherents = new ArrayList<Adherent>();*/
		
	
	@Column(name="TEL")
	private String telphone;
	
	@Column(name="ID_GARPACK")
	private Boolean notifie;
	
	@Column(name="PERSONNE_PRV")
	private String personnePrv;
	
	private String localite;
	private String gouver;
	private String adresse;
	private Double taxe;
	private Double primetot;
	private BigDecimal cout;
	@Column(name = "CODE_P")
	private String codeP;
	
		
//	//bi-directional many-to-one association to DetailAvj
//		@OneToMany(mappedBy="proposition")
//		private List<DetailAvj> detailAvjs;
//
//		//bi-directional many-to-one association to DetailMrh_test
//		@OneToMany(mappedBy="proposition")
//		private List<DetailMrh> detailMrhs;

		//bi-directional many-to-one association to DetailAvj
		@ManyToOne
		@JoinColumn(name="ID_DETAIL_AVJ")
		private DetailAvj detailAvj;

		//bi-directional many-to-one association to DetailMrh_test
		@ManyToOne
		@JoinColumn(name="ID_DETAIL_MRH")
		private DetailMrh detailMrh;

		//bi-directional many-to-one association to Pack_test
		@ManyToOne
		@JoinColumn(name="ID_PACK")
		private Pack pack;
		
		@Column(name = "COUVERTURE")
		private Double couverture;
		
	public Proposition() {
	}
	
	
	public Proposition(Adhesion adhesion) {
		this.police = adhesion.getPolice();
		this.client = adhesion.getClient();
	
		this.idUtilisateurs = adhesion.getIdUtilisateurs();
		this.policeCredit = adhesion.getPoliceCredit();
		this.capital = adhesion.getCapital();
		this.datDebut = adhesion.getDatDebut();
		this.datFin = adhesion.getDatFin();
		this.duree = adhesion.getDuree();
		this.fraPolice = adhesion.getFraPolice();
		this.durPaiemt = adhesion.getDurPaiemt();
		this.etat = adhesion.getEtat();
		this.datMaj = adhesion.getDatMaj();
		this.tauxPrime = adhesion.getTauxPrime();
		this.tauxSprim = adhesion.getTauxSprim();
		this.tauxRemis = adhesion.getTauxRemis();
		this.tauxInter = adhesion.getTauxInter();
		this.echAnnuel = adhesion.getEchAnnuel();
		this.majoration = adhesion.getMajoration();
		this.franchise = adhesion.getFranchise();
		this.effCap = adhesion.getEffCap();
		this.mtPrime = adhesion.getMtPrime();
		this.poids = adhesion.getPoids();
		this.taille = adhesion.getTaille();
		this.personnePrv=adhesion.getPersonnePrv();
		this.durPaiemt=adhesion.getDurPaiemt();
		this.nombrePersonne=adhesion.getNombrePersonne();
		this.destination=adhesion.getDestination();
		this.telphone=adhesion.getTelphone();
		this.destination=adhesion.getDestination();
		this.typeAbonnement=adhesion.getTypeAbonnement();
		
		
	}


	public Boolean getNotifie() {
		return notifie;
	}


	public void setNotifie(Boolean notifie) {
		this.notifie = notifie;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public TypeAbonnement getTypeAbonnement() {
		return typeAbonnement;
	}


	public void setTypeAbonnement(TypeAbonnement typeAbonnement) {
		this.typeAbonnement = typeAbonnement;
	}

	
	public BigDecimal getNombrePersonne() {
		return nombrePersonne;
	}


	public void setNombrePersonne(BigDecimal nombrePersonne) {
		this.nombrePersonne = nombrePersonne;
	}


	public int getIdProposition() {
		return this.idProposition;
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


	public void setIdProposition(int idProposition) {
		this.idProposition = idProposition;
	}

	public double getCapital() {
		return this.capital;
	}

	public void setCapital(double capital) {
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

	public double getMajoration() {
		return this.majoration;
	}

	public void setMajoration(double majoration) {
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

	public double getTauxSprim() {
		return this.tauxSprim;
	}

	public void setTauxSprim(double tauxSprim) {
		this.tauxSprim = tauxSprim;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
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
	
	
	
	public Date getDatcreation() {
		return datcreation;
	}

	public void setDatcreation(Date datcreation) {
		this.datcreation = datcreation;
	}

	public String getnProposition() {
		return nProposition;
	}


	public void setnProposition(String nProposition) {
		this.nProposition = nProposition;
	}


	public Proposition(Adhesion adhesion,String id) {
		this.capital = adhesion.getCapital();
		this.datDebut = adhesion.getDatDebut();
		this.datFin = adhesion.getDatFin();
		this.duree = adhesion.getDuree();
		this.etat = adhesion.getEtat();
		this.idUtilisateurs = adhesion.getIdUtilisateurs();
		this.mtPrime = adhesion.getMtPrime();
		this.tauxPrime = adhesion.getTauxPrime();
		this.localite = adhesion.getLocalite();
		this.gouver = adhesion.getGouver();
		this.adresse = adhesion.getAdresse();
		this.codeP = adhesion.getCodeP();
		this.client = adhesion.getClient();
		this.police = adhesion.getPolice();
		this.taxe=adhesion.getTaxe();
		this.primetot=adhesion.getPrimetot();
		this.datcreation=adhesion.getDatcreation();
		
	}
	
	public GarantieAvj addGarantieAvj(GarantieAvj garantieAvj) {
		getGarantieAvjs().add(garantieAvj);
		garantieAvj.setProposition(this);

		return garantieAvj;
	}

	public GarantieAvj removeGarantieAvj(GarantieAvj garantieAvj) {
		getGarantieAvjs().remove(garantieAvj);
		garantieAvj.setProposition(null);

		return garantieAvj;
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

	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
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
	
	public BigDecimal getCout() {
		return this.cout;
	}

	public void setCout(BigDecimal cout) {
		this.cout = cout;
	}
	public DetailAvj getDetailAvj() {
		return this.detailAvj;
	}

	public void setDetailAvj(DetailAvj detailAvj) {
		this.detailAvj = detailAvj;
	}

	public DetailMrh getDetailMrh() {
		return this.detailMrh;
	}

	public void setDetailMrh(DetailMrh detailMrh) {
		this.detailMrh = detailMrh;
	}

	public Pack getPack() {
		return this.pack;
	}

	public void setPack(Pack pack) {
		this.pack = pack;
	}

	//bi-directional many-to-one association to Adhesion
	//	@OneToMany(mappedBy="proposition", updatable=false , insertable=false)
//		
//		private List<Adhesion> adhesions;
//		public List<Adhesion> getAdhesions() {
//			return this.adhesions;
//		}
//
//		public void setAdhesions(List<Adhesion> adhesions) {
//			this.adhesions = adhesions;
//		}


	public List<Beneficiaire> getBeneficiaires() {
		return beneficiaires;
	}


	public void setBeneficiaires(List<Beneficiaire> beneficiaires) {
		this.beneficiaires = beneficiaires;
	}


	/*public List<Adherent> getAdherents() {
		return adherents;
	}


	public void setAdherents(List<Adherent> adherents) {
		this.adherents = adherents;
	}
	*/
	

	public Double getCouverture() {
		return couverture;
	}


	public void setCouverture(Double couverture) {
		this.couverture = couverture;
	}


	public String getNumeroProposition() {
		
		
        Integer currentNomber ;
		String codeProduit=new Long(getPolice().getCodProdui().getIdProduit()).toString();
        String structure="00000000";
		currentNomber= getIdProposition();
		
	        
		String number = currentNomber.toString();

		String newNomber =codeProduit+ structure.substring(0,
				structure.length() - number.length())
				+ number;

		
		return newNomber;
	}
		
}