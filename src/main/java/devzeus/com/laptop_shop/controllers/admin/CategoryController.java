package devzeus.com.laptop_shop.controllers.admin;

import devzeus.com.laptop_shop.dtos.requests.CategoryDTO;
import devzeus.com.laptop_shop.models.Category;
import devzeus.com.laptop_shop.services.interfaces.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);
        return "admin/categories/page-list-category";
    }

    @GetMapping("/add-category")
    public String addCategory(Model model) {
        CategoryDTO categoryRequest = new CategoryDTO();
        model.addAttribute("category", categoryRequest);
        return "admin/categories/page-add-category";
    }

    @PostMapping("/insert")
    public String addCategory(@Valid @ModelAttribute("category") CategoryDTO categoryRequest,
                              BindingResult bindingResult,
                              Model model) {
        String msg = "";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("error", msg);
            return "admin/categories/list-category";
        }
        if (!categoryRequest.getName().matches("^[a-zA-Z\\s]+$")) {
            msg = "Category Name cannot contain numbers or special characters";
            model.addAttribute("error", msg);
            return "admin/categories/add-category";
        }
        if (categoryService.findByName(categoryRequest.getName()) != null) {
            msg = "Category already exists";
            model.addAttribute("error", msg);
            return "admin/categories/add-category";
        }
        if (categoryService.add(categoryRequest)) {
            return "redirect:/admin/categories";
        } else {
            msg = "Category not added";
            model.addAttribute("error", msg);
        }
        return "admin/categories/add-category";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(Model model, @PathVariable(value = "id") Long id) {
        Category category = categoryService.findById(id).get();
        model.addAttribute("category", category);
        return "admin/categories/page-edit-category";
    }

    @PostMapping("/edit-category")
    public String editCategory(Model model,
                               @ModelAttribute("category") CategoryDTO categoryRequest,
                               @RequestParam("id") Long id) {
        String msg = "";
        Category categoryOld = categoryService.findById(id).get();
        if (!categoryRequest.getName().matches("^[a-zA-Z\\s]+$")) {
            msg = "Category Name cannot contain numbers or special characters";
            model.addAttribute("error", msg);
            model.addAttribute("category", categoryOld);
            return "admin/categories/page-edit-category";
        }
        if (categoryService.update(categoryRequest, id)) {
            return "redirect:/admin/categories";
        } else {
            msg = "Category already exists";
            model.addAttribute("error", msg);
            model.addAttribute("category", categoryOld);
            return "admin/categories/page-edit-category";
        }
    }

    @GetMapping("/delete-category/{id}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable(value = "id") Long id) {
        Map<String, String> response = new HashMap<>();

        if (categoryService.delete(id)) {
            response.put("status", "success");
            response.put("message", "Category deleted successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Failed to delete the category.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
