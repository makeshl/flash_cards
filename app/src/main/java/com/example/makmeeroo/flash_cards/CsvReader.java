package com.example.makmeeroo.flash_cards;

import android.util.Log;

import com.example.makmeeroo.flash_cards.DisplayData.DisplayDataUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MakMeeRoo on 11/12/2016.
 */
public class CsvReader {
    InputStream inputStream;
    //String csvFile = "C:\\Users\\Meera\\AndroidStudioProjects\\flash_cards\\app\\src\\main\\res\\raw";

    public CsvReader(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List read(){
        List<DisplayDataUser> TempList = new ArrayList<>();
//        List TempList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                DisplayDataUser dummy = new DisplayDataUser();
                dummy.setLessonName(row[0]);
                dummy.setImage(row[1]);
                dummy.setNumberLessons(row.length - 3);
                dummy.setRating(Integer.valueOf(row[2]));

                List<String> dummy1 = new ArrayList<>();
                for (int i = 3; i < row.length; i++) {dummy1.add(row[i]);}
                dummy.setCards(dummy1);

                TempList.add(dummy);

                Log.d(dummy.getLessonName()+" "+ dummy.getImage()+dummy.getRating()+" "+dummy.getNumberLessons()," " +dummy.getCards().size());
            }
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
        return TempList;
    }

}
