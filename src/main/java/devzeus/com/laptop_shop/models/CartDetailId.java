package devzeus.com.laptop_shop.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class CartDetailId implements Serializable {
    private Long cartId;
    private Long productId;

    public CartDetailId() {
    }

    public CartDetailId(Long CartId, Long ProductId) {
        this.cartId = CartId;
        this.productId = ProductId;
    }

    // Getters, Setters, equals() và hashCode() để đảm bảo đúng logic khóa chính
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDetailId that = (CartDetailId) o;
        return Objects.equals(cartId, that.cartId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }
}
