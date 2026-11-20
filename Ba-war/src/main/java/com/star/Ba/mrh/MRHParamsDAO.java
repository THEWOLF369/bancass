/*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package com.star.Ba.mrh;
//
//import com.star.Ba.model.ComgouvMrh;
//import com.star.Ba.model.TarifMrh;
//import com.star.Ba.model.Produit;
//import com.star.Ba.model.Tauxcomm;
//import com.star.Ba.model.TauxgarMrh;
import com.star.Ba.service.IParametresMrhService;

//import java.util.List;
import java.util.Map;
//
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
//
///**
// *
// * @author evilkid
// */
@ManagedBean(name = "MRHParamsDAO")
public class MRHParamsDAO {
//
    private Map<String, Object> cities;
   // private Map<String, Object> pack;
    @EJB
    private IParametresMrhService parametresMrhService;

    public MRHParamsDAO() {
    }

    public Map<String, Object> getCities() {
        cities = parametresMrhService.getCities();
        return cities;
    }

//    public Map<String, Object> getPack() {
//        pack = parametresMrhService.getpack;
//        return pack;
//    }

//    public List<TauxgarMrh> getTauxGarentie(String packName) {
//
//        List<TauxgarMrh> list = parametresMrhService.getTauxGarMrhByPack(packName);
//        return list;
//    }
//
//    public Tauxcomm getTauxCom() {
//        return parametresMrhService.getTauxCom("805");
//    }
//
//    public  int getTauxGouv(String city) {
//        ComgouvMrh comgouv1 = parametresMrhService.getTauxGouvByGouv(city);
//        if (comgouv1 != null) {
//            return comgouv1.getTaux();
//        }
//
//        return 0;
//    }
//
//    public TarifMrh getParamPrices() {
//    	TarifMrh tarif805 = parametresMrhService.getTarifMrh();
//        return tarif805;
//    }
//
//    public Produit getProduit() {
//        Produit p = parametresMrhService.getProduitByCode("805");
//
//        return p;
//    }

//    public static int insertDatabase(MrhLogSimulation simulation, String matcin,
//            String codprod, String codagc) {
//        session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//
//        session.save(simulation);
//
//        if (simulation != null) {
//            LogSimulation logSimulation = new LogSimulation(
//                    new LogSimulationId(simulation.getSimId(), matcin, codprod, codagc),
//                    new Date());
//LogSimulation logSim = new LogSimulation();
//log
//            session.save(logSimulation);
//            session.getTransaction().commit();
//
//            session.close();
//            return simulation.getSimId();
//        } else {
//            session.close();
//            return 0;
//        }
//    }
//
//    public static void insertOptionsDatabase(ArrayList<String> opGar, int sim_id) {
//        session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//
//        for (String gar : opGar) {
//            OptionsSimulation opSimulation = new OptionsSimulation(new OptionsSimulationId(sim_id, gar));
//            session.save(opSimulation);
//        }
//
//        session.getTransaction().commit();
//        session.close();
//
//    }
}
