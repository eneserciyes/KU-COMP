import data.Actor;
import data.Movie;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MovieParser {

    private static Pattern releaseYearPattern = Pattern.compile("\\([12]\\d{3}\\)");

    //Matches the whole actor line, then the line gets split.
    private static Pattern actorPattern = Pattern.compile("(/.+)");


    static List<Movie> parseMovies(String file) {
        Matcher matcher;
        List<Movie> movies = new ArrayList<>();

        try (Scanner scanner = new Scanner(Paths.get(file))) {
            // Every line is parsed into a movie object and added to list.
            while (scanner.hasNextLine()) {
                String movie = scanner.nextLine();
                String title;
                int releaseYear = 0;
                List<Actor> cast = new ArrayList<>();

                title = movie.split("\\(")[0];

                matcher = releaseYearPattern.matcher(movie);
                if (matcher.find()) {
                    releaseYear = Integer.parseInt(matcher.group().substring(1, matcher.group().length() - 1));
                }

                matcher = actorPattern.matcher(movie);
                if (matcher.find()) {
                    String[] actors = matcher.group().split("/");
                    for (String s : actors) {
                        if (s.length() > 0) {
                            String[] actor = s.split(", ");
                            try {
                                Actor actorObject = new Actor(actor[0].trim(), actor[1].trim());
                                cast.add(actorObject);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                cast.add(new Actor("", actor[0]));
                            }
                        }
                    }
                }

                movies.add(new Movie(title.trim(), releaseYear, cast));
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println(e.getMessage());
        }

        return movies;
    }
}
