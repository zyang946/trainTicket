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

public class ManageTrainCRUDServiceImpl implements ManageTrainCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageTrainCRUDServiceImpl() {
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
	public boolean createTrain(String trainid, String name, TrainType traintype) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
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
		if (StandardOPs.oclIsundefined(train) == true) 
		{ 
			/* Logic here */
			Train tra = null;
			tra = (Train) EntityManager.createObject("Train");
			tra.setTrainId(trainid);
			tra.setName(name);
			tra.setTrainType(traintype);
			EntityManager.addObject("Train", tra);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			tra.getTrainId() == trainid
			 && 
			tra.getName() == name
			 && 
			tra.getTrainType() == traintype
			 && 
			StandardOPs.includes(((List<Train>)EntityManager.getAllInstancesOf("Train")), tra)
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
		//string parameters: [trainid, name] 
		//all relevant vars : tra
		//all relevant entities : Train
	}  
	
	static {opINVRelatedEntity.put("createTrain", Arrays.asList("Train"));}
	 
	@SuppressWarnings("unchecked")
	public Train queryTrain(String trainid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
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
		if (StandardOPs.oclIsundefined(train) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return train;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [trainid] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean modifyTrain(String trainid, String name, TrainType traintype) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
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
		if (StandardOPs.oclIsundefined(train) == false) 
		{ 
			/* Logic here */
			train.setTrainId(trainid);
			train.setName(name);
			train.setTrainType(traintype);
			
			
			refresh();
			// post-condition checking
			if (!(train.getTrainId() == trainid
			 && 
			train.getName() == name
			 && 
			train.getTrainType() == traintype
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
		//string parameters: [trainid, name] 
		//all relevant vars : train
		//all relevant entities : Train
	}  
	
	static {opINVRelatedEntity.put("modifyTrain", Arrays.asList("Train"));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteTrain(String trainid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
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
		if (StandardOPs.oclIsundefined(train) == false && StandardOPs.includes(((List<Train>)EntityManager.getAllInstancesOf("Train")), train)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Train", train);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Train>)EntityManager.getAllInstancesOf("Train")), train)
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
		//string parameters: [trainid] 
		//all relevant vars : train
		//all relevant entities : Train
	}  
	
	static {opINVRelatedEntity.put("deleteTrain", Arrays.asList("Train"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
