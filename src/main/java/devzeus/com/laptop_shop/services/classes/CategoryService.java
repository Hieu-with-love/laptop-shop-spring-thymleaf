package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.requests.CategoryDTO;
import devzeus.com.laptop_shop.models.Category;
import devzeus.com.laptop_shop.repositories.CategoryRepository;
import devzeus.com.laptop_shop.services.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public boolean add(CategoryDTO categoryDTO) {
        try {
            if (categoryRepository.findByName(categoryDTO.getName()) != null) {
                return false;
            }
            Category category = new Category();
            category.setName(categoryDTO.getName());
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(CategoryDTO categoryDTO, Long id) {
        try {
            Category category = categoryRepository.findById(id).get();
            category.setName(categoryDTO.getName());
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public <S extends Category> S save(S entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public Optional<Category> findById(Long aLong) {
        return categoryRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return categoryRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        categoryRepository.deleteById(aLong);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
