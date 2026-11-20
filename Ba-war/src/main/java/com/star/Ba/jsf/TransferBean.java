package com.star.Ba.jsf;

import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IChartDAO;
import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Utilisateurs;

import java.io.OutputStream;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author yassine
 *
 */
@ManagedBean(name = "transferBean")
@SessionScoped
public class TransferBean implements Serializable {

    private String value;

    private Date to;
    private Date from;

    @EJB
    private IAdhesionDAO adhesionDAO;
    
    @EJB
	private IChartDAO iChartDAO;

    @ManagedProperty(value = "#{login.utilisateur}")
    private Utilisateurs utilisateur;

    
    
   
    
    public  void transfer(){
    	if(utilisateur.getProfil().getCode().equalsIgnoreCase("TDST")){
    		 transferTD();
    	}else if (utilisateur.getProfil().getCode().equalsIgnoreCase("VST")){
    		TransferVoyageSTB();
    	}else if (utilisateur.getProfil().getCode().equalsIgnoreCase("VTST")){
    		
    		TransferVoyageTLS();
    		
    	}
    	
    	//TransferVoyageTLS();
    }
    
    
    
    
    
    public void TransferVoyageSTB() {
    	
    	
    	 System.out.println(to);
         System.out.println(from);
         if (from.after(to)) {
             FacesMessage msg = new FacesMessage("ERREUR",
                     "Verifier votre dates");
             msg.setSeverity(FacesMessage.SEVERITY_ERROR);
             FacesContext.getCurrentInstance().addMessage("Erreur", msg);
             return;
         }
         
         System.out.println("debut transfert ");

			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateDu = formatter.format(from);
			String dateAu = formatter.format(to);


         
         Produit produit = iChartDAO.avoirProduit(Long.valueOf(3));
         System.out.println("produit "+produit.getLibelleProduit());
         List<Police> listPolice=  iChartDAO.listePolice(produit);
         System.out.println("size police "+listPolice.size());
         System.out.println(" police "+listPolice.get(0).getNPolice());
         
         List<Adhesion> list = adhesionDAO.FindAdhesionByProduit(listPolice, dateDu, dateAu);
         System.out.println("size Adhesion "+list.size());
         
         List<Adherent> listAdherent = new ArrayList<Adherent>();
         List<Adherent> listeassurees =  new ArrayList<Adherent>();
         
         for(Adhesion adhesion :list ){
        	 
        	 for (Adherent adherent : adhesion.getAdherents()) {
        		 
        		 listAdherent.add(adherent);
				
			}
        	 
        	 //listeassurees.addAll(adhesionDAO.FindAssuree(adhesion));
        	 
         }
        	 
         //listAdherent.addAll(listeassurees);
         
         String format = "%-9s%-40s%-10s%-10s%-10s%-10s%-10s";
         StringBuilder sb = new StringBuilder();
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         
         for (Adherent adherent : listAdherent) {
        	 
        	 final String nAdhesion = (adherent.getAdhesion().getNadhesion()!= null) ? adherent.getAdhesion().getNadhesion() : "         " ;   ///////9
             final String nomPrenomClient =  adherent.getNomAdherent()+" "+adherent.getPrenomAdherent() ;///////////////40
             final String datNaiss = (adherent.getDatNaiss()!= null) ? sdf.format(adherent.getDatNaiss()) : "00/00/0000" ;/////10
             final String pack = "" ;//pack 2
            
             final String datDebut =  (adherent.getAdhesion().getDatDebut() !=null) ? sdf.format(adherent.getAdhesion().getDatDebut()) : "00/00/0000";//10
             final String datFin =  (adherent.getAdhesion().getDatFin() !=null) ? sdf.format(adherent.getAdhesion().getDatFin()) : "00/00/0000";//10
        	 final String destination = (adherent.getAdhesion().getDestination()!=null) ? adherent.getAdhesion().getDestination() : "          ";//10
        	 
//        	  System.out.println("format "+format);
//              System.out.println("nAdhesion "+nAdhesion);
//              System.out.println("nomPrenomClient "+nomPrenomClient);
//              System.out.println("datNaiss "+datNaiss);
//              System.out.println("pack "+pack);
//              System.out.println("datDebut "+datDebut);
//              System.out.println("datFin "+datFin);
//              System.out.println("destination "+destination);
              
              sb.append(String.format(format,
            		  nAdhesion,
            		  nomPrenomClient,
                      datNaiss,
                      pack,
                      datDebut,
                      datFin,
                      destination)
              );
              //new line
              sb.append(System.lineSeparator());
         }
         
         
         try {
             FacesContext fc = FacesContext.getCurrentInstance();
             ExternalContext ec = fc.getExternalContext();
             ec.responseReset();
             ec.setResponseContentType("text/plain");
             ec.setResponseContentLength(sb.toString().getBytes().length);
             ec.setResponseHeader("Content-Disposition",
                     "attachment; filename=\"Assistance_voyage_Transfert_STB.txt\"");

             OutputStream output = ec.getResponseOutputStream();
             output.write(sb.toString().getBytes());
             fc.responseComplete();

       

         } catch (Exception e) {
             e.printStackTrace();
         }
         System.out.println(sb.toString());
    	
    }
    
    
    
