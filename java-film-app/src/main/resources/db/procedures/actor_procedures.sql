-- INSERT
CREATE PROCEDURE insert_actor
    @first_name NVARCHAR(50),
    @last_name NVARCHAR(50)
AS
BEGIN
    INSERT INTO Actor (first_name, last_name)
    VALUES (@first_name, @last_name);
END;
GO

-- UPDATE
CREATE PROCEDURE update_actor
    @id INT,
    @first_name NVARCHAR(50),
    @last_name NVARCHAR(50)
AS
BEGIN
    UPDATE Actor
    SET first_name = @first_name,
        last_name = @last_name
    WHERE id = @id;
END;
GO

-- DELETE
CREATE PROCEDURE delete_actor
@id INT
AS
BEGIN
    DELETE FROM Actor WHERE id = @id;
END;
GO

-- GET ALL
CREATE PROCEDURE get_all_actors
AS
BEGIN
    SELECT id, first_name, last_name FROM Actor;
END;
GO

-- GET BY ID
CREATE PROCEDURE get_actor_by_id
@id INT
AS
BEGIN
    SELECT id, first_name, last_name FROM Actor WHERE id = @id;
END;
GO
