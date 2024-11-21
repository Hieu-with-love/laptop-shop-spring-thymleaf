package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.BrandDTO;
import devzeus.com.laptop_shop.models.Brand;

import java.util.List;
import java.util.Optional;

public interface IBrandService {
    Brand findByName(String name);

    List<Brand> findAll();

    <S extends Brand> S save(S entity);

    Optional<Brand> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    boolean create(BrandDTO brandDTO);

    boolean update(BrandDTO brandDTO, Long brandId);

    void deleteBrand(Long brandId);

    List<Brand> getAllBrands();

    Brand getBrandById(Long brandId);
}
