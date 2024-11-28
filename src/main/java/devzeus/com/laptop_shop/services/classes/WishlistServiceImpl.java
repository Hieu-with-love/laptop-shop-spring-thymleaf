package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.models.User;
import devzeus.com.laptop_shop.models.Wishlist;
import devzeus.com.laptop_shop.repositories.WishlistItemRepository;
import devzeus.com.laptop_shop.repositories.WishlistRepository;
import devzeus.com.laptop_shop.services.interfaces.WishlistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;

    @Override
    @Transactional
    public Wishlist getWishlistByUserId(Long id) {
        return wishlistRepository.findByUserId(id);
    }

    @Override
    public Wishlist getWishlistById(Long id) {
        return wishlistRepository.findById(id).orElse(new Wishlist());
    }

    @Override
    public boolean existsWishlist(Long userId) {
        return wishlistRepository.existsByUserId(userId);
    }

    @Override
    @Transactional
    public void createWishlist(User user) {
        if (!existsWishlist(user.getId())) {
            Wishlist wishlist = Wishlist.builder()
                    .user(user)
                    .build();
            wishlistRepository.save(wishlist);
        }
    }

    @Override
    @Transactional
    public void clearWishlist(Long id) {
        Wishlist wishlist = this.getWishlistById(id);
        wishlist.getItems().clear();
        log.info("Deleting all items from wishlist with ID: {}", id);
        wishlistItemRepository.deleteAllByWishlistId(id);
    }


}
