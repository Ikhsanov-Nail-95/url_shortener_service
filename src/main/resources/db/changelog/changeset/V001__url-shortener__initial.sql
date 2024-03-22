CREATE SEQUENCE IF NOT EXISTS unique_number_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS url (
    hash VARCHAR(6) PRIMARY KEY,
    url  TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS hash (
    hash VARCHAR(6) PRIMARY KEY
);

CREATE INDEX url_hash_idx ON url(url);
