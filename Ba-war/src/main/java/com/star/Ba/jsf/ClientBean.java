package com.star.Ba.jsf;

import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dao.ILocgouvDAO;
import com.star.Ba.dao.IProfessionClientDAO;
import com.star.Ba.dao.ITypeVoieDAO;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.model.Client;
import com.star.Ba.model.Locgouv;
import com.star.Ba.model.Police;
import com.star.Ba.model.ProfessionClient;
import com.star.Ba.model.TypeVoie;
import com.star.Ba.model.Utilisateurs;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedProperty;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.primefaces.event.SelectEvent;
//01/11/2022
import org.primefaces.util.ComponentUtils;
//import com.star.Ba.jsf.util.ComponentUtils;

import ws.client.ParseException_Exception;
import ws.client.WsRecupClientService;
import ws.client.WsRecupererClient;

/**
 * @author
 * 
 */
@ManagedBean(name = "clientBean")
@SessionScoped
public class ClientBean implements Serializable {

	@EJB
	private IClientDAO clientDAO;
	@EJB
	private ITypeVoieDAO typeVoieDAO;
	@EJB
	private ILocgouvDAO locgouvDAO;
	@EJB
	private IProfessionClientDAO professionClientDAO;
	
	@ManagedProperty("#{login.utilisateur}")
	private Utilisateurs utilisateur;// = new Utilisateurs();
	
	private List<TypeVoie> typeVoies;
	private List<Locgouv> locgouvs;
	private Locgouv selectedLocgouv;
    private boolean AS = false;
    private boolean qnb = false;
	private boolean searching = false;
	private boolean find = false;
	private List<ProfessionClient> professions;
	private static final long serialVersionUID = -3324864097586374969L;
	private Client client = new Client();
	
	private boolean fromWS = false;
	
	private boolean nomWS = false;
	private boolean prenomWS = false;
	private boolean dateNaisWS = false;
	private boolean cinWS = false;
	private boolean ribWS = false;
	
	private boolean clientStb;
	private boolean requiredRib;
	

	
	/*** hoss ***/
	Map<String, Object> listCPT = new LinkedHashMap<String, Object>();
	List<Locgouv> allCodePostalByGouvernera = new ArrayList<Locgouv>();
	
	int ageClient = 0;

	public List<Locgouv> getAllCodePostalByGouvernera() {
		return allCodePostalByGouvernera;
	}

	public void setAllCodePostalByGouvernera(
			List<Locgouv> allCodePostalByGouvernera) {
		this.allCodePostalByGouvernera = allCodePostalByGouvernera;
	}

	public Map<String, Object> getListCPT() {
		return listCPT;
	}

	public void setListCPT(Map<String, Object> listCPT) {
		this.listCPT = listCPT;
	}

	Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat = new HashMap<String, List<Locgouv>>();

	public Map<String, List<Locgouv>> getMapAllCodePostalByGouvernerat() {
		return mapAllCodePostalByGouvernerat;
	}

	public void setMapAllCodePostalByGouvernerat(
			Map<String, List<Locgouv>> mapAllCodePostalByGouvernerat) {
		this.mapAllCodePostalByGouvernerat = mapAllCodePostalByGouvernerat;
	}

	List<String> allGouverneras = new ArrayList<String>();

	String selectedGouverneras;

//	public void updateListCodePostalActionListener() {
//		allCodePostalByGouvernera.clear();
//		allCodePostalByGouvernera.addAll(mapAllCodePostalByGouvernerat
//				.get(client.getGouver()));
//
//	}
	
public void updateListCodePostalActionListener(){	
		
		System.out.println("gov bis");

		if(client.getGouver() !=null && (!client.getGouver().equals("")))
		{
			//allCodePostalByGouvernera.clear();
			allCodePostalByGouvernera=new  ArrayList<Locgouv>();
			client.setCodeP(null);
			client.setLocalite(null);
			selectedLocgouv = new Locgouv();
			allCodePostalByGouvernera = locgouvDAO.findByGouv(client.getGouver());// (mapAllCodePostalByGouvernerat.get(newAdhesion.getGouver()));
			System.out.println("gov1");

		}
		else{
			//allCodePostalByGouvernera.clear();
			allCodePostalByGouvernera=new  ArrayList<Locgouv>();
			client.setCodeP(null);
			client.setLocalite(null);
			client.setGouver(null);
			//selectedLocgouv = new Locgouv();
			System.out.println("gov2");

		}
		
		 


	}



public boolean  isCondition() {
	
	if( utilisateur.getAgence().getSociete().getCodSociet().equals("002707PPM000"))
		return true;
		else
			return false;

}
	

