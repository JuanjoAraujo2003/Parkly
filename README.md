# Parking Admin

Panel administrativo MVP para la gestión operativa de un parqueadero.

## Alcance actual

- Dashboard con ocupación, ingresos y vehículos activos.
- Mapa visual de plazas: disponible, reservada, ocupada y en mantenimiento.
- Consulta de actividad reciente.
- Configuración de tarifas por hora, fracción y tarifa nocturna.
- API REST en Java con datos de demostración en memoria.

## Requisitos

- Java 25 o superior
- Maven 3.9+
- Node.js 20+

## Entornos (dev / prod)

El backend tiene dos perfiles de Spring. Si no se define `SPRING_PROFILES_ACTIVE`, se usa `dev`.

- `dev`: PostgreSQL local de `backend/docker-compose.yml` (credenciales por defecto `parkly`) y `ddl-auto=update`.
- `prod`: no usa `ddl-auto=update` ni credenciales por defecto. Requiere `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` y `SPRING_DATASOURCE_PASSWORD`.

Base de datos de desarrollo:

```powershell
cd backend
docker compose up -d
```

Base de datos de producción (define antes las variables `POSTGRES_DB`, `POSTGRES_USER` y `POSTGRES_PASSWORD`):

```powershell
cd backend
docker compose -f docker-compose.prod.yml up -d
```

Ejecutar la API en producción:

```powershell
$env:SPRING_PROFILES_ACTIVE="prod"
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/parkly"
$env:SPRING_DATASOURCE_USERNAME="<usuario>"
$env:SPRING_DATASOURCE_PASSWORD="<contraseña>"
cd backend
mvn spring-boot:run
```

## Ejecutar

En una terminal:

```powershell
cd backend
mvn spring-boot:run
```

En otra terminal:

```powershell
cd frontend
npm install
npm run dev
```

El panel estará disponible en `http://localhost:5173` y la API en
`http://localhost:8080/api`.
