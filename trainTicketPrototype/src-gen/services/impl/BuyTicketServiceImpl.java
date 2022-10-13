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

public class BuyTicketServiceImpl implements BuyTicketService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public BuyTicketServiceImpl() {
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
	public boolean buyTicket(String accoutId, String routeId, String ticketId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get route
		Route route = null;
		//no nested iterator --  iterator: any previous:any
		for (Route rou : (List<Route>)EntityManager.getAllInstancesOf("Route"))
		{
			if (rou.getRouteId().equals(routeId))
			{
				route = rou;
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
		//Get ticket
		Ticket ticket = null;
		//no nested iterator --  iterator: any previous:any
		for (Ticket tic : (List<Ticket>)EntityManager.getAllInstancesOf("Ticket"))
		{
			if (tic.getTicketId().equals(ticketId))
			{
				ticket = tic;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(route) == false && StandardOPs.oclIsundefined(accout) == false && StandardOPs.oclIsundefined(ticket) == true) 
		{ 
			/* Logic here */
			Ticket tic = null;
			tic = (Ticket) EntityManager.createObject("Ticket");
			tic.setTicketId(ticketId);
			tic.setRouteId(routeId);
			tic.setIsValid(false);
			EntityManager.addObject("Ticket", tic);
			CurrentTicket = tic;
			CurrentAccout = accout;
			CurrentRoute = route;
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			tic.getTicketId() == ticketId
			 && 
			tic.getRouteId() == routeId
			 && 
			tic.getIsValid() == false
			 && 
			StandardOPs.includes(((List<Ticket>)EntityManager.getAllInstancesOf("Ticket")), tic)
			 && 
			CurrentTicket == tic
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
		//string parameters: [accoutId, routeId, ticketId] 
		//all relevant vars : tic
		//all relevant entities : Ticket
	}  
	
	static {opINVRelatedEntity.put("buyTicket", Arrays.asList("Ticket"));}
	 
	@SuppressWarnings("unchecked")
	public boolean saveOrder(String orderId, String boughtTime) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getOrderId().equals(orderId))
			{
				order = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(order) == true) 
		{ 
			/* Logic here */
			Order ord = null;
			ord = (Order) EntityManager.createObject("Order");
			ord.setOrderId(orderId);
			ord.setTicketId(CurrentTicket.getTicketId());
			ord.setAccoutId(CurrentAccout.getAccoutId());
			ord.setCreateTime(boughtTime);
			ord.setOrderStatus(OrderStatus.NOTPAID);
			CurrentOrder = ord;
			EntityManager.addObject("Order", ord);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			ord.getOrderId() == orderId
			 && 
			ord.getTicketId() == CurrentTicket.getTicketId()
			 && 
			ord.getAccoutId() == CurrentAccout.getAccoutId()
			 && 
			ord.getCreateTime() == boughtTime
			 && 
			ord.getOrderStatus() == OrderStatus.NOTPAID
			 && 
			CurrentOrder == ord
			 && 
			StandardOPs.includes(((List<Order>)EntityManager.getAllInstancesOf("Order")), ord)
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
		//string parameters: [orderId, boughtTime] 
		//all relevant vars : ord
		//all relevant entities : Order
	}  
	
	static {opINVRelatedEntity.put("saveOrder", Arrays.asList("Order"));}
	 
	@SuppressWarnings("unchecked")
	public List<Seat> showSeats() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/
 
		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			List<Seat> tempssea = new LinkedList<>();
			//no nested iterator --  iterator: select
			for (Seat sea : ((List<Seat>)EntityManager.getAllInstancesOf("Seat")))
			{
				if (sea.getTrainId().equals(CurrentRoute.getTrainId()))
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
	public boolean selectSeat(String seatId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get seat
		Seat seat = null;
		//no nested iterator --  iterator: any previous:any
		for (Seat sea : (List<Seat>)EntityManager.getAllInstancesOf("Seat"))
		{
			if (sea.getTrainId().equals(CurrentRoute.getTrainId()) && sea.getSeatId().equals(seatId))
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
			CurrentTicket.setSeatId(seatId);
			
			
			refresh();
			// post-condition checking
			if (!(CurrentTicket.getSeatId() == seatId
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
		//all relevant vars : CurrentTicket
		//all relevant entities : 
	}  
	
	static {opINVRelatedEntity.put("selectSeat", Arrays.asList(""));}
	 
	@SuppressWarnings("unchecked")
	public boolean pay(float price) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/
 
		/* check precondition */
		if (price > 0) 
		{ 
			/* Logic here */
			CurrentTicket.setPrice(price);
			CurrentTicket.setIsValid(true);
			CurrentOrder.setOrderStatus(OrderStatus.PAID);
			services.sendNotification(CurrentAccout.getPhoneNumber());
			
			
			refresh();
			// post-condition checking
			if (!(CurrentTicket.getPrice() == price
			 && 
			CurrentTicket.getIsValid() == true
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
	
	static {opINVRelatedEntity.put("pay", Arrays.asList("",""));}
	 
	
	
	
	/* temp property for controller */
	private Order CurrentOrder;
	private Accout CurrentAccout;
	private Ticket CurrentTicket;
	private Route CurrentRoute;
			
	/* all get and set functions for temp property*/
	public Order getCurrentOrder() {
		return CurrentOrder;
	}	
	
	public void setCurrentOrder(Order currentorder) {
		this.CurrentOrder = currentorder;
	}
	public Accout getCurrentAccout() {
		return CurrentAccout;
	}	
	
	public void setCurrentAccout(Accout currentaccout) {
		this.CurrentAccout = currentaccout;
	}
	public Ticket getCurrentTicket() {
		return CurrentTicket;
	}	
	
	public void setCurrentTicket(Ticket currentticket) {
		this.CurrentTicket = currentticket;
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
