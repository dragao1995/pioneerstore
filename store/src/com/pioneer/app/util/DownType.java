package com.pioneer.app.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 在下载文件的时候，根据文件的扩展名称，确定要在response头中设置文件的类型。
 * @author pioneer
 *
 */
public class DownType {
	Map typeMap = new HashMap();

	public DownType() {
		super();
		init();
	}

	private void init() {
		typeMap.put("gif", "image/gif");
		typeMap.put("jpg", "image/jpeg");
		typeMap.put("jpeg", "image/jpeg");
		typeMap.put("jpe", "image/jpeg");
		typeMap.put("bmp", "image/bmp");
		typeMap.put("png", "image/png");
		typeMap.put("tif", "image/tiff");
		typeMap.put("tiff", "image/tiff");
		typeMap.put("pict", "image/x-pict");
		typeMap.put("pic", "image/x-pict");
		typeMap.put("pct", "image/x-pict");
		typeMap.put("tif", "image/tiff");
		typeMap.put("tiff", "image/tiff");
		typeMap.put("psd", "image/x-photoshop");

		typeMap.put("swf", "application/x-shockwave-flash");
		typeMap.put("js", "application/x-javascript");
		typeMap.put("pdf", "application/pdf");
		typeMap.put("ps", "application/postscript");
		typeMap.put("eps", "application/postscript");
		typeMap.put("ai", "application/postscript");
		typeMap.put("wmf", "application/x-msmetafile");

		typeMap.put("css", "text/css");
		typeMap.put("htm", "text/html");
		typeMap.put("html", "text/html");
		typeMap.put("txt", "text/plain");
		typeMap.put("xml", "text/xml");
		typeMap.put("wml", "text/wml");
		typeMap.put("wbmp", "image/vnd.wap.wbmp");

		typeMap.put("mid", "audio/midi");
		typeMap.put("wav", "audio/wav");
		typeMap.put("mp3", "audio/mpeg");
		typeMap.put("mp2", "audio/mpeg");

		typeMap.put("avi", "video/x-msvideo");
		typeMap.put("mpeg", "video/mpeg");
		typeMap.put("mpg", "video/mpeg");
		typeMap.put("qt", "video/quicktime");
		typeMap.put("mov", "video/quicktime");

		typeMap.put("lha", "application/x-lha");
		typeMap.put("lzh", "application/x-lha");
		typeMap.put("z", "application/x-compress");
		typeMap.put("gtar", "application/x-gtar");
		typeMap.put("gz", "application/x-gzip");
		typeMap.put("gzip", "application/x-gzip");
		typeMap.put("tgz", "application/x-gzip");
		typeMap.put("tar", "application/x-tar");
		typeMap.put("bz2", "application/bzip2");
		typeMap.put("zip", "application/zip");
		typeMap.put("arj", "application/x-arj");
		typeMap.put("rar", "application/x-rar-compressed");

		typeMap.put("hqx", "application/mac-binhex40");
		typeMap.put("sit", "application/x-stuffit");
		typeMap.put("bin", "application/x-macbinary");

		typeMap.put("uu", "text/x-uuencode");
		typeMap.put("uue", "text/x-uuencode");

		typeMap.put("latex", "application/x-latex");
		typeMap.put("ltx", "application/x-latex");
		typeMap.put("tcl", "application/x-tcl");

		typeMap.put("pgp", "application/pgp");
		typeMap.put("asc", "application/pgp");
		typeMap.put("exe", "application/x-msdownload");
		typeMap.put("doc", "application/msword");
		typeMap.put("rtf", "application/rtf");
		typeMap.put("xls", "application/vnd.ms-excel");
		typeMap.put("ppt", "application/vnd.ms-powerpoint");
		typeMap.put("mdb", "application/x-msaccess");
		typeMap.put("wri", "application/x-mswrite");

	}

	public String getDownTypeByFileName(String fileName) {
		String extendName = this.getExtendName(fileName);
		if (null != extendName) {
			return (String) this.typeMap.get(extendName.toLowerCase());
		}
		return null;
	}

	public String getDownTypeByExtendName(String extendName) {
		if (null != extendName) {
			return (String) this.typeMap.get(extendName.toLowerCase());
		}
		return null;

	}

	private String getExtendName(String fileName) {
		if (null != fileName) {
			int i = fileName.lastIndexOf(".");
			if (-1 != i) {
				return fileName.substring(i + 1, fileName.length());
			}
		}
		return null;
	}

	public static DownType getInst() {
		return _inst;
	}

	private static DownType _inst = new DownType();

	/**
	 * @desc:
	 * @param args :
	 * @auther : pionner
	 * @date : 2007-10-25
	 */
	public static void main(String[] args) {
		String downType = DownType.getInst().getDownTypeByExtendName("gif");
		System.out.println("downType==" + downType);

	}

}
