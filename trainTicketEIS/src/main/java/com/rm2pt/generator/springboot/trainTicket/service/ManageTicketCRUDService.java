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
public class ManageTicketCRUDService{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;

	public Boolean createTicket(String ticketid,String routeid,String seatid,double price,Boolean isvalid) throws PreconditionException{
		Ticket ticket = DM.getTicketDao().findByTicketId(ticketid);
		
		if(StandardOPs.oclIsUndefined(ticket) == true)
		{
			Ticket tic = new Ticket();
			tic.setTicketId(ticketid);
			tic.setRouteId(routeid);
			tic.setSeatId(seatid);
			tic.setPrice(price);
			tic.setIsValid(isvalid);
			DM.getTicketDao().save(tic);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Ticket queryTicket(String ticketid) throws PreconditionException{
		Ticket ticket = DM.getTicketDao().findByTicketId(ticketid);
		
		if(StandardOPs.oclIsUndefined(ticket) == false)
		{
			return ticket;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean modifyTicket(String ticketid,String routeid,String seatid,double price,Boolean isvalid) throws PreconditionException{
		Ticket ticket = DM.getTicketDao().findByTicketId(ticketid);
		
		if(StandardOPs.oclIsUndefined(ticket) == false)
		{
			ticket.setTicketId(ticketid);
			ticket.setRouteId(routeid);
			ticket.setSeatId(seatid);
			ticket.setPrice(price);
			ticket.setIsValid(isvalid);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean deleteTicket(String ticketid) throws PreconditionException{
		Ticket ticket = DM.getTicketDao().findByTicketId(ticketid);
		
		if(StandardOPs.oclIsUndefined(ticket) == false && ticket!= null)
		{
			DM.getTicketDao().delete(ticket);
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
