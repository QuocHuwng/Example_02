package org.device_management.controller;

import jakarta.validation.Valid;
import org.device_management.entity.Device;
import org.device_management.repository.BrandRepository;
import org.device_management.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.UUID;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService service;
    private final BrandRepository brandRepo;
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    public DeviceController(DeviceService service, BrandRepository brandRepo) {
        this.service = service;
        this.brandRepo = brandRepo;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) Long brandId,
                       Model model) {

        model.addAttribute("devices", service.search(keyword, brandId, page));
        model.addAttribute("brands", brandRepo.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("brandId", brandId);
        return "device/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("device", new Device());
        model.addAttribute("brands", brandRepo.findAll());
        return "device/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Device device = service.findById(id);
        model.addAttribute("device", device);
        model.addAttribute("brands", brandRepo.findAll());
        return "device/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Device device,
                       BindingResult result,
                       @RequestParam("file") MultipartFile file,
                       Model model) {

        model.addAttribute("brands", brandRepo.findAll());

        if (result.hasErrors()) {
            return "device/form";
        }

        if (device.getBrand() != null && device.getBrand().getId() != null) {
            device.setBrand(brandRepo.findById(device.getBrand().getId()).orElse(null));
        } else {
            device.setBrand(null);
        }

        if (!file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Files.copy(file.getInputStream(), uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);

                device.setProductImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (device.getIsAvailable() == null) device.setIsAvailable(false);

        service.save(device);
        return "redirect:/devices";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/devices";
    }
}