package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class DeleteSeatMessage{
	public String seatid;
	public String getSeatid() {
		return seatid;
	}
					
	public void setSeatid(String seatid) {
		this.seatid = seatid;
	}
}
