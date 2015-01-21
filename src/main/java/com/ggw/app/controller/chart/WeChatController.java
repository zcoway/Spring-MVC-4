package com.ggw.app.controller.chart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggw.app.domain.chart.msg.CheckModle;
import com.ggw.app.service.chart.WeChatService;

@Controller
public class WeChatController {
	
	private static Log logger = LogFactory.getLog(WeChatController.class);
	
	@Autowired
	private WeChatService weChatService;
	

	@RequestMapping(value = "/wechat",method=RequestMethod.GET,produces="text/plain")
	@ResponseBody
	public String checkSigin(CheckModle siginModle) {
		
		System.out.println(siginModle);
	
		if(!weChatService.verifyURL(siginModle)){
			return "error";
		}
		return siginModle.getEchostr();
	}
	
	
	@RequestMapping(value = "/wechat",method=RequestMethod.POST)
	public void responeWeChatRequest(CheckModle siginModle, HttpServletRequest request,HttpServletResponse response) throws IOException{
		logger.info("queryString = "+request.getQueryString());
		logger.info("siginModle = "+siginModle);
		//检验是否为微信请求
		if(!weChatService.verifyURL(siginModle)){
			return;
		}
		
		String respContent = weChatService.processRequest(siginModle,request);
		logger.info("respone content="+respContent);
		PrintWriter writer = response.getWriter();
		writer.write(respContent);
		writer.close();
		writer=null;	
	}
	
	@RequestMapping(value = "/wechat/test",method=RequestMethod.GET)
	public String test(){
		return "chat/test";
	}
}
