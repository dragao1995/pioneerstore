package com.pioneer.app.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.pioneer.app.db.OADBUtil;
import com.pioneer.app.util.DateTimeUtil;
import com.pioneer.app.util.Dom4jUtil;



public class XML2SQL {
	private static final String fieldNodeName = "/DATAPACKET/METADATA/FIELDS/FIELD";

	private static final String keyNodeName = "/DATAPACKET/METADATA/FIELDS/FIELD/PARAM[@Name='IsKeyFld']";

	private static final String tableNodeName = "/DATAPACKET/METADATA/PARAMS";

	private static final String dataNodeName = "/DATAPACKET/ROWDATA/ROW";

	private static List listKey = new ArrayList();

	private static List listField = new ArrayList();

	private static String tableName = "";

	public XML2SQL() {
	}

  public static String getTableName(Document document){
		if(null!=document){
			Element element = (Element) document
			.selectSingleNode(tableNodeName);
			return element.valueOf("@TableName");
		}
		return null;
	}
	
	public static Object getSQL(Document document,boolean fromDelphi) {
		List list = new ArrayList();

		if (document != null) {
			listField = getNodeValue(document.selectNodes(fieldNodeName));
			

			List tmpKeyList = document.selectNodes(keyNodeName);
			System.out.println(tmpKeyList.size());
			listKey.clear();
			for(int i=tmpKeyList.size()-1;i>=0;i--){
				Element paraElmt = (Element) tmpKeyList.get(i) ;
				if ("1".equals(paraElmt.valueOf("@Value"))) {
					listKey.add(paraElmt.getParent().attributeValue("attrname") );
				}
			}
			
//			listKey = getNodeValue(document.selectNodes(keyNodeName));
			Element element = (Element) document
					.selectSingleNode(tableNodeName);
			tableName = element.valueOf("@TableName");

			// System.out.println(((Element)
			// document.selectSingleNode("/DATAPACKET/ROWDATA")).asXML());
			// List nodes = document.selectNodes(dataNodeName);
			list = getDataValue(document.selectNodes(dataNodeName),fromDelphi);
		}
		return list;
	}

	public static Object getSQL(Document document) {
		return getSQL(document, false);
	}

	private static List getNodeValue(List nodes) {
		List list = new ArrayList();
		for (int i = 0; i < nodes.size(); i++) {
			Node node = (Node) nodes.get(i);
			// System.out.println(node.asXML());
			// System.out.println(node.valueOf("@attrname"));
			list.add(node.valueOf("@attrname"));
		}
		return list;
	}

	private static List getDataValue(List nodes) {
		return getDataValue(nodes,false);
	}
	
