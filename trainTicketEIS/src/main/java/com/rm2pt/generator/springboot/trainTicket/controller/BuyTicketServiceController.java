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
@RequestMapping("/BuyTicketService")
public class BuyTicketServiceController {
	
	//@Autowired HttpSession hs;
	
	@Autowired 
	private BuyTicketService buyTicketService;
	
	@RequestMapping(value="/buyTicket",method=RequestMethod.POST)	
	public String buyTicket(BuyTicketMessage buyTicketMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", buyTicketService.buyTicket(buyTicketMsg.accoutId,buyTicketMsg.routeId,buyTicketMsg.ticketId));
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
	@RequestMapping(value="/saveOrder",method=RequestMethod.POST)	
	public String saveOrder(SaveOrderMessage saveOrderMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", buyTicketService.saveOrder(saveOrderMsg.orderId,saveOrderMsg.boughtTime));
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
	@RequestMapping(value="/showSeats",method=RequestMethod.GET)	
	public String showSeats() {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", buyTicketService.showSeats());
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
	@RequestMapping(value="/selectSeat",method=RequestMethod.PUT)	
	public String selectSeat(SelectSeatMessage selectSeatMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", buyTicketService.selectSeat(selectSeatMsg.seatId));
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
	@RequestMapping(value="/pay",method=RequestMethod.PUT)	
	public String pay(PayMessage payMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", buyTicketService.pay(payMsg.price));
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
