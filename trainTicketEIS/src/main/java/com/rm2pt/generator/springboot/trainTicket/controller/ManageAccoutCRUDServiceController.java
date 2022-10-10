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
@RequestMapping("/ManageAccoutCRUDService")
public class ManageAccoutCRUDServiceController {
	
	//@Autowired HttpSession hs;
	
	@Autowired 
	private ManageAccoutCRUDService manageAccoutCRUDService;
	
	@RequestMapping(value="/createAccout",method=RequestMethod.POST)	
	public String createAccout(CreateAccoutMessage createAccoutMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageAccoutCRUDService.createAccout(createAccoutMsg.accoutid,createAccoutMsg.name,createAccoutMsg.phonenumber));
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
	@RequestMapping(value="/queryAccout",method=RequestMethod.GET)	
	public String queryAccout(QueryAccoutMessage queryAccoutMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageAccoutCRUDService.queryAccout(queryAccoutMsg.accoutid));
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
	@RequestMapping(value="/modifyAccout",method=RequestMethod.PUT)	
	public String modifyAccout(ModifyAccoutMessage modifyAccoutMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageAccoutCRUDService.modifyAccout(modifyAccoutMsg.accoutid,modifyAccoutMsg.name,modifyAccoutMsg.phonenumber));
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
	@RequestMapping(value="/deleteAccout",method=RequestMethod.DELETE)	
	public String deleteAccout(DeleteAccoutMessage deleteAccoutMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageAccoutCRUDService.deleteAccout(deleteAccoutMsg.accoutid));
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