	private static List getDataValue(List nodes,boolean fromDelphi) {
		String stsAdd = fromDelphi ? "4" : "2" ;
		String stsDel = fromDelphi ? "2" : "4" ;
 		List list = new ArrayList();
		String rowState = "";
		String aWhere = "";
		StringBuffer newValue = new StringBuffer();
		StringBuffer sbFldSet = new StringBuffer();

		for (int i = 0; i < nodes.size(); i++) {
			Node node = (Node) nodes.get(i);
			// System.out.println(node.asXML());
			rowState = node.valueOf("@RowState");
			// System.out.println(rowState);
			if (rowState.equals("1")) {
				aWhere = "";
				//newValue = "";
				for (int k = 0; k < listKey.size(); k++) {
					String aVal = node.valueOf("@" + listKey.get(k));
					if (!aVal.equals("")) {
						if (aWhere.equals("")) {
							aWhere = " where " + listKey.get(k) + "='"
									+ node.valueOf("@" + listKey.get(k))+"' ";
						} else {
							aWhere = aWhere + " and " + listKey.get(k) + "='"
									+ node.valueOf("@" + listKey.get(k))+"' ";
						}
					}
				}
			}

			if (rowState.equals(stsAdd)) {
				String aField = "";
				String aValue = "";
				for (int k = 0; k < listField.size(); k++) {
					String aVal = node.valueOf("@" + listField.get(k));
					if (!aVal.equals("")) {
						if (aField.equals("")) {
							aField = (String) listField.get(k);
							aValue = "'"+node.valueOf("@" + listField.get(k))+"'";
						} else {
							aField = aField + "," + listField.get(k);
							aValue = aValue + ",'"
									+ node.valueOf("@" + listField.get(k))+"'";
						}
						// System.out.println(listField.get(k));
						// System.out.println(node.valueOf("@"
						// + listField.get(k)));

					}
				}
				list.add("insert into " + tableName + "(" + aField
						+ ") values(" + aValue + ")");
			}

			if (rowState.equals(stsDel)) {
				aWhere = "";
				for (int k = 0; k < listKey.size(); k++) {
					String aVal = node.valueOf("@" + listKey.get(k));
					if (!aVal.equals("")) {
						if (aWhere.equals("")) {
							aWhere = " where " + listKey.get(k) + "='"
									+ node.valueOf("@" + listKey.get(k))+"'";
						} else {
							aWhere = aWhere + " and " + listKey.get(k) + "='"
									+ node.valueOf("@" + listKey.get(k))+"'";
						}
						// System.out.println(listKey.get(k));
						// System.out.println(node.valueOf("@"
						// + listKey.get(k)));
						//							
					}
				}
				list.add("delete from " + tableName + aWhere);
			}
			if (rowState.equals("8")) {
				for (int k = 0; k < listField.size(); k++) {
					String aVal = node.valueOf("@" + listField.get(k));
					if (!aVal.equals("")) {
						sbFldSet.setLength(0);
						sbFldSet.append(" ").append(listField.get(k)).append("='")
						  .append(node.valueOf("@" + listField.get(k)).replaceAll("'","''")).append("',");
						newValue.append(sbFldSet.toString());
						// System.out.println(listField.get(k));
						// System.out.println(node.valueOf("@" 
						// + listField.get(k)));
						//
					}
				}
				newValue.deleteCharAt(newValue.length()-1);
				list.add("update " + tableName + " set "+newValue + aWhere);
			}
		}
		return list;
	}	
	public static List getALLSQL(Document document){
		List list = new ArrayList();
		if (document != null) {
			List listField = document.selectNodes(fieldNodeName);
			
			List tmpKeyList = document.selectNodes(keyNodeName);
//			System.out.println(tmpKeyList.size());
			listKey.clear();
			for(int i=tmpKeyList.size()-1;i>=0;i--){
				Element paraElmt = (Element) tmpKeyList.get(i) ;
				if ("1".equals(paraElmt.valueOf("@Value"))) {
					listKey.add(paraElmt.getParent().attributeValue("attrname") );
				}
			}
			
//			listKey = getNodeValue(document.selectNodes(keyNodeName));
			Element element = (Element) document.selectSingleNode(tableNodeName);
			tableName = element.valueOf("@TableName");
			list = buildSql(document.selectNodes(dataNodeName),listField,document,true,null);
		}
		return list;
	}
	/**
	 * @param document
	 * @return
	 */
	public static List getAllSQL2(Document document,Document tblsDoc){
		List list = new ArrayList();
		if (document != null) {
			List listField = document.selectNodes(fieldNodeName);
			
			List tmpKeyList = document.selectNodes(keyNodeName);
//			System.out.println(tmpKeyList.size());
			listKey.clear();
			for(int i=tmpKeyList.size()-1;i>=0;i--){
				Element paraElmt = (Element) tmpKeyList.get(i) ;
				if ("1".equals(paraElmt.valueOf("@Value"))) {
					listKey.add(paraElmt.getParent().attributeValue("attrname") );
				}
			}
			
//			listKey = getNodeValue(document.selectNodes(keyNodeName));
			Element element = (Element) document.selectSingleNode(tableNodeName);
			tableName = element.valueOf("@TableName");
			list = buildSql(document.selectNodes(dataNodeName),listField,document,false,tblsDoc);
		}
		return list;
	}
	private static boolean isKey(String colName){
		if(null!=colName){
			if(null!=listKey){
				String key=null;
				for(int i=0;i<listKey.size();i++){
					key=(String)listKey.get(i);
					if(key!=null){
						if(key.equalsIgnoreCase(colName)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	private static List buildSql(List nodes,List listField,Document doc,boolean extflag,Document tblsDoc){
		List list = new ArrayList();
		String rowState = "";
		String aWhere = "";
		String colName;
		Element insertRows=null;
		Element root=null;
		if(null!=tblsDoc){
			root=tblsDoc.getRootElement();
			insertRows=(Element)tblsDoc.selectSingleNode("/tables/insertrows");
			insertRows=(Element)root.selectSingleNode("insertrows");
			if(null==insertRows){
				insertRows=root.addElement("insertrows");
			}
		}
		//Dom4jUtil.writeDocToFile(tblsDoc,"gbk","c:/tblsdoc.xml");
		
		StringBuffer sbFldSet = new StringBuffer();
//		List listField=document.selectNodes(fieldNodeName);
		for (int i = 0; i < nodes.size(); i++) {
			Node node = (Node) nodes.get(i);
			// System.out.println(node.asXML());
			rowState = node.valueOf("@RowState");
			// System.out.println(rowState);
			aWhere = "";
			Element keyElt=null;
			for (int k = 0; k < listKey.size(); k++) {
				String aVal = node.valueOf("@" + listKey.get(k));
				keyElt=(Element)doc.selectSingleNode("/DATAPACKET/METADATA/FIELDS/FIELD[@attrname='"+listKey.get(k)+"']");
				if (!aVal.equals("")) {
					if (aWhere.equals("")) {
						if("String".equalsIgnoreCase(keyElt.valueOf("@fieldtype"))){
							aWhere = " where " + listKey.get(k) + "='"
							+ node.valueOf("@" + listKey.get(k))+"' ";
						}else{
							aWhere = " where " + listKey.get(k) + "="
							+ node.valueOf("@" + listKey.get(k));
						}
						
					} else {
						if("String".equalsIgnoreCase(keyElt.valueOf("@fieldtype"))){
							aWhere = aWhere + " and " + listKey.get(k) + "='"
							+ node.valueOf("@" + listKey.get(k))+"'";
						}else{
							aWhere = aWhere + " and " + listKey.get(k) + "="
							+ node.valueOf("@" + listKey.get(k));
						}
						
					}
				}
			}
			if (rowState.equals("8")) {
				
				Element elt=null;
				boolean flag=false;
				sbFldSet.setLength(0);
//				����Ƿ�����ˣ����Ҹ��µļ�¼�ͷ�����˵ļ�¼��ͻ���ڴ����������¼����������£������¡��������ɸ�����䡣
				if(Const.sys_app_type==1 && null!=tblsDoc){
					continue;
				}
//				�����µ���part8�?��Ҫ�����child�7�
				for (int k = 0; k < listField.size(); k++) {
					
					elt=(Element)listField.get(k);
					colName=elt.valueOf("@attrname");
					if(!extflag && tableName.equalsIgnoreCase("cotr_t_illcase")){
						if("status".equalsIgnoreCase(colName))continue;
					}
					String aVal = node.valueOf("@" + elt.valueOf("@attrname"));
					//if (!aVal.equals("")&& !isKey(colName)) 
					if (!isKey(colName) && isUpdateCol((Element)node,colName)) 
					{
						if(!flag){
							if("String".equalsIgnoreCase(elt.valueOf("@fieldtype"))){
								sbFldSet.append(" set ").append(elt.valueOf("@attrname")).append("='")
							  .append(node.valueOf("@" + elt.valueOf("@attrname")).replaceAll("'","''")).append("'");
							}else{
								sbFldSet.append(" set ").append(elt.valueOf("@attrname")).append("=")
							  .append(node.valueOf("@" + elt.valueOf("@attrname")).replaceAll("'","''"));
							}
							flag=true;
						}else{
							if("String".equalsIgnoreCase(elt.valueOf("@fieldtype"))){
								sbFldSet.append(" ,").append(elt.valueOf("@attrname")).append("='")
							  .append(node.valueOf("@" + elt.valueOf("@attrname")).replaceAll("'","''")).append("'");
							}else{
								sbFldSet.append(" ,").append(elt.valueOf("@attrname")).append("=")
							  .append(node.valueOf("@" + elt.valueOf("@attrname")).replaceAll("'","''"));
							}
						}
					}
				}
				if(sbFldSet.toString().length()>0){
					list.add("update " + tableName + sbFldSet.toString() + aWhere);
				}
				
			}else	if (rowState.equals("2")) {
				String aField = "";
				String aValue = "";
				String fields="";
				String values="";
				Element elt=null;
				
				for (int k = 0; k < listField.size(); k++) {
					elt=(Element)listField.get(k);
					String aVal = node.valueOf("@" + elt.valueOf("@attrname"));
					aField=(String) elt.valueOf("@attrname");
					if("COTRNO".equalsIgnoreCase(aField) && Integer.parseInt(aVal)<0 && null!=insertRows){
						String key="old"+String.valueOf(-Integer.parseInt(aVal));
						if(null==insertRows.valueOf("@"+key) ||"".equals(insertRows.valueOf("@"+key))){
							Element row=insertRows.addElement("row");
							row.addAttribute("oldcotrno",aVal);
							key="old"+String.valueOf(-Integer.parseInt(aVal));
							aVal=String.valueOf("");
							row.addAttribute("newcotrno",aVal);
							insertRows.addAttribute(key,aVal);
						}else{
							aVal=insertRows.valueOf("@"+key);
						}
						
					}
					if (!aVal.equals("")) {
						if (fields.equals("")) {
							fields = aField;
							aValue = aVal;
						} else {
							fields = fields + "," + aField;
							if("String".equalsIgnoreCase(elt.valueOf("@fieldtype"))){
								aValue = "'"+ aVal+"'";
							}else{
								aValue = aVal;
							}
						}
						values=values+","+aValue;
					}
				}
				list.add("insert into " + tableName + "(" + fields+ ") values(" + values.substring(1,values.length()) + ")");
			}else	if (rowState.equals("4")) {
				list.add("delete from" + tableName + aWhere);
			}else{
				list.add("delete from" + tableName + aWhere);
			}
			if((tableName.toLowerCase().startsWith("cotr_t_part") || tableName.startsWith("cotr_t_ill")) && extflag &&
					(aWhere.toUpperCase().indexOf("CHECKTIME") ==-1) ){
				String createDate=DateTimeUtil.getDateTime(DateTimeUtil.DEFAULT_OA_SYSTEM_FORMAT);
				String sql="update cotr_t_illcase set status="+Const.report_status_nutread+",create_date='"+createDate+"' "+aWhere; 
				list.add(sql);
			}
		}
		
		return list;
	
	}
	
	private static boolean isUpdateCol(Element row,String colName){
		return row.attribute(colName)!=null;
	}


	public static void main(String[] args) {
	
	}
}
