package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.ProductDTO;
import devzeus.com.laptop_shop.dtos.requests.ProductImageDTO;
import devzeus.com.laptop_shop.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO, String imagePath);

    Product updateProduct(Long productId, ProductDTO productDTO);

    void deleteProduct(Long productId);

    List<Product> getAllProducts();

    Product getProductById(Long productId);

    void createProductImage(Long productId, ProductImageDTO productDTO);

    List<String> storeMultiFile(List<MultipartFile> files);
}
