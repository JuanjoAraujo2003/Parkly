-- Esquema inicial de la tabla parking_spaces (entidad ParkingSpace).
-- Flyway es el único responsable del esquema: Hibernate solo valida (ddl-auto=validate).

CREATE TABLE parking_spaces (
    id      VARCHAR(20) PRIMARY KEY,
    zone    VARCHAR(10) NOT NULL,
    status  VARCHAR(20) NOT NULL
            CHECK (status IN ('AVAILABLE', 'RESERVED', 'OCCUPIED', 'MAINTENANCE')),
    vehicle VARCHAR(10),
    since   VARCHAR(5)
);
