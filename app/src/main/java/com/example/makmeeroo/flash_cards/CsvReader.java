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
    String csvFile = "C:\\Users\\Meera\\AndroidStudioProjects\\flash_cards\\app\\src\\main\\res\\raw";

    public CsvReader(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List read(){
        List<DisplayDataUser> TempList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                DisplayDataUser dummy5 = new DisplayDataUser();
                dummy5.setLessonName(row[0]);
                dummy5.setImage(row[1]);
                dummy5.setNumberLessons(Integer.valueOf(row[2]));
                dummy5.setRating(Integer.valueOf(row[3]));
                TempList.add(dummy5);
                Log.d("0,1 " + row[0] + " "+ row[1], "2,3 "+ row[2] + " "+ row[3]);
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
