package repository;

import model.User;
import util.DbConnection;

import java.sql.*;

public class UserRepository {

    public User login(String username, String password) throws SQLException {
        String sql = "{CALL login_user(?, ?)}";

        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("role")
                    );
                }
            }
        }

        return null; // login failed
    }

    public void register(String username, String password) throws SQLException {
        String sql = "{CALL register_user(?, ?)}";

        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        }
    }
}
