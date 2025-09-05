package controller;

import model.Film;
import model.Actor;
import model.Genre;
import repository.FilmRepository;

import java.sql.SQLException;
import java.util.List;

public class FilmController {

    private final FilmRepository repo = new FilmRepository();

    public void addFilm(Film film) throws SQLException {
        repo.insertFilm(film);
    }

    public void updateFilm(Film film) throws SQLException {
        repo.updateFilm(film);
    }

    public void deleteFilm(int filmId) throws SQLException {
        repo.deleteFilm(filmId);
    }

    public List<Film> getAllFilms() throws SQLException {
        return repo.getAllFilms();
    }

    public Film getFilmById(int id) throws SQLException {
        return repo.getFilmById(id);
    }

    public void assignActorsToFilm(int filmId, List<Integer> actorIds) throws SQLException {
        repo.assignActorsToFilm(filmId, actorIds);
    }

    public void assignGenresToFilm(int filmId, List<Integer> genreIds) throws SQLException {
        repo.assignGenresToFilm(filmId, genreIds);
    }

    public void removeActorFromFilm(int filmId, int actorId) throws SQLException {
        repo.removeActorFromFilm(filmId, actorId);
    }

    public void removeGenreFromFilm(int filmId, int genreId) throws SQLException {
        repo.removeGenreFromFilm(filmId, genreId);
    }

    public List<Actor> getActorsForFilm(int filmId) throws SQLException {
        return repo.getActorsForFilm(filmId);
    }

    public List<Genre> getGenresForFilm(int filmId) throws SQLException {
        return repo.getGenresForFilm(filmId);
    }

    public int getLastInsertedFilmId() throws SQLException {
        return repo.getLastInsertedFilmId();
    }
}
