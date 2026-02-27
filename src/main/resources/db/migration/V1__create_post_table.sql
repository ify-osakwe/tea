CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    category_id BIGINT,
    CONSTRAINT uq_posts_title UNIQUE (title)
);

CREATE INDEX idx_posts_category_id ON posts (category_id);
