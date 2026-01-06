
CREATE DOMAIN HUMAN_READABLE_ID AS VARCHAR(255);

CREATE TABLE categories
(
    id HUMAN_READABLE_ID NOT NULL,
    name SHORT_STRING NOT NULL,
    version SMALLINT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE category_details
(
    id HUMAN_READABLE_ID NOT NULL,
    description LONG_STRING NOT NULL,
    desc_ref SHORT_STRING NULL,
    images SHORT_STRING[] NULL,
    sort SMALLINT NULL,
    version SMALLINT NULL,
    CONSTRAINT pk_category_details PRIMARY KEY (id)
);

ALTER TABLE category_details
    ADD CONSTRAINT fk_category_details_on_categories FOREIGN KEY (id) REFERENCES categories (id)
        ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE products
(
    id HUMAN_READABLE_ID NOT NULL,
    category_id HUMAN_READABLE_ID NOT NULL,
    name SHORT_STRING NOT NULL,
    description LONG_STRING NOT NULL,
    desc_ref SHORT_STRING NULL,
    images SHORT_STRING[] NULL,
    discounted BOOLEAN NOT NULL,
    visible BOOLEAN NOT NULL,
    sort SMALLINT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NULL,
    version SMALLINT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE products
    ADD CONSTRAINT fk_products_on_categories FOREIGN KEY (category_id) REFERENCES categories (id)
        ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE product_variants
(
    product_id HUMAN_READABLE_ID NOT NULL,
    index SMALLINT NOT NULL,
    name SHORT_STRING NOT NULL,
    stock INT NOT NULL,
    price INT NOT NULL,
    discount SMALLINT NULL,
    visible BOOLEAN NOT NULL,
    ordered_at TIMESTAMP NULL,
    version SMALLINT NULL,
    CONSTRAINT pk_product_variants PRIMARY KEY (product_id, index)
);

ALTER TABLE product_variants
    ADD CONSTRAINT fk_product_variants_on_products FOREIGN KEY (product_id) REFERENCES products (id)
        ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE stock_changes
(
    id UUID NOT NULL,
    user_id UUID NULL,
    product_id HUMAN_READABLE_ID NOT NULL,
    variant_index SMALLINT NOT NULL,
    increased BOOLEAN NOT NULL,
    stock INT NOT NULL,
    count SMALLINT NOT NULL,
    description SHORT_STRING NOT NULL,
    created_at TIMESTAMP NOT NULL,
    version SMALLINT NULL,
    CONSTRAINT pk_stock_changes PRIMARY KEY (id)
);

ALTER TABLE stock_changes
    ADD CONSTRAINT fk_stock_changes_on_users FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE stock_changes
    ADD CONSTRAINT fk_stock_changes_on_product_variants FOREIGN KEY (product_id, variant_index)
        REFERENCES product_variants (product_id, index)
        ON DELETE CASCADE ON UPDATE CASCADE;
