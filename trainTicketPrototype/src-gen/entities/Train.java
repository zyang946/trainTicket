package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Train implements Serializable {
	
	/* all primary attributes */
	private String TrainId;
	private String Name;
	private TrainType TrainType;
	
	/* all references */
	private List<Route> InRoute = new LinkedList<Route>(); 
	
	/* all get and set functions */
	public String getTrainId() {
		return TrainId;
	}	
	
	public void setTrainId(String trainid) {
		this.TrainId = trainid;
	}
	public String getName() {
		return Name;
	}	
	
	public void setName(String name) {
		this.Name = name;
	}
	public TrainType getTrainType() {
		return TrainType;
	}	
	
	public void setTrainType(TrainType traintype) {
		this.TrainType = traintype;
	}
	
	/* all functions for reference*/
	public List<Route> getInRoute() {
		return InRoute;
	}	
	
	public void addInRoute(Route route) {
		this.InRoute.add(route);
	}
	
	public void deleteInRoute(Route route) {
		this.InRoute.remove(route);
	}
	


}
