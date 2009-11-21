package com.pioneer.app.file;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.pioneer.app.comm.ApplicationPathMg;
import com.pioneer.app.util.FileUtil;
import com.pioneer.app.xml.TableInfoService;
import com.sun.jndi.toolkit.url.UrlUtil;

public class FileUploadServlet extends HttpServlet {
//定义文件的上传路径
  private String uploadPath = "";

//限制文件的上传大小
  private int maxPostSize = 100 * 1024 * 1024;


	/**
	 * Constructor of the object.
	 */
	public FileUploadServlet() {
		super();
		File uploadDir=new File(uploadPath);
		if(!uploadDir.exists()){
			uploadDir.mkdir();
		}
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	//System.out.println("Access !");

	String rtname="-1";
	//保存文件到服务器中
	DiskFileItemFactory factory = new DiskFileItemFactory();
	factory.setSizeThreshold(4096);
	ServletFileUpload upload = new ServletFileUpload(factory);
	upload.setSizeMax(maxPostSize);
//	Map pars=new HashMap();
	String comName=request.getParameter("comName");
	if(null==comName || "".equals(comName) || "null".equals(comName)){
		String tblName1=request.getParameter("TABLE");
		if(null!=tblName1 && !"".equals(tblName1) && !"null".equals(tblName1)){
			String tblName=UrlUtil.decode(tblName1, "UTF-8");
			
			comName=TableInfoService.getInst().getTableCode(tblName);
		}
	}
	
	String oldFile=request.getParameter("oldFile");
	if(null!=oldFile && !"null".equals(oldFile) && !"".equals(oldFile)){
		File oldfile=new File(uploadPath+comName+"/"+oldFile);
		if(oldfile.exists())
			oldfile.delete();
	}
	String timeStr=request.getParameter("time");
	String filePath=uploadPath+comName+"/";
	File FileDir =new File(filePath);
	if(!FileDir.exists()){
		FileDir.mkdirs();
	}
	try {
	  List fileItems = upload.parseRequest(request);
	  Iterator iter = fileItems.iterator();
	  while (iter.hasNext()) {
	      FileItem item = (FileItem) iter.next();
	      if (!item.isFormField()) {
	         try {
		          	String filename = item.getName();
								//System.out.println("name1="+filename);
								if (null == filename || "".equals(filename)) {
									continue;
								} 
									filename = FileUtil.getShortName(filename);
//									Date now=new Date();
									
		              item.write(new File(filePath+timeStr+filename));
		              
	          } catch (Exception e) {
	              e.printStackTrace();
	          }
	      }else{
	      	
	      }
	  }
	  
	} catch (Exception e) {
	  e.printStackTrace();
	  //System.out.println(e.getMessage() + "结束");
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
		 request.setCharacterEncoding("UTF-8");
		processRequest(request, response);

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
		 request.setCharacterEncoding("UTF-8");
		processRequest(request, response);

		/**

		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(5 * 1024);// 设置临时文件夹
			File tempFile = new File("c:/");
			factory.setRepository(tempFile); // 建文件项列

			ServletFileUpload upload = new ServletFileUpload(factory); // 分析构成文件列表,
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator(); // 对列表项进行处理
			Map pars = new HashMap();
			
			List attachs = new ArrayList();
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
		
**/
	
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here  ApplicationPathMg.getInstance().getContextPath()+"upload/";
		uploadPath=this.getServletContext().getRealPath("/")+"upload/";
	}

}
