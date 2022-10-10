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

public class ManageOrderCRUDServiceImpl implements ManageOrderCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageOrderCRUDServiceImpl() {
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
	public boolean createOrder(String orderid, String ticketid, String accoutid, String createtime, OrderStatus orderstatus) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getOrderId().equals(orderid))
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
			ord.setOrderId(orderid);
			ord.setTicketId(ticketid);
			ord.setAccoutId(accoutid);
			ord.setCreateTime(createtime);
			ord.setOrderStatus(orderstatus);
			EntityManager.addObject("Order", ord);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			ord.getOrderId() == orderid
			 && 
			ord.getTicketId() == ticketid
			 && 
			ord.getAccoutId() == accoutid
			 && 
			ord.getCreateTime() == createtime
			 && 
			ord.getOrderStatus() == orderstatus
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
		//string parameters: [orderid, ticketid, accoutid, createtime] 
		//all relevant vars : ord
		//all relevant entities : Order
	}  
	
	static {opINVRelatedEntity.put("createOrder", Arrays.asList("Order"));}
	 
	@SuppressWarnings("unchecked")
	public Order queryOrder(String orderid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getOrderId().equals(orderid))
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
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return order;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [orderid] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean modifyOrder(String orderid, String ticketid, String accoutid, String createtime, OrderStatus orderstatus) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getOrderId().equals(orderid))
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
			order.setOrderId(orderid);
			order.setTicketId(ticketid);
			order.setAccoutId(accoutid);
			order.setCreateTime(createtime);
			order.setOrderStatus(orderstatus);
			
			
			refresh();
			// post-condition checking
			if (!(order.getOrderId() == orderid
			 && 
			order.getTicketId() == ticketid
			 && 
			order.getAccoutId() == accoutid
			 && 
			order.getCreateTime() == createtime
			 && 
			order.getOrderStatus() == orderstatus
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
		//string parameters: [orderid, ticketid, accoutid, createtime] 
		//all relevant vars : order
		//all relevant entities : Order
	}  
	
	static {opINVRelatedEntity.put("modifyOrder", Arrays.asList("Order"));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteOrder(String orderid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getOrderId().equals(orderid))
			{
				order = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(order) == false && StandardOPs.includes(((List<Order>)EntityManager.getAllInstancesOf("Order")), order)) 
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
		//string parameters: [orderid] 
		//all relevant vars : order
		//all relevant entities : Order
	}  
	
	static {opINVRelatedEntity.put("deleteOrder", Arrays.asList("Order"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
