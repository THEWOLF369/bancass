package com.star.Ba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the PRODUIT database table.
 * 
 */
@Entity
@NamedQuery(name="Produit.findAll", query="SELECT p FROM Produit p")
public class Produit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRODUIT_IDPRODUIT_GENERATOR", sequenceName="ID_PRODUIT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUIT_IDPRODUIT_GENERATOR")
	@Column(name="ID_PRODUIT")
	private long idProduit;

	@Column(name="CODE_PRODUIT")
	private String codeProduit;
	
	
	@Column(name="FRAIS_AQUISITION")
	private BigDecimal fraisAquisition;

	@Column(name="FRAIS_GESTION")
	private BigDecimal fraisGestion;

	@Column(name="LIBELLE_PRODUIT")
	private String libelleProduit;

	//bi-directional many-to-one association to Prodconv
    @OneToMany(mappedBy = "produit")
    private List<Prodconv> prodconvs;
    
  //bi-directional many-to-one association to taxe
    @OneToMany(mappedBy = "produit")
    private List<Taxe> taxe;
    
	//bi-directional many-to-one association to Pack_test
	@OneToMany(mappedBy="produit")
	private List<Pack> packs;

	//bi-directional many-to-one association to Tauxcomm_test
	@OneToMany(mappedBy="produit")
	private List<Tauxcomm> tauxcomms;

//    @JoinTable(name = "produit_has_convention",
//            joinColumns = {
//                @JoinColumn(name = "produit_id_produit", referencedColumnName = "ID_PRODUIT")
//            },
//            inverseJoinColumns = {
//                @JoinColumn(name = "convention", referencedColumnName = "ID_CONVENTION")
//            }
//    )
//    @ManyToMany
//    private List<Convention> conventions;

	@Column(name = "TAUXBAS")
  private BigDecimal tauxbas;
  @Column(name = "TAUXANX")
  private BigDecimal tauxanx;
  @Column(name = "MNTFRC")
  private BigDecimal mntfrc;
  
	
  
	public Produit() {
	}

	public long getIdProduit() {
		return this.idProduit;
	}

	public void setIdProduit(long idProduit) {
		this.idProduit = idProduit;
	}

	public String getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	public String getLibelleProduit() {
		return this.libelleProduit;
	}

	public void setLibelleProduit(String libelleProduit) {
		this.libelleProduit = libelleProduit;
	}
	public List<Prodconv> getProdconvs() {
        return this.prodconvs;
    }

    public void setProdconvs(List<Prodconv> prodconvs) {
        this.prodconvs = prodconvs;
    }
    
    
//
//    public List<Convention> getConventions() {
//        return conventions;
//    }
//
//    public void setConventions(List<Convention> conventions) {
//        this.conventions = conventions;
//    }

    public List<Taxe> getTaxe() {
		return taxe;
	}

	public void setTaxe(List<Taxe> taxe) {
		this.taxe = taxe;
	}

	public BigDecimal getFraisAquisition() {
		return this.fraisAquisition;
	}

	public void setFraisAquisition(BigDecimal fraisAquisition) {
		this.fraisAquisition = fraisAquisition;
	}

	public BigDecimal getFraisGestion() {
		return this.fraisGestion;
	}

	public void setFraisGestion(BigDecimal fraisGestion) {
		this.fraisGestion = fraisGestion;
	}
	
	
    public Prodconv addProdconv(Prodconv prodconv) {
        getProdconvs().add(prodconv);
        prodconv.setProduit(this);

        return prodconv;
    }

    public Prodconv removeProdconv(Prodconv prodconv) {
        getProdconvs().remove(prodconv);
        prodconv.setProduit(null);

        return prodconv;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProduit ^ (idProduit >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		if (idProduit != other.idProduit)
			return false;
		return true;
	}
	
	public List<Pack> getPacks() {
		return this.packs;
	}

	public void setPacks(List<Pack> packs) {
		this.packs = packs;
	}

	public Pack addPack(Pack pack) {
		getPacks().add(pack);
		pack.setProduit(this);

		return pack;
	}

	public Pack removePack(Pack pack) {
		getPacks().remove(pack);
		pack.setProduit(null);

		return pack;
	}

	public List<Tauxcomm> getTauxcomms() {
		return this.tauxcomms;
	}

	public void setTauxcomms(List<Tauxcomm> tauxcomms) {
		this.tauxcomms = tauxcomms;
	}

	public Tauxcomm addTauxcomm(Tauxcomm tauxcomm) {
		getTauxcomms().add(tauxcomm);
		tauxcomm.setProduit(this);

		return tauxcomm;
	}

	public Tauxcomm removeTauxcomm(Tauxcomm tauxcomm) {
		getTauxcomms().remove(tauxcomm);
		tauxcomm.setProduit(null);

		return tauxcomm;
	}
    
	public BigDecimal getTauxbas() {
      return tauxbas;
  }

  public void setTauxbas(BigDecimal tauxbas) {
      this.tauxbas = tauxbas;
  }

  public BigDecimal getTauxanx() {
      return tauxanx;
  }

  public void setTauxanx(BigDecimal tauxanx) {
      this.tauxanx = tauxanx;
  }

  public BigDecimal getMntfrc() {
      return mntfrc;
  }

  public void setMntfrc(BigDecimal mntfrc) {
      this.mntfrc = mntfrc;
  }


}