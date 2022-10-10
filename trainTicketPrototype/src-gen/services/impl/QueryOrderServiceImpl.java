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

public class QueryOrderServiceImpl implements QueryOrderService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public QueryOrderServiceImpl() {
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
	public List<Order> queryByAccoutId(String accoutId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
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
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(accout) == false) 
		{ 
			/* Logic here */
			List<Order> tempsorder = new LinkedList<>();
			//no nested iterator --  iterator: select
			for (Order order : ((List<Order>)EntityManager.getAllInstancesOf("Order")))
			{
				if (order.getAccoutId().equals(accoutId))
				{
					tempsorder.add(order);
				} 
			}
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return tempsorder;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [accoutId] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public List<Order> queryTicketId(String ticketId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
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
		if (StandardOPs.oclIsundefined(ticket) == false) 
		{ 
			/* Logic here */
			List<Order> tempsorder = new LinkedList<>();
			//no nested iterator --  iterator: select
			for (Order order : ((List<Order>)EntityManager.getAllInstancesOf("Order")))
			{
				if (order.getTicketId().equals(ticketId))
				{
					tempsorder.add(order);
				} 
			}
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return tempsorder;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [ticketId] 
	}  
	
	 
	
	
	
	/* temp property for controller */
	private QueryMethod CurrentQueryMethod;
			
	/* all get and set functions for temp property*/
	public QueryMethod getCurrentQueryMethod() {
		return CurrentQueryMethod;
	}	
	
	public void setCurrentQueryMethod(QueryMethod currentquerymethod) {
		this.CurrentQueryMethod = currentquerymethod;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
