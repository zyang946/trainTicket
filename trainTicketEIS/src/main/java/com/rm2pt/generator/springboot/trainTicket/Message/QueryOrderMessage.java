package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class QueryOrderMessage{
	public String orderid;
	public String getOrderid() {
		return orderid;
	}
					
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
}
