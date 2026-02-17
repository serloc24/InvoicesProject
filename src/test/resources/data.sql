
INSERT INTO companies (name, email, address, iban) VALUES
('Tech Solutions SL', 'info@techsolutions.com', 'Calle Mayor 10, Madrid', 'ES7620770024003102575766'),
('Digital Services SA', 'contact@digitalservices.com', 'Avenida Europa 25, Barcelona', 'ES9121000418450200051332');


INSERT INTO clients (name, email, address) VALUES
('Juan Pérez', 'juan.perez@email.com', 'Calle Luna 5, Valencia'),
('María López', 'maria.lopez@email.com', 'Calle Sol 22, Sevilla'),
('Carlos Gómez', 'carlos.gomez@email.com', 'Gran Vía 100, Madrid');

INSERT INTO products (description, unit_price, taxes) VALUES
('Desarrollo web corporativo', 1500.00, 21),
('Mantenimiento mensual', 300.00, 21),
('Consultoría IT', 80.00, 21),
('Licencia software anual', 500.00, 21),
('Hosting premium anual', 200.00, 21);

INSERT INTO invoices
(date, state, reason, id_client, id_companies, due_date, tax_base, total_amount)
VALUES
(NOW(), 'PAID', 'Desarrollo página web', 1, 1, NOW(), 1500.00, 1815.00),
(NOW(), 'PENDING', 'Mantenimiento y hosting', 2, 1, NOW(), 500.00, 605.00),
(NOW(), 'OVERDUE', 'Consultoría técnica', 3, 2, NOW(), 400.00, 484.00);

INSERT INTO invoice_details (id_invoice, id_product, amount) VALUES
-- Factura 1
(1, 1, 1),

-- Factura 2
(2, 2, 1),
(2, 5, 1),

-- Factura 3
(3, 3, 5);