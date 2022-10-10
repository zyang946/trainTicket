package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Seat implements Serializable {
	
	/* all primary attributes */
	private String SeatId;
	private String TrainId;
	private SeatType SeatType;
	
	/* all references */
	private List<Ticket> InTicket = new LinkedList<Ticket>(); 
	
	/* all get and set functions */
	public String getSeatId() {
		return SeatId;
	}	
	
	public void setSeatId(String seatid) {
		this.SeatId = seatid;
	}
	public String getTrainId() {
		return TrainId;
	}	
	
	public void setTrainId(String trainid) {
		this.TrainId = trainid;
	}
	public SeatType getSeatType() {
		return SeatType;
	}	
	
	public void setSeatType(SeatType seattype) {
		this.SeatType = seattype;
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
	


}
