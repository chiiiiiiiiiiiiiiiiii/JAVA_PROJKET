package view.components.film;

import controller.FilmController;
import model.Actor;
import model.Film;
import model.Genre;
import view.components.AbstractEntityForm;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditFilmForm extends AbstractEntityForm<Film> {

    private final JTextField titleField;
    private final JTextField yearField;
    private final JTextArea descriptionArea;
    private final JTextField imagePathField;

    private final Film film;
    private final FilmListView listView;
    private JPanel actorPanel;
    private JPanel genrePanel;


    public EditFilmForm(FilmListView listView, Film film) {
        super("Edit Film", 500, 300);
        this.listView = listView;
        this.film = film;

        titleField = new JTextField(film.getTitle());
        yearField = new JTextField(String.valueOf(film.getYear()));
        descriptionArea = new JTextArea(film.getDescription(), 3, 20);
        imagePathField = new JTextField(film.getImagePath());

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Year:"));
        formPanel.add(yearField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(new JLabel("Image Path:"));
        formPanel.add(imagePathField);

        // Sredi jos ako zelis da bude ljepse, gumb i layout
        // remove actors and genres from form
        formPanel.add(new JLabel("Assigned Actors:"));
        actorPanel = new JPanel();
        actorPanel.setLayout(new BoxLayout(actorPanel, BoxLayout.Y_AXIS));
        formPanel.add(new JScrollPane(actorPanel));
        formPanel.add(new JLabel("Assigned Genres:"));
        genrePanel = new JPanel();
        genrePanel.setLayout(new BoxLayout(genrePanel, BoxLayout.Y_AXIS));
        formPanel.add(new JScrollPane(genrePanel));
        renderLinkedEntities();
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
            film.setTitle(titleField.getText().trim());
            film.setYear(Integer.parseInt(yearField.getText().trim()));
            film.setDescription(descriptionArea.getText().trim());
            film.setImagePath(imagePathField.getText().trim());

            new FilmController().updateFilm(film);
            JOptionPane.showMessageDialog(this, "Film updated.");
            listView.refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void renderLinkedEntities() {
        actorPanel.removeAll(); // Do we need this?
        genrePanel.removeAll(); // Do we need this?

        try {
            List<Actor> actors = new FilmController().getActorsForFilm(film.getId());
            for (Actor actor : actors) {
                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                row.add(new JLabel(actor.getFirstName() + " " + actor.getLastName()));
                JButton removeBtn = new JButton("Remove");
                removeBtn.addActionListener(e -> {
                    try {
                        new FilmController().removeActorFromFilm(film.getId(), actor.getId());
                        renderLinkedEntities();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error removing actor: " + ex.getMessage());
                    }
                });
                row.add(removeBtn);
                actorPanel.add(row);
            }

            List<Genre> genres = new FilmController().getGenresForFilm(film.getId());
            for (Genre genre : genres) {
                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                row.add(new JLabel(genre.getName()));
                JButton removeBtn = new JButton("Remove");
                removeBtn.addActionListener(e -> {
                    try {
                        new FilmController().removeGenreFromFilm(film.getId(), genre.getId());
                        renderLinkedEntities();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error removing genre: " + ex.getMessage());
                    }
                });
                row.add(removeBtn);
                genrePanel.add(row);
            }

            revalidate();
            repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load linked data: " + e.getMessage());
        }
    }

}
