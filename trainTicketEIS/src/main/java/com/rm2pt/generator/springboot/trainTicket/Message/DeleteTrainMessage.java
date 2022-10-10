package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class DeleteTrainMessage{
	public String trainid;
	public String getTrainid() {
		return trainid;
	}
					
	public void setTrainid(String trainid) {
		this.trainid = trainid;
	}
}
