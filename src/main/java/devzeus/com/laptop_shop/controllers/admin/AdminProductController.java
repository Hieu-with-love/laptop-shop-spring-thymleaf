package devzeus.com.laptop_shop.controllers.admin;

import devzeus.com.laptop_shop.dtos.requests.ProductDTO;
import devzeus.com.laptop_shop.models.Brand;
import devzeus.com.laptop_shop.models.Category;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.repositories.CategoryRepository;
import devzeus.com.laptop_shop.repositories.ProductImageRepository;
import devzeus.com.laptop_shop.services.classes.BrandService;
import devzeus.com.laptop_shop.services.classes.CategoryService;
import devzeus.com.laptop_shop.services.classes.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @GetMapping
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/products/index";
    }

    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        ProductDTO productDTO = new ProductDTO();
        // Get list brands
        List<Brand> brands = brandService.getAllBrands();
        // Get list categories
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("pageTitle", "Create Product");
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("brandsList", brands);
        model.addAttribute("categoriesList", categories);
        return "admin/products/CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            @RequestParam("fileImage") MultipartFile file,
            BindingResult bindingResult,
            Model model
    ) {
        if (productDTO.getFileImage().isEmpty()) {
            bindingResult.addError(new FieldError("productDTO", "image", "The image is required"));
        }

        if (bindingResult.hasErrors()) {
            return "admin/products/CreateProduct";
        }

        MultipartFile multipartFile = productDTO.getFileImage();
        try {
            String img = storeFile(file);
            if (!isValidImage(img)) {
                bindingResult.addError(new FieldError("productDTO", "image", "The image is invalid"));
            }
            // validated product
            Product product = productService.createProduct(productDTO, img);
            if (product == null) {
                model.addAttribute("error", "Sản phẩm bị null, có lẽ bạn nhập sai. Vui lòng thử lại !");
                return "admin/products/CreateProduct";
            }
        } catch (IOException ioe) {
            model.addAttribute("error", "Tạo sản phẩm thất bại. Vui lòng thử lại !");
            return "admin/products/CreateProduct";
        }
        // return to products page
        return "redirect:/admin/products";
    }

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

    private String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        int length = 10;
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }

        return result.toString();
    }

}
