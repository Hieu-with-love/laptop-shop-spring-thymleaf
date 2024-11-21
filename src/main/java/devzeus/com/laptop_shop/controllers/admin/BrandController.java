package devzeus.com.laptop_shop.controllers.admin;

import devzeus.com.laptop_shop.dtos.requests.BrandDTO;
import devzeus.com.laptop_shop.models.Brand;
import devzeus.com.laptop_shop.services.interfaces.IBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/brands")
@RequiredArgsConstructor
public class BrandController {
    private final IBrandService brandService;

    @GetMapping("")
    public String brands(Model model) {
        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return "admin/brands/page-list-brand";
    }

    @GetMapping("/add-brand")
    public String addBrand(Model model) {
        BrandDTO brandRequest = new BrandDTO();
        model.addAttribute("brandDto", brandRequest);
        return "admin/brands/page-add-brand";
    }

    @PostMapping("/insert")
    public String insertBrand(@Valid @ModelAttribute("brandDto") BrandDTO brandRequest,
                              BindingResult bindingResult, Model model) throws IOException {
        String msg = "";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("msg", msg);
            return "admin/brands/page-list-brand";
        }
        if (!brandRequest.getName().matches("^[a-zA-Z].*")) {
            msg = "Brand Name must start with a letter";
            model.addAttribute("error", msg);
            return "admin/brands/page-add-brand";
        }
        if (!brandRequest.getName().matches("^[a-zA-Z0-9\\s]+$")) {
            msg = "Name is not valid";
            model.addAttribute("error", msg);
            return "admin/brands/page-add-brand";
        }
        if (brandService.findByName(brandRequest.getName()) != null) {
            msg = "Name already exists";
            model.addAttribute("error", msg);
            return "admin/brands/page-add-brand";
        }
        if (brandService.create(brandRequest)) {
            return "redirect:/admin/brands";
        } else {
            msg = "Something went wrong";
            model.addAttribute("error", msg);
        }
        return "admin/brands/page-add-brand";
    }

    @GetMapping("/edit-brand/{id}")
    public String editBrand(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.findById(id).get();
        model.addAttribute("brand", brand);
        return "admin/brands/page-edit-brand";
    }

    @PostMapping("/edit-brand")
    public String editBrand(@Valid @ModelAttribute("brandDto") BrandDTO brandRequest,
                            Model model, @RequestParam("id") Long id,
                            BindingResult bindingResult) {
        String msg = "";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("msg", msg);
            return "admin/brands/page-list-brand";
        }
        if (!brandRequest.getName().matches("^[a-zA-Z].*")) {
            msg = "Brand Name must start with a letter";
            model.addAttribute("error", msg);
            model.addAttribute("brandDto", brandRequest);
            return "admin/brands/page-add-brand";
        }
        if (!brandRequest.getName().matches("^[a-zA-Z0-9\\s]+$")) {
            msg = "Name is not valid";
            model.addAttribute("msg", msg);
            return "admin/brands/page-add-brand";
        }
        Brand brand = brandService.findByName(brandRequest.getName());
        if (brand != null && !brand.getId().equals(id)) {
            msg = "Brand already exists";
            model.addAttribute("error", msg);
            return "admin/brands/page-edit-brand";
        }
        brandService.update(brandRequest, id);
        return "redirect:/admin/brands";
    }

    @GetMapping("/delete-brand/{id}")
    public String deleteBrand(@PathVariable(value = "id") Long id) {
        brandService.deleteBrand(id);
        return "redirect:/admin/brands";
    }
}