    public void TransferVoyageTLS() {
    	
    	
    	 System.out.println(to);
         System.out.println(from);
         if (from.after(to)) {
             FacesMessage msg = new FacesMessage("ERREUR",
                     "Verifier votre dates");
             msg.setSeverity(FacesMessage.SEVERITY_ERROR);
             FacesContext.getCurrentInstance().addMessage("Erreur", msg);
             return;
         }
         
         System.out.println("debut transfert ");

			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateDu = formatter.format(from);
			String dateAu = formatter.format(to);


         
         Produit produit = iChartDAO.avoirProduit(Long.valueOf(6));
         System.out.println("produit "+produit.getLibelleProduit());
         List<Police> listPolice=  iChartDAO.listePolice(produit);
         System.out.println("size police "+listPolice.size());
         System.out.println(" police "+listPolice.get(0).getNPolice());
         
         List<Adhesion> list = adhesionDAO.FindAdhesionByProduit(listPolice, dateDu, dateAu);
         System.out.println("size Adhesion "+list.size());
         
         List<Adherent> listAdherent = new ArrayList<Adherent>();
         List<Adherent> listeassurees =  new ArrayList<Adherent>();
         
         for(Adhesion adhesion :list ){
        	 
        	 for (Adherent adherent : adhesion.getAdherents()) {
        		 
        		 listAdherent.add(adherent);
				
			}
        	 
        	 //listeassurees.addAll(adhesionDAO.FindAssuree(adhesion));
        	 
         }
        	 
         //listAdherent.addAll(listeassurees);
         
         String format = "%-9s%-40s%-10s%-10s%-10s%-10s%-10s";
         StringBuilder sb = new StringBuilder();
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         
         for (Adherent adherent : listAdherent) {
        	 
        	 final String nAdhesion = (adherent.getAdhesion().getNadhesion()!= null) ? adherent.getAdhesion().getNadhesion() : "         " ;   ///////9
             final String nomPrenomClient =  adherent.getNomAdherent()+" "+adherent.getPrenomAdherent() ;///////////////40
             final String datNaiss = (adherent.getDatNaiss()!= null) ? sdf.format(adherent.getDatNaiss()) : "00/00/0000" ;/////10
             final String pack = adherent.getAdhesion().getPack().getLibelle() ;//pack 2
            
             final String datDebut =  (adherent.getAdhesion().getDatDebut() !=null) ? sdf.format(adherent.getAdhesion().getDatDebut()) : "00/00/0000";//10
             final String datFin =  (adherent.getAdhesion().getDatFin() !=null) ? sdf.format(adherent.getAdhesion().getDatFin()) : "00/00/0000";//10
        	 final String destination = (adherent.getAdhesion().getDestination()!=null) ? adherent.getAdhesion().getDestination() : "          ";//10
        	 
//        	  System.out.println("format "+format);
//              System.out.println("nAdhesion "+nAdhesion);
//              System.out.println("nomPrenomClient "+nomPrenomClient);
//              System.out.println("datNaiss "+datNaiss);
//              System.out.println("pack "+pack);
//              System.out.println("datDebut "+datDebut);
//              System.out.println("datFin "+datFin);
//              System.out.println("destination "+destination);
              
              sb.append(String.format(format,
            		  nAdhesion,
            		  nomPrenomClient,
                      datNaiss,
                      pack,
                      datDebut,
                      datFin,
                      destination)
              );
              //new line
              sb.append(System.lineSeparator());
         }
         
         
         try {
             FacesContext fc = FacesContext.getCurrentInstance();
             ExternalContext ec = fc.getExternalContext();
             ec.responseReset();
             ec.setResponseContentType("text/plain");
             ec.setResponseContentLength(sb.toString().getBytes().length);
             ec.setResponseHeader("Content-Disposition",
                     "attachment; filename=\"Assistance_voyage_Transfert_TLS.txt\"");

             OutputStream output = ec.getResponseOutputStream();
             output.write(sb.toString().getBytes());
             fc.responseComplete();

       

         } catch (Exception e) {
             e.printStackTrace();
         }
         System.out.println(sb.toString());
    	
    }
    
    
    
    
    public void transferTD() {
        System.out.println(to);
        System.out.println(from);
        if (from.after(to)) {
            FacesMessage msg = new FacesMessage("ERREUR",
                    "Verifier votre dates");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("Erreur", msg);
            return;
        }

     /*   List<Adhesion> list = adhesionDAO.findByUtilisateur(utilisateur);

        List<Adhesion> result = new ArrayList<Adhesion>();

        for (Adhesion adhesion : list) {

            //d.after(min) && d.before(max)
            if ((adhesion.getDatDebut().after(from) || adhesion.getDatDebut().equals(from)) && (adhesion.getDatDebut().before(to) || adhesion.getDatDebut().equals(to))) {
                result.add(adhesion);
            }
        }*/
        
        
        
        System.out.println("debut transfert ");

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateDu = formatter.format(from);
		String dateAu = formatter.format(to);


     
     Produit produit = iChartDAO.avoirProduit(Long.valueOf(1));
     System.out.println("produit "+produit.getLibelleProduit());
     List<Police> listPolice=  iChartDAO.listePolice(produit);
     System.out.println("size police "+listPolice.size());
     System.out.println(" police "+listPolice.get(0).getNPolice());
     
     List<Adhesion> result = adhesionDAO.FindAdhesionByProduit(listPolice, dateDu, dateAu);
     System.out.println("size Adhesion "+result.size());
        
        

        String format = "%9d%-42s%-10s%-2s%4d%09d%11s%08d%010d%-10s%05d%05d%06d%8s%-10s%9s%-1s%-1s%-30s";
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Adhesion adhesion : result) {
        	
            int nPolice = (adhesion.getPolice().getNPolice() != null) ? Integer.parseInt(adhesion.getPolice().getNPolice()) : 0 ;   ///////9
            final String nomClient = (adhesion.getClient().getNomClient()!= null) ? adhesion.getClient().getNomClient() : "xx" ;///////////////42
            final String datNaiss = (adhesion.getClient().getDatNaiss()!= null) ? sdf.format(adhesion.getClient().getDatNaiss()) : "xx" ;/////10
            final String groupe = (adhesion.getDatDebut()!=null) ? sdf.format(adhesion.getDatDebut()).substring(8) : "00" ;//groupe 2
            final int codeAgence = (adhesion.getUtilisateur().getAgence().getCodeAgence() !=null) ?  Integer.parseInt(adhesion.getUtilisateur().getAgence().getCodeAgence()) :0;//4
            final int numdoss = (adhesion.getNadhesion()!=null) ?  Integer.parseInt(adhesion.getNadhesion()) : 0;//numdoss 9
            final String ncompte =(adhesion.getClient().getNcompte()!=null) ? adhesion.getClient().getNcompte().substring(0, 11) : "00000000000";//MATRICULE 11
            final int primclt = (adhesion.getMtPrime()!=null) ? (int)(adhesion.getMtPrime()*1000) : 7;//PRIMCLT 8
            final int capital = (adhesion.getCapital() !=null) ?  (int) ((adhesion.getCapital())*1000) : 7;//CAPITAL  //10
            final String datDebut =  (adhesion.getDatDebut() !=null) ? sdf.format(adhesion.getDatDebut()) : "xx";//10
            
            
            final int  duree= ((Double)adhesion.getDuree() != null) ?  (int)adhesion.getDuree() : 0;////5
            
//             if((Double)adhesion.getDuree() != null){
//            	 
//            	double duree_double = adhesion.getDuree();
//            	 int dureeA = (int) duree_double;
//     			int dureeM = (int) ((duree_double - (int) duree_double) * 100);
//     			duree=(dureeA*12)+dureeM;
//            	 
//             }else{
//            	 
//            	 duree = 0;
//             }
          
    			
    		
            
            final int franchise ;//= (adhesion.getFranchise() !=null) ? Integer.parseInt(adhesion.getFranchise()) : 0;///5
            
            if(adhesion.getFranchise() !=null){
            	
            	
            	Object object = adhesion.getFranchise();
            	if(object instanceof Integer) {

            		franchise = (Integer) object ;

            		} else {
            			Double val = Double.parseDouble(adhesion.getFranchise());
                    	
                    	int val2 = (int) (val*1000);
                    	
                    	 franchise = val2;
            		}
            	
            	
              
            	 
            }else{
            	
            	franchise = 7;
            	
            }
            
            
            final int tauxInter = (adhesion.getTauxInter() !=null)  ? (adhesion.getTauxInter()).intValue()*1000 : 0;///6
            final String identifiant = (adhesion.getClient().getIdentifiant() !=null) ? adhesion.getClient().getIdentifiant() : "xx";//8
            final String datCreation = (adhesion.getClient().getDatCreation()!=null) ?  sdf.format(adhesion.getClient().getDatCreation()) : "xx";//10
            
            final String tauxSprim;// = (adhesion.getTauxSprim() !=null) ? (adhesion.getTauxSprim()).intValue()*1000 : 7;///9
            
            if(adhesion.getTauxSprim() !=null){
            	
            	Double val = adhesion.getTauxSprim();
            	
            	int val2 = (int) (val*1000);
            	
            	  if(val2==0) tauxSprim = "";
            	  else tauxSprim = String.valueOf(val2);
            	
            	
            }else{
            	
            	tauxSprim = "";
            	
            }
            
            
            
            final String etat1 = "5";//etat1 //1
            final String etat2 = "A";//etat2 //1
            final String prenomClient = adhesion.getClient().getPrenomClient();//30
            
//            
//            System.out.println("format "+format);
            System.out.println("nPolice "+nPolice);
            System.out.println("nomClient "+nomClient);
            System.out.println("datNaiss "+datNaiss);
            System.out.println("groupe "+groupe);
            System.out.println("codeAgence "+codeAgence);
            System.out.println("numdoss "+numdoss);
            System.out.println("ncompte "+ncompte);
            System.out.println("primclt "+primclt);
            System.out.println("capital "+capital);
            System.out.println("datDebut "+datDebut);
            System.out.println("duree "+duree);
            System.out.println("franchise "+franchise);
            System.out.println("tauxInter "+tauxInter);
            System.out.println("identifiant "+identifiant);
            System.out.println("datCreation "+datCreation);
            System.out.println("tauxSprim "+tauxSprim);
            System.out.println("prenomClient "+prenomClient);
            System.out.println("etat1 "+etat1);
            System.out.println("etat2 "+etat2);

            sb.append(String.format(format,
                    nPolice,
                    nomClient,
                    datNaiss,
                    groupe,
                    codeAgence,
                    numdoss,
                    ncompte,
                    primclt,
                    capital,
                    datDebut,
                    duree,
                    franchise,
                    tauxInter,
                    identifiant,
                    datCreation,
                    tauxSprim,
                    etat1,
                    etat2,
                    prenomClient)
            );
            //new line
            sb.append(System.lineSeparator());
        }

        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.responseReset();
            ec.setResponseContentType("text/plain");
            ec.setResponseContentLength(sb.toString().getBytes().length);
            ec.setResponseHeader("Content-Disposition",
                    "attachment; filename=\"TD_Transfert.txt\"");

            OutputStream output = ec.getResponseOutputStream();
            output.write(sb.toString().getBytes());
            fc.responseComplete();

            /*
            String path = "\\\\172.16.8.2\\STB";
            Date date = new Date();
            SimpleDateFormat pathDateFormatter = new SimpleDateFormat("yyyy\\MM\\");

            path += pathDateFormatter.format(date);
            File file = new File(path);
            if (file.mkdirs()) {

                PrintWriter writer = new PrintWriter(path + "transfer.txt", "UTF-8");
                writer.write(sb.toString());
                writer.close();
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public IAdhesionDAO getAdhesionDAO() {
        return adhesionDAO;
    }

    public void setAdhesionDAO(IAdhesionDAO adhesionDAO) {
        this.adhesionDAO = adhesionDAO;
    }

    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

}
