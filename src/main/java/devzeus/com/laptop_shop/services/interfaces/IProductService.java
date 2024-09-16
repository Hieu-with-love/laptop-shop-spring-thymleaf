package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.ProductDTO;
import devzeus.com.laptop_shop.models.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(ProductDTO productDTO);

    Product updateProduct(Long productId, ProductDTO productDTO);

    void deleteProduct(Long productId);

    List<Product> getAllProducts();

    Product getProductById(Long productId);
}
