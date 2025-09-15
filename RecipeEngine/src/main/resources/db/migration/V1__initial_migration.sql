-- Recipe (metadata) table
CREATE TABLE recipe (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    instructions TEXT,
    url TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Ingredients seperated so they can be handled seperately
CREATE TABLE ingredient (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Recipe-Ingredient join table (with amounts)
CREATE TABLE recipe_ingredient (
   id BIGSERIAL PRIMARY KEY,
   recipe_id BIGINT NOT NULL REFERENCES recipe(id) ON DELETE CASCADE,
   ingredient_id BIGINT NOT NULL REFERENCES ingredient(id) ON DELETE CASCADE,

   -- Unit and amount as per recipe
   original_amount NUMERIC(10, 2),
   original_unit VARCHAR(50),

   -- Normalised to g and ml (if in cups)
   amount_in_grams NUMERIC(10, 2),
   amount_in_milliliters NUMERIC(10, 2),

   -- Original text from ingredient list
   original_text VARCHAR(255)
);
