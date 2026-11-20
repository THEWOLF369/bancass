/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.IComgouvMrhDAO;
import com.star.Ba.dao.IGarantieMrhDAO;
import com.star.Ba.dao.INatureRisqDAO;
//import com.star.Ba.dao.IOptionsSimulationDAO;
import com.star.Ba.dao.IPackDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IQualiteDAO;
import com.star.Ba.dao.ITarifMrhDAO;
import com.star.Ba.dao.ITauxcommDAO;
import com.star.Ba.dao.ITauxgarMrhDAO;
import com.star.Ba.model.Adhesion;
//import com.star.Ba.dao.ITypeCliDAO;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Beneficiaire;
import com.star.Ba.model.Client;
import com.star.Ba.model.ComgouvMrh;
import com.star.Ba.model.DetailMrh;
import com.star.Ba.model.GarantieMrh;
import com.star.Ba.model.NatureRisq;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Qualite;
//import com.star.Ba.model.OptionsSimulation;
//import com.star.Ba.model.OptionsSimulationPK;
//import com.star.Ba.model.PackautoSimulation;
import com.star.Ba.model.TarifMrh;
import com.star.Ba.model.Tauxcomm;
import com.star.Ba.model.TauxgarMrh;
import com.star.Ba.model.Utilisateurs;

/**
 *
 * @author star
 */
@Stateless
public  class ParametresMrhService extends AbstractDAO<Client> implements IParametresMrhService {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	
	@EJB
	private IGarantieMrhDAO iGarantieMrhDAO;
	
	public ParametresMrhService() {
		super(Client.class);
	}
	
    public ParametresMrhService(Class<Client> entityClass) {
		super(entityClass);
	}
    
    
    @Override
	protected EntityManager getEntityManager() {
    	return em;
	}
    

	@EJB
    private IComgouvMrhDAO comgouvmrhDAO;
    @EJB
    private IPackDAO packDAO;
    @EJB 
    private IQualiteDAO qualiteDAO;
    @EJB
    private INatureRisqDAO natureRisqDAO;
    @EJB
    private ITauxgarMrhDAO tauxgar805DAO;
    @EJB
    private ITauxcommDAO tauxcommDAO;
    @EJB
    private ITarifMrhDAO paramTarif805DAO;
    @EJB
    private IProduitDAO produitDAO;
    
    
    //rym
  //  @EJB   
   // private IQualiteDAO iQualiteDAO;
    
   // @EJB
    //private INatureRisqDAO iNatureRisqDAO  ;
    
    
//    @EJB
//    private IOptionsSimulationDAO optionsSimulationDAO;
   // @EJB
   // private IMrhLogSimulationDAO mrhLogSimulationDAO;
    @EJB 
    private IParametresMrhService parametresMrhService;
    
    
//    @EJB
//    private ITypeCliDAO typeCliDAO;

    @Override
    public Map<String, Object> getCities() {
        List<ComgouvMrh> list = comgouvmrhDAO.findAll();
        Map<String, Object> cities = new LinkedHashMap<String, Object>();
        for (ComgouvMrh comgouv1 : list) {
            cities.put(comgouv1.getGouvernora(), comgouv1.getGouvernora());
        }
        return cities;
    }

  

    @Override
    public List<TauxgarMrh> getTauxGarMrhByPack(String packName) {
        return tauxgar805DAO.getTauxGarMrhByPack(packName);
    }

    @Override
    public Tauxcomm getTauxCom(String codeProd) {
        return tauxcommDAO.getTauxCom(codeProd);
    }

    @Override
    public ComgouvMrh getTauxGouvByGouv(String gouv) {
        return comgouvmrhDAO.getTauxGouvByGouv(gouv);
    }

    @Override
    public TarifMrh getTarifMrh() {
        return paramTarif805DAO.getTarifMrh();
    }

