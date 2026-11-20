package com.star.Ba.service;

//import String;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.ejb.criteria.ValueHandlerFactory.DoubleValueHandler;

import com.bna.ws.soap.saeb.agricole.services.Adresse;
import com.bna.ws.soap.saeb.agricole.services.Agriculteur;
import com.bna.ws.soap.saeb.agricole.services.DecisionAgr;
import com.bna.ws.soap.saeb.agricole.services.DetailDecAgri;
import com.star.Ba.dao.AgrNatureDAO;
import com.star.Ba.dao.IAdhesionDAO;
import com.star.Ba.dao.IAgrClasseDAO;
import com.star.Ba.dao.IAgrNatureDAO;
import com.star.Ba.dao.IClientDAO;
import com.star.Ba.dao.IDelegationDAO;
import com.star.Ba.dao.IPackPoliceDAO;
import com.star.Ba.dao.IPackPolicePKDAO;
import com.star.Ba.dao.IParcelleDAO;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.IPrixHomDAO;
import com.star.Ba.dao.IProdconvDAO;
import com.star.Ba.dao.IProfessionClientDAO;
import com.star.Ba.dao.IQuittanceDAO;
import com.star.Ba.dao.ITarifGreleDAO;
import com.star.Ba.dao.ITarifGreleDelegationDAO;
import com.star.Ba.dao.ITarifIncendieAgrDAO;
import com.star.Ba.dao.ITauxReductionDAO;
import com.star.Ba.dao.ITauxcommDAO;
import com.star.Ba.dao.ITypeVoieDAO;
import com.star.Ba.dto.ContratDTO;
import com.star.Ba.dto.PackPoliceDTO;
import com.star.Ba.dto.QuittancesClient;
import com.star.Ba.dto.TarifAgrDTO;
import com.star.Ba.dto.UtilisateursConnexionDTO;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agrclasse;
import com.star.Ba.model.AgrclasseNature;
import com.star.Ba.model.Agrnature;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Delegation;
import com.star.Ba.model.PackPolice;
import com.star.Ba.model.PackPolicePK;
import com.star.Ba.model.Parcelle;
import com.star.Ba.model.Police;
import com.star.Ba.model.PrixHom;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.TarifGrele;
import com.star.Ba.model.Tauxcomm;
import com.star.Ba.model.Tauxreduction;
import com.star.Ba.model.Utilisateurs;

@Stateless
public class ParametreAgricoleServiceImpl implements IParametreAgricoleService {
	@EJB
	private IProdconvDAO iProdconvDAO;
	@EJB
	private IAgrNatureDAO iAgrNatureDAO;
	@EJB
	private ITarifGreleDAO iTarifGreleDAO;
	@EJB
	private IAgrClasseDAO iAgrClasseDAO;
	@EJB
	private ITarifGreleDelegationDAO iTarifGreleDelegationDAO;
	@EJB
	private ITarifIncendieAgrDAO iTarifIncendieAgrDAO;
	@EJB
	private IPoliceDAO iPoliceDAO;
	@EJB
	private IAdhesionDAO iAdhesionDAO;
	@EJB
	private IClientDAO iClientDAO;
	@EJB
	private IParcelleDAO iParcelleDAO;
	@EJB
	private ITauxcommDAO iTauxcommDAO;
	@EJB
	private ITauxReductionDAO iTauxReductionDAO;
	@EJB
	private IQuittanceDAO iQuittanceDAO;
	@EJB
	private IDelegationDAO iDelegationDAO;
	@EJB
	private ITypeVoieDAO iTypeVoieDAO;
	@EJB
	private IProfessionClientDAO iProfessionClientDAO;
	@EJB
	private IPrixHomDAO iPrixHomDAO;
	@EJB
	private IPackPolicePKDAO iPackPolicePKDAO;
	@EJB
	private IPackPoliceDAO iPackPoliceDAO;

