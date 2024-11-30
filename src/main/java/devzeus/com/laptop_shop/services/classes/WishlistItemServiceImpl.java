package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.responses.WishlistItemResponse;
import devzeus.com.laptop_shop.exceptions.NotFoundException;
import devzeus.com.laptop_shop.models.Product;
import devzeus.com.laptop_shop.models.Wishlist;
import devzeus.com.laptop_shop.models.WishlistItem;
import devzeus.com.laptop_shop.repositories.ProductRepository;
import devzeus.com.laptop_shop.repositories.WishlistItemRepository;
import devzeus.com.laptop_shop.repositories.WishlistRepository;
import devzeus.com.laptop_shop.services.interfaces.WishlistItemService;
import devzeus.com.laptop_shop.utils.Constant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistItemServiceImpl implements WishlistItemService {
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Wishlist getWishlist(Long id) {
        return wishlistRepository.findById(id).orElse(new Wishlist());
    }

    @Override
    @Transactional
    public WishlistItem getItem(Long wishlistId, Long productId) {
        Wishlist wishlist = this.getWishlist(wishlistId);
        return wishlist.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new WishlistItem());
    }

    @Override
    @Transactional
    public List<WishlistItemResponse> getItems(Long wishlistId) {
        List<WishlistItem> wishlistItems = wishlistItemRepository.findAll();
        return wishlistItems.stream()
                .filter(item -> item.getWishlist().getId().equals(wishlistId))
                .map(item -> WishlistItemResponse.builder()
                        .thumbnail(item.getProduct().getThumbnail())
                        .title(item.getProduct().getName())
                        .unitPrice(Constant.formatter.format(item.getProduct().getPrice()))
                        .status(item.isStatus())
                        .productId(item.getProduct().getId())
                        .isUrlImg(item.getProduct().getThumbnail().contains("https"))
                        .build()
                )
                .toList();
    }

    private boolean existsItem(Long productId) {
        List<WishlistItem> wishlistItems = wishlistItemRepository.findAll();
        WishlistItem itemToInsert = wishlistItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new WishlistItem());
        return itemToInsert.getId() != null;
    }

    @Override
    @Transactional
    public boolean insertItemIntoWishlist(Long wishlistId, Long productId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(
                () -> new NotFoundException("Wishlist with id " + wishlistId + " not found")
        );
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("Product with id " + productId + " not found")
        );
        boolean status = product.getId() != null && product.getQuantity() > 0;

        WishlistItem item = WishlistItem.builder()
                .wishlist(wishlist)
                .product(product)
                .status(status)
                .build();

        if (!existsItem(productId)) {
            wishlist.getItems().add(item);
            wishlistItemRepository.save(item);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void removeItemFromWishlist(Long wishlistId, Long productId) {
        Wishlist wishlist = this.getWishlist(wishlistId);
        WishlistItem itemToRemove = wishlist.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new WishlistItem());
        wishlist.getItems().remove(itemToRemove);
        wishlistItemRepository.deleteWishlistItemById(itemToRemove.getId());
    }

    @Override
    @Transactional
    public int getItemsCount(Long wishlistId) {
        return wishlistItemRepository.countByWishlistId(wishlistId);
    }
}
