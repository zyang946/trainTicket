package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class QueryTicketMessage{
	public String ticketid;
	public String getTicketid() {
		return ticketid;
	}
					
	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}
}
