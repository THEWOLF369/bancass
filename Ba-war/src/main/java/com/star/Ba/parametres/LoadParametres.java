/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.parametres;

//import com.star.Ba.service.IParametresAutoService;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author star
 */
@ManagedBean
public class LoadParametres {

    @EJB
//    private IParametresAutoService iParametresService;
    
    private static LoadParametres INSTANCE;
    private Map<String, Object> packsAuto;

    private LoadParametres() {
    }

    public static  LoadParametres getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoadParametres();
        }
        return INSTANCE;
    }

//    public String getTypeClientByCode(String code) {
//        System.out.println("Code :"+code);
//        return iParametresService.getTypeClientByCode(code);
//    }
//
//    public Map<String, Object> getPacksAuto() {
//        packsAuto = iParametresService.getPacksAuto();
//        return packsAuto;
//    }
}
