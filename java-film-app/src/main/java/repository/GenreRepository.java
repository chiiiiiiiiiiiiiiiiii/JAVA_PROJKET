package repository;

import model.Genre;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {

    public void insertGenre(Genre genre) throws SQLException {
        String sql = "{CALL insert_genre(?)}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, genre.getName());
            stmt.executeUpdate();
        }
    }

    public void updateGenre(Genre genre) throws SQLException {
        String sql = "{CALL update_genre(?, ?)}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, genre.getId());
            stmt.setString(2, genre.getName());
            stmt.executeUpdate();
        }
    }

    public void deleteGenre(int id) throws SQLException {
        String sql = "{CALL delete_genre(?)}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Genre getGenreById(int id) throws SQLException {
        String sql = "{CALL get_genre_by_id(?)}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Genre(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }

    public List<Genre> getAllGenres() throws SQLException {
        List<Genre> list = new ArrayList<>();
        String sql = "{CALL get_all_genres}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Genre(rs.getInt("id"), rs.getString("name")));
            }
        }
        return list;
    }
}
