package entities;

import org.json.JSONObject;
import java.util.HashMap;import java.util.List;import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.json.JSONArray;
import org.json.JSONException;

public class RouteJsonUtils{
	
	
	public static List<HashMap<String, Object>> getJsonList(String json) {
	    	List<HashMap<String, Object>> dataList;
	    	   
	    	   dataList = new ArrayList<>();
	    	   
	    	   try {
	    	   	
	    	   	JsonObject respJson=new JsonParser().parse(json).getAsJsonObject();
	    	   	
	    	   	System.out.println(respJson);
	    	   	
	    	   	   JSONObject rootObject = new JSONObject(respJson.toString());
	
	            JSONArray dataArray = rootObject.getJSONArray("Route");

	            for (int i = 0; i < dataArray.length(); i++) {
	
	   JSONObject sonObject = dataArray.getJSONObject(i);
	   
	   String RouteIdStr = String.valueOf(sonObject.get("RouteId"));
	   String StartStationStr = String.valueOf(sonObject.get("StartStation"));
	   String EndStationStr = String.valueOf(sonObject.get("EndStation"));
	   String TimeStr = String.valueOf(sonObject.get("Time"));
	   String TrainIdStr = String.valueOf(sonObject.get("TrainId"));

			                
			        HashMap<String, Object> map = new HashMap<>(); 
					map.put("RouteId", RouteIdStr);
					map.put("StartStation", StartStationStr);
					map.put("EndStation", EndStationStr);
					map.put("Time", TimeStr);
					map.put("TrainId", TrainIdStr);
	
	                dataList.add(map);
	}
	return dataList;
	
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return null;
	}

	
	}	
			
