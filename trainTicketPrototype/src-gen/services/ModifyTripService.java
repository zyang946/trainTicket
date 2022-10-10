package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ModifyTripService {

	/* all system operations of the use case*/
	boolean updateTicket(String accoutId, String ticketId, String newRouteId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean updateOrder(String time) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<Seat> showSeatsByRouteId() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean selectNewSeat(String seatId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean payDifference(float difference) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	Order getCurrentOrder();
	void setCurrentOrder(Order currentorder);
	Ticket getCurrentTicket();
	void setCurrentTicket(Ticket currentticket);
	Accout getCurrentAccout();
	void setCurrentAccout(Accout currentaccout);
	Route getCurrentRoute();
	void setCurrentRoute(Route currentroute);
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
