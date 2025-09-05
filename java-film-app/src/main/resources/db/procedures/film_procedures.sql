CREATE PROCEDURE insert_film
    @title NVARCHAR(100),
    @year INT,
    @description TEXT,
    @image_path NVARCHAR(255)
AS
BEGIN
    INSERT INTO Film (title, year, description, image_path)
    VALUES (@title, @year, @description, @image_path);
END;
SELECT SCOPE_IDENTITY() AS id;
END;
GO

CREATE PROCEDURE update_film
    @id INT,
    @title NVARCHAR(100),
    @year INT,
    @description TEXT,
    @image_path NVARCHAR(255)
AS
BEGIN
UPDATE Film
SET title = @title,
    year = @year,
    description = @description,
    image_path = @image_path
WHERE id = @id;
END;
GO

CREATE PROCEDURE delete_film
    @id INT
AS
BEGIN
DELETE FROM Film WHERE id = @id;
END;
GO

CREATE PROCEDURE get_all_films
    AS
BEGIN
SELECT * FROM Film;
END;
GO

CREATE PROCEDURE get_film_by_id
    @id INT
AS
BEGIN
SELECT * FROM Film WHERE id = @id;
END;
GO
