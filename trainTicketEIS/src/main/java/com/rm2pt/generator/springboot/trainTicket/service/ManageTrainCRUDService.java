package com.rm2pt.generator.springboot.trainTicket.service;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rm2pt.generator.springboot.trainTicket.entity.*;
import com.rm2pt.generator.springboot.trainTicket.utils.DaoManage;
import com.rm2pt.generator.springboot.trainTicket.utils.PreconditionException;
import com.rm2pt.generator.springboot.trainTicket.utils.ServiceManage;
import com.rm2pt.generator.springboot.trainTicket.utils.StandardOPs;
import com.rm2pt.generator.springboot.trainTicket.redis.CurrentUtils;

@Transactional
@Service
public class ManageTrainCRUDService{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;

	public Boolean createTrain(String trainid,String name,Integer traintype) throws PreconditionException{
		Train train = DM.getTrainDao().findByTrainId(trainid);
		
		if(StandardOPs.oclIsUndefined(train) == true)
		{
			Train tra = new Train();
			tra.setTrainId(trainid);
			tra.setName(name);
			tra.setTrainType(traintype);
			DM.getTrainDao().save(tra);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Train queryTrain(String trainid) throws PreconditionException{
		Train train = DM.getTrainDao().findByTrainId(trainid);
		
		if(StandardOPs.oclIsUndefined(train) == false)
		{
			return train;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean modifyTrain(String trainid,String name,Integer traintype) throws PreconditionException{
		Train train = DM.getTrainDao().findByTrainId(trainid);
		
		if(StandardOPs.oclIsUndefined(train) == false)
		{
			train.setTrainId(trainid);
			train.setName(name);
			train.setTrainType(traintype);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean deleteTrain(String trainid) throws PreconditionException{
		Train train = DM.getTrainDao().findByTrainId(trainid);
		
		if(StandardOPs.oclIsUndefined(train) == false && train!= null)
		{
			DM.getTrainDao().delete(train);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
				
	public static Object GetData(Optional<?> op) {
		if (op.isPresent())
			return op.get();
		else 
			return null;
	}
}
