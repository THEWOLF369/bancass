package com.star.Ba.model;

 
 

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.star.Ba.commun.OtpStatus;
import com.star.Ba.commun.OtpType;

@Entity
@Table(name = "P_USER_OTP")
public class UserOtp implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_OTP")
    @SequenceGenerator(name = "SEQ_USER_OTP", sequenceName = "SEQ_USER_OTP", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "OTP_CODE")
    private String otpCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "OTP_TYPE")
    private OtpType otpType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OtpStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRES_AT")
    private Date expiresAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALIDATED_AT")
    private Date validatedAt;

    @Column(name = "ATTEMPTS")
    private Integer attempts;

    @Column(name = "MAX_ATTEMPTS")
    private Integer maxAttempts;

//    @Column(name = "IS_USED")
//    private Integer isUsed;
    
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "USED_AT")
    private Date usedAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    // GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public OtpType getOtpType() {
        return otpType;
    }

    public void setOtpType(OtpType otpType) {
        this.otpType = otpType;
    }

    public OtpStatus getStatus() {
        return status;
    }

    public void setStatus(OtpStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(Date validatedAt) {
        this.validatedAt = validatedAt;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

	public Date getUsedAt() {
		return usedAt;
	}

	public void setUsedAt(Date usedAt) {
		this.usedAt = usedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	

//    public Integer getIsUsed() {
//        return isUsed;
//    }
//
//    public void setIsUsed(Integer isUsed) {
//        this.isUsed = isUsed;
//    }
    
    
    
}
