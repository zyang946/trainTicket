package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageTicketCRUDService {

	/* all system operations of the use case*/
	boolean createTicket(String ticketid, String routeid, String seatid, float price, boolean isvalid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Ticket queryTicket(String ticketid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyTicket(String ticketid, String routeid, String seatid, float price, boolean isvalid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteTicket(String ticketid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