	public String getSelectedGouverneras() {
		return selectedGouverneras;
	}

	public void setSelectedGouverneras(String selectedGouverneras) {
		this.selectedGouverneras = selectedGouverneras;
	}

	public List<String> getAllGouverneras() {
		return allGouverneras;
	}

	public void setAllGouverneras(List<String> allGouverneras) {
		this.allGouverneras = allGouverneras;
	}

	
	public boolean isAS() {
		return AS;
	}

	public void setAS(boolean aS) {
		AS = aS;
	}

	@PostConstruct
	public void init() {
		
		clientStb = true;
		requiredRib = true;
		
		if(utilisateur.getAgence().getSociete().getMatfisc().equalsIgnoreCase("0022671FPM000")){
			qnb = true;  
			requiredRib = true;
		}
		else {
			qnb = false;
			requiredRib = true;
		}
		
		System.out.println("matricule fiscal ==== "+ utilisateur.getAgence().getSociete().getMatfisc());
		
		System.out.println("qnb boolean value ==== "+ qnb);
		
		System.out.println("init client bean");
		client = new Client();
		client.setTypePers("SO");
		
		typeVoies = typeVoieDAO.findAll();
//		List<Locgouv> list = locgouvDAO.findAll();
//
//		for (Locgouv locgouv : list) {
//			if (mapAllCodePostalByGouvernerat.containsKey(locgouv
//					.getGouvernora())) {
//				mapAllCodePostalByGouvernerat.get(locgouv.getGouvernora()).add(
//						locgouv);
//			} else {
//				allGouverneras.add(locgouv.getGouvernora());
//				mapAllCodePostalByGouvernerat.put(locgouv.getGouvernora(),
//						new ArrayList<Locgouv>());
//			}
//
//		}

		
		allGouverneras = locgouvDAO.findGouv();
		
		// setLocgouvs(locgouvDAO.findAll());
		professions = professionClientDAO.findAll();
		
	}

