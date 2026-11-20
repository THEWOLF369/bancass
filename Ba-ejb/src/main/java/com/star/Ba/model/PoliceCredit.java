package com.star.Ba.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;


/**
 * The persistent class for the POLICE_CREDIT database table.
 * 
 */
@Entity
@Table(name="POLICE_CREDIT")
@NamedQuery(name="PoliceCredit.findAll", query="SELECT p FROM PoliceCredit p")
public class PoliceCredit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_POLCREDIT")
	private long idPolcredit;

	//bi-directional many-to-one association to Adhesion
	@OneToMany(mappedBy="policeCredit")
	private List<Adhesion> adhesions;

	//bi-directional many-to-one association to Police
	@ManyToOne
	@JoinColumn(name="N_POLICE")
	private Police police;

	//bi-directional many-to-one association to TypeCredit
//	@ManyToOne
//	@JoinColumn(name="ID_TYPE_CREDIT")
//	private TypeCredit typeCredit;

	//bi-directional many-to-one association to Proposition
	@OneToMany(mappedBy="policeCredit")
	private List<Proposition> propositions;

	public PoliceCredit() {
	}

	public long getIdPolcredit() {
		return this.idPolcredit;
	}

	public void setIdPolcredit(long idPolcredit) {
		this.idPolcredit = idPolcredit;
	}

	public List<Adhesion> getAdhesions() {
		return this.adhesions;
	}

	public void setAdhesions(List<Adhesion> adhesions) {
		this.adhesions = adhesions;
	}

	public Adhesion addAdhesion(Adhesion adhesion) {
		getAdhesions().add(adhesion);
		adhesion.setPoliceCredit(this);

		return adhesion;
	}

	public Adhesion removeAdhesion(Adhesion adhesion) {
		getAdhesions().remove(adhesion);
		adhesion.setPoliceCredit(null);

		return adhesion;
	}

	public Police getPolice() {
		return this.police;
	}

	public void setPolice(Police police) {
		this.police = police;
	}

//	public TypeCredit getTypeCredit() {
//		return this.typeCredit;
//	}
//
//	public void setTypeCredit(TypeCredit typeCredit) {
//		this.typeCredit = typeCredit;
//	}

	public List<Proposition> getPropositions() {
		return this.propositions;
	}

	public void setPropositions(List<Proposition> propositions) {
		this.propositions = propositions;
	}

	public Proposition addProposition(Proposition proposition) {
		getPropositions().add(proposition);
		proposition.setPoliceCredit(this);

		return proposition;
	}

	public Proposition removeProposition(Proposition proposition) {
		getPropositions().remove(proposition);
		proposition.setPoliceCredit(null);

		return proposition;
	}

}