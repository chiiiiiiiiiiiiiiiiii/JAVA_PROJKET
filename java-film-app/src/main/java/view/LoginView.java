package view;
import java.sql.Connection;
import java.sql.SQLException;


import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private final UserController userController = new UserController();

    public LoginView() {
        setTitle("Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> register());
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        try {
            User user = userController.login(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Welcome, " + user.getUsername() + "!");
                new PanelView(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void register() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password are required.");
            return;
        }

        try {
            userController.register(username, password);
            JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.");
            passwordField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

   public static void main(String[] args) {
    // TESTIRAJ KONEKCIJU NA BAZU PRI POKRETANJU
    try (Connection conn = util.DbConnection.getConnection()) {
        System.out.println("✅ Uspješna konekcija s bazom!");
    } catch (SQLException e) {
        System.err.println("❌ Neuspješna konekcija s bazom!");
        e.printStackTrace();
    }

    // POKRENI GUI
    SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
}

}
