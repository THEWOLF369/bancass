package com.star.Ba.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;
import org.primefaces.util.ComponentUtils;

import com.star.Ba.dao.IAgenceDAO;
import com.star.Ba.dao.IPasswordHistoryDAO;
import com.star.Ba.dao.IProfilDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.dao.ProfilDAO;
import com.star.Ba.jsf.util.CryptPassword;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.jsf.util.PasswordValidator;
import com.star.Ba.jsf.util.JsfUtil.PersistAction;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Client;
import com.star.Ba.model.PasswordHistory;
import com.star.Ba.model.Profil;
import com.star.Ba.model.SituationBlocageUser;
import com.star.Ba.model.SituationUser;
import com.star.Ba.model.Societe;
import com.star.Ba.model.Utilisateurs;

/**
 * 
 * @author
 */
@ManagedBean(name = "userBean2")
@SessionScoped
public class UserBean2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@EJB
	private IUtilisateursDAO utilisateursDAO;
	
	
	
	@EJB
	private IPasswordHistoryDAO passwordHistoryDAO;
	
	
	@EJB
	
	private IProfilDAO profilDAO;
	
	
	@EJB
	private IAgenceDAO agenceDAO;
	
	private String selectedAgence;

	private List<Utilisateurs> items = null;
	private Utilisateurs selected;
	private SituationUser selectedSituation;
	
	
	private String rePassword, rePasswordModif;
	
    private String msg = "";
	
	public SituationUser getSelectedSituation() {
		return selectedSituation;
	}

	public void setSelectedSituation(SituationUser selectedSituation) {
		this.selectedSituation = selectedSituation;
	}

	public UserBean2() {
	}

	public Utilisateurs getSelected() {
		return selected;
	}

	public void setSelected(Utilisateurs selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}
	
	
	
	@PostConstruct
	private void initialiser(){
		
	if(utilisateur.getProfil().getCode().equalsIgnoreCase("ASI")) {
			
			allUsersbna = utilisateursDAO.find_utilisateurs_all_bna();
		}else {
			
			allUsersbna = utilisateursDAO.find_utilisateurs_bna();
		}
	}
	
	
	
 
	public Utilisateurs prepareCreate() {
		System.out.println("debut prepare create +++++++++++++++++++++++++");

		selected = new Utilisateurs();
		selected.setConnecte(BigDecimal.ZERO);
		selected.setNbrTentative(0);
		initializeEmbeddableKey();
		System.out.println("fin prepare create +++++++++++++++++++++++++");
		return selected;
	}
	
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
			+ "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
			+ "(\\.[A-Za-z]{2,})$";

	private Pattern pattern = Pattern.compile(EMAIL_PATTERN);



	public void create() {
		
		System.out.println("debut  create +++++++++++++++++++++++++"+selectedAgence);
		
//		System.out.println("profil choisit +++++++++++++++++++++++++"+selected.getProfil().getCode());
		
		
		
		if ( selectedAgence != null) {
		for (Agence agence : allAgence) {
			
			
			if (agence.getIdAgence().toString().equals(selectedAgence)) {
							
				selected.setAgence(agence);
				System.out.println("  create +++++++++++++++++++++++++"+agence.toString());
				
			}
			
		}
		}
		
		if (selected.getNom().isEmpty() || selected.getLogin().isEmpty()
				|| selected.getPassword().isEmpty()
				|| selected.getPrenom().isEmpty()
				|| selected.getMail().isEmpty()) {
			JsfUtil.addErrorMessage("Vérifier les champs ");
			

		} 
		
		else if (!pattern.matcher(selected.getMail()).matches() && !selected.getMail().isEmpty()) {

			JsfUtil.addErrorMessage("Format E-mail Invalide ");

		}
		else if (exitUserLogin(selected.getLogin())) {

			JsfUtil.addErrorMessage("login deja existant");

		}
		/*else if (exitUsermail(selected.getMail())) {

			JsfUtil.addErrorMessage("e_mail deja existant");

		}*/
		/*
		else if (selected.getLogin().equals(selected.getPassword()) || selected.getPassword().length()<6 ) {

			JsfUtil.addErrorMessage("mot de passe non suffisamment sécurisé");

		}*/
		

		else {
			
			
			 if(selected.getProfil().getCode().equalsIgnoreCase("BNAA")) {
				
					/*
					 * if (!PasswordValidator.isStrongPassword(selected.getPassword())) {
					 * System.out.println("Password is not strong.");
					 * //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");
					 * JsfUtil.showAlertMessageForPassword(); return; }
					 */
			}
			else  {
				
				/*
				 * if (!PasswordValidator.isStrongPasswordForAdmin(selected.getPassword())) {
				 * System.out.println("Password admin is not strong.");
				 * //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");
				 * JsfUtil.showAlertMessageForPasswordAdmin(); return; }
				 */
			}
			
			
			
			selected.setDateCreation(new Date());
			
			selected.setDateLastChangedPwd(new Date());
			
			selected.setFirstLogin(1);
			
	     	PasswordHistory passwordHistory = new PasswordHistory();
	      	passwordHistory.setUtilisateurs(selected);
	      	passwordHistory.setPassword(CryptPassword.encrypt(selected
	 					.getPassword()));
	      	passwordHistory.setChangedAt(new Date());
	      	
	      	List<PasswordHistory> histories = new ArrayList<>();
	      	histories.add(passwordHistory);
	      	
	      	selected.setPasswordHistories(histories);
			
			persist(PersistAction.CREATE, "Utilisateur cree avec success");
			
			System.out.println("fin  create +++++++++++++++++++++++++");


			if (!JsfUtil.isValidationFailed()) {
				items = null; // Invalidate list of items to trigger re-query.
				
			}
			
			
			 
			//14/08/2026
			initialiser();	
			
		}

	}

	public void destroy() {
		persist(PersistAction.DELETE, "Utilisateur supprimer avec success");
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null; // Invalidate list of items to trigger re-query.
		}
	}

	public List<Utilisateurs> getItems() {
		if (items == null) {
			items = utilisateursDAO.findAll();
		}
		return items;
	}

	public boolean exitUserLogin(String login) {
		return utilisateursDAO.searchLogin(login) == null ? false : true;

	}
	
	public boolean exitUsermail(String mail) {
		return utilisateursDAO.searchmail(mail) == null ? false : true;

	}

	boolean initialiseTentative;

	public boolean isInitialiseTentative() {
		return initialiseTentative;
	}

	public void setInitialiseTentative(boolean initialiseTentative) {
		this.initialiseTentative = initialiseTentative;
	}
	
	
	boolean premiereConnexion;



	public boolean isPremiereConnexion() {
		return premiereConnexion;
	}

	public void setPremiereConnexion(boolean premiereConnexion) {
		this.premiereConnexion = premiereConnexion;
	}
	

	public void updateUser() {

		if (selected.getNom().isEmpty() || selected.getLogin().isEmpty() ||

		selected.getPrenom().isEmpty() /*|| selected.getMail().isEmpty()*/) {
			JsfUtil.addWarningMessage("Vérifier  les champs ");

		} 
		else {

			if (!selected.getNewPassword().isEmpty()) {
				
				if(selected.getProfil().getCode().equalsIgnoreCase("BNAA")) {
					
					/*
					 * if (!PasswordValidator.isStrongPassword(selected.getNewPassword())) {
					 * System.out.println("Password is not strong.");
					 * //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");
					 * JsfUtil.showAlertMessageForPassword(); return ; }
					 */
					
		      		 List<PasswordHistory> historiesTop10 = passwordHistoryDAO.findTop10ByUserOrderByChangedAtDesc(selected);
//		     		 boolean existeHistoriesTop = false;
		     		  
		     		  if(!historiesTop10.isEmpty()) {
		     			 System.out.println("PasswordHistory  existed. (modification)");
		     			 
							/*
							 * for (PasswordHistory ph : historiesTop10) { if
							 * (selected.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.
							 * getPassword())) ) {
							 * JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des "
							 * + historiesTop10.size() + " derniers mots de passe."); return ; } }
							 */

		     		  }
					
					
					
				} else {
					
					/*
					 * if (!PasswordValidator.isStrongPasswordForAdmin(selected.getNewPassword())) {
					 * System.out.println("Password Admin is not strong.");
					 * //JsfUtil.addErrorMessage("Mot de passe non suffisamment sécurisé");
					 * JsfUtil.showAlertMessageForPasswordAdmin(); return ; }
					 */
					
		     		 List<PasswordHistory> historiesTop3 = passwordHistoryDAO.findTop3ByUserOrderByChangedAtDesc(selected);
//		     		 boolean existeHistoriesTop = false;
		     		  
		     		  if(!historiesTop3.isEmpty()) {
		     			 System.out.println("PasswordHistory admin existed. (modification)");
		     			 
							/*
							 * for (PasswordHistory ph : historiesTop3) { if
							 * (selected.getNewPassword().equalsIgnoreCase(CryptPassword.decrypt(ph.
							 * getPassword())) ) {
							 * JsfUtil.addErrorMessage("Le nouveau mot de passe ne doit pas être l'un des "
							 * + historiesTop3.size() + " derniers mots de passe."); return ; } }
							 */

		     		  }
					
				}
				

				
		     	 PasswordHistory passwordHistory = new PasswordHistory();
		      	passwordHistory.setUtilisateurs(selected);
		      	passwordHistory.setPassword(CryptPassword.encrypt(selected
		 					.getNewPassword()));
		      	passwordHistory.setChangedAt(new Date());
		      	
		      	List<PasswordHistory> histories = passwordHistoryDAO.findUserOrderByChangedAtDesc(selected);
		      	if(!histories.isEmpty()) {
		      		
		      		histories.add(passwordHistory);
		      	} else {
		      		
		      		histories = new ArrayList<>();
		      		histories.add(passwordHistory);
		      	}
				
		      	selected.setPasswordHistories(histories);
				
				selected.setPassword(CryptPassword.encrypt(selected
						.getNewPassword()));
				
				selected.setDateLastChangedPwd(new Date());
			}

			if (selected.getSituationUserToSave() != null) {
				selected.setSituation(selected.getSituationUserToSave()
						.getSituationNomber());
				
				if(selected.getSituationUserToSave().getSituationNomber()==1) {
					
					System.out.println("profil activé");
					selected.setDateReactivation(new Date());
				}else {
					
					System.out.println("profil désactivé");
					selected.setDateDesactivation(new Date());
				}
				
			}
			
			
			if (selected.getSituationBlocageUserToSave() != null) {
				selected.setSituationBlocage(selected.getSituationBlocageUserToSave()
						.getSituationNomber());
				
				if(selected.getSituationBlocageUserToSave().getSituationNomber()==1) {
					
					System.out.println("profil bloqué");
					selected.setDateBlocage(new Date());
				}else {
					
					System.out.println("profil débloqué");
					selected.setDateDeblocage(new Date());
				}
				
			}	

			if (initialiseTentative) {
				selected.setNbrTentative(0);
			}
			
			if (premiereConnexion) {
				selected.setFirstLogin(1);
			}else {
				selected.setFirstLogin(0);
			}
			
			// selected.setSituation(selectedSituation.getSituationNomber());
			utilisateursDAO.createUser(selected);
//			selected = new Utilisateurs();
			JsfUtil.addSuccessMessage("Utilisateur modifier avec succèss");
		}

	}


	private void persist(PersistAction persistAction, String successMessage) {
		System.out.println("debut persist +++++++++++++++++++++++++");

		
		if (selected != null) { 			

			try {

				if (!exitUserLogin(selected.getLogin())) {

					selected.setPassword(CryptPassword.encrypt(selected
							.getPassword()));					
					selected.setSituation(1);
					utilisateursDAO.create(selected);
					selected = new Utilisateurs();
					JsfUtil.addSuccessMessage(successMessage);
					
					System.out.println("fin persist +++++++++++++++++++++++++");

				} else {
					JsfUtil.addWarningMessage("login déja exist");

				}

			} catch (EJBException ex) {
				String msg = "";
				Throwable cause = ex.getCause();
				if (cause != null) {
					msg = cause.getLocalizedMessage();
				}
				if (msg.length() > 0) {
					JsfUtil.addErrorMessage(msg);
				} else {
					JsfUtil.addErrorMessage(ex, "Error");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						null, ex);
				JsfUtil.addErrorMessage(ex, "Error");
			}
		}
	}

	public Utilisateurs getUtilisateurs(Integer id) {
		return utilisateursDAO.find(id);
	}

	public List<Utilisateurs> getItemsAvailableSelectMany() {
		return utilisateursDAO.findAll();
	}

	public List<Utilisateurs> getItemsAvailableSelectOne() {
		return utilisateursDAO.findAll();
	}

	@FacesConverter(forClass = Utilisateurs.class)
	public static class UtilisateursControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext,
				UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			UserBean2 controller = (UserBean2) facesContext.getApplication()
					.getELResolver()
					.getValue(facesContext.getELContext(), null, "userBean2");
			return controller.getUtilisateurs(getKey(value));
		}

		Integer getKey(String value) {
			Integer key;
			key = new Integer(value);
			return key;
		}

		String getStringKey(Integer value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext,
				UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Utilisateurs) {
				Utilisateurs o = (Utilisateurs) object;
				return getStringKey(o.getIdUtilisateurs());
			} else {
				Logger.getLogger(this.getClass().getName()).log(
						Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(),
								Utilisateurs.class.getName() });
				return null;
			}
		}

	}



	List<Profil> listProfile = new ArrayList<Profil>();

	List<Utilisateurs> allUsersbna = new ArrayList<Utilisateurs>();
	
	List<Agence> abna = new ArrayList<Agence>();
	
	public List<Utilisateurs> getAllUsersbna() {
		
//		if(utilisateur.getProfil().getCode().equalsIgnoreCase("ASI")) {
//			
//			allUsersbna = utilisateursDAO.find_utilisateurs_all_bna();
//		}else {
//			
//			allUsersbna = utilisateursDAO.find_utilisateurs_bna();
//		}
		
		
		return allUsersbna;
	}


	
