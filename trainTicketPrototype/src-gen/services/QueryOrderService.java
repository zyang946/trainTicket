package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface QueryOrderService {

	/* all system operations of the use case*/
	List<Order> queryByAccoutId(String accoutId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<Order> queryTicketId(String ticketId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	QueryMethod getCurrentQueryMethod();
	void setCurrentQueryMethod(QueryMethod currentquerymethod);
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
