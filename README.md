# Parking Admin

Panel administrativo MVP para la gestión operativa de un parqueadero.

## Alcance actual

- Dashboard con ocupación, ingresos y vehículos activos.
- Mapa visual de plazas: disponible, reservada, ocupada y en mantenimiento.
- Consulta de actividad reciente.
- Configuración de tarifas por hora, fracción y tarifa nocturna.
- API REST en Java con datos de demostración en memoria.

## Requisitos

- Java 17 o superior
- Maven 3.9+
- Node.js 20+

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
