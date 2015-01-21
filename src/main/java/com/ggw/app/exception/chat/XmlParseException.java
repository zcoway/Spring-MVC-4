package com.ggw.app.exception.chat;
/**
 * xml解析异常，如果解析xml數據失敗，則拋出此異常。
 * @author ggw
 *
 */
public class XmlParseException extends RuntimeException {
	
	public XmlParseException() {
		super();
	}
	
	public XmlParseException(String msg){
		super(msg);
	}
	
	public XmlParseException(String msg,Throwable cause){
		super(msg, cause);
	}
	
	
}
