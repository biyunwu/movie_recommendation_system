
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;

public class FirstRatings {
    //This method should process every record from the CSV file whose name is filename, 
    // a file of movie information, and return an ArrayList of type Movie with all of the movie data from the file.
    public ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        FileResource fr = new FileResource(filename);
        CSVParser movieParser = fr.getCSVParser();
        for (CSVRecord currentRow: movieParser){
            String currId = currentRow.get("id");
            String currTitle = currentRow.get("title");
            String currYear = currentRow.get("year");
            String currGenres = currentRow.get("genre");
            String currDirector = currentRow.get("director");
            String currCountry = currentRow.get("country");
            String currPoster = currentRow.get("poster");
            int currMinutes = Integer.parseInt(currentRow.get("minutes"));
            
            movies.add(new Movie(currId, currTitle, currYear, currGenres, 
                                currDirector, currCountry, currPoster, currMinutes));
        }
        return movies;
    }
    
    public ArrayList<Rater> loadRaters(String filename){
        ArrayList<Rater> raters = new ArrayList<Rater>();
        FileResource fr = new FileResource("data/" + filename);
        CSVParser raterParser = fr.getCSVParser();
        // Initial the String to check if the rater is existed or not. 
        String existed = "";
        for (CSVRecord currentRow: raterParser){
            String raterId = currentRow.get("rater_id");
            String movieId = currentRow.get("movie_id");
            double rating = Double.parseDouble(currentRow.get("rating"));
            
            // Ckeck if the current rater is existed in the ArrayList "raters" or not.
            boolean exist = false;
            /*for (Rater r: raters){
                if (r.getID().equals(raterId)){
                    exist = true;
                }
            }*/
            if (existed.indexOf(raterId) != -1){
                exist = true;
            }
            
            if (exist == false){
                //Rater currRater = new PlainRater(raterId);
                Rater currRater = new EfficientRater(raterId);
                currRater.addRating(movieId, rating);
                raters.add(currRater);
                // Add the rater's ID to the "existed" String.
                existed += raterId + " ";
            } else {
                for (Rater r: raters){
                    if (r.getID().equals(raterId)){
                        r.addRating(movieId, rating);
                    }
                }
            }
        }
        return raters;
    }
    
    /*public ArrayList<Rater> loadRaters1(String filename){
        ArrayList<Rater> raters = new ArrayList<Rater>();
        FileResource fr = new FileResource("data/" + filename);
        CSVParser raterParser = fr.getCSVParser();
        ArrayList<String> IDs = new ArrayList<String>();
        for (CSVRecord currentRow: raterParser){
            String raterId = currentRow.get("rater_id");
            if (!IDs.contains(raterId)){
                IDs.add(raterId);
            }
        }
        for (String s: IDs){
            Rater currRater = new Rater(s);
            for (CSVRecord currentRow: raterParser){
                if (s.equals(currentRow.get("rater_id"))){
                    String movieId = currentRow.get("movie_id");
                    double rating = Double.parseDouble(currentRow.get("rating"));
                    currRater.addRating(movieId, rating);
                }
            }
            raters.add(currRater);
        }
        return raters;
    }*/
    
    public void findNumOfRater(String filename, String RaterID){
        ArrayList<Rater> raters = loadRaters(filename);
        Rater result = null;
        for (Rater currRater: raters){
            if (currRater.getID().equals(RaterID)){
                result = currRater;
            }
        }
        int num = result.numRatings();
        System.out.println("There are " + num + " ratings of " + "ID " + RaterID);
    }
    
    public void findMaxNumOfRatingsByRater(String filename){
        ArrayList<Rater> raters = loadRaters(filename);
        int max = 0;
        for (Rater currRater: raters){
            if (currRater.numRatings() > max){
                max = currRater.numRatings();
            }
        }
        System.out.println("The maximum number of ratings of the rater(s) is " + max + ". Their IDs are:");
        String s = "";
        for (Rater currRater: raters){
            if (currRater.numRatings() == max){
                s += currRater.getID() + ", ";
            }
        }
        System.out.println(s.substring(0, s.length()-2));
    }
    
    public void findRatingsOfMovie(String filename, String movieID){
        ArrayList<Rater> raters = loadRaters(filename);
        int num = 0;
        for (Rater currRater: raters){
            ArrayList<String> movies = currRater.getItemsRated();
            if (movies.contains(movieID)){
                num += 1;
            }
        }
        System.out.println("Movie with ID " + movieID + " was rated by " + num + " raters.");
    }
    
    public void countRatedMovies(String filename){
        ArrayList<Rater> raters = loadRaters(filename);
        ArrayList<String> movies = new ArrayList<String>();
        for (Rater currRater: raters){
            ArrayList<String> currMovies = currRater.getItemsRated();
            for (String s: currMovies){
                if (!movies.contains(s)){
                    movies.add(s);
                }
            }
        }
        System.out.println("There are " + movies.size() + " movies rated.");
    }
    
    public void test(){
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            String filename = f.getName();
            System.out.println("Processing file: " + filename);
            System.out.println(" ");
            //testLoadMovies(filename);
            //testLoadRaters(filename);
            findNumOfRater(filename, "193");
            //findMaxNumOfRatingsByRater(filename);
            //findRatingsOfMovie(filename, "1798709");
            //countRatedMovies(filename);
        }
    }
    
    public void testLoadMovies(String filename){
        //DirectoryResource dr = new DirectoryResource();
        //for (File f: dr.selectedFiles()){
            //String filename = f.getName();
            //System.out.println("Processing file: " + filename);
            ArrayList<Movie> movies = loadMovies(filename);
            System.out.println("There are " + movies.size() + " records.");
            //System.out.println(movie);
            
            int numComedy = 0;
            for (Movie currMovie: movies){
                if (currMovie.getGenres().indexOf("Comedy") != -1){
                    numComedy += 1;
                }
            }
            System.out.println("There are " + numComedy + " comedy movies in the file.");
            
            int numLength150 = 0;
            for (Movie currMovie: movies){
                if (currMovie.getMinutes() > 150){
                    numLength150 += 1;
                }
            }
            System.out.println("There are " + numLength150 + " movies which their lengths are more than 150 min.\n");
            
            // Remember that some movies may have more than one director.
            HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
            for (Movie currMovie: movies){
                String director = currMovie.getDirector().trim();
                if (director.indexOf(",") == -1){
                    if (!map.containsKey(director)){
                        map.put(director, new ArrayList<String>());
                    }
                    String title = currMovie.getTitle();
                    map.get(director).add(title);
                  
                } else {
                    while (director.indexOf(",") != -1){
                        int idxComma = director.indexOf(",");
                        String currDirector = director.substring(0, idxComma);
                        
                        if (!map.containsKey(currDirector)){
                            map.put(currDirector, new ArrayList<String>());
                        }
                        String title = currMovie.getTitle();
                        map.get(currDirector).add(title);
                        
                        director = director.substring(idxComma+1).trim();
                    }
                }
            }
            
            int maxNumOfMoviesByDirector = 0;
            for (String s: map.keySet()){
                if (map.get(s).size() > maxNumOfMoviesByDirector){
                    maxNumOfMoviesByDirector = map.get(s).size();
                }
            }
            System.out.println("The maximum number of films directed by one director is " + maxNumOfMoviesByDirector);
            
            String directorWithMaxMovies = "";
            for (String s: map.keySet()){
                if (map.get(s).size() == maxNumOfMoviesByDirector){
                    directorWithMaxMovies += s + ", ";
                }
            }
            System.out.println("Names of the directors who directed the maximum number of movies " +
                                directorWithMaxMovies.substring(0, directorWithMaxMovies.length()-2));
    }
    
    public void testLoadRaters(String filename){
        ArrayList<Rater> raters = loadRaters(filename);
        System.out.println("There are " + raters.size() + " raters.");
        System.out.println(" ");
        /*for (Rater currRater: raters){
            System.out.println("Rater ID " + currRater.getID() + ": " + currRater.numRatings() + " ratings.");
            ArrayList<String> items = currRater.getItemsRated();
            for (String item: items){
                double rating = currRater.getRating(item);
                System.out.print(item + " " + rating + "; ");
            }
            System.out.println("\n");
        }*/
    }
    
    
}
