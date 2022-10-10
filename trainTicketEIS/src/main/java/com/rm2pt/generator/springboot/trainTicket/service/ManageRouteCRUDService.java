package com.rm2pt.generator.springboot.trainTicket.service;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rm2pt.generator.springboot.trainTicket.entity.*;
import com.rm2pt.generator.springboot.trainTicket.utils.DaoManage;
import com.rm2pt.generator.springboot.trainTicket.utils.PreconditionException;
import com.rm2pt.generator.springboot.trainTicket.utils.ServiceManage;
import com.rm2pt.generator.springboot.trainTicket.utils.StandardOPs;
import com.rm2pt.generator.springboot.trainTicket.redis.CurrentUtils;

@Transactional
@Service
public class ManageRouteCRUDService{
	@Autowired CurrentUtils currentUtils;
	@Autowired private DaoManage DM;
		
	@Autowired private ServiceManage serviceManage;

	public Boolean createRoute(String routeid,String startstation,String endstation,String time,String trainid) throws PreconditionException{
		Route route = DM.getRouteDao().findByRouteId(routeid);
		Train train = DM.getTrainDao().findByTrainId(trainid);
		
		if(StandardOPs.oclIsUndefined(route) == true && StandardOPs.oclIsUndefined(train) == false)
		{
			Route rou = new Route();
			rou.setRouteId(routeid);
			rou.setStartStation(startstation);
			rou.setEndStation(endstation);
			rou.setTime(time);
			rou.setTrainId(trainid);
			DM.getRouteDao().save(rou);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Route queryRoute(String routeid) throws PreconditionException{
		Route route = DM.getRouteDao().findByRouteId(routeid);
		
		if(StandardOPs.oclIsUndefined(route) == false)
		{
			return route;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean modifyRoute(String routeid,String startstation,String endstation,String time,String trainid) throws PreconditionException{
		Route route = DM.getRouteDao().findByRouteId(routeid);
		Train train = DM.getTrainDao().findByTrainId(trainid);
		
		if(StandardOPs.oclIsUndefined(route) == false && StandardOPs.oclIsUndefined(train) == false)
		{
			route.setRouteId(routeid);
			route.setStartStation(startstation);
			route.setEndStation(endstation);
			route.setTime(time);
			route.setTrainId(trainid);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
	public Boolean deleteRoute(String routeid) throws PreconditionException{
		Route route = DM.getRouteDao().findByRouteId(routeid);
		
		if(StandardOPs.oclIsUndefined(route) == false && route!= null)
		{
			DM.getRouteDao().delete(route);
			return true;
			
		}else{
		
		 	 throw new PreconditionException();
		
		}
		
	}
				
	public static Object GetData(Optional<?> op) {
		if (op.isPresent())
			return op.get();
		else 
			return null;
	}
}
