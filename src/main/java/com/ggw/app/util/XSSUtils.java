package com.ggw.app.util;
/**
 * XSSUtils工具类
 * @author ggw
 */
public class XSSUtils {
	private static final String[] xssStrings = { "&quot;", null, null, "&#37;",
			"&amp;", "&#39;", "&#40;", "&#41;", null, "&#43;", null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, "&#59;", "&lt;", null, "&gt;" };
	/**
	 * 编码文本内容，将文本内容中的   < , >, & , ' , " , ( , ) , + , ; , % 字符进行转义，从而避免xss攻击 
	 * 如:<Script>alert (XSS)<Script> 转义为: &lt;Script&gt;alert &#40;&quot; XSS &quot;&#41;&lt;Script&gt;
	 * @param text
	 * @return
	 */
	public static String encodeXSS(String text) {
		if (text == null)
			return null;
		int len = text.length();
		if (len < 1)
			return text;
		boolean foundXSSChar = false;
		StringBuilder buf = null;
		for (int i = 0; i < len; ++i) {
			char ch = text.charAt(i);
			int pos = ch - '"';
			if ((pos > -1) && (pos < xssStrings.length)
					&& (xssStrings[pos] != null)) {
				if (!(foundXSSChar)) {
					buf = new StringBuilder(len);
					buf.append(text.substring(0, i));
				}
				buf.append(xssStrings[pos]);
				foundXSSChar = true;
			} else if (foundXSSChar) {
				buf.append(ch);
			}
		}
		if (!(foundXSSChar))
			return text;
		return buf.toString();
	}
}