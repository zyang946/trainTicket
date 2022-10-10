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
@RequestMapping("/ManageSeatCRUDService")
public class ManageSeatCRUDServiceController {
	
	//@Autowired HttpSession hs;
	
	@Autowired 
	private ManageSeatCRUDService manageSeatCRUDService;
	
	@RequestMapping(value="/createSeat",method=RequestMethod.POST)	
	public String createSeat(CreateSeatMessage createSeatMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageSeatCRUDService.createSeat(createSeatMsg.seatid,createSeatMsg.trainid,createSeatMsg.seattype));
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
	@RequestMapping(value="/querySeat",method=RequestMethod.GET)	
	public String querySeat(QuerySeatMessage querySeatMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageSeatCRUDService.querySeat(querySeatMsg.seatid));
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
	@RequestMapping(value="/modifySeat",method=RequestMethod.PUT)	
	public String modifySeat(ModifySeatMessage modifySeatMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageSeatCRUDService.modifySeat(modifySeatMsg.seatid,modifySeatMsg.trainid,modifySeatMsg.seattype));
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
	@RequestMapping(value="/deleteSeat",method=RequestMethod.DELETE)	
	public String deleteSeat(DeleteSeatMessage deleteSeatMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageSeatCRUDService.deleteSeat(deleteSeatMsg.seatid));
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