public  List<Agence> agencebna(){
	
		abna = agenceDAO.getagencebna();
		return abna;
	} 

	public List<Profil> getListProfile() {
		
		if(utilisateur.getProfil().getCode().equalsIgnoreCase("ASI")) {
			
			listProfile = profilDAO.getprofilbna_all();
			
		}else {
			listProfile = profilDAO.getprofilbna();
		}

		
		return listProfile;
	}

	public void setListProfile(List<Profil> listProfile) {
		this.listProfile = listProfile;
	}

	public Converter getProfilConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (Profil profil : getListProfile()) {
					if (profil.getCode().equals(value)) {
						return profil;
					}
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				return value.toString();
			}
		};
	}

	public Converter getSituationConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (SituationUser situationUser : getSituationUserList()) {
					if (situationUser.getSituationName().equals(value)) {
						return situationUser;
					}
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				return value.toString();
			}
		};
	}
	
	
	public Converter getSituationBlocageConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (SituationBlocageUser situationBlocageUser : getSituationBlocageUserList()) {
					if (situationBlocageUser.getSituationName().equals(value)) {
						return situationBlocageUser;
					}
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				return value.toString();
			}
		};
	}
	
	
	

	List<Agence> allAgence;

	public List<Agence> getAllAgence() {

	
		allAgence = agenceDAO.getagencebna();
		HashMap<String, String>agences= new HashMap<String, String>();
		for (Agence a : allAgence) {
			agences.put(a.getLibelle(), a.getIdAgence()+"");
		}
		return allAgence;
		
	}
	
	public HashMap<String, String> getAgences(){
		getAllAgence();
		HashMap<String, String>agences= new HashMap<String, String>();
		for (Agence a : allAgence) {
			agences.put(a.getLibelle(), a.getIdAgence()+"");
		}
		return agences;
	}

	public Converter getAgenceConverter(){
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				
				for (Agence agence : getAllAgence()) {
					if (agence.getLibelle().equals(value)) {
									
						return agence;
						
					}
					
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				System.out.println("getAsString ========"+ value.toString());
				return value.toString();
				
			}
		};
	}

	//test bna
	
	public void agenceget() {
		System.out.println("selectedagence======== :" + selected.getAgence());
	}
	
	private boolean listAgentRender;
	private boolean listAgentRender2;
	// private boolean listProfilRender;

	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;
	
	public void updatePasswordUserConnected(){
		if(!utilisateur.getNewPassword().isEmpty()){
			if (utilisateur.getLogin().equals(utilisateur.getNewPassword()) || utilisateur.getNewPassword().length()<6 ) {

				JsfUtil.addErrorMessage("mot de passe non suffisamment sécurisé");
			}
			else{
			utilisateur.setPassword(CryptPassword.encrypt(utilisateur.getNewPassword()));
			utilisateursDAO.createUser(utilisateur);
			JsfUtil.addSuccessMessage("Utilisateur modifié avec succes");
			}
		}
		
	}
		
	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public boolean isListAgentRender2() {
		listAgentRender2 = utilisateur.getProfil().getCode()
				.equalsIgnoreCase(PofileEnum.BNA.name()) ? true : false;
		return listAgentRender2;
	}
	
	public void setListAgentRender2(boolean listAgentRender2) {
		this.listAgentRender2 = listAgentRender2;
	}

	public boolean isListAgentRender() {
		listAgentRender = utilisateur.getProfil().getCode()
				.equalsIgnoreCase(PofileEnum.AS.name()) ? true : false;
		return listAgentRender;
	}

	public void setListAgentRender(boolean listAgentRender) {
		this.listAgentRender = listAgentRender;
	}

	public List<SituationUser> situationUserList = new ArrayList<SituationUser>();

	public List<SituationUser> getSituationUserList() {
		if (situationUserList.isEmpty()) {
			situationUserList.add(new SituationUser(1, "Activé"));
			situationUserList.add(new SituationUser(2, "Désactivé"));
		}
		return situationUserList;
	}

	public void setSituationUserList(List<SituationUser> situationUserList) {
		this.situationUserList = situationUserList;
	}

	
	public List<SituationBlocageUser> situationBlocageUserList = new ArrayList<SituationBlocageUser>();

	public List<SituationBlocageUser> getSituationBlocageUserList() {
		if (situationBlocageUserList.isEmpty()) {
			situationBlocageUserList.add(new SituationBlocageUser(1, "Bloqué"));
			situationBlocageUserList.add(new SituationBlocageUser(0, "Débloqué"));
		}
		return situationBlocageUserList;
	}

	public void setSituationBlocageUserList(List<SituationBlocageUser> situationBlocageUserList) {
		this.situationBlocageUserList = situationBlocageUserList;
	}
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

public String getSelectedAgence() {
	return selectedAgence;
}
public void setSelectedAgence(String selectedAgence) {
	this.selectedAgence = selectedAgence;
}

public String getRePassword() {
	return rePassword;
}

public void setRePassword(String rePassword) {
	this.rePassword = rePassword;
}

public String getRePasswordModif() {
	return rePasswordModif;
}

public void setRePasswordModif(String rePasswordModif) {
	this.rePasswordModif = rePasswordModif;
}

	



	
	
	
}