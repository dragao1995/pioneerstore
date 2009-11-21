package com.pioneer.app.db;

import java.io.InputStream;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;


import com.pioneer.app.exception.DAOException;
import com.pioneer.app.exception.FactoryException;

public class OADBUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OADBUtil.class);

	public OADBUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static int[] runBatchSQL(List sqlList) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("runBatchSQL(List) - start");
		}

		int[] returnintArray = runBatchSQL(sqlList, 20);
		if (logger.isDebugEnabled()) {
			logger.debug("runBatchSQL(List) - end");
		}
		return returnintArray;
	}

	/**
	 * ����ִ�еķ�ʽ4���ж���SQL���?(��Ӧ�ð�Select ���?)
	 * 
	 * @param sqlList
	 *            ���Ҫȫ��ִ�е�SQL���?
	 * @param iBatCommitCount
	 *            ��С���ύ��SQL�������?
	 * @throws Exception
	 */
	public synchronized static int[] runBatchSQL(List sqlList,
			int iBatCommitCount) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("runBatchSQL(List, int) - start");
		}

		int[] ers = null;
		if (sqlList == null)
			throw new Exception("SQLListΪ��");
		Connection con = DBConnectionManager.getInstance().getConnection()
				.getConnection();
		boolean oldAutoCommit = true;

		try {
			oldAutoCommit = con.getAutoCommit();
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
			int iBatch = 0;
			ArrayList batSqlList = new ArrayList();

			for (Iterator iter = sqlList.iterator(); iter.hasNext();) {
				batSqlList.add(iter.next());
				if (++iBatch == iBatCommitCount) { // �ݶ�һ��ִ��20��.
					ers = _runBatchSqls(con, stmt, batSqlList);
					batSqlList.clear();
					iBatch = 0;
				}
			}
			if (iBatch != 0) {
				ers = _runBatchSqls(con, stmt, batSqlList);
				batSqlList.clear();
			}
			con.commit();
		} catch (Exception e) {
			logger.error("runBatchSQL(List, int)", e);

			e.printStackTrace();
			con.rollback();
			throw e;
		}

		finally {
			try {
				con.setAutoCommit(oldAutoCommit);
			} catch (SQLException e1) {
				logger.error("runBatchSQL(List, int)", e1);
			}
			con.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("runBatchSQL(List, int) - end");
		}
		return ers;
	}

	private static int[] _runBatchSqls(Connection con, Statement stmt,
			ArrayList batSqlList) throws Exception {
		if (logger.isDebugEnabled()) {
			logger
					.debug("_runBatchSqls(Connection, Statement, ArrayList) - start");
		}

		int[] ers = null;
		try {
			String sSql = null;
			for (Iterator iter = batSqlList.iterator(); iter.hasNext();) {
				sSql = (String) iter.next();
				sSql = sSql.toLowerCase();
				//System.out.println("sql="+sSql);
				stmt.addBatch(sSql);
			}
			ers = stmt.executeBatch();
		} catch (BatchUpdateException e) {
			logger.error("_runBatchSqls(Connection, Statement, ArrayList)", e);

			try {
				con.rollback();
				stmt.clearBatch();
			} catch (SQLException e1) {
				logger.error("_runBatchSqls(Connection, Statement, ArrayList)",
						e1);
			}
			String curSql = (String) batSqlList.get(e.getUpdateCounts().length);
			throw new Exception("ErrMsg=" + e.getMessage() + "\nErrSql="
					+ curSql);
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("_runBatchSqls(Connection, Statement, ArrayList) - end");
		}
		return ers;
	}

	/**
	 * @param sSql
	 *            ִ�в�ѯ��䡣
	 * @return
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-3-19
	 * @desc:
	 */
	public static ResultSet runQuerySql(String sSql, Connection conn)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("runQuerySql(String, Connection) - start");
		}

		ResultSet rs = null;
		if (null != sSql) {

			Statement stm = conn.createStatement();
			rs = stm.executeQuery(sSql);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("runQuerySql(String, Connection) - end");
		}
		return rs;
	}
