package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.requests.ProductDTO;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.repositories.ProductRepository;
import devzeus.com.laptop_shop.services.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    // dependency injection
    private final ProductRepository productRepository;

    @Override
    public Product addProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Product getProductById(Long productId) {
        return null;
    }
}
