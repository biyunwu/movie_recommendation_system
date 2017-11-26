
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class MovieRunnerWithFilters {
    private String ratingFileName;
    private ThirdRatings tr;
    
    public MovieRunnerWithFilters (){
        ratingFileName = "ratings.csv";
        tr = new ThirdRatings(ratingFileName);
    }
    
    public void printAverageRatings(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + tr.getRaterSize() + " raters in the file.");
        
        int numRating = 35;
        ArrayList<Rating> ratings = tr.getAverageRatings(numRating);
        Collections.sort(ratings);
        
        int num = 0;
        System.out.println("Rating values of Movies with at least " + numRating + " ratings:");
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                System.out.println(currValue + "  " + MovieDatabase.getTitle(currMovieID));
            }
        }
        System.out.println("There are " + num + " movies have at least " + numRating + " ratings.");
        /*int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
            }
        }
        System.out.println("There are " + num + " movies have " + numRating + " ratings.");*/
    }
    
    public void printAverageRatingsByYearAfter(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + tr.getRaterSize() + " raters in the file.");
        
        int numRating = 20;
        Filter filterYear = new YearAfterFilter(2000);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filterYear);
        Collections.sort(ratings);
        
        int num = 0;
        System.out.println("Rating values of Movies with at least " + numRating + " ratings:");
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                System.out.println(currValue + "  " + MovieDatabase.getYear(currMovieID) + "  " +MovieDatabase.getTitle(currMovieID));
            }
        }
        System.out.println("There are " + num + " movies have at least " + numRating + " ratings and satisfy the filter.");
    }
    
    public void printAverageRatingsByGenre(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + tr.getRaterSize() + " raters in the file.");
        
        int numRating = 20;
        Filter filterGenre = new GenreFilter("Comedy");
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filterGenre);
        Collections.sort(ratings);
        
        int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                System.out.println(currValue + "  " + MovieDatabase.getTitle(currMovieID));
                System.out.println("     " + MovieDatabase.getGenres(currMovieID));
            }
        }
        System.out.println("There are " + num + " movies have at least " + numRating + " ratings and satisfy the filter.");
    }
    
    public void printAverageRatingsByMinutes(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + tr.getRaterSize() + " raters in the file.");
        
        int numRating = 5;
        int minMinutes = 105;
        int maxMinutes = 135;
        Filter filterMinutes = new MinutesFilter(minMinutes, maxMinutes);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filterMinutes);
        Collections.sort(ratings);
        
        int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                System.out.println(currValue + "  " + "Time: " + MovieDatabase.getMinutes(currMovieID)
                                   + " " + MovieDatabase.getTitle(currMovieID));
            }
        }
        System.out.println("There are " + num + " movies have at least " + numRating + " ratings and satisfy the filter.");
    }
    
    public void printAverageRatingsByDirectors(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + tr.getRaterSize() + " raters in the file.");
        
        int numRating = 4;
        String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        Filter filterDirectors = new DirectorsFilter(directors);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filterDirectors);
        Collections.sort(ratings);
        
        int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                System.out.println(currValue + "  " + MovieDatabase.getTitle(currMovieID));
                System.out.println("    " + MovieDatabase.getDirector(currMovieID));
            }
        }
        System.out.println("There are " + num + " movies have at least " + numRating + " ratings and satisfy the filter.");
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + tr.getRaterSize() + " raters in the file.");
        
        int numRating = 8;
        Filter filterYear = new YearAfterFilter(1990);
        Filter filterGenre = new GenreFilter("Drama");
        
        AllFilters filters = new AllFilters();
        filters.addFilter(filterYear);
        filters.addFilter(filterGenre);
        
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filters);
        Collections.sort(ratings);
        
        int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                System.out.println(currValue + " " + MovieDatabase.getYear(currMovieID) + " "+MovieDatabase.getTitle(currMovieID));
                System.out.println("    " + MovieDatabase.getGenres(currMovieID));
            }
        }
        System.out.println("There are " + num + " movies have at least " + numRating + " ratings and satisfy the filters.");
    }
    
    public void printAverageRatingsByDirectorsAndMinutes (){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("There are " + MovieDatabase.size() + " movies in the file.");
        System.out.println("There are " + tr.getRaterSize() + " raters in the file.");
        
        int numRating = 3;
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        Filter filterDirectors = new DirectorsFilter(directors);
        int minMinutes = 90;
        int maxMinutes = 180;
        Filter filterMinutes = new MinutesFilter(minMinutes, maxMinutes);
        
        AllFilters filters = new AllFilters();
        filters.addFilter(filterDirectors);
        filters.addFilter(filterMinutes);
        
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(numRating, filters);
        Collections.sort(ratings);
        
        int num = 0;
        for (Rating currRating: ratings){
            double currValue = currRating.getValue();
            if(currValue != 0.0){
                num += 1;
                String currMovieID = currRating.getItem();
                System.out.println(currValue + " Time: " + MovieDatabase.getMinutes(currMovieID) + " "+ MovieDatabase.getTitle(currMovieID));
                System.out.println("    " + MovieDatabase.getDirector(currMovieID));
            }
        }
        System.out.println("There are " + num + " movies have at least " + numRating + " ratings and satisfy the filters.");
    }
    
}
