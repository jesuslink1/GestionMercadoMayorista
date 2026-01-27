# Sistema de Gestión de Mercado Mayorista  
## Backend – API REST

Backend del **Sistema de Gestión de Mercado Mayorista**, desarrollado como parte del **Proyecto de Titulación (PPI)**.  
El sistema expone una **API REST segura, escalable y documentada**, orientada a la administración operativa, financiera y de atención de un mercado mayorista, permitiendo la interacción entre administradores, socios y clientes.

---

## 1. Objetivo del sistema

Diseñar e implementar un backend que centralice la gestión de un mercado mayorista, permitiendo:

- La administración de usuarios y roles.
- El control de stands, productos y su visibilidad pública.
- La gestión de cuotas, pagos y morosidad.
- El registro y seguimiento de incidencias operativas.
- La calificación de stands por parte de clientes.
- La emisión y validación de credenciales QR.
- La exposición de un directorio público de productos y puestos.

---

## 2. Stack tecnológico

- **Lenguaje:** Java 21  
- **Framework:** Spring Boot 3.3.x  
- **Persistencia:** Spring Data JPA  
- **Seguridad:** Spring Security + JWT  
- **Base de datos:** PostgreSQL  
- **Documentación:** Swagger / OpenAPI (springdoc-openapi)  
- **Pruebas:** JUnit 5, Mockito  

---

## 3. Arquitectura del sistema

El backend sigue una **arquitectura en capas**, favoreciendo la mantenibilidad, escalabilidad y separación de responsabilidades:

- **Controller:** Exposición de endpoints REST.
- **Service / ServiceImpl:** Implementación de la lógica de negocio.
- **Repository:** Acceso a datos mediante JPA.
- **Entity:** Mapeo de las tablas de base de datos.
- **DTO:** Objetos de transferencia para requests y responses.
- **Mapper:** Conversión entre entidades y DTOs.
- **Security / Config:** Configuración de autenticación y autorización.
- **Exception:** Manejo centralizado de errores.

**Paquete raíz:**  
`com.sise.GestionMercadoMayorista`

**Clase principal:**  
`GestionMercadoMayoristaApplication`

---

## 4. Seguridad y control de acceso

El sistema implementa **autenticación stateless basada en JWT**, garantizando el acceso seguro según el rol del usuario.

- Autenticación mediante `Bearer Token`.
- Filtro JWT que valida firma, expiración y credenciales.
- Manejo de errores estándar:
  - **401 – No autenticado**
  - **403 – Acceso denegado**

### Roles del sistema
- **ADMIN**
- **SUPERVISOR**
- **SOCIO**
- **CLIENTE**

---

## 5. Gestión de usuarios y roles

- Administración completa de usuarios por parte del administrador.
- Asignación de roles.
- Control de estados: ACTIVO, SUSPENDIDO, BAJA.
- Eliminación lógica de registros.
- Registro público de clientes.

---

## 6. Gestión de stands

- Registro y mantenimiento de stands por bloque y número.
- Asociación de stands a socios propietarios.
- Clasificación por categorías.
- Eliminación lógica para mantener trazabilidad histórica.

---

## 7. Gestión de productos

- Registro de productos por stand.
- Asociación a categorías de producto.
- Control de precios y ofertas.
- Validaciones de reglas de negocio:
  - Precio mayor o igual a cero.
  - Precio de oferta no mayor al precio regular.
- Control de visibilidad en el directorio público.
- Restricción de edición por rol (socio solo sobre sus stands).

---

## 8. Gestión de cuotas y pagos

- Generación de cuotas periódicas por stand.
- Prevención de cuotas duplicadas.
- Registro de pagos parciales o totales.
- Actualización automática del estado de la cuota.
- Reportes de morosidad e indicadores financieros.

---

## 9. Gestión de incidencias

- Registro de incidencias operativas por usuarios autenticados.
- Flujo de estados controlado:
  - ABIERTA → EN_PROCESO → RESUELTA → CERRADA
- Validación de transiciones de estado.
- Registro de fechas de cierre.
- Reportes consolidados:
  - Resumen mensual.
  - Resumen por responsable.

---

## 10. Calificaciones y reputación

- Calificación de stands con puntuación del 1 al 5.
- Registro de comentarios.
- Soporte para calificaciones de usuarios registrados y anónimos.
- Cálculo de promedios y conteo de valoraciones.

---

## 11. Credenciales QR y favoritos

- Generación de credenciales QR para socios.
- Validación de credenciales por administración.
- Gestión de stands favoritos por clientes.

---

## 12. Directorio público

- Consulta pública de stands y productos.
- Búsqueda y filtrado sin autenticación.
- API preparada para consumo por frontend web o móvil.

---

## 13. Documentación de la API

La API se encuentra documentada mediante **Swagger / OpenAPI**, permitiendo la exploración y prueba de los endpoints.

- OpenAPI JSON:  
  `http://localhost:8080/v3/api-docs`
- Swagger UI:  
  `http://localhost:8080/swagger-ui.html`

---

## 14. Base de datos

Base de datos relacional **PostgreSQL**, con eliminación lógica para preservar la integridad histórica.

Tablas principales:
- usuarios
- roles
- stands
- categorias_stands
- productos
- categorias_productos
- cuotas_pagos
- incidencias
- calificaciones
- favoritos
- credenciales_qr

---

## 15. Ejecución del proyecto

### Requisitos
- Java 21
- Maven
- PostgreSQL

### Ejecución local

```bash
mvn clean install
mvn spring-boot:run
```
---

### 16. Pruebas unitarias
El proyecto incluye pruebas unitarias enfocadas en la capa de servicios, utilizando JUnit 5 y Mockito, asegurando el correcto funcionamiento de las reglas de negocio críticas.

---

### 17. Manejo de errores
El sistema cuenta con un manejador global de excepciones que estandariza las respuestas de error, proporcionando:

Código HTTP

Mensaje descriptivo

Ruta solicitada

Fecha y hora del error

---

### 18. Autor
Jesús Ramos
Proyecto de Titulación – Sistema de Gestión de Mercado Mayorista
