package com.ggw.app.domain.chart.msg;

/**
 * 文本消息
 * @author ggw
 */
public class TextMessage extends BaseMessage {
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String toString() {
		return "Content=" + Content + ", ToUserName="
				+ getToUserName() + ", FromUserName=" + getFromUserName()
				+ ",CreateTime=" + getCreateTime() + ", MsgType="
				+ getMsgType() + ", FuncFlag=" + getFuncFlag()
				;
	}
}