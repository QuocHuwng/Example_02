package org.device_management.service;

import org.device_management.entity.Device;
import org.device_management.repository.DeviceRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl extends DeviceService {

    private final DeviceRepository repo;

    public DeviceServiceImpl(DeviceRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<Device> getAll(int page) {
        return repo.findAll(PageRequest.of(page, 5));
    }

    @Override
    public Page<Device> search(String keyword, Long brandId, int page) {
        return repo.search(keyword, brandId, PageRequest.of(page, 5));
    }

    @Override
    public void save(Device device) {
        repo.save(device);
    }

    @Override
    public Device findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}