package dk.eak.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateItemRequest(
        @NotBlank String title,
        String note,
        String url,
        @PositiveOrZero double price
) {}
