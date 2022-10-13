package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface CancelTripService {

	/* all system operations of the use case*/
	boolean checkCancelTrip(String accoutId, String ticketId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean cancelTicket() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	float refund() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	Order getCurrentOrder();
	void setCurrentOrder(Order currentorder);
	Ticket getCurrentTicket();
	void setCurrentTicket(Ticket currentticket);
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
