package view;

import model.User;
import util.AdminUtils;
import util.DbConnection;
import util.FilmExporter;
import util.RssImporter;
import view.components.actor.ActorListView;
import view.components.film.FilmListView;
import view.components.genre.GenreListView;

import javax.swing.*;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;

public class PanelView extends JFrame {

    private FilmListView filmListView;
    private ActorListView actorListView;
    private GenreListView genreListView;
    private User currentUser;

    public PanelView(User user) {
        this.currentUser = user;
        setTitle("Film Management Panel - " + this.currentUser.getUsername() + " (" + this.currentUser.getRole() + ")");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create and set up menu bar
        JMenuBar menuBar = new JMenuBar();

        // --- File Menu ---
        JMenu fileMenu = new JMenu("File");
        JMenuItem logoutItem = new JMenuItem("Logout");
        JMenu exportMenu = new JMenu("Export");
        logoutItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to log out?",
                    "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Close the current PanelView window
                new LoginView().setVisible(true); // Open login window again
            }
        });

        JMenuItem exportCsv = new JMenuItem("Export to CSV");
        exportCsv.addActionListener(e -> {
            try {
                FilmExporter.exportToCSV();
                JOptionPane.showMessageDialog(this, "Exported to downloads/films.csv");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "CSV export failed: " + ex.getMessage());
            }
        });

        JMenuItem exportXml = new JMenuItem("Export to XML");
        exportXml.addActionListener(e -> {
            try {
                FilmExporter.exportToXML();
                JOptionPane.showMessageDialog(this, "Exported to downloads/films.xml");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "XML export failed: " + ex.getMessage());
            }
        });
        fileMenu.add(logoutItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        exportMenu.add(exportCsv);
        exportMenu.add(exportXml);
        menuBar.add(exportMenu);


        // --- Admin Menu (only visible for ADMIN users) ---
        if (currentUser.isAdmin()) {
            JMenu adminMenu = new JMenu("Admin");
            JMenuItem resetDbItem = new JMenuItem("Reset Database");
            JMenuItem importRssItem = new JMenuItem("Import from RSS");

            // Add action listeners (optional for now)
            resetDbItem.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete all data and images?",
                        "Confirm Reset",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        // Call the stored procedure
                        Connection conn = DbConnection.getConnection();
                        CallableStatement stmt = conn.prepareCall("{CALL reset_database}");
                        stmt.execute();
                        stmt.close();
                        conn.close();

                        // Delete all images from folder
                        AdminUtils.deleteAllImages("src/assets/");

                        // Refresh the film list if needed
                        filmListView.refresh();
                        actorListView.refresh();
                        genreListView.refresh();

                        JOptionPane.showMessageDialog(this, "Database and images cleared successfully.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error resetting: " + ex.getMessage());
                    }
                }
            });

            // RSS import film data
            importRssItem.addActionListener(e -> {
                String[] options = { "From Local File", "From Web URL" };
                int choice = JOptionPane.showOptionDialog(this, "Import RSS feed from:",
                        "Import RSS", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);

                int count = 0;

                if (choice == 0) {
                    // LOCAL file import (resources/rss/films.xml)
                    count = RssImporter.importFromFile("rss/films.xml");
                } else if (choice == 1) {
                    String urlStr = JOptionPane.showInputDialog(this, "Enter RSS URL:",
                            "https://www.nasa.gov/rss/dyn/breaking_news.rss");
                    if (urlStr != null && !urlStr.trim().isEmpty()) {
                        count = RssImporter.importFromUrl(urlStr.trim());
                    }
                }

                JOptionPane.showMessageDialog(this, count + " films imported.");
                filmListView.refresh();
                actorListView.refresh();
                genreListView.refresh();
            });

            adminMenu.add(resetDbItem);
            adminMenu.add(importRssItem);

            menuBar.add(adminMenu);
        }
        // Set the menu bar
        setJMenuBar(menuBar);

        // --- Main Tabbed Panel ---
        // Left panel: Film list
        filmListView = new FilmListView(); // save instance
        JScrollPane filmScrollPane = new JScrollPane(filmListView);

// Right panel: Tabbed entity views
        JTabbedPane rightTabs = new JTabbedPane();
        actorListView = new ActorListView();
        genreListView = new GenreListView();
        rightTabs.addTab("Actors", actorListView);
        rightTabs.addTab("Genres", genreListView);
// Add DirectorListView if you have it:
        rightTabs.addTab("Directors", new JPanel()); // placeholder
// Split layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filmScrollPane, rightTabs);
        splitPane.setDividerLocation(800); // adjust as needed
        add(splitPane, BorderLayout.CENTER);
    }
}
