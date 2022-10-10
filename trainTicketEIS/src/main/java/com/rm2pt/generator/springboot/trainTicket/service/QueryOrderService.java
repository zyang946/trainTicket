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
public class QueryOrderService{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;

	public List<Order> queryByAccoutId(String accoutId) throws PreconditionException{
		Accout accout = DM.getAccoutDao().findByAccoutId(accoutId);
		
		if(StandardOPs.oclIsUndefined(accout) == false)
		{
			return DM.getOrderDao().findByAccoutId(accoutId);
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public List<Order> queryTicketId(String ticketId) throws PreconditionException{
		Ticket ticket = DM.getTicketDao().findByTicketId(ticketId);
		
		if(StandardOPs.oclIsUndefined(ticket) == false)
		{
			return DM.getOrderDao().findByTicketId(ticketId);
			
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
