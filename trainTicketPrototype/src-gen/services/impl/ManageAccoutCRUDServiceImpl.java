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

public class ManageAccoutCRUDServiceImpl implements ManageAccoutCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageAccoutCRUDServiceImpl() {
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
	public boolean createAccout(String accoutid, String name, String phonenumber) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get accout
		Accout accout = null;
		//no nested iterator --  iterator: any previous:any
		for (Accout acc : (List<Accout>)EntityManager.getAllInstancesOf("Accout"))
		{
			if (acc.getAccoutId().equals(accoutid))
			{
				accout = acc;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(accout) == true) 
		{ 
			/* Logic here */
			Accout acc = null;
			acc = (Accout) EntityManager.createObject("Accout");
			acc.setAccoutId(accoutid);
			acc.setName(name);
			acc.setPhoneNumber(phonenumber);
			EntityManager.addObject("Accout", acc);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			acc.getAccoutId() == accoutid
			 && 
			acc.getName() == name
			 && 
			acc.getPhoneNumber() == phonenumber
			 && 
			StandardOPs.includes(((List<Accout>)EntityManager.getAllInstancesOf("Accout")), acc)
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
		//string parameters: [accoutid, name, phonenumber] 
		//all relevant vars : acc
		//all relevant entities : Accout
	}  
	
	static {opINVRelatedEntity.put("createAccout", Arrays.asList("Accout"));}
	 
	@SuppressWarnings("unchecked")
	public Accout queryAccout(String accoutid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get accout
		Accout accout = null;
		//no nested iterator --  iterator: any previous:any
		for (Accout acc : (List<Accout>)EntityManager.getAllInstancesOf("Accout"))
		{
			if (acc.getAccoutId().equals(accoutid))
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
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return accout;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [accoutid] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean modifyAccout(String accoutid, String name, String phonenumber) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get accout
		Accout accout = null;
		//no nested iterator --  iterator: any previous:any
		for (Accout acc : (List<Accout>)EntityManager.getAllInstancesOf("Accout"))
		{
			if (acc.getAccoutId().equals(accoutid))
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
			accout.setAccoutId(accoutid);
			accout.setName(name);
			accout.setPhoneNumber(phonenumber);
			
			
			refresh();
			// post-condition checking
			if (!(accout.getAccoutId() == accoutid
			 && 
			accout.getName() == name
			 && 
			accout.getPhoneNumber() == phonenumber
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
		//string parameters: [accoutid, name, phonenumber] 
		//all relevant vars : accout
		//all relevant entities : Accout
	}  
	
	static {opINVRelatedEntity.put("modifyAccout", Arrays.asList("Accout"));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteAccout(String accoutid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get accout
		Accout accout = null;
		//no nested iterator --  iterator: any previous:any
		for (Accout acc : (List<Accout>)EntityManager.getAllInstancesOf("Accout"))
		{
			if (acc.getAccoutId().equals(accoutid))
			{
				accout = acc;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(accout) == false && StandardOPs.includes(((List<Accout>)EntityManager.getAllInstancesOf("Accout")), accout)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Accout", accout);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Accout>)EntityManager.getAllInstancesOf("Accout")), accout)
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
		//string parameters: [accoutid] 
		//all relevant vars : accout
		//all relevant entities : Accout
	}  
	
	static {opINVRelatedEntity.put("deleteAccout", Arrays.asList("Accout"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
