package com.ggw.app.domain.chart.msg;
/**
 * 检验签名模型对象
 *@author ggw
 */
public class CheckModle {
	private String msg_signature;
	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;
	
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getEchostr() {
		return echostr;
	}
	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String msg_signature) {
		this.signature = msg_signature;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getMsg_signature() {
		return msg_signature;
	}
	public void setMsg_signature(String msg_signature) {
		this.msg_signature = msg_signature;
	}
	@Override
	public String toString() {
		return "CheckModle [msg_signature=" + msg_signature + ", signature="
				+ signature + ", timestamp=" + timestamp + ", nonce=" + nonce
				+ ", echostr=" + echostr + "]";
	}

}
