package dk.eak.wishlist.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateWishlistRequest(
        @NotBlank String title
) {}
