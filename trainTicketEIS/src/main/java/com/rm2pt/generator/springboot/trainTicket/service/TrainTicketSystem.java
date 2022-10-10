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
public class TrainTicketSystem{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;

	public List<Route> queryInformation(String startStation,String endStation,String time) throws PreconditionException{
		
		if(true)
		{
			return DM.getRouteDao().findByStartStationAndEndStationAndTime(startStation,endStation,time);
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean cancelTrip(String ticketId,String accoutId) throws PreconditionException{
		Ticket ticket = DM.getTicketDao().findByTicketId(ticketId);
		Accout accout = DM.getAccoutDao().findByAccoutId(accoutId);
		Order order = DM.getOrderDao().findByTicketIdAndAccoutIdAndOrderStatus(ticketId,accoutId,OrderStatus.PAID);
		
		if(StandardOPs.oclIsUndefined(ticket) == false && StandardOPs.oclIsUndefined(accout) == false && StandardOPs.oclIsUndefined(order) == false)
		{
			ticket.setIsValid(false);
			order.setOrderStatus(2);
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
