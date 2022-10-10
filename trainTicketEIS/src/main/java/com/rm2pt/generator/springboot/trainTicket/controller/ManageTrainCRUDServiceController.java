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
@RequestMapping("/ManageTrainCRUDService")
public class ManageTrainCRUDServiceController {
	
	//@Autowired HttpSession hs;
	
	@Autowired 
	private ManageTrainCRUDService manageTrainCRUDService;
	
	@RequestMapping(value="/createTrain",method=RequestMethod.POST)	
	public String createTrain(CreateTrainMessage createTrainMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageTrainCRUDService.createTrain(createTrainMsg.trainid,createTrainMsg.name,createTrainMsg.traintype));
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
	@RequestMapping(value="/queryTrain",method=RequestMethod.GET)	
	public String queryTrain(QueryTrainMessage queryTrainMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageTrainCRUDService.queryTrain(queryTrainMsg.trainid));
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
	@RequestMapping(value="/modifyTrain",method=RequestMethod.PUT)	
	public String modifyTrain(ModifyTrainMessage modifyTrainMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageTrainCRUDService.modifyTrain(modifyTrainMsg.trainid,modifyTrainMsg.name,modifyTrainMsg.traintype));
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
	@RequestMapping(value="/deleteTrain",method=RequestMethod.DELETE)	
	public String deleteTrain(DeleteTrainMessage deleteTrainMsg) {
		
		
		JSONObject object = new JSONObject();
		try {
			object.put("data", manageTrainCRUDService.deleteTrain(deleteTrainMsg.trainid));
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
