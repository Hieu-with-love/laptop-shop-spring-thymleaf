package devzeus.com.laptop_shop.controllers.admin;

import devzeus.com.laptop_shop.dtos.requests.ProductDTO;
import devzeus.com.laptop_shop.dtos.responses.ProductResponse;
import devzeus.com.laptop_shop.models.Brand;
import devzeus.com.laptop_shop.models.Category;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.services.classes.BrandService;
import devzeus.com.laptop_shop.services.classes.CategoryService;
import devzeus.com.laptop_shop.services.classes.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductControllerAdmin {
    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @GetMapping
    public String showProducts(Model model,
                               @Param("keyword") String keyword,
                               @RequestParam(name = "pageNo", defaultValue = "1") int pageNo) {
        List<ProductResponse> products = productService.getAllProductResponses();
//        if (keyword != null) {
//            model.addAttribute("keyword", keyword);
//            products = productService.searchProductsByKeyword(keyword);
//        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
        model.addAttribute("pageTitle", "Product Management");
        return "admin/product/page-list-product";
    }

    private void updateDataForForm(Model model) {
        // Get list brands
        List<Brand> brands = brandService.getAllBrands();
        // Get list categories
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("brandsList", brands);
        model.addAttribute("categoriesList", categories);
    }

    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("productDTO", productDTO);

        updateDataForForm(model);

        return "admin/product/page-add-product";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult bindingResult,
            @RequestParam(value = "fileImage") MultipartFile file,
            Model model
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            updateDataForForm(model);
            return "admin/product/page-add-product";
        }

        Product product = productService.createProduct(productDTO, file);
        if (product == null) {
            updateDataForForm(model);
            model.addAttribute("error", "Sản phẩm bị null, có lẽ bạn nhập sai. Vui lòng thử lại !");
            return "admin/product/page-add-product";
        }

        // return to products page
        return "redirect:/admin/products";
    }

    @GetMapping("/update")
    public String showUpdateProductForm(Model model, @RequestParam Long id) {
        Product existingProduct = productService.getProductById(id);
        ProductDTO productDTO = ProductDTO.builder()
                .name(existingProduct.getName())
                .price(existingProduct.getPrice())
                .ram(existingProduct.getRam())
                .batteryCapacity(existingProduct.getBatteryCapacity())
                .monitor(existingProduct.getMonitor())
                .description(existingProduct.getDescription())
                .quantity(existingProduct.getQuantity())
                .brandId(existingProduct.getBrand().getId())
                .categoryId(existingProduct.getCategory().getId())
                .build();
        // Get list brands
        List<Brand> brands = brandService.getAllBrands();
        // Get list categories
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("product", existingProduct);
        model.addAttribute("brandsList", brands);
        model.addAttribute("categoriesList", categories);
        return "admin/product/page-update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        Product existingProduct = productService.getProductById(id);
        model.addAttribute("product", existingProduct);
        if (existingProduct == null) {
            bindingResult.addError(new FieldError("productDTO", "product", "The product is required"));
        }
        if (bindingResult.hasErrors()) {
            return "admin/product/page-update-product";
        }
        Product product = productService.updateProduct(id, productDTO);
        if (product == null) {
            // handle exception with alert, use js code
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
