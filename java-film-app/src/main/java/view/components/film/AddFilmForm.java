package view.components.film;

import controller.FilmController;
import model.Film;
import view.components.AbstractEntityForm;

import javax.swing.*;

public class AddFilmForm extends AbstractEntityForm<Film> {

    private final JTextField titleField = new JTextField();
    private final JTextField yearField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea(3, 20);
    private final JTextField imagePathField = new JTextField();

    private final FilmListView listView;

    public AddFilmForm(FilmListView listView) {
        super("Add Film", 400, 300);
        this.listView = listView;

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Year:"));
        formPanel.add(yearField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(new JLabel("Image Path:"));
        formPanel.add(imagePathField);
    }

    @Override
    protected boolean validateInput() {
        if (titleField.getText().trim().isEmpty() || yearField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title and year are required.");
            return false;
        }
        try {
            Integer.parseInt(yearField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Year must be a number.");
            return false;
        }
        return true;
    }

    @Override
    protected void onSave() {
        try {
            String title = titleField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            String desc = descriptionArea.getText().trim();
            String image = imagePathField.getText().trim();

            Film film = new Film(title, year, desc, image);
            new FilmController().addFilm(film);
            JOptionPane.showMessageDialog(this, "Film added.");
            listView.refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
