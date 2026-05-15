# IdeaForge Platform Backend

Backend de **IdeaForge** generado siguiendo el modelo modular de Sendify: Spring Boot, Java 21, Maven, JPA, MySQL, Swagger/OpenAPI y organización por bounded contexts.

## Bounded contexts incluidos

- `iam`: registro, login, cuentas y cambio de contraseña.
- `profiles`: perfiles, habilidades e intereses.
- `ideas`: creación, gestión, estados, etapas y roles requeridos de ideas/proyectos.
- `exploration`: búsqueda, exploración y guardado de ideas favoritas.
- `collaboration`: postulaciones, aceptación/rechazo, equipos, conversaciones y mensajes.
- `moderation`: reportes y resolución de reportes.
- `notifications`: soporte para notificaciones de actividad.

## Base de datos

El proyecto usa MySQL. La base esperada es:

```sql
CREATE DATABASE ideaforge_platform;
```

También se incluye el script completo en:

```txt
database/ideaforge_platform_schema.sql
```

## Configuración

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

## Ejecutar

```bash
./mvnw spring-boot:run
```

Swagger estará disponible en:

```txt
http://localhost:8080/swagger-ui/index.html
```
