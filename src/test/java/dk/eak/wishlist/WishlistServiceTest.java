package dk.eak.wishlist;

import dk.eak.wishlist.dto.CreateItemRequest;
import dk.eak.wishlist.dto.CreateWishlistRequest;
import dk.eak.wishlist.dto.ItemDto;
import dk.eak.wishlist.dto.WishlistDto;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    @Test
    void createList_generatesOwnerKeyAndPublicToken() {
        var lists = mock(WishlistRepository.class);
        var items = mock(WishItemRepository.class);
        var svc = new WishlistService(lists, items);

        // Gem og returnér samme entity (simulerer DB save)
        when(lists.save(any(Wishlist.class))).thenAnswer(inv -> {
            Wishlist w = inv.getArgument(0);
            var saved = new Wishlist();
            saved.setTitle(w.getTitle());
            saved.setOwnerKey(w.getOwnerKey());
            saved.setPublicToken(w.getPublicToken());
            return saved;
        });

        WishlistDto dto = svc.createList(new CreateWishlistRequest("Test"));

        assertThat(dto.title()).isEqualTo("Test");
        assertThat(dto.ownerKey()).isNotBlank();
        assertThat(dto.publicToken()).isNotBlank();
    }

    @Test
    void addItem_requiresCorrectOwnerKey() {
        var lists = mock(WishlistRepository.class);
        var items = mock(WishItemRepository.class);
        var svc = new WishlistService(lists, items);

        when(lists.findByIdAndOwnerKey(1L, "ok"))
                .thenReturn(Optional.of(new Wishlist()));
        when(items.save(any(WishItem.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        ItemDto dto = svc.addItem(1L, "ok",
                new CreateItemRequest("Book", "note", "url", 99));

        assertThat(dto.title()).isEqualTo("Book");

        assertThrows(IllegalArgumentException.class, () ->
                svc.addItem(1L, "bad",
                        new CreateItemRequest("X", null, null, 0)));
    }
}
