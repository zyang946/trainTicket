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

public class DeleteOverdueOrderServiceImpl implements DeleteOverdueOrderService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public DeleteOverdueOrderServiceImpl() {
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
	public boolean deleteOverdueTicket(String ticketId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get ticket
		Ticket ticket = null;
		//no nested iterator --  iterator: any previous:any
		for (Ticket tic : (List<Ticket>)EntityManager.getAllInstancesOf("Ticket"))
		{
			if (tic.getTicketId().equals(ticketId) && tic.getIsValid() == false)
			{
				ticket = tic;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(ticket) == false) 
		{ 
			/* Logic here */
			this.setCurrentTicketId(ticketId);
			EntityManager.deleteObject("Ticket", ticket);
			
			
			refresh();
			// post-condition checking
			if (!(this.getCurrentTicketId() == ticketId
			 && 
			StandardOPs.excludes(((List<Ticket>)EntityManager.getAllInstancesOf("Ticket")), ticket)
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
		//string parameters: [ticketId] 
		//all relevant vars : ticket this
		//all relevant entities : Ticket 
	}  
	
	static {opINVRelatedEntity.put("deleteOverdueTicket", Arrays.asList("Ticket",""));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteOverdueOrder() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getTicketId() == CurrentTicketId && ord.getOrderStatus() == OrderStatus.CANCELED)
			{
				order = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(order) == false) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Order", order);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Order>)EntityManager.getAllInstancesOf("Order")), order)
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
		//all relevant vars : order
		//all relevant entities : Order
	}  
	
	static {opINVRelatedEntity.put("deleteOverdueOrder", Arrays.asList("Order"));}
	 
	
	
	
	/* temp property for controller */
	private String CurrentTicketId;
			
	/* all get and set functions for temp property*/
	public String getCurrentTicketId() {
		return CurrentTicketId;
	}	
	
	public void setCurrentTicketId(String currentticketid) {
		this.CurrentTicketId = currentticketid;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
