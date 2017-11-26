
/**
 * Write a description of GenreFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GenreFilter implements Filter {
    private String myGenre;
    
    public GenreFilter(String genre) {
        myGenre = genre;
    }
    
    @Override
    public boolean satisfies(String id) {
        int idx = MovieDatabase.getGenres(id).indexOf(myGenre);
        if (idx != -1){
            return true;
        } else {
            return false;
        }
    }
}
