package com.distributedsystems.energyplatform.repository;

import com.distributedsystems.energyplatform.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findAllByUser_Id(Long id);

    @Modifying
    @Query("update Device d set d.description = :description, d.address = :address where d.id = :id")
    void updateDevice(Long id, String description, String address);
}