    @Override
    public Produit getProduitByCode(String codeProduit) {
        return produitDAO.getProduit_by_code(codeProduit);
    }
    
    
    
    
    @Override
    public DetailMrh create_detail_mrh(Qualite qualite,NatureRisq natureRisq,BigDecimal nbrpieceless,BigDecimal nbrpiecemore,
    		BigDecimal furnitureprice,BigDecimal area,
    		String rachIn, String contractType, String duree,String residence,BigDecimal valsol,BigDecimal valmultim)
	 {
    	DetailMrh detailMrh =new DetailMrh();
		 
    	detailMrh.setQualite(qualite);
    	detailMrh.setNatureRisq(natureRisq);
    	detailMrh.setNbrpieceless(nbrpieceless);
    	detailMrh.setNbrpiecemore(nbrpiecemore);	 
    	detailMrh.setFurnitureprice(furnitureprice);	 
		detailMrh.setArea(area); 
		detailMrh.setRachIn(rachIn);
		detailMrh.setTypecontrat(contractType);
		detailMrh.setDuree(duree);
        detailMrh.setResidence(residence);
        detailMrh.setValsolaire(valsol);
        detailMrh.setValmultimedia(valmultim);
        
		return detailMrh;
	 }
    
    
    
    
    @Override
	public Proposition saveProposition(Client client, Adhesion adhesion,GarantieMrh garantieMrh1, 
			GarantieMrh garantieMrh2, GarantieMrh garantieMrh3, GarantieMrh garantieMrh10, GarantieMrh garantieMrh11, 
			GarantieMrh garantieMrh4, GarantieMrh garantieMrh5, GarantieMrh garantieMrh6,GarantieMrh garantieMrh7,
			GarantieMrh garantieMrh8, GarantieMrh garantieMrh9,GarantieMrh garantieMrh12,GarantieMrh garantieMrh13,
			GarantieMrh garantieMrh14,GarantieMrh garantieMrh15,GarantieMrh garantieMrh16,GarantieMrh garantieMrh17,
			Utilisateurs utilisateurs, long idpack) {
		Proposition proposition = new Proposition();
		proposition.setPolice(adhesion.getPolice());
		proposition.setClient(adhesion.getClient());

		//proposition.setPoliceCredit(adhesion.getPoliceCredit());

		// proposition.setCapital(adhesion.getCapital()==null?null:adhesion.getCapital())
		// ;

		proposition.setDatDebut(adhesion.getDatDebut());
		proposition.setDatFin(adhesion.getDatFin());
		//proposition.setDuree(adhesion.getDuree());

		proposition.setFraPolice(adhesion.getFraPolice());
		proposition.setDurPaiemt(adhesion.getDurPaiemt());

		proposition.setEtat(adhesion.getEtat());
		proposition.setDatMaj(adhesion.getDatMaj());
		proposition.setTauxPrime(adhesion.getTauxPrime());

		proposition.setMtPrime(adhesion.getMtPrime());
		
		proposition.setGouver(adhesion.getGouver());
		proposition.setAdresse(adhesion.getAdresse());
		proposition.setCodeP(adhesion.getCodeP());
		proposition.setLocalite(adhesion.getLocalite());
		
		

		proposition.setNombrePersonne(adhesion.getNombrePersonne());
		//proposition.setDestination(adhesion.getDestination());
		//proposition.setTelphone(adhesion.getTelphone());
		proposition.setTypeAbonnement(adhesion.getTypeAbonnement());
		
		proposition.setTauxInter(adhesion.getTauxInter());
		proposition.setTaxe(adhesion.getTaxe());
		proposition.setPrimetot(adhesion.getPrimetot());
		proposition.setCout(adhesion.getCout());
		
		 
		
		proposition.setDatcreation(new Date());
		if (client.getIdClient() == 0) {
			client.setDatCreation(new Date());
		}

		proposition.setClient(client);

		proposition.setIdUtilisateurs(utilisateurs.getIdUtilisateurs());
		
		
		System.out.println("1 adhesion"+adhesion.getDetailMrh().getNbrpieceless());
		System.out.println("2 "+adhesion.getDetailMrh().getNbrpiecemore());
		System.out.println("3 "+adhesion.getDetailMrh().getFurnitureprice());
		System.out.println("4 "+adhesion.getDetailMrh().getArea());
		System.out.println("5 "+adhesion.getGouver());
		System.out.println("6 "+adhesion.getAdresse());
		System.out.println("7 "+adhesion.getCodeP());
		System.out.println("8 "+adhesion.getLocalite());
		System.out.println("9 "+adhesion.getDetailMrh().getRachIn());
		System.out.println("10 "+adhesion.getNombrePersonne());
		
		
		proposition.setDetailMrh(adhesion.getDetailMrh());
		
		proposition.setPack(adhesion.getPack());
		
		
		
		
		if(idpack==3 ){
			
			if(garantieMrh15 !=null){
				 
				
				for(Beneficiaire benf : adhesion.getBeneficiaires()){
					benf.setAdhesion(null);
					proposition.getBeneficiaires().add(benf);
					
				}
				
				//proposition.setBeneficiaires(adhesion.getBeneficiaires());
				
				 
			}
			
			
			
		}
		
		
		
		
		
		
		
//		beneficiaire.setPropositions(proposition);
//		beneficiaire.setAdhesion(adhesion);
		
//		garantieMrh1.setAdhesion(adhesion);
//		garantieMrh2.setAdhesion(adhesion);
//		garantieMrh3.setAdhesion(adhesion);
//		garantieMrh10.setAdhesion(adhesion);
//		garantieMrh11.setAdhesion(adhesion);
		
//		garantieMrh1.setProposition(proposition);
//		garantieMrh2.setProposition(proposition);
//		garantieMrh3.setProposition(proposition);
//		garantieMrh11.setProposition(proposition);

		return createProposition(proposition,  
				garantieMrh1,  garantieMrh2,  garantieMrh3,  garantieMrh10,  garantieMrh11, 
				 garantieMrh4,  garantieMrh5,  garantieMrh6, garantieMrh7,
				 garantieMrh8,  garantieMrh9, garantieMrh12, garantieMrh13,
				 garantieMrh14, garantieMrh15,garantieMrh16,garantieMrh17,
				idpack);
	}
    
    
    public Proposition createProposition(Proposition proposition, 
    		GarantieMrh garantieMrh1 , GarantieMrh garantieMrh2, GarantieMrh garantieMrh3, 
    		GarantieMrh garantieMrh10, GarantieMrh garantieMrh11,
    		GarantieMrh garantieMrh4, GarantieMrh garantieMrh5, GarantieMrh garantieMrh6,GarantieMrh garantieMrh7,
			GarantieMrh garantieMrh8, GarantieMrh garantieMrh9,GarantieMrh garantieMrh12,GarantieMrh garantieMrh13,
			GarantieMrh garantieMrh14,GarantieMrh garantieMrh15,GarantieMrh garantieMrh16,GarantieMrh garantieMrh17,
    		
    		long idPack) {
    	
	     Client client	=	em.merge(proposition.getClient());
	      proposition.setClient(client);
	      
	      
	      System.out.println("1 proposition "+proposition.getDetailMrh().getNbrpieceless());
			System.out.println("2 "+proposition.getDetailMrh().getNbrpiecemore());
			System.out.println("3 "+proposition.getDetailMrh().getFurnitureprice());
			System.out.println("4 "+proposition.getDetailMrh().getArea());
			System.out.println("5 "+proposition.getGouver());
			System.out.println("6 "+proposition.getAdresse());
			System.out.println("7 "+proposition.getCodeP());
			System.out.println("8 "+proposition.getLocalite());
			System.out.println("9 "+proposition.getDetailMrh().getRachIn());
			System.out.println("10 "+proposition.getNombrePersonne());
	      
	      DetailMrh detailMrh = em.merge(proposition.getDetailMrh());
	      proposition.setDetailMrh(detailMrh);
	      
	      proposition.setnProposition(proposition.getNumeroProposition());

	      
	    	Proposition newProposition= em.merge(proposition);
	    	//newProposition.setnProposition(newProposition.getNumeroProposition());
	    	 System.out.println("koup1 "+proposition.getNumeroProposition());
	    	 newProposition.setnProposition(newProposition.getNumeroProposition());
		      System.out.println("koup2 "+newProposition.getNumeroProposition());
	    	
	    	if( idPack==3 ){
	    		
	    		
	    		garantieMrh10.setProposition(newProposition);
	    		em.merge(garantieMrh10);
				
				garantieMrh4.setProposition(newProposition);
				em.merge(garantieMrh4);
				
				garantieMrh5.setProposition(newProposition);
				em.merge(garantieMrh5);
				
				garantieMrh6.setProposition(newProposition);
				em.merge(garantieMrh6);
				
				garantieMrh7.setProposition(newProposition);
				em.merge(garantieMrh7);
				
				
				
				garantieMrh8.setProposition(newProposition);
				em.merge(garantieMrh8);
				
				garantieMrh9.setProposition(newProposition);
				em.merge(garantieMrh9);
				
				if(garantieMrh12 !=null){
					garantieMrh12.setProposition(newProposition);
					em.merge(garantieMrh12);
				}
				
				if(garantieMrh13 !=null){
					garantieMrh13.setProposition(newProposition);
					em.merge(garantieMrh13);
				}
				
				if(garantieMrh14 !=null){
					garantieMrh14.setProposition(newProposition);
					em.merge(garantieMrh14);
				}
				
				if(garantieMrh15 !=null){
					garantieMrh15.setProposition(newProposition);
					em.merge(garantieMrh15);
					
					for(Beneficiaire benef : newProposition.getBeneficiaires()){
		    			
		    			benef.setPropositions(newProposition);
				    	em.merge(benef);
		    		}
					
					 
				}
				if(garantieMrh16 !=null){
					garantieMrh16.setProposition(newProposition);
					em.merge(garantieMrh16);
				}
			    
				if(garantieMrh17 !=null){
					garantieMrh17.setProposition(newProposition);
					em.merge(garantieMrh17);
				}
	    		
	    	}
	    	
	    	

			if(idPack==2 ){
				
				garantieMrh4.setProposition(newProposition);
				em.merge(garantieMrh4);
				garantieMrh5.setProposition(newProposition);
				em.merge(garantieMrh5);
				garantieMrh6.setProposition(newProposition);
				em.merge(garantieMrh6);
				garantieMrh7.setProposition(newProposition);
				em.merge(garantieMrh7);
				
				if(garantieMrh10!=null ){
					
					garantieMrh10.setProposition(newProposition);
					em.merge(garantieMrh10);
				}
				
				if(garantieMrh16 !=null){
					garantieMrh16.setProposition(newProposition);
					em.merge(garantieMrh16);
				}

			}
			
			
			if(idPack==1 ){
				
				if(garantieMrh10!=null ){
					
					garantieMrh10.setProposition(newProposition);
					em.merge(garantieMrh10);
				}
				
				if(garantieMrh16 !=null){
					garantieMrh16.setProposition(newProposition);
					em.merge(garantieMrh16);
				}

				
			}
          if( idPack==4 ){
	    	
				garantieMrh4.setProposition(newProposition);
				em.merge(garantieMrh4);
				
				garantieMrh6.setProposition(newProposition);
				em.merge(garantieMrh6);
				
				
				garantieMrh10.setProposition(newProposition);
	    		em.merge(garantieMrh10);
				
				
				garantieMrh17.setProposition(newProposition);
					em.merge(garantieMrh17);
				
	    		
	    	}
	    	
          if( idPack==5 ){
  	    	
				garantieMrh4.setProposition(newProposition);
				em.merge(garantieMrh4);
				
				garantieMrh5.setProposition(newProposition);
				em.merge(garantieMrh5);
				
				garantieMrh6.setProposition(newProposition);
				em.merge(garantieMrh6);
				
				garantieMrh7.setProposition(newProposition);
				em.merge(garantieMrh7);
				
				garantieMrh10.setProposition(newProposition);
	    		em.merge(garantieMrh10);
	    		
	    		if(garantieMrh13 !=null){
					garantieMrh13.setProposition(newProposition);
					em.merge(garantieMrh13);
				}
	    		if(garantieMrh16 !=null){
					garantieMrh16.setProposition(newProposition);
					em.merge(garantieMrh16);
				}
				
	    		
	    	}
	    	
	    	garantieMrh1.setProposition(newProposition);
	    	em.merge(garantieMrh1);
			garantieMrh2.setProposition(newProposition);
			em.merge(garantieMrh2);
			garantieMrh3.setProposition(newProposition);
			em.merge(garantieMrh3);
			garantieMrh11.setProposition(newProposition);
			em.merge(garantieMrh11);
	    	
	    	
//		      em.merge(garantieMrh1);
//		      em.merge(garantieMrh2);
//		      em.merge(garantieMrh3);
//		      em.merge(garantieMrh10);
//		      em.merge(garantieMrh11);
	    	
	   return /*newProposition;*/em.merge(newProposition);
	    }
    
