package com.example.makmeeroo.flash_cards.DisplayData;

/**
 * Created by MakMeeRoo on 7/22/2017.
 */
public class SentenceElements {
    private String sentence;
    private String firstPart;
    private String secondPart;
    private int nSpaces;

    public SentenceElements(String sentence) {this.sentence = sentence;}
    public SentenceElements(){}

    public void setSentence(String sentence){
        this.sentence = sentence;
    }
    public void setFirstPart(String firstPart){
        this.firstPart = firstPart;
    }
    public void setSecondPart(String secondPart){
        this.secondPart = secondPart;
    }
    public void setnSpaces(int nSpaces){
        this.nSpaces = nSpaces;
    }


    public String getFirstPart(String sentence) {
        String firstPart= null;


        return firstPart;
    }
    public String getSecondPart() {return secondPart;}
    public int getnSpaces() {return nSpaces;}

    public String getSpaceString(int nSpaces) {
        String spaceString = null;
        for (int i =0; i<nSpaces; i++ ){
            spaceString = spaceString + " ";
        }
        return spaceString;
    }
}
