/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.mrh.contrat;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.star.Ba.commun.Constante;
import com.star.Ba.jsf.ReportController;
import com.star.Ba.model.TauxgarMrh;
import com.star.Ba.mrh.pack.MRHPack;

public class MRHRenewable extends MRHContract {

    private Date echeanceDate;
    private int fractionnement;
    
    @ManagedProperty("#{reportController}")
	private ReportController reportController;
    
    public ReportController getReportController() {
		return reportController;
	}

	public void setReportController(ReportController reportController) {
		this.reportController = reportController;
	}

    public MRHRenewable(Date echeanceDate, int fractionnement, String contractType,
            Date effDate, String clientType, String quality, int nbrPieceLess,
            int nbrPieceMore, String city, String riskNature, double area,
            MRHPack pack) {

        super(contractType, effDate, clientType, quality, nbrPieceLess, nbrPieceMore,
                city, riskNature, area, pack);
        this.echeanceDate = echeanceDate;
        this.fractionnement = fractionnement;
    }

    @Override
    public double getPrimeNette(List<TauxgarMrh> tauxList,int taux) throws Exception {
        double primeNette = super.getPrimeNette(tauxList,taux);
        double prorata = 1; //for all the contracts

        primeNette = (prorata * primeNette) * ((double) taux / 100);
        System.out.println("prime nette test="+ primeNette );
        primeNette = primeNette / fractionnement;
        

       // if (clientType.toUpperCase().equals("ST")) {
         //   primeNette = primeNette * (100 - pack.getRedStPac1()) / 100;
            
        //}
        
        System.out.println("rym primeNette "+ primeNette );

        return primeNette;
    }

