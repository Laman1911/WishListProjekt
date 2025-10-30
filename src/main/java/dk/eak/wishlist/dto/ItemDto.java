package dk.eak.wishlist.dto;

public record ItemDto(
        Long id,
        String title,
        String note,
        String url,
        double price,
        boolean reserved
) {}
