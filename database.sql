CREATE DATABASE IF NOT EXISTS employee_db;
USE employee_db;

CREATE TABLE IF NOT EXISTS employees (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  department VARCHAR(100) NOT NULL,
  designation VARCHAR(100) NOT NULL,
  salary DECIMAL(10,2) NOT NULL,
  joining_date DATE NOT NULL
);

INSERT INTO employees (name, email, department, designation, salary, joining_date)
VALUES
('Aarav Sharma', 'aarav@example.com', 'IT', 'Software Developer', 45000.00, '2024-01-15'),
('Priya Verma', 'priya@example.com', 'HR', 'HR Executive', 38000.00, '2023-08-01');
