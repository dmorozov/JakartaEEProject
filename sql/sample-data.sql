-- Sample Data for JakartaEE Application
-- This script populates the database with test data
-- Run after schema.sql has been executed

-- Clear existing data (optional)
TRUNCATE TABLE addresses RESTART IDENTITY CASCADE;
TRUNCATE TABLE contacts RESTART IDENTITY CASCADE;
TRUNCATE TABLE accounts RESTART IDENTITY CASCADE;

-- ============================================
-- Sample Accounts
-- ============================================

INSERT INTO accounts (name, email, created_date) VALUES
    ('Acme Corporation', 'info@acme.com', CURRENT_TIMESTAMP - INTERVAL '30 days'),
    ('Global Tech Solutions', 'contact@globaltech.com', CURRENT_TIMESTAMP - INTERVAL '25 days'),
    ('Innovative Systems Inc', 'hello@innovative.com', CURRENT_TIMESTAMP - INTERVAL '20 days'),
    ('Enterprise Dynamics LLC', 'support@enterprisedynamics.com', CURRENT_TIMESTAMP - INTERVAL '15 days'),
    ('Future Digital Group', 'info@futuredigital.com', CURRENT_TIMESTAMP - INTERVAL '10 days'),
    ('Tech Innovations Ltd', 'contact@techinnovations.com', CURRENT_TIMESTAMP - INTERVAL '5 days'),
    ('Cloud Solutions Co', 'hello@cloudsolutions.com', CURRENT_TIMESTAMP - INTERVAL '3 days'),
    ('Data Systems International', 'info@datasystems.com', CURRENT_TIMESTAMP - INTERVAL '1 day');

-- ============================================
-- Sample Contacts
-- ============================================

-- Contacts for Acme Corporation (Account 1)
INSERT INTO contacts (first_name, last_name, phone, email, account_id) VALUES
    ('John', 'Doe', '555-0101', 'john.doe@acme.com', 1),
    ('Jane', 'Smith', '555-0102', 'jane.smith@acme.com', 1),
    ('Michael', 'Johnson', '555-0103', 'michael.johnson@acme.com', 1);

-- Contacts for Global Tech Solutions (Account 2)
INSERT INTO contacts (first_name, last_name, phone, email, account_id) VALUES
    ('Bob', 'Williams', '555-0201', 'bob.williams@globaltech.com', 2),
    ('Alice', 'Brown', '555-0202', 'alice.brown@globaltech.com', 2),
    ('David', 'Jones', '555-0203', 'david.jones@globaltech.com', 2),
    ('Sarah', 'Davis', '555-0204', 'sarah.davis@globaltech.com', 2);

-- Contacts for Innovative Systems Inc (Account 3)
INSERT INTO contacts (first_name, last_name, phone, email, account_id) VALUES
    ('Charlie', 'Miller', '555-0301', 'charlie.miller@innovative.com', 3),
    ('Emma', 'Wilson', '555-0302', 'emma.wilson@innovative.com', 3);

-- Contacts for Enterprise Dynamics LLC (Account 4)
INSERT INTO contacts (first_name, last_name, phone, email, account_id) VALUES
    ('Robert', 'Martinez', '555-0401', 'robert.martinez@enterprisedynamics.com', 4),
    ('Linda', 'Anderson', '555-0402', 'linda.anderson@enterprisedynamics.com', 4),
    ('James', 'Taylor', '555-0403', 'james.taylor@enterprisedynamics.com', 4);

-- Contacts for Future Digital Group (Account 5)
INSERT INTO contacts (first_name, last_name, phone, email, account_id) VALUES
    ('Patricia', 'Thomas', '555-0501', 'patricia.thomas@futuredigital.com', 5),
    ('Christopher', 'Jackson', '555-0502', 'christopher.jackson@futuredigital.com', 5);

-- Contacts for Tech Innovations Ltd (Account 6)
INSERT INTO contacts (first_name, last_name, phone, email, account_id) VALUES
    ('Nancy', 'White', '555-0601', 'nancy.white@techinnovations.com', 6),
    ('Daniel', 'Harris', '555-0602', 'daniel.harris@techinnovations.com', 6),
    ('Karen', 'Martin', '555-0603', 'karen.martin@techinnovations.com', 6);

-- Contacts for Cloud Solutions Co (Account 7)
INSERT INTO contacts (first_name, last_name, phone, email, account_id) VALUES
    ('Paul', 'Thompson', '555-0701', 'paul.thompson@cloudsolutions.com', 7),
    ('Betty', 'Garcia', '555-0702', 'betty.garcia@cloudsolutions.com', 7);

