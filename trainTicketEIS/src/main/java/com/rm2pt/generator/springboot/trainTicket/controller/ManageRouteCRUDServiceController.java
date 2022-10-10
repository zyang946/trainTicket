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
@RequestMapping("/ManageRouteCRUDService")
public class ManageRouteCRUDServiceController {
	
	//@Autowired HttpSession hs;
	
	@Autowired 
	private ManageRouteCRUDService manageRouteCRUDService;
	
	@RequestMapping(value="/createRoute",method=RequestMethod.POST)	
	public String createRoute(CreateRouteMessage createRouteMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageRouteCRUDService.createRoute(createRouteMsg.routeid,createRouteMsg.startstation,createRouteMsg.endstation,createRouteMsg.time,createRouteMsg.trainid));
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
	@RequestMapping(value="/queryRoute",method=RequestMethod.GET)	
	public String queryRoute(QueryRouteMessage queryRouteMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageRouteCRUDService.queryRoute(queryRouteMsg.routeid));
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
	@RequestMapping(value="/modifyRoute",method=RequestMethod.PUT)	
	public String modifyRoute(ModifyRouteMessage modifyRouteMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageRouteCRUDService.modifyRoute(modifyRouteMsg.routeid,modifyRouteMsg.startstation,modifyRouteMsg.endstation,modifyRouteMsg.time,modifyRouteMsg.trainid));
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
	@RequestMapping(value="/deleteRoute",method=RequestMethod.DELETE)	
	public String deleteRoute(DeleteRouteMessage deleteRouteMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageRouteCRUDService.deleteRoute(deleteRouteMsg.routeid));
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
