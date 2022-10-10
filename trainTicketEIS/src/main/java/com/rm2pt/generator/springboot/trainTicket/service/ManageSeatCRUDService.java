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
public class ManageSeatCRUDService{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;

	public Boolean createSeat(String seatid,String trainid,Integer seattype) throws PreconditionException{
		Seat seat = DM.getSeatDao().findBySeatId(seatid);
		Train train = DM.getTrainDao().findByTrainId(trainid);
		
		if(StandardOPs.oclIsUndefined(seat) == true && StandardOPs.oclIsUndefined(train) == false)
		{
			Seat sea = new Seat();
			sea.setSeatId(seatid);
			sea.setTrainId(trainid);
			sea.setSeatType(seattype);
			DM.getSeatDao().save(sea);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Seat querySeat(String seatid) throws PreconditionException{
		Seat seat = DM.getSeatDao().findBySeatId(seatid);
		
		if(StandardOPs.oclIsUndefined(seat) == false)
		{
			return seat;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean modifySeat(String seatid,String trainid,Integer seattype) throws PreconditionException{
		Seat seat = DM.getSeatDao().findBySeatId(seatid);
		Train train = DM.getTrainDao().findByTrainId(trainid);
		
		if(StandardOPs.oclIsUndefined(seat) == false && StandardOPs.oclIsUndefined(train) == false)
		{
			seat.setSeatId(seatid);
			seat.setTrainId(trainid);
			seat.setSeatType(seattype);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean deleteSeat(String seatid) throws PreconditionException{
		Seat seat = DM.getSeatDao().findBySeatId(seatid);
		
		if(StandardOPs.oclIsUndefined(seat) == false && seat!= null)
		{
			DM.getSeatDao().delete(seat);
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
