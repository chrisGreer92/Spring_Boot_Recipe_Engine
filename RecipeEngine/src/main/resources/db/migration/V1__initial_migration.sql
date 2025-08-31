-- Recipe table --
CREATE TABLE recipe (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    instructions TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Ingredients table --
CREATE TABLE ingredient (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    category VARCHAR(100)
);

-- Units Table --
CREATE TABLE unit (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  abbreviation VARCHAR(10) NOT NULL
);

-- Recipe <-> Ingredients Many-To-Many linking table --
CREATE TABLE recipe_ingredient (
   id BIGSERIAL PRIMARY KEY,
   recipe_id BIGINT NOT NULL REFERENCES recipe(id) ON DELETE CASCADE,
   ingredient_id BIGINT NOT NULL REFERENCES ingredient(id) ON DELETE CASCADE,
   amount NUMERIC(10, 2) NOT NULL,
   unit_id BIGINT NOT NULL REFERENCES unit(id),
   original_text VARCHAR(255)
);

-- Conversion (factors)/self join linking table --
CREATE TABLE unit_conversion (
     id BIGSERIAL PRIMARY KEY,
     from_unit_id BIGINT NOT NULL REFERENCES unit(id),
     to_unit_id BIGINT NOT NULL REFERENCES unit(id),
     factor NUMERIC(10, 6) NOT NULL,
     UNIQUE (from_unit_id, to_unit_id)
);