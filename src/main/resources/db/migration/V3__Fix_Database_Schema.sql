-- Drop old tables first (if redoing schema)
DROP TABLE IF EXISTS token;
DROP TABLE IF EXISTS exercise_stats;
DROP TABLE IF EXISTS workout_session;
DROP TABLE IF EXISTS program_exercise;
DROP TABLE IF EXISTS program;
DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS user_program_exercise;
DROP TABLE IF EXISTS user_program;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_sequence;
DROP SEQUENCE IF EXISTS token_sequence;

CREATE SEQUENCE user_sequence START 1 INCREMENT 1;
CREATE SEQUENCE token_sequence START 1 INCREMENT 1;


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

CREATE TABLE token (
    id BIGINT PRIMARY KEY DEFAULT nextval('token_sequence'),
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    confirmed_at TIMESTAMP,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_token_user FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE exercise (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL -- optional for custom exercises
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
    program_id BIGINT REFERENCES program(id) ON DELETE CASCADE,
    exercise_id BIGINT REFERENCES exercise(id) ON DELETE CASCADE,
    sets INT,
    reps INT,
    order_index INT,
    day_number INT
);


CREATE TABLE user_program (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    program_id BIGINT REFERENCES program(id)
);


CREATE TABLE user_program_exercise (
    id BIGSERIAL PRIMARY KEY,
    user_program_id BIGINT REFERENCES user_program(id) ON DELETE CASCADE,
    program_exercise_id BIGINT REFERENCES program_exercise(id) ON DELETE CASCADE,
    completed_sets INT,
    completed_reps INT,
    weight_used DOUBLE PRECISION,
    workout_date TIMESTAMP
);


CREATE TABLE exercise_stats (
    id BIGSERIAL PRIMARY KEY,
    exercise_id BIGINT REFERENCES exercise(id),
    user_id BIGINT REFERENCES users(id),
    date TIMESTAMP NOT NULL,
    reps INT NOT NULL,
    sets INT NOT NULL,
    weight DOUBLE PRECISION,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
