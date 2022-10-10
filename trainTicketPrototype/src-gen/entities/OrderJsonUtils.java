package entities;

import org.json.JSONObject;
import java.util.HashMap;import java.util.List;import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.json.JSONArray;
import org.json.JSONException;

public class OrderJsonUtils{
	
	
	public static List<HashMap<String, Object>> getJsonList(String json) {
	    	List<HashMap<String, Object>> dataList;
	    	   
	    	   dataList = new ArrayList<>();
	    	   
	    	   try {
	    	   	
	    	   	JsonObject respJson=new JsonParser().parse(json).getAsJsonObject();
	    	   	
	    	   	System.out.println(respJson);
	    	   	
	    	   	   JSONObject rootObject = new JSONObject(respJson.toString());
	
	            JSONArray dataArray = rootObject.getJSONArray("Order");

	            for (int i = 0; i < dataArray.length(); i++) {
	
	   JSONObject sonObject = dataArray.getJSONObject(i);
	   
	   String OrderIdStr = String.valueOf(sonObject.get("OrderId"));
	   String TicketIdStr = String.valueOf(sonObject.get("TicketId"));
	   String AccoutIdStr = String.valueOf(sonObject.get("AccoutId"));
	   String CreateTimeStr = String.valueOf(sonObject.get("CreateTime"));
	   String OrderStatusStr = String.valueOf(sonObject.get("OrderStatus"));

			                
			        HashMap<String, Object> map = new HashMap<>(); 
					map.put("OrderId", OrderIdStr);
					map.put("TicketId", TicketIdStr);
					map.put("AccoutId", AccoutIdStr);
					map.put("CreateTime", CreateTimeStr);
					map.put("OrderStatus", OrderStatusStr);
	
	                dataList.add(map);
	}
	return dataList;
	
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return null;
	}

	
	}	
			
