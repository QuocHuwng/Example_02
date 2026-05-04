package org.device_management;

import org.device_management.entity.Brand;
import org.device_management.entity.Device;
import org.device_management.repository.BrandRepository;
import org.device_management.repository.DeviceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner run(DeviceRepository deviceRepo, BrandRepository brandRepo) {
        return args -> {
            if (brandRepo.count() == 0) {

                Brand apple = brandRepo.save(new Brand(null, "Apple", "USA"));
                Brand samsung = brandRepo.save(new Brand(null, "Samsung", "Korea"));
                Brand sony = brandRepo.save(new Brand(null, "Sony", "Japan"));

                createDevicesForBrand(deviceRepo, apple, 4);
                createDevicesForBrand(deviceRepo, samsung, 4);
                createDevicesForBrand(deviceRepo, sony, 4);
            }
        };
    }

    private void createDevicesForBrand(DeviceRepository repo, Brand brand, int count) {
        for (int i = 1; i <= count; i++) {
            Device d = new Device();
            d.setDeviceName(brand.getName() + " Product " + i);
            d.setModelCode(brand.getName().substring(0, 2).toUpperCase() + "-00" + i);
            d.setPrice(12000000.0 + i * 3000000);
            d.setManufactureDate(LocalDate.now().minusMonths(i));
            d.setProductImage("default.jpg");
            d.setBrand(brand);
            d.setIsAvailable(true);
            repo.save(d);
        }
    }
}