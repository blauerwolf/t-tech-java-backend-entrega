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

### Frontend: Categorías
![Imagen de panel de administración del frontend](https://github.com/blauerwolf/t-tech-java-backend-entrega/blob/main/images/frontend-categorias.png)

### Frontend: Productos
![Imagen de panel de administración del frontend](https://github.com/blauerwolf/t-tech-java-backend-entrega/blob/main/images/frontend-productos.png)

![Imagen de panel de administración del frontend](https://github.com/blauerwolf/t-tech-java-backend-entrega/blob/main/images/frontend-producto-edicion.png)

### Frontend: Carritos de compra
![Imagen de panel de administración del frontend](https://github.com/blauerwolf/t-tech-java-backend-entrega/blob/main/images/frontend-carrito.png)

### Frontend: Documentación en Swagger
![Imagen de panel de documentación de API](https://github.com/blauerwolf/t-tech-java-backend-entrega/blob/main/images/docs-swagger.png)

---

## 📋 Endpoints disponibles

![Imagen de panel de documentación de API](https://github.com/blauerwolf/blob/main/images/docs-swagger.png)

**product-controller**
- `GET /products/{id}` – Obtiene un producto que coincida con el ID.
- `PUT /products{id}` – Actualiza un producto usando las propiedades, si el ID es válido.
- `DELETE /products/{id}` – Elimina un producto del sistema. Ésto pasa si el ID es válido.
- `GET /products` –  Lista todos los productos con filtros opcionales, paginación y ordenamiento.
- `POST /products – Agrega un nuevo producto al sistema.
- `GET /products/search – Obtiene un listado de todos los productos del sistema que cumplen con el término de búsqueda.

**cart-controller**
- `PUT /carts/{id}/items/{itemId} – Actualiza un producto del carrito de compras.
- `DELETE /carts/{id}/items/{itemId} – Elimina un producto del carrito de compras.
- `GET /carts – Devuelve una lista con todos los carritos del sistema.
- `POST /carts – Crea un nuevo carrito de compras vacío con saldo total 0.0.
- `POST /carts/{id}/items – Agrega un producto al carrito de compras.
- `GET /carts/{id} – Obtiene el carrito de compras a partir de su ID.
- `DELETE /carts/{id} –  Elimina un carrito de compras si está vacío.

**category-controller**
- `GET /categorias – Lista todas las categorías en el sistema.
- `POST /categorias – Crea una nueva categoría para productos.

---


## 👨‍💻 Autor

Ernesto Ardenghi  
Back-End / Java
Talento Tech
