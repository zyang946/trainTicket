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
public class DeleteOverdueOrderService{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;
	private String CurrentTicketId;
	public Boolean deleteOverdueTicket(String ticketId) throws PreconditionException{
		Ticket ticket = DM.getTicketDao().findByTicketIdAndIsValid(ticketId,false);
		
		if(StandardOPs.oclIsUndefined(ticket) == false)
		{
			currentUtils.setAttribute("CurrentTicketId" , ticketId);
			DM.getTicketDao().delete(ticket);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean deleteOverdueOrder() throws PreconditionException{
		Order order = DM.getOrderDao().findByTicketIdAndOrderStatus(CurrentTicketId,OrderStatus.CANCELED);
		
		if(StandardOPs.oclIsUndefined(order) == false)
		{
			DM.getOrderDao().delete(order);
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
