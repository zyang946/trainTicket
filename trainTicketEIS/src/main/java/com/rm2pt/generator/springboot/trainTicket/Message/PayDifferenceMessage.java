package com.rm2pt.generator.springboot.trainTicket.Message;
import java.sql.Date;
import javax.json.bind.annotation.JsonbDateFormat;
import java.util.List;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public class PayDifferenceMessage{
	public double difference;
	public double getDifference() {
		return difference;
	}
					
	public void setDifference(double difference) {
		this.difference = difference;
	}
}
