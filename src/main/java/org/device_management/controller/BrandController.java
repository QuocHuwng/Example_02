package org.device_management.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.device_management.entity.Brand;
import org.device_management.entity.Device;
import org.device_management.repository.BrandRepository;
import org.device_management.repository.DeviceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final BrandRepository brandRepo;
    private final DeviceRepository deviceRepo;

    public BrandController(BrandRepository brandRepo, DeviceRepository deviceRepo) {
        this.brandRepo = brandRepo;
        this.deviceRepo = deviceRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("brands", brandRepo.findAll());
        return "brand/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("brand", new Brand());
        return "brand/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandRepo.findById(id).orElseThrow());
        return "brand/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Brand brand, BindingResult result) {
        if (result.hasErrors()) {
            return "brand/form";
        }
        brandRepo.save(brand);
        return "redirect:/brands";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String delete(@PathVariable Long id) {
        List<Device> devices = deviceRepo.findByBrandId(id);
        for (Device d : devices) {
            d.setBrand(null);
        }
        deviceRepo.saveAll(devices);
        brandRepo.deleteById(id);
        return "redirect:/brands";
    }
}