	/*
	 * public void getParamTarificationAgricoleByUser(UtilisateursConnexionDTO
	 * user) { List<Convention> conventions = user.getConventions(); for
	 * (Convention convention : conventions) { List<Prodconv> prodConv =
	 * convention.getProdconvs(); if (prodConv != null) { for (Prodconv p :
	 * prodConv) { Prodconv pp= iProdconvDAO.find(p.getIdProdconv());
	 * List<TarifGrele> listTarifGrele= pp.getTarifGreleList(); } } } }
	 */
	@Override
	public List<Agrnature> getAgrNature() {
		return iAgrNatureDAO.findAll();
	}

	@Override
	public List<TarifGrele> getTarifGrele() {
		return iTarifGreleDAO.findAll();
	}

	@Override
	public TarifAgrDTO getTarifByClasseZone(String classe, String idzone,
			BigDecimal capital, int nbrExtension) {
		TarifAgrDTO resultat = iTarifGreleDAO.getTarifByClasseZone(classe,
				idzone, capital);

		if (nbrExtension != 0 && resultat.getExtension1() != null) {
			BigDecimal prime = new BigDecimal(resultat.getPrime());
			prime = prime.multiply(new BigDecimal(nbrExtension)
					.multiply(resultat.getExtension1()));
			prime = prime.add(new BigDecimal(resultat.getPrime()));
			resultat.setPrime(prime + "");
			// Prime minimal 15
			/*
			 * if (prime.compareTo(new BigDecimal(15)) < 0) {
			 * resultat.setPrime("15"); }
			 */
			BigDecimal test;
			test = new BigDecimal(resultat.getPrime());
			double d = test.doubleValue();
			BigDecimal test2 = new BigDecimal("15");

			if (d < 15) {
				resultat.setPrime(test2.toString());
			}

		}
		return resultat;
	}

	@Override
	public List<Delegation> getDelegationByZone(BigDecimal idZone) {
		return iTarifGreleDelegationDAO.getDelegationByTarif(idZone);
	}

	@Override
	public TarifAgrDTO getTarifByIdprodConv(long idProdconv,
			BigDecimal capital, boolean extensionVA, boolean extensionPP) {

		TarifAgrDTO resultat = iTarifIncendieAgrDAO.getTarifByIdprodConv(
				idProdconv, capital);
		BigDecimal ex1 = BigDecimal.ZERO, ex2 = BigDecimal.ZERO,
				prime = new BigDecimal(resultat.getPrime());
		if (extensionVA && resultat.getExtension1() != null) {
			ex1 = prime.multiply(resultat.getExtension1());

		}
		if (extensionPP && resultat.getExtension2() != null) {
			ex2 = prime.multiply(resultat.getExtension2());

		}

		prime = prime.add(ex1);
		prime = prime.add(ex2);
		System.out.println("mmmmmm"+extensionVA+"    "+extensionPP+"    "+prime);
		resultat.setPrime(prime + "");
		return resultat;
	}
	
	public TarifAgrDTO gettarif661(BigDecimal taux,BigDecimal capital) {

		TarifAgrDTO resultat = iTarifIncendieAgrDAO.getbyTarif661(
				taux, capital);
		BigDecimal	prime = new BigDecimal(resultat.getPrime());
	

		resultat.setPrime(prime + "");
		return resultat;
	}

	@Override
	public Client enregistrerClient(Client client) {
		// Creation Client
		System.out.println("profession:" + client.getProfession() + " adr:"
				+ client.getAdrClient() + "voie=" + client.getTypeVoie());
		if (client.getTypeVoie() != null && client.getTypeVoie().getCode() != 0) {
			client.setTypeVoie(iTypeVoieDAO
					.find(client.getTypeVoie().getCode()));
		}

		if (client.getProfession() != null) {

			if (client.getProfession().getIdProfession() != null
					&& client.getProfession().getIdProfession() != 0) {
				client.setProfession(iProfessionClientDAO.find(client
						.getProfession().getIdProfession()));
			}
		}

		// client.setAdrClient(" ");
		// client.setTypeVoie(null);
		// client.setProfession(null);
		client.setDatCreation(new Date());
		if (client.getNumAdr() == null) {
			client.setNumAdr(BigDecimal.ZERO);
		}
		client = iClientDAO.creation(client);
		return client;
	}

