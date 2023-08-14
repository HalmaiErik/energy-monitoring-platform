package com.distributedsystems.energyplatform.repository;

import com.distributedsystems.energyplatform.entity.SensorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorLogRepository extends JpaRepository<SensorLog, Long> {

    List<SensorLog> findAllBySensor_IdOrderByTimeStamp(Long id);
    SensorLog findFirstBySensor_IdOrderByTimeStampDesc(Long id);
}

