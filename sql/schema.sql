-- JakartaEE Sample Application - Database Schema
-- PostgreSQL Database Schema for Sample Application
-- Run this script as the database owner or with sufficient privileges

-- Drop existing tables if they exist (be careful in production!)
DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS contacts CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;

-- Create sequence for IDs (optional - can use SERIAL instead)
-- PostgreSQL will create these automatically with SERIAL/BIGSERIAL

-- ============================================
-- Accounts Table
-- ============================================
CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_account_email CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

-- Create indexes for accounts
CREATE INDEX idx_accounts_email ON accounts(email);
CREATE INDEX idx_accounts_name ON accounts(name);

-- ============================================
-- Contacts Table
-- ============================================
CREATE TABLE contacts (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(150),
    account_id BIGINT NOT NULL,
    CONSTRAINT fk_contacts_account FOREIGN KEY (account_id)
        REFERENCES accounts(id) ON DELETE CASCADE,
    CONSTRAINT chk_contact_email CHECK (email IS NULL OR email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

-- Create indexes for contacts
CREATE INDEX idx_contacts_account_id ON contacts(account_id);
CREATE INDEX idx_contacts_email ON contacts(email);
CREATE INDEX idx_contacts_last_name ON contacts(last_name);
CREATE INDEX idx_contacts_full_name ON contacts(last_name, first_name);

-- ============================================
-- Addresses Table
-- ============================================
CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(200) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50),
    zip_code VARCHAR(20),
    country VARCHAR(100) NOT NULL,
    contact_id BIGINT NOT NULL,
    CONSTRAINT fk_addresses_contact FOREIGN KEY (contact_id)
        REFERENCES contacts(id) ON DELETE CASCADE
);

-- Create indexes for addresses
CREATE INDEX idx_addresses_contact_id ON addresses(contact_id);
CREATE INDEX idx_addresses_city ON addresses(city);
CREATE INDEX idx_addresses_state ON addresses(state);
CREATE INDEX idx_addresses_city_state ON addresses(city, state);

-- ============================================
-- Sample Data (Optional - for testing)
-- ============================================

-- Insert sample accounts
INSERT INTO accounts (name, email, created_date) VALUES
    ('Acme Corporation', 'info@acme.com', CURRENT_TIMESTAMP),
    ('Global Tech Solutions', 'contact@globaltech.com', CURRENT_TIMESTAMP),
    ('Innovative Systems Inc', 'hello@innovative.com', CURRENT_TIMESTAMP);

-- Insert sample contacts
INSERT INTO contacts (first_name, last_name, phone, email, account_id) VALUES
    ('John', 'Doe', '555-0101', 'john.doe@acme.com', 1),
    ('Jane', 'Smith', '555-0102', 'jane.smith@acme.com', 1),
    ('Bob', 'Johnson', '555-0201', 'bob.johnson@globaltech.com', 2),
    ('Alice', 'Williams', '555-0202', 'alice.williams@globaltech.com', 2),
    ('Charlie', 'Brown', '555-0301', 'charlie.brown@innovative.com', 3);

-- Insert sample addresses
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('123 Main St', 'New York', 'NY', '10001', 'USA', 1),
    ('456 Oak Ave', 'Los Angeles', 'CA', '90001', 'USA', 2),
    ('789 Pine Rd', 'Chicago', 'IL', '60601', 'USA', 3),
    ('321 Elm St', 'Houston', 'TX', '77001', 'USA', 4),
    ('654 Maple Dr', 'Phoenix', 'AZ', '85001', 'USA', 5);

-- ============================================
-- Views (Optional - for reporting)
-- ============================================

-- View for accounts with contact count
CREATE OR REPLACE VIEW account_summary AS
SELECT
    a.id,
    a.name,
    a.email,
    a.created_date,
    COUNT(c.id) as contact_count
FROM accounts a
LEFT JOIN contacts c ON a.id = c.account_id
GROUP BY a.id, a.name, a.email, a.created_date;

-- View for contacts with full details
CREATE OR REPLACE VIEW contact_details AS
SELECT
    c.id,
    c.first_name,
    c.last_name,
    c.first_name || ' ' || c.last_name as full_name,
    c.phone,
    c.email,
    c.account_id,
    a.name as account_name,
    a.email as account_email,
    COUNT(addr.id) as address_count
FROM contacts c
INNER JOIN accounts a ON c.account_id = a.id
LEFT JOIN addresses addr ON c.id = addr.contact_id
GROUP BY c.id, c.first_name, c.last_name, c.phone, c.email, c.account_id, a.name, a.email;

-- ============================================
-- Grant Permissions
-- ============================================

-- Grant necessary permissions to application user
-- Replace 'sampleuser' with your actual database user
GRANT SELECT, INSERT, UPDATE, DELETE ON accounts TO sampleuser;
GRANT SELECT, INSERT, UPDATE, DELETE ON contacts TO sampleuser;
GRANT SELECT, INSERT, UPDATE, DELETE ON addresses TO sampleuser;

-- Grant sequence permissions (for SERIAL columns)
GRANT USAGE, SELECT ON SEQUENCE accounts_id_seq TO sampleuser;
GRANT USAGE, SELECT ON SEQUENCE contacts_id_seq TO sampleuser;
GRANT USAGE, SELECT ON SEQUENCE addresses_id_seq TO sampleuser;

-- Grant view permissions
GRANT SELECT ON account_summary TO sampleuser;
GRANT SELECT ON contact_details TO sampleuser;

-- ============================================
-- Verification Queries
-- ============================================

-- Verify tables created
-- SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';

-- Verify data inserted
-- SELECT * FROM accounts;
-- SELECT * FROM contacts;
-- SELECT * FROM addresses;

-- Verify views
-- SELECT * FROM account_summary;
-- SELECT * FROM contact_details;

-- ============================================
-- Cleanup Script (Use with caution!)
-- ============================================

-- To completely remove all objects:
-- DROP VIEW IF EXISTS contact_details;
-- DROP VIEW IF EXISTS account_summary;
-- DROP TABLE IF EXISTS addresses CASCADE;
-- DROP TABLE IF EXISTS contacts CASCADE;
-- DROP TABLE IF EXISTS accounts CASCADE;
