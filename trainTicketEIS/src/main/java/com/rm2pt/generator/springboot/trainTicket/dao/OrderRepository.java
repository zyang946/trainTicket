package com.rm2pt.generator.springboot.trainTicket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Date;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public interface OrderRepository extends JpaRepository<Order, Integer> ,JpaSpecificationExecutor<Order>{
	Order findByHasTicket(Ticket ticket);
			Order findByAccoutIdAndTicketIdAndOrderStatus(Object accoutId,Object ticketId,Object orderStatus);
			Order findByTicketIdAndOrderStatus(Object CurrentTicketId,Object orderStatus);
			Order findByTicketIdAndAccoutIdAndOrderStatus(Object ticketId,Object accoutId,Object orderStatus);
			List<Order> findByTicketId(Object ticketId);
			Order findByOrderId(Object orderId);
			List<Order> findByAccoutId(Object accoutId);
}
