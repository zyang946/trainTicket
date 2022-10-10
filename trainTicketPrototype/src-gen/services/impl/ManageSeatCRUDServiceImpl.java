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

public class ManageSeatCRUDServiceImpl implements ManageSeatCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageSeatCRUDServiceImpl() {
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
	public boolean createSeat(String seatid, String trainid, SeatType seattype) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get seat
		Seat seat = null;
		//no nested iterator --  iterator: any previous:any
		for (Seat sea : (List<Seat>)EntityManager.getAllInstancesOf("Seat"))
		{
			if (sea.getSeatId().equals(seatid))
			{
				seat = sea;
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
		if (StandardOPs.oclIsundefined(seat) == true && StandardOPs.oclIsundefined(train) == false) 
		{ 
			/* Logic here */
			Seat sea = null;
			sea = (Seat) EntityManager.createObject("Seat");
			sea.setSeatId(seatid);
			sea.setTrainId(trainid);
			sea.setSeatType(seattype);
			EntityManager.addObject("Seat", sea);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			sea.getSeatId() == seatid
			 && 
			sea.getTrainId() == trainid
			 && 
			sea.getSeatType() == seattype
			 && 
			StandardOPs.includes(((List<Seat>)EntityManager.getAllInstancesOf("Seat")), sea)
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
		//string parameters: [seatid, trainid] 
		//all relevant vars : sea
		//all relevant entities : Seat
	}  
	
	static {opINVRelatedEntity.put("createSeat", Arrays.asList("Seat"));}
	 
	@SuppressWarnings("unchecked")
	public Seat querySeat(String seatid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get seat
		Seat seat = null;
		//no nested iterator --  iterator: any previous:any
		for (Seat sea : (List<Seat>)EntityManager.getAllInstancesOf("Seat"))
		{
			if (sea.getSeatId().equals(seatid))
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
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return seat;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [seatid] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean modifySeat(String seatid, String trainid, SeatType seattype) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get seat
		Seat seat = null;
		//no nested iterator --  iterator: any previous:any
		for (Seat sea : (List<Seat>)EntityManager.getAllInstancesOf("Seat"))
		{
			if (sea.getSeatId().equals(seatid))
			{
				seat = sea;
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
		if (StandardOPs.oclIsundefined(seat) == false && StandardOPs.oclIsundefined(train) == false) 
		{ 
			/* Logic here */
			seat.setSeatId(seatid);
			seat.setTrainId(trainid);
			seat.setSeatType(seattype);
			
			
			refresh();
			// post-condition checking
			if (!(seat.getSeatId() == seatid
			 && 
			seat.getTrainId() == trainid
			 && 
			seat.getSeatType() == seattype
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
		//string parameters: [seatid, trainid] 
		//all relevant vars : seat
		//all relevant entities : Seat
	}  
	
	static {opINVRelatedEntity.put("modifySeat", Arrays.asList("Seat"));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteSeat(String seatid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get seat
		Seat seat = null;
		//no nested iterator --  iterator: any previous:any
		for (Seat sea : (List<Seat>)EntityManager.getAllInstancesOf("Seat"))
		{
			if (sea.getSeatId().equals(seatid))
			{
				seat = sea;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(seat) == false && StandardOPs.includes(((List<Seat>)EntityManager.getAllInstancesOf("Seat")), seat)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Seat", seat);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Seat>)EntityManager.getAllInstancesOf("Seat")), seat)
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
		//string parameters: [seatid] 
		//all relevant vars : seat
		//all relevant entities : Seat
	}  
	
	static {opINVRelatedEntity.put("deleteSeat", Arrays.asList("Seat"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
