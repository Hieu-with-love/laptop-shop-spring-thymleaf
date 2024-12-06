package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> searchProducts(String keyword);

    List<Product> findByCategoryId(Long categoryId);

    Optional<Product> findByName(String name);

//    @Query("SELECT p FROM Product p WHERE " +
//            "(:priceRange IS NULL OR " +
//            "   (:priceRange = '<5' AND p.price < 5000000) OR " +
//            "   (:priceRange = '5-10' AND p.price BETWEEN 5000000 AND 10000000) OR " +
//            "   (:priceRange = '10-15' AND p.price BETWEEN 10000000 AND 15000000) OR " +
//            "   (:priceRange = '15-20' AND p.price BETWEEN 15000000 AND 20000000) OR " +
//            "   (:priceRange = '20-30' AND p.price BETWEEN 20000000 AND 30000000) OR " +
//            "   (:priceRange = '30-50' AND p.price BETWEEN 30000000 AND 50000000) OR " +
//            "   (:priceRange = '>50' AND p.price > 50000000)) AND " +
//            "(:brand IS NULL OR p.brand = :brand) AND " +
//            "(:cpu IS NULL OR p.cpu = :cpu) AND " +
//            "(:ram IS NULL OR p.ram = :ram) AND " +
//            "(:graphicCard IS NULL OR p.graphicCard = :graphicCard)")
//    List<Product> filterProducts(
//            @Param("priceRange") String priceRange,
//            @Param("brand") String brand,
//            @Param("cpu") String cpu,
//            @Param("ram") String ram,
//            @Param("storage") String storage,
//            @Param("graphicCard") String graphicCard,
//            @Param("screenSize") String screenSize,
//            @Param("feature") String feature,
//            @Param("deviceType") String deviceType);

    @Query("SELECT p FROM Product p WHERE p.price < :maxPrice")
    Page<Product> findProductsByPriceBelow(@Param("maxPrice") long maxPrice, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.price < :minPrice")
    Page<Product> findProductsByPriceGreaterThan(@Param("minPrice") long minPrice, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.price between :minPrice and :maxPrice")
    Page<Product> findProductsByPriceBetween(@Param("minPrice") long minPrice, @Param("maxPrice") long maxPrice, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.createdAt >= :recentDate")
    Page<Product> findRecentProducts(@Param("recentDate") LocalDate recentDate, Pageable pageable);

    Page<Product> findAllBy(Pageable pageable);


}
