# Recipe Engine
Self-hosted recipe management system built with Java + Spring Boot and PostgreSQL, containerised with Docker and deployed on fly.io.

Designed to let users store and browse structured recipes, with ingredients, amounts, tags, and dietary flags.

The system follows a clean architecture approach with separation of DTOs, entities, and controllers, and uses Spring Data JPA for persistence, Flyway for versioned schema migrations, and MapStruct for entity–DTO mapping.

The RESTful API currently supports creating, listing, retrieving, and deleting recipes, with planned extensions for filtering, sorting, and ingredients management. A custom Gumloop workflow integrates with the backend to parse recipes from external URLs and automatically convert ingredient units into UK/metric values.

A lightweight frontend (HTML + JavaScript) provides a simple interface for adding recipes by URL and viewing the stored collection.
