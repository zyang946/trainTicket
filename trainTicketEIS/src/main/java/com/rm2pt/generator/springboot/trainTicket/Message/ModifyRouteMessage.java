package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class ModifyRouteMessage{
	public String routeid;
	public String getRouteid() {
		return routeid;
	}
					
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}
	public String startstation;
	public String getStartstation() {
		return startstation;
	}
					
	public void setStartstation(String startstation) {
		this.startstation = startstation;
	}
	public String endstation;
	public String getEndstation() {
		return endstation;
	}
					
	public void setEndstation(String endstation) {
		this.endstation = endstation;
	}
	public String time;
	public String getTime() {
		return time;
	}
					
	public void setTime(String time) {
		this.time = time;
	}
	public String trainid;
	public String getTrainid() {
		return trainid;
	}
					
	public void setTrainid(String trainid) {
		this.trainid = trainid;
	}
}
