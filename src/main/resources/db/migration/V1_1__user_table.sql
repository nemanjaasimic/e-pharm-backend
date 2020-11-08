CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE public."user" (
    id UUID NOT NULL CONSTRAINT user_pkey PRIMARY KEY DEFAULT uuid_generate_v4(),
    time_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    time_updated TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    username VARCHAR(30) NOT NULL UNIQUE,
    email VARCHAR(254) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(100) NOT NULL UNIQUE,
    accepted BOOLEAN NOT NULL DEFAULT FALSE,
    role VARCHAR(32) NOT NULL
);