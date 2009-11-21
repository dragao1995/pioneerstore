package com.pioneer.app.xml.action;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.dom4j.Document;

import com.opensymphony.xwork2.Action;
import com.pioneer.app.util.Dom4jUtil;
import com.pioneer.app.xml.CDSDataService;

public class CDSAction implements Action, ServletRequestAware,ServletResponseAware,Serializable {
	private HttpServletResponse response;
	private HttpServletRequest request;
	private File tmplFile;
	
	
public String doList(){
		
		try {
			InputStream in=	this.request.getInputStream();
			Document reqDoc=Dom4jUtil.getDocFromStream(in);
			//System.out.println(reqDoc.asXML());
			
			Document rtdoc=null;
			if(null!=reqDoc){
				rtdoc=CDSDataService.FirstFilter(reqDoc);
//			Dom4jUtil.writeDocToFile(rtdoc, "GBK", "c:/listdoc.xml");
				OutputStream out=this.response.getOutputStream();
				Dom4jUtil.writeDocToOut(rtdoc, "UTF-8", out);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
public String doCDSAction(){
	try {
		InputStream in=	this.request.getInputStream();
		Document reqDoc=Dom4jUtil.getDocFromStream(in);
		//System.out.println(reqDoc.asXML());
		
		Document rtdoc=null;
		if(null!=reqDoc){
			rtdoc=CDSDataService.FirstFilter(reqDoc);
			
			OutputStream out=this.response.getOutputStream();
			Dom4jUtil.writeDocToOut(rtdoc, "UTF-8", out);
			out.flush();
			out.close();
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
	public String doDetail(){
		try {
			InputStream in=	this.request.getInputStream();
			Document reqDoc=Dom4jUtil.getDocFromStream(in);
			
			Document rtdoc=null;
			if(null!=reqDoc){
				rtdoc=CDSDataService.FirstFilter(reqDoc);
//				Dom4jUtil.writeDocToFile(rtdoc, "GBK", "c:/detail.xml");
				OutputStream out=this.response.getOutputStream();
				Dom4jUtil.writeDocToOut(rtdoc, "UTF-8", out);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public File getTmplFile() {
		return tmplFile;
	}

	public void setTmplFile(File tmplFile) {
		this.tmplFile = tmplFile;
	}

}
