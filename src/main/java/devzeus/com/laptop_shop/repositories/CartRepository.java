package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Cart findByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(*) > 0 FROM Cart c WHERE c.user.id = :userId")
    boolean existsCartByUserId(@Param("userId") Long userId);
}
