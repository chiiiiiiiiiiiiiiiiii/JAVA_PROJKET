-- Create initial admin (run once after creating tables)
CREATE PROCEDURE create_initial_admin
    AS
BEGIN
    IF NOT EXISTS (SELECT * FROM Users WHERE role = 'ADMIN')
BEGIN
INSERT INTO Users (username, password, role)
VALUES ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN')
    -- password for admin: admin123
END;
END;
GO

CREATE PROCEDURE reset_database
AS
BEGIN
    -- Disable foreign key constraints
    ALTER TABLE Film_Actor NOCHECK CONSTRAINT ALL;
    ALTER TABLE Film NOCHECK CONSTRAINT ALL;
    ALTER TABLE Actor NOCHECK CONSTRAINT ALL;

    -- Delete from child to parent
    DELETE FROM Film_Actor;
    DELETE FROM Film;
    DELETE FROM Actor;
    DELETE FROM Genre;
    DELETE FROM Director;

    -- Enable constraints again
    ALTER TABLE Film_Actor CHECK CONSTRAINT ALL;
    ALTER TABLE Film CHECK CONSTRAINT ALL;
    ALTER TABLE Actor CHECK CONSTRAINT ALL;
END;
GO