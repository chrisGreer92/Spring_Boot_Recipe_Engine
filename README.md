# recipe_engine
Backend application for managing recipes and ingredients, built with Spring Boot and PostgreSQL using a database-first approach with Flyway migrations. Recipes to be stored in a structured format with normalised ingredients, linked through a many-to-many relationship. Each recipe stores ingredient amounts and their converted values, making it possible to switch between units like cups, grams, and milliliters.

The system exposes RESTful APIs for adding and retrieving recipes, while handling duplicate ingredient detection and unit conversion automatically. Future extensions include using external parsing tools (e.g. Gumloop) to extract recipe data from the web into structured JSON, and a lightweight frontend recipe book to browse and filter stored recipes.
