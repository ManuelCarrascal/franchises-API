
# 🚀 Microservicio de Gestión de Franquicias

Este proyecto es un microservicio **reactivo** desarrollado en **Spring Boot 3** y **WebFlux**, estructurado siguiendo principios de arquitectura **hexagonal** y buenas prácticas de desarrollo moderno.

---

## 📁 Estructura del Proyecto

```
com.nequi.franchise
├── application              # Capa de aplicación
│   ├── dto                  # DTOs de entrada/salida
│   ├── handler              # Lógica HTTP (RouterFunctions)
│   └── mapper               # Mapeadores con MapStruct
├── domain                  # Capa de dominio
│   ├── api                  # Interfaces (puertos de entrada)
│   ├── exception            # Excepciones del dominio
│   ├── model                # Entidades del dominio
│   └── spi                  # Interfaces (puertos de salida)
├── infrastructure           # Capa de infraestructura
│   ├── input                # Entrypoints (router + OpenAPI)
│   └── out                  # Adaptadores (repositorios, mapeadores)
└── FranchiseApplication.java
```

---

## 🧠 Decisiones Técnicas

- **Arquitectura hexagonal**: separación entre negocio, entrada e infraestructura.
- **Programación reactiva**: uso de `Mono` y `Flux` para operaciones no bloqueantes.
- **Swagger/OpenAPI**: documentación integrada usando `RouterFunctions`.
- **MapStruct**: facilita el mapeo limpio entre entidades y DTOs.
- **Repositorios personalizados**: consultas SQL avanzadas con proyecciones como `ProductByBranchProjection`.

---

## 🔧 Tecnologías

- Java 17
- Spring Boot 3.x
- Spring WebFlux
- R2DBC MySQL
- MapStruct
- OpenAPI Swagger
- Mockito & JUnit 5

---

## 🧪 Ejecutar las pruebas

```bash
./gradlew test
```

### 📊 Ver reporte de cobertura

Abre en el navegador:

```bash
build/reports/jacoco/test/html/index.html
```

---

## ✨ Endpoints Disponibles

La documentación Swagger estará disponible en:

```bash
http://localhost:8080/webjars/swagger-ui/index.html
```

### 🧭 Rutas Principales

| Entidad    | Método | Ruta                        | Descripción                             |
|------------|--------|-----------------------------|-----------------------------------------|
| Franquicia | POST   | `/api/franchises`           | Crear nueva franquicia                  |
| Franquicia | PUT    | `/api/franchises/{id}`      | Actualizar nombre de franquicia         |
| Sucursal   | POST   | `/api/branches`             | Crear nueva sucursal                    |
| Sucursal   | PUT    | `/api/branches/{id}`        | Actualizar nombre de sucursal           |
| Producto   | POST   | `/api/products`             | Crear nuevo producto                    |
| Producto   | DELETE | `/api/products/{id}`        | Eliminar un producto                    |
| Producto   | PUT    | `/api/products/{id}/name`   | Actualizar nombre de producto           |
| Producto   | PUT    | `/api/products/{id}/stock`  | Actualizar stock de producto            |
| Consulta   | GET    | `/api/franchises/1/top-stock-products` | Obtener producto con más stock/sucursal |

---

## ▶️ Opciones de Ejecución
### Opción 1: Usar AWS RDS (Producción)
El contenedor del backend ya viene configurado por defecto para conectarse a la base de datos en la nube (RDS):

`Dockerfile`
```
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/franchise-0.0.1-SNAPSHOT.jar app.jar

ENV DB_URL=r2dbc:mysql://franchises.c0tkayoskznw.us-east-1.rds.amazonaws.com:3306/franchises
ENV DB_USERNAME=admin
ENV DB_PASSWORD=Manuel963211007912596

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

```
Build y ejecución:
```
docker build -t franchise-app .
docker run -p 8080:8080 franchise-app
```
### OOpción 2: Probar Localmente (MySQL + backend)
Paso 1: Generar base de datos local
La carpeta `local-db/` contiene el siguiente archivo:
 - `db.sql` Script de creación de db con datos ya generados

Usa el siguiente `Dockerfile` para montar la base de datos:
```
FROM mysql:8.0
COPY db.sql /docker-entrypoint-initdb.d/
```

Build y ejecución de la base de datos:

```
docker build -t franchise-db-local ./local-db
docker run --name mysql-franchise-local -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=franchises -p 3307:3306 -d franchise-db-local
```
Paso 2: Usar variables locales en el backend
Puedes editar el Dockerfile del backend o pasar los valores al momento de ejecutar:
```
docker build -t franchise-app .
docker run -p 8080:8080 \
-e DB_URL=r2dbc:mysql://host.docker.internal:3307/franchises \
-e DB_USERNAME=root \
-e DB_PASSWORD=root \
franchise-app
```


### Alternativa: Usar Podman
Si estás usando Podman Desktop, puedes construir las imágenes y correr los contenedores de la misma forma.

1. Desde la interfaz gráfica, ve a Images → Build Image.
2. Selecciona el Dockerfile y asigna nombre.
3. Ve a Containers → Create Container y selecciona los puertos y variables necesarias.
4. Corre y observa los logs en vivo desde Podman.



### 🔐 Variables de Entorno del Backend

Estas variables se pueden definir al ejecutar el contenedor:

| Variable       | Descripción                             | Valor por defecto (local)              |
|----------------|-----------------------------------------|----------------------------------------|
| `DB_URL`       | URL R2DBC de conexión a MySQL           | `r2dbc:mysql://localhost:3307/franchises` |
| `DB_USERNAME`  | Usuario de la base de datos             | `root`                                 |
| `DB_PASSWORD`  | Contraseña del usuario                  | `root`                                 |

Ejemplo de ejecución:

```bash
docker run -p 8080:8080 \
-e DB_URL=r2dbc:mysql://host.docker.internal:3307/franchises \
-e DB_USERNAME=root \
-e DB_PASSWORD=root \
franchise-app
```

## 🧑‍💻 Autor

**Manuel Alejandro Carrascal Arias**  
Prueba técnica para microservicio de franquicias 🚀
