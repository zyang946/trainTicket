package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class CreateOrderMessage{
	public String orderid;
	public String getOrderid() {
		return orderid;
	}
					
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String ticketid;
	public String getTicketid() {
		return ticketid;
	}
					
	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}
	public String accoutid;
	public String getAccoutid() {
		return accoutid;
	}
					
	public void setAccoutid(String accoutid) {
		this.accoutid = accoutid;
	}
	public String createtime;
	public String getCreatetime() {
		return createtime;
	}
					
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Integer orderstatus;
	public Integer getOrderstatus() {
		return orderstatus;
	}
					
	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}
}
