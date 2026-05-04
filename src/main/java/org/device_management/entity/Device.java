package org.device_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 150, message = "Tên thiết bị từ 5-150 ký tự")
    private String deviceName;

    @NotBlank(message = "Model code không được để trống")
    private String modelCode;

    @Positive(message = "Giá phải lớn hơn 0")
    private Double price;

    @NotNull(message = "Ngày sản xuất không được để trống")
    @PastOrPresent(message = "Ngày sản xuất không được là ngày tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufactureDate;

    private String productImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private Boolean isAvailable = true;
}