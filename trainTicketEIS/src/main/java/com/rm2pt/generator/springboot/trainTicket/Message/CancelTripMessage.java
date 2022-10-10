package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class CancelTripMessage{
	public String ticketId;
	public String getTicketId() {
		return ticketId;
	}
					
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String accoutId;
	public String getAccoutId() {
		return accoutId;
	}
					
	public void setAccoutId(String accoutId) {
		this.accoutId = accoutId;
	}
}
