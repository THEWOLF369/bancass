package com.star.Ba.jsf;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.Visibility;

import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IAgenceDAO;
import com.star.Ba.dao.IChartDAO;
import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IOperationTransactionDAO;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.IProdconvDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.IProfilDAO;
import com.star.Ba.dao.IPropositionDAO;
import com.star.Ba.dao.IQuittanceDAO;
import com.star.Ba.dao.IQuittanceDetailsDAO;
import com.star.Ba.dao.IQuittanceTermeDAO;
import com.star.Ba.dao.IQuittanceVersementLibreDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.dto.QuittanceImapyeeDTO;
import com.star.Ba.dto.QuittanceVLDTO;
import com.star.Ba.dto.QuittanceVLDTO2;
import com.star.Ba.jsf.util.ENUMCODEPRODUIT;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.QuittanceDetails;
import com.star.Ba.model.QuittanceVersementLibre;
import com.star.Ba.model.TarifMrh;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.HayyaServiceInter;
import com.star.Ba.service.IParametresMrhService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author 
 *
 */
@ManagedBean(name = "versementLibreConsultationBean")
@ViewScoped
public class VersementLibreConsultationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	 @EJB
	    private IAdhesionDAO adhesionDAO;
	    @EJB
	    private ISocieteDAO societeDAO;
	    @EJB
	    private IPropositionDAO propositionDAO;
	    
	    @EJB
	    private IConventionDAO conventionDAO;
	    @EJB
	    private IProdconvDAO prodconvDAO;
	    @EJB
	    private IUtilisateursDAO utilisateursDAO;
	    @EJB
	    private IAgenceDAO agenceDAO;
	    
	    @EJB
	    private IPoliceDAO policeDAO;
	    
	    @EJB
	    private IClientDAO clientDAO;
	    
	    @EJB
	    private IProduitDAO produitDAO;
	  
	    
	    @EJB
		private IParametresMrhService parametresMrhService;
	    
	    @EJB
	   	private IProfilDAO iProfilDAO;
	    
		@EJB
		private IQuittanceDAO iQuittanceDAO;
		
		@EJB
		private IQuittanceTermeDAO  iQuittanceTermeDAO;
		
		
		@EJB
	    private IOperationTransactionDAO iOperationTransactionDAO;
		
		
		@EJB
		private IQuittanceVersementLibreDAO  iQuittanceVLDAO;
	    
	    @ManagedProperty(value = "#{login.utilisateur}")
	    private Utilisateurs utilisateur;
	    @ManagedProperty("#{reportController}")
		private ReportController reportController;
	    
	    @ManagedProperty("#{gestionProfil}")
		private GestionProfil gestionProfil ;
	    
	    
	    
	    @EJB
		private HayyaServiceInter hayyaServiceInter;
	    
		@EJB
		private IQuittanceDetailsDAO quittanceDetailsDAO;
		
		
		private Visibility evenement;
	    
	    
	    private Adhesion selectedAdhesion;
	    private Double contrib;
	    private boolean disabledValidationAdhesion=false;
	    private boolean disabledValidationEncaissement = true;
	    private boolean disabledImprimQuittance = true;


		private List<Adhesion> adhesions = new ArrayList<Adhesion>();
	    private List<Adhesion> lstadhs;
	    private List<Proposition> propositions = new ArrayList<Proposition>();
	    private List<Proposition> lstprops;
	    private List<Client> clients = new ArrayList<Client>();
	    private List<Prodconv> lstproduit;
	    private boolean affiche = true;
	    private Map<String, Object> listSociete=null;
		private String codeSociete;
		
		  private Date to, toV;
		  private Date from, fromV;
		  private String dateDu,dateAu, dateDuV,dateAuV;
		  private String nadhesion,cin,libelleProduit, numquit;
		  
		  int rowIndex;
		  
		  private List<QuittanceImapyeeDTO> quittancesImapyeesList = new ArrayList<>();
		  
		  private List<QuittanceVersementLibre> quittanceVLList = new ArrayList<>();
		  
		  private List<Quittance> quittancesList = new ArrayList<>();
		  
		  private List<QuittanceDetails> qtcDetailsList = new ArrayList<>();
		  
		  private List<QuittanceVLDTO2> qtcVlList = new ArrayList<>();
		  
		private String [] userSociete={PofileEnum.G.name(),PofileEnum.AA.name(),PofileEnum.AS.name(),PofileEnum.BNAA.name(),PofileEnum.BNA.name()};
		  private List<Produit> produits=new ArrayList<Produit>();
		
		@EJB
		private IChartDAO iChartDAO;
		

		@PostConstruct
	    public void init() {
			
			/*** societe****/
			 listSociete = iChartDAO.recupererListeSociete();
			 codeSociete = (String) listSociete.values().toArray()[0];
			
			  System.out.println("le_codeSociete :"+codeSociete);
			  
			/*
			  Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			  Date date = new Date();
			  System.out.println(formatter.format(date));
				 dateDu = formatter.format(date);
				 dateAu = formatter.format(date);
				
				from = date;
				to = date;
				
				 String cin_ = cin;
				 String nadhesion_ = nadhesion;
				 String libelleProduit_ = libelleProduit;
			*/


				if(Arrays.asList(userSociete).contains(utilisateur.getProfil().getCode())){
					Convention c=conventionDAO.find_conv_by_Societ(utilisateur.getAgence().getSociete());
					// lstproduit=prodconvDAO.getlstProduit_by_convention_for_qtc(c);
					lstproduit=prodconvDAO.getlstProduit_by_convention_for_qtc_vlibre(c);
					for (Prodconv produit : lstproduit) {
						produits.add(produit.getProduit());
					}
				}else {
					
					 produits=produitDAO.findAll();
					
					
				}
				
		}
		
		
		
		public void Chercher(){
			
			  System.out.println("debut recherche");
			
			  dateDu = "";
				 dateAu = "";
				 
				  dateDuV = "";
					 dateAuV = "";
			  
			 
			  if(from == null && to == null && fromV == null && toV == null && cin.toString().isEmpty() && nadhesion.toString().isEmpty() && numquit.toString().isEmpty()) {
				  
				 
				  
				  FacesMessage msg = new FacesMessage("Veuillez saisir une date et/ou n° contrat et/ou un identifiant et/ou n° quittance", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					return;
			  }
			  
			  
			  if(from != null && to == null) {
				  
				 
				  
				  FacesMessage msg = new FacesMessage("Veuillez vérifier la date de création", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					return;
			  }
			  if(fromV != null && toV == null) {
				  
				 
				  
				  FacesMessage msg = new FacesMessage("Veuillez vérifier la date de versement", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					return;
			  }
			  
			  
			  
			  
			  if(from == null && to != null) {
				  
				 
				  
				  FacesMessage msg = new FacesMessage("Veuillez vérifier la date de création", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					return;
			  }
			  if(fromV == null && toV != null) {
				  
				 
				  
				  FacesMessage msg = new FacesMessage("Veuillez vérifier la date de versement", "ERREUR");
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage("Erreur", msg);
					return;
			  }
			  
			  
			  
			  
			  
			  if(from != null && to != null) {
				  
				  Format formatter = new SimpleDateFormat("dd/MM/yyyy");
					 dateDu = formatter.format(from);
					 dateAu = formatter.format(to);
			  }
			  if(fromV != null && toV != null) {
				  
				  Format formatter = new SimpleDateFormat("dd/MM/yyyy");
					 dateDuV = formatter.format(fromV);
					 dateAuV = formatter.format(toV);
			  }
			  
			  
			  
				 String cin_ = cin;
				 String nadhesion_ = nadhesion;
				 String libelleProduit_ = libelleProduit;
				 
				 adhesions = new ArrayList<Adhesion>(); 
				 propositions = new ArrayList<Proposition>();
				 
				 
				 
				 if(utilisateur.getProfil().getCode().equals(PofileEnum.G.name()) )
			        {
					 
					 System.out.println("consultationBean fnction rechercher pour gestionnaire");
					 
			        }
				 
				 else if(utilisateur.getProfil().getCode().equals(PofileEnum.AA.name()))
			        {
			        	 System.out.println("consultationBean fnction rechercher pour AA"); 
			        	  					        	
				        	List<Utilisateurs> listeUtilisateurs = utilisateursDAO.listeUtilisateur(utilisateur.getAgence());
				        	// System.out.println("***liste utilisateurs="+listeUtilisateurs);
				        	 //List<Client> listWithDuplicates = new ArrayList<>();
//				             for (Utilisateurs utilisateurs : listeUtilisateurs){
//				            //	 System.out.println("***utilisateur="+utilisateurs);
//				            	 lstadhs=adhesionDAO.findByUtilisateurAndDateAndPoliceAndClient_for_VL(
//					                		utilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_);
//				            	 //System.out.println("***adhesions="+adhesions);
//				            	 
//				                 adhesions.addAll(lstadhs);
//				             }
//				             
//				             
//				             List<Integer> ids = new ArrayList<Integer>();
//				             for (Adhesion a : adhesions) {
//				                 ids.add(a.getIdAdhesion());
//				             }
				        	
				        	List<Integer> idsUtilisateurs = new ArrayList<Integer>();
				        	for (Utilisateurs u : listeUtilisateurs) {
				        	    idsUtilisateurs.add(u.getIdUtilisateurs());
				        	}
				             
				             List<Object[]> results = iQuittanceDAO.searchVLByAdhesions(

				            		 idsUtilisateurs,dateDu,dateAu, nadhesion_, cin_, libelleProduit_, 
				            		 dateDuV, dateAuV, numquit
				            		 
				            		 );
				             // Construire la liste de DTO
				             qtcVlList = new ArrayList<QuittanceVLDTO2>();
				             
				             for (int i = 0; i < results.size(); i++) {
				                 Object[] row = results.get(i);
				                 Adhesion adhesion = (Adhesion) row[0];
				                 QuittanceDetails detail = (QuittanceDetails) row[1];
				                 Quittance quittance = (Quittance) row[2];
				                 qtcVlList.add(new QuittanceVLDTO2(adhesion, quittance, detail));
				             }
				             
				             for (QuittanceVLDTO2 vl : qtcVlList) {
				            	    System.out.println("Num adhesion: " + vl.getAdhesion().getNumeroAdhesion());
				            	    System.out.println("Num Quittance: " + vl.getQuittance().getIdQuittance());
				            	    System.out.println("Prime Totale: " + vl.getDetail().getPrimetot());
				            	}
				             
				             
	/*			             
				             qtcVlList = new ArrayList<QuittanceVLDTO>();

				             if (adhesions != null && !adhesions.isEmpty()) {

				                 for (Adhesion adhesion : adhesions) {

				                     String id = adhesion.getNadhesion();

				                     qtcDetailsList = quittanceDetailsDAO.searchByNAdhesion(id, "VL");

				                     if (qtcDetailsList != null && !qtcDetailsList.isEmpty()) {

				                         List<Long> idQuittances = new ArrayList<Long>();

				                         for (QuittanceDetails det : qtcDetailsList) {
				                             idQuittances.add(det.getIdQuittance());
				                         }

				                         quittancesList = iQuittanceDAO.searchQuitByManyIdQuittance(idQuittances);

				                         // 🔥 Transformation en Map pour éviter double boucle lente
				                         Map<Long, Quittance> quittanceMap = new HashMap<Long, Quittance>();

				                         for (Quittance qtc : quittancesList) {
				                             quittanceMap.put(qtc.getIdQuittance(), qtc);
				                         }

				                         for (QuittanceDetails det : qtcDetailsList) {

				                             Quittance qtc = quittanceMap.get(det.getIdQuittance());

				                             if (qtc != null) {
				                                 QuittanceVLDTO vl = new QuittanceVLDTO(qtc, det);
				                                 qtcVlList.add(vl);
				                             }
				                         }
				                     }
				                 }
				             }
		         
		             */
		            
	 
		        }
		        else if(utilisateur.getProfil().getCode().equals(PofileEnum.AS.name()) )
		        {
		        	System.out.println("consultationBean fnction rechercher pour AS"); 
		        
			       
		        }
		        else if(/*utilisateur.getProfil().getCode().equals(PofileEnum.AST.name())||*/utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name())/*|| PofileEnum.ASI.getChildrensASI().contains(utilisateur.getProfil().getCode())*/)
		        {
		        	System.out.println("consultationBean fnction rechercher pour ASI"); 
		        
		        }
		        else {
		        	
		        	System.out.println("cas metier TD"); 
		        	
		        	  }
			  
			  
		}
		
		
 
		public void printQTC(QuittanceVLDTO2 qtc) {
			
		Adhesion adhesion = qtc.getAdhesion();
		
		 if (adhesion.getPolice().getCodProdui().getCodeProduit().
				 equalsIgnoreCase( ENUMCODEPRODUIT.H7AYYA.getCode()) ||
				 adhesion.getPolice().getCodProdui().getCodeProduit().
				 equalsIgnoreCase( ENUMCODEPRODUIT.H7ayyaQNB.getCode())
				 )
		{
			
			
			//numquit=serviceAssistantVoiyagetService.getNumeroQuit();
		
			
			//Adhesion adhesion =adhesionToPrintNotise;
			String report;
			try {
				
					 report = reportController.getRealPath()	+ "Quit_VL_hayaa.jasper";
					 
					 
					 
				
				Map<String, Object> parameters = new HashMap<String, Object>();
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
				
				parameters.put("numquit", String.valueOf( qtc.getQuittance().getNumquit()) );

				parameters.put("agence", qtc.getDetail().getAgence().getCodeAgence());
				parameters.put("contrat",adhesion.getNadhesion());
				
				

				
				parameters.put("echeance", dateFormat.format(qtc.getQuittance().getDATE_CREATION()));
				
				parameters.put("souscr", adhesion.getClient().getNomClient()
						+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_souscr", adhesion.getClient().getAdrClient());
				 
				parameters.put("assure", adhesion.getClient().getNomClient()
						+" "+adhesion.getClient().getPrenomClient());
				parameters.put("adr_assure", adhesion.getClient().getAdrClient());
				 
				 
				
				//parameters.put("prime_tot", adhesion.getPrimetot());
				parameters.put("frais", qtc.getQuittance().getFrais());
				parameters.put("fga", qtc.getQuittance().getFga());
				parameters.put("taxe", qtc.getQuittance().getTaxe().doubleValue());
				//parameters.put("prime_nette", detailHayya.getPnette());
				
				
			
					
					// parameters.put("prime_tot", qtc.getDetail().getPrimetot().doubleValue());
					parameters.put("prime_tot", new BigDecimal( qtc.getDetail().getPrimenette() ));
					parameters.put("prime_nette", new BigDecimal( qtc.getDetail().getPrimenette() ));
				  
			 
			
				
				 
				
				parameters
				.put("star",  reportController.getLogoJasper());
				
				parameters.put("imagesociete", utilisateur.getAgence().getSociete()
						.getCodSociet() + ".png");
				
				
				


				JasperPrint print = JasperFillManager.fillReport(report,parameters, new JREmptyDataSource());
				byte[] bytes = JasperExportManager.exportReportToPdf(print);

				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext ec = fc.getExternalContext();
				ec.responseReset();
				ec.setResponseContentType("application/pdf");
				ec.setResponseContentLength(bytes.length);
				ec.setResponseHeader("Content-Disposition", "inline; filename=QUITTANCE.pdf");

				OutputStream output = ec.getResponseOutputStream();
				output.write(bytes);
				fc.responseComplete();

				//adhesion.setImpr(true);
				//adhesionDAO.edit(adhesion);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		 
		// avenir jeunesse
		 if (adhesion.getPolice().getCodProdui().getCodeProduit().equalsIgnoreCase( ENUMCODEPRODUIT.AVENIRJENESSE.getCode()))
		{
			 
			 String report;
				try {
					
						 report = reportController.getRealPath()	+ "Quit_VL_avj.jasper";
						 
						 
						 
					
					Map<String, Object> parameters = new HashMap<String, Object>();
				
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
					
					parameters.put("numquit", String.valueOf( qtc.getQuittance().getNumquit()) );

					parameters.put("agence", qtc.getDetail().getAgence().getCodeAgence());
					parameters.put("contrat",adhesion.getNadhesion());
					
					

					
					parameters.put("echeance", dateFormat.format(qtc.getQuittance().getDATE_CREATION()));
					
					parameters.put("souscr", adhesion.getClient().getNomClient()
							+" "+adhesion.getClient().getPrenomClient());
					parameters.put("adr_souscr", adhesion.getClient().getAdrClient());
					 
					parameters.put("assure", adhesion.getClient().getNomClient()
							+" "+adhesion.getClient().getPrenomClient());
					parameters.put("adr_assure", adhesion.getClient().getAdrClient());
					 
					 
					
					//parameters.put("prime_tot", adhesion.getPrimetot());
					parameters.put("frais", qtc.getQuittance().getFrais());
					parameters.put("fga", qtc.getQuittance().getFga());
					double taxe = qtc.getQuittance().getTaxe() != null ? qtc.getQuittance().getTaxe().doubleValue() : 0;
					parameters.put("taxe", taxe);
					//parameters.put("prime_nette", detailHayya.getPnette());
					
					
				
						
						// parameters.put("prime_tot", qtc.getDetail().getPrimetot().doubleValue());
						parameters.put("prime_tot", new BigDecimal( qtc.getDetail().getPrimenette() ));
						parameters.put("prime_nette", new BigDecimal( qtc.getDetail().getPrimenette() ));
					  
				 
				
					
					 
					
					parameters
					.put("star",  reportController.getLogoJasper());
					
					parameters.put("imagesociete", utilisateur.getAgence().getSociete()
							.getCodSociet() + ".png");
					
					
					


					JasperPrint print = JasperFillManager.fillReport(report,parameters, new JREmptyDataSource());
					byte[] bytes = JasperExportManager.exportReportToPdf(print);

					FacesContext fc = FacesContext.getCurrentInstance();
					ExternalContext ec = fc.getExternalContext();
					ec.responseReset();
					ec.setResponseContentType("application/pdf");
					ec.setResponseContentLength(bytes.length);
					ec.setResponseHeader("Content-Disposition", "inline; filename=QUITTANCE.pdf");

					OutputStream output = ec.getResponseOutputStream();
					output.write(bytes);
					fc.responseComplete();

					//adhesion.setImpr(true);
					//adhesionDAO.edit(adhesion);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
			
		}
		
		
		
///////////////////// getter and setter		
		
	    
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

		public ReportController getReportController() {
			return reportController;
		}

		public void setReportController(ReportController reportController) {
			this.reportController = reportController;
		}
		
		public List<Produit> getProduits() {
			return produits;
		}

		public void setProduits(List<Produit> produits) {
			this.produits = produits;
		}

		
		
		public GestionProfil getGestionProfil() {
			return gestionProfil;
		}

		public void setGestionProfil(GestionProfil gestionProfil) {
			this.gestionProfil = gestionProfil;
		}
		
		
		

		public Adhesion getSelectedAdhesion() {
			disabledValidationAdhesion = false;
			disabledValidationEncaissement = true;
			disabledImprimQuittance = true;
			return selectedAdhesion;
		}

		public void setSelectedAdhesion(Adhesion selectedAdhesion) {
			this.selectedAdhesion = selectedAdhesion;
		}
		
		

		public Double getContrib() {
			return contrib;
		}

		public void setContrib(Double contrib) {
			this.contrib = contrib;
		}
		
		
		

		public boolean isDisabledValidationAdhesion() {
			return disabledValidationAdhesion;
		}

		public void setDisabledValidationAdhesion(boolean disabledValidationAdhesion) {
			this.disabledValidationAdhesion = disabledValidationAdhesion;
		}
		
		

		public boolean isDisabledValidationEncaissement() {
			return disabledValidationEncaissement;
		}

		public void setDisabledValidationEncaissement(boolean disabledValidationEncaissement) {
			this.disabledValidationEncaissement = disabledValidationEncaissement;
		}
		
		
		

		public boolean isDisabledImprimQuittance() {
			return disabledImprimQuittance;
		}

		public void setDisabledImprimQuittance(boolean disabledImprimQuittance) {
			this.disabledImprimQuittance = disabledImprimQuittance;
		}
		
		
		 public List<Prodconv> getLstproduit() {
				return lstproduit;
			}

			public void setLstproduit(List<Prodconv> lstproduit) {
				this.lstproduit = lstproduit;
			}

			public List<Adhesion> getAdhesions() {
		        return adhesions;
		    }

		    public List<Proposition> getPropositions() {
		        return propositions;
		    }

		    public Utilisateurs getUtilisateur() {
		        return utilisateur;
		    }

		    public void setUtilisateur(Utilisateurs utilisateur) {
		        this.utilisateur = utilisateur;
		    }
		    
		    public List<Client> getClients() {
				return clients;
			}

			public void setClients(List<Client> clients) {
				this.clients = clients;
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
			    
			    
		    
			    
			    public List<QuittanceImapyeeDTO> getQuittancesImapyeesList() {
					return quittancesImapyeesList;
				}

				public void setQuittancesImapyeesList(List<QuittanceImapyeeDTO> quittancesImapyeesList) {
					this.quittancesImapyeesList = quittancesImapyeesList;
				}
				
				
				

				public List<QuittanceVersementLibre> getQuittanceVLList() {
					return quittanceVLList;
				}

				public void setQuittanceVLList(List<QuittanceVersementLibre> quittanceVLList) {
					this.quittanceVLList = quittanceVLList;
				}
				
				

				public List<Quittance> getQuittancesList() {
					return quittancesList;
				}

				public void setQuittancesList(List<Quittance> quittancesList) {
					this.quittancesList = quittancesList;
				}
				
				
				
				

				public Visibility getEvenement() {
					return evenement;
				}

				public void setEvenement(Visibility evenement) {
					this.evenement = evenement;
				}

				public List<QuittanceVLDTO2> getQtcVlList() {
					return qtcVlList;
				}

				public void setQtcVlList(List<QuittanceVLDTO2> qtcVlList) {
					this.qtcVlList = qtcVlList;
				}

				public List<QuittanceDetails> getQtcDetailsList() {
					return qtcDetailsList;
				}

				public void setQtcDetailsList(List<QuittanceDetails> qtcDetailsList) {
					this.qtcDetailsList = qtcDetailsList;
				}

				public String getLibelleProduit() {
					return libelleProduit;
				}

				public void setLibelleProduit(String libelleProduit) {
					this.libelleProduit = libelleProduit;
				}

				public String getNadhesion() {
					return nadhesion;
				}

				public void setNadhesion(String nadhesion) {
					this.nadhesion = nadhesion;
				}

				public String getCin() {
					return cin;
				}

				public void setCin(String cin) {
					this.cin = cin;
				}

				public TarifMrh getParamPrices() {
					TarifMrh tarif805 = parametresMrhService.getTarifMrh();
					return tarif805;
				}

				public boolean isAffiche() {
					return affiche;
				}

				public void setAffiche(boolean affiche) {
					this.affiche = affiche;
				}



				public Date getToV() {
					return toV;
				}



				public void setToV(Date toV) {
					this.toV = toV;
				}



				public Date getFromV() {
					return fromV;
				}



				public void setFromV(Date fromV) {
					this.fromV = fromV;
				}



				public String getDateDu() {
					return dateDu;
				}



				public void setDateDu(String dateDu) {
					this.dateDu = dateDu;
				}



				public String getDateAu() {
					return dateAu;
				}



				public void setDateAu(String dateAu) {
					this.dateAu = dateAu;
				}



				public String getDateDuV() {
					return dateDuV;
				}



				public void setDateDuV(String dateDuV) {
					this.dateDuV = dateDuV;
				}



				public String getDateAuV() {
					return dateAuV;
				}



				public void setDateAuV(String dateAuV) {
					this.dateAuV = dateAuV;
				}



				public String getNumquit() {
					return numquit;
				}



				public void setNumquit(String numquit) {
					this.numquit = numquit;
				}


				
				
}
