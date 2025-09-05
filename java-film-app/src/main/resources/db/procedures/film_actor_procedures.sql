-- Add link
CREATE PROCEDURE insert_film_actor
    @film_id INT,
    @actor_id INT
AS
BEGIN
INSERT INTO Film_Actor (film_id, actor_id) VALUES (@film_id, @actor_id)
END;
GO

-- Remove all actors from film
CREATE PROCEDURE delete_actors_for_film
    @film_id INT
AS
BEGIN
DELETE FROM Film_Actor WHERE film_id = @film_id;
END;
GO

-- Get actors for film
CREATE PROCEDURE get_actors_for_film
    @film_id INT
AS
BEGIN
SELECT a.id, a.first_name, a.last_name
FROM Actor a
         JOIN Film_Actor fa ON a.id = fa.actor_id
WHERE fa.film_id = @film_id;
END;
GO
