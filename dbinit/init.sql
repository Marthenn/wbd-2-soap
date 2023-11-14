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