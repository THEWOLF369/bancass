package com.star.Ba.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.LocalDateTime;

@Entity
@Table(name="API_RESPONSES")
public class ApiResponseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "API_RESPONSES_SEQ")
	@SequenceGenerator(name = "API_RESPONSES_SEQ", sequenceName = "API_RESPONSES_SEQ", allocationSize = 1)	
	@Column(name="ID")
	private Long  id;
	
	
    @ManyToOne
	@JoinColumn(name="ID_ADHESION")
	private Adhesion idAdhesion;
	
	
	 @Column(name = "TRANSACTION_ID", nullable = false)
	    private String transactionId;

	    @Column(name = "ORDER_REFERENCE", nullable = false)
	    private String orderReference;

	    @Column(name = "HTTP_STATUS")
	    private Integer httpStatus;

	    @Lob
	    @Column(name = "INPUT_JSON")
	    private String inputJson;

	    @Lob
	    @Column(name = "RESPONSE_JSON")
	    private String responseJson;

	    @Column(name = "RESPONSE_TYPE")
	    private String responseType; // JSON, NON_JSON, NULL, EXCEPTION

	    @Lob
	    @Column(name = "ERROR_MESSAGE")
	    private String errorMessage;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "CREATED_AT")
	    private Date createdAt;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Adhesion getIdAdhesion() {
			return idAdhesion;
		}

		public void setIdAdhesion(Adhesion idAdhesion) {
			this.idAdhesion = idAdhesion;
		}

		public String getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}

		public String getOrderReference() {
			return orderReference;
		}

		public void setOrderReference(String orderReference) {
			this.orderReference = orderReference;
		}

		public Integer getHttpStatus() {
			return httpStatus;
		}

		public void setHttpStatus(Integer httpStatus) {
			this.httpStatus = httpStatus;
		}

		public String getInputJson() {
			return inputJson;
		}

		public void setInputJson(String inputJson) {
			this.inputJson = inputJson;
		}

		public String getResponseJson() {
			return responseJson;
		}

		public void setResponseJson(String responseJson) {
			this.responseJson = responseJson;
		}

		public String getResponseType() {
			return responseType;
		}

		public void setResponseType(String responseType) {
			this.responseType = responseType;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public Date getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
	    
	    
	    
	    

}
