erDiagram
COMMON {
        UUID id PK
        TIMESTAMP createdAt "NOT NULL"
        TIMESTAMP updatedAt "NOT NULL"
    }

    USERS {
        UUID id PK "REFERENCES COMMON(id)"
        VARCHAR(50) username "UK,NOT NULL"
        VARCHAR(100) email "UK,NOT NULL"
        VARCHAR(60) password "NOT NULL"
        UUID profile_id FK "ON Delete SET NULL"
    }

    CHANNELS {
        UUID id PK "REFERENCES COMMON(id)"
        VARCHAR(10) type "NOT NULL, ENUM(public, private)"
        VARCHAR(100) name
        VARCHAR(500) description
    }

    MESSAGES {
        UUID id PK "REFERENCES COMMON(id)"
        TEXT content
        UUID channel_id FK "NOT NULL, ON Delete CASCADE"
        UUID author_id FK "ON Delete SET NULL"
        UUID[] attachmentIds
    }

    READ_STATUSES {
        UUID user_id FK "UK(user_id,channel_id), ON Delete CASCADE"
        UUID channel_id FK "UK(user_id,channel_id), ON Delete CASCADE"
        TIMESTAMP lastReadAt "NOT NULL"
    }

    BINARY_CONTENTS {
        UUID id PK
        TIMESTAMP createdAt "NOT NULL"
        VARCHAR filename "NOT NULL"
        BIGINT size "NOT NULL"
        VARCHAR(100) contentType "NOT NULL"
        BYTEA bytes "NOT NULL"
    }

    USER_STATUSES {
        UUID user_id FK "UK,NOT NULL, ON Delete CASCADE"
        TIMESTAMP lastActiveAt "NOT NULL"
    }

    MESSAGE_ATTACHMENTS {
        UUID message_id FK "NOT NULL, ON Delete CASCADE"
        UUID attachment_id FK "NOT NULL, ON Delete CASCADE"
    }

    USERS ||--o| BINARY_CONTENTS : profile_id
    USERS ||--o| USER_STATUSES : user_id
    USERS ||--o| READ_STATUSES : user_id
    USERS ||--o| MESSAGES : author_id

    CHANNELS ||--o| READ_STATUSES : channel_id
    CHANNELS ||--o| MESSAGES : channel_id

    MESSAGES ||--o| MESSAGE_ATTACHMENTS : message_id
    BINARY_CONTENTS ||--o| MESSAGE_ATTACHMENTS : attachment_id
