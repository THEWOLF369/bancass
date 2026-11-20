package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "PASSWORD_HISTORY")
public class PasswordHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PASSWORD_HISTORY")
    @SequenceGenerator(name = "ID_PASSWORD_HISTORY", sequenceName = "ID_PASSWORD_HISTORY", allocationSize = 1)
    @Column(name = "ID_PASSWORD_HISTORY")
    private Long idPasswordHistory;

    @ManyToOne
    @JoinColumn(name = "ID_UTILISATEURS", nullable = false)
    private Utilisateurs utilisateurs;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHANGED_AT")
    private Date changedAt;

    @Column(name = "PASSWORD")
    private String password;

	public Long getIdPasswordHistory() {
		return idPasswordHistory;
	}

	public void setIdPasswordHistory(Long idPasswordHistory) {
		this.idPasswordHistory = idPasswordHistory;
	}

	public Utilisateurs getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(Utilisateurs utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public Date getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(Date changedAt) {
		this.changedAt = changedAt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



     
}