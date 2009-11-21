package com.pioneer.app.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionUtil {

	public ExceptionUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @desc:
	 * @param args :
	 * @auther : pionner
	 * @date : 2007-9-4
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public void AJAXExceptionShow(HttpServletRequest request,
			HttpServletResponse response, Exception e) {
		try {
			response.setCharacterEncoding("utf-8");
			StringBuffer bf = new StringBuffer(100);
			bf.append("<error>");
			bf.append(e.getMessage());
			bf.append("</error>");
			// 同时保存到数据库�?
//			SysLogService.getInst().AddExceptionLog(request, e);
			response.getOutputStream().write(bf.toString().getBytes("utf-8"));
			response.getOutputStream().flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static ExceptionUtil getInst() {
		return _instant;
	}

	private static ExceptionUtil _instant = new ExceptionUtil();

}
