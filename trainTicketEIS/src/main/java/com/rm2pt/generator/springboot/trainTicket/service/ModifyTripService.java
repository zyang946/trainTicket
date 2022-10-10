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
public class ModifyTripService{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;

	public Boolean updateTicket(String accoutId,String ticketId,String newRouteId) throws PreconditionException{
		Accout CurrentAccout=(Accout)currentUtils.getAttribute("CurrentAccout");
		Order CurrentOrder=(Order)currentUtils.getAttribute("CurrentOrder");
		Ticket CurrentTicket=(Ticket)currentUtils.getAttribute("CurrentTicket");
		Route CurrentRoute=(Route)currentUtils.getAttribute("CurrentRoute");
		Ticket ticket = DM.getTicketDao().findByTicketIdAndIsValid(ticketId,true);
		Accout accout = DM.getAccoutDao().findByAccoutId(accoutId);
		Route route = DM.getRouteDao().findByRouteId(newRouteId);
		Order order = DM.getOrderDao().findByAccoutIdAndTicketIdAndOrderStatus(accoutId,ticketId,OrderStatus.PAID);
		
		if(StandardOPs.oclIsUndefined(ticket) == false && StandardOPs.oclIsUndefined(accout) == false && StandardOPs.oclIsUndefined(route) == false && StandardOPs.oclIsUndefined(order) == false)
		{
			ticket.setRouteId(newRouteId);
			ticket.setIsValid(false);
			CurrentTicket = ticket;
			CurrentOrder = order;
			CurrentAccout = accout;
			CurrentRoute = route;
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean updateOrder(String time) throws PreconditionException{
		Order CurrentOrder=(Order)currentUtils.getAttribute("CurrentOrder");
		
		if(true)
		{
			CurrentOrder.setCreateTime(time);
			CurrentOrder.setOrderStatus(0);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public List<Seat> showSeatsByRouteId() throws PreconditionException{
		
		if(true)
		{
			return DM.getSeatDao().findByTrainId(CurrentRoute.getTrainId());
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean selectNewSeat(String seatId) throws PreconditionException{
		Ticket CurrentTicket=(Ticket)currentUtils.getAttribute("CurrentTicket");
		Seat seat = DM.getSeatDao().findBySeatIdAndTrainId(seatId,CurrentRoute.getTrainId());
		
		if(StandardOPs.oclIsUndefined(seat) == false)
		{
			CurrentTicket.setSeatId(seatId);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean payDifference(double difference) throws PreconditionException{
		Order CurrentOrder=(Order)currentUtils.getAttribute("CurrentOrder");
		Ticket CurrentTicket=(Ticket)currentUtils.getAttribute("CurrentTicket");
		
		if(true)
		{
			CurrentTicket.setIsValid(true);
			CurrentTicket.setPrice(CurrentTicket.getPrice() + difference);
			CurrentOrder.setOrderStatus(1);
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
