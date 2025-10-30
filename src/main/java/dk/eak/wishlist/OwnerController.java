package dk.eak.wishlist;

import dk.eak.wishlist.dto.CreateItemRequest;
import dk.eak.wishlist.dto.CreateWishlistRequest;
import dk.eak.wishlist.dto.ItemDto;
import dk.eak.wishlist.dto.WishlistDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OwnerController {

    private final WishlistService service;

    public OwnerController(WishlistService service) { this.service = service; }

    // Opret ønskeseddel
    @PostMapping("/wishlists")
    public ResponseEntity<WishlistDto> createList(@RequestBody @Valid CreateWishlistRequest req) {
        var dto = service.createList(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Tilføj ønske (kræver X-Owner-Key header)
    @PostMapping("/wishlists/{id}/items")
    public ResponseEntity<ItemDto> addItem(
            @PathVariable Long id,
            @RequestHeader("X-Owner-Key") String ownerKey,
            @RequestBody @Valid CreateItemRequest req
    ) {
        var dto = service.addItem(id, ownerKey, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
