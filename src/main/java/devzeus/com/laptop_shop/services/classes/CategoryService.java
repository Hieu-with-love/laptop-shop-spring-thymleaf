package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.models.Category;
import devzeus.com.laptop_shop.repositories.CategoryRepository;
import devzeus.com.laptop_shop.services.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
