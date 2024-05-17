-- Script para la base H2
-- Inserta datos en las tablas `company` y `flight`

INSERT INTO `company`
(`name`, `page`, `banner`)
VALUES
    ('Aerolíneas Argentinas', 'https://www.aerolineas.com.ar', 'banner1'),
    ('Flybondi', 'https://flybondi.com/ar', 'banner2'),
    ('Jet Smart', 'https://jetsmart.com/ar', 'banner3');

INSERT INTO `flight`
(`origin`, `destiny`, `departure_time`, `arriving_time`, `price`, `frequency`, `company_id`)
VALUES
    ('EZE', 'COR', '2024-02-01T20:00:00', '2024-02-01T22:00:00', 10.0, 'Diario', 1),
    ('USU', 'EZE', '2024-02-01T20:00:00', '2024-02-01T22:00:00', 20.0, 'Semanal', 1),
    ('EZE', 'JUJ', '2024-02-01T20:00:00', '2024-02-01T22:00:00', 15.0, 'Diario', 1);




