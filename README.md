# SV-UTP

Este proyecto utiliza Spring Boot. Se ha añadido seguridad con Spring Security y vistas con Thymeleaf.

## Inicio de la aplicación

1. Asegúrese de tener Java y Maven instalados.
2. Ejecute:
   ```bash
   ./mvnw spring-boot:run
   ```
   La aplicación se iniciará en `http://localhost:8081`.

## Acceso al login

Abra en el navegador `http://localhost:8081/login`. Ingrese las credenciales que estén almacenadas en la tabla `usuario` de la base de datos. Tras autenticarse se mostrará la página de inicio.

## Rutas configuradas

- Página de login: `/login`
- Cierre de sesión: `/logout`
