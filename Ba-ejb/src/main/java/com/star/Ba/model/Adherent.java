package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the ADHERENT database table.
 * 
 */
@Entity
@Table(name="ASSURE")
@NamedQuery(name="Adherent.findAll", query="SELECT a FROM Adherent a")
public class Adherent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ADHERENT")
	@SequenceGenerator(name = "ID_ADHERENT", sequenceName = "ID_ADHERENT", allocationSize = 1)
	@Column(name="ID_ADHERENT")
	private long idAdherent;

	@Temporal(TemporalType.DATE)
	@Column(name="DAT_NAISS")
	private Date datNaiss;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_CREATION")
	private Date dateCreation;

	private String identifiant;

	private String matricule;

	@Column(name="NOM_ADHERENT")
	private String nomAdherent;

	@Column(name="PRENOM_ADHERENT")
	private String prenomAdherent;

	private String tel;

	@Column(name="TYPE_DOC")
	private String typeDoc;
	
	
	
	@Column(name="SEXE")
	private String sexe;
	
	
	
	@ManyToOne
	@JoinColumn(name="PROFESSION")
	private ProfessionClient profession= new ProfessionClient();
	
	@Column(name="ADRESSE")
	private String adresse;
	
	
	@Column(name="CODE_POSTAL")
	private String codePostal;
	
	@Column(name="LOCALITE")
	private String localite;
	
	
	@Column(name="GOUVER")
	private String gouver;
	
	
	@Column(name = "NUM_ADR")
	private BigDecimal numAdr;
	
	public BigDecimal getNumAdr() {
		return this.numAdr;
	}

	public void setNumAdr(BigDecimal numAdr) {
		this.numAdr = numAdr;
	}
	
	// bi-directional many-to-one association to Adhesion
		@ManyToOne
		@JoinColumn(name = "TYPE_VOIE")
		private TypeVoie typeVoie=new TypeVoie();
		
		
		public TypeVoie getTypeVoie() {
			return this.typeVoie;
		}

		public void setTypeVoie(TypeVoie typeVoie) {
			this.typeVoie = typeVoie;
		}
	
	

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	

	public ProfessionClient getProfession() {
		return profession;
	}

	public void setProfession(ProfessionClient profession) {
		this.profession = profession;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getLocalite() {
		return localite;
	}

	public void setLocalite(String localite) {
		this.localite = localite;
	}

	public String getGouver() {
		return gouver;
	}

	public void setGouver(String gouver) {
		this.gouver = gouver;
	}

	//bi-directional many-to-one association to Adhesion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_ADHESION")
	private Adhesion adhesion;

	//bi-directional many-to-one association to Adhesion
		
		//@JoinColumn(name = "ID_PROPOSITION", referencedColumnName = "ID_PROPOSITION")
	/*@ManyToOne
	@JoinColumn(name="ID_PROPOSITION")*/
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_PROPOSITION")*/
	@Column(name="ID_PROPOSITION")
	private Integer propositions;
	
	
	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="ID_CLIENT")
	private Client client;

	public Adherent() {
	}

	public long getIdAdherent() {
		return this.idAdherent;
	}

	public void setIdAdherent(long idAdherent) {
		this.idAdherent = idAdherent;
	}

	public Date getDatNaiss() {
		return this.datNaiss;
	}

	public void setDatNaiss(Date datNaiss) {
		this.datNaiss = datNaiss;
	}

	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getIdentifiant() {
		return this.identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMatricule() {
		return this.matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getNomAdherent() {
		return this.nomAdherent;
	}

	public void setNomAdherent(String nomAdherent) {
		this.nomAdherent = nomAdherent;
	}

	public String getPrenomAdherent() {
		return this.prenomAdherent;
	}

	public void setPrenomAdherent(String prenomAdherent) {
		this.prenomAdherent = prenomAdherent;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTypeDoc() {
		return this.typeDoc;
	}

	public void setTypeDoc(String typeDoc) {
		this.typeDoc = typeDoc;
	}

	public Adhesion getAdhesion() {
		return this.adhesion;
	}

	public void setAdhesion(Adhesion adhesion) {
		this.adhesion = adhesion;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Integer getPropositions() {
		return propositions;
	}

	public void setPropositions(Integer propositions) {
		this.propositions = propositions;
	}

	@Override
	public String toString() {
		return "Adherent [idAdherent=" + idAdherent + ", datNaiss=" + datNaiss
				+ ", dateCreation=" + dateCreation + ", identifiant="
				+ identifiant + ", matricule=" + matricule + ", nomAdherent="
				+ nomAdherent + ", prenomAdherent=" + prenomAdherent + ", tel="
				+ tel + ", typeDoc=" + typeDoc + ", sexe=" + sexe
				+ ", profession=" + profession + ", adresse=" + adresse
				+ ", codePostal=" + codePostal + ", localite=" + localite
				+ ", gouver=" + gouver + ", numAdr=" + numAdr + ", typeVoie="
				+ typeVoie + ", adhesion=" + adhesion + ", propositions="
				+ propositions + ", client=" + client + "]";
	}

}