package com.pioneer.app.db;

import java.sql.Connection;

import java.sql.SQLException;

/**
 * @author chenjb
 * @company mdcl email chenjb@mdcl.com.cn Jul 2, 2006
 */
public final class DBConnection {

	/**
	 * 
	 * ���캯��
	 * 
	 */

	public DBConnection(Connection conn) {

		super();

		// TODO Auto-generated constructor stub

		this._conn = conn;

	}

	/**
	 * 
	 * ��������ķ���
	 * 
	 */

	public void beignTransaction() {

		if (null != this._conn) {

			try {

				this._conn.setAutoCommit(false);

			} catch (SQLException e) {

				// TODO: handle exception

			}

		}

	}

	/**
	 * 
	 * �ع�����ķ���
	 * 
	 */

	public void rollback() {

		if (null != this._conn) {

			try {

				this._conn.rollback();

				this._conn.setAutoCommit(true);

			} catch (SQLException e) {

				// TODO: handle exception

			}

		}

	}

	/**
	 * 
	 * �ύ����ķ���
	 * 
	 */

	public void commit() {

		if (null != this._conn) {

			try {

				this._conn.commit();

				this._conn.setAutoCommit(true);

			} catch (SQLException e) {

				// TODO: handle exception

			}

		}

	}

	/**
	 * 
	 * ��ݿ�l���Ƿ�رյķ���
	 * 
	 * @return true - �Ѿ��ر�,false - δ�ر�
	 * 
	 */

	public boolean isClosed() {

		if (null != this._conn) {

			try {

				return this._conn.isClosed();

			} catch (SQLException e) {

				// TODO: handle exception

			}

		}

		return false;

	}

	/**
	 * 
	 * ����Ƿ�֧�������µķ���
	 * 
	 * @return - true - ֧�� , false-��֧��
	 * 
	 */

	public boolean isSupportBatch() {

		try {

			if (null != this._conn && !this._conn.isClosed()) {

				return this._conn.getMetaData().supportsBatchUpdates();

			}

		} catch (Exception e) {

			// TODO: handle exception

		}

		return false;

	}

	/**
	 * 
	 * ����������ݿ�l�ӵķ���
	 * 
	 * @return Connection
	 * 
	 */

	public Connection getConnection() {

		return this._conn;

	}

	/**
	 * 
	 * �ر���ݿ�l�ӵķ���
	 * 
	 */

	protected void close() {

		try {

			if (null != this._conn && !this._conn.isClosed()) {

				this._conn.close();

			}

		} catch (SQLException e) {

			// TODO: handle exception

		}

	}

	/**
	 * 
	 * <code>_conn</code> - �������ݿ�l��
	 * 
	 */

	private Connection _conn = null;

}// END CLASS OF DBConnection

