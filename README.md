## Repositorios de PrivData

## authService: [Link al repo](https://github.com/WVinet/authservice-PrivData.git)
## frontend-PrivDataReact: [Link al repo](https://github.com/miloQ1/frontend-Privdata.git)

---

# complianceService - PrivData

Este repositorio contiene el microservicio **complianceService** del ecosistema PrivData. Está desarrollado en **Spring Boot** con **Java 17** y tiene como objetivo principal gestionar el cumplimiento normativo (compliance) relacionado con la privacidad de los datos.

## Tecnologías Principales
- **Java 17**
- **Spring Boot** (WebMVC, Data JPA, Validation)
- **PostgreSQL** (Persistencia de datos)
- **Springdoc OpenAPI / Swagger** (Documentación interactiva de la API)

## Modelos y Dominios Principales
El servicio maneja la lógica de negocio y persistencia de las siguientes entidades:

- **DataSubject**: Gestión de los sujetos de datos (usuarios a los que pertenecen los datos).
- **ProcessingPurpose**: Definición de los propósitos para los cuales se procesa la información y sus bases legales.
- **PolicyVersion**: Control de las diferentes versiones de las políticas de privacidad.
- **Consent & ConsentDataCategory**: Administración de los consentimientos otorgados, actualizados o revocados por los sujetos de datos.
- **ConsentEvent**: Registro de auditoría y trazabilidad inmutable sobre cualquier acción en los consentimientos.

## Estructura del Proyecto
La aplicación sigue una arquitectura clásica de múltiples capas:
- `controller/`: Controladores REST para exponer la API (`ConsentController`, `DataSubjectController`, etc.).
- `service/`: Lógica de negocio y orquestación de operaciones.
- `repository/`: Interfaces de Spring Data JPA para la comunicación con PostgreSQL.
- `model/` y `enums/`: Entidades JPA que mapean el dominio y enumeraciones para estados y tipos (`ConsentStatus`, `LegalBasis`, etc.).
- `DTO/`: Objetos de transferencia de datos separados en `request` (con validaciones) y `response`.