-- Contacts for Data Systems International (Account 8)
INSERT INTO contacts (first_name, last_name, phone, email, account_id) VALUES
    ('Mark', 'Rodriguez', '555-0801', 'mark.rodriguez@datasystems.com', 8),
    ('Sandra', 'Lewis', '555-0802', 'sandra.lewis@datasystems.com', 8),
    ('Steven', 'Lee', '555-0803', 'steven.lee@datasystems.com', 8),
    ('Lisa', 'Walker', '555-0804', 'lisa.walker@datasystems.com', 8);

-- ============================================
-- Sample Addresses
-- ============================================

-- Addresses for Contact 1 (John Doe)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('123 Main Street', 'New York', 'NY', '10001', 'USA', 1),
    ('456 Broadway Ave', 'New York', 'NY', '10002', 'USA', 1);

-- Addresses for Contact 2 (Jane Smith)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('789 Fifth Avenue', 'New York', 'NY', '10022', 'USA', 2);

-- Addresses for Contact 3 (Michael Johnson)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('321 Park Place', 'New York', 'NY', '10017', 'USA', 3);

-- Addresses for Contact 4 (Bob Williams)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('654 Sunset Boulevard', 'Los Angeles', 'CA', '90028', 'USA', 4),
    ('987 Hollywood Blvd', 'Los Angeles', 'CA', '90028', 'USA', 4);

-- Addresses for Contact 5 (Alice Brown)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('147 Santa Monica Blvd', 'Los Angeles', 'CA', '90401', 'USA', 5);

-- Addresses for Contact 6 (David Jones)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('258 Wilshire Blvd', 'Los Angeles', 'CA', '90017', 'USA', 6);

-- Addresses for Contact 7 (Sarah Davis)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('369 Rodeo Drive', 'Beverly Hills', 'CA', '90210', 'USA', 7);

-- Addresses for Contact 8 (Charlie Miller)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('741 Michigan Avenue', 'Chicago', 'IL', '60611', 'USA', 8);

-- Addresses for Contact 9 (Emma Wilson)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('852 State Street', 'Chicago', 'IL', '60605', 'USA', 9);

-- Addresses for Contact 10 (Robert Martinez)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('963 Main Street', 'Houston', 'TX', '77002', 'USA', 10);

-- Addresses for Contact 11 (Linda Anderson)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('159 Allen Parkway', 'Houston', 'TX', '77019', 'USA', 11);

-- Addresses for Contact 12 (James Taylor)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('357 Westheimer Road', 'Houston', 'TX', '77027', 'USA', 12);

-- Addresses for Contact 13 (Patricia Thomas)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('246 Central Avenue', 'Phoenix', 'AZ', '85004', 'USA', 13);

-- Addresses for Contact 14 (Christopher Jackson)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('135 Washington Street', 'Phoenix', 'AZ', '85003', 'USA', 14);

-- Addresses for Contact 15 (Nancy White)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('579 Market Street', 'San Francisco', 'CA', '94102', 'USA', 15);

-- Addresses for Contact 16 (Daniel Harris)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('680 Mission Street', 'San Francisco', 'CA', '94105', 'USA', 16);

-- Addresses for Contact 17 (Karen Martin)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('791 Valencia Street', 'San Francisco', 'CA', '94110', 'USA', 17);

-- Addresses for Contact 18 (Paul Thompson)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('802 Pike Street', 'Seattle', 'WA', '98101', 'USA', 18);

-- Addresses for Contact 19 (Betty Garcia)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('913 1st Avenue', 'Seattle', 'WA', '98104', 'USA', 19);

-- Addresses for Contact 20 (Mark Rodriguez)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('124 Peachtree Street', 'Atlanta', 'GA', '30303', 'USA', 20);

-- Addresses for Contact 21 (Sandra Lewis)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('235 Spring Street', 'Atlanta', 'GA', '30303', 'USA', 21);

-- Addresses for Contact 22 (Steven Lee)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('346 Ponce de Leon Ave', 'Atlanta', 'GA', '30308', 'USA', 22);

-- Addresses for Contact 23 (Lisa Walker)
INSERT INTO addresses (street, city, state, zip_code, country, contact_id) VALUES
    ('457 Piedmont Avenue', 'Atlanta', 'GA', '30308', 'USA', 23);

-- ============================================
-- Verify Data
-- ============================================

-- Count records
DO $$
DECLARE
    account_count INTEGER;
    contact_count INTEGER;
    address_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO account_count FROM accounts;
    SELECT COUNT(*) INTO contact_count FROM contacts;
    SELECT COUNT(*) INTO address_count FROM addresses;

    RAISE NOTICE 'Data insertion completed:';
    RAISE NOTICE '  Accounts: %', account_count;
    RAISE NOTICE '  Contacts: %', contact_count;
    RAISE NOTICE '  Addresses: %', address_count;
END $$;
