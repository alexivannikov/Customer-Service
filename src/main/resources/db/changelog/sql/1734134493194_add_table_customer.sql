--liquibase formatted sql
--changeset avivannikov:1
CREATE TABLE IF NOT EXISTS customer(
    id UUID PRIMARY KEY,
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    phone_number VARCHAR(32),
	rating INT
);