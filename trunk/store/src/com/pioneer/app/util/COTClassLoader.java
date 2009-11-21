package com.pioneer.app.util;

/**
 * <p/> Title: ��װ����
 * </p>
 * <p/> Description:
 * </p>
 * <p/> Copyright: 2005-6-11
 * </p>
 * <p/> Company: MDCL-Frontline
 * </p>
 * 
 * @author zhangping zhangping@sc.mdcl.com.cn
 * @version Mocha ITAM5.0
 */
public class COTClassLoader extends ClassLoader {

	/**
	 * ���캯��
	 */
	public COTClassLoader() {
		super();
	}

	/**
	 * ��õ�ǰ����װ����
	 * 
	 * @return
	 */
	public static ClassLoader getAppClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * ��������ϸ����
	 * 
	 * @return
	 */
	public static ClassLoader getLocalClassLoader() {
		return localClassLoader;
	}

	/**
	 * ��������ϸ����
	 * 
	 * @param _classLoader
	 */
	public static void setLocalClassLoader(ClassLoader _classLoader) {
		localClassLoader = _classLoader;
	}

	/**
	 * �����Ա��* <code>localClassLoader</code> ��ע��
	 */
	/**
	 * <code>localClassLoader</code> ��ע��
	 */
	private static ClassLoader localClassLoader = new COTClassLoader();

}// END CLASS OF BPFClassLoader
