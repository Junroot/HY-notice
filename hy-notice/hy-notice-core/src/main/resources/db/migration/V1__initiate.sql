CREATE TABLE collecting_meta_data
(
    id               BIGINT NOT NULL AUTO_INCREMENT,
    collecting_count BIGINT,
    collecting_time  DATETIME(6),
    PRIMARY KEY (id)
);

CREATE TABLE post
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    board        BIGINT,
    created_at   DATETIME(6),
    title        VARCHAR(255),
    url          VARCHAR(511),
    writing_date DATE,
    PRIMARY KEY (id)
);

ALTER TABLE post ADD CONSTRAINT UK_post_url unique (url);
