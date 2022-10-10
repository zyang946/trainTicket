package com.rm2pt.generator.springboot.trainTicket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Date;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public interface RouteRepository extends JpaRepository<Route, Integer> ,JpaSpecificationExecutor<Route>{
	List<Route> findByUsedTrain(Train train);
			List<Route> findByStartStationAndEndStationAndTime(Object startStation,Object endStation,Object time);
			Route findByRouteId(Object newRouteId);
}
