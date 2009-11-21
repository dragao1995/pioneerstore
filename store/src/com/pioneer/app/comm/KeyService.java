package com.pioneer.app.comm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pioneer.app.db.DBConnectionManager;
import com.pioneer.app.exception.BaseException;
import com.pioneer.app.exception.FactoryException;
import com.pioneer.app.exception.ServiceException;

public class KeyService {

	public KeyService() {
		super();

	}

	/**
	 * @desc: 取到问题和答案的编号
	 * @return
	 * @throws BaseException :
	 * @auther : pionner
	 * @date : 2007-9-4
	 */
	public long getQAKey() throws BaseException {

		String sql = "select sequence_key.NEXTVAl as key from dual";
		Connection conn = null;
		try {
			conn = DBConnectionManager.getInstance().getConn();
			if (null != conn) {
				ResultSet rs = conn.createStatement().executeQuery(sql);
				if (rs.next()) {
					return rs.getLong("key");
				}
			} else {
				return -1;
			}
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		} finally {
			try {
				if (null != conn && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	/**
	 * @desc:取到附件的编号
	 * @return
	 * @throws BaseException :
	 * @auther : pionner
	 * @date : 2007-9-4
	 */
	public long getAttachKey() throws BaseException {
		Connection conn = null;
		String sql = "select sequence_attach.NEXTVAl as key from dual";
		try {
			conn = DBConnectionManager.getInstance().getConn();
			if (null != conn) {
				ResultSet rs = conn.createStatement().executeQuery(sql);
				if (rs.next()) {
					return rs.getLong("key");
				}
			} else {
				return -1;
			}
		} catch (FactoryException e) {
			throw e;
		} catch (SQLException e) {
			throw new ServiceException(e);
		} finally {
			try {
				if (null != conn && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public long getTypeKey() throws BaseException {
		Connection conn = null;
		String sql = "select SEQUENCE_TYPE.NEXTVAl as key from dual";
		try {
			conn = DBConnectionManager.getInstance().getConn();
			if (null != conn) {
				ResultSet rs = conn.createStatement().executeQuery(sql);
				if (rs.next()) {
					return rs.getLong("key");
				}
			} else {
				return -1;
			}
		} catch (FactoryException e) {
			throw e;
		} catch (SQLException e) {
			throw new ServiceException(e);
		} finally {
			try {
				if (null != conn && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public long getMessageKey() throws BaseException {
		Connection conn = null;
		String sql = "select SEQUENCE_USER.NEXTVAl as key from dual";
		try {
			conn = DBConnectionManager.getInstance().getConn();
			if (null != conn) {
				ResultSet rs = conn.createStatement().executeQuery(sql);
				if (rs.next()) {
					return rs.getLong("key");
				}
			} else {
				return -1;
			}
		} catch (FactoryException e) {
			throw e;
		} catch (SQLException e) {
			throw new ServiceException(e);
		} finally {
			try {
				if (null != conn && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public long getLogKey() throws BaseException {
		Connection conn = null;
		String sql = "select SEQUENCE_LOG.NEXTVAl as key from dual";
		try {
			conn = DBConnectionManager.getInstance().getConn();
			if (null != conn) {
				ResultSet rs = conn.createStatement().executeQuery(sql);
				if (rs.next()) {
					return rs.getLong("key");
				}
			} else {
				return -1;
			}
		} catch (FactoryException e) {
			throw e;
		} catch (SQLException e) {
			throw new ServiceException(e);
		} finally {
			try {
				if (null != conn && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	// sequence_log

	// sequence_attach

	public static KeyService getInst() {
		return _instant;
	}

	private static KeyService _instant = new KeyService();

	public static void main(String[] args) {
		long key = KeyService.getInst().getMessageKey();
		System.out.println("the key is =" + key);
	}

}
