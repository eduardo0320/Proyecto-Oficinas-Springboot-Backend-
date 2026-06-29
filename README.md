# Sistema de Control de Acceso a Oficinas

Proyecto de demostración desarrollado como parte de un curso universitario. 
Al ser un proyecto académico, puede contener errores o áreas de mejora.

---

## Tecnologías utilizadas

- Java 21 + Spring Boot 3
- Spring Security + JWT
- MySQL
- Docker + Docker Compose
- JavaScript + HTML + Bootstrap (Frontend)

---

## Requisitos

- Docker Desktop instalado
- Git

---

## Cómo correr el proyecto

1. Clonar el repositorio:
```bash
git clone <url-del-repositorio>
```

2. Entrar a la carpeta del backend:
```bash
cd Backend
```

3. Levantar los contenedores:
```bash
docker-compose up --build
```

4. El backend estará disponible en:

http://localhost:8080

---

## Usuarios por defecto

Usuario: admin
Contraseña: changeit

Usuario: registrador
Contraseña: changeit

Usuario: visor
Contraseña: changeit

---

## Funcionalidades

- Autenticación con JWT
- Gestión de personas y oficinas (CRUD)
- Control de ingresos y salidas
- Roles de usuario (ADMIN, REGISTRADOR, VISOR)
- Paginación y búsqueda
- Exportación a PDF y Excel
- Estadísticas y gráficos
- Contenedorización con Docker

---

## Notas

Este proyecto fue desarrollado por un estudiante universitario con fines demostrativos.
No está pensado para uso en producción y puede contener errores, configuraciones inseguras o código mejorable.
