package com.ggw.app.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 文本解析工具类
 * 
 * @author ggw
 */
public class TextParseUtil {
	/**
	 * 返回由逗号分隔的字符集合
	 * 
	 * @param s
	 *            解析字符串 (is not null)
	 * @return 由逗号分隔的字符集合
	 */
	public static Set<String> commaDelimitedStringToSet(String s) {
		Set<String> set = new HashSet<String>();
		String[] split = s.split(",");
		for (String aSplit : split) {
			String trimmed = aSplit.trim();
			if (trimmed.length() > 0) {
				set.add(trimmed);
			}
		}
		return set;
	}


}
