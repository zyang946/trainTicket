package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class DeleteOverdueTicketMessage{
	public String ticketId;
	public String getTicketId() {
		return ticketId;
	}
					
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
}
