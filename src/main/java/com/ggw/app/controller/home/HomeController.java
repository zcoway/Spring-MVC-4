package com.ggw.app.controller.home;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ggw.app.serice.chart.WeChatService;

@Controller
public class HomeController {
	@Autowired
	private WeChatService weChatService ;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal) {
		return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
	}
	
	@RequestMapping(value = "/displayMessge", method = RequestMethod.GET,produces="text/plain")
	@ResponseBody
	public String displayMessge() {
		return weChatService.getMessageContent();
	}
}
