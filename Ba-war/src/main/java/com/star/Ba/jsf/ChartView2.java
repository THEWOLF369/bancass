package com.star.Ba.jsf;

import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

import com.star.Ba.dao.IAgenceDAO;
import com.star.Ba.dao.IChartDAO;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IProdconvDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dto.ImportData;
import com.star.Ba.dto.ProduitStat;
import com.star.Ba.model.Agence;
import com.star.Ba.model.AgenceProduit;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Utilisateurs;

@ManagedBean(name = "chartView2")
@ViewScoped
public class ChartView2 implements Serializable {

	@EJB
	private IAgenceDAO agenceDAO;

	private BarChartModel barModel;
	private BarChartModel barSommeModel;
	private PieChartModel pieModel1;
	private String month;// = "10";
	private String year;
	private String month_year;
	private String etat;
	private double TotalA;
	private double TotalB;
	private double TotalC;
	private double TotalBordereau;

	private boolean faux;
	private boolean vrais;

	String months[] = { "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" };

	int monthsLists[] = { DateTimeConstants.JANUARY,
			DateTimeConstants.FEBRUARY, DateTimeConstants.MARCH,
			DateTimeConstants.APRIL, DateTimeConstants.MAY,
			DateTimeConstants.JUNE, DateTimeConstants.JULY,
			DateTimeConstants.AUGUST, DateTimeConstants.SEPTEMBER,
			DateTimeConstants.OCTOBER, DateTimeConstants.NOVEMBER,
			DateTimeConstants.DECEMBER };

	@ManagedProperty("#{login}")
	private Login login;

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	@EJB
	private IChartDAO iChartDAO;

	private Map<String, Object> listAgence = null;
	private String codeAgence;

	private Map<String, Object> listSociete = null;
	private String codeSociete;

	private List<AgenceProduit> listeAgenceProduit = null;

	private List<AgenceProduit> agenceProduits;

	@EJB
	private IProduitDAO produitDAO;

	/*** pour pie ***/
	// private Map<String,Object> Produits = new HashMap<String, Object>();
	/*** pour bar ***/
	private Map<Integer, ChartSeries> ChartProduits;// = new HashMap
													// <Integer,ChartSeries>();
	private Map<Integer, ChartSeries> ChartProduitsMT;// = new HashMap
														// <Integer,ChartSeries>();

	@EJB
	private IConventionDAO conventionDAO;
	@EJB
	private IProdconvDAO prodconvDAO;
	private List<Prodconv> lstproduit;

	public List<Prodconv> getLstproduit() {
		return lstproduit;
	}

	public void setLstproduit(List<Prodconv> lstproduit) {
		this.lstproduit = lstproduit;
	}

	// // modif pour bna

	private Date date1;
	private Date date2;

	private ProduitStat produitStat;
	private ImportData importData;

	private List<ProduitStat> produitStats;
	private List<ImportData> importDatas;

	// / fin modif pour bna

