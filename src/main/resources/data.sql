INSERT INTO departments (id, name) VALUES
(1, 'Accounting'),
(2, 'HR');

INSERT INTO users (firstname, lastname, email, password, activated, role, department_id, dtype) VALUES
('Test', 'Test', 'test1@mail.com', '$2a$10$QkwB8tfLia1o5/cFu1cFM.C7ojQaBaBkUxDtQB4KQxilMDiVcLGf.', true, 0, null, 'User'),
('Test', 'Test', 'test4@mail.com', '$2a$10$QkwB8tfLia1o5/cFu1cFM.C7ojQaBaBkUxDtQB4KQxilMDiVcLGf.', true, 3, null, 'Admin');

INSERT INTO users (firstname, lastname, email, password, activated, role, department_id, dtype) VALUES
('Test', 'Test', 'test2@mail.com', '$2a$10$QkwB8tfLia1o5/cFu1cFM.C7ojQaBaBkUxDtQB4KQxilMDiVcLGf.', true, 1, 1, 'Employee'),
('Test', 'Test', 'test5@mail.com', '$2a$10$QkwB8tfLia1o5/cFu1cFM.C7ojQaBaBkUxDtQB4KQxilMDiVcLGf.', true, 1, 2, 'Employee'),
('Test', 'Test', 'test3@mail.com', '$2a$10$QkwB8tfLia1o5/cFu1cFM.C7ojQaBaBkUxDtQB4KQxilMDiVcLGf.', true, 2, 1, 'Manager');
