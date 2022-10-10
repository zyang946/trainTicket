package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class ModifyTrainMessage{
	public String trainid;
	public String getTrainid() {
		return trainid;
	}
					
	public void setTrainid(String trainid) {
		this.trainid = trainid;
	}
	public String name;
	public String getName() {
		return name;
	}
					
	public void setName(String name) {
		this.name = name;
	}
	public Integer traintype;
	public Integer getTraintype() {
		return traintype;
	}
					
	public void setTraintype(Integer traintype) {
		this.traintype = traintype;
	}
}
