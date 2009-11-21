package com.pioneer.app.comm;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.pioneer.app.util.DatCache;

public class I18Message {

	private String langType = "zh-cn";

	private Properties JSPPro = null;

	private String contextRoot = null;

	private I18Message() {
		setLangType(null);
	}

	private I18Message(String langtype) {
		if (null != langtype)
			this.langType = langtype.toLowerCase();
		this.setLangType(langtype.toLowerCase());
	}

	public String getLangType() {
		return langType;
	}

	public void setLangType(String langType) {
		String currentJSPFILE = null;
		if (null == langType) {
			StringBuffer bf = new StringBuffer(60);
			bf.append(Locale.getDefault().getLanguage()).append("_").append(
					Locale.getDefault().getCountry());
			langType = bf.toString().toLowerCase();
		}
		this.langType = langType.toLowerCase();
		// �ӵ�ǰcache��ȡ����ʻ��ļ�.
		String currentJSPKey = "JSPPro" + this.langType;

		// get the fileProperties from file
		try {
			JSPPro = (Properties) DatCache.inst().getDat(currentJSPKey);
			if (null == JSPPro) {
				currentJSPFILE = ApplicationPathMg.getInstance().getLangDir()
						+ "application_" + this.langType + ".properties";
				File file = new File(currentJSPFILE);
				if (!file.exists()) {
					throw new Exception();
				}
				JSPPro = getProsObjFromFile(file);
				DatCache.inst().setDat(currentJSPKey, JSPPro);
			}
		} catch (Exception e) {
			try {
				JSPPro = (Properties) DatCache.inst().getDat("JSPPro");
				if (null == JSPPro) {
					currentJSPFILE = ApplicationPathMg.getInstance()
							.getLangDir()
							+ "application.properties";
					JSPPro = getProsObjFromFile(currentJSPFILE);
					DatCache.inst().setDat("JSPPro", JSPPro);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}

	public String getJSPMessage(String key) {
		if (null != key) {
			return this.JSPPro.getProperty(key);
		}
		return null;
	}

	public String getImagePath(String imageName) {
		if (null != imageName) {
			StringBuffer bf = new StringBuffer(60);
			bf.append(contextRoot);
			bf.append("/images/").append(this.langType).append("/").append(
					imageName);
			return bf.toString();
		}
		return null;
	}

	public String getImagePath() {
		StringBuffer bf = new StringBuffer(60);
		if (null != this.contextRoot)
			bf.append(this.contextRoot).append("/images/")
					.append(this.langType).append("/");
		return bf.toString();
	}

	public String getCommImagePath(String imageName) {
		if (null != imageName) {
			StringBuffer bf = new StringBuffer(60);
			bf.append("images/comm").append("/").append(imageName);
			return bf.toString();
		}
		return null;
	}

	public String getCommImagePath() {
		StringBuffer bf = new StringBuffer(60);
		bf.append(contextRoot);
		bf.append("/images/comm/");
		return bf.toString();
	}

	private Properties getProsObjFromFile(String file) throws Exception {
		Properties pros = null;
		if (null != file) {
			pros = new Properties();
			pros.load(new FileInputStream(file));
		}
		return pros;
	}

	private Properties getProsObjFromFile(File file) throws Exception {
		Properties pros = null;
		if (null != file) {
			pros = new Properties();
			pros.load(new FileInputStream(file));
		}
		return pros;
	}

	public static I18Message getInstance(HttpServletRequest request) {
		I18Message message = null;
		if (null != request) {
			message = (I18Message) request.getSession().getAttribute(
					"messageObj");
			if (message == null) {
				String langType = request.getHeader("Accept-Language");// =Locale.getDefault().getLanguage()+"_"+Locale.getDefault().getCountry();
				message = new I18Message(langType);
				request.getSession().setAttribute("messageObj", message);
			}
			if (null == message.getContextRoot()) {
				message.setContextRoot(request.getContextPath());
			}
		} else {
			message = _instance;
		}

		return message;
	}

	private static I18Message _instance = new I18Message("zh-cn");

	public static void main(String[] args) {
		// String message=I18Message.getInstance().getJSPMessage("test");
		// System.out.println("message=");
	}

	public String getContextRoot() {
		return contextRoot;
	}

	public void setContextRoot(String contextRoot) {
		this.contextRoot = contextRoot;
	}
}
