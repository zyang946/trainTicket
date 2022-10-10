package entities;

import org.json.JSONObject;
import java.util.HashMap;import java.util.List;import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.json.JSONArray;
import org.json.JSONException;

public class TicketJsonUtils{
	
	
	public static List<HashMap<String, Object>> getJsonList(String json) {
	    	List<HashMap<String, Object>> dataList;
	    	   
	    	   dataList = new ArrayList<>();
	    	   
	    	   try {
	    	   	
	    	   	JsonObject respJson=new JsonParser().parse(json).getAsJsonObject();
	    	   	
	    	   	System.out.println(respJson);
	    	   	
	    	   	   JSONObject rootObject = new JSONObject(respJson.toString());
	
	            JSONArray dataArray = rootObject.getJSONArray("Ticket");

	            for (int i = 0; i < dataArray.length(); i++) {
	
	   JSONObject sonObject = dataArray.getJSONObject(i);
	   
	   String TicketIdStr = String.valueOf(sonObject.get("TicketId"));
	   String RouteIdStr = String.valueOf(sonObject.get("RouteId"));
	   String SeatIdStr = String.valueOf(sonObject.get("SeatId"));
	   String PriceStr = String.valueOf(sonObject.get("Price"));
	   String IsValidStr = String.valueOf(sonObject.get("IsValid"));

			                
			        HashMap<String, Object> map = new HashMap<>(); 
					map.put("TicketId", TicketIdStr);
					map.put("RouteId", RouteIdStr);
					map.put("SeatId", SeatIdStr);
					map.put("Price", PriceStr);
					map.put("IsValid", IsValidStr);
	
	                dataList.add(map);
	}
	return dataList;
	
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return null;
	}

	
	}	
			
