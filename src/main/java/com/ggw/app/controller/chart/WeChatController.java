package com.ggw.app.controller.chart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggw.app.domain.chart.CheckModle;
import com.ggw.app.serice.chart.WeChatService;

@Controller
public class WeChatController {
	
	@Autowired
	private WeChatService weChatService;
	

	@RequestMapping(value = "/weChat",method=RequestMethod.GET,produces="text/plain")
	@ResponseBody
	public String checkSigin(CheckModle siginModle) {
		
		System.out.println(siginModle);
	
		if(!weChatService.checkSignature(siginModle)){
			return "error";
		}
		return siginModle.getEchostr();
	}
	
	
	@RequestMapping(value = "/weChat",method=RequestMethod.POST)
	public void responeWeChatRequest(CheckModle siginModle,HttpServletRequest request,HttpServletResponse response) throws IOException{
		if(!weChatService.checkSignature(siginModle)){
			return;
		}
		String content = weChatService.processRequest(request);
		PrintWriter writer = response.getWriter();
		writer.write(content);
		writer.close();
		writer=null;
	}
}
