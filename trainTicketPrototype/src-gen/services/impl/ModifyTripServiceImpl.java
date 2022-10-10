package services.impl;

import services.*;
import entities.*;
import java.util.List;
import java.util.LinkedList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Arrays;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import org.apache.commons.lang3.SerializationUtils;
import java.util.Iterator;

public class ModifyTripServiceImpl implements ModifyTripService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ModifyTripServiceImpl() {
		services = new ThirdPartyServicesImpl();
	}

	
	//Shared variable from system services
	
	/* Shared variable from system services and get()/set() methods */
			
	/* all get and set functions for temp property*/
				
	
	
	/* Generate inject for sharing temp variables between use cases in system service */
	public void refresh() {
		TrainTicketSystem trainticketsystem_service = (TrainTicketSystem) ServiceManager.getAllInstancesOf("TrainTicketSystem").get(0);
	}
	
	/* Generate buiness logic according to functional requirement */
	@SuppressWarnings("unchecked")
	public boolean updateTicket(String accoutId, String ticketId, String newRouteId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get ticket
		Ticket ticket = null;
		//no nested iterator --  iterator: any previous:any
		for (Ticket tic : (List<Ticket>)EntityManager.getAllInstancesOf("Ticket"))
		{
			if (tic.getTicketId().equals(ticketId) && tic.getIsValid() == true)
			{
				ticket = tic;
				break;
			}
				
			
		}
		//Get accout
		Accout accout = null;
		//no nested iterator --  iterator: any previous:any
		for (Accout acc : (List<Accout>)EntityManager.getAllInstancesOf("Accout"))
		{
			if (acc.getAccoutId().equals(accoutId))
			{
				accout = acc;
				break;
			}
				
			
		}
		//Get route
		Route route = null;
		//no nested iterator --  iterator: any previous:any
		for (Route rou : (List<Route>)EntityManager.getAllInstancesOf("Route"))
		{
			if (rou.getRouteId().equals(newRouteId))
			{
				route = rou;
				break;
			}
				
			
		}
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getAccoutId().equals(accoutId) && ord.getTicketId().equals(ticketId) && ord.getOrderStatus() == OrderStatus.PAID)
			{
				order = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(ticket) == false && StandardOPs.oclIsundefined(accout) == false && StandardOPs.oclIsundefined(route) == false && StandardOPs.oclIsundefined(order) == false) 
		{ 
			/* Logic here */
			ticket.setRouteId(newRouteId);
			ticket.setIsValid(false);
			CurrentTicket = ticket;
			CurrentOrder = order;
			CurrentAccout = accout;
			CurrentRoute = route;
			
			
			refresh();
			// post-condition checking
			if (!(ticket.getRouteId() == newRouteId
			 && 
			ticket.getIsValid() == false
			 && 
			CurrentTicket == ticket
			 && 
			CurrentOrder == order
			 && 
			CurrentAccout == accout
			 && 
			CurrentRoute == route
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [accoutId, ticketId, newRouteId] 
		//all relevant vars : ticket
		//all relevant entities : Ticket
	}  
	
	static {opINVRelatedEntity.put("updateTicket", Arrays.asList("Ticket"));}
	 
	@SuppressWarnings("unchecked")
	public boolean updateOrder(String time) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/
 
		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			CurrentOrder.setCreateTime(time);
			CurrentOrder.setOrderStatus(OrderStatus.NOTPAID);
			
			
			refresh();
			// post-condition checking
			if (!(CurrentOrder.getCreateTime() == time
			 && 
			CurrentOrder.getOrderStatus() == OrderStatus.NOTPAID
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [time] 
		//all relevant vars : CurrentOrder
		//all relevant entities : 
	}  
	
	static {opINVRelatedEntity.put("updateOrder", Arrays.asList(""));}
	 
	@SuppressWarnings("unchecked")
	public List<Seat> showSeatsByRouteId() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/
 
		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			List<Seat> tempssea = new LinkedList<>();
			//no nested iterator --  iterator: select
			for (Seat sea : ((List<Seat>)EntityManager.getAllInstancesOf("Seat")))
			{
				if (sea.getTrainId() == CurrentRoute.getTrainId())
				{
					tempssea.add(sea);
				} 
			}
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return tempssea;
		}
		else
		{
			throw new PreconditionException();
		}
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean selectNewSeat(String seatId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get seat
		Seat seat = null;
		//no nested iterator --  iterator: any previous:any
		for (Seat sea : (List<Seat>)EntityManager.getAllInstancesOf("Seat"))
		{
			if (sea.getSeatId().equals(seatId) && sea.getTrainId() == CurrentRoute.getTrainId())
			{
				seat = sea;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(seat) == false) 
		{ 
			/* Logic here */
			this.getCurrentTicket().setSeatId(seatId);
			
			
			refresh();
			// post-condition checking
			if (!(this.getCurrentTicket().getSeatId() == seatId
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [seatId] 
		//all relevant vars : this
		//all relevant entities : 
	}  
	
	static {opINVRelatedEntity.put("selectNewSeat", Arrays.asList(""));}
	 
	@SuppressWarnings("unchecked")
	public boolean payDifference(float difference) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/
 
		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			CurrentTicket.setIsValid(true);
			CurrentTicket.setPrice(CurrentTicket.getPrice()+difference);
			CurrentOrder.setOrderStatus(OrderStatus.PAID);
			services.sendNotification(CurrentAccout.getPhoneNumber());
			
			
			refresh();
			// post-condition checking
			if (!(CurrentTicket.getIsValid() == true
			 && 
			CurrentTicket.getPrice() == CurrentTicket.getPrice()+difference
			 && 
			CurrentOrder.getOrderStatus() == OrderStatus.PAID
			 && 
			true
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : CurrentOrder CurrentTicket
		//all relevant entities :  
	}  
	
	static {opINVRelatedEntity.put("payDifference", Arrays.asList("",""));}
	 
	
	
	
	/* temp property for controller */
	private Order CurrentOrder;
	private Ticket CurrentTicket;
	private Accout CurrentAccout;
	private Route CurrentRoute;
			
	/* all get and set functions for temp property*/
	public Order getCurrentOrder() {
		return CurrentOrder;
	}	
	
	public void setCurrentOrder(Order currentorder) {
		this.CurrentOrder = currentorder;
	}
	public Ticket getCurrentTicket() {
		return CurrentTicket;
	}	
	
	public void setCurrentTicket(Ticket currentticket) {
		this.CurrentTicket = currentticket;
	}
	public Accout getCurrentAccout() {
		return CurrentAccout;
	}	
	
	public void setCurrentAccout(Accout currentaccout) {
		this.CurrentAccout = currentaccout;
	}
	public Route getCurrentRoute() {
		return CurrentRoute;
	}	
	
	public void setCurrentRoute(Route currentroute) {
		this.CurrentRoute = currentroute;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
