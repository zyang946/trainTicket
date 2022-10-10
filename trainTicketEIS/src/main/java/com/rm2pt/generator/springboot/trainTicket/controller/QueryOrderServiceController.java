package com.rm2pt.generator.springboot.trainTicket.controller;
import com.rm2pt.generator.springboot.trainTicket.service.*;
import com.rm2pt.generator.springboot.trainTicket.Message.*;
import java.util.Date;
import com.rm2pt.generator.springboot.trainTicket.utils.PreconditionException;
import com.rm2pt.generator.springboot.trainTicket.utils.StandardOPs;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/QueryOrderService")
public class QueryOrderServiceController {
	
	//@Autowired HttpSession hs;
	
	@Autowired 
	private QueryOrderService queryOrderService;
	
	@RequestMapping(value="/queryByAccoutId",method=RequestMethod.GET)	
	public String queryByAccoutId(QueryByAccoutIdMessage queryByAccoutIdMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", queryOrderService.queryByAccoutId(queryByAccoutIdMsg.accoutId));
			object.put("msg", "success");
			object.put("code", "200");
		} catch (Exception e) {
			if (e instanceof PreconditionException) {
				object.put("msg", "PreConditionException");
			}else {
				object.put("msg", "PostConditionException");
				e.printStackTrace();
			}
			object.put("code", "400");
		}
		String s=JSON.toJSONString(object);
		return s;
	}
	@RequestMapping(value="/queryTicketId",method=RequestMethod.GET)	
	public String queryTicketId(QueryTicketIdMessage queryTicketIdMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", queryOrderService.queryTicketId(queryTicketIdMsg.ticketId));
			object.put("msg", "success");
			object.put("code", "200");
		} catch (Exception e) {
			if (e instanceof PreconditionException) {
				object.put("msg", "PreConditionException");
			}else {
				object.put("msg", "PostConditionException");
				e.printStackTrace();
			}
			object.put("code", "400");
		}
		String s=JSON.toJSONString(object);
		return s;
	}
	
}
