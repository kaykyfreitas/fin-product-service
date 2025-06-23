CREATE TABLE products
(
    id          VARCHAR(32)                 NOT NULL,
    name        VARCHAR(100)                NOT NULL,
    description VARCHAR(255),
    price       DECIMAL                     NOT NULL,
    sku         VARCHAR(20)                 NOT NULL,
    active      BOOLEAN                     NOT NULL,
    category_id VARCHAR(32)                 NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_products PRIMARY KEY (id)
);