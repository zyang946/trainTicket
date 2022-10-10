package com.rm2pt.generator.springboot.trainTicket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Date;
import com.rm2pt.generator.springboot.trainTicket.entity.*;

public interface TrainRepository extends JpaRepository<Train, Integer> ,JpaSpecificationExecutor<Train>{
			Train findByTrainId(Object trainid);
}
