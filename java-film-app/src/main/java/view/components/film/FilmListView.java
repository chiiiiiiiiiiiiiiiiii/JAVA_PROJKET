package view.components.film;

import controller.FilmController;
import model.Film;
import view.components.EntityListPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class FilmListView extends EntityListPanel<Film> {

    private List<Film> filmList;
    private final FilmController filmController = new FilmController();

    public FilmListView() {
        super(new String[]{"Title", "Year", "Description", "Image", "Actors", "Genre"});
        table.setRowHeight(100);

        // Set image renderer on the "Image" column (assumed index = 3)
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof ImageIcon icon) {
                    setIcon(icon);
                    setText(null); // No text
                } else {
                    setIcon(null);
                    setText("No image");
                }
            }
        });

        // Enable drag-and-drop
        table.setDropMode(DropMode.ON);
        table.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.stringFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                try {
                    String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    JTable.DropLocation dropLocation = (JTable.DropLocation) support.getDropLocation();
                    int row = dropLocation.getRow();
                    if (row == -1 || row >= filmList.size()) {
                        JOptionPane.showMessageDialog(FilmListView.this, "Invalid drop location.");
                        return false;
                    }

                    Film targetFilm = filmList.get(row);
                    String[] parts = data.split(":");
                    if (parts.length < 2) return false;

                    String type = parts[0];
                    int entityId = Integer.parseInt(parts[1]);

                    if (type.equals("actor")) {
                        new FilmController().assignActorsToFilm(targetFilm.getId(), List.of(entityId));
                        JOptionPane.showMessageDialog(FilmListView.this, "Actor assigned to film: " + targetFilm.getTitle());
                    } else if (type.equals("genre")) {
                        new FilmController().assignGenresToFilm(targetFilm.getId(), List.of(entityId));
                        JOptionPane.showMessageDialog(FilmListView.this, "Genre assigned to film: " + targetFilm.getTitle());
                    }
                    refresh();
                    return true;

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(FilmListView.this, "Failed to assign actor: " + e.getMessage());
                }

                return false;
            }
        });
        refresh();
    }

    @Override
    protected void refreshData() {
        try {
            filmList = filmController.getAllFilms();
            for (Film film : filmList) {
                tableModel.addRow(new Object[]{
                        film.getTitle(),
                        film.getYear(),
                        film.getDescription(),
                        loadImageIcon(film.getImagePath()),
                        filmController.getActorsForFilm(film.getId()),
                        filmController.getGenresForFilm(film.getId())
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load films: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() {
        new AddFilmForm(this).setVisible(true);
    }

    @Override
    protected void onEdit(Film film) {
        if (film == null) return;
        new EditFilmForm(this, film).setVisible(true);
    }

    @Override
    protected void onDelete(Film film) {
        if (film == null) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Delete film: " + film.getTitle() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                new FilmController().deleteFilm(film.getId());
                refresh();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Failed to delete film: " + e.getMessage());
            }
        }
    }

    @Override
    protected Film getSelectedEntity() {
        int selected = table.getSelectedRow();
        if (selected >= 0 && selected < filmList.size()) {
            return filmList.get(selected);
        }
        return null;
    }

    private ImageIcon loadImageIcon(String path) {
        try {
            URL resource = getClass().getClassLoader().getResource(path);
            ImageIcon icon = new ImageIcon(resource);
            Image scaled = icon.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null; // or return a default icon
        }
    }

}
