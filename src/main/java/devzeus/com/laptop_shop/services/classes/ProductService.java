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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private boolean isValidImage(String img) {
        return img.endsWith(".jpg") || img.endsWith(".jpeg") ||
                img.endsWith(".png") || img.endsWith(".gif") ||
                img.endsWith(".bmp");
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (file.getSize() == 0)
            return "Anh bi rong";
        if (file.getSize() > 10 * 1024 * 1024) {
            return "File is too large. Maximum size is 10MB";
        }
        if (!isImage(file)) {
            return "File is not an image";
        }
        // get file name
        String fileName = file.getOriginalFilename();
        // generate code random base on UUID
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // check and create if haven't existed
        if (!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO) throws IOException {
        Category categoryExisting = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Cannot found category with id = " + productDTO.getCategoryId()));
        Brand brandExisting = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new NotFoundException("Cannot found brand with id = " + productDTO.getBrandId()));
        // get product old by id
        Product existingProduct = getProductById(productId);

        // handle delete old image if has image uploaded
        if (!productDTO.getFileImage().isEmpty()) {
            String uploadDir = "uploads/";
            java.nio.file.Path oldImagePath = Paths.get(uploadDir + existingProduct.getThumbnail());
            try {
                Files.delete(oldImagePath);
            } catch (Exception e) {
                throw new RuntimeException("Cannot delete old image." + e.getMessage());
            }
            // store file be able to exception
            String newThumbnail = storeFile(productDTO.getFileImage());
            existingProduct.setThumbnail(newThumbnail);
        }

        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setRam(productDTO.getRam());
        existingProduct.setBatteryCapacity(productDTO.getBatteryCapacity());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setMonitor(productDTO.getMonitor());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setCategory(categoryExisting);
        existingProduct.setBrand(brandExisting);

        // create if chua ton tai, update if ton tai
        productRepository.save(existingProduct);

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
