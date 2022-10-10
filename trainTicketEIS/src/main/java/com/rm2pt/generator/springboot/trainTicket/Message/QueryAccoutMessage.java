package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class QueryAccoutMessage{
	public String accoutid;
	public String getAccoutid() {
		return accoutid;
	}
					
	public void setAccoutid(String accoutid) {
		this.accoutid = accoutid;
	}
}
