CREATE TABLE IF NOT EXISTS wishlist (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        owner_key VARCHAR(64) NOT NULL,
    title VARCHAR(120) NOT NULL,
    public_token CHAR(36) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS wish_item (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         wishlist_id BIGINT NOT NULL,
                                         title VARCHAR(160) NOT NULL,
    note TEXT,
    url VARCHAR(400),
    price DECIMAL(10,2) DEFAULT 0,
    reserved BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_item_list FOREIGN KEY (wishlist_id) REFERENCES wishlist(id) ON DELETE CASCADE
    );

CREATE INDEX IF NOT EXISTS idx_item_list ON wish_item(wishlist_id);
