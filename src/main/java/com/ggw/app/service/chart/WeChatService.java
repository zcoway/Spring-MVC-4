package com.ggw.app.service.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ggw.app.controller.chart.WeChatController;
import com.ggw.app.domain.chart.msg.Article;
import com.ggw.app.domain.chart.msg.CheckModle;
import com.ggw.app.domain.chart.msg.MessageContent;
import com.ggw.app.domain.chart.msg.NewsMessage;
import com.ggw.app.domain.chart.msg.TextMessage;
import com.ggw.app.exception.chat.AesException;
import com.ggw.app.util.chat.MessageUtil;
import com.ggw.app.util.chat.WXBizMsgCrypt;

@Service
@PropertySource("classpath:/chart/wxkey.properties")
public class WeChatService {
	
	private static Log logger = LogFactory.getLog(WeChatController.class);
	
	@Autowired
	private Environment env;
	
	private WXBizMsgCrypt bizMsgCrypt;
	
	private MessageContent msgCenter = new MessageContent();
	
	@PostConstruct
	protected void initialize(){
		try {
			bizMsgCrypt = new WXBizMsgCrypt(env.getProperty("token"),env.getProperty("encodingAesKey"), env.getProperty("corpId"));
		} catch (AesException e) {
			SecurityException se = new SecurityException();
			se.initCause(e.getCause());
			throw se;
		}
	}

	/**
	 * 检验是否为微信请求
	 * 
	 * @param signModle
	 * @return
	 */
	public boolean verifyURL(CheckModle signModle){
		return bizMsgCrypt.checkSignature(signModle);
	}
	/**
	 * 对密文进行解密
	 * @param text
	 * @return 明文
	 * @throws  AesException 如果解密失败，则抛出 AesException 异常
	 */
	public String DecryptMsg(String text){
		return bizMsgCrypt.decrypt(text);
	}

