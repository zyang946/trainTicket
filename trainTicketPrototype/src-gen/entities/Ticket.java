package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Ticket implements Serializable {
	
	/* all primary attributes */
	private String TicketId;
	private String RouteId;
	private String SeatId;
	private float Price;
	private boolean IsValid;
	
	/* all references */
	private Route HasRoute; 
	private Order InOrder; 
	private Seat HasSeat; 
	private Accout BelongedAccout; 
	
	/* all get and set functions */
	public String getTicketId() {
		return TicketId;
	}	
	
	public void setTicketId(String ticketid) {
		this.TicketId = ticketid;
	}
	public String getRouteId() {
		return RouteId;
	}	
	
	public void setRouteId(String routeid) {
		this.RouteId = routeid;
	}
	public String getSeatId() {
		return SeatId;
	}	
	
	public void setSeatId(String seatid) {
		this.SeatId = seatid;
	}
	public float getPrice() {
		return Price;
	}	
	
	public void setPrice(float price) {
		this.Price = price;
	}
	public boolean getIsValid() {
		return IsValid;
	}	
	
	public void setIsValid(boolean isvalid) {
		this.IsValid = isvalid;
	}
	
	/* all functions for reference*/
	public Route getHasRoute() {
		return HasRoute;
	}	
	
	public void setHasRoute(Route route) {
		this.HasRoute = route;
	}			
	public Order getInOrder() {
		return InOrder;
	}	
	
	public void setInOrder(Order order) {
		this.InOrder = order;
	}			
	public Seat getHasSeat() {
		return HasSeat;
	}	
	
	public void setHasSeat(Seat seat) {
		this.HasSeat = seat;
	}			
	public Accout getBelongedAccout() {
		return BelongedAccout;
	}	
	
	public void setBelongedAccout(Accout accout) {
		this.BelongedAccout = accout;
	}			
	


}
