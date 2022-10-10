package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Accout implements Serializable {
	
	/* all primary attributes */
	private String AccoutId;
	private String Name;
	private String PhoneNumber;
	
	/* all references */
	private Seat AccouttoSeat; 
	private Order InOrder; 
	private List<Ticket> HasTicket = new LinkedList<Ticket>(); 
	
	/* all get and set functions */
	public String getAccoutId() {
		return AccoutId;
	}	
	
	public void setAccoutId(String accoutid) {
		this.AccoutId = accoutid;
	}
	public String getName() {
		return Name;
	}	
	
	public void setName(String name) {
		this.Name = name;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}	
	
	public void setPhoneNumber(String phonenumber) {
		this.PhoneNumber = phonenumber;
	}
	
	/* all functions for reference*/
	public Seat getAccouttoSeat() {
		return AccouttoSeat;
	}	
	
	public void setAccouttoSeat(Seat seat) {
		this.AccouttoSeat = seat;
	}			
	public Order getInOrder() {
		return InOrder;
	}	
	
	public void setInOrder(Order order) {
		this.InOrder = order;
	}			
	public List<Ticket> getHasTicket() {
		return HasTicket;
	}	
	
	public void addHasTicket(Ticket ticket) {
		this.HasTicket.add(ticket);
	}
	
	public void deleteHasTicket(Ticket ticket) {
		this.HasTicket.remove(ticket);
	}
	


}
