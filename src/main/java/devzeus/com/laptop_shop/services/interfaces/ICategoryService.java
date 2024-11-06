package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.models.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> findByName(String name);

    List<Category> findAll();

    <S extends Category> S save(S entity);

    Optional<Category> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    List<Category> getAllCategories();
}
