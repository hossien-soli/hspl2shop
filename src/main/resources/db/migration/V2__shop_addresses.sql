CREATE TABLE provinces
(
    id   SMALLINT    NOT NULL,
    name ShortString NOT NULL,
    CONSTRAINT pk_provinces PRIMARY KEY (id)
);

CREATE TABLE cities
(
    id          SMALLINT    NOT NULL,
    province_id SMALLINT    NOT NULL,
    name        ShortString NOT NULL,
    CONSTRAINT pk_cities PRIMARY KEY (id)
);

ALTER TABLE cities
    ADD CONSTRAINT fk_cities_on_province FOREIGN KEY (province_id) REFERENCES provinces (id)
        ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE user_addresses
(
    id          UUID             NOT NULL,
    user_id     UUID             NOT NULL,
    name        ShortString      NOT NULL,
    phone1      ShortString      NOT NULL,
    phone2      ShortString      NULL,
    city_id     SMALLINT         NOT NULL,
    address     LongString       NOT NULL,
    postal_code ShortString      NOT NULL,
    llat        DOUBLE PRECISION NULL,
    llong       DOUBLE PRECISION NULL,
    created_at  TIMESTAMP        NOT NULL,
    updated_at  TIMESTAMP        NULL,
    version     SMALLINT         NULL,
    CONSTRAINT pk_user_addresses PRIMARY KEY (id)
);

ALTER TABLE user_addresses
    ADD CONSTRAINT fk_user_addresses_on_city FOREIGN KEY (city_id) REFERENCES cities (id)
        ON DELETE RESTRICT ON UPDATE CASCADE;
