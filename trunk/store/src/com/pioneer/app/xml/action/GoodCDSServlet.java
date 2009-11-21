package com.pioneer.app.xml.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;

import com.pioneer.app.util.Dom4jUtil;
import com.pioneer.app.xml.CDSDataService;
import com.pioneer.app.xml.GoodCDSService;

public class GoodCDSServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GoodCDSServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	private void cdsProcess(HttpServletRequest request, HttpServletResponse response){

		try {
			
			InputStream in=	request.getInputStream();
			Document reqDoc=Dom4jUtil.getDocFromStream(in);
			
			Document rtdoc=null;
			if(null!=reqDoc){
				rtdoc=GoodCDSService.FirstFilter(reqDoc);
				OutputStream out=response.getOutputStream();
				Dom4jUtil.writeDocToOut(rtdoc, "UTF-8", out);
				out.flush();
				out.close();
			}else{
				System.out.println("request document is null");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

		cdsProcess(request,response);
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

		cdsProcess(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		//System.out.println("GoodCDSServlet init!!!!!!!!!!!!");
	}

}
