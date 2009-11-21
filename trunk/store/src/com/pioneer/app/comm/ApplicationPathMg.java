package com.pioneer.app.comm;

public class ApplicationPathMg {
	private ApplicationPathMg() {

	}

	private String webRootPath = null;

	private String langDir = "lang/";

	private String imagesDir = "images/";


	public static ApplicationPathMg getInstance() {
		return _instance;
	}

	private static ApplicationPathMg _instance = new ApplicationPathMg();

	public String getWebRootPath() {
		return webRootPath;
	}

	public void setWebRootPath(String webRootPath) {
		this.webRootPath = webRootPath;
	}

	public String getLangDir() {
		return this.webRootPath + this.langDir;
	}

	public String getImagesDir() {
		return this.webRootPath + this.imagesDir;
	}

	
}
