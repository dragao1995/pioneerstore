package com.pioneer.app.util;

public class HtmlEncoding {

	public HtmlEncoding() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String htmlencoding(String s) {

		if (s == null || s.equals(""))
			return "";

		s = s.replaceAll(" ", "&nbsp;");
		s = s.replaceAll("\r\n", "<br/>");
		s = s.replaceAll("\n", "<br/>");
		s = s.replaceAll("\n\n", "<p>");
		// s=s.replaceAll("&","&amp;");
		// s=s.replaceAll("<","&lt;");
		// s=s.replaceAll(">","&gt;");
		// s=s.replaceAll("'", "&#39");
		return s;
	}

	public static String htmlDecoding(String s) {

		if (s == null || s.equals(""))
			return "";

		s = s.replaceAll("&nbsp;", " ");
		// s=s.replaceAll("<br/>","\r\n");
		s = s.replaceAll("<br/>", "\n");
		s = s.replaceAll("<p>", "\n\n");

		return s;
	}
}
