/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.jsf;

import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IProduitDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.jsf.util.JsfUtil;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Duree;
import com.star.Ba.model.Prodconv;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Societe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DualListModel;

/**
 * 
 * @author yassine
 */
@ManagedBean(name = "conventionBean")
@ViewScoped
public class ConventionBean implements Serializable {

	@EJB
	private IConventionDAO conventionDAO;
	@EJB
	private IProduitDAO produitDAO;
	@EJB
	private ISocieteDAO societeDAO;

	private List<Convention> conventions;
	private Convention convention;
	private List<Produit> allProduits;
	private List<Societe> allSocietes;
	private DualListModel<Produit> produitPickList;

	private List<Prodconv> prodconvs;

	@PostConstruct
	public void init() {
		allInit();
	}

	public void allInit() {
		conventions = conventionDAO.findAll();
		allProduits = produitDAO.findAll();
		produitPickList = new DualListModel<Produit>(allProduits,
				new ArrayList<Produit>());
		allSocietes = societeDAO.findAll();
		
		  for (Societe societe : allSocietes) {
          	
          	if(societe.getCodSociet().equalsIgnoreCase("star")){
          		allSocietes.remove(societe);
          		break;
          	}
				
			}
		
		
		convention = new Convention();
		updateConvention = false;
	}

	private boolean updateConvention = false;

	public boolean isUpdateConvention() {
		return updateConvention;
	}

	public void setUpdateConvention(boolean updateConvention) {
		this.updateConvention = updateConvention;
	}
	public void loadConvention(ToggleEvent event) {
        Convention conventiontmp = (Convention) event.getData();
        conventiontmp = conventionDAO.lazyLoad(conventiontmp);

        prodconvs = conventiontmp.getProdconvs();
    }
	public void updateConventionActionListener() {
		
		convention = selectedConvention;
		updateConvention = true;
		produitPickList.getTarget().clear();
		produitPickList.setSource( produitDAO.findAll());
		for (Prodconv prodconv : convention.getProdconvs()) {
			if (produitPickList.getSource().contains(prodconv.getProduit())) {
				produitPickList.getSource().remove(prodconv.getProduit());
			}

			produitPickList.getTarget().add(prodconv.getProduit());

		}
	
	}

	public void renitialiserConvention() {
		allInit();

	}

	public void createOrUpdate() {
		List<Prodconv> prodconvs=new ArrayList<Prodconv>();
		Prodconv newProdconv;
		for (Produit produit : produitPickList.getTarget()) {
			boolean exsist=false;
		
			for (Prodconv prodconv : convention.getProdconvs()) {
				if (prodconv.getProduit().equals(produit)) {
					prodconvs.add(prodconv);
					exsist=true;
					break;
					

				}

			}
			if( !exsist){
				newProdconv=new Prodconv();
				newProdconv.setConvention(convention);
				newProdconv.setProduit(produit);
				
				prodconvs.add(newProdconv);
				}
			
			
				
			}
	
		convention.setProdconvs(prodconvs);
		convention.setDatedernieMiseAjour(new Date());
		conventionDAO.createOrUpdate(convention);

		JsfUtil.addSuccessMessage("La convention a ete ajouté avec succes!");
		init();
	}

	public void addConvention() {
		List<Prodconv> prodconvs = new ArrayList<Prodconv>();

		for (Produit produit : produitPickList.getTarget()) {
			prodconvs.add(new Prodconv(convention, (Produit) produit));
		}

		convention.setProdconvs(prodconvs);

		conventionDAO.create(convention);

		JsfUtil.addSuccessMessage("La convocation a ete ajouter avec success!");
		init();
	}

	public Convention getConvention() {
		return convention;
	}

	public void setConvention(Convention convention) {
		this.convention = convention;
	}

	public DualListModel<Produit> getProduitPickList() {
		return produitPickList;
	}

	public void setProduitPickList(DualListModel<Produit> produitPickList) {
		this.produitPickList = produitPickList;
	}

	public List<Convention> getConventions() {
		return conventions;
	}

	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}

	public Produit getProduit(long id) {
		return produitDAO.find(id);
	}

	@FacesConverter(forClass = Produit.class, value = "produit")
	public static class ProduitControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext,
				UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			ConventionBean controller = (ConventionBean) facesContext
					.getApplication()
					.getELResolver()
					.getValue(facesContext.getELContext(), null,
							"conventionBean");
			return controller.getProduit(getKey(value));
		}

		long getKey(String value) {
			long key;
			key = Long.parseLong(value);
			return key;
		}

		String getStringKey(long value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext,
				UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Produit) {
				Produit o = (Produit) object;
				return getStringKey(o.getIdProduit());
			} else {
				Logger.getLogger(this.getClass().getName()).log(
						Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(),
								Produit.class.getName() });
				return null;
			}
		}

	}

	public List<Prodconv> getProdconvs() {
		return prodconvs;
	}

	// ------------------
	private Convention selectedConvention;

	public Convention getSelectedConvention() {
		return selectedConvention;
	}

	public void setSelectedConvention(Convention selectedConvention) {
		this.selectedConvention = selectedConvention;
	}

	public List<Societe> getAllSocietes() {
		return allSocietes;
	}

	public void setAllSocietes(List<Societe> allSocietes) {
		this.allSocietes = allSocietes;
	}

	public Converter getSocieteConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {
				for (Societe obj : getAllSocietes()) {
					if (obj.getCodSociet().equals(value)) {
						return obj;
					}
				}
				return null;
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				return value.toString();
			}
		};
	}
}