	public String getNumeroQuit() {
		Integer currentNomber;
		Calendar c = Calendar.getInstance();
		String currentyear = String.valueOf(c.getInstance().get(c.YEAR));
		System.out.println("current year=" + currentyear);
		String structure = "00000";
		String quit = iQuittanceDAO.FindMaxNumquit();

		if (quit.equals(0)) {
			currentNomber = 1;
		} else {
			String year = String.valueOf(quit).substring(0, 4);
			System.out
					.println("year=" + (String.valueOf(quit).substring(0, 4)));
			if (year.equals(currentyear)) {
				currentNomber = Integer.parseInt((String.valueOf(quit)
						.substring(4, 9))) + 1;
				System.out.println("currentNomber=" + currentNomber);
			} else {
				currentNomber = 1;
			}
		}
		String number = currentNomber.toString();
		String newNomber = currentyear
				+ structure.substring(0, structure.length() - number.length())
				+ number;
		return newNomber;
	}

	@Override
	public ContratDTO enregistrerAgricoleIncendieGrele(long idProdconv,
			Date dateEffet, Date dateFin, TarifAgrDTO prime, Client client,
			Utilisateurs utilisateur, List<Parcelle> listParcelle,
			String selectedClasse, BigDecimal capital, BigInteger idPack,
			boolean packComplet,String num_decision,String ref_credit, int nbrparcelle) {
		Prodconv prodConv = iProdconvDAO.find(idProdconv);
		Tauxcomm tauxCommision = iTauxcommDAO.getTauxComValide(idProdconv);
		Agrclasse agrclasse = iAgrClasseDAO.getClasseByNom(selectedClasse);
		Police p = new Police();
		Adhesion a = new Adhesion();
		p.setNPolice(iPoliceDAO.getNumeroContrat(prodConv.getCodeProduit()));
		System.out.println("numero police:" + p.getNPolice());
		p.setCodProdui(prodConv.getProduit());
		p.setConvention(prodConv.getConvention());
		p.setDatDeb(dateEffet);
		p.setDatFin(dateFin);
		p.setLibelle(prodConv.getProduit().getLibelleProduit());
		p.setEtaPolice("V");
		if (tauxCommision != null) {
			p.setTxCom(tauxCommision.getTaux());
		}

		p = iPoliceDAO.enregistrer(p);

		// Creation adhesion
		BigDecimal primeNette = new BigDecimal(prime.getPrime());
		a.setCodeP(prodConv.getCodeProduit());
		a.setPolice(p);
		a.setNadhesion(p.getNPolice());
		BigDecimal fraisContrat = new BigDecimal(0);
		
		System.out.println("valeur ====== packcomplet"+packComplet);
		if (packComplet == false) {
			fraisContrat = prodConv.getProduit().getMntfrc();
		}
		a.setPrimetot(primeNette.doubleValue() + fraisContrat.doubleValue());
		a.setMtPrime(primeNette.doubleValue());
		a.setCout(fraisContrat);
		
		System.out.println("frais========contrat"+a.getCout());
		a.setFranchise(prime.getFranchise());
		a.setDatcreation(new Date());
		a.setDatDebut(dateEffet);
		a.setDatFin(dateFin);
		a.setEtat("V");
		a.setClient(client);
		a.setUtilisateur(utilisateur);
		a.setIdUtilisateurs(utilisateur.getIdUtilisateurs());
		a.setCapital(capital.doubleValue());
		a.setImpr(false);
		a.setFranchise(prime.getFranchise());
		a.setFga(3.0);
		a.setNum_decision(num_decision);
		a.setRef_credit(ref_credit);
		iAdhesionDAO.create(a);

		for (Parcelle parcelle : listParcelle) {
			BigDecimal capitalpa = capital.divide(BigDecimal.valueOf(nbrparcelle));
			System.out.println(parcelle);
			parcelle.setnPolice(p.getNPolice());
			parcelle.setUniteRendement(agrclasse.getUniterendement());
			parcelle.setUnitePRix(agrclasse.getUniteprix());
			BigDecimal rendement = BigDecimal.ZERO;
			if (parcelle.getPrixunitaire() != null) {
				if (agrclasse.getMinnbrpied() != null
						&& parcelle.getNbrpied() != null) {
					// K DIV (nombre de piedsxprix unitaire)
					rendement = capitalpa.divide((parcelle.getPrixunitaire()
							.multiply(new BigDecimal(parcelle.getNbrpied()))),3,
							RoundingMode.HALF_UP);
									 
					
				} else if (agrclasse.getMinnbrpied() == null
						&& parcelle.getSuperficie() != null) {
					// K DIV (supxprix unitaire)
					rendement = capitalpa.divide((parcelle.getSuperficie()
							.multiply(parcelle.getPrixunitaire())),3,
							RoundingMode.HALF_UP);
					
					
				}
			}
			
			parcelle.setRendement(rendement.toString());
			iParcelleDAO.creation(parcelle);
		}

		Quittance q = new Quittance();
		q.setDATE_CREATION(new Date());
		q.setDateDu(dateEffet);
		q.setDateAu(dateFin);
		q.setIdAdhesion(a);
		q.setMontant(primeNette.doubleValue());
		q.setTaxe(Double.valueOf(0));
		Integer numQui = Integer.parseInt(getNumeroQuit());
		q.setNumquit(numQui);
		iQuittanceDAO.create(q);

		// enregistrement dans packpolice
		PackPolicePK packPolicePK = new PackPolicePK(idPack, p.getNPolice());
		PackPolice packPolice = new PackPolice();
		packPolice.setPackPolicePK(packPolicePK);
		packPolice.setIdClient(client.getIdClient());
		packPolice.setDatecreation(new Date());
		packPolice.setEtaPolice("V");
		iPackPoliceDAO.create(packPolice);

		ContratDTO contrat = new ContratDTO();
		contrat.setAdhesion(a);
		contrat.setPolice(p);
		contrat.setClient(client);
		contrat.setParcelles(listParcelle);
		contrat.setQuittance(q);
		return contrat;
	}
	
