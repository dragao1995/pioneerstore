package com.pioneer.app.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.pioneer.app.comm.ApplicationPathMg;

public final class DBConnectionConfig implements Serializable {

	private static final Logger logger = Logger
			.getLogger(DBConnectionConfig.class);


	public static final int DEFAULT_MAXACTIVE = 5;

	public static final long DEFAULT_MAXWAIT = 200;


	public static final int DEFAULT_MAX_OPENPREPAREDSTATEMENTS = 5;

	private static final long serialVersionUID = 3861264310559318709L;

	private DBConnectionConfig() {
		super();
		//init(ApplicationPathMg.getInstance().getWebRootPath());
	}


	public String getDb_password() {
		return this._db_password;
	}


	public void setDb_password(String db_password) {
		this._db_password = db_password;
	}

	public String getDb_url() {
		return this._db_url;
	}


	public void setDb_url(String db_url) {
		this._db_url = db_url;
	}

	public String getDb_user() {
		return this._db_user;
	}


	public void setDb_user(String db_user) {
		this._db_user = db_user;
	}


	public String getDriver_class_name() {
		return this._driver_class_name;
	}

	public void setDriver_class_name(String driver_class_name) {
		this._driver_class_name = driver_class_name;
	}


	public boolean isPoolPreparedStatements() {
		return this._isPoolPreparedStatements;
	}


	public void setPoolPreparedStatements(boolean isPoolPreparedStatements) {
		this._isPoolPreparedStatements = isPoolPreparedStatements;
	}

	public int getMaxActive() {
		return this._maxActive;
	}


	public void setMaxActive(int maxActive) {
		this._maxActive = maxActive;
	}


	public int getMaxOpenPreparedStatements() {
		return this._maxOpenPreparedStatements;
	}

