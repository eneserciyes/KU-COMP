import data.Actor;
import data.Movie;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Utils {
    private List<Movie> moviesList;

    Utils(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    List<Movie> hasCoStarred(Actor[] actors) throws ArrayIndexOutOfBoundsException,NoSuchElementException {


        /* Only this method in this class leaves output handling to method because
        it is especially asked to do so in the assignment.
        * */

        //According to number of movies, string is modified.
        return moviesList.stream()
                .filter(e -> e.getCast().contains(actors[0]))
                .filter(e -> e.getCast().contains(actors[1])).collect(Collectors.toList());

    }

    void searchByLetter(String character, String orderType) {

        Comparator<Movie> movieOrder = orderType.toLowerCase().equals("ascending") ? Comparator.comparing(Movie::getName)
                : Comparator.comparing(Movie::getName).reversed();

        moviesList.stream()
                .filter(e -> e.getName().toLowerCase().startsWith(character.toLowerCase()))
                .sorted(movieOrder)
                .forEach(System.out::println);


    }

    void searchByFirstName(String name) {
        System.out.printf("Movies played by actors with first name '%s'\n", name);
        System.out.printf("%20s\t%17s\n", "Actor's Name/Surname", "Movie(s) Title(s)");
        System.out.printf("%20s\t%17s\n", "--------------------", "-----------------");

        List<Actor> actors = moviesList.stream()
                .flatMap(e -> e.getCast().stream())
                .filter(e -> e.getName().toLowerCase().equals(name.toLowerCase()))
                .sorted(Comparator.comparing(Actor::getSurname))
                .distinct()
                .collect(Collectors.toList());

        //Every movie which contains that actor is printed near.
        actors.forEach(actor->{
            System.out.printf("%-20s\t", actor);
            moviesList.stream()
                    .filter(e -> e.getCast().contains(actor))
                    .sorted(Comparator.comparing(Movie::getReleaseYear).reversed())
                    .forEach(e -> System.out.printf("%s(%s) ", e.getName(), e.getReleaseYear()));
            System.out.println();
            }
        );

    }

    void searchByYear(int beginning, int end) {
        Predicate<Movie> betweenYears = (e -> e.getReleaseYear() >= beginning &&
                e.getReleaseYear() <= end);
        System.out.printf("Movies released between %d-%d:\n", beginning, end);

        moviesList.stream()
                .filter(betweenYears)
                .sorted(Comparator.comparing(Movie::getReleaseYear).thenComparing(Movie::getName))
                .forEach(e -> System.out.printf("%-20s\t%-4s\n", e.getName(), e.getReleaseYear()));
    }


    void mostProductiveActor() {
        Map<Actor, Long> actorMovieCount;
        //All actors are flat mapped to one stream and they are grouped according to how many times they exist.
        actorMovieCount = moviesList.stream()
                .flatMap(e->e.getCast().stream())
                .collect(Collectors.groupingBy(e->e,Collectors.counting()));

        Map.Entry<Actor,Long> max = actorMovieCount.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get();
               System.out.printf("The actor with the maximum number of movies played is %s, who played in %d movies\n",
                max.getKey(),//Name of the actor
                max.getValue()); //Number of movies

        //Collecting movies of most productive actor grouped by years.
        Map<Integer,Long> yearMovieCount = moviesList.stream()
                .filter(e->e.getCast().contains(max.getKey()))
                .collect(Collectors.groupingBy(Movie::getReleaseYear,TreeMap::new,Collectors.counting()));

        long maxMoviesInYear = yearMovieCount.values().stream()
                .max(Comparator.comparing(Function.identity())).get();

        //Building the string for most active years and printing
        StringBuilder builder = new StringBuilder();
        yearMovieCount.forEach((k,v)->{
            if(v==maxMoviesInYear){
                builder.append(k).append(",");
            }
        });
        //Deletes the last added comma
        builder.deleteCharAt(builder.length()-1);
        System.out.printf("%s %s the most active year(s) with %d movies\n",builder,builder.length()>4?"were":"was",maxMoviesInYear);
    }


}
