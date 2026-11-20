package com.star.Ba.parametres;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.star.Ba.dao.IAppConfigDAO;
import com.star.Ba.jsf.util.FtpConfig;
import com.star.Ba.model.AppConfig;

import javax.ejb.EJB;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Singleton
@Startup
public class AppConfigService {

    @EJB
    private IAppConfigDAO appConfigDAO;

    private Map<String, String> cache = new HashMap<String, String>();
    
    private List<FtpConfig> ftpConfigs; // cache local
    
    

    @PostConstruct
    public void init() {
        loadAll();
        System.out.println(">>> init() dans  AppConfigService <<<");
    }

    public synchronized void loadAll() {
        cache.clear();

        List<AppConfig> list = appConfigDAO.findAll();

        for (int i = 0; i < list.size(); i++) {
            AppConfig c = list.get(i);
            cache.put(c.getCode(), c.getValue());
        }
    }

    public String get(String key) {
        return cache.get(key);
    }

    public Boolean getBoolean(String key) {
        String val = cache.get(key);

        if (val == null) return false;

        return "TRUE".equalsIgnoreCase(val) || "1".equals(val);
    }

    public Integer getInt(String key) {
        String val = cache.get(key);

        try {
            return val != null ? Integer.parseInt(val) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void refresh() {
        loadAll();
    }
    
    public synchronized void refreshWithLog() {
        loadAll();
        loadFtpConfigs();
        System.out.println(">>> CACHE CONFIG RECHARGE <<<");
    }
    
    
    // =========================
    // Partie FTP Configs
    // =========================


    public synchronized List<FtpConfig> getFtpConfigs() {
//        if (ftpConfigs == null) {
            loadFtpConfigs();
//        }
        return ftpConfigs;
    }

    private void loadFtpConfigs() {
        ftpConfigs = new ArrayList<FtpConfig>();

        ftpConfigs.add(new FtpConfig(
            "002707PPM000",
            get("FTP.STB.SERVER"),
            getInt("FTP.STB.PORT"),
            get("FTP.STB.USER"),
            get("FTP.STB.PASS"),
            get("FTP.STB.FOLDER")
        ));

        ftpConfigs.add(new FtpConfig(
            "022671FPM000",
            get("FTP.QNB.SERVER"),
            getInt("FTP.QNB.PORT"),
            get("FTP.QNB.USER"),
            get("FTP.QNB.PASS"),
            get("FTP.QNB.FOLDER")
        ));
    }

    public synchronized void refreshFtpConfigs() {
        loadFtpConfigs();
        System.out.println(">>> FTP configs rechargées <<<");
    }

    public FtpConfig getFtpBySociete(String codeSociete) {
        for (FtpConfig cfg : getFtpConfigs()) {
            if (cfg.getCodeSociete().equals(codeSociete)) {
                return cfg;
            }
        }
        return null;
    }
}