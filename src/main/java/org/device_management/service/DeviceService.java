package org.device_management.service;

import org.device_management.entity.Device;
import org.springframework.data.domain.Page;

public abstract class DeviceService {

    public abstract Page<Device> getAll(int page);

    public abstract Page<Device> search(String keyword, Long brandId, int page);

    public abstract void save(Device device);

    public abstract Device findById(Long id);

    public abstract void delete(Long id);
}