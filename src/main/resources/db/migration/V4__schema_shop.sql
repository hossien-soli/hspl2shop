-- CREATE DOMAIN HUMAN_READABLE_ID AS VARCHAR(255);

CREATE TABLE category_details
(
    id          VARCHAR(255)   NOT NULL,
    description TEXT           NOT NULL,
    desc_ref    VARCHAR(255)   NULL,
    images      VARCHAR(255)[] NULL,
    sort        SMALLINT       NULL,
    created_at  TIMESTAMP      NOT NULL,
    updated_at  TIMESTAMP      NULL,
    version     SMALLINT       NOT NULL,
    CONSTRAINT pk_category_details PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id   VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

ALTER TABLE categories
    ADD CONSTRAINT fk_categories_on_category_details FOREIGN KEY (id) REFERENCES category_details (id)
        ON DELETE CASCADE ON UPDATE CASCADE;

-- category_details is the main table and the owner of relationship with categories table
-- ALTER TABLE category_details
--     ADD CONSTRAINT fk_category_details_on_categories FOREIGN KEY (id) REFERENCES categories (id)
--         ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE products
(
    id          VARCHAR(255)   NOT NULL,
    category_id VARCHAR(255)   NOT NULL,
    name        VARCHAR(255)   NOT NULL,
    description TEXT           NOT NULL,
    desc_ref    VARCHAR(255)   NULL,
    images      VARCHAR(255)[] NULL,
    discounted  BOOLEAN        NOT NULL,
    price_index SMALLINT       NULL,
    price       INT            NULL,
    visible     BOOLEAN        NOT NULL,
    sort        SMALLINT       NULL,
    created_at  TIMESTAMP      NOT NULL,
    updated_at  TIMESTAMP      NULL,
    version     SMALLINT       NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE products
    ADD CONSTRAINT fk_products_on_categories FOREIGN KEY (category_id) REFERENCES categories (id)
        ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE product_variants
(
    product_id   VARCHAR(255) NOT NULL,
    index        SMALLINT     NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    variant_name VARCHAR(255) NOT NULL,
    stock        SMALLINT     NOT NULL,
    price        INT          NOT NULL,
    discount     SMALLINT     NULL,
    weight       INT          NOT NULL,
    visible      BOOLEAN      NOT NULL,
    ordered_at   TIMESTAMP    NULL,
    version      SMALLINT     NOT NULL,
    CONSTRAINT pk_product_variants PRIMARY KEY (product_id, index)
);

ALTER TABLE product_variants
    ADD CONSTRAINT fk_product_variants_on_products FOREIGN KEY (product_id) REFERENCES products (id)
        ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE stock_changes
(
    id            UUID         NOT NULL,
    user_id       UUID         NULL,
    product_id    VARCHAR(255) NOT NULL,
    variant_index SMALLINT     NOT NULL,
    increased     BOOLEAN      NOT NULL,
    stock         SMALLINT     NOT NULL,
    count         SMALLINT     NOT NULL,
    description   VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP    NOT NULL,
    version       SMALLINT     NOT NULL,
    CONSTRAINT pk_stock_changes PRIMARY KEY (id)
);

ALTER TABLE stock_changes
    ADD CONSTRAINT fk_stock_changes_on_users FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE stock_changes
    ADD CONSTRAINT fk_stock_changes_on_product_variants FOREIGN KEY (product_id, variant_index)
        REFERENCES product_variants (product_id, index) ON DELETE CASCADE ON UPDATE CASCADE;