	//pack3
	
	@Override
	public ContratDTO enregistrerAgricoleIncendieAliment(long idProdconv,
			Date dateEffet, Date dateFin, TarifAgrDTO prime, Client client,
			Utilisateurs utilisateur, BigDecimal capital, BigInteger idPack,
			boolean packComplet,String num_decision,String ref_credit,String situation_risque) {
		Prodconv prodConv = iProdconvDAO.find(idProdconv);
		Tauxcomm tauxCommision = iTauxcommDAO.getTauxComValide(idProdconv);
		
		Police p = new Police();
		Adhesion a = new Adhesion();
		p.setNPolice(iPoliceDAO.getNumeroContrat(prodConv.getCodeProduit()));
		System.out.println("numero police:" + p.getNPolice());
		p.setCodProdui(prodConv.getProduit());
		p.setConvention(prodConv.getConvention());
		p.setDatDeb(dateEffet);
		p.setDatFin(dateFin);
		p.setLibelle(prodConv.getProduit().getLibelleProduit());
		p.setEtaPolice("V");
		if (tauxCommision != null) {
			p.setTxCom(tauxCommision.getTaux());
		}

		p = iPoliceDAO.enregistrer(p);

		// Creation adhesion
		BigDecimal primeNette = new BigDecimal(prime.getPrime());
		a.setCodeP(prodConv.getCodeProduit());
		a.setPolice(p);
		a.setNadhesion(p.getNPolice());
		BigDecimal fraisContrat = new BigDecimal(0);
		if (!packComplet) {
			fraisContrat = prodConv.getProduit().getMntfrc();
		}
		a.setPrimetot(primeNette.doubleValue() + fraisContrat.doubleValue());
		a.setMtPrime(primeNette.doubleValue());
		a.setCout(fraisContrat);
		a.setFranchise(prime.getFranchise());
		a.setDatcreation(new Date());
		a.setDatDebut(dateEffet);
		a.setDatFin(dateFin);
		a.setEtat("V");
		a.setClient(client);
		a.setUtilisateur(utilisateur);
		a.setIdUtilisateurs(utilisateur.getIdUtilisateurs());
		a.setCapital(capital.doubleValue());
		a.setImpr(false);
		a.setFranchise(prime.getFranchise());
		a.setFga(3.0);
		a.setNum_decision(num_decision);
		a.setRef_credit(ref_credit);
		a.setSituation_risque(situation_risque);
		iAdhesionDAO.create(a);

		Quittance q = new Quittance();
		q.setDATE_CREATION(new Date());
		q.setDateDu(dateEffet);
		q.setDateAu(dateFin);
		q.setIdAdhesion(a);
		q.setMontant(primeNette.doubleValue());
		q.setTaxe(Double.valueOf(0));
		Integer numQui = Integer.parseInt(getNumeroQuit());
		q.setNumquit(numQui);
		iQuittanceDAO.create(q);

		// enregistrement dans packpolice
		PackPolicePK packPolicePK = new PackPolicePK(idPack, p.getNPolice());
		PackPolice packPolice = new PackPolice();
		packPolice.setPackPolicePK(packPolicePK);
		packPolice.setIdClient(client.getIdClient());
		packPolice.setDatecreation(new Date());
		packPolice.setEtaPolice("V");
		iPackPoliceDAO.create(packPolice);

		ContratDTO contrat = new ContratDTO();
		contrat.setAdhesion(a);
		contrat.setPolice(p);
		contrat.setClient(client);
		//contrat.setParcelles(listParcelle);
		contrat.setQuittance(q);
		return contrat;
	}
	
	
	@Override
	public Tauxreduction getTauxreductionValide(long idProdconv,Integer pack) {
		return iTauxReductionDAO.getTauxComValide(idProdconv,pack);
	}

