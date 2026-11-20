package com.star.Ba.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;





import javax.xml.datatype.DatatypeConfigurationException;

import com.bna.ws.soap.saeb.agricole.services.DecisionAgr;
import com.star.Ba.dto.ContratDTO;
import com.star.Ba.dto.PackPoliceDTO;
import com.star.Ba.dto.QuittancesClient;
import com.star.Ba.dto.TarifAgrDTO;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agrnature;
import com.star.Ba.model.Client;
import com.star.Ba.model.Delegation;
import com.star.Ba.model.PackPolice;
import com.star.Ba.model.Parcelle;
import com.star.Ba.model.PrixHom;
import com.star.Ba.model.TarifGrele;
import com.star.Ba.model.Tauxreduction;
import com.star.Ba.model.Utilisateurs;

public interface IParametreAgricoleService {

	List<Agrnature> getAgrNature();

	List<TarifGrele> getTarifGrele();
	
	
   


	List<Delegation> getDelegationByZone(BigDecimal idZone);


	Client enregistrerClient(Client client);

	Delegation getDelegationBYID(BigDecimal idDelegation);

	TarifAgrDTO getTarifByClasseZone(String classe, String idzone, BigDecimal capital, int nbrExtension);

	TarifAgrDTO getTarifByIdprodConv(long idProdconv, BigDecimal capital, boolean extensionVA, boolean extensionPP);
	TarifAgrDTO gettarif661(BigDecimal taux,BigDecimal capital);


	List<PrixHom> findPrixHom();

	BigInteger getMaxPackPolicePK();


	ContratDTO enregistrerAgricoleTD(long idProdconv, Date dateEffet,
			Date dateFin, double prime, Client client,
			Utilisateurs utilisateur, String numPolice, BigInteger idPack, String num_decision, String ref_credit);

	PackPoliceDTO chercherPackPolice(String numPolice);

	void annulerPackPolice(PackPoliceDTO policeDTO);

	ContratDTO enregistrerAgricoleIncendieGrele(long idProdconv,
			Date dateEffet, Date dateFin, TarifAgrDTO prime, Client client,
			Utilisateurs utilisateur, List<Parcelle> listParcelle,
			String selectedClasse, BigDecimal capital, BigInteger idPack,
			boolean packComplet, String num_decision, String ref_credit, int nbrparcelle);
	
	ContratDTO enregistrerAgricoleIncendieAliment(long idProdconv,
			Date dateEffet, Date dateFin, TarifAgrDTO prime, Client client,
			Utilisateurs utilisateur,BigDecimal capital, BigInteger idPack,
	boolean packComplet, String num_decision, String ref_credit, String situation_risque);

	QuittancesClient getMontantPackByNumQuittance(int numquit);

	QuittancesClient getMontantPackByNumQuittance2(int numquit);

	DecisionAgr simulationWSBNA() throws DatatypeConfigurationException;

	Tauxreduction getTauxreductionValide(long idProdconv, Integer pack);

	DecisionAgr simulationWSBNA2() throws DatatypeConfigurationException;

	DecisionAgr simulationWSBNApm1() throws DatatypeConfigurationException;

	DecisionAgr simulationWSBNAp1() throws DatatypeConfigurationException;

	//DecisionAgr test(String numdecision);


}
