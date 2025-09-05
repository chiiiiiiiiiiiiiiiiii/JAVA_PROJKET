package repository;

import model.Actor;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorRepository {

    public void insertActor(Actor actor) throws SQLException {
        String sql = "{CALL insert_actor(?, ?)}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, actor.getFirstName());
            stmt.setString(2, actor.getLastName());
            stmt.executeUpdate();
        }
    }

    public void updateActor(Actor actor) throws SQLException {
        String sql = "{CALL update_actor(?, ?, ?)}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, actor.getId());
            stmt.setString(2, actor.getFirstName());
            stmt.setString(3, actor.getLastName());
            stmt.executeUpdate();
        }
    }

    public void deleteActor(int id) throws SQLException {
        String sql = "{CALL delete_actor(?)}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Actor getActorById(int id) throws SQLException {
        String sql = "{CALL get_actor_by_id(?)}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Actor(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name")
                    );
                }
            }
        }
        return null;
    }

    public List<Actor> getAllActors() throws SQLException {
        List<Actor> list = new ArrayList<>();
        String sql = "{CALL get_all_actors}";
        try (Connection conn = DbConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Actor(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                ));
            }
        }
        return list;
    }
}
