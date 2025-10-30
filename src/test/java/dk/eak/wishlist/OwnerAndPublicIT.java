package dk.eak.wishlist;

import dk.eak.wishlist.dto.CreateItemRequest;
import dk.eak.wishlist.dto.CreateWishlistRequest;
import dk.eak.wishlist.dto.WishlistDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnerAndPublicIT {

    @LocalServerPort int port;
    @Autowired TestRestTemplate http;

    private String url(String p){ return "http://localhost:" + port + p; }

    @Test
    void createList_thenAddItem_thenPublicRead() {
        // 1) create list
        var req = new CreateWishlistRequest("IT Test");
        ResponseEntity<WishlistDto> created = http.postForEntity(
                url("/api/wishlists"), req, WishlistDto.class);

        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var wl = created.getBody();
        assertThat(wl).isNotNull();
        assertThat(wl.ownerKey()).isNotBlank();
        assertThat(wl.publicToken()).isNotBlank();

        // 2) add item (with owner key)
        var itemReq = new CreateItemRequest("Kindle", "note", "https://x", 599);
        var headers = new HttpHeaders();
        headers.set("X-Owner-Key", wl.ownerKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Map> addItemResp = http.exchange(
                url("/api/wishlists/" + wl.id() + "/items"),
                HttpMethod.POST,
                new HttpEntity<>(itemReq, headers),
                Map.class
        );
        assertThat(addItemResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // 3) public read
        ResponseEntity<Map> pub = http.getForEntity(
                url("/api/public/" + wl.publicToken()), Map.class);
        assertThat(pub.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
