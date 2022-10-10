package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageRouteCRUDService {

	/* all system operations of the use case*/
	boolean createRoute(String routeid, String startstation, String endstation, String time, String trainid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Route queryRoute(String routeid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyRoute(String routeid, String startstation, String endstation, String time, String trainid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteRoute(String routeid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