	@Override
	public ContratDTO enregistrerAgricoleTD(long idProdconv, Date dateEffet,
			Date dateFin, double prime, Client client,
			Utilisateurs utilisateur, String numPolice, BigInteger idPack, String num_decision, String ref_credit) {
		Prodconv prodConv = iProdconvDAO.find(idProdconv);
		Tauxcomm tauxCommision = iTauxcommDAO.getTauxComValide(idProdconv);
		Police p = iPoliceDAO.find(numPolice);
		Adhesion a = new Adhesion();
		// p.setNPolice(iPoliceDAO.getNumeroContrat(prodConv.getCodeProduit()));
		// System.out.println("numero police:" + p.getNPolice());
		// p.setCodProdui(prodConv.getProduit());
		// p.setConvention(prodConv.getConvention());
		// p.setDatDeb(dateEffet);
		// p.setDatFin(dateFin);
		// p.setLibelle(prodConv.getProduit().getLibelleProduit());
		// p.setEtaPolice("V");
		// if(tauxCommision!=null){
		// p.setTxCom(tauxCommision.getTaux());
		// }
		//
		// p = iPoliceDAO.enregistrer(p);

		// Creation adhesion
		BigDecimal primeNette = new BigDecimal(prime);
		String numAdhesionS = iAdhesionDAO.getNadhesion(numPolice);
		a.setCodeP(prodConv.getCodeProduit());
		a.setPolice(p);
		a.setNadhesion(numAdhesionS);
		BigDecimal fraisContrat = BigDecimal.ZERO;
		a.setPrimetot(primeNette.doubleValue() + fraisContrat.doubleValue());
		a.setMtPrime(primeNette.doubleValue());
		a.setCout(fraisContrat);
		a.setFranchise("");
		a.setDatcreation(new Date());
		a.setDatDebut(dateEffet);
		a.setDatFin(dateFin);
		a.setEtat("V");
		a.setClient(client);
		a.setUtilisateur(utilisateur);
		a.setIdUtilisateurs(utilisateur.getIdUtilisateurs());
		a.setImpr(false);
		a.setNum_decision(num_decision);
		a.setRef_credit(ref_credit);
		a.setFga(3.0);
		iAdhesionDAO.create(a);

		Quittance q = new Quittance();
		q.setDATE_CREATION(new Date());
		q.setDateDu(dateEffet);
		q.setDateAu(dateFin);
		q.setIdAdhesion(a);
		q.setMontant(primeNette.doubleValue());
		q.setTaxe(Double.valueOf(0));
		Integer numQui = Integer.parseInt(getNumeroQuit());
		q.setNumquit(numQui);
		iQuittanceDAO.create(q);

		// enregistrement dans packpolice
		PackPolicePK packPolicePK = new PackPolicePK(idPack, numAdhesionS);
		PackPolice packPolice = new PackPolice();
		packPolice.setPackPolicePK(packPolicePK);
		packPolice.setIdClient(client.getIdClient());
		packPolice.setDatecreation(new Date());
		packPolice.setEtaPolice("V");
		iPackPoliceDAO.create(packPolice);

		ContratDTO contrat = new ContratDTO();
		contrat.setAdhesion(a);
		contrat.setPolice(p);
		contrat.setClient(client);
		contrat.setQuittance(q);
		return contrat;
	}

