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

public class ManageTicketCRUDServiceImpl implements ManageTicketCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageTicketCRUDServiceImpl() {
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
	public boolean createTicket(String ticketid, String routeid, String seatid, float price, boolean isvalid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get ticket
		Ticket ticket = null;
		//no nested iterator --  iterator: any previous:any
		for (Ticket tic : (List<Ticket>)EntityManager.getAllInstancesOf("Ticket"))
		{
			if (tic.getTicketId().equals(ticketid))
			{
				ticket = tic;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(ticket) == true) 
		{ 
			/* Logic here */
			Ticket tic = null;
			tic = (Ticket) EntityManager.createObject("Ticket");
			tic.setTicketId(ticketid);
			tic.setRouteId(routeid);
			tic.setSeatId(seatid);
			tic.setPrice(price);
			tic.setIsValid(isvalid);
			EntityManager.addObject("Ticket", tic);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			tic.getTicketId() == ticketid
			 && 
			tic.getRouteId() == routeid
			 && 
			tic.getSeatId() == seatid
			 && 
			tic.getPrice() == price
			 && 
			tic.getIsValid() == isvalid
			 && 
			StandardOPs.includes(((List<Ticket>)EntityManager.getAllInstancesOf("Ticket")), tic)
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
		//string parameters: [ticketid, routeid, seatid] 
		//all relevant vars : tic
		//all relevant entities : Ticket
	}  
	
	static {opINVRelatedEntity.put("createTicket", Arrays.asList("Ticket"));}
	 
	@SuppressWarnings("unchecked")
	public Ticket queryTicket(String ticketid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get ticket
		Ticket ticket = null;
		//no nested iterator --  iterator: any previous:any
		for (Ticket tic : (List<Ticket>)EntityManager.getAllInstancesOf("Ticket"))
		{
			if (tic.getTicketId().equals(ticketid))
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
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return ticket;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [ticketid] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean modifyTicket(String ticketid, String routeid, String seatid, float price, boolean isvalid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get ticket
		Ticket ticket = null;
		//no nested iterator --  iterator: any previous:any
		for (Ticket tic : (List<Ticket>)EntityManager.getAllInstancesOf("Ticket"))
		{
			if (tic.getTicketId().equals(ticketid))
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
			ticket.setTicketId(ticketid);
			ticket.setRouteId(routeid);
			ticket.setSeatId(seatid);
			ticket.setPrice(price);
			ticket.setIsValid(isvalid);
			
			
			refresh();
			// post-condition checking
			if (!(ticket.getTicketId() == ticketid
			 && 
			ticket.getRouteId() == routeid
			 && 
			ticket.getSeatId() == seatid
			 && 
			ticket.getPrice() == price
			 && 
			ticket.getIsValid() == isvalid
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
		//string parameters: [ticketid, routeid, seatid] 
		//all relevant vars : ticket
		//all relevant entities : Ticket
	}  
	
	static {opINVRelatedEntity.put("modifyTicket", Arrays.asList("Ticket"));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteTicket(String ticketid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get ticket
		Ticket ticket = null;
		//no nested iterator --  iterator: any previous:any
		for (Ticket tic : (List<Ticket>)EntityManager.getAllInstancesOf("Ticket"))
		{
			if (tic.getTicketId().equals(ticketid))
			{
				ticket = tic;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(ticket) == false && StandardOPs.includes(((List<Ticket>)EntityManager.getAllInstancesOf("Ticket")), ticket)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Ticket", ticket);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Ticket>)EntityManager.getAllInstancesOf("Ticket")), ticket)
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
		//string parameters: [ticketid] 
		//all relevant vars : ticket
		//all relevant entities : Ticket
	}  
	
	static {opINVRelatedEntity.put("deleteTicket", Arrays.asList("Ticket"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
