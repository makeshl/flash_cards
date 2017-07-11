package com.example.makmeeroo.flash_cards;

import android.content.Context;
import android.util.Log;

import com.example.makmeeroo.flash_cards.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by MakMeeRoo on 1/15/2017.
 */
public class CsvReaderV2 {
    InputStream inputStream;
    Context context;
    List<Lesson> lessons = new ArrayList<>();

    public CsvReaderV2 (InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List<Lesson> read(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");

                Lesson eachLesson = new Lesson();
                eachLesson.setLessonName(row[0]);
                eachLesson.setDisplayImage(row[1]);

                List<Card> cardsinLesson = new ArrayList<>();
                for (int i = 3; i < row.length; i++) {
                    Card eachCard = new Card(row[i]);
                    cardsinLesson.add(eachCard);
                }
                eachLesson.setCards(cardsinLesson);
                lessons.add(eachLesson);
            }
            reader.close();
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }

        for (int i=0; i<lessons.size(); i++){
            Log.d("ABC Lesson "+ i, " = "+lessons.get(i).getLessonName());
            List<Card> cardsinLesson = new ArrayList<>();
            cardsinLesson = lessons.get(i).getCards();
            for (int j=0; j<cardsinLesson.size(); j++) {
                Card eachCard = cardsinLesson.get(j);
                eachCard = cardsinLesson.get(j);
                Log.d("ABC ImageFileName" + j," = "+eachCard.getImageFileName());
                Log.d("ABC VoiceFileName" + j," = "+eachCard.getVoiceFileName());
                Log.d("ABC ImageExtLoc" + j," = "+eachCard.getImageFileExternalMemoryLocation());
                Log.d("ABC VoiceExtLoc" + j," = "+eachCard.getVoiceFileExternalMemoryLocation());
            }
        }

        return lessons;
    }

}

