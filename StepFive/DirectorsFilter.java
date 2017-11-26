
public class DirectorsFilter implements Filter {
    private String myDirectors;
    
    public DirectorsFilter(String directors) {
        myDirectors = directors;
    }
    
    @Override
    public boolean satisfies(String id) {
        String[] directors = myDirectors.split(",");
        
        for (int k=0; k < directors.length; k++){
            if (MovieDatabase.getDirector(id).indexOf(directors[k]) != -1){
                return true;
            }
        }
        return false;
        
        /*int idx = MovieDatabase.getDirector(id).indexOf(myDirectors);
        if (idx != -1){
            return true;
        } else {
            return false;
        }*/
    }
}