# APIBarWaiter

APIBarWaiter es un proyecto de API desarrollado en Java con la estructura siguiente:

- **fp.api.ApiBarWaiter**: Clase principal del proyecto.

- **src/main/java/fp/api/barwaiter/api**: Directorio que contiene las clases relacionadas con la API.
    - **config**: Directorio que contiene la configuración del proyecto.
    - **controller**: Directorio que contiene los controladores de la API.
    - **error**: Directorio que contiene las clases de manejo de errores.
    - **model**: Directorio que contiene los modelos de datos de la API.
    - **repository**: Directorio que contiene las interfaces de repositorio.
    - **upload**: Directorio que contiene los archivos relacionados con la carga de archivos.

- **src/main/resources**: Directorio que contiene los recursos del proyecto.
    - **application.properties**: Archivo de configuración de la aplicación.

## Configuración

La configuración del proyecto se encuentra en el directorio `src/main/java/fp/api/barwaiter/api/config`.
Asegúrate de revisar y modificar adecuadamente los archivos de configuración según tus necesidades.

## Controller

La configuración del proyecto se encuentra en el directorio `src/main/java/fp/api/barwaiter/api/controller`.
  - TapaController
  - PostreController
  - PlatoController
  - FicherosController
  - DesayunoController
  - CubataController
  - CafeController
  - BebidaController

## Error

La configuración del proyecto se encuentra en el directorio `src/main/java/fp/api/barwaiter/api/error`.
- ProductoNotFoundException
- GlobalControllerAdvice
- ApiError

## Model

La configuración del proyecto se encuentra en el directorio `src/main/java/fp/api/barwaiter/api/model`.
- Tapa
- Postre
- Plato
- Ficheros
- Desayuno
- Cubata
- Cafe
- Bebida

## Repository

La configuración del proyecto se encuentra en el directorio `src/main/java/fp/api/barwaiter/api/repository`.
- TapaRepository
- PostreRepository
- PlatoRepository
- FicherosRepository
- DesayunoRepository
- CubataRepository
- CafeRepository
- BebidaRepository


## Upload

La configuración del proyecto se encuentra en el directorio `src/main/java/fp/api/barwaiter/api/upload`.

- StorageFileNotFoundException
- StorageService
- StorageException
- FileSystemStorageService

## Version
 - **JAVA** : 17
 - **SpringBoot**: 3.0.6

## Dependencias

El proyecto APIBarWaiter utiliza las siguientes dependencias:

- `spring-boot-starter-data-jpa`: Dependencia de Spring Boot para la integración de JPA (Java Persistence API).
- `spring-boot-starter-validation`: Dependencia de Spring Boot para la validación de datos.
- `spring-boot-starter-web`: Dependencia de Spring Boot para el desarrollo de aplicaciones web.
- `mysql-connector-java` (version: 8.0.33): Dependencia para la integración de MySQL con Java.
- `lombok`: Dependencia para la generación automática de código repetitivo en Java.
- `spring-boot-starter-test`: Dependencia de Spring Boot para las pruebas unitarias y de integración.

Asegúrate de incluir estas dependencias en tu archivo `pom.xml` o `build.gradle` según corresponda.

## Contacto

- GitHub: [https://github.com/RomanCito-969](https://github.com/RomanCito-969)
- LinkedIn: [https://www.linkedin.com/in/romansolanasbarrera969/](https://www.linkedin.com/in/romansolanasbarrera969/)
