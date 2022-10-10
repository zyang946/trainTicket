package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface DeleteOverdueOrderService {

	/* all system operations of the use case*/
	boolean deleteOverdueTicket(String ticketId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteOverdueOrder() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	String getCurrentTicketId();
	void setCurrentTicketId(String currentticketid);
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
