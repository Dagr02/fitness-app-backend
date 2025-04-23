CREATE TABLE IF NOT EXISTS _users (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(250),
    last_name VARCHAR(250),
    user_name VARCHAR(250),
    age INT,
    joined_on DATE,
    date_of_birth DATE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (id)
);
