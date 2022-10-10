package services.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import services.*;
	
public class ServiceManager {
	
	private static Map<String, List> AllServiceInstance = new HashMap<String, List>();
	
	private static List<TrainTicketSystem> TrainTicketSystemInstances = new LinkedList<TrainTicketSystem>();
	private static List<ThirdPartyServices> ThirdPartyServicesInstances = new LinkedList<ThirdPartyServices>();
	private static List<BuyTicketService> BuyTicketServiceInstances = new LinkedList<BuyTicketService>();
	private static List<QueryOrderService> QueryOrderServiceInstances = new LinkedList<QueryOrderService>();
	private static List<ManageRouteCRUDService> ManageRouteCRUDServiceInstances = new LinkedList<ManageRouteCRUDService>();
	private static List<ManageTrainCRUDService> ManageTrainCRUDServiceInstances = new LinkedList<ManageTrainCRUDService>();
	private static List<ManageTicketCRUDService> ManageTicketCRUDServiceInstances = new LinkedList<ManageTicketCRUDService>();
	private static List<ManageOrderCRUDService> ManageOrderCRUDServiceInstances = new LinkedList<ManageOrderCRUDService>();
	private static List<ManageAccoutCRUDService> ManageAccoutCRUDServiceInstances = new LinkedList<ManageAccoutCRUDService>();
	private static List<ManageSeatCRUDService> ManageSeatCRUDServiceInstances = new LinkedList<ManageSeatCRUDService>();
	private static List<ModifyTripService> ModifyTripServiceInstances = new LinkedList<ModifyTripService>();
	private static List<DeleteOverdueOrderService> DeleteOverdueOrderServiceInstances = new LinkedList<DeleteOverdueOrderService>();
	
	static {
		AllServiceInstance.put("TrainTicketSystem", TrainTicketSystemInstances);
		AllServiceInstance.put("ThirdPartyServices", ThirdPartyServicesInstances);
		AllServiceInstance.put("BuyTicketService", BuyTicketServiceInstances);
		AllServiceInstance.put("QueryOrderService", QueryOrderServiceInstances);
		AllServiceInstance.put("ManageRouteCRUDService", ManageRouteCRUDServiceInstances);
		AllServiceInstance.put("ManageTrainCRUDService", ManageTrainCRUDServiceInstances);
		AllServiceInstance.put("ManageTicketCRUDService", ManageTicketCRUDServiceInstances);
		AllServiceInstance.put("ManageOrderCRUDService", ManageOrderCRUDServiceInstances);
		AllServiceInstance.put("ManageAccoutCRUDService", ManageAccoutCRUDServiceInstances);
		AllServiceInstance.put("ManageSeatCRUDService", ManageSeatCRUDServiceInstances);
		AllServiceInstance.put("ModifyTripService", ModifyTripServiceInstances);
		AllServiceInstance.put("DeleteOverdueOrderService", DeleteOverdueOrderServiceInstances);
	} 
	
	public static List getAllInstancesOf(String ClassName) {
			 return AllServiceInstance.get(ClassName);
	}	
	
	public static TrainTicketSystem createTrainTicketSystem() {
		TrainTicketSystem s = new TrainTicketSystemImpl();
		TrainTicketSystemInstances.add(s);
		return s;
	}
	public static ThirdPartyServices createThirdPartyServices() {
		ThirdPartyServices s = new ThirdPartyServicesImpl();
		ThirdPartyServicesInstances.add(s);
		return s;
	}
	public static BuyTicketService createBuyTicketService() {
		BuyTicketService s = new BuyTicketServiceImpl();
		BuyTicketServiceInstances.add(s);
		return s;
	}
	public static QueryOrderService createQueryOrderService() {
		QueryOrderService s = new QueryOrderServiceImpl();
		QueryOrderServiceInstances.add(s);
		return s;
	}
	public static ManageRouteCRUDService createManageRouteCRUDService() {
		ManageRouteCRUDService s = new ManageRouteCRUDServiceImpl();
		ManageRouteCRUDServiceInstances.add(s);
		return s;
	}
	public static ManageTrainCRUDService createManageTrainCRUDService() {
		ManageTrainCRUDService s = new ManageTrainCRUDServiceImpl();
		ManageTrainCRUDServiceInstances.add(s);
		return s;
	}
	public static ManageTicketCRUDService createManageTicketCRUDService() {
		ManageTicketCRUDService s = new ManageTicketCRUDServiceImpl();
		ManageTicketCRUDServiceInstances.add(s);
		return s;
	}
	public static ManageOrderCRUDService createManageOrderCRUDService() {
		ManageOrderCRUDService s = new ManageOrderCRUDServiceImpl();
		ManageOrderCRUDServiceInstances.add(s);
		return s;
	}
	public static ManageAccoutCRUDService createManageAccoutCRUDService() {
		ManageAccoutCRUDService s = new ManageAccoutCRUDServiceImpl();
		ManageAccoutCRUDServiceInstances.add(s);
		return s;
	}
	public static ManageSeatCRUDService createManageSeatCRUDService() {
		ManageSeatCRUDService s = new ManageSeatCRUDServiceImpl();
		ManageSeatCRUDServiceInstances.add(s);
		return s;
	}
	public static ModifyTripService createModifyTripService() {
		ModifyTripService s = new ModifyTripServiceImpl();
		ModifyTripServiceInstances.add(s);
		return s;
	}
	public static DeleteOverdueOrderService createDeleteOverdueOrderService() {
		DeleteOverdueOrderService s = new DeleteOverdueOrderServiceImpl();
		DeleteOverdueOrderServiceInstances.add(s);
		return s;
	}
}	