	@PostConstruct
	public void init() {

		DateTime dateTime = new DateTime();
		month = dateTime.toString("MM");
		year = dateTime.toString("yyyy");
		System.out.println("month_year " + month + "/" + year);

		month_year = month + "/" + year;

		/**** liste agence ****/
		AfficherAgences();

		createPieModels();

		// // modif pour bna

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		System.out.println("date_stat " + formatter.format(date));
		date1 = date;
		date2 = date;

		// / fin modif pour bna

	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	/************************* afficher agences ***************************************/

	public void AfficherAgences() {

		String valProfil = login.getUtilisateur().getProfil().getCode();
		//
		// if (valProfil.equalsIgnoreCase("BNA")) {

		listAgence = iChartDAO.recupererListeAgence(login.getUtilisateur()
				.getAgence().getSociete(), login.getUtilisateur().getProfil()
				.getCode());

		// codeAgence = (String) listAgence.values().toArray()[0];
		codeAgence = (String) listAgence.values().toArray()[0];// login.getUtilisateur().getAgence().getIdAgence().toString();
		System.out.println("le_codeAgence :" + codeAgence);
		// System.out.println("le_code  :"+listAgence.keySet().toArray()[0]);

		// }

	}

	public void CodeChanged(ValueChangeEvent e) {
		// assign new value to localeCode
		codeAgence = e.getNewValue().toString();

	}

	public void CodeSocChanged(ValueChangeEvent e) {
		// assign new value to localeCode
		codeSociete = e.getNewValue().toString();

	}

	/*************************** list des produit **************************************/

	public void listeDesProduits() {

		ChartProduits = new HashMap<>();
		ChartProduitsMT = new HashMap<>();

		for (Prodconv prd : lstproduit) {

			System.out
					.println("------------------------------ lstProduit-------------");

			ChartSeries bar = new ChartSeries();
			bar.setLabel(prd.getProduit().getLibelleProduit());

			ChartSeries barbis = new ChartSeries();
			barbis.setLabel(prd.getProduit().getLibelleProduit());

			ChartProduits.put((int) prd.getProduit().getIdProduit(), bar);
			ChartProduitsMT.put((int) prd.getProduit().getIdProduit(), barbis);
		}

	}

	/***************************************** fct creation les bars ************************************************/

	/***************************************** fin fct creation les bars ************************************************/

	/***************************************** fct creation camabert ************************************************/

	public void createPieModels() {
		createPieModel1();
	}

	private void createPieModel1() {
		pieModel1 = new PieChartModel();

		String valProfil = login.getUtilisateur().getProfil().getCode();
		// int idAgence = login.getUtilisateur().getAgence().getIdAgence();
		Utilisateurs utilisateur = login.getUtilisateur();

		int x = utilisateur.getIdUtilisateurs();

		if (valProfil.equalsIgnoreCase("BNAA")) {

			pieModel1.set("TD", iChartDAO.countAdhesionYeartdbnaparuser(x));
			pieModel1.set("Incendie agricole",
					iChartDAO.countAdhesionYearincendieuser(x));
			pieModel1.set("grele", iChartDAO.countAdhesionYeargreleuser(x));

		}

		else if (valProfil.equalsIgnoreCase("BNA")) {

			pieModel1.set("TD", iChartDAO.countAdhesionYeartdbna());
			pieModel1.set("Incendie agricole",
					iChartDAO.countAdhesionYearincendie());
			pieModel1.set("grele", iChartDAO.countAdhesionYeargrele());

		}

		pieModel1
				.setTitle("Répartition des contrats d'assurance pour l'année en cours");
		pieModel1.setLegendPosition("n");
		pieModel1.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		pieModel1.setFill(true);
		pieModel1.setShowDataLabels(true);
	}

	/***************************************** fin fct creation camabert ************************************************/

	/******************************** calcul % les bars *****************************************************/

	/***************** tous les produit **************/

	/***************** tous les produit par utilisateur **************/

	// end bna
	/******************************** fin calcul % les bars *****************************************************/

	/******************************** calcul % les bars pr montant *****************************************************/

	/***************** tous les produit **************/

	// bna

	/******************************** calcul % camambert *****************************************************/

	/***************** tous les produit **************/

	/******************************** fin calcul % camambert *****************************************************/

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth_year() {
		return month_year;
	}

	public void setMonth_year(String month_year) {
		this.month_year = month_year;
	}

	public Map<String, Object> getListAgence() {

		String valProfil = login.getUtilisateur().getProfil().getCode();

		return listAgence;
	}

	public void setListAgence(Map<String, Object> listAgence) {
		this.listAgence = listAgence;
	}

	public String getCodeAgence() {
		return codeAgence;
	}

	public void setCodeAgence(String codeAgence) {
		this.codeAgence = codeAgence;
	}

	public Map<String, Object> getListSociete() {
		return listSociete;
	}

	public void setListSociete(Map<String, Object> listSociete) {
		this.listSociete = listSociete;
	}

	public String getCodeSociete() {
		return codeSociete;
	}

	public void setCodeSociete(String codeSociete) {
		this.codeSociete = codeSociete;
	}

	public boolean isFaux() {
		return faux;
	}

	public void setFaux(boolean faux) {
		this.faux = faux;
	}

	public boolean isVrais() {
		return vrais;
	}

	public void setVrais(boolean vrais) {
		this.vrais = vrais;
	}

	public List<AgenceProduit> getListeAgenceProduit() {
		return listeAgenceProduit;
	}

	public void setListeAgenceProduit(List<AgenceProduit> listeAgenceProduit) {
		this.listeAgenceProduit = listeAgenceProduit;
	}

	public List<AgenceProduit> getAgenceProduits() {
		return agenceProduits;
	}

	public void setAgenceProduits(List<AgenceProduit> agenceProduits) {
		this.agenceProduits = agenceProduits;
	}

	public void avoirIntervalle(int mois, int annee, int jour) {

		int month = mois;// DateTimeConstants.NOVEMBER;
		int year = annee;// 2016;
		int dayOfWeek = jour;// DateTimeConstants.MONDAY;

		LocalDate firstOfMonth = new LocalDate(year, month, 1);
		LocalDate firstOfNextMonth = firstOfMonth.plusMonths(1);
		LocalDate firstDateInGrid = firstOfMonth.withDayOfWeek(dayOfWeek);
		if (firstDateInGrid.isAfter(firstOfMonth)) { // If getting the next
														// start of week instead
														// of desired week's
														// start, adjust
														// backwards.
			firstDateInGrid = firstDateInGrid.minusWeeks(1);
		}

		LocalDate weekStart = firstDateInGrid;
		LocalDate weekStop = null;
		int weekNumber = 0;

		do {
			weekNumber = weekNumber + 1;
			weekStop = weekStart.plusDays(6);
			System.out.println(weekNumber + " week: " + weekStart + " --- "
					+ weekStop); // 1 week: 03-30-2014 --- 04-05-2014
			weekStart = weekStop.plusDays(1);
		} while (weekStop.isBefore(firstOfNextMonth));
	}

	public void buttonAction(ActionEvent actionEvent) {
		addMessage("Welcome to Primefaces!!");
		// List<AgenceProduit> agenceProduits = null;
		// agenceProduits= iChartDAO.exportProduit(252, "21/11/2016",
		// "27/11/2016");
		// System.out.println("agenceProduits "+agenceProduits.get(0).getProduit());

	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void ExportListe(int idagence, String codesociete,
			List<Integer> listProduit) {

		agenceProduits = new ArrayList<AgenceProduit>();

		int month_value = monthsLists[Integer.valueOf(month) - 1];
		int year_value = Integer.valueOf(year);
		int dayOfWeek = DateTimeConstants.MONDAY;

		LocalDate firstOfMonth = new LocalDate(year_value, month_value, 1);
		LocalDate firstOfNextMonth = firstOfMonth.plusMonths(1);
		LocalDate firstDateInGrid = firstOfMonth.withDayOfWeek(dayOfWeek);
		if (firstDateInGrid.isAfter(firstOfMonth)) { // If getting the next
														// start of week instead
														// of desired week's
														// start, adjust
														// backwards.
			firstDateInGrid = firstDateInGrid.minusWeeks(1);
		}

		LocalDate weekStart = firstDateInGrid;
		LocalDate weekStop = null;
		int weekNumber = 0;

		do {
			weekNumber = weekNumber + 1;
			weekStop = weekStart.plusDays(6);
			System.out.println(weekNumber + " week: " + weekStart + " --- "
					+ weekStop); // 1 week: 03-30-2014 --- 04-05-2014
			System.out.println("weekStart " + weekStart.toString("dd/MM/yyyy"));
			System.out.println("weekStop " + weekStop.toString("dd/MM/yyyy"));

			AgenceProduit agprd = new AgenceProduit();
			List<Object> objects = null;

			if (vrais) {
				System.out.println("vrais tout export");
				objects = iChartDAO.exportProduit(codesociete,
						weekStart.toString("dd/MM/yyyy"),
						weekStop.toString("dd/MM/yyyy"), listProduit,
						month_value);
			} else {

				System.out.println("faux tout export");
				objects = iChartDAO.exportProduit(idagence,
						weekStart.toString("dd/MM/yyyy"),
						weekStop.toString("dd/MM/yyyy"), listProduit,
						month_value);
			}
			// System.out.println("agenceProduits "+agenceProduits.get(0).getProduit());
			for (Object result : objects) {

				Object[] r = (Object[]) result;
				agprd.setIdagence(Integer.valueOf(r[0].toString()));
				agprd.setCodeagence(r[1].toString());
				agprd.setLibelle(r[2].toString());
				agprd.setProduit(r[3].toString());
				agprd.setIdproduit(Integer.valueOf(r[4].toString()));
				agprd.setNombre(Integer.valueOf(r[5].toString()));
				agprd.setMtprime(Double.valueOf(r[6].toString()));
				agprd.setPrimetot(Double.valueOf(r[7].toString()));
				agprd.setSemaine(String.valueOf(weekNumber));

				System.out.println("agprd " + agprd.getNombre());

				agenceProduits.add(agprd);
				agprd = new AgenceProduit();

				// for (Object r1: r) {
				// System.out.println(r1.toString());
				// }

			}

			// System.out.println("agenceProduits "+agenceProduits.get(0).getProduit());

			weekStart = weekStop.plusDays(1);
		} while (weekStop.isBefore(firstOfNextMonth));

	}

	// / modif pour bna

	public void click() throws ParseException {

		System.out.println("clic stat");
		System.out.println("codeSociete " + codeSociete);
		System.out.println("codeAgence " + codeAgence);
		// System.out.println("date1 "+date1.toString());

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateDu = formatter.format(date1);
		String dateAu = formatter.format(date2);

		produitStats = new ArrayList<ProduitStat>();
		// Agence agence = iChartDAO.avoirAgence(Integer.valueOf("5555"));
		List<Agence> agence = iChartDAO.avoirAgencebysoci();
		// Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());

		Convention c = conventionDAO.find_conv_by_Societbna();
		lstproduit = prodconvDAO.getlstProduit_by_convention(c);
		List<Integer> listeIdProduit = new ArrayList<Integer>();

		for (Prodconv prd : lstproduit)
			listeIdProduit.add((int) prd.getProduit().getIdProduit());

		ProduitStat agprd = new ProduitStat();
		List<Object> objects = null;

		// B142431996

		if (vrais) {
			System.out.println("vrais tout export stat");
			objects = iChartDAO.aficherStat(login.getUtilisateur().getAgence()
					.getSociete().getCodSociet(), dateDu, dateAu,
					listeIdProduit);
			// objects= iChartDAO.aficherStat(codeSociete, dateDu,
			// dateAu,listeIdProduit);
		} else {

			System.out
					.println("coooooooooooooooooooooooooooooooooooooooode agennnnnnnnnnnnnnnnnnnnnnnce"
							+ codeAgence);

			System.out.println("faux tout export stat");
			// objects=
			// iChartDAO.aficherStat(Integer.valueOf(login.getUtilisateur().getAgence().getIdAgence()),
			// dateDu, dateAu,listeIdProduit);
			if (codeAgence != null) {
				objects = iChartDAO.aficherStat(Integer.valueOf(codeAgence),
						dateDu, dateAu, listeIdProduit);
			}

			// login.getUtilisateur().getAgence().getIdAgence();
			// objects= iChartDAO.aficherStat(Integer.valueOf(codeAgence),
			// dateDu, dateAu,listeIdProduit);
		}

		/*
		 * agprd.setSmtprime(Tmtprime + agprd.getMtprime());
		 * agprd.setScommission(Tcommision + agprd.getCommission());
		 * agprd.setSrtsource(Trtcomm + agprd.getRtsource());
		 */
		String x = null;

		TotalA = TotalB = TotalC = 0.0;
		TotalBordereau = 0.0;
		if (objects != null) {
			for (Object result : objects) {

				Object[] r = (Object[]) result;

				// System.out.println(Arrays.toString(r));

				agprd.setIdagence(Integer.valueOf(r[0].toString()));
				agprd.setCodeagence(r[1].toString());
				agprd.setDateop(r[2].toString());

				agprd.setNcompte(r[3].toString());
				agprd.setLibelle(r[4].toString());
				agprd.setProduit(r[5].toString());
				agprd.setNumquit(r[6].toString());
				if (r[7] != null)
					agprd.setContrat(r[7].toString());
				if (r[8] != null)
					agprd.setMtprime(Double.valueOf(r[8].toString()));
				if (r[9] != null) {
					agprd.setCommission(Double.valueOf(r[9].toString()));
					agprd.setRtsource(agprd.getCommission() * 0.15);
				}

				// agprd.setId_pack(Integer.valueOf(r[10].toString()));

				// id borderau

				// System.out.println("date brut ////////////////////////"+r[10].toString());

				if (Integer.valueOf(r[10].toString().substring(9, 10)) <= 15) {

					x = (r[10].toString().substring(0, 8) + "01");

					// System.out.println("***********************"+ x);

					agprd.setId_bordereau(x);
				}

				else {
					x = (r[10].toString().substring(0, 8) + "02");
					// System.out.println("**********else*************"+ x);

					agprd.setId_bordereau(x);
				}

				// id borderau
				TotalA = TotalA + agprd.getMtprime();
				System.out.println("total des primes++++++++++++++++++++++++"
						+ TotalA);
				TotalB = TotalB + agprd.getCommission();
				System.out
						.println("total des commisions++++++++++++++++++++++++"
								+ TotalB);

				TotalC = TotalC + agprd.getRtsource();
				System.out
						.println("total des rtsources++++++++++++++++++++++++"
								+ TotalC);

				System.out.println("r[1].toString() " + r[1].toString());

				produitStats.add(agprd);
				agprd = new ProduitStat();

				// for (Object r1: r) {
				// System.out.println(r1.toString());
				// }

			}
		}
		TotalBordereau = (TotalA - TotalB) + TotalC;

	}

	// extraction des données par période

	public void importData() {

		System.out.println("extraction des données par période");
		System.out.println("codeSociete "
				+ login.getUtilisateur().getAgence().getSociete()
						.getCodSociet());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateDu = formatter.format(date1);
		String dateAu = formatter.format(date2);

		System.out.println("dateDu " + dateDu);
		System.out.println("dateAu " + dateAu);
		importDatas = new ArrayList<ImportData>();
		ImportData agprd = new ImportData();
		List<Object> objects = null;
		objects = iChartDAO.exportData(login.getUtilisateur().getAgence()
				.getSociete().getCodSociet(), dateDu, dateAu, etat);
		for (Object result : objects) {

			Object[] r = (Object[]) result;
			agprd.setCodeagence(r[0].toString());
			if (r[1] != null)
				agprd.setNcompte(r[1].toString());
			agprd.setContrat(r[2].toString());
			agprd.setNom_client(r[3].toString());
			agprd.setPrenom_client(r[4].toString());
			agprd.setAdhesion(r[5].toString());
			System.out.println("r[6].toString()" + r[6].toString());
			agprd.setDebut(r[6].toString().substring(0, 10));
			agprd.setFin(r[7].toString().substring(0, 10));
			agprd.setDuree(Double.valueOf(r[8].toString()));
			agprd.setEtat(r[9].toString());
			agprd.setAnnulation(r[10].toString().substring(0, 10));
			agprd.setMtprime(Double.valueOf(r[11].toString()));
			agprd.setCommission(Double.valueOf(r[12].toString()));
			importDatas.add(agprd);
			agprd = new ImportData();

		}

	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public ProduitStat getProduitStat() {
		return produitStat;
	}

	public void setProduitStat(com.star.Ba.dto.ProduitStat produitStat) {
		this.produitStat = produitStat;
	}

	public List<ProduitStat> getProduitStats() {
		return produitStats;
	}

	public void setProduitStats(List<ProduitStat> produitStats) {
		this.produitStats = produitStats;
	}

	public List<ImportData> getImportDatas() {
		return importDatas;
	}

	public void setImportDatas(List<ImportData> importDatas) {
		this.importDatas = importDatas;
	}

	public double getTotalA() {
		return TotalA;
	}

	public void setTotalA(double totalA) {
		TotalA = totalA;
	}

	public double getTotalB() {
		return TotalB;
	}

	public void setTotalB(double totalB) {
		TotalB = totalB;
	}

	public double getTotalC() {
		return TotalC;
	}

	public void setTotalC(double totalC) {
		TotalC = totalC;
	}

	public double getTotalBordereau() {
		return TotalBordereau;
	}

	public void setTotalBordereau(double totalBordereau) {
		TotalBordereau = totalBordereau;
	}

	// / fin modif pour bna

}