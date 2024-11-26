package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.models.Address;
import devzeus.com.laptop_shop.repositories.AddressRepository;
import devzeus.com.laptop_shop.services.interfaces.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {
    private final AddressRepository addressRepository;

    @Override
    public List<Address> findByUser_Username(String userUsername) {
        return addressRepository.findByUser_Email(userUsername);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public <S extends Address> S save(S entity) {
        return addressRepository.save(entity);
    }

    @Override
    public Optional<Address> findById(Long aLong) {
        return addressRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return addressRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return addressRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        addressRepository.deleteById(aLong);
    }

    @Override
    public void deleteAll() {
        addressRepository.deleteAll();
    }
}
