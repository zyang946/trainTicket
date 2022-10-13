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

public class CancelTripServiceImpl implements CancelTripService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public CancelTripServiceImpl() {
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
	public boolean checkCancelTrip(String accoutId, String ticketId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
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
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(order) == false && StandardOPs.oclIsundefined(ticket) == false) 
		{ 
			/* Logic here */
			CurrentOrder = order;
			CurrentTicket = ticket;
			
			
			refresh();
			// post-condition checking
			if (!(CurrentOrder == order
			 && 
			CurrentTicket == ticket
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
		//string parameters: [accoutId, ticketId] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean cancelTicket() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/
 
		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			CurrentOrder.setOrderStatus(OrderStatus.CANCELED);
			CurrentTicket.setIsValid(false);
			
			
			refresh();
			// post-condition checking
			if (!(CurrentOrder.getOrderStatus() == OrderStatus.CANCELED
			 && 
			CurrentTicket.getIsValid() == false
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
	
	static {opINVRelatedEntity.put("cancelTicket", Arrays.asList("",""));}
	 
	@SuppressWarnings("unchecked")
	public float refund() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/
 
		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return CurrentTicket.getPrice();
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : CurrentTicket
		//all relevant entities : 
	}  
	
	static {opINVRelatedEntity.put("refund", Arrays.asList(""));}
	 
	
	
	
	/* temp property for controller */
	private Order CurrentOrder;
	private Ticket CurrentTicket;
			
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
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
