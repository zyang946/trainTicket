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
	public class Accout extends BaseEntity {
		
		/* all primary attributes */
		private String accoutId;
		public String getAccoutId() {
			return accoutId;
		}
		public void setAccoutId(String AccoutId) {
			this.accoutId = AccoutId;
		}	
			
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String Name) {
			this.name = Name;
		}	
			
		private String phoneNumber;
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String PhoneNumber) {
			this.phoneNumber = PhoneNumber;
		}	
			

		
		@ManyToOne(targetEntity=Seat.class)
		@JoinColumn(name="accouttoseat_realid",referencedColumnName="realid")
		private Seat accouttoSeat;
				
		public Seat getAccouttoSeat() {
			return accouttoSeat;
		}
				
		public void setAccouttoSeat(Seat AccouttoSeat) {
			this.accouttoSeat = AccouttoSeat;
		}
		
		
		@JsonIgnore	
		@OneToOne(cascade = CascadeType.REFRESH)
		@JoinColumn(name = "in_order_realid")
		private Order inOrder;
				
		public Order getInOrder() {
			return inOrder;
		}
				
		public void setInOrder(Order InOrder) {
			this.inOrder = InOrder;
		}
		
		@JsonIgnore
		@Fetch(FetchMode.SUBSELECT)
		@OneToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.EAGER,mappedBy="belongedAccout")
				private List<Ticket> hasTicket = new ArrayList<Ticket>();
					
		public List<Ticket> getHasTicket() {
			return hasTicket;
		}
				
		public void setHasTicket(List<Ticket> HasTicket) {
			this.hasTicket = HasTicket;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Accout other = (Accout) obj;
			if (!this.getRealid().equals(other.getRealid()))
				return false;
			else
				return true;
		}
	}
