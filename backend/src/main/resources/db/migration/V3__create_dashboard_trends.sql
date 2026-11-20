-- Datos para las tendencias del dashboard y /api/exits.
-- entered_at: instante de ingreso, necesario para calcular la estadía al salir.
-- stay_minutes: duración de la estadía en las salidas (null en el resto de tipos).
-- occupancy_snapshot: estado de ocupación cada vez que cambia una plaza; la ocupación de una fecha pasada
-- es el último snapshot anterior o igual a esa fecha.

ALTER TABLE parking_spaces ADD COLUMN entered_at TIMESTAMP WITH TIME ZONE;

ALTER TABLE activity ADD COLUMN stay_minutes INTEGER;

CREATE TABLE occupancy_snapshot (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    recorded_at TIMESTAMP WITH TIME ZONE NOT NULL,
    in_use      INTEGER NOT NULL,
    total       INTEGER NOT NULL
);

CREATE INDEX idx_occupancy_snapshot_recorded_at ON occupancy_snapshot (recorded_at);
