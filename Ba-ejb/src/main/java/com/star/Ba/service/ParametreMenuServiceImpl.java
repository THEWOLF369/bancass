package com.star.Ba.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.star.Ba.dao.IAgrNatureDAO;
import com.star.Ba.dao.IMenucontenuDAO;
import com.star.Ba.dao.IParametresPackDAO;
import com.star.Ba.dao.IProdconvDAO;
import com.star.Ba.dao.IProfilDAO;
import com.star.Ba.dto.ContenuDTO;
import com.star.Ba.dto.UtilisateursConnexionDTO;
import com.star.Ba.model.Agrnature;
import com.star.Ba.model.Contenu;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Menucontenu;
import com.star.Ba.model.ParametresPack;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.TarifGrele;

@Stateless
public class ParametreMenuServiceImpl implements IParametreMenuService {
	@EJB
	private IMenucontenuDAO iMenucontenuDAO;

	@EJB
	private  IParametresPackDAO iParametresPackDAO;
	@Override
	public List<ContenuDTO> getMenuContenu(String page, BigDecimal idProfil) {
		List<Menucontenu> menuContenu = iMenucontenuDAO.getMenuContenu(page, idProfil);
		List<ContenuDTO> result = new ArrayList<ContenuDTO>();
		if (menuContenu != null) {
			for (Menucontenu menucontenu2 : menuContenu) {
				System.out.println("ID prodCOnv="+menucontenu2.getIdcontenu().getIdprodconv());
				if (menucontenu2.getIdcontenu().getIdprodconv() != null) {
					System.out.println("LISt GRELE:"+menucontenu2.getIdcontenu().getIdprodconv().getTarifGreleList());
					System.out.println("LISt INC:"+menucontenu2.getIdcontenu().getIdprodconv().getTarifIncendieAgrList());
					if (menucontenu2.getIdcontenu().getIdprodconv().getTarifGreleList() != null

					&& !menucontenu2.getIdcontenu().getIdprodconv().getTarifGreleList().isEmpty()) {
						System.out.println("++++++ GRELE" + menucontenu2.getIdcontenu().getIdprodconv());
						menucontenu2.getIdcontenu().getIdprodconv().setTarifIncendieAgrList(null);
					}

					if (menucontenu2.getIdcontenu().getIdprodconv().getTarifIncendieAgrList() != null
							&& !menucontenu2.getIdcontenu().getIdprodconv().getTarifIncendieAgrList().isEmpty()) {
						System.out.println("++++++ INCENDIE" + menucontenu2.getIdcontenu().getIdprodconv());
						menucontenu2.getIdcontenu().getIdprodconv().setTarifGreleList(null);
					}
					if ((menucontenu2.getIdcontenu().getIdprodconv().getTarifGreleList() == null
							|| menucontenu2.getIdcontenu().getIdprodconv().getTarifGreleList().isEmpty())
							&& (menucontenu2.getIdcontenu().getIdprodconv().getTarifIncendieAgrList() == null
									|| menucontenu2.getIdcontenu().getIdprodconv().getTarifIncendieAgrList().isEmpty())
							) {
								System.out.println("++++++ TD" + menucontenu2.getIdcontenu().getIdprodconv());
								menucontenu2.getIdcontenu().getIdprodconv().setTarifIncendieAgrList(null);
								menucontenu2.getIdcontenu().getIdprodconv().setTarifGreleList(null);
							}
					
					
				}
				ContenuDTO c = new ContenuDTO(menucontenu2.getIdcontenu(), menucontenu2.getObligatoire().intValue());
				if (menucontenu2.getObligatoire().intValue() == 0) { // !Obligatoire
					c.setChecked(true);
					c.setDisabledCheckBox(false);
				}
				
				
				else if (menucontenu2.getObligatoire().intValue() == 5) {// !Obligatoire
					c.setChecked(true);
					c.setDisabledCheckBox(false);
				}
				
				else if (menucontenu2.getObligatoire().intValue() == 1) {// Obligatoire
					c.setChecked(true);
					c.setDisabledCheckBox(true);
				} else if (menucontenu2.getObligatoire().intValue() == 2) {// OBLIGATOIRE
																			// SI
																			// TYPE
																			// CLIENT
																			// =
																			// PERSONNE
																			// PHYSIQUE
																			// IL
																			// NE
																			// FAUT
																			// pas
																			// l'ajouter
																			// si
																			// le
																			// type
																			// client
																			// est
																			// societe
																			// comme
																			// le
																			// cas
																			// de
																			// TD
					c.setChecked(true);
					c.setDisabledCheckBox(true);
				} else if (menucontenu2.getObligatoire().intValue() == 3) {// NON
																			// OBLIGATOIRE
																			// SI
																			// TYPE
																			// CLIENT
																			// =
																			// PERSONNE
																			// PHYSIQUE
																			// IL
																			// NE
																			// FAUT
																			// pas
																			// l'ajouter
																			// si
																			// le
																			// type
																			// client
																			// est
																			// societe
																			// comme
																			// le
																			// cas
																			// de
																			// TD
					c.setChecked(false);
					c.setDisabledCheckBox(false);
				}
				result.add(c);
			}
		}
		return result;
	}
	@Override
	public List<ContenuDTO> getMenuContenuByPack(String page, BigDecimal idProfil,Integer pack) {
		List<Menucontenu> menuContenu = iMenucontenuDAO.getMenuContenuByPack(page, idProfil, pack);
		List<ContenuDTO> result = new ArrayList<ContenuDTO>();
		if (menuContenu != null) {
			for (Menucontenu menucontenu2 : menuContenu) {
				System.out.println("ID prodCOnv="+menucontenu2.getIdcontenu().getIdprodconv());
				if (menucontenu2.getIdcontenu().getIdprodconv() != null) {
					System.out.println("LISt GRELE:"+menucontenu2.getIdcontenu().getIdprodconv().getTarifGreleList());
					System.out.println("LISt INC:"+menucontenu2.getIdcontenu().getIdprodconv().getTarifIncendieAgrList());
					if (menucontenu2.getIdcontenu().getIdprodconv().getTarifGreleList() != null

					&& !menucontenu2.getIdcontenu().getIdprodconv().getTarifGreleList().isEmpty()) {
						System.out.println("++++++ GRELE" + menucontenu2.getIdcontenu().getIdprodconv());
						menucontenu2.getIdcontenu().getIdprodconv().setTarifIncendieAgrList(null);
					}

					if (menucontenu2.getIdcontenu().getIdprodconv().getTarifIncendieAgrList() != null
							&& !menucontenu2.getIdcontenu().getIdprodconv().getTarifIncendieAgrList().isEmpty()) {
						System.out.println("++++++ INCENDIE" + menucontenu2.getIdcontenu().getIdprodconv());
						menucontenu2.getIdcontenu().getIdprodconv().setTarifGreleList(null);
					}
					if ((menucontenu2.getIdcontenu().getIdprodconv().getTarifGreleList() == null
							|| menucontenu2.getIdcontenu().getIdprodconv().getTarifGreleList().isEmpty())
							&& (menucontenu2.getIdcontenu().getIdprodconv().getTarifIncendieAgrList() == null
									|| menucontenu2.getIdcontenu().getIdprodconv().getTarifIncendieAgrList().isEmpty())
							) {
								System.out.println("++++++ TD" + menucontenu2.getIdcontenu().getIdprodconv());
								menucontenu2.getIdcontenu().getIdprodconv().setTarifIncendieAgrList(null);
								menucontenu2.getIdcontenu().getIdprodconv().setTarifGreleList(null);
							}
					
					
				}
				ContenuDTO c = new ContenuDTO(menucontenu2.getIdcontenu(), menucontenu2.getObligatoire().intValue());
				c.setNextTab(menucontenu2.getNextTab());
				if (menucontenu2.getObligatoire().intValue() == 0) { // !Obligatoire
					c.setChecked(true);
					c.setDisabledCheckBox(false);
				}
				
				
				else if (menucontenu2.getObligatoire().intValue() == 5) {// !Obligatoire
					c.setChecked(true);
					c.setDisabledCheckBox(false);
				}
				
				else if (menucontenu2.getObligatoire().intValue() == 1) {// Obligatoire
					c.setChecked(true);
					c.setDisabledCheckBox(true);
				} else if (menucontenu2.getObligatoire().intValue() == 2) {// OBLIGATOIRE
																			// SI
																			// TYPE
																			// CLIENT
																			// =
																			// PERSONNE
																			// PHYSIQUE
																			// IL
																			// NE
																			// FAUT
																			// pas
																			// l'ajouter
																			// si
																			// le
																			// type
																			// client
																			// est
																			// societe
																			// comme
																			// le
																			// cas
																			// de
																			// TD
					c.setChecked(true);
					c.setDisabledCheckBox(true);
				} else if (menucontenu2.getObligatoire().intValue() == 3) {// NON
																			// OBLIGATOIRE
																			// SI
																			// TYPE
																			// CLIENT
																			// =
																			// PERSONNE
																			// PHYSIQUE
																			// IL
																			// NE
																			// FAUT
																			// pas
																			// l'ajouter
																			// si
																			// le
																			// type
																			// client
																			// est
																			// societe
																			// comme
																			// le
																			// cas
																			// de
																			// TD
					c.setChecked(false);
					c.setDisabledCheckBox(false);
				}
				result.add(c);
			}
		}
		return result;
	}
	
	@Override
	public Map<String, ParametresPack> findParametresByPack(Integer pack){
		return iParametresPackDAO.findParametresByPack(pack);
	}
}
