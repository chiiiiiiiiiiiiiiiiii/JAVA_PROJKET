package view.components.genre;

import controller.GenreController;
import model.Genre;
import view.components.AbstractEntityForm;

import javax.swing.*;


public class EditGenreForm extends AbstractEntityForm<Genre> {

    private final JTextField nameField;
    private final Genre genre;
    private final GenreListView listView;

    public EditGenreForm(GenreListView listView, Genre genre) {
        super("Edit Genre", 300, 100);
        this.listView = listView;
        this.genre = genre;

        nameField = new JTextField(genre.getName());

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
            genre.setName(nameField.getText().trim());
            new GenreController().updateGenre(genre);
            JOptionPane.showMessageDialog(this, "Genre updated.");
            listView.refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}