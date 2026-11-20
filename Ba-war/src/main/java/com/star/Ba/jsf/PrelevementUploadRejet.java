package com.star.Ba.jsf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.star.Ba.commun.Constante;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.jsf.util.FtpConfig;
import com.star.Ba.jsf.util.PofileEnum;
import com.star.Ba.model.Societe;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.parametres.AppConfigService;

//@Named( "prelevementUploadFileBean")
//@SessionScoped
@ManagedBean(name = "prelevementUploadRejet")
@ViewScoped
//@RequestScoped
public class PrelevementUploadRejet implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
    private UploadedFile file;
    private int progress;
    private boolean progressVisible;
    
//    private Date selectedDate;
	
	
//	   private final String ftpServer  = "192.168.204.91";
//	    private final int ftpPort = 21;
//	    private final String ftpUser = "groupe";
//	    private final String ftpPass = "groupe";
	    
	    
	    private Map<String, Object> listSociete=null;
	    
		@EJB
		private ISocieteDAO iSocieteDAO;
		
		private String codeSociete;
		
	    @ManagedProperty(value = "#{login.utilisateur}")
	    private Utilisateurs utilisateur;
	    
	    private List<FtpConfig> configs;
	    private FtpConfig selectedConfig;
	    
	    
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
        } else {
            System.out.println("Config FTP chargée pour : " + selectedConfig.getCodeSociete());
            

        }
    }
    
	
	
	 public UploadedFile getFile() {
	        return file;
	    }

	    public void setFile(UploadedFile file) {
	        this.file = file;
	    }

	    public int getProgress() {
	        return progress;
	    }

	    public boolean isProgressVisible() {
	        return progressVisible;
	    }

	    /*
	    public void uploadToFTP() {
	        System.out.println("uploadToFTP() appelé ");
	        System.out.println("FILE = " + file);
	        System.out.println("FILENAME = " + (file != null ? file.getFileName() : "null"));

	        progressVisible = true;
	        progress = 0;

	        if (file == null || file.getFileName() == null || file.getFileName().isEmpty()) {
	            addMessage("Erreur", "Aucun fichier sélectionné", FacesMessage.SEVERITY_WARN);
	            return;
	        }

	        FTPClient ftpClient = new FTPClient();

	        try (InputStream input = file.getInputstream()) {
	            ftpClient.connect(ftpServer, ftpPort);
	            if (!ftpClient.login(ftpUser, ftpPass)) {
	                addMessage("Erreur FTP", "Échec de connexion FTP", FacesMessage.SEVERITY_ERROR);
	                return;
	            }

	            ftpClient.enterLocalPassiveMode();
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

	            // Lecture manuelle du fichier
	            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	            byte[] temp = new byte[4096];
	            int bytesRead;
	            while ((bytesRead = input.read(temp)) != -1) {
	                buffer.write(temp, 0, bytesRead);
	            }
	            byte[] fileData = buffer.toByteArray();

	            InputStream ftpInput = new ByteArrayInputStream(fileData);
	            OutputStream outputStream = ftpClient.storeFileStream("/uploads/" + file.getFileName());

	            int totalBytes = fileData.length;
	            int uploadedBytes = 0;
	            int chunkSize = 2048;

	            while (uploadedBytes < totalBytes) {
	                int remaining = totalBytes - uploadedBytes;
	                int currentChunk = Math.min(chunkSize, remaining);
	                outputStream.write(fileData, uploadedBytes, currentChunk);
	                uploadedBytes += currentChunk;

	                progress = (int) ((uploadedBytes * 100.0f) / totalBytes);
	                TimeUnit.MILLISECONDS.sleep(100); // pour simuler la progression
	            }

	            outputStream.flush();
	            outputStream.close();
	            ftpInput.close();

	            if (ftpClient.completePendingCommand()) {
	                addMessage("Succès", "Fichier uploadé avec succès", FacesMessage.SEVERITY_INFO);
	            } else {
	                addMessage("Erreur FTP", "Erreur lors de la finalisation de l’upload", FacesMessage.SEVERITY_ERROR);
	            }

	            ftpClient.logout();

	        } catch (Exception e) {
	            addMessage("Erreur", e.getMessage(), FacesMessage.SEVERITY_ERROR);
	        } finally {
	            try {
	                ftpClient.disconnect();
	            } catch (IOException ignored) {}
	        }

	        progress = 100;
	    }
 */
	    
	    
	    public void handleFileUpload(FileUploadEvent event) {
	        UploadedFile file = event.getFile();
	        System.out.println("handleFileUpload() appelé");
//	        System.out.println("FILE = " + file);
//	        System.out.println("FILENAME = " + (file != null ? file.getFileName() : "null"));
	        
	        if(!utilisateur.getProfil().getCode().equals(PofileEnum.ASI.name())) {
	        	codeSociete = utilisateur.getAgence().getSociete().getCodSociet();
	        }
	        System.out.println("codeSociete " + codeSociete);
	        
/********************	   cas avec saisir une date   
            if (selectedDate == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "La date est manquante ou invalide."));
                return;
            }
            
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String dateFolder = sdf.format(selectedDate); // ici selectedDate est bien un Date
                // ... suite du traitement FTP
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Erreur lors du traitement de la date."));
            }
*/
 

	        FTPClient ftpClient = new FTPClient();
	        
	        // Lire tout le contenu en binaire (pour éviter les soucis de stream corrompu)
	        byte[] fileBytes = file.getContents(); // méthode valide en PrimeFaces 6.0
	        try {
	        // 🔧 Convertir CRLF (\r\n) en LF (\n) si nécessaire
	        String content = new String(fileBytes, "UTF-8");
	        if (content.contains("\r\n")) {
	            System.out.println("Conversion CRLF vers LF...");
	            content = content.replace("\r\n", "\n");
	            fileBytes = content.getBytes("UTF-8");
	        }
	        }catch (Exception e) {
				// TODO: handle exception
	        	e.printStackTrace();
	        	addMessage("Erreur", "Exception : " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
	        	return;
			}

	        try (/*InputStream input = file.getInputstream()*/InputStream input = new ByteArrayInputStream(fileBytes)) {
	            // Connexion au serveur FTP
	            try {
	                ftpClient.connect(selectedConfig.getFtpServer(), selectedConfig.getFtpPort());
	                
	                int replyCode = ftpClient.getReplyCode();
	                if (!FTPReply.isPositiveCompletion(replyCode)) {
	                    addMessage("Erreur", "FTP non disponible. Code: " + replyCode, FacesMessage.SEVERITY_ERROR);
	                    return;
	                }
	                
	                if (!ftpClient.login(selectedConfig.getFtpUser(), selectedConfig.getFtpPass())) {
	                    addMessage("Erreur FTP", "Échec de la connexion FTP", FacesMessage.SEVERITY_ERROR);
	                    return;
	                }
	            } catch (IOException e) {
	            	e.printStackTrace();
	                handleFTPError(e, "Erreur lors de la connexion au serveur FTP");
	                return;
	            }

	            // Vérifier la réponse du serveur FTP après le login
	             
	                if (ftpClient.getReplyCode() != 230) {
	                    handleFTPError(null, "Échec de la connexion. Code de réponse : " + ftpClient.getReplyCode());
	                    return;
	                }
	            

	            // Passer en mode passif et définir le type de fichier
	            try {
	                ftpClient.enterLocalPassiveMode();
	                boolean setType = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	                System.out.println("FileType set to binary: " + setType);
	            } catch (IOException e) {
	            	e.printStackTrace();
	                handleFTPError(e, "Erreur lors de la configuration du mode passif ou du type de fichier");
	                return;
	            }

	            // Uploader le fichier sur le serveur FTP
//	            String remoteFile = selectedConfig.getFtpFolder()+"/REJET/" + file.getFileName();
	            
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	            String dateStr = sdf.format(new Date()); // ou selectedDate si tu veux une date saisie

	            String remoteFolder = selectedConfig.getFtpFolder() + "/REJET/";
	            String extension = ".txt";

	            int index = 1;
	            String newFileName;
	            try {
	                // Cherche un nom libre
	                do {
	                    newFileName = "rejet_" + dateStr + "_" + index + extension;
	                    FTPFile[] existingFiles = ftpClient.listFiles(remoteFolder + newFileName);
	                    if (existingFiles == null || existingFiles.length == 0) {
	                        break;
	                    }
	                    index++;
	                } while (true);
	            } catch (IOException e) {
	                e.printStackTrace();
	                handleFTPError(e, "Erreur lors de la vérification des fichiers existants sur le FTP");
	                return;
	            }

	            String remoteFile = remoteFolder + newFileName;



/********************	   cas avec saisir une date         
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  		  
	            String dateFolder = sdf.format(selectedDate); // par exemple "20250430"
	            String remoteDir = selectedConfig.getFtpFolder() + "/REJET/" + dateFolder;            
	         // Créer le dossier distant s'il n'existe pas (important avec Apache Commons Net)
	            try {
	                ftpClient.makeDirectory(remoteDir);
	            } catch (IOException e) {
	                e.printStackTrace(); // pas bloquant
	                handleFTPError(e, "Erreur lors de l'upload du fichier vers le serveur FTP");
	                return;
	            }

	            String remoteFile = remoteDir + "/" + file.getFileName();
*/	            
	            
	            boolean success = false;
	            try {
	                success = ftpClient.storeFile(remoteFile, input);
	            } catch (IOException e) {
	            	e.printStackTrace();
	                handleFTPError(e, "Erreur lors de l'upload du fichier vers le serveur FTP");
	                return;
	            }

	            // Vérification du succès de l'upload
	            if (success) {
	                addMessage("Succès", "Fichier envoyé avec succès sur le serveur FTP", FacesMessage.SEVERITY_INFO);
	            } else {
	                handleFTPError(null, "L'upload du fichier a échoué. Vérifiez les permissions du serveur ou le chemin de destination.");
	            }

	            // Déconnexion propre du FTP
	            try {
	                ftpClient.logout();
	            } catch (IOException e) {
	            	e.printStackTrace();
	                handleFTPError(e, "Erreur lors de la déconnexion du serveur FTP");
	                return;
	            }
	        } catch (IOException e) {
	            // Gérer toutes les erreurs d'entrée/sortie liées à la lecture du fichier
	        	e.printStackTrace();
	            addMessage("Erreur", "Une erreur est survenue lors de la lecture du fichier : " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
	            return;
	        } catch (Exception e) {
	            // Gérer toutes les autres exceptions non prévues
	        	e.printStackTrace();
	            addMessage("Erreur", "Une erreur imprévu est survenue : " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
	            return;
	        } finally {
	            // Tentative de déconnexion en cas d'erreur
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ignored) {}
	        }
	    }
	    
	    
	    public void handleFileUpload_(FileUploadEvent event) {
	        UploadedFile file = event.getFile();
	        FTPClient ftpClient = new FTPClient();

	        try {
	            // Lecture stricte des octets du fichier
	            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	            InputStream input = file.getInputstream();
	            byte[] data = new byte[4096];
	            int bytesRead;

	            while ((bytesRead = input.read(data)) != -1) {
	                buffer.write(data, 0, bytesRead);
	            }

	            input.close();
	            byte[] fileBytes = buffer.toByteArray();

	            // 💡 Diagnostic : affiche les 50 premiers octets en hexadécimal
	            System.out.println("DEBUG - PREMIERS OCTETS :");
	            for (int i = 0; i < Math.min(50, fileBytes.length); i++) {
	                System.out.printf("%02X ", fileBytes[i]);
	            }
	            System.out.println();

	            // Connexion FTP
	            ftpClient.connect(selectedConfig.getFtpServer(), selectedConfig.getFtpPort());
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // IMPORTANT : binaire
	            ftpClient.enterLocalPassiveMode();

	            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
	                addMessage("Erreur FTP", "Réponse du serveur incorrecte", FacesMessage.SEVERITY_ERROR);
	                return;
	            }

	            if (!ftpClient.login(selectedConfig.getFtpUser(), selectedConfig.getFtpPass())) {
	                addMessage("Erreur FTP", "Connexion échouée", FacesMessage.SEVERITY_ERROR);
	                return;
	            }

	            // Upload
	            String remoteFile = "interf/" + file.getFileName();
	            InputStream fileInputStream = new ByteArrayInputStream(fileBytes);
	            boolean success = ftpClient.storeFile(remoteFile, fileInputStream);
	            fileInputStream.close();

	            if (success) {
	                addMessage("Succès", "Fichier envoyé sans modification :)", FacesMessage.SEVERITY_INFO);
	            } else {
	                addMessage("Erreur", "Échec de l’envoi FTP", FacesMessage.SEVERITY_ERROR);
	            }

	            ftpClient.logout();

	        } catch (IOException e) {
	            addMessage("Erreur", "Exception : " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
	            e.printStackTrace();
	        } finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ignored) {}
	        }
	    }

	    

	    // Méthode pour ajouter des messages de manière centralisée
	    private void handleFTPError(Exception e, String message) {
	        if (e != null) {
	            addMessage("Erreur FTP", message + " - " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
	        } else {
	            addMessage("Erreur FTP", message, FacesMessage.SEVERITY_ERROR);
	        }
	    }

	    // Méthode pour afficher les messages dans l'interface utilisateur
	    private void addMessage(String title, String detail, FacesMessage.Severity severity) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, title, detail));
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
