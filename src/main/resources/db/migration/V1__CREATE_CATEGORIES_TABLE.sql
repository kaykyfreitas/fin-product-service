CREATE TABLE categories
(
    id          VARCHAR(32)                 NOT NULL,
    name        VARCHAR(100)                NOT NULL,
    description VARCHAR(255),
    active      BOOLEAN                     NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);