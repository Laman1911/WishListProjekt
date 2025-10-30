package dk.eak.wishlist;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class WishItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JsonBackReference
    private Wishlist wishlist;

    @Column(nullable = false, length = 160) private String title;
    @Column(columnDefinition = "TEXT")      private String note;
    private String url;
    @Column(precision = 10, scale = 2)      private BigDecimal price = BigDecimal.ZERO;
    private boolean reserved = false;

    public Long getId() { return id; }
    public Wishlist getWishlist() { return wishlist; }
    public void setWishlist(Wishlist wishlist) { this.wishlist = wishlist; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public boolean isReserved() { return reserved; }
    public void setReserved(boolean reserved) { this.reserved = reserved; }
}
