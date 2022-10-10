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
public class BuyTicketService{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;
	
	public Boolean buyTicket(String accoutId,String routeId,String ticketId) throws PreconditionException{
		Accout CurrentAccout=(Accout)currentUtils.getAttribute("CurrentAccout");
		Ticket CurrentTicket=(Ticket)currentUtils.getAttribute("CurrentTicket");
		Route CurrentRoute=(Route)currentUtils.getAttribute("CurrentRoute");
		Route route = DM.getRouteDao().findByRouteId(routeId);
		Accout accout = DM.getAccoutDao().findByAccoutId(accoutId);
		Ticket ticket = DM.getTicketDao().findByTicketId(ticketId);
		
		if(StandardOPs.oclIsUndefined(route) == false && StandardOPs.oclIsUndefined(accout) == false && StandardOPs.oclIsUndefined(ticket) == true)
		{
			Ticket tic = new Ticket();
			tic.setTicketId(ticketId);
			tic.setRouteId(routeId);
			tic.setIsValid(false);
			DM.getTicketDao().save(tic);
			CurrentTicket = tic;
			CurrentAccout = accout;
			CurrentRoute = route;
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean saveOrder(String orderId,String boughtTime) throws PreconditionException{
		Accout CurrentAccout=(Accout)currentUtils.getAttribute("CurrentAccout");
		Order CurrentOrder=(Order)currentUtils.getAttribute("CurrentOrder");
		Ticket CurrentTicket=(Ticket)currentUtils.getAttribute("CurrentTicket");
		Order order = DM.getOrderDao().findByOrderId(orderId);
		
		if(StandardOPs.oclIsUndefined(order) == true)
		{
			Order ord = new Order();
			ord.setOrderId(orderId);
			ord.setTicketId(CurrentTicket.getTicketId());
			ord.setAccoutId(CurrentAccout.getAccoutId());
			ord.setCreateTime(boughtTime);
			ord.setOrderStatus(0);
			CurrentOrder = ord;
			DM.getOrderDao().save(ord);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public List<Seat> showSeats() throws PreconditionException{
		
		if(true)
		{
			return DM.getSeatDao().findByTrainId(CurrentRoute.getTrainId());
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean selectSeat(String seatId) throws PreconditionException{
		Ticket CurrentTicket=(Ticket)currentUtils.getAttribute("CurrentTicket");
		Seat seat = DM.getSeatDao().findByTrainIdAndSeatId(CurrentRoute.getTrainId(),seatId);
		
		if(StandardOPs.oclIsUndefined(seat) == false)
		{
			CurrentTicket.setSeatId(seatId);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean pay(double price) throws PreconditionException{
		Order CurrentOrder=(Order)currentUtils.getAttribute("CurrentOrder");
		Ticket CurrentTicket=(Ticket)currentUtils.getAttribute("CurrentTicket");
		
		if(price > 0)
		{
			CurrentTicket.setPrice(price);
			CurrentTicket.setIsValid(true);
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
