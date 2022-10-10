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
	public class Ticket extends BaseEntity {
		
		/* all primary attributes */
		private String ticketId;
		public String getTicketId() {
			return ticketId;
		}
		public void setTicketId(String TicketId) {
			this.ticketId = TicketId;
		}	
			
		private String routeId;
		public String getRouteId() {
			return routeId;
		}
		public void setRouteId(String RouteId) {
			this.routeId = RouteId;
		}	
			
		private String seatId;
		public String getSeatId() {
			return seatId;
		}
		public void setSeatId(String SeatId) {
			this.seatId = SeatId;
		}	
			
		private double price;
		public double getPrice() {
			return price;
		}
		public void setPrice(double Price) {
			this.price = Price;
		}	
			
		private Boolean isValid;
		public Boolean getIsValid() {
			return isValid;
		}
		public void setIsValid(Boolean IsValid) {
			this.isValid = IsValid;
		}	
			

		
		@ManyToOne(targetEntity=Route.class)
		@JoinColumn(name="hasroute_realid",referencedColumnName="realid")
		private Route hasRoute;
				
		public Route getHasRoute() {
			return hasRoute;
		}
				
		public void setHasRoute(Route HasRoute) {
			this.hasRoute = HasRoute;
		}
		
		
		@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "hasTicket")
		private Order inOrder;
				
		public Order getInOrder() {
			return inOrder;
		}
				
		public void setInOrder(Order InOrder) {
			this.inOrder = InOrder;
		}
		
		@ManyToOne(targetEntity=Seat.class)
		@JoinColumn(name="hasseat_realid",referencedColumnName="realid")
		private Seat hasSeat;
				
		public Seat getHasSeat() {
			return hasSeat;
		}
				
		public void setHasSeat(Seat HasSeat) {
			this.hasSeat = HasSeat;
		}
		
		@ManyToOne(targetEntity=Accout.class)
		@JoinColumn(name="belongedaccout_realid",referencedColumnName="realid")
		private Accout belongedAccout;
				
		public Accout getBelongedAccout() {
			return belongedAccout;
		}
				
		public void setBelongedAccout(Accout BelongedAccout) {
			this.belongedAccout = BelongedAccout;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Ticket other = (Ticket) obj;
			if (!this.getRealid().equals(other.getRealid()))
				return false;
			else
				return true;
		}
	}
