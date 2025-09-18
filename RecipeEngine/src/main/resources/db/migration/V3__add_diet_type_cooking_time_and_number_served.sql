ALTER TABLE recipe
    ADD COLUMN diet_type VARCHAR(20),
    ADD COLUMN gluten_free BOOLEAN,
    ADD COLUMN prep_time_minutes INT,
    ADD COLUMN cooking_time_minutes INT,
    ADD COLUMN total_time_minutes INT,
    ADD COLUMN number_served INT;
