package codeman.ifco.CustomListView;

import android.graphics.Bitmap;

public class Movie {
    private String movieTitle;
    private Bitmap ageRating;

    public Movie() {}

    public String getMovieTitle() { return movieTitle; }
    public Bitmap getAgeRating() { return ageRating; }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    public void setAgeRating(Bitmap ageRating) {
        this.ageRating = ageRating;
    }
}

