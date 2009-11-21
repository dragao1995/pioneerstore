package com.pioneer.app.file;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public class FileAction implements ServletRequestAware, ServletResponseAware {

	private HttpServletResponse response;
	private HttpServletRequest request;
	private String name;
	
	private int maxPostSize = 1000 * 1024 * 1024;
	
	
	public String uploadFile(){
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024*20);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(maxPostSize);
			List fileItems = upload.parseRequest(this.request);
      Iterator iter = fileItems.iterator();
      Map pars = new HashMap();
      while (iter.hasNext()){
      	FileItem item = (FileItem) iter.next();
      	if (!item.isFormField()){
      		String name = item.getName();
      		System.out.println("file name is :"+name);
      	}else{
      		String name = item.getFieldName();
					if (null == name || "".equals(name))
						continue;
					String val = item.getString();
					System.out.println(name+"="+val);
					pars.put(name, val);
      	}
      		
      }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		
		this.request=arg0;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		this.response=arg0;

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
