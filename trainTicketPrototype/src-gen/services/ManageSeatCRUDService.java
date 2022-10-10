package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageSeatCRUDService {

	/* all system operations of the use case*/
	boolean createSeat(String seatid, String trainid, SeatType seattype) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Seat querySeat(String seatid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifySeat(String seatid, String trainid, SeatType seattype) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteSeat(String seatid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
