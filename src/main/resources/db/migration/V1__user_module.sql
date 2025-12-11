CREATE DOMAIN ShortString AS VARCHAR(255);
CREATE DOMAIN LongString AS TEXT;

CREATE TYPE UserRole AS ENUM ('CUSTOMER', 'MANAGER', 'OWNER');

CREATE TABLE users
(
    id         UUID        NOT NULL,
    name       ShortString NOT NULL,
    phone      ShortString NOT NULL,
    password   ShortString NOT NULL,
    email      ShortString NULL,
    role       UserRole    NOT NULL,
    banned     BOOLEAN     NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP   NULL,
    version    SMALLINT    NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TYPE VerificationPurpose AS ENUM ('REGISTRATION', 'PASSWORD_RESET', 'PHONE_NUMBER_CHANGE');

CREATE TABLE verification_sessions
(
    id                UUID                NOT NULL,
    phone             ShortString         NOT NULL,
    code              ShortString         NOT NULL,
    request_client_id ShortString         NOT NULL,
    purpose           VerificationPurpose NOT NULL,
    verified          BOOLEAN             NOT NULL,
    created_at        TIMESTAMP           NOT NULL,
    version           SMALLINT            NULL,
    CONSTRAINT pk_verification_sessions PRIMARY KEY (id)
);

-- create index idx_verification_sessions_phone on verification_sessions(phone,created_at desc);
-- create index idx_verification_sessions_request_id on verification_sessions(request_id,created_at desc);