	public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
		this._maxOpenPreparedStatements = maxOpenPreparedStatements;
	}


	public long getMaxWait() {
		return this._maxWait;
	}


	public void setMaxWait(long maxWait) {
		this._maxWait = maxWait;
	}


	public boolean isValide() {
		if (null != this._db_url && !"".equals(this._db_url)
				&& null != this._db_user && !"".equals(this._db_user)
				&& null != this._driver_class_name
				&& !"".equals(this._driver_class_name)) {
			return true;
		}
		return false;
	}

	public static DBConnectionConfig getDefaultDBConfig() {

		DBConnectionConfig t_config = new DBConnectionConfig();
		t_config.setDb_url("jdbc:mysql://localhost:3306/store");
		t_config.setDriver_class_name("com.mysql.jdbc.Driver");
		t_config.setDb_user("root");
		t_config.setDb_password("");
		t_config.setMaxActive(DEFAULT_MAXACTIVE);
		t_config.setMaxWait(DEFAULT_MAXWAIT);
		t_config.setPoolPreparedStatements(true);
		t_config.setMaxOpenPreparedStatements(30);
		return t_config;
	}

	/**
	 * ��������ϸ���� ȡ�����Ψһһ��ʵ��
	 * 
	 * @return
	 */
	public static DBConnectionConfig getInstance() {
		if (null == dbconnectionConfig) {
			dbconnectionConfig = new DBConnectionConfig();
			if (null == dbconnectionConfig.getDb_url() || "".equals(dbconnectionConfig.getDb_url().trim())) {
				dbconnectionConfig= DBConnectionConfig.getDefaultDBConfig();
			}
		}
		return dbconnectionConfig;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer string = new StringBuffer();
		string.append("maxActive" + " db_user: " + this.getDb_user()
				+ "  db_password:  " + this.getDb_password() + "  url: "
				+ this.getDb_url().toString());

		return string.toString();
	}

	/**
	 * ��ʼ����ݿ�������Ϣ
	 */
	public void init(String webRoot) {
		if (logger.isDebugEnabled()) {
			logger.debug("init() - start");
		}
		String configFilePath = webRoot + "config/mysqldb.properties";
		logger.debug("############################configFilePath="
				+ configFilePath);
		if (null == dbconnectionConfig)
			dbconnectionConfig = new DBConnectionConfig();
		try {
			Properties pro = new Properties();
			File sourcefile = new File(configFilePath);
			if (!sourcefile.exists()) {
				sourcefile = new File(
						"E:/services/tomcat5.5/webapps/tcxhApp/config/mysqldb.properties");
			} else {
				pro.load(new FileInputStream(sourcefile));
				if (null != pro) {
					this.db_name=(String) pro.get("db_name");
					String t_url = (String) pro.get("db_url");
					String t_driver_class = (String) pro.get("db_driver_class");
					String t_user = (String) pro.get("db_user");
					String t_password = (String) pro.get("db_password");
					String t_maxActiveStr = (String) pro.get("db_maxActive");
					int t_maxActive;
					if (null != t_maxActiveStr) {
						t_maxActive = Integer.parseInt(t_maxActiveStr);
					} else {
						t_maxActive = DBConnectionConfig.DEFAULT_MAXACTIVE;
					}
					String t_maxWaiteStr = (String) pro.get("db_maxWait");
					long t_maxWaite;
					if (null != t_maxWaiteStr) {
						t_maxWaite = Integer.parseInt(t_maxWaiteStr);
					} else {
						t_maxWaite = DBConnectionConfig.DEFAULT_MAXWAIT;
					}

					boolean t_isPoolPreparedStatement;
					String t_isPoolPreparedStatementStr = (String) pro
							.get("isPoolPreparedStatements");
					if (null != t_isPoolPreparedStatementStr) {
						t_isPoolPreparedStatement = Boolean.valueOf(
								t_isPoolPreparedStatementStr).booleanValue();
					} else {
						t_isPoolPreparedStatement = false;
					}
					int t_maxOpenPreparedStatements;
					String t_maxOpenPreparedStatementsStr = (String) pro
							.get("maxOpenPreparedStatements");
					if (null != t_maxOpenPreparedStatementsStr) {
						t_maxOpenPreparedStatements = Integer
								.parseInt(t_maxOpenPreparedStatementsStr);
					} else {
						t_maxOpenPreparedStatements = DEFAULT_MAX_OPENPREPAREDSTATEMENTS;
					}
					dbconnectionConfig.setDb_name(db_name);
					dbconnectionConfig.setDb_url(t_url);
					dbconnectionConfig.setDriver_class_name(t_driver_class);
					dbconnectionConfig.setDb_user(t_user);
					dbconnectionConfig.setDb_password(t_password);
					dbconnectionConfig.setMaxActive(t_maxActive);
					dbconnectionConfig.setMaxWait(t_maxWaite);
					dbconnectionConfig
							.setPoolPreparedStatements(t_isPoolPreparedStatement);
					dbconnectionConfig
							.setMaxOpenPreparedStatements(t_maxOpenPreparedStatements);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("init()", e);
		}

		if (logger.isDebugEnabled()) {

			logger.debug("init() - end");
		}
	}

	// public Configuration getConfiguration() {
	// return ConfigFactory.getConfiguration();
	// }
	/* ����˽�г�Ա�� */

	/**
	 * <code>dbconnectionConfig</code> - ���Ψһһ��ʵ��
	 */
	private static DBConnectionConfig dbconnectionConfig = null;

	/**
	 * <code>_driver_class_name</code> - ��������
	 */
	private String _driver_class_name = null;

	private String db_name=null;
	
	/**
	 * <code>_db_url</code> - ��ݿ�l���ַ�
	 */
	private String _db_url = null;

	/**
	 * <code>_db_user</code> - ��ݿ���û�
	 */
	private String _db_user = null;

	/**
	 * <code>_db_password</code> - ��ݿ������
	 */
	private String _db_password = null;

	/**
	 * <code>_maxActive</code> - ���Ļt�ӵ���
	 */
	private int _maxActive = DBConnectionConfig.DEFAULT_MAXACTIVE;

	/**
	 * <code>_maxWait</code> - ��ĵȴ�ʱ��
	 */
	private long _maxWait = DBConnectionConfig.DEFAULT_MAXWAIT;

	/**
	 * <code>_isPoolPreparedStatements</code> - �Ƿ񻺳�׼��������
	 */
	private boolean _isPoolPreparedStatements = false;

	/**
	 * <code>_maxOpenPreparedStatements</code> -
	 * ��໺��׼��������,�����������ܾ���Ҫ�˲���
	 */
	private int _maxOpenPreparedStatements = 0;

	public String getDb_name() {
		return db_name;
	}


	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}

}// END CLASS OF DBConnectionConfig
