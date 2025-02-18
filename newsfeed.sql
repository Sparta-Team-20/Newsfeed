CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(40) NOT NULL,
    password    TEXT NOT NULL,
    name        VARCHAR(20) NOT NULL,
    created_at  DATETIME    NOT NULL,
    modified_at DATETIME    NOT NULL,
    is_delete  BOOLEAN     NOT NULL
);

CREATE TABLE users_image
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    image_url  TEXT   NOT NULL,
    image_type TEXT   NOT NULL,
    created_at  DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    CONSTRAINT fk_image_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE follows
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower    BIGINT   NOT NULL,
    following   BIGINT   NOT NULL,
    created_at  DATETIME NOT NULL,
    CONSTRAINT fk_follower FOREIGN KEY (follower) REFERENCES users (id),
    CONSTRAINT fk_following FOREIGN KEY (following) REFERENCES users (id)
);

CREATE TABLE boards
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT   NOT NULL,
    title       TEXT     NOT NULL,
    contents    TEXT     NOT NULL,
    created_at  DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE boards_image
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id   BIGINT NOT NULL,
    image_url  TEXT   NOT NULL,
    image_type TEXT   NOT NULL,
    created_at  DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    CONSTRAINT fk_image_board FOREIGN KEY (board_id) REFERENCES boards (id)
);

CREATE TABLE comments
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id    BIGINT   NOT NULL,
    user_id     BIGINT   NOT NULL,
    contents    TEXT     NOT NULL,
    created_at  DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    CONSTRAINT fk_comments_board FOREIGN KEY (board_id) REFERENCES boards (id),
    CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users (id)
);