	/**
	 * 处理微信请求
	 * @param siginModle 
	 * 
	 * @param request
	 * @return 响应信息
	 */
	public String processRequest(CheckModle siginModle, HttpServletRequest request) {
		String respMessage = null;
			logger.warn("checkSignature = true");
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);;
			
//------------安全模式时使用的
//			String encrypt = requestMap.get("Encrypt");
//			logger.warn("testEncrypt="+"eMW5d0xeM95f7PbPwF32OReCnZRE+hnDXix05m1h8H7r4FGEgzCZSCJwsj2onfnO9BHimJ3LamPziRdmU61n6SKK6Vvq3pF0B+KZ3uctg0JZIrBgIZ1AtKnMp8BchXfujwM/z8MvBFl9Hy9hqvz67QwRikJOqNZH/k42L4ylCIPw9CIlNrx0RswosUxWhUwSzyd3yUwYvBImA6UJNMoYl1pp87jbMAPuzBK/V9vFo9owBx2R4Vfn9w6I1VIQCfo9Glx4XX08lQZbxqgGM8zO18Pz4zlXLEAtbblqwVOe5kqBqeU7RLz3GgY28aye8q3Y4Zi9Nd8nlNdMehBA/xIRNONJcS5VZSLq+ROvP66+RMS1LA7OsCY6Y0RDZwDbb4+yz0a0RpMtB2ZetsrFEcebo7Crnw3LUcy0xz39UHrDWDE=");		
//			logger.warn("encrypt="+encrypt);
//			
//			String postData = bizMsgCrypt.decrypt(encrypt);
//			
//			logger.warn("postData="+postData);
//			Map<String, String> elementMap = MessageUtil.parseXml(postData);
//			
//			// 发送方帐号（open_id）
//			String fromUserName = elementMap.get("FromUserName");
//			// 公众帐号
//			// 消息类型
//			String msgType = elementMap.get("MsgType");
//			String content = elementMap.get("Content");
//			String msgId = elementMap.get("MsgId");
//----------		
			
//----------正常模式
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
			String msgType = requestMap.get("MsgType");
			String content = requestMap.get("Content");
			String msgId = requestMap.get("MsgId");
			
//----------正常模式			
			
			
			
			
			// *********************额外的代码，为了防止其他用户看到测试效果，现在信息做特殊验证***************start
			if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_TEXT)) {

				if ("20141101".equals(content)) {
					if (!msgCenter.containKey(fromUserName)) {
						msgCenter.addUser(fromUserName, new Date().getTime());

						TextMessage msg1 = new TextMessage();
						msg1.setToUserName(toUserName);
						msg1.setFromUserName(fromUserName);
						msgCenter.addMsg(msg1);
					}
				}
			}
			// 如果未发送“暗号”，则直接回绝
			if (!msgCenter.containKey(fromUserName)) {
				return "";
			}

			// *********************额外的代码，为了防止其他用户看到测试效果，现在信息做特殊验证***************end
			
			
			//默认回复文本信息
			TextMessage textMessage = createDefaultReplyTextMsg(fromUserName,toUserName,msgId);
			
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容

				// 创建图文消息
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setMsgId(Long.valueOf(msgId));
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				List<Article> articleList = new ArrayList<Article>();
				// 单图文消息
				if ("1".equals(content)) {
					Article article = new Article();
					article.setTitle("微信公众帐号开发教程Java版");
					article.setDescription("方便PICC信息技术人员以及公司其他用户交流运维经验、提供运维技术支持、提高运维服务相应速度和服务质量。");
					article.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
					article.setUrl("http://springx.jd-app.com/weChat/test");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 单图文消息---不含图片
				else if ("2".equals(content)) {
					Article article = new Article();
					article.setTitle("微信公众帐号开发教程Java版");
					// 图文消息中可以使用QQ表情、符号表情
					article.setDescription("甘国威，90后，"
							+ emoji(0x1F6B9)
							+ "，微信公众帐号开发经验0个月。为帮助初学者入门，特推出此系列连载教程，也希望借此机会认识更多同行！\n\n目前已推出教程共12篇，包括接口配置、消息封装、框架搭建、QQ表情发送、符号表情发送等。\n\n后期还计划推出一些实用功能的开发讲解，例如：天气预报、周边搜索、聊天功能等。");
					// 将图片置为空
					article.setPicUrl("");
					article.setUrl("http://springx.jd-app.com/weChat/test");
					articleList.add(article);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息
				else if ("3".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("微信公众帐号开发教程\n引言");
					article1.setDescription("");
					article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
					article1.setUrl("http://springx.jd-app.com/weChat/test");

					Article article2 = new Article();
					article2.setTitle("第2篇\n微信公众帐号的类型");
					article2.setDescription("");
					article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article2.setUrl("http://springx.jd-app.com/weChat/test");

					Article article3 = new Article();
					article3.setTitle("第3篇\n开发模式启用及接口配置");
					article3.setDescription("");
					article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article3.setUrl("http://springx.jd-app.com/weChat/test");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---首条消息不含图片
				else if ("4".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("微信公众帐号开发教程Java版");
					article1.setDescription("");
					// 将图片置为空
					article1.setPicUrl("");
					article1.setUrl("http://springx.jd-app.com/weChat/test");

					Article article2 = new Article();
					article2.setTitle("第4篇\n消息及消息处理工具的封装");
					article2.setDescription("");
					article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article2.setUrl("http://springx.jd-app.com/weChat/test");

					Article article3 = new Article();
					article3.setTitle("第5篇\n各种消息的接收与响应");
					article3.setDescription("");
					article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article3.setUrl("http://springx.jd-app.com/weChat/test");

					Article article4 = new Article();
					article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");
					article4.setDescription("");
					article4.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article4.setUrl("http://springx.jd-app.com/weChat/test");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					articleList.add(article4);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---最后一条消息不含图片
				else if ("5".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("第7篇\n文本消息中换行符的使用");
					article1.setDescription("");
					article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
					article1.setUrl("http://springx.jd-app.com/weChat/test");

					Article article2 = new Article();
					article2.setTitle("第8篇\n文本消息中使用网页超链接");
					article2.setDescription("");
					article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
					article2.setUrl("http://springx.jd-app.com/weChat/test");

					Article article3 = new Article();
					article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");
					article3.setDescription("");
					// 将图片置为空
					article3.setPicUrl("");
					article3.setUrl("http://springx.jd-app.com/weChat/test");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
					
				}
			}
		//安全模式
//		respMessage=bizMsgCrypt.encrypt(bizMsgCrypt.getRandomStr(), respMessage);
		return respMessage;
	}
	
	// 默认回复文本消息
	private TextMessage createDefaultReplyTextMsg(String fromUserName,String toUserName,String msgId){
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setMsgId(Long.valueOf(msgId));
		textMessage.setFuncFlag(0);
		// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义
		StringBuffer contentMsg = new StringBuffer();
		contentMsg.append(
				"欢迎访问<a href=\"http://www.cantonfair.com/login.html\">个人主页</a>")
				.append("\n");
		contentMsg.append("您好，我是机器人小Q，请回复数字选择服务：").append("\n\n");
		contentMsg.append("1  天气预报").append("\n");
		contentMsg.append("2  公交查询").append("\n");
		contentMsg.append("3  周边搜索").append("\n");
		contentMsg.append("4  歌曲点播").append("\n");
		contentMsg.append("5  经典游戏").append("\n");
		contentMsg.append("6  美女电台").append("\n");
		contentMsg.append("7  人脸识别").append("\n");
		contentMsg.append("8  聊天唠嗑").append("\n");
		contentMsg.append("9  电影排行榜").append("\n");
		contentMsg.append("10 Q友圈").append("\n\n");
		contentMsg
				.append("点击查看 <a href=\"http://chatcourse.duapp.com\">帮助手册</a>");

		textMessage.setContent(contentMsg.toString());
		
		return textMessage;
	}
	
	

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	
	
	//测试方法
	public String getMessageContent() {
		return msgCenter.toString();
	}

}
