package com.ggw.app.domain.chart.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageContent {
	
	
	private Map<String,Long> uses = new HashMap<String,Long>();
	private List<BaseMessage> messageList = new ArrayList<BaseMessage>();
	
	public void addUser(String useName,long l){
		uses.put(useName,l);
	}
	
	public void addMsg(BaseMessage message){
		messageList.add(message);
	}
	public boolean containKey(String usename){
		return uses.containsKey(usename);
	}
	@Override
	public String toString() {
		if(uses.isEmpty()){
			return "MessageContent {[]}";
		}
		return uses.toString() + " : "+ messageList.toString();
	}
	
	public static void main(String[] args) {
		
		new HashMap().toString();
		MessageContent content = new MessageContent();
		content.addUser("xiaoming", new Date().getTime());
		
		TextMessage textMsg = new TextMessage();
		textMsg.setFromUserName("xiao");
		textMsg.setToUserName("fock");
		content.addMsg(textMsg);
		content.addUser("dag", new Date().getTime());
		TextMessage textMsg2 = new TextMessage();
		textMsg2.setFromUserName("dag");
		textMsg2.setToUserName("fock");
		
		content.addMsg(textMsg2);
		System.out.println(content);
	}
}
