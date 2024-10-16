package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.ProductDTO;
import devzeus.com.laptop_shop.dtos.requests.ProductImageDTO;
import devzeus.com.laptop_shop.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO, MultipartFile file) throws IOException;

    Product updateProduct(Long productId, ProductDTO productDTO) throws IOException;

    void deleteProduct(Long productId);

    List<Product> getAllProducts();

    Product getProductById(Long productId);

    void createProductImage(Long productId, ProductImageDTO productDTO);

    List<Product> searchProductsByKeyword(String keyword);

    Page<Product> getAllProducts(int pageNo);
}