	@Override
	public Delegation getDelegationBYID(BigDecimal idDelegation) {
		return iDelegationDAO.find(idDelegation);
	}

	@Override
	public List<PrixHom> findPrixHom() {
		return iPrixHomDAO.findPrixHom();
	}

	@Override
	public BigInteger getMaxPackPolicePK() {
		return iPackPolicePKDAO.getMaxPackPolicePK();
	}

	@Override
	public PackPoliceDTO chercherPackPolice(String numPolice) {
		return iPackPoliceDAO.chercherPackPolice(numPolice);
	}

	/*
	 * @Override public DecisionAgr test(String numdecision) { return
	 * Main.testws("A1121800229"); }
	 */

	@Override
	public void annulerPackPolice(PackPoliceDTO policeDTO) {

		if (policeDTO.getList() != null) {
			for (PackPolice p : policeDTO.getList()) {
				Police police = iPoliceDAO.find(p.getPackPolicePK()
						.getNPolice());

				if (police != null) {
					police.setEtaPolice("A");
					iPoliceDAO.edit(police);
				}
				Adhesion adhesion = iAdhesionDAO.FindAdhesionByNadhesion(p
						.getPackPolicePK().getNPolice());
				if (adhesion != null) {
					adhesion.setEtat("A");
					iAdhesionDAO.edit(adhesion);

				}

				p.setEtaPolice("A");
				iPackPoliceDAO.edit(p);
			}

		}
	}

	@Override
	public QuittancesClient getMontantPackByNumQuittance(int numquit) {
		
   
		QuittancesClient retour = new QuittancesClient();
		Quittance quittance = iQuittanceDAO.searchquit(numquit);
		if (quittance == null) {
			retour.setCodeRetour(0);
			retour.setMsgRetour("Numero quittance erroné");
			return retour;
		}

		Adhesion adhesion = quittance.getIdAdhesion();
		retour.setNom(adhesion.getClient().getNomClient());
		retour.setPrenom(adhesion.getClient().getPrenomClient());
		retour.setIdentifiant(adhesion.getClient().getIdentifiant());
		List<PackPolice> listPackPolice=iPackPoliceDAO.findPackbypolice(adhesion.getNadhesion());
		retour.setMontant(0.0);
		retour.setCommision(0.0);
		retour.setNumdecision(adhesion.getNum_decision());
		retour.setNumdetaildecision(adhesion.getRef_credit());
		
		if(listPackPolice !=null && ! listPackPolice.isEmpty()){
			
			for (PackPolice packPolice : listPackPolice) {
				
					Quittance q= iQuittanceDAO.searchquitByNAdhesion
							(packPolice.getPackPolicePK().getNPolice());
					if(q!=null){
									
						BigDecimal x = q.getIdAdhesion().getPolice().getTxCom(); 
						double d = x.doubleValue();
						retour.setMontant(retour.getMontant() + q.getMontant()  + q.getIdAdhesion().getFga() + (q.getIdAdhesion().getCout().doubleValue())) ;
						
						
						retour.setCommision(retour.getCommision()+(q.getMontant()   * (d/100)));
						
						
						 
					}
				
			}
	
	
					
			BigDecimal mont = new BigDecimal((retour.getMontant()*1000));
			BigDecimal com = new BigDecimal(retour.getCommision()*1000);
			
			mont=mont.setScale(0, RoundingMode.HALF_UP);
			com=com.setScale(0, RoundingMode.HALF_UP);
				
			retour.setMontant(mont.doubleValue());
			retour.setCommision(com.doubleValue());

			//retour.setMontant((retour.getMontant())*1000);
			//retour.setCommision((retour.getCommision())*1000);
			
			
			if(listPackPolice.size()<=2){
				retour.setPackComplet(false);
				retour.setNomPack("Grele Incendie");
			}else{
				retour.setPackComplet(true);
				retour.setNomPack("Grele Incendie TD");
			}
			
		}
		
		
		return retour;
		
	}
	
