package devzeus.com.laptop_shop.models;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product extends TrackingDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Min(value = 1, message = "Product' price must be granter than 0")
    private BigDecimal price;

    private String description;

    private String ram;

    private Long quantity;

    @Column(name = "battery_capacity")
    private String batteryCapacity;

    private String monitor;

    @Column(name = "graphic_card", length = 200)
    private String graphicCard;

    @Column(name = "hard_drive", length = 300)
    private String hardDrive;

    @Column(name = "screen_technology", length = 300)
    private String screenTechnology;

    @Column(name = "ports", length = 500)
    private String ports;

    @Column(name = "cpu", length = 500)
    private String cpu;

    private String thumbnail;

    @Column(columnDefinition = "double default 0.0")
    private double discount;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductImage> productImages = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderDetail> orderDetails = new HashSet<>();
}
