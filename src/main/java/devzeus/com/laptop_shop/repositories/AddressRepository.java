package devzeus.com.laptop_shop.repositories;

import devzeus.com.laptop_shop.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_Email(String userEmail);
}
