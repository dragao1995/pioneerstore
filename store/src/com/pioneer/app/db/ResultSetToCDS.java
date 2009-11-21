package com.pioneer.app.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.pioneer.app.exception.FactoryException;
import com.pioneer.app.xml.TableMateService;

public class ResultSetToCDS {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ResultSetToCDS.class);

	public ResultSetToCDS() {
		super();
	}

	/**
	 * @param rs
	 * @return
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-3-28
	 * @desc:����Ϊ��״̬�࣬���ڷ����еĲ������ڶ�ջ�п��ٵģ����Կ���ʹ�õ�����
	 */
	public Document buildCDS(ResultSet rs) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("buildCDS(ResultSet) - start");
		}

		Document returnDocument=null;
		try {
			returnDocument = buildCDS(null, rs, null);
			rs.close();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("buildCDS(ResultSet) - end");
		}
		
		return returnDocument;
	}
	
	/**
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public Document buildNormalCDS(ResultSet rs) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("buildCDS(ResultSet) - start");
		}

		Document returnDocument = buildCDSBody(null, rs, null);
		if (logger.isDebugEnabled()) {
			logger.debug("buildCDS(ResultSet) - end");
		}
		return returnDocument;
	}
	
	public Document buildCDSBody(Document doc, ResultSet rs, String tableName)
		throws Exception {
	if (logger.isDebugEnabled()) {
		logger.debug("buildCDS(Document, ResultSet, String) - start");
	}
	
	if (null != rs) {
		Element tables = null;
		if (doc == null) {
			doc = DocumentHelper.createDocument();
			tables = doc.addElement("DATAPACKETS");
		} else {
			tables = doc.getRootElement();
		}
		tables.addAttribute("version",""+(new Date().getTime()));
		tables.addAttribute("status", "new");
		Element table = tables.addElement("DATAPACKET");
		if (null != tableName && !"".equals(tableName)) {
			table.addAttribute("tableName", tableName);
		}
		//buildCDSHead(table, rs);
		buildCDSBody(tables, table, rs);
	
		rs.close();
	}
	
	if (logger.isDebugEnabled()) {
		logger.debug("buildCDS(Document, ResultSet, String) - end");
	}
	return doc;
	}

	/**
	 * @desc: ��һ���������ļ��ӵ�document�����С�
	 * @param doc
	 * @param rs
	 * @param tableName
	 * @return
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-4-14
	 */
	public Document buildCDS(Document doc, ResultSet rs, String tableName)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("buildCDS(Document, ResultSet, String) - start");
		}

		if (null != rs) {
			Element tables = null;
			if (doc == null) {
				doc = DocumentHelper.createDocument();
				tables = doc.addElement("DATAPACKETS");
			} else {
				tables = doc.getRootElement();
			}
//			tables.addAttribute("version",""+(new Date().getTime()));
			Element table = tables.addElement("DATAPACKET");
			if (null != tableName && !"".equals(tableName)) {
				table.addAttribute("tableName", tableName);
			}
			buildCDSHead(table, rs);
			buildCDSBody(tables, table, rs);

			rs.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buildCDS(Document, ResultSet, String) - end");
		}
		return doc;
	}

	/**
	 * @desc: ��һ�����Ϊ�ӱ�ӵ������ļ���
	 * @param doc
	 * @param rs
	 * @param tableName
	 * @param parentTable
	 * @param foreignCol
	 * @return
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-4-14
	 */
	public Document buildTableWidthTypeCDS(Document doc, ResultSet rs,
			String tableName, String tableType, String foreignCol)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger
					.debug("buildTableWidthTypeCDS(Document, ResultSet, String, String, String) - start");
		}

		if (null != rs) {
			Element tables = null;
			if (doc == null) {
				doc = DocumentHelper.createDocument();
				tables = doc.addElement("DATAPACKETS");
			} else {
				tables = doc.getRootElement();
			}

			Element table = tables.addElement("DATAPACKET");
			if (null != tableName && !"".equals(tableName)) {
				table.addAttribute("tableName", tableName);
			}
			if (null != tableType && !"".equals(tableType)) {
				table.addAttribute("tableType", tableType);
			}
			if (null != foreignCol && !"".equals(foreignCol)) {
				table.addAttribute("foreignCol", foreignCol);
			}
			buildCDSHead(table, rs);
			String workflowId = buildCDSBody(tables, table, rs);

		}
		// Dom4jUtil.writeDocToFile(doc,"gbk","c:/tmp/myeditpageinfor.xml");

		if (logger.isDebugEnabled()) {
			logger
					.debug("buildTableWidthTypeCDS(Document, ResultSet, String, String, String) - end");
		}
		return doc;
	}

	/**
	 * @desc:
	 * @param root
	 * @param rs
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-4-14
	 */
	private void buildCDSHead(Element root, ResultSet rs) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("buildCDSHead(Element, ResultSet) - start");
		}

		ResultSetMetaData rsmeta = rs.getMetaData();
		int Ncol = rsmeta.getColumnCount();
		if (Ncol > 0) {
			String colName;
			String colType;
			int precision = 0;
			Element MEtaElt = root.addElement("METADATA");
			Element FiledsElt = MEtaElt.addElement("FIELDS");
			Element filedElt = null;
			for (int i = 0; i < Ncol; i++) {
				filedElt = FiledsElt.addElement("FIELD");
				colName = rsmeta.getColumnName(i + 1);
				colType = rsmeta.getColumnTypeName(i + 1);
				precision = rsmeta.getPrecision(i + 1);
				filedElt.addAttribute("attrname", colName);
				filedElt.addAttribute("fieldtype", colType);
				filedElt.addAttribute("WIDTH", String.valueOf(precision));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buildCDSHead(Element, ResultSet) - end");
		}
	}

	/**
	 * @desc:ȡ���������Ϣ��
	 * @param elt
	 * @param rs
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-4-12
	 */
	public void buildMetaInfor(Element elt, ResultSet rs) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("buildMetaInfor(Element, ResultSet) - start");
		}

		buildCDSHead(elt, rs);

		if (logger.isDebugEnabled()) {
			logger.debug("buildMetaInfor(Element, ResultSet) - end");
		}
	}

	/**
	 * @param root
	 * @param rs
	 * @throws Exception :
	 * @auther : pionner
	 * @date : 2007-3-29
	 * @desc:
	 */
	private String buildCDSBody(Element root, Element table, ResultSet rs)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("buildCDSBody(Element, Element, ResultSet) - start");
		}

		// Element table=tables.addElement("DATAPACKET");
		String workflowId = null;
		String draftoutId = null;
		if (null != table && null != rs) {
			ResultSetMetaData rsmeta = rs.getMetaData();
			int Ncol = rsmeta.getColumnCount();
			Element RsElt = table.addElement("ROWDATA");
			Element RElt = null;
			String colName = null;
			String colVal = null;

			while (rs.next()) {
				RElt = RsElt.addElement("ROW");
				for (int i = 0; i < Ncol; i++) {
					colName = rsmeta.getColumnName(i + 1);
					colVal = rs.getString(colName);
					RElt.addAttribute(colName, colVal);
				}

			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buildCDSBody(Element, Element, ResultSet) - end");
		}
		return workflowId;
	}

	private String ColNameTOVarName(String colName) {
		if (logger.isDebugEnabled()) {
			logger.debug("ColNameTOVarName(String) - start");
		}

		StringBuffer result = null;
		List temps = new ArrayList();
		colName = colName.toLowerCase();
		int i = colName.indexOf("_");
		while (-1 != i) {
			temps.add(colName.substring(0, i));
			colName = colName.substring(i + 1, colName.length());
			i = colName.indexOf("_");
		}
		temps.add(colName);
		String temp = null;
		String ch = null;
		char first;
		result = new StringBuffer();
		result.append(temps.get(0));
		for (int j = 1; j < temps.size(); j++) {
			temp = (String) temps.get(j);
			first = temp.charAt(0);
			ch = "" + first;
			ch = ch.toUpperCase();
			temp = ch + temp.substring(1, temp.length());
			result.append(temp);
		}

		String returnString = result.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("ColNameTOVarName(String) - end");
		}
		return returnString;
	}
	
	public static Document BuildCDSWithMate(String tblName,String sql){
		Document doc=TableMateService.getInst().getTableMate(tblName);
		Document rtdoc=(Document)doc.clone();
		java.sql.Connection con=null;
		try {
			if(null!=sql){
				con= DBConnectionManager.getInstance().getConn();
				ResultSet rs=OADBUtil.runQuerySql(sql, con);
				
				buildCDSBodyForMate(rtdoc,rs);
				rs.close();
			}
			
		} catch (FactoryException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(null!=sql)
				con.close();
			}catch(Exception ee){}
		}
		
		return rtdoc;
	}
	private static void buildCDSBodyForMate(Document doc,  ResultSet rs)throws Exception {

	Element root=doc.getRootElement();
	Element table=root.addElement("TABLEDATA");

	if ( null != rs) {
			ResultSetMetaData rsmeta = rs.getMetaData();
			int Ncol = rsmeta.getColumnCount();
			Element RsElt = table.addElement("ROWDATA");
			Element RElt = null;
			String colName = null;
			String colVal = null;
		
			while (rs.next()) {
				RElt = RsElt.addElement("ROW");
				for (int i = 0; i < Ncol; i++) {
					
					colName = rsmeta.getColumnName(i + 1);
					colVal = rs.getString(colName);
					
					RElt.addAttribute(colName, colVal);
				}
			
			}
	}

}
	
	private static Element getFieldByName(Document doc,String name){
		Element elt=null;
		
		
		return elt;
		
	}
	

	public static ResultSetToCDS getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - end");
		}
		return _instance;
	}

	private static ResultSetToCDS _instance = new ResultSetToCDS();

	/**
	 * @param args :
	 * @auther :pionner
	 * @date : 2007-3-27
	 * @desc:
	 */
	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - start");
		}

		try {
			String Ssql = "select t.id,t.name,t.nickname,t.telphone,t.email,t.mobile,t.address,t.loginname from oa_t_user t";
			// ResultSet rs=OADBUtil.runQuerySql(Ssql);
			// Document doc=ResultSetToCDS.getInstance().buildCDS(rs);
			// Dom4jUtil.writeDocToFile(doc,"gbk","c:/tmp/xmltest.xml");

		} catch (Exception e) {
			logger.error("main(String[])", e);

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end");
		}
	}

}
