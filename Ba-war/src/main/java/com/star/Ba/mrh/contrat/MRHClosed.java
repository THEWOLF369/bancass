package com.star.Ba.mrh.contrat;

import com.star.Ba.commun.Constante;
import com.star.Ba.model.TauxgarMrh;
import com.star.Ba.mrh.pack.MRHPack;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class MRHClosed extends MRHContract {

    private Date lastEcheanceDate;
    private Date extinction;
    private String duration;

    public MRHClosed(Date lastEcheanceDate, Date extinction, String duration,
            String contractType, Date effDate, String clientType, String quality,
            int nbrPieceLess, int nbrPieceMore, String city, String riskNature,
            double area, MRHPack pack) {

        super(contractType, effDate, clientType, quality, nbrPieceLess,
                nbrPieceMore, city, riskNature, area, pack);
        this.lastEcheanceDate = lastEcheanceDate;
        this.extinction = extinction;
        this.duration = duration;
    }

  /*  @Override
    public double getPrimeNette(List<Tauxgar805> tauxList,int taux) throws Exception {
        double primeNette = super.getPrimeNette(tauxList,taux);

        double prorata = 1; //for all the contracts

        primeNette = (prorata * primeNette) * ((double) taux / 100);
        //primeNette = primeNette / fractionnement; for the renewable contracts

        if (clientType.toUpperCase().equals("ST")) {
            primeNette = primeNette * (100 - pack.getRedStPac1()) / 100;
        }

        return primeNette;
    }*/

    @Override
    public void generateReport(double primeNette, double tax, double cost,
            double primeTotal, String clientName ,String clientType, HttpServletResponse response) {
        Map parameters = new HashMap();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        parameters.put("pack", pack.getName());
        parameters.put("contratType", "Ferme");
        parameters.put("clientName", clientName);

        if (effDate != null) {
            parameters.put("effDate", df.format(effDate));
        } else {
            parameters.put("effDate", "N/A");
        }

        if (lastEcheanceDate != null) {
            parameters.put("lastEcheanceDate", df.format(lastEcheanceDate));
        } else {
            parameters.put("lastEcheanceDate", "N/A");
        }

        if (extinction != null) {
            parameters.put("extinction", df.format(extinction));
        } else {
            parameters.put("extinction", "N/A");
        }

        parameters.put("quality", quality);
        parameters.put("nbrPieceLess", String.valueOf(nbrPieceLess));
        parameters.put("nbrPieceMore", String.valueOf(nbrPieceMore));
        parameters.put("furniturePrice", (new DecimalFormat("#0.000")).format(pack.getFurniturePrice()) + " TND");
        parameters.put("city", city);

        parameters.put("clientType", clientType);

        parameters.put("duration", duration);
        parameters.put("riskNature", riskNature);
        parameters.put("area", String.valueOf(area));

        parameters.put("primeNette", (new DecimalFormat("#0.000")).format(primeNette) + " TND");
        parameters.put("tax", (new DecimalFormat("#0.000")).format(tax) + " TND");
        parameters.put("cost", (new DecimalFormat("#0.000")).format(cost) + " TND");
        parameters.put("primeTotal", (new DecimalFormat("#0.000")).format(primeTotal) + " TND");

        parameters.put("baseGar", pack.getBaseGar());

        parameters.put("opGar", pack.getOpGarString());
        parameters.put("star",getRealPathImage()+"star.png");
   

        try {
            //String path = Constante.PDF+"mrhclosed.jasper";
            String path = /*reportController.*/getRealPath() + "mrhclosed.jasper";
            //this.getClass().getResource("cC:\\Program Files\\Apache Software Foundation\\Tomcat 8.0\\webapps\\StarSimulation\\WEB-INF\\classes\\tn\\com\\star\\mrh\\model\\contract\\closed.jasperlosed.jasper").getPath()
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
    
    
    
    @Override
    public void generateReportAdhesion(double primeNette, double tax, double cost,
            double primeTotal, String clientName ,String clientType, HttpServletResponse response) {
        Map parameters = new HashMap();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        parameters.put("pack", pack.getName());
        parameters.put("contratType", "Ferme");
        parameters.put("clientName", clientName);

        if (effDate != null) {
            parameters.put("effDate", df.format(effDate));
        } else {
            parameters.put("effDate", "N/A");
        }

        if (lastEcheanceDate != null) {
            parameters.put("lastEcheanceDate", df.format(lastEcheanceDate));
        } else {
            parameters.put("lastEcheanceDate", "N/A");
        }

        if (extinction != null) {
            parameters.put("extinction", df.format(extinction));
        } else {
            parameters.put("extinction", "N/A");
        }

        parameters.put("quality", quality);
        parameters.put("nbrPieceLess", String.valueOf(nbrPieceLess));
        parameters.put("nbrPieceMore", String.valueOf(nbrPieceMore));
        parameters.put("furniturePrice", (new DecimalFormat("#0.000")).format(pack.getFurniturePrice()) + " TND");
        parameters.put("city", city);

        parameters.put("clientType", clientType);

        parameters.put("duration", duration);
        parameters.put("riskNature", riskNature);
        parameters.put("area", String.valueOf(area));

        parameters.put("primeNette", (new DecimalFormat("#0.000")).format(primeNette) + " TND");
        parameters.put("tax", (new DecimalFormat("#0.000")).format(tax) + " TND");
        parameters.put("cost", (new DecimalFormat("#0.000")).format(cost) + " TND");
        parameters.put("primeTotal", (new DecimalFormat("#0.000")).format(primeTotal) + " TND");

        parameters.put("baseGar", pack.getBaseGar());

        parameters.put("opGar", pack.getOpGarString());
    
        
        try {
            //String path = Constante.PDF+"mrhclosed.jasper";
            String path = /*reportController.*/getRealPath() + "mrhclosed.jasper";
            //this.getClass().getResource("cC:\\Program Files\\Apache Software Foundation\\Tomcat 8.0\\webapps\\StarSimulation\\WEB-INF\\classes\\tn\\com\\star\\mrh\\model\\contract\\closed.jasperlosed.jasper").getPath()
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

 /*   @Override
    public MrhLogSimulation insertDatabase(String clientCIN, String codeAgence, double primeNette, double cost, double tax, double primeTotal, double monCom, String code_pack) throws SQLException {
        int sim_id = ParamsDAO.insertDatabase(clientCIN, codeAgence, contractType,
                duration, effDate, null, lastEcheanceDate, extinction,
                quality, riskNature, nbrPieceLess, nbrPieceMore,
                pack.getFurniturePrice(), area, city, primeNette, cost, tax, primeTotal,
                monCom, code_pack, pack.isRedemption());

        ParamsDAO.insertOptionsDatabase(pack.getOpCodeGar(), sim_id);
        return sim_id;

        MrhLogSimulation simulation = new MrhLogSimulation(contractType, duration,
                effDate, null, lastEcheanceDate, extinction, quality, riskNature,
                nbrPieceLess, nbrPieceMore, pack.getFurniturePrice(), area,
                city, primeNette, cost, tax, primeTotal, monCom, code_pack,
                (byte) (pack.isRedemption() ? 1 : 0));

        int sim_id = MRHParamsDAO.insertDatabase(simulation, clientCIN, "805",
                codeAgence);

        MRHParamsDAO.insertOptionsDatabase(pack.getOpCodeGar(), sim_id);

        return simulation;
    }*/
}
