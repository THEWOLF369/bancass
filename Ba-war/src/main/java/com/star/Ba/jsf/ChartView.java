package com.star.Ba.jsf;

 

import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

import com.star.Ba.dao.IChartDAO;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IProdconvDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dto.ProduitStat;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.dto.ImportData;
import com.star.Ba.model.Agence;
import com.star.Ba.model.AgenceProduit;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Utilisateurs;
 
 
@ManagedBean
@SessionScoped
public class ChartView implements Serializable {
 
    private BarChartModel barModel;
    private BarChartModel barSommeModel;
    private PieChartModel pieModel1;
    private String month;// = "10";
    private String year;
    private String month_year;
    private String etat;
  //private  double commision = 0.0 ;
    
    private boolean faux;  
    private boolean vrais;
 
	
    String months[] = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};
    
    
    int monthsLists[] = {DateTimeConstants.JANUARY,DateTimeConstants.FEBRUARY,DateTimeConstants.MARCH,DateTimeConstants.APRIL,
    		DateTimeConstants.MAY,DateTimeConstants.JUNE,DateTimeConstants.JULY,DateTimeConstants.AUGUST,DateTimeConstants.SEPTEMBER,
    		DateTimeConstants.OCTOBER,DateTimeConstants.NOVEMBER,DateTimeConstants.DECEMBER};
    
    
    @ManagedProperty("#{login}")
    private Login login;
    
    
    
   public static final String CODE_HAYYA = ENUMCODEPRODUIT.H7AYYA.getCode();
   public static final String CODE_HAYYA_QNB = ENUMCODEPRODUIT.H7ayyaQNB.getCode();
   public static final String CODE_AVJ = ENUMCODEPRODUIT.AVENIRJENESSE.getCode();
	

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
	
	private Map<String, Object> listAgence=null;
	private String codeAgence;
	
	private Map<String, Object> listSociete=null;
	private String codeSociete;
	
	private List<AgenceProduit> listeAgenceProduit=null;
	
	
	private List<AgenceProduit> agenceProduits  ;
	
	
	 @EJB
	 private IProduitDAO produitDAO ;
	 
	 /*** pour pie ***/
	 //private Map<String,Object> Produits = new  HashMap<String, Object>();
	 /*** pour bar ***/
	 private Map<Integer,ChartSeries> ChartProduits ;//= new HashMap <Integer,ChartSeries>(); 
	 private Map<Integer,ChartSeries> ChartProduitsMT;// = new HashMap <Integer,ChartSeries>(); 
	
	
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
	 
//// modif pour bna
	 
	 private Date date1;
	 private Date date2;
	 
	 private ProduitStat produitStat;
	 private ImportData importData;
	 
	 private List<ProduitStat> produitStats  ;
	 private List<ImportData> importDatas;
/// fin modif pour bna	
 
    @PostConstruct
    public void init() {
    	/*Date mDate = new Date(System.currentTimeMillis());
    	int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    	int currentYear = Calendar.getInstance().get(Calendar.YEAR) ;
    	System.out.println("month_year "+currentMonth + "/"+ currentYear);
    	
    	
    	
    	month = String.valueOf(currentMonth);
    	year = String.valueOf(currentYear);*/
    	
    	DateTime dateTime = new DateTime();
    	month = dateTime.toString("MM");
    	year = dateTime.toString("yyyy");
    	System.out.println("month_year "+month + "/"+ year);
    	
    	month_year = month+"/"+year;
    	
    	
    	 
    	 /**** list des produit****/
  /*  	List<Produit> listProduit = produitDAO.findAll();
    	// String Produits[]={"TD","Incendie","Assistance voyage","Avenir jeunesse" };
    	 
    	 for(Produit produit:listProduit){
    		 //Produits.put(produit.getLibelleProduit(),produit.getIdProduit());
    		 
    		 ChartSeries bar = new ChartSeries();
    		 bar.setLabel(produit.getLibelleProduit());
    		 
    		 ChartSeries barbis = new ChartSeries();
    		 barbis.setLabel(produit.getLibelleProduit());
    		 
    		 ChartProduits.put((int) produit.getIdProduit(),  bar);
    		 ChartProduitsMT.put((int) produit.getIdProduit(),  barbis);
    		 
    	 }
    		 
    */	 
    	 
    	
    	
    	/**** liste agence ****/
  		AfficherAgences();
    	 
        createBarModels();
        createPieModels();
        
     //// modif pour bna
        
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		  Date date = new Date();
		  System.out.println("date_stat "+formatter.format(date));
		  date1 = date;
		  date2 = date;

        
        
        /// fin modif pour bna
        
        
    }
 
    public BarChartModel getBarModel() {
        return barModel;
    }
    
    
    
     
    public BarChartModel getBarSommeModel() {
		return barSommeModel;
	}

	 

	public PieChartModel getPieModel1() {
        return pieModel1;
    }
 
    
    public void Bothchart(){
    	
    	String valProfil = login.getUtilisateur().getProfil().getCode();
    	if(listSociete != null && 
    			
    			/*(valProfil.equalsIgnoreCase("AST")||valProfil.equalsIgnoreCase("IST")||valProfil.equalsIgnoreCase("TDST")||
    	    			 valProfil.equalsIgnoreCase("VST")||valProfil.equalsIgnoreCase("ASI"))*/
    	    			 
    	    	 /*** equivalente ****/	
    	    			 
    	    	(!valProfil.equalsIgnoreCase("G") && !valProfil.equalsIgnoreCase("AA") && !valProfil.equalsIgnoreCase("AS") && !valProfil.equalsIgnoreCase("BNA") && !valProfil.equalsIgnoreCase("BNAA"))		 
    			
    			){
  		  listAgence = iChartDAO.recupererListeAgence(iChartDAO.avoiSociete(codeSociete), "X");
  		 codeAgence= (String) listAgence.values().toArray()[0];
 	        System.out.println("le_codeAgence AST Bothchart: "+codeAgence);
  		  }
    	//codeAgence = null;
    	 createBarModels();
         createPieModels();
    }
    
    
    /************************* afficher agences***************************************/
    
    
    public void AfficherAgences(){
    	
    	 String valProfil = login.getUtilisateur().getProfil().getCode();
//    	
   	if (valProfil.equalsIgnoreCase("BNA")){
 		
		 listAgence = iChartDAO.recupererListeAgence(login.getUtilisateur().getAgence().getSociete(), login.getUtilisateur().getProfil().getCode());
         
	        //codeAgence = (String) listAgence.values().toArray()[0];
	        codeAgence=  (String) listAgence.values().toArray()[1];//login.getUtilisateur().getAgence().getIdAgence().toString();
	        System.out.println("le_codeAgence :"+codeAgence);
	        //System.out.println("le_code  :"+listAgence.keySet().toArray()[0]);
	        
	 } 
//    
//    		
//    		
//    		
    	 else if (valProfil.equalsIgnoreCase("AS") ){
    		
    		 listAgence = iChartDAO.recupererListeAgence(login.getUtilisateur().getAgence().getSociete(), login.getUtilisateur().getProfil().getCode());
             
    	        //codeAgence = (String) listAgence.values().toArray()[0];
    	        codeAgence=  (String) listAgence.values().toArray()[0];//login.getUtilisateur().getAgence().getIdAgence().toString();
    	        System.out.println("le_codeAgence :"+codeAgence);
    	        //System.out.println("le_code  :"+listAgence.keySet().toArray()[0]);
    	        
    	 }      
//    		
//    	}else{
//    		
//    		
//    	}
    	
    	 if /*(valProfil.equalsIgnoreCase("AST")||valProfil.equalsIgnoreCase("IST")||valProfil.equalsIgnoreCase("TDST")||
    			 valProfil.equalsIgnoreCase("VST")||valProfil.equalsIgnoreCase("ASI"))*/
    	 
	    	 /*** equivalente ****/	
			 
	    	(!valProfil.equalsIgnoreCase("G") && !valProfil.equalsIgnoreCase("AA") && !valProfil.equalsIgnoreCase("AS") && !valProfil.equalsIgnoreCase("BNA") && !valProfil.equalsIgnoreCase("BNAA"))	
    	 
    	 {
    		 
    		
    		  listSociete = iChartDAO.recupererListeSociete();
    		  codeSociete = (String) listSociete.values().toArray()[0];
    		  System.out.println("le_codeSociete :"+codeSociete);
    		  
    		  
    		  if(listSociete != null && 

    				 /* (valProfil.equalsIgnoreCase("AST")||valProfil.equalsIgnoreCase("IST")||valProfil.equalsIgnoreCase("TDST")||
    	    	    			 valProfil.equalsIgnoreCase("VST")||valProfil.equalsIgnoreCase("ASI"))*/
    	    	    	 /*** equivalente ****/	
 	    			 
    	    	    	(!valProfil.equalsIgnoreCase("G") && !valProfil.equalsIgnoreCase("AA") && !valProfil.equalsIgnoreCase("AS") && !valProfil.equalsIgnoreCase("BNA") && !valProfil.equalsIgnoreCase("BNAA"))	
    				  
    				  ){
        		  listAgence = iChartDAO.recupererListeAgence(iChartDAO.avoiSociete(codeSociete), "X");
       	      if (codeAgence==null)  codeAgence= (String) listAgence.values().toArray()[0];
       	        System.out.println("le_codeAgence AST dans init :"+codeAgence);
        		  }
    		  
    		
    		 
    	 }
    	 
    }
    
    
    public void CodeChanged(ValueChangeEvent e){
		//assign new value to localeCode
    	codeAgence = e.getNewValue().toString();

	}
    
    public void CodeSocChanged(ValueChangeEvent e){
		//assign new value to localeCode
    	codeSociete = e.getNewValue().toString();
    	 
	}
    
    
/*************************** list des produit **************************************/
    
    public void listeDesProduits(){
    	
    	   ChartProduits = new HashMap <>(); 
    	   ChartProduitsMT = new HashMap <>(); 
        
     	 for( Prodconv prd : lstproduit){
     		 
     		 ChartSeries bar = new ChartSeries();
     		 bar.setLabel(prd.getProduit().getLibelleProduit());
     		 
     		ChartSeries barbis = new ChartSeries();
     		barbis.setLabel(prd.getProduit().getLibelleProduit());
     		
     		ChartProduits.put((int) prd.getProduit().getIdProduit(),  bar);
    		ChartProduitsMT.put((int) prd.getProduit().getIdProduit(),  barbis);
    		
///// 7ayya et avj
    		int code_vl = 0;
    		int code_et = 0;
    		
    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
    			code_vl = 6857;
    			code_et = 6858;
    		}
    		
    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)) {
    			code_vl = 6867;
    			code_et = 6868;
    		}
    		
    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
    			code_vl = 6927;
    			code_et = 6928;
    		}
    		
    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA) ||
    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)||
    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
    			
    			System.out.println("prd.getProduit().getCodeProduit() "+prd.getProduit().getCodeProduit());
    			 ChartSeries bar_vl = new ChartSeries();
    			bar_vl.setLabel(prd.getProduit().getLibelleProduit() + " versement libre");
        		 
        		ChartSeries barbis_vl = new ChartSeries();
        		barbis_vl.setLabel(prd.getProduit().getLibelleProduit() + " versement libre");
        		
        		ChartProduits.put(code_vl,  bar_vl);
       		ChartProduitsMT.put(code_vl,  barbis_vl);
       		
       		
       		 ChartSeries bar_et = new ChartSeries();
       		 bar_et.setLabel(prd.getProduit().getLibelleProduit() + " encaissement terme");
         		 
         		ChartSeries barbis_et = new ChartSeries();
         		barbis_et.setLabel(prd.getProduit().getLibelleProduit() + " encaissement terme");
         		
         		ChartProduits.put(code_et,  bar_et);
        		ChartProduitsMT.put(code_et,  barbis_et);
    		}
    		
    		
