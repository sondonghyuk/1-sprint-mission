erDiagram
    users {
        UUID id PK
        TIMESTAMP created_at "NOT NULL"
        TIMESTAMP updated_at
        VARCHAR(50) username "UK,NOT NULL"
        VARCHAR(100) email "UK,NOT NULL"
        VARCHAR(60) password "NOT NULL"
        UUID profile_id FK "ON DELETE SET NULL"
    }

    channels {
        UUID id PK
        TIMESTAMP created_at "NOT NULL"
        TIMESTAMP updated_at
        VARCHAR(100) name
        VARCHAR(500) description
        VARCHAR(10) type "NOT NULL, ENUM(PUBLIC,PRIVATE)"
    }

    messages {
        UUID id PK
        TIMESTAMP created_at "NOT NULL"
        TIMESTAMP updated_at
        TEXT content
        UUID channel_id FK "NOT NULL, ON DELETE CASCADE"
        UUID author_id FK "ON DELETE SET NULL"
    }

    read_statuses {
        UUID id PK
        TIMESTAMP created_at "NOT NULL"
        TIMESTAMP updated_at
        UUID user_id FK "UK(user_id,channel_id), ON DELETE CASCADE"
        UUID channel_id FK "UK(user_id,channel_id), ON DELETE CASCADE"
        TIMESTAMP last_read_at "NOT NULL"
    }

    binary_contents {
        UUID id PK
        TIMESTAMP created_at "NOT NULL"
        VARCHAR filename "NOT NULL"
        BIGINT size "NOT NULL"
        VARCHAR(100) content_type "NOT NULL"
        BYTEA bytes "NOT NULL"
    }

    user_statuses {
        UUID id PK
        TIMESTAMP created_at "NOT NULL"
        TIMESTAMP updated_at
        UUID user_id FK "UK,NOT NULL, ON DELETE CASCADE"
        TIMESTAMP last_active_at "NOT NULL"
    }

    message_attachments {
        UUID message_id FK "ON DELETE CASCADE"
        UUID attachment_id FK "ON DELETE CASCADE"
    }

    users ||--|| binary_contents : "users(profile_id):binary_contents(id)"
    users ||--|| user_statuses : "users(id):user_statuses(user_id)"
    users ||--o{ read_statuses : "users(id):read_statuses(user_id)"
    users ||--o{ messages : "users(id):messages(author_id)"

    channels ||--o{ read_statuses : "channels(id):read_statuses(channel_id)"
    channels ||--o{ messages : "channels(id):messages(channel_id)"

    messages ||--o{ message_attachments : "messages(id):message_attachments(message_id)"
    binary_contents ||--|| message_attachments : "binary_contents(id):message_attachments(attachment_id)"

CREATE TABLE binary_contents
(
    id           UUID                        NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    file_name    VARCHAR(255)                NOT NULL,
    size         BIGINT                      NOT NULL,
    content_type VARCHAR(100)                NOT NULL,
    message_id   UUID,
    CONSTRAINT pk_binary_contents PRIMARY KEY (id)
);

CREATE TABLE channels
(
    id          UUID                        NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    type        VARCHAR(255)                NOT NULL,
    name        VARCHAR(100),
    description VARCHAR(500),
    CONSTRAINT pk_channels PRIMARY KEY (id)
);

CREATE TABLE messages
(
    id         UUID                        NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    content    TEXT,
    channel_id UUID                        NOT NULL,
    author_id  UUID,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);

CREATE TABLE read_statuses
(
    id           UUID                        NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id      UUID                        NOT NULL,
    channel_id   UUID                        NOT NULL,
    last_read_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_read_statuses PRIMARY KEY (id)
);

CREATE TABLE user_statuses
(
    id             UUID                        NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id        UUID                        NOT NULL,
    last_active_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_user_statuses PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         UUID                        NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    username   VARCHAR(50)                 NOT NULL,
    email      VARCHAR(100)                NOT NULL,
    password   VARCHAR(60)                 NOT NULL,
    profile_id UUID,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE read_statuses
    ADD CONSTRAINT uc_3fd6e4741dcb586be6de40913 UNIQUE (user_id, channel_id);

ALTER TABLE user_statuses
    ADD CONSTRAINT uc_user_statuses_user UNIQUE (user_id);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_profile UNIQUE (profile_id);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE binary_contents
    ADD CONSTRAINT FK_BINARY_CONTENTS_ON_MESSAGE FOREIGN KEY (message_id) REFERENCES messages (id);

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id);

ALTER TABLE messages
    ADD CONSTRAINT FK_MESSAGES_ON_CHANNEL FOREIGN KEY (channel_id) REFERENCES channels (id);

ALTER TABLE read_statuses
    ADD CONSTRAINT FK_READ_STATUSES_ON_CHANNEL FOREIGN KEY (channel_id) REFERENCES channels (id);

ALTER TABLE read_statuses
    ADD CONSTRAINT FK_READ_STATUSES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_PROFILE FOREIGN KEY (profile_id) REFERENCES binary_contents (id);

ALTER TABLE user_statuses
    ADD CONSTRAINT FK_USER_STATUSES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);