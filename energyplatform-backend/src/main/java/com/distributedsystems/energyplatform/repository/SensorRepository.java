package com.distributedsystems.energyplatform.repository;

import com.distributedsystems.energyplatform.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Sensor findByDevice_Id(Long id);

    @Modifying
    @Query("update Sensor s set s.description = :description where s.id = :id")
    void updateDevice(Long id, String description);
}
