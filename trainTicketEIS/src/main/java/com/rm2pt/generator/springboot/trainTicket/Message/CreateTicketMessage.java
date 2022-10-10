package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class CreateTicketMessage{
	public String ticketid;
	public String getTicketid() {
		return ticketid;
	}
					
	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}
	public String routeid;
	public String getRouteid() {
		return routeid;
	}
					
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}
	public String seatid;
	public String getSeatid() {
		return seatid;
	}
					
	public void setSeatid(String seatid) {
		this.seatid = seatid;
	}
	public double price;
	public double getPrice() {
		return price;
	}
					
	public void setPrice(double price) {
		this.price = price;
	}
	public Boolean isvalid;
	public Boolean getIsvalid() {
		return isvalid;
	}
					
	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}
}
