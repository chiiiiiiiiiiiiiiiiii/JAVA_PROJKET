package util;

import model.Film;
import repository.FilmRepository;

public class FilmLoader{
    public static void main(String[] args) {
        try {
            FilmRepository repo = new FilmRepository();

            Film[] films = {
                    new Film("Inception", 2010, "Dream inside a dream.", "assets/inception.jpg"),
                    new Film("Interstellar", 2014, "Journey through space and time.", "assets/interstellar.jpg"),
                    new Film("The Matrix", 1999, "A hacker discovers the truth about reality.", "assets/matrix.jpg"),
                    new Film("The Dark Knight", 2008, "Batman faces the Joker in Gotham.", "assets/dark_knight.jpg"),
                    new Film("Pulp Fiction", 1994, "Interconnected stories of crime and redemption.", "assets/pulp_fiction.jpg"),
                    new Film("Forrest Gump", 1994, "Life is like a box of chocolates.", "assets/forrest_gump.jpg"),
                    new Film("The Shawshank Redemption", 1994, "A banker survives prison with hope.", "assets/shawshank.jpg"),
                    new Film("The Godfather", 1972, "A mafia family's power struggle.", "assets/godfather.jpg"),
                    new Film("Fight Club", 1999, "An underground club spirals into chaos.", "assets/fight_club.jpg"),
                    new Film("Gladiator", 2000, "A general seeks revenge in ancient Rome.", "assets/gladiator.jpg")
            };

            for (Film film : films) {
                repo.insertFilm(film);
            }

            System.out.println("10 films inserted successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
