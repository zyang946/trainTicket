package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class DeleteRouteMessage{
	public String routeid;
	public String getRouteid() {
		return routeid;
	}
					
	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}
}
