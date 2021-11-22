package data;

import java.util.List;

public class Movie {
    private String name;
    private int releaseYear;
    private List<Actor> cast;

    public Movie(String name, int releaseYear, List<Actor> cast) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.cast = cast;
    }

    public String getName() {
        return name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public List<Actor> getCast() {
        return cast;
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
