package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

import com.star.Ba.commun.CryptPassword;
//import com.star.Ba.jsf.util.SituationUser;



/**
 * The persistent class for the UTILISATEURS database table.
 *
 */
@Entity
@Table(name = "UTILISATEURS")
@NamedQueries({
    @NamedQuery(name = "Utilisateurs.findAll", query = "SELECT u FROM Utilisateurs u"),
    @NamedQuery(name = "Utilisateurs.delete", query = "DELETE FROM Utilisateurs U WHERE u.idUtilisateurs = :idUtilisateurs")
})
public class Utilisateurs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_UTILISATEURS")
    @SequenceGenerator(name = "ID_UTILISATEURS", sequenceName = "ID_UTILISATEURS", allocationSize = 1)
    @Column(name = "ID_UTILISATEURS")
    private int idUtilisateurs;

    private BigDecimal connecte;

    private String login;

    @Column(name = "NBR_TENTATIVE")
    private int nbrTentative;

    private String nom;
    
    private String mail;
	private String prenom;
    private String password;
    @Transient
    private String newPassword="";
  //bi-directional many-to-one association to Profil
    
    @Transient
    private String decryptedPassword;
    
  	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@ManyToOne
  	@JoinColumn(name="PROFIL")
  	private Profil profil;

    private int situation;
    @Transient
    private SituationUser situationUser;
    @Transient
    private SituationUser situationUserToSave;
    //bi-directional many-to-one association to Agence
    @ManyToOne
    @JoinColumn(name = "ID_AGENCE")
    private Agence agence;

    
    @Column(name = "DATE_CREATION")
    private Date dateCreation;
    
    
    @Column(name = "DATE_DESACTIVATION")
    private Date dateDesactivation;
    
    
    @Column(name = "DATE_REACTIVATION")
    private Date dateReactivation;
    
    @Column(name = "DATE_LAST_CONNECTION")
    private Date dateLastConnection;
    
    
    @Column(name = "DATE_LAST_CHANGED_PWD")
    private Date dateLastChangedPwd;
    
    @Column(name = "SITUATION_BLOCAGE")
    private Integer situationBlocage = 0;
    
    
    @Transient
    private SituationBlocageUser situationBlocageUser;
    @Transient
    private SituationBlocageUser situationBlocageUserToSave;
    
    
    
    @Column(name = "DATE_BLOCAGE")
    private Date dateBlocage;
    
    @Column(name = "DATE_DEBLOCAGE")
    private Date dateDeblocage;
    
    
    @Column(name = "FIRST_LOGIN")
    private Integer firstLogin = 1;
    
    
    @OneToMany(mappedBy = "utilisateurs", cascade = CascadeType.ALL)
    private List<PasswordHistory> passwordHistories;
    
    
    
    @ManyToMany
    @JoinTable(
        name = "USER_PRODUIT",
        joinColumns = @JoinColumn(name = "ID_UTILISATEURS"),
        inverseJoinColumns = @JoinColumn(name = "ID_PRODUIT")
    )
    private List<Produit> produits = new ArrayList<>();
    
    
    public SituationUser getSituationUser() {
    	if(getSituation()==1){
    		situationUser=new SituationUser(1, "Activé");
    	}else{
    		situationUser=new SituationUser(2, "Désactivé");	
    	}
		return situationUser;
	}

	public void setSituationUser(SituationUser situationUser) {
		
		
		this.situationUser = situationUser;
	}

	public SituationUser getSituationUserToSave() {
		return situationUserToSave;
	}

	public void setSituationUserToSave(SituationUser situationUserToSave) {
		this.situationUserToSave = situationUserToSave;
	}
	
	
	
	
    public SituationBlocageUser getSituationBlocageUser() {
    	if(getSituationBlocage()==1){
    		situationBlocageUser=new SituationBlocageUser(1, "Bloqué");
    	}else{
    		situationBlocageUser=new SituationBlocageUser(0, "Débloqué");	
    	}
		return situationBlocageUser;
	}

	public void setSituationBlocageUser(SituationBlocageUser situationBlocageUser) {
		
		
		this.situationBlocageUser = situationBlocageUser;
	}

	public SituationBlocageUser getSituationBlocageUserToSave() {
		return situationBlocageUserToSave;
	}

	public void setSituationBlocageUserToSave(SituationBlocageUser situationBlocageUserToSave) {
		this.situationBlocageUserToSave = situationBlocageUserToSave;
	}
	
	

	public Utilisateurs() {
    }

    public int getIdUtilisateurs() {
        return this.idUtilisateurs;
    }

    public void setIdUtilisateurs(int idUtilisateurs) {
        this.idUtilisateurs = idUtilisateurs;
    }

    public BigDecimal getConnecte() {
        return this.connecte;
    }

    public void setConnecte(BigDecimal connecte) {
        this.connecte = connecte;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getNbrTentative() {
        return this.nbrTentative;
    }

    public void setNbrTentative(int nbrTentative) {
        this.nbrTentative = nbrTentative;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profil getProfil() {
        return this.profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public int getSituation() {
        return this.situation;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }

    public Agence getAgence() {
        return this.agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }
    
	public String getNewPassword() {
	//decryptedPassword=CryptPassword.encrypt(getPassword());
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getDecryptedPassword() {
		decryptedPassword=CryptPassword.decrypt(getPassword());
		return decryptedPassword;
	}

	public void setDecryptedPassword(String decryptedPassword) {
		this.decryptedPassword = decryptedPassword;
	}
	
	
	

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateDesactivation() {
		return dateDesactivation;
	}

	public void setDateDesactivation(Date dateDesactivation) {
		this.dateDesactivation = dateDesactivation;
	}

	public Date getDateLastConnection() {
		return dateLastConnection;
	}

	public void setDateLastConnection(Date dateLastConnection) {
		this.dateLastConnection = dateLastConnection;
	}
	
	
	

	public Date getDateReactivation() {
		return dateReactivation;
	}

	public void setDateReactivation(Date dateReactivation) {
		this.dateReactivation = dateReactivation;
	}
	
	
	

	public Date getDateLastChangedPwd() {
		return dateLastChangedPwd;
	}

	public void setDateLastChangedPwd(Date dateLastChangedPwd) {
		this.dateLastChangedPwd = dateLastChangedPwd;
	}
	
	
	

	public List<PasswordHistory> getPasswordHistories() {
		return passwordHistories;
	}

	public void setPasswordHistories(List<PasswordHistory> passwordHistories) {
		this.passwordHistories = passwordHistories;
	}
	
	
	
	

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}
	
	
	

	public Date getDateBlocage() {
		return dateBlocage;
	}

	public void setDateBlocage(Date dateBlocage) {
		this.dateBlocage = dateBlocage;
	}

	public Date getDateDeblocage() {
		return dateDeblocage;
	}

	public void setDateDeblocage(Date dateDeblocage) {
		this.dateDeblocage = dateDeblocage;
	}
	
	

	public Integer getSituationBlocage() {
		return situationBlocage;
	}

	public void setSituationBlocage(Integer situationBlocage) {
		this.situationBlocage = situationBlocage;
	}
	
	public Integer getFirstLogin() {
	    return firstLogin;
	}

	public void setFirstLogin(Integer firstLogin) {
	    this.firstLogin = firstLogin;
	}
	
	
	public String getFirstLoginLabel() {
	    return firstLogin == 1 ? "Oui" : "Non";
	}

	@Override
	public String toString() {
		return "Utilisateurs [idUtilisateurs=" + idUtilisateurs + ", connecte=" + connecte + ", login=" + login + ", nbrTentative=" + nbrTentative
				+ ", nom=" + nom + ", mail=" + mail + ", prenom=" + prenom + ", password=" + password + ", newPassword=" + newPassword
				+ ", decryptedPassword=" + decryptedPassword + ", profil=" + profil + ", situation=" + situation + ", situationUser=" + situationUser
				+ ", situationUserToSave=" + situationUserToSave + ", agence=" + agence + "]";
	}

}