	@Override
	public QuittancesClient getMontantPackByNumQuittance2(int numquit) {
		QuittancesClient retour = new QuittancesClient();
		Quittance quittance = iQuittanceDAO.searchquit(numquit);
		if (quittance == null) {
			retour.setCodeRetour(0);
			retour.setMsgRetour("Numero quittance erroné");
			return retour;
		}

		Adhesion adhesion = quittance.getIdAdhesion();
		retour.setNom("neant");
		retour.setPrenom("neant");
		retour.setIdentifiant("neant");
		List<PackPolice> listPackPolice=iPackPoliceDAO.findPackbypolice(adhesion.getNadhesion());
		retour.setMontant(quittance.getMontant());
		if(listPackPolice !=null && ! listPackPolice.isEmpty()){
			
			for (PackPolice packPolice : listPackPolice) {
				if(!packPolice.getPackPolicePK().getNPolice().equals(adhesion.getNadhesion())){
					Quittance q= iQuittanceDAO.searchquitByNAdhesion
							(packPolice.getPackPolicePK().getNPolice());
					if(q!=null){
						retour.setMontant(Double.valueOf(0));
					}
				}
			}
			if(listPackPolice.size()<=2){
				retour.setPackComplet(false);
				retour.setNomPack("neant");
			}else{
				retour.setPackComplet(true);
				retour.setNomPack("neant");
			}
		}
		
		
		return retour;
		
	}
	
	
	@Override
	public DecisionAgr simulationWSBNA2() throws DatatypeConfigurationException{
		DecisionAgr agr=new DecisionAgr();
		Agriculteur a =new Agriculteur();
		Adresse adr   =new Adresse();
		adr.setCite("Mourouj");
		adr.setCodePostale("2074");
		adr.setGouvernerat(10);
		adr.setImmeuble("11");
		adr.setPays("TUNISIE");
		adr.setRue("PARIS");
		adr.setVille("TUNIS");
		a.setAddresse(adr);
		
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(1929, 7, 3, 5, 22, 0, 0, 1);
		a.setDateNaissance(date2);
		a.setMail("mmmm@mm.tn");
		a.setNom("JAWAHDOU");
		a.setNumPiece("12122121");
		a.setNumTel("98565656");
		a.setPrenom("");
		a.setRaisonsocialle("think digital tunisia");

		a.setTypePiece("9");
		a.setSex("");
		agr.setAgriculteur(a);
		agr.setNumCompte("");
		
		agr.setMontant(3000000L);
		DetailDecAgri d= new DetailDecAgri();
		d.setCodeProduitSpeculation(661L);
		d.setSpeculation("661");
		d.setDateDerniereEch(DatatypeFactory.newInstance().newXMLGregorianCalendar(2019,12, 1, 5, 22, 0, 0, 1));
		d.setMontant(3000000L);
		d.setDateContrat(DatatypeFactory.newInstance().newXMLGregorianCalendar(2019,7, 22, 5, 22, 0, 0, 1));
		agr.setDetailsDecision(d);
		
		return agr;
		
	}
	
	
	@Override
	public DecisionAgr simulationWSBNA() throws DatatypeConfigurationException{
		DecisionAgr agr=new DecisionAgr();
		Agriculteur a =new Agriculteur();
		Adresse adr   =new Adresse();
		adr.setCite("Mourouj");
		adr.setCodePostale("2074");
		adr.setGouvernerat(10);
		adr.setImmeuble("11");
		adr.setPays("TUNISIE");
		adr.setRue("PARIS");
		adr.setVille("TUNIS");
		a.setAddresse(adr);
		
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(1989, 7, 3, 5, 22, 0, 0, 1);
		a.setDateNaissance(date2);
		a.setMail("mmmm@mm.tn");
		a.setNom("JAWAHDOU");
		a.setNumPiece("05050505");
		a.setNumTel("98565656");
		a.setPrenom("KACEM");
		a.setTypePiece("2");
		a.setSex("M");
		agr.setAgriculteur(a);
		agr.setNumCompte("123456789");
		
		agr.setMontant(250000000L);
		DetailDecAgri d= new DetailDecAgri();
		d.setCodeProduitSpeculation(153L);
		d.setSpeculation("153");
		d.setDateDerniereEch(DatatypeFactory.newInstance().newXMLGregorianCalendar(2019,12, 1, 5, 22, 0, 0, 1));
		d.setMontant(250000000L);
		d.setDateContrat(DatatypeFactory.newInstance().newXMLGregorianCalendar(2019,7, 22, 5, 22, 0, 0, 1));
		agr.setDetailsDecision(d);
		
		return agr;
		
	}
	
