package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.ProductDTO;
import devzeus.com.laptop_shop.dtos.requests.ProductImageDTO;
import devzeus.com.laptop_shop.dtos.responses.ProductResponse;
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

    List<Product> getProductByCategory(Long categoryId);

    Product getProductById(Long productId);

    Product getProductByName(String name);

    ProductResponse getProductResponse(Long id);

    List<ProductResponse> getAllProductResponses();

    List<ProductResponse> getAllProductResponses(List<Product> products);

    void createProductImage(Long productId, ProductImageDTO productDTO);

    List<Product> searchProductsByKeyword(String keyword);

    Page<Product> getAllProducts(int pageNo);

    Page<Product> getProductsByPage(int page, int size);

    Page<Product> getProductsBelow5M(int page, int size, long maxPrice);

    Page<Product> getProductsBetween(int page, int size, long minPrice, long maxPrice);


    Page<Product> getProductsAbove50M(int page, int size, long minPrice);

    Page<Product> getProductRecent(int page, int size);

    Page<Product> getProductsAToZ(int page, int size);

    Page<Product> getProductsZToA(int page, int size);

    Page<Product> getFilteredProducts(int page, int size, String priceRange, String criteria);

}
