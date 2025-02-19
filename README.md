# 20조 - 뉴스피드 프로젝트

## 목차

### 팀 소개

#### 역할분담

### 프로젝트

#### 소개

#### 주요 요구사항

#### ERD

#### API

#### SQL

### 팀 소개

#### 역할분담

### 프로젝트

**인스파그램**

#### 소개

인스타그램, 쓰레드를 참조하여 뉴스피드를 구현해보는 프로젝트 입니다.

#### 주요 요구사항

#### ERD

```mermaid
erDiagram
    users {
        BIGINT id PK
        VARCHAR(40) email
        TEXT password
        VARCHAR(20) name
        DATETIME created_at
        DATETIME modified_at
        BOOLEAN is_deleted
    }

    users_image {
        BIGINT id PK
        BIGINT user_id FK
        TEXT image_url
        TEXT image_type
        DATETIME created_at
        DATETIME modified_at
    }

    follows {
        BIGINT id PK
        BIGINT follower FK
        BIGINT following FK
        DATETIME created_at
    }

    boards {
        BIGINT id PK
        BIGINT user_id FK
        TEXT title
        TEXT contents
        DATETIME created_at
        DATETIME modified_at
        BOOLEAN is_deleted
    }

    boards_image {
        BIGINT id PK
        BIGINT board_id FK
        TEXT image_url
        TEXT image_type
        DATETIME created_at
        DATETIME modified_at
    }

    comments {
        BIGINT id PK
        BIGINT board_id FK
        BIGINT user_id FK
        TEXT contents
        DATETIME created_at
        DATETIME modified_at
        BOOLEAN is_deleted
    }

%% 관계 정의
    users ||--o{ users_image : "1:N"
    users ||--o{ follows : "1:N"
    users ||--o{ boards : "1:N"
    users ||--o{ comments : "1:N"

    follows ||--|| users : "follower → id"
    follows ||--|| users : "following → id"

    boards ||--o{ boards_image : "1:N"
    boards ||--o{ comments : "1:N"

```

#### API

**인증**

|  기능  | HTTP Method |       URL        | Parameters | Request Body                              | Response                                           | HTTP Status |
|:----:|-------------|:----------------:|------------|-------------------------------------------|:---------------------------------------------------|-------------|
| 로그인  | **POST**    | `/api/v1/login`  | NONE       | { "email" : string, "password" : string } | { "id" : long, "email" : string, "name" : string } | `200 OK`    |
| 로그아웃 | **POST**    | `/api/v1/logout` | NONE       | NONE                                      | "로그아웃 되었습니다"                                       | `200 OK`    |

**유저**

|        기능        | HTTP Method     |          URL           | Parameters                                       | Request Body                                                                 | Response                                                                                                                                        | HTTP Status |
|:----------------:|-----------------|:----------------------:|--------------------------------------------------|------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------|-------------|
|    유저 생성(등록)     | **POST**        |    `/api/v1/users`     | NONE                                             | { "email" : string, "password" : string }                                    | { “id” : long, “email” : string, “password” : string, “name” : string, “image” : string, “createdAt” : string, “modifiedAt” : string }          | `200 OK`    |
|     전체 유저 조회     | **GET**         |    `/api/v1/users`     | NONE                                             | NONE                                                                         | [ { “id” : long, “email” : string, “name” : string, “image” : string, “follower_count” : int, “createdAt” : string, “modifiedAt” : string } … ] | `200 OK`    |
|     단건 유저 조회     | **GET**         |  `/api/v1/users/{id}`  | Path - id : long                                 | NONE                                                                         | { “id” : long, “email” : string, “name” : string, “image” : string, “followings” : List<User>, “createdAt” : string, “modifiedAt” : string }    | `200 OK`    |
| 유저 팔로우(추가 or 취소) | **PUT??POST??** | ``/api/v1/users/{id}`` | Path - id : long<br/>Session - login_user : long | NONE                                                                         | { “id” : long, “email” : string, “name” : string, “image” : string, “followings” : List<User>, “createdAt” : string, “modifiedAt” : string }    | `200 OK`    |
|      유저 수정       | **PUT**         |   `/api/v1/users/me`   | Session - login_user : long                      | { "email" : string, "password" : string, "name" : string, "image" : string } | { “id” : long, “email” : string, “name” : string, “image” : string, “createdAt” : string, “updateAt” : string }                                 | `200 OK`    |
|      유저 삭제       | **PATCH**       |   `/api/v1/users/me`   | Session - login_user : long                      | NONE                                                                         | NONE                                                                                                                                            | `200 OK`    |

