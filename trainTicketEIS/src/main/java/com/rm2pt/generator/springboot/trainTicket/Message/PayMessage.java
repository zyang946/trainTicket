package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class PayMessage{
	public double price;
	public double getPrice() {
		return price;
	}
					
	public void setPrice(double price) {
		this.price = price;
	}
}
