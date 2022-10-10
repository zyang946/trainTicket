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
@RequestMapping("/ManageOrderCRUDService")
public class ManageOrderCRUDServiceController {
	
	//@Autowired HttpSession hs;
	
	@Autowired 
	private ManageOrderCRUDService manageOrderCRUDService;
	
	@RequestMapping(value="/createOrder",method=RequestMethod.POST)	
	public String createOrder(CreateOrderMessage createOrderMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageOrderCRUDService.createOrder(createOrderMsg.orderid,createOrderMsg.ticketid,createOrderMsg.accoutid,createOrderMsg.createtime,createOrderMsg.orderstatus));
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
	@RequestMapping(value="/queryOrder",method=RequestMethod.GET)	
	public String queryOrder(QueryOrderMessage queryOrderMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageOrderCRUDService.queryOrder(queryOrderMsg.orderid));
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
	@RequestMapping(value="/modifyOrder",method=RequestMethod.PUT)	
	public String modifyOrder(ModifyOrderMessage modifyOrderMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageOrderCRUDService.modifyOrder(modifyOrderMsg.orderid,modifyOrderMsg.ticketid,modifyOrderMsg.accoutid,modifyOrderMsg.createtime,modifyOrderMsg.orderstatus));
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
	@RequestMapping(value="/deleteOrder",method=RequestMethod.DELETE)	
	public String deleteOrder(DeleteOrderMessage deleteOrderMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageOrderCRUDService.deleteOrder(deleteOrderMsg.orderid));
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
