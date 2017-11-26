
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings firstrating = new FirstRatings();
        myMovies = firstrating.loadMovies(moviefile);
        myRaters = firstrating.loadRaters(ratingsfile);
    }
    
    /*public ArrayList<Movie> getMovies(){
        // return the number of movies.
        return myMovies;
    }
    
    public ArrayList<Rater> getRaters(){
        // return the number of movies.
        return myRaters;
    }*/
    
    public int getMovieSize(){
        // return the number of movies.
        return myMovies.size();
    }
    
    public int getRaterSize(){
        // return the number of raters.
        return myRaters.size();
    }
    
    private double getAverageByID(String movieID, int minimalRaters){
        /* This method returns a double representing the average movie rating for this ID 
         * if there are at least minimalRaters ratings. 
           If there are not minimalRaters ratings, then it returns 0.0.*/
        int numRatings = 0;
        double totalScore = 0;
        for (Rater currRater: myRaters){
            ArrayList<String> currMovies = currRater.getItemsRated();
            for (String s: currMovies){
                if (s.equals(movieID)){
                    numRatings += 1;
                    totalScore += currRater.getRating(movieID);
                }
            }
        }
        
        if (numRatings < minimalRaters){
            return 0.0;
        } else {
            return totalScore/numRatings;
        }
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        /*
         * This method should find the average rating for every movie that has been rated by at least
         * minimalRaters raters. Store each such rating in a Rating object in which the movie ID 
         * and the average rating are used in creating the Rating object. 
         * The method getAverageRatings should return an ArrayList of all the Rating objects for movies
         * that have at least the minimal number of raters supplying a rating. 
         * For example, if minimalRaters has the value 10, then this method returns rating information
         * (the movie ID and its average rating) for each movie that has at least 10 ratings. 
         * You should consider calling the private getAverageByID method.
         */
        ArrayList<Rating> allAverageRatings = new ArrayList<Rating>();
        for (Movie currMovie: myMovies){
            String currMovieID = currMovie.getID();
            double averageRating = getAverageByID(currMovieID, minimalRaters);
            allAverageRatings.add(new Rating(currMovieID, averageRating));
        }
        return allAverageRatings;
    }
    
    public String getTitle(String movieID){
        /*
         * This method returns the title of the movie with that ID. 
         * If the movie ID does not exist, then this method should return a String indicating the ID was not found.
         */
        for (Movie currMovie: myMovies){
            if (currMovie.getID().equals(movieID)){
                return currMovie.getTitle();
            }
        }
        return "N/A";
    }
    
    public String getID(String movieTitle){
        /*
         * This method returns the movie ID of this movie. 
         * If the title is not found, return an appropriate message such as “NO SUCH TITLE.” 
         * Note that the movie title must be spelled exactly as it appears in the movie data files.
         */
        for (Movie currMovie: myMovies){
            if (currMovie.getTitle().equals(movieTitle)){
                return currMovie.getID();
            }
        }
        return "N/A";
    }
}
