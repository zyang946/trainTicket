package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageOrderCRUDService {

	/* all system operations of the use case*/
	boolean createOrder(String orderid, String ticketid, String accoutid, String createtime, OrderStatus orderstatus) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Order queryOrder(String orderid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyOrder(String orderid, String ticketid, String accoutid, String createtime, OrderStatus orderstatus) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteOrder(String orderid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
