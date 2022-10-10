package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Order implements Serializable {
	
	/* all primary attributes */
	private String OrderId;
	private String TicketId;
	private String AccoutId;
	private String CreateTime;
	private OrderStatus OrderStatus;
	
	/* all references */
	private Ticket HasTicket; 
	private Accout HasAccout; 
	
	/* all get and set functions */
	public String getOrderId() {
		return OrderId;
	}	
	
	public void setOrderId(String orderid) {
		this.OrderId = orderid;
	}
	public String getTicketId() {
		return TicketId;
	}	
	
	public void setTicketId(String ticketid) {
		this.TicketId = ticketid;
	}
	public String getAccoutId() {
		return AccoutId;
	}	
	
	public void setAccoutId(String accoutid) {
		this.AccoutId = accoutid;
	}
	public String getCreateTime() {
		return CreateTime;
	}	
	
	public void setCreateTime(String createtime) {
		this.CreateTime = createtime;
	}
	public OrderStatus getOrderStatus() {
		return OrderStatus;
	}	
	
	public void setOrderStatus(OrderStatus orderstatus) {
		this.OrderStatus = orderstatus;
	}
	
	/* all functions for reference*/
	public Ticket getHasTicket() {
		return HasTicket;
	}	
	
	public void setHasTicket(Ticket ticket) {
		this.HasTicket = ticket;
	}			
	public Accout getHasAccout() {
		return HasAccout;
	}	
	
	public void setHasAccout(Accout accout) {
		this.HasAccout = accout;
	}			
	


}
