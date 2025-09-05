CREATE PROCEDURE insert_film_genre
    @film_id INT,
    @genre_id INT
AS
BEGIN
INSERT INTO Film_Genre (film_id, genre_id)
VALUES (@film_id, @genre_id)
END;
GO

CREATE PROCEDURE delete_genres_for_film
    @film_id INT
AS
BEGIN
DELETE FROM Film_Genre WHERE film_id = @film_id
END;
GO

CREATE PROCEDURE get_genres_for_film
    @film_id INT
AS
BEGIN
SELECT g.id, g.name
FROM Genre g
         JOIN Film_Genre fg ON g.id = fg.genre_id
WHERE fg.film_id = @film_id
END;
GO