///// 7ayya
    		/*
     		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
     			System.out.println("prd.getProduit().getCodeProduit() "+prd.getProduit().getCodeProduit());
     			 ChartSeries bar_vl = new ChartSeries();
     			bar_vl.setLabel(prd.getProduit().getLibelleProduit() + " versement libre");
         		 
         		ChartSeries barbis_vl = new ChartSeries();
         		barbis_vl.setLabel(prd.getProduit().getLibelleProduit() + " versement libre");
         		
         		ChartProduits.put(97,  bar_vl);
        		ChartProduitsMT.put(97,  barbis_vl);
        		
        		
        		 ChartSeries bar_et = new ChartSeries();
        		 bar_et.setLabel(prd.getProduit().getLibelleProduit() + " encaissement terme");
          		 
          		ChartSeries barbis_et = new ChartSeries();
          		barbis_et.setLabel(prd.getProduit().getLibelleProduit() + " encaissement terme");
          		
          		ChartProduits.put(98,  bar_et);
         		ChartProduitsMT.put(98,  barbis_et);
        		
        		
     		}
     		
     		
     		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ))  {
     			
     			 ChartSeries bar_avj_et = new ChartSeries();
     			bar_avj_et.setLabel(prd.getProduit().getLibelleProduit() + " encaissement terme");
          		 
          		ChartSeries barbis_avj_et = new ChartSeries();
          		barbis_avj_et.setLabel(prd.getProduit().getLibelleProduit() + " encaissement terme");
          		
          		ChartProduits.put(99,  bar_avj_et);
         		ChartProduitsMT.put(99,  barbis_avj_et);
     			
     		}
     		*/
    		
     	 }
  
    }
    
    
    
    /***************************************** fct creation les bars ************************************************/
    
    
    public void createBarModels() {
    	
    	System.out.println("length "+month_year.length());
    	 
    	 System.out.println("month export ="+month);
	         System.out.println("year export ="+year);
	         
	         
	         month = month_year.substring(0, 2);
	         year = month_year.substring(3);
	         
	         System.out.println("month_apres export ="+month);
	         System.out.println("year_apres export ="+year);
    	
//        createBarModel();
//        createBarSommeModel();
        
        String valProfil = login.getUtilisateur().getProfil().getCode();
        if(valProfil.equalsIgnoreCase("G") || valProfil.equalsIgnoreCase("AA") || valProfil.equalsIgnoreCase("AS") || valProfil.equalsIgnoreCase("BNA") || valProfil.equalsIgnoreCase("BNAA")){
        	
        	 Convention c=conventionDAO.find_conv_by_Societ(login.getUtilisateur().getAgence().getSociete());
 	        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
 	        List<Integer> listeIdProduit=new ArrayList<Integer>();
 	        
 	        for( Prodconv prd : lstproduit)
 	        	listeIdProduit.add((int)prd.getProduit().getIdProduit());
 	      
 	       if(valProfil.equalsIgnoreCase("AS")|| valProfil.equalsIgnoreCase("BNA"))
        	ExportListe(Integer.valueOf(codeAgence),login.getUtilisateur().getAgence().getSociete().getCodSociet(),listeIdProduit);
        	
        }else if/*(valProfil.equalsIgnoreCase("AST")||valProfil.equalsIgnoreCase("IST")||valProfil.equalsIgnoreCase("TDST")||
   			 valProfil.equalsIgnoreCase("VST")||valProfil.equalsIgnoreCase("ASI"))*/
   	 /*** equivalente ****/	
		 
   	(!valProfil.equalsIgnoreCase("G") && !valProfil.equalsIgnoreCase("AA") && !valProfil.equalsIgnoreCase("AS") && !valProfil.equalsIgnoreCase("BNA")  && !valProfil.equalsIgnoreCase("BNAA") )	{
        	
        	Agence agence =  iChartDAO.avoirAgence(Integer.valueOf(codeAgence));
        	 Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());
  	        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
  	        List<Integer> listeIdProduit=new ArrayList<Integer>();
  	        
  	        for( Prodconv prd : lstproduit)
  	        	listeIdProduit.add((int)prd.getProduit().getIdProduit());
        	
        	ExportListe(Integer.valueOf(codeAgence),codeSociete,listeIdProduit);
        }
        
        
        listeDesProduits();
        
        createBarModel();
        createBarSommeModel();
    }
     
    private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Répartition des contrats d'assurance par semaine pour le mois séléctionné");
        barModel.setLegendPosition("n");
        barModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        
        barModel.setAnimate(true);
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Semaines");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Nombre d'Adhesions");
//        yAxis.setMin(0);
//        yAxis.setMax(200);
    }
    
    
    private void createBarSommeModel() {
        barSommeModel = initBarSommeModel();
         
        barSommeModel.setTitle("Répartition des contrats d'assurance par semaine pour le mois séléctionné");
        barSommeModel.setLegendPosition("n");
        barSommeModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        barSommeModel.setAnimate(true);
         
        Axis xAxis = barSommeModel.getAxis(AxisType.X);
        xAxis.setLabel("Semaines");
         
        Axis yAxis = barSommeModel.getAxis(AxisType.Y);
        yAxis.setLabel("Chiffre d'affaires");
//        yAxis.setMin(0);
//        yAxis.setMax(900000);
        
        
    }
    
    
     
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        String monthName;
        
        
        
        System.out.println("month ="+month);
        System.out.println("year ="+year);
        
        
        month = month_year.substring(0, 2);
        year = month_year.substring(3);
        
        System.out.println("month_apres ="+month);
        System.out.println("year_apres ="+year);
        
        monthName = months[Integer.valueOf(month)-1];
        
        
        String valProfil = login.getUtilisateur().getProfil().getCode();
        System.out.println("valProfil = "+valProfil);
    	Utilisateurs utilisateur =  login.getUtilisateur();
    	
    	
//    	ChartSeries TD = new ChartSeries();
//        TD.setLabel("TD");
//        
//        
//        ChartSeries Incendie = new ChartSeries();
//        Incendie.setLabel("Incendie");
//        
//        
//        ChartSeries Assistance_voyage = new ChartSeries();
//        Assistance_voyage.setLabel("Assistance voyage");
//        
//        
//        ChartSeries Avenir_jeunesse = new ChartSeries();
//        Avenir_jeunesse.setLabel("Avenir jeunesse");
//        
//        
//        ChartSeries ChartProduits[] = {TD,Incendie,Assistance_voyage,Avenir_jeunesse};
        
        
        int month_value = monthsLists[Integer.valueOf(month)-1];
        int year_value = Integer.valueOf(year);
        int dayOfWeek = DateTimeConstants.MONDAY;
    	
        LocalDate firstOfMonth = new LocalDate( year_value, month_value, 1 );
        LocalDate firstOfNextMonth = firstOfMonth.plusMonths( 1 );
        LocalDate firstDateInGrid = firstOfMonth.withDayOfWeek( dayOfWeek );
        if ( firstDateInGrid.isAfter( firstOfMonth ) ) { // If getting the next start of week instead of desired week's start, adjust backwards.
            firstDateInGrid = firstDateInGrid.minusWeeks( 1 );
        }
        
    	System.out.println("month_value_est "+month_value);
    	
    	
if(valProfil.equalsIgnoreCase("BNAA")){
    		
    		System.out.println("valProfil if BNAA = "+valProfil);
    		
	  		
	      
	         
	        LocalDate weekStart = firstDateInGrid;
	        LocalDate weekStop = null;
	        int weekNumber = 0;
	         
	        do {
	            weekNumber = weekNumber + 1;
	            weekStop = weekStart.plusDays( 6 );
	            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
	            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
	            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
	            
	           
					for (Map.Entry<Integer, ChartSeries> entry : ChartProduits.entrySet())
					{
					    //System.out.println(entry.getKey() + "/" + entry.getValue());
					    System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
					    entry.getValue().set(weekNumber, pourcentageBarModelParUtilisateurbna(entry.getKey(),utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
					    
					}
			 
//	            TD.set(weekNumber, pourcentageBarModelParUtilisateur(1,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Incendie.set(weekNumber, pourcentageBarModelParUtilisateur(2,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Assistance_voyage.set(weekNumber, pourcentageBarModelParUtilisateur(3,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Avenir_jeunesse.set(weekNumber, pourcentageBarModelParUtilisateur(4,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
	            
	            weekStart = weekStop.plusDays( 1 );
	        } while ( weekStop.isBefore( firstOfNextMonth ) );
	        
	        
		 
	        Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
	        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
	        
	        for( Prodconv prd : lstproduit){
	        	model.addSeries(ChartProduits.get((int)prd.getProduit().getIdProduit()));
	        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
	        	System.out.println("id produit "+(int)prd.getProduit().getIdProduit());
	        }
	        
	        
//	        model.addSeries(TD);
//	        model.addSeries(Incendie);
//	        model.addSeries(Assistance_voyage);
//	        model.addSeries(Avenir_jeunesse);
    		
    		
	       
	    	
  	 
  		
		  	}
    	
    	
    	
    	
    	
        
    	else if(valProfil.equalsIgnoreCase("G")){
    		
    		System.out.println("valProfil if G = "+valProfil);
    		
	  		
	      
	         
	        LocalDate weekStart = firstDateInGrid;
	        LocalDate weekStop = null;
	        int weekNumber = 0;
	         
	        do {
	            weekNumber = weekNumber + 1;
	            weekStop = weekStart.plusDays( 6 );
	            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
	            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
	            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
	            
	           
					for (Map.Entry<Integer, ChartSeries> entry : ChartProduits.entrySet())
					{
					    //System.out.println(entry.getKey() + "/" + entry.getValue());
					    System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
					    entry.getValue().set(weekNumber, pourcentageBarModelParUtilisateur(entry.getKey(),utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
					    
					}
			 
//	            TD.set(weekNumber, pourcentageBarModelParUtilisateur(1,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Incendie.set(weekNumber, pourcentageBarModelParUtilisateur(2,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Assistance_voyage.set(weekNumber, pourcentageBarModelParUtilisateur(3,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Avenir_jeunesse.set(weekNumber, pourcentageBarModelParUtilisateur(4,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
	            
	            weekStart = weekStop.plusDays( 1 );
	        } while ( weekStop.isBefore( firstOfNextMonth ) );
	        
	        
		 
	        Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
	        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
	        
	        for( Prodconv prd : lstproduit){
	        	
	        	
	        	//7ayya et avj
			    int code_vl = 0;
	    		int code_et = 0;
	    		
	    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
	    			code_vl = 6857;
	    			code_et = 6858;
	    		}
	    		
	    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)) {
	    			code_vl = 6867;
	    			code_et = 6868;
	    		}
	    		
	    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
	    			code_vl = 6927;
	    			code_et = 6928;
	    		}
	    		
	    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)||
	    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)||
	    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
	    			
	    			model.addSeries(ChartProduits.get(code_vl));
	        		model.addSeries(ChartProduits.get(code_et));
	    		}
	        	
	        	
	        	/*
	        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
	        		
	        		model.addSeries(ChartProduits.get(97));
	        		model.addSeries(ChartProduits.get(98));
	        	}
	        	
	        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
	        		
	        		//model.addSeries(ChartProduits.get(97));
	        		model.addSeries(ChartProduits.get(99));
	        	}
	        	*/
	        	
	        	
	        	model.addSeries(ChartProduits.get((int)prd.getProduit().getIdProduit()));
	        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
	        	System.out.println("id produit "+(int)prd.getProduit().getIdProduit());
	        }
	        
	        
//	        model.addSeries(TD);
//	        model.addSeries(Incendie);
//	        model.addSeries(Assistance_voyage);
//	        model.addSeries(Avenir_jeunesse);
    		
    		
	       
	    	
  	 
  		
		  	}else if(valProfil.equalsIgnoreCase("AA")) {
		  		
		  		System.out.println("valProfil if AA = "+valProfil);
		  		 
		  		Agence agence = login.getUtilisateur().getAgence();
		  		
		  		
		  	  LocalDate weekStart = firstDateInGrid;
		        LocalDate weekStop = null;
		        int weekNumber = 0;
		         
		        do {
		            weekNumber = weekNumber + 1;
		            weekStop = weekStart.plusDays( 6 );
		            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
		            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
		            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
		            
		            for (Map.Entry<Integer, ChartSeries> entry : ChartProduits.entrySet())
					{
					    //System.out.println(entry.getKey() + "/" + entry.getValue());
					    System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
					    entry.getValue().set(weekNumber, pourcentageBarModelParAgence(entry.getKey(),agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
					    
					}
		            
//		            TD.set(weekNumber, pourcentageBarModelParAgence(1,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		            Incendie.set(weekNumber, pourcentageBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		            Assistance_voyage.set(weekNumber, pourcentageBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		            Avenir_jeunesse.set(weekNumber, pourcentageBarModelParAgence(4,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
		            
		            weekStart = weekStop.plusDays( 1 );
		        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  		
		  		
		        
		        Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());
		        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
		        
		        for( Prodconv prd : lstproduit){
		        	
		        	
		        	//7ayya et avj
		        	//7ayya et avj
				    int code_vl = 0;
		    		int code_et = 0;
		    		
		    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
		    			code_vl = 6857;
		    			code_et = 6858;
		    		}
		    		
		    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)) {
		    			code_vl = 6867;
		    			code_et = 6868;
		    		}
		    		
		    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		    			code_vl = 6927;
		    			code_et = 6928;
		    		}
		        	
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)||
		    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)||
		    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		model.addSeries(ChartProduits.get(code_vl));
		        		model.addSeries(ChartProduits.get(code_et));
		        		
		        	}
		        	
		        	/*
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
		        		
		        		model.addSeries(ChartProduits.get(97));
		        		model.addSeries(ChartProduits.get(98));
		        	}
		        	
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		//model.addSeries(ChartProduits.get(97));
		        		model.addSeries(ChartProduits.get(99));
		        	}
		        	*/
		        	
		        	
		        	model.addSeries(ChartProduits.get((int)prd.getProduit().getIdProduit()));
		        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
		        	System.out.println("id produit "+(int)prd.getProduit().getIdProduit());
		        }
		        
 
	    	        //model.addSeries(TD);
	    	        //.addSeries(Incendie);
	    	        //model.addSeries(Assistance_voyage);
