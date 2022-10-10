package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class ModifySeatMessage{
	public String seatid;
	public String getSeatid() {
		return seatid;
	}
					
	public void setSeatid(String seatid) {
		this.seatid = seatid;
	}
	public String trainid;
	public String getTrainid() {
		return trainid;
	}
					
	public void setTrainid(String trainid) {
		this.trainid = trainid;
	}
	public Integer seattype;
	public Integer getSeattype() {
		return seattype;
	}
					
	public void setSeattype(Integer seattype) {
		this.seattype = seattype;
	}
}
