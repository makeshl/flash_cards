package com.example.makmeeroo.flash_cards.DisplayData;

import java.util.List;

/**
 * Created by MakMeeRoo on 11/5/2016.
 */
public class DisplayDataUser {
    private String image;
    private String lessonName;
    private int numberLessons;
    private int rating;
    private List<String> cards;

    public List<String> getCards(){
        return cards;
    }

    public void setCards(List<String> cards){
        this.cards = cards;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getNumberLessons() {
        return numberLessons;
    }

    public void setNumberLessons(int numberLessons) {
        this.numberLessons = numberLessons;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
