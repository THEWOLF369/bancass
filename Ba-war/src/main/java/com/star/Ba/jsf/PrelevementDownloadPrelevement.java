package com.star.Ba.jsf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.star.Ba.commun.Constante;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.jsf.util.FtpConfig;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.model.Societe;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.parametres.AppConfigService;

@ManagedBean( name = "prelevementDownloadPrelevement")
@ViewScoped
public class PrelevementDownloadPrelevement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
  

//	    private final String ftpServer = "192.168.204.91";
//	    private final int ftpPort = 21;
//	    private final String ftpUser = "groupe";
//	    private final String ftpPass = "groupe";

	    private List<String> ftpFiles;
	    private List<String> filteredFiles;
	    
	    private Map<String, Object> listSociete=null;
	    
		@EJB
		private ISocieteDAO iSocieteDAO;
		
		private String codeSociete;
		
	    @ManagedProperty(value = "#{login.utilisateur}")
	    private Utilisateurs utilisateur;
	    
	    
	    private List<FtpConfig> configs;
	    private FtpConfig selectedConfig;
	    
//	    private Date selectedDate;
	    
	    @EJB
	    private AppConfigService configService; 

	    @PostConstruct
	    public void init() {
	    	System.out.println("Init() appelé une fois au chargement de la page");
	        
	        
	        initFtpConfigs(); // Assure-toi que la liste est remplie
	        
	        listSociete = recupererListeSociete();
			
			 if(utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name())) {
				 codeSociete = (String) listSociete.values().toArray()[0];
		        	
		        } else {
		        	codeSociete = utilisateur.getAgence().getSociete().getCodSociet();
		        	
		     }
			 
		        System.out.println("codeSociete " + codeSociete);
		        
		        initFtpConfigParSociete(codeSociete);
			
	    }
	    
	    
	    
	    private void initFtpConfigs() {
	        configs = new ArrayList<>();
	        configs = configService.getFtpConfigs();
	        /*
	        configs.add(new FtpConfig(
	        		"002707PPM000", Constante.ftpServer_stb, Integer.parseInt(Constante.ftpPort_stb), 
	        		Constante.ftpUser_stb, Constante.ftpPass_stb, Constante.ftpFolder_stb));
	        configs.add(new FtpConfig(
	        		"022671FPM000", Constante.ftpServer_qnb, Integer.parseInt(Constante.ftpPort_qnb), 
	        		Constante.ftpUser_qnb, Constante.ftpPass_qnb, Constante.ftpFolder_qnb));
	        		*/
	    }
	    
	    public void initFtpConfigParSociete(String societeConnectee) {
	    	
	    	System.out.println("debut initFtpConfigParSociete et société est : " + societeConnectee);
	    	selectedConfig = null;

	        for (FtpConfig config : configs) {
	            if (config.getCodeSociete().equalsIgnoreCase(societeConnectee)) {
	                selectedConfig = config;
	                break;
	            }
	        }

	        if (selectedConfig == null) {
	            System.out.println("Aucune configuration FTP trouvée pour la société : " + societeConnectee);
	            ftpFiles = new ArrayList<String>();
	            ftpFiles.clear();
	        } else {
	            System.out.println("Config FTP chargée pour : " + selectedConfig.getCodeSociete());
	            
	            loadFtpFileList();
	        }
	    }
	    

	    public void loadFtpFileList() {
	    	
	    	System.out.println("loadFtpFileList() appelé ");
	    	
	    	
	        if(!utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name())) {
	        	codeSociete = utilisateur.getAgence().getSociete().getCodSociet();
	        }
	        System.out.println("codeSociete apres loadFtpFileList " + codeSociete);
	    	
	        ftpFiles = new ArrayList<String>();
	        FTPClient ftpClient = new FTPClient();
	        ftpFiles.clear();

	        try {
	            ftpClient.connect(selectedConfig.getFtpServer(), selectedConfig.getFtpPort());
	            if (!ftpClient.login(selectedConfig.getFtpUser(), selectedConfig.getFtpPass())) {
	                addMessage("FTP", "Échec de connexion FTP", FacesMessage.SEVERITY_ERROR);
	                return;
	            }

	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
/** **********************  cas date	            
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  		  
	            String dateFolder = sdf.format(selectedDate); // par exemple "20250430"
	            String remoteDir = selectedConfig.getFtpFolder() + "/PRELV/" + dateFolder; 
*/
	            
	            String remoteDir = selectedConfig.getFtpFolder()+ "/PRELV/";

	            String[] fileNames = ftpClient.listNames(remoteDir);
	            if (fileNames != null) {
	                for (int i = 0; i < fileNames.length; i++) {
	                    String name = fileNames[i];
	                    if (name != null && !name.trim().isEmpty()) {
	                        // Supprime le chemin si présent
	                        name = name.substring(name.lastIndexOf("/") + 1);
	                        ftpFiles.add(name);
	                    }
	                }
	            }

	            ftpClient.logout();
	        } catch (IOException e) {
	        	e.printStackTrace();
	            addMessage("Erreur FTP", "IOException : " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
	        }catch (Exception e) {
	        	e.printStackTrace();
	            addMessage("FTP", "Erreur : " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
	        } finally {
	            if (ftpClient.isConnected()) {
	                try {
	                    ftpClient.disconnect();
	                } catch (IOException ignored) {
	                }
	            }
	        }
	    }

	    public void downloadFile(String filename) {
	        FTPClient ftpClient = new FTPClient();
	        try {
	            ftpClient.connect(selectedConfig.getFtpServer(), selectedConfig.getFtpPort());
	            if (!ftpClient.login(selectedConfig.getFtpUser(), selectedConfig.getFtpPass())) {
	                addMessage("FTP", "Connexion FTP échouée", FacesMessage.SEVERITY_ERROR);
	                return;
	            }

	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
/******************* cas date***********	            
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  		  
	            String dateFolder = sdf.format(selectedDate); // par exemple "20250430"
	            String remoteDir = selectedConfig.getFtpFolder() + "/PRELV/" + dateFolder; 
	            String remoteFile = remoteDir+"/" + filename;
*/
	            String remoteFile = selectedConfig.getFtpFolder()+"/PRELV/" + filename;

	            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile);

	            if (inputStream == null) {
	                addMessage("FTP", "Fichier introuvable sur le serveur", FacesMessage.SEVERITY_WARN);
	                return;
	            }

	            FacesContext fc = FacesContext.getCurrentInstance();
	            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	            response.reset();
	            response.setContentType("application/octet-stream");
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

	            OutputStream output = response.getOutputStream();
	            byte[] buffer = new byte[4096];
	            int bytesRead;

	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                output.write(buffer, 0, bytesRead);
	            }

	            inputStream.close();
	            output.flush();
	            ftpClient.completePendingCommand();
	            ftpClient.logout();

	            fc.responseComplete();

	        } catch (Exception e) {
	        	e.printStackTrace();
	            addMessage("FTP", "Erreur téléchargement : " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
	        } finally {
	            try {
	            	if (ftpClient.isConnected()) ftpClient.disconnect();
	            } catch (IOException ignored) {
	            }
	        }
	    }
	    
	    
	    public void deleteFile(String filename) {
	        FTPClient ftpClient = new FTPClient();

	        try {
	            ftpClient.connect(selectedConfig.getFtpServer(), selectedConfig.getFtpPort());

	            int replyCode = ftpClient.getReplyCode();
	            if (!FTPReply.isPositiveCompletion(replyCode)) {
	                addMessage("Erreur FTP", "Réponse FTP invalide après connexion : " + replyCode, FacesMessage.SEVERITY_ERROR);
	                return;
	            }

	            boolean loginSuccess = ftpClient.login(selectedConfig.getFtpUser(), selectedConfig.getFtpPass());
	            if (!loginSuccess) {
	                addMessage("Erreur FTP", "Échec de l'authentification FTP", FacesMessage.SEVERITY_ERROR);
	                return;
	            }

	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
/*	            
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  		  
	            String dateFolder = sdf.format(selectedDate); // par exemple "20250430"
	            String remoteDir = selectedConfig.getFtpFolder() + "/PRELV/" + dateFolder; 

	            String remoteFile = remoteDir+"/" + filename;
*/	            
	            String remoteFile = selectedConfig.getFtpFolder()+"/PRELV/" + filename;

	            // Vérifie si le fichier existe
	            FTPFile[] files = ftpClient.listFiles(remoteFile);
	            if (files.length == 0) {
	                addMessage("Erreur", "Fichier introuvable sur le serveur FTP : " + filename, FacesMessage.SEVERITY_WARN);
	                return;
	            }
	            for (FTPFile file : files) {
	                System.out.println("Fichier dans le répertoire : " + file.getName());
	            }

	            // Tente la suppression
	            boolean deleted = ftpClient.deleteFile(remoteFile);
	            if (deleted) {
	                addMessage("Succès", "Fichier supprimé : " + filename, FacesMessage.SEVERITY_INFO);
	                ftpFiles.remove(filename); // Mise à jour de la liste côté JSF
	            } else {
//	                addMessage("Erreur", "Impossible de supprimer le fichier : " + filename, FacesMessage.SEVERITY_ERROR);
	            	replyCode = ftpClient.getReplyCode();
	                String replyMsg = ftpClient.getReplyString();
	                addMessage("Erreur",
	                    "Échec suppression : code " + replyCode + " - " + replyMsg,
	                    FacesMessage.SEVERITY_ERROR);
	            }

	            ftpClient.logout();
	        } catch (SocketException e) {
	        	e.printStackTrace();
	            addMessage("Erreur Réseau", "Problème de connexion au serveur FTP : " + e.getMessage(), FacesMessage.SEVERITY_FATAL);
	        } catch (IOException e) {
	        	e.printStackTrace();
	            addMessage("Erreur IO", "Erreur lors des opérations FTP : " + e.getMessage(), FacesMessage.SEVERITY_FATAL);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            addMessage("Erreur Générale", "Une erreur inattendue s'est produite : " + e.getMessage(), FacesMessage.SEVERITY_FATAL);
	        } finally {
	            if (ftpClient.isConnected()) {
	                try {
	                    ftpClient.disconnect();
	                } catch (IOException ignored) {
	                }
	            }
	        }
	    }


	    public List<String> getFtpFiles() {
	        return ftpFiles;
	    }

	    public List<String> getFilteredFiles() {
	        return filteredFiles;
	    }

	    public void setFilteredFiles(List<String> filteredFiles) {
	        this.filteredFiles = filteredFiles;
	    }

	    private void addMessage(String summary, String detail, FacesMessage.Severity severity) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	    }
	    
	    
		public Map<String, Object> recupererListeSociete() {
			Map<String, Object> listsociete = new LinkedHashMap<String, Object>();
			List<Societe> list =null;

			list =  iSocieteDAO.listeSociete();

			for(Societe societe : list){
				listsociete.put( societe.getRaisonSoc(),societe.getCodSociet()) ;
			}

			return listsociete;
		}
	    
		public void onSocieteChange() {
		    System.out.println("Code société sélectionné : " + codeSociete);
		    initFtpConfigParSociete(codeSociete);
		}
/*		
		public void saveSelectedDate() {
		    // Cette méthode est appelée via p:remoteCommand
		    System.out.println("Date sélectionnée enregistrée : " + selectedDate);
		}
*/	    
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
		
	    public Utilisateurs getUtilisateur() {
	        return utilisateur;
	    }

	    public void setUtilisateur(Utilisateurs utilisateur) {
	        this.utilisateur = utilisateur;
	    }

	    
//	    public Date getSelectedDate() {
//	        return selectedDate;
//	    }
//
//	    public void setSelectedDate(Date selectedDate) {
//	        this.selectedDate = selectedDate;
//	    }

}
