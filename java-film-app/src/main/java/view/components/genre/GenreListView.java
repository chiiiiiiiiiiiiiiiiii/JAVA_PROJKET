package view.components.genre;

import controller.GenreController;
import model.Genre;
import view.components.EntityListPanel;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.sql.SQLException;
import java.util.List;

public class GenreListView extends EntityListPanel<Genre> {

    private List<Genre> genreList;

    public GenreListView() {
        super(new String[]{"Name"});
        table.setDragEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refresh();
    }

    @Override
    protected void refreshData() {
        try {
            genreList = new GenreController().getAllGenres();
            for (Genre g : genreList) {
                tableModel.addRow(new Object[]{g.getName()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load genres: " + e.getMessage());
        }
    }

    @Override
    protected void onAdd() {
        new AddGenreForm(this).setVisible(true);
    }

    @Override
    protected void onEdit(Genre genre) {
        if (genre != null) new EditGenreForm(this, genre).setVisible(true);
    }

    @Override
    protected void onDelete(Genre genre) {
        if (genre != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Delete genre: " + genre.getName() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    new GenreController().deleteGenre(genre.getId());
                    refresh();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Failed to delete genre: " + e.getMessage());
                }
            }
        }
    }

    @Override
    protected Genre getSelectedEntity() {
        int selected = table.getSelectedRow();
        if (selected >= 0 && selected < genreList.size()) {
            return genreList.get(selected);
        }
        return null;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        table.setTransferHandler(new TransferHandler() {
            @Override
            protected Transferable createTransferable(JComponent c) {
                int row = table.getSelectedRow();
                if (row >= 0 && row < genreList.size()) {
                    Genre genre = genreList.get(row);
                    int genreId = genre.getId();
                    String name = genre.getName();
                    return new StringSelection("genre:" + genreId + ":" + name); // format: genre:id:name
                }
                return null;
            }

            @Override
            public int getSourceActions(JComponent c) {
                return COPY;
            }
        });
    }
}