//	    	        model.addSeries(Avenir_jeunesse);
		  		
		  		
		  	}
		  	
		  	
    	
             else if(valProfil.equalsIgnoreCase("BNA")) {
		  		
		  		System.out.println("valProfil if AS = "+valProfil);
		  		
		  		System.out.println("codeAgence apres clic=: "+codeAgence);
		  		
		  		Agence agence =  iChartDAO.avoirAgence(Integer.valueOf(codeAgence));
		  		
		  		Convention convention = iChartDAO.avoirConvention(login.getUtilisateur().getAgence().getSociete());
		  		
  
		  		if(vrais){
		  			
		  			System.out.println("vrais");
		  			
		  			
		  		  LocalDate weekStart = firstDateInGrid;
			        LocalDate weekStop = null;
			        int weekNumber = 0;
			         
			        do {
			            weekNumber = weekNumber + 1;
			            weekStop = weekStart.plusDays( 6 );
			            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
			            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
			            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
			            
			            
			            for (Map.Entry<Integer, ChartSeries> entry : ChartProduits.entrySet())
						{
						    //System.out.println(entry.getKey() + "/" + entry.getValue());
						    System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
						    entry.getValue().set(weekNumber, pourcentageBarModel(entry.getKey(),weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention,month_value));
						    
						}
			            
//			            TD.set(weekNumber, pourcentageBarModel(1,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			 Incendie.set(weekNumber, pourcentageBarModel(2,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			Assistance_voyage.set(weekNumber, pourcentageBarModel(3,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			 Avenir_jeunesse.set(weekNumber, pourcentageBarModel(4,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
			            
			            weekStart = weekStop.plusDays( 1 );
			        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  			
		  			
		  			
		  			
		  				
		  			
		  		}else{
		  			
		  			System.out.println("faux");
		  			
		  			
		  			LocalDate weekStart = firstDateInGrid;
			        LocalDate weekStop = null;
			        int weekNumber = 0;
			         
			        do {
			            weekNumber = weekNumber + 1;
			        	
			            weekStop = weekStart.plusDays( 6 );
			            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
			            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
			            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
			            
			            
			            for (Map.Entry<Integer, ChartSeries> entry : ChartProduits.entrySet())
						{
						    //System.out.println(entry.getKey() + "/" + entry.getValue());
						    System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
						    entry.getValue().set(weekNumber, pourcentageBarModelParAgencebna(entry.getKey(),agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
						    
						}
			            
			            
//			            TD.set(weekNumber, pourcentageBarModelParAgence(1,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));  
//		    	        Incendie.set(weekNumber, pourcentageBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"))); 
//		    	        Assistance_voyage.set(weekNumber, pourcentageBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		    	        Avenir_jeunesse.set(weekNumber, pourcentageBarModelParAgence(4,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
			            
			            weekStart = weekStop.plusDays( 1 );
			        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  			
		  			
		  			
		  			
		  		}
		  		
		  		
		  		 Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());
			        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
			        
			        for( Prodconv prd : lstproduit){
			        	model.addSeries(ChartProduits.get((int)prd.getProduit().getIdProduit()));
			        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
			        	System.out.println("id produit "+(int)prd.getProduit().getIdProduit());
			        }
	  		 
	    	        //model.addSeries(TD);
//	    	        model.addSeries(Incendie);
//	    	        model.addSeries(Assistance_voyage);
//	    	        model.addSeries(Avenir_jeunesse);
		  		
		  		
		  	}
		  	
		  	
		  	
		  	
		  	else if(valProfil.equalsIgnoreCase("AS")) {
		  		
		  		System.out.println("valProfil if AS = "+valProfil);
		  		
		  		System.out.println("codeAgence apres clic=: "+codeAgence);
		  		
		  		Agence agence =  iChartDAO.avoirAgence(Integer.valueOf(codeAgence));
		  		
		  		Convention convention = iChartDAO.avoirConvention(login.getUtilisateur().getAgence().getSociete());
		  		
  
		  		if(vrais){
		  			
		  			System.out.println("vrais");
		  			
		  			
		  		  LocalDate weekStart = firstDateInGrid;
			        LocalDate weekStop = null;
			        int weekNumber = 0;
			         
			        do {
			            weekNumber = weekNumber + 1;
			            weekStop = weekStart.plusDays( 6 );
			            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
			            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
			            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
			            
			            
			            for (Map.Entry<Integer, ChartSeries> entry : ChartProduits.entrySet())
						{
						    
			            	
			            	
			            	//System.out.println(entry.getKey() + "/" + entry.getValue());
						    System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
						    entry.getValue().set(weekNumber, pourcentageBarModel(entry.getKey(),weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention,month_value));
						    
			            	
			            	
			            
			            	
						}
			            
//			            TD.set(weekNumber, pourcentageBarModel(1,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			 Incendie.set(weekNumber, pourcentageBarModel(2,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			Assistance_voyage.set(weekNumber, pourcentageBarModel(3,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			 Avenir_jeunesse.set(weekNumber, pourcentageBarModel(4,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
			            
			            weekStart = weekStop.plusDays( 1 );
			        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  			
		  			
		  			
		  			
		  				
		  			
		  		}else{
		  			
		  			System.out.println("faux");
		  			
		  			
		  			LocalDate weekStart = firstDateInGrid;
			        LocalDate weekStop = null;
			        int weekNumber = 0;
			         
			        do {
			            weekNumber = weekNumber + 1;
			            weekStop = weekStart.plusDays( 6 );
			            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
			            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
			            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
			            
			            
			            for (Map.Entry<Integer, ChartSeries> entry : ChartProduits.entrySet())
						{
						    
			            	
			            		
			            	
			            	
			            	//System.out.println(entry.getKey() + "/" + entry.getValue());
						    System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
						    entry.getValue().set(weekNumber, pourcentageBarModelParAgence(entry.getKey(),agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
						    
						    
			            	
						}
			            
			            
//			            TD.set(weekNumber, pourcentageBarModelParAgence(1,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));  
//		    	        Incendie.set(weekNumber, pourcentageBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"))); 
//		    	        Assistance_voyage.set(weekNumber, pourcentageBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		    	        Avenir_jeunesse.set(weekNumber, pourcentageBarModelParAgence(4,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
			            
			            weekStart = weekStop.plusDays( 1 );
			        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  			
		  			
		  			
		  			
		  		}
		  		
		  		
		  		 Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());
			        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
			        
			        for( Prodconv prd : lstproduit){
			        	
			        	///7ayya et avj
					    int code_vl = 0;
			    		int code_et = 0;
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
			    			code_vl = 6857;
			    			code_et = 6858;
			    		}
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)) {
			    			code_vl = 6867;
			    			code_et = 6868;
			    		}
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
			    			code_vl = 6927;
			    			code_et = 6928;
			    		}
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)||
			    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)||
			    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
			    			
			    			model.addSeries(ChartProduits.get(code_vl));
			        		model.addSeries(ChartProduits.get(code_et));
			    			
			    		}
			    		
			        	/*
			        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
			        		
			        		model.addSeries(ChartProduits.get(97));
			        		model.addSeries(ChartProduits.get(98));
			        	}
			        	
			        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
			        		
			        		//model.addSeries(ChartProduits.get(97));
			        		model.addSeries(ChartProduits.get(99));
			        	}
			        	*/
			        	
			        	model.addSeries(ChartProduits.get((int)prd.getProduit().getIdProduit()));
			        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
			        	System.out.println("id produit "+(int)prd.getProduit().getIdProduit());
			        }
	  		 
	    	        //model.addSeries(TD);
//	    	        model.addSeries(Incendie);
//	    	        model.addSeries(Assistance_voyage);
//	    	        model.addSeries(Avenir_jeunesse);
		  		
		  		
		  	}else{
		  		
		  		System.out.println("valProfil else = "+valProfil);
		  		
		  		System.out.println("codeAgence apres clic=: "+codeAgence);
		  		
		  		Agence agence =  iChartDAO.avoirAgence(Integer.valueOf(codeAgence));
		  		
		  		 Convention convention = iChartDAO.avoirConvention(iChartDAO.avoiSociete(codeSociete));
		  		
 
	    	        
 
		  		if(vrais){
		  			
		  			System.out.println("vrais");
		  			
		  			 LocalDate weekStart = firstDateInGrid;
				        LocalDate weekStop = null;
				        int weekNumber = 0;
				         
				        do {
				            weekNumber = weekNumber + 1;
				            weekStop = weekStart.plusDays( 6 );
				            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
				            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
				            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
				            
				            for (Map.Entry<Integer, ChartSeries> entry : ChartProduits.entrySet())
							{
							    //System.out.println(entry.getKey() + "/" + entry.getValue());
							    System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
							    entry.getValue().set(weekNumber, pourcentageBarModel(entry.getKey(),weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention,month_value));
							    
							}
				            
//				            TD.set(weekNumber, pourcentageBarModel(1,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//				  			 Incendie.set(weekNumber, pourcentageBarModel(2,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//				  			Assistance_voyage.set(weekNumber, pourcentageBarModel(3,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//				  			 Avenir_jeunesse.set(weekNumber, pourcentageBarModel(4,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
				            
				            weekStart = weekStop.plusDays( 1 );
				        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  			
		  			
		  			 
		  			
		  		}else{
		  			
		  			System.out.println("faux");
		  			
		  			
		  			LocalDate weekStart = firstDateInGrid;
			        LocalDate weekStop = null;
			        int weekNumber = 0;
			         
			        do {
			            weekNumber = weekNumber + 1;
			            weekStop = weekStart.plusDays( 6 );
			            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
			            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
			            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
			            
			            for (Map.Entry<Integer, ChartSeries> entry : ChartProduits.entrySet())
						{
						    //System.out.println(entry.getKey() + "/" + entry.getValue());
						    System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
						    entry.getValue().set(weekNumber, pourcentageBarModelParAgence(entry.getKey(),agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
						    
						}
			            
//			            TD.set(weekNumber, pourcentageBarModelParAgence(1,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));  
//		    	        Incendie.set(weekNumber, pourcentageBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"))); 
//		    	        Assistance_voyage.set(weekNumber, pourcentageBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		    	        Avenir_jeunesse.set(weekNumber, pourcentageBarModelParAgence(4,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
			            
			            weekStart = weekStop.plusDays( 1 );
			        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  			
		  			 
		  		}
		  		
		  		 Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());
			        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
			        
			        for( Prodconv prd : lstproduit){
			        	
			        	
			        	//7ayya et avj
					    int code_vl = 0;
			    		int code_et = 0;
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
			    			code_vl = 6857;
			    			code_et = 6858;
			    		}
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)) {
			    			code_vl = 6867;
			    			code_et = 6868;
			    		}
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
			    			code_vl = 6927;
			    			code_et = 6928;
			    		}
						
						
						
						if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)||
				    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)||
				    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
							
							model.addSeries(ChartProduits.get(code_vl));
			        		model.addSeries(ChartProduits.get(code_et));
							
						}
			        	/*
			        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
			        		
			        		model.addSeries(ChartProduits.get(97));
			        		model.addSeries(ChartProduits.get(98));
			        	}
			        	
			        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
			        		
			        		//model.addSeries(ChartProduits.get(97));
			        		model.addSeries(ChartProduits.get(99));
			        	}
			        	*/
			        	
			        	
			        	model.addSeries(ChartProduits.get((int)prd.getProduit().getIdProduit()));
			        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
			        	System.out.println("id produit "+(int)prd.getProduit().getIdProduit());
			        }
	    	       
	  		 
	    	        //model.addSeries(TD);
