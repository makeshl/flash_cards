package com.example.makmeeroo.flash_cards;

import android.content.Context;
import com.example.makmeeroo.flash_cards.Card;

import java.util.List;

/**
 * Created by MakMeeRoo on 1/15/2017.
 */
public class Lesson {

    public Lesson() {}

    private String lessonName;
    private List<Card> cards;
    private String lessonImage;

    public void setLessonName(String lessonName){this.lessonName = lessonName;}

    public String getLessonName(){return lessonName;}

    public void setCards(List<Card> cards){this.cards = cards;}

    public List<Card> getCards() {return cards;}

    public void setDisplayImage(String lessonImage) {this.lessonImage = lessonImage;}

    public String getDisplayImage() {return lessonImage;}
}
