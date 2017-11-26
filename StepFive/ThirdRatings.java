

import java.util.*;

public class ThirdRatings {
    
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile) {
        FirstRatings firstrating = new FirstRatings();
        myRaters = firstrating.loadRaters(ratingsfile);
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
        // Get the ArrayList of Movies from MovieDatabase.
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> allAverageRatings = new ArrayList<Rating>();
        for (String currMovieID: movies){
            double averageRating = getAverageByID(currMovieID, minimalRaters);
            allAverageRatings.add(new Rating(currMovieID, averageRating));
        }
        return allAverageRatings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        /*
         * This method should create and return an ArrayList of type Rating of all the movies 
         * that have at least minimalRaters ratings and satisfies the filter criteria. 
         * This method will need to create the ArrayList of type String of movie IDs 
         * from the MovieDatabase using the filterBy method before calculating those averages.
         */
        ArrayList<String> movieIDs = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> averageRatings = new ArrayList<Rating>();
        for (String s: movieIDs){
            double ratingValue = getAverageByID(s, minimalRaters);
            averageRatings.add(new Rating(s, ratingValue));
        }
        return averageRatings;
    }
}