//	    	        model.addSeries(Incendie);
//	    	        model.addSeries(Assistance_voyage);
//	    	        model.addSeries(Avenir_jeunesse);
		  		  
		  		
		  	}
  
         
        return model;
    }
    
    
    ////// somme bar montant
    private BarChartModel initBarSommeModel() {
        BarChartModel model = new BarChartModel();
        String monthName;
        
        
        
        System.out.println("month ="+month);
        System.out.println("year ="+year);
        
        
        month = month_year.substring(0, 2);
        year = month_year.substring(3);
        
        System.out.println("month_apres ="+month);
        System.out.println("year_apres ="+year);
        
        monthName = months[Integer.valueOf(month)-1];
        
        
        String valProfil = login.getUtilisateur().getProfil().getCode();
        System.out.println("valProfil = "+valProfil);
    	Utilisateurs utilisateur =  login.getUtilisateur();
    	
    	
//    	ChartSeries TD = new ChartSeries();
//        TD.setLabel("TD");
//        
//        
//        ChartSeries Incendie = new ChartSeries();
//        Incendie.setLabel("Incendie");
//        
//        
//        ChartSeries Assistance_voyage = new ChartSeries();
//        Assistance_voyage.setLabel("Assistance voyage");
//        
//        
//        ChartSeries Avenir_jeunesse = new ChartSeries();
//        Avenir_jeunesse.setLabel("Avenir jeunesse");
//        
//        ChartSeries ChartProduits[] = {TD,Incendie,Assistance_voyage,Avenir_jeunesse};
//        
        
        int month_value = monthsLists[Integer.valueOf(month)-1];
        int year_value = Integer.valueOf(year);
        int dayOfWeek = DateTimeConstants.MONDAY;
    	
        LocalDate firstOfMonth = new LocalDate( year_value, month_value, 1 );
        LocalDate firstOfNextMonth = firstOfMonth.plusMonths( 1 );
        LocalDate firstDateInGrid = firstOfMonth.withDayOfWeek( dayOfWeek );
        if ( firstDateInGrid.isAfter( firstOfMonth ) ) { // If getting the next start of week instead of desired week's start, adjust backwards.
            firstDateInGrid = firstDateInGrid.minusWeeks( 1 );
        }
        
    	

    	if(valProfil.equalsIgnoreCase("BNAA") ){
    		
    		System.out.println("valProfil if BNAA = "+valProfil);
    		
    		LocalDate weekStart = firstDateInGrid;
	        LocalDate weekStop = null;
	        int weekNumber = 0;
	         
	        do {
	            weekNumber = weekNumber + 1;
	            weekStop = weekStart.plusDays( 6 );
	            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
	            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
	            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
	            
	            
	            for (Map.Entry<Integer, ChartSeries> entry : ChartProduitsMT.entrySet())
				{
				    //System.out.println(entry.getKey() + "/" + entry.getValue());
				    System.out.println("Item MT: " + entry.getKey() + " Count MT : " + entry.getValue());
				    entry.getValue().set(weekNumber, SommeMontantBarModelParUtilisateurbna(entry.getKey(),utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
				    
				}
	            
//		        TD.set(weekNumber, SommeMontantBarModelParUtilisateur(1,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Incendie.set(weekNumber, SommeMontantBarModelParUtilisateur(2,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Assistance_voyage.set(weekNumber, SommeMontantBarModelParUtilisateur(3,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Avenir_jeunesse.set(weekNumber, SommeMontantBarModelParUtilisateur(4,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
	            
	            weekStart = weekStop.plusDays( 1 );
	        } while ( weekStop.isBefore( firstOfNextMonth ) );
  			
    		
	  		 
	        Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
	        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
	        
	        for( Prodconv prd : lstproduit){
	        	model.addSeries(ChartProduitsMT.get((int)prd.getProduit().getIdProduit()));
	        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
	        	System.out.println("id produit MT"+(int)prd.getProduit().getIdProduit());
	        }
		 
	        //model.addSeries(TD);
//	        model.addSeries(Incendie);
//	        model.addSeries(Assistance_voyage);
//	        model.addSeries(Avenir_jeunesse);
    		
    		
  	 
  		
		  	}
    	
    	
        else if(valProfil.equalsIgnoreCase("G")){
    		
    		System.out.println("valProfil if G = "+valProfil);
    		
    		LocalDate weekStart = firstDateInGrid;
	        LocalDate weekStop = null;
	        int weekNumber = 0;
	         
	        do {
	            weekNumber = weekNumber + 1;
	            weekStop = weekStart.plusDays( 6 );
	            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
	            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
	            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
	            
	            
	            for (Map.Entry<Integer, ChartSeries> entry : ChartProduitsMT.entrySet())
				{
				    //System.out.println(entry.getKey() + "/" + entry.getValue());
				    System.out.println("Item MT: " + entry.getKey() + " Count MT : " + entry.getValue());
				    entry.getValue().set(weekNumber, SommeMontantBarModelParUtilisateur(entry.getKey(),utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
				    
				}
	            
//		        TD.set(weekNumber, SommeMontantBarModelParUtilisateur(1,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Incendie.set(weekNumber, SommeMontantBarModelParUtilisateur(2,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Assistance_voyage.set(weekNumber, SommeMontantBarModelParUtilisateur(3,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		        Avenir_jeunesse.set(weekNumber, SommeMontantBarModelParUtilisateur(4,utilisateur,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
	            
	            weekStart = weekStop.plusDays( 1 );
	        } while ( weekStop.isBefore( firstOfNextMonth ) );
  			
    		
	  		 
	        Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
	        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
	        
	        for( Prodconv prd : lstproduit){
	        	
	        	
	        	//7ayya et avj
			    int code_vl = 0;
	    		int code_et = 0;
	    		
	    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
	    			code_vl = 6857;
	    			code_et = 6858;
	    		}
	    		
	    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)) {
	    			code_vl = 6867;
	    			code_et = 6868;
	    		}
	    		
	    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
	    			code_vl = 6927;
	    			code_et = 6928;
	    		}
				
				
				
				if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)||
		    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)||
		    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
					
					model.addSeries(ChartProduitsMT.get(code_vl));
	        		model.addSeries(ChartProduitsMT.get(code_et));
				}
	        	/*
	        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
	        		
	        		model.addSeries(ChartProduitsMT.get(97));
	        		model.addSeries(ChartProduitsMT.get(98));
	        	}
	        	
	        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
	        		
	        		//model.addSeries(ChartProduits.get(97));
	        		model.addSeries(ChartProduitsMT.get(99));
	        	}
	        	*/
	        	
	        	model.addSeries(ChartProduitsMT.get((int)prd.getProduit().getIdProduit()));
	        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
	        	System.out.println("id produit MT"+(int)prd.getProduit().getIdProduit());
	        }
		 
	        //model.addSeries(TD);
//	        model.addSeries(Incendie);
//	        model.addSeries(Assistance_voyage);
//	        model.addSeries(Avenir_jeunesse);
    		
    		
  	 
  		
		  	}else if(valProfil.equalsIgnoreCase("AA")) {
		  		
		  		System.out.println("valProfil if AA = "+valProfil);
		  		 
		  		Agence agence = login.getUtilisateur().getAgence();
		  		
		  		
		  		
		  		LocalDate weekStart = firstDateInGrid;
		        LocalDate weekStop = null;
		        int weekNumber = 0;
		         
		        do {
		            weekNumber = weekNumber + 1;
		            weekStop = weekStart.plusDays( 6 );
		            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
		            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
		            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
		            
		            for (Map.Entry<Integer, ChartSeries> entry : ChartProduitsMT.entrySet())
					{
					    //System.out.println(entry.getKey() + "/" + entry.getValue());
					    System.out.println("Item MT: " + entry.getKey() + " Count MT : " + entry.getValue());
					    entry.getValue().set(weekNumber, SommeMontantBarModelParAgence(entry.getKey(),agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
					    
					}
		            
//		            TD.set(weekNumber, SommeMontantBarModelParAgence(1,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		            Incendie.set(weekNumber, SommeMontantBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		            Assistance_voyage.set(weekNumber, SommeMontantBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//			        Avenir_jeunesse.set(weekNumber, SommeMontantBarModelParAgence(4,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
		            
		            weekStart = weekStop.plusDays( 1 );
		        } while ( weekStop.isBefore( firstOfNextMonth ) );
	  			
		  		 
		        Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());
		        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
		        
		        for( Prodconv prd : lstproduit){
		        	
		        	//7ayya et avj
				    int code_vl = 0;
		    		int code_et = 0;
		    		
		    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
		    			code_vl = 6857;
		    			code_et = 6858;
		    		}
		    		
		    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)) {
		    			code_vl = 6867;
		    			code_et = 6868;
		    		}
		    		
		    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		    			code_vl = 6927;
		    			code_et = 6928;
		    		}
					
					
					
					if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)||
			    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)||
			    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
						
						model.addSeries(ChartProduitsMT.get(code_vl));
		        		model.addSeries(ChartProduitsMT.get(code_et));
						
					}
		        	/*
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
		        		
		        		model.addSeries(ChartProduitsMT.get(97));
		        		model.addSeries(ChartProduitsMT.get(98));
		        	}
		        	
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		//model.addSeries(ChartProduits.get(97));
		        		model.addSeries(ChartProduitsMT.get(99));
		        	}
		        	*/
		        	
		        	model.addSeries(ChartProduitsMT.get((int)prd.getProduit().getIdProduit()));
		        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
		        	System.out.println("id produit MT"+(int)prd.getProduit().getIdProduit());
		        }
	    
	  		 
	    	        //model.addSeries(TD);
//	    	        model.addSeries(Incendie);
//	    	        model.addSeries(Assistance_voyage);
//	    	        model.addSeries(Avenir_jeunesse);
		  		
		  		
		  	}
		  	
		  	
else if(valProfil.equalsIgnoreCase("BNA")) {
		  		
		  		System.out.println("valProfil if AS = "+valProfil);
		  		
		  		System.out.println("codeAgence apres clic=: "+codeAgence);
		  		
		  		Agence agence =  iChartDAO.avoirAgence(Integer.valueOf(codeAgence));
		  		
		  		Convention convention = iChartDAO.avoirConvention(login.getUtilisateur().getAgence().getSociete());
		  		
		  		 
		  		
		  		
		  			if(vrais){
		  			
		  				System.out.println("vrais pour montant");
			  			
		  				
		  				LocalDate weekStart = firstDateInGrid;
				        LocalDate weekStop = null;
				        int weekNumber = 0;
				         
				        do {
				            weekNumber = weekNumber + 1;
				            weekStop = weekStart.plusDays( 6 );
				            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
				            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
				            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
				            
				            for (Map.Entry<Integer, ChartSeries> entry : ChartProduitsMT.entrySet())
							{
							    //System.out.println(entry.getKey() + "/" + entry.getValue());
							    System.out.println("Item MT: " + entry.getKey() + " Count MT : " + entry.getValue());
							    entry.getValue().set(weekNumber, SommeMontantBarModel(entry.getKey(),weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention,month_value));
							    
							}
				            
//				             TD.set(weekNumber, SommeMontantBarModel(1,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//				  			 Incendie.set(weekNumber, SommeMontantBarModel(2,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention)); 
//				  			 Assistance_voyage.set(weekNumber, SommeMontantBarModel(3,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//				  			 Avenir_jeunesse.set(weekNumber, SommeMontantBarModel(4,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			
			  			   
				            weekStart = weekStop.plusDays( 1 );
				        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  				
			  			 
			  			
		  			
		  			}else{
		  				
		  				System.out.println("faux pour montant");
		  				
		  				
		  				LocalDate weekStart = firstDateInGrid;
				        LocalDate weekStop = null;
				        int weekNumber = 0;
				         
				        do {
				            weekNumber = weekNumber + 1;
				            weekStop = weekStart.plusDays( 6 );
				            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
				            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
				            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
				            
				            for (Map.Entry<Integer, ChartSeries> entry : ChartProduitsMT.entrySet())
							{
							    //System.out.println(entry.getKey() + "/" + entry.getValue());
							    System.out.println("Item MT: " + entry.getKey() + " Count MT : " + entry.getValue());
							    entry.getValue().set(weekNumber, SommeMontantBarModelParAgencebna(entry.getKey(),agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
							    
							}
				            
//				            TD.set(weekNumber, SommeMontantBarModelParAgence(1,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//			    	        Incendie.set(weekNumber, SommeMontantBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));  
//			    	        Assistance_voyage.set(weekNumber, SommeMontantBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//			    	        Avenir_jeunesse.set(weekNumber, SommeMontantBarModelParAgence(4,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//			  				
			  			   
				            weekStart = weekStop.plusDays( 1 );
				        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  				
		  				
		  			   
		  			}
		  			

		  		  Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());
			        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
			        
			        for( Prodconv prd : lstproduit){
			        	model.addSeries(ChartProduitsMT.get((int)prd.getProduit().getIdProduit()));
			        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
			        	System.out.println("id produit MT"+(int)prd.getProduit().getIdProduit());
			        }
	  		 
	    	        //model.addSeries(TD);
//	    	        model.addSeries(Incendie);
//	    	        model.addSeries(Assistance_voyage);
//	    	        model.addSeries(Avenir_jeunesse);
		  		
		  		
		  	}
		  	
		  	
		  	
		  	else if(valProfil.equalsIgnoreCase("AS")) {
		  		
		  		System.out.println("valProfil if AS = "+valProfil);
		  		
		  		System.out.println("codeAgence apres clic=: "+codeAgence);
		  		
		  		Agence agence =  iChartDAO.avoirAgence(Integer.valueOf(codeAgence));
		  		
		  		Convention convention = iChartDAO.avoirConvention(login.getUtilisateur().getAgence().getSociete());
		  		
		  		 
		  		
		  		
		  			if(vrais){
		  			
		  				System.out.println("vrais pour montant");
			  			
		  				
		  				LocalDate weekStart = firstDateInGrid;
				        LocalDate weekStop = null;
				        int weekNumber = 0;
				         
				        do {
				            weekNumber = weekNumber + 1;
				            weekStop = weekStart.plusDays( 6 );
				            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
				            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
				            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
				            
				            for (Map.Entry<Integer, ChartSeries> entry : ChartProduitsMT.entrySet())
							{
				            	
				            		
				            	 
							    //System.out.println(entry.getKey() + "/" + entry.getValue());
							    System.out.println("Item MT: " + entry.getKey() + " Count MT : " + entry.getValue());
							    entry.getValue().set(weekNumber, SommeMontantBarModel(entry.getKey(),weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention,month_value));
							    
				            	
				            	
				            
							}
				            
//				             TD.set(weekNumber, SommeMontantBarModel(1,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//				  			 Incendie.set(weekNumber, SommeMontantBarModel(2,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention)); 
//				  			 Assistance_voyage.set(weekNumber, SommeMontantBarModel(3,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//				  			 Avenir_jeunesse.set(weekNumber, SommeMontantBarModel(4,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			
			  			   
				            weekStart = weekStop.plusDays( 1 );
				        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  				
			  			 
			  			
		  			
		  			}else{
		  				
		  				System.out.println("faux pour montant");
		  				
		  				
		  				LocalDate weekStart = firstDateInGrid;
				        LocalDate weekStop = null;
				        int weekNumber = 0;
				         
				        do {
				            weekNumber = weekNumber + 1;
				            weekStop = weekStart.plusDays( 6 );
				            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
				            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
				            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
				            
				            for (Map.Entry<Integer, ChartSeries> entry : ChartProduitsMT.entrySet())
							{
							    
				            	
				            	
				            	//System.out.println(entry.getKey() + "/" + entry.getValue());
							    System.out.println("Item MT: " + entry.getKey() + " Count MT : " + entry.getValue());
							    entry.getValue().set(weekNumber, SommeMontantBarModelParAgence(entry.getKey(),agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
							    
				            	
				            	
				            	
							}
				            
//				            TD.set(weekNumber, SommeMontantBarModelParAgence(1,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//			    	        Incendie.set(weekNumber, SommeMontantBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));  
//			    	        Assistance_voyage.set(weekNumber, SommeMontantBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//			    	        Avenir_jeunesse.set(weekNumber, SommeMontantBarModelParAgence(4,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//			  				
			  			   
				            weekStart = weekStop.plusDays( 1 );
				        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  				
		  				
		  			   
		  			}
		  			

		  		  Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());
			        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
			        
			        for( Prodconv prd : lstproduit){
			        	
			        	
			        	//7ayya et avj
					    int code_vl = 0;
			    		int code_et = 0;
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
			    			code_vl = 6857;
			    			code_et = 6858;
			    		}
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)) {
			    			code_vl = 6867;
			    			code_et = 6868;
			    		}
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
			    			code_vl = 6927;
			    			code_et = 6928;
			    		}
						
						
						
						if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)||
				    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)||
				    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
							
							model.addSeries(ChartProduitsMT.get(code_vl));
			        		model.addSeries(ChartProduitsMT.get(code_et));
							
							
						}
			        	/*
			        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
			        		
			        		model.addSeries(ChartProduitsMT.get(97));
			        		model.addSeries(ChartProduitsMT.get(98));
			        	}
			        	
			        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
			        		
			        		//model.addSeries(ChartProduits.get(97));
			        		model.addSeries(ChartProduitsMT.get(99));
			        	}
			        	*/
			        	
			        	model.addSeries(ChartProduitsMT.get((int)prd.getProduit().getIdProduit()));
			        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
			        	System.out.println("id produit MT"+(int)prd.getProduit().getIdProduit());
			        }
	  		 
	    	        //model.addSeries(TD);
