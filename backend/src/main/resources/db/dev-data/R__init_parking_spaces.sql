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

-- Ingreso de las plazas ocupadas: hoy, a la hora de since. Las demás quedan sin ingreso.
UPDATE parking_spaces
   SET entered_at = CASE WHEN status = 'OCCUPIED' THEN CURRENT_DATE + since::time END;

-- Historial para las tendencias del dashboard. Solo se siembra si la tabla está vacía.
-- Hace 24 h había 5 de 12 plazas en uso (hoy, con los datos de arriba, hay 6 de 12).
INSERT INTO occupancy_snapshot (recorded_at, in_use, total)
SELECT now() - INTERVAL '24 hours', 5, 12
WHERE NOT EXISTS (SELECT 1 FROM occupancy_snapshot);

-- Ingresos de ayer y de hoy para revenueTrend. Solo se siembran si no hay ingresos en esas fechas.
INSERT INTO revenue (recorded_on, amount)
SELECT d, a FROM (VALUES (CURRENT_DATE - 1, 150000.00), (CURRENT_DATE, 180000.00)) AS seed (d, a)
WHERE NOT EXISTS (SELECT 1 FROM revenue WHERE recorded_on IN (CURRENT_DATE - 1, CURRENT_DATE));