    //***************adhesion*************************/
    @Override
   	public Adhesion saveAdhesion(Client client,Adhesion adhesion, Proposition proposition, GarantieMrh garantieMrh1, 
   			GarantieMrh garantieMrh2, GarantieMrh garantieMrh3, GarantieMrh garantieMrh10, GarantieMrh garantieMrh11, 
   			GarantieMrh garantieMrh4, GarantieMrh garantieMrh5, GarantieMrh garantieMrh6,GarantieMrh garantieMrh7,
   			GarantieMrh garantieMrh8, GarantieMrh garantieMrh9,GarantieMrh garantieMrh12,GarantieMrh garantieMrh13,
   			GarantieMrh garantieMrh14,GarantieMrh garantieMrh15,GarantieMrh garantieMrh16,GarantieMrh garantieMrh17,
   			Utilisateurs utilisateurs, long idpack) {
   		
   		
   		adhesion.setDatcreation(new Date());
		if (client.getIdClient() == 0) {
			client.setDatCreation(new Date());
		}

		adhesion.setClient(client);

		adhesion.setIdUtilisateurs(utilisateurs.getIdUtilisateurs());
		
		return createAdhesion(adhesion, proposition, 
				garantieMrh1,  garantieMrh2,  garantieMrh3,  garantieMrh10,  garantieMrh11, 
				 garantieMrh4,  garantieMrh5,  garantieMrh6, garantieMrh7,
				 garantieMrh8,  garantieMrh9, garantieMrh12, garantieMrh13,
				 garantieMrh14, garantieMrh15,garantieMrh16, garantieMrh17,
				idpack);
    }
		
