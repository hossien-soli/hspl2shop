CREATE DOMAIN SHORT_STRING AS VARCHAR(255);
CREATE DOMAIN LONG_STRING AS TEXT;

CREATE TYPE USER_ROLE AS ENUM ('CUSTOMER', 'MANAGER', 'OWNER');

CREATE TABLE users
(
    id            UUID         NOT NULL,
    name          SHORT_STRING NOT NULL,
    phone         SHORT_STRING NOT NULL,
    password      SHORT_STRING NOT NULL,
    email         SHORT_STRING NULL,
    role          USER_ROLE    NOT NULL,
    banned        BOOLEAN      NOT NULL,
    created_at    TIMESTAMP    NOT NULL,
    updated_at    TIMESTAMP    NULL,
    token_refresh TIMESTAMP    NULL,
    version       SMALLINT     NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uniq_idx_users_phone ON users (phone);
CREATE UNIQUE INDEX uniq_idx_users_email ON users (email);

CREATE TYPE VERIFICATION_PURPOSE AS ENUM ('REGISTRATION', 'PASSWORD_RESET', 'PHONE_NUMBER_CHANGE');

CREATE TABLE verification_sessions
(
    id                UUID                 NOT NULL,
    phone             SHORT_STRING         NOT NULL,
    code              SHORT_STRING         NOT NULL,
    request_client_id SHORT_STRING         NOT NULL,
    purpose           VERIFICATION_PURPOSE NOT NULL,
    verified          BOOLEAN              NOT NULL,
    created_at        TIMESTAMP            NOT NULL,
    version           SMALLINT             NULL,
    CONSTRAINT pk_verification_sessions PRIMARY KEY (id)
);

-- instead of indexes for fast checks create a scheduled job for removing unused obsolete session records...
-- ...and rely of full table scans
-- create index idx_verification_sessions_phone on verification_sessions(phone,created_at desc);
-- create index idx_verification_sessions_request_id on verification_sessions(request_id,created_at desc);

CREATE TYPE Login_SESSION_STATE AS ENUM ('ACTIVE', 'LOGGED_OUT', 'EXPIRED', 'INVALIDATED');

CREATE TABLE login_sessions
(
    id                UUID                NOT NULL,
    user_id           UUID                NOT NULL,
    refresh_count     INT                 NOT NULL,
    state             Login_SESSION_STATE NOT NULL,
    request_client_id SHORT_STRING        NOT NULL,
    created_at        TIMESTAMP           NOT NULL,
    state_updated_at  TIMESTAMP           NULL,
    version           INT                 NULL,
    CONSTRAINT pk_login_sessions PRIMARY KEY (id)
);

ALTER TABLE login_sessions
    ADD CONSTRAINT fk_login_sessions_on_users FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE refresh_tokens
(
    id           UUID         NOT NULL,
    session_id   UUID         NOT NULL,
    token        SHORT_STRING NOT NULL,
    lifetime     SMALLINT     NOT NULL,
    refreshed    BOOLEAN      NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    refreshed_at TIMESTAMP    NULL,
    version      SMALLINT     NULL,
    CONSTRAINT pk_refresh_tokens PRIMARY KEY (id)
);

ALTER TABLE refresh_tokens
    ADD CONSTRAINT fk_refresh_tokens_on_login_sessions FOREIGN KEY (session_id) REFERENCES login_sessions (id)
        ON DELETE CASCADE ON UPDATE CASCADE;
