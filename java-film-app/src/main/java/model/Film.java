package model;

import java.util.List;
import java.util.Objects;

public class Film {
    private int id;
    private String title;
    private int year;
    private String description;
    private String imagePath;
    private List<Genre> genres;

    // Default constructor
    public Film() {
    }

    // Used for new films (before insert)
    public Film(String title, int year, String description, String imagePath) {
        this.title = title;
        this.year = year;
        this.description = description;
        this.imagePath = imagePath;
    }

    // Used for existing films (after loading from DB)
    public Film(int id, String title, int year, String description, String imagePath) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.description = description;
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // toString
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", genres=" + genres +
                '}';
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return id == film.id;
    }

    // hashCode (based on id)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}