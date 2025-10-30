package dk.eak.wishlist;

import dk.eak.wishlist.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

@Service
public class WishlistService {
    private final WishlistRepository lists;
    private final WishItemRepository items;
    private static final SecureRandom RNG = new SecureRandom();

    public WishlistService(WishlistRepository lists, WishItemRepository items) {
        this.lists = lists; this.items = items;
    }

    @Transactional
    public WishlistDto createList(CreateWishlistRequest req) {
        var wl = new Wishlist();
        wl.setTitle(req.title());
        wl.setOwnerKey(generateOwnerKey());
        wl.setPublicToken(UUID.randomUUID().toString());
        wl = lists.save(wl);
        return toDtoOwner(wl);
    }

    @Transactional
    public ItemDto addItem(Long listId, String ownerKey, CreateItemRequest req) {
        var wl = lists.findByIdAndOwnerKey(listId, ownerKey)
                .orElseThrow(() -> new IllegalArgumentException("Invalid owner key or list id"));

        var it = new WishItem();
        it.setWishlist(wl);
        it.setTitle(req.title());
        it.setNote(req.note());
        it.setUrl(req.url());
        it.setPrice(java.math.BigDecimal.valueOf(req.price()));
        it.setReserved(false);

        it = items.save(it);
        return toDto(it);
    }

    // ------- mapping helpers -------
    private WishlistDto toDtoOwner(Wishlist w) {
        return new WishlistDto(
                w.getId(),
                w.getTitle(),
                w.getOwnerKey(),
                w.getPublicToken(),
                w.getCreatedAt(),
                w.getItems().stream().map(this::toDto).toList()   // virker nu, fordi getItems() er List<WishItem>
        );
    }

    private ItemDto toDto(WishItem i) {
        return new ItemDto(
                i.getId(),
                i.getTitle(),
                i.getNote(),
                i.getUrl(),
                i.getPrice() == null ? 0.0 : i.getPrice().doubleValue(),
                i.isReserved()
        );
    }

    private String generateOwnerKey() {
        byte[] b = new byte[16];
        RNG.nextBytes(b);
        return HexFormat.of().formatHex(b);
    }
}