//	    	        model.addSeries(Incendie);
//	    	        model.addSeries(Assistance_voyage);
//	    	        model.addSeries(Avenir_jeunesse);
		  		
		  		
		  	}else{
		  		
		  		System.out.println("valProfil else = "+valProfil);
		  		
		  		Agence agence =  iChartDAO.avoirAgence(Integer.valueOf(codeAgence));
		  	   
		  		
		  		
		  		if(vrais){
		  			
		  			System.out.println("vrais pour montant");
		  			
		  			Convention convention = iChartDAO.avoirConvention(iChartDAO.avoiSociete(codeSociete));
		  			
		  			
		  			LocalDate weekStart = firstDateInGrid;
			        LocalDate weekStop = null;
			        int weekNumber = 0;
			         
			        do {
			            weekNumber = weekNumber + 1;
			            weekStop = weekStart.plusDays( 6 );
			            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
			            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
			            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
			            
			            for (Map.Entry<Integer, ChartSeries> entry : ChartProduitsMT.entrySet())
						{
						    //System.out.println(entry.getKey() + "/" + entry.getValue());
						    System.out.println("Item MT: " + entry.getKey() + " Count MT : " + entry.getValue());
						    entry.getValue().set(weekNumber, SommeMontantBarModel(entry.getKey(),weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention,month_value));
						    
						}
			            
//			             TD.set(weekNumber, SommeMontantBarModel(1,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			 Incendie.set(weekNumber, SommeMontantBarModel(2,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			 Assistance_voyage.set(weekNumber, SommeMontantBarModel(3,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));
//			  			 Avenir_jeunesse.set(weekNumber, SommeMontantBarModel(4,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),convention));	
//		  			   
			            weekStart = weekStop.plusDays( 1 );
			        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  			
		  			
		  			
		  		}else{
		  			
		  			System.out.println("faux pour montant");
		  			
		  			
		  			
		  			
		  			LocalDate weekStart = firstDateInGrid;
			        LocalDate weekStop = null;
			        int weekNumber = 0;
			         
			        do {
			            weekNumber = weekNumber + 1;
			            weekStop = weekStart.plusDays( 6 );
			            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
			            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
			            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
			            
			            for (Map.Entry<Integer, ChartSeries> entry : ChartProduitsMT.entrySet())
						{
						    //System.out.println(entry.getKey() + "/" + entry.getValue());
						    System.out.println("Item MT: " + entry.getKey() + " Count MT : " + entry.getValue());
						    entry.getValue().set(weekNumber, SommeMontantBarModelParAgence(entry.getKey(),agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),month_value));
						    
						}
			            
//			            TD.set(weekNumber, SommeMontantBarModelParAgence(1,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		    	        Incendie.set(weekNumber, SommeMontantBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		    	        Assistance_voyage.set(weekNumber, SommeMontantBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"))); 
//		    	        Avenir_jeunesse.set(weekNumber, SommeMontantBarModelParAgence(4,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
//		  		  
			            weekStart = weekStop.plusDays( 1 );
			        } while ( weekStop.isBefore( firstOfNextMonth ) );
		  			
		  			
		  			
		  		}
		  		
 
		  		 Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());
			        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
			        
			        for( Prodconv prd : lstproduit){
			        	
			        	//7ayya et avj
					    int code_vl = 0;
			    		int code_et = 0;
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
			    			code_vl = 6857;
			    			code_et = 6858;
			    		}
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)) {
			    			code_vl = 6867;
			    			code_et = 6868;
			    		}
			    		
			    		if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
			    			code_vl = 6927;
			    			code_et = 6928;
			    		}
						
						
						
						if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)||
				    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB)||
				    				prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
							
							model.addSeries(ChartProduitsMT.get(code_vl));
			        		model.addSeries(ChartProduitsMT.get(code_et));
							
						}
			        	/*
			        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA)) {
			        		
			        		model.addSeries(ChartProduitsMT.get(97));
			        		model.addSeries(ChartProduitsMT.get(98));
			        	}
			        	
			        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
			        		
			        		//model.addSeries(ChartProduits.get(97));
			        		model.addSeries(ChartProduitsMT.get(99));
			        	}
			        	*/
			        	
			        	model.addSeries(ChartProduitsMT.get((int)prd.getProduit().getIdProduit()));
			        	//model.addSeries(ChartProduits[(int) (prd.getProduit().getIdProduit()-1)]);
			        	System.out.println("id produit MT"+(int)prd.getProduit().getIdProduit());
			        }
	    	       
	  		 
	    	        //model.addSeries(TD);
//	    	        model.addSeries(Incendie);
//	    	        model.addSeries(Assistance_voyage);
//	    	        model.addSeries(Avenir_jeunesse);
		  		  
		  		
		  	}
 
        
       
         
        return model;
    }
 
      
    
 
    /***************************************** fin fct creation les bars ************************************************/
    
    
    
    
    
    /***************************************** fct creation camabert ************************************************/
    
    public void createPieModels() {
        createPieModel1();
    }
     
    
    private void createPieModel1() {
        pieModel1 = new PieChartModel();
     
        
        String valProfil = login.getUtilisateur().getProfil().getCode();
        
        System.out.println("valProfil "+valProfil);
        
    	//int idAgence = login.getUtilisateur().getAgence().getIdAgence();
    	Utilisateurs utilisateur =  login.getUtilisateur();
    	
    	int x = utilisateur.getIdUtilisateurs();
    	
	        if(valProfil.equalsIgnoreCase("BNAA")){
	    		
	        	pieModel1.set("TD", iChartDAO.countAdhesionYeartdbnaparuser(x));
			  	pieModel1.set("Incendie agricole", iChartDAO.countAdhesionYearincendieuser(x));
		        pieModel1.set("grele", iChartDAO.countAdhesionYeargreleuser(x));
	    	    	 
	    		
	    	}
	        
    	
	       else if(valProfil.equalsIgnoreCase("G")){
    		
    		 Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
		        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
		        
		        
		        for( Prodconv prd : lstproduit){
		        	
		        	
		        	
		         	//7ayya et avj
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		pieModel1.set(prd.getProduit().getLibelleProduit() + " versement libre", pourcentagePieModelParUtilisateur_VL((int) prd.getProduit().getIdProduit(), utilisateur));
		        	
		        	}
		        	
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA) || 
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		pieModel1.set(prd.getProduit().getLibelleProduit() + " encaissement terme", pourcentagePieModelParUtilisateur_ET((int) prd.getProduit().getIdProduit(), utilisateur));
		        	
		        	}
		        	
		        	
		        	pieModel1.set( prd.getProduit().getLibelleProduit(),  pourcentagePieModelParUtilisateur((int) prd.getProduit().getIdProduit(), utilisateur) );
		        	System.out.println("id produit pie "+prd.getProduit().getIdProduit());
		        }
    		
    		
//    		  pieModel1.set("TD", pourcentagePieModelParUtilisateur(1, utilisateur));
//    	        pieModel1.set("Incendie", pourcentagePieModelParUtilisateur(2, utilisateur));
//    	        pieModel1.set("Assistance voyage", pourcentagePieModelParUtilisateur(3, utilisateur));
//    	        pieModel1.set("Avenir jeunesse", pourcentagePieModelParUtilisateur(4, utilisateur));
    		
    	 
    		
    	}else if(valProfil.equalsIgnoreCase("AA")) {
    		
    		 Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
		        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
		        
		        for( Prodconv prd : lstproduit){
		        	
		        	
		        	//7ayya et avj
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		pieModel1.set(prd.getProduit().getLibelleProduit() + " versement libre", pourcentagePieModelParAgence_VL((int) prd.getProduit().getIdProduit()));
		        	
		        	}
		        	
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA) || 
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		pieModel1.set(prd.getProduit().getLibelleProduit() + " encaissement terme", pourcentagePieModelParAgence_ET((int) prd.getProduit().getIdProduit()));
		        	
		        	}
		        	
		        	pieModel1.set (prd.getProduit().getLibelleProduit(), pourcentagePieModelParAgence((int) prd.getProduit().getIdProduit()) );
		        	System.out.println("id produit pie "+prd.getProduit().getIdProduit());
		        }
    		
//    		 pieModel1.set("TD", pourcentagePieModelParAgence(1));
//    		 pieModel1.set("Incendie", pourcentagePieModelParAgence(2));
// 	        pieModel1.set("Assistance voyage", pourcentagePieModelParAgence(3));
// 	        pieModel1.set("Avenir jeunesse", pourcentagePieModelParAgence(4));
    		
    		
    	}
    	
else if(valProfil.equalsIgnoreCase("BNA")) {
    		
    		/*String codeSociete = utilisateur.getAgence().getSociete().getCodSociet();
    		
    		 Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
		        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
		        
		        for( Prodconv prd : lstproduit){
		        	pieModel1.set(prd.getProduit().getLibelleProduit(), pourcentagePieModel((int) prd.getProduit().getIdProduit(),codeSociete));
		        	System.out.println("id produit pie "+prd.getProduit().getIdProduit());
		        }*/
    		
   		  	pieModel1.set("TD", iChartDAO.countAdhesionYeartdbna());
   		  	pieModel1.set("Incendie agricole", iChartDAO.countAdhesionYearincendie());
   	        pieModel1.set("grele", iChartDAO.countAdhesionYeargrele());
//    	        pieModel1.set("Avenir jeunesse", pourcentagePieModel(4,codeSociete));
    		
    	}
    	
    	
    	
    	else if(valProfil.equalsIgnoreCase("AS")) {
    		
    		String codeSociete = utilisateur.getAgence().getSociete().getCodSociet();
    		
    		 Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
		        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
		        
		        for( Prodconv prd : lstproduit){
		        	
		        	//7ayya et avj
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		pieModel1.set(prd.getProduit().getLibelleProduit() + " versement libre", pourcentagePieModel_VL((int) prd.getProduit().getIdProduit(),codeSociete));
		        	
		        	}
		        	
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA) || 
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		pieModel1.set(prd.getProduit().getLibelleProduit() + " encaissement terme", pourcentagePieModel_ET((int) prd.getProduit().getIdProduit(),codeSociete));
		        	
		        	}
		        	
		        	pieModel1.set(prd.getProduit().getLibelleProduit(), pourcentagePieModel((int) prd.getProduit().getIdProduit(),codeSociete));
		        	System.out.println("id produit pie "+prd.getProduit().getIdProduit());
		        }
    		
//    		  	pieModel1.set("TD", pourcentagePieModel(1,codeSociete));
//    	        pieModel1.set("Incendie", pourcentagePieModel(2,codeSociete));
//    	        pieModel1.set("Assistance voyage", pourcentagePieModel(3,codeSociete));
//    	        pieModel1.set("Avenir jeunesse", pourcentagePieModel(4,codeSociete));
    		
    	}else{
    		
    		System.out.println("codeSociete pour star "+codeSociete);
    		
    		 
    		 Convention c=conventionDAO.find_conv_by_Societ(iChartDAO.avoiSociete(codeSociete));
		        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
		        
		        for( Prodconv prd : lstproduit){
		        	
		        	
		        	//7ayya et avj
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		pieModel1.set(prd.getProduit().getLibelleProduit() + " versement libre", pourcentagePieModel_VL((int) prd.getProduit().getIdProduit(),codeSociete));
		        	
		        	}
		        	
		        	if(prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA) || 
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_HAYYA_QNB) ||
		        			prd.getProduit().getCodeProduit().equalsIgnoreCase(CODE_AVJ)) {
		        		
		        		pieModel1.set(prd.getProduit().getLibelleProduit() + " encaissement terme", pourcentagePieModel_ET((int) prd.getProduit().getIdProduit(),codeSociete));
		        	
		        	}
		        	
		        	
		        	pieModel1.set(prd.getProduit().getLibelleProduit(), pourcentagePieModel((int) prd.getProduit().getIdProduit(),codeSociete));
		        	System.out.println("id produit pie "+prd.getProduit().getIdProduit());
		        }
    		
//    		pieModel1.set("TD", pourcentagePieModel(1,codeSociete));
//	        pieModel1.set("Incendie", pourcentagePieModel(2,codeSociete));
//	        pieModel1.set("Assistance voyage", pourcentagePieModel(3,codeSociete));
//	        pieModel1.set("Avenir jeunesse", pourcentagePieModel(4,codeSociete));
    	}
        
        
         
    	
      
         
        pieModel1.setTitle("Répartition des contrats d'assurance pour l'année en cours");
        pieModel1.setLegendPosition("n");
        pieModel1.setLegendPlacement(LegendPlacement.OUTSIDEGRID);  
        pieModel1.setFill(true);
        pieModel1.setShowDataLabels(true);
    }
 
    
    /***************************************** fin fct creation camabert ************************************************/
   
    
    
    
    
    
    
    /******************************** calcul % les bars *****************************************************/
    
    
    /*****************  tous les produit  **************/
    
    private int pourcentageBarModel (int typeProduit, String annee1,String annee2,Convention convention,int num_mois){
    	
    	int typeProduit_ = typeProduit;
    	
    	if(typeProduit==6857 || typeProduit ==6858 ) {
    		
    		typeProduit_ = 10;
    	}
    	
    	
    	if(typeProduit==6867 || typeProduit ==6868 ) {
    		
    		typeProduit_ = 11;
    	}
    	
    	
		if(typeProduit==6927 || typeProduit ==6928 ) {
    		
    		typeProduit_ = 4;
    	}
    	
    	System.out.println("tous les produit :  (les bars)");
    	 
        Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit_));
        System.out.println("val_hori= "+produitTD.getLibelleProduit());
        
        //Convention convention = iChartDAO.avoirConvention(iChartDAO.avoiSociete(codeSociete));
   
        System.out.println("convention "+convention.getIdConvention());
        
        List<Police> listePoliceTD=  iChartDAO.listePoliceParConvention(produitTD,convention);