	@Override
	public DecisionAgr simulationWSBNAp1() throws DatatypeConfigurationException{
		DecisionAgr agr=new DecisionAgr();
		Agriculteur a =new Agriculteur();
		Adresse adr   =new Adresse();
		adr.setCite("Mourouj");
		adr.setCodePostale("2074");
		adr.setGouvernerat(10);
		adr.setImmeuble("11");
		adr.setPays("TUNISIE");
		adr.setRue("PARIS");
		adr.setVille("TUNIS");
		a.setAddresse(adr);
		
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(1989, 7, 3, 5, 22, 0, 0, 1);
		a.setDateNaissance(date2);
		a.setMail("mmmm@mm.tn");
		a.setNom("JAWAHDOU");
		a.setNumPiece("05050505");
		a.setNumTel("98565656");
		a.setPrenom("KACEM");
		a.setTypePiece("2");
		a.setSex("M");
		agr.setAgriculteur(a);
		agr.setNumCompte("123456789");

		
		agr.setMontant(2000000L);
		DetailDecAgri d= new DetailDecAgri();
		d.setCodeProduitSpeculation(150L);
		d.setSpeculation("150");
		d.setDateDerniereEch(DatatypeFactory.newInstance().newXMLGregorianCalendar(2019,12, 1, 5, 22, 0, 0, 1));
		d.setMontant(2000000L);
		d.setDateContrat(DatatypeFactory.newInstance().newXMLGregorianCalendar(2019,7, 22, 5, 22, 0, 0, 1));
		agr.setDetailsDecision(d);
		
		return agr;
		
	}
	
	
	@Override
	public DecisionAgr simulationWSBNApm1() throws DatatypeConfigurationException{
		DecisionAgr agr=new DecisionAgr();
		Agriculteur a =new Agriculteur();
		Adresse adr   =new Adresse();
		adr.setCite("Mourouj");
		adr.setCodePostale("2074");
		adr.setGouvernerat(10);
		adr.setImmeuble("11");
		adr.setPays("TUNISIE");
		adr.setRue("PARIS");
		adr.setVille("TUNIS");
		a.setAddresse(adr);
		
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(1929, 7, 3, 5, 22, 0, 0, 1);
		a.setDateNaissance(date2);
		a.setMail("mmmm@mm.tn");
		a.setNom("JAWAHDOU");
		a.setNumPiece("12122121");
		a.setNumTel("98565656");
		a.setPrenom("");
		a.setRaisonsocialle("think digital tunisia");

		a.setTypePiece("9");
		a.setSex("");
		agr.setAgriculteur(a);
		agr.setNumCompte("123456789");

		
		agr.setMontant(250000000L);
		DetailDecAgri d= new DetailDecAgri();
		d.setCodeProduitSpeculation(133L);
		d.setSpeculation("133");
		d.setDateDerniereEch(DatatypeFactory.newInstance().newXMLGregorianCalendar(2019,12, 1, 5, 22, 0, 0, 1));
		d.setMontant(250000000L);
		d.setDateContrat(DatatypeFactory.newInstance().newXMLGregorianCalendar(2019,7, 22, 5, 22, 0, 0, 1));
		agr.setDetailsDecision(d);
		
		return agr;
	}


}
