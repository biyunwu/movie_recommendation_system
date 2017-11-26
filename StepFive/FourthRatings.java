
/**
 * Write a description of FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


import java.util.*;

public class FourthRatings {
    
    public FourthRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public FourthRatings(String ratingsfile) {
        RaterDatabase.initialize(ratingsfile);
    }
    
    public int getRaterSize(){
        // return the number of raters.
        return RaterDatabase.size();
    }
    
    private double getAverageByID(String movieID, int minimalRaters){
        /* This method returns a double representing the average movie rating for this ID 
         * if there are at least minimalRaters ratings. 
           If there are not minimalRaters ratings, then it returns 0.0.*/
        int numRatings = 0;
        double totalScore = 0;
        for (Rater currRater: RaterDatabase.getRaters()){
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
    
    private double dotProduct(Rater me, Rater r){
        /*
         * This method should first translate a rating from the scale 0 to 10 to the scale -5 to 5 
         * and return the dot product of the ratings of movies that they both rated. 
         * This method will be called by getSimilarities.
         */
        double result = 0.0;
        ArrayList<String> myMovieIDs = me.getItemsRated();
        ArrayList<String> otherMovieIDs = r.getItemsRated();
        for (String s: myMovieIDs){
            if (otherMovieIDs.contains(s)){
                double myValue = me.getRating(s) - 5;
                double otherValue = r.getRating(s) - 5;
                result += myValue*otherValue;
            }
        }
        return result;
    }
    
    private ArrayList<Rating> getSimilarities(String id){
        /*
         * this method computes a similarity rating for each rater in the RaterDatabase 
         * (except the rater with the ID given by the parameter) to see how similar they are to the Rater 
         * whose ID is the parameter to getSimilarities. 
         * This method returns an ArrayList of type Rating sorted by ratings 
         * from highest to lowest rating with the highest rating first and only including those raters 
         * who have a positive similarity rating since those with negative values are not similar in any way. 
         * Note that in each Rating object the item field is a rater’s ID, 
         * and the value field is the dot product comparison between that rater and the rater 
         * whose ID is the parameter to getSimilarities. 
         * Be sure not to use the dotProduct method with parameter id and itself!
         */
        ArrayList<Rating> list = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        for (Rater r: RaterDatabase.getRaters()){
            String currOtherID = r.getID();
            if (!currOtherID.equals(id)){
                double currDotProduct = dotProduct(me, r);
                if (currDotProduct > 0.0){
                    list.add(new Rating(currOtherID, currDotProduct));
                }
            }
        }
        Collections.sort(list, Collections.reverseOrder());
        // The instance variables in every object of the arraylist are ID(String) and dotProduct(double).
        return list; 
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
        /*
         * This method should return an ArrayList of type Rating, of movies and their weighted average ratings 
         * using only the top numSimilarRaters with positive ratings and including only those movies 
         * that have at least minimalRaters ratings from those most similar raters (not just minimalRaters ratings overall). 
         * For example, if minimalRaters is 3 and a movie has 4 ratings but only 2 of those ratings 
         * were made by raters in the top numSimilarRaters, that movie should not be included. 
         * These Rating objects should be returned in sorted order by weighted average rating from largest to smallest ratings. 
         * This method is very much like the getAverageRatings method you have written previously.
         */
        ArrayList<Rating> similarList = getSimilarities(id);
        // System.out.println("Similar raters list: " + similarList); // Test
        // Key: Movies' IDs.  Value: RaterID and ratring value.
        HashMap<String, HashMap<String, Double>> recMap = new HashMap<String, HashMap<String, Double>>();
        if (similarList.size() < numSimilarRaters){
            numSimilarRaters = similarList.size();
        }
        for (int k=0; k<numSimilarRaters; k++){
            String currRaterID = similarList.get(k).getItem();
            Rater currRater = RaterDatabase.getRater(currRaterID);
            ArrayList<String> ratedMovies = currRater.getItemsRated();
            for (String currMovie: ratedMovies){
                if (!recMap.containsKey(currMovie)){
                    HashMap<String, Double> first = new HashMap<String, Double>();
                    first.put(currRaterID, currRater.getRating(currMovie));
                    recMap.put(currMovie, first);
                } else {
                    recMap.get(currMovie).put(currRaterID, currRater.getRating(currMovie));
                }
            }
        }
        // System.out.println(recMap);
        
        ArrayList<Rating> result = new ArrayList<Rating>();
        for (String currMovie : recMap.keySet()){
            HashMap<String, Double> currValueMap = recMap.get(currMovie);
            if (currValueMap.size() >= minimalRaters){
                double total = 0.0;
                for (String currRaterID: currValueMap.keySet()){
                    double currSimilarRating = 0.0;
                    // Find similar rating for the currRater.
                    for (Rating r: similarList){
                        if (r.getItem().equals(currRaterID)){
                            currSimilarRating = r.getValue();
                        }
                    }
                    total += currValueMap.get(currRaterID)*currSimilarRating;
                }
                double weightedAverage = total/currValueMap.size();
                result.add(new Rating(currMovie, weightedAverage));
            }
        }
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }
    
}
