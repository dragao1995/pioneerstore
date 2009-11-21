package com.pioneer.app.util;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

/**
 * <p/> Title: ��ʵ��
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
public class ClassHelper {

	/**
	 * ˽�й��캯��
	 */
	private ClassHelper() {
	}

	/**
	 * ������ķ���
	 * 
	 * @param className
	 * @return
	 */
	public static Class loadClass(String className) {
		try {
			if (null == className) {
				throw new ClassNotFoundException("className is null ! ");
			}
			return Class.forName(className, true, COTClassLoader
					.getAppClassLoader());

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * ʵ�����
	 * 
	 * @param className
	 * @return
	 */
	public static Object newInstance(String className) {
		try {
			return loadClass(className).newInstance();
		} catch (InstantiationException e) {
			logger.error(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * ʵ�����
	 * 
	 * @param className -
	 *            ����
	 * @param cls -
	 *            ��������
	 * @param obs -
	 *            �����ֵ
	 * @return
	 */
	public static Object newInstance(String className, Class[] cls,
			Object obs[]) {
		try {
			return loadClass(className).getConstructor(cls).newInstance(obs);
		} catch (InstantiationException e) {
			logger.error(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/** �����Ա��* */

	/**
	 * <code>logger</code> ��ע��
	 */
	private static Logger logger = Logger.getLogger(ClassHelper.class);

}