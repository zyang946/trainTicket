package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface TrainTicketSystem {

	/* all system operations of the use case*/
	List<Route> queryInformation(String startStation, String endStation, String time) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean cancelTrip(String ticketId, String accoutId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	
	/* invariant checking function */
}