public static List find(String sql,Connection con){
		boolean conflg=false;
		if(null==con)conflg=true;
		if(null==sql)return null;
		
		List rsList=new ArrayList();
		try{
					if(conflg)
						con=DBConnectionManager.getInstance().getConn();
					
	        java.sql.Statement stm=con.createStatement();
	        ResultSet rs=stm.executeQuery(sql);
	        ResultSetMetaData rsmd= rs.getMetaData();
	        int colNum=rsmd.getColumnCount();
	        Map row=null;
	        String colName=null;
	        while(rs.next()){
	        	row=new HashMap();
	        	for(int i=1;i<=colNum;i++){
	        		colName=rsmd.getColumnName(i);
	        		row.put(colName,rs.getString(colName));
	        	}
	        	rsList.add(row);
	        }
	        stm.close();
	        rs.close();
	        
	        //session.clear();
	        
		}catch(Exception e){
			logger.error(e);
		}finally{
			if(conflg)
				try{con.close();}catch(Exception e){}
		}
        return rsList;
	}

	/**
	 * @param sSql
	 *            带参数的查询
	 * @param pars
	 * @return
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-3-19
	 * @desc: �?:sql中的?�?定要和pars中的顺序�?�?
	 */
	public static ResultSet runQuerySql(String sSql, Object[] pars,
			Connection conn) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("runQuerySql(String, Object[], Connection) - start");
		}

		ResultSet rs = null;
		if (null != sSql) {

			PreparedStatement pstm = conn.prepareStatement(sSql);
			preparedParam(pars, pstm);
			rs = pstm.executeQuery();

		}

		if (logger.isDebugEnabled()) {
			logger.debug("runQuerySql(String, Object[], Connection) - end");
		}
		return rs;
	}

	/**
	 * @param sSql
	 * @param pars
	 * @return
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-3-19
	 * @desc: 执行带有参数的更新语�?.
	 */
	public static boolean runUpdateSql(String sSql, Object[] pars, Connection conn)
			throws Exception {
		boolean flg=false;
		
		ResultSet rs = null;
		if (null != sSql) {
			try {
				PreparedStatement pstm = conn.prepareStatement(sSql);
				if (pars != null)
					preparedParam(pars, pstm);
				  flg=pstm.execute();
			} catch (RuntimeException e) {
				logger.error("runUpdateSql(String, Object[], Connection)", e);

				throw new Exception(e.toString());
			} catch (Exception e) {
				logger.error("runUpdateSql(String, Object[], Connection)", e);

				throw new Exception(e.toString());
			} finally {
			}
		}

		return flg;
	}
	
	public static int[] insertBatch(List args,PreparedStatement ps) throws DAOException {
		
		int[] ret = null;
		try {
			if (null != ps) {
				if (null != args && args.size() > 0) {
					
//						ps = conn.prepareStatement(sql);
						for (int i = 0; i < args.size(); i++) {

							Object[] t_param = (Object[]) args.get(i);

							preparedParam(t_param, ps);

							ps.addBatch();

							ret = ps.executeBatch();

						}
					/*}else{// 驱动不支持批量插入
						ret = new int[args.size()];

						for (int i = 0; i < args.size(); i++) {

							Object[] t_param = (Object[]) args.get(i);

							//ret[i] = update(sql, t_param);

						}
					}*/
				}
				//测试是否需要关闭ps.
			}
			
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		
		return ret;
	}

	/**
	 * @param args
	 * @param prepStatement
	 * @throws SQLException :
	 * @auther : pionner
	 * @date : 2007-3-19
	 * @desc: 把参数�?�放到sql语句生成的PreparedStatement对象�?.
	 */
	private static void preparedParam(Object args[],
			PreparedStatement prepStatement) throws SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("preparedParam(Object[], PreparedStatement) - start");
		}

		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				Object param = args[i];
				if (param instanceof Integer) {
					int value = ((Integer) param).intValue();
					prepStatement.setInt(i + 1, value);
				} else if (param instanceof Short) {
					short sh = ((Short) param).shortValue();
					prepStatement.setShort(i + 1, sh);
				} else if (param instanceof String) {
					String s = (String) param;
					prepStatement.setString(i + 1, s);
				} else if (param instanceof Double) {
					double d = ((Double) param).doubleValue();
					prepStatement.setDouble(i + 1, d);
				} else if (param instanceof Float) {
					float f = ((Float) param).floatValue();
					prepStatement.setFloat(i + 1, f);
				} else if (param instanceof Long) {
					long l = ((Long) param).longValue();
					prepStatement.setFloat(i + 1, l);
				} else if (param instanceof Boolean) {
					boolean b = ((Boolean) param).booleanValue();
					prepStatement.setBoolean(i + 1, b);
				} else if (param instanceof Date) {
					prepStatement.setDate(i + 1, (Date) param);
				} else if (param instanceof Time) {
					prepStatement.setTime(i + 1, (Time) param);
				} else if (param instanceof Timestamp) {
					prepStatement.setTimestamp(i + 1, (Timestamp) param);
				} else if (param instanceof InputStream) {
					// ((InputStream)param).available();
					// ByteArrayInputStream bin=new
					// ByteArrayInputStream((InputStream)param,1000);

					prepStatement.setBinaryStream(i + 1, (InputStream) param,
							20);
				}else if (param == null) {
					prepStatement.setNull(i + 1, 12);
				}

			}

		}

		if (logger.isDebugEnabled()) {
			logger.debug("preparedParam(Object[], PreparedStatement) - end");
		}
	}

	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - start");
		}

		 try {
			 Connection conn=null;
			 conn=DBConnectionManager.getInstance().getConn();
			 String sqlstr="UPDATE T_QUESTION SET STATUS=1 WHERE ID=?";
			 String[] pars= new String[1];
			 pars[0]="281";
			 ResultSet rs=OADBUtil.runQuerySql("select * from t_question", conn);
			 //System.out.println(rs);
		} catch (FactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*String[] user_id = { "wenhong", "guojinyu" };
		String[] grade = { "80", "60" };

		String sqlstr = null;

		List sqlList = new ArrayList();
		for (int i = 0; i < user_id.length; i++) {
			sqlstr = "UPDATE T_USER SET GRADE=GRADE+" + grade[i]
					+ " WHERE ID='" + user_id[i] + "'";
			sqlList.add(sqlstr);
		}
		try {
			runBatchSQL(sqlList);
		} catch (Exception e) {
			logger.error("main(String[])", e);

			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end");
		}
	}

}
