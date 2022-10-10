package com.rm2pt.generator.springboot.trainTicket.utils;
	import java.util.Optional;
	import org.springframework.stereotype.Component;
	import org.springframework.beans.factory.annotation.Autowired;
	import com.rm2pt.generator.springboot.trainTicket.dao.*;
	import com.rm2pt.generator.springboot.trainTicket.entity.*;
	@Component
	public class DaoManage {
		@Autowired
		private RouteRepository routeRepository;
		public RouteRepository getRouteDao() {
			return routeRepository;
		}
		@Autowired
		private TrainRepository trainRepository;
		public TrainRepository getTrainDao() {
			return trainRepository;
		}
		@Autowired
		private TicketRepository ticketRepository;
		public TicketRepository getTicketDao() {
			return ticketRepository;
		}
		@Autowired
		private OrderRepository orderRepository;
		public OrderRepository getOrderDao() {
			return orderRepository;
		}
		@Autowired
		private AccoutRepository accoutRepository;
		public AccoutRepository getAccoutDao() {
			return accoutRepository;
		}
		@Autowired
		private SeatRepository seatRepository;
		public SeatRepository getSeatDao() {
			return seatRepository;
		}
	}
	