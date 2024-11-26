package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.models.Address;

import java.util.List;
import java.util.Optional;

public interface IAddressService {
    List<Address> findByUser_Username(String userUsername);

    List<Address> findAll();

    <S extends Address> S save(S entity);

    Optional<Address> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void deleteAll();
}
