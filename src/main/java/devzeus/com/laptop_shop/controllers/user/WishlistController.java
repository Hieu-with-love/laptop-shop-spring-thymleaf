package devzeus.com.laptop_shop.controllers.user;

import devzeus.com.laptop_shop.dtos.responses.WishlistItemResponse;
import devzeus.com.laptop_shop.services.classes.WishlistItemServiceImpl;
import devzeus.com.laptop_shop.services.classes.WishlistServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistServiceImpl wishlistService;
    private final WishlistItemServiceImpl wishlistItemService;

    @GetMapping
    public String showWishlists(Model model,
                                @RequestParam("wishlistId") Long wishlistId) {
        List<WishlistItemResponse> wishlistItems = wishlistItemService.getItems(wishlistId);
        model.addAttribute("wishlistItems", wishlistItems);
        model.addAttribute("wishlistEmpty", wishlistItems.isEmpty());
        return "/user/wishlist";
    }


}
