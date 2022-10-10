package entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Map;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.File;

public class EntityManager {

	private static Map<String, List> AllInstance = new HashMap<String, List>();
	
	private static List<Route> RouteInstances = new LinkedList<Route>();
	private static List<Train> TrainInstances = new LinkedList<Train>();
	private static List<Ticket> TicketInstances = new LinkedList<Ticket>();
	private static List<Order> OrderInstances = new LinkedList<Order>();
	private static List<Accout> AccoutInstances = new LinkedList<Accout>();
	private static List<Seat> SeatInstances = new LinkedList<Seat>();

	
	/* Put instances list into Map */
	static {
		AllInstance.put("Route", RouteInstances);
		AllInstance.put("Train", TrainInstances);
		AllInstance.put("Ticket", TicketInstances);
		AllInstance.put("Order", OrderInstances);
		AllInstance.put("Accout", AccoutInstances);
		AllInstance.put("Seat", SeatInstances);
	} 
		
	/* Save State */
	public static void save(File file) {
		
		try {
			
			ObjectOutputStream stateSave = new ObjectOutputStream(new FileOutputStream(file));
			
			stateSave.writeObject(RouteInstances);
			stateSave.writeObject(TrainInstances);
			stateSave.writeObject(TicketInstances);
			stateSave.writeObject(OrderInstances);
			stateSave.writeObject(AccoutInstances);
			stateSave.writeObject(SeatInstances);
			
			stateSave.close();
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* Load State */
	public static void load(File file) {
		
		try {
			
			ObjectInputStream stateLoad = new ObjectInputStream(new FileInputStream(file));
			
			try {
				
				RouteInstances =  (List<Route>) stateLoad.readObject();
				AllInstance.put("Route", RouteInstances);
				TrainInstances =  (List<Train>) stateLoad.readObject();
				AllInstance.put("Train", TrainInstances);
				TicketInstances =  (List<Ticket>) stateLoad.readObject();
				AllInstance.put("Ticket", TicketInstances);
				OrderInstances =  (List<Order>) stateLoad.readObject();
				AllInstance.put("Order", OrderInstances);
				AccoutInstances =  (List<Accout>) stateLoad.readObject();
				AllInstance.put("Accout", AccoutInstances);
				SeatInstances =  (List<Seat>) stateLoad.readObject();
				AllInstance.put("Seat", SeatInstances);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	/* create object */  
	public static Object createObject(String Classifer) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method createObjectMethod = c.getDeclaredMethod("create" + Classifer + "Object");
			return createObjectMethod.invoke(c);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* add object */  
	public static Object addObject(String Classifer, Object ob) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method addObjectMethod = c.getDeclaredMethod("add" + Classifer + "Object", Class.forName("entities." + Classifer));
			return  (boolean) addObjectMethod.invoke(c, ob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
	
	/* add objects */  
	public static Object addObjects(String Classifer, List obs) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method addObjectsMethod = c.getDeclaredMethod("add" + Classifer + "Objects", Class.forName("java.util.List"));
			return  (boolean) addObjectsMethod.invoke(c, obs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* Release object */
	public static boolean deleteObject(String Classifer, Object ob) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method deleteObjectMethod = c.getDeclaredMethod("delete" + Classifer + "Object", Class.forName("entities." + Classifer));
			return  (boolean) deleteObjectMethod.invoke(c, ob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/* Release objects */
	public static boolean deleteObjects(String Classifer, List obs) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method deleteObjectMethod = c.getDeclaredMethod("delete" + Classifer + "Objects", Class.forName("java.util.List"));
			return  (boolean) deleteObjectMethod.invoke(c, obs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}		 	
	
	 /* Get all objects belongs to same class */
	public static List getAllInstancesOf(String ClassName) {
			 return AllInstance.get(ClassName);
	}	

   /* Sub-create object */
	public static Route createRouteObject() {
		Route o = new Route();
		return o;
	}
	
	public static boolean addRouteObject(Route o) {
		return RouteInstances.add(o);
	}
	
	public static boolean addRouteObjects(List<Route> os) {
		return RouteInstances.addAll(os);
	}
	
	public static boolean deleteRouteObject(Route o) {
		return RouteInstances.remove(o);
	}
	
	public static boolean deleteRouteObjects(List<Route> os) {
		return RouteInstances.removeAll(os);
	}
	public static Train createTrainObject() {
		Train o = new Train();
		return o;
	}
	
	public static boolean addTrainObject(Train o) {
		return TrainInstances.add(o);
	}
	
	public static boolean addTrainObjects(List<Train> os) {
		return TrainInstances.addAll(os);
	}
	
	public static boolean deleteTrainObject(Train o) {
		return TrainInstances.remove(o);
	}
	
	public static boolean deleteTrainObjects(List<Train> os) {
		return TrainInstances.removeAll(os);
	}
	public static Ticket createTicketObject() {
		Ticket o = new Ticket();
		return o;
	}
	
	public static boolean addTicketObject(Ticket o) {
		return TicketInstances.add(o);
	}
	
	public static boolean addTicketObjects(List<Ticket> os) {
		return TicketInstances.addAll(os);
	}
	
	public static boolean deleteTicketObject(Ticket o) {
		return TicketInstances.remove(o);
	}
	
	public static boolean deleteTicketObjects(List<Ticket> os) {
		return TicketInstances.removeAll(os);
	}
	public static Order createOrderObject() {
		Order o = new Order();
		return o;
	}
	
	public static boolean addOrderObject(Order o) {
		return OrderInstances.add(o);
	}
	
	public static boolean addOrderObjects(List<Order> os) {
		return OrderInstances.addAll(os);
	}
	
	public static boolean deleteOrderObject(Order o) {
		return OrderInstances.remove(o);
	}
	
	public static boolean deleteOrderObjects(List<Order> os) {
		return OrderInstances.removeAll(os);
	}
	public static Accout createAccoutObject() {
		Accout o = new Accout();
		return o;
	}
	
	public static boolean addAccoutObject(Accout o) {
		return AccoutInstances.add(o);
	}
	
	public static boolean addAccoutObjects(List<Accout> os) {
		return AccoutInstances.addAll(os);
	}
	
	public static boolean deleteAccoutObject(Accout o) {
		return AccoutInstances.remove(o);
	}
	
	public static boolean deleteAccoutObjects(List<Accout> os) {
		return AccoutInstances.removeAll(os);
	}
	public static Seat createSeatObject() {
		Seat o = new Seat();
		return o;
	}
	
	public static boolean addSeatObject(Seat o) {
		return SeatInstances.add(o);
	}
	
	public static boolean addSeatObjects(List<Seat> os) {
		return SeatInstances.addAll(os);
	}
	
	public static boolean deleteSeatObject(Seat o) {
		return SeatInstances.remove(o);
	}
	
	public static boolean deleteSeatObjects(List<Seat> os) {
		return SeatInstances.removeAll(os);
	}
  
}

