CREATE TABLE words (
                         id BIGSERIAL PRIMARY KEY,
                         word TEXT NOT NULL,
                         is_in_use BOOLEAN NOT NULL,
                         unique (word)
);
