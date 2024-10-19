package devzeus.com.laptop_shop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "carts")
public class Cart extends TrackingDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phone_number_owner", nullable = false)
    private String phoneNumberOwner;

    @ManyToMany
    @JoinTable(
            name = "cart_products", // Ten bang trung gian
            joinColumns = @JoinColumn(name = "cart_id"), // Khoa ngoai toi cart
            inverseJoinColumns = @JoinColumn(name = "product_id") // Khoa ngoai voi product
    )
    private List<Product> products = new ArrayList<>();
}
