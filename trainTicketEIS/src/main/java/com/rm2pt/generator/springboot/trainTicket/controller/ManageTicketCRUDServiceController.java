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
@RequestMapping("/ManageTicketCRUDService")
public class ManageTicketCRUDServiceController {
	
	//@Autowired HttpSession hs;
	
	@Autowired 
	private ManageTicketCRUDService manageTicketCRUDService;
	
	@RequestMapping(value="/createTicket",method=RequestMethod.POST)	
	public String createTicket(CreateTicketMessage createTicketMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageTicketCRUDService.createTicket(createTicketMsg.ticketid,createTicketMsg.routeid,createTicketMsg.seatid,createTicketMsg.price,createTicketMsg.isvalid));
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
	@RequestMapping(value="/queryTicket",method=RequestMethod.GET)	
	public String queryTicket(QueryTicketMessage queryTicketMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageTicketCRUDService.queryTicket(queryTicketMsg.ticketid));
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
	@RequestMapping(value="/modifyTicket",method=RequestMethod.PUT)	
	public String modifyTicket(ModifyTicketMessage modifyTicketMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageTicketCRUDService.modifyTicket(modifyTicketMsg.ticketid,modifyTicketMsg.routeid,modifyTicketMsg.seatid,modifyTicketMsg.price,modifyTicketMsg.isvalid));
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
	@RequestMapping(value="/deleteTicket",method=RequestMethod.DELETE)	
	public String deleteTicket(DeleteTicketMessage deleteTicketMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageTicketCRUDService.deleteTicket(deleteTicketMsg.ticketid));
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
