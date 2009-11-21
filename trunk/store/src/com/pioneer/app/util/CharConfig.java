package com.pioneer.app.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author lixy
 * @version 1.0
 */

public class CharConfig {
	public CharConfig() {
	}

	public final static String[] KEY = { "����", "̨��", "��Ƭ", "H��Ӱ",
			"���˵�Ӱ", "h��Ӱ" };

	public static Map charMap = null;

	public static Map getMap() {
		if (charMap == null) {
			charMap = new HashMap();
			for (int i = 0; i < KEY.length; i++) {
				charMap.put(KEY[i].trim(), "");
			}
		}
		return charMap;
	}
}
