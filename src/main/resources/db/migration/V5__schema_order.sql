CREATE TABLE delivery_method_details
(
    id          SMALLINT  NOT NULL,
    description VARCHAR(255) NULL,
    icon        VARCHAR(255) NULL,
    sort        SMALLINT NULL,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP NULL,
    version     SMALLINT  NOT NULL,
    CONSTRAINT pk_delivery_method_details PRIMARY KEY (id)
);

CREATE TABLE delivery_methods
(
    id            SMALLINT     NOT NULL,
    name          VARCHAR(255) NOT NULL,
    price         INT          NOT NULL,
    discount      SMALLINT NULL,
    max_weight_kg SMALLINT     NOT NULL,
    active        BOOLEAN      NOT NULL,
    CONSTRAINT pk_delivery_methods PRIMARY KEY (id)
);

CREATE TABLE order_addresses
(
    id            UUID         NOT NULL,
    user_id       UUID         NOT NULL,
    name          VARCHAR(255) NOT NULL,
    phone1        VARCHAR(255) NOT NULL,
    phone2        VARCHAR(255) NULL,
--     province_id   SMALLINT         NOT NULL,
    province_name VARCHAR(255) NOT NULL,
--     city_id       SMALLINT         NOT NULL,
    city_name     VARCHAR(255) NOT NULL,
    address       TEXT         NOT NULL,
    postal_code   VARCHAR(255) NOT NULL,
    llat          DOUBLE PRECISION NULL,
    llong         DOUBLE PRECISION NULL,
    version       SMALLINT     NOT NULL,
    CONSTRAINT pk_order_addresses PRIMARY KEY (id)
);

ALTER TABLE order_addresses
    ADD CONSTRAINT fk_order_addresses_on_users FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE SET NULL ON UPDATE CASCADE;

CREATE TYPE ORDER_STATUS AS ENUM('REGISTERED', 'PAID', 'PAYMENT_VERIFIED', 'PAYMENT_FAILED',
    'RECEIVED_PREPARING', 'DELIVERED', 'CANCELLED', 'OUTDATED');

CREATE TABLE orders
(
    id                        UUID         NOT NULL,
    user_id                   UUID NULL,
    payment_price             BIGINT       NOT NULL,
    total_discount            INT NULL,
    status                    ORDER_STATUS NOT NULL,
    delivery_method_id        SMALLINT     NOT NULL,
    delivery_method_name      VARCHAR(255) NOT NULL,
    delivery_base_price       INT          NOT NULL,
    delivery_discount_percent SMALLINT NULL,
    address_id                UUID         NOT NULL,
    created_at                TIMESTAMP    NOT NULL,
    paid_at                   TIMESTAMP NULL,
    verified_at               TIMESTAMP NULL,
    failed_at                 TIMESTAMP NULL,
    received_at               TIMESTAMP NULL,
    delivered_at              TIMESTAMP NULL,
    cancelled_at              TIMESTAMP NULL,
    outdated_at               TIMESTAMP NULL,
    version                   SMALLINT     NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_on_users FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_on_order_addresses FOREIGN KEY (address_id) REFERENCES order_addresses (id)
        ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE order_items
(
    order_id         UUID         NOT NULL,
    product_id       VARCHAR(255) NOT NULL,
    variant_index    SMALLINT     NOT NULL,
    variant_price    INT          NOT NULL,
    variant_discount SMALLINT NULL,
    count            SMALLINT     NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (order_id, product_id, variant_index)
);

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_on_orders FOREIGN KEY (order_id) REFERENCES orders (id)
        ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_on_product_variants FOREIGN KEY (product_id, variant_index)
        REFERENCES product_variants (product_id, index) ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TYPE PAYMENT_SESSION_STATE AS ENUM ('CREATED', 'PAID', 'VERIFIED', 'FAILED');

CREATE TABLE payment_sessions
(
    id           UUID                  NOT NULL,
    user_id      UUID NULL,
    order_id     UUID NULL,
    pg_authority VARCHAR(255)          NOT NULL,
    price        BIGINT                NOT NULL,
    state        PAYMENT_SESSION_STATE NOT NULL,
    pg_ref       VARCHAR(255) NULL,
    pg_card_pan  VARCHAR(255) NULL,
    pg_card_hash VARCHAR(255) NULL,
    created_at   TIMESTAMP             NOT NULL,
    paid_at      TIMESTAMP NULL,
    verified_at  TIMESTAMP NULL,
    failed_at    TIMESTAMP NULL,
    version      SMALLINT              NOT NULL,
    CONSTRAINT pk_payment_sessions PRIMARY KEY (id)
);

ALTER TABLE payment_sessions
    ADD CONSTRAINT fk_payment_sessions_on_users FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE payment_sessions
    ADD CONSTRAINT fk_payment_sessions_on_orders FOREIGN KEY (order_id) REFERENCES orders (id)
        ON DELETE SET NULL ON UPDATE CASCADE;

-- TODO: create include index(created_at) on payment_sessions.user_id
