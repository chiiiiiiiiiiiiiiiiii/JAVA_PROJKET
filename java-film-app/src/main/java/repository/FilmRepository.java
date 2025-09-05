package repository;

import model.Actor;
import model.Film;
import model.Genre;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmRepository {

    public List<Film> getAllFilms() throws SQLException {
        List<Film> films = new ArrayList<>();
        String sql = "{CALL get_all_films}";

        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Film film = new Film(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("year"),
                        rs.getString("description"),
                        rs.getString("image_path")
                );
                films.add(film);
            }
        }
        return films;
    }


    public Film getFilmById(int id) throws SQLException {
        String sql = "{CALL get_film_by_id(?)}";

        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Film(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getInt("year"),
                            rs.getString("description"),
                            rs.getString("image_path")
                    );
                }
            }
        }
        return null;
    }

    public void insertFilm(Film film) throws SQLException {
        String sql = "{CALL insert_film(?, ?, ?, ?)}";

        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, film.getTitle());
            stmt.setInt(2, film.getYear());
            stmt.setString(3, film.getDescription());
            stmt.setString(4, film.getImagePath());

            boolean hasResultSet = stmt.execute();

            if (hasResultSet) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        film.setId(rs.getInt("id"));
                    }
                }
            }
        }
    }

    public void updateFilm(Film film) throws SQLException {
        String sql = "{CALL update_film(?, ?, ?, ?, ?)}";

        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, film.getId());
            stmt.setString(2, film.getTitle());
            stmt.setInt(3, film.getYear());
            stmt.setString(4, film.getDescription());
            stmt.setString(5, film.getImagePath());

            stmt.executeUpdate();
        }
    }

    public void deleteFilm(int id) throws SQLException {
        String sql = "{CALL delete_film(?)}";

        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void assignActorsToFilm(int filmId, List<Integer> actorIds) throws SQLException {
        try (Connection conn = DbConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Insert new links
            for (int actorId : actorIds) {
                try (CallableStatement stmt = conn.prepareCall("{CALL insert_film_actor(?, ?)}")) {
                    stmt.setInt(1, filmId);
                    stmt.setInt(2, actorId);
                    stmt.executeUpdate();
                }
            }

            conn.commit();
        }
    }

    public List<Actor> getActorsForFilm(int filmId) throws SQLException {
        List<Actor> actors = new ArrayList<>();
        String sql = "{CALL get_actors_for_film(?)}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, filmId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    actors.add(new Actor(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name")
                    ));
                }
            }
        }
        return actors;
    }

    public void removeActorFromFilm(int filmId, int actorId) throws SQLException {
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Film_Actor WHERE film_id = ? AND actor_id = ?")) {
            stmt.setInt(1, filmId);
            stmt.setInt(2, actorId);
            stmt.executeUpdate();
        }
    }



    public int getLastInsertedFilmId() throws SQLException {
        String sql = "SELECT TOP 1 id FROM Film ORDER BY id DESC";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        throw new SQLException("No film ID found.");
    }

    public void assignGenresToFilm(int filmId, List<Integer> genreIds) throws SQLException {
        try (Connection conn = DbConnection.getConnection()) {
            conn.setAutoCommit(false);

            for (int genreId : genreIds) {
                try (CallableStatement stmt = conn.prepareCall("{CALL insert_film_genre(?, ?)}")) {
                    stmt.setInt(1, filmId);
                    stmt.setInt(2, genreId);
                    stmt.executeUpdate();
                }
            }
            conn.commit();
        }
    }

    public List<Genre> getGenresForFilm(int filmId) throws SQLException {
        List<Genre> genres = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL get_genres_for_film(?)}")) {

            stmt.setInt(1, filmId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setName(rs.getString("name"));
                genres.add(genre);
            }
        }

        return genres;
    }
    public void removeGenreFromFilm(int filmId, int genreId) throws SQLException {
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Film_Genre WHERE film_id = ? AND genre_id = ?")) {
            stmt.setInt(1, filmId);
            stmt.setInt(2, genreId);
            stmt.executeUpdate();
        }
    }
}

