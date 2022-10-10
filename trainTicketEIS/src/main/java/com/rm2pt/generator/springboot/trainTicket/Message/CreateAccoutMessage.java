package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class CreateAccoutMessage{
	public String accoutid;
	public String getAccoutid() {
		return accoutid;
	}
					
	public void setAccoutid(String accoutid) {
		this.accoutid = accoutid;
	}
	public String name;
	public String getName() {
		return name;
	}
					
	public void setName(String name) {
		this.name = name;
	}
	public String phonenumber;
	public String getPhonenumber() {
		return phonenumber;
	}
					
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
}
