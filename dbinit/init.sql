CREATE DATABASE soapwebwbd;

USE soapwebwbd;

CREATE TABLE IF NOT EXISTS account (
    uid BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(256) NOT NULL,
    email VARCHAR(256) NOT NULL UNIQUE,
    username VARCHAR(256) NOT NULL UNIQUE,
    joined_date DATE NOT NULL,
    is_admin TINYINT(1) NOT NULL,
    expired_date DATE NULL
);

CREATE TABLE IF NOT EXISTS logging (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description TEXT NULL,
    ip_address VARCHAR(15) NOT NULL,
    endpoint TEXT NOT NULL,
    request_time DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS request (
    request_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(256) NOT NULL UNIQUE,
    email VARCHAR(256) NOT NULL UNIQUE,
    date DATE NOT NULL,
    proof_directory TEXT NOT NULL,
    status VARCHAR(8) NOT NULL,
    description TEXT NULL
);

GRANT ALL ON soapwebwbd.* TO 'dev';

-- Inserting an admin account
INSERT INTO account (username, password, email, joined_date, is_admin)
VALUES ('admin_user', 'admin_password', 'admin@example.com', '2023-11-17', 1);

-- Inserting two non-admin accounts
INSERT INTO account (username, password, email, joined_date, is_admin)
VALUES ('user1', 'password1', 'user1@example.com', '2023-11-17', 0),
       ('user2', 'password2', 'user2@example.com', '2023-11-17', 0);

-- Add 5 more rows for non-admin users
INSERT INTO request (username, email, date, proof_directory, status, description)
VALUES ('user1', 'user1@example.com', '2023-01-11', '/proofs/proof11.pdf', 'approved', 'Approved request description 11');

INSERT INTO request (username, email, date, proof_directory, status, description)
VALUES ('user2', 'user2@example.com', '2023-01-12', '/proofs/proof12.pdf', 'pending', 'Pending request description 12');

INSERT INTO request (username, email, date, proof_directory, status, description)
VALUES ('user1', 'user1@example.com', '2023-01-13', '/proofs/proof13.pdf', 'rejected', 'Rejected request description 13');

INSERT INTO request (username, email, date, proof_directory, status, description)
VALUES ('user2', 'user2@example.com', '2023-01-14', '/proofs/proof14.pdf', 'approved', 'Approved request description 14');

INSERT INTO request (username, email, date, proof_directory, status, description)
VALUES ('user1', 'user1@example.com', '2023-01-15', '/proofs/proof15.pdf', 'pending', 'Pending request description 15');

