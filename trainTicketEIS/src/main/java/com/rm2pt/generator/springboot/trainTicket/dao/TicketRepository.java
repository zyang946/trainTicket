package com.rm2pt.generator.springboot.trainTicket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Date;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public interface TicketRepository extends JpaRepository<Ticket, Integer> ,JpaSpecificationExecutor<Ticket>{
	List<Ticket> findByHasRoute(Route route);
	List<Ticket> findByHasSeat(Seat seat);
	List<Ticket> findByBelongedAccout(Accout accout);
			Ticket findByTicketId(Object ticketId);
			Ticket findByTicketIdAndIsValid(Object ticketId,Object paramFalse);
}