	public void loadClient() {
		String keep = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("keep");
		if ((keep == null || keep.isEmpty() || Boolean.parseBoolean(keep))
				&& !searching) {
			cancel();
		}

		searching = false;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void rechercher() {
		
		
		/**** demande STB pour ne pas modifier les donner depuis WS ***/
		fromWS = false;
		setNomWS(false);
		setPrenomWS(false);
		setDateNaisWS(false);
		setCinWS(false);
		setRibWS(false);
		
		String numCompte = client.getNcompte();
		String numCIN = client.getIdentifiant();
		String typeCompte = client.getTypeDoc();
		
		setClient(new Client());
		
		client.setNcompte(numCompte);
		client.setIdentifiant(numCIN);
		client.setTypeDoc(typeCompte);
		
		
/**** demande STB pour ne pas modifier les donner depuis WS ***/
		
		
		searching = true;
		
		// si c valide pour QNB  càd sans RIB
		/*
		if(client != null && client.getIdentifiant() != null &&  (client.getNcompte() == null || client.getNcompte().trim() == "")) {
			
			System.out.println("client sans rib " );
			
			client = clientDAO.findByIdentifiant(client.getIdentifiant());
			
			if (client == null) {
				FacesMessage msg = new FacesMessage("Client inexistant","Client inexistant ");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				FacesContext.getCurrentInstance().validationFailed();
				cancel();
			} else {

				calculateAgeWithJava7();
				
				
				selectedLocgouv = locgouvDAO.findByPostaCode(client.getCodeP(),	client.getLocalite());
				System.out.println("else "+selectedLocgouv);
				if(selectedLocgouv!=null){
				allCodePostalByGouvernera.clear();
				allCodePostalByGouvernera = locgouvDAO.findByGouv(selectedLocgouv.getGouvernora());
				}else {
					selectedLocgouv = new Locgouv();
	      			 setSelectedLocgouv(selectedLocgouv);
	      			client.setLocalite("");
	      			client.setCodeP("");
				}
				find = true;
				
//				client.setWs("N");

			}
			
		}
		
		
		else*/ if (client != null && client.getIdentifiant() != null && client.getNcompte() != null) {
			
			System.out.println("client avec rib " );
			// client = clientDAO.findByIdentifiant(client.getIdentifiant());
			client = clientDAO.findByIdentifiantAndNcompte(client.getIdentifiant(), client.getNcompte());
			
			if (client == null) {
				FacesMessage msg = new FacesMessage("Client inexistant","Client inexistant ");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage("Erreur", msg);
				FacesContext.getCurrentInstance().validationFailed();
				cancel();
			} else {

				calculateAgeWithJava7();
				/*** calcule age pour 7ayya ***/
				/*
				Calendar calendar = Calendar.getInstance();

		        calendar.setTime(client.getDatNaiss());
		        int dateYear = calendar.get(Calendar.YEAR);
		        int dateMonth = calendar.get(Calendar.MONTH);
		        int dateDay = calendar.get(Calendar.DAY_OF_MONTH);
				
				LocalDate birthdate = new LocalDate(dateYear, dateMonth, dateDay);
				LocalDate now = new LocalDate();  // test, in real world without args
				Years age = Years.yearsBetween(birthdate, now);
				System.out.println("age: " +age.getYears()); // 18
				
				ageClient = age.getYears();
				*/
				/** fin calcul age 7ayya ***/
				
				selectedLocgouv = locgouvDAO.findByPostaCode(client.getCodeP(),	client.getLocalite());
				System.out.println("else "+selectedLocgouv);
				if(selectedLocgouv!=null){
				allCodePostalByGouvernera.clear();
				allCodePostalByGouvernera = locgouvDAO.findByGouv(selectedLocgouv.getGouvernora());
				}else {
					selectedLocgouv = new Locgouv();
	      			 setSelectedLocgouv(selectedLocgouv);
	      			client.setLocalite("");
	      			client.setCodeP("");
				}
				find = true;
				
//				client.setWs("N");

			}
		} else {
			FacesMessage msg = new FacesMessage("Veuillez saisir un identifiant", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
	}

	private ProfessionClient professionClient = new ProfessionClient();

	public ProfessionClient getProfessionClient() {
		return professionClient;
	}

	public void setProfessionClient(ProfessionClient professionClient) {
		this.professionClient = professionClient;
	}

	public Converter getProfessionConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (ProfessionClient professionClient : getProfessions()) {

					if (professionClient.getIdProfession().toString()
							.equals(value)) {
						return professionClient;
					}
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				if (value != null)
					return value.toString();
				else
					return null;

			}
		};
	}
	
	
	
	public Converter getTypeVoieConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (TypeVoie professionClient : getTypeVoies()) {

					if (String.valueOf(professionClient.getCode())
							.equals(value)) {
						return professionClient;
					}
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				if (value != null)
					return value.toString();
				else
					return null;

			}
		};
	}
	

	public void cancel() {

		System.out.println("cancel ");
		// System.out.println("cancel "+client.getTypeVoie());
		client = new Client();
		selectedLocgouv = null;
		allCodePostalByGouvernera.clear();
		
		fromWS = false;
		setNomWS(false);
		setPrenomWS(false);
		setDateNaisWS(false);
		setCinWS(false);
		setRibWS(false);
	}
	
	
	public void suivant() {
		System.out.println("suivant()");
	}
	
	
	public void calculateAgeWithJava7() {            
			    // validate inputs ...                                                                               
			    DateFormat formatter = new SimpleDateFormat("yyyyMMdd");                           
			    int d1 = Integer.parseInt(formatter.format(client.getDatNaiss()));                            
			    int d2 = Integer.parseInt(formatter.format(new Date()));                          
			    int age = (d2 - d1) / 10000;  
			    System.out.println("age est: " +age);
			    ageClient = age;                                                                        
			}
	
	
	public void calcAge() {
		
		
		/*** calcule age pour 7ayya ***/
		Calendar calendar = Calendar.getInstance();

        calendar.setTime(client.getDatNaiss());
        int dateYear = calendar.get(Calendar.YEAR);
        int dateMonth = calendar.get(Calendar.MONTH);
        int dateDay = calendar.get(Calendar.DAY_OF_MONTH);
		
		LocalDate birthdate = new LocalDate(dateYear, dateMonth, dateDay);
		LocalDate now = new LocalDate();  // test, in real world without args
		Years age = Years.yearsBetween(birthdate, now);
		System.out.println("age: " +age.getYears()); // 18
		
		ageClient = age.getYears();
		
		/** fin calcul age 7ayya ***/
		
	}
	
	
	
	/*** recevoir age calculé pour 7ayya ***/
	
	public int calculatedAge() {
		
		
		/*** calcule age pour 7ayya ***/
		Calendar calendar = Calendar.getInstance();

        calendar.setTime(client.getDatNaiss());
        int dateYear = calendar.get(Calendar.YEAR);
        int dateMonth = calendar.get(Calendar.MONTH);
        int dateDay = calendar.get(Calendar.DAY_OF_MONTH);
		
		LocalDate birthdate = new LocalDate(dateYear, dateMonth, dateDay);
		LocalDate now = new LocalDate();  // test, in real world without args
		Years age = Years.yearsBetween(birthdate, now);
		System.out.println("age: " +age.getYears()); // 18
		
		ageClient = age.getYears();
		
		return ageClient;
		
		/** fin calcul age 7ayya ***/
		
	}
	
	
	
	/***** fin ******************************/
	

	public String navigateToClientPage() {
		rechercher();
		return "client?faces-redirect=true&keep=true";
	}

	public List<TypeVoie> getTypeVoies() {
		return typeVoies;
	}

	public void setTypeVoies(List<TypeVoie> typeVoies) {
		this.typeVoies = typeVoies;
	}

	public List<Locgouv> getLocgouvs() {
		return locgouvs;
	}

	public void setLocgouvs(List<Locgouv> locgouvs) {
		this.locgouvs = locgouvs;
		for (Locgouv locgouv : this.locgouvs) {

			listCPT.put(locgouv.getCodePosta() + " " + locgouv.getLocalite(),
					locgouv);
		}
	}

	public List<ProfessionClient> getProfessions() {
		return professions;
	}

	public void setProfessions(List<ProfessionClient> professions) {
		this.professions = professions;
	}

	public List<Locgouv> completeLocgouv(String query) {
		ArrayList<Locgouv> complete = new ArrayList<Locgouv>();
		for (Locgouv locgouv : locgouvs) {
			if (locgouv.getCodePosta().toLowerCase()
					.startsWith(query.toLowerCase())) {
				complete.add(locgouv);
			}
		}

		return complete;
	}

	public void locgouvSelectListener(SelectEvent event) {

		searching = true;
		Locgouv locgouv = (Locgouv) event.getObject();
		if (locgouv != null) {
			client.setCodeP(locgouv.getCodePosta());
			client.setLocalite(locgouv.getLocalite());
			client.setGouver(locgouv.getGouvernora());
		} else {
			client.setCodeP("");
			client.setLocalite("");
			client.setGouver("");
		}
	}

	/*** hoss ***/
	public void locgouvSelectListened() {

		System.out.println("sys1 bis");

		searching = true;
		selectedLocgouv = locgouvDAO.findByCode((getSelectedLocgouv().getCode()));
		Locgouv locgouv = selectedLocgouv;
		if (locgouv != null) {
			client.setCodeP(locgouv.getCodePosta());
			client.setLocalite(locgouv.getLocalite());
			client.setGouver(locgouv.getGouvernora());
			// System.out.println("sys2 bis "+locgouv);
		} else {
			client.setCodeP("");
			client.setLocalite("");
			client.setGouver("");
			// System.out.println("sys3 bis "+locgouv);
		}
	}

	public Locgouv getSelectedLocgouv() {
		return selectedLocgouv;
	}

	public void setSelectedLocgouv(Locgouv selectedLocgouv) {
		this.selectedLocgouv = selectedLocgouv;
	}

	public Locgouv getLocgouv(long code) {
		return locgouvDAO.find(code);
	}

	public TypeVoie getTypeVoie(long code) {
		return typeVoieDAO.find(code);
	}

	public ProfessionClient getProfession(long code) {
		return professionClientDAO.find(code);
	}

	@FacesConverter(forClass = Locgouv.class)
	public static class LocgouvConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
			if (value != null && !value.isEmpty()) {
				try {
					long code = Long.parseLong(value);
					ClientBean clientBean = (ClientBean) context
							.getApplication()
							.getELResolver()
							.getValue(context.getELContext(), null,
									"clientBean");
					return clientBean.getLocgouv(code);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			return null;
		}

		@Override
		public String getAsString(FacesContext context, UIComponent component,
				Object value) {

			try {
				Locgouv locgouv = (Locgouv) value;
				if (locgouv != null) {
					return String.valueOf(locgouv.getCode());
				}

			} catch (Exception e) {
			}

			return null;
		}

	}

	@FacesConverter(forClass = TypeVoie.class)
	public static class TypeVoieConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
			if (value != null && !value.isEmpty()) {
				try {
					long code = Long.parseLong(value);
					ClientBean clientBean = (ClientBean) context
							.getApplication()
							.getELResolver()
							.getValue(context.getELContext(), null,
									"clientBean");
					return clientBean.getTypeVoie(code);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			return null;
		}

		@Override
		public String getAsString(FacesContext context, UIComponent component,
				Object value) {

			try {
				TypeVoie typeVoie = (TypeVoie) value;
				if (typeVoie != null) {
					return String.valueOf(typeVoie.getCode());
				}

			} catch (Exception e) {
			}

			return null;
		}
	}

	public void identificationValidator(FacesContext context, UIComponent comp,	Object value) {
       
		String docTypeId = ComponentUtils.findComponentClientId("docType");
		if (docTypeId != null) {
			UIInput docTypeInput = (UIInput) context.getViewRoot()	.findComponent(docTypeId);
			if (docTypeInput != null) {
				String docType = (String) docTypeInput.getValue();
				
			
				
				if (docType != null && docType.equals("CIN") /*&& client.getTypePers().equals("PA")*/ ) {
					System.out.println("type " +client.getTypePers());
					if( client.getTypePers() == null || client.getTypePers().equals("PA")){
						
						String cin = (String) value;

						// cin should start with either 0 or 1, length = 8
						Pattern pattern = Pattern.compile("^[0, 1]\\d{7}");
						Matcher matcher = pattern.matcher(cin);
						if (!matcher.find()) {
							String msg = "CIN doit commencer par 0 ou 1 sur 8 chiffre";
							throw new ValidatorException(new FacesMessage(	FacesMessage.SEVERITY_ERROR, msg, msg));
						}
						
					}

					
			
				}
			}

		}
	
	}
	public Date getMaxDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -18);
		return calendar.getTime();
	}
	
	
	
	

	public void rechercherDepuisWS() {
		searching = true;
		String identifiant = client.getNcompte();
		String ctrl = null;

		System.out.println("identifiant =" + identifiant);
		System.out
				.println("client.getIdentifiant() " + client.getIdentifiant());

		WsRecupClientService service1 = new WsRecupClientService();
		System.out.println("Create Web Service...");
		WsRecupererClient port1 = service1.getWsRecupererClientPort();
		System.out.println("Call Web Service Operation...");

		System.out.println("identifiant " + identifiant);

		try {
			ctrl = port1.recupererClient(identifiant).getNOMCLIENT();
			System.out.println("ctrl: " + ctrl);

			client.setNomClient(port1.recupererClient(identifiant)
					.getNOMCLIENT());
			client.setPrenomClient(port1.recupererClient(identifiant)
					.getPRENOMCLIENT());

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.S");
			String dateInString = port1.recupererClient(identifiant)
					.getDATNAISS();
			try {

				Date date = formatter.parse(dateInString);
				client.setDatNaiss(date);

			} catch (ParseException e) {
				e.printStackTrace();
				client = null;
			}

			client.setIdentifiant(port1.recupererClient(identifiant)
					.getIDENTIFIANT());
			client.setTypeDoc(port1.recupererClient(identifiant).getTYPEDOC());
			client.setTel(port1.recupererClient(identifiant).getTEL());
		} catch (ParseException_Exception e) {
			e.printStackTrace();
			client = null;
			System.out.println("ParseException_Exception ");
		} catch (Exception e) {
			e.printStackTrace();
			client = null;
			System.out.println("Exception ");

		}

		if (client == null) {
			FacesMessage msg = new FacesMessage("ERREUR", "Client inexistant ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Erreur", msg);
			FacesContext.getCurrentInstance().validationFailed();
			cancel();
		}

	}

	public void rechercheClientByNcompte(FacesContext context,
			UIComponent comp, Object value) {
		System.out.println("find de rechercheClientByNcompte=" + find);
		if (find == false) {
			String ncompte = (String) value;
			System.out.println("ncompte" + ncompte);
			if (clientDAO.FindClientByNcompte(ncompte) != null) {
				String msg = "Ce numéro de compte existe déja!!";
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, msg, msg));
			}

		}
	}

	public int getAgeClient() {
		return ageClient;
	}

	public void setAgeClient(int ageClient) {
		this.ageClient = ageClient;
	}

	public boolean isQnb() {
		return qnb;
	}

	public void setQnb(boolean qnb) {
		this.qnb = qnb;
	}

	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}

	public boolean isFromWS() {
		return fromWS;
	}

	public void setFromWS(boolean fromWS) {
		this.fromWS = fromWS;
	}

	public IClientDAO getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(IClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	public boolean isNomWS() {
		return nomWS;
	}

	public void setNomWS(boolean nomWS) {
		this.nomWS = nomWS;
	}

	public boolean isPrenomWS() {
		return prenomWS;
	}

	public void setPrenomWS(boolean prenomWS) {
		this.prenomWS = prenomWS;
	}

	public boolean isDateNaisWS() {
		return dateNaisWS;
	}

	public void setDateNaisWS(boolean dateNaisWS) {
		this.dateNaisWS = dateNaisWS;
	}

	public boolean isCinWS() {
		return cinWS;
	}

	public void setCinWS(boolean cinWS) {
		this.cinWS = cinWS;
	}

	public boolean isRibWS() {
		return ribWS;
	}

	public void setRibWS(boolean ribWS) {
		this.ribWS = ribWS;
	}

	public boolean isRequiredRib() {
		return requiredRib;
	}

	public void setRequiredRib(boolean requiredRib) {
		this.requiredRib = requiredRib;
	}
	
	
	
	
	
	public boolean isClientStb() {
		return clientStb;
	}

	public void setClientStb(boolean clientStb) {
		this.clientStb = clientStb;
	}

	public void updateRequiredRib(){
		
		System.out.println("updateRequiredRib");
		requiredRib = clientStb;
		
		cancel();
		
	}
	
	
	
	
	
}
