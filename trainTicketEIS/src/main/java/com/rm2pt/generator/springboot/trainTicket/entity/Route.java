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
	public class Route extends BaseEntity {
		
		/* all primary attributes */
		private String routeId;
		public String getRouteId() {
			return routeId;
		}
		public void setRouteId(String RouteId) {
			this.routeId = RouteId;
		}	
			
		private String startStation;
		public String getStartStation() {
			return startStation;
		}
		public void setStartStation(String StartStation) {
			this.startStation = StartStation;
		}	
			
		private String endStation;
		public String getEndStation() {
			return endStation;
		}
		public void setEndStation(String EndStation) {
			this.endStation = EndStation;
		}	
			
		private String time;
		public String getTime() {
			return time;
		}
		public void setTime(String Time) {
			this.time = Time;
		}	
			
		private String trainId;
		public String getTrainId() {
			return trainId;
		}
		public void setTrainId(String TrainId) {
			this.trainId = TrainId;
		}	
			

		
		@JsonIgnore
		@Fetch(FetchMode.SUBSELECT)
		@OneToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.EAGER,mappedBy="hasRoute")
				private List<Ticket> inTicket = new ArrayList<Ticket>();
					
		public List<Ticket> getInTicket() {
			return inTicket;
		}
				
		public void setInTicket(List<Ticket> InTicket) {
			this.inTicket = InTicket;
		}
		
		@ManyToOne(targetEntity=Train.class)
		@JoinColumn(name="usedtrain_realid",referencedColumnName="realid")
		private Train usedTrain;
				
		public Train getUsedTrain() {
			return usedTrain;
		}
				
		public void setUsedTrain(Train UsedTrain) {
			this.usedTrain = UsedTrain;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Route other = (Route) obj;
			if (!this.getRealid().equals(other.getRealid()))
				return false;
			else
				return true;
		}
	}
