package com.pioneer.app.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pioneer.app.exception.FactoryException;
import com.pioneer.app.util.ClassHelper;
import com.pioneer.frame.dao.IDAO;

public class DAOFactory {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DAOFactory.class);

	private Map daoInstances = null;

	private Map voInstances = null;

	public DAOFactory() {
		super();
		// ����ʹ��
		regDAOForOracle();
	}

	public void init(String type) {
		if (logger.isDebugEnabled()) {
			logger.debug("init(String) - start");
		}

		daoInstances = new HashMap();
		voInstances = new HashMap();
		if (type == null) {

		} else if ("oracle".equals(type)) {
			regDAOForOracle();
		} else if ("db2".equals(type)) {
			regDAOForDB2();
		} else if ("sqlserver".equals(type)) {
			regDAOForSQLServer();
		} else if ("mysql".equals(type)) {
			regDAOForMysql();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("init(String) - end");
		}
	}

	/**
	 * @desc: :
	 * @auther : pionner
	 * @date : 2007-9-3
	 */
	private void regDAOForOracle() {
		if (logger.isDebugEnabled()) {
			logger.debug("regDAOForOracle() - start");
		}

		if (null == this.daoInstances)
			daoInstances = new HashMap();
		
		//用户dao
		//this.daoInstances.put("IUserDAO",UserDAOImpl.class);


		if (logger.isDebugEnabled()) {
			logger.debug("regDAOForOracle() - end");
		}
	}

	private void regDAOForDB2() {

	}

	private void regDAOForSQLServer() {

	}

	private void regDAOForMysql() {

	}

	private void regVO() {
	}

	public Object getDAO(String key, Connection conn) throws FactoryException {
		if (logger.isDebugEnabled()) {
			logger.debug("getDAO(String, Connection) - start");
		}

		IDAO dao = null;

		try {
			if (null != key) {
				Class cls = (Class) this.daoInstances.get(key);
				if (null != cls) {
					dao = (IDAO) ClassHelper.newInstance(cls.getName());
					if (null != conn && !conn.isClosed())
						dao.setConnection(conn);
				}
			}
		} catch (Exception e) {
			logger.error("getDAO(String, Connection)", e);

			FactoryException fe = new FactoryException(e);
			throw fe;
		}
		if (null == dao) {
			FactoryException fe = new FactoryException("申请的ｄａｏ没有注册到系统中�?");
			throw fe;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDAO(String, Connection) - end");
		}
		return dao;
	}

	public Object getVO(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("getVO(String) - start");
		}

		if (null != key) {
			Class cls = (Class) this.voInstances.get(key);
			if (null != cls) {
				Object returnObject = ClassHelper.newInstance(cls.getName());
				if (logger.isDebugEnabled()) {
					logger.debug("getVO(String) - end");
				}
				return returnObject;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getVO(String) - end");
		}
		return null;
	}

	public static DAOFactory getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - end");
		}
		return instance;
	}

	private static DAOFactory instance = new DAOFactory();

	/**
	 * @param args :
	 * @auther : pionner
	 * @date : 2007-3-28
	 * @desc:
	 */
	public static void main(String[] args) {
		// ITWtMessageDAO
		// messagedao=(ITWtMessageDAO)DAOFactory.getInstance().getDAO("ITWtMessageDAO");
		// System.out.println("44444444"+messagedao);

	}

}
