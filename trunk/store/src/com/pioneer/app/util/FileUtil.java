package com.pioneer.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.pioneer.app.comm.ApplicationPathMg;

public class FileUtil {

	public FileUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String getShortName(String name) {
		String shortName = null;//D:\log.txt
		if("/".equals(java.io.File.separator)){
			name=name.replaceAll("\\\\", java.io.File.separator);
		}
//		name=name.replaceAll("\\", replacement)
		if (null != name) {
			int i = name.lastIndexOf(java.io.File.separator);
			if (-1 != i) {
				shortName = name.substring(i + 1, name.length());
			}else{
				shortName = name;
			}
			
		}

		return shortName;
	}
	
	public static String writeADFile(File adfile){
		String shortName=null;
		if(null!=adfile){
			shortName=getShortName(adfile.getAbsolutePath());
			String path=ApplicationPathMg.getInstance().getWebRootPath()+"ad/"+shortName;
			copyFile(adfile,new File(path));
		}
		return shortName;
	}

	public static String writeShopLogoFile(File adfile){
		String shortName=null;
		if(null!=adfile){
			shortName=getShortName(adfile.getAbsolutePath());
			String path=ApplicationPathMg.getInstance().getWebRootPath()+"shoplogo/"+shortName;
			copyFile(adfile,new File(path));
		}
		return shortName;
	}
	public static boolean delShopLogoFile(String shortName){
		String path=ApplicationPathMg.getInstance().getWebRootPath()+"shoplogo/"+shortName;
		return delFile(new File(path));
	}
	public static boolean delADFile(String shortName){
		String path=ApplicationPathMg.getInstance().getWebRootPath()+"ad/"+shortName;
		return delFile(new File(path));
	}
	
	public static String writeImageFile(File imagefile){
		String shortName=null;
		if(null!=imagefile){
			shortName=getShortName(imagefile.getAbsolutePath());
			String path=ApplicationPathMg.getInstance().getWebRootPath()+"picture/purchase/"+shortName;
			copyFile(imagefile,new File(path));
		}
		return shortName;
	}
	public static boolean delImageFile(String shortName){
		String path=ApplicationPathMg.getInstance().getWebRootPath()+"picture/purchase/"+shortName;
		return delFile(new File(path));
	}
	public static String writeUseFile(File usefile){
		String shortName=null;
		if(null!=usefile){
			shortName=getShortName(usefile.getAbsolutePath());
			String path=ApplicationPathMg.getInstance().getWebRootPath()+"move/purchase/"+shortName;
			copyFile(usefile,new File(path));
		}
		return shortName;
	}
	public static boolean delUseFile(String shortName){
		String path=ApplicationPathMg.getInstance().getWebRootPath()+"move/purchase/"+shortName;
		return delFile(new File(path));
	}
	
	public static void copyFile(File source,File des){
		
		if(null!=source){
			
			try {
					if(!des.exists()){
							des.createNewFile();
						}
						FileInputStream fis  = new FileInputStream(source);
						FileOutputStream fos = new FileOutputStream(des);
						byte[] buf = new byte[1024];
						int i = 0;
						while((i=fis.read(buf))!=-1) {
						  fos.write(buf, 0, i);
						  }
						fis.close();
						fos.close();
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
		public static boolean delFile(File file){
			boolean flg=false;
			if(null!=file){
				if(file.exists()){
					file.delete();
					flg=true;
				}
			}
			return flg;
		}


	/**
	 * @desc:
	 * @param args :
	 * @auther : pionner
	 * @date : 2007-9-13
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src="Untitled.png";
		String shortName=FileUtil.getShortName(src);
		Date now=new Date();
		System.out.println(now.getTime());
		System.out.println("shortName="+shortName);
		
	}

}