    @Override
  public void generateReport(double primeNette, double tax, double cost,
            double primeTotal, String clientName,String clientType, HttpServletResponse response) {
        Map parameters = new HashMap();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        parameters.put("pack", pack.getName());
        System.out.println("pack"+ pack.getName());
        
        parameters.put("contratType", "Renouvlable");
        
        parameters.put("clientName", clientName);
        System.out.println("clientName"+ clientName);
        
        parameters.put("effDate", df.format(effDate));
        System.out.println("effDate"+ df.format(effDate));
        
        parameters.put("echDate", df.format(echeanceDate));
        System.out.println("echDate"+ df.format(echeanceDate));
        
        parameters.put("quality", quality);
        System.out.println("quality"+  quality);
        
        parameters.put("nbrPieceLess", String.valueOf(nbrPieceLess));
        System.out.println("nbrPieceLess"+ String.valueOf(nbrPieceLess));
        
        parameters.put("nbrPieceMore", String.valueOf(nbrPieceMore));
        System.out.println("nbrPieceMore"+ String.valueOf(nbrPieceMore));
        
        parameters.put("furniturePrice", (new DecimalFormat("#0.000"+"")).format(pack.getFurniturePrice()) + " TND");
        System.out.println("furniturePrice"+  (new DecimalFormat("#0.000"+"")).format(pack.getFurniturePrice()) + " TND");
        
        parameters.put("city", city);
        System.out.println("city"+ city);
System.out.println("*******************************************generateReport");
        parameters.put("clientType", clientType);
        System.out.println("clientType"+ clientType);

        parameters.put("duration", "Annuelle");
        
        parameters.put("riskNature", riskNature);
        System.out.println("riskNature"+ riskNature);
        
        parameters.put("area", String.valueOf(area));
        System.out.println("area"+ area);

        parameters.put("primeNette", (new DecimalFormat("#0.000"+"")).format(primeNette) + " TND");
        System.out.println("primeNette"+ (new DecimalFormat("#0.000"+"")).format(primeNette) + " TND");
        
        parameters.put("tax", (new DecimalFormat("#0.000"+"")).format(tax) + " TND");
        System.out.println("tax"+ (new DecimalFormat("#0.000"+"")).format(tax) + " TND");
        
        parameters.put("cost", (new DecimalFormat("#0.000"+"")).format(cost) + " TND");
        System.out.println("cost"+ (new DecimalFormat("#0.000"+"")).format(cost) + " TND");
        
        parameters.put("primeTotal", (new DecimalFormat("#0.000"+"")).format(primeTotal) + " TND");
        System.out.println("primeTotal"+ (new DecimalFormat("#0.000"+"")).format(primeTotal) + " TND");

//        parameters.put("baseGar", pack.getBaseGar());

        parameters.put("baseGar", pack.getBaseGar());
        System.out.println("baseGar"+ pack.getBaseGar());

        parameters.put("opGar", pack.getOpGarString());
        System.out.println("opGar"+ pack.getOpGarString());
        parameters.put("star",getRealPathImage()+"star.png");

        try {
            //String path=Constante.PDF+"mrhrenewable.jasper";
        	String path = /*reportController.*/getRealPath() + "mrhrenewable.jasper";
            //this.getClass().getResource("renewable.jasper").getPath()
    //        JasperPrint jp = JasperFillManager.fillReport(path, parameters, new JREmptyDataSource());
 JasperPrint jp = JasperFillManager.fillReport(path, parameters, new JREmptyDataSource());
            HttpServletResponse httpServletResponse;
            if (response == null) {
                httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            } else {
                httpServletResponse = response;
            }

            httpServletResponse.addHeader("Content-disposition", "inline; filename=report.pdf");
            ServletOutputStream servletOutputStream;
            try {
                servletOutputStream = httpServletResponse.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jp, servletOutputStream);
                if (response == null) {
                    FacesContext.getCurrentInstance().responseComplete();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
	public String getRealPath(){
		ServletContext cxt=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath= cxt.getRealPath("/");
		return realPath+"report/";
		
		 
		 
	}
	 public String getRealPathImage(){
			ServletContext cxt=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath= cxt.getRealPath("/");
			return realPath+"images/";
		}

	 
    @Override
    public void generateReportAdhesion(double primeNette, double tax, double cost,
            double primeTotal, String clientName ,String clientType, HttpServletResponse response) {
    	 Map parameters = new HashMap();
         DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

         parameters.put("pack", pack.getName());
         System.out.println("pack"+ pack.getName());
         
         parameters.put("contratType", "Renouvlable");
         
         parameters.put("clientName", clientName);
         System.out.println("clientName"+ clientName);
         
         parameters.put("effDate", df.format(effDate));
         System.out.println("effDate"+ df.format(effDate));
         
         parameters.put("echDate", df.format(echeanceDate));
         System.out.println("echDate"+ df.format(echeanceDate));
         
         parameters.put("quality", quality);
         System.out.println("quality"+  quality);
         
         parameters.put("nbrPieceLess", String.valueOf(nbrPieceLess));
         System.out.println("nbrPieceLess"+ String.valueOf(nbrPieceLess));
         
         parameters.put("nbrPieceMore", String.valueOf(nbrPieceMore));
         System.out.println("nbrPieceMore"+ String.valueOf(nbrPieceMore));
         
         parameters.put("furniturePrice", (new DecimalFormat("#0.000"+"")).format(pack.getFurniturePrice()) + " TND");
         System.out.println("furniturePrice"+  (new DecimalFormat("#0.000"+"")).format(pack.getFurniturePrice()) + " TND");
         
         parameters.put("city", city);
         System.out.println("city"+ city);
 System.out.println("*******************************************generateReport");
         parameters.put("clientType", clientType);
         System.out.println("clientType"+ clientType);

         parameters.put("duration", "Annuelle");
         
         parameters.put("riskNature", riskNature);
         System.out.println("riskNature"+ riskNature);
         
         parameters.put("area", String.valueOf(area));
         System.out.println("area"+ area);

         parameters.put("primeNette", (new DecimalFormat("#0.000"+"")).format(primeNette) + " TND");
         System.out.println("primeNette"+ (new DecimalFormat("#0.000"+"")).format(primeNette) + " TND");
         
         parameters.put("tax", (new DecimalFormat("#0.000"+"")).format(tax) + " TND");
         System.out.println("tax"+ (new DecimalFormat("#0.000"+"")).format(tax) + " TND");
         
         parameters.put("cost", (new DecimalFormat("#0.000"+"")).format(cost) + " TND");
         System.out.println("cost"+ (new DecimalFormat("#0.000"+"")).format(cost) + " TND");
         
         parameters.put("primeTotal", (new DecimalFormat("#0.000"+"")).format(primeTotal) + " TND");
         System.out.println("primeTotal"+ (new DecimalFormat("#0.000"+"")).format(primeTotal) + " TND");

//         parameters.put("baseGar", pack.getBaseGar());

         parameters.put("baseGar", pack.getBaseGar());
         System.out.println("baseGar"+ pack.getBaseGar());

         parameters.put("opGar", pack.getOpGarString());
         System.out.println("opGar"+ pack.getOpGarString());

         try {
             //String path=Constante.PDF+"mrhrenewable.jasper";
         	String path = /*reportController.*/getRealPath() + "mrhrenewable.jasper";
             //this.getClass().getResource("renewable.jasper").getPath()
     //        JasperPrint jp = JasperFillManager.fillReport(path, parameters, new JREmptyDataSource());
  JasperPrint jp = JasperFillManager.fillReport(path, parameters, new JREmptyDataSource());
             HttpServletResponse httpServletResponse;
             if (response == null) {
                 httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
             } else {
                 httpServletResponse = response;
             }

             httpServletResponse.addHeader("Content-disposition", "inline; filename=report.pdf");
             ServletOutputStream servletOutputStream;
             try {
                 servletOutputStream = httpServletResponse.getOutputStream();
                 JasperExportManager.exportReportToPdfStream(jp, servletOutputStream);
                 if (response == null) {
                     FacesContext.getCurrentInstance().responseComplete();
                 }
             } catch (IOException ex) {
                 System.out.println(ex.getMessage());
             }
         } catch (JRException e) {
             System.out.println(e.getMessage());
         }
    }
    
    
    
     

/*    @Override
    public MrhLogSimulation insertDatabase(String clientCIN, String codeAgence, double primeNette,
            double cost, double tax, double primeTotal, double monCom, String code_pack) throws SQLException {

        MrhLogSimulation simulation = new MrhLogSimulation(contractType, "Annuelle",
                effDate, echeanceDate, null, null, quality, riskNature, nbrPieceLess,
                nbrPieceMore, pack.getFurniturePrice(), area, city, primeNette, cost, tax,
                primeTotal, monCom, code_pack, (byte) (pack.isRedemption() ? 1 : 0));

        int sim_id = MRHParamsDAO.insertDatabase(simulation, clientCIN, "805",
                codeAgence);

        MRHParamsDAO.insertOptionsDatabase(pack.getOpCodeGar(), sim_id);

        return simulation;
    }
*/
    
}
