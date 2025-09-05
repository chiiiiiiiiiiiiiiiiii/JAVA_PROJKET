-- USERS
CREATE TABLE Users (
   id INT IDENTITY PRIMARY KEY,
   username NVARCHAR(50) NOT NULL UNIQUE,
   password NVARCHAR(255) NOT NULL,
   role NVARCHAR(20) NOT NULL -- 'ADMIN' or 'USER'
);

-- FILMS
CREATE TABLE Film (
  id INT IDENTITY PRIMARY KEY,
  title NVARCHAR(100) NOT NULL,
  year INT,
  description TEXT,
  image_path NVARCHAR(255)
);

-- ACTORS
CREATE TABLE Actor (
   id INT IDENTITY PRIMARY KEY,
   first_name NVARCHAR(50),
   last_name NVARCHAR(50)
);

-- DIRECTORS
CREATE TABLE Director (
    id INT IDENTITY PRIMARY KEY,
    first_name NVARCHAR(50),
    last_name NVARCHAR(50)
);

-- GENRES
CREATE TABLE Genre (
   id INT IDENTITY PRIMARY KEY,
   name NVARCHAR(50) NOT NULL
);

-- RELATIONSHIPS (many-to-many)

CREATE TABLE Film_Actor (
    film_id INT,
    actor_id INT,
    PRIMARY KEY (film_id, actor_id),
    FOREIGN KEY (film_id) REFERENCES Film(id) ON DELETE CASCADE,
    FOREIGN KEY (actor_id) REFERENCES Actor(id) ON DELETE CASCADE
);

CREATE TABLE Film_Genre (
    film_id INT,
    genre_id INT,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES Film(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES Genre(id) ON DELETE CASCADE
);

CREATE TABLE Film_Director (
   film_id INT,
   director_id INT,
   PRIMARY KEY (film_id, director_id),
   FOREIGN KEY (film_id) REFERENCES Film(id) ON DELETE CASCADE,
   FOREIGN KEY (director_id) REFERENCES Director(id) ON DELETE CASCADE
);