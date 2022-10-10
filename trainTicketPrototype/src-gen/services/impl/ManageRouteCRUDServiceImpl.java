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

public class ManageRouteCRUDServiceImpl implements ManageRouteCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageRouteCRUDServiceImpl() {
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
	public boolean createRoute(String routeid, String startstation, String endstation, String time, String trainid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get route
		Route route = null;
		//no nested iterator --  iterator: any previous:any
		for (Route rou : (List<Route>)EntityManager.getAllInstancesOf("Route"))
		{
			if (rou.getRouteId().equals(routeid))
			{
				route = rou;
				break;
			}
				
			
		}
		//Get train
		Train train = null;
		//no nested iterator --  iterator: any previous:any
		for (Train tra : (List<Train>)EntityManager.getAllInstancesOf("Train"))
		{
			if (tra.getTrainId().equals(trainid))
			{
				train = tra;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(route) == true && StandardOPs.oclIsundefined(train) == false) 
		{ 
			/* Logic here */
			Route rou = null;
			rou = (Route) EntityManager.createObject("Route");
			rou.setRouteId(routeid);
			rou.setStartStation(startstation);
			rou.setEndStation(endstation);
			rou.setTime(time);
			rou.setTrainId(trainid);
			EntityManager.addObject("Route", rou);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			rou.getRouteId() == routeid
			 && 
			rou.getStartStation() == startstation
			 && 
			rou.getEndStation() == endstation
			 && 
			rou.getTime() == time
			 && 
			rou.getTrainId() == trainid
			 && 
			StandardOPs.includes(((List<Route>)EntityManager.getAllInstancesOf("Route")), rou)
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
		//string parameters: [routeid, startstation, endstation, time, trainid] 
		//all relevant vars : rou
		//all relevant entities : Route
	}  
	
	static {opINVRelatedEntity.put("createRoute", Arrays.asList("Route"));}
	 
	@SuppressWarnings("unchecked")
	public Route queryRoute(String routeid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get route
		Route route = null;
		//no nested iterator --  iterator: any previous:any
		for (Route rou : (List<Route>)EntityManager.getAllInstancesOf("Route"))
		{
			if (rou.getRouteId().equals(routeid))
			{
				route = rou;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(route) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return route;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [routeid] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean modifyRoute(String routeid, String startstation, String endstation, String time, String trainid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get route
		Route route = null;
		//no nested iterator --  iterator: any previous:any
		for (Route rou : (List<Route>)EntityManager.getAllInstancesOf("Route"))
		{
			if (rou.getRouteId().equals(routeid))
			{
				route = rou;
				break;
			}
				
			
		}
		//Get train
		Train train = null;
		//no nested iterator --  iterator: any previous:any
		for (Train tra : (List<Train>)EntityManager.getAllInstancesOf("Train"))
		{
			if (tra.getTrainId().equals(trainid))
			{
				train = tra;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(route) == false && StandardOPs.oclIsundefined(train) == false) 
		{ 
			/* Logic here */
			route.setRouteId(routeid);
			route.setStartStation(startstation);
			route.setEndStation(endstation);
			route.setTime(time);
			route.setTrainId(trainid);
			
			
			refresh();
			// post-condition checking
			if (!(route.getRouteId() == routeid
			 && 
			route.getStartStation() == startstation
			 && 
			route.getEndStation() == endstation
			 && 
			route.getTime() == time
			 && 
			route.getTrainId() == trainid
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
		//string parameters: [routeid, startstation, endstation, time, trainid] 
		//all relevant vars : route
		//all relevant entities : Route
	}  
	
	static {opINVRelatedEntity.put("modifyRoute", Arrays.asList("Route"));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteRoute(String routeid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get route
		Route route = null;
		//no nested iterator --  iterator: any previous:any
		for (Route rou : (List<Route>)EntityManager.getAllInstancesOf("Route"))
		{
			if (rou.getRouteId().equals(routeid))
			{
				route = rou;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(route) == false && StandardOPs.includes(((List<Route>)EntityManager.getAllInstancesOf("Route")), route)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Route", route);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Route>)EntityManager.getAllInstancesOf("Route")), route)
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
		//string parameters: [routeid] 
		//all relevant vars : route
		//all relevant entities : Route
	}  
	
	static {opINVRelatedEntity.put("deleteRoute", Arrays.asList("Route"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
