package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by abdallah on 08/09/17.
 */

public class Movies implements Serializable {


    private String title;
    private String image;
    private Double rating;
    private String releaseYear;
    private List<String> genre;


    public Movies(String title, String image, Double rating, String releaseYear, List<String> genre) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public Movies(String title){
        this.title=title;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }
}
