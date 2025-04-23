DROP TABLE IF EXISTS _users;

CREATE SEQUENCE user_sequence START 1 INCREMENT 1;

CREATE TABLE users (
    id BIGINT PRIMARY KEY DEFAULT nextval('user_sequence'),
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_role VARCHAR(50),
    locked BOOLEAN DEFAULT FALSE,
    enabled BOOLEAN DEFAULT FALSE
);


CREATE SEQUENCE token_sequence START 1 INCREMENT 1;

CREATE TABLE token (
    id BIGINT PRIMARY KEY DEFAULT nextval('token_sequence'),
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    confirmed_at TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_token_user FOREIGN KEY (user_id) REFERENCES _users(id)
);



CREATE TABLE exercise (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    highest_weight DOUBLE PRECISION,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE program (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    name VARCHAR(255),
    description TEXT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE program_exercise (
    id BIGSERIAL PRIMARY KEY,
    program_id BIGINT REFERENCES program(id),
    exercise_id BIGINT REFERENCES exercise(id),
    sets INT,
    reps INT,
    order_index INT
);

CREATE TABLE workout_session (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    session_date TIMESTAMP
);

CREATE TABLE exercise_stats (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    reps INT NOT NULL,
    sets INT NOT NULL,
    weight DOUBLE PRECISION,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    exercise_id BIGINT REFERENCES exercise(id),
    user_id BIGINT REFERENCES users(id),
    session_id BIGINT REFERENCES workout_session(id)
);
