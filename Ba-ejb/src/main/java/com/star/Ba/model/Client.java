package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the CLIENT database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
		@NamedQuery(name = "Client.findByIdentifiant", query = "SELECT c FROM Client c WHERE c.identifiant = :identifiant") })
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_CLIENT")
	@SequenceGenerator(name = "ID_CLIENT", sequenceName = "ID_CLIENT", allocationSize = 1)
	@Column(name = "ID_CLIENT")
	private long idClient;

	@Column(name = "ADR_CLIENT")
	private String adrClient;

	@Column(name = "CODE_P")
	private String codeP;

	@Temporal(TemporalType.DATE)
	@Column(name = "DAT_CREATION")
	private Date datCreation;

	@Temporal(TemporalType.DATE)
	@Column(name = "DAT_NAISS")
	private Date datNaiss;

	private String gouver;
    
	@Column(unique = true ) 
	private String identifiant;

	private String localite;

	private String matricule;

	@Column(name = "NOM_CLIENT")
	private String nomClient;

	@Column(name = "NUM_ADR")
	private BigDecimal numAdr;

	@Column(name = "PRENOM_CLIENT")
	private String prenomClient;

	private String sexe;

	private String tel;

	@Column(name = "TYPE_DOC")
	private String typeDoc;

	@Column(name = "TYPE_PERS")
	private String typePers;
    
	@Column(name = "MAIL")
	private String mail;
	
	@Column(name = "NCOMPTE")
	private String ncompte;

	// bi-directional many-to-one association to Adhesion
	@ManyToOne
	@JoinColumn(name = "TYPE_VOIE")
	private TypeVoie typeVoie=new TypeVoie();

	// bi-directional many-to-one association to Adherent
	@OneToMany(mappedBy = "client")
	private List<Adherent> adherents=new ArrayList<Adherent>();

	// bi-directional many-to-one association to Adhesion
	@OneToMany(mappedBy = "client")
	private List<Adhesion> adhesions=new ArrayList<Adhesion>();

	// bi-directional many-to-one association to Proposition
	@OneToMany(mappedBy = "client")
	private List<Proposition> propositions=new ArrayList<Proposition>();
	
	@ManyToOne 
	@JoinColumn(name="ID_PROFESSION")
	private ProfessionClient profession= new ProfessionClient();
	
	
//	@Column(name = "WS")
//	private String ws;

	public Client() {
	}

	public ProfessionClient getProfession() {
		return this.profession;
	}

	public void setProfession(ProfessionClient profession) {
		this.profession = profession;
	}

	public long getIdClient() {
		return this.idClient;
	}

	public void setIdClient(long idClient) {
		this.idClient = idClient;
	}

	public String getAdrClient() {
		return this.adrClient;
	}

	public void setAdrClient(String adrClient) {
		this.adrClient = adrClient;
	}

	public String getCodeP() {
		return this.codeP;
	}

	public void setCodeP(String codeP) {
		this.codeP = codeP;
	}

	public Date getDatCreation() {
		return this.datCreation;
	}

	public void setDatCreation(Date datCreation) {
		this.datCreation = datCreation;
	}

	public Date getDatNaiss() {
		return this.datNaiss;
	}

	public void setDatNaiss(Date datNaiss) {
		this.datNaiss = datNaiss;
	}

	public String getGouver() {
		return this.gouver;
	}

	public void setGouver(String gouver) {
		this.gouver = gouver;
	}

	public String getIdentifiant() {
		return this.identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getLocalite() {
		return this.localite;
	}

	public void setLocalite(String localite) {
		this.localite = localite;
	}

	public String getMatricule() {
		return this.matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getNomClient() {
		return this.nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public BigDecimal getNumAdr() {
		return this.numAdr;
	}

	public void setNumAdr(BigDecimal numAdr) {
		this.numAdr = numAdr;
	}

	public String getPrenomClient() {
		return this.prenomClient;
	}

	public void setPrenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
	}

	public String getSexe() {
		return this.sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
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

	public String getTypePers() {
		return this.typePers;
	}

	public void setTypePers(String typePers) {
		this.typePers = typePers;
	}

	public TypeVoie getTypeVoie() {
		return this.typeVoie;
	}

	public void setTypeVoie(TypeVoie typeVoie) {
		this.typeVoie = typeVoie;
	}

	public List<Adherent> getAdherents() {
		return this.adherents;
	}

	public void setAdherents(List<Adherent> adherents) {
		this.adherents = adherents;
	}

	public Adherent addAdherent(Adherent adherent) {
		getAdherents().add(adherent);
		adherent.setClient(this);

		return adherent;
	}

	public Adherent removeAdherent(Adherent adherent) {
		getAdherents().remove(adherent);
		adherent.setClient(null);

		return adherent;
	}

	
	
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNcompte() {
		return ncompte;
	}

	public void setNcompte(String ncompte) {
		this.ncompte = ncompte;
	}

	public List<Adhesion> getAdhesions() {
		return this.adhesions;
	}

	public void setAdhesions(List<Adhesion> adhesions) {
		this.adhesions = adhesions;
	}
	
	
	

//	public String getWs() {
//		return ws;
//	}
//
//	public void setWs(String ws) {
//		this.ws = ws;
//	}

	public Adhesion addAdhesion(Adhesion adhesion) {
		getAdhesions().add(adhesion);
		adhesion.setClient(this);

		return adhesion;
	}

	public Adhesion removeAdhesion(Adhesion adhesion) {
		getAdhesions().remove(adhesion);
		adhesion.setClient(null);

		return adhesion;
	}

	public List<Proposition> getPropositions() {
		return this.propositions;
	}

	public void setPropositions(List<Proposition> propositions) {
		this.propositions = propositions;
	}

	public Proposition addProposition(Proposition proposition) {
		getPropositions().add(proposition);
		proposition.setClient(this);

		return proposition;
	}

	public Proposition removeProposition(Proposition proposition) {
		getPropositions().remove(proposition);
		proposition.setClient(null);

		return proposition;
	}

	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", adrClient=" + adrClient
				+ ", codeP=" + codeP + ", datCreation=" + datCreation
				+ ", datNaiss=" + datNaiss + ", gouver=" + gouver
				+ ", identifiant=" + identifiant + ", localite=" + localite
				+ ", matricule=" + matricule + ", nomClient=" + nomClient
				+ ", numAdr=" + numAdr + ", prenomClient=" + prenomClient
				+ ", sexe=" + sexe + ", tel=" + tel + ", typeDoc=" + typeDoc
				+ ", typePers=" + typePers + ", typeVoie=" + typeVoie
				+ ", mail=" + mail + ",profession=" + profession + "]";
	}

}