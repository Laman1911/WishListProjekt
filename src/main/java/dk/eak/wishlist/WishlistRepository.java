package dk.eak.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByPublicToken(String token);
    Optional<Wishlist> findByIdAndOwnerKey(Long id, String ownerKey);
}
