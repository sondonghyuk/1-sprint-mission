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
