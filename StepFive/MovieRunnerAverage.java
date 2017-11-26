
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class MovieRunnerAverage {
    
    private String movieFileName;
    private String ratingFileName;
    private SecondRatings sr;
    
    public MovieRunnerAverage(){
        movieFileName = "ratedmoviesfull.csv";
        ratingFileName = "ratings.csv";
        sr = new SecondRatings(movieFileName, ratingFileName);
    }
    
    public void printAverageRatings(){
        System.out.println("There are " + sr.getMovieSize() + " movies in the file.");
        System.out.println("There are " + sr.getRaterSize() + " raters in the file.");
        
        int numRating = 12;
        ArrayList<Rating> ratings = sr.getAverageRatings(numRating);
        Collections.sort(ratings);
        
        System.out.println("Rating values of Movies with at least " + numRating + " ratings:");
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                String currMovieID = currRating.getItem();
                System.out.println(currValue + "  " + sr.getTitle(currMovieID));
            }
        }
        /*int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
            }
        }
        System.out.println("There are " + num + " movies have " + numRating + " ratings.");*/
    }
    
    public void getAverageRatingOneMovie(){
        /*
         * This method should first create a SecondRatings object, reading in data from the movie and ratings data files. 
         * Then this method should print out the average ratings for a specific movie title
         */
        int numRating = 0;
        ArrayList<Rating> ratings = sr.getAverageRatings(numRating);
        
        String movieTitle = "Vacation";
        String movieID = sr.getID(movieTitle);
        double value = -1;
        for (Rating currRating: ratings){
            if (currRating.getItem().equals(movieID)){
                value = currRating.getValue();
            }
        }
        System.out.println("The average rating for " + movieTitle + " is " + value + ".");
    }
}
