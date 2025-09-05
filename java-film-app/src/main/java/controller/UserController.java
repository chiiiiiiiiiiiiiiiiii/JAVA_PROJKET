package controller;

import model.User;
import repository.UserRepository;
import util.PasswordUtils;

import java.sql.SQLException;

public class UserController {
    private final UserRepository repo = new UserRepository();

    public User login(String username, String password) throws SQLException {
        String hashed = PasswordUtils.hashPassword(password);
        return repo.login(username, hashed);
    }

    public void register(String username, String password) throws SQLException {
        String hashed = PasswordUtils.hashPassword(password);
        repo.register(username, hashed);
    }
}