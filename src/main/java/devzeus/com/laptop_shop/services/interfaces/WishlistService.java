package devzeus.com.laptop_shop.services.interfaces;


import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.models.Wishlist;

public interface WishlistService {
    Wishlist getWishlistByUserId(Long id);

    Wishlist getWishlistById(Long id);

    void createWishlist(User user);

    void clearWishlist(Long id);

    boolean existsWishlist(Long userId);
}
