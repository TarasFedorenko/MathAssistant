CREATE DATABASE equation_db;

CREATE TABLE equation (
    equation_id SERIAL PRIMARY KEY,
    equation_value TEXT NOT NULL
);
CREATE TABLE roots(
    roots_id SERIAL PRIMARY KEY,
    eq_id INT REFERENCES equation(equation_id) NOT NULL,
    roots_value TEXT NOT NULL
);
