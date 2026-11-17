-- Tablas de las entidades Activity y Revenue (paquete com.parkingadmin.entity).
-- Flyway es el único responsable del esquema: Hibernate solo valida (ddl-auto=validate).

CREATE TABLE activity (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    vehicle     VARCHAR(10) NOT NULL,
    action      VARCHAR(100) NOT NULL,
    type        VARCHAR(20) NOT NULL
                CHECK (type IN ('ENTRY', 'RESERVATION', 'PAYMENT', 'EXIT')),
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE revenue (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    recorded_on DATE NOT NULL,
    amount      NUMERIC(12, 2) NOT NULL
);
