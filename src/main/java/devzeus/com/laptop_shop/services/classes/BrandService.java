package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.requests.BrandDTO;
import devzeus.com.laptop_shop.models.Brand;
import devzeus.com.laptop_shop.repositories.BrandRepository;
import devzeus.com.laptop_shop.services.interfaces.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final BrandRepository brandRepository;

    @Override
    public Brand createBrand(BrandDTO brandDTO) {
        return null;
    }

    @Override
    public Brand updateBrand(BrandDTO brandDTO, Long brandId) {
        return null;
    }

    @Override
    public void deleteBrand(Long brandId) {

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
