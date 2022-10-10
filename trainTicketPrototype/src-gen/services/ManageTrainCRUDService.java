package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageTrainCRUDService {

	/* all system operations of the use case*/
	boolean createTrain(String trainid, String name, TrainType traintype) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Train queryTrain(String trainid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyTrain(String trainid, String name, TrainType traintype) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteTrain(String trainid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
