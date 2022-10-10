package com.rm2pt.generator.springboot.trainTicket.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;import org.springframework.stereotype.Component;
import com.rm2pt.generator.springboot.trainTicket.service.*;
@Component
	public class ServiceManage {
	@Lazy
	@Autowired
	private TrainTicketSystem trainTicketSystem;
	@Lazy
	@Autowired
	private ThirdPartyServices thirdPartyServices;
	@Lazy
	@Autowired
	private BuyTicketService buyTicketService;
	@Lazy
	@Autowired
	private QueryOrderService queryOrderService;
	@Lazy
	@Autowired
	private ManageRouteCRUDService manageRouteCRUDService;
	@Lazy
	@Autowired
	private ManageTrainCRUDService manageTrainCRUDService;
	@Lazy
	@Autowired
	private ManageTicketCRUDService manageTicketCRUDService;
	@Lazy
	@Autowired
	private ManageOrderCRUDService manageOrderCRUDService;
	@Lazy
	@Autowired
	private ManageAccoutCRUDService manageAccoutCRUDService;
	@Lazy
	@Autowired
	private ManageSeatCRUDService manageSeatCRUDService;
	@Lazy
	@Autowired
	private ModifyTripService modifyTripService;
	@Lazy
	@Autowired
	private DeleteOverdueOrderService deleteOverdueOrderService;
	public Object getService(String name) {
	if("TrainTicketSystem".equals(name)) {
			return trainTicketSystem;
		}
		else if("ThirdPartyServices".equals(name)) {
			return thirdPartyServices;
		}
		else if("BuyTicketService".equals(name)) {
			return buyTicketService;
		}
		else if("QueryOrderService".equals(name)) {
			return queryOrderService;
		}
		else if("ManageRouteCRUDService".equals(name)) {
			return manageRouteCRUDService;
		}
		else if("ManageTrainCRUDService".equals(name)) {
			return manageTrainCRUDService;
		}
		else if("ManageTicketCRUDService".equals(name)) {
			return manageTicketCRUDService;
		}
		else if("ManageOrderCRUDService".equals(name)) {
			return manageOrderCRUDService;
		}
		else if("ManageAccoutCRUDService".equals(name)) {
			return manageAccoutCRUDService;
		}
		else if("ManageSeatCRUDService".equals(name)) {
			return manageSeatCRUDService;
		}
		else if("ModifyTripService".equals(name)) {
			return modifyTripService;
		}
		else if("DeleteOverdueOrderService".equals(name)) {
			return deleteOverdueOrderService;
		}
		return null;
	}
	}
	