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
public class ManageOrderCRUDService{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;

	public Boolean createOrder(String orderid,String ticketid,String accoutid,String createtime,Integer orderstatus) throws PreconditionException{
		Order order = DM.getOrderDao().findByOrderId(orderid);
		
		if(StandardOPs.oclIsUndefined(order) == true)
		{
			Order ord = new Order();
			ord.setOrderId(orderid);
			ord.setTicketId(ticketid);
			ord.setAccoutId(accoutid);
			ord.setCreateTime(createtime);
			ord.setOrderStatus(orderstatus);
			DM.getOrderDao().save(ord);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Order queryOrder(String orderid) throws PreconditionException{
		Order order = DM.getOrderDao().findByOrderId(orderid);
		
		if(StandardOPs.oclIsUndefined(order) == false)
		{
			return order;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean modifyOrder(String orderid,String ticketid,String accoutid,String createtime,Integer orderstatus) throws PreconditionException{
		Order order = DM.getOrderDao().findByOrderId(orderid);
		
		if(StandardOPs.oclIsUndefined(order) == false)
		{
			order.setOrderId(orderid);
			order.setTicketId(ticketid);
			order.setAccoutId(accoutid);
			order.setCreateTime(createtime);
			order.setOrderStatus(orderstatus);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean deleteOrder(String orderid) throws PreconditionException{
		Order order = DM.getOrderDao().findByOrderId(orderid);
		
		if(StandardOPs.oclIsUndefined(order) == false && order!= null)
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
