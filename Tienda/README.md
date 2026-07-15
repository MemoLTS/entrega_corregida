//Juan Pablo Jofre
//Instalacion de Lombok Annotations Support en extensions
//Puerto 8889

Tienda swagger: http://localhost:8889/swagger-ui/swagger-ui/index.html

Crear una tienda body 
{
  "nombre": "EcoMarket Santiago Centro",
  "codigoTienda": "ECO-001",
  "direccion": "Av. Libertador 1234",
  "comuna": "Santiago",
  "ciudad": "Santiago",
  "region": "Metropolitana",
  "telefono": "+56912345678",
  "estado": "ACTIVA",
  "fechaCreacion": "2026-06-28T09:00:00"
}

Crear una asignacion de personal body
{
  "idTienda": 1,
  "idEmpleado": 1,
  "nombreEmpleado": "Juan Perez",
  "cargo": "Vendedor",
  "fechaInicio": "2026-06-28T08:00:00",
  "fechaTermino": null,
  "estadoAsignacion": "ACTIVA"
}

Crear un horario para el personal body
{
  "idAsignacion": 1,
  "diaSemana": "LUNES",
  "horaInicio": "08:00:00",
  "horaTermino": "17:00:00",
  "turno": "MAÑANA",
  "activo": true
}

Crear un horario de tienda body
{
  "idTienda": 1,
  "diaSemana": "LUNES",
  "horaApertura": "09:00:00",
  "horaCierre": "20:00:00",
  "activo": true
}

Crear un producto aosciado a la tienda body
{
  "idTienda": 1,
  "idProducto": 1
}
--nombreProducto lo asigna automáticamente el servicio consultando el microservicio Catalogo.

Crear un reporte de tienda body
{
  "idTienda": 1,
  "tipoReporte": "VENTAS",
  "periodoInicio": "2026-06-01T00:00:00",
  "periodoFin": "2026-06-30T23:59:59",
  "fechaGeneracion": "2026-06-28T10:00:00"
}
