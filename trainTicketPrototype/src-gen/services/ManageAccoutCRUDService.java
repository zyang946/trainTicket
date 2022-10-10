package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageAccoutCRUDService {

	/* all system operations of the use case*/
	boolean createAccout(String accoutid, String name, String phonenumber) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Accout queryAccout(String accoutid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyAccout(String accoutid, String name, String phonenumber) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteAccout(String accoutid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
