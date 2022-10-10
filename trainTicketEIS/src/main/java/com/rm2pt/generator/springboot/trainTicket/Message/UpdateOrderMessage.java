package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class UpdateOrderMessage{
	public String time;
	public String getTime() {
		return time;
	}
					
	public void setTime(String time) {
		this.time = time;
	}
}
