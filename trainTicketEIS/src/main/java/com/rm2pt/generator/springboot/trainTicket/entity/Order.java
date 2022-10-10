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
	public class Order extends BaseEntity {
		
		/* all primary attributes */
		private String orderId;
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String OrderId) {
			this.orderId = OrderId;
		}	
			
		private String ticketId;
		public String getTicketId() {
			return ticketId;
		}
		public void setTicketId(String TicketId) {
			this.ticketId = TicketId;
		}	
			
		private String accoutId;
		public String getAccoutId() {
			return accoutId;
		}
		public void setAccoutId(String AccoutId) {
			this.accoutId = AccoutId;
		}	
			
		private String createTime;
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String CreateTime) {
			this.createTime = CreateTime;
		}	
			
		private Integer orderStatus;
		public Integer getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(Integer OrderStatus) {
			this.orderStatus = OrderStatus;
		}	
			

		
		
		@JsonIgnore	
		@OneToOne(cascade = CascadeType.REFRESH)
		@JoinColumn(name = "has_ticket_realid")
		private Ticket hasTicket;
				
		public Ticket getHasTicket() {
			return hasTicket;
		}
				
		public void setHasTicket(Ticket HasTicket) {
			this.hasTicket = HasTicket;
		}
		
		
		@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "inOrder")
		private Accout hasAccout;
				
		public Accout getHasAccout() {
			return hasAccout;
		}
				
		public void setHasAccout(Accout HasAccout) {
			this.hasAccout = HasAccout;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Order other = (Order) obj;
			if (!this.getRealid().equals(other.getRealid()))
				return false;
			else
				return true;
		}
	}
