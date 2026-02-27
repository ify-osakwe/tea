CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

ALTER TABLE posts
    ADD CONSTRAINT fk_posts_category FOREIGN KEY (category_id) REFERENCES categories (id);