**게시글**

|     기능     | HTTP Method |           URL           | Parameters                                       | Request Body                                                 | Response                                                                                                                                                                                       | HTTP Status |
|:----------:|-------------|:-----------------------:|--------------------------------------------------|--------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------|
| 게시글 생성(등록) | **POST**    |    `/api/v1/boards`     | NONE                                             | { "title" : string, "contents" : string, "images" : string } | { “id” : long, “title” : string, “contents” : string, “image” : string, “commentCount” : int, “createdAt” : string, “modifiedAt” : string }                                                    | `200 OK`    |
| 전체 게시글 조회  | **GET**     |    `/api/v1/boards`     | NONE                                             | NONE                                                         | [ { “id” : long, “title” : string, “contents” : string, “image” : string, "user_name" : string, "user_image" : string, “commentCount” : int, “createdAt” : string, “modifiedAt” : string } … ] | `200 OK`    |
|   게시글 수정   | **PUT**     |  `/api/v1/boards/{id}`  | Path - id : long<br/>Session - login_user : long | { "title" : string, "contents" : string, "images" : string } | { “id” : long, “title” : string, “contents” : string, “image” : string, “commentCount” : int, “createdAt” : string, “modifiedAt” : string }                                                    | `200 OK`    |
|   게시글 삭제   | **DELETE**  | ``/api/v1/boards/{id}`` | Path - id : long<br/>Session - login_user : long | NONE                                                         | NONE                                                                                                                                                                                           | `200 OK`    |

**댓글**

|        기능        | HTTP Method |                 URL                 | Parameters                                            | Request Body            | Response                                                                                                                                    | HTTP Status |
|:----------------:|-------------|:-----------------------------------:|-------------------------------------------------------|-------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------|-------------|
|    댓글 생성(등록)     | **POST**    | `/api/v1/boards/{boardId}/comments` | Path - boardId : long<br/>Session - login_user : long | { "contents" : string } | { “id” : long, “userId” : long, "name" : string, “boardId” : long, “contents” : string, “createdAt” : string, “modifiedAt” : string }       | `200 OK`    |
| 댓글(특정 게시물) 전체 조회 | **GET**     | `/api/v1/boards/{boardId}/comments` | Path - id : long                                      | NONE                    | [ { “id” : long, “userId” : long, "name" : string, “boardId” : long, “contents” : string, “createdAt” : string, “modifiedAt” : string } … ] | `200 OK`    |
|      댓글 수정       | **PUT**     |       `/api/v1/comments/{id}`       | Path - id : long<br/>Session - login_user : long      | { "contents" : string } | { “id” : long, “userId” : long, "name" : string, “boardId” : long, “contents” : string, “createdAt” : string, “modifiedAt” : string }       | `200 OK`    |
|      댓글 삭제       | **DELETE**  |      ``/api/v1/comments/{id}``      | Path - id : long<br/>Session - login_user : long      | NONE                    | NONE                                                                                                                                        | `200 OK`    |

#### SQL
```sql
CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(40) NOT NULL,
    password    TEXT        NOT NULL,
    name        VARCHAR(20) NOT NULL,
    created_at  DATETIME    NOT NULL,
    modified_at DATETIME    NOT NULL,
    is_deleted  BOOLEAN     NOT NULL
);

CREATE TABLE users_image
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT   NOT NULL,
    image_url   TEXT     NOT NULL,
    image_type  TEXT     NOT NULL,
    created_at  DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    CONSTRAINT fk_image_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE follows
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower   BIGINT   NOT NULL,
    following  BIGINT   NOT NULL,
    created_at DATETIME NOT NULL,
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
    is_deleted  BOOLEAN  NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE boards_image
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    board_id    BIGINT   NOT NULL,
    image_url   TEXT     NOT NULL,
    image_type  TEXT     NOT NULL,
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
    is_deleted  BOOLEAN  NOT NULL,
    CONSTRAINT fk_comments_board FOREIGN KEY (board_id) REFERENCES boards (id),
    CONSTRAINT fk_comments_user FOREIGN KEY (user_id) REFERENCES users (id)
);
```
