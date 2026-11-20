package com.star.Ba.jsf.util;

public class FtpConfig {
	
    private String codeSociete;
    private String ftpServer;
    private int ftpPort;
    private String ftpUser;
    private String ftpPass;
    private String ftpFolder;
    
    
    
	public FtpConfig() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public FtpConfig(String codeSociete, String ftpServer, int ftpPort, String ftpUser, String ftpPass,
			String ftpFolder) {
		super();
		this.codeSociete = codeSociete;
		this.ftpServer = ftpServer;
		this.ftpPort = ftpPort;
		this.ftpUser = ftpUser;
		this.ftpPass = ftpPass;
		this.ftpFolder = ftpFolder;
	}




	public String getCodeSociete() {
		return codeSociete;
	}
	public void setCodeSociete(String codeSociete) {
		this.codeSociete = codeSociete;
	}
	public String getFtpServer() {
		return ftpServer;
	}
	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}
	public int getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpUser() {
		return ftpUser;
	}
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public String getFtpPass() {
		return ftpPass;
	}
	public void setFtpPass(String ftpPass) {
		this.ftpPass = ftpPass;
	}
	public String getFtpFolder() {
		return ftpFolder;
	}
	public void setFtpFolder(String ftpFolder) {
		this.ftpFolder = ftpFolder;
	}
    
    
    
    

}
