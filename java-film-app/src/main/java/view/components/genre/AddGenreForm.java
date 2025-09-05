package view.components.genre;

import controller.GenreController;
import model.Genre;
import view.components.AbstractEntityForm;

import javax.swing.*;

public class AddGenreForm extends AbstractEntityForm<Genre> {

    private final JTextField nameField = new JTextField();
    private final GenreListView listView;

    public AddGenreForm(GenreListView listView) {
        super("Add Genre", 300, 100);
        this.listView = listView;

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
    }

    @Override
    protected boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return false;
        }
        return true;
    }

    @Override
    protected void onSave() {
        try {
            new GenreController().addGenre(new Genre(nameField.getText().trim()));
            JOptionPane.showMessageDialog(this, "Genre added.");
            listView.refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
