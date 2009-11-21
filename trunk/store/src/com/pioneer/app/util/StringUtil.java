package com.pioneer.app.util;

/**
 * @author zhengwei
 * 
 */
public class StringUtil {

	private StringUtil() {
	}

	/**
	 * 判断一个字符是Ascill字符还是其它字符（如汉字字符）
	 * 
	 * @param char
	 *            c, 需要判断的字符
	 * @return boolean, 返回true,Ascill字符
	 */
	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字长度为2,英文字符长度为1
	 * 
	 * @param int
	 *            s, 需要得到长度的字符串
	 * @return int, 得到的字符串长度
	 */
	public static int length(String s) {
		if (null == s)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i]))
				len++;
		}
		return len;
	}

	/**
	 * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
	 * 
	 * @param String
	 *            origin, 原始字符串
	 * @param int
	 *            len, 截取长度(一个汉字长度按2算的)
	 * @return String, 返回的字符串
	 */
	public static String substring(String origin, int len) {
		if (null == origin || "".equals(origin) || len < 1)
			return "";
		byte[] strByte = new byte[len];
		if (len > StringUtil.length(origin))
			return origin;
		System.arraycopy(origin.getBytes(), 0, strByte, 0, len);
		int count = 0;
		for (int i = 0; i < len; i++) {
			int value = strByte[i];
			if (value < 0) {
				count++;
			}
		}
		if (count % 2 != 0) {
			len = (len == 1) ? ++len : --len;
		}
		return new String(strByte, 0, len);
	}
}
