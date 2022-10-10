	package com.rm2pt.generator.springboot.trainTicket.entity;
	
	import org.hibernate.annotations.Fetch;
	import org.hibernate.annotations.FetchMode;
	import com.fasterxml.jackson.annotation.JsonBackReference;
	import com.fasterxml.jackson.annotation.JsonIgnore;
	import javax.persistence.FetchType;
	import java.util.ArrayList;
	import javax.persistence.OneToMany;
	import javax.persistence.OneToOne;
	import javax.persistence.CascadeType;
	import java.util.Date;
	import java.util.List;
	import java.io.Serializable;
	import javax.persistence.Entity;
	import javax.persistence.Transient;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.JoinColumn;
	import javax.persistence.ManyToOne;
	import com.fasterxml.jackson.annotation.ObjectIdGenerators;
	import com.fasterxml.jackson.annotation.JsonIdentityInfo;
	
	@Entity
	public class Seat extends BaseEntity {
		
		/* all primary attributes */
		private String seatId;
		public String getSeatId() {
			return seatId;
		}
		public void setSeatId(String SeatId) {
			this.seatId = SeatId;
		}	
			
		private String trainId;
		public String getTrainId() {
			return trainId;
		}
		public void setTrainId(String TrainId) {
			this.trainId = TrainId;
		}	
			
		private Integer seatType;
		public Integer getSeatType() {
			return seatType;
		}
		public void setSeatType(Integer SeatType) {
			this.seatType = SeatType;
		}	
			

		
		@JsonIgnore
		@Fetch(FetchMode.SUBSELECT)
		@OneToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.EAGER,mappedBy="hasSeat")
				private List<Ticket> inTicket = new ArrayList<Ticket>();
					
		public List<Ticket> getInTicket() {
			return inTicket;
		}
				
		public void setInTicket(List<Ticket> InTicket) {
			this.inTicket = InTicket;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Seat other = (Seat) obj;
			if (!this.getRealid().equals(other.getRealid()))
				return false;
			else
				return true;
		}
	}
