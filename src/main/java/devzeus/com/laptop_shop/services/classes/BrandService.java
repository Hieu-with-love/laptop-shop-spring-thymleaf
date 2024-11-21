package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.requests.BrandDTO;
import devzeus.com.laptop_shop.models.Brand;
import devzeus.com.laptop_shop.repositories.BrandRepository;
import devzeus.com.laptop_shop.services.interfaces.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> findByName(String name) {
        return brandRepository.findByName(name);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public <S extends Brand> S save(S entity) {
        return brandRepository.save(entity);
    }

    @Override
    public Optional<Brand> findById(Long aLong) {
        return brandRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return brandRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return brandRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        brandRepository.deleteById(aLong);
    }

    @Override
    public boolean create(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brand.setOs(brandDTO.getOs());
        brand.setWarranty(brandDTO.getWarranty());
        brandRepository.save(brand);
        return true;
    }

    @Override
    public boolean update(BrandDTO brandDTO, Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElse(null);
        brand.setName(brandDTO.getName());
        brand.setOs(brandDTO.getOs());
        brand.setWarranty(brandDTO.getWarranty());
        brandRepository.save(brand);
        return true;
    }

    @Override
    public void deleteBrand(Long brandId) {
        brandRepository.deleteById(brandId);
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long brandId) {
        return null;
    }
}
