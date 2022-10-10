package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Route implements Serializable {
	
	/* all primary attributes */
	private String RouteId;
	private String StartStation;
	private String EndStation;
	private String Time;
	private String TrainId;
	
	/* all references */
	private List<Ticket> InTicket = new LinkedList<Ticket>(); 
	private Train UsedTrain; 
	
	/* all get and set functions */
	public String getRouteId() {
		return RouteId;
	}	
	
	public void setRouteId(String routeid) {
		this.RouteId = routeid;
	}
	public String getStartStation() {
		return StartStation;
	}	
	
	public void setStartStation(String startstation) {
		this.StartStation = startstation;
	}
	public String getEndStation() {
		return EndStation;
	}	
	
	public void setEndStation(String endstation) {
		this.EndStation = endstation;
	}
	public String getTime() {
		return Time;
	}	
	
	public void setTime(String time) {
		this.Time = time;
	}
	public String getTrainId() {
		return TrainId;
	}	
	
	public void setTrainId(String trainid) {
		this.TrainId = trainid;
	}
	
	/* all functions for reference*/
	public List<Ticket> getInTicket() {
		return InTicket;
	}	
	
	public void addInTicket(Ticket ticket) {
		this.InTicket.add(ticket);
	}
	
	public void deleteInTicket(Ticket ticket) {
		this.InTicket.remove(ticket);
	}
	public Train getUsedTrain() {
		return UsedTrain;
	}	
	
	public void setUsedTrain(Train train) {
		this.UsedTrain = train;
	}			
	


}
