-- Register a new user
CREATE PROCEDURE register_user
    @username NVARCHAR(50),
    @password NVARCHAR(255)
AS
BEGIN
INSERT INTO Users (username, password, role)
VALUES (@username, @password, 'USER')
END;
GO

-- Login a user and return their role
CREATE PROCEDURE login_user
    @username NVARCHAR(50),
    @password NVARCHAR(255)
AS
BEGIN
SELECT id, username, role FROM Users
WHERE username = @username AND password = @password;
END;
GO