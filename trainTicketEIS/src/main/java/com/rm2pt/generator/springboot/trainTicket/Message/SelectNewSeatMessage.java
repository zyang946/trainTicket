package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class SelectNewSeatMessage{
	public String seatId;
	public String getSeatId() {
		return seatId;
	}
					
	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
}
