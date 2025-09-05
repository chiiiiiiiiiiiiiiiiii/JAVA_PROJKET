-- Insert
CREATE PROCEDURE insert_genre
    @name NVARCHAR(100)
AS
BEGIN
INSERT INTO Genre (name) VALUES (@name);
END;
GO

-- Update
CREATE PROCEDURE update_genre
    @id INT,
    @name NVARCHAR(100)
AS
BEGIN
UPDATE Genre SET name = @name WHERE id = @id;
END;
GO

-- Delete
CREATE PROCEDURE delete_genre
    @id INT
AS
BEGIN
DELETE FROM Genre WHERE id = @id;
END;
GO

-- Get all
CREATE PROCEDURE get_all_genres
    AS
BEGIN
SELECT id, name FROM Genre;
END;
GO

-- Get by id
CREATE PROCEDURE get_genre_by_id
    @id INT
AS
BEGIN
SELECT id, name FROM Genre WHERE id = @id;
END;
GO
