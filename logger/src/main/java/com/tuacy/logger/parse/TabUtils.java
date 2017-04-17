package com.tuacy.logger.parse;


public class TabUtils {

	/**
	 * 获取缩进字符
	 * @param tab 多少个缩进
	 * @return string
	 */
	public static String getTabString(int tab) {
		String tabString = "";
		for (int i = 0; i < tab; i++) {
			tabString += "    ";
		}
		return tabString;
	}

}
