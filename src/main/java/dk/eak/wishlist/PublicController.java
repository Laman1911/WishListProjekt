package dk.eak.wishlist;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    private final WishlistRepository repo;
    public PublicController(WishlistRepository repo) { this.repo = repo; }

    @GetMapping("/{token}")
    public Wishlist getByToken(@PathVariable String token) {
        return repo.findByPublicToken(token).orElseThrow(() -> new NotFound("No wishlist with token"));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class NotFound extends RuntimeException {
        NotFound(String m) { super(m); }
    }
}
