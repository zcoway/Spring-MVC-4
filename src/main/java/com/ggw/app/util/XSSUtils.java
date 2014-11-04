package com.ggw.app.util;
/**
 * XSSUtils工具类提供了替换危险字符功能，避免XSS攻击。
 * @author ggw
 */
public class XSSUtils {
	private static final String[] xssStrings = { "&quot;", null, null, "&#37;",
			"&amp;", "&#39;", "&#40;", "&#41;", null, "&#43;", null, null,
			null, null, null, null, null, null, null, null, null, null, null,
			null, null, "&#59;", "&lt;", null, "&gt;" };
	/**
	 * 替换数据中的危险字符。(要确保整个应用程序的安全，必须在每次返回用户提供的数据时都使用 encodeXSS() 方法)
	 * 如："<Script>alert ("XSS")<Script>"  替换为: &lt;Script&gt;alert &#40;&quot; XSS &quot;&#41;&lt;Script&gt;
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
	public static void main(String[] args) {
		System.out.println(encodeXSS("<Script>alert (\"XSS\")<Script>"));
	}
}