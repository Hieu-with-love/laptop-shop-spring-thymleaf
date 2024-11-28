package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.responses.WishlistItemResponse;
import devzeus.com.laptop_shop.models.Wishlist;
import devzeus.com.laptop_shop.models.WishlistItem;

import java.util.List;

public interface WishlistItemService {
    Wishlist getWishlist(Long id);

    WishlistItem getItem(Long wishlistId, Long productId);

    List<WishlistItemResponse> getItems(Long wishlistId);

    boolean insertItemIntoWishlist(Long wishlistId, Long productId);

    void removeItemFromWishlist(Long wishlistId, Long productId);

    int getItemsCount(Long wishlistId);
}
