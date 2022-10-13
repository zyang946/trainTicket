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

public class TrainTicketSystemImpl implements TrainTicketSystem, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public TrainTicketSystemImpl() {
		services = new ThirdPartyServicesImpl();
	}

	public void refresh() {
		CreateTripService createtripservice_service = (CreateTripService) ServiceManager
				.getAllInstancesOf("CreateTripService").get(0);
		QueryOrderService queryorderservice_service = (QueryOrderService) ServiceManager
				.getAllInstancesOf("QueryOrderService").get(0);
		ManageRouteCRUDService manageroutecrudservice_service = (ManageRouteCRUDService) ServiceManager
				.getAllInstancesOf("ManageRouteCRUDService").get(0);
		ManageTrainCRUDService managetraincrudservice_service = (ManageTrainCRUDService) ServiceManager
				.getAllInstancesOf("ManageTrainCRUDService").get(0);
		ManageTicketCRUDService manageticketcrudservice_service = (ManageTicketCRUDService) ServiceManager
				.getAllInstancesOf("ManageTicketCRUDService").get(0);
		ManageOrderCRUDService manageordercrudservice_service = (ManageOrderCRUDService) ServiceManager
				.getAllInstancesOf("ManageOrderCRUDService").get(0);
		ManageAccoutCRUDService manageaccoutcrudservice_service = (ManageAccoutCRUDService) ServiceManager
				.getAllInstancesOf("ManageAccoutCRUDService").get(0);
		ManageSeatCRUDService manageseatcrudservice_service = (ManageSeatCRUDService) ServiceManager
				.getAllInstancesOf("ManageSeatCRUDService").get(0);
		ModifyTripService modifytripservice_service = (ModifyTripService) ServiceManager
				.getAllInstancesOf("ModifyTripService").get(0);
		DeleteOverdueOrderService deleteoverdueorderservice_service = (DeleteOverdueOrderService) ServiceManager
				.getAllInstancesOf("DeleteOverdueOrderService").get(0);
		CancelTripService canceltripservice_service = (CancelTripService) ServiceManager
				.getAllInstancesOf("CancelTripService").get(0);
	}			
	
	/* Generate buiness logic according to functional requirement */
	@SuppressWarnings("unchecked")
	public List<Route> queryInformation(String startStation, String endStation, String time) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/
 
		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			List<Route> tempsroute = new LinkedList<>();
			//no nested iterator --  iterator: select
			for (Route route : ((List<Route>)EntityManager.getAllInstancesOf("Route")))
			{
				if (route.getStartStation().equals(startStation) && route.getEndStation().equals(endStation) && route.getTime().equals(time))
				{
					tempsroute.add(route);
				} 
			}
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return tempsroute;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [startStation, endStation, time] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean cancelTrip(String ticketId, String accoutId) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
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
		//Get order
		Order order = null;
		//no nested iterator --  iterator: any previous:any
		for (Order ord : (List<Order>)EntityManager.getAllInstancesOf("Order"))
		{
			if (ord.getTicketId().equals(ticketId) && ord.getAccoutId().equals(accoutId) && ord.getOrderStatus() == OrderStatus.PAID)
			{
				order = ord;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(ticket) == false && StandardOPs.oclIsundefined(accout) == false && StandardOPs.oclIsundefined(order) == false) 
		{ 
			/* Logic here */
			ticket.setIsValid(false);
			order.setOrderStatus(OrderStatus.CANCELED);
			
			
			refresh();
			// post-condition checking
			if (!(ticket.getIsValid() == false
			 && 
			order.getOrderStatus() == OrderStatus.CANCELED
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
		//string parameters: [ticketId, accoutId] 
		//all relevant vars : ticket order
		//all relevant entities : Ticket Order
	}  
	
	static {opINVRelatedEntity.put("cancelTrip", Arrays.asList("Ticket","Order"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
