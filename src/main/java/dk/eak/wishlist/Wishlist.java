package dk.eak.wishlist;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Wishlist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)  private String ownerKey;
    @Column(nullable = false, length = 120) private String title;
    @Column(nullable = false, unique = true, length = 36) private String publicToken;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WishItem> items = new ArrayList<>();

    // --- getters / setters (VIGTIGT: generics på getter) ---
    public Long getId() { return id; }
    public String getOwnerKey() { return ownerKey; }
    public void setOwnerKey(String ownerKey) { this.ownerKey = ownerKey; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPublicToken() { return publicToken; }
    public void setPublicToken(String publicToken) { this.publicToken = publicToken; }
    public Instant getCreatedAt() { return createdAt; }

    public List<WishItem> getItems() { return items; }   // <-- her var fejlen
}
