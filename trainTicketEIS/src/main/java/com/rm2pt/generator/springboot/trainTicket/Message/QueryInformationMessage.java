package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class QueryInformationMessage{
	public String startStation;
	public String getStartStation() {
		return startStation;
	}
					
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public String endStation;
	public String getEndStation() {
		return endStation;
	}
					
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}
	public String time;
	public String getTime() {
		return time;
	}
					
	public void setTime(String time) {
		this.time = time;
	}
}
