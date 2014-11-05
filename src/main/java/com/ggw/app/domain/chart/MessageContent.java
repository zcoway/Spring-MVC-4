package com.ggw.app.domain.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MessageContent extends HashMap<String, BaseMessage>{
	
	
	private List<String> uses = new ArrayList<String>();
	private List<BaseMessage> messageList = new ArrayList<BaseMessage>();
	
	public void addUser(String useName,long l){
		uses.add(useName+":"+l);
	}
	
	public void addMsg(BaseMessage message){
		messageList.add(message);
	}
	public boolean containKey(String usename){
		return uses.contains(usename);
	}
	@Override
	public String toString() {
		if(uses.isEmpty()){
			return "MessageContent {[]}";
		}
			
		StringBuilder sb = new StringBuilder();
		sb.append("MessageContent {[");
		int size = uses.size();
		int index = 0;
		for(int i = 0; i < size; i++){
			if(i > 0){
				sb.append(",[");
			}
			sb.append("use="+uses.get(i)+",");
			for(int j = 0; j <= i; j++){
				sb.append("message : "+messageList.get(j));
			}
			if(i < size-1)
			sb.append("]");
		}
		sb.append("]}");
		
		return sb.toString();
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
