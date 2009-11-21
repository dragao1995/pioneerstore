package com.pioneer.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.pioneer.app.exception.FactoryException;

/**
 * @name :
 * @version : v 1.0
 * 
 * <desc> description : </desc>
 * @package : datasource
 */
public final class DBConnectionManager {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DBConnectionManager.class);

	/**
	 * ���캯��
	 */
	private DBConnectionManager() {
		super();

		if (null == this._dbConfig) {
			//System.out.println("get the config");
			this._dbConfig = DBConnectionConfig.getInstance();
			//System.out.println(_dbConfig.toString());
		}
		//System.out.println("begin init the datasource");
		this.initDataSource();
	}

	/**
	 * ���ظ����Ψһһ��ʵ��ķ���
	 * 
	 * @return DBConnectionManager
	 */
	public static DBConnectionManager getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - end");
		}
		return DBConnectionManager._instance;
	}

	/**
	 * ���һ����ݿ�l�ӵķ���
	 * 
	 * @return IConnection
	 */
	public DBConnection getConnection() throws FactoryException {
		if (logger.isDebugEnabled()) {
			logger.debug("getConnection() - start");
		}
		DBConnection returnDBConnection=null;
		try {
		 returnDBConnection = new DBConnection(this._dataSource.getConnection());
		 if (logger.isDebugEnabled()) {
		 logger.debug("getConnection() - end");
		 }
		} catch (Exception e) {
			logger.error("getConnection()", e);
			e.printStackTrace();
			throw new FactoryException(e.toString());
		}
		 return returnDBConnection;
		
		/*Connection con = null;
		try {
			Context ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/cmcckm");
			con = ds.getConnection();
		} catch (Exception e) {
			logger.error("getConnection()", e);
			e.printStackTrace();
			throw new FactoryException(e.toString());
		}
		return new DBConnection(con);*/
	}

	public Connection getConn() throws FactoryException {
		if (logger.isDebugEnabled()) {
			logger.debug("getConn() - start");
		}

		try {

			Connection returnConnection = this._dataSource.getConnection();
			if (logger.isDebugEnabled()) {
				logger.debug("getConn() - end");
			}
			return returnConnection;
		} catch (SQLException e) {
			logger.error("getConn()", e);

			throw new FactoryException(e.getMessage());
		}
	}

	public Connection getRIMConn() throws FactoryException {
		if (logger.isDebugEnabled()) {
			logger.debug("getRIMConn() - start");
		}

		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			String url = "jdbc:oracle:thin:@10.1.28.60:1521:sidkanwu";
			String user = "infocenter";
			String password = "infocenter";
			conn = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			logger.error("getRIMConn()", e);

			throw new FactoryException(e.getMessage());
		} catch (InstantiationException e) {
			logger.error("getRIMConn()", e);

			throw new FactoryException(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("getRIMConn()", e);

			throw new FactoryException(e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error("getRIMConn()", e);

			throw new FactoryException(e.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getRIMConn() - end");
		}
		return conn;
	}

	/**
	 * ���һ����ݿ�l�ӵķ���
	 * 
	 * @return IConnection
	 */
	public DBConnection getConnection(String derect) throws FactoryException {
		if (logger.isDebugEnabled()) {
			logger.debug("getConnection(String) - start");
		}

		DBConnection t_conn = null;

		String url = this._dbConfig.getDb_url();
		String driver = this._dbConfig.getDriver_class_name();
		String user = this._dbConfig.getDb_user();
		String pass = this._dbConfig.getDb_password();
		try {
			Class.forName(driver);
			Connection conn = java.sql.DriverManager.getConnection(url, user,
					pass);
			t_conn = new DBConnection(conn);
		} catch (ClassNotFoundException e) {
			logger.error("getConnection(String)", e);

			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("getConnection(String)", e);

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getConnection(String) - end");
		}
		return t_conn;
	}

	/**
	 * ����ݿ�l�Ӳ�û�зŵ��߳��� �����κ��˲��ܵ���,�����������������
	 * 
	 * @return DBConnection
	 * @throws FactoryException
	 */
	public DBConnection getNoThreadConnection() throws FactoryException {
		if (logger.isDebugEnabled()) {
			logger.debug("getNoThreadConnection() - start");
		}

		DBConnection t_conn = null;
		if (null == t_conn || t_conn.isClosed()) {
			try {
				Class.forName(this._dbConfig.getDriver_class_name());
				t_conn = new DBConnection(DriverManager.getConnection(
						this._dbConfig.getDb_url(), this.getDbConfig()
								.getDb_user(), this.getDbConfig()
								.getDb_password()));
			} catch (Exception e) {
				logger.error("getNoThreadConnection()", e);

				throw new FactoryException(e);
			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNoThreadConnection() - end");
		}
		return t_conn;
	}

	/**
	 * �ر���ݿ�l�ӵķ���
	 * 
	 * @param conn -
	 *            ��ݿ�l��
	 */
	public void closeConnection(DBConnection conn) {
		if (logger.isDebugEnabled()) {
			logger.debug("closeConnection(DBConnection) - start");
		}

		if (null != conn) {
			try {
				if ((DBConnection) _threadLocal.get() == conn) {
					conn.close();
					conn = null;
					_threadLocal.set(null);
				} else {
					conn.close();

					conn = null;
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.debug("Closing DBConnection is error:" + e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("closeConnection(DBConnection) - end");
		}
	}

	/**
	 * �رյ�ǰ�̵߳���ݿ�l�ӵķ���
	 */
	public void closeConnection() {
		if (logger.isDebugEnabled()) {
			logger.debug("closeConnection() - start");
		}

		DBConnection t_conn = (DBConnection) _threadLocal.get();
		if (null != t_conn && !t_conn.isClosed()) {
			try {
				t_conn.close();
				_threadLocal.set(null);
			} catch (Exception e) {

				logger.debug("Closing DBConnection is error:" + e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("closeConnection() - end");
		}
	}

	/**
	 * ��õ�ǰ���l����ķ���
	 * 
	 * @return ����l����
	 */
	public int getCurrentActiveConnectionNum() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCurrentActiveConnectionNum() - start");
		}

		if (null != this._dataSource) {
			int returnint = this._dataSource.getNumActive();
			if (logger.isDebugEnabled()) {
				logger.debug("getCurrentActiveConnectionNum() - end");
			}
			return returnint;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCurrentActiveConnectionNum() - end");
		}
		return 0;
	}

	/**
	 * ��õ�ǰ���е�l����ķ���
	 * 
	 * @return ���е�l����
	 */
	public int getCurrentIdlConnectionNum() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCurrentIdlConnectionNum() - start");
		}

		if (null != this._dataSource) {
			int returnint = this._dataSource.getNumIdle();
			if (logger.isDebugEnabled()) {
				logger.debug("getCurrentIdlConnectionNum() - end");
			}
			return returnint;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCurrentIdlConnectionNum() - end");
		}
		return 0;
	}

	/**
	 * ������Ļl����ķ���
	 * 
	 * @return
	 */
	public int getMaxActiveConnectionNum() {
		if (logger.isDebugEnabled()) {
			logger.debug("getMaxActiveConnectionNum() - start");
		}

		if (null != this._dataSource) {
			int returnint = this._dataSource.getMaxActive();
			if (logger.isDebugEnabled()) {
				logger.debug("getMaxActiveConnectionNum() - end");
			}
			return returnint;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMaxActiveConnectionNum() - end");
		}
		return 0;
	}

	/**
	 * @return ���� dbConfig - ����ע�͡�
	 */
	public DBConnectionConfig getDbConfig() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDbConfig() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDbConfig() - end");
		}
		return this._dbConfig;
	}

	/**
	 * @param dbConfig
	 *            Ҫ���õ� dbConfig - ����ע�͡�
	 */
	public void setDbConfig(DBConnectionConfig dbConfig) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDbConfig(DBConnectionConfig) - start");
		}

		this._dbConfig = dbConfig;
		initDataSource();

		if (logger.isDebugEnabled()) {
			logger.debug("setDbConfig(DBConnectionConfig) - end");
		}
	}

	/**
	 * �ر���Դ�ķ���
	 * 
	 * @param rs -
	 *            ������
	 * @param ps -
	 *            ׼��������
	 * @param conn -
	 *            ��ݿ�l�Ӷ���
	 */
	public static void closeResource(ResultSet rs, PreparedStatement ps) {
		if (logger.isDebugEnabled()) {
			logger.debug("closeResource(ResultSet, PreparedStatement) - start");
		}

		if (null != rs) {
			try {
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				logger.debug("close ResultSet is error :" + e);
			}
		}

		if (null != ps) {
			try {
				ps.close();
			} catch (Exception e) {
				// TODO: handle exception
				logger.debug("Close PreparedStatement is error :" + e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("closeResource(ResultSet, PreparedStatement) - end");
		}
	}

	/**
	 * ��ʼ�����Դ�ķ���
	 */
	private synchronized void initDataSource() {
		if (logger.isDebugEnabled()) {
			logger.debug("initDataSource() - start");
		}

		System.out.println("init the datesource");
		if (null != this.getDbConfig()) {
			this._dataSource = new BasicDataSource();
			logger.debug("####################" + this._dataSource);
			this._dataSource.setDriverClassName(this.getDbConfig()
					.getDriver_class_name());
			// this._dataSource.setMaxActive(this.getDbConfig().getMaxActive());
			// this._dataSource.setMaxWait(this.getDbConfig().getMaxWait());
			this._dataSource.setUsername(this.getDbConfig().getDb_user());
			this._dataSource.setPassword(this.getDbConfig().getDb_password());
			this._dataSource.setUrl(this.getDbConfig().getDb_url());
			/**
			 * if (this.getDbConfig().isPoolPreparedStatements()) {
			 * this._dataSource.setPoolPreparedStatements(true);
			 * this._dataSource.setMaxOpenPreparedStatements(this
			 * .getDbConfig().getMaxOpenPreparedStatements()); }
			 */
		}

		if (logger.isDebugEnabled()) {
			logger.debug("initDataSource() - end");
		}
	}

	/* ����˽�г�Ա�� */
	/**
	 * <code>_instance</code> - �����Ψһһ��ʵ��
	 */
	private static DBConnectionManager _instance = new DBConnectionManager();

	/**
	 * <code>_threadLocal</code> - �̼߳���
	 */
	private static final ThreadLocal _threadLocal = new ThreadLocal();

	/**
	 * <code>_dbConfig</code> - ��ݿ�l��������
	 */
	private DBConnectionConfig _dbConfig = null;

	/**
	 * <code>_dataSource</code> - ���Դ
	 */
	private BasicDataSource _dataSource = null;

}// END CLASS OF DBConnectionManager
