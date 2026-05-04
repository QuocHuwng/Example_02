package org.device_management.repository;

import org.device_management.entity.Device;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query("SELECT d FROM Device d WHERE " +
            "(:keyword IS NULL OR LOWER(d.deviceName) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:brandId IS NULL OR d.brand.id = :brandId)")
    Page<Device> search(String keyword, Long brandId, Pageable pageable);

    List<Device> findByBrandId(Long brandId);
}