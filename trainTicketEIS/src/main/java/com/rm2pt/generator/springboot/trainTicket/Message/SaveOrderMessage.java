package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class SaveOrderMessage{
	public String orderId;
	public String getOrderId() {
		return orderId;
	}
					
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String boughtTime;
	public String getBoughtTime() {
		return boughtTime;
	}
					
	public void setBoughtTime(String boughtTime) {
		this.boughtTime = boughtTime;
	}
}
