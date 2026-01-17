CREATE TABLE provinces
(
    id   SMALLINT     NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_provinces PRIMARY KEY (id)
);

CREATE TABLE cities
(
    id          SMALLINT     NOT NULL,
    province_id SMALLINT     NOT NULL,
    name        VARCHAR(255) NOT NULL,
    CONSTRAINT pk_cities PRIMARY KEY (id)
);

ALTER TABLE cities
    ADD CONSTRAINT fk_cities_on_provinces FOREIGN KEY (province_id) REFERENCES provinces (id)
        ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE user_addresses
(
    id          UUID             NOT NULL,
    user_id     UUID             NOT NULL,
    name        VARCHAR(255)     NOT NULL,
    phone1      VARCHAR(255)     NOT NULL,
    phone2      VARCHAR(255)     NULL,
    city_id     SMALLINT         NOT NULL,
    address     TEXT             NOT NULL,
    postal_code VARCHAR(255)     NOT NULL,
    llat        DOUBLE PRECISION NULL,
    llong       DOUBLE PRECISION NULL,
    created_at  TIMESTAMP        NOT NULL,
    updated_at  TIMESTAMP        NULL,
    version     SMALLINT         NOT NULL,
    CONSTRAINT pk_user_addresses PRIMARY KEY (id)
);

ALTER TABLE user_addresses
    ADD CONSTRAINT fk_user_addresses_on_users FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE user_addresses
    ADD CONSTRAINT fk_user_addresses_on_cities FOREIGN KEY (city_id) REFERENCES cities (id)
        ON DELETE RESTRICT ON UPDATE CASCADE;
