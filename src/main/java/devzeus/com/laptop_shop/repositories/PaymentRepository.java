package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByType(String name);
}
