# 🛒 ecommerce API & Web Manager

API REST para gestionar carritos de compra y sus productos
Web frontend para consumir la API

---

## ⚙️ Características del proyecto

- Gestión de productos (CRUD): crear, leer, actualizar y eliminar.
- Búsqueda avanzada por nombre de producto.
- Soporte para imágenes, stock, categorías y descripciones detalladas.
- Configuración CORS para integración segura con frontend.
- DTOs para una comunicación limpia entre cliente y servidor.
- Código desarrollado con Spring Boot para máxima robustez y escalabilidad.

---


## 🛠️ Stak utilizado

- Java
- Spring Boot
- Maven
- REST API
- CORS
- next.js (frontend)
- shadcn (frontend)
- mysql
- flyway
- docker
- docker compose

---

## ⚙️ Instalación y ejecución local

1. **Clonar el repositorio**

   ```bash
   git clone git@github.com:blauerwolf/t-tech-java-backend-entrega.git
   ```

2. **Construir los contenedores**
    * Requiere tener instalador Docker/Docker Desktop
   ```cd t-tech-java-backend-entrega
   docker compose build
   docker compose up -d
   
   ```
   
---

## 🔗 Enlaces importantes

| Aplicación           | URL                                                                                                        |
|----------------------|------------------------------------------------------------------------------------------------------------|
| 🌐 Frontend          | [https://localhost:3000)                                                                                   |
| 🔧 Backend API       | [https://localhost:8080)                                                                                   |
| 📄 Documentación API | [https://localhost:8080/docs)                                                                              |

---

## 📋 Endpoints disponibles

- `POST /products` – Crear un nuevo producto.
- `GET /products` – Listar todos los productos.
- `GET /products/search?queryName=` – Buscar productos por nombre.
- `GET /products/{id}` – Obtener un producto por ID.
- `PUT /products/{id}` – Actualizar un producto existente.
- `DELETE /products/{id}` – Eliminar un producto por ID.


---


## 👨‍💻 Autor

Ernesto Ardenghi  
Back-End / Java
Talento Tech
