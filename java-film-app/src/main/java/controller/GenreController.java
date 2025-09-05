package controller;

import model.Genre;
import repository.GenreRepository;

import java.sql.SQLException;
import java.util.List;

public class GenreController {

    private final GenreRepository repo = new GenreRepository();

    public void addGenre(Genre genre) throws SQLException {
        repo.insertGenre(genre);
    }

    public void updateGenre(Genre genre) throws SQLException {
        repo.updateGenre(genre);
    }

    public void deleteGenre(int genreId) throws SQLException {
        repo.deleteGenre(genreId);
    }

    public List<Genre> getAllGenres() throws SQLException {
        return repo.getAllGenres();
    }

    public Genre getGenreById(int id) throws SQLException {
        return repo.getGenreById(id);
    }
}
