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
@RequestMapping("/ModifyTripService")
public class ModifyTripServiceController {
	
	//@Autowired HttpSession hs;
	
	@Autowired 
	private ModifyTripService modifyTripService;
	
	@RequestMapping(value="/updateTicket",method=RequestMethod.PUT)	
	public String updateTicket(UpdateTicketMessage updateTicketMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", modifyTripService.updateTicket(updateTicketMsg.accoutId,updateTicketMsg.ticketId,updateTicketMsg.newRouteId));
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
	@RequestMapping(value="/updateOrder",method=RequestMethod.PUT)	
	public String updateOrder(UpdateOrderMessage updateOrderMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", modifyTripService.updateOrder(updateOrderMsg.time));
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
	@RequestMapping(value="/showSeatsByRouteId",method=RequestMethod.GET)	
	public String showSeatsByRouteId() {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", modifyTripService.showSeatsByRouteId());
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
	@RequestMapping(value="/selectNewSeat",method=RequestMethod.PUT)	
	public String selectNewSeat(SelectNewSeatMessage selectNewSeatMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", modifyTripService.selectNewSeat(selectNewSeatMsg.seatId));
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
	@RequestMapping(value="/payDifference",method=RequestMethod.PUT)	
	public String payDifference(PayDifferenceMessage payDifferenceMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", modifyTripService.payDifference(payDifferenceMsg.difference));
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
