package dk.eak.wishlist.dto;

import java.time.Instant;
import java.util.List;

public record WishlistDto<ItemDto>(
        Long id,
        String title,
        String ownerKey,     // vises kun til ejer ved oprettelse
        String publicToken,
        Instant createdAt,
        List<ItemDto> items
) {}
