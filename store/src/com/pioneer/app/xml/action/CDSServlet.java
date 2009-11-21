package com.pioneer.app.xml.action;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.frame.util.security.MACService;
import com.pioneer.app.comm.ApplicationPathMg;
import com.pioneer.app.db.DBConnectionConfig;
import com.pioneer.app.util.Dom4jUtil;
import com.pioneer.app.xml.CDSDataService;

public class CDSServlet extends HttpServlet {
	private boolean flg=false;
	/**
	 * Constructor of the object.
	 */
	public CDSServlet() {
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
				rtdoc=CDSDataService.FirstFilter(reqDoc);
				OutputStream out=response.getOutputStream();
				Dom4jUtil.writeDocToOut(rtdoc, "UTF-8", out);
				out.flush();
				out.close();
			}else{
				rtdoc=DocumentHelper.createDocument();
				
				Element rtroot=rtdoc.addElement("DATAPACKET");
				rtroot.addAttribute("code", "error");
				rtroot.addAttribute("message", "请求内容为空！");
				OutputStream out=response.getOutputStream();
				Dom4jUtil.writeDocToOut(rtdoc, "UTF-8", out);
				out.flush();
				out.close();
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
		
		/*System.out.println("########CDS int########");
		String dbHome=this.getInitParameter("db_home");
		System.out.println("dbHome=="+dbHome);
		try{
		Process ps=	java.lang.Runtime.getRuntime().exec(dbHome+"/run.bat");
		InputStream stdoutStream = new BufferedInputStream(ps.getInputStream());
		 
		StringBuffer buffer= new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1) break;
			buffer.append((char)c);
		}
		String outputText = buffer.toString();
		System.out.println("outputText="+outputText);
		stdoutStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
		
		String webpath=this.getServletContext().getRealPath("/");
		ApplicationPathMg.getInstance().setWebRootPath(webpath);
		DBConnectionConfig.getInstance().init(webpath);
		
		
		try {
			String port=this.getInitParameter("port");
			if(null==port)port="80";
			String context=this.getInitParameter("context");
			if(null==context)context="store";
			String basePath = "http://"+InetAddress.getLocalHost().getHostAddress()+":"+port+"/"+context+"/";
			Document doc=Dom4jUtil.getDocFromFile(webpath+"assets/info.xml");
			if(null!=doc){
				Element root=doc.getRootElement();
				root.addAttribute("url", basePath);
				
				Dom4jUtil.writeDocToFile(doc, "GBK", webpath+"assets/info.xml");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
