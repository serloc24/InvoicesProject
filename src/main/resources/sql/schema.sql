CREATE SCHEMA IF NOT EXISTS facturam_db;

CREATE USER facturam_app WITH PASSWORD 'facturam123';
GRANT CONNECT ON DATABASE facturam_db TO facturam_app;
GRANT USAGE ON SCHEMA facturam_db TO facturam_app;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA facturam_db TO facturam_app;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA facturam_db TO facturam_app;

CREATE TABLE IF NOT EXISTS companies(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    address VARCHAR(100) NOT NULL UNIQUE,
    iban VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS clients(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    address VARCHAR(100) NOT NULL UNIQU
);

CREATE TABLE IF NOT EXISTS invoices(
    id SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    state VARCHAR(50),
    reason VARCHAR(150) NOT NULL,
    id_client serial NOT NULL REFERENCES clients(id),
    id_companies serial NOT NULL REFERENCES companies(id),
    due_date DATE,
    tax_base DOUBLE PRECISION,
    total_amount DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS products(
    id SERIAL PRIMARY KEY,
    description VARCHAR(100),
    unit_price DOUBLE PRECISION,
    taxes DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS invoice_details(
    id_detail SERIAL PRIMARY KEY,
    id_invoice SERIAL NOT NULL REFERENCES invoices(id),
    id_product SERIAL NOT NULL REFERENCES products(id),
    amount INT NOT NULL
);