		 public Adhesion createAdhesion(Adhesion adhesion, Proposition proposition,
		    		GarantieMrh garantieMrh1 , GarantieMrh garantieMrh2, GarantieMrh garantieMrh3, 
		    		GarantieMrh garantieMrh10, GarantieMrh garantieMrh11,
		    		GarantieMrh garantieMrh4, GarantieMrh garantieMrh5, GarantieMrh garantieMrh6,GarantieMrh garantieMrh7,
					GarantieMrh garantieMrh8, GarantieMrh garantieMrh9,GarantieMrh garantieMrh12,GarantieMrh garantieMrh13,
					GarantieMrh garantieMrh14,GarantieMrh garantieMrh15,GarantieMrh garantieMrh16,GarantieMrh garantieMrh17,
		    		
		    		long idPack) {
		    	
			     Client client	=	em.merge(adhesion.getClient());
			      adhesion.setClient(client);
			      
			      DetailMrh detailMrh = em.merge(adhesion.getDetailMrh());
			      adhesion.setDetailMrh(detailMrh);
			      
			    
//			      adhesion.setNadhesion(adhesion.getNumeroAdhesion());

			      
			      if(proposition != null){
			    	  
			    	  adhesion.setProposition(proposition);	 
			      }
			      
			      Adhesion newAdhesion= em.merge(adhesion);
			      System.out.println("kou1 "+adhesion.getNumeroAdhesion());
			      newAdhesion.setNadhesion(newAdhesion.getNumeroAdhesion());
			      System.out.println("kou2 "+newAdhesion.getNumeroAdhesion());
			    	
			      if( idPack==3 ){
			    		
			    		
			    		garantieMrh10.setAdhesion(newAdhesion);
			    		em.merge(garantieMrh10);
						
						garantieMrh4.setAdhesion(newAdhesion);
						em.merge(garantieMrh4);
						
						garantieMrh5.setAdhesion(newAdhesion);
						em.merge(garantieMrh5);
						
						garantieMrh6.setAdhesion(newAdhesion);
						em.merge(garantieMrh6);
						
						garantieMrh7.setAdhesion(newAdhesion);
						em.merge(garantieMrh7);
						
						
						
						garantieMrh8.setAdhesion(newAdhesion);
						em.merge(garantieMrh8);
						
						garantieMrh9.setAdhesion(newAdhesion);
						em.merge(garantieMrh9);
						
						if(garantieMrh12 !=null){
							garantieMrh12.setAdhesion(newAdhesion);
							em.merge(garantieMrh12);
						}
						
						if(garantieMrh13 !=null){
							garantieMrh13.setAdhesion(newAdhesion);
							em.merge(garantieMrh13);
						}
						
						if(garantieMrh14 !=null){
							garantieMrh14.setAdhesion(newAdhesion);
							em.merge(garantieMrh14);
						}
						
						if(garantieMrh15 !=null){
							garantieMrh15.setAdhesion(newAdhesion);
							em.merge(garantieMrh15);
							
							for(Beneficiaire benef : newAdhesion.getBeneficiaires()){
				    			
				    			benef.setAdhesion(newAdhesion);
//				    			 if(proposition != null){
//							    	  
//				    				 benef.setPropositions(proposition);	 
//							      }
						    	em.merge(benef);
				    		}
							
							 
						}
					 
			    		
			    	}
			    	
			    	

					if(idPack==2 ){
						
						garantieMrh4.setAdhesion(newAdhesion);
						em.merge(garantieMrh4);
						garantieMrh5.setAdhesion(newAdhesion);
						em.merge(garantieMrh5);
						garantieMrh6.setAdhesion(newAdhesion);
						em.merge(garantieMrh6);
						garantieMrh7.setAdhesion(newAdhesion);
						em.merge(garantieMrh7);
						
						if(garantieMrh10!=null ){
							
							garantieMrh10.setAdhesion(newAdhesion);
							em.merge(garantieMrh10);
						}
					}
					
					
					if(idPack==1 ){
						
						if(garantieMrh10!=null ){
							
							garantieMrh10.setAdhesion(newAdhesion);
							em.merge(garantieMrh10);
						}
						
					}
					
					 if( idPack==4 ){
					    	
							garantieMrh4.setAdhesion(newAdhesion);
							em.merge(garantieMrh4);
							
							garantieMrh6.setAdhesion(newAdhesion);
							em.merge(garantieMrh6);
							
							
							garantieMrh10.setAdhesion(newAdhesion);
				    		em.merge(garantieMrh10);
							
							
							garantieMrh17.setAdhesion(newAdhesion);
								em.merge(garantieMrh17);
							
				    		
				    	}
				    	
			          if( idPack==5 ){
			  	    	
							garantieMrh4.setAdhesion(newAdhesion);
							em.merge(garantieMrh4);
							
							garantieMrh5.setAdhesion(newAdhesion);
							em.merge(garantieMrh5);
							
							garantieMrh6.setAdhesion(newAdhesion);
							em.merge(garantieMrh6);
							
							garantieMrh7.setAdhesion(newAdhesion);
							em.merge(garantieMrh7);
							
							garantieMrh10.setAdhesion(newAdhesion);
				    		em.merge(garantieMrh10);
				    		
				    		if(garantieMrh13 !=null){
								garantieMrh13.setAdhesion(newAdhesion);
								em.merge(garantieMrh13);
							}
				    		if(garantieMrh16 !=null){
								garantieMrh16.setAdhesion(newAdhesion);
								em.merge(garantieMrh16);
							}
							
				    		
				    	}
					
			    	
			    	
			    	garantieMrh1.setAdhesion(newAdhesion);
			    	em.merge(garantieMrh1);
					garantieMrh2.setAdhesion(newAdhesion);
					em.merge(garantieMrh2);
					garantieMrh3.setAdhesion(newAdhesion);
					em.merge(garantieMrh3);
					garantieMrh11.setAdhesion(newAdhesion);
					em.merge(garantieMrh11);
			    	
			    	

			    	
			 //  return /*newAdhesion;*/em.merge(newAdhesion);
			   return  em.merge(newAdhesion);
			    }
		    
			      
			      
	
		
		
     
    

   
    	
    	
//        clientService.saveClient(client,agence);
//        mrhLogSimulationDAO.create(mrhLogSimulation);
//        //log
//        LogSimulation logSimulation = new LogSimulation();
//        logSimulation.setMatcin(client);
//        logSimulation.setCodprod("805");
//        logSimulation.setCodagc(agence);
//        logSimulation.setSimDate(new Date());
//        logSimulation.setSimIdmrh(mrhLogSimulation);
//        logSimulationDAO.create(logSimulation);

        
        
        
        

