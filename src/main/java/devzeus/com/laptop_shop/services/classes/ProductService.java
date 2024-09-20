package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.requests.ProductDTO;
import devzeus.com.laptop_shop.dtos.requests.ProductImageDTO;
import devzeus.com.laptop_shop.exceptions.NotFoundException;
import devzeus.com.laptop_shop.models.Brand;
import devzeus.com.laptop_shop.models.Category;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.models.ProductImage;
import devzeus.com.laptop_shop.repositories.BrandRepository;
import devzeus.com.laptop_shop.repositories.CategoryRepository;
import devzeus.com.laptop_shop.repositories.ProductImageRepository;
import devzeus.com.laptop_shop.repositories.ProductRepository;
import devzeus.com.laptop_shop.services.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    // dependency injection
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO, String imagePath) throws NotFoundException {
        Category categoryExisting = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Cannot found category with id = " + productDTO.getCategoryId()));
        Brand brandExisting = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new NotFoundException("Cannot found brand with id = " + productDTO.getBrandId()));

        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .ram(productDTO.getRam())
                .batteryCapacity(productDTO.getBatteryCapacity())
                .description(productDTO.getDescription())
                .monitor(productDTO.getMonitor())
                .quantity(productDTO.getQuantity())
                .thumbnail(imagePath)
                .category(categoryExisting)
                .brand(brandExisting)
                .build();
        return productRepository.save(product);
    }

    // has completed yet
    @Override
    public List<String> storeMultiFile(List<MultipartFile> files) {
        List<String> errors = new ArrayList<>();
        if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
        }
        String uploadDir = "uploads/";
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            try {

            } catch (Exception e) {
                throw new RuntimeException("Cannot store multi file");
            }
        }
        return null;
    }

    @Override
    public void createProductImage(Long productId, ProductImageDTO productImageDTO) {
        Product existingProduct = getProductById(productId);

        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

        productImageRepository.save(newProductImage);
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Cannot found product with id = " + productId));
    }

}
