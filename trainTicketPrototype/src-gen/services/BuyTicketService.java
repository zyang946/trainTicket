package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface BuyTicketService {

	/* all system operations of the use case*/
	boolean buyTicket(String accoutId, String routeId, String ticketId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean saveOrder(String orderId, String boughtTime) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<Seat> showSeats() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean selectSeat(String seatId) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean pay(float price) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	Order getCurrentOrder();
	void setCurrentOrder(Order currentorder);
	Accout getCurrentAccout();
	void setCurrentAccout(Accout currentaccout);
	Ticket getCurrentTicket();
	void setCurrentTicket(Ticket currentticket);
	Route getCurrentRoute();
	void setCurrentRoute(Route currentroute);
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
