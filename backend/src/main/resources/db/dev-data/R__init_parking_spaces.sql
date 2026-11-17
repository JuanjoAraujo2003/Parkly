-- Datos de prueba para los espacios del estacionamiento (solo perfil dev).
-- Flyway lo ejecuta después de las migraciones versionadas porque es una migración
-- repetible (R__): se vuelve a aplicar cada vez que cambia el contenido de este archivo.
-- Es idempotente: si el espacio ya existe, se restablecen sus valores de prueba.

INSERT INTO parking_spaces (id, zone, status, vehicle, since) VALUES
    ('A-01', 'A', 'OCCUPIED',    'JTG-482', '08:42'),
    ('A-02', 'A', 'AVAILABLE',   NULL,      NULL),
    ('A-03', 'A', 'RESERVED',    'KLM-091', '09:15'),
    ('A-04', 'A', 'OCCUPIED',    'PQR-735', '07:58'),
    ('A-05', 'A', 'AVAILABLE',   NULL,      NULL),
    ('A-06', 'A', 'MAINTENANCE', NULL,      NULL),
    ('B-01', 'B', 'OCCUPIED',    'MNO-619', '08:21'),
    ('B-02', 'B', 'AVAILABLE',   NULL,      NULL),
    ('B-03', 'B', 'OCCUPIED',    'BCA-204', '06:47'),
    ('B-04', 'B', 'RESERVED',    'DEF-580', '09:32'),
    ('B-05', 'B', 'AVAILABLE',   NULL,      NULL),
    ('B-06', 'B', 'AVAILABLE',   NULL,      NULL)
ON CONFLICT (id) DO UPDATE SET
    zone    = EXCLUDED.zone,
    status  = EXCLUDED.status,
    vehicle = EXCLUDED.vehicle,
    since   = EXCLUDED.since;
