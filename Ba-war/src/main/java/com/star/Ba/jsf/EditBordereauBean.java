package com.star.Ba.jsf;

import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import com.star.Ba.commun.Constante;
import com.star.Ba.dao.IAgenceDAO;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IProdconvDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Duree;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;
import com.star.Ba.model.TypeAbonnement;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.AssistantVoiyagetServiceInter;
import com.star.Ba.dao.IChartDAO;
import com.star.Ba.dao.ChartDAO;

/**
 * @author
 *
 */
@ManagedBean(name = "editbordereauBean")
@SessionScoped
public class EditBordereauBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	    @EJB	   
	    private IConventionDAO conventionDAO;
        @EJB
	    private IProdconvDAO prodconvDAO;
	    @EJB
	    private IUtilisateursDAO utilisateursDAO;
	    @EJB
	    private IAgenceDAO agenceDAO;
	    @EJB
	    private IChartDAO ichartDAO;
	    
	    @ManagedProperty(value = "#{login.utilisateur}")
	    private Utilisateurs utilisateur;
	    @ManagedProperty(value = "#{reportController}")
	    private ReportController reportController ;
	    
	    
	    /*** rim***/
	    @EJB
		private AssistantVoiyagetServiceInter serviceAssistantVoiyagetService;
	    private List<TypeAbonnement> allTypeAbonnement=new ArrayList<TypeAbonnement>();
	    private  Duree selectedDuree=new Duree();
	    private String [] userSociete={PofileEnum.G.name(),PofileEnum.AA.name(),PofileEnum.AS.name()};
	    private String [] userStarass={PofileEnum.VST.name()};
	    private String [] userStarinc={PofileEnum.IST.name()};
	    private String [] userStarAvj={PofileEnum.AST.name()};
	    private String [] userStarHayya={PofileEnum.HST.name()};
	    private String [] userStarHayyaQnb={PofileEnum.HQST.name()};
	    private String [] userStarbna={PofileEnum.BNA.name()};
	    private String [] userMetierStar={PofileEnum.MSTA.name()};
		  private List<Produit> produits=new ArrayList<Produit>();
		  
		  @EJB
		  private IProduitDAO iProduitDAO;
	    
	private Date datdeb;
	private Date datefin;
	private Date aujourdhui;
    private String produit;
    private List<Prodconv> lstproduit;
    
    private Map<String, Object> listSociete=null;
	private String codeSociete;
	private String codeSocietebna = "B142431996";
	
	
	private String versement;


	
    public EditBordereauBean() {

    }


    
    
    TypeAbonnement selectedTypeAbonnement;
    @EJB
	private IProduitDAO produitDAO;
    
	public TypeAbonnement getSelectedTypeAbonnement() {
		return selectedTypeAbonnement;
	}


	public void setSelectedTypeAbonnement(TypeAbonnement selectedTypeAbonnement) {
		this.selectedTypeAbonnement = selectedTypeAbonnement;
	}


	public List<Prodconv> getLstproduit() {
		return lstproduit;
	}


	public void setLstproduit(List<Prodconv> lstproduit) {
		this.lstproduit = lstproduit;
	}


	public Date getDatdeb() {
		return datdeb;
	}


	public void setDatdeb(Date datdeb) {
		this.datdeb = datdeb;
	}


	public ReportController getReportController() {
		return reportController;
	}


	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}


	public Date getDatefin() {
		return datefin;
	}


	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}


	public Date getAujourdhui() {
		return aujourdhui;
	}


	public void setAujourdhui(Date aujourdhui) {
		this.aujourdhui = aujourdhui;
	}

	public IConventionDAO getConventionDAO() {
		return conventionDAO;
	}


	public void setConventionDAO(IConventionDAO conventionDAO) {
		this.conventionDAO = conventionDAO;
	}


	public IProdconvDAO getProdconvDAO() {
		return prodconvDAO;
	}


	public void setProdconvDAO(IProdconvDAO prodconvDAO) {
		this.prodconvDAO = prodconvDAO;
	}


	public IUtilisateursDAO getUtilisateursDAO() {
		return utilisateursDAO;
	}


	public void setUtilisateursDAO(IUtilisateursDAO utilisateursDAO) {
		this.utilisateursDAO = utilisateursDAO;
	}


	public IAgenceDAO getAgenceDAO() {
		return agenceDAO;
	}


	public void setAgenceDAO(IAgenceDAO agenceDAO) {
		this.agenceDAO = agenceDAO;
	}


	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}


	public String getProduit() {
		return produit;
	}


	public void setProduit(String produit) {
		this.produit = produit;
	}
	
	
	
	
	
	
	public String getVersement() {
		return versement;
	}


	public void setVersement(String versement) {
		this.versement = versement;
	}


	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	@PostConstruct
	public void init() {
		aujourdhui = new Date();
		if(Arrays.asList(userSociete).contains(utilisateur.getProfil().getCode())){
			Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
			System.out.println("convention=========="+c.getIdConvention());
			lstproduit=prodconvDAO.getlstProduit_by_convention(c);
			for (Prodconv produit : lstproduit) {
				produits.add(produit.getProduit());
			}
		}
		
		else if(Arrays.asList(userStarass).contains(utilisateur.getProfil().getCode())){
			listSociete = ichartDAO.recupererListeSociete();
			produits.add(iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.ASSISTANCEVOIYAGE.getCode()));
			produits.add(iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.TLSAssistV.getCode()));
		}
		
		else if(Arrays.asList(userStarinc).contains(utilisateur.getProfil().getCode())){
			listSociete = ichartDAO.recupererListeSociete();
			produits.add(iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.INCENDIE.getCode()));
		}
		
		else if(Arrays.asList(userStarAvj).contains(utilisateur.getProfil().getCode())){
			listSociete = ichartDAO.recupererListeSociete();
			produits.add(iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.AVENIRJENESSE.getCode()));
		}
		
		else if(Arrays.asList(userStarHayya).contains(utilisateur.getProfil().getCode())){
			listSociete = ichartDAO.recupererListeSociete();
			produits.add(iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.H7AYYA.getCode()));
		}
		
		else if(Arrays.asList(userStarHayyaQnb).contains(utilisateur.getProfil().getCode())){
			listSociete = ichartDAO.recupererListeSociete();
			produits.add(iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.H7ayyaQNB.getCode()));
		}
		
		else if(Arrays.asList(userMetierStar).contains(utilisateur.getProfil().getCode())){
			listSociete = ichartDAO.recupererListeSociete();
			produits = iProduitDAO.findByUser(utilisateur);
		}
		
		else if(Arrays.asList(userStarbna).contains(utilisateur.getProfil().getCode())){
			listSociete = ichartDAO.recupererListeSocietebna();
			produits.add(iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.Grele.getCode()));
			produits.add(iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.IncendieAgricole.getCode()));
			produits.add(iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.TD.getCode()));
			produits.add(iProduitDAO.getProduit_by_code(ENUMCODEPRODUIT.IncendieAlim.getCode()));

		}
		
		
		
		else {
			 listSociete = ichartDAO.recupererListeSociete();
			 produits=produitDAO.findAll();
			
			
		}
		
        /***rim***/
        setAllTypeAbonnement(serviceAssistantVoiyagetService.getAllTypeAbonnement());
        setAllDurees(serviceAssistantVoiyagetService.getAllDurees());
 }

	public void newBordereau() {
		datdeb = null;
		datefin = null;
	}
	
	
	public void imprimerBordereaubna() {
		String report;
        try{ 
        Map<String, Object> parameters = new HashMap();
        if(produit.equalsIgnoreCase("Grele")){
        	report = reportController.getRealPath() + "Bordereau_prodinc2.jasper";
        		
        }
        else  if(produit.equalsIgnoreCase("Incendie Agricole")){
        	
        	report = reportController.getRealPath() + "Bordereau_prodinc3.jasper";
        }
        
else  if(produit.equalsIgnoreCase("Incendie Alim")){
        	
        	report = reportController.getRealPath() + "Bordereau_prodin_alim.jasper";
        }
        
       else  if(produit.equalsIgnoreCase("TD")){
        	
        	report = reportController.getRealPath() + "Bordereau_prodinc4.jasper";
        }
            else{
            report = reportController.getRealPath() + "Bordereau_asstls.jasper";
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        parameters.put("produit", produit);
        parameters.put("datdeb", df.format(datdeb));
        parameters.put("datfin", df.format(datefin));
        if(utilisateur.getProfil().getCode().equals("AS") ) {  
        parameters.put("cod_societ", utilisateur.getAgence().getSociete().getCodSociet());
        parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
       
        }
        else{
        	 parameters.put("cod_societ", codeSociete); 
        	 System.out.println("cod_societ " + codeSociete);
        	 parameters.put("codagc", "");
        }
        parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
        parameters.put("imagesociete",""+utilisateur.getAgence().getSociete().getCodSociet()+".png");
        parameters.put("star","star.png");
      
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/bancass_star");
        Connection connection = dataSource.getConnection();
        
    

        JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);

        
        
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseContentLength(bytes.length);
        ec.setResponseHeader("Content-Disposition",
                "inline; filename=\"bordereau.pdf"
                + "\"");

        OutputStream output = ec.getResponseOutputStream();
        output.write(bytes);
        fc.responseComplete();
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}
	
	
	
	
	
          
	public void imprimerBordereau() {
		String report;
        try{ 
        Map<String, Object> parameters = new HashMap();
        if(produit.equalsIgnoreCase("Assistance voyage")){
        	report = reportController.getRealPath() + "Bordereau_prodass.jasper";
        	parameters.put("typecontrat", selectedTypeAbonnement.getValTypeAbonnement());
            parameters.put("duree", selectedDuree.getValDuree());	
            
            parameters.put("star","star.png");
        }
        else  if(produit.equalsIgnoreCase("Incendie")){
        	
        	report = reportController.getRealPath() + "Bordereau_prodinc.jasper";
        	
        	parameters.put("star","star.png");
        }
        else  if(produit.contains("INVESTI") || produit.contains("FUTURE DREAMS")){
        	
        	System.out.println("versement " + versement);
        	
        	
        	if(versement.equalsIgnoreCase("1")) {
        		
        		report = reportController.getRealPath() + "Bordereau_hayya_ET.jasper";
        		
        		parameters.put("versement", produit+" Versement Terme");
        		
        	}else if (versement.equalsIgnoreCase("2")) {
        		
        		report = reportController.getRealPath() + "Bordereau_hayya_VL.jasper";
        		
        		parameters.put("versement", produit+" Versement Libre");
        		
        	}else {
        		
        		report = reportController.getRealPath() + "Bordereau_hayya.jasper";
        		
        	}
        	
        	
        	parameters.put("star",reportController.getLogoJasper());
        	
        }
        
        else  if(produit.equalsIgnoreCase("UNIVERSITY")){
        	
        	//report = Constante.PDF+ "Bordereau_avj.jasper";
        	
        	if(versement.equalsIgnoreCase("1")) {
        		
        		report = reportController.getRealPath() + "Bordereau_avj_ET.jasper";
        		
        		parameters.put("versement", "UNIVERSITY Versement Terme");
        		
        	}else if (versement.equalsIgnoreCase("2")) {
        		
        		report = reportController.getRealPath() + "Bordereau_avj_VL.jasper";
        		
        		parameters.put("versement", "UNIVERSITY Versement Libre");
        		
        	}else {
        		
        		report = reportController.getRealPath() + "Bordereau_avj.jasper";
        		
        	}
        	
        	parameters.put("star",reportController.getLogoJasper());
        }
        
            else{
            report = reportController.getRealPath() + "Bordereau_asstls.jasper";
            
            parameters.put("star","star.png");
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        parameters.put("produit", produit);
        parameters.put("datdeb", df.format(datdeb));
        parameters.put("datfin", df.format(datefin));
        if(utilisateur.getProfil().getCode().equals("AS") ) {  
        parameters.put("cod_societ", utilisateur.getAgence().getSociete().getCodSociet());
        parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
       
        }
        else{
        	 parameters.put("cod_societ", codeSociete); 
        	 System.out.println("cod_societ " + codeSociete);
        	 parameters.put("codagc", "");
        }
        parameters.put("codagc", utilisateur.getAgence().getCodeAgence());
        parameters.put("imagesociete",""+utilisateur.getAgence().getSociete().getCodSociet()+".png");
//        parameters.put("star","star.png");
        parameters.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);
      
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/bancass_star");
        Connection connection = dataSource.getConnection();
        JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseContentLength(bytes.length);
        ec.setResponseHeader("Content-Disposition", "inline; filename=\"bordereau.pdf" + "\"");
        OutputStream output = ec.getResponseOutputStream();
        output.write(bytes);
        fc.responseComplete();
//        JRXlsxExporter exporter = new JRXlsxExporter();
//        exporter.setExporterInput(new SimpleExporterInput(print));
//        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C:/bordereau.xls");
//        File outputFile = new File("c:\\excelTest.xlsx");
//        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
//        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
//        configuration.setDetectCellType(true);//Set configuration as you like it!!
//        configuration.setCollapseRowSpan(false);
//        exporter.setConfiguration(configuration);
//        exporter.exportReport();
        //autre
//        String reportId = "Person";
//		Driver mDriver = new Driver();
//		DriverManager.registerDriver(mDriver);
//		Connection connection = DriverManager.getConnection(
//				Constants.DRIVER_DATABASE_MYSQL,
//				Constants.LOGIN_DATABASE_MYSQL,
//				Constants.PASSWORD_DATABASE_MYSQL);
//		File file = new File(Constants.PATH_OF_REPORTS_JASPER);
//		JasperPrint jasperPrint = JasperFillManager.fillReport(
//				new FileInputStream(new File(file, reportId + ".jasper")),
//				null, connection);
//		FacesContext context = FacesContext.getCurrentInstance();
//		HttpServletResponse response = (HttpServletResponse) context
//				.getExternalContext().getResponse();
//		if (jasperPrint != null) {
//			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//			JRXlsExporter exporter = new JRXlsExporter();
//			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,
//					jasperPrint);
//			exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
//					xlsReport);
//			exporter.exportReport();
//			// Send response
//			byte[] bytes = xlsReport.toByteArray();
//			/*******************************************************************
//			 * Pour afficher une boîte de dialogue pour enregistrer le fichier
//			 * sous le nom rapport.xls
//			 ******************************************************************/
//			response.addHeader("Content-disposition",
//					"attachment;filename=rapport.xls");
//			response.setContentType("application/vnd.ms-excel");
//			response.setContentLength(bytes.length);
//			response.getOutputStream().write(bytes, 0, bytes.length);
//			response.getOutputStream().flush();
//			response.getOutputStream().close();
//		} else {
//			Writer writer = response.getWriter();
//			writer.write("Aucun rapport à afficher");
//			response.setContentType("text/HTML");
//		}String reportId = "Person";
//		Driver mDriver = new Driver();
//		DriverManager.registerDriver(mDriver);
//		Connection connection = DriverManager.getConnection(
//				Constants.DRIVER_DATABASE_MYSQL,
//				Constants.LOGIN_DATABASE_MYSQL,
//				Constants.PASSWORD_DATABASE_MYSQL);
//		File file = new File(Constants.PATH_OF_REPORTS_JASPER);
//		JasperPrint jasperPrint = JasperFillManager.fillReport(
//				new FileInputStream(new File(file, reportId + ".jasper")),
//				null, connection);
//		FacesContext context = FacesContext.getCurrentInstance();
//		HttpServletResponse response = (HttpServletResponse) context
//				.getExternalContext().getResponse();
//		if (jasperPrint != null) {
//			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//			JRXlsExporter exporter = new JRXlsExporter();
//			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,
//					jasperPrint);
//			exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
//					xlsReport);
//			exporter.exportReport();
//			// Send response
//			byte[] bytes = xlsReport.toByteArray();
//			/*******************************************************************
//			 * Pour afficher une boîte de dialogue pour enregistrer le fichier
//			 * sous le nom rapport.xls
//			 ******************************************************************/
//			response.addHeader("Content-disposition",
//					"attachment;filename=rapport.xls");
//			response.setContentType("application/vnd.ms-excel");
//			response.setContentLength(bytes.length);
//			response.getOutputStream().write(bytes, 0, bytes.length);
//			response.getOutputStream().flush();
//			response.getOutputStream().close();
//		} else {
//			Writer writer = response.getWriter();
//			writer.write("Aucun rapport à afficher");
//			response.setContentType("text/HTML");
//		}
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}
	
	 
	public List<TypeAbonnement> getAllTypeAbonnement() {
		return allTypeAbonnement;
	}


	public void setAllTypeAbonnement(List<TypeAbonnement> allTypeAbonnement) {
		this.allTypeAbonnement = allTypeAbonnement;
	}
	
	public Converter getNatureAbonnementConverter() {
        return new Converter() {

            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String value) {
                for (TypeAbonnement obj : getAllTypeAbonnement()) {
                
                    if (obj.getValTypeAbonnement().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value) {
                return value.toString();
            }
        };
    }
	
	public Converter getDureConverter() {
        return new Converter() {

            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String value) {
                for (Duree obj : getAllDurees()) {
                    if (obj.getValDuree().equals(value)) {
                        return obj;
                    }
                }
                return null;
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value) {
                return value.toString();
            }
        };
    }
	
	
	List<Duree> allDurees =new ArrayList<Duree>();
    


	public List<Duree> getAllDurees() {
	return allDurees;
}


public void setAllDurees(List<Duree> allDurees) {
	this.allDurees = allDurees;
}

public Duree getSelectedDuree() {
	return selectedDuree;
}


public void setSelectedDuree(Duree selectedDuree) {
	this.selectedDuree = selectedDuree;
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


public String getCodeSocietebna() {
	return codeSocietebna;
}


public void setCodeSocietebna(String codeSocietebna) {
	this.codeSocietebna = codeSocietebna;
}




}