        //options
       /* if (options != null && !options.isEmpty()) {
            for (String op : options) {
                OptionsSimulationPK optionpk = new OptionsSimulationPK();
                optionpk.setSimId(mrhLogSimulation.getSimId());
                optionpk.setCodeGar(op);
                optionsSimulationDAO.create(new OptionsSimulation(optionpk));
            }
        } */
    }



	

//    @Override
//    public String getTypeClientByCode(String code) {
//        return typeCliDAO.getTypeClientByCode(code);
//    }
    
   // rym
    
  /* @Override
  	public Map<String, Object> recupererAllQualite() {
  		 Map<String, Object> allqualite = new LinkedHashMap<String, Object>();
  		  List<Qualite> list =null;
  		  
  		  list =  iQualiteDAO.listeQualite();

  		  for(Qualite qualite : list){
  			  allqualite.put(qualite.getQualite(),qualite.getIdQualite()) ;
  		  }
  		  
  		return allqualite;
  	}
      
  	 @Override
     	public Map<String, Object> recupererAllNaturisq() {
     		   Map<String, Object> allnaturisq = new LinkedHashMap<String, Object>();
     		   List<NatureRisq> list =null;
     		  
     		  list =  iNatureRisqDAO.listeNaturRisq();
     				  for(NatureRisq naturisq : list){
     			  allnaturisq.put(naturisq.getLibelleNature(),naturisq.getIdRiskMrh()) ;
     		  }
     		  
     		return allnaturisq;
     	} */
    

