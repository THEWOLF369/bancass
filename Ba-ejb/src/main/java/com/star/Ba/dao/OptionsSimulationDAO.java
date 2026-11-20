///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.star.Ba.dao;
//
//import com.star.Ba.dao.AbstractDAO;
//import com.star.Ba.dao.IOptionsSimulationDAO;
//import com.star.Ba.model.OptionsSimulation;
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
///**
// *
// * @author star
// */
//@Stateless
//public class OptionsSimulationDAO extends AbstractDAO<OptionsSimulation> implements IOptionsSimulationDAO {
//    @PersistenceContext(unitName = "oracle")
//    private EntityManager em;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }
//
//    public OptionsSimulationDAO() {
//        super(OptionsSimulation.class);
//    }
//    
//}
