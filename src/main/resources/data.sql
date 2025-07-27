-- =================================================================
-- Database and Table Creation
-- =================================================================

-- 1. Create the database if it doesn't already exist
CREATE DATABASE IF NOT EXISTS sampleDB;

-- 2. Switch to the newly created database context
USE sampleDB;

-- 3. Create the 'departments' table based on the Department entity
-- This table stores a unique list of all company departments.
CREATE TABLE IF NOT EXISTS departments (
    department_id UUID PRIMARY KEY,
    department_name VARCHAR(255) NOT NULL UNIQUE
);

-- 4. Create the 'employees' table based on the Employee entity
-- This table holds all employee records. The 'department_name' field provides a link
-- to the departments table, as defined in the Java entity.
CREATE TABLE IF NOT EXISTS employees (
    employee_id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mobile_number VARCHAR(255) NOT NULL UNIQUE,
    employee_type VARCHAR(255) NOT NULL,
    department_name VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- =================================================================
-- Data Population
-- =================================================================
-- Note: The INSERT statements below assume the tables are empty.
-- If you run this script multiple times, you might get a duplicate key error
-- unless you clear the tables first.

-- Populating the 'departments' table 🏢
INSERT INTO departments (department_id, department_name) VALUES
('d3b8f2d5-1d5e-4b6a-8c9e-0f3f5a7e6c1a', 'Human Resources'),
('c8a2b5e3-4f1a-4e8b-9d2c-1a3b5c7d9e2f', 'Engineering'),
('b4d9c1a0-3e2b-4a5c-8f1d-6e9a2b4c8d7e', 'Sales');

-- Populating the 'employees' table 🧑‍💻
-- Assumes EmployeeType enum contains 'FULL_TIME', 'PART_TIME', 'CONTRACTOR'
INSERT INTO employees (employee_id, first_name, last_name, email, mobile_number, employee_type, department_id, created_at, updated_at) VALUES
('e1b2c3d4-5f6a-7b8c-9d0e-1a2b3c4d5e6f', 'John', 'Doe', 'john.doe@example.com', '+11234567890', 'FULL_TIME', 'c8a2b5e3-4f1a-4e8b-9d2c-1a3b5c7d9e2f', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('f9e8d7c6-5b4a-3c2b-1a9f-8e7d6c5b4a3c', 'Jane', 'Smith', 'jane.smith@example.com', '+12345678901', 'FULL_TIME', 'c8a2b5e3-4f1a-4e8b-9d2c-1a3b5c7d9e2f', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('a1b2c3d4-e5f6-a7b8-c9d0-e1f2a3b4c5d6', 'Peter', 'Jones', 'peter.jones@example.com', '+13456789012', 'PART_TIME', 'd3b8f2d5-1d5e-4b6a-8c9e-0f3f5a7e6c1a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('b2c3d4e5-f6a7-b8c9-d0e1-f2a3b4c5d6e7', 'Mary', 'Johnson', 'mary.johnson@example.com', '+14567890123', 'FULL_TIME', 'b4d9c1a0-3e2b-4a5c-8f1d-6e9a2b4c8d7e', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('c3d4e5f6-a7b8-c9d0-e1f2-a3b4c5d6e7f8', 'Sam', 'Williams', 'sam.williams@example.com', '+15678901234', 'CONTRACTOR', 'b4d9c1a0-3e2b-4a5c-8f1d-6e9a2b4c8d7e', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('d4e5f6a7-b8c9-d0e1-f2a3-b4c5d6e7f8a9', 'Alice', 'Brown', 'alice.brown@example.com', '+16789012345', 'FULL_TIME', 'c8a2b5e3-4f1a-4e8b-9d2c-1a3b5c7d9e2f', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('e5f6a7b8-c9d0-e1f2-a3b4-c5d6e7f8a9b0', 'Bob', 'White', 'bob.white@example.com', '+17890123456', 'PART_TIME', 'd3b8f2d5-1d5e-4b6a-8c9e-0f3f5a7e6c1a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('f6a7b8c9-d0e1-f2a3-b4c5-d6e7f8a9b0c1', 'Charlie', 'Green', 'charlie.green@example.com', '+18901234567', 'FULL_TIME', 'b4d9c1a0-3e2b-4a5c-8f1d-6e9a2b4c8d7e', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('g7h8i9j0-k1l2-m3n4-o5p6-q7r8s9t0u1v2', 'Diana', 'Hall', 'diana.hall@example.com', '+19012345678', 'CONTRACTOR', 'c8a2b5e3-4f1a-4e8b-9d2c-1a3b5c7d9e2f', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('h8i9j0k1-l2m3-n4o5-p6q7-r8s9t0u1v2w3', 'Eve', 'King', 'eve.king@example.com', '+10123456789', 'FULL_TIME', 'd3b8f2d5-1d5e-4b6a-8c9e-0f3f5a7e6c1a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('i9j0k1l2-m3n4-o5p6-q7r8-s9t0u1v2w3x4', 'Frank', 'Lee', 'frank.lee@example.com', '+11123456789', 'FULL_TIME', 'b4d9c1a0-3e2b-4a5c-8f1d-6e9a2b4c8d7e', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('j0k1l2m3-n4o5-p6q7-r8s9-t0u1v2w3x4y5', 'Grace', 'Moore', 'grace.moore@example.com', '+12123456789', 'PART_TIME', 'c8a2b5e3-4f1a-4e8b-9d2c-1a3b5c7d9e2f', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('k1l2m3n4-o5p6-q7r8-s9t0-u1v2w3x4y5z6', 'Henry', 'Scott', 'henry.scott@example.com', '+13123456789', 'FULL_TIME', 'd3b8f2d5-1d5e-4b6a-8c9e-0f3f5a7e6c1a', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('l2m3n4o5-p6q7-r8s9-t0u1-v2w3x4y5z6a7', 'Ivy', 'Taylor', 'ivy.taylor@example.com', '+14123456789', 'CONTRACTOR', 'b4d9c1a0-3e2b-4a5c-8f1d-6e9a2b4c8d7e', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('m3n4o5p6-q7r8-s9t0-u1v2-w3x4y5z6a7b8', 'Jack', 'Walker', 'jack.walker@example.com', '+15123456789', 'FULL_TIME', 'c8a2b5e3-4f1a-4e8b-9d2c-1a3b5c7d9e2f', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);