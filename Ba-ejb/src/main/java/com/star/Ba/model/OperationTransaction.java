package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the OPERATION_TRANSACTION database table.
 * 
 */
@Entity
@Table(name="OPERATION_TRANSACTION")
@NamedQuery(name="OperationTransaction.findAll", query="SELECT o FROM OperationTransaction o")
public class OperationTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_OP_TRANS")
	@SequenceGenerator(name = "ID_OP_TRANS", sequenceName = "ID_OP_TRANS", allocationSize = 1)
	
	@Column(name="ID_OP_TRANS")
	private long idOpTrans;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_TRANS")
	private Date dateTrans;

	//bi-directional many-to-one association to Pack
    @ManyToOne
	@JoinColumn(name="ID_ADHESION")
	private Adhesion idAdhesion;

	@Column(name="REF_OPERATION")
	private String refOperation;

	@Column(name="TYP_TRANS")
	private String typTrans;

	public OperationTransaction() {
	}

	public long getIdOpTrans() {
		return this.idOpTrans;
	}

	public void setIdOpTrans(long idOpTrans) {
		this.idOpTrans = idOpTrans;
	}

	public Date getDateTrans() {
		return this.dateTrans;
	}

	public void setDateTrans(Date dateTrans) {
		this.dateTrans = dateTrans;
	}

	public Adhesion getIdAdhesion() {
		return this.idAdhesion;
	}

	public void setIdAdhesion(Adhesion idAdhesion) {
		this.idAdhesion = idAdhesion;
	}

	public String getRefOperation() {
		return this.refOperation;
	}

	public void setRefOperation(String refOperation) {
		this.refOperation = refOperation;
	}

	public String getTypTrans() {
		return this.typTrans;
	}

	public void setTypTrans(String typTrans) {
		this.typTrans = typTrans;
	}

}