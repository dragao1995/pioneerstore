package com.pioneer.app.xml.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import com.pioneer.app.TreeService;
import com.pioneer.app.util.Dom4jUtil;
import com.pioneer.app.xml.TableInfoService;

public class TreeServlet extends HttpServlet {

	public static String REQ_ALL="all";
	public static String REQ_ROOT="root";
	public static String REQ_SUB="sub";
	/**
	 * Constructor of the object.
	 */
	public TreeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	private void processTreeRQ(HttpServletRequest request, HttpServletResponse response){
		try {
			String reqType=request.getParameter("req_type");
			String tblName=request.getParameter("tblName");
			String rootId=request.getParameter("rootId");
			
			String tblCode=TableInfoService.getInst().getTableCode(tblName);
			Document doc=null;
			if(null==rootId || "".equals(rootId) || "null".equals(rootId)){
				doc=TreeService.getAllZone(tblCode);
				
			}else{
				doc=TreeService.getSubZoneCDS(tblCode, rootId);
			}
			
			
			OutputStream out=response.getOutputStream();
			Dom4jUtil.writeDocToOut(doc, "UTF-8", out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		processTreeRQ(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		processTreeRQ(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
