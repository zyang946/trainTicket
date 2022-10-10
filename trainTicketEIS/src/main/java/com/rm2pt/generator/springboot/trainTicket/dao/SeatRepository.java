package com.rm2pt.generator.springboot.trainTicket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Date;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public interface SeatRepository extends JpaRepository<Seat, Integer> ,JpaSpecificationExecutor<Seat>{
			List<Seat> findByTrainId(Object TrainId);
			Seat findBySeatIdAndTrainId(Object seatId,Object TrainId);
			Seat findByTrainIdAndSeatId(Object TrainId,Object seatId);
			Seat findBySeatId(Object seatid);
}