//        System.out.println("val2_hori= "+listePoliceTD.get(0).getNPolice());
        System.out.println("listePoliceTD.size()_hori "+listePoliceTD.size());
//        System.out.println("adhesion count_hori "+ iChartDAO.countAdhesionParDate(listePoliceTD.get(0), annee1 , annee2,num_mois));
        
         
        long count = 0;
        for (Police policeTD : listePoliceTD) {
			    
        	if(typeProduit==6857 || typeProduit==6867 || typeProduit==6927) {
        		
        		count=count+ iChartDAO.countAdhesionParDate_VL(policeTD, annee1 , annee2,num_mois);
        		
        	}
        	
        	else if(typeProduit ==6858 || typeProduit ==6868 || typeProduit ==6928) {
        		
        		count=count+ iChartDAO.countAdhesionParDate_ET(policeTD, annee1 , annee2,num_mois);
        		
        	}
        	
        	else {
        		
        		count=count+ iChartDAO.countAdhesionParDate(policeTD, annee1 , annee2,num_mois);
        		
        	}
        	
       	 
	        }
        
        System.out.println("count_hori "+ count);
        
        
        return (int) count;
    	
    }
    
    

    
    
    /*****************  tous les produit  par utilisateur**************/
    
    private int pourcentageBarModelParUtilisateur (int typeProduit, Utilisateurs utilisateur,String annee1,String annee2,int num_mois){
    	
    	System.out.println("tous les produit  par utilisateur : (les bars)");
    	
int typeProduit_ = typeProduit;
    	
    	if(typeProduit==6857 || typeProduit ==6858 ) {
    		
    		typeProduit_ = 10;
    	}
    	
       	if(typeProduit==6867 || typeProduit ==6868 ) {
    		
    		typeProduit_ = 11;
    	}
    	
    	if(typeProduit==6927 || typeProduit ==6928  ) {
    		
    		typeProduit_ = 4;
    	}
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit_));
         System.out.println("val_hori= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
         System.out.println("listePoliceTD.size()_hori "+listePoliceTD.size());
         
         long count = 0;
         for (Police policeTD : listePoliceTD) {
			  
        	 
    		  if(typeProduit==6857 || typeProduit==6867 || typeProduit==6927)
    			  count=count+ iChartDAO.countAdhesionParDatePArUtilisateur_VL(policeTD, utilisateur, annee1 , annee2,num_mois);	 	 
        	 
        	 else if(typeProduit==6858 || typeProduit ==6868 || typeProduit ==6928)
        		 count=count+ iChartDAO.countAdhesionParDatePArUtilisateur_VL(policeTD, utilisateur, annee1 , annee2,num_mois);	 	 
        	 
        	 else
        	 count=count+ iChartDAO.countAdhesionParDatePArUtilisateur(policeTD, utilisateur, annee1 , annee2,num_mois);
	        }
         
         System.out.println("count "+ count);
         
         return (int) count;
    }
    
    // bnaaaa
    
 private int pourcentageBarModelParUtilisateurbna (int typeProduit, Utilisateurs utilisateur,String annee1,String annee2,int num_mois){
    	
    	System.out.println("tous les produit  par utilisateur : (les bars)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val_hori= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolicebna(produitTD);
         System.out.println("listePoliceTD.size()_hori "+listePoliceTD.size());
         
         long count = 0;
     
			    
        	 count=count+ iChartDAO.countAdhesionParDatePArUtilisateurbna(utilisateur, annee1 , annee2,num_mois);
	     
         
         System.out.println("count "+ count);
         
         return (int) count;
    }
    
    
    
    // bnaaa
    
    /*****************  tous les produit  pour une agence**************/
    
    private int pourcentageBarModelParAgence(int typeProduit, Agence agence,String annee1, String annee2,int num_mois){

    	int typeProduit_ = typeProduit;
    	
    	if(typeProduit==6857 || typeProduit ==6858 ) {
    		
    		typeProduit_ = 10;
    	}
    	
       	if(typeProduit==6867 || typeProduit ==6868 ) {
    		
    		typeProduit_ = 11;
    	}
    	
    	if(typeProduit==6927 || typeProduit ==6928  ) {
    		
    		typeProduit_ = 4;
    	}
    	
    	System.out.println("tous les produit  pour une agence : (les bars)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit_));
         System.out.println("val_hori= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
         System.out.println("listePoliceTD.size()_hori "+listePoliceTD.size());
         
         List<Utilisateurs> listeUtilisateurs = iChartDAO.listeUtilisateur(agence);
         System.out.println("listeUtilisateurs.size() "+listeUtilisateurs.size());
         
       
         long count = 0;
         for (Utilisateurs utilisateur : listeUtilisateurs){
       
         
         for (Police policeTD : listePoliceTD) {
			   
        	 if(typeProduit==6857 || typeProduit==6867 || typeProduit==6927 ) {
         		
        		 count=count+ iChartDAO.countAdhesionParDatePArUtilisateur_VL(policeTD, utilisateur,  annee1,  annee2,num_mois);
         		
         	}
         	
         	else if(typeProduit ==6858 || typeProduit ==6868 || typeProduit ==6928) {
         		
         		count=count+ iChartDAO.countAdhesionParDatePArUtilisateur_ET(policeTD, utilisateur,  annee1,  annee2,num_mois);
         		
         	}
         	
         	else {
        	 
        	 count=count+ iChartDAO.countAdhesionParDatePArUtilisateur(policeTD, utilisateur,  annee1,  annee2,num_mois);
        	 System.out.println("utilisateur_move "+utilisateur.getIdUtilisateurs());
        	 
         	}
        	 
	        }
         
         
         }
         
         System.out.println("count "+ count);
    	
    	return (int)count;
    }
    
    
    
    
    
    
    //for bna
    
    
    
    private int pourcentageBarModelParAgencebna(int typeProduit, Agence agence,String annee1, String annee2,int num_mois){
    	
    	System.out.println("bnaaaa ======== tous les produit  pour une agence : (les bars)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val_hori= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolicebna(produitTD);
         System.out.println("listePoliceTD.size()_hori "+listePoliceTD.size());
         
         List<Utilisateurs> listeUtilisateurs = iChartDAO.listeUtilisateurbna(agence);
         System.out.println("listeUtilisateurs.size() "+listeUtilisateurs.size());
         
       
         long count = 0;
         for (Utilisateurs utilisateur : listeUtilisateurs){
       
         
        
			    
        	 count=count+ iChartDAO.countAdhesionParDatePArUtilisateurbna( utilisateur,  annee1,  annee2,num_mois);
        	// System.out.println("utilisateur_move "+utilisateur.getIdUtilisateurs());
	       
         
         
         }
         
         System.out.println("count "+ count);
    	
    	return (int)count;
    }
    
    
    
    
    //end bna
    /******************************** fin calcul % les bars *****************************************************/
    
    
    
    
    
    
    /******************************** calcul % les bars pr montant *****************************************************/
    
    
    
    
/*****************  tous les produit  **************/
    
    private double SommeMontantBarModel (int typeProduit, String annee1,String annee2,  Convention convention,int num_mois){
    	
    	
    	int typeProduit_ = typeProduit;
    	
    	if(typeProduit==6857 || typeProduit ==6858 ) {
    		
    		typeProduit_ = 10;
    	}
    	
       	if(typeProduit==6867 || typeProduit ==6868 ) {
    		
    		typeProduit_ = 11;
    	}
    	
    	if(typeProduit==6927 || typeProduit ==6928 ) {
    		
    		typeProduit_ = 4;
    	}

    	System.out.println("tous les produit :  (les bars)");
    	 
        Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit_));
        System.out.println("val_hori= "+produitTD.getLibelleProduit());
        
        //Convention convention = iChartDAO.avoirConvention(iChartDAO.avoiSociete(codeSociete));
   
        System.out.println("convention "+convention.getIdConvention());
        
        List<Police> listePoliceTD=  iChartDAO.listePoliceParConvention(produitTD,convention);
        System.out.println("listePoliceTD.size()_hori "+listePoliceTD.size());
        
        
         
        double somme = 0;
        for (Police policeTD : listePoliceTD) {
        	
        	 if(typeProduit==1 || typeProduit ==3 || typeProduit==7 || typeProduit ==8)
        		 somme=somme+ iChartDAO.sommeMtPrime(policeTD, annee1 , annee2,num_mois);
        	 
        	 else if(typeProduit==6857 || typeProduit==6867 || typeProduit==6927 )
        		 somme=somme+ iChartDAO.sommeMtPrimeTot_VL(policeTD, annee1 , annee2,num_mois);
        		 
             else if(typeProduit ==6858 || typeProduit ==6868 || typeProduit ==6928)	
            	 somme=somme+ iChartDAO.sommeMtPrimeTot_ET(policeTD, annee1 , annee2,num_mois);
        	 
        	 else
        		 somme=somme+ iChartDAO.sommeMtPrimeTot(policeTD, annee1 , annee2,num_mois);	 
        
	        }
        
        System.out.println("somme "+ somme);
        
        
        return (double) somme;
    	
    }
    
    
    
    
  /*****************  tous les produit  par utilisateur**************/
    
    private double SommeMontantBarModelParUtilisateur (int typeProduit, Utilisateurs utilisateur,String annee1,String annee2,int num_mois){
    	
    	System.out.println("tous les produit  par utilisateur : (les bars)");
    	
int typeProduit_ = typeProduit;
    	
    	if(typeProduit==6857 || typeProduit ==6858 ) {
    		
    		typeProduit_ = 10;
    	}
    	
       	if(typeProduit==6867 || typeProduit ==6868 ) {
    		
    		typeProduit_ = 11;
    	}
		
		
		
		if(typeProduit==6927 || typeProduit ==6928 ) {
    		
    		typeProduit_ = 4;
    	}

    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit_));
         System.out.println("val_hori= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
         System.out.println("listePoliceTD.size()_hori "+listePoliceTD.size());
         
         double somme = 0;
         
         
         
         for (Police policeTD : listePoliceTD) {
			    
        	 if(typeProduit==1 || typeProduit ==3 || typeProduit==7 || typeProduit ==8)
        		 somme=somme+ iChartDAO.sommeMtPrimeParDateParUtilisateur(policeTD, utilisateur, annee1 , annee2,num_mois);
        	 
        	 else if(typeProduit==6857 || typeProduit==6867 || typeProduit==6927 )
        		 somme=somme+ iChartDAO.sommeMtPrimeTotParDateParUtilisateur_VL(policeTD, utilisateur, annee1 , annee2,num_mois);	 	 
        	 
        	 else if(typeProduit==6858 || typeProduit ==6868 || typeProduit ==6928)
        		 somme=somme+ iChartDAO.sommeMtPrimeTotParDateParUtilisateur_ET(policeTD, utilisateur, annee1 , annee2,num_mois);	 	 
        	 
        	 
        	 
        	 else
        		 somme=somme+ iChartDAO.sommeMtPrimeTotParDateParUtilisateur(policeTD, utilisateur, annee1 , annee2,num_mois);	 
	        }
         
         System.out.println("somme "+ somme);
         
         return (double) somme;
    }
    
    
    
    //bna
    
    
    
    private double SommeMontantBarModelParUtilisateurbna (int typeProduit, Utilisateurs utilisateur,String annee1,String annee2,int num_mois){
    	
    	System.out.println("tous les produit  par utilisateur : (les bars)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val_hori= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolicebnaagent(produitTD);
         System.out.println("listePoliceTD.size()_hori "+listePoliceTD.size());
         
         double somme = 0;
         
         
         for (Police policeTD : listePoliceTD) {
         
			    
        	 if(typeProduit==1 || typeProduit ==3 || typeProduit==7 || typeProduit ==8)
        		 somme=somme+ iChartDAO.sommeMtPrimeParDateParUtilisateurbna( utilisateur, annee1 , annee2,num_mois);
        	 
        	 else
        		 somme = 0;
	       
         }
         System.out.println("somme "+ somme);
         
         return (double) somme;
    }
    
    
    //bna
    
    
 /*****************  tous les produit  pour une agence**************/
    
    private double SommeMontantBarModelParAgence(int typeProduit, Agence agence,String annee1, String annee2,int num_mois){
    	
    	
    	int typeProduit_ = typeProduit;
    	
       	if(typeProduit==6857 || typeProduit ==6858 ) {
    		
    		typeProduit_ = 10;
    	}
   
       	if(typeProduit==6867 || typeProduit ==6868 ) {
    		
    		typeProduit_ = 11;
    	}
		
		
		
		if(typeProduit==6927 || typeProduit ==6928 ) {
    		
    		typeProduit_ = 4;
    	}


    	
    	System.out.println("tous les produit  pour une agence : (les bars)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit_));
         System.out.println("val_hori= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
         System.out.println("listePoliceTD.size()_hori "+listePoliceTD.size());
         
         List<Utilisateurs> listeUtilisateurs = iChartDAO.listeUtilisateur(agence);
         System.out.println("listeUtilisateurs.size() "+listeUtilisateurs.size());
         
       
         double somme = 0;
         for (Utilisateurs utilisateur : listeUtilisateurs){
       
         
         for (Police policeTD : listePoliceTD) {
			    
        	  
        	 
        	 if(typeProduit==1 || typeProduit ==3 || typeProduit==7 || typeProduit ==8)
        		 somme=somme+ iChartDAO.sommeMtPrimeParDateParUtilisateur(policeTD, utilisateur, annee1 , annee2,num_mois);
            	
        	 else if(typeProduit==6857 || typeProduit==6867 || typeProduit==6927 )
        		 somme=somme+ iChartDAO.sommeMtPrimeTotParDateParUtilisateur_VL(policeTD, utilisateur, annee1 , annee2,num_mois);	 
        	 
        	 else if(typeProduit==6858 || typeProduit ==6868 || typeProduit ==6928)
        		 somme=somme+ iChartDAO.sommeMtPrimeTotParDateParUtilisateur_ET(policeTD, utilisateur, annee1 , annee2,num_mois);	 
        	 
        		 
            	 else
            		 somme=somme+ iChartDAO.sommeMtPrimeTotParDateParUtilisateur(policeTD, utilisateur, annee1 , annee2,num_mois);	 
        	 
        	 
        	 
	        }
         
         
         }
         
         System.out.println("somme "+ somme);
    	
    	return (double)somme;
    }
    
 //bna
    
 private double SommeMontantBarModelParAgencebna(int typeProduit, Agence agence,String annee1, String annee2,int num_mois){
    	
    	System.out.println("sommme ========= tous les produit  pour une agence : (les bars)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val_hori= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolicebna(produitTD);
         System.out.println("listePoliceTD.size()_hori "+listePoliceTD.size());
         
         List<Utilisateurs> listeUtilisateurs = iChartDAO.listeUtilisateurbna(agence);
         System.out.println("listeUtilisateurs.size() "+listeUtilisateurs.size());
         
       
         double somme = 0;
         
         
         for (Utilisateurs utilisateur : listeUtilisateurs){
       
         
			    
        	  
        	 
        	 if(typeProduit==1 || typeProduit ==3 || typeProduit==7 || typeProduit ==8)
        		 somme=somme+ iChartDAO.sommeMtPrimeParDateParUtilisateurbna( utilisateur, annee1 , annee2,num_mois);
            	 
            	 else
            		 somme=0;
        	 
        	 
        	 
	       
         
         
         }
         
         System.out.println("somme "+ somme);
    	
    	return (double)somme;
    }
    
    
    //fin bna
    
    
    
    /******************************** calcul % camambert *****************************************************/
    
    
    /*****************  tous les produit **************/
    
    private int pourcentagePieModel(int typeProduit, String codeSociete){
    	
    	System.out.println("tous les produit : (camambert) ");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val= "+produitTD.getLibelleProduit());
         
         
         Convention convention = iChartDAO.avoirConvention(iChartDAO.avoiSociete(codeSociete));
         
         System.out.println("convention "+convention.getIdConvention());
         
         List<Police> listePoliceTD=  iChartDAO.listePoliceParConvention(produitTD,convention);
         
         System.out.println("listePoliceTD.size() "+listePoliceTD.size());
         //System.out.println("val2= "+listePoliceTD.get(0).getNPolice());
        
         //System.out.println("adhesion count "+ iChartDAO.countAdhesion(listePoliceTD.get(0)));
         
         //System.out.println("val3 "+iChartDAO.SimplePie(listePoliceTD.get(0)).get(0).getIdAdhesion());
         
         long count = 0;
         for (Police policeTD : listePoliceTD) {
			    
        	 count=count+ iChartDAO.countAdhesionYear(policeTD);
	        }
         
         System.out.println("count "+ count);
         
       /*  List<Adhesion> adhesionsTD = new ArrayList<Adhesion>(); 
         
      		   
      		   for (Police policeTD : listePoliceTD) {
      			   //listadhesionsTD.put(policeTD.getNPolice().trim(), iChartDAO.SimplePie(policeTD));
      			   adhesionsTD.addAll(iChartDAO.SimplePie(policeTD));
      	        }

         System.out.println("adhesionsTD.size() "+adhesionsTD.size());
         
         return adhesionsTD.size();*/
         
         return (int) count;

          
          //System.out.println( iChartDAO.SimplePie().get(1).getPolice().getProduit().getLibelleProduit());
          //System.out.println( iChartDAO.SimplePie().get(2).getPolice().getProduit().getLibelleProduit());
          
         /* List<Object[]> results =iChartDAO.SimplePieObject();
          
          for (int i = 0; i < results.size(); i++) {
      		Object[] arr = results.get(i);
      		for (int j = 0; j < arr.length; j++) {
      			System.out.print(arr[j] + " ");
      		}
      		System.out.println("ccc");
      		}*/
 
    }
    
    
    //// cas 7ayya 
    
    private int pourcentagePieModel_VL(int typeProduit, String codeSociete){
    	
    	System.out.println("tous les produit : (camambert) ");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val= "+produitTD.getLibelleProduit());
         
         
         Convention convention = iChartDAO.avoirConvention(iChartDAO.avoiSociete(codeSociete));
         
         System.out.println("convention "+convention.getIdConvention());
         
         List<Police> listePoliceTD=  iChartDAO.listePoliceParConvention(produitTD,convention);
         
         System.out.println("listePoliceTD.size() "+listePoliceTD.size());
         //System.out.println("val2= "+listePoliceTD.get(0).getNPolice());
        
         //System.out.println("adhesion count "+ iChartDAO.countAdhesion(listePoliceTD.get(0)));
         
         //System.out.println("val3 "+iChartDAO.SimplePie(listePoliceTD.get(0)).get(0).getIdAdhesion());
         
         long count = 0;
         for (Police policeTD : listePoliceTD) {
			    
        	 count=count+ iChartDAO.countAdhesionYear_VL(policeTD);
	        }
         
         System.out.println("count "+ count);
         

         
         return (int) count;


 
    }
    
    
    private int pourcentagePieModel_ET(int typeProduit, String codeSociete){
    	
    	System.out.println("tous les produit : (camambert) ");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val= "+produitTD.getLibelleProduit());
         
         
         Convention convention = iChartDAO.avoirConvention(iChartDAO.avoiSociete(codeSociete));
         
         System.out.println("convention "+convention.getIdConvention());
         
         List<Police> listePoliceTD=  iChartDAO.listePoliceParConvention(produitTD,convention);
         
         System.out.println("listePoliceTD.size() "+listePoliceTD.size());
         //System.out.println("val2= "+listePoliceTD.get(0).getNPolice());
        
         //System.out.println("adhesion count "+ iChartDAO.countAdhesion(listePoliceTD.get(0)));
         
         //System.out.println("val3 "+iChartDAO.SimplePie(listePoliceTD.get(0)).get(0).getIdAdhesion());
         
         long count = 0;
         for (Police policeTD : listePoliceTD) {
			    
        	 count=count+ iChartDAO.countAdhesionYear_ET(policeTD);
	        }
         
         System.out.println("count "+ count);
         

         
         return (int) count;


 
    }
    
    
    
    
 
    
    /*****************  tous les produit  par utilisateur**************/
    
    private int pourcentagePieModelParUtilisateur(int typeProduit, Utilisateurs utilisateur){
    	
    	System.out.println("tous les produit  par utilisateur : (camambert)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
         System.out.println("listePoliceTD.size() "+listePoliceTD.size());
         
         long count = 0;
         for (Police policeTD : listePoliceTD) {
			    
        	 count=count+ iChartDAO.countAdhesionParUtilisateurYear(policeTD, utilisateur);
	        }
         
         System.out.println("count "+ count);
         
         return (int) count;
    }
    
    /// 7ayya
    private int pourcentagePieModelParUtilisateur_VL(int typeProduit, Utilisateurs utilisateur){
    	
    	System.out.println("tous les produit  par utilisateur : (camambert)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
         System.out.println("listePoliceTD.size() "+listePoliceTD.size());
         
         long count = 0;
         for (Police policeTD : listePoliceTD) {
			    
        	 count=count+ iChartDAO.countAdhesionParUtilisateurYear_VL(policeTD, utilisateur);
	        }
         
         System.out.println("count "+ count);
         
         return (int) count;
    }
    
    private int pourcentagePieModelParUtilisateur_ET(int typeProduit, Utilisateurs utilisateur){
    	
    	System.out.println("tous les produit  par utilisateur : (camambert)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
         System.out.println("listePoliceTD.size() "+listePoliceTD.size());
         
         long count = 0;
         for (Police policeTD : listePoliceTD) {
			    
        	 count=count+ iChartDAO.countAdhesionParUtilisateurYear_ET(policeTD, utilisateur);
	        }
         
         System.out.println("count "+ count);
         
         return (int) count;
    }
    
    
    
    /*****************  tous les produit  pour une agence**************/
    
    private int pourcentagePieModelParAgence(int typeProduit){
    	
    	System.out.println("tous les produit  pour une agence : (camambert)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
         System.out.println("listePoliceTD.size() "+listePoliceTD.size());
         List<Utilisateurs> listeUtilisateurs = iChartDAO.listeUtilisateur(login.getUtilisateur().getAgence());
         System.out.println("listeUtilisateurs.size() "+listeUtilisateurs.size());
         
       
         long count = 0;
         for (Utilisateurs utilisateur : listeUtilisateurs){
       
         
         for (Police policeTD : listePoliceTD) {
			    
        	 count=count+ iChartDAO.countAdhesionParUtilisateurYear(policeTD, utilisateur);
	        }
         
         
         }
         
         System.out.println("count "+ count);
    	
    	return (int)count;
    }
    
    
    
    ///7ayya
    
    
    private int pourcentagePieModelParAgence_VL(int typeProduit){
    	
    	System.out.println("tous les produit  pour une agence : (camambert)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
         System.out.println("listePoliceTD.size() "+listePoliceTD.size());
         List<Utilisateurs> listeUtilisateurs = iChartDAO.listeUtilisateur(login.getUtilisateur().getAgence());
         System.out.println("listeUtilisateurs.size() "+listeUtilisateurs.size());
         
       
         long count = 0;
         for (Utilisateurs utilisateur : listeUtilisateurs){
       
         
         for (Police policeTD : listePoliceTD) {
			    
        	 count=count+ iChartDAO.countAdhesionParUtilisateurYear_VL(policeTD, utilisateur);
	        }
         
         
         }
         
         System.out.println("count "+ count);
    	
    	return (int)count;
    }
    
    
    private int pourcentagePieModelParAgence_ET(int typeProduit){
    	
    	System.out.println("tous les produit  pour une agence : (camambert)");
    	
    	 Produit produitTD = iChartDAO.avoirProduit(Long.valueOf(typeProduit));
         System.out.println("val= "+produitTD.getLibelleProduit());
         List<Police> listePoliceTD=  iChartDAO.listePolice(produitTD);
         System.out.println("listePoliceTD.size() "+listePoliceTD.size());
         List<Utilisateurs> listeUtilisateurs = iChartDAO.listeUtilisateur(login.getUtilisateur().getAgence());
         System.out.println("listeUtilisateurs.size() "+listeUtilisateurs.size());
         
       
         long count = 0;
         for (Utilisateurs utilisateur : listeUtilisateurs){
       
         
         for (Police policeTD : listePoliceTD) {
			    
        	 count=count+ iChartDAO.countAdhesionParUtilisateurYear_ET(policeTD, utilisateur);
	        }
         
         
         }
         
         System.out.println("count "+ count);
    	
    	return (int)count;
    }
    
    
    
    /******************************** fin  calcul % camambert *****************************************************/
    

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
 	
	    	 
		  if(listSociete != null && 

				  /*(valProfil.equalsIgnoreCase("AST")||valProfil.equalsIgnoreCase("IST")||valProfil.equalsIgnoreCase("TDST")||
	    	    			 valProfil.equalsIgnoreCase("VST")||valProfil.equalsIgnoreCase("ASI"))*/
	    	    	 /*** equivalente ****/	
	    			 
	    	    	(!valProfil.equalsIgnoreCase("G") && !valProfil.equalsIgnoreCase("AA") && !valProfil.equalsIgnoreCase("BNAA") && !valProfil.equalsIgnoreCase("AS") && !valProfil.equalsIgnoreCase("BNA"))	
				  
				  ){
    		  listAgence = iChartDAO.recupererListeAgence(iChartDAO.avoiSociete(codeSociete), "X");
    		  if (codeAgence==null) codeAgence= (String) listAgence.values().toArray()[0];
   	        System.out.println("le_codeAgence AST :"+codeAgence);
    		  }
		
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

	public  void avoirIntervalle(int mois,int annee,int jour) {
         
		    int month = mois;//DateTimeConstants.NOVEMBER;
		int year = annee;//2016;
		int dayOfWeek = jour;//DateTimeConstants.MONDAY;
		 
		LocalDate firstOfMonth = new LocalDate( year, month, 1 );
		LocalDate firstOfNextMonth = firstOfMonth.plusMonths( 1 );
		LocalDate firstDateInGrid = firstOfMonth.withDayOfWeek( dayOfWeek );
		if ( firstDateInGrid.isAfter( firstOfMonth ) ) { // If getting the next start of week instead of desired week's start, adjust backwards.
		    firstDateInGrid = firstDateInGrid.minusWeeks( 1 );
		}
		 
		LocalDate weekStart = firstDateInGrid;
		LocalDate weekStop = null;
		int weekNumber = 0;
		 
		do {
		    weekNumber = weekNumber + 1;
		    weekStop = weekStart.plusDays( 6 );
		    System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
		    weekStart = weekStop.plusDays( 1 );
		} while ( weekStop.isBefore( firstOfNextMonth ) );
		    }
		    
		 
   
	 public void __listeAgenceProduit__(ActionEvent actionEvent){
		 
		 System.out.println("actionEvent");
		 
		 
		 Agence agence =  iChartDAO.avoirAgence(Integer.valueOf(codeAgence));
	  		
	  		Convention convention = iChartDAO.avoirConvention(login.getUtilisateur().getAgence().getSociete());
		 
	  		AgenceProduit incendie = new AgenceProduit();
	  		AgenceProduit assistance_voyage = new AgenceProduit();
	  		AgenceProduit grele = new AgenceProduit();
	  		AgenceProduit incendieagr = new AgenceProduit();
	  		
	  		 int month_value = monthsLists[Integer.valueOf(month)-1];
	         int year_value = Integer.valueOf(year);
	         int dayOfWeek = DateTimeConstants.MONDAY;
	     	
	         LocalDate firstOfMonth = new LocalDate( year_value, month_value, 1 );
	         LocalDate firstOfNextMonth = firstOfMonth.plusMonths( 1 );
	         LocalDate firstDateInGrid = firstOfMonth.withDayOfWeek( dayOfWeek );
	         if ( firstDateInGrid.isAfter( firstOfMonth ) ) { // If getting the next start of week instead of desired week's start, adjust backwards.
	             firstDateInGrid = firstDateInGrid.minusWeeks( 1 );
	         }
	         
	  		
	  		LocalDate weekStart = firstDateInGrid;
	        LocalDate weekStop = null;
	        int weekNumber = 0;
	         
	        do {
	            weekNumber = weekNumber + 1;
	            weekStop = weekStart.plusDays( 6 );
	            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
	            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
	            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
	            
	            /*
    	        Incendie.set(weekNumber, pourcentageBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"))); 
    	        Assistance_voyage.set(weekNumber, pourcentageBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
    	        
    	        Incendie.set(weekNumber, SommeMontantBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));  
    	        Assistance_voyage.set(weekNumber, SommeMontantBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
   	       
   	       */
	            //incendie.setAgence(agence);
	            incendie.setNombre(2);
	            incendie.setNombre(pourcentageBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),0));
	            //incendie.setCa(SommeMontantBarModelParAgence(2,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));

    	        listeAgenceProduit.add(incendie);
    	        
    	        //assistance_voyage.setAgence(agence);
    	        assistance_voyage.setNombre(3);
    	        assistance_voyage.setNombre(pourcentageBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),0));
    	        //assistance_voyage.setCa(SommeMontantBarModelParAgence(3,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy")));
    	        
    	        listeAgenceProduit.add(assistance_voyage);
    	        
    	        grele.setNombre(4);
    	        grele.setNombre(pourcentageBarModelParAgence(4,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),0));
    	        listeAgenceProduit.add(grele);
    	        
    	        incendieagr.setNombre(5);
    	        incendieagr.setNombre(pourcentageBarModelParAgence(4,agence,weekStart.toString("dd/MM/yyyy"),weekStop.toString("dd/MM/yyyy"),0));
    	        listeAgenceProduit.add(incendieagr);
    	        
	            
	            weekStart = weekStop.plusDays( 1 );
	        } while ( weekStop.isBefore( firstOfNextMonth ) );
  			
	        System.out.println("end actionEvent");
	 }
	 
	 
	 public void buttonAction(ActionEvent actionEvent) {
	        addMessage("Welcome to Primefaces!!");
//	        List<AgenceProduit> agenceProduits = null;
//	        agenceProduits= iChartDAO.exportProduit(252, "21/11/2016", "27/11/2016");
//	        System.out.println("agenceProduits "+agenceProduits.get(0).getProduit());
	        
	    }
	     
	    public void addMessage(String summary) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
	    }
	    
	    
	    public void ExportListe(int idagence,String codesociete,List<Integer> listProduit){
	    	
	    	agenceProduits = new ArrayList<AgenceProduit>();
	    	
	    	int month_value = monthsLists[Integer.valueOf(month)-1];
	         int year_value = Integer.valueOf(year);
	         int dayOfWeek = DateTimeConstants.MONDAY;
	     	
	         LocalDate firstOfMonth = new LocalDate( year_value, month_value, 1 );
	         LocalDate firstOfNextMonth = firstOfMonth.plusMonths( 1 );
	         LocalDate firstDateInGrid = firstOfMonth.withDayOfWeek( dayOfWeek );
	         if ( firstDateInGrid.isAfter( firstOfMonth ) ) { // If getting the next start of week instead of desired week's start, adjust backwards.
	             firstDateInGrid = firstDateInGrid.minusWeeks( 1 );
	         }
	         
	  		
	  		LocalDate weekStart = firstDateInGrid;
	        LocalDate weekStop = null;
	        int weekNumber = 0;
	         
	        do {
	            weekNumber = weekNumber + 1;
	            weekStop = weekStart.plusDays( 6 );
	            System.out.println( weekNumber + " week: " + weekStart + " --- " + weekStop );  // 1 week: 03-30-2014 --- 04-05-2014
	            System.out.println("weekStart "+weekStart.toString("dd/MM/yyyy"));
	            System.out.println("weekStop "+weekStop.toString("dd/MM/yyyy"));
	            
	            AgenceProduit agprd = new AgenceProduit();
	            List<Object> objects = null;
	            
	            if(vrais){
		  		 System.out.println("vrais tout export");
		         objects= iChartDAO.exportProduit(codesociete, weekStart.toString("dd/MM/yyyy"), weekStop.toString("dd/MM/yyyy"),listProduit,month_value);
	            }else{
	            	
	            	System.out.println("faux tout export");
	            	objects= iChartDAO.exportProduit(idagence, weekStart.toString("dd/MM/yyyy"), weekStop.toString("dd/MM/yyyy"),listProduit,month_value);
	            }
		         //System.out.println("agenceProduits "+agenceProduits.get(0).getProduit());
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
		             
		             System.out.println("agprd "+agprd.getNombre());
		             
		             agenceProduits.add(agprd);
		             agprd = new AgenceProduit();
		             
		             
		              
		             
//		             for (Object r1: r) {
//		                 System.out.println(r1.toString());
//		             }
		          
		         }
		          
		         //System.out.println("agenceProduits "+agenceProduits.get(0).getProduit());
	  
	            
	            weekStart = weekStop.plusDays( 1 );
	        } while ( weekStop.isBefore( firstOfNextMonth ) );
 			
	     
	    }
    
    
	    
	    
	    
/// modif pour bna
	    
	    public void click() {
	    	
	    	System.out.println("clic stat");
	    	System.out.println("codeSociete "+codeSociete);
	    	System.out.println("codeAgence "+codeAgence);
	    	//System.out.println("date1 "+date1.toString());
	    	
	    	 Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			 String dateDu = formatter.format(date1);
			 String dateAu = formatter.format(date2);
	    	 
	    	produitStats = new ArrayList<ProduitStat>();
	    	//Agence agence =  iChartDAO.avoirAgence(Integer.valueOf("5555"));
	    	List<Agence> agence =  iChartDAO.avoirAgencebysoci();
       	// Convention c=conventionDAO.find_conv_by_Societ(agence.getSociete());
	    	
	    	Convention c=conventionDAO.find_conv_by_Societbna();
 	        lstproduit=prodconvDAO.getlstProduit_by_convention(c);
 	        List<Integer> listeIdProduit=new ArrayList<Integer>();
 	        
 	        for( Prodconv prd : lstproduit)
 	        	listeIdProduit.add((int)prd.getProduit().getIdProduit());
       	 
      
 	       ProduitStat agprd = new ProduitStat();
	    	 List<Object> objects = null;
	    	 
	    	 //B142431996 
	    	 
	    	  if(vrais){
			  		 System.out.println("vrais tout export stat");
			  		objects= iChartDAO.aficherStat(login.getUtilisateur().getAgence().getSociete().getCodSociet(), dateDu, dateAu,listeIdProduit);
			  		//objects= iChartDAO.aficherStat(codeSociete, dateDu, dateAu,listeIdProduit);
		            }else{
		            	
		            	System.out.println("faux tout export stat");
		            	objects= iChartDAO.aficherStat(Integer.valueOf(login.getUtilisateur().getAgence().getIdAgence()), dateDu, dateAu,listeIdProduit);
                  
		            	//login.getUtilisateur().getAgence().getIdAgence();
		            //	objects= iChartDAO.aficherStat(Integer.valueOf(codeAgence), dateDu, dateAu,listeIdProduit);
		            }
	    	 
	    	 
	   	   for (Object result : objects) {
	         	 
	         	
	             Object[] r = (Object[]) result;
	             
	             //System.out.println(Arrays.toString(r));
	             
	             agprd.setIdagence(Integer.valueOf(r[0].toString()));
	             agprd.setCodeagence(r[1].toString());
	            
	             agprd.setLibelle(r[2].toString());
	             agprd.setProduit(r[3].toString());
	             if (r[4]!=null ) agprd.setContrat(r[4].toString());
	             if (r[5]!=null ) agprd.setMtprime(Double.valueOf(r[5].toString()) + Double.valueOf(r[6].toString()));
	             if (r[7]!=null ) agprd.setCommission(Double.valueOf(r[7].toString()));
	            
	             System.out.println("r[1].toString() "+r[1].toString());
	             
	             
	             produitStats.add(agprd);
	             agprd = new ProduitStat();
	             
	             
	              
	             
//	             for (Object r1: r) {
//	                 System.out.println(r1.toString());
//	             }
	          
	         }
	       
	    }
//extraction des données par période	 
	    
 public void importData() {
	 
	    
	 String valProfil = login.getUtilisateur().getProfil().getCode();
	 
	 String codeSociete_ = login.getUtilisateur().getAgence().getSociete().getCodSociet();
	 
	 if(valProfil.equalsIgnoreCase("ASI")) {
		 
		 codeSociete_ = codeSociete;
	 }
	    	
	    	System.out.println("extraction des données par période");
	    	System.out.println("codeSociete "+codeSociete_);
	    	
	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
	    	 SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	    	 Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			 String dateDu = formatter.format(date1);
			 String dateAu = formatter.format(date2);
	    	 
			 System.out.println("dateDu "+dateDu);
		     System.out.println("dateAu "+dateAu);
		     System.out.println("login.getUtilisateur().getAgence().getSociete().getCodSociet() "+login.getUtilisateur().getAgence().getSociete().getCodSociet());
		     System.out.println("etat "+etat);
              importDatas =new ArrayList<ImportData>();
 	        ImportData agprd = new ImportData();
	    	 List<Object> objects = null;
			  	//objects= iChartDAO.exportData(login.getUtilisateur().getAgence().getSociete().getCodSociet(), dateDu, dateAu, etat);
		  		objects= iChartDAO.exportDataupdate(codeSociete_, dateDu, dateAu);

	    	   for (Object result : objects) {
		         	 
		             Object[] r = (Object[]) result;
		             agprd.setCodeagence(r[0].toString());
		             if (r[1]!=null ) agprd.setNcompte(r[1].toString());
		             agprd.setContrat(r[2].toString());
		             agprd.setDate_create(r[3].toString());
		             
		             if(r[4]!=null)
					 { 
		            	 
		             if (Integer.valueOf(r[4].toString()) == 1) {
						 agprd.setType_assist("Individuel");
					 }
					 else if (Integer.valueOf(r[4].toString()) == 2) {
						 agprd.setType_assist("Familial");
					 }
					 }
					 else {
						 agprd.setType_assist(" ");
					 }
		             //agprd.setType_assist(r[4].toString());
		            // agprd.setNom_client(r[3].toString());
		            // agprd.setPrenom_client(r[4].toString());
		             if(r[5]!=null)
					 { 
		            	 agprd.setAdhesion(r[5].toString());
		            	 
					 }else {
						 
						 agprd.setAdhesion("0");
						 
					 }
		             
		             //System.out.println("r[6].toString()"+r[6].toString());
		             agprd.setDebut(r[6].toString().substring(0, 10));
					 agprd.setFin(r[7].toString().substring(0, 10));
				     agprd.setDuree(Double.valueOf(r[8].toString()));
		             agprd.setEtat(r[9].toString());
		             agprd.setAnnulation(r[10].toString().substring(0, 10));
					 agprd.setMtprime(Double.valueOf(r[11].toString()));
										 
					/* if ( r[2].toString() =="110101724") {
						 commision = commision + ((Double.valueOf(r[11].toString())) * 0.33);
					 }
					 else {commision = commision;}*/

					 if(r[12]!=null)
					 {
						 agprd.setCommission(Double.valueOf(r[12].toString()));
					 }else{
						 agprd.setCommission(0);
					 }
					 
                     /*agprd.setDate_create(r[13].toString());
					 
					 if (Integer.valueOf(r[14].toString()) == 1) {
						 agprd.setType_assist("Individuel");
					 }
					 else if (Integer.valueOf(r[14].toString()) == 2) {
						 agprd.setType_assist("Familial");
					 }
					 else {
						 agprd.setType_assist("undefined");
					 }
*/
					 agprd.setPpnette_avec_surprime(Double.valueOf(r[14].toString()));
					 agprd.setFract(r[13].toString());
					 agprd.setSurprime(Double.valueOf(r[15].toString()));
					 
					 agprd.setPpnette(Double.valueOf(r[16].toString()));
					 
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
	    
	    
	    
	    
	    
/// fin modif pour bna	    
    
}