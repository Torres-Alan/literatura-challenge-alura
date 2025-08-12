<h1 align="center">📚 Literatura Challenge · Alura</h1>

<p align="center">
  Aplicación de consola desarrollada para practicar el consumo de APIs, persistencia de datos con Spring Boot y consultas personalizadas usando JPA/Hibernate.
</p>


---

## ✨ ¿Qué hace esta app?

**Literatura Challenge** permite explorar libros y autores desde la API de [Gutenberg](https://gutendex.com/), almacenarlos en una base de datos y consultar la información mediante diferentes filtros.

---

## 🚀 Funcionalidades principales

- 🔍 **Buscar libros por título** usando la API externa.
- 📥 **Guardar libros y autores en base de datos**, evitando duplicados.
- 📚 **Listar todos los libros y autores** registrados localmente.
- 🌐 **Filtrar libros por idioma** (`en`, `es`, `pt`, etc).
- 📅 **Consultar autores vivos en un año determinado**.

---

## 🛠️ Tecnologías utilizadas

| Tecnología         | Descripción                                |
|--------------------|--------------------------------------------|
| ☕ Java 17+         | Lenguaje principal                         |
| 🌱 Spring Boot     | Framework para construir la aplicación     |
| 📦 Spring Data JPA | Acceso a datos con consultas dinámicas     |
| 🐘 Hibernate        | Implementación de JPA                      |
| 🛢️ MySQL / H2      | Persistencia (según configuración)         |
| ⚙️ Maven            | Gestión de dependencias                    |

---

## 🧪 Cómo ejecutar el proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Torres-Alan/literatura-challenge-alura.git
  2. Importar el proyecto en un entorno de desarrollo compatible con Maven (por ejemplo, IntelliJ IDEA, Eclipse, VSCode).
  3. Configurar la conexión a la base de datos en el archivo application.properties o application.yml.
  4. Ejecutar el proyecto desde la clase principal (main) o utilizando Maven: ./mvnw spring-boot:run
  5. Utiliza la interfaz de consola para navegar por las opciones del menú y realizar las operaciones disponibles.
     
---
## Autor

**Alan Torres Fuentes**  
Desarrollado como parte del Challenge de Alura Latam  
Repositorio: [https://github.com/Torres-Alan/literatura-challenge-alura](https://github.com/Torres-Alan/literatura-challenge-alura)





