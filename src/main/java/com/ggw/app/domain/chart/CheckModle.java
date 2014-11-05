package com.ggw.app.domain.chart;

public class CheckModle {
	
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
	@Override
	public String toString() {
		return "CheckModle [msg_signature=" + signature + ", timestamp="
				+ timestamp + ", nonce=" + nonce + ", echostr=" + echostr + "]";
	}

}
